package com.lmmmowi.bci.enums;

/**
 * @Author: lmmmowi
 * @Date: 2020/1/15
 * @Description:
 */
public enum ClassAccessFlag {

    ACC_PUBLIC(0x0001),

    ACC_FINAL(0x0010),

    ACC_SUPER(0x0020),

    ACC_INTERFACE(0x0200),

    ACC_ABSTRACT(0x0400),

    ACC_SYNTHETIC(0x1000),

    ACC_ANOTATION(0x2000),

    ACC_ENUM(0x4000);

    private int value;

    ClassAccessFlag(int value) {
        this.value = value;
    }

    public boolean match(int accessFlag) {
        return (value & accessFlag) >0;
    }
}
