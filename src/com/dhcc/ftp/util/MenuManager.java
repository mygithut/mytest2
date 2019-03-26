package com.dhcc.ftp.util;
/**
 * ·µ»Ø²Ëµ¥×Ö·û´®
 * @author Sunhongyu
 */
public class MenuManager {

	private static MenuManager instance = new MenuManager();
	
	private MenuManager() {}
	
	public static MenuManager getInstance() {
		return instance;
	}
	
	/**
	 * ·µ»ØÒµÎñÖÖÀà×Ö·û´®
	 * @return ×Ö·û´®
	 */
	public String getMenuTreeString(String role_no) {
		return new MenuTreeReader().read(role_no);
	}
}