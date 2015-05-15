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

import org.dom4j.Document;
import org.dom4j.DocumentException;

import org.dom4j.io.SAXReader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.net.URL;


/**
 * <a href="DocumentUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class DocumentUtil {
    public static Document readDocumentFromFile(File file)
        throws DocumentException {
        return readDocumentFromFile(file, false);
    }

    public static Document readDocumentFromFile(File file, boolean validate)
        throws DocumentException {
        SAXReader reader = SAXReaderFactory.getInstance(validate);

        return reader.read(file);
    }

    public static Document readDocumentFromStream(InputStream is)
        throws DocumentException {
        return readDocumentFromStream(is, false);
    }

    public static Document readDocumentFromStream(InputStream is, boolean validate)
        throws DocumentException {
        SAXReader reader = SAXReaderFactory.getInstance(validate);

        return reader.read(is);
    }

    public static Document readDocumentFromURL(String url)
        throws DocumentException, IOException {
        return readDocumentFromURL(url, false);
    }

    public static Document readDocumentFromURL(String url, boolean validate)
        throws DocumentException, IOException {
        SAXReader reader = SAXReaderFactory.getInstance(validate);

        return reader.read(new URL(url));
    }

    public static Document readDocumentFromXML(String xml)
        throws DocumentException {
        return readDocumentFromXML(xml, false);
    }

    public static Document readDocumentFromXML(String xml, boolean validate)
        throws DocumentException {
        SAXReader reader = SAXReaderFactory.getInstance(validate);

        return reader.read(new XMLSafeReader(xml));
    }
}
