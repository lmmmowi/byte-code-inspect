package com.lmmmowi.bci;

import com.lmmmowi.bci.bean.IConstant;

import java.util.List;

/**
 * @Author: lmmmowi
 * @Date: 2020/1/15
 * @Description:
 */
public class ConstantPool {

    private List<IConstant> constants;

    public ConstantPool(List<IConstant> constants) {
        this.constants = constants;
    }

    public <T extends IConstant> T getConstant(int index) {
        return (T) constants.get(index - 1);
    }
}
