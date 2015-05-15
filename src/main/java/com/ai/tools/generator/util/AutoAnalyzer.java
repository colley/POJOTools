/*
 * Copyright (c) 2012-2014 by Asiainfo Linkage
 * All rights reserved.
 * $Id$
 */
package com.ai.tools.generator.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;


/**
 * Java 类名分词
 * @User: mayc
 * @version $Revision$ $Date$
  */
public final class AutoAnalyzer {
    private static Analyzer analyzer = new SimpleAnalyzer(Version.LUCENE_36);

    /**
     * 智能分词
     * @param keyWorld
     * @return
     * @throws IOException
     */
    public static String analyzerWorld(String keyWorld) {
        String            retKeyWorld = keyWorld;
        StringBuilder     sb = new StringBuilder("");
        Reader            r = new StringReader(keyWorld);
        TokenStream       tokenStream = analyzer.tokenStream("", r);
        CharTermAttribute termAtt = tokenStream.addAttribute(CharTermAttribute.class);
        try {
            while (tokenStream.incrementToken()) {
                String world = termAtt.toString();
                sb.append(world.substring(0, 1).toUpperCase() + world.substring(1, world.length()));
            }

            if (StringUtil.isNotEmpty(sb.toString())) {
                retKeyWorld = sb.toString();
            }
        } catch (IOException e) {
            retKeyWorld = keyWorld;
        }

        System.out.println(retKeyWorld);
        return retKeyWorld.concat(StringPool.DTO);
    }
    
    public static String analyzerPropName(String keyWorld) {
        String            retKeyWorld = keyWorld;
        StringBuilder     sb = new StringBuilder("");
        Reader            r = new StringReader(keyWorld);
        TokenStream       tokenStream = analyzer.tokenStream("", r);
        CharTermAttribute termAtt = tokenStream.addAttribute(CharTermAttribute.class);
        try {
            while (tokenStream.incrementToken()) {
                String world = termAtt.toString();
                sb.append(world.substring(0, 1).toUpperCase() + world.substring(1, world.length()));
            }

            if (StringUtil.isNotEmpty(sb.toString())) {
                retKeyWorld = sb.toString();
            }
        } catch (IOException e) {
            retKeyWorld = keyWorld;
        }
        retKeyWorld =  retKeyWorld.substring(0, 1).toLowerCase()+ retKeyWorld.substring(1, retKeyWorld.length());
        System.out.println(retKeyWorld);
        return retKeyWorld;
    }

    public static void main(String[] args) {
        try {
        	analyzerPropName("MARKET_KIND_OFFERS");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
