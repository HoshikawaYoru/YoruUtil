package net.hoshikawayoru.yoruutil.io.bit;


import java.io.IOException;
import java.io.InputStream;


public class BitInputStream {
    private final InputStream inputStream;
    private int currentByte = 0;
    private int numBitsRemaining = 0;

    public BitInputStream(InputStream in) {
        inputStream = in;
    }

    public int read(int numBits) throws IOException {
        if (numBits < 0 || numBits > 32) {
            throw new IllegalArgumentException("Illegal number of bits");
        }

        int result = 0;
        while (numBits > 0) {
            if (numBitsRemaining == 0) {
                currentByte = inputStream.read();
                if (currentByte == -1) {
                    throw new IOException("End of stream reached");
                }
                numBitsRemaining = 8;
            }

            int bitsToRead = Math.min(numBits, numBitsRemaining);
            int shiftAmount = numBitsRemaining - bitsToRead;
            int mask = (1 << bitsToRead) - 1;
            result |= ((currentByte >> shiftAmount) & mask) << (numBits - bitsToRead);
            numBits -= bitsToRead;
            numBitsRemaining -= bitsToRead;
        }

        return result;
    }

    public boolean hasNext() throws IOException {
        inputStream.mark(1);
        int nextByte = inputStream.read();
        inputStream.reset();

        return nextByte != -1;
    }
}