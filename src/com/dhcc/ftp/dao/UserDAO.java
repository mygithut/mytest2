package com.dhcc.ftp.dao;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.entity.TelMst;

public class UserDAO extends DaoFactory {
	private static final Logger logger=Logger.getLogger(DaoFactory.class);
	private DaoFactory factory=new DaoFactory();
	
	/**
	 * ϵͳ��¼ʱУ�����Ա��
	 * @param username
	 * @return
	 */
	public Object getUserBean(String username){
		String hsql="from TelMst t where t.telNo='"+username.trim()+"'";
		Object object=this.factory.getBean(hsql, null);
		return object;
	}
	
	/**
	 * �����޸�Ȩ�ޱ�right_ctl
	 */
	public void update(String initValue,String rightNo){
		String hsql="update RightCtl set initValue='"+initValue+"' where rightNo='"+rightNo+"'";
		this.factory.update(hsql, null);
		
	}
	
	/**
	 * �ж�Ȩ��
	 */
	
	public boolean hasRight(int qxno){
		TelMst telMstBean = (TelMst)ServletActionContext.getRequest().getSession().getAttribute("userBean");
		String s=telMstBean.getPurvNo();
        if(s == null)
            return false;
        else
        {
        	int len=s.length();
        	if(qxno>len||qxno==0){
        		logger.info("������׵�Ȩ�޻�û�����ã������á�");
        		logger.info("������׵�Ȩ��λ��:::"+(qxno));
        		return false;
        	}
            char ac[] = s.toCharArray();
            if (ac[qxno-1] == '1'){
            	//System.out.println("������׵�Ȩ��λ��:::"+(i));
            	//System.out.println("������׵�Ȩ��λ��:::"+(s));
            	return true;
            }
        }
        return false;
        
	}
	
	
	
	
	
	
	
	
	
}