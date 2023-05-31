package net.hoshikawayoru.yoruutil.io.file.download;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadDownload {
    public static void download(String fileUrl, File saveDir, int threadCount) throws IOException {
        if (fileUrl == null || saveDir == null || saveDir.isFile() || threadCount <= 0){
            return;
        }

        if (saveDir.exists()){
            saveDir.mkdirs();
        }

        File saveFile = new File(saveDir + "/" + new File(fileUrl).getName());

        if (!saveFile.exists()){
            saveFile.createNewFile();
        }

        URL url = new URL(fileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestProperty("User-Agent", "Mozilla/5.0");

        int fileSize = conn.getContentLength();
        conn.disconnect();

        int blockSize = fileSize / threadCount;
        int lastBlock = fileSize - (threadCount - 1) * blockSize;

        RandomAccessFile raf = new RandomAccessFile(saveFile, "rwd");
        raf.setLength(fileSize);

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

        for (int i = 0;i < threadCount;i++){
            int startPos = i * blockSize;
            int endPos = i == threadCount - 1 ? fileSize - 1 : startPos + blockSize - 1;

            DownloadThread thread = new DownloadThread(fileUrl, startPos, endPos, saveFile);

            executorService.submit(thread);
        }
        raf.close();
        conn.disconnect();
        executorService.shutdown();
    }



    private static class DownloadThread extends Thread {
        private final String fileUrl;
        private final int startPos;
        private final int endPos;
        private final File saveFile;

        public DownloadThread(String fileUrl, int startPos, int endPos, File saveFile) {
            this.fileUrl = fileUrl;
            this.startPos = startPos;
            this.endPos = endPos;
            this.saveFile = saveFile;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            try {
                RandomAccessFile raf = new RandomAccessFile(saveFile, "rwd");
                URL url = new URL(fileUrl);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla/5.0");
                conn.setRequestProperty("Range", "bytes=" + startPos + "-" + endPos);
                int responseCode = conn.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_PARTIAL && responseCode != HttpURLConnection.HTTP_OK) {
                    throw new IOException("Server returned HTTP response code: " + responseCode);
                }

                byte[] buffer = new byte[4096];
                int bytesRead;
                raf.seek(startPos);
                while ((bytesRead = conn.getInputStream().read(buffer)) != -1) {
                    raf.write(buffer, 0, bytesRead);
                }
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }
    }
}
