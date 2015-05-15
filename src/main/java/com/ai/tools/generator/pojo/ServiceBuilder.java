/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator.pojo;

import java.util.List;


/**
 * @User: mayc
 * @Date: 2014年4月30日
 * @Time: 下午1:56:10
 * @version $Revision$ $Date$
 */
public class ServiceBuilder {
    private String packagePath;
    List<EntityXml> listEntity;

    public ServiceBuilder(String packagePath, List<EntityXml> listEntity) {
        this.packagePath = packagePath;
        this.listEntity = listEntity;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public List<EntityXml> getListEntity() {
        return listEntity;
    }

    public void setListEntity(List<EntityXml> listEntity) {
        this.listEntity = listEntity;
    }
}
