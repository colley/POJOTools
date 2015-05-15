/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.dotmatrix;


/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;


/**
 * TODO
 * @User: mayc
 * @version $Revision$ $Date$
  */
public class TestDot {
    private int[] unit = new int[32];
    private String yes = "*";
    private String no = " ";
    private PrintStream print = null;

    public void setYes(String str) {
        yes = str;
    }

    public void setNo(String str) {
        no = str;
    }

    private int negativeToPlus(byte b) {
        return b & 0xFF;
    }

    public void setOutputFile(String filename)
        throws Exception {
        print = new PrintStream(filename);
    }

    public void colseFileStream() {
        print.close();
    }

    private void readChina(char ch) { //需要得到点阵的汉字
        byte[] buf = new byte[32];
        FileInputStream input = null;
        try {
            String string = Character.toString(ch);
            byte[] bt = string.getBytes("GBK"); //获得国标码
            int a1 = negativeToPlus(bt[0]); //转为无符号整数
            int a2 = negativeToPlus(bt[1]);
            int qh = a1 - 0xA0; //得到区位码
            int wh = a2 - 0xA0;
            long offset = ((94 * (qh - 1)) + (wh - 1)) * 32; //获得偏移量
            String path = TestDot.class.getResource("x16x16/HZK16S").getPath();
            File file = new File(path);
            input = new FileInputStream(file);
            input.skip(offset);
            input.read(buf, 0, 32);
            for (int i = 0; i < 32; i++) {
                unit[i] = negativeToPlus(buf[i]);
            }

            input.close();
        } catch (Exception e) {
            System.out.println("文件异常");
            e.printStackTrace();
        }
    }

    public void writeChina(char ch) { //参数: yes是有点处显示   no是无点处显示
        int i;
        int j;
        int k;
        readChina(ch);
        for (j = 0; j < 16; j++) {
            for (i = 0; i < 2; i++) {
                for (k = 0; k < 8; k++) {
                    if ((unit[(j * 2) + i] & (0x80 >> k)) >= 1) //取bit位值
                     {
                        System.out.print(yes);
                    } else {
                        System.out.print(no);
                    }
                }
                
            }

            System.out.println();
        }
    }

    public void writeChinaToFile(String str) {
        try {
            int i;
            int j;
            int k;
            for (int x = 0; x < str.length(); x++) {
                readChina(str.charAt(x));
                for (j = 0; j < 16; j++) {
                    for (i = 0; i < 2; i++) {
                        for (k = 0; k < 8; k++) {
                            if ((unit[(j * 2) + i] & (0x80 >> k)) >= 1) //取bit位值
                             {
                                System.out.print(yes);
                            } else {
                            	System.out.print(no);
                            }
                        }
                    }

                    System.out.println(); 
                }
                System.out.println(); 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void main(String[] str)
        throws Exception {
        TestDot t = new TestDot();
        String xp = "卓股";

        t.setOutputFile("123.txt"); //确定输出 文件流
        t.writeChinaToFile(xp); //要写的字符串
        t.colseFileStream(); //关闭流
    }
}
