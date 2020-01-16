package com.lmmmowi.bci.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: lmmmowi
 * @Date: 2020/1/15
 * @Description:
 */
@Data
@AllArgsConstructor
public class ConstantMethodHandle implements IConstant {
    private int referenceKind;
    private int referenceIndex;
}
