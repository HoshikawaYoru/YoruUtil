package net.hoshikawayoru.minsed.util.io.bytes.padding;

import net.hoshikawayoru.minsed.util.io.bytes.manipulate.ByteManipulate;

public class HHBP {
    public static byte[] padding(byte[] bytes, int blockSize){
        if (bytes == null) {
            return null;
        }

        if (blockSize <= bytes.length) {
            return bytes;
        }

        byte[] out = new byte[blockSize];
        byte highestByte = ByteManipulate.get.getHighestByte(bytes);
        boolean flag = false;

        System.arraycopy(bytes, 0, out, 0, bytes.length);

        for (int i = bytes.length;i < blockSize;i++){
            out[i] = flag ? highestByte : 23;
            flag = !flag;
        }

        return out;
    }
}
