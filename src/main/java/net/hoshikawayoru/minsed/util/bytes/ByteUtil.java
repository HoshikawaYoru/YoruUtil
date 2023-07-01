package net.hoshikawayoru.minsed.util.bytes;

import java.util.*;

public class ByteUtil {
    public static byte[] transposition(byte[] bytes, int pos1, int pos2){
        byte byte1 = bytes[pos1];
        byte byte2 = bytes[pos2];

        byte[] out = new byte[bytes.length];

        System.arraycopy(bytes, 0, out, 0, bytes.length);

        out[pos1] = byte2;
        out[pos2] = byte1;

        return out;
    }
    public static int addAll(byte[] bytes){
        int num = 0;
        for (byte b :  bytes){
            num += b;
        }
        return num;
    }
    public static byte[] generateRandomBytes(int blockSize) {
        Random random = new Random();
        byte[] out = new byte[blockSize];
        for (int i = 0; i < blockSize; i++) {
            out[i] = (byte) (random.nextInt(256) - 128);
        }
        return out;
    }
    public static byte[] generateRandomNoDuplicationBytes(int blockSize) {
        if (blockSize > 256){
            return null;
        }

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
    public static String toHexString(byte[] bytes, boolean lowercase) {
        StringBuilder sb = new StringBuilder();
        if (lowercase){
            for (byte b : bytes) {
                sb.append(String.format("%02x", b & 0xff));
            }
        }else {
            for (byte b : bytes) {
                sb.append(String.format("%02X", b & 0xff));
            }
        }
        return sb.toString();
    }
    public static String toHexString(byte[] bytes) {
        return toHexString(bytes, true);
    }
    public static String toBinaryString(byte[] bytes){
        StringBuilder sb = new StringBuilder();

        for (byte b : bytes){
            sb.append(String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0'));
        }
        return sb.toString();
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
    public static byte getHighestByte(byte[] bytes){
        byte highestByte = -128;

        for (byte b : bytes){
            if (b > highestByte){
            highestByte = b;
            }
        }
        return highestByte;
    }
    public static byte getLowestByte(byte[] bytes){
        byte highestByte = 127;

        for (byte b : bytes){
            if (b < highestByte){
                highestByte = b;
            }
        }
        return highestByte;
    }
    public static boolean getIsContainsByte(byte[] bytes, byte b) {
        for (byte b1 : bytes) {
            if (b1 == b) {
                return true;
            }
        }
        return false;
    }
    public static int getByteCount(byte[] bytes, byte b){
        int count = 0;
        for (byte bt : bytes){
            if (bt == b){
                count++;
            }
        }
        return count;
    }
    public static byte getRandomPosByte(byte[] bytes){
        Random random = new Random();
        return bytes[random.nextInt(bytes.length - 1)];
    }
    public static byte[] reorder(byte[] bytes){
        List<Byte> list = new ArrayList<>();
        for (int i = 0;i < bytes.length;i++){
            list.add(i, bytes[i]);
        }

        byte[] out = new byte[bytes.length];

        Collections.shuffle(list);

        for (int i = 0;i < bytes.length;i++){
            out[i] = list.get(i);
        }
        return out;
    }
    public static byte[] removeByte(byte[] bytes, int index) {
        byte[] out = new byte[bytes.length - 1];
        for (int i = 0,j = 0;i < bytes.length - 1;i++){
            if (i == index){
                j++;
            }
            out[i] = bytes[j];
            j++;
        }
        return out;
    }
}

