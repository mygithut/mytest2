package com.dhcc.ftp.action;
import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.FtpPoolInfo;
import com.dhcc.ftp.entity.FtpProductMethodRel;
import com.dhcc.ftp.entity.RoleMst;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.FtpUtil;

/**
 * <p>
 * Title: 下拉列表填充action
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Company: 东华软件股份公司金融事业部
 * </p>
 * 
 * @author 孙红玉
 * 
 * @date June 6, 2012 3:29:33 PM
 * 
 * @version 1.0
 */
public class FillSelectAction extends BoBuilder {
	private static final long serialVersionUID = 1L;
	private String bizCodeType;
	private String businessNo;
	private String productNo;
	private String methodNo;
	private String nowDate;
	private String assessScope;
	private String prcMode;
	private String brNo;
	private String staticNo;
	private String prdtCtgNo;
	private String isTz;
	DaoFactory df = new DaoFactory();
	public String execute() throws Exception {
		return super.execute();
	}

	//生成县联社的下拉列表，县联社manageLvl=2
	public String getBrNoByLvl2() throws Exception {
		TelMst telMst=(TelMst)ServletActionContext.getRequest().getSession().getAttribute("userBean");
		
		HttpServletResponse resp = ServletActionContext.getResponse();
		String hsql = "from BrMst where manageLvl = '3'";
		if(Integer.valueOf(telMst.getBrMst().getManageLvl())<= 3) {//如果登陆机构是县联社及以下，则获取对应的县联社
			hsql += " and brNo = '"+FtpUtil.getXlsBrNo(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl())+"'";
		}
		hsql += " order by brNo";
		List<BrMst> brMstList = df.query(hsql, null);
		StringBuilder brMstListStr = new StringBuilder();
		for (BrMst brMst : brMstList) {
			brMstListStr = brMstListStr.append(brMst.getBrNo() + "|" + brMst.getBrName()+"["+brMst.getBrNo()+"]"
					+ ",");
	    }
		brMstListStr = brMstListStr.deleteCharAt(brMstListStr.length() - 1);
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(brMstListStr.toString());
		return null;
	}

	//生成县联社的下拉列表，县联社manageLvl=2
	public String getBrNoByLvl2_sylqx() throws Exception {
		TelMst telMst=(TelMst)ServletActionContext.getRequest().getSession().getAttribute("userBean");

		HttpServletResponse resp = ServletActionContext.getResponse();
		String hsql = "from BrMst where ";
		if(Integer.valueOf(telMst.getBrMst().getManageLvl())<3) {//如果登陆机构是县联社及以下，则获取对应的县联社
			hsql += " brNo = '"+FtpUtil.getXlsBrNo_sylqx(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl())+"'";
		}else {
			hsql += " superBrNo = '"+telMst.getBrMst().getBrNo()+"'";
		}
		hsql += " order by brNo desc";
		List<BrMst> brMstList = df.query(hsql, null);
		StringBuilder brMstListStr = new StringBuilder();
		for (BrMst brMst : brMstList) {
			brMstListStr = brMstListStr.append(brMst.getBrNo() + "|" + brMst.getBrName()+"["+brMst.getBrNo()+"]"
					+ ",");
		}
		brMstListStr = brMstListStr.deleteCharAt(brMstListStr.length() - 1);
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(brMstListStr.toString());
		return null;
	}
	
	//生成期限匹配业务类型的下拉列表--未添加机构条件
	public String getBusinessName() throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		String hsql = "select distinct business_no, business_name from ftp.ftp_product_method_rel order by business_no";
		List businessList = df.query1(hsql, null);
		StringBuilder businessListStr = new StringBuilder();
		for (Object business : businessList) {
			Object[] o = (Object[])business;
			businessListStr = businessListStr.append(o[0] + "|" + o[1] 
					+ ",");
	    }
		businessListStr = businessListStr.deleteCharAt(businessListStr.length() - 1);
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(businessListStr.toString());
		return null;
	}
	
	/**
	 * 策略调整
	 * @return
	 * @throws Exception
	 */
	public String getBusinessName1() throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		String hsql = "select distinct business_no, business_name from ftp.ftp_product_method_rel    " +
				" where business_no in  ('YW101','YW201','YW202','YW203','YW204')" +
				" order by business_no";
		List businessList = df.query1(hsql, null);
		StringBuilder businessListStr = new StringBuilder();
		for (Object business : businessList) {
			Object[] o = (Object[])business;
			businessListStr = businessListStr.append(o[0] + "|" + o[1]  + ",");
		}
		if(businessListStr.length()!=0){
			businessListStr = businessListStr.deleteCharAt(businessListStr.length() - 1);
		}
		
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(businessListStr.toString());
		return null;
	}
	
	//根据业务类型，生成产品select下拉列表 --未添加机构条件
	public String getPrdtName() throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		StringBuilder prdtNameListStr = new StringBuilder();
		String sql = "select distinct product_No, product_name from ftp.FTP_business_static_divide" +
				" where 1=1 ";
		System.out.println("businessNo"+businessNo);
		if(businessNo != null && !businessNo.equals("") && !businessNo.equals("null")) {
			sql += " and business_No = '"+businessNo+"'";
		}
		if(prdtCtgNo != null && !prdtCtgNo.equals("") && !prdtCtgNo.equals("null")) {
			sql += " and product_ctg_No = '"+prdtCtgNo+"'";
		}
		sql += " order by product_No";
		List productList = df.query1(sql, null);
		for (Object product : productList) {
			Object[] o = (Object[])product;
			prdtNameListStr.append(o[0] + "|" + o[1] + ",");
	    }
		
		prdtNameListStr = prdtNameListStr.deleteCharAt(prdtNameListStr.length() - 1);
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(prdtNameListStr.toString());
		return null;
	}

	//FTP期限匹配产品定价方法配置--具体定价方法
	public String getMethodName() throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		Map<String,String> methodMAp = new HashMap<String,String>();
		methodMAp.put("01", "原始期限匹配法");
		methodMAp.put("02", "指定利率法");
		//methodMAp.put("03", "重定价期限匹配法");
		methodMAp.put("04", "现金流法");
		methodMAp.put("05", "久期法");
		methodMAp.put("06", "指定期限法");
		//methodMAp.put("07", "加权利率法");
		methodMAp.put("08", "固定利差法");
		String hsql = "";
		StringBuilder methodNameListStr = new StringBuilder();
		hsql = "from FtpProductMethodRel where productNo = '"+productNo+"'";
		System.out.println("hsql:"+hsql);
		FtpProductMethodRel ftpProductMethodRel = (FtpProductMethodRel)df.getBean(hsql, null);
		//具体定价方法+其它可以定价方法
		methodNameListStr.append(ftpProductMethodRel.getMethodNo() + "|" + ftpProductMethodRel.getMethodName());
	   /*	if(ftpProductMethodRel.getAvailableMethodNo() != null && !ftpProductMethodRel.getAvailableMethodNo().equals(""))
			methodNameListStr.append("," + ftpProductMethodRel.getAvailableMethodNo() + "|" + ftpProductMethodRel.getAvailableMethod());*/
		Set<String> methodSet = methodMAp.keySet();
		Iterator<String> it = methodSet.iterator();
		while(it.hasNext()){
			String methodNo = it.next();
			if(!methodNo.equals(ftpProductMethodRel.getMethodNo()))
				methodNameListStr.append("," + methodNo + "|" + methodMAp.get(methodNo));
		}
		System.out.println("定价方法:"+methodNameListStr);
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(methodNameListStr.toString());
		return null;
	}
	
	//收益率曲线
	public String getCurveName() throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		String hsql = "";
		StringBuilder curveListStr = new StringBuilder();
		//固定利差法08和指定利率法02没有收益率曲线
		if(!methodNo.equals("02") && !methodNo.equals("08")) {
			hsql = "select distinct curve_no, curve_name from ftp.Ftp_Yield_Curve order by curve_no";
			List ftpYieldCurveList = df.query1(hsql, null);
			for (Object ftpYieldCurve : ftpYieldCurveList) {
				Object[] o = (Object[])ftpYieldCurve;
				curveListStr.append(o[0] + "|" + o[1] + ",");
			}
			curveListStr = curveListStr.deleteCharAt(curveListStr.length() - 1);
		}else {
			curveListStr.append("无|无");
		}
		
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(curveListStr.toString());
		return null;
	}
	//获取对应的资金池
	public String getPoolInfo() throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		StringBuilder poolListStr = new StringBuilder();
		String hsql = "from FtpPoolInfo where 1=1";
		if(prcMode != null || !prcMode.equals("")) {
			hsql += " and prcMode = '"+prcMode+"'";
		}
		hsql += " order by poolNo";
		List<FtpPoolInfo> list = df.query(hsql, null);
		for (FtpPoolInfo ftpPoolInfo : list) {
			poolListStr.append(ftpPoolInfo.getPoolNo()+ "+" + ftpPoolInfo.getPoolType() + "|" + ftpPoolInfo.getPoolName() + ",");
		}
		poolListStr = poolListStr.deleteCharAt(poolListStr.length() - 1);
		System.out.println("资金池select"+poolListStr);
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(poolListStr.toString());
		return null;
	}
	
	/**
	 * 获取盈利分析的日期，如果当前日期为月底，可查看当月，如果不为月底，则从上月开始
	 * 注：1月份支持跨年
	 * @return
	 * @throws Exception
	 */
	public String getReportDate() throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		StringBuilder dateListStr = new StringBuilder();
		
		int nowYear = Integer.valueOf(nowDate.substring(0,4));		
		//nowDate= "20130630";//(贴源数据不更新时)试用测试，正式时必须去掉
	/*	if(!CommonFunctions.dateModifyD(nowDate, 1).substring(6,8).equals("01")) {//+1天后看是否为下月月初，如果不是则获取上月月底日期
			nowDate = CommonFunctions.dateModifyM(nowDate, -1);//往前-1个月并处理到月底
		}*/
		int nowMonth = Integer.valueOf(String.valueOf(nowDate).substring(4, 6));//当前月
		if(assessScope.equals("-1")) {//月度
			
			dateListStr.append(nowDate + "|"+nowDate.substring(0,4)+"年"+ nowMonth + "月,");
			for(int i = nowMonth-1; i > 0; i--) {//往前循环，获取每月的月底
				String date = CommonFunctions.dateModifyM(String.valueOf(nowDate), i-nowMonth);
				dateListStr.append(date + "|"+nowDate.substring(0,4)+"年"+i + "月,");
			}
			
			String lastNowDate = String.valueOf(nowYear-1)+"1231";
			if(!lastNowDate.equals(nowDate)){
				for(int i = 12; i > 0; i--) {//往前循环，获取每月的月底
					String date = CommonFunctions.dateModifyM(String.valueOf(lastNowDate), i-12);
					dateListStr.append(date + "|"+lastNowDate.substring(0,4)+"年"+i + "月,");
				}
			}
			
			
		}else if(assessScope.equals("-3")) {//季度
			Map<String, String> jdMap = new HashMap<String, String>();//已经加入到dateListStr中的季度数据
			String lastNowDate = String.valueOf(nowYear-1)+"0101";
			int monthNum = CommonFunctions.monthsSubtract(Long.valueOf(nowDate), Long.valueOf(lastNowDate));
			for(int i = 0; i <= monthNum; i++) {
				int jd = 0;
				String date = (i == 0 ? nowDate : CommonFunctions.dateModifyM(String.valueOf(nowDate), -i));
				int month = Integer.valueOf(String.valueOf(date).substring(4, 6));//当前月
				if (month >= 1 && month <= 3) jd = 1;
		        else if (month >= 4 && month <= 6) jd =  2;
		        else if (month >= 7 && month <= 9) jd = 3;
		        else if (month >= 10 && month <= 12) jd = 4;
				
				String dateStr = date.substring(0,4)+"年第"+ jd + "季度"
				               +(CommonFunctions.dateModifyD(date, 1).substring(6,8).equals("01")?"":"("+date+")");//如果是支持非月末的当日的季报时，显示当日日期。
				if(jdMap.get(dateStr.substring(0,9)) == null) {
					dateListStr.append(date + "|"+dateStr+",");
					jdMap.put(dateStr.substring(0,9), dateStr);
				}
			}
			/*int jd = nowMonth/3;//目前完整的季度数，例:如果是20130731，jd=2
			int scope = nowMonth%3;//超出完整季度的月份数，例:如果是20130731，scope=1
			int m = 0;
			for(int i = jd; i > 0; i--) {//往前循环-scope-3个月
				String date = CommonFunctions.dateModifyM(nowDate, -scope-m);
				dateListStr.append(date + "|"+nowDate.substring(0,4)+"年第"+ i + "季度,");
				m += 3;
			}
			
			String lastNowDate = String.valueOf(nowYear-1)+"1231";
			jd = 4;
			scope =0;
			for(int i = jd; i > 0; i--) {//往前循环-scope-3个月
				String date = CommonFunctions.dateModifyM(lastNowDate, -scope-m);
				dateListStr.append(date + "|"+lastNowDate.substring(0,4)+"年第"+ i + "季度,");
				m += 3;
			}*/
			
		}else {//年度
			
			
			dateListStr.append(nowDate + "|"+nowDate+",");
			for(int i = nowMonth-1; i > 0; i--) {//往前循环，获取每月的月底
				String date = CommonFunctions.dateModifyM(String.valueOf(nowDate), i-nowMonth);
				dateListStr.append(date + "|"+date+ ",");
			}
			
			String lastNowDate = String.valueOf(nowYear-1)+"1231";
			if(!lastNowDate.equals(nowDate)){
				for(int i = 12; i > 0; i--) {//往前循环，获取每月的月底
					String date = CommonFunctions.dateModifyM(String.valueOf(lastNowDate), i-12);
					dateListStr.append(date + "|"+date+ ",");
				}
			}
			
			
			
//			dateListStr.append(nowDate + "|"+nowDate+",");
//			if(Integer.valueOf(nowDate) > Integer.valueOf(nowYear+"0630")) {
//				dateListStr.append((nowYear)+"0630|"+(nowYear)+"0630,");//如果是下半年，则可查看上半年半年报
//			}
//
//			dateListStr.append((nowYear-1)+"1231|"+(nowYear-1)+"1231,");//去年年底日期
		}
		dateListStr = dateListStr.length() > 0 ? dateListStr.deleteCharAt(dateListStr.length() - 1):dateListStr;
		System.out.println("dateListStr:"+dateListStr);
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(dateListStr.toString());
		return null;
	}
	
	/**
	 * 生成统计报表中业务条线统计大类下拉框
	 * @return
	 * @throws Exception
	 */
	public String getStaticName() throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		String hsql = "select distinct left(static_no,3) a,static_name from ftp.FTP_business_static_divide order by a";
		List staticList = df.query1(hsql, null);
		StringBuilder staticListStr = new StringBuilder();
		for (Object obj : staticList) {
			Object[] o = (Object[])obj;
			staticListStr = staticListStr.append(o[0] + "|" + o[1] + ",");
	    }
		staticListStr = staticListStr.deleteCharAt(staticListStr.length() - 1);
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(staticListStr.toString());
		return null;
	}
	/**
	 * 根据统计报表中的业务条线生成产品大类下拉框
	 * @return
	 * @throws Exception
	 */
	public String getPrdtCtgName() throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		StringBuilder prdtCtgNameListStr = new StringBuilder();
		String hsql = "select distinct product_Ctg_No, product_Ctg_name from ftp.FTP_business_static_divide where business_no = '"+businessNo+"' order by product_Ctg_No";
		List prdtCtgList = df.query1(hsql, null);
		for (Object product : prdtCtgList) {
			Object[] o = (Object[])product;
			prdtCtgNameListStr.append(o[0] + "|" + o[1] + ",");
	    }
		
		prdtCtgNameListStr = prdtCtgNameListStr.deleteCharAt(prdtCtgNameListStr.length() - 1);
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(prdtCtgNameListStr.toString());
		return null;
	}
	/**
	 * 根据角色表获取角色下拉框
	 * @return
	 * @throws Exception
	 */
	public String getDkIsTz() throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		String hsql = "";
		StringBuilder tzListStr = new StringBuilder();
		hsql = "from FtpProductMethodRel where  productNo = '"+productNo+"'";
		System.out.println("hsql:"+hsql);
		FtpProductMethodRel ftpProductMethodRel = (FtpProductMethodRel)df.getBean(hsql, null);
		if(ftpProductMethodRel.getIsTz().equals("0")){
		tzListStr.append(ftpProductMethodRel.getIsTz() + "|"+"否");
		}
		else{
			
			tzListStr.append(ftpProductMethodRel.getIsTz() + "|"+"是");
		}
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(tzListStr.toString());
		return null;
	}
	
	
	/**
	 * 根据角色表获取角色下拉框
	 * @return
	 * @throws Exception
	 */
	public String getRoleLvl()  throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		StringBuilder roleLvlListStr = new StringBuilder();
		List<RoleMst> roleList = telMstBO.findAllRole();
		for (RoleMst roleMst : roleList) {
			roleLvlListStr.append(roleMst.getRoleNo() + "|" + roleMst.getRoleName() + ",");
		}
		
		roleLvlListStr = roleLvlListStr.deleteCharAt(roleLvlListStr.length() - 1);
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(roleLvlListStr.toString());
		return null;
	}
	public String getBizCodeType() {
		return bizCodeType;
	}
	public void setBizCodeType(String bizCodeType) {
		this.bizCodeType = bizCodeType;
	}
	
	public String getBusinessNo() {
		return businessNo;
	}
	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getMethodNo() {
		return methodNo;
	}

	public void setMethodNo(String methodNo) {
		this.methodNo = methodNo;
	}

	public String getPrcMode() {
		return prcMode;
	}

	public void setPrcMode(String prcMode) {
		this.prcMode = prcMode;
	}

	public String getBrNo() {
		return brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getStaticNo() {
		return staticNo;
	}

	public void setStaticNo(String staticNo) {
		this.staticNo = staticNo;
	}

	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public String getAssessScope() {
		return assessScope;
	}

	public void setAssessScope(String assessScope) {
		this.assessScope = assessScope;
	}

	public String getPrdtCtgNo() {
		return prdtCtgNo;
	}

	public void setPrdtCtgNo(String prdtCtgNo) {
		this.prdtCtgNo = prdtCtgNo;
	}

	public String getIsTz() {
		return isTz;
	}

	public void setIsTz(String isTz) {
		this.isTz = isTz;
	}
    
}
