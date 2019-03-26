package com.dhcc.ftp.util;

/**
 * �ݹ��ȡҵ��������
 * @author Sunhongyu
 */
import java.util.ArrayList;
import java.util.List;

import com.dhcc.ftp.dao.DaoFactory;


public class MenuTreeReader {
	private int j = 0;
	private StringBuffer sbTree = new StringBuffer();
	List methodNameList = new ArrayList();
	/**
	 * ��ȡ�˵�����(���ط���)
	 * @return �ַ���
	 */
	public String read(String role_no) {
		DaoFactory df = new DaoFactory();
		String sql = "select menu_no from ftp.sys_role_menu where role_no='"+role_no+"'";
		System.out.println("sql��"+sql);
		List list = df.query1(sql,null);
	    read("0",0,list);
	    sbTree.deleteCharAt(sbTree.length()-1);
	    System.out.println("sbTree.toString()"+sbTree.toString());
		return sbTree.toString();
	}
	
	/**
	 * �ݹ��ȡҵ��������
	 * 
	 * @param conn
	 * @param id 
	 */
	private void read(String id,int pid,List roleNoList) {
		DaoFactory df = new DaoFactory();
		String sql = "select menu_no,menu_name,menu_parent,menu_lvl"
				+ " from ftp.sys_menu where menu_parent='" + id + "' and menu_stats='1' ";
		if (id.equals("0")) {
			sql += " order by menu_seqc";// 1������menu_seqc����
		} else {
			sql += " order by menu_no";//
		}
		System.out.println("sql��" + sql);
		List list = df.query1(sql,null);
		for (int i = 0; i < list.size(); i++) {
			boolean isChecked = false;
			j++;
			Object[] o = (Object[])list.get(i);
			sbTree.append(j+",");
			sbTree.append(pid+",");
			sbTree.append(o[0]+",");
			//�ж��Ƿ��Ѿ�ѡ��
			for (int m = 0; m < roleNoList.size(); m++) {
				if (o[0].equals(roleNoList.get(m))) {
					isChecked = true;
					break;
				}
			}
			sbTree.append(o[1]+",");
			sbTree.append(isChecked+"|");
			if (Integer.valueOf(o[3].toString()) != 3) {
				read(o[0].toString(),j,roleNoList);
			}
			}
		}	
		
}
