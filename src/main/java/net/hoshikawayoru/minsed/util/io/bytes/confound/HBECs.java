package net.hoshikawayoru.minsed.util.io.bytes.confound;

import net.hoshikawayoru.minsed.util.io.bytes.manipulate.ByteManipulate;

public class HBECs {
    public static byte[] confound(byte[] bytes, int seed){
        if (bytes == null){
            return null;
        }

        byte[] out = new byte[bytes.length];

        int pos1 = 0;
        int pos2;

        System.arraycopy(bytes, 0, out, 0, bytes.length);

        for (byte b : bytes){
            pos2 = (Math.abs(Math.abs(b) << seed)) > (bytes.length - 1) ? (Math.abs(Math.abs(b) << seed) % 255) % (bytes.length - 1)  : Math.abs(Math.abs(b) << seed) % 255;

            out = ByteManipulate.operate.pos.transposition(out, pos1, pos2);
            pos1++;
        }

        return out;
    }
}
