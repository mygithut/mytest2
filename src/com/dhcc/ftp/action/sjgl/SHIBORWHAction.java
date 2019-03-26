package com.dhcc.ftp.action.sjgl;
/**
 * shibor维护
 * @author Sunhongyu
 */
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

import com.dhcc.ftp.action.BoBuilder;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.FtpShibor;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.ImportExcelChecks;
import com.dhcc.ftp.util.PageUtil;

public class SHIBORWHAction  extends BoBuilder {

	private int pageSize = 8;
	private int rowsCount = -1;
	private String shiborId;
	private String shiborTerm;
	private String Shibor;
	private String shiborDate;
	private String shiborIdStr;
	private FtpShibor ftpshibor;
	private int page = 1;
	private PageUtil ftpShiborUtil = null;
	HttpServletRequest request = getRequest();
	private String savePath = null;
	DaoFactory df = new DaoFactory();

	public String execute() throws Exception {
		return super.execute();
	}
	/**
	 * 查询列表
	 * @return
	 * @throws Exception
	 */
	public String List() throws Exception {
		String hql = "from FtpShibor order by shiborDate desc,substr(shiborId,1,1)";//substr(shiborTerm,2,1),substr(shiborTerm,1,1)";
		String pageName = "SHIBORWH_List.action?";
		ftpShiborUtil = queryPageBO.queryPage(hql, pageSize, page, rowsCount, pageName);
		return "List";
	}
	/**
	 * 查询具体某条数据
	 * @return
	 * @throws Exception
	 */
	public String Query() throws Exception {
		String hsql = "from FtpShibor where shiborId = "+shiborId;
		ftpshibor = (FtpShibor)df.getBean(hsql, null);
		return "Edit";
	}
	/**
	 * 保存
	 * @return
	 * @throws Exception
	 */
	public String save() throws Exception {
		if (shiborId != null && !shiborId.equals("")) {//编辑
			FtpShibor ftpShibor = new FtpShibor();
			ftpShibor.setShiborId(shiborId);
			ftpShibor.setShiborRate(Double.valueOf(Shibor) / 100);
			ftpShibor.setShiborTerm(shiborTerm);
			ftpShibor.setShiborDate(Integer.valueOf(shiborDate));
			df.update(ftpShibor);
		} else {//新增
			String hsql2 = "delete from FtpShibor where shiborDate ="+shiborDate+" ";
			df.delete(hsql2, null);
			String[] shiborTerm =new String[]{"O/N","1W","2W","1M","3M","6M","9M","1Y"};
			String[] id = new String[]{"1","2","3","4","5","6","7","8"};//用来进行排序，放在ID的第一位
			String[] rates = Shibor.split(",");
			for(int i=0;i<rates.length;i++){
				FtpShibor po = new FtpShibor();
				po.setShiborId(id[i]+IDUtil.getInstanse().getUID());
				po.setShiborRate(Double.valueOf(rates[i])/100.0);
				po.setShiborTerm(shiborTerm[i]);
				po.setShiborDate(Integer.valueOf(shiborDate));
				df.insert(po);
			}
		}
		return null;
	}

	/**
	 * 删除
	 * @return
	 * @throws Exception
	 */
	public String del() throws Exception {
		String[] shiborId = shiborIdStr.split(",");
		for (int i = 0; i < shiborId.length; i++) {
			String hsql = "delete from FtpShibor where shiborId=" + shiborId[i]
					+ "";
			df.delete(hsql, null);
		}
		return null;
	}
	/**
	 * 导入
	 * @return
	 * @throws Exception
	 */
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
			if(rows<=2||columns<9||!sheet.getCell(0, 0).getContents().equals("指标名称")||!sheet.getCell(3, 0).getContents().equals("SHIBOR2W")||!sheet.getCell(8, 0).getContents().equals("SHIBOR1Y")||!sheet.getCell(8, 1).getContents().equals("日")){

				typeError="文件模板错误，请先下载模板";
				msg="{'typeError':'"+typeError+"'}";
				//ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
			} else{
				for (int i = 2; i < rows; i++) {
					boolean rightFlag = true;
					String date = sheet.getCell(0, i).getContents();
					Cell cell = sheet.getCell(0, i);
					if(cell.getType() == CellType.DATE){//将日期单元格日期格式化为YYYYMMDD
						DateCell dc = (DateCell)cell;
						Date cellDate = dc.getDate();
						SimpleDateFormat ds = new SimpleDateFormat("yyyyMMdd");
						date = ds.format(cellDate);
					}
					for (int j = 0; j < columns; j++) {
						//不能为空
						if(sheet.getCell(j, i).getContents()==null||sheet.getCell(j, i).getContents().equals("")) {
							rightFlag = false;
							break;
						}
						//是否为数字
						if (j != 0 && !ImportExcelChecks.isNumberic(sheet.getCell(j, i).getContents().trim())) {
							rightFlag = false;
							break;
						}
					}
					if(rightFlag) {
						String[] shiborTerm =new String[]{"O/N","1W","2W","1M","3M","6M","9M","1Y"};
						String[] id = new String[]{"1","2","3","4","5","6","7","8"};//用来进行排序，放在ID的第一位
						Map<String, String> termIdMap = new HashMap<String, String>();
						for(int k = 0; k < shiborTerm.length; k++) {
							termIdMap.put(shiborTerm[k], id[k]);
						}
						String shiborDate = date;//模板的excel格式为字符串20121101
						String hsql2 = "delete FtpShibor where shiborDate ='"+shiborDate+"'";
						df.delete(hsql2, null);
						for (int j = 1; j < columns; j++) {
							FtpShibor ftpShibor = new FtpShibor();
							ftpShibor.setShiborDate(Integer.valueOf(shiborDate));
							ftpShibor.setShiborTerm(shiborTerm[j-1]);
							ftpShibor.setShiborRate(Double.valueOf(sheet.getCell(j, i).getContents())/100);
							ftpShibor.setShiborId(termIdMap.get(shiborTerm[j-1])+IDUtil.getInstanse().getUID());
							df.insert(ftpShibor);
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
		return "Import";
	}
	public String getShibor() {
		return Shibor;
	}
	public void setShibor(String shibor) {
		Shibor = shibor;
	}
	public String getShiborId() {
		return shiborId;
	}
	public void setShiborId(String shiborId) {
		this.shiborId = shiborId;
	}
	public String getShiborTerm() {
		return shiborTerm;
	}
	public void setShiborTerm(String shiborTerm) {
		this.shiborTerm = shiborTerm;
	}
	public String getShiborDate() {
		return shiborDate;
	}
	public void setShiborDate(String shiborDate) {
		this.shiborDate = shiborDate;
	}
	public String getShiborIdStr() {
		return shiborIdStr;
	}
	public void setShiborIdStr(String shiborIdStr) {
		this.shiborIdStr = shiborIdStr;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
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
	public FtpShibor getFtpshibor() {
		return ftpshibor;
	}
	public void setFtpshibor(FtpShibor ftpshibor) {
		this.ftpshibor = ftpshibor;
	}
	public PageUtil getFtpShiborUtil() {
		return ftpShiborUtil;
	}
	public void setFtpShiborUtil(PageUtil ftpShiborUtil) {
		this.ftpShiborUtil = ftpShiborUtil;
	}

}
