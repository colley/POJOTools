/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator.pojo;

import com.ai.tools.generator.util.StringUtil;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * 用于JOSN生成Entity 节点  属性 XML文件
 * @User: mayc
 * @version $Revision$ $Date$
  */
public class EntityXml {
    private String                  name; //类名  主要是该属性
    private String                  desc; //实体描述
    private String                  packagePath; //包路径
    private List<EntityXmlProperty> propertyList; //类属性
    private String                  superClazz; //实体描述

    public EntityXml(String name) {
        this.name = name;
    }

    public void addEntityXmlProperty(EntityXmlProperty property) {
        if (propertyList == null) {
            propertyList = new ArrayList<EntityXmlProperty>();
        }

        if ((property != null) && StringUtil.isNotEmpty(property.getName())) {
            propertyList.add(property);
        }
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<EntityXmlProperty> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<EntityXmlProperty> propertyList) {
        this.propertyList = propertyList;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }


	public String getSuperClazz() {
		return superClazz;
	}

	public void setSuperClazz(String superClazz) {
		this.superClazz = superClazz;
	}

	public static void main(String[] args) {
        String superName = "com.yesy.Test";
        System.out.println(superName.substring(superName.lastIndexOf(".") + 1, superName.length()));
        System.out.println(superName.substring(0, superName.lastIndexOf(".")));
    }
}
