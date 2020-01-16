package com.lmmmowi.bci;

import cn.hutool.core.io.IoUtil;
import com.lmmmowi.bci.bean.*;
import com.lmmmowi.bci.enums.ClassAccessFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: lmmmowi
 * @Date: 2020/1/15
 * @Description:
 */
public class ClassParser {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private ClassBytesWrapper bytes;
    private ClassInfo.ClassInfoBuilder builder;
    private ConstantPool constantPool;

    public ClassParser(byte[] bytes) {
        this.bytes = new ClassBytesWrapper(bytes);
    }

    public ClassInfo parse() {
        checkMagicNumber();

        builder = ClassInfo.builder();
        parseJdkVersion();
        parseConstantPool();
        parseClassAccessFlag();
        parseClassName();
        parseSuperClassName();
        parseInterfaces();
        parseFields();
        parseMethods();
        parseAttributes();

        if (bytes.hasMoreData()) {
            throw new IllegalStateException("invalid data at the end of class");
        } else {
            logger.info("解析结束");
        }

        return builder.build();
    }

    private void checkMagicNumber() {
        for (byte magicNumber : ClassDefine.MAGIC_NUMBERS) {
            if (bytes.readByte() != magicNumber) {
                throw new IllegalStateException("invalid java class");
            }
        }

        logger.info("解析文件头魔数");
    }

    private void parseJdkVersion() {
        int jdkVersion = bytes.readInt(4);
        logger.info("解析JDK版本：{}", jdkVersion);
        builder.jdkVersion(jdkVersion);
    }

    private void parseConstantPool() {
        ConstantParser constantParser = ConstantParser.getInstance();
        List<IConstant> constants = constantParser.parse(bytes);
        builder.constants(constants);

        this.constantPool = new ConstantPool(constants);
    }

    private void parseClassAccessFlag() {
        int accessFlag = bytes.readInt(2);
        Set<ClassAccessFlag> accessFlags = Arrays.stream(ClassAccessFlag.values())
                .filter(o -> o.match(accessFlag))
                .collect(Collectors.toSet());
        logger.info("解析访问标志：{}", accessFlags);
        builder.accessFlags(accessFlags);
    }

    private void parseClassName() {
        int index = bytes.readInt(2);
        ConstantClass constantClass = constantPool.getConstant(index);
        ConstantUtf8 constantUtf8 = constantPool.getConstant(constantClass.getIndex());
        String className = constantUtf8.getString();
        logger.info("解析类名：{}", className);
        builder.className(className);
    }

    private void parseSuperClassName() {
        int index = bytes.readInt(2);
        ConstantClass constantClass = constantPool.getConstant(index);
        ConstantUtf8 constantUtf8 = constantPool.getConstant(constantClass.getIndex());
        String superClassName = constantUtf8.getString();
        logger.info("解析父类类名：{}", superClassName);
        builder.className(superClassName);
    }

    private void parseInterfaces() {
        int count = bytes.readInt(2);
        List<String> interfaceNames = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            int index = bytes.readInt(2);
            ConstantClass constantClass = constantPool.getConstant(index);
            ConstantUtf8 constantUtf8 = constantPool.getConstant(constantClass.getIndex());
            String interfaceName = constantUtf8.getString();
            interfaceNames.add(interfaceName);
        }
        logger.info("解析类实现的接口: {}", interfaceNames);
        builder.interfaceNames(interfaceNames);
    }

    private void parseFields() {
        FieldParser fieldParser = FieldParser.getInstance();
        List<ClassField> fields = fieldParser.parse(bytes, constantPool);
        builder.fields(fields);
    }

    private void parseMethods() {
        MethodParser methodParser = MethodParser.getInstance();
        List<ClassMethod> methods = methodParser.parse(bytes, constantPool);
        builder.methods(methods);
    }

    private void parseAttributes() {
        AttributeParser attributeParser = AttributeParser.getInstance();
        List<Attribute> attributes = attributeParser.parse(bytes, constantPool);
        builder.attributes(attributes);
    }

    public static void main(String[] args) throws IOException {
        InputStream inputStream = new FileInputStream(new File("E:\\workspace\\lmmmowi\\byte-code-inspect\\Inspect.class"));
        byte[] bytes = IoUtil.readBytes(inputStream);
        ClassInfo classInfo = new ClassParser(bytes).parse();
        System.out.println(classInfo);
    }

}
