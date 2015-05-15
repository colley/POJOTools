/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;


/**
 * TODO
 * @User: mayc
 * @version $Revision$ $Date$
  */
public class FileUtil {
    public static boolean exists(String fileName) {
        return exists(new File(fileName));
    }

    public static boolean exists(File file) {
        return file.exists();
    }

    public static void mkdirs(String pathName) {
        File file = new File(pathName);

        file.mkdirs();
    }

    public static String read(String fileName) throws IOException {
        return read(new File(fileName));
    }

    public static String read(File file) throws IOException {
        return read(file, false);
    }

    public static String read(File file, boolean raw) throws IOException {
        FileInputStream fis = new FileInputStream(file);

        byte[]          bytes = new byte[fis.available()];

        fis.read(bytes);

        fis.close();

        String s = new String(bytes, StringPool.UTF8);

        if (raw) {
            return s;
        } else {
            return StringUtil.replace(s, StringPool.RETURN_NEW_LINE, StringPool.NEW_LINE);
        }
    }

    public static void write(File file, String s, boolean lazy)
        throws IOException {
        write(file, s, lazy, false);
    }

    public static void write(File file, String s, boolean lazy, boolean append)
        throws IOException {
        if (file.getParent() != null) {
            mkdirs(file.getParent());
        }

        if (lazy && file.exists()) {
            String content = read(file);

            if (content.equals(s)) {
                return;
            }
        }

        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, append), StringPool.UTF8));

        bw.write(s);

        bw.close();
    }
}
