/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.dotmatrix;

import java.io.FileInputStream;

import javax.swing.JOptionPane;


/**
 * @User: mayc
 * @Date: 2014年8月6日
 * @Time: 下午2:26:36
 * @version $Revision$ $Date$
 */
public class TestFont {
    public static void main(String[] args) {
    	String str = "中";

		char[] ch = str.toCharArray();
		byte[][] asc = new byte[ch.length][16];
		try {
			for (int i = 0; i < ch.length; i++) {
				String path = TestDot.class.getResource("x16x16/HZK16S").getPath();
				FileInputStream file = new FileInputStream(path);
				int off = ch[i] * 16;
				file.skip(off);
				file.read(asc[i]);
				file.close();
			}
			for (int i = 0; i < 16; i++) {
				for (int id = 0; id < ch.length; id++) {
					for (int j = 0; j < 8; j++) {
						int bit = (asc[id][i] & (0x80 >> j)) >> (7 - j);
						if (bit == 1) {
							System.out.print("＊");
						} else {
							System.out.print("　");
						}
					}
				}

				System.out.println();
			}


		} catch (Exception e1) {
			e1.printStackTrace();
		}
    }
}
