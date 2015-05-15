/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator;

import com.ai.tools.generator.pojo.EntityXml;
import com.ai.tools.generator.pojo.EntityXmlProperty;
import com.ai.tools.generator.pojo.ServiceBuilder;
import com.ai.tools.generator.util.AutoAnalyzer;
import com.ai.tools.generator.util.FreeMarkerUtil;
import com.ai.tools.generator.util.StringPool;
import com.ai.tools.generator.util.StringUtil;
import com.ai.tools.generator.util.XMLFormatter;

import freemarker.ext.beans.BeansWrapper;

import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * @User: mayc
 * @Date: 2014年4月29日
 * @Time: 下午2:57:09
 * @version $Revision$ $Date$
 */
public class JSON2EntityXml {
    private static String       _implDir;
    private String              _outputPath;
    private String              _packagePath;
    private static final String _TPL_ROOT = "tools/dependencies/";
    private String              _tplEntityXml = _TPL_ROOT + "entity_xml.ftl";
    private String              dtdFilePath = "";
    private String 				superClazz;

    public List<EntityXml> transform(String json, String mainName) {
        JSONObject      jsonObject = JSONObject.fromObject(json);
        List<EntityXml> entityList = new ArrayList<EntityXml>();
        transform(mainName, jsonObject, entityList,true);
        System.out.println(entityList.size());
        for (EntityXml en : entityList) {
            System.out.println(en.getName());
            for (EntityXmlProperty e : en.getPropertyList()) {
                System.out.println(e.toString());
            }
        }

        return entityList;
    }

    public void transform(String name, Object json, List<EntityXml> entityList,boolean isMain) {
        EntityXml entityXml = new EntityXml(name);
        if(isMain){
        	entityXml.setSuperClazz(superClazz);
        }
        if (json instanceof JSONArray) {
            JSONArray jsonArray = (JSONArray) json;
            Object    jsonObj = jsonArray.get(0);
            transform(name, jsonObj, entityList,false);
        } else if (json instanceof JSONObject) {
            JSONObject  jsonObject = (JSONObject) json;
            Iterator<?> iter = jsonObject.keys();
            while (iter.hasNext()) {
                String            objKey = (String) iter.next();
                EntityXmlProperty property = new EntityXmlProperty(objKey);
                Object            objValue = jsonObject.get(objKey);
                if (objValue instanceof JSONObject) {
                    property.setEntity(AutoAnalyzer.analyzerWorld(objKey));
                    property.setType(AutoAnalyzer.analyzerWorld(objKey));
                    transform(AutoAnalyzer.analyzerWorld(objKey), objValue, entityList,false);
                } else if (objValue instanceof JSONArray) {
                    property.setEntity(AutoAnalyzer.analyzerWorld(objKey));
                    property.setType("List");
                    JSONArray jsonArr = (JSONArray) objValue;
                    transform(AutoAnalyzer.analyzerWorld(objKey), jsonArr.get(0), entityList,false);
                } else {
                    property.setType(objValue.getClass().getSimpleName());
                }

                entityXml.addEntityXmlProperty(property);
            }
        }

        entityList.add(entityXml);
    }

    private String getTplProperty(String key, String defaultValue) {
        return System.getProperty("service.tpl." + key, defaultValue);
    }

    public JSON2EntityXml() {
    }

    public JSON2EntityXml(String implDir, String packagepath,String superClazz, int len) {
        _tplEntityXml = getTplProperty("entity_xml", _tplEntityXml);
        _implDir = implDir;
        len = StringUtil.getDTDFilePath(implDir, packagepath);
        dtdFilePath = StringUtil.add(StringPool.BACK_SLASH_1, len);
        this.superClazz = superClazz;
        this._outputPath = _implDir + "/" + StringUtil.replace(packagepath, ".", "/");
        this._packagePath = packagepath;
    }

    private String processTemplate(String name, Map<String, Object> context)
        throws Exception {
        return FreeMarkerUtil.process(name, context);
    }

    public String createXML(String json, String mainClazzName)
        throws Exception {
        List<EntityXml>     list = transform(json, mainClazzName);
        Map<String, Object> context = getContext();
        ServiceBuilder      serviceBuilder = new ServiceBuilder(_packagePath, list);
        context.put("entity", serviceBuilder);
        String content = processTemplate(_tplEntityXml, context);
        File   xmlFile = new File(_outputPath + File.separator +
                mainClazzName.concat("-entity.xml"));
        if (xmlFile.exists()) {
            xmlFile.delete();
        }
        System.gc();
        if (!xmlFile.exists()) {
            String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<!DOCTYPE service-builder SYSTEM \"" + dtdFilePath + "tools/pojo-builder.dtd\">";
            String oldContent = xml.concat(content);
            String newContent = XMLFormatter.formatXML(oldContent);
            System.err.println(newContent);
            FileUtils.writeStringToFile(xmlFile, newContent);
        }

        return xmlFile.getPath();
    }

    private Map<String, Object> getContext() throws TemplateModelException {
        BeansWrapper        wrapper = BeansWrapper.getDefaultInstance();
        TemplateHashModel   staticModels = wrapper.getStaticModels();
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("implDir", _implDir);
        context.put("outputPath", _outputPath);
        context.put("packagePath", _packagePath);
        context.put("serviceBuilder", this);
        context.put("system", staticModels.get("java.lang.System"));
        context.put("tempMap", wrapper.wrap(new HashMap<String, Object>()));
        context.put("validator", staticModels.get("com.ai.tools.generator.util.Validator"));
        context.put("StringPool", staticModels.get("com.ai.tools.generator.util.StringPool"));
        return context;
    }

    public static String JSONStr = "{" + "\"BILL_ID\": 13588489075," +
        "\"MARKET_PROG_DES\": \"这是一项通过GPRS、EDGE等移动通信技术上网或使用相关数据增值业务所产生的数据流量，让您享受无线上网的乐趣。目前的标准资费为0.01元/KB，包月有5元含30M 、10元含80M、20元含200M等档位。\"," +
        "\"MARKET_PROG_ID\": 500480002098," + "\"MARKET_PROG_NAME\": \"手机上网流量包\"," +
        "\"MARKET_PROG_TYPE\": 1," + "\"SIM_AREA_IS_PREOPEN\": 1," + "\"USE_CHNL_FUNDS_POOL\": 1," +
        "\"VERIF_LEVEL\": 2," + "\"MARKET_KIND\": {" +
        "\"MARKET_KIND_DES\": \"月费10元,含80M国内（不含港澳台）移动数据流量,超过部分0.001元/1KB收取。\"," +
        "\"MARKET_KIND_ID\": 500480002162," + "\"MARKET_KIND_NAME\": \"10元80M\"}," +
        "\"OTHER_OFFERS\": [{" + "\"EXPIRE_DATE\": 20991231000000," +
        "\"EXPIRE_DATE_SHOW\": \"2099-12-31\"," + "\"MAX_DATE_SHOW\": \"2015-04-23\"," +
        "\"MAX_SELECT\": -1," + "\"MIN_SELECT\": -1," + "\"MUST_SEL\": 0," +
        "\"OFFER_ID\": 500300118690," + "\"OFFER_NAME\": \"移动数据流量10元包\"," +
        "\"OFFER_TYPE\": \"OFFER_VAS_OTHER\"}]}";

    public static void main(String[] args) throws Exception {
        String json = "{" + "\"BILL_ID\": 13588489075," +
            "\"MARKET_PROG_DES\": \"这是一项通过GPRS、EDGE等移动通信技术上网或使用相关数据增值业务所产生的数据流量，让您享受无线上网的乐趣。目前的标准资费为0.01元/KB，包月有5元含30M 、10元含80M、20元含200M等档位。\"," +
            "\"MARKET_PROG_ID\": 500480002098," + "\"MARKET_PROG_NAME\": \"手机上网流量包\"," +
            "\"MARKET_PROG_TYPE\": 1," + "\"SIM_AREA_IS_PREOPEN\": 1," +
            "\"USE_CHNL_FUNDS_POOL\": 1," + "\"VERIF_LEVEL\": 2," + "\"MARKET_KIND\": {" +
            "\"MARKET_KIND_DES\": \"月费10元,含80M国内（不含港澳台）移动数据流量,超过部分0.001元/1KB收取。\"," +
            "\"MARKET_KIND_ID\": 500480002162," + "\"MARKET_KIND_NAME\": \"10元80M\"}," +
            "\"OTHER_OFFERS\": [{" + "\"EXPIRE_DATE\": 20991231000000," +
            "\"EXPIRE_DATE_SHOW\": \"2099-12-31\"," + "\"MAX_DATE_SHOW\": \"2015-04-23\"," +
            "\"MAX_SELECT\": -1," + "\"MIN_SELECT\": -1," + "\"MUST_SEL\": 0," +
            "\"OFFER_ID\": 500300118690," + "\"OFFER_NAME\": \"移动数据流量10元包\"," +
            "\"OFFER_TYPE\": \"OFFER_VAS_OTHER\"}]}";
        String json1 = "{" + "\"BILL_ID\": 13588489075," + "\"MARKET_KIND\": {" +
            "\"MARKET_KIND_DES\": \"月费10元,含80M国内（不含港澳台）移动数据流量,超过部分0.001元/1KB收取。\"," +
            "\"MARKET_KIND_ID\": 500480002162," + "\"MARKET_KIND_NAME\": \"10元80M\"," +
            "\"MARKET_KIND_OFFERS\": {" + "\"OTHER_OFFERS\": [" + "{" +
            "\"EXPIRE_DATE\": 20991231000000," + "\"EXPIRE_DATE_SHOW\": \"2099-12-31\"," +
            "\"MAX_DATE_SHOW\": \"2015-04-23\"," + "\"MAX_SELECT\": -1," + "\"MIN_SELECT\": -1," +
            "\"MUST_SEL\": 0," + "\"OFFER_ID\": 500300118690," + "\"OFFER_NAME\": \"移动数据流量10元包\"," +
            "\"OFFER_TYPE\": \"OFFER_VAS_OTHER\"," + "\"OFFER_TYPE_SHOW\": \"增\"," +
            "\"OPER_TYPE\": \"CREATE\"," + "\"VALID_CYCLE\": \"永久生效\"," +
            "\"VALID_DATE\": \"20140501000000\"," + "\"VALID_DATE_SHOW\": \"2014-05-01\"" + "}" +
            "]," + "\"SELECT_FLAG\": 0" + "}," + "\"MARKET_KIND_PRICE\": \"0.00\"" + "}," +
            "\"MARKET_PROG_DES\": \"这是一项通过GPRS、EDGE等移动通信技术上网或使用相关数据增值业务所产生的数据流量，让您享受无线上网的乐趣。目前的标准资费为0.01元/KB，包月有5元含30M 、10元含80M、20元含200M等档位。\"," +
            "\"MARKET_PROG_ID\": 500480002098," + "\"MARKET_PROG_NAME\": \"手机上网流量包\"," +
            "\"MARKET_PROG_TYPE\": 1," + "\"SIM_AREA_IS_PREOPEN\": 1," +
            "\"USE_CHNL_FUNDS_POOL\": 1," + "\"VERIF_LEVEL\": 2}";
        args = new String[2];
        args[0] = "src/main/resources/service.xml";
        args[1] = System.getProperty("user.dir") + File.separator + "src/main/java";
        JSON2EntityXml jsonXml = new JSON2EntityXml(args[1], "com.test.common.collections","com.asiainfo.scrm.common.data.InputCommonData", 2);
        String         xmlPath = jsonXml.createXML(json1, "InputObject");
        String         userPath = System.getProperty("user.dir");
        String         pojoPath = xmlPath.substring(xmlPath.indexOf(userPath) + userPath.length() +
                1, xmlPath.length());
        System.out.println(xmlPath.substring(xmlPath.indexOf(userPath) + userPath.length() + 1,
                xmlPath.length()));
        args = new String[2];
        args[0] = pojoPath;
        args[1] = System.getProperty("user.dir") + File.separator + "src/main/java";
        POJOBuilder.process(args);
    }
}
