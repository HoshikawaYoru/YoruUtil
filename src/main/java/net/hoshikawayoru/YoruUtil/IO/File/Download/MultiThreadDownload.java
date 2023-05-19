package net.hoshikawayoru.YoruUtil.IO.File.Download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class MultiThreadDownload {
    private final String downloadFileUrl;
    private final String saveDirectoryPath;
    private final String saveFilePath;
    private final int threadCount;
    private long downloadTime = 0;
    private long fileLength;


    public MultiThreadDownload(String downloadFileUrl, String saveDirectoryPath, int threadCount){
        this.downloadFileUrl = downloadFileUrl;
        this.saveDirectoryPath = saveDirectoryPath;
        this.threadCount = threadCount;
        this.saveFilePath = this.saveDirectoryPath + "\\" + getFileName();
    }

    public void start() throws IOException, InterruptedException {
        MultiThread multiThread = new MultiThread(downloadFileUrl, saveDirectoryPath, threadCount);
        fileLength = multiThread.getLength();
        multiThread.run();

        File oldFile = new File(saveFilePath + ".downloading");
        File newFile = new File(saveDirectoryPath + "\\" + getFileName());

        boolean flag = oldFile.renameTo(newFile);

        while (!flag){
            flag = oldFile.renameTo(newFile);
            downloadTime++;
            Thread.sleep(1);
        }
    }

    public long getDownloadTime(){
        return downloadTime;
    }

    public String getFileName() {
        int lastUnixPos = downloadFileUrl.lastIndexOf(47);
        int lastWindowsPos = downloadFileUrl.lastIndexOf(92);
        int index = Math.max(lastUnixPos, lastWindowsPos);
        return downloadFileUrl.substring(index + 1);
    }

    public long getFileLength(){
        return fileLength;
    }


    static class MultiThread {

        private final String downloadFileUrl;
        private final String saveFilePath;
        private final int threadCount;


        MultiThread(String DownloadFileUrl, String SaveFilePath, int ThreadCount ) {
            this.downloadFileUrl = DownloadFileUrl;
            this.saveFilePath = SaveFilePath;
            this.threadCount = ThreadCount;
        }


        public void run() {
            long normalThreadSize;
            boolean AllSame = false;

            try {
                if ((getLength() % threadCount) == 0){
                    normalThreadSize = getLength() / threadCount;
                    AllSame = true;
                }else {
                    normalThreadSize = getLength() / (threadCount - 1);
                }

                if (AllSame){
                    for (int i = 0; i < threadCount; i++) {
                        Thread thread = new Thread(new ThreadDownload(downloadFileUrl, saveFilePath + "\\" + getFileName() + ".downloading", String.valueOf(i * normalThreadSize), String.valueOf((i + 1) * normalThreadSize), getLength()));
                        thread.start();
                    }
                }else {
                    for (int i = 0; i < threadCount; i++) {
                        Thread thread;
                        if (i + 1 == threadCount){
                            thread = new Thread(new ThreadDownload(downloadFileUrl, saveFilePath + "\\" + getFileName() + ".downloading", String.valueOf(i * normalThreadSize), String.valueOf(getLength()), getLength()));
                        }else {
                            thread = new Thread(new ThreadDownload(downloadFileUrl, saveFilePath + "\\" + getFileName() + ".downloading", String.valueOf(i * normalThreadSize), String.valueOf((i + 1) * normalThreadSize), getLength()));
                        }
                        thread.start();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public long getLength() throws IOException {
            URL url = new URL(downloadFileUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            long length = httpURLConnection.getContentLengthLong();
            httpURLConnection.disconnect();
            return length;
        }

        public String getFileName() throws IOException {
            int lastUnixPos = downloadFileUrl.lastIndexOf(47);
            int lastWindowsPos = downloadFileUrl.lastIndexOf(92);
            int index = Math.max(lastUnixPos, lastWindowsPos);
            return downloadFileUrl.substring(index + 1);
        }


        static class ThreadDownload implements Runnable{
            private final String downloadFileUrl;
            private final String saveFilePath;
            private final String startPos;
            private final String stopPos;
            private final long fileSize;


            ThreadDownload(String downloadFileUrl, String saveFilePath, String startPos, String stopPos, long fileSize){
                this.downloadFileUrl = downloadFileUrl;
                this.saveFilePath = saveFilePath;
                this.startPos = startPos;
                this.stopPos = stopPos;
                this.fileSize = fileSize;
            }

            @Override
            public void run() {
                try {
                    URL url = new URL(downloadFileUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("Range","bytes=" + startPos + "-" + stopPos);
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL){
                        RandomAccessFile file = new RandomAccessFile(saveFilePath,"rwd");
                        file.setLength(fileSize);
                        file.seek(Long.parseLong(startPos));
                        InputStream inputStream = connection.getInputStream();

                        byte[] bytes = new byte[8192];
                        int len;
                        while ((len = inputStream.read(bytes)) > 0){
                            file.write(bytes, 0, len);
                        }
                        file.close();
                        inputStream.close();
                    }
                    connection.disconnect();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
