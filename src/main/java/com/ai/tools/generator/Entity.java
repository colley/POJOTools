/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.ai.tools.generator.util.StringUtil;


/**
 * 实体描述
 * @User: mayc
 * @version $Revision$ $Date$
  */
public class Entity implements Serializable {
    /**@Fields serialVersionUID */ 
	private static final long serialVersionUID = -6461480040720777630L;
	private String                packagePath; //包路径
    private String                name; //类名
    private String                desc; //实体描述
    private List<EntityProperty>  propertyList; //类属性
    private List<EntityProperty>  collectionList;
    private List<EntityProperty>  pojoList;
    private List<EntityProperty>  baseList;
    private EntitySuperClazz      superClazz; //父类
    private Set<String>           imports; //要导入的外部类
    private List<EntityImplClazz> implClazzList;

    public Entity(String packagepath, String name, String desc, String superclassName) {
        this.packagePath = packagepath;
        this.name = name;
        this.desc = desc;
        if (StringUtil.isNotEmpty(superclassName)) {
            superClazz = new EntitySuperClazz(superclassName);
        }
    }

    public Entity(String packagepath, String name, String desc, EntitySuperClazz superclass,
        List<EntityProperty> propertyList, List<EntityProperty> collectionList,
        List<EntityProperty> pojoList, List<EntityProperty> baseList) {
        this.packagePath = packagepath;
        this.name = name;
        this.desc = desc;
        this.superClazz = superclass;
        this.propertyList = propertyList;
        this.collectionList = collectionList;
        this.pojoList = pojoList;
        this.baseList = baseList;
    }

    public void addEntityProperty(EntityProperty property) {
        if (propertyList == null) {
            propertyList = new ArrayList<EntityProperty>();
        }

        if (property != null) {
            if (!propertyList.contains(property)) {
                propertyList.add(property);
            }
        }
    }

    public boolean hasProperty() {
        if ((propertyList == null) || (propertyList.size() == 0)) {
            return false;
        } else {
            return true;
        }
    }

    public String getPackagePath() {
        return StringUtil.trimToEmpty(packagePath);
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getName() {
        return StringUtil.trimToEmpty(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EntityProperty> getPropertyList() {
        return propertyList;
    }

    public void setPropertyList(List<EntityProperty> propertyList) {
        this.propertyList = propertyList;
    }

    public EntitySuperClazz getSuperClazz() {
        return superClazz;
    }

    public void setSuperClazz(EntitySuperClazz superClazz) {
        this.superClazz = superClazz;
    }

    public List<EntityProperty> getCollectionList() {
        return collectionList;
    }

    public void setCollectionList(List<EntityProperty> collectionList) {
        this.collectionList = collectionList;
    }

    public List<EntityProperty> getPojoList() {
        return pojoList;
    }

    public void setPojoList(List<EntityProperty> pojoList) {
        this.pojoList = pojoList;
    }

    public List<EntityProperty> getBaseList() {
        return baseList;
    }

    public void setBaseList(List<EntityProperty> baseList) {
        this.baseList = baseList;
    }

    public Set<String> getImports() {
        return imports;
    }

    public void setImports(Set<String> imports) {
        this.imports = imports;
    }

    public List<EntityImplClazz> getImplClazzList() {
        return implClazzList;
    }

    public void setImplClazzList(List<EntityImplClazz> implClazzList) {
        this.implClazzList = implClazzList;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
