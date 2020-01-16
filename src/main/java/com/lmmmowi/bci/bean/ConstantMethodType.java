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
public class ConstantMethodType implements IConstant {
    private int descriptorIndex;
}
