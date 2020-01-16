package com.lmmmowi.bci.enums;

/**
 * @Author: lmmmowi
 * @Date: 2020/1/15
 * @Description:
 */
public enum FieldAccessFlag {

    ACC_PUBLIC(0x0001),

    ACC_PRIVATE(0x0002),

    ACC_PROTECTED(0x0004),

    ACC_STATIC(0x0008),

    ACC_FINAL(0x0010),

    ACC_VOLATILE(0x0040),

    ACC_TRANSIAENT(0x0080),

    ACC_SYNTHETIC(0x1000),

    ACC_ENUM(0x4000);

    private int value;

    FieldAccessFlag(int value) {
        this.value = value;
    }

    public boolean match(int accessFlag) {
        return (value & accessFlag) >0;
    }
}
