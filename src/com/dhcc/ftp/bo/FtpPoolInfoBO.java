package com.dhcc.ftp.bo;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.entity.FtpBusinessPrdt;
import com.dhcc.ftp.entity.FtpPoolInfo;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.IDUtil;

/**
 * <p>
 * Title: DjclpzAction 资金池配置信息
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
 * @date Oct 11, 2012 3:57:54 PM
 * 
 * @version 1.0
 */
public class FtpPoolInfoBO extends BaseBo {
	/**
	 * 获取机构对应的资金池信息以及产品信息
	 * 
	 * @param request
	 * @return
	 */
	public void getFtpPoolInfo(HttpServletRequest request, String brNo,
			String prcMode) {
		// 获取该机构对应的资金池信息
		String hsql = "from FtpPoolInfo f where f.brNo = '" + brNo
				+ "' and f.prcMode = '"+prcMode+"' order by poolNo asc"; // 定价策略1：单资金池 2：双资金池 3：多资金池
		List<FtpPoolInfo> ftpPoolInfoList = daoFactory.query(hsql, null);
		request.setAttribute("ftpPoolInfoList", ftpPoolInfoList);
	}

	/**
	 * 获取定价业务科目—产品表
	 * 
	 * @return
	 */
	public List<FtpBusinessPrdt> getFtpBusinessPrdt() {
		String hsql = "from FtpBusinessPrdt";
		List<FtpBusinessPrdt> ftpBusinessPrdtList = daoFactory
				.query(hsql, null);
		return ftpBusinessPrdtList;
	}

	/**
	 * 获取定价业务科目—产品表
	 * @param request
	 * @param poolType
	 *            资金池资产类型
	 * @param outContentObject
	 *            前台传来的已经被选中的prdtno
	 */
	public void getBusinessPrdt(HttpServletRequest request, String poolType,
			String outContentObject) {
		String hsql = "from FtpBusinessPrdt where isPrice ='1' ";
		if (poolType != null && !poolType.equals(""))
			hsql += "and prdtNo like 'P" + poolType + "%'";// 资产类P1打头，负债类P2打头
		if (outContentObject != null && !outContentObject.equals(""))
			hsql += "and prdtNo not in (" + outContentObject + ")";// 同一个产品不能在不用的资金池中重复出现
		hsql += " order by accSeqc";
		List<FtpBusinessPrdt> ftpBusinessPrdtList = daoFactory
				.query(hsql, null);
		System.out.println("ftpBusinessPrdtList" + ftpBusinessPrdtList);
		request.setAttribute("ftpBusinessPrdtList", ftpBusinessPrdtList);
	}

	// 保存
	public void saveFtpPoolInfo(HttpServletRequest request, String brNo, String prcMode,
			String[] poolNos, String[] poolNames, String[] poolTypes,
			String[] contentObjects) {
		TelMst user = (TelMst) request.getSession().getAttribute("userBean"); // 先把该机构的，多资金池相关配置删除
		String delHsql = "delete from FtpPoolInfo where brNo = '" + brNo
				+ "' and prcMode = '"+prcMode+"'";
		daoFactory.delete(delHsql, null);
		for (int i = 0; i < poolNos.length; i++) {
			FtpPoolInfo ftpPoolInfo = new FtpPoolInfo();
			ftpPoolInfo.setPoolId(IDUtil.getInstanse().getUID());
			ftpPoolInfo.setBrNo(brNo);
			ftpPoolInfo.setPrcMode(prcMode);// 定价策略1：单资金池 2：双资金池 3：多资金池
			ftpPoolInfo.setPoolNo(poolNos[i]);
			ftpPoolInfo.setPoolName(poolNames[i]);
			if(poolTypes != null)ftpPoolInfo.setPoolType(poolTypes[i]);
			ftpPoolInfo.setContentObject(contentObjects[i]);
			ftpPoolInfo.setGenerateDate(String.valueOf(CommonFunctions
					.GetDBSysDate()));
			ftpPoolInfo.setTelNo(user.getTelNo());
			daoFactory.insert(ftpPoolInfo);
		}
	}

}
