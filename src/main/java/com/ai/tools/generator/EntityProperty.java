/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator;


/**
 * 实体属性描述
 * @User: mayc
 * @version $Revision$ $Date$
  */
public class EntityProperty implements Cloneable {
    private String  name;
    private String  type;
    private String  desc; //属性描述
    private String  defaultValue;
    private boolean notNull;
    private String  order;
    private String  ejbName;
    private String  ejbPackagePath;

    public EntityProperty(String name, String type, String desc, String ejbName,
        String ejbPackagePath) {
        this(name, type, desc, null, false, null, ejbName, null);
    }

    public EntityProperty(String name, String type, String desc) {
        this(name, type, desc, null, false, null, null, null);
    }

    public EntityProperty(String name, String type, String desc, String defaultValue,
        boolean notNull, String order, String ejbName, String ejbPackagePath) {
        this.name = name;
        this.type = type;
        this.desc = desc;
        this.defaultValue = defaultValue;
        this.notNull = notNull;
        this.order = order;
        this.ejbName = ejbName;
        this.ejbPackagePath = ejbPackagePath;
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

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(boolean notNull) {
        this.notNull = notNull;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getEjbName() {
        return ejbName;
    }

    public void setEjbName(String ejbName) {
        this.ejbName = ejbName;
    }

    public Object clone() {
        return new EntityProperty(getName(), getType(), getDesc(), getDefaultValue(), isNotNull(),
            getOrder(), getEjbName(), getEjbPackagePath());
    }

    public boolean equals(Object obj) {
        EntityProperty col = (EntityProperty) obj;

        String         name = col.getName();

        if (getName().equals(name)) {
            return true;
        } else {
            return false;
        }
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEjbPackagePath() {
        return ejbPackagePath;
    }

    public void setEjbPackagePath(String ejbPackagePath) {
        this.ejbPackagePath = ejbPackagePath;
    }
}
