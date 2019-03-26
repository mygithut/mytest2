package com.dhcc.ftp.bo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;

import com.dhcc.ftp.cache.CacheOperation;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.FtpBusinessStaticDivide;
import com.dhcc.ftp.entity.FtpEmpInfo;
import com.dhcc.ftp.entity.FtpProductMethodRel;
import com.dhcc.ftp.entity.TelBrConfig;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.entity.YlfxBbReport;
import com.dhcc.ftp.entity.YlfxReport;
import com.dhcc.ftp.util.CastUtil;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.ExcelExport;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;

public class ReportBbBO extends BaseBo {
	public Map<String, List<BrMst>> brMstMap = this.getBrByEmpNo();
	CacheOperation co = CacheOperation.getInstance();//缓存
    long intervalTime = Long.valueOf("2592000000");//缓存存放一个月
    int maxVisitCount = 0;//不限制访问次数
	/**
	 * 机构总盈利分析
	 * @param request
	 * @param minDate
	 * @param maxDate
	 * @param brNo
	 * @param manageLvl 机构级别
	 * @param assessScope 统计维度 月-1、-3、-12
	 * @param isMx 是否查看明细
	 * @return
	 */
	public List<YlfxBbReport> brPayOffProfile(HttpServletRequest request, String minDate, String maxDate, String brNo,String brCountLvl, String manageLvl, Integer assessScope, Integer isMx) {
	    
	    TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
	    List<YlfxBbReport> ylfxReportList = new ArrayList<YlfxBbReport>();
	    
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(brNo, manageLvl);// 县联社
		System.out.println("县联社：" + xlsBrNo);
		
		int days = CommonFunctions.daysSubtract(maxDate, minDate);//天数

		//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultPrdtList",new Object[]{xlsBrNo, minDate, maxDate,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultPrdtList(xlsBrNo, minDate, maxDate,assessScope);
		if(ftpResultsList == null) return null;
		
		//获取该操作员可以查看的客户类型
		String custType = this. getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		//总行默认显示下级机构|支行默认显示支行，点击明细再显示下级
		if (isMx == 0 && !manageLvl.equals("3")&&!manageLvl.equals("2")) {// 如果不是查看子机构的盈利分析，则直接查看该机构的盈利分析
			YlfxBbReport ylfxBbReport = new YlfxBbReport();
			String brSql = LrmUtil.getBrSqlByTel(brNo, telMst.getTelNo());
			ylfxBbReport.setBrName(brInfoDAO.getInfo(brNo).getBrName());
			ylfxBbReport.setBrNo(brNo);
			ylfxBbReport.setManageLvl(manageLvl);
			ylfxBbReport.setLeaf(this.getIsLeaf(brNo));
			double[] resultValue = this.getQxppValueByBrNo(ftpResultsList, brSql, custType);
			ylfxBbReport.setZcbal(resultValue[4]);// 资产余额
			ylfxBbReport.setZcrjye(resultValue[0]);// 资产日均余额
			ylfxBbReport.setZcftplr(resultValue[1]);//资产FTP利润
			ylfxBbReport.setZclc(resultValue[0]==0?0.00:resultValue[1]/resultValue[0]*360/days);//资产利差(年利率)

			ylfxBbReport.setFzbal(resultValue[5]);  //负债余额
			ylfxBbReport.setGrhqbal(resultValue[6]);  //负债余额
			ylfxBbReport.setGrdqbal(resultValue[7]);  //负债余额
			ylfxBbReport.setDwhqbal(resultValue[8]);  //负债余额
			ylfxBbReport.setDwdqbal(resultValue[9]);  //负债余额
			ylfxBbReport.setCzxbal(resultValue[10]);  //负债余额
			ylfxBbReport.setYhkbal(resultValue[11]);  //负债余额
			ylfxBbReport.setYjhkbal(resultValue[12]);  //负债余额
			ylfxBbReport.setBzjbal(resultValue[13]);  //负债余额

			ylfxBbReport.setFzrjye(resultValue[2]);// 负债日均余额
			ylfxBbReport.setGrhqrjye(resultValue[14]);  //负债日均余额
			ylfxBbReport.setGrdqrjye(resultValue[15]);  //负债日均余额
			ylfxBbReport.setDwhqrjye(resultValue[16]);  //负债日均余额
			ylfxBbReport.setDwdqrjye(resultValue[17]);  //负债日均余额
			ylfxBbReport.setCzxrjye(resultValue[18]);  //负债日均余额
			ylfxBbReport.setYhkrjye(resultValue[19]);  //负债日均余额
			ylfxBbReport.setYjhkrjye(resultValue[20]);  //负债日均余额
			ylfxBbReport.setBzjrjye(resultValue[21]);  //负债日均余额
			ylfxBbReport.setFzftplr(resultValue[3]);// 负债FTP利润
			ylfxBbReport.setFzlc(resultValue[2]==0?0.00:resultValue[3]/resultValue[2]*360/days);//负债利差(年利率)
			ylfxBbReport.setFtplrhj(ylfxBbReport.getZcftplr()+ylfxBbReport.getFzftplr());// FTP利润合计
			
			ylfxReportList.add(ylfxBbReport);
		}else if(isMx == 0&&brCountLvl.equals("0")){
			List<BrMst> brMstList = getBrMstList(brNo, brCountLvl);
			Map<String,Double[]> QxppValue_map = this.getQxppValueMap_qhsyjgftphzb(ftpResultsList, brMstList, telMst.getTelNo(), custType);
			for (int i = 0; i < brMstList.size(); i++) {
				BrMst brMst = brMstList.get(i);
				System.out.println("开始计算机构:"+brMst.getBrNo()+"...");
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setBrName(brMst.getBrName());
				ylfxBbReport.setBrNo(brMst.getBrNo());
				ylfxBbReport.setManageLvl(brMst.getManageLvl());
				ylfxBbReport.setLeaf(this.getIsLeaf(brMst.getBrNo()));

				Double[] qxppZcValue = QxppValue_map.get(brMst.getBrNo()+"-1");
				ylfxBbReport.setZcbal(qxppZcValue[2]);// 资产余额
				ylfxBbReport.setZcrjye(qxppZcValue[0]);// 资产日均余额
				ylfxBbReport.setZcftplr(qxppZcValue[1]);//资产FTP利润
				ylfxBbReport.setZclc(qxppZcValue[0]==0?0.00:qxppZcValue[1]/qxppZcValue[0]*360/days);//资产利差(年利率)

				Double[] qxppFzValue = QxppValue_map.get(brMst.getBrNo()+"-2");
				ylfxBbReport.setFzbal(qxppFzValue[2]);// 负债余额
				ylfxBbReport.setGrhqbal(qxppFzValue[3]);// 负债余额
				ylfxBbReport.setGrdqbal(qxppFzValue[4]);// 负债余额
				ylfxBbReport.setDwhqbal(qxppFzValue[5]);// 负债余额
				ylfxBbReport.setDwdqbal(qxppFzValue[6]);// 负债余额
				ylfxBbReport.setCzxbal(qxppFzValue[7]);// 负债余额
				ylfxBbReport.setYhkbal(qxppFzValue[8]);// 负债余额
				ylfxBbReport.setYjhkbal(qxppFzValue[9]);// 负债余额
				ylfxBbReport.setBzjbal(qxppFzValue[10]);// 负债余额

				ylfxBbReport.setFzrjye(qxppFzValue[0]);// 负债日均余额
				ylfxBbReport.setFzftplr(qxppFzValue[1]);// 负债FTP利润
				ylfxBbReport.setGrhqrjye(qxppFzValue[11]);// 负债日均余额
				ylfxBbReport.setGrdqrjye(qxppFzValue[12]);// 负债日均余额
				ylfxBbReport.setDwhqrjye(qxppFzValue[13]);// 负债日均余额
				ylfxBbReport.setDwdqrjye(qxppFzValue[14]);// 负债日均余额
				ylfxBbReport.setCzxrjye(qxppFzValue[15]);// 负债日均余额
				ylfxBbReport.setYhkrjye(qxppFzValue[16]);// 负债日均余额
				ylfxBbReport.setYjhkrjye(qxppFzValue[17]);// 负债日均余额
				ylfxBbReport.setBzjrjye(qxppFzValue[18]);// 负债日均余额
				ylfxBbReport.setFzlc(qxppFzValue[0]==0?0.00:qxppFzValue[1]/qxppFzValue[0]*360/days);//负债利差(年利率)
				ylfxBbReport.setFtplrhj(ylfxBbReport.getZcftplr()+ylfxBbReport.getFzftplr());// FTP利润合计
				ylfxReportList.add(ylfxBbReport);
			}
			//对ylfxBbReportList按FTP利润大小进行降序排序
			Collections.sort(ylfxReportList, new Comparator<YlfxBbReport>() {
						public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
							return arg1.getFtplrhj().compareTo(arg0.getFtplrhj());
						}
					}
			);
		}
		else {
			// 获取该机构的下级机构
			List<BrMst> brMstList = getChildBrListByTel(brNo, telMst.getTelNo());
			Map<String,Double[]> QxppValue_map = this.getQxppValueMap_qhsyjgftphzb(ftpResultsList, brMstList, telMst.getTelNo(), custType);
			for (int i = 0; i < brMstList.size(); i++) {
				BrMst brMst = brMstList.get(i);
				System.out.println("开始计算机构:"+brMst.getBrNo()+"...");
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setBrName(brMst.getBrName());
				ylfxBbReport.setBrNo(brMst.getBrNo());
				ylfxBbReport.setManageLvl(brMst.getManageLvl());
				ylfxBbReport.setLeaf(this.getIsLeaf(brMst.getBrNo()));
				
				Double[] qxppZcValue = QxppValue_map.get(brMst.getBrNo()+"-1");
				ylfxBbReport.setZcbal(qxppZcValue[2]);// 资产余额
				ylfxBbReport.setZcrjye(qxppZcValue[0]);// 资产日均余额
				ylfxBbReport.setZcftplr(qxppZcValue[1]);//资产FTP利润
				ylfxBbReport.setZclc(qxppZcValue[0]==0?0.00:qxppZcValue[1]/qxppZcValue[0]*360/days);//资产利差(年利率)
				
				Double[] qxppFzValue = QxppValue_map.get(brMst.getBrNo()+"-2");
				ylfxBbReport.setFzbal(qxppFzValue[2]);// 负债余额
				ylfxBbReport.setGrhqbal(qxppFzValue[3]);// 负债余额
				ylfxBbReport.setGrdqbal(qxppFzValue[4]);// 负债余额
				ylfxBbReport.setDwhqbal(qxppFzValue[5]);// 负债余额
				ylfxBbReport.setDwdqbal(qxppFzValue[6]);// 负债余额
				ylfxBbReport.setCzxbal(qxppFzValue[7]);// 负债余额
				ylfxBbReport.setYhkbal(qxppFzValue[8]);// 负债余额
				ylfxBbReport.setYjhkbal(qxppFzValue[9]);// 负债余额
				ylfxBbReport.setBzjbal(qxppFzValue[10]);// 负债余额

				ylfxBbReport.setFzrjye(qxppFzValue[0]);// 负债日均余额
				ylfxBbReport.setFzftplr(qxppFzValue[1]);// 负债FTP利润
				ylfxBbReport.setGrhqrjye(qxppFzValue[11]);// 负债日均余额
				ylfxBbReport.setGrdqrjye(qxppFzValue[12]);// 负债日均余额
				ylfxBbReport.setDwhqrjye(qxppFzValue[13]);// 负债日均余额
				ylfxBbReport.setDwdqrjye(qxppFzValue[14]);// 负债日均余额
				ylfxBbReport.setCzxrjye(qxppFzValue[15]);// 负债日均余额
				ylfxBbReport.setYhkrjye(qxppFzValue[16]);// 负债日均余额
				ylfxBbReport.setYjhkrjye(qxppFzValue[17]);// 负债日均余额
				ylfxBbReport.setBzjrjye(qxppFzValue[18]);// 负债日均余额
				ylfxBbReport.setFzlc(qxppFzValue[0]==0?0.00:qxppFzValue[1]/qxppFzValue[0]*360/days);//负债利差(年利率)
				ylfxBbReport.setFtplrhj(ylfxBbReport.getZcftplr()+ylfxBbReport.getFzftplr());// FTP利润合计
				ylfxReportList.add(ylfxBbReport);
			}
			//对ylfxBbReportList按FTP利润大小进行降序排序
			Collections.sort(ylfxReportList, new Comparator<YlfxBbReport>() {
				 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
					 return arg1.getFtplrhj().compareTo(arg0.getFtplrhj());
					 }
				 }
			);
		}



		return ylfxReportList;
	}
	
	/**
	 * 全行所有机构FTP利润排名
	 * @param request
	 * @param minDate
	 * @param maxDate
	 * @param brCountLvl 机构统计级别
	 * @param assessScope 统计维度
	 * @return
	 */
	public List<YlfxBbReport> brPayOffRanking(HttpServletRequest request, String minDate, String maxDate, String brCountLvl, Integer assessScope) {
	    
	    List<YlfxBbReport> ylfxReportList = new ArrayList<YlfxBbReport>();
	    TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
	    String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());// 县联社
		System.out.println("县联社：" + xlsBrNo);
		
		// 获取该机构的所有1级机构
		//List<BrMst> brMstList = getBrLvl1List(brNo, telMst.getTelNo());//根据权限判断查看哪些机构
       // List<BrMst> brMstList = getBrLvl1List(xlsBrNo, "");//目前排名可以查看所有机构
		int days = CommonFunctions.daysSubtract(maxDate, minDate);//天数
		//根据机构级别获取该县联社下该级别下的所有机构
		List<BrMst> brMstList = getBrMstList(xlsBrNo, brCountLvl);
		
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, maxDate,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultPrdtList(xlsBrNo, minDate, maxDate,assessScope);
		if(ftpResultsList == null) return null;
		
		//获取该操作员可以查看的客户类型
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		//String custType = "0";//目前利润排名可以查看所有的业务条线，没有权限限制
		Map<String,Double[]> QxppValue_map=this.getQxppValueMap_qhsyjgftphzb(ftpResultsList, brMstList, telMst.getTelNo(), custType);

		for(BrMst brMst : brMstList) {
			System.out.println("开始计算机构:"+brMst.getBrNo()+"...");
			YlfxBbReport ylfxBbReport = new YlfxBbReport();
			ylfxBbReport.setBrName(brMst.getBrName());
			ylfxBbReport.setBrNo(brMst.getBrNo());
			ylfxBbReport.setManageLvl(brMst.getManageLvl());
			ylfxBbReport.setLeaf(this.getIsLeaf(brMst.getBrNo()));

			Double[] qxppZcValue = QxppValue_map.get(brMst.getBrNo()+"-1");
			ylfxBbReport.setZcbal(qxppZcValue[2]);// 资产余额
			ylfxBbReport.setZcrjye(qxppZcValue[0]);// 资产日均余额
			ylfxBbReport.setZcftplr(qxppZcValue[1]);//资产FTP利润
			ylfxBbReport.setGrdkftplr(qxppZcValue[3]);//个人贷款FTP利润
			ylfxBbReport.setGsdkftplr(qxppZcValue[4]);//公司贷款FTP利润
			ylfxBbReport.setZclc(qxppZcValue[0]==0?0.00:qxppZcValue[1]/qxppZcValue[0]*360/days);//资产利差(年利率)

			Double[] qxppFzValue = QxppValue_map.get(brMst.getBrNo()+"-2");
			ylfxBbReport.setFzbal(qxppFzValue[2]);// 负债余额
			ylfxBbReport.setGrhqbal(qxppFzValue[3]);// 负债余额
			ylfxBbReport.setGrdqbal(qxppFzValue[4]);// 负债余额
			ylfxBbReport.setDwhqbal(qxppFzValue[5]);// 负债余额
			ylfxBbReport.setDwdqbal(qxppFzValue[6]);// 负债余额
			ylfxBbReport.setCzxbal(qxppFzValue[7]);// 负债余额
			ylfxBbReport.setYhkbal(qxppFzValue[8]);// 负债余额
			ylfxBbReport.setYjhkbal(qxppFzValue[9]);// 负债余额
			ylfxBbReport.setBzjbal(qxppFzValue[10]);// 负债余额

			ylfxBbReport.setFzrjye(qxppFzValue[0]);// 负债日均余额
			ylfxBbReport.setFzftplr(qxppFzValue[1]);// 负债FTP利润
			ylfxBbReport.setGrhqrjye(qxppFzValue[11]);// 负债日均余额
			ylfxBbReport.setGrdqrjye(qxppFzValue[12]);// 负债日均余额
			ylfxBbReport.setDwhqrjye(qxppFzValue[13]);// 负债日均余额
			ylfxBbReport.setDwdqrjye(qxppFzValue[14]);// 负债日均余额
			ylfxBbReport.setCzxrjye(qxppFzValue[15]);// 负债日均余额
			ylfxBbReport.setYhkrjye(qxppFzValue[16]);// 负债日均余额
			ylfxBbReport.setYjhkrjye(qxppFzValue[17]);// 负债日均余额
			ylfxBbReport.setBzjrjye(qxppFzValue[18]);// 负债日均余额

            ylfxBbReport.setDsftplr(qxppFzValue[19]);// 对私利润
            ylfxBbReport.setDgftplr(qxppFzValue[20]);// 对公利润

			ylfxBbReport.setGrhqftplr(qxppFzValue[21]);
			ylfxBbReport.setGrdqftplr(qxppFzValue[22]);
			ylfxBbReport.setDwhqftplr(qxppFzValue[23]);
			ylfxBbReport.setDwdqftplr(qxppFzValue[24]);
			ylfxBbReport.setCzxftplr(qxppFzValue[25]);
			ylfxBbReport.setYhkftplr(qxppFzValue[26]);
			ylfxBbReport.setYjhkftplr(qxppFzValue[27]);
			ylfxBbReport.setBzjftplr(qxppFzValue[28]);

			ylfxBbReport.setFzlc(qxppFzValue[0]==0?0.00:qxppFzValue[1]/qxppFzValue[0]*360/days);//负债利差(年利率)
			ylfxBbReport.setFtplrhj(ylfxBbReport.getZcftplr()+ylfxBbReport.getFzftplr());// FTP利润合计
			ylfxReportList.add(ylfxBbReport);
		}
		//对ylfxBbReportList按FTP利润合计大小进行降序排序
		Collections.sort(ylfxReportList, new Comparator<YlfxBbReport>() {
			 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
				 return arg1.getFtplrhj().compareTo(arg0.getFtplrhj());
				 }
			 }
		);
		return ylfxReportList;
	}

	/**
	 * 机构业务条线FTP利润汇总表-- 所有业务类型的数据获取
	 * @param request
	 * @param minDate
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @return
	 */
	public List<YlfxBbReport> busPayOffProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, Integer assessScope) {
		
	    TelMst telMst=(TelMst)request.getSession().getAttribute("userBean");
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
		
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(brNo, manageLvl);// 县联社

		int days = CommonFunctions.daysSubtract(date, minDate);//天数
		//从缓存中获取数据
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultPrdtList(xlsBrNo, minDate, date,assessScope);
		if(ftpResultsList == null) return null;
		
		String brNos = this.getAllChildBrAllbrno(brNo, manageLvl, telMst.getTelNo());//获取所有的子级机构，包括自己
		//获取该操作员可以查看的客户类型
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());

		//生成以产品编号为KEY的结果集
		Map<String,Double[]> QxppValue_map = this.getQxppValueMap_ywtxylfx(ftpResultsList, xlsBrNo, brNos, custType);
		
		//获取统计大类
		String sql1 = "select distinct business_no, business_name,substr(business_no,3,1) as zctype " +
		" from ftp.FTP_business_static_divide" +
		//" where br_no = '"+xlsBrNo+"'" +
		" order by zctype,business_no";//先按资产负债类型，再按编号排序
        List tjdlList = daoFactory.query1(sql1, null);
		
		for(int i = 0; i < tjdlList.size(); i++) {
			Object[] tjdl = (Object[])tjdlList.get(i);
			
			if(tjdl[0]!=null) {
				//根据统计大类编号获取对应的产品编号
				List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = this.getFtpBSDivideList(xlsBrNo, tjdl[0].toString(), null, null);

				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setBusinessNo(tjdl[0].toString());// 业务条线编号
				ylfxBbReport.setBusinessName(tjdl[1].toString());// 业务条线名称
				double rjye = 0.0,ftplr = 0.0,bal=0.0;
				for(FtpBusinessStaticDivide ftpBusinessStaticDivide : ftpBusinessStaticDivideList) {
					Double[] values=QxppValue_map.get(ftpBusinessStaticDivide.getProductNo());
					rjye += (values==null||values[0]==null)?0.0:values[0];
					ftplr += (values==null||values[1]==null)?0.0:values[1];
					bal += (values==null||values[2]==null)?0.0:values[2];
				}

				ylfxBbReport.setBal(bal);//余额
				ylfxBbReport.setRjye(rjye);//日均余额
				ylfxBbReport.setFtplr(ftplr);//FTP利润
				ylfxBbReport.setLc(rjye==0?0.00:ftplr/rjye*360/days);//利差(年利率)
				
				ylfxBbReportList.add(ylfxBbReport);
			}
			
		}
	   return ylfxBbReportList;
	}
	
	/**
	 * 机构各条线下分产品FTP利润明细表---某一统计大类下所有产品盈利分析 数据获取
	 * @param request
	 * @param minDate
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @param businessNo
	 * @return
	 */
	public List<YlfxBbReport> prdtPayOffProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, Integer assessScope, String businessNo) {
	    
		List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
		
		TelMst telMst=(TelMst)request.getSession().getAttribute("userBean");
	    String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(brNo, manageLvl);// 县联社
		System.out.println("县联社：" + xlsBrNo);
		
		//从缓存中获取数据
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultPrdtList(xlsBrNo, minDate, date,assessScope);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//天数
		
		String brNos = this.getAllChildBrAllbrno(brNo, manageLvl, telMst.getTelNo());//获取所有的子级机构，包括自己
		//获取该操作员可以查看的客户类型
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		//生成以产品编号为KEY的结果集
		Map<String,Double[]> QxppValue_map = this.getQxppValueMap_ywtxylfx(ftpResultsList, xlsBrNo, brNos, custType);
		
		//获取产品编号
		String hsql = "select distinct product_no, product_name" +
				" from ftp.Ftp_Business_Static_Divide" +
				" where  business_no = '"+businessNo+"' order by product_no";
        List prdtList = daoFactory.query1(hsql, null);
        
        //按照产品进行循环
        for (int i = 0; i < prdtList.size(); i++) {
			Object[] obj = (Object[])prdtList.get(i);
			double rjye = 0.0,ftplr = 0.0,bal= 0.0;
			//投资业务下有一个虚拟的负债投资业务统计大类，对应的业务和产品都是null
			if(obj[0] == null) {
				continue;
			}
			Double[] values=QxppValue_map.get(obj[0]);
			rjye = (values==null||values[0]==null)?0.0:values[0];
			ftplr = (values==null||values[1]==null)?0.0:values[1];
			bal = (values==null||values[2]==null)?0.0:values[2];
			//只显示有值的
			if(rjye==0&&ftplr==0) {
			}else {
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setPrdtNo(obj[0].toString());
				ylfxBbReport.setPrdtName(obj[1].toString());
				ylfxBbReport.setBal(bal);//余额
				ylfxBbReport.setRjye(rjye);//日均余额
				ylfxBbReport.setFtplr(ftplr);//FTP利润
				ylfxBbReport.setLc(rjye==0?0.00:ftplr/rjye*360/days);//利差(年利率)
				
				ylfxBbReportList.add(ylfxBbReport);
			}
			
		}
      //对ylfxBbReportList按FTP利润大小进行降序排序
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
			 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
				 return arg1.getFtplr().compareTo(arg0.getFtplr());
				 }
			 }
		);
		return ylfxBbReportList;
	}
	
	/**
	 * 根据业务条线或者产品或者产品大类进行产品分机构FTP利润分析
	 * @param request
	 * @param minDate
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @param businessNo
	 * @param prdtCtgNo
	 * @param prdtNo
	 * @param isMx
	 * @return
	 */
    public List<YlfxBbReport> prdtBrProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, Integer assessScope, String businessNo, String prdtCtgNo,String prdtNo, Integer isMx) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
		
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(brNo, manageLvl);// 县联社
		TelMst telMst=(TelMst)request.getSession().getAttribute("userBean");
	    
		//从缓存中获取数据
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultPrdtList(xlsBrNo, minDate, date,assessScope);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//天数
		//获取该操作员可以查看的客户类型
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		
		//根据统计大类编号或者产品大类编号或者产品编号获取对应的产品编号
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = this.getFtpBSDivideList(xlsBrNo, businessNo, prdtCtgNo, prdtNo);
		
		//总行默认显示下级机构|支行默认显示支行，点击明细再显示下级
		if (isMx == 0 && !manageLvl.equals("3")&&!manageLvl.equals("2")) {// 如果不是查看子机构的，则直接查看该机构的
			YlfxBbReport ylfxBbReport = new YlfxBbReport();
			String brSql = LrmUtil.getBrSqlByTel(brNo, telMst.getTelNo());
			String prdtNos = "";
			for(FtpBusinessStaticDivide ftpBusinessStaticDivide : ftpBusinessStaticDivideList) {
				prdtNos += ftpBusinessStaticDivide.getProductNo() + ",";
			}
			ylfxBbReport.setBrName(brInfoDAO.getInfo(brNo).getBrName());
			ylfxBbReport.setBrNo(brNo);
			ylfxBbReport.setManageLvl(manageLvl);
			ylfxBbReport.setLeaf(this.getIsLeaf(brNo));
			Double[] resultValue = this.getQxppValue_cpfjglrb(ftpResultsList, brSql, custType, prdtNos);

			ylfxBbReport.setRjye(resultValue[0]);//日均余额
			ylfxBbReport.setFtplr(resultValue[1]);//FTP利润
			ylfxBbReport.setBal(resultValue[2]);//FTP利润
			ylfxBbReport.setLc(resultValue[0]==0?0.00:resultValue[1]/resultValue[0]*360/days);//利差(年利率)
			
			ylfxBbReportList.add(ylfxBbReport);
		}else {
			// 获取该机构的下级机构
			List<BrMst> brMstList = getChildBrListByTel(brNo, telMst.getTelNo());
			Map<String,Double[]> QxppValue_map = this.getQxppValueMap_cpfjglrb(ftpResultsList, xlsBrNo, brMstList, custType, telMst.getTelNo());
			
			//循环机构，获取对应的要统计的产品的值
			for(BrMst brMst : brMstList) {
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setBrNo(brMst.getBrNo());
				ylfxBbReport.setBrName(brMst.getBrName());
				ylfxBbReport.setManageLvl(brMst.getManageLvl());
				ylfxBbReport.setLeaf(this.getIsLeaf(brMst.getBrNo()));
				double rjye = 0.0,ftplr = 0.0,bal=0.0;
				for(FtpBusinessStaticDivide ftpBusinessStaticDivide : ftpBusinessStaticDivideList) {
					Double[] values=QxppValue_map.get(ftpBusinessStaticDivide.getProductNo()+"-"+brMst.getBrNo());
					rjye += (values==null||values[0]==null)?0.0:values[0];
					ftplr += (values==null||values[1]==null)?0.0:values[1];
					bal += (values==null||values[2]==null)?0.0:values[2];
				}
				
				ylfxBbReport.setRjye(rjye);//日均余额
				ylfxBbReport.setBal(bal);//余额
				ylfxBbReport.setFtplr(ftplr);//FTP利润
				ylfxBbReport.setLc(rjye==0?0.00:ftplr/rjye*360/days);//利差(年利率)
				
				ylfxBbReportList.add(ylfxBbReport);
			}
			
			//对ylfxBbReportList按FTP利润大小进行降序排序
			Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
				 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
					 return arg1.getFtplr().compareTo(arg0.getFtplr());
					 }
				 }
			);
		}
		
	   return ylfxBbReportList;
	}
    
    /**
	 * 根据业务条线或者产品大类或者产品进行产品分客户经理FTP利润分析
	 * @param request
	 * @param minDate
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @param businessNo
	 * @param prdtCtgNo
	 * @param prdtNo
	 * @return
	 */
    public List<YlfxBbReport> prdtEmpProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, Integer assessScope, String businessNo, String prdtCtgNo, String prdtNo) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
	    TelMst telMst=(TelMst)request.getSession().getAttribute("userBean");
	    
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(brNo, manageLvl);// 县联社
		//从缓存中获取数据
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultPrdtList(xlsBrNo, minDate, date,assessScope);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//天数
		
		//根据业务条线编号或者产品大类或者产品编号获取对应的产品编号
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = this.getFtpBSDivideList(xlsBrNo, businessNo, prdtCtgNo, prdtNo);
		String prdtNos = "";
		for(FtpBusinessStaticDivide ftpBusinessStaticDivide : ftpBusinessStaticDivideList) {
			prdtNos += ftpBusinessStaticDivide.getProductNo()+",";
		}
		
		//获取该操作员可以查看的客户类型
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
        
		Map<String,Double> QxppValue_map = this.getQxppValueMap_khjl(ftpResultsList, prdtNos, custType);
		
		//获取该机构下的所有客户经理
		String hsql1 = "from FtpEmpInfo where brMst.brNo "+this.getAllChildBr(brNo, manageLvl, telMst.getTelNo())+"";
		List<FtpEmpInfo> empList = daoFactory.query(hsql1, null);
		
		//循环客户经理，获取对应的要统计的产品的值
		for(FtpEmpInfo ftpEmpInfo : empList) {
			double rjye = QxppValue_map.get(ftpEmpInfo.getEmpNo()+"-ye")==null?0:QxppValue_map.get(ftpEmpInfo.getEmpNo()+"-ye");
			double ftplr = QxppValue_map.get(ftpEmpInfo.getEmpNo()+"-lr")==null?0:QxppValue_map.get(ftpEmpInfo.getEmpNo()+"-lr");
			double bal = QxppValue_map.get(ftpEmpInfo.getEmpNo()+"-bal")==null?0:QxppValue_map.get(ftpEmpInfo.getEmpNo()+"-bal");

			//只显示有值的
			if(rjye==0&&ftplr==0) {
			}else {
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setEmpNo(ftpEmpInfo.getEmpNo());
				ylfxBbReport.setEmpName(ftpEmpInfo.getEmpName());
				ylfxBbReport.setBrNo(ftpEmpInfo.getBrMst().getBrNo());
				ylfxBbReport.setBrName(ftpEmpInfo.getBrMst().getBrName());
				ylfxBbReport.setRjye(rjye);//日均余额
				ylfxBbReport.setBal(bal);//余额
				ylfxBbReport.setFtplr(ftplr);//FTP利润
				ylfxBbReport.setLc(rjye==0?0.00:ftplr/rjye*360/days);//利差(年利率)
				
				ylfxBbReportList.add(ylfxBbReport);
			}
		}
		
		//对ylfxBbReportList按FTP利润大小进行降序排序
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
			 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
				 return arg1.getFtplr().compareTo(arg0.getFtplr());
				 }
			 }
		);
	   return ylfxBbReportList;
	}
    
    /**
	 * 客户经理分产品FTP利润排名表
	 * @param request
	 * @param minDate
	 * @param date
	 * @param empNo
	 * @param assessScope
	 * @return
	 */
    public List<YlfxBbReport> empPrdtProfile(HttpServletRequest request, String minDate, String date, String empNo, Integer assessScope) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());// 县联社
		//从缓存中获取数据
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultPrdtList(xlsBrNo, minDate, date,assessScope);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//天数
		
		//获取该操作员可以查看的客户类型
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		
		Map<String,Double> QxppValue_map = this.getQxppValueMap_khjlfcp(ftpResultsList, empNo, custType);
		
		//获取产品编号
		String hsql = "select distinct product_no, product_name" +
				" from ftp.Ftp_Business_Static_Divide";
				//" where br_No = '"+xlsBrNo+"'";
        List prdtList = daoFactory.query1(hsql, null);
		
        //按照产品进行循环
        for (int i = 0; i < prdtList.size(); i++) {
			Object[] obj = (Object[])prdtList.get(i);
			double rjye = 0.0,ftplr = 0.0,bal=0;
			//投资业务下有一个虚拟的负债投资业务统计大类，对应的业务和产品都是null
			if(obj[0] == null) {
				continue;
			}
			rjye = QxppValue_map.get(obj[0]+"-ye")==null?0:QxppValue_map.get(obj[0]+"-ye");
			ftplr = QxppValue_map.get(obj[0]+"-lr")==null?0:QxppValue_map.get(obj[0]+"-lr");
			bal = QxppValue_map.get(obj[0]+"-bal")==null?0:QxppValue_map.get(obj[0]+"-bal");
		
			//只显示有值的
			if(rjye==0&&ftplr==0) {
			}else {
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setPrdtNo(obj[0].toString());
				ylfxBbReport.setPrdtName(obj[1].toString());
				ylfxBbReport.setBal(bal);//余额
				ylfxBbReport.setRjye(rjye);//日均余额
				ylfxBbReport.setFtplr(ftplr);//FTP利润
				ylfxBbReport.setLc(rjye==0?0.00:ftplr/rjye*360/days);//利差(年利率)
				
				ylfxBbReportList.add(ylfxBbReport);
			}
		}
		//对ylfxBbReportList按FTP利润大小进行降序排序
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
			 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
				 return arg1.getFtplr().compareTo(arg0.getFtplr());
				 }
			 }
		);
	   return ylfxBbReportList;
	}
    
    /**
	 * 客户经理分客户FTP利润表
	 * @param request
	 * @param minDate
	 * @param date
	 * @param empNo
	 * @param assessScope
	 * @param pageName
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
    public PageUtil empCusProfile(HttpServletRequest request, String minDate, String date, String empNo, Integer assessScope,
    		String pageName, int pageSize, int currentPage,boolean isAll) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
		
		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());// 县联社
		//从缓存中获取数据
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultListEmp(xlsBrNo, minDate, date,assessScope,empNo);
		if(ftpResultsList == null) return null;
		int days = CommonFunctions.daysSubtract(date, minDate);//天数
		//获取该操作员可以查看的客户类型
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		StringBuffer accIds = new StringBuffer();
		//从结果集中获取某个客户经理的数据，根据账号汇总到map中
		Map<String,String>  brNameMap= FtpUtil.getBrNameMap();
		//从结果集中获取真正客户经理的数据，根据客户号-账号汇总到map中
		for (String[] result : ftpResultsList) {
			boolean isStatic = this.getIsStatic(custType, result[6], result[16]);//记录是否纳入统计,分对公和对私

			if(!result[2].equals("")&&isStatic) {
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[8]);

				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setEmpNo(result[2]);
				ylfxBbReport.setBrName(brNameMap.get(result[0]));
				ylfxBbReport.setAcId(String.valueOf(result[5]));
				ylfxBbReport.setPrdtName(String.valueOf(result[9]));
				ylfxBbReport.setCustNo(String.valueOf(result[2]));
				ylfxBbReport.setCustName(String.valueOf(result[10]));
				ylfxBbReport.setOpnDate(String.valueOf(result[11]));
				ylfxBbReport.setMtrDate(String.valueOf(result[12]));
				ylfxBbReport.setRate(result[15] == null ? 0 : Double.valueOf(String.valueOf(result[15])));
				ylfxBbReport.setFtp(result[14] == null ? 0 : Double.valueOf(String.valueOf(result[14])));//如果为null则赋值为-999
				ylfxBbReport.setRjye(rjye);//日均余额
				ylfxBbReport.setFtplr(ftplr);//FTP利润
				ylfxBbReport.setBal(bal);//余额
				ylfxBbReport.setLc(rjye == 0 ? 0.00 : ftplr / rjye * 360 / days);//利差(年利率)
				ylfxBbReport.setBusinessName(result[13]);
				ylfxBbReportList.add(ylfxBbReport);
			}
		}

		//对ylfxBbReportList按FTP利润大小进行降序排序
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
			 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
				 return arg1.getFtplr().compareTo(arg0.getFtplr());
				 }
			 }
		);
		int rowsCount = ylfxBbReportList.size();
		QueryPageBO queryPageBo = new QueryPageBO();
		String pageLine=queryPageBo.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		if(isAll){
			return new PageUtil(ylfxBbReportList,pageLine);
		}else{
			if(currentPage*pageSize<rowsCount){
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,currentPage*pageSize),pageLine);
			}else {
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,rowsCount),pageLine);
			}
		}
		
	}
    
    /**
     * 客户经理FTP利润明细表
     * @param request
     * @param minDate
     * @param date
     * @param empNo
     * @param assessScope
     * @param businessNo
     * @param prdtCtgNo
     * @param prdtNo
     * @param pageName
     * @param pageSize
     * @param currentPage
     * @return
     */
    public PageUtil empDetailProfile(HttpServletRequest request, String minDate, String date, String empNo, Integer assessScope,String businessNo,String prdtCtgNo,String prdtNo,
    		String pageName, int pageSize, int currentPage, boolean isAll) {

	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();

		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());// 县联社
		//从缓存中获取数据
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultListEmp(xlsBrNo, minDate, date,assessScope,empNo);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//天数

		//获取该操作员可以查看的客户类型
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());

		//根据业务条线编号或者产品大类或者产品编号获取对应的产品编号
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = this.getFtpBSDivideList(xlsBrNo, businessNo, prdtCtgNo, prdtNo);
		String prdtNos = "";
		for(FtpBusinessStaticDivide ftpBusinessStaticDivide : ftpBusinessStaticDivideList) {
			prdtNos += ftpBusinessStaticDivide.getProductNo()+",";
		}

		Map<String,String>  brNameMap= FtpUtil.getBrNameMap();
		//从结果集中获取真正客户经理的数据，根据客户号-账号汇总到map中
		for (String[] result : ftpResultsList) {
			boolean isStatic = this.getIsStatic(custType, result[6], result[16]);//记录是否纳入统计,分对公和对私

			if(!result[2].equals("")&&isStatic && prdtNos.indexOf(result[1])!=-1) {
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[8]);

				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setEmpNo(result[2]);
				ylfxBbReport.setBrName(brNameMap.get(result[0]));
				ylfxBbReport.setAcId(String.valueOf(result[5]));
				ylfxBbReport.setPrdtName(String.valueOf(result[9]));
				ylfxBbReport.setCustNo(String.valueOf(result[2]));
				ylfxBbReport.setCustName(String.valueOf(result[10]));
				ylfxBbReport.setOpnDate(String.valueOf(result[11]));
				ylfxBbReport.setMtrDate(String.valueOf(result[12]));
				ylfxBbReport.setRate(result[15] == null ? 0 : Double.valueOf(String.valueOf(result[15])));
				ylfxBbReport.setFtp(result[14] == null ? 0 : Double.valueOf(String.valueOf(result[14])));//如果为null则赋值为-999
				ylfxBbReport.setRjye(rjye);//日均余额
				ylfxBbReport.setFtplr(ftplr);//FTP利润
				ylfxBbReport.setBal(bal);//余额
				ylfxBbReport.setLc(rjye == 0 ? 0.00 : ftplr / rjye * 360 / days);//利差(年利率)
				ylfxBbReport.setBusinessName(result[13]);
				ylfxBbReportList.add(ylfxBbReport);
			}
		}
		
		//对ylfxBbReportList按FTP利润大小进行降序排序
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
			 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
				 return arg1.getFtplr().compareTo(arg0.getFtplr());
				 }
			 }
		);
		int rowsCount=ylfxBbReportList.size();
		QueryPageBO queryPageBo = new QueryPageBO();
		String pageLine=queryPageBo.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		if(isAll){
			return new PageUtil(ylfxBbReportList,pageLine);
		}else{
			if(currentPage*pageSize<rowsCount){
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,currentPage*pageSize),pageLine);
			}else {
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,rowsCount),pageLine);
			}
		}
		
	}
    
    /**
     * 银行客户经理统计报表
     * @param request
     * @param minDate
     * @param date
     * @param brNo
     * @param manageLvl
     * @param assessScope
     * @param businessNo
     * @param prdtCtgNo
     * @param prdtNo
     * @param pageName
     * @param pageSize
     * @param currentPage
     * @param isAll
     * @return
     */
    public PageUtil allEmpDetailProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, Integer assessScope,String businessNo,String prdtCtgNo,String prdtNo,
    		String pageName, int pageSize, int currentPage, boolean isAll) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
		
		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");

		String brNos = this.getAllChildBrAllbrno(brNo, manageLvl,telMst.getTelNo());//获取所有的子级机构，包括自己

		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());// 县联社
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultListEmpAll(xlsBrNo, minDate, date,assessScope,brNos);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//天数
		
		//获取该操作员可以查看的客户类型
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		
		//根据业务条线编号或者产品大类或者产品编号获取对应的产品编号
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = this.getFtpBSDivideList(xlsBrNo, businessNo, prdtCtgNo, prdtNo);
		String prdtNos = "";
		for(FtpBusinessStaticDivide ftpBusinessStaticDivide : ftpBusinessStaticDivideList) {
			prdtNos += ftpBusinessStaticDivide.getProductNo()+",";
		}

		Map<String,String>  brNameMap= FtpUtil.getBrNameMap();
		//获取该机构下的所有客户经理
		String hsql1 = "from FtpEmpInfo where brMst.brNo "+this.getAllChildBrAllbrno(brNo, manageLvl, telMst.getTelNo())+"";
		List<FtpEmpInfo> empList = daoFactory.query(hsql1, null);
		Map<String, String> ftpEmpMap = new HashMap<String, String>();
		for(FtpEmpInfo ftpEmpInfo : empList) {
			ftpEmpMap.put(ftpEmpInfo.getEmpNo(), ftpEmpInfo.getPostNo()+"-"+ftpEmpInfo.getEmpName());
		}
		//从结果集中获取真正客户经理的数据，根据客户号-账号汇总到map中
		for (String[] result : ftpResultsList) {
			boolean isStatic = this.getIsStatic(custType, result[6], result[16]);//记录是否纳入统计,分对公和对私
			
			String empInfo = ftpEmpMap.get(result[2]) == null ? "":ftpEmpMap.get(result[2]);
			String[] empInfos = empInfo.split("-");
			//客户经理的EMP_NO不为空且POST_NO=4(真正的银行客户经理) 且 是要统计的产品
			if(!result[2].equals("")&&empInfos[0].equals("4")&&isStatic && prdtNos.indexOf(result[1])!=-1) {
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[8]);

				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setEmpNo(result[2]);
				ylfxBbReport.setEmpName(empInfos[1]);
				ylfxBbReport.setBrNo(result[0]);
				ylfxBbReport.setBrName(brNameMap.get(result[0]));
				ylfxBbReport.setAcId(String.valueOf(result[5]));
				ylfxBbReport.setPrdtName(String.valueOf(result[9]));
				ylfxBbReport.setCustNo(String.valueOf(result[2]));
				ylfxBbReport.setCustName(String.valueOf(result[10]));
				ylfxBbReport.setOpnDate(String.valueOf(result[11]));
				ylfxBbReport.setMtrDate(String.valueOf(result[12]));
				ylfxBbReport.setRate(result[15] == null ? 0 : Double.valueOf(String.valueOf(result[15])));
				ylfxBbReport.setFtp(result[14] == null ? 0 : Double.valueOf(String.valueOf(result[14])));//如果为null则赋值为-999
				ylfxBbReport.setRjye(rjye);//日均余额
				ylfxBbReport.setFtplr(ftplr);//FTP利润
				ylfxBbReport.setBal(bal);//余额
				ylfxBbReport.setLc(rjye == 0 ? 0.00 : ftplr / rjye * 360 / days);//利差(年利率)
				ylfxBbReport.setBusinessName(result[13]);
				ylfxBbReportList.add(ylfxBbReport);
			}
		}
		//对ftpEmpLrList按客户经理编号排序，再按FTP利润排序
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
			public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
				if (arg0.getEmpNo().compareTo(arg1.getEmpNo()) == 0) {
					return Double.valueOf(arg0.getFtplr()).compareTo(
							Double.valueOf(arg1.getFtplr()));
				} else {
					return arg0.getEmpNo().compareTo(arg1.getEmpNo());
				}
			}
		});

		int rowsCount = ylfxBbReportList.size();
		QueryPageBO queryPageBo = new QueryPageBO();
		String pageLine=queryPageBo.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		if(isAll){
			return new PageUtil(ylfxBbReportList,pageLine);
		}else{
			if(currentPage*pageSize<rowsCount){
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,currentPage*pageSize),pageLine);
			}else {
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,rowsCount),pageLine);
			}
		}
	}
    /**
	 * 银行客户经理统计报表-导出
	 * @param reportList
	 * @param empName
	 * @param empNo
	 * @param minDate
	 * @param maxDate
	 * @param title
	 * @return
	 */
	public HSSFWorkbook getYhkhjltjbbWorkbook(List<YlfxBbReport> reportList, String brName , String minDate, String maxDate, String title) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFSheet sheet = workbook.createSheet(title);//生成一个sheet
		try {
	        // 表头合并
			sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 14 ));// 合并第一行第一到15列
			sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 14 ));// 合并第二行第一到15列
			//设置第一列机构单元格的宽度
	        sheet.setColumnWidth(0, 20*2*256);//长度乘以2是为了解决纯数字列宽度不足会显示科学计数法问题，乘以256得到的数据才是excel真实列宽。
			
			ExcelExport excelExport = new ExcelExport(workbook, sheet);
			excelExport.createRow(0); // 创建第一行
			excelExport.setCell(0, title, excelExport.getHeaderStyle());
			
			excelExport.createRow(1); // 创建第二行
			excelExport.setCell(0, "机构："+brName+"        报表时段："+CommonFunctions.dateModifyD(minDate,1)+"-"+maxDate+"         单位：元，%(年利率)", excelExport.getTitleStyle());
			
			excelExport.createRow(2);//第三行，表头列
			sheet.setColumnWidth(0, 7*256);//设置第一列的宽度   
			sheet.setColumnWidth(1, 25*256);//设置第2列的宽度   
			sheet.setColumnWidth(3, 15*256);//设置第4列的宽度   
			sheet.setColumnWidth(4, 30*256);//设置第5列的宽度  
			sheet.setColumnWidth(5, 25*256);//设置第6列的宽度   
			sheet.setColumnWidth(6, 12*256);//设置第7列的宽度  
			sheet.setColumnWidth(8, 20*256);//设置第九列的宽度   
			sheet.setColumnWidth(9, 20*256);//设置第10列的宽度  
			sheet.setColumnWidth(10, 20*256);//设置第11列的宽度  
			excelExport.setCell(0, "序号", excelExport.headCenterNormalStyle);
			excelExport.setCell(1, "机构名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(2, "客户经理名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(3, "账号", excelExport.headCenterNormalStyle);
			excelExport.setCell(4, "产品名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(5, "客户名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(6, "开户日期", excelExport.headCenterNormalStyle);
			excelExport.setCell(7, "到期日期", excelExport.headCenterNormalStyle);
			excelExport.setCell(8, "余额", excelExport.headCenterNormalStyle);
			excelExport.setCell(9, "日均余额", excelExport.headCenterNormalStyle);
			excelExport.setCell(10, "日均余额积数", excelExport.headCenterNormalStyle);
			excelExport.setCell(11, "利率（%）", excelExport.headCenterNormalStyle);
			excelExport.setCell(12, "FTP价格（%）", excelExport.headCenterNormalStyle);
			excelExport.setCell(13, "利差（%）", excelExport.headCenterNormalStyle);
			excelExport.setCell(14, "FTP利润", excelExport.headCenterNormalStyle);
			double zftplr = 0,rjye=0, ftplr=0,ratefz=0,ftpfz=0,bal=0;
			int days = CommonFunctions.daysSubtract(maxDate, minDate);//天数
			if(reportList != null) {
				for(YlfxBbReport ylfxBbReport : reportList) {
					zftplr += ylfxBbReport.getFtplr();
				}
				for (int j = 0; j < reportList.size(); j++) {
					YlfxBbReport entity = reportList.get(j);
					rjye += entity.getRjye();
		        	ftplr += entity.getFtplr();
		        	ratefz += entity.getRjye()*entity.getRate();
		        	ftpfz += entity.getRjye()*(entity.getFtp()==-999?0:entity.getFtp());
		        	bal+=entity.getBal();
					//循环创建数据行
					excelExport.createRow(j+3);
					excelExport.setCell(0, j+1, excelExport.centerNormalStyle);
					excelExport.setCell(1, entity.getBrName()+"["+entity.getBrNo()+"]", excelExport.centerNormalStyle);
					excelExport.setCell(2, entity.getEmpName()+"["+entity.getEmpNo()+"]", excelExport.centerNormalStyle);
					excelExport.setCell(3, entity.getAcId(), excelExport.centerNormalStyle);
					excelExport.setCell(4, entity.getPrdtName(), excelExport.centerNormalStyle);
					excelExport.setCell(5, entity.getCustName(), excelExport.centerNormalStyle);
					excelExport.setCell(6,(entity.getOpnDate().equals("")||entity.getOpnDate()==null||entity.getOpnDate().equals("null"))?"-":entity.getOpnDate(), excelExport.centerNormalStyle);
					excelExport.setCell(7, (entity.getMtrDate().equals("")||entity.getMtrDate()==null||entity.getMtrDate().equals("null"))?"-":entity.getMtrDate(), excelExport.centerNormalStyle);
					excelExport.setCell(8, entity.getBal(), excelExport.rightNormalMoneyStyle);
					excelExport.setCell(9, entity.getRjye(), excelExport.rightNormalMoneyStyle);
					excelExport.setCell(10, entity.getRjye()*days, excelExport.rightNormalMoneyStyle);
					excelExport.setCell(11, CommonFunctions.doublecut(entity.getRate()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(12, entity.getFtp()==-999?0:CommonFunctions.doublecut(entity.getFtp()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(13, CommonFunctions.doublecut(entity.getLc()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(14, entity.getFtplr(), excelExport.rightNormalMoneyStyle);
					
					
				}
				//合计行
				//int days = CommonFunctions.daysSubtract(maxDate, minDate);//天数
				excelExport.createRow(reportList.size()+3);
				excelExport.setCell(0, "合计", excelExport.centerBoldStyle);
				excelExport.setCell(1, "-", excelExport.centerBoldStyle);
				excelExport.setCell(2, "-", excelExport.centerBoldStyle);
				excelExport.setCell(3, "-", excelExport.centerBoldStyle);
				excelExport.setCell(4, "-", excelExport.centerBoldStyle);
				excelExport.setCell(5, "-", excelExport.centerBoldStyle);
				excelExport.setCell(6, "-", excelExport.centerBoldStyle);
				excelExport.setCell(7, "-", excelExport.centerBoldStyle);
				excelExport.setCell(8, bal, excelExport.rightBoldMoneyStyle);
				excelExport.setCell(9, rjye, excelExport.rightBoldMoneyStyle);
				excelExport.setCell(10, CommonFunctions.doublecut(rjye*days,3), excelExport.rightBoldMoneyStyle);
				excelExport.setCell(11, CommonFunctions.doublecut(ratefz/rjye*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(12, CommonFunctions.doublecut(ftpfz/rjye*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(13, CommonFunctions.doublecut(ftplr/rjye*360/days*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(14, ftplr, excelExport.rightBoldMoneyStyle);
				
			}
			
			
			sheet.getRow(0).setHeight((short)500);//设置第一行表格高度
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return workbook;
	}
    /**
	 * 客户经理利润排名表
	 * @param request
	 * @param minDate
	 * @param date
	 * @param brNo
	 * @param assessScope
	 * @return
	 */
    public List<YlfxBbReport> brEmpOrderProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, Integer assessScope) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
	    TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(brNo, manageLvl);// 县联社
		//从缓存中获取数据
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultPrdtList(xlsBrNo, minDate, date,assessScope);
		if(ftpResultsList == null) return null;
		
		int days = CommonFunctions.daysSubtract(date, minDate);//天数
		String brNos = this.getAllChildBrAllbrno(brNo, manageLvl, telMst.getTelNo());
		//获取该操作员可以查看的客户类型
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		
		Map<String, Double> rjyejlMap = new HashMap<String, Double>();//key:客户经理编号
		Map<String, Double> ftplrjlMap = new HashMap<String, Double>();//key:客户经理编号
		Map<String, Double> balMap = new HashMap<String, Double>();//key:客户经理编号

		Map<String,Double> ftplr_dg_map=new HashMap<String,Double>();//对公
		Map<String,Double> ftplr_ds_map=new HashMap<String,Double>();//对私

		Map<String,List<String>> prdtCtgMap = getPrdtCtgMap();

		Map<String,Double> grhqrjye_map=new HashMap<String,Double>();//个人活期日均余额map
		Map<String,Double> grdqrjye_map=new HashMap<String,Double>();//个人定期日均余额map
		Map<String,Double> dwhqrjye_map=new HashMap<String,Double>();//单位活期日均余额map
		Map<String,Double> dwdqrjye_map=new HashMap<String,Double>();//单位定期日均余额map
		Map<String,Double> yhkrjye_map=new HashMap<String,Double>();//银行卡日均余额map
		Map<String,Double> czxrjye_map=new HashMap<String,Double>();//财政性存款日均余额map
		Map<String,Double> yjhkrjye_map=new HashMap<String,Double>();//应解汇款日均余额map
		Map<String,Double> bzjrjye_map=new HashMap<String,Double>();//保证金日均余额map

		Map<String,Double> grhqbal_map=new HashMap<String,Double>();//个人活期余额map
		Map<String,Double> grdqbal_map=new HashMap<String,Double>();//个人定期余额map
		Map<String,Double> dwhqbal_map=new HashMap<String,Double>();//单位活期余额map
		Map<String,Double> dwdqbal_map=new HashMap<String,Double>();//单位定期余额map
		Map<String,Double> yhkbal_map=new HashMap<String,Double>();//银行卡余额map
		Map<String,Double> czxbal_map=new HashMap<String,Double>();//财政性存款余额map
		Map<String,Double> yjhkbal_map=new HashMap<String,Double>();//应解汇款余额map
		Map<String,Double> bzjbal_map=new HashMap<String,Double>();//保证金余额map

		Map<String,Double> grdkftplr_map=new HashMap<String,Double>();//个人贷款ftp利润map
		Map<String,Double> gsdkftplr_map=new HashMap<String,Double>();//公司贷款ftp利润map
		Map<String,Double> grhqftplr_map=new HashMap<String,Double>();//个人活期ftp利润map
		Map<String,Double> grdqftplr_map=new HashMap<String,Double>();//个人定期ftp利润map
		Map<String,Double> dwhqftplr_map=new HashMap<String,Double>();//单位活期ftp利润map
		Map<String,Double> dwdqftplr_map=new HashMap<String,Double>();//单位定期ftp利润map
		Map<String,Double> yhkftplr_map=new HashMap<String,Double>();//银行卡ftp利润map
		Map<String,Double> czxftplr_map=new HashMap<String,Double>();//财政性存款ftp利润map
		Map<String,Double> yjhkftplr_map=new HashMap<String,Double>();//应解汇款ftp利润map
		Map<String,Double> bzjftplr_map=new HashMap<String,Double>();//保证金ftp利润map


		//从结果集中获取某些客户经理的数据，根据客户经理编号汇总到map中
		for (String[] result : ftpResultsList) {
			boolean isStatic = this.getIsStatic(custType, result[5], result[7]);//记录是否纳入统计,分对公和对私
			if (brNos.indexOf(result[0]) != -1 && isStatic) {
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[6]);
				//日均余额
				if (rjyejlMap.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
					rjyejlMap.put(result[2] + "-" + result[1].substring(1, 2), rjye);
				} else {
					rjyejlMap.put(result[2] + "-" + result[1].substring(1, 2), rjye + rjyejlMap.get(result[2] + "-" + result[1].substring(1, 2)));
				}
				//FTP利润
				if (ftplrjlMap.get(result[2]) == null) {
					ftplrjlMap.put(result[2], ftplr);
				} else {
					ftplrjlMap.put(result[2], ftplr + ftplrjlMap.get(result[2]));
				}
				//FTP余额
				if (balMap.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
					balMap.put(result[2] + "-" + result[1].substring(1, 2), bal);
				} else {
					balMap.put(result[2] + "-" + result[1].substring(1, 2), bal + balMap.get(result[2] + "-" + result[1].substring(1, 2)));
				}

				if (prdtCtgMap.get("grhq").contains(result[1])) {
					if (grhqrjye_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						grhqrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye);
					} else {
						grhqrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye + grhqrjye_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (grhqbal_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						grhqbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal);
					} else {
						grhqbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal + grhqbal_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (grhqftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						grhqftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						grhqftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + grhqftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("grdq").contains(result[1])) {
					if (grdqrjye_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						grdqrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye);
					} else {
						grdqrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye + grdqrjye_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (grdqbal_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						grdqbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal);
					} else {
						grdqbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal + grdqbal_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (grdqftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						grdqftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						grdqftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + grdqftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("dwhq").contains(result[1])) {
					if (dwhqrjye_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						dwhqrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye);
					} else {
						dwhqrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye + dwhqrjye_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (dwhqbal_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						dwhqbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal);
					} else {
						dwhqbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal + dwhqbal_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (dwhqftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						dwhqftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						dwhqftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + dwhqftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("dwdq").contains(result[1])) {
					if (dwdqrjye_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						dwdqrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye);
					} else {
						dwdqrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye + dwdqrjye_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (dwdqbal_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						dwdqbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal);
					} else {
						dwdqbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal + dwdqbal_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (dwdqftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						dwdqftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						dwdqftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + dwdqftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}


				if (prdtCtgMap.get("yhk").contains(result[1])) {
					if (yhkrjye_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						yhkrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye);
					} else {
						yhkrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye + yhkrjye_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (yhkbal_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						yhkbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal);
					} else {
						yhkbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal + yhkbal_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (yhkftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						yhkftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						yhkftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + yhkftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

				}

				if (prdtCtgMap.get("czx").contains(result[1])) {
					if (czxrjye_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						czxrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye);
					} else {
						czxrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye + czxrjye_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (czxbal_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						czxbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal);
					} else {
						czxbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal + czxbal_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (czxftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						czxftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						czxftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + czxftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("yjhk").contains(result[1])) {
					if (yjhkrjye_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						yjhkrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye);
					} else {
						yjhkrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye + yjhkrjye_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (yjhkbal_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						yjhkbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal);
					} else {
						yjhkbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal + yjhkbal_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (yjhkftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						yjhkftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						yjhkftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + yjhkftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("bzj").contains(result[1])) {
					if (bzjrjye_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						bzjrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye);
					} else {
						bzjrjye_map.put(result[2] + "-" + result[1].substring(1, 2), rjye + bzjrjye_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (bzjbal_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						bzjbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal);
					} else {
						bzjbal_map.put(result[2] + "-" + result[1].substring(1, 2), bal + bzjbal_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}

					if (bzjftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						bzjftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						bzjftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + bzjftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}

				boolean isds = this.getIsStatic("1", result[5], result[7]);//对私统计
				if (isds) {
					if (ftplr_ds_map.get(result[2]) == null) {
						ftplr_ds_map.put(result[2], ftplr);
					} else {
						ftplr_ds_map.put(result[2], ftplr + ftplr_ds_map.get(result[2]));
					}
				}
				boolean isdg = this.getIsStatic("2", result[5], result[7]);//对公统计
				if (isdg) {
					//FTP利润
					if (ftplr_dg_map.get(result[2]) == null) {
						ftplr_dg_map.put(result[2], ftplr);
					} else {
						ftplr_dg_map.put(result[2], ftplr + ftplr_dg_map.get(result[2]));
					}
				}


				boolean isdsdk = this.getIsStaticDk("1", result[5], result[7]);//对私统计
				if (isdsdk) {
					if (grdkftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						grdkftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						grdkftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + grdkftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}
				boolean isdgdk = this.getIsStaticDk("2", result[5], result[7]);//对公统计
				if (isdgdk) {
					//FTP利润
					if (gsdkftplr_map.get(result[2] + "-" + result[1].substring(1, 2)) == null) {
						gsdkftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						gsdkftplr_map.put(result[2] + "-" + result[1].substring(1, 2), ftplr + gsdkftplr_map.get(result[2] + "-" + result[1].substring(1, 2)));
					}
				}
			}

		}
		//获取该机构下的所有客户经理
		String hsql1 = "from FtpEmpInfo where brMst.brNo  "+brNos+"";
		List<FtpEmpInfo> empList = daoFactory.query(hsql1, null);
        //循环客户经理，获取要统计的值
		for(FtpEmpInfo ftpEmpInfo : empList) {
			double zcrjye = rjyejlMap.get(ftpEmpInfo.getEmpNo()+"-1")==null?0:rjyejlMap.get(ftpEmpInfo.getEmpNo()+"-1");
			double fzrjye = rjyejlMap.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:rjyejlMap.get(ftpEmpInfo.getEmpNo()+"-2");

			double zcye = balMap.get(ftpEmpInfo.getEmpNo()+"-1")==null?0:balMap.get(ftpEmpInfo.getEmpNo()+"-1");
			double fzye = balMap.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:balMap.get(ftpEmpInfo.getEmpNo()+"-2");

			double grhqbal = grhqbal_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:grhqbal_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double grdqbal = grdqbal_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:grdqbal_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double dwhqbal = dwhqbal_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:dwhqbal_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double dwdqbal = dwdqbal_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:dwdqbal_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double czxbal = czxbal_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:czxbal_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double yhkbal = yhkbal_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:yhkbal_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double yjhkbal = yjhkbal_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:yjhkbal_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double bzjbal = bzjbal_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:bzjbal_map.get(ftpEmpInfo.getEmpNo()+"-2");

			double grhqrjye = grhqrjye_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:grhqrjye_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double grdqrjye = grdqrjye_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:grdqrjye_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double dwhqrjye = dwhqrjye_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:dwhqrjye_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double dwdqrjye = dwdqrjye_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:dwdqrjye_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double czxrjye = czxrjye_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:czxrjye_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double yhkrjye = yhkrjye_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:yhkrjye_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double yjhkrjye = yjhkrjye_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:yjhkrjye_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double bzjrjye = bzjrjye_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:bzjrjye_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double ftplr = ftplrjlMap.get(ftpEmpInfo.getEmpNo())==null?0:ftplrjlMap.get(ftpEmpInfo.getEmpNo());
			double dsftplr = ftplr_ds_map.get(ftpEmpInfo.getEmpNo())==null?0:ftplr_ds_map.get(ftpEmpInfo.getEmpNo());
			double dgftplr = ftplr_dg_map.get(ftpEmpInfo.getEmpNo())==null?0:ftplr_dg_map.get(ftpEmpInfo.getEmpNo());
			double grhqftplr = grhqftplr_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:grhqftplr_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double grdqftplr = grdqftplr_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:grdqftplr_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double dwhqftplr = dwhqftplr_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:dwhqftplr_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double dwdqftplr = dwdqftplr_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:dwdqftplr_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double czxftplr = czxftplr_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:czxftplr_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double yhkftplr = yhkftplr_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:yhkftplr_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double yjhkftplr = yjhkftplr_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:yjhkftplr_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double bzjftplr = bzjftplr_map.get(ftpEmpInfo.getEmpNo()+"-2")==null?0:bzjftplr_map.get(ftpEmpInfo.getEmpNo()+"-2");
			double grdkftplr = grdkftplr_map.get(ftpEmpInfo.getEmpNo()+"-1")==null?0:grdkftplr_map.get(ftpEmpInfo.getEmpNo()+"-1");
			double gsdkftplr = gsdkftplr_map.get(ftpEmpInfo.getEmpNo()+"-1")==null?0:gsdkftplr_map.get(ftpEmpInfo.getEmpNo()+"-1");



			//只显示有值的
			if((zcrjye!=0||fzrjye!=0)) {
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setEmpNo(ftpEmpInfo.getEmpNo());
				ylfxBbReport.setEmpName(ftpEmpInfo.getEmpName());
				ylfxBbReport.setBrNo(ftpEmpInfo.getBrMst().getBrNo());
				ylfxBbReport.setBrName(ftpEmpInfo.getBrMst().getBrName());
				ylfxBbReport.setZcrjye(zcrjye);//资产日均余额
				ylfxBbReport.setFzrjye(fzrjye);//负债日均余额
				ylfxBbReport.setGrhqrjye(grhqrjye);
				ylfxBbReport.setGrdqrjye(grdqrjye);
				ylfxBbReport.setDwhqrjye(dwhqrjye);
				ylfxBbReport.setDwdqrjye(dwdqrjye);
				ylfxBbReport.setCzxrjye(czxrjye);
				ylfxBbReport.setYhkrjye(yhkrjye);
				ylfxBbReport.setYjhkrjye(yjhkrjye);
				ylfxBbReport.setBzjrjye(bzjrjye);

				ylfxBbReport.setZcbal(zcye);//资产余额
				ylfxBbReport.setFzbal(fzye);//负债余额
				ylfxBbReport.setGrhqbal(grhqbal);
				ylfxBbReport.setGrdqbal(grdqbal);
				ylfxBbReport.setDwhqbal(dwhqbal);
				ylfxBbReport.setDwdqbal(dwdqbal);
				ylfxBbReport.setCzxbal(czxbal);
				ylfxBbReport.setYhkbal(yhkbal);
				ylfxBbReport.setYjhkbal(yjhkbal);
				ylfxBbReport.setBzjbal(bzjbal);


				ylfxBbReport.setGrhqftplr(grhqftplr);
				ylfxBbReport.setGrdqftplr(grdqftplr);
				ylfxBbReport.setDwhqftplr(dwhqftplr);
				ylfxBbReport.setDwdqftplr(dwdqftplr);
				ylfxBbReport.setCzxftplr(czxftplr);
				ylfxBbReport.setYhkftplr(yhkftplr);
				ylfxBbReport.setYjhkftplr(yjhkftplr);
				ylfxBbReport.setBzjftplr(bzjftplr);

				ylfxBbReport.setGrdkftplr(grdkftplr);
				ylfxBbReport.setGsdkftplr(gsdkftplr);

				ylfxBbReport.setDsftplr(dsftplr);
				ylfxBbReport.setDgftplr(dgftplr);

				ylfxBbReport.setFtplr(ftplr);//FTP利润
				ylfxBbReport.setLc((zcrjye+fzrjye)==0?0.00:ftplr/(zcrjye+fzrjye)*360/days);//利差(年利率)
				
				ylfxBbReportList.add(ylfxBbReport);
			}
		}
		//对ylfxBbReportList按FTP利润大小进行降序排序
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
			 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
				 return arg1.getFtplr().compareTo(arg0.getFtplr());
				 }
			 }
		);
	   return ylfxBbReportList;
	}
    /**
	 * 个人按揭机构FTP利润分析
	 * @param request
	 * @param minDate
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @param businessNo
	 * @param prdtCtgNo
	 * @param prdtNo
	 * @param isMx
	 * @return
	 */
    public List<YlfxBbReport> grajBrProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, Integer assessScope, Integer isMx) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();

		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(brNo, manageLvl);// 县联社
		TelMst telMst=(TelMst)request.getSession().getAttribute("userBean");
		String brNos = this.getAllChildBrAllbrno(brNo, manageLvl,telMst.getTelNo());//获取所有的子级机构，包括自己
	    System.out.println(manageLvl+"!!!"+brNo);
		System.out.println(","+xlsBrNo+","+minDate+","+date);
		//从缓存中获取数据
		List<String[]> ftpResultsList =getQxppResultListMortgage(xlsBrNo, minDate, date,assessScope,brNos,null);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//天数
		//获取该操作员可以查看的客户类型
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		
		//总行默认显示下级机构|支行默认显示支行，点击明细再显示下级
		if (isMx == 0 && !manageLvl.equals("3")&&!manageLvl.equals("2")) {// 如果不是查看子机构的盈利分析，则直接查看该机构的盈利分析
			YlfxBbReport ylfxBbReport = new YlfxBbReport();
			String brSql = LrmUtil.getBrSqlByTel(brNo, telMst.getTelNo());
			ylfxBbReport.setBrName(brInfoDAO.getInfo(brNo).getBrName());
			ylfxBbReport.setBrNo(brNo);
			ylfxBbReport.setManageLvl(manageLvl);
			ylfxBbReport.setLeaf(this.getIsLeaf(brNo));
			double[] resultValue = this.getQxppValueMap_grajjglrbhz(ftpResultsList, brSql, custType);
			ylfxBbReport.setRjye(resultValue[0]);//日均余额
			ylfxBbReport.setFtplr(resultValue[1]);//FTP利润
			ylfxBbReport.setBal(resultValue[2]);//FTP余额
			ylfxBbReport.setLc(resultValue[0]==0?0.00:resultValue[1]/resultValue[0]*360/days);//利差(年利率)
			ylfxBbReportList.add(ylfxBbReport);
		}else {
			// 获取该机构的下级机构
			List<BrMst> brMstList = getChildBrListByTel(brNo, telMst.getTelNo());
			Map<String,Double[]> QxppValue_map = this.getQxppValueMap_grajjglrb(ftpResultsList, xlsBrNo, brMstList, custType, telMst.getTelNo());
			//循环机构，获取对应的要统计的产品的值
			for(BrMst brMst : brMstList) {
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setBrNo(brMst.getBrNo());
				ylfxBbReport.setBrName(brMst.getBrName());
				ylfxBbReport.setManageLvl(brMst.getManageLvl());
				ylfxBbReport.setLeaf(this.getIsLeaf(brMst.getBrNo()));
				double rjye = 0.0,ftplr = 0.0,bal=0;
				Double[] values=QxppValue_map.get(brMst.getBrNo());
				rjye += (values==null||values[0]==null)?0.0:values[0];
				ftplr += (values==null||values[1]==null)?0.0:values[1];
				bal += (values==null||values[2]==null)?0.0:values[2];

				ylfxBbReport.setBal(bal);//余额
				ylfxBbReport.setRjye(rjye);//日均余额
				ylfxBbReport.setFtplr(ftplr);//FTP利润
				ylfxBbReport.setLc(rjye==0?0.00:ftplr/rjye*360/days);//利差(年利率)
				
				ylfxBbReportList.add(ylfxBbReport);
			}
			//对ylfxBbReportList按FTP利润大小进行降序排序
			Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
				 public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
					 return arg1.getFtplr().compareTo(arg0.getFtplr());
					 }
				 }
			);
		}
		
	   return ylfxBbReportList;
	}
    
    /**
     * 个人按揭客户经理FTP利润明细表
     * @param request
     * @param minDate
     * @param date
	 * @param brNo
	 * @param manageLvl
     * @param empNo
     * @param assessScope
     * @param pageName
     * @param pageSize
     * @param currentPage
     * @param isAll
     * @return
     */
    public PageUtil grajEmpDetailProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, String empNo, Integer assessScope,
    		String pageName, int pageSize, int currentPage, boolean isAll) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();
		
		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());// 县联社
		String brNos = this.getAllChildBr(brNo, manageLvl,telMst.getTelNo());//获取所有的子级机构，包括自己
		//从缓存中获取数据
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList =getQxppResultListMortgage(xlsBrNo, minDate, date,assessScope,brNos,empNo);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//天数

		//获取该操作员可以查看的客户类型
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());

		Map<String,String>  brNameMap= FtpUtil.getBrNameMap();
		Map<String,String>  empNameMap= FtpUtil.getempNameMap();
		//从结果集中获取真正客户经理的数据，根据客户号-账号汇总到map中
		for (String[] result : ftpResultsList) {
			boolean isStatic = this.getIsStatic(custType, result[6], result[16]);//记录是否纳入统计,分对公和对私
			if(!result[2].equals("")&&isStatic) {
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[8]);

				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setEmpNo(result[2]);
				ylfxBbReport.setEmpName(empNameMap.get(result[2]));
				ylfxBbReport.setBrNo(result[0]);
				ylfxBbReport.setBrName(brNameMap.get(result[0]));
				ylfxBbReport.setAcId(String.valueOf(result[5]));
				ylfxBbReport.setPrdtName(String.valueOf(result[9]));
				ylfxBbReport.setCustNo(String.valueOf(result[2]));
				ylfxBbReport.setCustName(String.valueOf(result[10]));
				ylfxBbReport.setOpnDate(String.valueOf(result[11]));
				ylfxBbReport.setMtrDate(String.valueOf(result[12]));
				ylfxBbReport.setRate(result[15] == null ? 0 : Double.valueOf(String.valueOf(result[15])));
				ylfxBbReport.setFtp(result[14] == null ? 0 : Double.valueOf(String.valueOf(result[14])));//如果为null则赋值为-999
				ylfxBbReport.setRjye(rjye);//日均余额
				ylfxBbReport.setFtplr(ftplr);//FTP利润
				ylfxBbReport.setBal(bal);//余额
				ylfxBbReport.setLc(rjye == 0 ? 0.00 : ftplr / rjye * 360 / days);//利差(年利率)
				ylfxBbReport.setBusinessName(result[13]);
				ylfxBbReportList.add(ylfxBbReport);
			}
		}

		//对ylfxBbReportList按FTP利润大小进行降序排序
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
					public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
						return arg1.getFtplr().compareTo(arg0.getFtplr());
					}
				}
		);
		int rowsCount=ylfxBbReportList.size();
		QueryPageBO queryPageBo = new QueryPageBO();
		String pageLine=queryPageBo.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		if(isAll){
			return new PageUtil(ylfxBbReportList,pageLine);
		}else{
			if(currentPage*pageSize<rowsCount){
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,currentPage*pageSize),pageLine);
			}else {
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,rowsCount),pageLine);
			}
		}
	}
    /**
	 * 个人按揭客户经理FTP利润明细表-导出
	 * @param reportList
	 * @param brName
	 * @param empName
	 * @param empNo
	 * @param minDate
	 * @param maxDate
	 * @param title
	 * @return
	 */
	public HSSFWorkbook getGrajkhjllrbWorkbook(List<YlfxBbReport> reportList, String brName, String empName, String empNo, String minDate, String maxDate, String title) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFSheet sheet = workbook.createSheet(title);//生成一个sheet
		try {
	        // 表头合并
			sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 13 ));// 合并第一行第一到16列
			sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 13 ));// 合并第二行第一到16列
			//设置第一列机构单元格的宽度
	        sheet.setColumnWidth(0, 20*2*256);//长度乘以2是为了解决纯数字列宽度不足会显示科学计数法问题，乘以256得到的数据才是excel真实列宽。
			
			ExcelExport excelExport = new ExcelExport(workbook, sheet);
			excelExport.createRow(0); // 创建第一行
			excelExport.setCell(0, title, excelExport.getHeaderStyle());
			
			excelExport.createRow(1); // 创建第二行
			excelExport.setCell(0, "机构："+brName+" "+(empNo!=null&&!empNo.equals("")?"       客户经理："+empName+"["+ empNo+"]":"")+"       报表时段："+CommonFunctions.dateModifyD(minDate,1)+"-"+maxDate+"         单位：元，%(年利率)", excelExport.getTitleStyle());
			
			excelExport.createRow(2);//第三行，表头列
			sheet.setColumnWidth(0, 7*256);//设置第一列的宽度
			sheet.setColumnWidth(1, 50*256);//设置第2列的宽度  
			sheet.setColumnWidth(2, 40*256);//设置第3列的宽度     
			sheet.setColumnWidth(5, 35*256);//设置第6列的宽度  
			sheet.setColumnWidth(6, 25*256);//设置第7列的宽度   
			sheet.setColumnWidth(9, 20*256);//设置第10列的宽度  
			sheet.setColumnWidth(11, 20*256);//设置第12列的宽度   
			sheet.setColumnWidth(13, 20*256);//设置第14列的宽度  
			excelExport.setCell(0, "序号", excelExport.headCenterNormalStyle);
			excelExport.setCell(1, "机构名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(2, "客户经理名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(3, "账号", excelExport.headCenterNormalStyle);
			excelExport.setCell(4, "客户名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(5, "产品名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(6, "开户日期", excelExport.headCenterNormalStyle);
			excelExport.setCell(7, "到期日期", excelExport.headCenterNormalStyle);
			excelExport.setCell(8, "余额", excelExport.headCenterNormalStyle);
			excelExport.setCell(9, "日均余额", excelExport.headCenterNormalStyle);
//			excelExport.setCell(10, "日均余额积数", excelExport.headCenterNormalStyle);
			excelExport.setCell(10, "利率(%)", excelExport.headCenterNormalStyle);
			excelExport.setCell(11, "FTP价格(%)", excelExport.headCenterNormalStyle);
			excelExport.setCell(12, "利差(%)", excelExport.headCenterNormalStyle);
			excelExport.setCell(13, "FTP利润", excelExport.headCenterNormalStyle);
			double zftplr = 0,rjye=0, ftplr=0,ratefz=0,ftpfz=0,bal=0;
			//合计行
			int days = CommonFunctions.daysSubtract(maxDate, minDate);//天数
			if(reportList != null) {
				for(YlfxBbReport ylfxBbReport : reportList) {
					zftplr += ylfxBbReport.getFtplr();
				}
				for (int j = 0; j < reportList.size(); j++) {
					YlfxBbReport entity = reportList.get(j);
					rjye += entity.getRjye();
		        	ftplr += entity.getFtplr();
		        	ratefz += entity.getRjye()*entity.getRate();
		        	ftpfz += entity.getRjye()*(entity.getFtp()==-999?0:entity.getFtp());
		        	bal +=entity.getBal();
					//循环创建数据行
					excelExport.createRow(j+3);
					excelExport.setCell(0, j+1, excelExport.centerNormalStyle);
					excelExport.setCell(1, entity.getBrName()+"["+entity.getBrNo()+"]", excelExport.centerNormalStyle);
					excelExport.setCell(2, entity.getEmpName()+"["+entity.getEmpNo()+"]", excelExport.centerNormalStyle);
					excelExport.setCell(3, entity.getAcId(), excelExport.centerNormalStyle);
					excelExport.setCell(4, entity.getPrdtName(), excelExport.centerNormalStyle);
					excelExport.setCell(5, entity.getCustName(), excelExport.centerNormalStyle);
					excelExport.setCell(6,(entity.getOpnDate().equals("")||entity.getOpnDate()==null||entity.getOpnDate().equals("null"))?"-":entity.getOpnDate(), excelExport.centerNormalStyle);
					excelExport.setCell(7, (entity.getMtrDate().equals("")||entity.getMtrDate()==null||entity.getMtrDate().equals("null"))?"-":entity.getMtrDate(), excelExport.centerNormalStyle);
					excelExport.setCell(8, entity.getBal(), excelExport.rightNormalMoneyStyle);
					excelExport.setCell(9, entity.getRjye(), excelExport.rightNormalMoneyStyle);
//					excelExport.setCell(10, entity.getRjye()*days, excelExport.rightNormalMoneyStyle);
					excelExport.setCell(10, CommonFunctions.doublecut(entity.getRate(),3), excelExport.rightNormalStyle);
					excelExport.setCell(11, entity.getFtp()==-999?0:CommonFunctions.doublecut(entity.getFtp()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(12, CommonFunctions.doublecut(entity.getLc()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(13, entity.getFtplr(), excelExport.rightNormalMoneyStyle);
					
				}
				excelExport.createRow(reportList.size()+3);
				excelExport.setCell(0, "合计", excelExport.centerBoldStyle);
				excelExport.setCell(1, "-", excelExport.centerBoldStyle);
				excelExport.setCell(2, "-", excelExport.centerBoldStyle);
				excelExport.setCell(3, "-", excelExport.centerBoldStyle);
				excelExport.setCell(4, "-", excelExport.centerBoldStyle);
				excelExport.setCell(5, "-", excelExport.centerBoldStyle);
				excelExport.setCell(6, "-", excelExport.centerBoldStyle);
				excelExport.setCell(7, "-", excelExport.centerBoldStyle);
				excelExport.setCell(8, bal, excelExport.rightBoldMoneyStyle);
				excelExport.setCell(9, rjye, excelExport.rightBoldMoneyStyle);
//				excelExport.setCell(10, CommonFunctions.doublecut(rjye*days,3), excelExport.rightBoldStyle);
				excelExport.setCell(10, CommonFunctions.doublecut(ratefz/rjye*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(11, CommonFunctions.doublecut(ftpfz/rjye*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(12, CommonFunctions.doublecut(ftplr/rjye*360/days*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(13, ftplr, excelExport.rightBoldMoneyStyle);
				
			}
			
			
			sheet.getRow(0).setHeight((short)500);//设置第一行表格高度
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return workbook;
	}
    /**
     * 保证金存款客户经理FTP利润明细表
     * @param request
     * @param minDate
     * @param date
	 * @param brNo
	 * @param manageLvl
     * @param empNo
     * @param assessScope
     * @param pageName
     * @param pageSize
     * @param currentPage
     * @param isAll
     * @return
     */
    public PageUtil bzjckEmpDetailProfile(HttpServletRequest request, String minDate, String date, String brNo, String manageLvl, String empNo, Integer assessScope,
    		String pageName, int pageSize, int currentPage,boolean isAll) {
		
	    List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();

		Map<String,String>  brNameMap= FtpUtil.getBrNameMap();

		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");

		Map<String,String>  empNameMap= FtpUtil.getempNameMap();

		String brNos = this.getAllChildBr(brNo, manageLvl,telMst.getTelNo());//获取所有的子级机构，包括自己
		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());// 县联社
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultListEmpBzj(brNos, minDate, date,assessScope,empNo);
		if(ftpResultsList == null) return null;
		int days = CommonFunctions.daysSubtract(date, minDate);//天数

		//获取该机构下的所有客户经理
		String hsql1 = "from FtpEmpInfo where brMst.brNo "+this.getAllChildBr(brNo, manageLvl, telMst.getTelNo())+"";
		List<FtpEmpInfo> empList = daoFactory.query(hsql1, null);
		Map<String, FtpEmpInfo> ftpEmpMap = new HashMap<String, FtpEmpInfo>();
		for(FtpEmpInfo ftpEmpInfo : empList) {
			ftpEmpMap.put(ftpEmpInfo.getEmpNo(),ftpEmpInfo);
		}

		//获取该操作员可以查看的客户类型
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());
		
		//从结果集中获取某个客户经理的数据，根据账号汇总到map中
		for (String[] result : ftpResultsList) {
			boolean isStatic = this.getIsStatic(custType, result[6], result[16]);//记录是否纳入统计,分对公和对私
			FtpEmpInfo empInfo = ftpEmpMap.get(result[2]) == null ? null:ftpEmpMap.get(result[2]);
			if(isStatic&&empInfo!=null) {//与要统计的客户经理号相同且是要统计的产品
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[8]);
				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setEmpNo(result[2]);
//				ylfxBbReport.setEmpNo(empNo);
				ylfxBbReport.setEmpName(empInfo.getEmpName());
				ylfxBbReport.setBrNo(result[0]);
				ylfxBbReport.setBrName(brNameMap.get(result[0]));
				ylfxBbReport.setAcId(String.valueOf(result[5]));
				ylfxBbReport.setPrdtName(String.valueOf(result[9]));
				ylfxBbReport.setCustNo(String.valueOf(result[2]));
				ylfxBbReport.setCustName(String.valueOf(result[10]));
				ylfxBbReport.setOpnDate(String.valueOf(result[11]));
				ylfxBbReport.setMtrDate(String.valueOf(result[12]));
				ylfxBbReport.setRate(result[15] == null ? 0 : Double.valueOf(String.valueOf(result[15])));
				ylfxBbReport.setFtp(result[14] == null ? 0 : Double.valueOf(String.valueOf(result[14])));//如果为null则赋值为-999
				ylfxBbReport.setRjye(rjye);//日均余额
				ylfxBbReport.setFtplr(ftplr);//FTP利润
				ylfxBbReport.setBal(bal);//余额
				ylfxBbReport.setLc(rjye == 0 ? 0.00 : ftplr / rjye * 360 / days);//利差(年利率)
				ylfxBbReport.setBusinessName(result[13]);

				ylfxBbReportList.add(ylfxBbReport);
			}
		}
		int rowsCount = ylfxBbReportList.size();
		//对ylfxBbReportList按FTP利润大小进行降序排序
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
					public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
						return arg1.getFtplr().compareTo(arg0.getFtplr());
					}
				}
		);
		QueryPageBO queryPageBo = new QueryPageBO();
		String pageLine=queryPageBo.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		if(isAll){
			return new PageUtil(ylfxBbReportList,pageLine);
		}else{
			if(currentPage*pageSize<rowsCount){
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,currentPage*pageSize),pageLine);
			}else {
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,rowsCount),pageLine);
			}
		}
	}
    /**
	 * 保证金存款客户经理FTP利润表-导出
	 * @param reportList
	 * @param brName
	 * @param empName
	 * @param empNo
	 * @param minDate
	 * @param maxDate
	 * @param title
	 * @return
	 */

	/**
	 * 对公业务FTP利润明细表
	 * @param request
	 * @param minDate
	 * @param date
	 * @param empNo
	 * @param assessScope
	 * @param businessNo
	 * @param prdtCtgNo
	 * @param prdtNo
	 * @param pageName
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public PageUtil gsywbDetailProfile(HttpServletRequest request, String minDate, String date, String brNo, Integer assessScope,String businessNo,String prdtCtgNo,String prdtNo,
									 String pageName, int pageSize, int currentPage,String manageLvl,boolean isAll) {

		List<YlfxBbReport> ylfxBbReportList = new ArrayList<YlfxBbReport>();

		TelMst telMst = (TelMst) request.getSession().getAttribute("userBean");

		String brNos = this.getAllChildBr(brNo, manageLvl,telMst.getTelNo());//获取所有的子级机构，包括自己

		String xlsBrNo = FtpUtil.getXlsBrNo_qhpm(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());// 县联社
		//从缓存中获取数据
		//List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, minDate, date,assessScope}, intervalTime, maxVisitCount);
		List<String[]> ftpResultsList = (List<String[]>)getQxppResultListGsyw(xlsBrNo, minDate, date,assessScope,brNos);
		if(ftpResultsList == null) return null;

		int days = CommonFunctions.daysSubtract(date, minDate);//天数

		//获取该操作员可以查看的客户类型
		String custType = this.getCustTypeByBrNoAndRoleLvl(telMst.getFtpEmpInfo()==null?"":telMst.getFtpEmpInfo().getBrMst().getBrNo(), telMst.getRoleMst().getRoleLvl());

		//根据业务条线编号或者产品大类或者产品编号获取对应的产品编号
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = this.getFtpBSDivideList(xlsBrNo, businessNo, prdtCtgNo, prdtNo);
		String prdtNos = "";
		for(FtpBusinessStaticDivide ftpBusinessStaticDivide : ftpBusinessStaticDivideList) {
			prdtNos += ftpBusinessStaticDivide.getProductNo()+",";
		}
		Map<String,String>  brNameMap= FtpUtil.getBrNameMap();
		Map<String, Double> ftplrjlMap = new HashMap<String, Double>();//key:账号
		Map<String,String>  empNameMap= FtpUtil.getempNameMap();
		StringBuffer accIds = new StringBuffer();
		//从结果集中获取某个客户经理的数据，根据账号汇总到map中
		for (String[] result : ftpResultsList) {
			boolean isStatic = this.getIsStatic(custType, result[6], result[16]);//记录是否纳入统计,分对公和对私
			if(isStatic && prdtNos.indexOf(result[1])!=-1) {//与要统计的客户经理号相同且是要统计的产品
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[8]);

				YlfxBbReport ylfxBbReport = new YlfxBbReport();
				ylfxBbReport.setBrNo(result[0]);
				ylfxBbReport.setBrName(brNameMap.get(result[0]));
				ylfxBbReport.setEmpNo(result[2]);
				ylfxBbReport.setEmpName(empNameMap.get(result[2]));
				ylfxBbReport.setEmpName(empNameMap.get(result[2]));
				ylfxBbReport.setAcId(String.valueOf(result[5]));
				ylfxBbReport.setPrdtName(String.valueOf(result[9]));
				ylfxBbReport.setCustNo(String.valueOf(result[2]));
				ylfxBbReport.setCustName(String.valueOf(result[10]));
				ylfxBbReport.setOpnDate(String.valueOf(result[11]));
				ylfxBbReport.setMtrDate(String.valueOf(result[12]));
				ylfxBbReport.setRate(result[15] == null ? 0 : Double.valueOf(String.valueOf(result[15])));
				ylfxBbReport.setFtp(result[14] == null ? 0 : Double.valueOf(String.valueOf(result[14])));//如果为null则赋值为-999
				ylfxBbReport.setRjye(rjye);//日均余额
				ylfxBbReport.setFtplr(ftplr);//FTP利润
				ylfxBbReport.setBal(bal);//余额
				ylfxBbReport.setLc(rjye == 0 ? 0.00 : ftplr / rjye * 360 / days);//利差(年利率)
				ylfxBbReport.setBusinessName(result[13]);

				ylfxBbReportList.add(ylfxBbReport);


			}
		}
		int rowsCount = ylfxBbReportList.size();
		//对ylfxBbReportList按FTP利润大小进行降序排序
		Collections.sort(ylfxBbReportList, new Comparator<YlfxBbReport>() {
					public int compare(YlfxBbReport arg0, YlfxBbReport arg1) {
						return arg1.getFtplr().compareTo(arg0.getFtplr());
					}
				}
		);
		QueryPageBO queryPageBo = new QueryPageBO();
		String pageLine=queryPageBo.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		if(isAll){
			return new PageUtil(ylfxBbReportList,pageLine);
		}else{
			if(currentPage*pageSize<rowsCount){
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,currentPage*pageSize),pageLine);
			}else {
				return new PageUtil(ylfxBbReportList.subList((currentPage-1)*pageSize,rowsCount),pageLine);
			}
		}
		
	}

	/**
	 * 公司业务部FTP利润明细表-导出
	 * @param reportList
	 * @param brName
	 * @param empName
	 * @param empNo
	 * @param minDate
	 * @param maxDate
	 * @param title
	 * @return
	 */
	public HSSFWorkbook getGsywblrmxbWorkbook(List<YlfxBbReport> reportList, String brName , String minDate, String maxDate, String title) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFSheet sheet = workbook.createSheet(title);//生成一个sheet
		try {
	        // 表头合并
			sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 15 ));// 合并第一行第一到15列
			sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 15 ));// 合并第二行第一到15列
			//设置第一列机构单元格的宽度
	        sheet.setColumnWidth(0, 20*2*256);//长度乘以2是为了解决纯数字列宽度不足会显示科学计数法问题，乘以256得到的数据才是excel真实列宽。
			
			ExcelExport excelExport = new ExcelExport(workbook, sheet);
			excelExport.createRow(0); // 创建第一行
			excelExport.setCell(0, title, excelExport.getHeaderStyle());
			
			excelExport.createRow(1); // 创建第二行
			excelExport.setCell(0, "机构："+brName+"        报表时段："+CommonFunctions.dateModifyD(minDate,1)+"-"+maxDate+"         单位：元，%(年利率)", excelExport.getTitleStyle());
			
			excelExport.createRow(2);//第三行，表头列
			sheet.setColumnWidth(0, 7*256);//设置第一列的宽度   
			sheet.setColumnWidth(1, 35*256);//设置第2列的宽度  
			sheet.setColumnWidth(3, 35*256);//设置第4列的宽度   
			sheet.setColumnWidth(5, 35*256);//设置第6列的宽度   
			sheet.setColumnWidth(6, 45*256);//设置第7列的宽度  
			sheet.setColumnWidth(9, 20*256);//设置第10列的宽度   
			sheet.setColumnWidth(10, 20*256);//设置第11列的宽度  
			sheet.setColumnWidth(11, 20*256);//设置第12列的宽度  
			excelExport.setCell(0, "序号", excelExport.headCenterNormalStyle);
			excelExport.setCell(1, "机构名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(2, "客户经理名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(3, "账号", excelExport.headCenterNormalStyle);
			excelExport.setCell(4, "业务名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(5, "产品名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(6, "客户名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(7, "开户日期", excelExport.headCenterNormalStyle);
			excelExport.setCell(8, "到期日期", excelExport.headCenterNormalStyle);
			excelExport.setCell(9, "余额", excelExport.headCenterNormalStyle);
			excelExport.setCell(10, "日均余额", excelExport.headCenterNormalStyle);
			excelExport.setCell(11, "日均余额积数", excelExport.headCenterNormalStyle);
			excelExport.setCell(12, "利率（%）", excelExport.headCenterNormalStyle);
			excelExport.setCell(13, "FTP价格（%）", excelExport.headCenterNormalStyle);
			excelExport.setCell(14, "利差（%）", excelExport.headCenterNormalStyle);
			excelExport.setCell(15, "FTP利润", excelExport.headCenterNormalStyle);
			double zftplr = 0,rjye=0, ftplr=0,ratefz=0,ftpfz=0,bal=0;
			int days = CommonFunctions.daysSubtract(maxDate, minDate);//天数
			if(reportList != null) {
				for(YlfxBbReport ylfxBbReport : reportList) {
					zftplr += ylfxBbReport.getFtplr();
				}
				for (int j = 0; j < reportList.size(); j++) {
					YlfxBbReport entity = reportList.get(j);
					rjye += entity.getRjye();
		        	ftplr += entity.getFtplr();
		        	ratefz += entity.getRjye()*entity.getRate();
		        	ftpfz += entity.getRjye()*(entity.getFtp()==-999?0:entity.getFtp());
		        	bal+=entity.getBal();
					//循环创建数据行
					excelExport.createRow(j+3);
					excelExport.setCell(0, j+1, excelExport.centerNormalStyle);
					excelExport.setCell(1, entity.getBrName()+"["+entity.getBrNo()+"]", excelExport.centerNormalStyle);
					excelExport.setCell(2, entity.getEmpName()+"["+entity.getEmpNo()+"]", excelExport.centerNormalStyle);
					excelExport.setCell(3, entity.getAcId(), excelExport.centerNormalStyle);
					excelExport.setCell(4, entity.getBusinessName(), excelExport.centerNormalStyle);
					excelExport.setCell(5, entity.getPrdtName(), excelExport.centerNormalStyle);
					excelExport.setCell(6, entity.getCustName(), excelExport.centerNormalStyle);
					excelExport.setCell(7,(entity.getOpnDate().equals("")||entity.getOpnDate()==null||entity.getOpnDate().equals("null"))?"-":entity.getOpnDate(), excelExport.centerNormalStyle);
					excelExport.setCell(8, (entity.getMtrDate().equals("")||entity.getMtrDate()==null||entity.getMtrDate().equals("null"))?"-":entity.getMtrDate(), excelExport.centerNormalStyle);
					excelExport.setCell(9, entity.getBal(), excelExport.rightNormalMoneyStyle);
					excelExport.setCell(10, entity.getRjye(), excelExport.rightNormalMoneyStyle);
					excelExport.setCell(11, entity.getRjye()*days, excelExport.rightNormalMoneyStyle);
					excelExport.setCell(12, CommonFunctions.doublecut(entity.getRate()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(13, entity.getFtp()==-999?0:CommonFunctions.doublecut(entity.getFtp()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(14, CommonFunctions.doublecut(entity.getLc()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(15, entity.getFtplr(), excelExport.rightNormalMoneyStyle);
				}
				//合计行
				//int days = CommonFunctions.daysSubtract(maxDate, minDate);//天数
				excelExport.createRow(reportList.size()+3);
				excelExport.setCell(0, "合计", excelExport.centerBoldStyle);
				excelExport.setCell(1, "-", excelExport.centerBoldStyle);
				excelExport.setCell(2, "-", excelExport.centerBoldStyle);
				excelExport.setCell(3, "-", excelExport.centerBoldStyle);
				excelExport.setCell(4, "-", excelExport.centerBoldStyle);
				excelExport.setCell(5, "-", excelExport.centerBoldStyle);
				excelExport.setCell(6, "-", excelExport.centerBoldStyle);
				excelExport.setCell(7, "-", excelExport.centerBoldStyle);
				excelExport.setCell(8, "-", excelExport.centerBoldStyle);
				excelExport.setCell(9, bal, excelExport.rightBoldMoneyStyle);
				excelExport.setCell(10, rjye, excelExport.rightBoldMoneyStyle);
				excelExport.setCell(11, CommonFunctions.doublecut(rjye*days,3), excelExport.rightBoldMoneyStyle);
				excelExport.setCell(12, CommonFunctions.doublecut(ratefz/rjye*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(13, CommonFunctions.doublecut(ftpfz/rjye*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(14, CommonFunctions.doublecut(ftplr/rjye*360/days*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(15, ftplr, excelExport.rightBoldMoneyStyle);
			}
			sheet.getRow(0).setHeight((short)500);//设置第一行表格高度
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return workbook;
	}

	public HSSFWorkbook getBzjckkhjllrbWorkbook(List<YlfxBbReport> reportList, String brName, String empName, String empNo, String minDate, String maxDate, String title) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		HSSFSheet sheet = workbook.createSheet(title);//生成一个sheet
		try {
	        // 表头合并
			sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 14 ));// 合并第一行第一到15列
			sheet.addMergedRegion(new Region(1, (short) 0, 1, (short) 14 ));// 合并第二行第一到15列
			//设置第一列机构单元格的宽度
	        sheet.setColumnWidth(0, 20*2*256);//长度乘以2是为了解决纯数字列宽度不足会显示科学计数法问题，乘以256得到的数据才是excel真实列宽。
			
			ExcelExport excelExport = new ExcelExport(workbook, sheet);
			excelExport.createRow(0); // 创建第一行
			excelExport.setCell(0, title, excelExport.getHeaderStyle());
			
			excelExport.createRow(1); // 创建第二行
			excelExport.setCell(0, "机构："+brName+" "+(empNo!=null&&!empNo.equals("")?"       客户经理："+empName+"["+ empNo+"]":"")+"       报表时段："+CommonFunctions.dateModifyD(minDate,1)+"-"+maxDate+"         单位：元，%(年利率)", excelExport.getTitleStyle());
			
			excelExport.createRow(2);//第三行，表头列
			sheet.setColumnWidth(0, 7*256);//设置第一列的宽度
			sheet.setColumnWidth(1, 30*256);//设置第2列的宽度  
			sheet.setColumnWidth(2, 30*256);//设置第3列的宽度     
			sheet.setColumnWidth(3, 30*256);//设置第6列的宽度  
			sheet.setColumnWidth(4, 30*256);//设置第6列的宽度  
			sheet.setColumnWidth(5, 40*256);//设置第3列的宽度     
			sheet.setColumnWidth(6, 25*256);//设置第7列的宽度   
			sheet.setColumnWidth(9, 12*256);//设置第10列的宽度  
			sheet.setColumnWidth(11, 10*256);//设置第12列的宽度   
			sheet.setColumnWidth(13, 10*256);//设置第13列的宽度  
			excelExport.setCell(0, "序号", excelExport.headCenterNormalStyle);
			excelExport.setCell(1, "机构名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(2, "客户经理名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(3, "账号", excelExport.headCenterNormalStyle);
			excelExport.setCell(4, "产品名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(5, "客户名称", excelExport.headCenterNormalStyle);
			excelExport.setCell(6, "开户日期", excelExport.headCenterNormalStyle);
			excelExport.setCell(7, "到期日期", excelExport.headCenterNormalStyle);
			excelExport.setCell(8, "期限", excelExport.headCenterNormalStyle);
			excelExport.setCell(9, "余额", excelExport.headCenterNormalStyle);
			excelExport.setCell(10, "日均余额", excelExport.headCenterNormalStyle);
			excelExport.setCell(11, "利率(%)", excelExport.headCenterNormalStyle);
			excelExport.setCell(12, "FTP价格(%)", excelExport.headCenterNormalStyle);
			excelExport.setCell(13, "利差(%)", excelExport.headCenterNormalStyle);
			excelExport.setCell(14, "FTP利润", excelExport.headCenterNormalStyle);
			double zftplr = 0,rjye=0, ftplr=0,ratefz=0,ftpfz=0,bal=0;
			if(reportList != null) {
				for(YlfxBbReport ylfxBbReport : reportList) {
					zftplr += ylfxBbReport.getFtplr();
				}
				for (int j = 0; j < reportList.size(); j++) {
					YlfxBbReport entity = reportList.get(j);
					rjye += entity.getRjye();
		        	ftplr += entity.getFtplr();
		        	ratefz += entity.getRjye()*entity.getRate();
		        	ftpfz += entity.getRjye()*(entity.getFtp()==-999?0:entity.getFtp());
		        	bal +=entity.getBal();
					//循环创建数据行
					excelExport.createRow(j+3);
					excelExport.setCell(0, j+1, excelExport.centerNormalStyle);
					excelExport.setCell(1, entity.getBrName()+"["+entity.getBrNo()+"]", excelExport.centerNormalStyle);
					excelExport.setCell(2, entity.getEmpName()+"["+entity.getEmpNo()+"]", excelExport.centerNormalStyle);
					excelExport.setCell(3, entity.getAcId(), excelExport.centerNormalStyle);
					excelExport.setCell(4, entity.getPrdtName(), excelExport.centerNormalStyle);
					excelExport.setCell(5, entity.getCustName(), excelExport.centerNormalStyle);
					excelExport.setCell(6, (entity.getOpnDate().equals("")||entity.getOpnDate()==null||entity.getOpnDate().equals("null"))?"-":entity.getOpnDate(), excelExport.centerNormalStyle);
					excelExport.setCell(7, (entity.getMtrDate().equals("")||entity.getMtrDate()==null||entity.getMtrDate().equals("null"))?"-":entity.getMtrDate(), excelExport.centerNormalStyle);
					excelExport.setCell(8, ((entity.getOpnDate().equals("")||entity.getOpnDate()==null||entity.getOpnDate().equals("null"))||(entity.getMtrDate().equals("")||entity.getMtrDate()==null||entity.getMtrDate().equals("null")))?"-":String.valueOf(CommonFunctions.daysSubtract(entity.getMtrDate(), entity.getOpnDate())), excelExport.centerNormalStyle);
					excelExport.setCell(9, entity.getBal(), excelExport.centerNormalStyle);
					excelExport.setCell(10, entity.getRjye(), excelExport.rightNormalMoneyStyle);
					excelExport.setCell(11, CommonFunctions.doublecut(entity.getRate()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(12, entity.getFtp()==-999?0:CommonFunctions.doublecut(entity.getFtp()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(13, CommonFunctions.doublecut(entity.getLc()*100,3), excelExport.rightNormalStyle);
					excelExport.setCell(14, entity.getFtplr(), excelExport.rightNormalMoneyStyle);
					
				}
				//合计行
				int days = CommonFunctions.daysSubtract(maxDate, minDate);//天数
				excelExport.createRow(reportList.size()+3);
				excelExport.setCell(0, "合计", excelExport.centerBoldStyle);
				excelExport.setCell(1, "-", excelExport.centerBoldStyle);
				excelExport.setCell(2, "-", excelExport.centerBoldStyle);
				excelExport.setCell(3, "-", excelExport.centerBoldStyle);
				excelExport.setCell(4, "-", excelExport.centerBoldStyle);
				excelExport.setCell(5, "-", excelExport.centerBoldStyle);
				excelExport.setCell(6, "-", excelExport.centerBoldStyle);
				excelExport.setCell(7, "-", excelExport.centerBoldStyle);
				excelExport.setCell(8, "-", excelExport.centerBoldStyle);
				excelExport.setCell(9, bal, excelExport.centerBoldStyle);
				excelExport.setCell(10, rjye, excelExport.rightBoldMoneyStyle);
				excelExport.setCell(11, CommonFunctions.doublecut(ratefz/rjye*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(12, CommonFunctions.doublecut(ftpfz/rjye*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(13, CommonFunctions.doublecut(ftplr/rjye*360/days*100,3), excelExport.rightBoldStyle);
				excelExport.setCell(14, ftplr, excelExport.rightBoldMoneyStyle);
				
			}
			
			
			sheet.getRow(0).setHeight((short)500);//设置第一行表格高度
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return workbook;
	}
	/**
	  * 以员工账户关联表为基准，获取日期范围内的账户的机构、产品、客户经理、日均余额、利润、账号、客户号
	  * @param xlsbrNo
	  * @param minDate日期左端点
	  * @param maxDate日期右端点
	  * @return
	  */
	public List<String[]> getQxppResultList(String xlsbrNo, String minDate, String maxDate,Integer assessScope) {
		List<String[]> resultList=new ArrayList<String[]>();

    	Map<String, String> ftpValuesMap = new HashMap<String, String>();
    	
    	Integer days = CommonFunctions.daysSubtract(maxDate, minDate);
    	//1.获取员工对应的机构信息
		Map<String, String> empInfoMap = new HashMap<String, String>();
		String sql3 = "select emp_no, trim(br_no) from ftp.ftp_emp_info";
		List empList = daoFactory.query1(sql3, null);
		if(empList != null && empList.size() > 0) {
			for(Object object : empList) {
				Object[] obj = (Object[])object;
				empInfoMap.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
			}
		}
		empList.clear(); System.gc();//释放empList所占的内存
    	
    	//2.获取日均余额和FTP价格
		int num_errorInfo=0;
		double rjye_whole_mx=0;
		
		String avgbalStr = "";//日均余额查询的字段
		if(assessScope == -1) {
			avgbalStr = "AVERATE_BAL_M";//月度日均余额
		} else if(assessScope == -3) {
			avgbalStr = "AVERATE_BAL_Q";//季度日均余额
		} else if(assessScope == -12) {
			avgbalStr = "AVERATE_BAL_Y";//年度日均余额
		}
		//从TJBB_RESULT表中直接获取日均余额、定价结果信息
		String sql = "select ac_id,"+avgbalStr+",FTP_PRICE,rate,cust_no,is_zq,prdt_no,bal,cum_no" +
				" from ftp.TJBB_RESULT where trim(CYC_DATE) = '"+maxDate+"'  and " +
						""+avgbalStr+" !=0 and "+avgbalStr+" is not null";
		List list = daoFactory.query1(sql, null);
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				double avebal = obj[1] == null ? 0.0 : Double.valueOf(obj[1].toString());//日均余额
				double ftp = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());//FTP
				double rate = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());//利率
				double bal = obj[7] == null ? 0.0 : Double.valueOf(obj[7].toString());//FTP
				String custNo = String.valueOf(obj[8]);
				String[] result = new String[10];
				result[0] = empInfoMap.get(custNo);//客户经理所属机构
				if(result[0] == null) {
					num_errorInfo++;
					continue;
				}
				if(obj[6]==null||"".equals(obj[6])){
					continue;
				}
				result[1] = String.valueOf(obj[6]);//产品
				result[2] = String.valueOf(custNo);//客户经理编号
				result[3] = String.valueOf(avebal);//日均余额
				if(obj[6].toString().startsWith("P1")){
					result[4] = String.valueOf(avebal*(rate-ftp)*days/360);//FTP利润
				}else{
					result[4] = String.valueOf(avebal*(ftp-rate)*days/360);//FTP利润
				}
				result[5] = String.valueOf(obj[0]);//账号
				result[6] = custNo;//客户号		
				result[7] = String.valueOf(obj[5]);//展期状态，如果='2'为个人按揭			
				if(avebal!=0){
					resultList.add(result);
				}
			}
		}
		list.clear(); System.gc();//释放list所占的内存
		ftpValuesMap.clear(); System.gc();
		return resultList;
	}

	/**
	 * 以员工账户关联表为基准，获取日期范围内的账户的机构、产品、客户经理、日均余额、利润、账号、客户号
	 * @param xlsbrNo
	 * @param minDate日期左端点
	 * @param maxDate日期右端点
	 * @return
	 */
	public List<String[]> getQxppResultListMortgage(String xlsbrNo, String minDate, String maxDate,Integer assessScope,String brNos,String empNo) {
		List<String[]> resultList=new ArrayList<String[]>();
		Map<String, String> ftpValuesMap = new HashMap<String, String>();

		Integer days = CommonFunctions.daysSubtract(maxDate, minDate);
		//1.获取员工对应的机构信息
		Map<String, String> empInfoMap = new HashMap<String, String>();
		String sql3 = "select emp_no, trim(br_no) from ftp.ftp_emp_info";
		List empList = daoFactory.query1(sql3, null);
		if(empList != null && empList.size() > 0) {
			for(Object object : empList) {
				Object[] obj = (Object[])object;
				empInfoMap.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
			}
		}
		empList.clear(); System.gc();//释放empList所占的内存


		//2.获取日均余额和FTP价格
		String avgbalStr = "";//日均余额查询的字段
		if(assessScope == -1) {
			avgbalStr = "AVERATE_BAL_M";//月度日均余额
		} else if(assessScope == -3) {
			avgbalStr = "AVERATE_BAL_Q";//季度日均余额
		} else if(assessScope == -12) {
			avgbalStr = "AVERATE_BAL_Y";//年度日均余额
		}

		String sql="";

		if(empNo!=null&&!"".equals(empNo)){
			//从TJBB_RESULT表中直接获取日均余额、定价结果信息
			sql = "select t.*,t1.product_name,t1.cus_name,t1.Opn_date,t1.Mtr_date,t1.BUSINESS_NAME,t1.kmh from (select ac_id,"+avgbalStr+",FTP_PRICE,rate,cust_no,is_zq,prdt_no,bal,cum_no" +
					" from ftp.TJBB_RESULT where trim(CYC_DATE) = '"+maxDate+"'  and " +
					""+avgbalStr+" !=0 and "+avgbalStr+" is not null and br_no "+brNos+" and  substr(kmh,1,6)='130405' and cum_no = '"+empNo+"') t left join";
			String sql_2 = " (select a.ac_id,c.product_name,a.cust_no,a.cus_name,a.Opn_date,a.Mtr_date,a.rate,c.BUSINESS_NAME,a.kmh" +
					" from ftp.fzh_history a " +
					" left join ftp.ftp_business_static_divide c on a.prdt_no = c.product_no " +
					"  ) t1 on t.ac_id=t1.ac_id" ;
			sql+=sql_2;
		}else {
			//从TJBB_RESULT表中直接获取日均余额、定价结果信息
			   sql = "select t.*,t1.product_name,t1.cus_name,t1.Opn_date,t1.Mtr_date,t1.BUSINESS_NAME,t1.kmh from (select ac_id,"+avgbalStr+",FTP_PRICE,rate,cust_no,is_zq,prdt_no,bal,cum_no" +
					" from ftp.TJBB_RESULT where trim(CYC_DATE) = '"+maxDate+"'  and " +
					""+avgbalStr+" !=0 and "+avgbalStr+" is not null and br_no "+brNos+" and  substr(kmh,1,6)='130405') t left join";
			String sql_2 = " (select a.ac_id,c.product_name,a.cust_no,a.cus_name,a.Opn_date,a.Mtr_date,a.rate,c.BUSINESS_NAME,a.kmh" +
					" from ftp.fzh_history a " +
					" left join ftp.ftp_business_static_divide c on a.prdt_no = c.product_no " +
					"  ) t1 on t.ac_id=t1.ac_id" ;
			sql+=sql_2;
		}

		List list = daoFactory.query1(sql, null);
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				double avebal = obj[1] == null ? 0.0 : Double.valueOf(obj[1].toString());//日均余额
				double ftp = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());//FTP
				double rate = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());//利率
				double bal = obj[7] == null ? 0.0 : Double.valueOf(obj[7].toString());//FTP
				String custNo = String.valueOf(obj[8]);
				String product_name = String.valueOf(obj[9]==null?"":obj[9].toString());
				String cus_name = String.valueOf(obj[10]==null?"":obj[10].toString());
				String Opn_date = String.valueOf(obj[11]==null?"":obj[11].toString());
				String Mtr_date = String.valueOf(obj[12]==null?"":obj[12].toString());
				String business_name = String.valueOf(obj[13]==null?"":obj[13].toString());
				String kmh = String.valueOf(obj[14]==null?"":obj[14].toString());
				String[] result = new String[17];
				result[0] = empInfoMap.get(custNo);//客户经理所属机构
				if(result[0] == null) {
					continue;
				}
				if(obj[6]==null||"".equals(obj[6])){
					continue;
				}
				result[1] = String.valueOf(obj[6]);//产品
				result[2] = String.valueOf(custNo);//客户经理编号
				result[3] = String.valueOf(avebal);//日均余额
				if(obj[6].toString().startsWith("P1")){
					result[4] = String.valueOf(avebal*(rate-ftp)*days/360);//FTP利润
				}else{
					result[4] = String.valueOf(avebal*(ftp-rate)*days/360);//FTP利润
				}
				result[5] = String.valueOf(obj[0]);//账号
				result[6] = custNo;//客户号
				result[7] = String.valueOf(obj[5]);//展期状态，如果='2'为个人按揭
				result[8] = String.valueOf(bal);//余额

				result[9] = String.valueOf(product_name);//产品名称
				result[10] = String.valueOf(cus_name);//客户名称
				result[11] = String.valueOf(Opn_date);//开户日期
				result[12] = String.valueOf(Mtr_date);//到期日期
				result[13] = String.valueOf(business_name);//业务

				result[14] = String.valueOf(ftp);//ftp价格
				result[15] = String.valueOf(rate);//ftp利率
				result[16] = String.valueOf(kmh);//科目号
				if(avebal!=0){
					resultList.add(result);
				}
			}
		}
		list.clear(); System.gc();//释放list所占的内存
		ftpValuesMap.clear(); System.gc();
		return resultList;
	}


	/**
	  * 以员工账户关联表为基准，获取日期范围内的账户的机构、产品、客户经理、日均余额、利润、账号、客户号
	  * @param xlsbrNo
	  * @param minDate日期左端点
	  * @param maxDate日期右端点
	  * @return
	  */
	public List<String[]> getQxppResultListEmp(String xlsbrNo, String minDate, String maxDate,Integer assessScope,String empNo) {
		List<String[]> resultList=new ArrayList<String[]>();
		Map<String, String> ftpValuesMap = new HashMap<String, String>();

		Integer days = CommonFunctions.daysSubtract(maxDate, minDate);
		//1.获取员工对应的机构信息
		Map<String, String> empInfoMap = new HashMap<String, String>();
		String sql3 = "select emp_no, trim(br_no) from ftp.ftp_emp_info";
		List empList = daoFactory.query1(sql3, null);
		if(empList != null && empList.size() > 0) {
			for(Object object : empList) {
				Object[] obj = (Object[])object;
				empInfoMap.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
			}
		}
		empList.clear(); System.gc();//释放empList所占的内存


		//2.获取日均余额和FTP价格
		String avgbalStr = "";//日均余额查询的字段
		if(assessScope == -1) {
			avgbalStr = "AVERATE_BAL_M";//月度日均余额
		} else if(assessScope == -3) {
			avgbalStr = "AVERATE_BAL_Q";//季度日均余额
		} else if(assessScope == -12) {
			avgbalStr = "AVERATE_BAL_Y";//年度日均余额
		}
		//从TJBB_RESULT表中直接获取日均余额、定价结果信息
		String sql = "select t.*,t1.product_name,t1.cus_name,t1.Opn_date,t1.Mtr_date,t1.BUSINESS_NAME,t1.kmh,t1.CRDNO from (select ac_id,"+avgbalStr+",FTP_PRICE,rate,cust_no,is_zq,prdt_no,bal,cum_no" +
				" from ftp.TJBB_RESULT where trim(CYC_DATE) = '"+maxDate+"'  and " +
				""+avgbalStr+" !=0 and "+avgbalStr+" is not null and cum_no = '"+empNo+"') t left join";
		String sql_2 = " (select a.ac_id,c.product_name,a.cust_no,a.cus_name,a.Opn_date,a.Mtr_date,a.rate,c.BUSINESS_NAME,a.kmh,trim(d.CRDNO) as CRDNO" +
				" from ftp.fzh_history a " +
				" left join ftp.ftp_business_static_divide c on a.prdt_no = c.product_no " +
				" left join bips.NSOP_MIR_CDFM23 d on a.SOP_CODE = d.ACCNO " +
				"  ) t1 on t.ac_id=t1.ac_id" ;
		sql+=sql_2;
		List list = daoFactory.query1(sql, null);
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				double avebal = obj[1] == null ? 0.0 : Double.valueOf(obj[1].toString());//日均余额
				double ftp = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());//FTP
				double rate = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());//利率
				double bal = obj[7] == null ? 0.0 : Double.valueOf(obj[7].toString());//FTP
				String custNo = String.valueOf(obj[8]);
				String product_name = String.valueOf(obj[9]==null?"":obj[9].toString());
				String cus_name = String.valueOf(obj[10]==null?"":obj[10].toString());
				String Opn_date = String.valueOf(obj[11]==null?"":obj[11].toString());
				String Mtr_date = String.valueOf(obj[12]==null?"":obj[12].toString());
				String business_name = String.valueOf(obj[13]==null?"":obj[13].toString());
				String kmh = String.valueOf(obj[14]==null?"":obj[14].toString());
				String[] result = new String[17];
				result[0] = empInfoMap.get(custNo);//客户经理所属机构
				if(result[0] == null) {
					continue;
				}
				if(obj[6]==null||"".equals(obj[6])){
					continue;
				}

				String ac_id = String.valueOf(obj[15]==null?obj[0].toString():obj[15].toString());
				result[1] = String.valueOf(obj[6]);//产品
				result[2] = String.valueOf(custNo);//客户经理编号
				result[3] = String.valueOf(avebal);//日均余额
				if(obj[6].toString().startsWith("P1")){
					result[4] = String.valueOf(avebal*(rate-ftp)*days/360);//FTP利润
				}else{
					result[4] = String.valueOf(avebal*(ftp-rate)*days/360);//FTP利润
				}

				result[5] = String.valueOf(ac_id);//账号
				result[6] = custNo;//客户号
				result[7] = String.valueOf(obj[5]);//展期状态，如果='2'为个人按揭
				result[8] = String.valueOf(bal);//余额

				result[9] = String.valueOf(product_name);//产品名称
				result[10] = String.valueOf(cus_name);//客户名称
				result[11] = String.valueOf(Opn_date);//开户日期
				result[12] = String.valueOf(Mtr_date);//到期日期
				result[13] = String.valueOf(business_name);//业务

				result[14] = String.valueOf(ftp);//ftp价格
				result[15] = String.valueOf(rate);//ftp利率
				result[16] = String.valueOf(kmh);//科目号
				if(avebal!=0){
					resultList.add(result);
				}
			}
		}
		list.clear(); System.gc();//释放list所占的内存
		ftpValuesMap.clear(); System.gc();
		return resultList;
	}


	/**
	 * 以员工账户关联表为基准，获取日期范围内的账户的机构、产品、客户经理、日均余额、利润、账号、客户号【保证金】
	 * @param xlsbrNo
	 * @return
	 */
	public List<String[]> getQxppResultListEmpBzj(String xlsbrNo, String minDate, String maxDate,Integer assessScope,String empNo) {
		List<String[]> resultList=new ArrayList<String[]>();

		Map<String, String> ftpValuesMap = new HashMap<String, String>();

		Integer days = CommonFunctions.daysSubtract(maxDate, minDate);
		//1.获取员工对应的机构信息
		Map<String, String> empInfoMap = new HashMap<String, String>();
		String sql3 = "select emp_no, trim(br_no) from ftp.ftp_emp_info";
		List empList = daoFactory.query1(sql3, null);
		if(empList != null && empList.size() > 0) {
			for(Object object : empList) {
				Object[] obj = (Object[])object;
				empInfoMap.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
			}
		}
		empList.clear(); System.gc();//释放empList所占的内存


		//2.获取日均余额和FTP价格
		String avgbalStr = "";//日均余额查询的字段
		if(assessScope == -1) {
			avgbalStr = "AVERATE_BAL_M";//月度日均余额
		} else if(assessScope == -3) {
			avgbalStr = "AVERATE_BAL_Q";//季度日均余额
		} else if(assessScope == -12) {
			avgbalStr = "AVERATE_BAL_Y";//年度日均余额
		}

		String sql="";
		if(empNo!=null&&!"".equals(empNo)){
			//从TJBB_RESULT表中直接获取日均余额、定价结果信息
			 sql = "select t.*,t1.product_name,t1.cus_name,t1.Opn_date,t1.Mtr_date,t1.BUSINESS_NAME,t1.kmh from (select ac_id,"+avgbalStr+",FTP_PRICE,rate,cust_no,is_zq,prdt_no,bal,cum_no" +
					" from ftp.TJBB_RESULT where trim(CYC_DATE) = '"+maxDate+"'  and " +
					""+avgbalStr+" !=0 and "+avgbalStr+" is not null and br_no "+xlsbrNo+" and cum_no = '"+empNo+"') t left join";
			String sql_2 = " (select a.ac_id,c.product_name,a.cust_no,a.cus_name,a.Opn_date,a.Mtr_date,a.rate,c.BUSINESS_NAME,a.kmh" +
					" from ftp.fzh_history a " +
					" left join ftp.ftp_business_static_divide c on a.prdt_no = c.product_no where c.PRODUCT_CTG_NO = 'D2011'" +
					"  ) t1 on t.ac_id=t1.ac_id where t1.ac_id is not null" ;
			sql+=sql_2;
		}else {
			//从TJBB_RESULT表中直接获取日均余额、定价结果信息
			 sql = "select t.*,t1.product_name,t1.cus_name,t1.Opn_date,t1.Mtr_date,t1.BUSINESS_NAME,t1.kmh from (select ac_id,"+avgbalStr+",FTP_PRICE,rate,cust_no,is_zq,prdt_no,bal,cum_no" +
					" from ftp.TJBB_RESULT where trim(CYC_DATE) = '"+maxDate+"'  and " +
					""+avgbalStr+" !=0 and "+avgbalStr+" is not null and br_no "+xlsbrNo+" ) t left join";
			String sql_2 = " (select a.ac_id,c.product_name,a.cust_no,a.cus_name,a.Opn_date,a.Mtr_date,a.rate,c.BUSINESS_NAME,a.kmh" +
					" from ftp.fzh_history a " +
					" left join ftp.ftp_business_static_divide c on a.prdt_no = c.product_no where c.PRODUCT_CTG_NO = 'D2011'" +
					"  ) t1 on t.ac_id=t1.ac_id where t1.ac_id is not null" ;
			sql+=sql_2;
		}
		List list = daoFactory.query1(sql, null);
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				double avebal = obj[1] == null ? 0.0 : Double.valueOf(obj[1].toString());//日均余额
				double ftp = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());//FTP
				double rate = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());//利率
				double bal = obj[7] == null ? 0.0 : Double.valueOf(obj[7].toString());//FTP
				String custNo = String.valueOf(obj[8]);
				String product_name = String.valueOf(obj[9]==null?"":obj[9].toString());
				String cus_name = String.valueOf(obj[10]==null?"":obj[10].toString());
				String Opn_date = String.valueOf(obj[11]==null?"":obj[11].toString());
				String Mtr_date = String.valueOf(obj[12]==null?"":obj[12].toString());
				String business_name = String.valueOf(obj[13]==null?"":obj[13].toString());
				String kmh = String.valueOf(obj[14]==null?"":obj[14].toString());
				String[] result = new String[17];
				result[0] = empInfoMap.get(custNo);//客户经理所属机构
				if(result[0] == null) {
					continue;
				}
				if(obj[6]==null||"".equals(obj[6])){
					continue;
				}
				result[1] = String.valueOf(obj[6]);//产品
				result[2] = String.valueOf(custNo);//客户经理编号
				result[3] = String.valueOf(avebal);//日均余额
				if(obj[6].toString().startsWith("P1")){
					result[4] = String.valueOf(avebal*(rate-ftp)*days/360);//FTP利润
				}else{
					result[4] = String.valueOf(avebal*(ftp-rate)*days/360);//FTP利润
				}
				result[5] = String.valueOf(obj[0]);//账号
				result[6] = custNo;//客户号
				result[7] = String.valueOf(obj[5]);//展期状态，如果='2'为个人按揭
				result[8] = String.valueOf(bal);//余额

				result[9] = String.valueOf(product_name);//产品名称
				result[10] = String.valueOf(cus_name);//客户名称
				result[11] = String.valueOf(Opn_date);//开户日期
				result[12] = String.valueOf(Mtr_date);//到期日期
				result[13] = String.valueOf(business_name);//业务

				result[14] = String.valueOf(ftp);//ftp价格
				result[15] = String.valueOf(rate);//ftp利率
				result[16] = String.valueOf(rate);//科目号
				if(avebal!=0){
					resultList.add(result);
				}
			}
		}
		list.clear(); System.gc();//释放list所占的内存
		ftpValuesMap.clear(); System.gc();
		return resultList;
	}


	/**
	 * 以员工账户关联表为基准，获取日期范围内的账户的机构、产品、客户经理、日均余额、利润、账号、客户号【所有客户经理】
	 * @param xlsbrNo
	 * @return
	 */
	public List<String[]> getQxppResultListEmpAll(String xlsbrNo, String minDate, String maxDate,Integer assessScope,String brNos) {
		List<String[]> resultList=new ArrayList<String[]>();

		Map<String, String> ftpValuesMap = new HashMap<String, String>();

		Integer days = CommonFunctions.daysSubtract(maxDate, minDate);
		//1.获取员工对应的机构信息
		Map<String, String> empInfoMap = new HashMap<String, String>();
		String sql3 = "select emp_no, trim(br_no) from ftp.ftp_emp_info";
		List empList = daoFactory.query1(sql3, null);
		if(empList != null && empList.size() > 0) {
			for(Object object : empList) {
				Object[] obj = (Object[])object;
				empInfoMap.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
			}
		}
		empList.clear(); System.gc();//释放empList所占的内存


		//2.获取日均余额和FTP价格
		String avgbalStr = "";//日均余额查询的字段
		if(assessScope == -1) {
			avgbalStr = "AVERATE_BAL_M";//月度日均余额
		} else if(assessScope == -3) {
			avgbalStr = "AVERATE_BAL_Q";//季度日均余额
		} else if(assessScope == -12) {
			avgbalStr = "AVERATE_BAL_Y";//年度日均余额
		}
		//从TJBB_RESULT表中直接获取日均余额、定价结果信息
		String sql = "select t.*,t1.product_name,t1.cus_name,t1.Opn_date,t1.Mtr_date,t1.BUSINESS_NAME,t1.kmh,t1.CRDNO from (select ac_id,"+avgbalStr+",FTP_PRICE,rate,cust_no,is_zq,prdt_no,bal,cum_no" +
				" from ftp.TJBB_RESULT where trim(CYC_DATE) = '"+maxDate+"'  and " +
				""+avgbalStr+" !=0 and "+avgbalStr+" is not null and br_no "+brNos+" and substr(cum_no,1,2) != '99' and cum_no is not null and cum_no !='') t left join";
		String sql_2 = " (select a.ac_id,c.product_name,a.cust_no,a.cus_name,a.Opn_date,a.Mtr_date,a.rate,c.BUSINESS_NAME,a.kmh,trim(d.CRDNO) as CRDNO" +
				" from ftp.fzh_history a " +
				" left join ftp.ftp_business_static_divide c on a.prdt_no = c.product_no " +
				" left join bips.NSOP_MIR_CDFM23 d on a.SOP_CODE = d.ACCNO " +
				"  ) t1 on t.ac_id=t1.ac_id" ;
		sql+=sql_2;
		List list = daoFactory.query1(sql, null);
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				double avebal = obj[1] == null ? 0.0 : Double.valueOf(obj[1].toString());//日均余额
				double ftp = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());//FTP
				double rate = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());//利率
				double bal = obj[7] == null ? 0.0 : Double.valueOf(obj[7].toString());//FTP
				String custNo = String.valueOf(obj[8]);
				String product_name = String.valueOf(obj[9]==null?"":obj[9].toString());
				String cus_name = String.valueOf(obj[10]==null?"":obj[10].toString());
				String Opn_date = String.valueOf(obj[11]==null?"":obj[11].toString());
				String Mtr_date = String.valueOf(obj[12]==null?"":obj[12].toString());
				String business_name = String.valueOf(obj[13]==null?"":obj[13].toString());
				String kmh = String.valueOf(obj[14]==null?"":obj[14].toString());
				String[] result = new String[17];
				result[0] = empInfoMap.get(custNo);//客户经理所属机构
				if(result[0] == null) {
					continue;
				}
				if(obj[6]==null||"".equals(obj[6])){
					continue;
				}

				String ac_id = String.valueOf(obj[15]==null?obj[0].toString():obj[15].toString());
				result[1] = String.valueOf(obj[6]);//产品
				result[2] = String.valueOf(custNo);//客户经理编号
				result[3] = String.valueOf(avebal);//日均余额
				if(obj[6].toString().startsWith("P1")){
					result[4] = String.valueOf(avebal*(rate-ftp)*days/360);//FTP利润
				}else{
					result[4] = String.valueOf(avebal*(ftp-rate)*days/360);//FTP利润
				}
				result[5] = String.valueOf(ac_id);//账号
				result[6] = custNo;//客户号
				result[7] = String.valueOf(obj[5]);//展期状态，如果='2'为个人按揭
				result[8] = String.valueOf(bal);//余额

				result[9] = String.valueOf(product_name);//产品名称
				result[10] = String.valueOf(cus_name);//客户名称
				result[11] = String.valueOf(Opn_date);//开户日期
				result[12] = String.valueOf(Mtr_date);//到期日期
				result[13] = String.valueOf(business_name);//业务

				result[14] = String.valueOf(ftp);//ftp价格
				result[15] = String.valueOf(rate);//ftp利率
				result[16] = String.valueOf(kmh);//科目号
				if(avebal!=0){
					resultList.add(result);
				}
			}
		}
		list.clear(); System.gc();//释放list所占的内存
		ftpValuesMap.clear(); System.gc();
		return resultList;
	}


	/**
	 * 公司业务部明细数据
	 * @param xlsbrNo
	 * @param minDate日期左端点
	 * @param maxDate日期右端点
	 * @return
	 */
	public List<String[]> getQxppResultListGsyw(String xlsbrNo, String minDate, String maxDate,Integer assessScope,String brNos) {
		List<String[]> resultList=new ArrayList<String[]>();

		Map<String, String> ftpValuesMap = new HashMap<String, String>();

		Integer days = CommonFunctions.daysSubtract(maxDate, minDate);
		//1.获取员工对应的机构信息
		Map<String, String> empInfoMap = new HashMap<String, String>();
		String sql3 = "select emp_no, trim(br_no) from ftp.ftp_emp_info";
		List empList = daoFactory.query1(sql3, null);
		if(empList != null && empList.size() > 0) {
			for(Object object : empList) {
				Object[] obj = (Object[])object;
				empInfoMap.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
			}
		}
		empList.clear(); System.gc();//释放empList所占的内存


		//2.获取日均余额和FTP价格
		String avgbalStr = "";//日均余额查询的字段
		if(assessScope == -1) {
			avgbalStr = "AVERATE_BAL_M";//月度日均余额
		} else if(assessScope == -3) {
			avgbalStr = "AVERATE_BAL_Q";//季度日均余额
		} else if(assessScope == -12) {
			avgbalStr = "AVERATE_BAL_Y";//年度日均余额
		}
		//从TJBB_RESULT表中直接获取日均余额、定价结果信息
		String sql = "select t.*,t1.product_name,t1.cus_name,t1.Opn_date,t1.Mtr_date,t1.BUSINESS_NAME,t1.kmh from (select ac_id,"+avgbalStr+",FTP_PRICE,rate,cust_no,is_zq,prdt_no,bal,cum_no" +
				" from ftp.TJBB_RESULT where trim(CYC_DATE) = '"+maxDate+"'  and " +
				""+avgbalStr+" !=0 and "+avgbalStr+" is not null and br_no "+brNos+" and (substr(kmh,1,4) in ('2001','2002','2006','2011','2014','1306') or (substr(kmh,1,4) in  ('1301','1302','1303','1304','1305','1307','1308') and substr(cust_no,1,1)='2'))) t left join";
		String sql_2 = " (select * from ( select a.ac_id,a.product_name,a.cust_no,a.cus_name,a.Opn_date,a.Mtr_date,a.rate,a.BUSINESS_NAME,a.kmh, row_number() over(partition by ac_id order by WRK_SYS_DATE desc ) as rn " +
				"  from ftp.FTP_QXPP_RESULT a ) where rn=1 " +
				"  ) t1 on t.ac_id=t1.ac_id" ;
		sql+=sql_2;
		List list = daoFactory.query1(sql, null);
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				double avebal = obj[1] == null ? 0.0 : Double.valueOf(obj[1].toString());//日均余额
				double ftp = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());//FTP
				double rate = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());//利率
				double bal = obj[7] == null ? 0.0 : Double.valueOf(obj[7].toString());//FTP
				String custNo = String.valueOf(obj[8]);
				String product_name = String.valueOf(obj[9]==null?"":obj[9].toString());
				String cus_name = String.valueOf(obj[10]==null?"":obj[10].toString());
				String Opn_date = String.valueOf(obj[11]==null?"":obj[11].toString());
				String Mtr_date = String.valueOf(obj[12]==null?"":obj[12].toString());
				String business_name = String.valueOf(obj[13]==null?"":obj[13].toString());
				String kmh = String.valueOf(obj[14]==null?"":obj[14].toString());
				String[] result = new String[17];
				result[0] = empInfoMap.get(custNo);//客户经理所属机构
				if(result[0] == null) {
					continue;
				}
				if(obj[6]==null||"".equals(obj[6])){
					continue;
				}
				result[1] = String.valueOf(obj[6]);//产品
				result[2] = String.valueOf(custNo);//客户经理编号
				result[3] = String.valueOf(avebal);//日均余额
				if(obj[6].toString().startsWith("P1")){
					result[4] = String.valueOf(avebal*(rate-ftp)*days/360);//FTP利润
				}else{
					result[4] = String.valueOf(avebal*(ftp-rate)*days/360);//FTP利润
				}
				result[5] = String.valueOf(obj[0]);//账号
				result[6] = custNo;//客户号
				result[7] = String.valueOf(obj[5]);//展期状态，如果='2'为个人按揭
				result[8] = String.valueOf(bal);//余额

				result[9] = String.valueOf(product_name);//产品名称
				result[10] = String.valueOf(cus_name);//客户名称
				result[11] = String.valueOf(Opn_date);//开户日期
				result[12] = String.valueOf(Mtr_date);//到期日期
				result[13] = String.valueOf(business_name);//业务

				result[14] = String.valueOf(ftp);//ftp价格
				result[15] = String.valueOf(rate);//ftp利率
				result[16] = String.valueOf(kmh);//科目号
				if(avebal!=0){
					resultList.add(result);
				}
			}
		}
		list.clear(); System.gc();//释放list所占的内存
		ftpValuesMap.clear(); System.gc();
		return resultList;
	}



	/**
	  * 以日均余额为基准，获取指定时间范围内账户的开户机构、产品、客户经理、日均余额、FTP利润、余额
	  * @param xlsbrNo
	  * @param minDate统计日期左端点
	  * @param maxDate统计日期右端点
	  * @param assessScope统计维度-1，-3，-12
	  * @return
	  */
	public List<String[]> getQxppResultPrdtList(String xlsbrNo, String minDate, String maxDate, Integer assessScope) {
		List<String[]> resultList=new ArrayList<String[]>();
		String STime0=CommonFunctions.GetCurrentTime();
		List list = new ArrayList();
		Integer days = CommonFunctions.daysSubtract(maxDate, minDate);

		//1.从TJBB_RESULT中获取数据
	    String balStr = "BAL";//余额查询的字段
		String avgbalStr = "AVERATE_BAL_M";//日均余额查询的字段
		if(assessScope == -0) {//旬度
			int leftDay = Integer.valueOf(String.valueOf(minDate).substring(6, 8));//左端点日期
			//通过左端点的日期进行判断上中下旬
			if(leftDay == 1) {//上旬
				avgbalStr = "AVERATE_BAL_SX";//上旬日均余额
			}else if(leftDay == 11) {//中旬
				avgbalStr = "AVERATE_BAL_ZX";//中旬日均余额
			}else {//下旬
				avgbalStr = "AVERATE_BAL_XX";//下旬日均余额
			}
		} else if(assessScope == -1) {
			avgbalStr = "AVERATE_BAL_M";//月度日均余额
		} else if(assessScope == -3) {
			avgbalStr = "AVERATE_BAL_Q";//季度日均余额
		} else if(assessScope == -12) {
			avgbalStr = "AVERATE_BAL_Y";//年度日均余额
		}
		//从TJBB_RESULT表中直接获取日均余额、定价结果信息 ## ls：加入币种
		String sql = "select t.cum_no,t."+avgbalStr+",t.FTP_RATE_M,t.PRDT_NO,t.BR_NO,t."+balStr+",t.CUST_TYPE,t1.ACC_NO"
	            +" from ftp.TJBB_RESULT_PRDT  t  left join ftp.FTP_BUSINESS_PRDT t1 on t.prdt_no = t1.PRDT_NO"
	            +" where trim(t.CYC_DATE) = '"+maxDate+"'"
				//+" and CUR_NO"+curNoSql
				+" and t."+avgbalStr+" !=0 and t."+avgbalStr+" is not null";
		
		list = daoFactory.query1(sql, null);
		String ETime0=CommonFunctions.GetCurrentTime();
		int costTime0=CommonFunctions.GetCostTimeInSecond(STime0, ETime0);
		int CostFen=costTime0/60;
		int CostMiao=costTime0%60;
		System.out.println("TJBB_RESULT中的查询结果"+list.size()+"行记录,耗时"+CostFen+"分"+CostMiao+"秒");


		//2.获取员工对应的机构信息
		Map<String, String> empInfoMap = new HashMap<String, String>();
		String sql3 = "select emp_no, trim(br_no) from ftp.ftp_emp_info";
		List empList = daoFactory.query1(sql3, null);
		if(empList != null && empList.size() > 0) {
			for(Object object : empList) {
				Object[] obj = (Object[])object;
				empInfoMap.put(String.valueOf(obj[0]), String.valueOf(obj[1]));
			}
		}

		//3.循环存入list中
		if(list.size() > 0) {
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				list.set(i, null);//释放list循环过程中，该笔记录的所占内存(在list中该笔记录被使用后)
				if(i%50000==0){
					System.out.println("------------>i="+i);
				}
				
				//将产品编号为空的过滤掉
				if (obj[3] == null || "".equals(obj[3])) {
					continue;
				}
				
				double avebal = obj[1] == null ? 0.0 : Double.valueOf(obj[1].toString());//日均余额
				double cz = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());//日均余额
				double bal = obj[5] == null ? 0.0 : Double.valueOf(obj[5].toString());//FTP
				String[] result = new String[8];
				//result[0] = String.valueOf(obj[4]);//开户机构，从定价结果表中获取
				result[0] = String.valueOf(empInfoMap.get(obj[0])==null?obj[4]:empInfoMap.get(obj[0]));
				result[1] = String.valueOf(obj[3]);//产品
				result[2] = String.valueOf(obj[0]);//客户经理编号
				result[3] = String.valueOf(avebal);//日均余额
				result[4] = String.valueOf(avebal*cz*days/360);//FTP利润
				result[5] = obj[6] == null ? "" : String.valueOf(obj[6]);//客户类型
				result[6] = String.valueOf(bal);//余额
				result[7] =obj[7] == null ? "" : String.valueOf(obj[7]);//科目号
				resultList.add(result);
			}
		}
		list = new ArrayList();list.clear(); System.gc();//释放list所占的内存
		return resultList;
	}
	
	
	/**
	 * 获取某类机构的日均余额、FTP利润
	 * @param ftped_data_successList
	 * @param brNos要汇总的机构字符串，例'1801031009','1801031008'
	 * @return 日均余额、FTP利润
	 * @paramm custType
	 */
	public double[] getQxppValueByBrNo(List<String[]> ftpResultList, String brNos, String custType) {
		double[] returnValue = {0.0, 0.0, 0.0, 0.0,0.0,0.0,0.0, 0.0, 0.0, 0.0,0.0,0.0,0.0, 0.0, 0.0, 0.0,0.0,0.0,0.0, 0.0, 0.0, 0.0};
		Map<String,List<String>> prdtCtgMap = getPrdtCtgMap();
		double rjye_zc = 0.0, rjye_fz = 0.0, ftplr_zc = 0.0, ftplr_fz = 0.0,zc_bal= 0.0,fz_bal=0.0,
				rjye_grhq = 0.0, rjye_grdq = 0.0, rjye_dwhq = 0.0, rjye_dwdq = 0.0,rjye_czx= 0.0,rjye_yhk=0.0,rjye_bzj= 0.0,rjye_yjhk=0.0,
				bal_grhq = 0.0, bal_grdq = 0.0, bal_dwhq = 0.0, bal_dwdq = 0.0,bal_czx= 0.0,bal_yhk=0.0,bal_bzj= 0.0,bal_yjhk=0.0;
		for (String[] result : ftpResultList) {
			boolean isStatic = this.getIsStatic(custType, result[5], result[7]);//记录是否纳入统计，分对公和对私
			if(isStatic) {
				if(result[1].indexOf("P1") != -1 && (brNos.indexOf(result[0])!= -1|| brNos.equals("is not null"))) {
					rjye_zc += Double.valueOf(result[3]);
					ftplr_zc += Double.valueOf(result[4]);
					zc_bal+=Double.valueOf(result[6]);
				}
				if(result[1].indexOf("P2") != -1 && (brNos.indexOf(result[0])!= -1|| brNos.equals("is not null"))) {
					rjye_fz += Double.valueOf(result[3]);
					ftplr_fz += Double.valueOf(result[4]);
					fz_bal+=Double.valueOf(result[6]);
				}

				if((brNos.indexOf(result[0])!= -1|| brNos.equals("is not null"))){
					if(prdtCtgMap.get("grhq").contains(result[1])){
						bal_grhq+=Double.valueOf(result[6]);
						rjye_grhq+=Double.valueOf(result[3]);
					}

					if(prdtCtgMap.get("grdq").contains(result[1])){
						bal_grdq+=Double.valueOf(result[6]);
						rjye_grdq+=Double.valueOf(result[3]);
					}

					if(prdtCtgMap.get("dwhq").contains(result[1])){
						bal_dwhq+=Double.valueOf(result[6]);
						rjye_dwhq+=Double.valueOf(result[3]);
					}

					if(prdtCtgMap.get("dwdq").contains(result[1])){
						bal_dwdq+=Double.valueOf(result[6]);
						rjye_dwdq+=Double.valueOf(result[3]);
					}


					if(prdtCtgMap.get("yhk").contains(result[1])){
						bal_yhk+=Double.valueOf(result[6]);
						rjye_yhk+=Double.valueOf(result[3]);
					}

					if(prdtCtgMap.get("czx").contains(result[1])){
						bal_czx+=Double.valueOf(result[6]);
						rjye_czx+=Double.valueOf(result[3]);
					}

					if(prdtCtgMap.get("yjhk").contains(result[1])){
						bal_yjhk+=Double.valueOf(result[6]);
						rjye_yjhk+=Double.valueOf(result[3]);
					}

					if(prdtCtgMap.get("bzj").contains(result[1])){
						bal_bzj+=Double.valueOf(result[6]);
						rjye_bzj+=Double.valueOf(result[3]);

					}
				}
			}
		}
		returnValue[0] = rjye_zc;
		returnValue[1] = ftplr_zc;
		returnValue[2] = rjye_fz;
		returnValue[3] = ftplr_fz;
		returnValue[4] = zc_bal;
		returnValue[5] = fz_bal;

		returnValue[6] = bal_grhq;
		returnValue[7] = bal_grdq;
		returnValue[8] = bal_dwhq;
		returnValue[9] = bal_dwdq;
		returnValue[10] = bal_czx;
		returnValue[11] = bal_yhk;
		returnValue[12] = bal_yjhk;
		returnValue[13] = bal_bzj;

		returnValue[14] = rjye_grhq;
		returnValue[15] = rjye_grdq;
		returnValue[16] = rjye_dwhq;
		returnValue[17] = rjye_dwdq;
		returnValue[18] = rjye_czx;
		returnValue[19] = rjye_yhk;
		returnValue[20] = rjye_yjhk;
		returnValue[21] = rjye_bzj;
		return returnValue;
	}
	
	
	
	/**
	 * 全行所有机构FTP利润汇总表，将ftpResultList中的数据，按照机构放到对应的map中
	 * @param ftped_data_successList
	 * @param brMstList 要获取数据值到界面直接显示的机构数据表对象列表(2、1、0级机构都支持)
	 * @paramm telNo
	 * @paramm custType
	 * @return map
	 */
	public Map<String,Double[]> getQxppValueMap_qhsyjgftphzb(List<String[]> ftpResultList, List<BrMst> brMstList, String telNo, String custType) {
		//key<机构号+资产负债类型(1负债2资产)>
		Map<String,Double[]> QxppValue_map=new HashMap<String,Double[]>();
		Map<String,Double> rjye_map=new HashMap<String,Double>();
		Map<String,Double> bal_map=new HashMap<String,Double>();//余额map
		Map<String,Double> ftplr_map=new HashMap<String,Double>();

        Map<String,Double> ftplr_dg_map=new HashMap<String,Double>();//对公
        Map<String,Double> ftplr_ds_map=new HashMap<String,Double>();//对私

		Map<String,List<String>> prdtCtgMap = getPrdtCtgMap();

		Map<String,Double> grhqrjye_map=new HashMap<String,Double>();//个人活期日均余额map
		Map<String,Double> grdqrjye_map=new HashMap<String,Double>();//个人定期日均余额map
		Map<String,Double> dwhqrjye_map=new HashMap<String,Double>();//单位活期日均余额map
		Map<String,Double> dwdqrjye_map=new HashMap<String,Double>();//单位定期日均余额map
		Map<String,Double> yhkrjye_map=new HashMap<String,Double>();//银行卡日均余额map
		Map<String,Double> czxrjye_map=new HashMap<String,Double>();//财政性存款日均余额map
		Map<String,Double> yjhkrjye_map=new HashMap<String,Double>();//应解汇款日均余额map
		Map<String,Double> bzjrjye_map=new HashMap<String,Double>();//保证金日均余额map

		Map<String,Double> grhqbal_map=new HashMap<String,Double>();//个人活期余额map
		Map<String,Double> grdqbal_map=new HashMap<String,Double>();//个人定期余额map
		Map<String,Double> dwhqbal_map=new HashMap<String,Double>();//单位活期余额map
		Map<String,Double> dwdqbal_map=new HashMap<String,Double>();//单位定期余额map
		Map<String,Double> yhkbal_map=new HashMap<String,Double>();//银行卡余额map
		Map<String,Double> czxbal_map=new HashMap<String,Double>();//财政性存款余额map
		Map<String,Double> yjhkbal_map=new HashMap<String,Double>();//应解汇款余额map
		Map<String,Double> bzjbal_map=new HashMap<String,Double>();//保证金余额map

		Map<String,Double> grdkftplr_map=new HashMap<String,Double>();//个人贷款ftp利润map
		Map<String,Double> gsdkftplr_map=new HashMap<String,Double>();//公司贷款ftp利润map
		Map<String,Double> grhqftplr_map=new HashMap<String,Double>();//个人活期ftp利润map
		Map<String,Double> grdqftplr_map=new HashMap<String,Double>();//个人定期ftp利润map
		Map<String,Double> dwhqftplr_map=new HashMap<String,Double>();//单位活期ftp利润map
		Map<String,Double> dwdqftplr_map=new HashMap<String,Double>();//单位定期ftp利润map
		Map<String,Double> yhkftplr_map=new HashMap<String,Double>();//银行卡ftp利润map
		Map<String,Double> czxftplr_map=new HashMap<String,Double>();//财政性存款ftp利润map
		Map<String,Double> yjhkftplr_map=new HashMap<String,Double>();//应解汇款ftp利润map
		Map<String,Double> bzjftplr_map=new HashMap<String,Double>();//保证金ftp利润map

		for (String[] result : ftpResultList) {
			boolean isStatic = this.getIsStatic(custType, result[5], result[7]);//记录是否纳入统计，分对公和对私
			if (isStatic) {
				double rjye = Double.valueOf(result[3]);
				double bal = Double.valueOf(result[6]);
				double ftplr = Double.valueOf(result[4]);
				//日均余额
				if (rjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
					rjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
				} else {
					rjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + rjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
				}
				//FTP利润
				if (ftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
					ftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
				} else {
					ftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + ftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
				}
				//余额
				if (bal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
					bal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
				} else {
					bal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + bal_map.get(result[0] + "-" + result[1].substring(1, 2)));
				}

				if (prdtCtgMap.get("grhq").contains(result[1])) {
					if (grhqrjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						grhqrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
					} else {
						grhqrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + grhqrjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (grhqbal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						grhqbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
					} else {
						grhqbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + grhqbal_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (grhqftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						grhqftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						grhqftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + grhqftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("grdq").contains(result[1])) {
					if (grdqrjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						grdqrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
					} else {
						grdqrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + grdqrjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (grdqbal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						grdqbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
					} else {
						grdqbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + grdqbal_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}


					if (grdqftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						grdqftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						grdqftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + grdqftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("dwhq").contains(result[1])) {
					if (dwhqrjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						dwhqrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
					} else {
						dwhqrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + dwhqrjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (dwhqbal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						dwhqbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
					} else {
						dwhqbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + dwhqbal_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (dwhqftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						dwhqftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						dwhqftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + dwhqftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("dwdq").contains(result[1])) {
					if (dwdqrjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						dwdqrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
					} else {
						dwdqrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + dwdqrjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (dwdqbal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						dwdqbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
					} else {
						dwdqbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + dwdqbal_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (dwdqftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						dwdqftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						dwdqftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + dwdqftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}


				if (prdtCtgMap.get("yhk").contains(result[1])) {
					if (yhkrjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						yhkrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
					} else {
						yhkrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + yhkrjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (yhkbal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						yhkbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
					} else {
						yhkbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + yhkbal_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (yhkftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						yhkftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						yhkftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + yhkftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

				}

				if (prdtCtgMap.get("czx").contains(result[1])) {
					if (czxrjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						czxrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
					} else {
						czxrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + czxrjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (czxbal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						czxbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
					} else {
						czxbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + czxbal_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (czxftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						czxftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						czxftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + czxftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("yjhk").contains(result[1])) {
					if (yjhkrjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						yjhkrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
					} else {
						yjhkrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + yjhkrjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (yjhkbal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						yjhkbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
					} else {
						yjhkbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + yjhkbal_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}


					if (yjhkftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						yjhkftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						yjhkftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + yjhkftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}

				if (prdtCtgMap.get("bzj").contains(result[1])) {
					if (bzjrjye_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						bzjrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye);
					} else {
						bzjrjye_map.put(result[0] + "-" + result[1].substring(1, 2), rjye + bzjrjye_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (bzjbal_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						bzjbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal);
					} else {
						bzjbal_map.put(result[0] + "-" + result[1].substring(1, 2), bal + bzjbal_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}

					if (bzjftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						bzjftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						bzjftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + bzjftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}

				boolean isds = this.getIsStatic("1", result[5], result[7]);//对私统计
				if (isds) {
					if (ftplr_ds_map.get(result[0]) == null) {
						ftplr_ds_map.put(result[0], ftplr);
					} else {
						ftplr_ds_map.put(result[0], ftplr + ftplr_ds_map.get(result[0]));
					}
				}

				boolean isdg = this.getIsStatic("2", result[5], result[7]);//对公统计
				if (isdg) {
					//FTP利润
					if (ftplr_dg_map.get(result[0]) == null) {
						ftplr_dg_map.put(result[0], ftplr);
					} else {
						ftplr_dg_map.put(result[0], ftplr + ftplr_dg_map.get(result[0]));
					}
				}

				boolean isdsdk = this.getIsStaticDk("1", result[5], result[7]);//对私统计
				if (isdsdk) {
					if (grdkftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						grdkftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						grdkftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + grdkftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}
				boolean isdgdk = this.getIsStaticDk("2", result[5], result[7]);//对公统计
				if (isdgdk) {
					//FTP利润
					if (gsdkftplr_map.get(result[0] + "-" + result[1].substring(1, 2)) == null) {
						gsdkftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr);
					} else {
						gsdkftplr_map.put(result[0] + "-" + result[1].substring(1, 2), ftplr + gsdkftplr_map.get(result[0] + "-" + result[1].substring(1, 2)));
					}
				}
			}
		}

            for(BrMst br_mst:brMstList){
			String brNos=this.getAllChildBrByNotIn(br_mst.getBrNo(), br_mst.getManageLvl(), telNo);
			String[] br_no_s=null;
			br_no_s = brNos.split(",");
			double[][] returnValue = new double[29][2];//[i][0]为资产的各计算数据项，[i][1]为负债的各计算数据项
			for(String br_no:br_no_s){
				//生成的brNos字符串，br_no带单引号
				br_no = br_no.substring(1, br_no.length() -1);
				returnValue[0][0]+=rjye_map.get(br_no+"-1")==null?0.0:rjye_map.get(br_no+"-1");
				returnValue[1][0]+=ftplr_map.get(br_no+"-1")==null?0.0:ftplr_map.get(br_no+"-1");
				returnValue[2][0]+=bal_map.get(br_no+"-1")==null?0.0:bal_map.get(br_no+"-1");
				returnValue[3][0]+=grdkftplr_map.get(br_no+"-1")==null?0.0:grdkftplr_map.get(br_no+"-1");
				returnValue[4][0]+=gsdkftplr_map.get(br_no+"-1")==null?0.0:gsdkftplr_map.get(br_no+"-1");

				returnValue[0][1]+=rjye_map.get(br_no+"-2")==null?0.0:rjye_map.get(br_no+"-2");
				returnValue[1][1]+=ftplr_map.get(br_no+"-2")==null?0.0:ftplr_map.get(br_no+"-2");
				returnValue[2][1]+=bal_map.get(br_no+"-2")==null?0.0:bal_map.get(br_no+"-2");
				returnValue[3][1]+=grhqbal_map.get(br_no+"-2")==null?0.0:grhqbal_map.get(br_no+"-2");
				returnValue[4][1]+=grdqbal_map.get(br_no+"-2")==null?0.0:grdqbal_map.get(br_no+"-2");
				returnValue[5][1]+=dwhqbal_map.get(br_no+"-2")==null?0.0:dwhqbal_map.get(br_no+"-2");
				returnValue[6][1]+=dwdqbal_map.get(br_no+"-2")==null?0.0:dwdqbal_map.get(br_no+"-2");
				returnValue[7][1]+=czxbal_map.get(br_no+"-2")==null?0.0:czxbal_map.get(br_no+"-2");
				returnValue[8][1]+=yhkbal_map.get(br_no+"-2")==null?0.0:yhkbal_map.get(br_no+"-2");
				returnValue[9][1]+=yjhkbal_map.get(br_no+"-2")==null?0.0:yjhkbal_map.get(br_no+"-2");
				returnValue[10][1]+=bzjbal_map.get(br_no+"-2")==null?0.0:bzjbal_map.get(br_no+"-2");

				returnValue[11][1]+=grhqrjye_map.get(br_no+"-2")==null?0.0:grhqrjye_map.get(br_no+"-2");
				returnValue[12][1]+=grdqrjye_map.get(br_no+"-2")==null?0.0:grdqrjye_map.get(br_no+"-2");
				returnValue[13][1]+=dwhqrjye_map.get(br_no+"-2")==null?0.0:dwhqrjye_map.get(br_no+"-2");
				returnValue[14][1]+=dwdqrjye_map.get(br_no+"-2")==null?0.0:dwdqrjye_map.get(br_no+"-2");
				returnValue[15][1]+=czxrjye_map.get(br_no+"-2")==null?0.0:czxrjye_map.get(br_no+"-2");
				returnValue[16][1]+=yhkrjye_map.get(br_no+"-2")==null?0.0:yhkrjye_map.get(br_no+"-2");
				returnValue[17][1]+=yjhkrjye_map.get(br_no+"-2")==null?0.0:yjhkrjye_map.get(br_no+"-2");
				returnValue[18][1]+=bzjrjye_map.get(br_no+"-2")==null?0.0:bzjrjye_map.get(br_no+"-2");
                returnValue[19][1]+=ftplr_ds_map.get(br_no)==null?0.0:ftplr_ds_map.get(br_no);
                returnValue[20][1]+=ftplr_dg_map.get(br_no)==null?0.0:ftplr_dg_map.get(br_no);

				returnValue[21][1]+=grhqftplr_map.get(br_no+"-2")==null?0.0:grhqftplr_map.get(br_no+"-2");
				returnValue[22][1]+=grdqftplr_map.get(br_no+"-2")==null?0.0:grdqftplr_map.get(br_no+"-2");
				returnValue[23][1]+=dwhqftplr_map.get(br_no+"-2")==null?0.0:dwhqftplr_map.get(br_no+"-2");
				returnValue[24][1]+=dwdqftplr_map.get(br_no+"-2")==null?0.0:dwdqftplr_map.get(br_no+"-2");
				returnValue[25][1]+=czxftplr_map.get(br_no+"-2")==null?0.0:czxftplr_map.get(br_no+"-2");
				returnValue[26][1]+=yhkftplr_map.get(br_no+"-2")==null?0.0:yhkftplr_map.get(br_no+"-2");
				returnValue[27][1]+=yjhkftplr_map.get(br_no+"-2")==null?0.0:yjhkftplr_map.get(br_no+"-2");
				returnValue[28][1]+=bzjftplr_map.get(br_no+"-2")==null?0.0:bzjftplr_map.get(br_no+"-2");
            }
			QxppValue_map.put(br_mst.getBrNo()+"-1", new Double[]{returnValue[0][0],returnValue[1][0],returnValue[2][0],
					returnValue[3][0],returnValue[4][0]
			});
			QxppValue_map.put(br_mst.getBrNo()+"-2", new Double[]{returnValue[0][1],returnValue[1][1],returnValue[2][1]
			        ,returnValue[3][1],returnValue[4][1],returnValue[5][1]
					,returnValue[6][1],returnValue[7][1],returnValue[8][1]
					,returnValue[9][1],returnValue[10][1],returnValue[11][1]
					,returnValue[12][1],returnValue[13][1],returnValue[14][1]
					,returnValue[15][1],returnValue[16][1],returnValue[17][1]
					,returnValue[18][1],returnValue[19][1],returnValue[20][1]
					,returnValue[21][1],returnValue[22][1],returnValue[23][1]
					,returnValue[24][1],returnValue[25][1],returnValue[26][1]
					,returnValue[27][1],returnValue[28][1]
			});
		}
		return QxppValue_map;
	}
	
	/**
	 * 业务条线盈利分析---按产品，将ftped_data_successList中的数据，根据要汇总的机构按照产品放到对应的map中
	 * @param ftped_data_successList
	 * @param date时间段右端点
	 * @param daysSubtract总的日期数
	 * @param xlsBrNo县联社
	 * @param brNos要汇总的机构字符串，例'1801031009','1801031008'
	 * @param custType客户类型，0所有，2对公，1对私
	 * @return map
	 */
	public Map<String,Double[]> getQxppValueMap_ywtxylfx(List<String[]> ftped_data_successList, String xlsBrNo, String brNos, String custType) {
		//key<产品号>
		Map<String,Double[]> QxppValue_map=new HashMap<String,Double[]>();
		Map<String,Double> rjye_map=new HashMap<String,Double>();
		Map<String,Double> ftplr_map=new HashMap<String,Double>();
		Map<String,Double> bal_map=new HashMap<String,Double>(); //余额map
		
		//按产品编号放到map中
		for (String[] result : ftped_data_successList) {
			boolean isStatic = this.getIsStatic(custType, result[5], result[7]);//记录是否纳入统计，分对公和对私
			if(brNos.indexOf(result[0])!= -1 && isStatic) {//如果要汇总的机构中包含result[0]
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[6]); //余额
				//日均余额
				if(rjye_map.get(result[1])==null){
					rjye_map.put(result[1], rjye);
				}else{
					rjye_map.put(result[1], rjye+rjye_map.get(result[1]));
				}
				//FTP利润
				if(ftplr_map.get(result[1])==null){
					ftplr_map.put(result[1], ftplr);
				}else{
					ftplr_map.put(result[1], ftplr+ftplr_map.get(result[1]));
				}
				//余额
				if(bal_map.get(result[1])==null){
					bal_map.put(result[1], bal);
				}else{
					bal_map.put(result[1], bal+bal_map.get(result[1]));
				}
			}
		}
		//该县联社对应的所有产品
		String hsql = "from FtpBusinessStaticDivide t ";
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = daoFactory.query(hsql, null);
		double[] returnValue = new double[3];
		for(FtpBusinessStaticDivide ftpBusinessStaticDivide:ftpBusinessStaticDivideList){
			returnValue[0]=rjye_map.get(ftpBusinessStaticDivide.getProductNo())==null?0.0:rjye_map.get(ftpBusinessStaticDivide.getProductNo());
			returnValue[1]=ftplr_map.get(ftpBusinessStaticDivide.getProductNo())==null?0.0:ftplr_map.get(ftpBusinessStaticDivide.getProductNo());
			returnValue[2]=bal_map.get(ftpBusinessStaticDivide.getProductNo())==null?0.0:bal_map.get(ftpBusinessStaticDivide.getProductNo());
			QxppValue_map.put(ftpBusinessStaticDivide.getProductNo(), new Double[]{returnValue[0],returnValue[1],returnValue[2]});
		}
		return QxppValue_map;
	}
	
	/**
	 * 产品分机构利润表，将ftpResultList中的数据，按照产品-机构放到对应的map中
	 * @param ftped_data_successList
	 * @param xlsBrNo县联社
	 * @param brMstList 要获取数据值到界面直接显示的机构数据表对象列表(2、1、0级机构都支持)
	 * @param custType客户类型，0所有，2对公，1对私
	 * @param telNo操作员编号
	 * @return map
	 */
	public Map<String,Double[]> getQxppValueMap_cpfjglrb(List<String[]> ftpResultList, String xlsBrNo, List<BrMst> brMstList, String custType, String telNo) {
		//key<产品号-机构号>
		Map<String,Double[]> QxppValue_map=new HashMap<String,Double[]>();
		Map<String,Double> rjye_map=new HashMap<String,Double>();
		Map<String,Double> ftplr_map=new HashMap<String,Double>();
		Map<String,Double> bal_map=new HashMap<String,Double>();
		
		for (String[] result : ftpResultList) {
			boolean isStatic = this.getIsStatic(custType, result[5], result[7]);//记录是否纳入统计，分对公和对私
			if(isStatic) {
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[6]);
				//日均余额
				if(rjye_map.get(result[1]+"-"+result[0])==null){
					rjye_map.put(result[1]+"-"+result[0], rjye);
				}else{
					rjye_map.put(result[1]+"-"+result[0], rjye+rjye_map.get(result[1]+"-"+result[0]));
				}
				//FTP利润
				if(ftplr_map.get(result[1]+"-"+result[0])==null){
					ftplr_map.put(result[1]+"-"+result[0], ftplr);
				}else{
					ftplr_map.put(result[1]+"-"+result[0], ftplr+ftplr_map.get(result[1]+"-"+result[0]));
				}

				//FTP余额
				if(bal_map.get(result[1]+"-"+result[0])==null){
					bal_map.put(result[1]+"-"+result[0], bal);
				}else{
					bal_map.put(result[1]+"-"+result[0], bal+bal_map.get(result[1]+"-"+result[0]));
				}
			}
		}

		//该县联社对应的所有产品
		String hsql = "from FtpBusinessStaticDivide t ";
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = daoFactory.query(hsql, null);
		
		//循环机构
		for(BrMst br_mst:brMstList){
			String brNos=this.getAllChildBrByNotIn(br_mst.getBrNo(), br_mst.getManageLvl(), telNo);
			String[] br_no_s=null;
			br_no_s = brNos.split(",");
			for(FtpBusinessStaticDivide ftpProductMethodRel:ftpBusinessStaticDivideList){
				double[] returnValue = new double[3];
				for(String br_no:br_no_s){
					//生成的brNos字符串，br_no带单引号
					br_no = br_no.substring(1, br_no.length() -1);
					returnValue[0]+=rjye_map.get(ftpProductMethodRel.getProductNo()+"-"+br_no)==null?0.0:rjye_map.get(ftpProductMethodRel.getProductNo()+"-"+br_no);
					returnValue[1]+=ftplr_map.get(ftpProductMethodRel.getProductNo()+"-"+br_no)==null?0.0:ftplr_map.get(ftpProductMethodRel.getProductNo()+"-"+br_no);
					returnValue[2]+=bal_map.get(ftpProductMethodRel.getProductNo()+"-"+br_no)==null?0.0:bal_map.get(ftpProductMethodRel.getProductNo()+"-"+br_no);
				}
				QxppValue_map.put(ftpProductMethodRel.getProductNo()+"-"+br_mst.getBrNo(), new Double[]{returnValue[0],returnValue[1],returnValue[2]});
			}
		}
		return QxppValue_map;
	}
	/**
	 * 产品分机构利润表-汇总某一个机构的数据，将ftpResultList中的数据，按照产品-机构放到对应的map中
	 * @param ftped_data_successList
	 * @param xlsBrNo县联社
	 * @param brNos
	 * @param custType客户类型，0所有，2对公，1对私
	 * @param prdtNos
	 * @return map
	 */
	public Double[] getQxppValue_cpfjglrb(List<String[]> ftpResultList, String brNos, String custType, String prdtNos) {
		Double[] resultValue = new Double[3];
		double rjye = 0;
		double ftplr = 0;
		double bal = 0;
		
		for (String[] result : ftpResultList) {
			boolean isStatic = this.getIsStatic(custType, result[5], result[7]);//记录是否纳入统计，分对公和对私
			if(isStatic) {
				if(prdtNos.indexOf(result[1]) != -1 && (brNos.indexOf(result[0])!= -1|| brNos.equals("is not null"))) {
					rjye += Double.valueOf(result[3]);
				    ftplr += Double.valueOf(result[4]);
					bal += Double.valueOf(result[6]);
				}
			}
		}
		resultValue[0] = rjye;
		resultValue[1] = ftplr;
		resultValue[2] = bal;
		return resultValue;
	}
	/**
	 * 个人按揭机构利润表，将ftpResultList中的数据，按照机构放到对应的map中，是否展期为2的
	 * @param ftped_data_successList
	 * @param xlsBrNo县联社
	 * @param brMstList 要获取数据值到界面直接显示的机构数据表对象列表(2、1、0级机构都支持)
	 * @param custType客户类型，0所有，2对公，1对私
	 * @param telNo操作员编号
	 * @return map
	 */
	public Map<String,Double[]> getQxppValueMap_grajjglrb(List<String[]> ftpResultList, String xlsBrNo, List<BrMst> brMstList, String custType, String telNo) {
		//key<产品号-机构号>
		Map<String,Double[]> QxppValue_map=new HashMap<String,Double[]>();
		Map<String,Double> rjye_map=new HashMap<String,Double>();
		Map<String,Double> ftplr_map=new HashMap<String,Double>();
		Map<String,Double> bal_map=new HashMap<String,Double>();

		for (String[] result : ftpResultList) {
			boolean isStatic = this.getIsStatic(custType, result[6], result[9]);//记录是否纳入统计，分对公和对私
			if(isStatic && result[7].equals("2")) {//是否展期为2的
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[8]);
				//日均余额
				if(rjye_map.get(result[0])==null){
					rjye_map.put(result[0], rjye);
				}else{
					rjye_map.put(result[0], rjye+rjye_map.get(result[0]));
				}
				//FTP利润
				if(ftplr_map.get(result[0])==null){
					ftplr_map.put(result[0], ftplr);
				}else{
					ftplr_map.put(result[0], ftplr+ftplr_map.get(result[0]));
				}

				//FTP余额
				if(bal_map.get(result[0])==null){
					bal_map.put(result[0], bal);
				}else{
					bal_map.put(result[0], bal+bal_map.get(result[0]));
				}
			}
		}
		for(BrMst br_mst:brMstList){
			String brNos=this.getAllChildBrByNotIn(br_mst.getBrNo(), br_mst.getManageLvl(), telNo);
			String[] br_no_s=null;
			br_no_s = brNos.split(",");
			double[] returnValue = new double[3];
			for(String br_no:br_no_s){
				//生成的brNos字符串，br_no带单引号
				br_no = br_no.substring(1, br_no.length() -1);
				returnValue[0]+=rjye_map.get(br_no)==null?0.0:rjye_map.get(br_no);
				returnValue[1]+=ftplr_map.get(br_no)==null?0.0:ftplr_map.get(br_no);
				returnValue[2]+=bal_map.get(br_no)==null?0.0:bal_map.get(br_no);
			}
			QxppValue_map.put(br_mst.getBrNo(), new Double[]{returnValue[0],returnValue[1],returnValue[2]});
		}
		return QxppValue_map;
	}
	
	/**
	 * 个人按揭机构利润表-某个机构的汇总，是否展期为2的
	 * @param ftped_data_successList
	 * @param brNos要汇总的机构字符串，例'1801031009','1801031008'
	 * @return 日均余额、FTP利润
	 * @paramm custType
	 */
	public double[] getQxppValueMap_grajjglrbhz(List<String[]> ftpResultList, String brNos, String custType) {
		double[] returnValue = {0.0, 0.0,0.0};
		double rjye = 0.0, ftplr = 0.0,bal=0;
		for (String[] result : ftpResultList) {
			boolean isStatic = this.getIsStatic(custType, result[6], result[9]);//记录是否纳入统计，分对公和对私
			if(isStatic) {
				if(result[7].equals("2") && (brNos.indexOf(result[0])!= -1|| brNos.equals("is not null"))) {
					rjye += Double.valueOf(result[3]);
					ftplr += Double.valueOf(result[4]);
					bal += Double.valueOf(result[8]);
				}
			}
		}
		returnValue[0] = rjye;
		returnValue[1] = ftplr;
		returnValue[2] = bal;
		return returnValue;
	}
	/**
	 * 将ftpResultList中的数据，按照客户经理-类型(ye/lr余额或者利润)放到对应的map中
	 * @param ftped_data_successList
	 * @param xlsBrNo县联社
	 * @param brMstList 要获取数据值到界面直接显示的机构数据表对象列表(2、1、0级机构都支持)
	 * @param custType客户类型，0所有，2对公，1对私
	 * @param telNo操作员编号
	 * @return map
	 */
	public Map<String, Double> getQxppValueMap_khjl(List<String[]> ftpResultList, String prdtNos, String custType) {
		Map<String, Double> QxppValue_map = new HashMap<String, Double>();//key:客户经理编号-类型(ye/lr余额或者利润)
		//从结果集中获取某些客户经理的数据，根据客户经理编号汇总到map中
		for (String[] result : ftpResultList) {
			boolean isStatic = this.getIsStatic(custType, result[5], result[7]);//记录是否纳入统计，分对公和对私
			if(prdtNos.indexOf(result[1])!=-1 && isStatic) {//统计对应的产品号
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[6]);

				//日均余额
				if(QxppValue_map.get(result[2]+"-ye")==null){
					QxppValue_map.put(result[2]+"-ye", rjye);
				}else{
					QxppValue_map.put(result[2]+"-ye", rjye+QxppValue_map.get(result[2]+"-ye"));
				}
				//FTP利润
				if(QxppValue_map.get(result[2]+"-lr")==null){
					QxppValue_map.put(result[2]+"-lr", ftplr);
				}else{
					QxppValue_map.put(result[2]+"-lr", ftplr+QxppValue_map.get(result[2]+"-lr"));
				}

				//FTP余额
				if(QxppValue_map.get(result[2]+"-bal")==null){
					QxppValue_map.put(result[2]+"-bal", bal);
				}else{
					QxppValue_map.put(result[2]+"-bal", bal+QxppValue_map.get(result[2]+"-bal"));
				}
			}
		}
		return QxppValue_map;
	}
	/**
	 * 将ftpResultList中的数据，对某个客户经理按照产品号放到对应的map中
	 * @param ftped_data_successList
	 * @param xlsBrNo县联社
	 * @param brMstList 要获取数据值到界面直接显示的机构数据表对象列表(2、1、0级机构都支持)
	 * @param custType客户类型，0所有，2对公，1对私
	 * @param telNo操作员编号
	 * @return map
	 */
	public Map<String, Double> getQxppValueMap_khjlfcp(List<String[]> ftpResultsList, String empNo, String custType) {
		Map<String, Double> QxppValue_map = new HashMap<String, Double>();//key:产品号
		//从结果集中获取某个客户经理的数据，根据产品号汇总到map中
		for (String[] result : ftpResultsList) {
			boolean isStatic = this.getIsStatic(custType, result[5], result[7]);//记录是否纳入统计，分对公和对私
			if(empNo.equals(result[2]) && isStatic) {//与要统计的客户经理号相同
				double rjye = Double.valueOf(result[3]);
				double ftplr = Double.valueOf(result[4]);
				double bal = Double.valueOf(result[6]);

				//日均余额
				if(QxppValue_map.get(result[1]+"-ye")==null){
					QxppValue_map.put(result[1]+"-ye", rjye);
				}else{
					QxppValue_map.put(result[1]+"-ye", rjye+QxppValue_map.get(result[1]+"-ye"));
				}
				//FTP利润
				if(QxppValue_map.get(result[1]+"-lr")==null){
					QxppValue_map.put(result[1]+"-lr", ftplr);
				}else{
					QxppValue_map.put(result[1]+"-lr", ftplr+QxppValue_map.get(result[1]+"-lr"));
				}

				if(QxppValue_map.get(result[1]+"-bal")==null){
					QxppValue_map.put(result[1]+"-bal", bal);
				}else{
					QxppValue_map.put(result[1]+"-bal", bal+QxppValue_map.get(result[1]+"-bal"));
				}
			}
		}
		return QxppValue_map;
	}
	/**
	 * 根据统计大类获取产品大类，获取统计大类下所有资产负债类的产品
	 * @param staticNo
	 * @return
	 */
	public String[][] getPrdtCtgNos(String businessNo, String brNo) {
		String[][] prdtCtgNos = null;
		String sql = "select distinct Product_ctg_no, Product_ctg_name" +
		" from ftp.FTP_business_static_divide" +
		" where business_no='"+businessNo+"'"+
		" order by Product_ctg_no";
		List list = daoFactory.query1(sql, null);
		if(list != null && list.size() > 0) {
			prdtCtgNos = new String[list.size()][2];
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				if(obj[0] == null) {
					continue;
				}
				prdtCtgNos[i][0] = obj[0].toString();
				prdtCtgNos[i][1] = obj[1].toString();
			}
		}
		return prdtCtgNos;
	}
	
	/**
	 * 获取该机构下的所有1级机构+本身(如果是1级机构)
	 * @param brNo
	 * @return
	 */
	public List<BrMst> getBrLvl1List(String brNo, String telNo) {
		List<BrMst> brMstList = brMstMap.get(telNo);//获取该操作员所辖的机构

		if(brMstList == null || brMstList.size() == 0) {
			String hsql = "from BrMst where (brNo ='"+brNo+"' or superBrNo = '"+brNo+"') and manageLvl ='1' order by brNo";
			brMstList = daoFactory.query(hsql, null);
		}
		
		return brMstList;
	}
	
	/**
	 * 根据机构编号，获取所有的下级机构+本身(不包括最高级别2的机构)，以,分隔
	 * @param br_no
	 * @return
	 */
	public String getAllChildBr(String br_no, String br_level, String tel_no) {
		String br_nos="";
		if("3".equals(br_level)){
			br_nos="is not null";//like效率太慢，考虑总行 单独到每个项目取数处具体处理:比like快，和不等于 或 is not null差不多。
		}else if("2".equals(br_level)){
			br_nos+="'"+br_no+"',";
			String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
			List br_noList0=daoFactory.query1(sql, null);
			for(Object obj0:br_noList0){
				br_nos+="'"+obj0.toString()+"',";
				String sql1="select br_no from ftp.br_mst where super_br_no='"+obj0+"'";
				List br_noList01=daoFactory.query1(sql1, null);
				for(Object obj01:br_noList01){
					br_nos+="'"+obj01.toString()+"',";
				}
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
			br_nos="in ("+br_nos+")";
		}else if("1".equals(br_level)){
			br_nos="'"+br_no+"',";//先把自己本身加进去
			String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
			List br_noList=daoFactory.query1(sql, null);
			for(Object obj:br_noList){				
				br_nos+="'"+obj.toString()+"',";		
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
			br_nos="in ("+br_nos+")";
		}else{
			br_nos="'"+br_no+"'";
			br_nos="in ("+br_nos+")";
		}

		return br_nos;
	}


	/**
	 * 根据机构编号，获取所有的下级机构+本身(不包括最高级别2的机构)，以,分隔
	 * @param br_no
	 * @return
	 */
	public String getAllChildBrByNotIn(String br_no, String br_level, String tel_no) {
		String br_nos="";
		if("3".equals(br_level)){
			br_nos="is not null";//like效率太慢，考虑总行 单独到每个项目取数处具体处理:比like快，和不等于 或 is not null差不多。
		}else if("2".equals(br_level)){
			br_nos+="'"+br_no+"',";
			String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
			List br_noList0=daoFactory.query1(sql, null);
			for(Object obj0:br_noList0){
				br_nos+="'"+obj0.toString()+"',";
				String sql1="select br_no from ftp.br_mst where super_br_no='"+obj0+"'";
				List br_noList01=daoFactory.query1(sql1, null);
				for(Object obj01:br_noList01){
					br_nos+="'"+obj01.toString()+"',";
				}
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
		}else if("1".equals(br_level)){
			br_nos="'"+br_no+"',";//先把自己本身加进去
			String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
			List br_noList=daoFactory.query1(sql, null);
			for(Object obj:br_noList){
				br_nos+="'"+obj.toString()+"',";
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
		}else{
			br_nos="'"+br_no+"'";
		}
		return br_nos;
	}

	/**
	 * 根据机构编号，获取所有的下级机构+本身(不包括最高级别2的机构)，以,分隔
	 * @param br_no
	 * @return
	 */
	public String getAllChildBrAllbrno(String br_no, String br_level, String tel_no) {
		String br_nos="";
		if("3".equals(br_level)){
			br_nos+="'"+br_no+"',";
			String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
			List br_noList0=daoFactory.query1(sql, null);
			for(Object obj0:br_noList0){
				String sql1="select br_no from ftp.br_mst where super_br_no='"+obj0+"'";
				List br_noList01=daoFactory.query1(sql1, null);
				for(Object obj01:br_noList01){
					br_nos+="'"+obj01+"',";
					String sql2="select br_no from ftp.br_mst where super_br_no='"+obj01+"'";
					List br_noList02=daoFactory.query1(sql2, null);
					for(Object obj02:br_noList02){
						br_nos+="'"+obj02.toString()+"',";
					}
				}
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
			br_nos="in ("+br_nos+")";
		}else if("2".equals(br_level)){
			br_nos+="'"+br_no+"',";
			String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
			List br_noList0=daoFactory.query1(sql, null);
			for(Object obj0:br_noList0){
				br_nos+="'"+obj0+"',";
				String sql1="select br_no from ftp.br_mst where super_br_no='"+obj0+"'";
				List br_noList01=daoFactory.query1(sql1, null);
				for(Object obj01:br_noList01){
					br_nos+="'"+obj01.toString()+"',";
				}
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
			br_nos="in ("+br_nos+")";
		}else if("1".equals(br_level)){
			br_nos="'"+br_no+"',";//先把自己本身加进去
			String sql="select br_no from ftp.br_mst where super_br_no='"+br_no+"'";
			List br_noList=daoFactory.query1(sql, null);
			for(Object obj:br_noList){
				br_nos+="'"+obj.toString()+"',";
			}
			br_nos=br_nos.substring(0,br_nos.length()-1);
			br_nos="in ("+br_nos+")";
		}else{
			br_nos="'"+br_no+"'";
			br_nos="in ("+br_nos+")";
		}

		return br_nos;
	}

	
	/**
	 * 根据最大日期和统计维度获取最小日期
	 * @param maxDate
	 * @param assessScope-1月度-3季度-12年度
	 * @return
	 */
	public String getMinDate(String maxDate,Integer assessScope) {
		int nowMonth = Integer.valueOf(String.valueOf(maxDate).substring(4, 6));//当前月
		if(assessScope == -3) {//季度
			if (nowMonth >= 1 && nowMonth <= 3)
				assessScope = 0 - nowMonth;
	        else if (nowMonth >= 4 && nowMonth <= 6)
	        	assessScope =  3 - nowMonth;
	        else if (nowMonth >= 7 && nowMonth <= 9)
	        	assessScope = 6 - nowMonth;
	        else if (nowMonth >= 10 && nowMonth <= 12)
	        	assessScope = 9 - nowMonth;
		}else if(assessScope == -12) {//年度
			assessScope = -nowMonth;
		}
		String minDate = CommonFunctions.dateModifyM(maxDate, assessScope);
		return minDate;
	}
	/**
	 * 根据操作员对应的员工所属机构号和对应的角色级别，获取他可以查看的客户类型
	 * @param brNo
	 * @param roleLvl
	 * @return
	 */
	public String getCustTypeByBrNoAndRoleLvl(String brNo, String roleLvl) {
		String custType = "0";//查询所有条线
		if(brNo.equals("3403112004") && roleLvl.equals("2")) {
			custType = "2";//如果操作员对应机构为公司银行部，且对应角色级别为-中级(总经理),查看对公业务条线
		}else if(brNo.equals("3403111105") && roleLvl.equals("2")) {
			custType = "1";//如果操作员对应机构为零售银行部，且对应角色级别为-中级(总经理),查看对私业务条线
		}
		
		return custType;
	}
	
	/**
	 * 根据统计客户类型和客户号，来判断当前记录是否纳入统计
	 * @param custType
	 * @param custNo
	 * @return
	 */
	public boolean getIsStatic(String custType, String custNo, String kmh) {
		boolean isStatic = false;
		boolean isdg=false;
		boolean isds=false;

		String dkkmh ="1301,1302,1303,1304,1305,1307,1308";//贷款科目(除去贴现)
		String dgkmh = "2001,2002,2006,2011,2014,1306";//对公科目号
		String dskmh = "2003,2004,2005";//对私科目号
		if(kmh!=null&&kmh.length()>=4){
			isdg= dgkmh.indexOf(kmh.substring(0,4)) == -1 ? false : true;
			isds = dskmh.indexOf(kmh.substring(0,4)) == -1 ? false : true;

			if(dkkmh.indexOf(kmh.substring(0,4)) != -1){
				if(custNo!=null&&!"".equals(custNo)){
					if(custNo.startsWith("1")){
						isds=true;
					}
				}
			}

			if(dkkmh.indexOf(kmh.substring(0,4)) != -1){
				if(custNo!=null&&!"".equals(custNo)){
					if(custNo.startsWith("2")){
						isdg=true;
					}
				}
			}
		}

		if(custType.equals("2")){
			if(isdg) {//统计对公客户，是金融台账补录的或（客户号以2开头的或者为空，或者=*）
				isStatic = true;
			}
		}else if(custType.equals("1")){
			if(isds) {//统计对私客户，不是金融台账补录的且客户号以1,3开头的
				isStatic = true;
			}
		}
		else {//否则所有都纳入统计
			isStatic = true;
		}
		return isStatic;
	}

	/**
	 * 根据统计客户类型和客户号，来判断当前记录是否纳入统计
	 * @param custType
	 * @param custNo
	 * @return
	 */
	public boolean getIsStaticDk(String custType, String custNo, String kmh) {
		boolean isStatic = false;
		boolean isdg=false;
		boolean isds=false;

		String dkkmh ="1301,1302,1303,1304,1305,1307,1308";//贷款科目(除去贴现)
		if(kmh!=null&&kmh.length()>=4){
			if(dkkmh.indexOf(kmh.substring(0,4)) != -1){
				if(custNo!=null&&!"".equals(custNo)){
					if(custNo.startsWith("1")){
						isds=true;
					}
				}
			}

			if(dkkmh.indexOf(kmh.substring(0,4)) != -1){
				if(custNo!=null&&!"".equals(custNo)){
					if(custNo.startsWith("2")){
						isdg=true;
					}
				}
			}
		}

		if(custType.equals("2")){
			if(isdg) {//统计对公客户，是金融台账补录的或（客户号以2开头的或者为空，或者=*）
				isStatic = true;
			}
		}else if(custType.equals("1")){
			if(isds) {//统计对私客户，不是金融台账补录的且客户号以1,3开头的
				isStatic = true;
			}
		}
		else {//否则所有都纳入统计
			isStatic = true;
		}
		return isStatic;
	}

	
	/**
	 * 获取TelBrConfig表中，操作员所配置的机构
	 * @param telNo
	 * @return MAP(KEY:TelNo，VALUE:List<BrMst>)
	 */
	public Map<String, List<BrMst>> getBrByEmpNo() {
		Map<String, List<BrMst>> brMstMap = new HashMap<String, List<BrMst>>();
		String hsql1 = "from TelBrConfig";//从配置文件中获取所有已配置操作员的机构权限
		List<TelBrConfig> telBrConfigList = daoFactory.query(hsql1, null);
		if(telBrConfigList != null && telBrConfigList.size() > 0) {
			String hsql2 = "from BrMst order by brNo desc";//获取所有机构
			List<BrMst> brList = daoFactory.query(hsql2, null);
			for(TelBrConfig telBrConfig : telBrConfigList) {
				List<BrMst> brMstList = new ArrayList<BrMst>();
				String brNos = telBrConfig.getBrNos();//以@间隔
				for(BrMst brMst : brList) {
					if(brNos.indexOf(brMst.getBrNo()) != -1) {
						brMstList.add(brMst);
					}
				}
				brMstMap.put(telBrConfig.getTelNo(), brMstList);
			}
			
		}
		return brMstMap;
	}
	/**
	 * 从FtpBusinessStaticDivide表中获取需要的产品
	 * @param xlsBrNo
	 * @param businessNo
	 * @param prdtCtgNo
	 * @param prdtNo
	 * @return
	 */
	public List<FtpBusinessStaticDivide> getFtpBSDivideList(String xlsBrNo, String businessNo, String prdtCtgNo, String prdtNo) {
		//根据业务条线编号或者产品大类编号获取对应的产品编号
		String hsql = "from FtpBusinessStaticDivide where 1=1";
		
		//判断顺序：按产品，然后是产品大类，最后是业务条线
		if(prdtNo != null && !prdtNo.equals("")){//按产品进行查询
			hsql += " and productNo = '"+prdtNo+"'";
		}else if(prdtCtgNo != null && !prdtCtgNo.equals("")) {//按产品大类进行查询
			hsql += " and productCtgNo = '"+prdtCtgNo+"'";
		}else if(businessNo != null && !businessNo.equals("")) {//按业务条线进行查询
			hsql += " and businessNo = '"+businessNo+"'";
		}
		hsql += " order by businessNo,productCtgNo,productNo";
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivideList = daoFactory.query(hsql, null);
		return ftpBusinessStaticDivideList;
	}
	
	/**
	 * 对map按照value进行排序
	 * @param map
	 * @return
	 */
	public static Map sortByValue(Map map) {
		// 将键值赌赢的entryset放到链表中
		List list = new LinkedList(map.entrySet());
		Collections.sort(list, new Comparator() {

			// 将链表按照值得从大到小进行排序
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue())
						.compareTo(((Map.Entry) (o1)).getValue());
			}
		});

		Map result = new LinkedHashMap();

		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	/**
	 * 获取要截取accIds的开始和结束位置
	 * @param rowsCount
	 * @param pageSize
	 * @param currentPage
	 * @param accIds
	 * @return
	 */
	public Integer[] getStartAndEnd(int rowsCount, int pageSize, int currentPage, String accIds) {
		Integer[] result = new Integer[2];
		pageSize=pageSize<1?100:pageSize;//每页显示行数
		currentPage=currentPage<1?1:currentPage;//第几页
		int pageCount=rowsCount/pageSize;//总页数
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		int start=0;
		if(currentPage != 1) {//不是第一页
			start = this.getCharacterPosition(accIds.toString(), ",", (currentPage-1)*pageSize)+1;//当前显示从第start个,出现的位置+1
		}
		int end = 0;
		if(currentPage==rowsCount/pageSize+1){//最后一页
			end=accIds.length();
		}else {
			end=this.getCharacterPosition(accIds.toString(), ",", currentPage*pageSize);//到第end个,出现的位置
		}
		result[0] = start;
		result[1] = end;
		return result;
	}
	/**
	 * 获取某个字符s在字符串string中出现第n次的位置--n不支持0
	 * @param string
	 * @param s
	 * @param n
	 * @return
	 */
	public static int getCharacterPosition(String string, String s, int n){
	    //这里是获取s符号的位置
	    Matcher slashMatcher = Pattern.compile(",").matcher(string);
	    int mIdx = 0;
	    while(slashMatcher.find()) {
	       mIdx++;
	       //当s符号第n次出现的位置
	       if(mIdx == n){
	          break;
	       }
	    }
	    return slashMatcher.start();
	 }
	/**
	 * 根据县联社brNo和要获取的机构级别，获取该联社下该级别的所有机构,不涉及到权限
	 * @param brNo 县联社brNo
	 * @param manageLvl
	 * @return
	 */
	public List<BrMst> getBrMstList(String xlsBrNo, String manageLvl) {
		String hsql = "";
		List<BrMst> brMstList = new ArrayList<BrMst>();
		if(manageLvl.equals("1")) {
			hsql = "from BrMst where superBrNo = '"+xlsBrNo+"'";
			brMstList = daoFactory.query(hsql, null);
		}else if(manageLvl.equals("0")) {
			hsql = "from BrMst where superBrNo = '"+xlsBrNo+"'";
			List<BrMst> brMstList1 = daoFactory.query(hsql, null);
			for(BrMst brMst : brMstList1) {
				hsql = "from BrMst where superBrNo = '"+brMst.getBrNo()+"'";
				List<BrMst> brMstList2 = daoFactory.query(hsql, null);
				for(BrMst brMst2 : brMstList2) {
					brMstList.add(brMst2);
				}
			}
		}else if(manageLvl.equals("2")) {
			hsql = "from BrMst where superBrNo = '"+xlsBrNo+"'";
			List<BrMst> brMstList1 = daoFactory.query(hsql, null);
			for(BrMst brMst : brMstList1) {
				hsql = "from BrMst where superBrNo = '"+brMst.getBrNo()+"'";
				List<BrMst> brMstList2 = daoFactory.query(hsql, null);
				for(BrMst brMst2 : brMstList2) {
					hsql = "from BrMst where superBrNo = '"+brMst2.getBrNo()+"'";
					List<BrMst> brMstList3 = daoFactory.query(hsql, null);
					brMstList.addAll(brMstList3);
				}
			}
		}
		return brMstList;
	}
	
	/**
	 * 获取机构的下级机构(某些操作员有机构权限)
	 * @param br_no
	 * @param tel_no
	 * @return
	 */
	public List<BrMst> getChildBrListByTel(String br_no, String tel_no) {
		List<BrMst> brMstList = new ArrayList<BrMst>();
		List<BrMst> brMstListN = new ArrayList<BrMst>();
		String[] brInfos = LrmUtil.getBr_lvlInfo(br_no);
		if("3".equals(brInfos[0])){
			ReportBbBO reportBbBO = new ReportBbBO();
			List<BrMst> brList = reportBbBO.brMstMap.get(tel_no);//获取该操作员所辖的机构
			if(brList == null || brList.size() == 0) {
				String sql="from BrMst where superBrNo='"+br_no+"'";
				brMstList=daoFactory.query(sql, null);
				for(BrMst brMst:brMstList){
					 sql="from BrMst where superBrNo='"+brMst.getBrNo()+"'";
					 brMstList=daoFactory.query(sql, null);
					 brMstListN.addAll(brMstList);
				}
			}else {//行监事长或者银行部经理
				brMstList=brList;
			}
		}if("2".equals(brInfos[0])){
			ReportBbBO reportBbBO = new ReportBbBO();
			List<BrMst> brList = reportBbBO.brMstMap.get(tel_no);//获取该操作员所辖的机构
			if(brList == null || brList.size() == 0) {
				String sql="from BrMst where superBrNo='"+br_no+"'";
				brMstList=daoFactory.query(sql, null);
			}else {//行监事长或者银行部经理
				brMstList=brList;
			}
		}else {
			String sql="from BrMst where superBrNo='"+br_no+"'";
			brMstList=daoFactory.query(sql, null);
		}

		return brMstList;
	}
	
	/**
	 * 判断该机构是否为叶子节点
	 * @param brNo
	 * @return
	 */
	public boolean getIsLeaf(String brNo) {
		boolean isLeaf = true;
		List<BrMst> brMstList = new ArrayList<BrMst>();
		String sql="from BrMst where superBrNo='"+brNo+"'";//判断是否有子级机构
		brMstList=daoFactory.query(sql, null);
		if(brMstList != null && brMstList.size() > 0) {
			isLeaf = false;
		}
		return isLeaf;
	}
	/**
	 * 获取所有员工的员工编号、员工姓名、所属机构，以及所属机构对应的上级机构
	 * @return
	 */
	public Map<String, String[]> getEmpBrInfoMap() {
		Map<String, String[]> resultMap = new HashMap<String, String[]>();
		String sql = "select a.emp_no, a.emp_name, a.br_no, b.br_name, c.br_no as super_br_no, c.br_name as super_br_name " +
				" from ftp.ftp_emp_info a " +
				" left join ftp.br_mst b on a.br_no = b.br_no " +
				" left join ftp.br_mst c on b.super_br_no = c.br_no";
		List brInfoList = daoFactory.query1(sql, null);
		for(Object object : brInfoList) {
			Object[] o = (Object[])object;
			String[] brInfo = new String[5];
			brInfo[0] = String.valueOf(o[1]);
			brInfo[1] = String.valueOf(o[2]);
			brInfo[2] = String.valueOf(o[3]);
			brInfo[3] = String.valueOf(o[4]);
			brInfo[4] = String.valueOf(o[5]);
			resultMap.put(String.valueOf(o[0]), brInfo);
		}
		return resultMap;
	}

	/**
	 * 获取产品大类
	 * @param brNo
	 * @return
	 */
	public Map<String,List<String>> getPrdtCtgMap() {
		Map<String,List<String>> listHashMap =new HashMap<String,List<String>>();
		String sql="from FtpBusinessStaticDivide";//判断是否有子级机构
		List<FtpBusinessStaticDivide> ftpBusinessStaticDivides=daoFactory.query(sql, null);
		if(ftpBusinessStaticDivides != null && ftpBusinessStaticDivides.size() > 0) {
			List<String> grhqList = new ArrayList<String>();
			List<String> grdqList = new ArrayList<String>();
			List<String> dwhqList = new ArrayList<String>();
			List<String> dwdqList = new ArrayList<String>();

			List<String> yhkList = new ArrayList<String>();
			List<String> czxList = new ArrayList<String>();
			List<String> yjhkList = new ArrayList<String>();
			List<String> bzjList = new ArrayList<String>();
             for(FtpBusinessStaticDivide ftpBusinessStaticDivide:ftpBusinessStaticDivides){
				 if(ftpBusinessStaticDivide.getProductCtgNo().equals("D2003")){
					 grhqList.add(ftpBusinessStaticDivide.getProductNo());
 				 }

				 if(ftpBusinessStaticDivide.getProductCtgNo().equals("D2004")){
					 grdqList.add(ftpBusinessStaticDivide.getProductNo());
				 }

				 if(ftpBusinessStaticDivide.getProductCtgNo().equals("D2002")){
					 dwdqList.add(ftpBusinessStaticDivide.getProductNo());
				 }

				 if(ftpBusinessStaticDivide.getProductCtgNo().equals("D2001")){
					 dwhqList.add(ftpBusinessStaticDivide.getProductNo());
				 }

				 if(ftpBusinessStaticDivide.getProductCtgNo().equals("D2005")){
					 yhkList.add(ftpBusinessStaticDivide.getProductNo());
				 }

				 if(ftpBusinessStaticDivide.getProductCtgNo().equals("D2006")){
					 czxList.add(ftpBusinessStaticDivide.getProductNo());
				 }

				 if(ftpBusinessStaticDivide.getProductCtgNo().equals("D2008")){
					 yjhkList.add(ftpBusinessStaticDivide.getProductNo());
				 }

				 if(ftpBusinessStaticDivide.getProductCtgNo().equals("D2011")){
					 bzjList.add(ftpBusinessStaticDivide.getProductNo());
				 }
			}

			listHashMap.put("grhq",grhqList);
			listHashMap.put("grdq",grdqList);
			listHashMap.put("dwhq",dwhqList);
			listHashMap.put("dwdq",dwdqList);
			listHashMap.put("yhk",yhkList);
			listHashMap.put("bzj",bzjList);
			listHashMap.put("yjhk", yjhkList);
			listHashMap.put("czx",czxList);
		}
		return listHashMap;
	}

	public static void main(String[] args) {
		String a = "1@ @";
		String[] as = a.split("@");
		for(int i = 0; i < as.length ; i++) {

			System.out.println("??????????"+as[i]);
		}
		
	}
}
