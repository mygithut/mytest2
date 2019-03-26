package com.dhcc.ftp.bo;

import java.sql.Connection;
import java.util.List;

import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.RoleMst;
import com.dhcc.ftp.entity.TelMst;

public class TelMstBO extends BaseBo {
	/**
	 * 
	 * 功能描述：操作员状态修改
	 * 
	 * @param tlrno
	 * @param flag
	 * @return
	 * @author dhcc liushian
	 * @date Jan 26, 2010
	 * @修改日志：
	 */
	public int freezeORstart(String tlrno, String flag) {
		this.updateComm(tlrno, flag);
		return 0;
	}

	/***************************************************************************
	 * edit by fuww
	 * 
	 * @param sqlstr
	 */
	public void updateComm(String tlrno, String flag) {
		Connection con = null;
		try {

			telMstDAO.updateComm(tlrno, flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/* 操作员密码重置 */
	public void pwdreset(String tlrno) {

		try {
			telMstDAO.pwdreset(tlrno);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String updateRole(String tlrctlno, String roleNo) {
		try {
			telMstDAO.updateRole(tlrctlno, roleNo);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		} finally {
		}
		return "1";
	}

	public String updatePWD(String tlrctlno, String pwd) {
		try {
			telMstDAO.updatePWD(tlrctlno, pwd);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		} finally {
		}
		return "1";
	}

	public String updatePSL(String tlrctlno, String psl) {
		try {
			telMstDAO.updatePSL(tlrctlno, psl);
		} catch (Exception e) {
			e.printStackTrace();
			return "0";
		} finally {
		}
		return "1";
	}

	public TelMst searchTelMst(String tlrno) {

		return telMstDAO.searchTelMst(tlrno);
	}

	public List<RoleMst> findAllRole() {

		return telMstDAO.findAllRole();
	}

	public boolean check(String brno, String brno2) {

		return telMstDAO.check(brno, brno2);
	}

	public BrMst findBrMst(String brno) {
		return telMstDAO.findBrMst(brno);
	}

	public RoleMst findRoleMst(String mainRole) {
		return telMstDAO.findRoleMst(mainRole);
	}

	public void save(TelMst telMst) {
		telMstDAO.save(telMst);
	}

	public void update(TelMst telMst) {
		telMstDAO.update(telMst);
	}

	public void delete(String tlrno) {
		telMstDAO.delete(tlrno);
	}
}
