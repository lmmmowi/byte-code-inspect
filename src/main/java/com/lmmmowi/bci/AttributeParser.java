package com.lmmmowi.bci;

import com.lmmmowi.bci.bean.Attribute;
import com.lmmmowi.bci.bean.ConstantUtf8;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lmmmowi
 * @Date: 2020/1/16
 * @Description:
 */
public class AttributeParser {

    private static final AttributeParser INSTANCE = new AttributeParser();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private AttributeParser() {
    }

    public static AttributeParser getInstance() {
        return INSTANCE;
    }

    public List<Attribute> parse(ClassBytesWrapper bytes, ConstantPool constantPool) {
        int count = bytes.readInt(2);
        logger.info("解析属性计数：{}", count);

        List<Attribute> attributes = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Attribute attribute = this.parseAttribute(bytes, constantPool);
            logger.info("解析第{}个属性：{}", i + 1, attribute);
            attributes.add(attribute);
        }
        return attributes;
    }

    private Attribute parseAttribute(ClassBytesWrapper bytes, ConstantPool constantPool) {
        int index = bytes.readInt(2);
        ConstantUtf8 constantUtf8 = constantPool.getConstant(index);
        String name = constantUtf8.getString();

        int length = bytes.readInt(4);
        byte[] info = bytes.readBytes(length);

        return new Attribute(name, info);
    }
}
