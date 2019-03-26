package com.dhcc.ftp.action.sjgl;
/**
 * @desc:市场公共利率维护Action
 * @author :孙红玉
 * @date ：2012-03-26
 */
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.action.BoBuilder;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.FtpPublicRate;

public class SCGGLLWHAction extends BoBuilder {

    private String rateId;
    private String rateNo;
    private String rate;
    private String adDate;
    private String floatPercent;
    private String floatPercentDg;
    private List<FtpPublicRate> list = null;
    HttpServletRequest request = getRequest();
	DaoFactory df = new DaoFactory();
	
    
    public String execute() throws Exception {
		return super.execute();
	}
    public String List() throws Exception {
    	String hsql = "from FtpPublicRate order by rateId";
    	list = df.query(hsql, null);
		request.setAttribute("list", list);
		return "List";
    }
    public String Query() throws Exception {
		String hsql = "from FtpPublicRate where rateId = '"+rateId+"'";
		FtpPublicRate ftpPublicRate = (FtpPublicRate)df.getBean(hsql, null);
		request.setAttribute("ftpPublicRate", ftpPublicRate);
		return "Edit";
    }
    public String save() throws Exception {
    	FtpPublicRate ftpPublicRate = new FtpPublicRate();
    	ftpPublicRate.setRateId(rateId);
    	ftpPublicRate.setRateNo(rateNo);
    	ftpPublicRate.setRate(Double.valueOf(rate)/100);
    	ftpPublicRate.setAdDate(adDate);
    	if(floatPercent==null||floatPercent.equals("")){
    		floatPercent = "0";
    	}
    	if(floatPercentDg==null||floatPercentDg.equals("")){
    		floatPercentDg = "0";
    	}
    	ftpPublicRate.setFloatPercent(Double.valueOf(floatPercent)/100);
    	ftpPublicRate.setFloatPercentDg(Double.valueOf(floatPercentDg)/100);
    	//存期赋值：对于存款才赋值，且rateNo固定后存期就固定
    	if(rateNo.equals("2M0")){
    		ftpPublicRate.setCq(0);
    	}else if(rateNo.equals("2D1")){
    		ftpPublicRate.setCq(1);
    	}else if(rateNo.equals("2D7")){
    		ftpPublicRate.setCq(7);
    	}else if(rateNo.equals("2M3")){
    		ftpPublicRate.setCq(63);
    	}else if(rateNo.equals("2M6")){
    		ftpPublicRate.setCq(66);
    	}else if(rateNo.equals("2Y1")){
    		ftpPublicRate.setCq(81);
    	}else if(rateNo.equals("2Y2")){
    		ftpPublicRate.setCq(82);
    	}else if(rateNo.equals("2Y3")){
    		ftpPublicRate.setCq(83);
    	}else if(rateNo.equals("2Y5")){
    		ftpPublicRate.setCq(85);
    	}
    	
		df.update(ftpPublicRate);
    	return null;
    }
	public String getRateId() {
		return rateId;
	}
	public void setRateId(String rateId) {
		this.rateId = rateId;
	}
	public String getRateNo() {
		return rateNo;
	}
	public void setRateNo(String rateNo) {
		this.rateNo = rateNo;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getAdDate() {
		return adDate;
	}
	public void setAdDate(String adDate) {
		this.adDate = adDate;
	}
	public List<FtpPublicRate> getList() {
		return list;
	}
	public void setList(List<FtpPublicRate> list) {
		this.list = list;
	}
	public String getFloatPercent() {
		return floatPercent;
	}
	public void setFloatPercent(String floatPercent) {
		this.floatPercent = floatPercent;
	}
	public String getFloatPercentDg() {
		return floatPercentDg;
	}
	public void setFloatPercentDg(String floatPercentDg) {
		this.floatPercentDg = floatPercentDg;
	}

}
