/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator;


/**
 * @User: mayc
 * @Date: 2014年4月25日
 * @Time: 下午5:24:01
 * @version $Revision$ $Date$
 */
public enum ClazzTypeEnum {
	STRING("String", "String"), 
	LONG("long", "long"),
	LONG_1("Long", "Long"), 
    DATE("Date", "java.util.Date"), 
    SDATE("SDate", "java.sql.Date"), 
    INT("int", "int"), 
    INTEGER("Integer", "Integer"), 
    DOUBLE("double", "double"),
    DOUBLE_1("Double", "Double"), 
    FLOAT("float", "float"),
    FLOAT_1("Float", "Float"), 
    BOOLEAN("boolean", "boolean"), 
    BOOLEAN_1("Boolean", "Boolean"), 
    SHORT("short", "short"),
    SHORT_1("Short", "Short"), 
    BYTE("byte", "byte"),
    BYTE_1("Byte", "Byte"), 
    CHAR("char", "char"), 
    CHAR_1("Character", "Character"),
    CONLECTION("Collection", "java.util.Collection"), 
    LIST("List", "java.util.List"),
    MAP("Map", "java.util.Map");
	
	ClazzTypeEnum(String clzType,String importClazz) {
        this.clzType = clzType;
        this.importClazz = importClazz;
    }

    private String clzType;
    private String importClazz;

    public String getClzType() {
        return clzType;
    }

    public void setClzType(String clzType) {
        this.clzType = clzType;
    }

    public String getImportClazz() {
        return importClazz;
    }

    public void setImportClazz(String importClazz) {
        this.importClazz = importClazz;
    }

    public static ClazzTypeEnum getClazzTypeEnumByType(String clzType) {
        ClazzTypeEnum tempClzEnum = null;
        for (ClazzTypeEnum clzEnum : ClazzTypeEnum.values()) {
            if (clzEnum.getClzType().equals(clzType.trim())) {
                tempClzEnum = clzEnum;
            }
        }

        for (ClazzTypeEnum clzEnum : ClazzTypeEnum.values()) {
            if (clzEnum.getImportClazz().equals(clzType.trim())) {
                tempClzEnum = clzEnum;
            }
        }

        return tempClzEnum;
    }

    public static ClazzTypeEnum isFilterType(String clzType) {
        ClazzTypeEnum clazzTypeEnum = getClazzTypeEnumByType(clzType);
        if (clazzTypeEnum != null) {
            if (clazzTypeEnum.equals(ClazzTypeEnum.BYTE_1) ||
                    clazzTypeEnum.equals(ClazzTypeEnum.STRING) ||
                    clazzTypeEnum.equals(ClazzTypeEnum.CHAR_1) ||
                    clazzTypeEnum.equals(ClazzTypeEnum.SHORT_1) ||
                    clazzTypeEnum.equals(ClazzTypeEnum.BOOLEAN_1) ||
                    clazzTypeEnum.equals(ClazzTypeEnum.FLOAT_1) ||
                    clazzTypeEnum.equals(ClazzTypeEnum.DOUBLE_1) ||
                    clazzTypeEnum.equals(ClazzTypeEnum.INTEGER) ||
                    clazzTypeEnum.equals(ClazzTypeEnum.LONG_1)) {
                return clazzTypeEnum;
            }
        }

        return null;
    }

    public static ClazzTypeEnum isImportClazz(String clzType) {
        ClazzTypeEnum clazzTypeEnum = getClazzTypeEnumByType(clzType);
        if (clazzTypeEnum != null) {
            if (clazzTypeEnum.equals(ClazzTypeEnum.CONLECTION) ||
                    clazzTypeEnum.equals(ClazzTypeEnum.LIST) ||
                    clazzTypeEnum.equals(ClazzTypeEnum.MAP) ||
                    clazzTypeEnum.equals(ClazzTypeEnum.DATE) ||
                    clazzTypeEnum.equals(ClazzTypeEnum.SDATE)) {
                return clazzTypeEnum;
            }
        }

        return null;
    }
}
