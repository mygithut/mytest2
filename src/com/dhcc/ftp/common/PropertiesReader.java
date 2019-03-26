package com.dhcc.ftp.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.dhcc.ftp.dao.DaoFactory;

// ��ȡ properties
public class PropertiesReader {
	private static final Logger logger=Logger.getLogger(DaoFactory.class);
	private static final Map<String, Object> properties = new Hashtable<String, Object>();
	static {
		initBean("/beans-dao.properties");
		initBean("/beans-bo.properties");
	}

	/**
	 * ��ȡָ���������ļ��������������õ�bean��ʵ�������ҷŵ� <code>beans</code> ��
	 * 
	 * @param cfgFile
	 */
	static void initBean(String cfgFile) {
		// ���������ļ� Properties props
		Properties props = loadProperties(cfgFile);
		// ѭ��props, �������õ�bean��ʵ��
		for (Object obj : props.keySet()) {
			String key = (String) obj;
			String className = props.getProperty(key);
			try {
				Object bean = Class.forName(className).newInstance();
				properties.put(key, bean);
			} catch (Exception e) {
				throw new RuntimeException("��ʼ����className=" + className + "��ʧ��", e);
			}
		}
	}
	
	/**
	 * ��ȡָ���������ļ��������������õ�bean��ʵ�������ҷŵ� <code>beans</code> ��
	 * 
	 * @param cfgFile
	 */
	static void initStr(String cfgFile) {
		// ���������ļ� Properties props
		Properties props = loadProperties(cfgFile);

		// ѭ��props, �������õĲ�ѯ�ַ���
		for (Object obj : props.keySet()) {
			String key = (String) obj;
			try {
				properties.put(key, props.getProperty(key));
			} catch (Exception e) {
				throw new RuntimeException("��ʼ����className=" + key + "��ʧ��", e);
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
			throw new RuntimeException("���������ļ�ʧ�ܣ�" + cfgFile, e);
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
	 * ��ȡ��Ӧ��Bean��ʵ��
	 * 
	 * @param key
	 * @return
	 */
	public static Object getPorperty(String key) {
		return properties.get(key);
	}

	// д�������
	public static void main(String[] args) {
		logger.info(PropertiesReader.getPorperty("brInfoBo"));
		// InputStream in = BeanFactory.class.getResourceAsStream("/beans.properties");
		// System.out.println(in);
	}
}
