package com.lmmmowi.bci;

import com.lmmmowi.bci.bean.*;
import com.lmmmowi.bci.utils.ValueUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: lmmmowi
 * @Date: 2020/1/15
 * @Description:
 */
public class ConstantParser {

    private static final ConstantParser INSTANCE = new ConstantParser();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public static ConstantParser getInstance() {
        return INSTANCE;
    }

    private ConstantParser() {
    }

    public List<IConstant> parse(ClassBytesWrapper bytes) {
        int constantPoolCount = bytes.readInt(2) - 1;
        logger.info("解析常量池计数：{}", constantPoolCount);

        List<IConstant> constants = new ArrayList<>(constantPoolCount);
        for (int i = 0; i < constantPoolCount; i++) {
            IConstant constant = this.parseConstant(bytes);
            logger.info("解析第{}个常量：{}", i + 1, constant);
            constants.add(constant);

            if (this.takeUpExtraEntry(constant)) {
                constants.add(null);
                i++;
            }
        }
        return constants;
    }

    private IConstant parseConstant(ClassBytesWrapper bytes) {
        byte tag = bytes.readByte();
        switch (tag) {
            case 1:
                return parseUtf8(bytes);
            case 3:
                return parseInteger(bytes);
            case 4:
                return parseFloat(bytes);
            case 5:
                return parseLong(bytes);
            case 6:
                return parseDouble(bytes);
            case 7:
                return parseClass(bytes);
            case 8:
                return parseString(bytes);
            case 9:
                return parseFieldRef(bytes);
            case 10:
                return parseMethodRef(bytes);
            case 11:
                return parseInterfaceMethodRef(bytes);
            case 12:
                return parseNameAndType(bytes);
            case 15:
                return parseMethodHandle(bytes);
            case 16:
                return parseMethodType(bytes);
            case 18:
                return parseInvokeDynamic(bytes);
            default:
                throw new IllegalStateException("invalid constant tag: " + tag);
        }
    }

    private ConstantUtf8 parseUtf8(ClassBytesWrapper bytes) {
        int length = bytes.readInt(2);
        byte[] data = bytes.readBytes(length);
        return new ConstantUtf8(length, new String(data));
    }

    private ConstantInteger parseInteger(ClassBytesWrapper bytes) {
        byte[] data = bytes.readBytes(4);
        return new ConstantInteger(ValueUtil.toInt(data));
    }

    private ConstantFloat parseFloat(ClassBytesWrapper bytes) {
        byte[] data = bytes.readBytes(4);
        return new ConstantFloat(ValueUtil.toFloat(data));
    }

    private ConstantLong parseLong(ClassBytesWrapper bytes) {
        byte[] data = bytes.readBytes(8);
        return new ConstantLong(ValueUtil.toLong(data));
    }

    private ConstantDouble parseDouble(ClassBytesWrapper bytes) {
        byte[] data = bytes.readBytes(8);
        return new ConstantDouble(ValueUtil.toDouble(data));
    }

    private ConstantClass parseClass(ClassBytesWrapper bytes) {
        int index = bytes.readInt(2);
        return new ConstantClass(index);
    }

    private ConstantString parseString(ClassBytesWrapper bytes) {
        int index = bytes.readInt(2);
        return new ConstantString(index);
    }

    private ConstantFieldRef parseFieldRef(ClassBytesWrapper bytes) {
        int classIndex = bytes.readInt(2);
        int nameAndTypeIndex = bytes.readInt(2);
        return new ConstantFieldRef(classIndex, nameAndTypeIndex);
    }

    private ConstantMethodRef parseMethodRef(ClassBytesWrapper bytes) {
        int classIndex = bytes.readInt(2);
        int nameAndTypeIndex = bytes.readInt(2);
        return new ConstantMethodRef(classIndex, nameAndTypeIndex);
    }

    private ConstantInterfaceMethodRef parseInterfaceMethodRef(ClassBytesWrapper bytes) {
        int classIndex = bytes.readInt(2);
        int nameAndTypeIndex = bytes.readInt(2);
        return new ConstantInterfaceMethodRef(classIndex, nameAndTypeIndex);
    }

    private ConstantNameAndType parseNameAndType(ClassBytesWrapper bytes) {
        int nameIndex = bytes.readInt(2);
        int typeIndex = bytes.readInt(2);
        return new ConstantNameAndType(nameIndex, typeIndex);
    }

    private ConstantMethodHandle parseMethodHandle(ClassBytesWrapper bytes) {
        int referenceKind = bytes.readInt(1);
        int referenceIndex = bytes.readInt(2);
        return new ConstantMethodHandle(referenceKind, referenceIndex);
    }

    private ConstantMethodType parseMethodType(ClassBytesWrapper bytes) {
        int descriptorIndex = bytes.readInt(2);
        return new ConstantMethodType(descriptorIndex);
    }

    private ConstantInvokeDynamic parseInvokeDynamic(ClassBytesWrapper bytes) {
        int bootstrapMethodAttrIndex = bytes.readInt(2);
        int nameAndTypeIndex = bytes.readInt(2);
        return new ConstantInvokeDynamic(bootstrapMethodAttrIndex, nameAndTypeIndex);
    }

    private boolean takeUpExtraEntry(IConstant constant) {
        return constant instanceof ConstantLong || constant instanceof ConstantDouble;
    }
}
