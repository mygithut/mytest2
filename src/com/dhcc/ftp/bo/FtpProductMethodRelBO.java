package com.dhcc.ftp.bo;

/**
 * <p>
 * Title: DjclpzAction 期限匹配产品定价方法配置
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
 * @date Oct 22, 2012 4:57:54 PM
 * 
 * @version 1.0
 */
import java.util.List;

import com.dhcc.ftp.entity.FtpProductMethodRel;

public class FtpProductMethodRelBO extends BaseBo{

	/**
	 * 获期限匹配产品定价方法配置表
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
	 * 根据prdtNo获取对应的期限匹配产品定价方法配置表
	 * 
	 * @return
	 */
	public FtpProductMethodRel getFtpProductMethodRel(String productNo, String brNo) {
		String hsql = "from FtpProductMethodRel where productNo = '"+productNo+"' and brNo = '"+brNo.trim()+"'";
		FtpProductMethodRel ftpProductMethodRel = (FtpProductMethodRel)daoFactory.getBean(hsql, null);
		return ftpProductMethodRel;
	}
	
}
