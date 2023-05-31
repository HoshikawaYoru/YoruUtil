package net.hoshikawayoru.yoruutil.io.bytes.filtration;

public class BytesFiltration {
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
