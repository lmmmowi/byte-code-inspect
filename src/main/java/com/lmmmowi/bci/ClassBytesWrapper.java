package com.lmmmowi.bci;

import java.io.PrintStream;

/**
 * @Author: lmmmowi
 * @Date: 2020/1/15
 * @Description:
 */
public class ClassBytesWrapper {

    private static final int[] HEX_POW = new int[]{0x01, 0x100, 0x10000, 0x1000000};

    private byte[] bytes;
    private int position;

    public ClassBytesWrapper(byte[] bytes) {
        this.bytes = bytes;
        this.position = 0;
    }

    public void print() {
        PrintStream stream = System.out;
        int n = 0;
        while (n < bytes.length) {
            byte b = bytes[n++];
            stream.print(String.format("%02x ", b).toUpperCase());
            if (n % 16 == 0) {
                stream.println();
            }
        }
    }

    public byte readByte() {
        return bytes[position++];
    }

    public int readInt(int length) {
        int value = 0;
        for (int i = 0; i < length; i++) {
            byte b = readByte();
            int bv = Byte.toUnsignedInt(b);
            if (bv > 0) {
                value += bv * HEX_POW[length - i - 1];
            }
        }
        return value;
    }

    public byte[] readBytes(int length) {
        byte[] r = new byte[length];
        for (int i = 0; i < length; i++) {
            r[i] = readByte();
        }
        return r;
    }

    public boolean hasMoreData() {
        return position < bytes.length;
    }
}
