package com.lmmmowi.bci.utils;

/**
 * @Author: lmmmowi
 * @Date: 2020/1/15
 * @Description:
 */
public class ValueUtil {

    public static int toInt(byte[] b) {
        int res = 0;
        for (int i = 0; i < b.length; i++) {
            res += (b[i] & 0xff) << ((3 - i) * 8);
        }
        return res;
    }

    public static float toFloat(byte[] b) {
        return Float.intBitsToFloat(toInt(b));
    }

    public static long toLong(byte[] b) {
        return (0xff00000000000000L & ((long) b[0] << 56)) |
                (0x00ff000000000000L & ((long) b[1] << 48)) |
                (0x0000ff0000000000L & ((long) b[2] << 40)) |
                (0x000000ff00000000L & ((long) b[3] << 32)) |
                (0x00000000ff000000L & ((long) b[4] << 24)) |
                (0x0000000000ff0000L & ((long) b[5] << 16)) |
                (0x000000000000ff00L & ((long) b[6] << 8)) |
                (0x00000000000000ffL & (long) b[7]);
    }

    public static double toDouble(byte[] b) {
        return Double.longBitsToDouble(toLong(b));
    }
}
