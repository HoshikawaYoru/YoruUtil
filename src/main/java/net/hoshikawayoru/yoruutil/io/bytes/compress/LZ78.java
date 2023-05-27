package net.hoshikawayoru.yoruutil.io.bytes.compress;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LZ78 {

    public byte[] compress(byte[] input, int dictSize) {
        ArrayList<Code> codes = new ArrayList<>();
        Map<String, Integer> dictionary = new HashMap<>();
        String current = "";
        for (byte b : input) {
            String combined = current + (char) (b & 0xFF);
            if (dictionary.containsKey(combined)) {
                current = combined;
            } else {
                codes.add(new Code(dictionary.get(current), (char) (b & 0xFF)));
                dictionary.put(combined, dictSize++);
                current = "" + (char) (b & 0xFF);
            }
        }
        if (!current.equals("")) {
            codes.add(new Code(dictionary.get(current), '\0'));
        }
        byte[] compressed = new byte[codes.size() * 3];
        for (int i = 0; i < codes.size(); i++) {
            int index = codes.get(i).index;
            char nextChar = codes.get(i).nextChar;
            compressed[i * 3] = (byte) (index >> 8);
            compressed[i * 3 + 1] = (byte) (index & 0xFF);
            compressed[i * 3 + 2] = (byte) nextChar;
        }
        return compressed;
    }

    public byte[] decompress(byte[] compressed, int dictSize) {
        ArrayList<Code> codes = new ArrayList<>();
        for (int i = 0; i < compressed.length; i += 3) {
            int index = ((compressed[i] & 0xFF) << 8) | (compressed[i + 1] & 0xFF);
            char nextChar = (char) (compressed[i + 2] & 0xFF);
            codes.add(new Code(index, nextChar));
        }
        StringBuilder result = new StringBuilder();
        Map<Integer, String> dictionary = new HashMap<>();
        for (int i = 0; i < dictSize; i++) {
            dictionary.put(i, "" + (char) i);
        }
        for (Code code : codes) {
            String current = dictionary.getOrDefault(code.index, "");
            current += code.nextChar;
            result.append(current);
            if (code.nextChar != '\0') {
                dictionary.put(dictSize++, current);
            }
        }
        return result.toString().getBytes();
    }

    private static class Code {
        public int index;
        public char nextChar;

        public Code(int index, char nextChar) {
            this.index = index;
            this.nextChar = nextChar;
        }
    }
}