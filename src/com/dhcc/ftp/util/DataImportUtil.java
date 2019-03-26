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
 * Title: 解析Excel，将数据导入到数据库中
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Company: 东华软件股份公司金融事业部
 * </p>
 * 
 * @author 孙红玉
 * 
 * @date july 22, 2011 3:29:33 PM
 * 
 * @version 1.0
 */
public class DataImportUtil {

	public DataImportUtil() {
	}

	/**
	 * 解析Excel，将数据导入到数据库中 如果字段为日期类型，必须将excel对应行的单元格格式设置为文本类型
	 * 
	 * @param excelFileName excel文件的绝对路径，含文件名
	 * @param tableName 数据表名
	 * @param fieldsMap Map 包括fields：字段list，字段顺序与excel的每行相对应
	 *                          flag:字段对应的状态，1：该字段要导入数据库，0：从其他表中根据该字段获取另一个字段的值，-1：不导入
	 *                          property:从另一个表要获得的字段名和表名
	 * @param sequence 自增序列 ，0对应的是自增的字段，1是该字段对应的sequence
	 * @param defaultField  需要加入默认值的字段
	 * @param defaultValue 对应的默认值
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
			//excel文件，有些xls文件其实是html文件，要进行检查
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

			// 获取数据表对应字段的字段类型等信息
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
			// 每行循环添加
			for (int m = 1; m < rows; m++) {
				//检查是否有空行
				String checkEmpty = "";
				for (int j = 0; j < fields.length; j++) {
					checkEmpty += sheet.getCell(j, m).getContents().trim();
				}
				if (checkEmpty.equals("")) {
					continue;
				}
				String errorQues = "";
				// 构造添加语句
				String hql = "insert into " + tableName + "(";
				if (sequence != null && sequence.length == 2) {
					hql += sequence[0] + ",";
				}
				for (int i = 0; i < fields.length; i++) {
					if (flag[i].equals("1")) {
						//如果flag[i]=1，则表示该字段要导入到数据表中
						hql += fields[i] + ",";
					}else if (flag[i].equals("0")) {
						//如果flag[i]=0，则从property中获取该字段对应的另一个表中要导入数据表中的字段
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
					   // 检查数据的准确性
						 for (int n = 0; tableInfoList != null && n < tableInfoList.size(); n++) {
						 Object[] o = (Object[]) tableInfoList.get(n);
						 // 如果数据表中该字段不可为空则判断是否为空
						 if (fields[j].equals(o[0]) && o[3].toString().equals("N")) {
							if (fieldValue.trim().equals("")) {
								errorQues += "数据不存在：" + fieldName + "   ";
							}
						 }
						 // 判断是否为number类型
						 if (fields[j].equals(o[0]) && o[1].toString().indexOf("NUMBER") != -1 && !fieldValue.equals("")) {
							if (!ImportExcelChecks.isNumberic(fieldValue)) {
								errorQues += "类型不匹配：" + fieldName + "，要求：数字，实际：字符    ";
							}
						 }
						 // 判断是否为日期类型
						 if (fields[j].equals(o[0]) && !fieldValue.equals("") && o[1].toString().equals("DATE")) {
							 fieldValue = fieldValue.replace("/", "-");
							 if (fieldValue.indexOf("-") == 2) {
								fieldValue = "20" + fieldValue;
							 }
							 System.out.println("fieldValue:"+fieldValue);
							 if (!ImportExcelChecks.isDate(fieldValue)) {
								errorQues = errorQues + "类型不匹配：" + fieldName + "，要求格式为：XXXX-XX-XX  ";
							}
						 }
					  }
					  hql += "'" + fieldValue + "',";
					}
					//如果flag[j]=0，则根据该字段去另一个表中获取相应的值
					if (flag[j].equals("0")) {
						String hsql1 = "SELECT "+property[0]+" FROM "+property[1]+" where "+fields[j]+" = '" + fieldValue + "'";
						System.out.println("hsql1:"+hsql1);
					    List specFieldList = null;
					    try {
						    session = HibernateFactory.getSession();
						    Query query = session.createSQLQuery(hsql1);
						    specFieldList = query.list();
						    for(int i=0;i<specFieldList.hashCode();i++){
						    	System.out.println("！！！！！这里输出的是List转换后的值：");
						    	System.out.println(specFieldList.get(i));
						    }
					    } catch (Exception e) {  }
					    if (specFieldList.size() == 0) {
					    	errorQues = errorQues + "" + fieldName + " ：" + fieldValue + " 在表中"+property[1]+"不存在  ";
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
				map.put("errorType", "excel文件有误，请检查！");
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
			//excel文件，有些xls文件其实是html文件，要进行检查
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

			// 获取数据表对应字段的字段类型等信息
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
			// 每行循环添加
			for (int m = begin-1; m < rows-space; m++) {
				//检查是否有空行
				String checkEmpty = "";
				for (int j = 0; j < fields.length; j++) {
					checkEmpty += sheet.getCell(j, m).getContents().trim();
				}
				if (checkEmpty.equals("")) {
					continue;
				}
				String errorQues = "";
				// 构造添加语句
				String hql = "insert into " + tableName + "(";
				if (sequence != null && sequence.length == 2) {
					hql += sequence[0] + ",";
				}
				for (int i = 0; i < fields.length; i++) {
					if (flag[i].equals("1")) {
						//如果flag[i]=1，则表示该字段要导入到数据表中
						hql += fields[i] + ",";
					}else if (flag[i].equals("0")) {
						//如果flag[i]=0，则从property中获取该字段对应的另一个表中要导入数据表中的字段
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
					   // 检查数据的准确性
						 for (int n = 0; tableInfoList != null && n < tableInfoList.size(); n++) {
						 Object[] o = (Object[]) tableInfoList.get(n);
						 // 如果数据表中该字段不可为空则判断是否为空
						 if (fields[j].equals(o[0]) && o[3].toString().equals("N")) {
							if (fieldValue.trim().equals("")) {
								errorQues += "数据不存在：" + fieldName + "   ";
							}
						 }
						 // 判断是否为number类型
						 if (fields[j].equals(o[0]) && o[1].toString().indexOf("NUMBER") != -1 && !fieldValue.equals("")) {
							if (!ImportExcelChecks.isNumberic(fieldValue)) {
								errorQues += "类型不匹配：" + fieldName + "，要求：数字，实际：字符    ";
							}
						 }
						 // 判断是否为日期类型
						 if (fields[j].equals(o[0]) && !fieldValue.equals("") && o[1].toString().equals("DATE")) {
							 fieldValue = fieldValue.replace("/", "-");
							 if (fieldValue.indexOf("-") == 2) {
								fieldValue = "20" + fieldValue;
							 }
							 System.out.println("fieldValue:"+fieldValue);
							 if (!ImportExcelChecks.isDate(fieldValue)) {
								errorQues = errorQues + "类型不匹配：" + fieldName + "，要求格式为：XXXX-XX-XX  ";
							}
						 }
					  }
					  hql += "'" + fieldValue + "',";
					}
					//如果flag[j]=0，则根据该字段去另一个表中获取相应的值
					if (flag[j].equals("0")) {
						String hsql1 = "SELECT "+property[0]+" FROM "+property[1]+" where "+fields[j]+" = '" + fieldValue + "'";
						System.out.println("hsql1:"+hsql1);
					    List specFieldList = null;
					    try {
						    session = HibernateFactory.getSession();
						    Query query = session.createSQLQuery(hsql1);
						    specFieldList = query.list();
						    for(int i=0;i<specFieldList.hashCode();i++){
						    	System.out.println("！！！！！这里输出的是List转换后的值：");
						    	System.out.println(specFieldList.get(i));
						    }
					    } catch (Exception e) {  }
					    if (specFieldList.size() == 0) {
					    	errorQues = errorQues + "" + fieldName + " ：" + fieldValue + " 在表中"+property[1]+"不存在  ";
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
				map.put("errorType", "excel文件有误，请检查！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
	
}
