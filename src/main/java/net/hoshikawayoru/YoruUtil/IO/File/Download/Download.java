package net.hoshikawayoru.YoruUtil.IO.File.Download;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Download {
    private final String downloadFileUrl;
    private final String saveDirectoryPath;
    private long downloadTime;

    public Download(String downloadFileUrl, String saveDirectoryPath){
        this.downloadFileUrl  = downloadFileUrl;
        this.saveDirectoryPath = saveDirectoryPath;
    }

    public void start() throws IOException {

        URL url = new URL(downloadFileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Range","bytes=0" + "-" + getLength());
        if (connection.getResponseCode() == HttpURLConnection.HTTP_PARTIAL){
            RandomAccessFile file = new RandomAccessFile(saveDirectoryPath + "\\" + getFileName() + ".downloading","rwd");
            file.setLength(getLength());
            file.seek(0);
            InputStream inputStream = connection.getInputStream();
            downloadTime = System.currentTimeMillis();
            byte[] bytes = new byte[8192];
            int len;
            while ((len = inputStream.read(bytes)) > 0){
                file.write(bytes, 0, len);
            }
            downloadTime = System.currentTimeMillis() - downloadTime;
            file.close();
            inputStream.close();

            File oldFile = new File(saveDirectoryPath + "\\" + getFileName() + ".downloading");


            if (!oldFile.renameTo(new File(saveDirectoryPath + "\\" + getFileName()))){
                throw new IOException("Rename File Exception");
            }
        }
        connection.disconnect();
    }

    public String getFileName(){
        int lastUnixPos = downloadFileUrl.lastIndexOf(47);
        int lastWindowsPos = downloadFileUrl.lastIndexOf(92);
        int index = Math.max(lastUnixPos, lastWindowsPos);
        return downloadFileUrl.substring(index + 1);
    }

    public long getLength() throws IOException {
        URL url = new URL(downloadFileUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        long length = httpURLConnection.getContentLengthLong();
        httpURLConnection.disconnect();
        return length;
    }

    public long getDownloadTime(){
        return downloadTime;
    }
}
