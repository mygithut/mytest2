package com.dhcc.ftp.action.sjgl;
/**
 * @desc:数据管理：国债收益率维护Action
 * @author :孙红玉
 * @date ：2011-08-30
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
import com.dhcc.ftp.entity.FtpStockYield;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.ImportExcelChecks;
import com.dhcc.ftp.util.PageUtil;

public class GZWHAction extends BoBuilder {

	private String stockId;
	private String stockTerm;
	private String stockYield;
	private String stockDate;
	private String stockIdStr;
	private String savePath;
	private String stock;
	private int page = 1;
	private PageUtil FtpStockUtil = null;
	HttpServletRequest request = getRequest();
	DaoFactory df = new DaoFactory();


	public String execute() throws Exception {
		return super.execute();
	}
	public String List() throws Exception {
		FtpStockUtil = ftpStockBO.dofind(page);
		request.setAttribute("FtpStockUtil", FtpStockUtil);
		return "List";
	}
	public String Query() throws Exception {
		String hsql = "from FtpStockYield where stockId = "+stockId;
		FtpStockYield ftpStock = (FtpStockYield)df.getBean(hsql, null);
		request.setAttribute("ftpStock", ftpStock);
		return "Edit";
	}
	public String save() throws Exception {
		if (stockId != null && !stockId.equals("")){
			FtpStockYield ftpStock = new FtpStockYield();
			ftpStock.setStockId(stockId);
			ftpStock.setStockYield(Double.valueOf(stockYield)/100);
			ftpStock.setStockTerm(stockTerm);
			ftpStock.setStockDate(Integer.valueOf(stockDate));
			df.update(ftpStock);
		}else {//新增
			String hsql2 = "delete from FtpStockYield where stockDate ="+stockDate+" ";
			df.delete(hsql2, null);
			String[] stockTerm =new String[]{"6M","9M","1Y","2Y","3Y","4Y","5Y","6Y","7Y","8Y","9Y","10Y","15Y","20Y","30Y"};
			String[] rates = stock.split(",");
			for(int i=0;i<rates.length;i++){
				FtpStockYield ftpStock = new FtpStockYield();
				ftpStock.setStockId(IDUtil.getInstanse().getUID());
				ftpStock.setStockYield(Double.valueOf(rates[i])/100);
				ftpStock.setStockTerm(stockTerm[i]);
				ftpStock.setStockDate(Integer.valueOf(stockDate));
				df.update(ftpStock);
			}
		}
		return null;
	}
	public String del() throws Exception {
		String[] stockId = stockIdStr.split(",");
		for(int i = 0; i < stockId.length; i++){
			String hsql="delete from FtpStockYield where stockId="+stockId[i]+"";
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
			if(rows<=2||columns<22||!sheet.getCell(0, 0).getContents().equals("指标名称")||!sheet.getCell(5, 0).getContents().equals("中债国债到期收益率:6个月")||!sheet.getCell(19, 0).getContents().equals("中债国债到期收益率:30年")||!sheet.getCell(21, 1).getContents().equals("日")){
				typeError="文件模板错误，请先下载模板";
				msg="{'typeError':'"+typeError+"'}";
			}
			else{
				for (int i = 2; i < rows; i++) {
					boolean rightFlag = true;
					for (int j = 0; j < columns-2; j++) {
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
						Cell cell = sheet.getCell(0, i);
						String date = sheet.getCell(0, i).getContents();
						if(cell.getType() == CellType.DATE){//将日期单元格日期格式化为YYYYMMDD
							DateCell dc = (DateCell)cell;
							Date cellDate = dc.getDate();
							SimpleDateFormat ds = new SimpleDateFormat("yyyyMMdd");
							date = ds.format(cellDate);
						}
						String stockDate = date;//模板的excel格式为字符串20121101，所以不用做处理
						String hsql2 = "delete from FtpStockYield where stockDate ="+stockDate+"";
						df.delete(hsql2, null);
						//模板里包含40年和50年，减去两位
						for (int j = 5; j < columns-2; j++) {
							FtpStockYield ftpStockYield = new FtpStockYield();

							ftpStockYield.setStockDate(Integer.valueOf(stockDate));
							String[] terms =new String[]{"6M","9M","1Y","2Y","3Y","4Y","5Y","6Y","7Y","8Y","9Y","10Y","15Y","20Y","30Y"};
							//termLimit = termLimit.substring(0, termLimit.length()-1);
							ftpStockYield.setStockTerm(terms[j-5]);
							ftpStockYield.setStockYield(Double.valueOf(sheet.getCell(j, i).getContents())/100);
							ftpStockYield.setStockId(IDUtil.getInstanse().getUID());
							df.insert(ftpStockYield);
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
	public String getStockId() {
		return stockId;
	}
	public String getStockTerm() {
		return stockTerm;
	}
	public void setStockTerm(String stockTerm) {
		this.stockTerm = stockTerm;
	}
	public String getStockYield() {
		return stockYield;
	}
	public void setStockYield(String stockYield) {
		this.stockYield = stockYield;
	}
	public String getStockDate() {
		return stockDate;
	}
	public void setStockDate(String stockDate) {
		this.stockDate = stockDate;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	public PageUtil getFtpStockUtil() {
		return FtpStockUtil;
	}
	public void setFtpStockUtil(PageUtil ftpStockUtil) {
		FtpStockUtil = ftpStockUtil;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getStockIdStr() {
		return stockIdStr;
	}
	public void setStockIdStr(String stockIdStr) {
		this.stockIdStr = stockIdStr;
	}
	public String getSavePath() {
		return savePath;
	}
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}
	public String getStock() {
		return stock;
	}
	public void setStock(String stock) {
		this.stock = stock;
	}

}
