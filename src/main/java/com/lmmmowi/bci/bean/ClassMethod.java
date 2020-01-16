package com.lmmmowi.bci.bean;

import com.lmmmowi.bci.enums.MethodAccessFlag;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @Author: lmmmowi
 * @Date: 2020/1/16
 * @Description:
 */
@Data
@AllArgsConstructor
public class ClassMethod {
    private Set<MethodAccessFlag> accessFlags;
    private String name;
    private String descriptor;
    private List<Attribute> attributes;
}
