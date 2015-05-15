/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;


/**
 * TODO
 * @User: mayc
 * @version $Revision$ $Date$
  */
public class FrermarkerUtil {
    private Log           logger = LogFactory.getLog(FrermarkerUtil.class);
    private Template      template;
    private Configuration tempConfiguration;
    private final static String   TEMPLATESPATH = "template";
    private static FrermarkerUtil instance = new FrermarkerUtil();

    private FrermarkerUtil() {
        init();
    }

    public static FrermarkerUtil getInstance() {
        return instance;
    }

    private void init() {
        if (logger.isDebugEnabled()) {
            logger.debug("start init...");
        }

        try {
            tempConfiguration = new Configuration();
            tempConfiguration.setClassicCompatible(true);
            //tempConfiguration.setClassForTemplateLoading();
            tempConfiguration.setDirectoryForTemplateLoading(new File(this.getClass()
                                                                          .getResource("/").getPath() +
                    TEMPLATESPATH));
            //tempConfiguration.setClassForTemplateLoading(this.getClass().getClassLoader().getClass(), TEMPLATESPATH);
            tempConfiguration.setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            tempConfiguration.setNumberFormat("");
            tempConfiguration.setDefaultEncoding("utf-8");
        } catch (Exception e) {
            logger.error("error", e);
        }

        if (logger.isDebugEnabled()) {
            logger.debug("end init...");
        }
    }

    public String themplate2String(Object root, String templateFile) {
        if (logger.isDebugEnabled()) {
            logger.debug("start generate String");
        }

        StringWriter sw = new StringWriter();
        try {
            template = tempConfiguration.getTemplate(templateFile);
            template.process(root, sw);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }

        return sw.toString();
    }

    public String themplate2File(String path, String fileName, Object root, String templateFile) {
        creatDirs(path);
        Writer out = null;
        File   afile = new File(path + File.separator + fileName);
        try {
            template = tempConfiguration.getTemplate(templateFile);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(afile), "UTF-8"));
            template.process(root, out);
            out.flush();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        } catch (TemplateException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
            }
        }

        return afile.getPath();
    }

    public static boolean creatDirs(String path) {
        File aFile = new File(path);

        if (!aFile.exists()) {
            return aFile.mkdirs();
        } else {
            return true;
        }
    }

    public static void save(final OutputStream os, final String content)
        throws IOException {
        BufferedOutputStream bos = new BufferedOutputStream(os);
        bos.write(content.getBytes());
        bos.flush();
    }

    /**
     * @param args
     *
     */
    public static void main(String[] args) {
        /*FrermarkerUtil fx=new FrermarkerUtil();
        String path =new File(fx.getClass().getResource("/").getPath()).getParent()+File.separator+"out";
        System.out.println(path);
        ClazzPojo clazz =new ClazzPojo();
        clazz.setClazzName("TestBase");
        clazz.setPackPath("com.ai.tools.util");
        List<String> impo = new ArrayList<String>();
        impo.add("java.util.Date");
        impo.add("java.util.List");
        clazz.setImportList(impo);
        ClazzProperty pro = new ClazzProperty("String", "name");
        clazz.addProperty(pro);
        pro = new ClazzProperty("int", "age");
        clazz.addProperty(pro);
        pro = new ClazzProperty("int", "sex");
        clazz.addProperty(pro);
        pro = new ClazzProperty("long", "name1");
        clazz.addProperty(pro);
        fx.themplate2File(path, clazz.getClazzName()+"DTO.java", clazz, "pojo.ftl");*/
    }
}
