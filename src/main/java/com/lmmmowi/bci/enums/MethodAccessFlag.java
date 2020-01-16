package com.lmmmowi.bci.enums;

/**
 * @Author: lmmmowi
 * @Date: 2020/1/15
 * @Description:
 */
public enum MethodAccessFlag {

    ACC_PUBLIC(0x0001),

    ACC_PRIVATE(0x0002),

    ACC_PROTECTED(0x0004),

    ACC_STATIC(0x0008),

    ACC_FINAL(0x0010),

    ACC_SYNCHRONIZED(0x0020),

    ACC_BRIDGE(0x0040),

    ACC_VARARGS(0x0080),

    ACC_NATIVE(0x0100),

    ACC_ABSTRACT(0x0400),

    ACC_STRICTFP(0x0800),

    ACC_SYNTHETIC(0x1000);

    private int value;

    MethodAccessFlag(int value) {
        this.value = value;
    }

    public boolean match(int accessFlag) {
        return (value & accessFlag) > 0;
    }
}
