/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator;


/**
 * 实体父类描述
 * @User: mayc
 * @version $Revision$ $Date$
  */
public class EntitySuperClazz implements Cloneable {
    private String clazzName;
    private String packagePath;

    public EntitySuperClazz() {
    }

    public EntitySuperClazz(String clazzName) {
        this(clazzName, "");
    }

    public EntitySuperClazz(String clazzName, String packagePath) {
        this.clazzName = clazzName;
        this.packagePath = packagePath;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public Object clone() {
        return new EntitySuperClazz(getClazzName(), getPackagePath());
    }

    public boolean equals(Object obj) {
        EntitySuperClazz sup = (EntitySuperClazz) obj;

        String           name = sup.getClazzName();

        if (getClazzName().equals(name)) {
            return true;
        } else {
            return false;
        }
    }
}
