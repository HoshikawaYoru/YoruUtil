package net.hoshikawayoru.minsed.util.bytes.message.digest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class MD5 {
    private static final int[] T = new int[64];

    static {
        for (int i = 0; i < 64; i++) {
            T[i] = (int) (long) ((1L << 32) * Math.abs(Math.sin(i + 1)));
        }
    }

    public static byte[] digest(byte[] bytes) {
        int[] A = {0x67452301};
        int[] B = {0xefcdab89};
        int[] C = {0x98badcfe};
        int[] D = {0x10325476};

        int padLength = 64 - (bytes.length + 9) % 64;
        if (padLength == 64) {
            padLength = 0;
        }
        int length = bytes.length + 1 + padLength + 8;
        byte[] padMessage = new byte[length];
        System.arraycopy(bytes, 0, padMessage, 0, bytes.length);
        padMessage[bytes.length] = (byte) 0x80;
        for (int i = bytes.length + 1; i < padMessage.length - 8; i++) {
            padMessage[i] = 0;
        }
        ByteBuffer bf = ByteBuffer.allocate(8);
        bf.order(ByteOrder.LITTLE_ENDIAN);
        bf.putLong(bytes.length * 8L);
        byte[] lengthBytes = bf.array();
        System.arraycopy(lengthBytes, 0, padMessage, padMessage.length - 8, 8);


        for (int i = 0; i < padMessage.length; i += 64) {
            int[] X = new int[16];
            for (int j = 0; j < 16; j++) {
                int k = i + j * 4;
                X[j] = ((padMessage[k] & 0xff) << 24) |
                        ((padMessage[k + 1] & 0xff) << 16) |
                        ((padMessage[k + 2] & 0xff) << 8) |
                        ((padMessage[k + 3] & 0xff));
            }

            int AA = A[0];
            int BB = B[0];
            int CC = C[0];
            int DD = D[0];

            for (int j = 0; j < 64; j++) {
                int F, g;
                if (j < 16) {
                    F = (B[0] & C[0]) | ((~B[0]) & D[0]);
                    g = j;
                } else if (j < 32) {
                    F = (D[0] & B[0]) | ((~D[0]) & C[0]);
                    g = (5 * j + 1) % 16;
                } else if (j < 48) {
                    F = B[0] ^ C[0] ^ D[0];
                    g = (3 * j + 5) % 16;
                } else {
                    F = C[0] ^ (B[0] | (~D[0]));
                    g = (7 * j) % 16;
                }

                int dTemp = DD;
                DD = CC;
                CC = BB;
                BB = BB + Integer.rotateLeft((AA + F + X[g] + T[j]), 7);
                AA = dTemp;
            }

            A[0] += AA;
            B[0] += BB;
            C[0] += CC;
            D[0] += DD;
        }

        byte[] md5Bytes = new byte[16];
        ByteBuffer buffer = ByteBuffer.wrap(md5Bytes);
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.putInt(A[0]);
        buffer.putInt(B[0]);
        buffer.putInt(C[0]);
        buffer.putInt(D[0]);

        return md5Bytes;
    }

}
