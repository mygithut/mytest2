package com.dhcc.ftp.action;

/**
 * @desc:数据管理：银行信用评级Action
 * @author :孙红玉
 * @date ：2011-08-29
 */
import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.FtpCreditRating;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.PageUtil;

public class UL05Action extends BoBuilder {

	private String ratingId;
	private String[] itemsList;
	private String ratingIdStr;
	private String ratingNo;
	private String creditRating;
	private Double pd;
	private Double lgd;
	private String creditType;
	private String ratingDate;
	private int page = 1;
	private PageUtil FtpCreditRatingUtil = null;
	DaoFactory df = new DaoFactory();

	public String execute() throws Exception {
		return super.execute();
	}

	public String list() throws Exception {

		HttpServletRequest request = getRequest();
		FtpCreditRatingUtil = ftpCreditRatingBO.dofind(page);
		request.setAttribute("FtpCreditRatingUtil", FtpCreditRatingUtil);
		return "list";
	}

	public String Query() throws Exception {
		String hsql = "from FtpCreditRating where ratingId = "+ratingId;
		FtpCreditRating ftpCreditRating = (FtpCreditRating)df.getBean(hsql, null);
		HttpServletRequest request = getRequest();
		request.setAttribute("ftpCreditRating", ftpCreditRating);
		return "Edit";
    }
    public String save() throws Exception {
    	if (ratingId != null && !ratingId.equals("")){
				FtpCreditRating ftpCreditRating = new FtpCreditRating();
				ftpCreditRating.setRatingId(ratingId);
				ftpCreditRating.setRatingNo(ratingNo);
				ftpCreditRating.setCreditRating(creditRating);
				ftpCreditRating.setPd(Double.valueOf(pd)/100);
				ftpCreditRating.setLgd(Double.valueOf(lgd)/100);
				ftpCreditRating.setCreditType(creditType);
				ftpCreditRating.setRatingDate(ratingDate);
				df.update(ftpCreditRating);
			} else {
				FtpCreditRating ftpCreditRating = new FtpCreditRating();
				ftpCreditRating.setRatingId(IDUtil.getInstanse().getUID());
				ftpCreditRating.setRatingNo(ratingNo);
				ftpCreditRating.setCreditRating(creditRating);
				ftpCreditRating.setPd(Double.valueOf(pd)/100);
				ftpCreditRating.setLgd(Double.valueOf(lgd)/100);
				ftpCreditRating.setCreditType(creditType);
				ftpCreditRating.setRatingDate(ratingDate);
				df.insert(ftpCreditRating);
			}
    	return null;
		}
		
	public String del() throws Exception {

		String[] ratingId = ratingIdStr.split(",");
		for(int i = 0; i < ratingId.length; i++){
			String hsql="delete from FtpCreditRating where ratingId="+ratingId[i]+"";
			df.delete(hsql, null);
		}
		return null;
	}
	
	
	public String getRatingId() {
		return ratingId;
	}

	public void setRatingId(String ratingId) {
		this.ratingId = ratingId;
	}

	public String[] getItemsList() {
		return itemsList;
	}

	public void setItemsList(String[] itemsList) {
		this.itemsList = itemsList;
	}

	public PageUtil getFtpCreditRatingUtil() {
		return FtpCreditRatingUtil;
	}

	public void setFtpCreditRatingUtil(PageUtil ftpCreditRatingUtil) {
		FtpCreditRatingUtil = ftpCreditRatingUtil;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getRatingIdStr() {
		return ratingIdStr;
	}

	public void setRatingIdStr(String ratingIdStr) {
		this.ratingIdStr = ratingIdStr;
	}

	public String getRatingNo() {
		return ratingNo;
	}

	public void setRatingNo(String ratingNo) {
		this.ratingNo = ratingNo;
	}

	public String getCreditRating() {
		return creditRating;
	}

	public void setCreditRating(String creditRating) {
		this.creditRating = creditRating;
	}

	public Double getPd() {
		return pd;
	}

	public void setPd(Double pd) {
		this.pd = pd;
	}

	public Double getLgd() {
		return lgd;
	}

	public void setLgd(Double lgd) {
		this.lgd = lgd;
	}

	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	public String getRatingDate() {
		return ratingDate;
	}

	public void setRatingDate(String ratingDate) {
		this.ratingDate = ratingDate;
	}

}
