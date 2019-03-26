/**
 * 
 */
package com.dhcc.ftp.action.xtgl;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.action.BoBuilder;
import com.dhcc.ftp.dao.BrInfoDAO;
import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;

/**
 * @author Administrator
 * 
 */
public class BrMstAction extends BoBuilder {
	private int page = 1;
	private int pageSize = 10;
	private int rowsCount = -1;
	private BrInfoDAO brinfo = null;
	private String operate= null;  //判断请求的类型选择查询方式
	private String selectbrno = null;
	private PageUtil BrnoUtil = null;
	private String ficode = null;
	private String telno = null;
	private TelMst telmst = null;
	private String brNo;
	private String brName;
	private String chargePersonName;
	private String manageLvl;
	private String fbrCode;
	private String legalPersonFlag;
	private String conPersonName;
	private String conPhone;
	private String conAddr;
	private String postNo;
	private String brClFlag;
	private String superBrNo;
	private String fax;
	private String brf;
	private String ywjg;
	private String result = null;

	@Override
	public String execute() throws Exception {
		return super.execute();
	}
	public String list() throws Exception {
		String brSql=LrmUtil.getBrSql(brNo);
		String hsql = "from BrMst b where b.brNo "+brSql+" order by brNo";
		String pageName = "brmst_list.action?pageSize="+pageSize+"&brNo="+brNo;
		BrnoUtil = queryPageBO.queryPage(hsql, pageSize, page, rowsCount, pageName);
		getRequest().setAttribute("BrnoUtil", BrnoUtil);
		getRequest().setAttribute("brNo", brNo);
		return "list";
	}
	
	public String addBrno(){
		getRequest().setAttribute("brNo", brNo);
		return "addBrno";
	}	
	
	public String add() throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		// TODO Auto-generated method stub
		BrMst brmst = new BrMst();
		brmst.setBrNo(brNo);
		brmst.setBrName(brName);
		brmst.setChargePersonName(chargePersonName);
		int manageLvl=0;
		BrMst superBrmst = brInfoBo.getInfo(superBrNo);
		if (superBrmst != null && !superBrmst.equals("")) {
			manageLvl = Integer.valueOf(superBrmst.getManageLvl())-1;
		}
		brmst.setManageLvl(String.valueOf(manageLvl));
		brmst.setFbrCode(fbrCode);
		brmst.setConPersonName(conPersonName);
		brmst.setSuperBrNo(superBrNo);
		brmst.setConPhone(conPhone);
		brmst.setConAddr(conAddr);
		brmst.setPostNo(postNo);
		brmst.setFax(fax);
		brmst.setIsBusiness(ywjg);
		
		result = brInfoBo.addData(brmst);
		ServletActionContext.getRequest().setAttribute("brmst", brmst);
		resp.setContentType("text/plain;charset=UTF-8");
		if (result.equals("success")) {
			result = "1";
		}else {
			result = "2";
		}
		resp.getWriter().print(result);
		return null;
	}
	
	public String del() throws Exception {
		brInfoBo.remove(brNo);
		return null;
	}
	
	public String detail() throws Exception {
		BrMst brmst = brInfoBo.getInfo(brNo);
		getRequest().setAttribute("brmst", brmst);
		getRequest().setAttribute("brNo", superBrNo);
		return "detail";
	}
	
	public String update() {
		BrMst brmst = brInfoBo.getInfo(brNo);
		if (brmst.getLegalPersonFlag() == null ) brmst.setLegalPersonFlag("");
		if (brmst.getBrClFlag() == null ) brmst.setBrClFlag("");
		brmst.setBrName(brName);
		brmst.setChargePersonName(chargePersonName);
		brmst.setManageLvl(manageLvl);
		brmst.setFbrCode(fbrCode);
		brmst.setConAddr(conAddr);
		brmst.setPostNo(postNo);
		brmst.setConPersonName(conPersonName);
		brmst.setSuperBrNo(superBrNo);
		brmst.setConPhone(conPhone);
		brmst.setFax(fax);
		brmst.setIsBusiness(ywjg);
		brInfoBo.modify(brmst);
		return null;
	}
	
	public String getOperate() {
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

	public String getFicode() {
		return ficode;
	}

	public void setFicode(String ficode) {
		this.ficode = ficode;
	}

	public String getTelno() {
		return telno;
	}

	public void setTelno(String telno) {
		this.telno = telno;
	}

	public String getSelectbrno() {
		return selectbrno;
	}

	public void setSelectbrno(String selectbrno) {
		this.selectbrno = selectbrno;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public BrInfoDAO getBrinfo() {
		return brinfo;
	}

	public void setBrinfo(BrInfoDAO brinfo) {
		this.brinfo = brinfo;
	}

	public TelMst getTelmst() {
		return telmst;
	}

	public void setTelmst(TelMst telmst) {
		this.telmst = telmst;
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

	public String getChargePersonName() {
		return chargePersonName;
	}

	public void setChargePersonName(String chargePersonName) {
		this.chargePersonName = chargePersonName;
	}

	public String getManageLvl() {
		return manageLvl;
	}

	public void setManageLvl(String manageLvl) {
		this.manageLvl = manageLvl;
	}

	public String getFbrCode() {
		return fbrCode;
	}

	public void setFbrCode(String fbrCode) {
		this.fbrCode = fbrCode;
	}

	public String getLegalPersonFlag() {
		return legalPersonFlag;
	}

	public void setLegalPersonFlag(String legalPersonFlag) {
		this.legalPersonFlag = legalPersonFlag;
	}

	public String getConPersonName() {
		return conPersonName;
	}

	public void setConPersonName(String conPersonName) {
		this.conPersonName = conPersonName;
	}

	public String getConPhone() {
		return conPhone;
	}

	public void setConPhone(String conPhone) {
		this.conPhone = conPhone;
	}

	public String getConAddr() {
		return conAddr;
	}

	public void setConAddr(String conAddr) {
		this.conAddr = conAddr;
	}

	public String getPostNo() {
		return postNo;
	}

	public void setPostNo(String postNo) {
		this.postNo = postNo;
	}

	public String getBrClFlag() {
		return brClFlag;
	}

	public void setBrClFlag(String brClFlag) {
		this.brClFlag = brClFlag;
	}

	public String getSuperBrNo() {
		return superBrNo;
	}

	public void setSuperBrNo(String superBrNo) {
		this.superBrNo = superBrNo;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getBrf() {
		return brf;
	}

	public void setBrf(String brf) {
		this.brf = brf;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getRowsCount() {
		return rowsCount;
	}
	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}
	public PageUtil getBrnoUtil() {
		return BrnoUtil;
	}
	public void setBrnoUtil(PageUtil brnoUtil) {
		BrnoUtil = brnoUtil;
	}
	public String getYwjg() {
		return ywjg;
	}
	public void setYwjg(String ywjg) {
		this.ywjg = ywjg;
	}
}
