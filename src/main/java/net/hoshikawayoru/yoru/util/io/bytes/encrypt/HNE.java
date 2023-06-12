package net.hoshikawayoru.yoru.util.io.bytes.encrypt;

public class HNE {
    public static byte[] encrypt(byte[] bytes){
        if (bytes == null){
            return null;
        }

        byte[] out = new byte[bytes.length];

        int i = 0;
        for (byte b : bytes){
            out[i] = (byte) ~b;
            i++;
        }
        return out;
    }

    public static byte[] decrypt(byte[] bytes){
        if (bytes == null){
            return null;
        }

        byte[] out = new byte[bytes.length];

        int i = 0;
        for (byte b : bytes){
            out[i] = (byte) ~b;
            i++;
        }
        return out;
    }
}
