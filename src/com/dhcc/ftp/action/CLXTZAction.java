/**
 * 
 */
package com.dhcc.ftp.action;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.util.PageUtil;

/**
 * @author Administrator
 * 
 */
public class CLXTZAction extends BoBuilder {
	private static final Logger logger=Logger.getLogger(DaoFactory.class);
	private String flag;
	private String poolName;
	private String brName;
	private String curNo;
	private PageUtil FtpResultUtil = null;
	private int page = 1;

	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = getRequest();
		DaoFactory df = new DaoFactory();
		String nature = request.getParameter("nature") == null ? "¹ÄÀøÐÔ": new String(request.getParameter("nature").getBytes("iso8859-1"), "utf-8");
		
		if (flag.equals("query")) {
			FtpResultUtil = ftpResultBO.dofind(page,poolName,brName,curNo);
			request.setAttribute("FtpResultUtil", FtpResultUtil);
			request.setAttribute("nature", nature);
			return flag;
		}
		if (flag.equals("update")) {
			String value=request.getParameter("value");
			String resultIds=request.getParameter("resultId");
			String[] resultId=resultIds.substring(0, resultIds.length()-1).split(",");
			String [] afresValue=value.substring(0, value.length()-1).split(",");
			int j=0;
			for(int i=0;i<afresValue.length;i++){
				if(afresValue[i]!=null&&!afresValue[i].equals("")){
			String hsql = "update FtpResult set afresValue='"+afresValue[i]+"' where result_Id='" + resultId[i] + "'";
	df.update(hsql, null);
				}
				j++;
			}
			for(int i=j;i<resultId.length;i++){
					String hsql = "update FtpResult set afresValue='' where result_Id='" + resultId[i] + "'";
					df.update(hsql, null);	
			}
			FtpResultUtil = ftpResultBO.dofind(page,poolName,brName,curNo);
			request.setAttribute("FtpResultUtil", FtpResultUtil);
			request.setAttribute("nature", nature);
			return flag;
		}
			return null;
}
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PageUtil getFtpResultUtil() {
		return FtpResultUtil;
	}

	public void setFtpResultUtil(PageUtil ftpResultUtil) {
		FtpResultUtil = ftpResultUtil;
	}


	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getBrName() {
		return brName;
	}


	public void setBrName(String brName)  throws UnsupportedEncodingException {
		this.brName = new String(brName.getBytes("iso-8859-1"),"UTF-8");
	}


	public String getCurNo() {
		return curNo;
	}


	public void setCurNo(String curNo) {
		this.curNo = curNo;
	}


	public String getPoolName() {
		return poolName;
	}

	public void setPoolName(String poolName)throws UnsupportedEncodingException  {
		this.poolName = new String(poolName.getBytes("iso-8859-1"),"UTF-8");
	}
}
