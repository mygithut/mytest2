package com.dhcc.ftp.action.sjgl;
/**
 * @desc:数据管理：政策性金融债收益率维护Action
 * @author :孙红玉
 * @date ：2012-03-28
 */
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import com.dhcc.ftp.action.BoBuilder;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.FtpFinacialRate;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.ImportExcelChecks;
import com.dhcc.ftp.util.PageUtil;

public class ZCXJRZWHAction extends BoBuilder {

	private String finacialId;
	private String finacialTerm;
	private String finacialDate;
	private String finacialRate;
	private String finacialIdStr;
	private String savePath;
	private int page = 1;
	private PageUtil FtpFinacialUtil = null;
	HttpServletRequest request = getRequest();
	DaoFactory df = new DaoFactory();


	public String execute() throws Exception {
		return super.execute();
	}
	public String List() throws Exception {
		FtpFinacialUtil = ftpFinacialBO.dofind(page);
		request.setAttribute("FtpFinacialUtil", FtpFinacialUtil);
		return "List";
	}
	public String Query() throws Exception {
		String hsql = "from FtpFinacialRate where finacialId = "+finacialId;
		FtpFinacialRate ftpFinacialRate = (FtpFinacialRate)df.getBean(hsql, null);
		request.setAttribute("ftpFinacialRate", ftpFinacialRate);
		return "Edit";
	}
	public String save() throws Exception {
		FtpFinacialRate ftpFinacialRate = new FtpFinacialRate();
		if (finacialId != null && !finacialId.equals("")){
			ftpFinacialRate.setFinacialId(finacialId);
			ftpFinacialRate.setFinacialRate(Double.valueOf(finacialRate)/100);
			ftpFinacialRate.setFinacialTerm(finacialTerm);
			ftpFinacialRate.setFinacialDate(Integer.valueOf(finacialDate));
			df.update(ftpFinacialRate);
		}else {
//    		ftpFinacialRate.setFinacialId(IDUtil.getInstanse().getUID());
//    		ftpFinacialRate.setFinacialRate(Double.valueOf(finacialRate)/100);
//    		ftpFinacialRate.setFinacialTerm(finacialTerm);
//    		ftpFinacialRate.setFinacialDate(Integer.valueOf(finacialDate));
//			df.insert(ftpFinacialRate);
//    		String[] finaTerm =new String[]{"1Y","2Y","3Y","5Y","7Y","10Y","15Y","20Y","30Y"};
			String hsql2 = "delete from FtpFinacialRate where finacialDate ='"+finacialDate+"'";
			df.delete(hsql2, null);
			String[] finaTerm =new String[]{"6M","9M","1Y","2Y","3Y","4Y","5Y","6Y","7Y","8Y","9Y","10Y","15Y","20Y","30Y"};
			String[] rates = finacialRate.split(",");
			for(int i=0;i<rates.length;i++){
				FtpFinacialRate po = new FtpFinacialRate();
				po.setFinacialId(IDUtil.getInstanse().getUID());
				po.setFinacialRate(Double.valueOf(rates[i])/100);
				po.setFinacialTerm(finaTerm[i]);
				po.setFinacialDate(Integer.valueOf(finacialDate));
				df.insert(po);
			}
		}
		return null;
	}
	public String del() throws Exception {
		String[] finacialId = finacialIdStr.split(",");
		for(int i = 0; i < finacialId.length; i++){
			String hsql="delete from FtpFinacialRate where finacialId="+finacialId[i]+"";
			df.delete(hsql, null);
		}
		return null;
	}
	public String doImport() throws Exception {
		getResponse().setContentType("text/html; charset=gb2312");
		int rows = 0, columns = 0;
		int successNum = 0;
		String typeError="";
		String msg="";
		PrintWriter pw = getResponse().getWriter();
		try {
			InputStream fis = new FileInputStream(savePath);
			Workbook wb = Workbook.getWorkbook(fis);
			Sheet sheet = wb.getSheet(0);
			rows = sheet.getRows();
			columns = sheet.getColumns();
			if(rows<=2||columns<21||!sheet.getCell(0, 0).getContents().equals("指标名称")||!sheet.getCell(1, 0).getContents().equals("中债政策性金融债到期收益率(国开行):0年")||!sheet.getCell(19, 0).getContents().equals("中债政策性金融债到期收益率(国开行):30年")||!sheet.getCell(20, 1).getContents().equals("日")){
				typeError="文件模板错误，请先下载模板";
				msg="{'typeError':'"+typeError+"'}";
			}
			else{
				for (int i = 2; i < rows; i++) {
					boolean rightFlag = true;

					for (int j = 0; j < 20; j++) {
						//不能为空
						if((j ==0 ||j > 4) &&(sheet.getCell(j, i).getContents()==null||sheet.getCell(j, i).getContents().equals(""))) {
							rightFlag = false;
							break;
						}
						//是否为数字
						if (j > 4 && !ImportExcelChecks.isNumberic(sheet.getCell(j, i).getContents().trim())) {
							rightFlag = false;
							break;
						}
					}
					if(rightFlag) {
						String date = sheet.getCell(0, i).getContents();
						Cell cell = sheet.getCell(0, i);
						if(cell.getType() == CellType.DATE){//将日期单元格日期格式化为YYYYMMDD
							DateCell dc = (DateCell)cell;
							Date cellDate = dc.getDate();
							SimpleDateFormat ds = new SimpleDateFormat("yyyyMMdd");
							date = ds.format(cellDate);
						}
						String finacialDate = date;//模板的excel格式为字符串20121101
						String hsql2 = "delete from FtpFinacialRate where finacialDate ='"+finacialDate+"'";
						df.delete(hsql2, null);
						for (int j = 5; j < 20; j++) {
							FtpFinacialRate ftpFinacialRate = new FtpFinacialRate();
							try{
								ftpFinacialRate.setFinacialDate(Integer.valueOf(finacialDate));
								String[] terms =new String[]{"6M","9M","1Y","2Y","3Y","4Y","5Y","6Y","7Y","8Y","9Y","10Y","15Y","20Y","30Y"};
								ftpFinacialRate.setFinacialTerm(terms[j-5]);
								ftpFinacialRate.setFinacialRate(Double.valueOf(sheet.getCell(j, i).getContents())/100);
								ftpFinacialRate.setFinacialId(IDUtil.getInstanse().getUID());
								df.insert(ftpFinacialRate);
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

	public PageUtil getFtpFinacialUtil() {
		return FtpFinacialUtil;
	}
	public void setFtpFinacialUtil(PageUtil ftpFinacialUtil) {
		FtpFinacialUtil = ftpFinacialUtil;
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
	public String getFinacialId() {
		return finacialId;
	}
	public void setFinacialId(String finacialId) {
		this.finacialId = finacialId;
	}
	public String getFinacialTerm() {
		return finacialTerm;
	}
	public void setFinacialTerm(String finacialTerm) {
		this.finacialTerm = finacialTerm;
	}
	public String getFinacialDate() {
		return finacialDate;
	}
	public void setFinacialDate(String finacialDate) {
		this.finacialDate = finacialDate;
	}
	public String getFinacialRate() {
		return finacialRate;
	}
	public void setFinacialRate(String finacialRate) {
		this.finacialRate = finacialRate;
	}
	public String getFinacialIdStr() {
		return finacialIdStr;
	}
	public void setFinacialIdStr(String finacialIdStr) {
		this.finacialIdStr = finacialIdStr;
	}

}
