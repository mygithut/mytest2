package com.dhcc.ftp.bo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.FtpSystemInitial;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.IDUtil;

/**
 * <p>
 * Title: DjclpzAction 定价策略配置
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
 * @author 孙红玉
 * 
 * @date Oct 11, 2012 8:47:54 AM
 * 
 * @version 1.0
 */
public class FtpSystemInitialBO extends BaseBo{
	/**
	  * 获取有多少个县联社未进行定价策略配置
	  * @param request
	  * @return
	  */
	 public Integer getFtpSetNum(HttpServletRequest request) {
		 TelMst user = (TelMst) request.getSession().getAttribute("userBean");
		 Integer num = 0;
		 if(user.getRoleMst().getRoleNo().equals("216")) {//省联社的，查看有多少个
			 String hsql = "select count(*) from ftp.br_mst b " +
		 		"left join ftp.ftp_system_initial f on b.br_no = f.br_no and f.Set_valid_mark = '1' " +
		 		"where b.manage_lvl = '2' and f.set_result is null";
		     List list = daoFactory.query1(hsql, null);
		     num = Integer.valueOf(list.get(0).toString());
		 }else if(user.getRoleMst().getRoleNo().equals("217") || user.getRoleMst().getRoleNo().equals("218")) {//如果是县联社，查看本机构是否配置
			 String hsql = "select count(*) from ftp.br_mst b " +
		 		"left join ftp.ftp_system_initial f on b.br_no = f.br_no and f.Set_valid_mark = '1' " +
		 		"where b.manage_lvl = '2' and f.set_result is null and b.br_no = '"+user.getBrMst().getBrNo()+"'";
		     List list = daoFactory.query1(hsql, null);
		     num = Integer.valueOf(list.get(0).toString());
		 }else {
			 String brNo = "";
			 if(user.getBrMst().getManageLvl().equals("1")) {
				 brNo = user.getBrMst().getSuperBrNo();//查看该机构的父级(县联社)是否配置
			 } else if(user.getBrMst().getManageLvl().equals("0")) {
				 String hsql = "from BrMst where brNo = '"+user.getBrMst().getSuperBrNo()+"'";
				 BrMst brMst = (BrMst)daoFactory.getBean(hsql, null);
				 brNo = brMst.getSuperBrNo();
			 } else {
				 brNo = user.getBrMst().getBrNo();//如果是县联社，查看该机构是否配置
			 }
			 String hsql = "from FtpSystemInitial where brNo = '"+brNo+"' " +
			 		"and setValidMark = '1'";
			 FtpSystemInitial ftpSystemInitial = (FtpSystemInitial)daoFactory.getBean(hsql, null);
			 if(ftpSystemInitial != null) {
				 num = 1;
			 }
		 }
		 return num;
	 }
	//保存
	public void save(String brNo, String setResult, TelMst telMst) {
		String hsql = "from FtpSystemInitial where brNo = '"+brNo+"' order by setNum desc";
		List<FtpSystemInitial> ftpSystemInitialList = daoFactory.query(hsql, null);
		//新增
		if(ftpSystemInitialList == null || ftpSystemInitialList.size() == 0) {
			FtpSystemInitial ftpSystemInitial = new FtpSystemInitial();
			ftpSystemInitial.setSetId(IDUtil.getInstanse().getUID());
			ftpSystemInitial.setBrNo(brNo);
			ftpSystemInitial.setSetResult(setResult);
			ftpSystemInitial.setTelNo(telMst.getTelNo());
			ftpSystemInitial.setSetValidMark("1");//生效标识=1‘有效’
			ftpSystemInitial.setSetNum(1);//第一次
			ftpSystemInitial.setSetDate(String.valueOf(CommonFunctions.GetDBSysDate()));
			daoFactory.insert(ftpSystemInitial);
		}else {
			//修改
			FtpSystemInitial ftpSystemInitial = ftpSystemInitialList.get(0);
			//先将历史记录设置成‘无效’
			String uhsql = "update FtpSystemInitial set setValidMark = '0' where brNo = '"+brNo+"'";
			daoFactory.update(uhsql, null);
			//插入一条新的记录
			FtpSystemInitial ftpSystemInitial_add = new FtpSystemInitial();
			ftpSystemInitial_add.setSetId(IDUtil.getInstanse().getUID());
			ftpSystemInitial_add.setBrNo(brNo);
			ftpSystemInitial_add.setSetResult(setResult);
			ftpSystemInitial_add.setTelNo(telMst.getTelNo());
			ftpSystemInitial_add.setSetValidMark("1");//生效标识=1‘有效’
			ftpSystemInitial_add.setSetNum(ftpSystemInitial.getSetNum()+1);//+1次
			ftpSystemInitial_add.setSetDate(String.valueOf(CommonFunctions.GetDBSysDate()));
			daoFactory.insert(ftpSystemInitial_add);
		}
	}
	
}
