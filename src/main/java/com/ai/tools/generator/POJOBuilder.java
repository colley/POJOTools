/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator;

import com.ai.tools.generator.util.DocumentUtil;
import com.ai.tools.generator.util.FreeMarkerUtil;
import com.ai.tools.generator.util.GetterUtil;
import com.ai.tools.generator.util.SourceFormatter;
import com.ai.tools.generator.util.StringPool;
import com.ai.tools.generator.util.StringUtil;
import com.ai.tools.generator.util.TimeUtil;

import de.hunsicker.io.FileFormat;

import de.hunsicker.jalopy.Jalopy;
import de.hunsicker.jalopy.storage.Convention;
import de.hunsicker.jalopy.storage.ConventionKeys;
import de.hunsicker.jalopy.storage.Environment;

import freemarker.ext.beans.BeansWrapper;

import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.Element;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;


/**
 * @User: mayc
 * @Date: 2014年4月28日
 * @Time: 下午3:00:57
 * @version $Revision$ $Date$
 */
public class POJOBuilder {
    Log log = LogFactory.getLog(this.getClass().getName());
    private static final String AUTHOR = "POJO Builder";
    private static final String TPL_ROOT = "tools/dependencies/";
    private String tplPOJO = TPL_ROOT + "pojo.ftl";
    private List<Entity> entityList;
    private String implDir;
    private String testDir;
    private String author;
    private String outputPath;
    private String packagePath;
    private static Map<String, Entity> _entityLookup = new HashMap<String, Entity>();
    private static Set<String> _importList = new HashSet<String>();
    private String rootXml = null;

    public POJOBuilder() {
    }

    public static void writeFile(File file, String content)
                          throws IOException {
        writeFile(file, content, AUTHOR);
    }

    public static void writeFile(File file, String content, String author)
                          throws IOException {
        writeFile(file, content, author, null);
    }

    public static void writeFile(File file, String content, String author,
                                 Map<String, Object> jalopySettings)
                          throws IOException {
        writeFile(file, content, author, jalopySettings, null);
    }

    public static void writeFile(File file, String content, String author,
                                 Map<String, Object> jalopySettings, String tempFileName)
                          throws IOException {
        String packagePath = getPackagePath(file);
        String className = file.getName();
        className = className.substring(0, className.length() - 5);

        content = SourceFormatter.stripImports(content, packagePath, className);

        // handle jalopy reserved words
        content = SourceFormatter.encodeReservedWords(content);
        File tempFile = null;
        if (StringUtils.isNotBlank(tempFileName)) {
            tempFile = new File(tempFileName);
        } else {
            tempFile = new File("ServiceBuilder.temp");
        }

        FileUtils.writeStringToFile(tempFile, content);
        // Beautify
        StringBuffer sb = new StringBuffer();
        Jalopy jalopy = new Jalopy();
        jalopy.setFileFormat(FileFormat.UNIX);
        jalopy.setInput(tempFile);
        jalopy.setOutput(sb);
        try {
            Jalopy.setConvention("tools/jalopy.xml");
        } catch (FileNotFoundException fnne) {
            fnne.printStackTrace();
            try {
                Jalopy.setConvention("../../misc/jalopy.xml");
            } catch (FileNotFoundException t) {
            }
        }

        if (jalopySettings == null) {
            jalopySettings = new HashMap<String, Object>();
        }

        Environment env = Environment.getInstance();
        // Author
        author = GetterUtil.getString((String) jalopySettings.get("author"), author);
        env.set("author", author);
        // File name
        env.set("fileName", file.getName());
        Convention convention = Convention.getInstance();
        String desc = GetterUtil.getString((String) jalopySettings.get("desc"), "");
        if (StringUtil.isNotEmpty(desc)) {
            desc = " * <p>" + desc + "</p>\n" + " *\n";
        }

        String classMask = "/**\n" +
                           " * <a href=\"$fileName$.html\"><b><i>View Source</i></b></a>\n" +
                           " *\n" + " * <p>\n" +
                           " * POJOBuilder generated this class. Modifications in this class will be\n" +
                           " * overwritten the next time is generated.\n" + " * </p>\n" + " *\n" +
                           desc + " * @author: $author$\n" + " * @date: " +
                           TimeUtil.getTimestamp() + "\n" + " * @version $Revision$ $Date$" +
                           "\n*/";
        convention.put(ConventionKeys.COMMENT_JAVADOC_TEMPLATE_CLASS, env.interpolate(classMask));
        convention.put(ConventionKeys.COMMENT_JAVADOC_TEMPLATE_INTERFACE, env.interpolate(classMask));
        jalopy.format();
        String newContent = sb.toString();

        // Write file if and only if the file has changed
        String oldContent = null;
        if (file.exists()) {
            // Read file
            oldContent = FileUtils.readFileToString(file, StringPool.UTF8);
            // Keep old version number
            int x = oldContent.indexOf("@version $Revision:");
            if (x != -1) {
                int y = oldContent.indexOf("$", x);
                y = oldContent.indexOf("$", y + 1);
                String oldVersion = oldContent.substring(x, y + 1);
                newContent = StringUtil.replace(newContent, "@version $Rev: $", oldVersion);
            }
        } else {
            newContent = StringUtil.replace(newContent, "@version $Rev: $",
                                            "@version $Revision: 1.183 $");
        }

        if ((oldContent == null) || !oldContent.equals(newContent)) {
            FileUtils.writeStringToFile(file, newContent, StringPool.UTF8);
            System.out.println("Writing " + file);
            // Workaround for bug with XJavaDoc
            file.setLastModified(System.currentTimeMillis() - (TimeUtil.SECOND * 5));
        }

        tempFile.deleteOnExit();
        if (tempFile.exists())
            tempFile.delete();
    }

    private String getTplProperty(String key, String defaultValue) {
        return System.getProperty("service.tpl." + key, defaultValue);
    }

    public POJOBuilder(String implDir, String fileName)
                throws Exception {
        this(fileName, implDir, null, true);
    }

    public POJOBuilder(String implDir, String fileName, String testDir)
                throws Exception {
        this(fileName, implDir, testDir, true);
    }

    @SuppressWarnings("unchecked")
    public POJOBuilder(String fileName, String implDir, String testDir, boolean build)
                throws Exception {
        tplPOJO = getTplProperty("pojo", tplPOJO);
        try {
            this.implDir    = implDir;
            this.testDir    = testDir;
            Document doc = DocumentUtil.readDocumentFromFile(new File(fileName), true);

            Element root = doc.getRootElement();

            String packagePath = root.attributeValue("package-path");

            this.outputPath = this.implDir + "/" + StringUtil.replace(packagePath, ".", "/");

            this.packagePath = packagePath;

            Element author = root.element("author");

            if (author != null) {
                this.author = author.getText();
            } else {
                this.author = AUTHOR;
            }

            entityList = new ArrayList<Entity>();

            List<Element> entities = root.elements("entity");

            Iterator<Element> itr1 = entities.iterator();
            while (itr1.hasNext()) {
                Element entityEl = itr1.next();
                String ejbName = entityEl.attributeValue("name");
                String packagepath = entityEl.attributeValue("package-path");
                String desc = entityEl.attributeValue("desc");

                //属性
                List<EntityProperty> collectionList = new ArrayList<EntityProperty>();
                List<EntityProperty> columnList = new ArrayList<EntityProperty>();
                List<EntityProperty> pojoList = new ArrayList<EntityProperty>();
                List<EntityProperty> baseList = new ArrayList<EntityProperty>();

                List<Element> propertys = entityEl.elements("property");

                Iterator<Element> itr2 = propertys.iterator();
                while (itr2.hasNext()) {
                    Element property = itr2.next();
                    // call create column method TODO mayc
                    this.createProperty(property, collectionList, columnList, pojoList, baseList,
                                        ejbName);
                }

                EntitySuperClazz superclass1 = createSuperclass(entityEl, "superclass");

                String entityPackagePath = this.packagePath;
                if (StringUtil.isNotEmpty(packagepath)) {
                    entityPackagePath = packagepath;
                }

                Entity entity = new Entity(entityPackagePath, ejbName, desc, superclass1,
                                           columnList, collectionList, pojoList, baseList);
                entityList.add(entity);
                _entityLookup.put(ejbName, entity);
            }

            //异常
            List<String> exceptionList = new ArrayList<String>();

            if (root.element("exceptions") != null) {
                List<Element> exceptions = root.element("exceptions").elements("exception");

                itr1 = exceptions.iterator();

                while (itr1.hasNext()) {
                    Element exception = itr1.next();

                    exceptionList.add(exception.getText());
                }
            }

            // process imports
            Element importsEl = root.element("imports");
            if (importsEl != null) {
                // mark the root XML as read
                if (rootXml == null) {
                    rootXml = fileName;
                    _importList.add(rootXml);
                }

                List<Element> imports = importsEl.elements("import");
                Iterator<Element> itr = imports.iterator();
                while (itr.hasNext()) {
                    Element importEl = itr.next();

                    String serviceXml = this.implDir + "/" + importEl.getTextTrim();

                    if (!_importList.contains(serviceXml)) {
                        _importList.add(serviceXml);

                        File file = new File(serviceXml);

                        if (!file.canRead()) {
                            System.out.println("File not found: " + serviceXml);
                            continue;
                        }

                        // fetch the entity information, it will add to _entityLookup
                        new POJOBuilder(this.implDir, serviceXml);
                    }
                }
            }

            if (build) {
                for (int x = 0; x < entityList.size(); x++) {
                    Entity entity = entityList.get(x);
                    Set<String> importsPath = new TreeSet<String>();
                    Map<String, Entity> fullEntityLookup = new HashMap<String, Entity>(_entityLookup);

                    //处理父类
                    if ((entity.getSuperClazz() != null) &&
                            StringUtil.isNotEmpty(entity.getSuperClazz().getClazzName())) {
                        EntitySuperClazz superClass = entity.getSuperClazz();
                        String superName = superClass.getClazzName();
                        if (StringUtil.isEmpty(superClass.getPackagePath())) {
                            Entity _entity = fullEntityLookup.get(superName);
                            if (_entity != null) {
                                superClass.setPackagePath(_entity.getPackagePath());
                                entity.setSuperClazz(superClass);
                            } else {
                                // Class.forName(superName);
                                if (superName.contains(".")) {
                                    superClass.setClazzName(superName.substring(superName.lastIndexOf(".") +
                                                                                1,
                                                                                superName.length()));
                                    superClass.setPackagePath(superName.substring(0,
                                                                                  superName.lastIndexOf(".")));
                                } else {
                                    superClass.setClazzName(superName);
                                    superClass.setPackagePath(null);
                                }
                            }
                        }
                    }

                    //处理基本属性
                    if ((entity.getBaseList() != null) && (entity.getBaseList().size() > 0)) {
                        Iterator<EntityProperty> iter = entity.getBaseList().iterator();
                        while (iter.hasNext()) {
                            EntityProperty baseProerty = iter.next();
                            ClazzTypeEnum clazzType = ClazzTypeEnum.getClazzTypeEnumByType(baseProerty.getType());
                            if (clazzType == null) {
                                throw new Exception("Can't found " + baseProerty.getType() +
                                                    " in the ClazzTypeEnum");
                            }

                            String clzzNameType = clazzType.getImportClazz();
                            baseProerty.setType(clzzNameType);

                            //imports
                            ClazzTypeEnum importClazzType = ClazzTypeEnum.isImportClazz(baseProerty.getType());
                            if (importClazzType != null) {
                                importsPath.add(importClazzType.getImportClazz());
                            }
                        }
                    }

                    //处理一般的POJO
                    if ((entity.getPojoList() != null) && (entity.getPojoList().size() > 0)) {
                        Iterator<EntityProperty> iter = entity.getPojoList().iterator();
                        while (iter.hasNext()) {
                            EntityProperty pojoProerty = iter.next();
                            Entity propertyE = fullEntityLookup.get(pojoProerty.getEjbName());
                            if (propertyE != null) {
                                //pojoProerty.setName(propertyE.getName());
                                pojoProerty.setType(propertyE.getPackagePath() + "." +
                                                    propertyE.getName());
                            } else {
                                pojoProerty.setType(pojoProerty.getType());
                                //不需要处理该类在
                                /*try {
                                    Class.forName(pojoProerty.getType());
                                    pojoProerty.setType(pojoProerty.getType());
                                } catch (ClassNotFoundException e) {
                                    throw new Exception(" Property:name=" + pojoProerty.getName() +
                                        " " + "type=" + pojoProerty.getType() + " is't exits");
                                }*/
                            }
                        }
                    }

                    //collectionList 集合类型
                    if ((entity.getCollectionList() != null) &&
                            (entity.getCollectionList().size() > 0)) {
                        Iterator<EntityProperty> iter = entity.getCollectionList().iterator();
                        while (iter.hasNext()) {
                            EntityProperty collProerty = iter.next();
                            Entity propertyE = fullEntityLookup.get(collProerty.getEjbName());
                            if (propertyE != null) {
                                collProerty.setEjbName(propertyE.getPackagePath() + "." +
                                                       propertyE.getName());
                            } else {
                                try {
                                    String clazzName = collProerty.getEjbName();
                                    ClazzTypeEnum baseTypeClaz = ClazzTypeEnum.isFilterType(collProerty.getEjbName());
                                    if (baseTypeClaz != null) {
                                        clazzName = "java.lang." + baseTypeClaz.getImportClazz();
                                    }

                                    Class.forName(clazzName);
                                } catch (ClassNotFoundException e) {
                                    throw new Exception(" Property:name=" + collProerty.getName() +
                                                        " " + "type=" + collProerty.getType() +
                                                        " is't exits");
                                }
                            }

                            ClazzTypeEnum importClazzType = ClazzTypeEnum.isImportClazz(collProerty.getType());
                            if (importClazzType != null) {
                                importsPath.add(importClazzType.getImportClazz());
                                if (importClazzType.getClzType().equals("List")) {
                                    importsPath.add("java.util.ArrayList");
                                }
                            }
                        }
                    }

                    entity.setImports(importsPath);
                    // 生成JAVA POJO 类
                    createPOJO(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
    }

    private void createPOJO(Entity entity) throws Exception {
        Map<String, Object> context = getContext();
        context.put("entity", entity);
        // Content
        String content = processTemplate(this.tplPOJO, context);

        // Write file
        File modelFile = new File(this.outputPath + "/" + entity.getName() + ".java");
        Map<String, Object> jalopySettings = new HashMap<String, Object>();

        jalopySettings.put("keepJavadoc", Boolean.TRUE);
        jalopySettings.put("desc", entity.getDesc());

        if (modelFile.exists()) {
            modelFile.delete();
            System.gc();
        }

        if (!modelFile.exists()) {
            writeFile(modelFile, content, this.author, jalopySettings);
        }
    }

    private String processTemplate(String name, Map<String, Object> context)
                            throws Exception {
        return FreeMarkerUtil.process(name, context);
    }

    private Map<String, Object> getContext() throws TemplateModelException {
        BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
        TemplateHashModel staticModels = wrapper.getStaticModels();
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("implDir", this.implDir);
        context.put("outputPath", this.outputPath);
        context.put("packagePath", this.packagePath);
        context.put("serviceBuilder", this);
        context.put("system", staticModels.get("java.lang.System"));
        context.put("tempMap", wrapper.wrap(new HashMap<String, Object>()));
        context.put("validator", staticModels.get("com.ai.tools.generator.util.Validator"));
        context.put("StringPool", staticModels.get("com.ai.tools.generator.util.StringPool"));
        context.put("autoAnalyzer", staticModels.get("com.ai.tools.generator.util.AutoAnalyzer"));

        return context;
    }

    public static void process(String[] args) throws Exception {
        POJOBuilder pojoBuilder = null;
        if (args.length >= 1) {
            String fileName = args[0];
            String implDir = args[1];
            pojoBuilder = new POJOBuilder(implDir, fileName, "");
        } else if (args.length == 0) {
            String fileName = System.getProperty("service.input.file");
            String implDir = System.getProperty("service.impl.dir");
            String testDir = System.getProperty("service.test.dir");
            pojoBuilder = new POJOBuilder(implDir, fileName, testDir);
        }

        if (pojoBuilder == null) {
            String msg = "Please set these required system properties. " + "Sample values are:\n" +
                         "\n" + "\t-Dservice.input.file=${service.file}\n" +
                         "\t-Dservice.tpl.pojo=" + TPL_ROOT + "pojo.ftl\n";
            System.out.println(msg);
        }
    }

    protected EntityProperty createProperty(Element property, List<EntityProperty> collectionList,
                                            List<EntityProperty> columnList,
                                            List<EntityProperty> pojoList,
                                            List<EntityProperty> baseList, String ejbName) {
        String propertyName = property.attributeValue("name");
        String propertyType = property.attributeValue("type");
        String collectionEntity = property.attributeValue("entity");
        String desc = property.attributeValue("desc");
        EntityProperty pro = new EntityProperty(propertyName, propertyType, desc, collectionEntity,
                                                "");
        if (propertyType.equals("Collection")) {
            collectionList.add(pro);
        } else if (propertyType.equals("List")) {
            collectionList.add(pro);
        } else if (propertyType.equals("java.util.Collection")) {
            collectionList.add(pro);
        } else if (propertyType.equals("java.util.List")) {
            collectionList.add(pro);
        } else if (ClazzTypeEnum.getClazzTypeEnumByType(propertyType) == null) {
            pojoList.add(pro); //java Pojo类型
        } else {
            baseList.add(pro); //一般类型
        }

        columnList.add(pro);
        return pro;
    }

    protected EntitySuperClazz createSuperclass(Element entityEl, String superclassElementName) {
        EntitySuperClazz superclass = null;
        if (entityEl != null) {
            String name = entityEl.attributeValue(superclassElementName);
            if (StringUtil.isNotEmpty(name)) {
                superclass = new EntitySuperClazz(name);
            }
        }

        return superclass;
    }

    //#############################################################################
    private static String getPackagePath(File file) {
        String fileName = StringUtil.replace(file.toString(), "\\", "/");

        int x = fileName.indexOf("src/");

        if (x == -1) {
            x = fileName.indexOf("test/");
        }

        int y = fileName.lastIndexOf("/");

        fileName = fileName.substring(x + 4, y);

        return StringUtil.replace(fileName, "/", ".");
    }

    public String getTestDir() {
        return testDir;
    }

    public void setTestDir(String testDir) {
        this.testDir = testDir;
    }

    public static void main(String[] args) {
        try {
            args    = new String[5];
            args[0] = "src/main/resources/service.xml";
            args[1] = System.getProperty("user.dir") + File.separator + "src/main/java";
            POJOBuilder.process(args);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
