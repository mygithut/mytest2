package com.dhcc.ftp.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.dhcc.ftp.entity.RoleMst;
import com.dhcc.ftp.entity.SysRoleMenu;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.PageUtil;

public class RoleListDAO extends DaoFactory {
	private static final Logger logger=Logger.getLogger(DaoFactory.class);
	private DaoFactory factory=new DaoFactory();
	public  PageUtil getpgUtil(String roleno,String rolename,int perpgLine,int currentPage,int rowsCount,String pageName){
		
	  StringBuffer buffer = new StringBuffer();
	  buffer.append("from RoleMst r where 1=1");
	  if (roleno != null && !roleno.equals("") && !roleno.equals("null")) {
			buffer.append(" and r.roleNo like '"+roleno+"'");
		}
	  if (rolename != null && !rolename.equals("") && !rolename.equals("null")) {
			buffer.append(" and r.roleName like '"+rolename+"'");
		}
//	  buffer.append(" order by to_number(roleNo)");
	  buffer.append(" order by roleNo");
	  String hsql=buffer.toString();
	  
	  PageUtil pageUtil=queryPage(hsql, null, perpgLine, currentPage, rowsCount, pageName);
		
		return pageUtil;
	}
	
	public void delete(String roleno) {
		
	  String hsql="delete from RoleMst where 1=1 and roleNo='"+roleno+"'";
	  super.delete(hsql, null);
	  //删除sys_role_menu的相关数据
	  String dsql = "delete from SysRoleMenu where roleNo = '"+roleno+"'";
	  super.delete(dsql, null);
	}
	
	public String add(RoleMst roleMst) {
		
		String roleno =this.getnum(roleMst.getRoleLvl());
		roleMst.setRoleNo(roleno);
		      // roleno=roleno.getRoleLvl()+roleno;//级别加上 1 信用社,2联社,3市办,4省联社
		try {
			this.factory.insert(roleMst);
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return roleno;
	}
	/**
	 * 取角色号并插入新角色
	 * @param roleno
	 * @return
	 */
	
	public String getnum(String roleLvl) {
		
//		String sql = "select max(substr(role_no,2,2))+1 from role_mst where role_lvl='"+roleLvl.trim()+"' and substr(role_no,2,2)!='99' ";
		String hsql="from RoleMst where roleLvl='"+roleLvl.trim()+"' and substr(roleNo,2,2)!='99' ";
		String num = "";
		try {
			List list=this.factory.query(hsql, null);
			List temp=new ArrayList<String>();
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
//					num = (String)list.get(i);
					RoleMst bean=(RoleMst)list.get(i);
					temp.add(bean.getRoleNo());
				} 
				int maxValue=Integer.valueOf(temp.get(0).toString().substring(1));
				for(int j=0;j<temp.size();j++){
					if(maxValue<Integer.valueOf(temp.get(j).toString().substring(1))){
						maxValue=Integer.valueOf(temp.get(j).toString().substring(1));
					}
					
				}
				num=String.valueOf(maxValue+1).trim();
				
				if (num.length() == 1) {
					num = roleLvl + "0" + num;
				}
				if (num.length() == 2) {
					num = roleLvl  + num;
				}
				
			}	
				else 
					num = roleLvl + "01";
				
			
		} 
		catch (Exception e) {
			e.printStackTrace();
		} 
		
		return num;
	}
	
	/**
	 * 修改角色信息时 更新操作员表中的权限purv_no
	 */
	public void update(String rihtflag,String roleno){
//		TelMst bean=(TelMst)ServletActionContext.getRequest().getSession().getAttribute("userBean");
//		String tleNo=bean.getTelNo();
		String hsql="update TelMst t set t.purvNo='"+rihtflag+"' where t.roleMst.roleNo='"+roleno+"'";
		this.factory.update(hsql, null);
	}
	/**
	 * 查看角色信息表
	 * @param roleno
	 * @return
	 */
	
	public RoleMst query(String roleno){
		RoleMst roleMst=new RoleMst();
		String hsql="from RoleMst where roleNo='"+roleno+"'";
		roleMst=(RoleMst)this.factory.getBean(hsql, null);
		
		return roleMst;
	}
	
	/**
	 * 修改角色信息 修改角色表
	 */
	public void update(String roleName, String roleLvl, String filler, String roleNo){
		String hsql = "update RoleMst r set r.roleName='" + roleName
		+ "',r.roleLvl='" + roleLvl + "',r.brf='" + filler
		+ "' where r.roleNo='" + roleNo + "'";
		this.factory.update(hsql, null);
		
	}
	
	/**
	 * 查看角色菜单表
	 * @param roleno
	 * @return
	 */
	
	public List queryMenu(){
		List list=new ArrayList();
		String hsql="from RoleMenu where length(menuNo) = 3 order by menuNo";
		list=this.factory.query(hsql, null);
		
		return list;
	}
	
	/**
	 * 查看原先角色拥有菜单
	 * @param roleno
	 * @return
	 */
	public RoleMst queryForMenu(String roleno){
		RoleMst bean=new RoleMst();
		String hsql="from RoleMst where roleNo="+roleno;
		bean=(RoleMst)this.factory.getBean(hsql, null);
		return bean;
	}
	
	/**
	 * 修改角色表中菜单
	 * @param 
	 * @return
	 */
	public void updateRoleMenu(String roleNo, String[] menuNo){
		String dsql = "delete from SysRoleMenu where roleNo = '"+roleNo+"'";
		this.factory.delete(dsql, null);
		
		StringBuffer menuflagSb = new StringBuffer();
	    if(menuNo != null && menuNo.length>0){
			for (int i = 0; i < menuNo.length; i++) {
				SysRoleMenu sysRoleMenu = new SysRoleMenu();
				sysRoleMenu.setId(IDUtil.getInstanse().getUID());
				sysRoleMenu.setRoleNo(roleNo);
				sysRoleMenu.setMenuNo(menuNo[i]);
				this.factory.insert(sysRoleMenu);
				menuflagSb.append(menuNo[i]);
				menuflagSb.append(",");
			}
		}
		
		String hsql="update RoleMst set roleMenu='"+menuflagSb.toString()+"' WHERE roleNo = '"+roleNo+"'";
		this.factory.update(hsql, null);
	}
	
	
	
	
}
