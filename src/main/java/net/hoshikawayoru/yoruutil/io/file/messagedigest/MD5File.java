package net.hoshikawayoru.yoruutil.io.file.messagedigest;

import net.hoshikawayoru.yoruutil.io.bytes.messagedigest.MD5;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MD5File {
    public static byte[] digest(File file) throws IOException {
        if (file == null){
            return null;
        }

        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        fis.close();
        return MD5.digest(bytes);
    }
}
