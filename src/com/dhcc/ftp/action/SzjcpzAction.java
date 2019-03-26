package com.dhcc.ftp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.util.PageUtil;

/**
 * <p>
 * Title: DjclpzAction 双资金池配置
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
 * @date Oct 17, 2012 9:47:54 AM
 * 
 * @version 1.0
 */
public class SzjcpzAction extends BoBuilder {
	
	private PageUtil ftpPoolInfoUtil = null;
	private int page = 1;
	private String brNo;
	private String poolType;
	private String outContentObject;
	private String poolNo;
	private String[] poolNos;
	private String[] poolNames;
	private String[] poolTypes;
	private String[] contentObjects;
	private int pageSize = 10;
	private int rowsCount = -1;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpServletResponse response = ServletActionContext.getResponse();
	DaoFactory df = new DaoFactory();
	
	public String execute() throws Exception {
		//return super.execute();
		return null;
	}
	
	//获取机构对应的资金池信息以及产品信息
	public String list() throws Exception {
		ftpPoolInfoBO.getFtpPoolInfo(request, brNo, "2");
		return "list";
	}
	
	//获取对应的产品
	public String getPrdt() throws Exception {
		ftpPoolInfoBO.getBusinessPrdt(request, poolType, outContentObject);
		request.setAttribute("poolNo", poolNo);
		return "prdtList";
	}
	
	//保存
	public String save() throws Exception {
		ftpPoolInfoBO.saveFtpPoolInfo(request, brNo, "2",  poolNos, poolNames, poolTypes, contentObjects);
		return null;
	}
	public PageUtil getFtpPoolInfoUtil() {
		return ftpPoolInfoUtil;
	}
	public void setFtpPoolInfoUtil(PageUtil ftpPoolInfoUtil) {
		this.ftpPoolInfoUtil = ftpPoolInfoUtil;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getBrNo() {
		return brNo;
	}
	public void setBrNo(String brNo) {
		this.brNo = brNo;
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
	public String getPoolType() {
		return poolType;
	}
	public void setPoolType(String poolType) {
		this.poolType = poolType;
	}

	public String getPoolNo() {
		return poolNo;
	}

	public void setPoolNo(String poolNo) {
		this.poolNo = poolNo;
	}

	public String getOutContentObject() {
		return outContentObject;
	}

	public void setOutContentObject(String outContentObject) {
		this.outContentObject = outContentObject;
	}

	public String[] getPoolNames() {
		return poolNames;
	}

	public void setPoolNames(String[] poolNames) {
		this.poolNames = poolNames;
	}

	public String[] getPoolTypes() {
		return poolTypes;
	}

	public void setPoolTypes(String[] poolTypes) {
		this.poolTypes = poolTypes;
	}

	public String[] getContentObjects() {
		return contentObjects;
	}

	public void setContentObjects(String[] contentObjects) {
		this.contentObjects = contentObjects;
	}

	public String[] getPoolNos() {
		return poolNos;
	}

	public void setPoolNos(String[] poolNos) {
		this.poolNos = poolNos;
	}


	
//	public String save() throws Exception {
//		TelMst bean = (TelMst)getSession().getAttribute("userBean");
//		ftpSystemInitialBO.save(brNo, setResult, bean);
//		return null;
//	}
	

}
