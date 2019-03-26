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
	 * ȡ��ɫ�Ų������½�ɫ
	 * 
	 * @param roleno
	 * @return
	 */

	public String getnum(String roleLvl) {

		return roleListDAO.getnum(roleLvl);
	}

	/**
	 * �޸Ľ�ɫ��Ϣʱ ���²���Ա���е�Ȩ��purv_no
	 */
	public void update(String rihtflag, String roleno) {

		roleListDAO.update(rihtflag, roleno);
	}

	/**
	 * �鿴��ɫ��Ϣ��
	 * 
	 * @param roleno
	 * @return
	 */

	public RoleMst query(String roleno) {

		return roleListDAO.query(roleno);
	}

	/**
	 * �޸Ľ�ɫ��Ϣ �޸Ľ�ɫ��
	 */
	public void update(String roleName, String roleLvl, String filler, String roleNo) {

		roleListDAO.update(roleName, roleLvl, filler, roleNo);

	}

	/**
	 * �鿴��ɫ�˵���
	 * 
	 * @param roleno
	 * @return
	 */

	public List queryMenu() {

		return roleListDAO.queryMenu();
	}

	/**
	 * �鿴ԭ�Ƚ�ɫӵ�в˵�
	 * 
	 * @param roleno
	 * @return
	 */
	public RoleMst queryForMenu(String roleno) {

		return roleListDAO.queryForMenu(roleno);
	}

	/**
	 * �޸Ľ�ɫ���в˵�
	 * 
	 * @param
	 * @return
	 */
	public void updateRoleMenu(String roleNo, String[] menuNo) {

		roleListDAO.updateRoleMenu(roleNo, menuNo);
	}
}
