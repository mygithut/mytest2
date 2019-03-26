package com.dhcc.ftp.bo;

/**
 * <p>
 * Title: DjclpzAction ����ƥ���Ʒ���۷�������
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
 * @date Oct 22, 2012 4:57:54 PM
 * 
 * @version 1.0
 */
import java.util.List;

import com.dhcc.ftp.entity.FtpProductMethodRel;

public class FtpProductMethodRelBO extends BaseBo{

	/**
	 * ������ƥ���Ʒ���۷������ñ�
	 * 
	 * @return
	 */
	public List getFtpProductMethodRelList() {
		String hsql = "select p.BUSINESS_NAME, p.PRODUCT_NAME, p.METHOD_NAME, p.ADJUST_RATE, y.CURVE_NAME, p.ASSIGN_TERM" +
				", p.APPOINT_RATE, p.APPOINT_DELTA_RATE from ftp.Ftp_Product_Method_Rel p " +
				"left join ftp.Ftp_YIELD_CURVE y on p.curve_no = y.curve_no order by business_No,product_No";
		List ftpProductMethodRelList = daoFactory.query1(hsql, null);
		return ftpProductMethodRelList;
	}
	
	/**
	 * ����prdtNo��ȡ��Ӧ������ƥ���Ʒ���۷������ñ�
	 * 
	 * @return
	 */
	public FtpProductMethodRel getFtpProductMethodRel(String productNo, String brNo) {
		String hsql = "from FtpProductMethodRel where productNo = '"+productNo+"' and brNo = '"+brNo.trim()+"'";
		FtpProductMethodRel ftpProductMethodRel = (FtpProductMethodRel)daoFactory.getBean(hsql, null);
		return ftpProductMethodRel;
	}
	
}
