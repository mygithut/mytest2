package com.dhcc.ftp.action.xtgl;

import java.util.List;

import org.apache.log4j.Logger;

import com.dhcc.ftp.action.BoBuilder;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.RoleMst;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.PageUtil;

public class RoleMstAction extends BoBuilder {
	private static final Logger logger = Logger.getLogger(DaoFactory.class);

	private int page = 1;
	private int pageSize = 10;
	private int rowsCount = -1;
	private String flag;
	private String roleNo;
	private String roleName;
	private String roleLvl;
	private String roleLvlName;
	private List list;
	// private String effdate;
	private String filler;
	private String ficode;
	private String[] menuNo;
	
	public String execute() throws Exception {
		return super.execute();
	}

	public String list() throws Exception {
		try {
			TelMst bean=(TelMst)getSession().getAttribute("userBean");
			String hsql = "from RoleMst order by roleLvl desc,roleNo";
			String pageName = "rolemst_list.action?pageSize=" + pageSize;
			PageUtil pageUtil = queryPageBO.queryPage(hsql, pageSize, page,
					rowsCount, pageName);

			getRequest().setAttribute("pageUtil", pageUtil);
			getRequest().getSession().setAttribute("roleNo", bean.getRoleMst().getRoleNo());
			getRequest().getSession().setAttribute("roleName", bean.getRoleMst().getRoleName());
		}

		catch (Exception e) {
			this.addActionMessage(e.getMessage());
		}
		return "list";

	}

	public String roleAdd() {
		return "roleAdd";
	}

	public String del() throws Exception {
		try {
			roleListBo.delete(roleNo);
			getRequest().setAttribute("remark", roleNo);
		} catch (Exception e) {
			this.addActionMessage(e.getMessage());
		}

		return null;
	}

	public String detail() throws Exception {

		try {
			RoleMst roleMst = roleListBo.query(roleNo);
			getRequest().setAttribute("roleMstBean", roleMst);
		}

		catch (Exception e) {
			this.addActionMessage(e.getMessage());
		}

		return "detail";
	}

	public String update() throws Exception {

		roleListBo.update(roleName, roleLvl, filler, roleNo);// 更新角色表信息
		return null;
	}

	public String add() throws Exception {
		try {
			RoleMst roleMst = new RoleMst();
			roleMst.setRoleName(roleName);
			roleMst.setRoleLvl(roleLvl);
			roleMst.setBrf(filler);

			roleNo = roleListBo.add(roleMst);
		}

		catch (Exception e) {
			this.addActionMessage(e.getMessage());
		}

		return null;
	}

	public String menuSearch() throws Exception {
		try {
			RoleMst roleMst = roleListBo.query(roleNo);
			getRequest().setAttribute("roleMst", roleMst);
		}

		catch (Exception e) {
			this.addActionMessage(e.getMessage());
		}

		return "menuSearch";
	}
	public String menuUpdate() throws Exception {
		try{
			roleListBo.updateRoleMenu(roleNo, menuNo);
		}
		catch (Exception e) {
			this.addActionMessage(e.getMessage());
		}
		return null;
	}
	public String addRole() {
		return "roleAdd";
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getRoleLvl() {
		return roleLvl;
	}

	public void setRoleLvl(String roleLvl) {
		this.roleLvl = roleLvl;
	}

	public String getRoleLvlName() {
		return roleLvlName;
	}

	public void setRoleLvlName(String roleLvlName) {
		this.roleLvlName = roleLvlName;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getRowsCount() {
		return rowsCount;
	}

	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}

	public String getRoleNo() {
		return roleNo;
	}

	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getFiller() {
		return filler;
	}

	public void setFiller(String filler) {
		this.filler = filler;
	}

	public String getFicode() {
		return ficode;
	}

	public void setFicode(String ficode) {
		this.ficode = ficode;
	}

	public String[] getMenuNo() {
		return menuNo;
	}

	public void setMenuNo(String[] menuNo) {
		this.menuNo = menuNo;
	}

}
