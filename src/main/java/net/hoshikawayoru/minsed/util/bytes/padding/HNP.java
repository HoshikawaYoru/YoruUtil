package net.hoshikawayoru.minsed.util.bytes.padding;

public class HNP {
    public static byte[] padding(byte[] bytes, int blockSize) {
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
            out[i] = (byte) num;
            if (num == 0){
                num = 1;
            }else {
                num = 0;
            }
        }

        return out;
    }
}
