package com.dhcc.ftp.action;
/**
 * 期限匹配业务模拟定价
 * @author Sunhongyu
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.Ftp1PrepayAdjust;
import com.dhcc.ftp.entity.FtpBusinessInfo;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.SCYTCZlineF;

public class QxppywmndjAction  extends BoBuilder {

	private String prdtNo;
	private String brNo;
	private String brName;
	private String manageLvl;
	private String curNo;
	private String curName;
	private String businessNo;
	private String businessName;
	private String productNo;
	private String productName;
	private String amt;
	private String rate;
	private String opnDate;
	private String mtrDate;
	private String hkTerm;
	private String zjTerm;
	private String scrapValueRate;
	HttpServletRequest request = getRequest();
	HttpServletResponse resp = getResponse();
	DaoFactory df = new DaoFactory();

	public String execute() throws Exception {
		return super.execute();
	}
	
	public String getMethodNo() throws Exception {
		String[] ftp_methodComb=FtpUtil.getFTPMethod_byPrdtNo(prdtNo,brNo);
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(ftp_methodComb[0]);
		return null;
	}
	public String ftp_compute_mn() throws Exception {

		String[] brNos = brNo.split(",");
		String[] brNames = CommonFunctions.Chinese_CodeChange(brName).split(",");
		String[] manageLvls = manageLvl.split(",");
		String[] curNos = curNo.split(",");
		String[] curNames =  CommonFunctions.Chinese_CodeChange(curName).split(",");
		String[] businessNos = businessNo.split(",");
		String[] businessNames = CommonFunctions.Chinese_CodeChange(businessName).split(",");
		String[] productNos = productNo.split(",");
		String[] productNames =  CommonFunctions.Chinese_CodeChange(productName).split(",");
		String[] amts = amt.split(",");
		String[] rates = rate.split(",");
		String[] opnDates = opnDate.split(",");
		String[] mtrDates = mtrDate.split(",");
		String[] hkTerms = hkTerm.split(",");
		String[] zjTerms = zjTerm.split(",");
		String[] scrapValueRates = scrapValueRate.split(",");
		String date = String.valueOf(CommonFunctions.GetDBSysDate());
		String sysdate = String.valueOf(CommonFunctions.GetCurrentDateInLong());//计算机日期
		int ftp_total_num=0;//被选定定价的总笔数
		int ftp_success_num=0;//定价成功总笔数
		int ftp_fault_num=0;//定价失败总笔数
		List<FtpBusinessInfo> ftped_data_successList=new ArrayList<FtpBusinessInfo>();//定价成功的业务记录list
		List<FtpBusinessInfo> ftped_data_errorList=new ArrayList<FtpBusinessInfo>();//定价失败的业务记录list
		
		//存贷款产品的各项策略调整
		Map<Integer, Double> ckzbjAdjustMap = uL06BO.getCkzbjAdjustValue();//存款准备金调整
		Map<Integer, Double> ldxAdjustMap = uL06BO.getLdxAdjustValue();//流动性调整
		Map<String, Double> irAdjustMap = uL06BO.getIrAdjustValue();//信用风险调整
		List<Ftp1PrepayAdjust> prepayList = uL06BO.getPrepayAdjustValue();//提前还款/提前支取调整
		Map<String, Double> clAdjustMap = uL06BO.getClAdjustValueByxls(brNos[0], manageLvls[0]);//策略调整
		double[][] dkAdjustArr = uL06BO.getDkAdjustValue();//贷款调整比率
		Double[][] publicRate = FtpUtil.getFtpPublicRate();//央行贷款利率
		//double[][] dkSfblAdjustArr=uL06BO.getDkSfblAdjustValue();//贷款上浮比例调整比率
		
		for(int i = 0; i < brNos.length; i++) {
			int term = 0;
			if(!mtrDates[i].equals("/")){
			    term = CommonFunctions.daysSubtract(mtrDates[i], opnDates[i]);
			}
			try{
				//对新增的业务行 依次定价 ，并将定价结果存入ftped_data_successList 与  ftped_data_errorList
				FtpBusinessInfo ftp_business_info = new FtpBusinessInfo();
				ftp_business_info.setBrNo(brNos[i]);
				ftp_business_info.setBrName(brNames[i]);
				ftp_business_info.setBusinessNo(businessNos[i]);
				ftp_business_info.setBusinessName(businessNames[i]);
				ftp_business_info.setCurNo(curNos[i]);
				ftp_business_info.setCurName(curNames[i]);
				ftp_business_info.setOpnDate(opnDates[i]);
				ftp_business_info.setMtrDate(mtrDates[i]);
				ftp_business_info.setTerm(String.valueOf(term));
				ftp_business_info.setPrdtNo(productNos[i]);
				ftp_business_info.setPrdtName(productNames[i]);
				ftp_business_info.setAmt(amts[i]);
				ftp_business_info.setRate(rates[i]);
				ftp_business_info.setHkTerm(hkTerms[i]);
				ftp_business_info.setZjTerm(zjTerms[i]);
				ftp_business_info.setScrapValueRate(scrapValueRates[i]);
				String[] ftp_methodComb=FtpUtil.getFTPMethod_byPrdtNo(productNos[i],brNos[i]);
				if(ftp_methodComb==null){//没在配置表中找到对应定价方法记录行，则不对该笔业务定价
					ftp_business_info.setMethodName("还未配置");
					ftped_data_errorList.add(ftp_business_info);
					ftp_fault_num++;
					continue;
				}
				
				double adjust_rate=Double.valueOf(ftp_methodComb[2]);//调整利率
				String method_no=ftp_methodComb[0];//具体定价方法编号
				String curve_no=ftp_methodComb[3];//使用的收益率曲线编号
				
				String br_no = FtpUtil.getXlsBrNo_sylqx(brNos[i], manageLvls[i]);
				SCYTCZlineF f=new SCYTCZlineF();
				if(!"无".equals(curve_no) ){
					 f= FtpUtil.getSCYTCZlineF_fromDB(curve_no,opnDates[i], br_no);
					 if(f==null){
						 //ftp_business_info.setCurveName("未发布");//页面里直接根据曲线编号来获取【页面里曲线编号为null，获取到曲线名:‘未发布’】
						 ftp_business_info.setMethodNo(method_no);
						 ftp_business_info.setMethodName(FtpUtil.getMethodName_byMethodNo(method_no));////
						 ftped_data_errorList.add(ftp_business_info);
						 ftp_fault_num++;
						 continue;
					 }
				}						
				
				if(method_no.equals("06")){//只有‘利率代码差额法06’才使用‘参考期限’ 
					term=Integer.valueOf(ftp_methodComb[1]);//参考期限
				}

				
				double appoint_rate=Double.valueOf(ftp_methodComb[4]);//指定利率
				double appoint_delta_rate=Double.valueOf(ftp_methodComb[5]);//固定利差
				
				double ftp_price=-1;
				if(method_no.equals("01")){//## 原始期限匹配法
					ftp_price=FtpUtil.getFTPPrice_ysqxppf(term, f, adjust_rate);
				}else if(method_no.equals("02")){//## 指定利率法
					//ftp_price=FtpUtil.getFTPPrice_zdllf(term, curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
					ftp_price=appoint_rate+adjust_rate;
				}else if(method_no.equals("03")){//## 重定价期限匹配法
					/////
				}else if(method_no.equals("04")){//## 现金流法,还款周期
					ftp_price=FtpUtil.getFTPPrice_xjlf(term,Integer.valueOf(hkTerms[i]),f, adjust_rate);
				}else if(method_no.equals("05")){//## 久期法
					if(ftp_business_info.getPrdtNo().substring(0, 4).equals("P109")){//按揭贷款，还款周期
						ftp_price=FtpUtil.getFTPPrice_jqf(term,Integer.valueOf(hkTerms[i]),0,f, adjust_rate);
					}else{//固定资产,默认设置残值率为0.4，折旧周期
						ftp_price=FtpUtil.getFTPPrice_jqf(term,Integer.valueOf(zjTerms[i]),Double.valueOf(scrapValueRates[i])/100,f, adjust_rate);
					}		
				}else if(method_no.equals("06")){//## 利率代码差额法
					ftp_price=FtpUtil.getFTPPrice_lldmcef(term, f, adjust_rate);
				}else if(method_no.equals("07")){//## 加权利率法
					ftp_price=FtpUtil.getFTPPrice_jqllf(f, adjust_rate);
				}else if(method_no.equals("08")){//## 固定利差法
					if(ftp_business_info.getPrdtNo().startsWith("P1")){//资产产品：FTP价格=客户端收益-固定利差值
						ftp_price=Double.parseDouble(ftp_business_info.getRate())/100-appoint_delta_rate+adjust_rate;
					}else{//负债产品：FTP价格=客户端成本+固定利差值
						ftp_price=Double.parseDouble(ftp_business_info.getRate())/100+appoint_delta_rate+adjust_rate;
					}
					//ftp_price=Double.parseDouble(ftp_business_info.getRate())+appoint_delta_rate+adjust_rate;
				}else{
					ftp_business_info.setMethodName("方法"+method_no+"配置错误");
					ftped_data_errorList.add(ftp_business_info);
					ftp_fault_num++;
					continue;
				}
				
				//FTP调整
				double adjustValue = 0;
				//是否金额调整=‘是’，且是贷款
				if(ftp_methodComb[8].equals("1")){
					double amt = (ftp_business_info.getAmt()==null||ftp_business_info.getAmt().equals(""))?Double.NaN:Double.valueOf(ftp_business_info.getAmt());
					double rate = (ftp_business_info.getRate()==null||ftp_business_info.getRate().equals(""))?Double.NaN:Double.valueOf(ftp_business_info.getRate())/100;
					adjustValue = FtpUtil.getDkAmtAdjust(ftp_business_info.getPrdtNo(), term, amt, dkAdjustArr, publicRate,rate);
				}else {//不进行金额调整
					//使用存贷款收益率曲线的产品
					if(curve_no.equals("0100")) {
						adjustValue = FtpUtil.getCdkFtpAdjustValue(ftp_business_info.getBusinessNo(), term, ckzbjAdjustMap, ldxAdjustMap, irAdjustMap, prepayList);
					}else if(curve_no.startsWith("02")) {//使用市场收益率曲线 +流动性风险加点+敞口占用加点
						adjustValue += Double.valueOf(ftp_methodComb[7])+Double.valueOf(ftp_methodComb[8]);
					}else{//其他暂时什么也不做
						
					}
					//策略调整，根据产品获取对应的数据
					adjustValue += clAdjustMap.get(ftp_business_info.getPrdtNo()) == null ? 0 : clAdjustMap.get(ftp_business_info.getPrdtNo());
				}
				ftp_price += adjustValue;
				
				ftp_business_info.setFtpPrice(ftp_price);
				ftp_business_info.setMethodNo(method_no);
				ftp_business_info.setMethodName(FtpUtil.getMethodName_byMethodNo(method_no));////
				ftp_business_info.setCurveNo(curve_no);
				ftp_business_info.setWrkDate(date);
				if(Double.isNaN(ftp_price)){
					ftped_data_errorList.add(ftp_business_info);
					ftp_fault_num++;
				}else{
					double jc = ftp_business_info.getPrdtNo().substring(0, 2).equals("P2")?(ftp_price-Double.valueOf(rates[i])/100):(Double.valueOf(rates[i])/100-ftp_price);
					if(!mtrDates[i].equals("/"))//活期和现金，没有总经济利润
						ftp_business_info.setTotalProfit(Double.valueOf(amts[i])*term*jc/365);
					String endDate = sysdate.substring(0,4)+"1231";//年末日期
					if(mtrDates[i].equals("/")) mtrDates[i] = endDate;//活期和现金，不考虑到期日期，直接按年末-开始日期计算
					if(CommonFunctions.daysSubtract(endDate, mtrDates[i]) > 0) {//如果到期日期在今年以内，则当年经济利润=总经济利润
						ftp_business_info.setCurYearProfit(ftp_business_info.getTotalProfit());
					}else {//如果到期日期超过今年，则期限=年末日期-开始日期，重新计算
						int term1 = CommonFunctions.daysSubtract(endDate, opnDates[i]);
						ftp_business_info.setCurYearProfit(Double.valueOf(amts[i])*term1*jc/365);
					}
					ftped_data_successList.add(ftp_business_info);
					ftp_success_num++;
				}														
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		ftp_total_num=ftp_fault_num+ftp_success_num;
		String ftpResultDescribe="定价完成！总共选中"+ftp_total_num+"笔；其中成功定价"+ftp_success_num+"笔，失败"+ftp_fault_num+"笔！";
		request.getSession().setAttribute("ftped_data_successList", ftped_data_successList);
		request.getSession().setAttribute("ftped_data_errorList", ftped_data_errorList);
		request.getSession().setAttribute("date", date);
		request.setAttribute("ftpResultDescribe", ftpResultDescribe);
		return "compute_result";
	 }

	public String getPrdtNo() {
		return prdtNo;
	}

	public void setPrdtNo(String prdtNo) {
		this.prdtNo = prdtNo;
	}

	public String getBrNo() {
		return brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getBrName() {
		return brName;
	}

	public void setBrName(String brName) {
		this.brName = brName;
	}

	public String getCurNo() {
		return curNo;
	}

	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}

	public String getCurName() {
		return curName;
	}

	public void setCurName(String curName) {
		this.curName = curName;
	}

	public String getBusinessNo() {
		return businessNo;
	}

	public void setBusinessNo(String businessNo) {
		this.businessNo = businessNo;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getProductNo() {
		return productNo;
	}

	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getOpnDate() {
		return opnDate;
	}

	public void setOpnDate(String opnDate) {
		this.opnDate = opnDate;
	}


	public String getMtrDate() {
		return mtrDate;
	}

	public void setMtrDate(String mtrDate) {
		this.mtrDate = mtrDate;
	}

	public String getHkTerm() {
		return hkTerm;
	}

	public void setHkTerm(String hkTerm) {
		this.hkTerm = hkTerm;
	}

	public String getZjTerm() {
		return zjTerm;
	}

	public void setZjTerm(String zjTerm) {
		this.zjTerm = zjTerm;
	}

	public String getManageLvl() {
		return manageLvl;
	}

	public void setManageLvl(String manageLvl) {
		this.manageLvl = manageLvl;
	}

	public String getScrapValueRate() {
		return scrapValueRate;
	}

	public void setScrapValueRate(String scrapValueRate) {
		this.scrapValueRate = scrapValueRate;
	}


}
