package com.dhcc.ftp.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dhcc.ftp.common.HibernateFactory;

/**
 * <p>
 * Title: ����Excel�������ݵ��뵽���ݿ���
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Company: ��������ɷݹ�˾������ҵ��
 * </p>
 * 
 * @author �����
 * 
 * @date july 22, 2011 3:29:33 PM
 * 
 * @version 1.0
 */
public class DataImportUtil {

	public DataImportUtil() {
	}

	/**
	 * ����Excel�������ݵ��뵽���ݿ��� ����ֶ�Ϊ�������ͣ����뽫excel��Ӧ�еĵ�Ԫ���ʽ����Ϊ�ı�����
	 * 
	 * @param excelFileName excel�ļ��ľ���·�������ļ���
	 * @param tableName ���ݱ���
	 * @param fieldsMap Map ����fields���ֶ�list���ֶ�˳����excel��ÿ�����Ӧ
	 *                          flag:�ֶζ�Ӧ��״̬��1�����ֶ�Ҫ�������ݿ⣬0�����������и��ݸ��ֶλ�ȡ��һ���ֶε�ֵ��-1��������
	 *                          property:����һ����Ҫ��õ��ֶ����ͱ���
	 * @param sequence �������� ��0��Ӧ�����������ֶΣ�1�Ǹ��ֶζ�Ӧ��sequence
	 * @param defaultField  ��Ҫ����Ĭ��ֵ���ֶ�
	 * @param defaultValue ��Ӧ��Ĭ��ֵ
	 */
	public Map<String, Object> importExcel(String excelFileName, String tableName, Map<String, Object> fieldsMap, String[] sequence, List defaultField, List defaultValue) {
		List<Object[]> errorList = new ArrayList<Object[]>();
		List<Object[]> rightList = new ArrayList<Object[]>();
		Map<String, Object> map = new HashMap<String, Object>();
		String[] fields = (String[])fieldsMap.get("fields");
		String[] flag = (String[])fieldsMap.get("flag");
		String[] property = (String[])fieldsMap.get("property");
		int rows = 0;
		try {
			InputStream fis = new FileInputStream(excelFileName);
			Workbook wb = null;
			String errorType = "";
			//excel�ļ�����Щxls�ļ���ʵ��html�ļ���Ҫ���м��
			try {
			    wb = Workbook.getWorkbook(fis);
			}catch (Exception e) {
				errorType = "1";
				System.out.println("errorType:"+errorType);
				e.printStackTrace();
			}
			
			if (!errorType.equals("1")) {
			Sheet sheet = wb.getSheet(0);
			rows = sheet.getRows();

			// ��ȡ���ݱ��Ӧ�ֶε��ֶ����͵���Ϣ
			String hsql = "SELECT COLUMN_NAME,DATA_TYPE,DATA_LENGTH,NULLABLE FROM dba_tab_cols where table_name='"
					+ tableName + "'";
			Session session = null;
			List tableInfoList = null;
			try {
				session = HibernateFactory.getSession();
				Query query = session.createSQLQuery(hsql);
				tableInfoList = query.list();
			} catch (Exception e) {
			}
			// ÿ��ѭ�����
			for (int m = 1; m < rows; m++) {
				//����Ƿ��п���
				String checkEmpty = "";
				for (int j = 0; j < fields.length; j++) {
					checkEmpty += sheet.getCell(j, m).getContents().trim();
				}
				if (checkEmpty.equals("")) {
					continue;
				}
				String errorQues = "";
				// ����������
				String hql = "insert into " + tableName + "(";
				if (sequence != null && sequence.length == 2) {
					hql += sequence[0] + ",";
				}
				for (int i = 0; i < fields.length; i++) {
					if (flag[i].equals("1")) {
						//���flag[i]=1�����ʾ���ֶ�Ҫ���뵽���ݱ���
						hql += fields[i] + ",";
					}else if (flag[i].equals("0")) {
						//���flag[i]=0�����property�л�ȡ���ֶζ�Ӧ����һ������Ҫ�������ݱ��е��ֶ�
						hql += property[0] + ",";
					}
				}
				for (int k = 0; defaultField !=null && k < defaultField.size(); k++) {
					hql += defaultField.get(k) + ",";
				}
				hql = hql.substring(0, hql.length() - 1) + ")values(";
				if (sequence != null && sequence.length == 2) {
					hql += "" + sequence[1] + ".nextval,";
				}
				String[] excelInfo = new String[fields.length+1];
				for (int j = 0; j < fields.length; j++) {
					String fieldValue = sheet.getCell(j, m).getContents().trim();
					String fieldName = sheet.getCell(j, 0).getContents().trim();
					excelInfo[j] = fieldValue;
					if (fieldValue.indexOf(",") != -1) {
						fieldValue = fieldValue.replace(",", "");
					}
					if (flag[j].equals("1")) {
					   // ������ݵ�׼ȷ��
						 for (int n = 0; tableInfoList != null && n < tableInfoList.size(); n++) {
						 Object[] o = (Object[]) tableInfoList.get(n);
						 // ������ݱ��и��ֶβ���Ϊ�����ж��Ƿ�Ϊ��
						 if (fields[j].equals(o[0]) && o[3].toString().equals("N")) {
							if (fieldValue.trim().equals("")) {
								errorQues += "���ݲ����ڣ�" + fieldName + "   ";
							}
						 }
						 // �ж��Ƿ�Ϊnumber����
						 if (fields[j].equals(o[0]) && o[1].toString().indexOf("NUMBER") != -1 && !fieldValue.equals("")) {
							if (!ImportExcelChecks.isNumberic(fieldValue)) {
								errorQues += "���Ͳ�ƥ�䣺" + fieldName + "��Ҫ�����֣�ʵ�ʣ��ַ�    ";
							}
						 }
						 // �ж��Ƿ�Ϊ��������
						 if (fields[j].equals(o[0]) && !fieldValue.equals("") && o[1].toString().equals("DATE")) {
							 fieldValue = fieldValue.replace("/", "-");
							 if (fieldValue.indexOf("-") == 2) {
								fieldValue = "20" + fieldValue;
							 }
							 System.out.println("fieldValue:"+fieldValue);
							 if (!ImportExcelChecks.isDate(fieldValue)) {
								errorQues = errorQues + "���Ͳ�ƥ�䣺" + fieldName + "��Ҫ���ʽΪ��XXXX-XX-XX  ";
							}
						 }
					  }
					  hql += "'" + fieldValue + "',";
					}
					//���flag[j]=0������ݸ��ֶ�ȥ��һ�����л�ȡ��Ӧ��ֵ
					if (flag[j].equals("0")) {
						String hsql1 = "SELECT "+property[0]+" FROM "+property[1]+" where "+fields[j]+" = '" + fieldValue + "'";
						System.out.println("hsql1:"+hsql1);
					    List specFieldList = null;
					    try {
						    session = HibernateFactory.getSession();
						    Query query = session.createSQLQuery(hsql1);
						    specFieldList = query.list();
						    for(int i=0;i<specFieldList.hashCode();i++){
						    	System.out.println("���������������������Listת�����ֵ��");
						    	System.out.println(specFieldList.get(i));
						    }
					    } catch (Exception e) {  }
					    if (specFieldList.size() == 0) {
					    	errorQues = errorQues + "" + fieldName + " ��" + fieldValue + " �ڱ���"+property[1]+"������  ";
					    }else {
					        fieldValue = specFieldList.get(0).toString();
					    }
					    hql += "'" + fieldValue + "',";
					}
				}
				for (int k = 0; defaultValue != null && k < defaultValue.size(); k++) {
					hql += defaultValue.get(k) + ",";
				}
				if (!errorQues.equals("")) {
					excelInfo[fields.length] = errorQues;
					errorList.add(excelInfo);
					continue;
				}
				hql = hql.substring(0, hql.length() - 1) + ")";
				System.out.println("hql:"+hql);
				session = HibernateFactory.getSession();
				Transaction tx = session.beginTransaction();
				int InsertEntities = session.createSQLQuery(hql).executeUpdate();
				tx.commit();
				rightList.add(excelInfo);
			}
			wb.close();
			map.put("error", errorList);
			map.put("right", rightList);
			}else {
				map.put("errorType", "excel�ļ��������飡");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	public Map<String, Object> importExcelbByRow(String excelFileName, String tableName, Map<String, Object> fieldsMap, String[] sequence, List defaultField, List defaultValue,int begin,int space) {
		List<Object[]> errorList = new ArrayList<Object[]>();
		List<Object[]> rightList = new ArrayList<Object[]>();
		Map<String, Object> map = new HashMap<String, Object>();
		String[] fields = (String[])fieldsMap.get("fields");
		String[] flag = (String[])fieldsMap.get("flag");
		String[] property = (String[])fieldsMap.get("property");
		int rows = 0;
		try {
			InputStream fis = new FileInputStream(excelFileName);
			Workbook wb = null;
			String errorType = "";
			//excel�ļ�����Щxls�ļ���ʵ��html�ļ���Ҫ���м��
			try {
			    wb = Workbook.getWorkbook(fis);
			}catch (Exception e) {
				errorType = "1";
				System.out.println("errorType:"+errorType);
				e.printStackTrace();
			}
			
			if (!errorType.equals("1")) {
			Sheet sheet = wb.getSheet(0);
			rows = sheet.getRows();

			// ��ȡ���ݱ��Ӧ�ֶε��ֶ����͵���Ϣ
			String hsql = "SELECT COLUMN_NAME,DATA_TYPE,DATA_LENGTH,NULLABLE FROM dba_tab_cols where table_name='"
					+ tableName + "'";
			Session session = null;
			List tableInfoList = null;
			try {
				session = HibernateFactory.getSession();
				Query query = session.createSQLQuery(hsql);
				tableInfoList = query.list();
			} catch (Exception e) {
			}
			// ÿ��ѭ�����
			for (int m = begin-1; m < rows-space; m++) {
				//����Ƿ��п���
				String checkEmpty = "";
				for (int j = 0; j < fields.length; j++) {
					checkEmpty += sheet.getCell(j, m).getContents().trim();
				}
				if (checkEmpty.equals("")) {
					continue;
				}
				String errorQues = "";
				// ����������
				String hql = "insert into " + tableName + "(";
				if (sequence != null && sequence.length == 2) {
					hql += sequence[0] + ",";
				}
				for (int i = 0; i < fields.length; i++) {
					if (flag[i].equals("1")) {
						//���flag[i]=1�����ʾ���ֶ�Ҫ���뵽���ݱ���
						hql += fields[i] + ",";
					}else if (flag[i].equals("0")) {
						//���flag[i]=0�����property�л�ȡ���ֶζ�Ӧ����һ������Ҫ�������ݱ��е��ֶ�
						hql += property[0] + ",";
					}
				}
				for (int k = 0; defaultField !=null && k < defaultField.size(); k++) {
					hql += defaultField.get(k) + ",";
				}
				hql = hql.substring(0, hql.length() - 1) + ")values(";
				if (sequence != null && sequence.length == 2) {
					hql += "" + sequence[1] + ".nextval,";
				}
				String[] excelInfo = new String[fields.length+1];
				for (int j = 0; j < fields.length; j++) {
					String fieldValue = sheet.getCell(j, m).getContents().trim();
					String fieldName = sheet.getCell(j, 0).getContents().trim();
					excelInfo[j] = fieldValue;
					if (fieldValue.indexOf(",") != -1) {
						fieldValue = fieldValue.replace(",", "");
					}
					if (flag[j].equals("1")) {
					   // ������ݵ�׼ȷ��
						 for (int n = 0; tableInfoList != null && n < tableInfoList.size(); n++) {
						 Object[] o = (Object[]) tableInfoList.get(n);
						 // ������ݱ��и��ֶβ���Ϊ�����ж��Ƿ�Ϊ��
						 if (fields[j].equals(o[0]) && o[3].toString().equals("N")) {
							if (fieldValue.trim().equals("")) {
								errorQues += "���ݲ����ڣ�" + fieldName + "   ";
							}
						 }
						 // �ж��Ƿ�Ϊnumber����
						 if (fields[j].equals(o[0]) && o[1].toString().indexOf("NUMBER") != -1 && !fieldValue.equals("")) {
							if (!ImportExcelChecks.isNumberic(fieldValue)) {
								errorQues += "���Ͳ�ƥ�䣺" + fieldName + "��Ҫ�����֣�ʵ�ʣ��ַ�    ";
							}
						 }
						 // �ж��Ƿ�Ϊ��������
						 if (fields[j].equals(o[0]) && !fieldValue.equals("") && o[1].toString().equals("DATE")) {
							 fieldValue = fieldValue.replace("/", "-");
							 if (fieldValue.indexOf("-") == 2) {
								fieldValue = "20" + fieldValue;
							 }
							 System.out.println("fieldValue:"+fieldValue);
							 if (!ImportExcelChecks.isDate(fieldValue)) {
								errorQues = errorQues + "���Ͳ�ƥ�䣺" + fieldName + "��Ҫ���ʽΪ��XXXX-XX-XX  ";
							}
						 }
					  }
					  hql += "'" + fieldValue + "',";
					}
					//���flag[j]=0������ݸ��ֶ�ȥ��һ�����л�ȡ��Ӧ��ֵ
					if (flag[j].equals("0")) {
						String hsql1 = "SELECT "+property[0]+" FROM "+property[1]+" where "+fields[j]+" = '" + fieldValue + "'";
						System.out.println("hsql1:"+hsql1);
					    List specFieldList = null;
					    try {
						    session = HibernateFactory.getSession();
						    Query query = session.createSQLQuery(hsql1);
						    specFieldList = query.list();
						    for(int i=0;i<specFieldList.hashCode();i++){
						    	System.out.println("���������������������Listת�����ֵ��");
						    	System.out.println(specFieldList.get(i));
						    }
					    } catch (Exception e) {  }
					    if (specFieldList.size() == 0) {
					    	errorQues = errorQues + "" + fieldName + " ��" + fieldValue + " �ڱ���"+property[1]+"������  ";
					    }else {
					        fieldValue = specFieldList.get(0).toString();
					    }
					    hql += "'" + fieldValue + "',";
					}
				}
				for (int k = 0; defaultValue != null && k < defaultValue.size(); k++) {
					hql += "'" +defaultValue.get(k) + "',";
				}
				if (!errorQues.equals("")) {
					excelInfo[fields.length] = errorQues;
					errorList.add(excelInfo);
					continue;
				}
				hql = hql.substring(0, hql.length() - 1) + ")";
				System.out.println("hql:"+hql);
				session = HibernateFactory.getSession();
				Transaction tx = session.beginTransaction();
				int InsertEntities = session.createSQLQuery(hql).executeUpdate();
				tx.commit();
				rightList.add(excelInfo);
			}
			wb.close();
			map.put("error", errorList);
			map.put("right", rightList);
			}else {
				map.put("errorType", "excel�ļ��������飡");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
}
