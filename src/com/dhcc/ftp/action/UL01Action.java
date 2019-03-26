package com.dhcc.ftp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.PageUtil;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.AlmCur;
import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.FtpResult;
import com.dhcc.ftp.entity.YlfxReport;
import com.dhcc.ftp.util.FtpUtil;
/**
 * <p>
 * Title: ���ʽ��
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Company: ��������ɷݹ�˾������ҵ��
 * </p>
 * 
 * @author �����
 * 
 * @date 10 23, 2011 3:29:33 PM
 * 
 * @version 1.0
 */
public class UL01Action extends BoBuilder {
	private String method;
	private String currencySelectId;
	private String brNo;
	private String date;
    private String methodName;
	private String resValue;
	private String prcMode;
	private Double adjustValue;
	private int page = 1;
	private int pageSize = 10;
	private int rowsCount = -1;
	private PageUtil resultPriceUtil = null;
	HttpServletRequest request = ServletActionContext.getRequest();
	
	public String execute() throws Exception {
		return super.execute();
	}
    //���ۼ���
	public String calculate() throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		String result = "";
		adjustValue = adjustValue == null ? 0.0 : adjustValue;
		
		if (method.equals("1")) {
			//�ɱ����淨  ת�ƻ�׼�۸񣬼������˼�룺ת�Ƽ۸�=���ɱ���+�����ʣ�/2
	        //����ʽ����׼����C=[(b-e)+a+f]/2
			Double b = FtpUtil.getYlxzchbl(currencySelectId, brNo, date);
			Double e = FtpUtil.getZcssl(currencySelectId, brNo, date);
			Double a = FtpUtil.getCzcbl(currencySelectId, brNo, date);
			Double f = FtpUtil.getCzfyl(currencySelectId, brNo, date);
			System.out.println("�۳�Ӫҵ˰�����ӵ�ӯ�����ʲ��ر���"+b);
			System.out.println("�ʲ���ʧ��"+e);
			System.out.println("���ʳɱ���"+a);
			System.out.println("���ʷ�����"+f);
			Double c = (b-e+a+f)/2 + adjustValue/100;
			System.out.println("�ڲ��ʽ�ת�ƻ�׼�۸�"+c);
			result =CommonFunctions.doublecut(c*100,3)+","+CommonFunctions.doublecut(b*100,3)+","+
			CommonFunctions.doublecut(e*100,3)+","+CommonFunctions.doublecut(a*100,3)+","+CommonFunctions.doublecut(f*100,3)+","+adjustValue;
		}else{
			//��Ȩƽ���ɱ���
			System.out.println("��Ȩƽ���ɱ���");
			Double ckpjcbl  = FtpUtil.getCkpjcbl(currencySelectId, brNo, date);//���ƽ���ɱ���
			Double dkpjsyl = FtpUtil.getDkpjsyl(currencySelectId, brNo, date);//����ƽ��������
			Double pjcdb = FtpUtil.getPjcdb(currencySelectId, brNo, date);//ƽ�������
			Double zejg = (ckpjcbl + dkpjsyl * pjcdb)/(1 + pjcdb) + adjustValue/100;
			System.out.println("���ƽ���ɱ���"+ckpjcbl);
			System.out.println("����ƽ��������"+dkpjsyl);
			System.out.println("ƽ�������"+pjcdb);
			System.out.println("ת�Ƽ۸�"+zejg);
			result =CommonFunctions.doublecut(zejg*100,3)+","+CommonFunctions.doublecut(ckpjcbl*100,3)+","+
			CommonFunctions.doublecut(dkpjsyl*100,3)+","+CommonFunctions.doublecut(pjcdb*100,3)+","+adjustValue;
		}
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(result);//��������
		return null;
	}
	
	public String back() throws Exception {
		return "back";
	}
	//���۷���
	public String save() throws Exception {
		DaoFactory df = new DaoFactory();
		//һ�����ڶ���һ�Σ�����������ظ����ۣ��򱣴����һ�εļ�¼
		String hsql = "from FtpResult where prcMode = '1' and prcMethod = '"+method+"' and br_No = '"+brNo+"' and cur_No = '"+currencySelectId+"' and resDate like '"+date.substring(0,6)+"%'";
		FtpResult ftpResult1 = (FtpResult)df.getBean(hsql, null);
		if (ftpResult1 != null) {
			String hsql1 = "delete from FtpResult where resultId = "+ftpResult1.getResultId();
			df.delete(hsql1,null);
		}
		FtpResult ftpResult = new FtpResult();
		ftpResult.setResultId(IDUtil.getInstanse().getUID());
		ftpResult.setBrMst(new BrMst(brNo));
		ftpResult.setAlmCur(new AlmCur(currencySelectId));
		ftpResult.setPrcMode("1");//���ʽ��
		ftpResult.setPoolNo("101");
		ftpResult.setPrcMethod(method);
		ftpResult.setResultPrice(Double.valueOf(resValue)/100);
		ftpResult.setAfresValue((adjustValue == null ? 0.0 : adjustValue)/100);
		ftpResult.setResDate(date);
		df.insert(ftpResult);
		return null;
	}
	
	//��ȡ���۽��
	public String getResultPrice() throws Exception {
		//�����������ȡ�������ڵĶ��۽��
		String sql = "select * from " +
				"(select b.br_name, a.curname, (case t.PRC_METHOD when '1' then '�ɱ����淨' else '��Ȩƽ���ɱ���' end) prc_method_name, t.prc_method, t.Result_price, t.RES_DATE, t.br_no, " +
				"row_number() over(partition by t.br_No order by  t.RES_DATE desc,t.result_id desc ) rn from ftp.Ftp_Result t " +
				"left join ftp.br_mst b on t.br_no = b.br_no " +
				"left join ftp.alm_cur a on t.cur_no = a.curno " +
				"where prc_Mode = '1' and cur_no = '01') " +
				"where rn=1";

        String pageName = "UL01_getResultPrice.action?pageSize="+pageSize;
		
        if (brNo != null && !brNo.equals("") && !brNo.equals("null")) {
        	sql += " and br_no = '"+brNo+"'";
			pageName +="&brNo="+brNo;
		}
        sql += " order by br_no";
        resultPriceUtil = queryPageBO.sqlQueryPage(sql, pageSize, page, rowsCount, pageName);
		request.setAttribute("resultPriceUtil", resultPriceUtil);
        return "resultPrice";
	}
	
	//��ʷת�Ƽ۸�,��ȡ��ǰ��12���µ�
	public String history() throws Exception {
		DaoFactory df = new DaoFactory();
		HttpServletRequest request = ServletActionContext.getRequest();
		//��ǰ��12����
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = FtpUtil.date2Calendar(sdf.parse(date));
		cal.add(Calendar.MONTH, -11);
		StringBuffer yTransPrice = new StringBuffer();
		StringBuffer xPricingDate = new StringBuffer();
		for (int i = 0; i < 12; i++) {
			String before = sdf.format(cal.getTime()).substring(0, 6);
			String sql = "from FtpResult where prcMode = '"+prcMode+"' and prcMethod = '"+method+"' and br_No = '"+brNo+"' and cur_No = '"+currencySelectId+"' and resDate like '"+before+"%'";
			System.out.println("sql:"+sql);
			FtpResult ftpResult = (FtpResult)df.getBean(sql,null);
			xPricingDate.append(before).append(",");
			if (ftpResult != null) {
				yTransPrice.append(CommonFunctions.doublecut(ftpResult.getResultPrice()*100,2)).append(",");
			}else {
				yTransPrice.append(0.0).append(",");
			}
		    
		    cal.add(Calendar.MONTH, 1);
		}
		if(yTransPrice.length()>0){
	        yTransPrice = yTransPrice.deleteCharAt(yTransPrice.length()-1);
		    xPricingDate = xPricingDate.deleteCharAt(xPricingDate.length()-1);
		}
	    System.out.println(yTransPrice);
	    System.out.println(xPricingDate);
	    request.setAttribute("methodName", methodName);
		request.setAttribute("yTransPrice", yTransPrice);
		request.setAttribute("xPricingDate", xPricingDate);
		return "history";
	}
	
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getCurrencySelectId() {
		return currencySelectId;
	}

	public void setCurrencySelectId(String currencySelectId) {
		this.currencySelectId = currencySelectId;
	}

	public String getBrNo() {
		return brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getResValue() {
		return resValue;
	}

	public void setResValue(String resValue) {
		this.resValue = resValue;
	}
	public String getPrcMode() {
		return prcMode;
	}
	public void setPrcMode(String prcMode) {
		this.prcMode = prcMode;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
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
	public PageUtil getResultPriceUtil() {
		return resultPriceUtil;
	}
	public void setResultPriceUtil(PageUtil resultPriceUtil) {
		this.resultPriceUtil = resultPriceUtil;
	}
	public Double getAdjustValue() {
		return adjustValue;
	}
	public void setAdjustValue(Double adjustValue) {
		this.adjustValue = adjustValue;
	}

}
