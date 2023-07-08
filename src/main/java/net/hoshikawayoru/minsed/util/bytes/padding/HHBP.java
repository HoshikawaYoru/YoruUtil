package net.hoshikawayoru.minsed.util.bytes.padding;

import net.hoshikawayoru.minsed.util.bytes.ByteUtil;

public class HHBP {
    public static byte[] padding(byte[] bytes, int blockSize){
        if (bytes == null) {
            return null;
        }

        if (blockSize <= bytes.length) {
            return bytes;
        }

        byte[] out = new byte[blockSize];
        byte highestByte = ByteUtil.getHighestByte(bytes);
        boolean flag = false;

        System.arraycopy(bytes, 0, out, 0, bytes.length);

        for (int i = bytes.length;i < blockSize;i++){
            out[i] = flag ? highestByte : 23;
            flag = !flag;
        }

        return out;
    }
}
