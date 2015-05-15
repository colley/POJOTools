/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.ai.tools.generator.util;

import org.apache.commons.io.FileUtils;
import org.dom4j.Branch;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;


/**
 * <a href="XMLFormatter.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alan Zimmerman
 *
 */
public class XMLFormatter {
    public static final String INDENT = "\t";

    public static String fixProlog(String xml) {
        // LEP-1921
        if (xml != null) {
            char[] charArray = xml.toCharArray();

            for (int i = 0; i < charArray.length; i++) {
                if (charArray[i] == '<') {
                    if (i != 0) {
                        xml = xml.substring(i, xml.length());
                    }

                    break;
                }
            }
        }

        return xml;
    }

    public static String fromCompactSafe(String xml) {
        return StringUtil.replace(xml, "[$NEW_LINE$]", "\n");
    }

    public static String toCompactSafe(String xml) {
        return StringUtil.replace(xml, "\n", "[$NEW_LINE$]");
    }

    public static String toString(String xml) throws DocumentException, IOException {
        return toString(xml, INDENT);
    }

    public static String toString(String xml, String indent)
        throws DocumentException, IOException {
        SAXReader reader = new SAXReader();
        Document  doc = DocumentHelper.parseText(xml);
       // Document  doc = reader.read(new StringReader(xml));

        return toString(doc, indent);
    }

    public static String toString(Branch branch) throws IOException {
        return toString(branch, INDENT);
    }

    public static String toString(Branch branch, String indent)
        throws IOException {
        return toString(branch, INDENT, false);
    }

    public static String toString(Branch branch, String indent, boolean expandEmptyElements)
        throws IOException {
        ByteArrayMaker bam = new ByteArrayMaker();

        OutputFormat   format = OutputFormat.createPrettyPrint();

        format.setExpandEmptyElements(expandEmptyElements);
        format.setIndent(indent);
        format.setLineSeparator("\n");

        XMLWriter writer = new XMLWriter(bam, format);

        writer.write(branch);

        String content = bam.toString(StringPool.UTF8);

        // LEP-4257

        //content = StringUtil.replace(content, "\n\n\n", "\n\n");
        if (content.endsWith("\n\n")) {
            content = content.substring(0, content.length() - 2);
        }

        if (content.endsWith("\n")) {
            content = content.substring(0, content.length() - 1);
        }

        while (content.indexOf(" \n") != -1) {
            content = StringUtil.replace(content, " \n", "\n");
        }

        return content;
    }

    public static String formatXML(String xml) throws DocumentException, IOException {
        String doctype = null;
        int    x = xml.indexOf("<!DOCTYPE");
        if (x != -1) {
            int y = xml.indexOf(">", x) + 1;
            doctype = xml.substring(x, y);
            xml = xml.substring(0, x) + "\n" + xml.substring(y);
        }

        xml = StringUtil.replace(xml, '\r', "");
        try {
            xml = XMLFormatter.toString(xml);
        } catch (DocumentException de) {
            String errorFile = "DocumentException.xml";
            System.err.println("*** XML input causing error written to " + errorFile + " ***");
            FileUtil.write(new File(errorFile), xml, false);
            throw de;
        }

        xml = StringUtil.replace(xml, "\"/>", "\" />");
        if (Validator.isNotNull(doctype)) {
            x = xml.indexOf("?>") + 2;
            xml = xml.substring(0, x) + "\n" + doctype + xml.substring(x);
        }

        return xml;
    }
    
    public static void main(String[] args) {
    	try {
			String xml = FileUtils.readFileToString(new File("E://github/zj_java/POJOTools/DocumentException.xml"));
			xml = formatXML(xml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
