package net.hoshikawayoru.yoru.util.io.bytes.padding;

import java.util.Arrays;

public class PKCS7 {
    public static byte[] padding(byte[] bytes, int blockSize) {
        int paddingSize = blockSize - bytes.length % blockSize;
        byte[] paddingBytes = new byte[paddingSize];
        Arrays.fill(paddingBytes, (byte) paddingSize);
        byte[] out = new byte[bytes.length + paddingSize];
        System.arraycopy(bytes, 0, out, 0, bytes.length);
        System.arraycopy(paddingBytes, 0, out, bytes.length, paddingSize);
        return out;
    }
}
