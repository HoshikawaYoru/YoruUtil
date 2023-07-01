package net.hoshikawayoru.minsed.util.bytes.message.digest;

import net.hoshikawayoru.minsed.util.bytes.confound.HBEC;
import net.hoshikawayoru.minsed.util.bytes.ByteUtil;
import net.hoshikawayoru.minsed.util.bytes.padding.HNP;
import net.hoshikawayoru.minsed.util.bytes.truncation.HCT4;

public class HBCH {
    public static byte[] digest(byte[] bytes){
        int IV1 = 0x67452301 * ByteUtil.addAll(bytes);
        int IV2 = 0xefcdab89 * ByteUtil.getHighestByte(bytes);
        int IV3 = 0x98badcfe * bytes.length;
        int IV4 = Math.abs(0x10325476) * bytes[0] + 3;

        int S1 = IV1;
        int S2 = IV2;
        int S3 = IV3;
        int S4 = IV4;

        for (byte b : bytes) {
            S1 += b;
            S2 *= b;
            S3 <<= b;
            S4 &= b;
        }

        int N1 = Math.abs((IV1 * IV2 >> IV3) + IV4 + S1 + S2 + S3 >> S4);
        int N2 = Math.abs(S1 << (S2 * S3) *S4);
        int N3 = (int) Math.abs(Math.log(S1) + Math.log(S2) + Math.log(S3) + Math.log(S4));
        int N4 = (int) Math.abs(Math.exp(S1) + Math.exp(S2) + Math.exp(S3) + Math.exp(S4));

        byte[] out = new byte[64];
        byte[] processedBytes = bytes.length <= 64 ? HNP.padding(bytes, 64) : HCT4.truncation(bytes, 64);

        processedBytes = HBEC.confound(processedBytes);

        for (int i = 0;i < 64;i++){
            if (i <= 16 && i > 0){
                out[i] = (byte) (((processedBytes[i] + (processedBytes[i] << Math.abs(S1) >> i) + i + (i * i)) + N1) % 255);
            } else if (i <= 32 && i > 16){
                out[i] = (byte) (((processedBytes[i] + (processedBytes[i] << Math.abs(S2) >> i) + i + (i * i)) + N2) % 255);
            } else if (i <= 48 && i > 32) {
                out[i] = (byte) (((processedBytes[i] + (processedBytes[i] << Math.abs(S3) * i) + i + (i * i)) + N3) % 255);
            } else {
                out[i] = (byte) (((processedBytes[i] + (processedBytes[i] << Math.abs(S4)) * i * 2 + (i * i)) + N4) % 255);
            }
        }

        out = HBEC.confound(out);

        for (int i = 0;i < 64;i++){
            if (i <= 16 && i > 0){
                out[i] = (byte) ((out[i] + (out[i] << Math.abs(N1)) + i + (i * i)) % 255);
            } else if (i <= 32 && i > 16) {
                out[i] = (byte) ((out[i] + (out[i] << Math.abs(N2)) + i + (i * Math.cos(i))) % 255);
            } else if (i <= 48 && i > 32) {
                out[i] = (byte) ((out[i] + (out[i] << Math.abs(N3)) + i + (i & out[i])) % 255);
            } else {
                out[i] = (byte) ((out[i] + (out[i] << Math.abs(N4)) + i + (i | out[i])) % 255);
            }
        }

        for (int i = 0; i < ByteUtil.addAll(out); i++){
            out = HBEC.confound(out);
        }

        return out;
    }
}
