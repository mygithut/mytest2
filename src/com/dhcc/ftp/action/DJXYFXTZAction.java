package com.dhcc.ftp.action;
/**
 * @desc:数据管理：定价信用风险调整Action
 * @author :sss
 * @date ：2013-09-08
 */
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.Ftp1PrdtIrAdjust;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.DateUtil;
import com.dhcc.ftp.util.IDUtil;

public class DJXYFXTZAction extends BoBuilder {

	private String ajustId;
	private String brNo;
	private String adjustValue;
	private String lastModifyTime;
	private String lastModifyTelNo;
    HttpServletRequest request = getRequest();
	DaoFactory df = new DaoFactory();
//	private String[] custCreditLvl = new String[]{"01","02","03","04","05","06","01","02","03","04"};
    
    public String execute() throws Exception {
		return super.execute();
	}
    public String List() throws Exception {
        String adjustValue[] =null;
    	String hql = "from Ftp1PrdtIrAdjust where 1=1 ";
		hql+=" order by custType,custCreditLvl";
		List<Ftp1PrdtIrAdjust> ftpPrdtAdjustList= df.query(hql, null);
		adjustValue = new String[ftpPrdtAdjustList.size()];
		for(int i=0;i<ftpPrdtAdjustList.size();i++){
			Ftp1PrdtIrAdjust po = ftpPrdtAdjustList.get(i);
			adjustValue[i]=String.valueOf(po.getAdjustValue()*10000.0);
//			for(int j=0;j<custCreditLvl.length;j++){
//				if(po.getCustCreditLvl().equals(custCreditLvl[j])){
//					adjustValue[j]=String.valueOf(po.getAdjustValue()*10000.0);
//				}
//			}
		}
		
			   
		request.setAttribute("adjustValue", adjustValue);
		return "List";
    }
    
    public String save() throws Exception {
        String[] rates = adjustValue.split(",");
        String hsql2 = "delete from Ftp1PrdtIrAdjust ";
        df.delete(hsql2, null);
        TelMst telmst = (TelMst) request.getSession().getAttribute("userBean");
        lastModifyTelNo = telmst.getTelNo();
        brNo = null;//telmst.getBrMst().getBrNo()
        lastModifyTime = DateUtil.getCurrentDay()+DateUtil.getCurrentTime();

        
    	for(int i=0;i<rates.length;i++){
    		Ftp1PrdtIrAdjust po = new Ftp1PrdtIrAdjust();
    		po.setAjustId(IDUtil.getInstanse().getUID());
    		po.setBrNo(brNo);
    		po.setLastModifyTelNo(lastModifyTelNo);
    		po.setLastModifyTime(lastModifyTime);
    		if(rates[i]==null||rates[i].equals("")||rates[i]==""||rates[i].equals("null")){
    			po.setAdjustValue(new Double("0.0"));
    		}else{
    			po.setAdjustValue(Double.parseDouble(rates[i])/10000.0);
    		}
//    		po.setCustCreditLvl(custCreditLvl[i]);
    		if(i<6){
    			po.setCustType("01");
    			po.setCustCreditLvl("0"+(i+1));
    		}else{
    			po.setCustType("02");
    			po.setCustCreditLvl("0"+(i-5));
    		}
    		df.insert(po);
         }
    	return null;
    }

	public DaoFactory getDf() {
		return df;
	}
	public void setDf(DaoFactory df) {
		this.df = df;
	}
	public String getAjustId() {
		return ajustId;
	}
	public void setAjustId(String ajustId) {
		this.ajustId = ajustId;
	}
	public String getAdjustValue() {
		return adjustValue;
	}
	public void setAdjustValue(String adjustValue) {
		this.adjustValue = adjustValue;
	}
	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}
	public String getLastModifyTime() {
		return lastModifyTime;
	}
	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}
	public String getLastModifyTelNo() {
		return lastModifyTelNo;
	}
	public void setLastModifyTelNo(String lastModifyTelNo) {
		this.lastModifyTelNo = lastModifyTelNo;
	}
}
