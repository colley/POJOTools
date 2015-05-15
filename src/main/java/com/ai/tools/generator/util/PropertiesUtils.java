package com.ai.tools.generator.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * @User: mayc
 * @Date: 2015年1月19日
 * @Time: 下午5:13:13
 * @version $Revision$ $Date$
 */
public class PropertiesUtils {

	private static Properties props = new Properties();
	static{
		try {
			String ppath = System.getProperty("user.dir")
					+ System.getProperty("file.separator")
					+ "tools/init.properties";
			InputStream input = new java.io.FileInputStream(ppath);
			props.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String key){
		return props.getProperty(key);
	}
}


