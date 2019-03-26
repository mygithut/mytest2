package com.dhcc.ftp.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.AlmCur;
import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.FtpPoolInfo;
import com.dhcc.ftp.entity.FtpResult;
import com.dhcc.ftp.entity.FtpResultSfb;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;
import com.opensymphony.xwork2.ActionContext;

/**
 * <p>
 * Title: 多资金池
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Company: 东华软件股份公司金融事业部
 * </p>
 * 
 * @author 孙红玉
 * 
 * @date august 23, 2011 4:29:33 PM
 * 
 * @version 1.0
 */
public class UL03Action extends BoBuilder {
	private String currencySelectId;
	private String brNo;
	private String[] poolNo;
	private String[] poolType;
	private String[] adjustValue;
	private String[] resultPrice;
	private String date;
	DaoFactory df = new DaoFactory();
	private int page = 1;
	private int pageSize = 10;
	private int rowsCount = -1;
	private PageUtil resultPriceUtil = null;
	HttpServletRequest request = ServletActionContext.getRequest();
	HttpSession Session = this.getSession();
	String filename;

	public String execute() throws Exception {
		return super.execute();
	}

	public String getPoolList() {

		String hsql = "from FtpPoolInfo where prcMode = '3' and brNo = '"
				+ brNo + "' order by poolNo";
		List<FtpPoolInfo> poolList = df.query(hsql, null);
		request.setAttribute("poolList", poolList);
		Session.setAttribute("poolList", poolList);
		return "list";
	}

	public String calculate() throws Exception {
		HttpServletResponse resp = ServletActionContext.getResponse();
		List<Double[]> ftpResultValue = new ArrayList<Double[]>();

		StringBuffer result = new StringBuffer();
		Double zcxyfxxz = 0.0, fzxyfxxz = 0.0, adjustVal = 0.0;
		String brSql = LrmUtil.getBrSql(brNo);
		String prdtNoZc = reportBO.getPrdtNoByPrcMode(brNo, "3", "1"); // 多资金池所配置的资产类产品
		if (prdtNoZc.indexOf("P1001") != -1)
			prdtNoZc = prdtNoZc.replace(",'P1001'", "");
		if (prdtNoZc.indexOf("P1016") != -1)
			prdtNoZc = prdtNoZc.replace(",'P1016'", "");
		if (prdtNoZc.indexOf("P1112") != -1)
			prdtNoZc = prdtNoZc.replace(",'P1112'", "");
		if (prdtNoZc.indexOf("P1125") != -1)
			prdtNoZc = prdtNoZc.replace(",'P1125'", "");
		if (prdtNoZc.indexOf("P1128") != -1)
			prdtNoZc = prdtNoZc.replace(",'P1128'", "");
		String prdtNoFz = reportBO.getPrdtNoByPrcMode(brNo, "3", "2"); // 多资金池所配置的负债类产品
		if (prdtNoFz.indexOf("P2071") != -1)
			prdtNoFz = prdtNoFz.replace(",'P2071'", "");
		if (prdtNoFz.indexOf("P2077") != -1)
			prdtNoFz = prdtNoFz.replace(",'P2077'", "");
		if (prdtNoFz.indexOf("P2082") != -1)
			prdtNoFz = prdtNoFz.replace(",'P2082'", "");
		if (prdtNoFz.indexOf("P2083") != -1)
			prdtNoFz = prdtNoFz.replace(",'P2083'", "");
		if (prdtNoFz.indexOf("P2085") != -1)
			prdtNoFz = prdtNoFz.replace(",'P2085'", "");
		Double zcjqpjlv = FtpUtil.getWeightedAveRate(brSql, date, prdtNoZc,
				"3", -12, false);// 资产类加权平均利率
		Double fzjqpjlv = FtpUtil.getWeightedAveRate(brSql, date, prdtNoFz,
				"3", -12, false);// 负债类加权平均利率
		// 国债期望收益率
		Double gzqwsyl = FtpUtil.getGzqwsyl(brNo, date);
		;
		System.out.println("国债期望收益率:" + gzqwsyl);
		// 负债要进行信用风险修正
		fzxyfxxz = FtpUtil.getXyfxxz(currencySelectId, brNo, date); // 信用风险修正
		System.out.println("负债信用风险修正:" + fzxyfxxz);
		// Double lcxz = (zcjqpjlv - fzjqpjlv)/2;//利差修正
		Double lcxz = 0.0;// 利差修正
		zcjqpjlv = zcjqpjlv - zcxyfxxz - gzqwsyl / 2;
		fzjqpjlv = fzjqpjlv + fzxyfxxz + gzqwsyl / 2;
		lcxz = (zcjqpjlv - fzjqpjlv) / 2;// 利差修正根据调整后的数来计算
		for (int i = 0; i < poolNo.length; i++) {// 循环每一行资金池
			// 产品加权平均利率
			Double cpjqpjll = FtpUtil.getCpjqpjRate(currencySelectId, brNo,
					date, poolNo[i]);

			adjustVal = (adjustValue[i] == null || adjustValue[i].equals("")) ? 0.0
					: Double.valueOf(adjustValue[i]);
			Double zyjg = 0.0;
			if (poolType[i].equals("1")) {// 资产
				System.out.println("投资收益率:" + cpjqpjll);

				zyjg = cpjqpjll - zcxyfxxz - gzqwsyl / 2 - lcxz + adjustVal
						/ 100;
				result.append(poolNo[i] + "," + poolType[i] + ","
						+ CommonFunctions.doublecut(zyjg * 100, 3) + ","
						+ CommonFunctions.doublecut(cpjqpjll * 100, 3) + ","
						+ CommonFunctions.doublecut(zcxyfxxz * 100, 3) + ","
						+ CommonFunctions.doublecut(-gzqwsyl / 2 * 100, 3)
						+ "," + adjustVal + ","
						+ CommonFunctions.doublecut(-lcxz * 100, 3) + "|");
			} else {// 负债
				System.out.println("融资成本率:" + cpjqpjll);
				zyjg = cpjqpjll + fzxyfxxz + gzqwsyl / 2 + lcxz + adjustVal
						/ 100;
				result.append(poolNo[i] + "," + poolType[i] + ","
						+ CommonFunctions.doublecut(zyjg * 100, 3) + ","
						+ CommonFunctions.doublecut(cpjqpjll * 100, 3) + ","
						+ CommonFunctions.doublecut(fzxyfxxz * 100, 3) + ","
						+ CommonFunctions.doublecut(gzqwsyl / 2 * 100, 3) + ","
						+ adjustVal + ","
						+ CommonFunctions.doublecut(lcxz * 100, 3) + "|");
			}
			Double[] frv = { adjustVal, zyjg };
			ftpResultValue.add(i, frv);

		}
		System.out.println("zcjqpjlv:" + zcjqpjlv);
		System.out.println("fzjqpjlv:" + fzjqpjlv);
		System.out.println("资产类产品:" + prdtNoZc);
		System.out.println("负债类产品:" + prdtNoFz);
		result.deleteCharAt(result.length() - 1);
		System.out.println("result" + result);
		resp.setContentType("text/plain;charset=UTF-8");
		resp.getWriter().print(result.toString());// 四舍五入
		Session.setAttribute("ftpResultValue", ftpResultValue);
		return null;
	}

	/**
	 * 
	 * @return
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public String doExport() throws IOException, RowsExceededException,
			WriteException {

		List<Double[]> ftpResultValue = (List<Double[]>) Session
				.getAttribute("ftpResultValue");
		List<FtpPoolInfo> poolList = (List<FtpPoolInfo>) Session
				.getAttribute("poolList");
		HttpServletRequest request = (HttpServletRequest) ActionContext
				.getContext().get(StrutsStatics.HTTP_REQUEST);
		HSSFWorkbook workbook = uL04BO.getzjcWorkbook(poolList, ftpResultValue,
				brNo,date);

		if (workbook != null) {
			// 第四步，将工作簿写入最上面定义的InputStream流-名称为excelStream，这个名字对应struts.xml中配置的inputName参数。
			workbook2InputStream(workbook, LrmUtil.getBrName(brNo));
			return null;
		} else {
			request.setAttribute("message", "创建Excel失败");
			return "error";
		}
	}

	// 创建workbook,将workbook写入到InputStream
	public void workbook2InputStream(HSSFWorkbook workbook, String fileName) {
		try {
			 this.filename = FtpUtil.toUtf8String(fileName); 
			this.getResponse().setHeader("content-disposition",
					"attachment;filename=" + filename + ".xls");
			OutputStream baos = this.getResponse().getOutputStream();
			workbook.write(baos);
			baos.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 定价发布
	public String save() throws Exception {
		DaoFactory df = new DaoFactory();
		Double adjustVal = 0.0, resultValue = 0.0;

		for (int i = 0; i < poolNo.length; i++) {// 循环每一行资金池
			// 一个月内定价一次，如果出现了重复定价，则保存最后一次的记录
			String hsql = "from FtpResult where prcMode = '3' and poolNo = '"
					+ poolNo[i] + "' and br_No = '" + brNo + "' and cur_No = '"
					+ currencySelectId + "' and resDate like '"
					+ date.substring(0, 6) + "%'";
			FtpResult ftpResult1 = (FtpResult) df.getBean(hsql, null);
			if (ftpResult1 != null) {
				String hsql1 = "delete from FtpResult where resultId = "
						+ ftpResult1.getResultId();
				df.delete(hsql1, null);
			}
			adjustVal = (adjustValue[i] == null || adjustValue[i].equals("")) ? 0.0
					: Double.valueOf(adjustValue[i]) / 100;
			resultValue = (resultPrice[i] == null || resultPrice[i].equals("")) ? 0.0
					: Double.valueOf(resultPrice[i]) / 100;
			FtpResult ftpResult = new FtpResult();
			ftpResult.setResultId(IDUtil.getInstanse().getUID());
			ftpResult.setBrMst(new BrMst(brNo));
			ftpResult.setAlmCur(new AlmCur(currencySelectId));
			ftpResult.setPrcMode("3");
			ftpResult.setPoolNo(poolNo[i]);
			ftpResult.setResultPrice(resultValue);
			ftpResult.setAfresValue(adjustVal);
			ftpResult.setResDate(date);
			df.insert(ftpResult);
		}

		return null;
	}
	
	//定价试发布
	public String save_sfb() throws Exception {
		DaoFactory df = new DaoFactory();
		Double adjustVal = 0.0, resultValue = 0.0;
		
		for(int i = 0; i < poolNo.length; i++) {//循环每一行资金池
			//一个月内定价一次，如果出现了重复定价，则保存最后一次的记录
			String hsql = "from FtpResultSfb where prcMode = '3' and poolNo = '"+poolNo[i]+"' and br_No = '"+brNo+"' and cur_No = '"+currencySelectId+"' and resDate like '"+date.substring(0,6)+"%'";
			FtpResultSfb ftpResult1 = (FtpResultSfb)df.getBean(hsql, null);
			if (ftpResult1 != null) {
				String hsql1 = "delete from FtpResultSfb where resultId = "+ftpResult1.getResultId();
				df.delete(hsql1,null);
			}
			adjustVal = (adjustValue[i] == null || adjustValue[i].equals("")) ? 0.0 : Double.valueOf(adjustValue[i])/100;
			resultValue = (resultPrice[i] == null || resultPrice[i].equals("")) ? 0.0 : Double.valueOf(resultPrice[i])/100;
			FtpResultSfb ftpResultSfb = new FtpResultSfb();
			ftpResultSfb.setResultId(IDUtil.getInstanse().getUID());
			ftpResultSfb.setBrMst(new BrMst(brNo));
			ftpResultSfb.setAlmCur(new AlmCur(currencySelectId));
			ftpResultSfb.setPrcMode("3");
			ftpResultSfb.setPoolNo(poolNo[i]);
			ftpResultSfb.setResultPrice(resultValue);
			ftpResultSfb.setAfresValue(adjustVal);
			ftpResultSfb.setResDate(date);
			df.insert(ftpResultSfb);
		}
		
		return null;
	}
	//获取定价结果
	public String getResultPrice() throws Exception {
		
		resultPriceUtil = uL03BO.sqlQueryPage(brNo, pageSize, page,
				rowsCount);
		request.setAttribute("resultPriceUtil", resultPriceUtil);
		Session.setAttribute("resultPriceUtil", resultPriceUtil);
		return "resultPrice";
	}

	// 历史转移价格,获取往前数12个月的
	public String history() throws Exception {
		DaoFactory df = new DaoFactory();
		HttpServletRequest request = ServletActionContext.getRequest();
		// 往前数12个月
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = FtpUtil.date2Calendar(sdf.parse(date));
		cal.add(Calendar.MONTH, -11);
		StringBuffer yTransPrice = new StringBuffer();
		StringBuffer xPricingDate = new StringBuffer();
		for (int i = 0; i < 12; i++) {
			String before = sdf.format(cal.getTime()).substring(0, 6);
			System.out.println("poolNo" + poolNo);
			String sql = "from FtpResult where prcMode = '3' and poolNo = '"
					+ poolNo[0] + "' and br_No = '" + brNo + "' and cur_No = '"
					+ currencySelectId + "' and resDate like '" + before + "%' order by resDate desc";
			System.out.println("sql:" + sql);
			FtpResult ftpResult = (FtpResult) df.getBean(sql, null);
			xPricingDate.append(before).append(",");
			if (ftpResult != null) {
				yTransPrice.append(CommonFunctions.doublecut(ftpResult.getResultPrice()*100, 2)).append(",");
			} else {
				yTransPrice.append(0.0).append(",");
			}

			cal.add(Calendar.MONTH, 1);
		}
		if (yTransPrice.length() > 0) {
			yTransPrice = yTransPrice.deleteCharAt(yTransPrice.length() - 1);
			xPricingDate = xPricingDate.deleteCharAt(xPricingDate.length() - 1);
		}
		System.out.println(yTransPrice);
		System.out.println(xPricingDate);
		request.setAttribute("yTransPrice", yTransPrice);
		request.setAttribute("xPricingDate", xPricingDate);
		return "history";
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

	public void setResultPrice(String[] resultPrice) {
		this.resultPrice = resultPrice;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	public String[] getPoolNo() {
		return poolNo;
	}

	public void setPoolNo(String[] poolNo) {
		this.poolNo = poolNo;
	}

	public String[] getPoolType() {
		return poolType;
	}

	public void setPoolType(String[] poolType) {
		this.poolType = poolType;
	}

	public String[] getAdjustValue() {
		return adjustValue;
	}

	public void setAdjustValue(String[] adjustValue) {
		this.adjustValue = adjustValue;
	}

}
