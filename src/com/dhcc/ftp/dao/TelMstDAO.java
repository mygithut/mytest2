package com.dhcc.ftp.dao;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.RoleMst;
import com.dhcc.ftp.entity.TelMst;

public class TelMstDAO {
	/* 操作员密码重置 */
	private DaoFactory factory = new DaoFactory();

	public void updateComm(String tlrno, String flag) {
		factory.update("update TelMst t set t.flag=" + flag + " where t.telNo=" + tlrno, null);
	}

	public void pwdreset(String tlrno) {

		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date(0);// 当期的交易日期
		factory.update("update TelMst t set t.passwd='000000',t.lastModDate=" + bartDateFormat.format(date) + " where t.telNo=" + tlrno, null);
	}

	public TelMst searchTelMst(String tlrno) {
		return (TelMst) factory.getBean("from TelMst t where t.telNo='" + tlrno + "'", null);
	}

	public List<RoleMst> findAllRole() {
		return factory.query("from RoleMst", null);
	}

	public void updateRole(String tlrctlno, String roleNo) {
		TelMst telMst = (TelMst) factory.getBean("from TelMst t where t.telNo=" + tlrctlno, null);
		RoleMst roleMst = (RoleMst) factory.getBean("from RoleMst r where r.roleNo=" + roleNo, null);
		telMst.setRoleMst(roleMst);
		factory.update(telMst);
	}

	public boolean check(String brno, String brno2) {
		do {
			if (brno.equals(brno2))
				return true;
			BrMst brMst = (BrMst) factory.getBean("from BrMst b where b.brNo='" + brno2 + "'", null);
			if (null == brMst)
				return false;
			brno2 = brMst.getSuperBrNo();
		} while (null != brno2 && !"".equals(brno2));
		return false;
	}

	public BrMst findBrMst(String brno) {
		return (BrMst) factory.getBean("from BrMst b where b.brNo='"+brno+"'", null);
	}

	public RoleMst findRoleMst(String mainRole) {
		return (RoleMst) factory.getBean("from RoleMst r where r.roleNo='"+mainRole+"'", null);
	}

	public void save(TelMst telMst) {
		factory.insert(telMst);
	}

	public void update(TelMst telMst) {
		factory.update(telMst);
	}
	
	public void delete(String tlrno) {
		String hsql = "delete from TelMst where telNo = '"+tlrno+"'";
		factory.delete(hsql, null);
	}
	public void updatePWD(String tlrno, String pwd) {
		SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date(0);// 当期的交易日期
		factory.update("update TelMst t set t.passwd='"+pwd+"' where t.telNo='" + tlrno+"',t.lastModDate=" + bartDateFormat.format(date), null);
	}

	public void updatePSL(String tlrno, String psl) {
		factory.update("update TelMst t set t.perpgShwLine='"+psl+"' where t.telNo='" + tlrno+"'", null);
	}
}
