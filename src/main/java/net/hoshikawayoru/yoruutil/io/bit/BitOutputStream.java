package net.hoshikawayoru.yoruutil.io.bit;

import java.io.IOException;
import java.io.OutputStream;

public class BitOutputStream {
    private final OutputStream outputStream;
    private int currentByte = 0;
    private int numBitsRemaining = 8;

    public BitOutputStream(OutputStream out) {
        outputStream = out;
    }

    public void write(int value, int numBits) throws IOException {
        if (numBits < 0 || numBits > 32) {
            throw new IllegalArgumentException("Illegal number of bits");
        }

        while (numBits > 0) {
            if (numBitsRemaining == 0) {
                outputStream.write(currentByte);
                currentByte = 0;
                numBitsRemaining = 8;
            }

            int bitsToWrite = Math.min(numBits, numBitsRemaining);
            int shiftAmount = numBits - bitsToWrite;
            int mask = (1 << bitsToWrite) - 1;
            currentByte |= (value >> shiftAmount) & mask;
            numBits -= bitsToWrite;
            numBitsRemaining -= bitsToWrite;
        }
    }

    public void flush() throws IOException {
        if (numBitsRemaining != 8) {
            outputStream.write(currentByte);
        }
        outputStream.flush();
    }
}