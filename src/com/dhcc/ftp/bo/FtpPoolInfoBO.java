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
 * Title: DjclpzAction �ʽ��������Ϣ
 * </p>
 * 
 * <p>
 * Description: TODO
 * </p>
 * 
 * <p>
 * Company: ��������ɷݹ�˾������ҵ��
 * </p>
 * 
 * @author �����
 * 
 * @date Oct 11, 2012 3:57:54 PM
 * 
 * @version 1.0
 */
public class FtpPoolInfoBO extends BaseBo {
	/**
	 * ��ȡ������Ӧ���ʽ����Ϣ�Լ���Ʒ��Ϣ
	 * 
	 * @param request
	 * @return
	 */
	public void getFtpPoolInfo(HttpServletRequest request, String brNo,
			String prcMode) {
		// ��ȡ�û�����Ӧ���ʽ����Ϣ
		String hsql = "from FtpPoolInfo f where f.brNo = '" + brNo
				+ "' and f.prcMode = '"+prcMode+"' order by poolNo asc"; // ���۲���1�����ʽ�� 2��˫�ʽ�� 3�����ʽ��
		List<FtpPoolInfo> ftpPoolInfoList = daoFactory.query(hsql, null);
		request.setAttribute("ftpPoolInfoList", ftpPoolInfoList);
	}

	/**
	 * ��ȡ����ҵ���Ŀ����Ʒ��
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
	 * ��ȡ����ҵ���Ŀ����Ʒ��
	 * @param request
	 * @param poolType
	 *            �ʽ���ʲ�����
	 * @param outContentObject
	 *            ǰ̨�������Ѿ���ѡ�е�prdtno
	 */
	public void getBusinessPrdt(HttpServletRequest request, String poolType,
			String outContentObject) {
		String hsql = "from FtpBusinessPrdt where isPrice ='1' ";
		if (poolType != null && !poolType.equals(""))
			hsql += "and prdtNo like 'P" + poolType + "%'";// �ʲ���P1��ͷ����ծ��P2��ͷ
		if (outContentObject != null && !outContentObject.equals(""))
			hsql += "and prdtNo not in (" + outContentObject + ")";// ͬһ����Ʒ�����ڲ��õ��ʽ�����ظ�����
		hsql += " order by accSeqc";
		List<FtpBusinessPrdt> ftpBusinessPrdtList = daoFactory
				.query(hsql, null);
		System.out.println("ftpBusinessPrdtList" + ftpBusinessPrdtList);
		request.setAttribute("ftpBusinessPrdtList", ftpBusinessPrdtList);
	}

	// ����
	public void saveFtpPoolInfo(HttpServletRequest request, String brNo, String prcMode,
			String[] poolNos, String[] poolNames, String[] poolTypes,
			String[] contentObjects) {
		TelMst user = (TelMst) request.getSession().getAttribute("userBean"); // �ȰѸû����ģ����ʽ���������ɾ��
		String delHsql = "delete from FtpPoolInfo where brNo = '" + brNo
				+ "' and prcMode = '"+prcMode+"'";
		daoFactory.delete(delHsql, null);
		for (int i = 0; i < poolNos.length; i++) {
			FtpPoolInfo ftpPoolInfo = new FtpPoolInfo();
			ftpPoolInfo.setPoolId(IDUtil.getInstanse().getUID());
			ftpPoolInfo.setBrNo(brNo);
			ftpPoolInfo.setPrcMode(prcMode);// ���۲���1�����ʽ�� 2��˫�ʽ�� 3�����ʽ��
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
