package net.hoshikawayoru.yoru.util.io.bytes.padding;

public class HRP {
    public static byte[] padding(byte[] bytes, int blockSize){
        if (bytes == null) {
            return null;
        }

        if (blockSize <= bytes.length) {
            return bytes;
        }

        byte[] out = new byte[blockSize];

        int num = 0;

        System.arraycopy(bytes, 0, out, 0, bytes.length);

        for (int i = bytes.length;i < blockSize;i++){
            if (num == bytes.length){
                num = 0;
            }

            out[i] = bytes[num];
            num++;
        }

        return out;
    }
}
