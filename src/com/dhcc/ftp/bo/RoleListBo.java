package com.dhcc.ftp.bo;

import java.util.List;

import com.dhcc.ftp.entity.RoleMst;
import com.dhcc.ftp.util.PageUtil;

public class RoleListBo extends BaseBo {
	
	public PageUtil getpgUtil(String roleno, String rolename, int perpgLine,
			int currentPage, int rowsCount, String pageName) {

		return roleListDAO.getpgUtil(roleno, rolename, perpgLine, currentPage,
				rowsCount, pageName);
	}

	public void delete(String roleno) {

		roleListDAO.delete(roleno);

	}

	public String add(RoleMst roleMst) {

		return roleListDAO.add(roleMst);
	}

	/**
	 * 取角色号并插入新角色
	 * 
	 * @param roleno
	 * @return
	 */

	public String getnum(String roleLvl) {

		return roleListDAO.getnum(roleLvl);
	}

	/**
	 * 修改角色信息时 更新操作员表中的权限purv_no
	 */
	public void update(String rihtflag, String roleno) {

		roleListDAO.update(rihtflag, roleno);
	}

	/**
	 * 查看角色信息表
	 * 
	 * @param roleno
	 * @return
	 */

	public RoleMst query(String roleno) {

		return roleListDAO.query(roleno);
	}

	/**
	 * 修改角色信息 修改角色表
	 */
	public void update(String roleName, String roleLvl, String filler, String roleNo) {

		roleListDAO.update(roleName, roleLvl, filler, roleNo);

	}

	/**
	 * 查看角色菜单表
	 * 
	 * @param roleno
	 * @return
	 */

	public List queryMenu() {

		return roleListDAO.queryMenu();
	}

	/**
	 * 查看原先角色拥有菜单
	 * 
	 * @param roleno
	 * @return
	 */
	public RoleMst queryForMenu(String roleno) {

		return roleListDAO.queryForMenu(roleno);
	}

	/**
	 * 修改角色表中菜单
	 * 
	 * @param
	 * @return
	 */
	public void updateRoleMenu(String roleNo, String[] menuNo) {

		roleListDAO.updateRoleMenu(roleNo, menuNo);
	}
}
