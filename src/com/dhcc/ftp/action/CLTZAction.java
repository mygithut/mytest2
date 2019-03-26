package com.dhcc.ftp.action;

import java.net.URLDecoder;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.Ftp1PrdtClAdjust;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.DateUtil;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.IDUtil;
import com.opensymphony.xwork2.ModelDriven;
/**
 * @author yl
 */
public class CLTZAction extends BoBuilder implements ModelDriven<Ftp1PrdtClAdjust>{
	private Ftp1PrdtClAdjust po;
	private String reqUrlParam;
	DaoFactory df = new DaoFactory();
	private List<Ftp1PrdtClAdjust> pos=new ArrayList<Ftp1PrdtClAdjust>();
/*	private int pageSize = 10;
	private int rowsCount = -1;
	private int page = 1;
	private HttpServletRequest request = getRequest();
	private String savePath = null;*/

	
	/**
	 * 查询列表
	 * @return
	 * @throws Exception
	 */
	public String getList() throws Exception {
		this.getResponse().setCharacterEncoding("utf-8");
		List newList = new ArrayList();
		String sql="select " +
				"  t1.BUSINESS_NAME," +
				"  t1.PRODUCT_NAME," +
				"  t1.PRODUCT_NO," +
				"  t2.ADJUST_ID," +
				"  t2.ADJUST_VALUE," +
				"  t1.BR_NO" +
				"    from FTP.FTP_PRODUCT_METHOD_REL t1  " +
				"    left join   FTP.FTP1_PRDT_CL_ADJUST  t2 on t1.PRODUCT_NO=t2.PRODUCT_NO   " +
				"   where  1=1" ;

		String sqlAdd="select " +
				"  t1.BUSINESS_NAME," +
				"  t1.PRODUCT_NAME," +
				"  t1.PRODUCT_NO," +
				"  t2.ADJUST_ID," +
				"  t2.ADJUST_VALUE," +
				"  t1.BR_NO" +
				"    from FTP.FTP_PRODUCT_METHOD_REL t1  " +
				"    left join   FTP.FTP1_PRDT_CL_ADJUST  t2 on t1.PRODUCT_NO=t2.PRODUCT_NO   " +
				"   where  1=1" ;
		
		if(po.getProductNo()!=null&&!"".equals(po.getProductNo())&&!"null".equals(po.getProductNo())){
			sql+=" and t1.PRODUCT_NO = '"+po.getProductNo()+"'";
			sqlAdd+=" and t1.PRODUCT_NO = '"+po.getProductNo()+"'";
		}
		if(po.getBusinessNo()!=null&&!"".equals(po.getBusinessNo())&&!"null".equals(po.getBusinessNo())){
			sql+=" and t1.BUSINESS_NO = '"+po.getBusinessNo()+"'";
			sqlAdd+=" and t1.PRODUCT_NO = '"+po.getProductNo()+"'";
		}
		if(po.getBrNo()!=null&&!"".equals(po.getBrNo())&&!"null".equals(po.getBrNo())){
			sql+=" and t1.BR_NO = '"+po.getBrNo()+"'";
			sql+=" and t2.BR_NO = '"+po.getBrNo()+"'";
			sqlAdd+=" and t1.BR_NO = '"+po.getBrNo()+"'";
		}
		sql+="   order by t1.BUSINESS_NO ,t1.PRODUCT_NO  ";
		sqlAdd+="   order by t1.BUSINESS_NO ,t1.PRODUCT_NO  ";
		List list=df.query1(sql, null);
		if(list.size()<1){
			list=df.query1(sqlAdd, null);
			for(Object object:list){
			  Object[]	objects= (Object[]) object;
			  objects[3]=null;
			  newList.add(objects);
			}
			this.getRequest().setAttribute("clList", newList);
		}else {
			this.getRequest().setAttribute("clList", list);
		}
		this.getRequest().setAttribute("clPo", po);
		return "List";
	}
	
	/**
	 * 查询detail
	 * @return
	 * @throws Exception
	 */
	private Ftp1PrdtClAdjust getQuery(String id) throws Exception {
		String hsql = "from Ftp1PrdtClAdjust where adjustId = '"+id +"'";
		Ftp1PrdtClAdjust clAdjust = (Ftp1PrdtClAdjust)df.getBean(hsql, null);
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
		String now=DateUtil.getCurrentDay()+DateUtil.getCurrentTime();
		String businessNo=this.getRequest().getParameter("param2");
		String productNo=this.getRequest().getParameter("param1");
		String brNo=this.getRequest().getParameter("param3");
		po.setBusinessNo(businessNo);
		po.setProductNo(productNo);
		po.setBrNo(brNo);
		String message="";
		boolean flag=false;
		for (Ftp1PrdtClAdjust temp : pos) {
			
			if(temp.getAdjustId()!=null&&!"".equals(temp.getAdjustId())&&!"null".equals(temp.getAdjustId())){
				Ftp1PrdtClAdjust paramPo= this.getQuery(temp.getAdjustId());
				paramPo.setAdjustValue(temp.getAdjustValue()==null?0:temp.getAdjustValue()/10000);
				paramPo.setModifyTime(now);
				paramPo.setModifyTelno(telmst.getTelNo());
				flag=df.update(paramPo);
				if(!flag){
					message="["+paramPo.getAdjustId()+"]";
				}
			}else{
				temp.setAdjustValue(temp.getAdjustValue()==null?0:temp.getAdjustValue()/10000);
				temp.setAdjustId(IDUtil.getInstanse().getUID());
				temp.setModifyTime(now);
				temp.setModifyTelno(telmst.getTelNo());
				flag=df.insert(temp);
				if(!flag){
					message="["+temp.getAdjustId()+"]";
				}
			}
			
		}
		if("".equals(message)){
			this.getRequest().setAttribute("message", "ok");
		}else{
			this.getRequest().setAttribute("message", message);
		}
		this.getRequest().setAttribute("clPo", po);
    	return "tosave";
	}
	
	
	public Ftp1PrdtClAdjust getModel() {
		if(po==null){
			po=new Ftp1PrdtClAdjust();
		}
		return po;
	}
	public Ftp1PrdtClAdjust getPo() {
		return po;
	}
	public void setPo(Ftp1PrdtClAdjust po) {
		this.po = po;
	}
	public List<Ftp1PrdtClAdjust> getPos() {
		return pos;
	}
	public void setPos(List<Ftp1PrdtClAdjust> pos) {
		this.pos = pos;
	}
	public String getReqUrlParam() {
		return reqUrlParam;
	}
	public void setReqUrlParam(String reqUrlParam) {
		this.reqUrlParam = reqUrlParam;
	}



}
