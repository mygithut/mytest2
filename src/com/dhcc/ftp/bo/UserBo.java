package com.dhcc.ftp.bo;


public class UserBo extends BaseBo{

	/**
	 * ϵͳ��¼ʱУ�����Ա��
	 * @param username
	 * @return
	 */
	public Object getUserBean(String username){
		return userDAO.getUserBean(username);
	}
	
	/**
	 * �����޸�Ȩ�ޱ�right_ctl
	 */
	public void update(String initValue,String rightNo){
		userDAO.update(initValue, rightNo);
	}
	
	/**
	 * �ж�Ȩ��
	 */
	public boolean hasRight(int qxno){
		return userDAO.hasRight(qxno);
        
	}
}
