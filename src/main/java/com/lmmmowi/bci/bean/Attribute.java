package com.lmmmowi.bci.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: lmmmowi
 * @Date: 2020/1/16
 * @Description:
 */
@Data
@AllArgsConstructor
public class Attribute {
    private String name;
    private byte[] info;
}
