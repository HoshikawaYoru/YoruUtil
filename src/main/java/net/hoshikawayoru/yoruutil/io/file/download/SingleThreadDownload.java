package net.hoshikawayoru.yoruutil.io.file.download;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;

public class SingleThreadDownload {
    private String saveDir;
    private String fileUrl;
    private long startTime;
    private long endTime;
    private long fileSize;

    public SingleThreadDownload(String fileUrl, String saveDir) {
        this.saveDir = saveDir;
        this.fileUrl = fileUrl;
    }

    public void download() {
        try {
            URL url = new URL(fileUrl);
            new File(saveDir).mkdirs();
            new File(saveDir + "/" + getFileName()).createNewFile();
            URLConnection connection = url.openConnection();
            InputStream in = new BufferedInputStream(connection.getInputStream());
            String fileName = new File(connection.getURL().getFile()).getName();
            File file = new File(saveDir + "/" + getFileName());
            FileOutputStream out = new FileOutputStream(file);
            OutputStream bout = new BufferedOutputStream(out);



            byte[] buffer = new byte[8192];
            int length;
            fileSize = 0;

            startTime = System.currentTimeMillis();

            while ((length = in.read(buffer)) != -1) {
                bout.write(buffer, 0, length);
                fileSize += length;
            }

            endTime = System.currentTimeMillis();

            in.close();
            bout.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getDownloadTime() {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return new Double(decimalFormat.format((endTime - startTime) / 1000.0));
    }

    public long getFileSize() {
        return fileSize;
    }

    public String getFileName(){
        return new File(fileUrl).getName();
    }

    public void setFileUrl(String fileUrl){
        this.fileUrl = fileUrl;
    }

    public void setSaveDir(String saveDir){
        this.saveDir = saveDir;
    }
}