package net.hoshikawayoru.yoruutil.io.file.messagedigest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MessageDigest5 {
    public static byte[] digest(File file) throws IOException {
        byte[] bytes = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(bytes);
        fis.close();
        return net.hoshikawayoru.yoruutil.io.bytes.messagedigest.MessageDigest5.digest(bytes);
    }
}
