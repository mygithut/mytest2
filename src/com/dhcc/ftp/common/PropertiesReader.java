package com.dhcc.ftp.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dhcc.ftp.dao.DaoFactory;

// 读取 properties
public class PropertiesReader {
	private static final Logger logger=Logger.getLogger(DaoFactory.class);
	private static final Map<String, Object> properties = new Hashtable<String, Object>();
	static {
		initBean("/beans-dao.properties");
		initBean("/beans-bo.properties");
	}

	/**
	 * 读取指定的配置文件，生成其中配置的bean的实例，并且放到 <code>beans</code> 中
	 * 
	 * @param cfgFile
	 */
	static void initBean(String cfgFile) {
		// 加载配置文件 Properties props
		Properties props = loadProperties(cfgFile);
		// 循环props, 生成配置的bean的实例
		for (Object obj : props.keySet()) {
			String key = (String) obj;
			String className = props.getProperty(key);
			try {
				Object bean = Class.forName(className).newInstance();
				properties.put(key, bean);
			} catch (Exception e) {
				throw new RuntimeException("初始化【className=" + className + "】失败", e);
			}
		}
	}
	
	/**
	 * 读取指定的配置文件，生成其中配置的bean的实例，并且放到 <code>beans</code> 中
	 * 
	 * @param cfgFile
	 */
	static void initStr(String cfgFile) {
		// 加载配置文件 Properties props
		Properties props = loadProperties(cfgFile);

		// 循环props, 生成配置的查询字符串
		for (Object obj : props.keySet()) {
			String key = (String) obj;
			try {
				properties.put(key, props.getProperty(key));
			} catch (Exception e) {
				throw new RuntimeException("初始化【className=" + key + "】失败", e);
			}
		}
	}
	
	

	private static Properties loadProperties(String cfgFile) {
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = PropertiesReader.class.getResourceAsStream(cfgFile);
			props.load(in);
		} catch (IOException e) {
			throw new RuntimeException("加载配置文件失败：" + cfgFile, e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return props;
	}

	/**
	 * 获取相应的Bean的实例
	 * 
	 * @param key
	 * @return
	 */
	public static Object getPorperty(String key) {
		return properties.get(key);
	}

	// 写完后运行
	public static void main(String[] args) {
		logger.info(PropertiesReader.getPorperty("brInfoBo"));
		// InputStream in = BeanFactory.class.getResourceAsStream("/beans.properties");
		// System.out.println(in);
	}
}
