package com.dhcc.ftp.action.sjgl;
/**
 * @desc:数据管理：质押式回购利率维护Action
 * @author :sss
 * @date ：2013-09-05
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
import com.dhcc.ftp.entity.Ftp1PledgeRepoRate;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.PageUtil;

public class ZYSHGLLAction extends BoBuilder {

	private int pageSize = 10;
	private int rowsCount = -1;
    private String repoId;
    private String repoTerm;
    private String repoDate;
    private String repoRate;
    private String repoIdStr;
    private String savePath;
    private int page = 1;
    private PageUtil ftp1PledgeRepoUtil = null;
    HttpServletRequest request = getRequest();
	DaoFactory df = new DaoFactory();
	
    
    public String execute() throws Exception {
		return super.execute();
	}
    public String List() throws Exception {
    	String hql = "from Ftp1PledgeRepoRate where 1=1 ";//substr(shiborTerm,2,1),substr(shiborTerm,1,1)";
		hql+=" order by repoDate desc,repoRate ";
		String pageName = "ZYSHGLL_List.action?";
		ftp1PledgeRepoUtil = queryPageBO.queryPage(hql, pageSize, page, rowsCount, pageName);
		request.setAttribute("ftpPledgeRepoUtil", ftp1PledgeRepoUtil);
		return "List";
    }
    public String Query() throws Exception {
		String hsql = "from Ftp1PledgeRepoRate where repoId = "+repoId;
		Ftp1PledgeRepoRate ftpPledgeRepo = (Ftp1PledgeRepoRate)df.getBean(hsql, null);
		request.setAttribute("ftpPledgeRepo", ftpPledgeRepo);
		return "Edit";
    }
    public String save() throws Exception {
    	Ftp1PledgeRepoRate ftpRepoRate = new Ftp1PledgeRepoRate();
		if(repoId != null && !repoId.equals("")){
    		ftpRepoRate.setRepoId(repoId);
    		ftpRepoRate.setRepoRate(Double.parseDouble(repoRate)/100.0);
    		ftpRepoRate.setRepoDate(repoDate);
    		ftpRepoRate.setTermType(repoTerm);
			df.update(ftpRepoRate);
    	}else {
//    		ftpFinacialRate.setFinacialId(IDUtil.getInstanse().getUID());
//    		ftpFinacialRate.setFinacialRate(Double.valueOf(finacialRate)/100);
//    		ftpFinacialRate.setFinacialTerm(finacialTerm);
//    		ftpFinacialRate.setFinacialDate(Integer.valueOf(finacialDate));										
//			df.insert(ftpFinacialRate);
    		String[] repoTerm =new String[]{"O/N","1W","2W","3W","1M","2M","3M"};
    		String[] rates = repoRate.split(",");
    		for(int i=0;i<rates.length;i++){
    			String hsql = "from Ftp1PledgeRepoRate where repoDate ="+repoDate+" and termType = '"+repoTerm[i]+"'";
            	Ftp1PledgeRepoRate ftpreporate = (Ftp1PledgeRepoRate)df.getBean(hsql, null);
            	System.out.println("ftpreporate"+ftpreporate);
            	
            	if (ftpreporate != null && !ftpreporate.equals("")) {
            		String hsql2 = "delete from Ftp1PledgeRepoRate where repoDate ="+repoDate+" and termType = '"+repoTerm[i]+"'";
                	df.delete(hsql2, null);
            	}
    			Ftp1PledgeRepoRate po = new Ftp1PledgeRepoRate();
    			po.setRepoId(IDUtil.getInstanse().getUID());
        		po.setRepoRate(Double.valueOf(rates[i])/100.0);
        		po.setTermType(repoTerm[i]);
        		po.setRepoDate(repoDate);
    			df.insert(po);
    		}
    	}
    	return null;
    }
    public String del() throws Exception {
    	String[] repoId = repoIdStr.split(",");
		for(int i = 0; i < repoId.length; i++){
			String hsql="delete from Ftp1PledgeRepoRate where repoId="+repoId[i]+"";
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
                    	Ftp1PledgeRepoRate ftpRepoRate = new Ftp1PledgeRepoRate();
                		String date = sheet.getCell(0, i).getContents();
////                		if(date==null||date.equals("")){
//                			successNum--;
//                			break;
//                		}
                    	System.out.println(date);
						try{
						String repoDate = date;//模板的excel格式为字符串20121101，所以不用做处理
						ftpRepoRate.setRepoDate(date);
                    	String termLimit  = sheet.getCell(j, 0).getContents();
//                    	termLimit = termLimit.substring(0, termLimit.length()-1);
                    	ftpRepoRate.setTermType(termLimit);
                    	ftpRepoRate.setRepoRate(Double.valueOf(sheet.getCell(j, i).getContents())/100);
                    	String hsql = "from Ftp1PledgeRepoRate where repoDate ="+repoDate+" and termType = '"+termLimit+"'";
                    	Ftp1PledgeRepoRate ftpreporate = (Ftp1PledgeRepoRate)df.getBean(hsql, null);
                    	System.out.println("ftpreporate"+ftpreporate);
                    	if (ftpreporate != null && !ftpreporate.equals("")) {
                    		String hsql2 = "delete from Ftp1PledgeRepoRate where repoDate ="+repoDate+" and termType = '"+termLimit+"'";
                        	df.delete(hsql2, null);
                    	}
                    	ftpRepoRate.setRepoId(IDUtil.getInstanse().getUID());
                    	df.insert(ftpRepoRate);
						}catch(Exception e){
							e.printStackTrace();
							flag= false;
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
	public String getRepoId() {
		return repoId;
	}
	public void setRepoId(String repoId) {
		this.repoId = repoId;
	}
	public String getRepoTerm() {
		return repoTerm;
	}
	public void setRepoTerm(String repoTerm) {
		this.repoTerm = repoTerm;
	}
	public String getRepoDate() {
		return repoDate;
	}
	public void setRepoDate(String repoDate) {
		this.repoDate = repoDate;
	}
	public String getRepoRate() {
		return repoRate;
	}
	public void setRepoRate(String repoRate) {
		this.repoRate = repoRate;
	}
	public String getRepoIdStr() {
		return repoIdStr;
	}
	public void setRepoIdStr(String repoIdStr) {
		this.repoIdStr = repoIdStr;
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
	public PageUtil getFtp1PledgeRepoUtil() {
		return ftp1PledgeRepoUtil;
	}
	public void setFtp1PledgeRepoUtil(PageUtil ftp1PledgeRepoUtil) {
		this.ftp1PledgeRepoUtil = ftp1PledgeRepoUtil;
	}
	public DaoFactory getDf() {
		return df;
	}
	public void setDf(DaoFactory df) {
		this.df = df;
	}
    
	
}
