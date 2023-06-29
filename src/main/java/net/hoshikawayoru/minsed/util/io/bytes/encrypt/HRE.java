package net.hoshikawayoru.minsed.util.io.bytes.encrypt;

public class HRE {
    public static byte[] encrypt(byte[] bytes){
        if (bytes == null){
            return null;
        }

        byte[] out = new byte[bytes.length];

        int o = 0;
        for (int i = bytes.length - 1;i > 0;i--){
            out[o] = bytes[i];
        }
        return out;
    }

    public static byte[] decrypt(byte[] bytes){
        if (bytes == null){
            return null;
        }

        byte[] out = new byte[bytes.length];

        int o = 0;
        for (int i = bytes.length - 1;i > 0;i--){
            out[o] = bytes[i];
        }
        return out;
    }
}
