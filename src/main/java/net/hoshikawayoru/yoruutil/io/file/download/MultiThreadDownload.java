package net.hoshikawayoru.yoruutil.io.file.download;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MultiThreadDownload {

    private String fileUrl;
    private String saveDir;
    private int threadCount;

    public MultiThreadDownload(String downloadUrl, String saveDir, int threadCount) {
        this.fileUrl = downloadUrl;
        this.saveDir = saveDir;
        this.threadCount = threadCount;
    }

    public void download() {
        try {
            URL url = new URL(fileUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            int fileSize = conn.getContentLength();

            String fileName = getFileName();

            new File(saveDir).mkdirs();

            File saveFile = new File(saveDir, fileName);

            long downloadedSize = 0;
            if (saveFile.exists()) {
                downloadedSize = saveFile.length();
            }

            RandomAccessFile randomAccessFile = new RandomAccessFile(saveFile, "rw");
            randomAccessFile.setLength(fileSize);

            int blockSize = fileSize / threadCount;

            for (int i = 0; i < threadCount; i++) {
                int startPosition = i * blockSize + (int) downloadedSize;
                int endPosition = (i == threadCount - 1) ? fileSize - 1 : (i + 1) * blockSize - 1;

                new DownloadThread(fileUrl, saveFile.getAbsolutePath(), startPosition, endPosition, i).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getSaveDir() {
        return saveDir;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public String getFileName(){
        return new File(fileUrl).getName();
    }

    public long getFileLength() throws IOException {
        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        long fileSize = conn.getContentLengthLong();
        conn.disconnect();
        return fileSize;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public void setSaveDir(String saveDir) {
        this.saveDir = saveDir;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    private class DownloadThread extends Thread {

        private final String downloadUrl;
        private final String saveFilePath;
        private final int startPosition;
        private final int endPosition;
        public DownloadThread(String downloadUrl, String saveFilePath, int startPosition, int endPosition, int threadId) {
            this.downloadUrl = downloadUrl;
            this.saveFilePath = saveFilePath;
            this.startPosition = startPosition;
            this.endPosition = endPosition;
        }

        @Override
        public void run() {
            try {
                URL url = new URL(downloadUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                String range = "bytes=" + startPosition + "-" + endPosition;
                conn.setRequestProperty("Range", range);

                InputStream inputStream = conn.getInputStream();
                RandomAccessFile randomAccessFile = new RandomAccessFile(saveFilePath, "rw");
                randomAccessFile.seek(startPosition);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                    randomAccessFile.write(buffer, 0, length);
                }

                inputStream.close();
                randomAccessFile.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
