package net.hoshikawayoru.minsed.util.bytes.confound;

import net.hoshikawayoru.minsed.util.bytes.ByteUtil;

public class HBEC {
    public static byte[] confound(byte[] bytes){
        if (bytes == null){
            return null;
        }

        byte[] out = new byte[bytes.length];

        int pos1 = 0;
        int pos2;

        System.arraycopy(bytes, 0, out, 0, bytes.length);

        for (byte b : bytes) {
            pos2 = Math.abs(b) > (bytes.length - 1) ? Math.abs(b) % (bytes.length - 1) : Math.abs(b);

            out = ByteUtil.transposition(out, pos1, pos2);
            pos1++;

        }

        return out;
    }
}
