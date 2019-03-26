package com.dhcc.ftp.action.sjgl;
/**
 * @desc:数据管理：央行票据利率维护Action
 * @author :sss
 */
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import jxl.Sheet;
import jxl.Workbook;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.action.BoBuilder;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.Ftp1BankBillsRate;
import com.dhcc.ftp.entity.Ftp1PledgeRepoRate;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.PageUtil;

public class YHPJLLAction extends BoBuilder {

    private String billsId;
    private String billsTerm;
    private String billsDate;
    private String billsRate;
    private String billsIdStr;
    private String savePath;
    private int page = 1;
    private int pageSize=10;
    private int rowsCount = -1;
    private PageUtil ftpBankBillsRateUtil = null;
    HttpServletRequest request = getRequest();
	DaoFactory df = new DaoFactory();
	
    
    public String execute() throws Exception {
		return super.execute();
	}
    public String List() throws Exception {
    	String hql = "from Ftp1BankBillsRate where 1=1 ";//substr(shiborTerm,2,1),substr(shiborTerm,1,1)";
		hql+=" order by billsDate desc,billsRate ";
		String pageName = "YHPJLL_List.action?";
		ftpBankBillsRateUtil = queryPageBO.queryPage(hql, pageSize, page, rowsCount, pageName);
		request.setAttribute("ftpBankBillsRateUtil", ftpBankBillsRateUtil);
		return "List";
    }
    public String Query() throws Exception {
		String hsql = "from Ftp1BankBillsRate where billsId = "+billsId;
		Ftp1BankBillsRate ftpBankRate = (Ftp1BankBillsRate)df.getBean(hsql, null);
		request.setAttribute("ftpBankRate", ftpBankRate);
		return "Edit";
    }
    public String save() throws Exception {
    	Ftp1BankBillsRate ftpBankRate = new Ftp1BankBillsRate();
		if (billsId != null && !billsId.equals("")){
			ftpBankRate.setBillsId(billsId);
			ftpBankRate.setBillsRate(Double.valueOf(billsRate)/100.0);
			ftpBankRate.setBillsDate(billsDate);
			ftpBankRate.setTermType(billsTerm);										
			df.update(ftpBankRate);
    	}else {
    		String[] billsTerm =new String[]{"6M","9M","1Y","2Y","3Y"};
    		String[] rates = billsRate.split(",");
    		for(int i=0;i<rates.length;i++){
    			String hsql = "from Ftp1BankBillsRate where billsDate ="+billsDate+" and termType = '"+billsTerm[i]+"'";
    			Ftp1BankBillsRate ftpbankrate = (Ftp1BankBillsRate)df.getBean(hsql, null);
            	System.out.println("ftpbankrate"+ftpbankrate);
            	
            	if (ftpbankrate != null && !ftpbankrate.equals("")) {
            		String hsql2 = "delete from Ftp1BankBillsRate where billsDate ="+billsDate+" and termType = '"+billsTerm[i]+"'";
                	df.delete(hsql2, null);
            	}
    			Ftp1BankBillsRate po = new Ftp1BankBillsRate();
    			po.setBillsId(IDUtil.getInstanse().getUID());
        		po.setBillsRate(Double.valueOf(rates[i])/100.0);
        		po.setTermType(billsTerm[i]);
        		po.setBillsDate(billsDate);
    			df.insert(po);
    		}
    	}
    	return null;
    }
    public String del() throws Exception {
    	String[] billsId = billsIdStr.split(",");
		for(int i = 0; i < billsId.length; i++){
			String hsql="delete from Ftp1BankBillsRate where billsId="+billsId[i]+"";
			df.delete(hsql, null);
		}
		return null;
    }
    public String doImport() throws Exception {
        int rows = 0, columns = 0;
        int successNum = 0;
        boolean flag = true;
		try {
            InputStream fis = new FileInputStream(savePath);
            Workbook wb = Workbook.getWorkbook(fis);
            Sheet sheet = wb.getSheet(0);
            rows = sheet.getRows();
            columns = sheet.getColumns();
            if(!sheet.getCell(0, 0).getContents().equals("日期")){
            	try {
        			ServletActionContext.getResponse().setCharacterEncoding("UTF-8");

        			PrintWriter pw = ServletActionContext.getResponse().getWriter();
        			pw.write("false");
        			pw.flush();
        			pw.close();
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
            }
            else{
            	for (int i = 1; i < rows; i++) {
                    for (int j = 1; j < columns; j++) {
                    	Ftp1BankBillsRate ftpBankRate = new Ftp1BankBillsRate();
                		String date = sheet.getCell(0, i).getContents();
                    	System.out.println(date);
//                    	if(date==null||date.equals("")){
//                			successNum--;
//                			break;
//                		}
                    	try{
						String billsDate = date;//模板的excel格式为字符串20121101，所以不用做处理
						ftpBankRate.setBillsDate(billsDate);
                    	String termLimit  = sheet.getCell(j, 0).getContents();
//                    	termLimit = termLimit.substring(0, termLimit.length()-1);
                    	ftpBankRate.setTermType(termLimit);
                    	ftpBankRate.setBillsRate(Double.valueOf(sheet.getCell(j, i).getContents())/100);
                    	String hsql = "from Ftp1BankBillsRate where billsDate ="+billsDate+" and termType= '"+termLimit+"'";
                    	Ftp1BankBillsRate ftpbankrate = (Ftp1BankBillsRate)df.getBean(hsql, null);
                    	System.out.println("ftpfinacialrate"+ftpbankrate);
                    	if (ftpbankrate != null && !ftpbankrate.equals("")) {
                    		String hsql2 = "delete from Ftp1BankBillsRate where billsDate ="+billsDate+" and termType = '"+termLimit+"'";
                        	df.delete(hsql2, null);
                    	}
                    	ftpBankRate.setBillsId(IDUtil.getInstanse().getUID());
                    	df.insert(ftpBankRate);
                    	}catch(Exception e){
                    		e.printStackTrace();
                    		flag=false;
                    		break;
                    	}
                    }
                    if(flag){
                    successNum++;
                    }
                }
                wb.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
	public PageUtil getFtpBankBillsRateUtil() {
		return ftpBankBillsRateUtil;
	}
	public void setFtpBankBillsRateUtil(PageUtil ftpBankBillsRateUtil) {
		this.ftpBankBillsRateUtil = ftpBankBillsRateUtil;
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
	public String getBillsId() {
		return billsId;
	}
	public void setBillsId(String billsId) {
		this.billsId = billsId;
	}
	public String getBillsTerm() {
		return billsTerm;
	}
	public void setBillsTerm(String billsTerm) {
		this.billsTerm = billsTerm;
	}
	public String getBillsDate() {
		return billsDate;
	}
	public void setBillsDate(String billsDate) {
		this.billsDate = billsDate;
	}
	public String getBillsRate() {
		return billsRate;
	}
	public void setBillsRate(String billsRate) {
		this.billsRate = billsRate;
	}
	public String getBillsIdStr() {
		return billsIdStr;
	}
	public void setBillsIdStr(String billsIdStr) {
		this.billsIdStr = billsIdStr;
	}
	public DaoFactory getDf() {
		return df;
	}
	public void setDf(DaoFactory df) {
		this.df = df;
	}

	
}
