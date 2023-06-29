package net.hoshikawayoru.minsed.util.io.bytes.manipulate;

import java.util.*;

public class ByteManipulate {
    public static class operate {
        public static class pos {
            public static byte[] transposition(byte[] bytes, int pos1, int pos2){
                byte byte1 = bytes[pos1];
                byte byte2 = bytes[pos2];

                byte[] out = new byte[bytes.length];

                System.arraycopy(bytes, 0, out, 0, bytes.length);

                out[pos1] = byte2;
                out[pos2] = byte1;

                return out;
            }
        }
        public static class arithmetic {
            public static int addAll(byte[] bytes){
                int num = 0;

                for (byte b :  bytes){
                    num += b;
                }

                return num;
            }
        }
        public static class generate {
            public static byte[] generateRandomBytes(int blockSize) {
                Random random = new Random();
                byte[] out = new byte[blockSize];
                for (int i = 0; i < blockSize; i++) {
                    out[i] = (byte) (random.nextInt(256) - 128);
                }
                return out;
            }

            public static byte[] generateRandomNoDuplicationBytes(int blockSize) {
                Byte[] bytes = new Byte[blockSize];
                for (int i = 0; i < 256; i++) {
                    bytes[i] = (byte) i;
                }

                List<Byte> list;
                list = Arrays.asList(bytes);
                Collections.shuffle(list);

                byte[] out = new byte[blockSize];

                for (int i = 0; i < blockSize; i++) {
                    out[i] = list.get(i);
                }
                return out;
            }
        }
        public static class format {
            public static String toHexString(byte[] bytes) {
                StringBuilder sb = new StringBuilder();
                for (byte b : bytes) {
                    sb.append(String.format("%02x", b & 0xff));
                }
                return sb.toString();
            }
        }
        public static byte[] filtration(byte[] bytes, byte filtrationByte){
            int length = 0;
            for (byte b : bytes) {
                if (b != filtrationByte) {
                    length++;
                }
            }

            byte[] filteredData = new byte[length];

            for (int i = 0, j = 0; i < bytes.length; i++) {
                if (bytes[i] != filtrationByte) {
                    filteredData[j++] = bytes[i];
                }
            }

            return filteredData;
        }
    }

    public static class get {
        public static byte getHighestByte(byte[] bytes){
            byte highestByte = -128;

            for (byte b : bytes){
                if (b > highestByte){
                    highestByte = b;
                }
            }
            return highestByte;
        }
        public static boolean getIsContainsByte(byte[] bytes, byte b){
            for (byte b1 : bytes){
                if (b1 == b){
                    return true;
                }
            }
            return false;
        }
    }
}
