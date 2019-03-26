package com.dhcc.ftp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.Ftp1PrdtCkzbjAdjust;
import com.dhcc.ftp.entity.FtpPublicRate;
import com.dhcc.ftp.entity.FtpYieldCurve;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.IDUtil;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @author yl
 */
public class CKZZJTZAction extends BoBuilder implements ModelDriven<Ftp1PrdtCkzbjAdjust> {
	private Ftp1PrdtCkzbjAdjust po;
	DaoFactory df = new DaoFactory();
	private String paramUrl;
	
	List<Ftp1PrdtCkzbjAdjust> pos =new ArrayList<Ftp1PrdtCkzbjAdjust>();

	
	
	/**
	 * 查询列表
	 * @return
	 * @throws Exception
	 */
	public String getList() throws Exception {
		String hsql = "from Ftp1PrdtCkzbjAdjust h  where 1=1 ";//substr(shiborTerm,2,1),substr(shiborTerm,1,1)";
		
		hsql+=" order by h.termType ";
		List<Ftp1PrdtCkzbjAdjust> list=df.query(hsql, null);
		Map<String, Ftp1PrdtCkzbjAdjust> map=new HashMap<String, Ftp1PrdtCkzbjAdjust>();
	/*	if(list.size()==0){
			//没有查到实体类
			//map=this.getLstFtpYield(po);
		}else{*/
			//查到实体类
			for (Ftp1PrdtCkzbjAdjust ftpPrdtCkzbjAdjust : list) {
				map.put(String.valueOf(ftpPrdtCkzbjAdjust.getTermType()), ftpPrdtCkzbjAdjust);
			}
		/*}*/
		this.getRequest().setAttribute("CKMap", map);
		return "List";
	}
	
	/**
	  * 保存
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
			TelMst telmst = (TelMst) this.getRequest().getSession().getAttribute("userBean"); 
			//String brNo=FtpUtil.getXlsBrNo(telmst.getBrMst().getBrNo(), telmst.getBrMst().getManageLvl());
			for (Ftp1PrdtCkzbjAdjust temp : pos) {
				if(pos.get(0).getAdjustId()!=null&&"".equals(pos.get(0).getAdjustId()!=null)){
					Ftp1PrdtCkzbjAdjust paramPo= this.getQuery(temp.getAdjustId());
					paramPo.setAdjustValue(temp.getAdjustValue()/10000);
					//paramPo.setBrNo(brNo);
					df.update(paramPo);
				}else{
					temp.setAdjustValue(temp.getAdjustValue()/10000);
					//temp.setBrNo(brNo);
					temp.setAdjustId(IDUtil.getInstanse().getUID());
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
					String now=sdf.format(new Date())+"01";
					temp.setAdjustDate(now);
					df.insert(temp);
				}
			}
	    	return "List";
	  }
	/**
	 * 查询detail
	 * @return
	 * @throws Exception
	 */
	private Ftp1PrdtCkzbjAdjust getQuery(String id) throws Exception {
		String hsql = "from FtpPrdtCkzbjAdjust where adjustId= "+id;
		Ftp1PrdtCkzbjAdjust adjust = (Ftp1PrdtCkzbjAdjust)df.getBean(hsql, null);
		/*request.setAttribute("ftpInterestMarginDivide", ftpInterestMarginDivide);
		return "detail";*/
		return adjust;
	}
	
	/**
	 * 通过查询FtpYieldCurve 获取所有 FtpPrdtCkzbjAdjust 的值
	 * @param paramPo
	 * @return
	 * @throws Exception
	 */
	private Map<String, Ftp1PrdtCkzbjAdjust> getLstFtpYield(Ftp1PrdtCkzbjAdjust paramPo) throws Exception{
		String hsql = "from FtpPrdtCkzbjAdjust h  where 1=1 ";//substr(shiborTerm,2,1),substr(shiborTerm,1,1)";
		if(paramPo.getAdjustDate()!=null&&!"".equals(paramPo.getAdjustDate())&&!"null".equals(paramPo.getAdjustDate())){
			hsql+=" and h.curveDate = '"+paramPo.getAdjustDate()+"'";
		}
		hsql+=" order by h.termType ";
		List<FtpYieldCurve> list=df.query(hsql, null);
		Map<String, Ftp1PrdtCkzbjAdjust> map=new HashMap<String, Ftp1PrdtCkzbjAdjust>();
		for (FtpYieldCurve ftpYieldCurve : list) {
			Ftp1PrdtCkzbjAdjust value=new Ftp1PrdtCkzbjAdjust();
//			value.setAdjustValue(this.getFtpValue(ftpYieldCurve.getFtp()));
//			value.setTermType(ftpYieldCurve.getTermType());
//			map.put(ftpYieldCurve.getTermType(), value);
		}
		return map;
	}
	
	
	/**
	 * 计算adjustValue的值
	 * @param param
	 * @return
	 * @throws Exception 
	 */
	private double getFtpValue(double param) throws Exception{
		return (param-this.getFtpPublicRateByRateNo("4"))*this.getFtpPublicRateByRateNo("3")/10000;
	}
	/**
	 * 获取
	 * @param rateNo
	 * @return
	 * @throws Exception
	 */
	private double getFtpPublicRateByRateNo(String rateNo) throws Exception{
		String hsql ="from FtpPublicRate  where  1=1 and rateNo ="+rateNo;
		FtpPublicRate rate =(FtpPublicRate) df.getBean(hsql, null);
		return rate.getRate();
	}
	
	
	public Ftp1PrdtCkzbjAdjust getPo() {
		return po;
	}



	public void setPo(Ftp1PrdtCkzbjAdjust po) {
		this.po = po;
	}

	public List<Ftp1PrdtCkzbjAdjust> getPos() {
		return pos;
	}

	public void setPos(List<Ftp1PrdtCkzbjAdjust> pos) {
		this.pos = pos;
	}

	public Ftp1PrdtCkzbjAdjust getModel() {
		if(this.po==null){
			this.po=new Ftp1PrdtCkzbjAdjust();
		}
		return this.po;
	}

	public String getParamUrl() {
		return paramUrl;
	}

	public void setParamUrl(String paramUrl) {
		this.paramUrl = paramUrl;
	}



}
