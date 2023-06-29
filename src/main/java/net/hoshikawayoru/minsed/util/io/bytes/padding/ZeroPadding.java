package net.hoshikawayoru.minsed.util.io.bytes.padding;

public class ZeroPadding {
    public static byte[] padding(byte[] bytes, int blockSize){
        if (bytes == null){
            return null;
        }

        if (blockSize <= bytes.length){
            return bytes;
        }

        byte[] out = new byte[blockSize];
        System.arraycopy(bytes, 0, out, 0, bytes.length);
        return out;
    }
}
