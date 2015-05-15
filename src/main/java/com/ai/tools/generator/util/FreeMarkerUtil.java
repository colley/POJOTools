/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator.util;

import freemarker.cache.ClassTemplateLoader;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

import java.io.StringWriter;
import java.io.Writer;


/**
 * TODO
 * @User: mayc
 * @version $Revision$ $Date$
  */
public class FreeMarkerUtil {
    public static String process(String name, Object context)
        throws Exception {
        StringWriter writer = new StringWriter();

        process(name, context, writer);

        return writer.toString();
    }

    public static void process(String name, Object context, Writer writer)
        throws Exception {
        Template template = _getConfiguration().getTemplate(name);

        template.process(context, writer);
    }

    private static Configuration _getConfiguration() {
        if (_configuration == null) {
            _configuration = new Configuration();

            _configuration.setObjectWrapper(new DefaultObjectWrapper());
            _configuration.setTemplateLoader(new ClassTemplateLoader(FreeMarkerUtil.class, "/"));
            _configuration.setClassForTemplateLoading(FreeMarkerUtil.class, "/");
        }

        return _configuration;
    }

    private static Configuration _configuration;
}
