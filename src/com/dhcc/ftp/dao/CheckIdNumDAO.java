package com.dhcc.ftp.dao;


/**
 * ����Ϊ���������֤��ƣ�������Ϣ���ͥ��Ϣ��
 * 
 * @author fuww 2007/12/29
 */
public class CheckIdNumDAO {
private DaoFactory factory=new DaoFactory();
	/**
	 * ��������Ա���뱣֤����������ݿ�û�����õ��˺�
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
