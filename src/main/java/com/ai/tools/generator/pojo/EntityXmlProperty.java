/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator.pojo;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * 用于JOSN生成Entity属性 XML文件
 * @User: mayc
 * @version $Revision$ $Date$
  */
public class EntityXmlProperty implements Cloneable {
    private String name;
    private String type;
    private String desc;
    private String entity;

    public EntityXmlProperty(String name, String type, String desc, String entity) {
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.entity = entity;
    }

    public EntityXmlProperty(String name) {
        this(name, null, null, null);
    }

    public Object clone() {
        return new EntityXmlProperty(getName(), getType(), getDesc(), getEntity());
    }

    public boolean equals(Object obj) {
        EntityXmlProperty col = (EntityXmlProperty) obj;

        String            name = col.getName();

        if (getName().equals(name)) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }
}
