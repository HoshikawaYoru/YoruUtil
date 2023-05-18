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

        private final String DownloadFileUrl;
        private final String SaveFilePath;
        private final int ThreadCount;


        MultiThread(String DownloadFileUrl, String SaveFilePath, int ThreadCount ) {
            this.DownloadFileUrl = DownloadFileUrl;
            this.SaveFilePath = SaveFilePath;
            this.ThreadCount = ThreadCount;
        }


        public void run() {
            long NormalThreadSize;
            boolean AllSame = false;

            try {
                if ((getLength() % ThreadCount) == 0){
                    NormalThreadSize = getLength() / ThreadCount;
                    AllSame = true;
                }else {
                    NormalThreadSize = getLength() / (ThreadCount - 1);
                }

                if (AllSame){
                    for (int i = 0;i < ThreadCount;i++) {
                        Thread thread = new Thread(new ThreadDownload(DownloadFileUrl, SaveFilePath + "\\" + getFileName() + ".downloading", String.valueOf(i * NormalThreadSize), String.valueOf((i + 1) * NormalThreadSize), getLength()));
                        thread.start();
                    }
                }else {
                    for (int i = 0;i < ThreadCount;i++) {
                        Thread thread;
                        if (i + 1 == ThreadCount){
                            thread = new Thread(new ThreadDownload(DownloadFileUrl, SaveFilePath + "\\" + getFileName() + ".downloading", String.valueOf(i * NormalThreadSize), String.valueOf(getLength()), getLength()));
                        }else {
                            thread = new Thread(new ThreadDownload(DownloadFileUrl, SaveFilePath + "\\" + getFileName() + ".downloading", String.valueOf(i * NormalThreadSize), String.valueOf((i + 1) * NormalThreadSize), getLength()));
                        }
                        thread.start();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public long getLength() throws IOException {
            URL url = new URL(DownloadFileUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            long length = httpURLConnection.getContentLengthLong();
            httpURLConnection.disconnect();
            return length;
        }

        public String getFileName() throws IOException {
            int lastUnixPos = DownloadFileUrl.lastIndexOf(47);
            int lastWindowsPos = DownloadFileUrl.lastIndexOf(92);
            int index = Math.max(lastUnixPos, lastWindowsPos);
            return DownloadFileUrl.substring(index + 1);
        }


        static class ThreadDownload implements Runnable{
            private final String DownloadFileUrl;
            private final String SaveFilePath;
            private final String StartPos;
            private final String StopPos;
            private final long FileSize;


            ThreadDownload(String DownloadFileUrl, String SaveFilePath, String StartPos, String StopPos, long FileSize){
                this.DownloadFileUrl = DownloadFileUrl;
                this.SaveFilePath = SaveFilePath;
                this.StartPos = StartPos;
                this.StopPos = StopPos;
                this.FileSize = FileSize;
            }

            @Override
            public void run() {
                try {
                    URL url = new URL(DownloadFileUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("Range","bytes=" + StartPos + "-" + StopPos);
                    if (connection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL){
                        RandomAccessFile file = new RandomAccessFile(SaveFilePath,"rwd");
                        file.setLength(FileSize);
                        file.seek(Long.parseLong(StartPos));
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
