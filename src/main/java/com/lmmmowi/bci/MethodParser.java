package com.lmmmowi.bci;

import com.lmmmowi.bci.bean.Attribute;
import com.lmmmowi.bci.bean.ClassMethod;
import com.lmmmowi.bci.bean.ConstantUtf8;
import com.lmmmowi.bci.enums.MethodAccessFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: lmmmowi
 * @Date: 2020/1/16
 * @Description:
 */
public class MethodParser {

    private static final MethodParser INSTANCE = new MethodParser();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private AttributeParser attributeParser = AttributeParser.getInstance();

    private MethodParser() {
    }

    public static MethodParser getInstance() {
        return INSTANCE;
    }

    public List<ClassMethod> parse(ClassBytesWrapper bytes, ConstantPool constantPool) {
        int count = bytes.readInt(2);
        logger.info("解析方法计数：{}", count);

        List<ClassMethod> methods = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            ClassMethod method = this.parseMethod(bytes, constantPool);
            logger.info("解析第{}个方法：{}", i + 1, method);
            methods.add(method);
        }
        return methods;
    }

    private ClassMethod parseMethod(ClassBytesWrapper bytes, ConstantPool constantPool) {
        int accessFlag = bytes.readInt(2);
        Set<MethodAccessFlag> accessFlags = Arrays.stream(MethodAccessFlag.values())
                .filter(o -> o.match(accessFlag))
                .collect(Collectors.toSet());

        int index = bytes.readInt(2);
        ConstantUtf8 constantUtf8 = constantPool.getConstant(index);
        String name = constantUtf8.getString();

        index = bytes.readInt(2);
        constantUtf8 = constantPool.getConstant(index);
        String descriptor = constantUtf8.getString();

        List<Attribute> attributes = attributeParser.parse(bytes, constantPool);

        return new ClassMethod(accessFlags, name, descriptor, attributes);
    }
}
