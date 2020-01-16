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
public class ConstantInvokeDynamic implements IConstant {
    private int bootstrapMethodAttrIndex;
    private int nameAndTypeIndex;
}
