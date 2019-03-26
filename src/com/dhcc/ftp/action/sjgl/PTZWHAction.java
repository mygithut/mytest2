package com.dhcc.ftp.action.sjgl;
/**
 * @desc:数据管理：本行普通债收益率维护Action
 * @author :SHY
 * @date ：2014-07-10
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
import com.dhcc.ftp.entity.Ftp1CommonYield;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.ImportExcelChecks;
import com.dhcc.ftp.util.PageUtil;

public class PTZWHAction extends BoBuilder {

	private int pageSize = 15;
	private int rowsCount = -1;
    private String commonId;
    private String termType;
    private String commonDate;
    private String commonRate;
    private String commonIdStr;
    private String savePath;
    private int page = 1;
    private PageUtil ftp1CommonYieldUtil = null;
    HttpServletRequest request = getRequest();
	DaoFactory df = new DaoFactory();
	
    
    public String execute() throws Exception {
		return super.execute();
	}
    public String List() throws Exception {
    	String hql = "from Ftp1CommonYield where 1=1 ";
		hql+=" order by commonDate desc,substr(termType,length(termType),1),to_number(substr(termType,1,length(termType)-1)) ";
		String pageName = "PTZWH_List.action?";
		ftp1CommonYieldUtil = queryPageBO.queryPage(hql, pageSize, page, rowsCount, pageName);
		request.setAttribute("ftp1CommonYieldUtil", ftp1CommonYieldUtil);
		return "List";
    }
    public String Query() throws Exception {
		String hsql = "from Ftp1CommonYield where commonId = "+commonId;
		Ftp1CommonYield ftp1CommonYield = (Ftp1CommonYield)df.getBean(hsql, null);
		request.setAttribute("ftp1CommonYield", ftp1CommonYield);
		return "Edit";
    }
    public String save() throws Exception {
    	Ftp1CommonYield ftp1CommonYield = new Ftp1CommonYield();
		if(commonId != null && !commonId.equals("")){
			ftp1CommonYield.setCommonId(commonId);
			ftp1CommonYield.setCommonRate(Double.parseDouble(commonRate)/100.0);
			ftp1CommonYield.setTermType(termType);
			ftp1CommonYield.setCommonDate(commonDate);
			df.update(ftp1CommonYield);
    	}else {
    		String hsql2 = "delete from Ftp1CommonYield where commonDate ='"+commonDate+"' ";
        	df.delete(hsql2, null);
        	String[] termType =new String[]{"6M","9M","1Y","2Y","3Y","4Y","5Y","6Y","7Y","8Y","9Y","10Y","15Y","20Y","30Y"};
    		String[] rates = commonRate.split(",");
    		for(int i=0;i<rates.length;i++){
            	Ftp1CommonYield po = new Ftp1CommonYield();
    			po.setCommonId(IDUtil.getInstanse().getUID());
        		po.setCommonRate(Double.valueOf(rates[i])/100.0);
        		po.setTermType(termType[i]);
        		po.setCommonDate(commonDate);
    			df.insert(po);
    		}
    	}
    	return null;
    }
    public String del() throws Exception {
    	String[] commonId = commonIdStr.split(",");
		for(int i = 0; i < commonId.length; i++){
			String hsql="delete from Ftp1CommonYield where commonId="+commonId[i]+"";
			df.delete(hsql, null);
		}
		return null;
    }
    public String doImport() throws Exception {
    	//System.out.println("进入PTZWHAction-doImport() ");
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
            //columns = sheet.getColumns();
            columns = 18;
            //屏蔽掉对相关评级的校验，评级有可能修改。
            if(rows<=2||columns<18||!sheet.getCell(0, 0).getContents().equals("指标名称")||!sheet.getCell(1, 0).getContents().contains("中债商业银行普通债到期收益率")||!sheet.getCell(1, 0).getContents().contains(":0年")||!sheet.getCell(15, 0).getContents().contains("中债商业银行普通债到期收益率")||!sheet.getCell(15, 0).getContents().contains(":10年")||!sheet.getCell(15, 1).getContents().equals("日")){
            	typeError="文件模板错误，请先下载模板";
				msg="{'typeError':'"+typeError+"'}";
            }
            else{
            	for (int i = 2; i < rows; i++) {
            		boolean rightFlag = true;
					for (int j = 0; j < columns; j++) {
						//不能为空
						if((j ==0 ||j > 2) &&(sheet.getCell(j, i).getContents()==null||sheet.getCell(j, i).getContents().equals(""))) {
							rightFlag = false;
							System.out.println(i+","+j+":"+sheet.getCell(j, i).getContents());
							break;
						}
						//是否为数字
						if (j > 2 && !ImportExcelChecks.isNumberic(sheet.getCell(j, i).getContents().trim())) {
							rightFlag = false;
							System.out.println(i+","+j+":"+sheet.getCell(j, i).getContents());
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
				        String commonDate = date;//模板的excel格式为字符串20121101，所以不用做处理
						String hsql2 = "delete from Ftp1CommonYield where commonDate ='"+commonDate+"'";
                    	df.delete(hsql2, null);
                    	double rate6M = 0, rate1Y = 0, rate8Y = 0, rate10Y = 0;
                    	for (int j = 3; j < columns; j++) {
	                    	Ftp1CommonYield ftp1CommonYield = new Ftp1CommonYield();
	                    	ftp1CommonYield.setCommonDate(commonDate);
							String[] terms =new String[]{"6M","9M","1Y","2Y","3Y","4Y","5Y","6Y","7Y","8Y","9Y","10Y","15Y","20Y","30Y"};
	                    	ftp1CommonYield.setTermType(terms[j-3]);
	                    	double rate = Double.valueOf(sheet.getCell(j, i).getContents());
	                    	ftp1CommonYield.setCommonRate(rate/100);
	                    	//模板里没有9M和9Y，保留各个利率来计算9M和9Y ls:模版已包含
	                    	/*if(ftp1CommonYield.getTermType().equals("6M")) rate6M = rate;
	                    	if(ftp1CommonYield.getTermType().equals("1Y")) rate1Y = rate;
	                    	if(ftp1CommonYield.getTermType().equals("8Y")) rate8Y = rate;
	                    	if(ftp1CommonYield.getTermType().equals("10Y")) rate10Y = rate;*/
	                    	ftp1CommonYield.setCommonId(IDUtil.getInstanse().getUID());
	                    	df.insert(ftp1CommonYield);
	                    }
						//模板不包含9M和9Y,要根据左右的值进行差值 ls:模版已包含
                    	/*double rate9M = (rate6M+rate1Y)/2;
                    	Ftp1CommonYield ftp1CommonYield = new Ftp1CommonYield();
                    	ftp1CommonYield.setCommonDate(commonDate);
                    	ftp1CommonYield.setTermType("9M");
                    	ftp1CommonYield.setCommonRate(rate9M/100);
                    	ftp1CommonYield.setCommonId(IDUtil.getInstanse().getUID());
                    	df.insert(ftp1CommonYield);
                    	double rate9Y = (rate8Y+rate10Y)/2;
                    	ftp1CommonYield = new Ftp1CommonYield();
                    	ftp1CommonYield.setCommonDate(commonDate);
                    	ftp1CommonYield.setTermType("9Y");
                    	ftp1CommonYield.setCommonRate(rate9Y/100);
                    	ftp1CommonYield.setCommonId(IDUtil.getInstanse().getUID());
                    	df.insert(ftp1CommonYield);*/
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
	public String getCommonId() {
		return commonId;
	}
	public void setCommonId(String commonId) {
		this.commonId = commonId;
	}
	public String getCommonDate() {
		return commonDate;
	}
	public void setCommonDate(String commonDate) {
		this.commonDate = commonDate;
	}
	public String getCommonRate() {
		return commonRate;
	}
	public void setCommonRate(String commonRate) {
		this.commonRate = commonRate;
	}
	public String getCommonIdStr() {
		return commonIdStr;
	}
	public void setCommonIdStr(String commonIdStr) {
		this.commonIdStr = commonIdStr;
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
	public PageUtil getFtp1CommonYieldUtil() {
		return ftp1CommonYieldUtil;
	}
	public void setFtp1CommonYieldUtil(PageUtil ftp1CommonYieldUtil) {
		this.ftp1CommonYieldUtil = ftp1CommonYieldUtil;
	}
	public String getTermType() {
		return termType;
	}
	public void setTermType(String termType) {
		this.termType = termType;
	}
	
}
