/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.HashSet;
import java.util.Set;


/**
 * TODO
 * @User: mayc
 * @version $Revision$ $Date$
  */
public class SetUtil {
    public static Set<Long> fromArray(long[] array) {
        if ((array == null) || (array.length == 0)) {
            return new HashSet();
        }

        Set<Long> set = new HashSet<Long>(array.length);

        for (int i = 0; i < array.length; i++) {
            set.add(array[i]);
        }

        return set;
    }

    public static Set fromArray(Object[] array) {
        if ((array == null) || (array.length == 0)) {
            return new HashSet();
        }

        Set set = new HashSet(array.length);

        for (int i = 0; i < array.length; i++) {
            set.add(array[i]);
        }

        return set;
    }

    public static Set fromFile(String fileName) throws IOException {
        return fromFile(new File(fileName));
    }

    public static Set fromFile(File file) throws IOException {
        Set set = new HashSet();

        BufferedReader br = new BufferedReader(new FileReader(file));

        String s = StringPool.BLANK;

        while ((s = br.readLine()) != null) {
            set.add(s);
        }

        br.close();

        return set;
    }

    public static Set fromString(String s) {
        return fromArray(StringUtil.split(s, StringPool.NEW_LINE));
    }
}
