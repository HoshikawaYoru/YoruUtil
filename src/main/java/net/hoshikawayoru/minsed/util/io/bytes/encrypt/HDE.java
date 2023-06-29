package net.hoshikawayoru.minsed.util.io.bytes.encrypt;

import java.util.HashMap;

public class HDE {
    public static byte[] encrypt(byte[] bytes, byte[] dirt){
        if (bytes == null){
            return null;
        }
        if (dirt.length != 256){
            return null;
        }

        HashMap<Byte,Byte> hashMap = new HashMap<>();
        for (int i = 0;i < 256;i++){
            hashMap.put((byte) i, dirt[i]);
        }

        byte[] out = new byte[bytes.length];

        int i = 0;
        for (byte b : bytes){
            out[i] = hashMap.get(b);
            i++;
        }
        return out;
    }

    public static byte[] decrypt(byte[] bytes, byte[] dirt){
        if (bytes == null){
            return null;
        }
        if (dirt.length != 256){
            return null;
        }

        HashMap<Byte,Byte> hashMap = new HashMap<>();
        for (int i = 0;i < 256;i++){
            hashMap.put(dirt[i], (byte) i);
        }

        byte[] out = new byte[bytes.length];

        int i = 0;
        for (byte b : bytes){
            out[i] = hashMap.get(b);
            i++;
        }
        return out;
    }
}
