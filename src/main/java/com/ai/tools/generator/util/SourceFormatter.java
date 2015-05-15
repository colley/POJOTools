/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


/**
 * TODO
 * @User: mayc
 * @version $Revision$ $Date$
  */
public class SourceFormatter {
    public static String stripImports(String content, String packageDir, String className)
        throws IOException {
        int x = content.indexOf("import ");

        if (x == -1) {
            return content;
        }

        int y = content.indexOf("{", x);

        y = content.substring(0, y).lastIndexOf(";") + 1;

        String imports = _formatImports(content.substring(x, y));

        content = content.substring(0, x) + imports + content.substring(y + 1, content.length());

        Set<String> classes = ClassUtil.getClasses(new StringReader(content), className);

        classes.add("_getMarkup");
        classes.add("_performBlockingInteraction");

        x = content.indexOf("import ");

        y = content.indexOf("{", x);

        y = content.substring(0, y).lastIndexOf(";") + 1;

        imports = content.substring(x, y);

        StringBuilder sb = new StringBuilder();

        BufferedReader br = new BufferedReader(new StringReader(imports));

        String line = null;

        while ((line = br.readLine()) != null) {
            if (line.indexOf("import ") != -1) {
                int importX = line.indexOf(" ");
                int importY = line.lastIndexOf(".");

                String importPackage = line.substring(importX + 1, importY);
                String importClass = line.substring(importY + 1, line.length() - 1);

                if (!packageDir.equals(importPackage)) {
                    if (!importClass.equals("*")) {
                        if (classes.contains(importClass)) {
                            sb.append(line);
                            sb.append("\n");
                        }
                    } else {
                        sb.append(line);
                        sb.append("\n");
                    }
                }
            }
        }

        imports = _formatImports(sb.toString());

        content = content.substring(0, x) + imports + content.substring(y + 1, content.length());

        return content;
    }

    public static String _formatImports(String imports)
        throws IOException {
        if ((imports.indexOf("/*") != -1) || (imports.indexOf("*/") != -1) ||
                (imports.indexOf("//") != -1)) {
            return imports + "\n";
        }

        List<String> importsList = new ArrayList<String>();

        BufferedReader br = new BufferedReader(new StringReader(imports));

        String line = null;

        while ((line = br.readLine()) != null) {
            if (line.indexOf("import ") != -1) {
                if (!importsList.contains(line)) {
                    importsList.add(line);
                }
            }
        }

        Collections.sort(importsList);

        StringBuilder sb = new StringBuilder();

        String        temp = null;

        for (int i = 0; i < importsList.size(); i++) {
            String s = importsList.get(i);

            int    pos = s.indexOf(".");

            pos = s.indexOf(".", pos + 1);

            if (pos == -1) {
                pos = s.indexOf(".");
            }

            String packageLevel = s.substring(7, pos);

            if ((i != 0) && (!packageLevel.equals(temp))) {
                sb.append("\n");
            }

            temp = packageLevel;

            sb.append(s);
            sb.append("\n");
        }

        return sb.toString();
    }

    public static String encodeReservedWords(String content) {
        String finalStr = content;

        // question marks
        finalStr = finalStr.replaceAll("\\?", "qqmmaarrkk");

        return finalStr;
    }

    public static String decodeReservedWords(String content) {
        String finalStr = content;

        // question marks
        finalStr = finalStr.replaceAll("qqmmaarrkk", "?");

        return finalStr;
    }
}
