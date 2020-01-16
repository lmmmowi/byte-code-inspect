package com.lmmmowi.bci;

import com.lmmmowi.bci.bean.ClassField;
import com.lmmmowi.bci.bean.ConstantUtf8;
import com.lmmmowi.bci.enums.FieldAccessFlag;
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
public class FieldParser {

    private static final FieldParser INSTANCE = new FieldParser();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private FieldParser() {
    }

    public static FieldParser getInstance() {
        return INSTANCE;
    }

    public List<ClassField> parse(ClassBytesWrapper bytes, ConstantPool constantPool) {
        int count = bytes.readInt(2);
        logger.info("解析字段计数：{}", count);

        List<ClassField> fields = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            ClassField field = this.parseField(bytes, constantPool);
            logger.info("解析第{}个字段：{}", i + 1, field);
            fields.add(field);
        }
        return fields;
    }

    private ClassField parseField(ClassBytesWrapper bytes, ConstantPool constantPool) {
        int accessFlag = bytes.readInt(2);
        Set<FieldAccessFlag> accessFlags = Arrays.stream(FieldAccessFlag.values())
                .filter(o -> o.match(accessFlag))
                .collect(Collectors.toSet());

        int index = bytes.readInt(2);
        ConstantUtf8 constantUtf8 = constantPool.getConstant(index);
        String name = constantUtf8.getString();

        index = bytes.readInt(2);
        constantUtf8 = constantPool.getConstant(index);
        String descriptor = constantUtf8.getString();

        int attributesCount = bytes.readInt(2);

        return new ClassField(accessFlags, name, descriptor, attributesCount);
    }

}
