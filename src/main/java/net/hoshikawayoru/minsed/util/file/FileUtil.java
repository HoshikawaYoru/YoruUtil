package net.hoshikawayoru.minsed.util.file;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class FileUtil {
    public static byte[] readAllBytes(File file) throws IOException {
        if (file == null){
            return null;
        }
        InputStream input = Files.newInputStream(file.toPath());
        byte[] bytes = new byte[0];
        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            byte[] newBytes = new byte[bytes.length + length];
            System.arraycopy(bytes, 0, newBytes, 0, bytes.length);
            System.arraycopy(buffer, 0, newBytes, bytes.length, length);
            bytes = newBytes;
        }
        input.close();
        return bytes;
    }
}
