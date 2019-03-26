package com.dhcc.ftp.util;
/**
 * ���ز˵��ַ���
 * @author Sunhongyu
 */
public class MenuManager {

	private static MenuManager instance = new MenuManager();
	
	private MenuManager() {}
	
	public static MenuManager getInstance() {
		return instance;
	}
	
	/**
	 * ����ҵ�������ַ���
	 * @return �ַ���
	 */
	public String getMenuTreeString(String role_no) {
		return new MenuTreeReader().read(role_no);
	}
}