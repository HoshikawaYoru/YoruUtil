package net.hoshikawayoru.yoru.util.io.bytes.truncation;

public class HCT4 {
    public static byte[] truncation(byte[] bytes, int blockSize){
        if (bytes == null){
            return null;
        }

        if (blockSize >= bytes.length){
            return bytes;
        }

        byte[] out = new byte[blockSize];
        int blockCount = bytes.length / blockSize;

        for (int i = 0;i < blockSize;i++){
            for (int j = 0;j < (bytes.length / blockSize);j++){
                if (bytes[i] + bytes[i] * bytes[i * blockCount + j] > 255){
                    out[i] = (byte) ((bytes[i] + bytes[i] * bytes[i * blockCount + j]) % 255);
                }else {
                    out[i] = (byte) (bytes[i] + bytes[i] * bytes[i * blockCount + j]);
                }
            }
        }
        return out;
    }
}
