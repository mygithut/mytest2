package com.dhcc.ftp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.Ftp1PrdtLdxAdjust;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.DateUtil;
import com.dhcc.ftp.util.IDUtil;
import com.opensymphony.xwork2.ModelDriven;

public class LDXTZAction  extends BoBuilder implements ModelDriven<Ftp1PrdtLdxAdjust>{
	private Ftp1PrdtLdxAdjust po;
	DaoFactory df = new DaoFactory();
	private List<Ftp1PrdtLdxAdjust> pos=new ArrayList<Ftp1PrdtLdxAdjust>();
	
	
	/**
	 * 查询列表
	 * @return
	 * @throws Exception
	 */
	public String getList() throws Exception {
		this.getResponse().setCharacterEncoding("utf-8");
		
		String hsql = "from Ftp1PrdtLdxAdjust h  where 1=1 ";
		//hsql+=" order by h.termType ";
		List<Ftp1PrdtLdxAdjust> list=(List<Ftp1PrdtLdxAdjust>)df.query(hsql, null);
		Map<String, Ftp1PrdtLdxAdjust> map=new HashMap<String, Ftp1PrdtLdxAdjust>();
			//查到实体类
		for (Ftp1PrdtLdxAdjust ldxAdjust : list) {
			map.put(String.valueOf(ldxAdjust.getTermType()), ldxAdjust);
		}
		this.getRequest().setAttribute("LDMap", map);
		return "List";
	}
	
	
	/**
	 * 查询detail
	 * @return
	 * @throws Exception
	 */
	private Ftp1PrdtLdxAdjust getQuery(String id) throws Exception {
		String hsql = "from Ftp1PrdtLdxAdjust where adjustId = "+id;
		Ftp1PrdtLdxAdjust clAdjust = (Ftp1PrdtLdxAdjust)df.getBean(hsql, null);
		/*request.setAttribute("ftpInterestMarginDivide", ftpInterestMarginDivide);
		return "detail";*/
		return clAdjust;
	}
	
	
	
	 /**
	  * 保存
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		TelMst telmst = (TelMst) this.getRequest().getSession().getAttribute("userBean"); 
		String now=String.valueOf(CommonFunctions.GetDBSysDate());
		now=DateUtil.getCurrentDay()+DateUtil.getCurrentTime();
		for (Ftp1PrdtLdxAdjust temp : pos) {
			if(temp.getAdjustId()!=null&&!"".equals(temp.getAdjustId())){
				Ftp1PrdtLdxAdjust paramPo= this.getQuery(temp.getAdjustId());
				paramPo.setLastModifyTime(now);
				paramPo.setLastModifyTelno(telmst.getTelNo());
				paramPo.setAdjustValue(temp.getAdjustValue()==null?0:temp.getAdjustValue()/10000);
				df.update(paramPo);
			}else{
				temp.setAdjustValue(temp.getAdjustValue()==null?0:temp.getAdjustValue()/10000);
				temp.setAdjustId(IDUtil.getInstanse().getUID());
				temp.setLastModifyTime(now);
				temp.setLastModifyTelno(telmst.getTelNo());
				df.insert(temp);
			}
		}
		/*adjustDate= now;
		productNo=po.getProductNo();*/
    	return "tosave";
	}
	
	public Ftp1PrdtLdxAdjust getPo() {
		return po;
	}

	public void setPo(Ftp1PrdtLdxAdjust po) {
		this.po = po;
	}
	public List<Ftp1PrdtLdxAdjust> getPos() {
		return pos;
	}

	public void setPos(List<Ftp1PrdtLdxAdjust> pos) {
		this.pos = pos;
	}

	public Ftp1PrdtLdxAdjust getModel() {
		if(po==null){
			po=new Ftp1PrdtLdxAdjust();
		}
		return po;
	}



	
}
