package com.dhcc.ftp.action.sjgl;
/**
 * @desc:数据管理：普通债与金融债收益率点差维护Action
 * @author :sss
 * @date ：2013-09-06
 */
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;
import com.dhcc.ftp.action.BoBuilder;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.Ftp1OfRateSpread;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.ImportExcelChecks;
import com.dhcc.ftp.util.PageUtil;

public class PTZJRZSYLDCWHAction extends BoBuilder {

	private int pageSize = 8;
	private int rowsCount = -1;
	private String spreadId;
	private String spreadTerm;
	private String spreadDate;
	private String spreadRate;
	private String spreadIdStr;
	private String savePath;
	private int page = 1;
	private PageUtil ftpRateSpreadUtil = null;
	HttpServletRequest request = getRequest();
	DaoFactory df = new DaoFactory();


	public String execute() throws Exception {
		return super.execute();
	}
	public String List() throws Exception {
		String hql = "from Ftp1OfRateSpread where 1=1 ";
		hql+=" order by spreadDate desc,to_number(substr(termType,1,length(termType)-1)) ";
		String pageName = "PTZJRZSYLDCWH_List.action?";
		ftpRateSpreadUtil = queryPageBO.queryPage(hql, pageSize, page, rowsCount, pageName);
		request.setAttribute("ftpRateSpreadUtil", ftpRateSpreadUtil);
		return "List";
	}
	public String Query() throws Exception {
		String hsql = "from Ftp1OfRateSpread where spreadId = "+spreadId;
		Ftp1OfRateSpread ftpRateSpread = (Ftp1OfRateSpread)df.getBean(hsql, null);
		request.setAttribute("ftpRateSpread", ftpRateSpread);
		return "Edit";
	}
	public String save() throws Exception {
		Ftp1OfRateSpread ftpRateSpread = new Ftp1OfRateSpread();
		if(spreadId != null && !spreadId.equals("")){
			ftpRateSpread.setSpreadId(spreadId);
			ftpRateSpread.setSpreadRate(Double.parseDouble(spreadRate)/100.0);
			ftpRateSpread.setTermType(spreadTerm);
			ftpRateSpread.setSpreadDate(spreadDate);
			df.update(ftpRateSpread);
		}else {
			String hsql2 = "delete from Ftp1OfRateSpread where spreadDate ='"+spreadDate+"' ";
			df.delete(hsql2, null);String[] spreadTerm =new String[]{"2Y","3Y","5Y","7Y","10Y","15Y","20Y","30Y"};
			String[] rates = spreadRate.split(",");
			for(int i=0;i<rates.length;i++){
				Ftp1OfRateSpread po = new Ftp1OfRateSpread();
				po.setSpreadId(IDUtil.getInstanse().getUID());
				po.setSpreadRate(Double.valueOf(rates[i])/100.0);
				po.setTermType(spreadTerm[i]);
				po.setSpreadDate(spreadDate);
				df.insert(po);
			}
		}
		return null;
	}
	public String del() throws Exception {
		String[] spreadId = spreadIdStr.split(",");
		for(int i = 0; i < spreadId.length; i++){
			String hsql="delete from Ftp1OfRateSpread where spreadId="+spreadId[i]+"";
			df.delete(hsql, null);
		}
		return null;
	}
	public String doImport() throws Exception {
		getResponse().setContentType("text/html; charset=gb2312");
		int rows = 0, columns = 0;
		int successNum = 0;
		boolean flag=true;
		String typeError="";
		String msg="";
		PrintWriter pw = getResponse().getWriter();
		try {
			InputStream fis = new FileInputStream(savePath);
			Workbook wb = Workbook.getWorkbook(fis);
			Sheet sheet = wb.getSheet(0);
			rows = sheet.getRows();
			columns = sheet.getColumns();
			if(!sheet.getCell(0, 0).getContents().equals("日期")||!sheet.getCell(1, 0).getContents().equals("2Y")||!sheet.getCell(8, 0).getContents().equals("30Y")){
				typeError="文件模板错误，请先下载模板";
				msg="{'typeError':'"+typeError+"'}";
			}
			else{
				for (int i = 1; i < rows; i++) {
					boolean rightFlag = true;
					String date = sheet.getCell(0, i).getContents();
					for (int j = 0; j < columns; j++) {
						//不能为空
						if(sheet.getCell(j, i).getContents()==null||sheet.getCell(j, i).getContents().equals("")) {
							rightFlag = false;
							break;
						}
						//是否为数字
						if (j != 0 && !ImportExcelChecks.isNumberic(sheet.getCell(j, i).getContents())) {
							rightFlag = false;
							break;
						}
					}
					if(rightFlag) {
						for (int j = 1; j < columns; j++) {
							Ftp1OfRateSpread ftpRateSpread = new Ftp1OfRateSpread();
							try{
								String spreadDate = date;//模板的excel格式为字符串20121101，所以不用做处理
								ftpRateSpread.setSpreadDate(spreadDate);
								String termLimit  = sheet.getCell(j, 0).getContents();
//		                    	termLimit = termLimit.substring(0, termLimit.length()-1);
								ftpRateSpread.setTermType(termLimit);
								ftpRateSpread.setSpreadRate(Double.valueOf(sheet.getCell(j, i).getContents())/100);
								ftpRateSpread.setSpreadId(IDUtil.getInstanse().getUID());
								String hsql2 = "delete from Ftp1OfRateSpread where spreadDate ='"+spreadDate+"' and termType = '"+termLimit+"'";
								df.delete(hsql2, null);
								df.insert(ftpRateSpread);
							}catch(Exception e){
								e.printStackTrace();
							}
						}
						successNum++;
					}
				}

				msg = "{'successNum':'" + successNum +"','success':false}";
				wb.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		pw.write(msg);
		pw.flush();
		pw.close();
		request.setAttribute("successNum", successNum);
		return "import";
	}

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
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
	public DaoFactory getDf() {
		return df;
	}
	public void setDf(DaoFactory df) {
		this.df = df;
	}
	public String getSpreadId() {
		return spreadId;
	}
	public void setSpreadId(String spreadId) {
		this.spreadId = spreadId;
	}
	public String getSpreadTerm() {
		return spreadTerm;
	}
	public void setSpreadTerm(String spreadTerm) {
		this.spreadTerm = spreadTerm;
	}
	public String getSpreadDate() {
		return spreadDate;
	}
	public void setSpreadDate(String spreadDate) {
		this.spreadDate = spreadDate;
	}
	public String getSpreadRate() {
		return spreadRate;
	}
	public void setSpreadRate(String spreadRate) {
		this.spreadRate = spreadRate;
	}
	public String getSpreadIdStr() {
		return spreadIdStr;
	}
	public void setSpreadIdStr(String spreadIdStr) {
		this.spreadIdStr = spreadIdStr;
	}
	public PageUtil getFtpRateSpreadUtil() {
		return ftpRateSpreadUtil;
	}
	public void setFtpRateSpreadUtil(PageUtil ftpRateSpreadUtil) {
		this.ftpRateSpreadUtil = ftpRateSpreadUtil;
	}



}
