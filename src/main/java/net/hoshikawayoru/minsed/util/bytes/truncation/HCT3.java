package net.hoshikawayoru.minsed.util.bytes.truncation;

public class HCT3 {
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
                if (j <= blockCount / 2){
                    out[i] = bytes[i * blockCount + j];
                }else {
                    out[i] = (byte) ((bytes[i * blockCount + j] * out[i - 1] + bytes[i * blockCount + j]) % 255);
                }
            }
        }
        return out;
    }
}
