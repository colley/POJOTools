/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator;


/**
 * 接口实体
 * @User: mayc
 * @Date: 2014年4月29日
 * @Time: 上午11:47:03
 * @version $Revision$ $Date$
 */
public class EntityImplClazz implements Cloneable {
    private String implClazzName;

    public EntityImplClazz(String implClazzName) {
        this.implClazzName = implClazzName;
    }

    public String getImplClazzName() {
        return implClazzName;
    }

    public void setImplClazzName(String implClazzName) {
        this.implClazzName = implClazzName;
    }

    public Object clone() {
        return new EntityImplClazz(getImplClazzName());
    }

    public boolean equals(Object obj) {
        EntityImplClazz sup = (EntityImplClazz) obj;

        String          name = sup.getImplClazzName();

        if (getImplClazzName().equals(name)) {
            return true;
        } else {
            return false;
        }
    }
}
