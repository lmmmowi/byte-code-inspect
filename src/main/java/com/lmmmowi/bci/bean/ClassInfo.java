package com.lmmmowi.bci.bean;

import com.lmmmowi.bci.enums.ClassAccessFlag;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @Author: lmmmowi
 * @Date: 2020/1/15
 * @Description:
 */
@Data
@Builder
public class ClassInfo {
    private int jdkVersion;
    private List<IConstant> constants;
    private Set<ClassAccessFlag> accessFlags;
    private String className;
    private String superClassName;
    private List<String> interfaceNames;
    private List<ClassField> fields;
    private List<ClassMethod> methods;
    private List<Attribute> attributes;
}
