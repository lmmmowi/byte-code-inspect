package com.lmmmowi.bci.bean;

import com.lmmmowi.bci.enums.FieldAccessFlag;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * @Author: lmmmowi
 * @Date: 2020/1/16
 * @Description:
 */
@Data
@AllArgsConstructor
public class ClassField {
    private Set<FieldAccessFlag> accessFlags;
    private String name;
    private String descriptor;
    private int attributesCount;
}
