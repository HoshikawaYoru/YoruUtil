package net.hoshikawayoru.yoruutil.io.bytes.datapadding;

public class Pkcs5 {
    public static byte[] padding(byte[] bytes, int blockSize) {
        int padding = blockSize - bytes.length % blockSize;
        byte[] result = new byte[bytes.length + padding];
        System.arraycopy(bytes, 0, result, 0, bytes.length);
        for (int i = 0; i < padding; i++) {
            result[bytes.length + i] = (byte) padding;
        }
        return result;
    }
}
