package com.dhcc.ftp.bo;


public class UserBo extends BaseBo{

	/**
	 * 系统登录时校验操作员号
	 * @param username
	 * @return
	 */
	public Object getUserBean(String username){
		return userDAO.getUserBean(username);
	}
	
	/**
	 * 更新修改权限表right_ctl
	 */
	public void update(String initValue,String rightNo){
		userDAO.update(initValue, rightNo);
	}
	
	/**
	 * 判断权限
	 */
	public boolean hasRight(int qxno){
		return userDAO.hasRight(qxno);
        
	}
}
