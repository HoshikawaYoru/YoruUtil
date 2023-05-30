package net.hoshikawayoru.yoruutil.io.file.download;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreadDownload {

    private static final int BUFFER_SIZE = 1024 * 1024;

    private String url;
    private String savePath;
    private int threadCount;
    private long startTime;
    private long endTime;

    public MultiThreadDownload(String fileUrl, String saveDir, int threadCount) {
        this.url = fileUrl;
        this.savePath = saveDir + File.separator + new File(fileUrl).getName();
        this.threadCount = threadCount;
    }

    public void download() throws Exception {
        File file = new File(savePath);
        if (!file.exists()) {
            file.createNewFile();
        }

        long fileSize = getFileSize();

        startTime = System.currentTimeMillis();

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        long blockSize = fileSize / threadCount;
        for (int i = 0; i < threadCount; i++) {
            long start = i * blockSize;
            long end = (i == threadCount - 1) ? fileSize - 1 : start + blockSize - 1;
            executorService.submit(new DownloadTask(this.url, this.savePath, start, end, latch));
        }

        latch.await();
        executorService.shutdown();

        endTime = System.currentTimeMillis();
    }

    public String getFileName() {
        return new File(url).getName();
    }

    public long getFileSize() throws IOException {
        URL url = new URL(this.url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("HEAD");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);
        int code = conn.getResponseCode();
        if (code == 200) {
            return conn.getContentLengthLong();
        } else if (code == 206) {
            String range = conn.getHeaderField("Content-Range");
            return Long.parseLong(range.substring(range.lastIndexOf("/") + 1));
        } else {
            throw new RuntimeException("Unsupported response code: " + code);
        }
    }

    public long getDownloadTime(){
        return endTime - startTime;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    private static class DownloadTask implements Runnable {

        private final String url;
        private final String savePath;
        private final long start;
        private final long end;
        private final CountDownLatch latch;

        public DownloadTask(String url, String savePath, long start, long end, CountDownLatch latch) {
            this.url = url;
            this.savePath = savePath;
            this.start = start;
            this.end = end;
            this.latch = latch;
        }

        @Override
        public void run() {
            HttpURLConnection conn = null;
            FileChannel fileChannel = null;
            SocketChannel socketChannel = null;
            try {
                conn = (HttpURLConnection) new URL(url).openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);
                conn.setRequestProperty("Range", "bytes=" + start + "-" + end);
                int code = conn.getResponseCode();
                if (code != 206) {
                    throw new RuntimeException("Unsupported response code: " + code);
                }

                int port;
                if (new URL(url).getProtocol().equals("http")){
                    port = 80;
                }else if (new URL(url).getProtocol().equals("https")){
                    port = 443;
                }else {
                    port = -1;
                }

                fileChannel = new RandomAccessFile(savePath, "rw").getChannel();
                socketChannel = SocketChannel.open(new InetSocketAddress(new URL(url).getHost(), port));
                socketChannel.configureBlocking(true);
                socketChannel.socket().setSoTimeout(5000);
                ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

                while (fileChannel.position() < end) {
                    buffer.clear();
                    int length = socketChannel.read(buffer);
                    if (length == -1) {
                        break;
                    }
                    buffer.flip();
                    fileChannel.write(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (socketChannel != null) {
                        socketChannel.close();
                    }
                    if (fileChannel != null) {
                        fileChannel.close();
                    }
                    if (conn != null) {
                        conn.disconnect();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            }
        }
    }
}