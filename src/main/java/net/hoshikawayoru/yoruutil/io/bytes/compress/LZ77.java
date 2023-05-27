package net.hoshikawayoru.yoruutil.io.bytes.compress;

import java.io.ByteArrayOutputStream;

public class LZ77 {
    private static final int WINDOW_SIZE = 4096;
    private static final int LOOKAHEAD_SIZE = 18;

    public static byte[] compress(byte[] data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int pos = 0;
        while (pos < data.length) {
            int matchPos = -1;
            int matchLen = -1;
            for (int i = Math.max(0, pos - WINDOW_SIZE); i < pos; i++) {
                int len = 0;
                while (pos + len < data.length && len < LOOKAHEAD_SIZE && data[i + len] == data[pos + len]) {
                    len++;
                }
                if (len > matchLen) {
                    matchPos = i;
                    matchLen = len;
                }
            }
            if (matchLen > 2) {
                out.write((matchPos & 0xff));
                out.write(((matchPos >> 8) & 0xff) | ((matchLen - 3) << 5));
            } else {
                out.write(data[pos]);
            }
            pos += matchLen > 0 ? matchLen : 1;
        }
        return out.toByteArray();
    }

    public static byte[] decompress(byte[] data) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int pos = 0;
        while (pos < data.length) {
            int flag = data[pos++] & 0xff;
            for (int i = 0; i < 8; i++) {
                if ((flag & (1 << i)) != 0) {
                    int offset = ((data[pos + 1] & 0xff) << 8) | (data[pos] & 0xff);
                    int len = (offset >> 5) + 3;
                    offset &= 0x1f;
                    offset |= ((data[pos + 2] & 0xff) << 5);
                    for (int j = 0; j < len; j++) {
                        out.write(out.toByteArray()[out.size() - offset]);
                    }
                    pos += 3;
                } else {
                    out.write(data[pos++]);
                }
                if (pos >= data.length) {
                    break;
                }
            }
        }
        return out.toByteArray();
    }
}