package com.dhcc.ftp.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.FtpUtil;


/**
 * <p>
 * Title: DistributeAction
 * </p>
 * 
 * <p>
 * Description: TODO
 * </p>
 * 
 * <p>
 * Company: 东华软件股份公司金融事业部
 * </p>
 * 
 * @modify 李瑞盛
 * 
 * @date Jun 13, 2011 4:08:08 PM
 * 
 * @version 1.0
 */
public class DistributeAction extends BoBuilder {

	private static final long serialVersionUID = -5371876087343925065L;
	private String url = null;
	private String menuName = null;
	private String key = null;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String execute() throws Exception {
		System.out.println("###进入DistributeAction:"+url);
		/*if(1==1)
			return url;
		//控制权限
//		if(!SystemUtil.securityPerm())
//			return url;
		
		int qxno=Quanxian.getNoByName(this.url);
		TelMst bean=(TelMst)getSession().getAttribute("userBean");
		String roleMenu=bean.getRoleMst().getRoleMenu();
		if(roleMenu==null)
			roleMenu="";
		String[] str=roleMenu.split(",");
		int len=str.length;
		boolean menuFlag=false;
		for(int i=0;i<len;i++){
			if(MenuQuanxian.getNoByMenuName(str[i])==qxno){
				menuFlag=true;
				break;
			}
		}
		boolean qxflag=userBo.hasRight(qxno);
			//if(menuFlag==true&&qxflag==true){
		if(menuFlag==true&&qxflag==true){
			if(null!=menuName&&!"".equals(menuName.trim()))getRequest().setAttribute("menuName", new String(menuName.getBytes("iso-8859-1")));
			if(null!=key&&!"".equals(key.trim()))getRequest().setAttribute("key", key);
			return url;
		}
		else
			return "noqxerror";*/
		if(null!=menuName&&!"".equals(menuName.trim()))getRequest().setAttribute("menuName", menuName);
		if(null!=key&&!"".equals(key.trim()))getRequest().setAttribute("key", key);
		return url;
	}
	
	
}
