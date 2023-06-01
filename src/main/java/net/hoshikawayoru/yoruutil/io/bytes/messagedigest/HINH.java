package net.hoshikawayoru.yoruutil.io.bytes.messagedigest;

import net.hoshikawayoru.yoruutil.io.bytes.padding.PKCS7;
import net.hoshikawayoru.yoruutil.io.bytes.truncation.HCTA;

public class HINH {
    public static byte[] digest(byte[] bytes){


        byte[] PostprocessingData = bytes.length <= 32 ?  PKCS7.padding(bytes, 32) : HCTA.truncation(bytes, 32);
        byte[] out = new byte[32];

        int IV1 = 0xefcdab89 * PostprocessingData[0];
        int IV2 = 0x98badcfe / PostprocessingData[1] + IV1;
        int IV3 = 0xc3d2e1f0 + PostprocessingData[2];
        int IV4 = 0x0fc19dc6 - PostprocessingData[3];

        byte N1 = (byte) (Math.cos(PostprocessingData[0]) * IV1 % 255);
        byte N2 = (byte) (PostprocessingData[1] * IV2 * N1 % 255);
        byte N3 = (byte) Math.sin(PostprocessingData[2] * IV3 % 255);
        byte N4 = (byte) Math.tan(PostprocessingData[3] * IV4 % 255);

        for (int i = 0;i < 32;i++){
            if (i < 8){
                out[i] = (byte) (5 ^ i + (PostprocessingData[i] + (PostprocessingData[i + 10] + 10) * 7 + (N1 * i << (2 * i * 64) << ~N2 << i * N4 >> i * N3) + ~N1) % 255);
            }else if (i < 16 && i > 8){
                out[i] = (byte) ((PostprocessingData[i] + out[i - 8] * (N2 << (4 * i) << N2 + 10 * i ^ 3 + N4 << i * N1) + 7 * i ^ i) % 255);
            } else if (i < 24 && i > 16) {
                out[i] = (byte) (i * 9 * IV1 + ((PostprocessingData[i] + (N4 << (6 * i) << N3 << ~PostprocessingData[i])) + PostprocessingData[i] << N4 + N1 * IV1 * 64 * i << i * (IV1 + N2 + N3) >> i * IV4 + (PostprocessingData[i - 5] >> out[i - 8] * i)) % 255);
            } else if (i > 24) {
                out[i] = (byte) ((i * 7 * out[i - 16] * out[i - 8] + i ^ i + (out[i - 16] + PostprocessingData[i] * i * 3) * N1 << N2 >> N3 >> N4 << PostprocessingData[i - 24] << IV2 >> IV3 >> IV4) % 255);
            }else {
                out[i] = (byte) ((i * 114514 + 5 ^ i + (out[i - 2] + PostprocessingData[i] * i * 1919810) * N1 << N2 >> N3) % 255);
            }
        }

        byte[] last = new byte[32];

        int j = 8;
        for (int i = 0;i < 31;i++){
            if (j > 30){
                j = 0;
            }
            j++;
            out[j] = out[i];
        }

        for (int o = 0;o < 32;o++){
            for (int i = 0;i < 32;i++){
                if (i < 8){
                    last[i] = (byte) (5 ^ i + (out[31 - i - 3] << (out[20 - i] + 10) * 7 + (N1 * i << (2 * i * 64) << ~N2 << i * N4 >> i * N3) + ~N1) % 255);
                }else if (i < 16 && i > 8){
                    last[i] = (byte) ((out[i] + ~out[i + 8] * (N2 << (4 * i) << N2 + 10 * i ^ 3 + N4 << i * N1) + 7 * i ^ i) % 255);
                } else if (i < 24 && i > 16) {
                    last[i] = (byte) (i * 9 * IV1 + ((out[i] + (N4 << (6 * i) << N3 << ~out[i])) + out[i - 8] << N4 + N1 * IV1 * 64 * i << i * (IV1 + N2 + N3) >> i * IV4 + (out[i - 5] >> out[i - 8] * i)) % 255);
                } else if (i > 24) {
                    last[i] = (byte) ((i * 7 * ~out[i - 16] * out[i - 2] ^ i + (out[i - 16] + out[i] * i * 3) * N1 << N2 >> N3 >> N4 << out[i - 24] << IV2 >> IV3 >> IV4) % 255);
                }else {
                    last[i] = (byte) ((i * 114514 + 5 ^ i + (out[i - 2] + out[i] * i * 1919810) * N1 << N2 >> N3) % 255);
                }
            }
        }
        return last;
    }
}
