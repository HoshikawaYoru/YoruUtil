package net.hoshikawayoru.yoruutil.io.bytes.truncation;

public class HCTA {
    public static byte[] truncation(byte[] bytes, int blockSize){
        if (bytes == null){
            return null;
        }

        if (blockSize >= bytes.length){
            return bytes;
        }

        byte[] out = new byte[blockSize];

        for (int i = 0;i < blockSize;i++){
            out[i] = (byte) (bytes[i * (bytes.length / blockSize)] + (bytes[(i + 1) * (bytes.length / blockSize) - 1] % 255));
        }

        return out;
    }
}
