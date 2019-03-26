package com.dhcc.ftp.dao;


/**
 * 此类为了排重身份证设计（个人信息与家庭信息）
 * 
 * @author fuww 2007/12/29
 */
public class CheckIdNumDAO {
private DaoFactory factory=new DaoFactory();
	/**
	 * 新增操作员必须保证这个人在数据库没有启用的账号
	 * 
	 * @param idNum
	 * @param otherIdNum
	 * @return
	 */
	public int checkTlrctlEixtData(String idNum, String otherIdNum) {
		int i = 0;
		String hql = "select count(*) from ftp.TelMst t where t.flag ='1' and (t.idNo="
				+ idNum + " or t.idNo=" + otherIdNum + ")";
		try {
			i=Integer.parseInt(factory.getUniqueResult(hql, null).toString());			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return i;
	}	
}
