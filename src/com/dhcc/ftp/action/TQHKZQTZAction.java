package com.dhcc.ftp.action;
/**
 * @desc:数据管理：提前还款/支取调整Action
 * @author :sss
 * @date ：2013-09-07
 */
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.Ftp1PrepayAdjust;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.DateUtil;
import com.dhcc.ftp.util.IDUtil;

public class TQHKZQTZAction extends BoBuilder {

    private String repayId;
    private String adjustValue;
    private String brNo;
	private String lastModifyTime;
	private String lastModifyTelNo;
    HttpServletRequest request = getRequest();
	DaoFactory df = new DaoFactory();
	
    
    public String execute() throws Exception {
		return super.execute();
	}
    public String List() throws Exception {
        String repayId[] = null;
        String adjustValue[] =null;
    	String hql = "from Ftp1PrepayAdjust where 1=1 ";
		hql+=" order by assetsType,maxTermType desc ";
		List<Ftp1PrepayAdjust> ftpPrepayAdjustList= df.query(hql, null);
		repayId = new String[ftpPrepayAdjustList.size()];
		adjustValue = new String[ftpPrepayAdjustList.size()];
		for(int i=0;i<ftpPrepayAdjustList.size();i++){
			Ftp1PrepayAdjust po = ftpPrepayAdjustList.get(i);
			repayId[i]=po.getId();
			adjustValue[i]=String.valueOf(po.getAdjustValue()*10000.0);
		}
		request.setAttribute("repayId",repayId);
		request.setAttribute("adjustValue", adjustValue);   
		return "List";
    }
    
    public String save() throws Exception {
        String[] rates = adjustValue.split(",");
        String hsql2 = "delete from Ftp1PrepayAdjust ";
        df.delete(hsql2, null);
        TelMst telmst = (TelMst) request.getSession().getAttribute("userBean");
        lastModifyTelNo = telmst.getTelNo();
        brNo = null;//telmst.getBrMst().getBrNo()
        lastModifyTime = DateUtil.getCurrentDay()+DateUtil.getCurrentTime();
        Integer[] minTermType1 = new Integer[]{60,36,12,0};//贷款期限下线
        Integer[] maxTermType1 = new Integer[]{999,60,36,12};//贷款期限上线
        Integer[] minTermType2 = new Integer[]{60,36,24,12,0};//贷款期限下线
        Integer[] maxTermType2 = new Integer[]{999,60,36,24,12};//贷款期限上线

        
    	for(int i=0;i<rates.length;i++){
    		Ftp1PrepayAdjust po = new Ftp1PrepayAdjust();
    		po.setId(IDUtil.getInstanse().getUID());
    		if(i<4){
    		    po.setMaxTermType(maxTermType1[i]);
    		    po.setMinTermType(minTermType1[i]);
    		    po.setAssetsType("01");
    		}else{
    			po.setMaxTermType(maxTermType2[i-4]);
    			po.setMinTermType(minTermType2[i-4]);
    			po.setAssetsType("02");
    		}
    		if(rates[i]==null||rates[i].equals("")||rates[i]==""||rates[i].equals("null")){
    			po.setAdjustValue(new Double(0.0));
    		}else{
    			po.setAdjustValue(Double.parseDouble(rates[i])/10000.0);
    		}
    		po.setBrNo(brNo);
    		po.setLastModifyTelNo(lastModifyTelNo);
    		po.setLastModifyTime(lastModifyTime);
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
	public String getRepayId() {
		return repayId;
	}
	public void setRepayId(String repayId) {
		this.repayId = repayId;
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
