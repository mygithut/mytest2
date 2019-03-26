package com.dhcc.ftp.bo;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.cache.CacheOperation;
import com.dhcc.ftp.entity.BrMst;
import com.dhcc.ftp.entity.Ftp1PrepayAdjust;
import com.dhcc.ftp.entity.FtpBusinessInfo;
import com.dhcc.ftp.entity.FtpPoolInfo;
import com.dhcc.ftp.entity.FtpProductMethodRel;
import com.dhcc.ftp.entity.FtpSystemInitial;
import com.dhcc.ftp.entity.YlfxReport;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.SCYTCZlineF;

public class ReportSfbBO extends BaseBo {

	/**
	 * 机构总盈利分析
	 * @param request
	 * @param date
	 * @param brNo
	 * @param manageLvl 机构级别
	 * @param assessScope 统计维度 月-1、-3、-12
	 * @param isMx 是否查看明细
	 * @return
	 */
	public List<YlfxReport> brPayOffProfile(HttpServletRequest request, String date, String brNo, String manageLvl, 
			Integer assessScope, Integer isMx) {
		CacheOperation co = CacheOperation.getInstance();//缓存
	    long intervalTime = Long.valueOf("2592000000");//缓存存放一个月
	    int maxVisitCount = 0;//不限制访问次数
		
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String xlsBrNo = this.getXlsBrNo(brNo, manageLvl);// 县联社
		System.out.println("县联社：" + xlsBrNo);
		String prcMode = FtpUtil.getPrcModeByBrNo(xlsBrNo);// 所配置的资金池
		if (prcMode == null)
			return null;
		request.getSession().setAttribute("prcMode", prcMode);
		System.out.println("所配置的定价策略：" + prcMode);
		String prdtNoZc = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "1"); // 对应资金池或期限匹配所配置的资产类产品
		String prdtNoFz = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "2"); // 对应资金池或期限匹配所配置的负债类产品
		System.out.println("资产资金池或期限匹配产品：" + prdtNoZc);
		System.out.println("负债资金池或期限匹配产品：" + prdtNoFz);
		if (prdtNoZc == null || prdtNoFz == null)
			return null;
		List ftpResultList = null;
		if(!prcMode.equals("4")) {//如果不是期限匹配法
			ftpResultList = this.getFtpResult(date, xlsBrNo, prcMode);//获取对应县联社的定价结果list
		}
		String date2 = CommonFunctions.dateModifyM(date, assessScope);
		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//如果跨过当年，则只取当年的数据，从1月份开始
	    	date2 = date2.substring(0,4)+"1231";
		
		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//获取所统计的天数
		
		if (isMx == 0) {// 如果不是查看子机构的盈利分析，则直接查看该机构的盈利分析
			YlfxReport ylfxReport = new YlfxReport();
			String brSql = LrmUtil.getBrSql(brNo);
			ylfxReport.setBrName(brInfoDAO.getInfo(brNo).getBrName());
			ylfxReport.setBrNo(brNo);
			ylfxReport.setManageLvl(manageLvl);
			if(!prcMode.equals("4")) {//不是期限匹配
				ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, prdtNoZc, "3", assessScope, true));// 资产余额
				System.out.println("资产余额:" + ylfxReport.getZcye());
				ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoZc, "3", assessScope, true));// 生息率：加权平均利率
				System.out.println("生息率:" + ylfxReport.getSxl());
				ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//资产平均期限
		        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
		        ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//负债平均期限
		        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
		        ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, prdtNoFz, "3", assessScope, true));// 负债余额
				System.out.println("负债余额:" + ylfxReport.getFzye());
				ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoFz, "3", assessScope, true));// 付息率：加权平均利率
				System.out.println("付息率:" + ylfxReport.getFxl());
				Double[] resultPrice = this.getFtpResultPrice(ftpResultList, date, brSql, prcMode, assessScope);
				ylfxReport.setZczyjg(resultPrice[0]);// 资产转移价格
				System.out.println("资产转移价格:" + ylfxReport.getZczyjg());
				ylfxReport.setFzzyjg(resultPrice[1]);// 负债转移价格
				System.out.println("负债转移价格:" + ylfxReport.getFzzyjg());
				ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);// 利息收入
				System.out.println("利息收入:" + ylfxReport.getLxsr());
				ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);// 利息支出
				System.out.println("利息支出:" + ylfxReport.getLxzc());
				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
				System.out.println("资产净利差:" + ylfxReport.getZcjlc());
				ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);// 资产转移支出
				System.out.println("资产转移支出:" + ylfxReport.getZczyzc());
				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
				System.out.println("资产净收入:" + ylfxReport.getZcjsr());
				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
				System.out.println("负债净利差:" + ylfxReport.getFzjlc());
				ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);// 负债转移收入
				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// 净收入
			}else {
			       
				List<String[]> ftped_data_successList=(List<String[]>)co.getCacheData(this, "get_ftped_data_successList",new Object[]{xlsBrNo, "2", date}, intervalTime, maxVisitCount);
				if(ftped_data_successList == null) return null;
				double[] qxppZcValue = this.getQxppValueByBrNo(ftped_data_successList, date, daysSubtract, prdtNoZc, brSql);
				ylfxReport.setZcye(qxppZcValue[5]);// 资产余额
				System.out.println("资产余额:"+ylfxReport.getZcye());
				ylfxReport.setSxl(qxppZcValue[0]);// 生息率
				System.out.println("生息率:"+ylfxReport.getSxl());
		        ylfxReport.setZcpjqx(qxppZcValue[1]);//资产平均期限
		        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
		        ylfxReport.setZczyjg(qxppZcValue[2]);// 资产转移价格
				System.out.println("资产转移价格:"+ylfxReport.getZczyjg());
				ylfxReport.setLxsr(qxppZcValue[3]);// 利息收入
				System.out.println("利息收入:"+ylfxReport.getLxsr());
				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
				System.out.println("资产净利差:" + ylfxReport.getZcjlc());
				ylfxReport.setZczyzc(qxppZcValue[4]);//资产转移支出
				System.out.println("资产转移支出:" + ylfxReport.getZczyzc());
				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
				System.out.println("资产净收入:" + ylfxReport.getZcjsr());
				
				double[] qxppFzValue = this.getQxppValueByBrNo(ftped_data_successList, date, daysSubtract, prdtNoFz, brSql);
				ylfxReport.setFzye(qxppFzValue[5]);// 负债余额
				System.out.println("负债余额:"+ylfxReport.getFzye());
				ylfxReport.setFxl(qxppFzValue[0]);// 付息率
				System.out.println("付息率:"+ylfxReport.getFxl());
		        ylfxReport.setFzpjqx(qxppFzValue[1]);//负债平均期限
		        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
		        ylfxReport.setFzzyjg(qxppFzValue[2]);// 负债转移价格
				System.out.println("负债转移价格:"+ylfxReport.getFzzyjg());
				ylfxReport.setLxzc(qxppFzValue[3]);// 利息支出
				System.out.println("利息支出:"+ylfxReport.getLxzc());
				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
				System.out.println("负债净利差:" + ylfxReport.getFzjlc());
				ylfxReport.setFzzysr(qxppFzValue[4]);//负债转移支出
				System.out.println("负债转移收入:" + ylfxReport.getZczyzc());
				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
				System.out.println("负债净收入:" + ylfxReport.getZcjsr());
				
				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// 净收入
			}
			ylfxReportList.add(ylfxReport);
		} else {//是机构明细
			String hsql = "from BrMst where superBrNo = '" + brNo + "' order by brNo";// 获取该机构的所有下级机构
			List<BrMst> list = daoFactory.query(hsql, null);
			if(!prcMode.equals("4")) {//不是期限匹配
				for (int i = 0; i < list.size(); i++) {
					BrMst brMst = list.get(i);
					System.out.println("开始计算机构:"+brMst.getBrNo()+"...");
					String brSql = LrmUtil.getBrSql(brMst.getBrNo());
					YlfxReport ylfxReport = new YlfxReport();
					ylfxReport.setBrName(brMst.getBrName());
					ylfxReport.setBrNo(brMst.getBrNo());
					ylfxReport.setManageLvl(brMst.getManageLvl());
					ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, prdtNoZc, "3", assessScope, true));// 资产余额
					System.out.println("资产余额:" + ylfxReport.getZcye());
					ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoZc, "3", assessScope, true));// 生息率：加权平均利率
					System.out.println("生息率:" + ylfxReport.getSxl());
					ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, prdtNoFz, "3", assessScope, true));// 负债余额
					System.out.println("负债余额:" + ylfxReport.getFzye());
					ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoFz, "3", assessScope, true));// 付息率：加权平均利率
					System.out.println("付息率:" + ylfxReport.getFxl());
					ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//资产平均期限
			        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
			        ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//负债平均期限
			        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
			        Double[] resultPrice = this.getFtpResultPrice(ftpResultList, date, brSql, prcMode, assessScope);
					ylfxReport.setZczyjg(resultPrice[0]);// 资产转移价格
					System.out.println("资产转移价格:" + ylfxReport.getZczyjg());
					ylfxReport.setFzzyjg(resultPrice[1]);// 负债转移价格
					System.out.println("负债转移价格:" + ylfxReport.getFzzyjg());
					ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);// 利息收入
					System.out.println("利息收入:" + ylfxReport.getLxsr());
					ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);// 利息支出
					System.out.println("利息支出:" + ylfxReport.getLxzc());
					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
					ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);// 资产转移支出
					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
					ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);// 负债转移收入
					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
					ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// 净收入
					ylfxReportList.add(ylfxReport);
				}
			}else{//是期限匹配
				List<String[]> ftped_data_successList=(List<String[]>)co.getCacheData(this, "get_ftped_data_successList",new Object[]{xlsBrNo, "2", date}, intervalTime, maxVisitCount);
				if(ftped_data_successList == null) return null;
				Map<String,Double[]> QxppValue_map=this.getQxppValueMap_jgzylfx(ftped_data_successList, date, daysSubtract, list);
				for (int i = 0; i < list.size(); i++) {
					BrMst brMst = list.get(i);
					System.out.println("开始计算机构:"+brMst.getBrNo()+"...");
					YlfxReport ylfxReport = new YlfxReport();
					ylfxReport.setBrName(brMst.getBrName());
					ylfxReport.setBrNo(brMst.getBrNo());
					ylfxReport.setManageLvl(brMst.getManageLvl());
					
					Double[] values_zc=QxppValue_map.get(brMst.getBrNo()+"-1");
					Double[] values_fz=QxppValue_map.get(brMst.getBrNo()+"-2");
					ylfxReport.setZcye(values_zc[5]);// 资产余额
					System.out.println("资产余额:"+ylfxReport.getZcye());
					ylfxReport.setSxl(values_zc[0]);// 生息率
					System.out.println("生息率:"+ylfxReport.getSxl());
			        ylfxReport.setZcpjqx(values_zc[1]);//资产平均期限
			        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
			        ylfxReport.setZczyjg(values_zc[2]);// 资产转移价格
					System.out.println("资产转移价格:"+ylfxReport.getZczyjg());
					ylfxReport.setLxsr(values_zc[3]);// 利息收入
					System.out.println("利息收入:"+ylfxReport.getLxsr());
					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
					System.out.println("资产净利差:" + ylfxReport.getZcjlc());
					ylfxReport.setZczyzc(values_zc[4]);//资产转移支出
					System.out.println("资产转移支出:" + ylfxReport.getZczyzc());
					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
					System.out.println("资产净收入:" + ylfxReport.getZcjsr());
					
					ylfxReport.setFzye(values_fz[5]);// 负债余额
					System.out.println("负债余额:"+ylfxReport.getFzye());
					ylfxReport.setFxl(values_fz[0]);// 付息率
					System.out.println("付息率:"+ylfxReport.getFxl());
			        ylfxReport.setFzpjqx(values_fz[1]);//负债平均期限
			        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
			        ylfxReport.setFzzyjg(values_fz[2]);// 负债转移价格
					System.out.println("负债转移价格:"+ylfxReport.getFzzyjg());
					ylfxReport.setLxzc(values_fz[3]);// 利息支出
					System.out.println("利息支出:"+ylfxReport.getLxzc());
					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
					System.out.println("负债净利差:" + ylfxReport.getFzjlc());
					ylfxReport.setFzzysr(values_fz[4]);//负债转移支出
					System.out.println("负债转移支出:" + ylfxReport.getZczyzc());
					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
					System.out.println("负债净收入:" + ylfxReport.getZcjsr());
					
					ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// 净收入
					ylfxReportList.add(ylfxReport);
				}
			}
		}
		return ylfxReportList;
	}

	/**
	 * 业务条线盈利分析--- 所有业务类型的数据获取
	 * @param request
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @return
	 */
	public List<YlfxReport> busPayOffProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope) {
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String xlsBrNo = this.getXlsBrNo(brNo, manageLvl);// 县联社
		System.out.println("县联社：" + xlsBrNo);
		String prcMode = FtpUtil.getPrcModeByBrNo(xlsBrNo);// 所配置的资金池
		if (prcMode == null) return null;
		System.out.println("所配置的资金池：" + prcMode);
		request.getSession().setAttribute("prcMode", prcMode);
		List<FtpPoolInfo> ftpPoolInfoList = new ArrayList<FtpPoolInfo>();
		if(!prcMode.equals("4")) {//如果不是期限匹配法，则获取对应的定价策略所配置的资金池
			ftpPoolInfoList = this.getFtpPoolInfoList(xlsBrNo, prcMode);// 对应的定价策略所配置资金池
			if (ftpPoolInfoList == null || ftpPoolInfoList.size() < 1) return null;
		}
		String[] businessNo = new String[14];//产品所对应的业务编号ftp_product_method_rel表
		// 贷款
		businessNo[0]  = "'YW101'";
		// 存放中央银行款项
		businessNo[1] = "'YW102'";
		// 存放同业
		businessNo[2] = "'YW103'";
		// 拆放同业
		businessNo[3] = "'YW104'";
		// 投资业务
		businessNo[4] = "'YW105'";
		// 买入贩售
		businessNo[5] = "'YW106'";
		// 现金
		businessNo[6] = "'YW107'";
		// 其他资产
		businessNo[7] = "'YW108'";
		// 存款
		businessNo[8]  = "'YW201','YW202','YW203','YW204'";
		// 同业拆入
		businessNo[9] = "'YW205'";
		// 向中央银行借款
		businessNo[10] = "'YW206'";
		// 贴现负债
		businessNo[11] = "'YW207'";
		// 卖出回购
		businessNo[12] = "'YW208'";
		// 其他负债
		businessNo[13] = "'YW209'";
		
		String brSql = LrmUtil.getBrSql(brNo);
		
		ylfxReportList = this.getYlfxReportList(request, brSql, xlsBrNo, brNo, manageLvl, date, prcMode, assessScope, businessNo, ftpPoolInfoList);
		
		return ylfxReportList;
	}
	
	/**
	 * 业务条线盈利分析---某一大业务(目前只支持：存款、贷款、投资业务)下所有产品盈利分析 数据获取
	 * @param request
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @param prdtType
	 * @return
	 */
	public List<YlfxReport> prdtPayOffProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope, String prdtType) {
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String xlsBrNo = this.getXlsBrNo(brNo, manageLvl);// 县联社
		System.out.println("县联社：" + xlsBrNo);
		String prcMode = FtpUtil.getPrcModeByBrNo(xlsBrNo);// 所配置的资金池
		if (prcMode == null) return null;
		System.out.println("所配置的资金池：" + prcMode);
		request.getSession().setAttribute("prcMode", prcMode);
		List<FtpPoolInfo> ftpPoolInfoList = new ArrayList<FtpPoolInfo>();
		if(!prcMode.equals("4")) {//如果不是期限匹配法，则获取对应的定价策略所配置的资金池
			ftpPoolInfoList = this.getFtpPoolInfoList(xlsBrNo, prcMode);// 对应的定价策略所配置资金池
			if (ftpPoolInfoList == null || ftpPoolInfoList.size() < 1) return null;
		}
		String[][] prdtNo = null;//产品编号+产品名称
		if(prdtType.equals("ck")) {//存款
			prdtNo = new String[19][2];
			prdtNo[0][0] = "'P2010','P2011,'P2012','P2027'";
			prdtNo[0][1] = "个人活期存款";
			prdtNo[1][0] = "'P2011','P2012','P2039','P2040'";
			prdtNo[1][1] = "个人定期存款1年期以下(不含1年)";
			prdtNo[2][0] = "'P2013','P2014','P2017','P2020','P2023','P2028','P2041','P2043','P2046','P2048','P2051'";
			prdtNo[2][1] = "个人定期存款1-2年期";
			prdtNo[3][0] = "'P2015','P2016','P2018','P2019','P2021','P2022','P2024','P2025','P2029','P2030','P2042','P2044','P2045','P2047','P2049','P2050','P2052','P2053'";
			prdtNo[3][1] = "个人定期存款3-5年期";
			prdtNo[4][0] = "'P2028'";
			prdtNo[4][1] = "个人信用卡存款";
			prdtNo[5][0] = "'P2026','P2054'";
			prdtNo[5][1] = "个人定活两便存款";
			prdtNo[6][0] = "'P2027'";
			prdtNo[6][1] = "个人通知存款";
			prdtNo[7][0] = "'P2001','P2031'";
			prdtNo[7][1] = "单位活期存款";
			prdtNo[8][0] = "'P2002','P2003','P2032','P2033'";
			prdtNo[8][1] = "单位定期存款1年以下(不含1年)";
			prdtNo[9][0] = "'P2004','P2005','P2034','P2035'";
			prdtNo[9][1] = "单位定期存款1-2年期";
			prdtNo[10][0] = "'P2006','P2007','P2036','P2037'";
			prdtNo[10][1] = "单位定期存款3-5年期";
			prdtNo[11][0] = "'P2057'";
			prdtNo[11][1] = "财政性存款";
			prdtNo[12][0] = "'P2059','P2060','P2061'";
			prdtNo[12][1] = "应解汇款、汇出汇款、开出本票";
			prdtNo[13][0] = "'P2062'";
			prdtNo[13][1] = "保证金存款";
			prdtNo[14][0] = "'P2055'";
			prdtNo[14][1] = "单位信用卡存款";
			prdtNo[15][0] = "'P2008'";
			prdtNo[15][1] = "单位协议存款";
			prdtNo[16][0] = "'P2009'";
			prdtNo[16][1] = "单位通知存款";
			prdtNo[17][0] = "'P2065'";
			prdtNo[17][1] = "同业存放款项";
			prdtNo[18][0] = "'P2066'";
			prdtNo[18][1] = "系统内存放款项";
		}else if(prdtType.equals("dk")) {//存款
			prdtNo = new String[23][2];
			prdtNo[0][0] = "'P1018','P1023','P1028','P1033','P1038','P1043'";
			prdtNo[0][1] = "农户贷款6个月(含)以内";
			prdtNo[1][0] = "'P1019','P1024','P1029','P1034','P1039','P1044'";
			prdtNo[1][1] = "农户贷款6个月-1年(含1年)";
			prdtNo[2][0] = "'P1020','P1025','P1030','P1035','P1040','P1045'";
			prdtNo[2][1] = "农户贷款1年-3年(含3年)";
			prdtNo[3][0] = "'P1021','P1026','P1031','P1036','P1041','P1046'";
			prdtNo[3][1] = "农户贷款3年-5年(含5年)";
			prdtNo[4][0] = "'P1022','P1027','P1032','P1037','P1042','P1047'";
			prdtNo[4][1] = "农户贷款5年以上(不含5年)";
			prdtNo[5][0] = "'P1048','P1053'";
			prdtNo[5][1] = "农村经济组织贷款6个月(含)以内";
			prdtNo[6][0] = "'P1049','P1054'";
			prdtNo[6][1] = "农村经济组织贷款6个月-1年(含1年)";
			prdtNo[7][0] = "'P1050','P1055'";
			prdtNo[7][1] = "农村经济组织贷款1年-3年(含3年)";
			prdtNo[8][0] = "'P1051','P1056'";
			prdtNo[8][1] = "农村经济组织贷款3年-5年(含5年)";
			prdtNo[9][0] = "'P1052','P1057'";
			prdtNo[9][1] = "农村经济组织贷款5年以上(不含5年)";
			prdtNo[10][0] = "'P1058','P1063','P1068'";
			prdtNo[10][1] = "农村企业贷款6个月(含)以内";
			prdtNo[11][0] = "'P1059','P1064','P1069'";
			prdtNo[11][1] = "农村企业贷款6个月-1年(含1年)";
			prdtNo[12][0] = "'P1060','P1065','P1070'";
			prdtNo[12][1] = "农村企业贷款1年-3年(含3年)";
			prdtNo[13][0] = "'P1061','P1066','P1071'";
			prdtNo[13][1] = "农村企业贷款3年-5年(含5年)";
			prdtNo[14][0] = "'P1062','P1067','P1072'";
			prdtNo[14][1] = "农村企业贷款5年以上(不含5年)";
			prdtNo[15][0] = "'P1073','P1078','P1083','P1088','P1093','P1098'";
			prdtNo[15][1] = "非农贷款6个月(含)以内";
			prdtNo[16][0] = "'P1074','P1079','P1084','P1089','P1094','P1099'";
			prdtNo[16][1] = "非农贷款6个月-1年(含1年)";
			prdtNo[17][0] = "'P1075','P1080','P1085','P1090','P1095','P1100'";
			prdtNo[17][1] = "非农贷款1年-3年(含3年)";
			prdtNo[18][0] = "'P1076','P1081','P1086','P1091','P1096','P1101'";
			prdtNo[18][1] = "非农贷款3年-5年(含5年)";
			prdtNo[19][0] = "'P1077','P1082','P1087','P1092','P1097','P1102'";
			prdtNo[19][1] = "非农贷款5年以上(不含5年)";
			prdtNo[20][0] = "'P1103'";
			prdtNo[20][1] = "信用卡透支";
			prdtNo[21][0] = "'P1104'";
			prdtNo[21][1] = "贴现资产";
			prdtNo[22][0] = "'P1106'";
			prdtNo[22][1] = "垫款";
		}else if(prdtType.equals("tzyw")) {//投资业务
			prdtNo = new String[4][2]; 
			prdtNo[0][0] = "'P1011'";
			prdtNo[0][1] = "交易性金融资产";
			prdtNo[1][0] = "'P1114'";
			prdtNo[1][1] = "持有至到期投资";
			prdtNo[2][0] = "'P1116'";
			prdtNo[2][1] = "可供出售金融资产";
			prdtNo[3][0] = "'P1123'";
			prdtNo[3][1] = "应收款项类投资";
		}
		String brSql = LrmUtil.getBrSql(brNo);
		ylfxReportList = this.getYlfxReportListByPrdtNo(request, brSql, xlsBrNo, date, prcMode, assessScope, prdtNo, ftpPoolInfoList);
		return ylfxReportList;
	}
	
	/**
	 * 机构盈利排名
	 * @param request
	 * @param date
	 * @param brNo 操作员所属机构
	 * @param manageLvl 机构的级别
	 * @param brCountLvl 机构统计级别
	 * @param assessScope 统计维度
	 * @return
	 */
	public List<YlfxReport> brPayOffRanking(HttpServletRequest request, String date, String brNo, String manageLvl, String brCountLvl, Integer assessScope) {
		CacheOperation co = CacheOperation.getInstance();//缓存
	    long intervalTime = Long.valueOf("2592000000");//缓存存放一个月
	    int maxVisitCount = 0;//不限制访问次数
	    
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String xlsBrNo = brNo;// 县联社
		System.out.println("县联社：" + xlsBrNo);
		String prcMode = FtpUtil.getPrcModeByBrNo(xlsBrNo);// 所配置的资金池
		if (prcMode == null)
			return null;
		System.out.println("所配置的资金池：" + prcMode);
		request.getSession().setAttribute("prcMode", prcMode);
		String prdtNoZc = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "1"); // 对应资金池所配置的资产类产品
		String prdtNoFz = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "2"); // 对应资金池所配置的负债类产品
		System.out.println("资产资金池产品：" + prdtNoZc);
		System.out.println("负债资金池产品：" + prdtNoFz);
		if (prdtNoZc == null || prdtNoFz == null)
			return null;
		List ftpResultList = null;
		if(!prcMode.equals("4")) {//如果不是期限匹配法
			ftpResultList = this.getFtpResult(date, xlsBrNo, prcMode);//获取对应县联社的定价结果list
		}
		//根据机构级别获取该县联社下该级别下的所有机构
		List<BrMst> brMstList = getBrMstList(brNo, brCountLvl);
		String date2 = CommonFunctions.dateModifyM(date, assessScope);
		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//如果跨过当年，则只取当年的数据，从1月份开始
	    	date2 = date2.substring(0,4)+"1231";
		
		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//获取所统计的天数
		if(!prcMode.equals("4")) {
			for(BrMst brMst : brMstList) {
				String brSql = LrmUtil.getBrSql(brMst.getBrNo());
				YlfxReport ylfxReport = new YlfxReport();
				ylfxReport.setBrName(brMst.getBrName());
				ylfxReport.setBrNo(brMst.getBrNo());
				ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, prdtNoZc, "3", assessScope, true));// 资产余额
				System.out.println("资产余额:" + ylfxReport.getZcye());
				ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoZc, "3", assessScope, true));// 生息率：加权平均利率
				System.out.println("生息率:" + ylfxReport.getSxl());
				ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, prdtNoFz, "3", assessScope, true));// 负债余额
				System.out.println("负债余额:" + ylfxReport.getFzye());
				ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoFz, "3", assessScope, true));// 付息率：加权平均利率
				System.out.println("付息率:" + ylfxReport.getFxl());
				ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//资产平均期限
		        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
		        ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//负债平均期限
		        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
		        Double[] resultPrice = this.getFtpResultPrice(ftpResultList, date, brSql, prcMode, assessScope);
				ylfxReport.setZczyjg(resultPrice[0]);// 资产转移价格
				System.out.println("资产转移价格:" + ylfxReport.getZczyjg());
				ylfxReport.setFzzyjg(resultPrice[1]);// 负债转移价格
				System.out.println("负债转移价格:" + ylfxReport.getFzzyjg());
				ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);// 利息收入
				System.out.println("利息收入:" + ylfxReport.getLxsr());
				ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);// 利息支出
				System.out.println("利息支出:" + ylfxReport.getLxzc());
				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
				ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);// 资产转移支出
				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
				ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);// 负债转移收入
				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// 净收入
				ylfxReportList.add(ylfxReport);
			}
		}else{
			List<String[]> ftped_data_successList=(List<String[]>)co.getCacheData(this, "get_ftped_data_successList",new Object[]{xlsBrNo, "2", date}, intervalTime, maxVisitCount);
			if(ftped_data_successList == null) return null;
			Map<String,Double[]> QxppValue_map=this.getQxppValueMap_jgzylfx(ftped_data_successList, date, daysSubtract, brMstList);
			for(BrMst brMst : brMstList) {
				YlfxReport ylfxReport = new YlfxReport();
				ylfxReport.setBrName(brMst.getBrName());
				ylfxReport.setBrNo(brMst.getBrNo());
				Double[] values_zc=QxppValue_map.get(brMst.getBrNo()+"-1");
				Double[] values_fz=QxppValue_map.get(brMst.getBrNo()+"-2");
			    
				ylfxReport.setZcye(values_zc[5]);// 资产余额
				System.out.println("资产余额:"+ylfxReport.getZcye());
				ylfxReport.setSxl(values_zc[0]);// 生息率
				System.out.println("生息率:"+ylfxReport.getSxl());
		        ylfxReport.setZcpjqx(values_zc[1]);//资产平均期限
		        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
		        ylfxReport.setZczyjg(values_zc[2]);// 资产转移价格
				System.out.println("资产转移价格:"+ylfxReport.getZczyjg());
				ylfxReport.setLxsr(values_zc[3]);// 利息收入
				System.out.println("利息收入:"+ylfxReport.getLxsr());
				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
				System.out.println("资产净利差:" + ylfxReport.getZcjlc());
				ylfxReport.setZczyzc(values_zc[4]);//资产转移支出
				System.out.println("资产转移支出:" + ylfxReport.getZczyzc());
				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
				System.out.println("资产净收入:" + ylfxReport.getZcjsr());
				
				ylfxReport.setFzye(values_fz[5]);// 负债余额
				System.out.println("负债余额:"+ylfxReport.getFzye());
				ylfxReport.setFxl(values_fz[0]);// 付息率
				System.out.println("付息率:"+ylfxReport.getFxl());
		        ylfxReport.setFzpjqx(values_fz[1]);//负债平均期限
		        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
		        ylfxReport.setFzzyjg(values_fz[2]);// 负债转移价格
				System.out.println("负债转移价格:"+ylfxReport.getFzzyjg());
				ylfxReport.setLxzc(values_fz[3]);// 利息支出
				System.out.println("利息支出:"+ylfxReport.getLxzc());
				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
				System.out.println("负债净利差:" + ylfxReport.getFzjlc());
				ylfxReport.setFzzysr(values_fz[4]);//负债转移支出
				System.out.println("负债转移支出:" + ylfxReport.getZczyzc());
				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
				System.out.println("负债净收入:" + ylfxReport.getZcjsr());
				
				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// 净收入
				ylfxReportList.add(ylfxReport);
		}
		
		
		}
		//对ylfxReportList按净收入大小进行降序排序
		Collections.sort(ylfxReportList, new Comparator<YlfxReport>() {
			 public int compare(YlfxReport arg0, YlfxReport arg1) {
				 return arg1.getJsr().compareTo(arg0.getJsr());
				 }
			 }
		);
		return ylfxReportList;
	}
	/**
	 * 资金中心盈利分析
	 * @param request
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @return
	 */
	public YlfxReport financialCenterLCProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope) {

		CacheOperation co = CacheOperation.getInstance();//缓存
	    long intervalTime = Long.valueOf("2592000000");//缓存存放一个月
	    int maxVisitCount = 0;//不限制访问次数
		
		String xlsBrNo = this.getXlsBrNo(brNo, manageLvl);// 县联社
		System.out.println("县联社：" + xlsBrNo);
		String prcMode = FtpUtil.getPrcModeByBrNo(xlsBrNo);// 所配置的资金池
		if (prcMode == null)
			return null;
		System.out.println("所配置的资金池：" + prcMode);
		request.getSession().setAttribute("prcMode", prcMode);
		String prdtNoZc = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "1"); // 对应资金池所配置的资产类产品
		String prdtNoFz = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "2"); // 对应资金池所配置的负债类产品
		System.out.println("资产资金池产品：" + prdtNoZc);
		System.out.println("负债资金池产品：" + prdtNoFz);
		if (prdtNoZc == null || prdtNoFz == null)
			return null;
		List ftpResultList = null;
		if(!prcMode.equals("4")) {//如果不是期限匹配法
			ftpResultList = this.getFtpResult(date, xlsBrNo, prcMode);//获取对应县联社的定价结果list
		}
		String date2 = CommonFunctions.dateModifyM(date, assessScope);
		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//如果跨过当年，则只取当年的数据，从1月份开始
	    	date2 = date2.substring(0,4)+"1231";
		
		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//获取所统计的天数
	    YlfxReport ylfxReport = new YlfxReport();
		String brSql = LrmUtil.getBrSql(brNo);
		ylfxReport.setBrName(brInfoDAO.getInfo(brNo).getBrName());
		ylfxReport.setBrNo(brNo);
		ylfxReport.setManageLvl(manageLvl);
		if(!prcMode.equals("4")) {
			ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, prdtNoZc, "3", assessScope, true));// 资产余额
			System.out.println("资产余额:" + ylfxReport.getZcye());
			ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, prdtNoFz, "3", assessScope, true));// 负债余额
			System.out.println("负债余额:" + ylfxReport.getFzye());
			Double[] resultPrice = this.getFtpResultPrice(ftpResultList, date, brSql, prcMode, assessScope);
			ylfxReport.setZczyjg(resultPrice[0]);// 资产转移价格
			System.out.println("资产转移价格:" + ylfxReport.getZczyjg());
			ylfxReport.setFzzyjg(resultPrice[1]);// 负债转移价格
			System.out.println("负债转移价格:" + ylfxReport.getFzzyjg());
			ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//资产平均期限
	        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
	        ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//负债平均期限
	        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
	        ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);// 资金中心的资产转移收入=机构的资产转移支出
			System.out.println("资产转移收入:" + ylfxReport.getZczyzc());
			ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);// 资金中心的负债转移支出=机构的负债转移收入
			System.out.println("负债转移支出:" + ylfxReport.getFzzysr());
		}else {//如果是期限匹配
			List<String[]> ftped_data_successList=(List<String[]>)co.getCacheData(this, "get_ftped_data_successList",new Object[]{xlsBrNo, "2", date}, intervalTime, maxVisitCount);
			if(ftped_data_successList == null) return null;
			double[] qxppZcValue = this.getQxppValue(ftped_data_successList, date, daysSubtract, prdtNoZc);
			double[] qxppFzValue = this.getQxppValue(ftped_data_successList, date, daysSubtract, prdtNoFz);
			ylfxReport.setZcye(qxppZcValue[5]);// 资产余额
			System.out.println("资产余额:"+ylfxReport.getZcye());
			ylfxReport.setZczyjg(qxppZcValue[2]);// 资产转移价格
			System.out.println("资产转移价格:"+ylfxReport.getZczyjg());
			ylfxReport.setFzye(qxppFzValue[5]);// 负债余额
			System.out.println("负债余额:"+ylfxReport.getFzye());
			ylfxReport.setFzzyjg(qxppFzValue[2]);// 负债转移价格
			System.out.println("负债转移价格:"+ylfxReport.getFzzyjg());
			ylfxReport.setZcpjqx(qxppZcValue[1]);//资产平均期限
	        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
	        ylfxReport.setFzpjqx(qxppFzValue[1]);//负债平均期限
	        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
	        ylfxReport.setZczyzc(qxppZcValue[4]);// 资金中心的资产转移收入=机构的资产转移支出
			System.out.println("资产转移收入:" + ylfxReport.getZczyzc());
			ylfxReport.setFzzysr(qxppFzValue[4]);// 资金中心的负债转移支出=机构的负债转移收入
			System.out.println("负债转移支出:" + ylfxReport.getFzzysr());
		}
		
		return ylfxReport;
	}
	/**
	 * 根据机构获取它对应资金池所配置的产品
	 * 
	 * @param brNo
	 *            县联社机构号
	 * @param prcMode
	 *            资金池
	 * @param poolType
	 *            资金池资产类型:资产/负债
	 * @return
	 */
	public String getPrdtNoByPrcMode(String brNo, String prcMode, String poolType) {
		StringBuffer prdtNo = new StringBuffer();
		if(!prcMode.equals("4")) {//单双多资金池，期限匹配和单双多定价查询方法不同，查不同的数据表
			String hsql = "from FtpPoolInfo where prcMode = '" + prcMode + "' and brNo = '" + brNo + "'";
			if (!prcMode.equals("1")) {// 如果是单资金池，无法根据poolType来区分资产还是负债
				hsql += " and poolType = '" + poolType + "'";
			}
			List<FtpPoolInfo> poolList = daoFactory.query(hsql, null);
			if (poolList == null || poolList.size() < 1) {
				return null;// 对应的定价策略未配置资金池
			} else {
				if (prcMode.equals("1")) {// 如果是单资金池，根据ContentObject里prdtNo来区分是资产还是负债，P1打头为资产，P2打头为负债
					FtpPoolInfo ftpPoolInfo = poolList.get(0);
					String contentObject = ftpPoolInfo.getContentObject();
					String[] contentObjects = contentObject.split("\\+");
					for (int i = 0; i < contentObjects.length; i++) {
						if (contentObjects[i].indexOf("P" + poolType) != -1)
							prdtNo.append(contentObjects[i] + ",");
					}
				} else {
					for (FtpPoolInfo ftpPoolInfo : poolList) {
						prdtNo.append(ftpPoolInfo.getContentObject() + ",");
					}
				}
			}
		}else {//期限匹配法
			String hsql = "from FtpProductMethodRel t where t.productNo like 'P"+poolType+"%' " +
					"and t.brNo = '"+brNo+"'";
			List<FtpProductMethodRel> ftpProductMethodRelList = daoFactory.query(hsql, null);
			for(FtpProductMethodRel ftpProductMethodRel : ftpProductMethodRelList) {
				prdtNo.append("'" + ftpProductMethodRel.getProductNo() + "',");
			}
		}
		if (prdtNo.lastIndexOf(",") == prdtNo.length() - 1)
			prdtNo = prdtNo.deleteCharAt(prdtNo.length() - 1);
		return prdtNo.toString().replace("+", ",");
	}
	
	/**
	 * 获取机构对应的资金转移价格，资产+负债
	 * 
	 * @param list 定价结果list
	 * @param date
	 * @param brSql
	 * @param prcMode
	 *            资金池
	 * @param assessScope
	 *            统计维度
	 * @return
	 */
	public Double[] getFtpResultPrice(List list, String date, String brSql, String prcMode, Integer assessScope) {
		Double[] resultPrice = {0.0, 0.0};// 资产+负债
		if(list == null || list.size() < 1) {
			return resultPrice;
		}
		if (prcMode.equals("1")) {// 如果是单资金池，则直接获取资金转移价格,资产转移价格和负债转移价格为同一个
			Object obj = list.get(0);
			Object[] o = (Object[]) obj;
			resultPrice[0] = Double.valueOf(o[0].toString());
			resultPrice[1] = Double.valueOf(o[0].toString());
		} else if (prcMode.equals("2")) {// 如果是双资金池，根据pool_no获取转移价格，pool_no=201为资产，pool_no为202为负债
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				Object[] o = (Object[]) obj;
				if (o[1].equals("201")) {// pool_no=201为资产
					resultPrice[0] = Double.valueOf(o[0].toString());
				} else if (o[1].equals("202")) {// pool_no为202为负债
					resultPrice[1] = Double.valueOf(o[0].toString());
				}
			}
		} else if (prcMode.equals("3")) {// 如果是多资金池，则对多个池子加权平均获取转移价格
			double zc = 0.0, zcye = 0.0, fz = 0.0, fzye = 0.0;
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				Object[] o = (Object[]) obj;
				String prdtNo = o[3].toString().replace("+", ",");
				System.out.println("prdtNo"+prdtNo);
				double ye = FtpUtil.getAverageAmount(brSql, date, prdtNo, "3", assessScope, true);
				if (o[2].equals("1")) {// 资产
					zc += ye * Double.valueOf(o[0].toString());
					zcye += ye;
				} else if (o[2].equals("2")) {// 负债
					fz += ye * Double.valueOf(o[0].toString());
					fzye += ye;
				}
			}
			resultPrice[0] = zc / zcye;
			resultPrice[1] = fz / fzye;
		}
		resultPrice[0] = (resultPrice[0].isInfinite() || resultPrice[0].isNaN()) ? 0.0 : resultPrice[0];
		resultPrice[1] = (resultPrice[1].isInfinite() || resultPrice[1].isNaN()) ? 0.0 : resultPrice[1];

		return resultPrice;

	}
	
	/**
	 * 获取县联社定价结果相关数据
	 * @param date
	 * @param xlsBrNo
	 * @param prcMode
	 * @return
	 */
	public List getFtpResult(String date, String xlsBrNo, String prcMode) {
		String sql = "select * from (select t.Result_price, t.pool_no, f.pool_type, f.Content_Object, f.prc_mode, "
			+ "row_number() over(partition by t.pool_no order by t.RES_DATE desc,T.RESULT_ID desc ) rn from ftp.Ftp_Result_sfb t "
			+ "left join ftp.ftp_pool_info f on t.prc_mode = f.prc_mode and t.br_no = f.br_no and t.pool_no = f.pool_no "
			+ "where t.prc_Mode ='" + prcMode
			+ "' and t.cur_no = '01' and t.br_no = '" + xlsBrNo + "' and to_date(t.res_date,'yyyyMMdd') <= to_date('"+date+"','yyyyMMdd') ";
	    if (prcMode.equals("3")) {
		     sql += " and t.pool_no in (select pool_no from ftp.ftp_pool_info where prc_mode='3' and br_no=t.br_no)";
	    }
	    sql += ") where rn=1 order by pool_type, pool_no";
	    List list = daoFactory.query1(sql, null);
	    return list;
	}

	/**
	 * 获取机构对应某类业务的资金转移价格
	 * 
	 * @return
	 */
	public Double getFtpResultPrice(String date, String brSql, String xlsBrNo,
			String prcMode, String prdtNo, Integer assessScope) {
		Double resultPrice = null;
		String sql = "select * from (select t.Result_price, t.pool_no, f.pool_type, f.Content_Object, "
				+ "row_number() over(partition by t.pool_no order by t.RES_DATE desc,T.RESULT_ID desc ) rn from ftp.Ftp_Result_sfb t "
				+ "left join ftp.ftp_pool_info f on t.prc_mode = f.prc_mode and t.br_no = f.br_no and t.pool_no = f.pool_no "
				+ "where t.prc_Mode ='" + prcMode + "' and t.cur_no = '01' and t.br_no = '" + xlsBrNo + "' and to_date(t.res_date,'yyyyMMdd') <= to_date('"+date+"','yyyyMMdd') ";
		if (prcMode.equals("3")) {
			sql += " and t.pool_no in (select pool_no from ftp.ftp_pool_info where prc_mode='3' and br_no=t.br_no)";
		}
		sql += ") where rn=1 order by pool_type, pool_no";
		List list = daoFactory.query1(sql, null);
		if (prcMode.equals("1")) {// 如果是单资金池，则直接获取资金转移价格
			Object obj = list.get(0);
			Object[] o = (Object[]) obj;
			resultPrice = Double.valueOf(o[0].toString());
		} else if (prcMode.equals("2") || prcMode.equals("3")) {// 如果是双/多资金池，对每个产品通过余额对资金转移价格进行加权平均，获取某类业务的资金转移价格
			double fz = 0.0;// 加权平均公式的分子
			double fm = 0.0;// 加权平均公式的分母
			System.out.println("prdtNo:"+prdtNo);
			for (int i = 0; i < list.size(); i++) {// 循环每个池子
				Object obj = list.get(i);
				Object[] o = (Object[]) obj;
				String[] contentObjects = o[3].toString().split("\\+");
				
				for (int j = 0; j < contentObjects.length; j++) {// 循环每个池子对应的产品，判断该产品是否在prdtNo中存在，如果存在，进行加权
					if (prdtNo.indexOf(contentObjects[j]) != -1) {
						// 获取该产品的月均余额
						double ye = FtpUtil.getAverageAmount(brSql, date, contentObjects[j], "3", assessScope, true);
						System.out.println("ye"+ye);
						fz += ye * Double.valueOf(o[0].toString());
						fm += ye;
					}
				}
			}
			resultPrice = fz / fm;
		}

		return (resultPrice.isNaN() || resultPrice.isInfinite()) ? 0.0 : resultPrice;

	}
	
	/**
	 * 根据业务类型编号数组 获取 数组内各个业务类型的盈利分析报表数据
	 * @param brSql 机构查询sql
	 * @param xlsBrNo 县联社
	 * @param brNo
	 * @param brNo
	 * @param date
	 * @param prcMode 资金池类型
	 * @param assessScope 统计维度
	 * @param businessNo 业务编号[]
	 * @param ftpPoolInfoList 资金池配置list
	 * @return
	 */
	public List<YlfxReport> getYlfxReportList(HttpServletRequest request,String brSql,String xlsBrNo, String brNo, String manageLvl, String date,String prcMode, Integer assessScope, String[] businessNo, List<FtpPoolInfo> ftpPoolInfoList) {

		CacheOperation co = CacheOperation.getInstance();//缓存
	    long intervalTime = Long.valueOf("2592000000");//缓存存放一个月
	    int maxVisitCount = 0;//不限制访问次数
		
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String date2 = CommonFunctions.dateModifyM(date, assessScope);
		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//如果跨过当年，则只取当年的数据，从1月份开始
	    	date2 = date2.substring(0,4)+"1231";
		
		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//获取所统计的天数
		List<String[]> ftped_data_successList = new ArrayList<String[]>();
		Map<String,Double[]> QxppValue_map = new HashMap<String, Double[]>();
		if(prcMode.equals("4")) {//如果是期限匹配
			ftped_data_successList=(List<String[]>)co.getCacheData(this, "get_ftped_data_successList",new Object[]{xlsBrNo, "2", date}, intervalTime, maxVisitCount);
			if(ftped_data_successList == null) return null;
			//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
			QxppValue_map = this.getQxppValueMap_ywtxylfx(ftped_data_successList, date, daysSubtract, xlsBrNo, brSql);
		}
		for(int i = 0; i < businessNo.length; i++) {
			YlfxReport ylfxReport = new YlfxReport();
			ylfxReport.setBusinessNo(businessNo[i]);// 业务编号
			//根据业务编号获取对应的产品编号
			String hsql = "from FtpProductMethodRel where businessNo in ("+businessNo[i]+") and brNo = '"+xlsBrNo+"'";
			List<FtpProductMethodRel> ftpProductMethodRelList = daoFactory.query(hsql, null);
			if(ftpProductMethodRelList == null || ftpProductMethodRelList.size() ==0) {
				ylfxReport.setBusinessName("-");// 业务名称
				ylfxReportList.add(ylfxReport);
				continue;
			}
			StringBuilder prdtNo = new StringBuilder();
			for (FtpProductMethodRel ftpProductMethodRel : ftpProductMethodRelList) {
				prdtNo.append("'" + ftpProductMethodRel.getProductNo() + "',");
			}
			if(prdtNo.length() > 0)prdtNo = prdtNo.deleteCharAt(prdtNo.length() - 1);
			// 获取对应资金池所配置的产品
			String settedPrdtNo = "";
			//如果单双多资金池，要根据单双多资金池的配置进行获取
			if(!prcMode.equals("4"))settedPrdtNo = this.getPrdtNoByPrcModeAndPrdtNo(ftpPoolInfoList, prdtNo.toString());
			else settedPrdtNo = prdtNo.toString();
			String businessName = ftpProductMethodRelList.get(0).getBusinessName();//非‘存款’大业务的业务只有一个，则直接取结果list的第一个的业务名字
			if(businessNo[i].indexOf("YW201") != -1) {//‘存款’大业务，由于包含4个业务，所以直接判断如果包含"活期存款"，则业务名为存款；不从查数据库配置表获取
				businessName = "存款";
			}
			System.out.println(businessName+"["+businessNo[i]+"]"+"所对应的产品"+settedPrdtNo);
			ylfxReport.setBusinessName(businessName);// 业务名称
			if(settedPrdtNo.equals("")) {
				ylfxReportList.add(ylfxReport);
				continue;
			}
			if(!prcMode.equals("4")) {
				if(businessNo[i].indexOf("YW1") != -1) {//资产类
					ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, settedPrdtNo, "3", assessScope, true));//资产余额
					System.out.println(businessName+"-资产余额:" + ylfxReport.getZcye());
					ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, settedPrdtNo, "3", assessScope, true));//生息率：加权平均利率
					System.out.println(businessName+"-生息率:" + ylfxReport.getSxl());
					ylfxReport.setZczyjg(this.getFtpResultPrice(date, brSql, xlsBrNo, prcMode, settedPrdtNo, assessScope));//资产转移价格
					ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//资产平均期限
			        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
			        System.out.println(businessName+"-资产转移价格:" + ylfxReport.getZczyjg());
					ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);//利息收入
					System.out.println(businessName+"-利息收入:" + ylfxReport.getLxsr());
					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());//资产净利差
					System.out.println(businessName+"-资产净利差:" + ylfxReport.getZcjlc());
					ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);//资产转移支出
					System.out.println(businessName+"-资产转移支出:" + ylfxReport.getZczyzc());
					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());//资产净收入
					System.out.println(businessName+"-资产净收入:" + ylfxReport.getZcjsr());
				}else if(businessNo[i].indexOf("YW2") != -1) {//负债类
					ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, settedPrdtNo, "3", assessScope, true));//负债余额
					System.out.println(businessName+"-负债余额:"+ylfxReport.getFzye());
					ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, settedPrdtNo, "3", assessScope, true));//付息率：加权平均利率
					System.out.println(businessName+"-付息率:"+ylfxReport.getFxl());
					ylfxReport.setFzzyjg(this.getFtpResultPrice(date, brSql, xlsBrNo, prcMode, settedPrdtNo, assessScope));//资产转移价格
					ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//负债平均期限
			        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
			        System.out.println(businessName+"-负债转移价格:"+ylfxReport.getFzzyjg());
					ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);//利息支出
					System.out.println(businessName+"-利息支出:"+ylfxReport.getLxzc());
					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());//负债净利差
					System.out.println(businessName+"-负债净利差:"+ylfxReport.getFzjlc());
					ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);//负债转移收入
					System.out.println(businessName+"-负债转移收入:"+ylfxReport.getFzzysr());
					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());//负债净收入
					System.out.println(businessName+"-负债净收入:"+ylfxReport.getFzjsr());
				}
			}else{//期限匹配
				double rate = 0.0,term = 0.0,ftpPrice = 0.0,lx = 0.0,zyjg = 0.0,amt = 0.0;
				String[] prdtNos = settedPrdtNo.replace("'", "").split(",");
				for(String prdt_no : prdtNos) {//从QxppValue_map获取需要的值
					Double[] values=QxppValue_map.get(prdt_no);
					rate += values[0];
					term += values[1];
					ftpPrice += values[2];
					lx += values[3];
					zyjg += values[4];
					amt += values[5];
				}
				if(businessNo[i].indexOf("YW1") != -1) {//资产类
					ylfxReport.setZcye(amt);// 资产余额
					System.out.println(businessName+"-资产余额:"+ylfxReport.getZcye());
					ylfxReport.setSxl(rate/amt);// 生息率
					System.out.println(businessName+"-生息率:"+ylfxReport.getSxl());
			        ylfxReport.setZcpjqx(CommonFunctions.doublecut(term/amt, 1));//资产平均期限
			        System.out.println(businessName+"-资产平均期限:"+ylfxReport.getZcpjqx());
					ylfxReport.setZczyjg(ftpPrice/amt);// 资产转移价格
					System.out.println(businessName+"-资产转移价格:"+ylfxReport.getZczyjg());
					ylfxReport.setLxsr(lx);// 利息收入
					System.out.println(businessName+"-利息收入:"+ylfxReport.getLxsr());
					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
					System.out.println(businessName+"-资产净利差:" + ylfxReport.getZcjlc());
					ylfxReport.setZczyzc(zyjg);//资产转移支出
					System.out.println(businessName+"-资产转移支出:" + ylfxReport.getZczyzc());
					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
					System.out.println(businessName+"-资产净收入:" + ylfxReport.getZcjsr());
				}else if(businessNo[i].indexOf("YW2") != -1) {//负债类
					ylfxReport.setFzye(amt);// 负债余额
					System.out.println(businessName+"-负债余额:"+ylfxReport.getFzye());
					ylfxReport.setFxl(rate/amt);// 付息率
					System.out.println(businessName+"-付息率:"+ylfxReport.getFxl());
			        ylfxReport.setFzpjqx(CommonFunctions.doublecut(term/amt, 1));//负债平均期限
			        System.out.println(businessName+"-负债平均期限:"+ylfxReport.getFzpjqx());
			        ylfxReport.setFzzyjg(ftpPrice/amt);// 负债转移价格
					System.out.println(businessName+"-负债转移价格:"+ylfxReport.getFzzyjg());
					ylfxReport.setLxzc(lx);// 利息支出
					System.out.println(businessName+"-利息支出:"+ylfxReport.getLxzc());
					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
					System.out.println(businessName+"-负债净利差:" + ylfxReport.getFzjlc());
					ylfxReport.setFzzysr(zyjg);//负债转移支出
					System.out.println(businessName+"-负债转移支出:" + ylfxReport.getZczyzc());
					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
					System.out.println(businessName+"-负债净收入:" + ylfxReport.getZcjsr());
				}
			}
			ylfxReportList.add(ylfxReport);
		}
			
		
	 return ylfxReportList;
	}
	
	/**
	 * 获取不同类产品的盈利分析数据
	 * @param brSql 机构查询sql
	 * @param xlsBrNo 县联社
	 * @param date
	 * @param prcMode 资金池类型
	 * @param assessScope 统计维度
	 * @param prdtNo 产品编号+产品名称[][]
	 * @param ftpPoolInfoList 资金池配置list
	 * @return
	 */
	public List<YlfxReport> getYlfxReportListByPrdtNo(HttpServletRequest request, String brSql, String xlsBrNo, String date,String prcMode, Integer assessScope, String[][] prdtNo, List<FtpPoolInfo> ftpPoolInfoList) {

		CacheOperation co = CacheOperation.getInstance();//缓存
	    long intervalTime = Long.valueOf("2592000000");//缓存存放一个月
	    int maxVisitCount = 0;//不限制访问次数
	    
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String date2 = CommonFunctions.dateModifyM(date, assessScope);
		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//如果跨过当年，则只取当年的数据，从1月份开始
	    	date2 = date2.substring(0,4)+"1231";
		
		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//获取所统计的天数
		List<String[]> ftped_data_successList = new ArrayList<String[]>();
		Map<String,Double[]> QxppValue_map = new HashMap<String, Double[]>();
		if(prcMode.equals("4")) {//如果是期限匹配
			ftped_data_successList=(List<String[]>)co.getCacheData(this, "get_ftped_data_successList",new Object[]{xlsBrNo, "2", date}, intervalTime, maxVisitCount);
			if(ftped_data_successList == null) return null;
			//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
			QxppValue_map = this.getQxppValueMap_ywtxylfx(ftped_data_successList, date, daysSubtract, xlsBrNo, brSql);
		}
		for(int i = 0; i < prdtNo.length; i++) {
			YlfxReport ylfxReport = new YlfxReport();
			// 获取对应资金池所配置的产品
			String settedPrdtNo = "";
			//如果单双多资金池，要根据单双多资金池的配置进行获取
			if(!prcMode.equals("4"))settedPrdtNo = this.getPrdtNoByPrcModeAndPrdtNo(ftpPoolInfoList, prdtNo[i][0]);
			else settedPrdtNo = prdtNo[i][0];
			ylfxReport.setPrdtName(prdtNo[i][1]);
			if(settedPrdtNo.equals("")) {
				ylfxReportList.add(ylfxReport);
				continue;
			}
			if(!prcMode.equals("4")) {
				if(prdtNo[i][0].indexOf("P1") != -1) {//资产类
					ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, settedPrdtNo, "3", assessScope, true));//资产余额
					System.out.println(prdtNo[i][1]+"-资产余额:" + ylfxReport.getZcye());
					ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, settedPrdtNo, "3", assessScope, true));//生息率：加权平均利率
					System.out.println(prdtNo[i][1]+"-生息率:" + ylfxReport.getSxl());
					ylfxReport.setZczyjg(this.getFtpResultPrice(date, brSql, xlsBrNo, prcMode, settedPrdtNo, assessScope));//资产转移价格
					ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//资产平均期限
			        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
			        System.out.println(prdtNo[i][1]+"-资产转移价格:" + ylfxReport.getZczyjg());
					ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);//利息收入
					System.out.println(prdtNo[i][1]+"-利息收入:" + ylfxReport.getLxsr());
					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());//资产净利差
					System.out.println(prdtNo[i][1]+"-资产净利差:" + ylfxReport.getZcjlc());
					ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);//资产转移支出
					System.out.println(prdtNo[i][1]+"-资产转移支出:" + ylfxReport.getZczyzc());
					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());//资产净收入
					System.out.println(prdtNo[i][1]+"-资产净收入:" + ylfxReport.getZcjsr());
				}else if(settedPrdtNo.indexOf("P2") != -1) {//负债类
					ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, settedPrdtNo, "3", assessScope, true));//负债余额
					System.out.println(prdtNo[i][1]+"-负债余额:"+ylfxReport.getFzye());
					ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, settedPrdtNo, "3", assessScope, true));//付息率：加权平均利率
					System.out.println(prdtNo[i][1]+"-付息率:"+ylfxReport.getFxl());
					ylfxReport.setFzzyjg(this.getFtpResultPrice(date, brSql, xlsBrNo, prcMode, settedPrdtNo, assessScope));//资产转移价格
					ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//负债平均期限
			        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
			        System.out.println(prdtNo[i][1]+"-负债转移价格:"+ylfxReport.getFzzyjg());
					ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);//利息支出
					System.out.println(prdtNo[i][1]+"-利息支出:"+ylfxReport.getLxzc());
					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());//负债净利差
					System.out.println(prdtNo[i][1]+"-负债净利差:"+ylfxReport.getFzjlc());
					ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);//负债转移收入
					System.out.println(prdtNo[i][1]+"-负债转移收入:"+ylfxReport.getFzzysr());
					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());//负债净收入
					System.out.println(prdtNo[i][1]+"-负债净收入:"+ylfxReport.getFzjsr());
				}
			}else {//如果是期限匹配法
				double rate = 0.0,term = 0.0,ftpPrice = 0.0,lx = 0.0,zyjg = 0.0,amt = 0.0;
				String[] prdtNos = settedPrdtNo.replace("'", "").split(",");
				for(String prdt_no : prdtNos) {//从QxppValue_map获取需要的值
					Double[] values=QxppValue_map.get(prdt_no);
					if(values == null){
						continue;
					}
					rate += values[0];
					term += values[1];
					ftpPrice += values[2];
					lx += values[3];
					zyjg += values[4];
					amt += values[5];
				}
				if(prdtNo[i][0].indexOf("P1") != -1) {//资产类
					ylfxReport.setZcye(amt);// 资产余额
					System.out.println(prdtNo[i][1]+"-资产余额:"+ylfxReport.getZcye());
					ylfxReport.setSxl(rate/amt);// 生息率
					System.out.println(prdtNo[i][1]+"-生息率:"+ylfxReport.getSxl());
			        ylfxReport.setZcpjqx(CommonFunctions.doublecut(term/amt, 1));//资产平均期限
			        System.out.println(prdtNo[i][1]+"-资产平均期限:"+ylfxReport.getZcpjqx());
			        ylfxReport.setZczyjg(ftpPrice/amt);// 资产转移价格
					System.out.println(prdtNo[i][1]+"-资产转移价格:"+ylfxReport.getZczyjg());
					ylfxReport.setLxsr(lx);// 利息收入
					System.out.println(prdtNo[i][1]+"-利息收入:"+ylfxReport.getLxsr());
					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
					System.out.println(prdtNo[i][1]+"-资产净利差:" + ylfxReport.getZcjlc());
					ylfxReport.setZczyzc(zyjg);//资产转移支出
					System.out.println(prdtNo[i][1]+"-资产转移支出:" + ylfxReport.getZczyzc());
					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
					System.out.println(prdtNo[i][1]+"-资产净收入:" + ylfxReport.getZcjsr());
				}else if(settedPrdtNo.indexOf("P2") != -1) {//负债类
					ylfxReport.setFzye(amt);// 负债余额
					System.out.println(prdtNo[i][1]+"-负债余额:"+ylfxReport.getFzye());
					ylfxReport.setFxl(rate/amt);// 付息率
					System.out.println(prdtNo[i][1]+"-付息率:"+ylfxReport.getFxl());
			        ylfxReport.setFzpjqx(CommonFunctions.doublecut(term/amt, 1));//负债平均期限
			        System.out.println(prdtNo[i][1]+"-负债平均期限:"+ylfxReport.getFzpjqx());
			        ylfxReport.setFzzyjg(ftpPrice/amt);// 负债转移价格
					System.out.println(prdtNo[i][1]+"-负债转移价格:"+ylfxReport.getFzzyjg());
					ylfxReport.setLxzc(lx);// 利息支出
					System.out.println(prdtNo[i][1]+"-利息支出:"+ylfxReport.getLxzc());
					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
					System.out.println(prdtNo[i][1]+"-负债净利差:" + ylfxReport.getFzjlc());
					ylfxReport.setFzzysr(zyjg);//负债转移支出
					System.out.println(prdtNo[i][1]+"-负债转移支出:" + ylfxReport.getZczyzc());
					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
					System.out.println(prdtNo[i][1]+"-负债净收入:" + ylfxReport.getZcjsr());
				}
			}
			ylfxReportList.add(ylfxReport);
		}
	 return ylfxReportList;
	}

	/**
	 * 根据机构获取它对应资金池所配置的某个业务下对应的产品
	 * 
	 * @param poolList
	 *            资金池list
	 * @param prdtNo
	 *            某个业务所对应的所有产品
	 * @return
	 */
	public String getPrdtNoByPrcModeAndPrdtNo(List<FtpPoolInfo> poolList,
			String prdtNo) {
		StringBuffer settedPrdtNo = new StringBuffer();
		for (FtpPoolInfo ftpPoolInfo : poolList) {
			String contentObject = ftpPoolInfo.getContentObject();
			String[] contentObjects = contentObject.split("\\+");
			for (int i = 0; i < contentObjects.length; i++) {
				// 循环判断所配置的产品是否在prdtNo中
				if (prdtNo.indexOf(contentObjects[i]) != -1)
					settedPrdtNo.append(contentObjects[i] + ",");
			}
		}
		System.out.println("settedPrdtNo:"+settedPrdtNo);
		if(settedPrdtNo.length() > 0) {
			if (settedPrdtNo.lastIndexOf(",") == settedPrdtNo.length() - 1)
				settedPrdtNo = settedPrdtNo.deleteCharAt(settedPrdtNo.length() - 1);
		}
		
		return settedPrdtNo.toString();
	}

	/**
	 * 根据prcMode和brNo，获取对应的资金池配置
	 * 
	 * @param brNo
	 *            县联社机构号
	 * @param prcMode
	 *            资金池
	 * @return
	 */
	public List<FtpPoolInfo> getFtpPoolInfoList(String brNo, String prcMode) {
		String hsql = "from FtpPoolInfo where prcMode = '" + prcMode
				+ "' and brNo = '" + brNo + "'";
		List<FtpPoolInfo> poolList = daoFactory.query(hsql, null);
		return poolList;
	}

	/**
	 * 获取某类产品的加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、总余额--期限匹配
	 * @param ftped_data_successList
	 * @param date时间段右端点
	 * @param daysSubtract总的日期数
	 * @param prdtNo
	 * @return 加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、总余额
	 */
	public double[] getQxppValue(List<String[]> ftped_data_successList, String date, Integer daysSubtract, String prdtNo) {
		double[] returnValue = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		double amt = 0.0, rate_fz = 0.0, ftpPrice_fz = 0.0, term_fz = 0.0, lxAmt = 0.0, zyAmt = 0.0;
		for (String[] result : ftped_data_successList) {
			if(prdtNo.indexOf(result[0]) != -1) {
				double rate = Double.valueOf(result[3]);
				double amt1 = Double.valueOf(result[2]==null?"0":result[2]);
				double ftpPrice = Double.valueOf(result[4]);
				double term = daysSubtract;//当前日期所在月的总天数
				amt += amt1;//总余额
				rate_fz += rate*amt1;//加权平均利率计算的分子
				term_fz += term*amt1;//加权平均期限计算的分子
				ftpPrice_fz += ftpPrice*amt1;//加权平均转移价格计算的分子
				lxAmt += amt1*rate*term/365;//利息收入/利息支出=余额*利率*期限/365
				zyAmt += amt1*ftpPrice*term/365;//资产转移支出/负债转移收入=余额*转移价格*期限/365
			}
		}
		returnValue[0] = rate_fz/amt;//加权平均利率
		returnValue[1] = CommonFunctions.doublecut(term_fz/amt, 1);//加权平均期限
		returnValue[2] = ftpPrice_fz/amt;//加权平均转移价格
		returnValue[3] = lxAmt;//利息收入/利息支出
		returnValue[4] = zyAmt;//资产转移支出/负债转移收入
		returnValue[5] = amt;//总余额
		return returnValue;
	}
	/**
	 * 获取某类机构、某类产品的加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、总余额--期限匹配
	 * @param ftped_data_successList
	 * @param date时间段右端点
	 * @param daysSubtract总的日期数
	 * @param prdtNo
	 * @param brNos要汇总的机构字符串，例'1801031009','1801031008'
	 * @return 加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、总余额
	 * @throws ParseException 
	 */
	public double[] getQxppValueByBrNo(List<String[]> ftped_data_successList, String date, Integer daysSubtract, String prdtNo, String brNos) {
		double[] returnValue = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		double amt = 0.0, rate_fz = 0.0, ftpPrice_fz = 0.0, term_fz = 0.0, lxAmt = 0.0, zyAmt = 0.0;
		for (String[] result : ftped_data_successList) {
			if(prdtNo.indexOf(result[0]) != -1 && (brNos.indexOf(result[5])!= -1 || brNos.equals("is not null"))) {
				double rate = Double.valueOf(result[3]);
				double amt1 = Double.valueOf(result[2]==null?"0":result[2]);
				double ftpPrice = Double.valueOf(result[4]);
				double term = daysSubtract;
				amt += amt1;//总余额
				rate_fz += rate*amt1;//加权平均利率计算的分子
				term_fz += term*amt1;//加权平均期限计算的分子
				ftpPrice_fz += ftpPrice*amt1;//加权平均转移价格计算的分子
				lxAmt += amt1*rate*term/365;//利息收入/利息支出=余额*利率*期限/365
				zyAmt += amt1*ftpPrice*term/365;//资产转移支出/负债转移收入=余额*转移价格*期限/365
			}
		}
		returnValue[0] = rate_fz/amt;//加权平均利率
		returnValue[1] = CommonFunctions.doublecut(term_fz/amt, 1);//加权平均期限
		returnValue[2] = ftpPrice_fz/amt;//加权平均转移价格
		returnValue[3] = lxAmt;//利息收入/利息支出
		returnValue[4] = zyAmt;//资产转移支出/负债转移收入
		returnValue[5] = amt;//总余额
		return returnValue;
	}
	
	
	/**
	 * 机构总盈利分析-----机构明细查询，将ftped_data_successList中的数据，按照机构放到对应的map中
	 * @param ftped_data_successList
	 * @param date时间段右端点
	 * @param daysSubtract总的日期数
	 * @param brMstList 要获取数据值到界面直接显示的机构数据表对象列表(2、1、0级机构都支持)
	 * @return map
	 */
	public Map<String,Double[]> getQxppValueMap_jgzylfx(List<String[]> ftped_data_successList, String date, Integer daysSubtract, List<BrMst> brMstList) {
		Map<String,Double[]> QxppValue_map=new HashMap<String,Double[]>();
		Map<String,Double> amt_map=new HashMap<String,Double>();
		Map<String,Double> rate_fz_map=new HashMap<String,Double>();
		Map<String,Double> ftpPrice_fz_map=new HashMap<String,Double>();
		Map<String,Double> term_fz_map=new HashMap<String,Double>();
		Map<String,Double> lxAmt_map=new HashMap<String,Double>();
		Map<String,Double> zyAmt_map=new HashMap<String,Double>();
	
		for (String[] result : ftped_data_successList) {
			
			double rate = Double.valueOf(result[3]);
			double amt1 = Double.valueOf(result[2]==null?"0":result[2]);
			double ftpPrice = Double.valueOf(result[4]);
			double term = daysSubtract;//总的日期数
			//总余额
			if(amt_map.get(result[5]+"-"+result[0].substring(1,2))==null){
				amt_map.put(result[5]+"-"+result[0].substring(1,2), amt1);
			}else{
				amt_map.put(result[5]+"-"+result[0].substring(1,2), amt1+amt_map.get(result[5]+"-"+result[0].substring(1,2)));
			}
			//加权平均利率计算的分子
			if(rate_fz_map.get(result[5]+"-"+result[0].substring(1,2))==null){
				rate_fz_map.put(result[5]+"-"+result[0].substring(1,2), rate*amt1);
			}else{
				rate_fz_map.put(result[5]+"-"+result[0].substring(1,2), rate*amt1+rate_fz_map.get(result[5]+"-"+result[0].substring(1,2)));
			}
			
			//加权平均期限计算的分子
			if(term_fz_map.get(result[5]+"-"+result[0].substring(1,2))==null){
				term_fz_map.put(result[5]+"-"+result[0].substring(1,2), term*amt1);
			}else{
				term_fz_map.put(result[5]+"-"+result[0].substring(1,2), term*amt1+term_fz_map.get(result[5]+"-"+result[0].substring(1,2)));
			}
			//加权平均转移价格计算的分子
			if(ftpPrice_fz_map.get(result[5]+"-"+result[0].substring(1,2))==null){
				ftpPrice_fz_map.put(result[5]+"-"+result[0].substring(1,2), ftpPrice*amt1);
			}else{
				ftpPrice_fz_map.put(result[5]+"-"+result[0].substring(1,2), ftpPrice*amt1+ftpPrice_fz_map.get(result[5]+"-"+result[0].substring(1,2)));
			}
			//利息收入/利息支出=余额*利率*期限/365
			if(lxAmt_map.get(result[5]+"-"+result[0].substring(1,2))==null){
				lxAmt_map.put(result[5]+"-"+result[0].substring(1,2),amt1*rate*term/365);
			}else{
				lxAmt_map.put(result[5]+"-"+result[0].substring(1,2), amt1*rate*term/365+lxAmt_map.get(result[5]+"-"+result[0].substring(1,2)));
			}
			//资产转移支出/负债转移收入=余额*转移价格*期限/365
			if(zyAmt_map.get(result[5]+"-"+result[0].substring(1,2))==null){
				zyAmt_map.put(result[5]+"-"+result[0].substring(1,2),amt1*ftpPrice*term/365);
			}else{
				zyAmt_map.put(result[5]+"-"+result[0].substring(1,2), amt1*ftpPrice*term/365+zyAmt_map.get(result[5]+"-"+result[0].substring(1,2)));
			}			
		}
		for(BrMst br_mst:brMstList){
			String brNos=LrmUtil.getBrSql(br_mst.getBrNo());
			String[] br_no_s=null;
			if(brNos.indexOf("in") != -1) {
				br_no_s = brNos.substring(4,brNos.length()-1).replaceAll("'", "").split(",");
			}else {
				br_no_s = brNos.substring(1).replaceAll("'", "").split(",");
			}
			double[][] returnValue = new double[6][2];//[i][0]为资产的各计算数据项，[i][1]为负债的各计算数据项
			for(String br_no:br_no_s){
				returnValue[0][0]=returnValue[0][0]+=rate_fz_map.get(br_no+"-1")==null?0.0:rate_fz_map.get(br_no+"-1");
				returnValue[0][1]=returnValue[0][1]+=rate_fz_map.get(br_no+"-2")==null?0.0:rate_fz_map.get(br_no+"-2");
				returnValue[1][0]=returnValue[1][0]+=term_fz_map.get(br_no+"-1")==null?0.0:term_fz_map.get(br_no+"-1");
				returnValue[1][1]=returnValue[1][1]+=term_fz_map.get(br_no+"-2")==null?0.0:term_fz_map.get(br_no+"-2");
				returnValue[2][0]=returnValue[2][0]+=ftpPrice_fz_map.get(br_no+"-1")==null?0.0:ftpPrice_fz_map.get(br_no+"-1");
				returnValue[2][1]=returnValue[2][1]+=ftpPrice_fz_map.get(br_no+"-2")==null?0.0:ftpPrice_fz_map.get(br_no+"-2");
				returnValue[3][0]=returnValue[3][0]+=lxAmt_map.get(br_no+"-1")==null?0.0:lxAmt_map.get(br_no+"-1");
				returnValue[3][1]=returnValue[3][1]+=lxAmt_map.get(br_no+"-2")==null?0.0:lxAmt_map.get(br_no+"-2");
				returnValue[4][0]=returnValue[4][0]+=zyAmt_map.get(br_no+"-1")==null?0.0:zyAmt_map.get(br_no+"-1");
				returnValue[4][1]=returnValue[4][1]+=zyAmt_map.get(br_no+"-2")==null?0.0:zyAmt_map.get(br_no+"-2");
				returnValue[5][0]=returnValue[5][0]+=amt_map.get(br_no+"-1")==null?0.0:amt_map.get(br_no+"-1");
				returnValue[5][1]=returnValue[5][1]+=amt_map.get(br_no+"-2")==null?0.0:amt_map.get(br_no+"-2");				
			}
			returnValue[0][0]=returnValue[0][0]/returnValue[5][0];
			returnValue[0][1]=returnValue[0][1]/returnValue[5][1];
			returnValue[1][0]=CommonFunctions.doublecut(returnValue[1][0]/returnValue[5][0],1);
			returnValue[1][1]=CommonFunctions.doublecut(returnValue[1][1]/returnValue[5][1],1);
			returnValue[2][0]=returnValue[2][0]/returnValue[5][0];
			returnValue[2][1]=returnValue[2][1]/returnValue[5][1];
			QxppValue_map.put(br_mst.getBrNo()+"-1", new Double[]{returnValue[0][0],returnValue[1][0],returnValue[2][0],returnValue[3][0],returnValue[4][0],returnValue[5][0]});
			QxppValue_map.put(br_mst.getBrNo()+"-2", new Double[]{returnValue[0][1],returnValue[1][1],returnValue[2][1],returnValue[3][1],returnValue[4][1],returnValue[5][1]});
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
	 * @return map
	 */
	public Map<String,Double[]> getQxppValueMap_ywtxylfx(List<String[]> ftped_data_successList, String date, Integer daysSubtract, String xlsBrNo, String brNos) {
		Map<String,Double[]> QxppValue_map=new HashMap<String,Double[]>();
		Map<String,Double> amt_map=new HashMap<String,Double>();
		Map<String,Double> rate_fz_map=new HashMap<String,Double>();
		Map<String,Double> ftpPrice_fz_map=new HashMap<String,Double>();
		Map<String,Double> term_fz_map=new HashMap<String,Double>();
		Map<String,Double> lxAmt_map=new HashMap<String,Double>();
		Map<String,Double> zyAmt_map=new HashMap<String,Double>();
	
		//按产品编号放到map中
		for (String[] result : ftped_data_successList) {
			if(brNos.indexOf(result[5]) != -1 || brNos.equals("is not null")) {//如果要汇总的机构中包含result[5]
				double rate = Double.valueOf(result[3]);
				double amt1 = Double.valueOf(result[1]==null?result[2]:result[1]);
				double ftpPrice = Double.valueOf(result[4]);
				double term = daysSubtract;//日期数
				//总余额
				if(amt_map.get(result[0])==null){
					amt_map.put(result[0], amt1);
				}else{
					amt_map.put(result[0], amt1+amt_map.get(result[0]));
				}
				//加权平均利率计算的分子
				if(rate_fz_map.get(result[0])==null){
					rate_fz_map.put(result[0], rate*amt1);
				}else{
					rate_fz_map.put(result[0], rate*amt1+rate_fz_map.get(result[0]));
				}
				
				//加权平均期限计算的分子
				if(term_fz_map.get(result[0])==null){
					term_fz_map.put(result[0], term*amt1);
				}else{
					term_fz_map.put(result[0], term*amt1+term_fz_map.get(result[0]));
				}
				//加权平均转移价格计算的分子
				if(ftpPrice_fz_map.get(result[0])==null){
					ftpPrice_fz_map.put(result[0], ftpPrice*amt1);
				}else{
					ftpPrice_fz_map.put(result[0], ftpPrice*amt1+ftpPrice_fz_map.get(result[0]));
				}
				//利息收入/利息支出=余额*利率*期限/365
				if(lxAmt_map.get(result[0])==null){
					lxAmt_map.put(result[0],amt1*rate*term/365);
				}else{
					lxAmt_map.put(result[0], amt1*rate*term/365+lxAmt_map.get(result[0]));
				}
				//资产转移支出/负债转移收入=余额*转移价格*期限/365
				if(zyAmt_map.get(result[0])==null){
					zyAmt_map.put(result[0],amt1*ftpPrice*term/365);
				}else{
					zyAmt_map.put(result[0], amt1*ftpPrice*term/365+zyAmt_map.get(result[0]));
				}	
			}
		}
		//该县联社对应的所有产品
		String hsql = "from FtpProductMethodRel t where t.brNo = '"+xlsBrNo+"'";
		List<FtpProductMethodRel> ftpProductMethodRelList = daoFactory.query(hsql, null);
		double[] returnValue = new double[6];
		for(FtpProductMethodRel ftpProductMethodRel:ftpProductMethodRelList){
			returnValue[0]=rate_fz_map.get(ftpProductMethodRel.getProductNo())==null?0.0:rate_fz_map.get(ftpProductMethodRel.getProductNo());
			returnValue[1]=term_fz_map.get(ftpProductMethodRel.getProductNo())==null?0.0:term_fz_map.get(ftpProductMethodRel.getProductNo());
			returnValue[2]=ftpPrice_fz_map.get(ftpProductMethodRel.getProductNo())==null?0.0:ftpPrice_fz_map.get(ftpProductMethodRel.getProductNo());
			returnValue[3]=lxAmt_map.get(ftpProductMethodRel.getProductNo())==null?0.0:lxAmt_map.get(ftpProductMethodRel.getProductNo());
			returnValue[4]=zyAmt_map.get(ftpProductMethodRel.getProductNo())==null?0.0:zyAmt_map.get(ftpProductMethodRel.getProductNo());
			returnValue[5]=amt_map.get(ftpProductMethodRel.getProductNo())==null?0.0:amt_map.get(ftpProductMethodRel.getProductNo());
			QxppValue_map.put(ftpProductMethodRel.getProductNo(), new Double[]{returnValue[0],returnValue[1],returnValue[2],returnValue[3],returnValue[4],returnValue[5]});
		}
		return QxppValue_map;
	}
	
	 /**
	  * 获取某一机构、指定日期下的所有笔业务的一次全部期限匹配定价 
	  * @param brNo
	  * @param manageLvl
	  * @param date
	  * @return
	  */
	public List<String[]> get_ftped_data_successList(String brNo, String manageLvl, String date) {
		String STime=CommonFunctions.GetCurrentTime();
		//查询获得该机构下的所有业务办理机构
		String sql="";
		if("2".equals(manageLvl)){
			sql="select br_no from ftp.br_mst where is_business='1' order by br_no";
		}else{
			String brSql=LrmUtil.getBrSql(brNo);
			sql="select br_no from ftp.br_mst where is_business='1' and br_no "+brSql+" order by br_no";
		}
		List brNoList = daoFactory.query1(sql, null);
		
		//存贷款产品的各项策略调整
		UL06BO uL06BO = new UL06BO();
		Map<Integer, Double> ckzbjAdjustMap = uL06BO.getCkzbjAdjustValue();//存款准备金调整
		Map<Integer, Double> ldxAdjustMap = uL06BO.getLdxAdjustValue();//流动性调整
		Map<String, Double> irAdjustMap = uL06BO.getIrAdjustValue();//信用风险调整
		List<Ftp1PrepayAdjust> prepayList = uL06BO.getPrepayAdjustValue();//提前还款/提前支取调整
		Map<String, Double> clAdjustMap = uL06BO.getClAdjustValue();//策略调整
		double[][] dkAdjustArr = uL06BO.getDkAdjustValue();//贷款调整比率
		Double[][] publicRate = FtpUtil.getFtpPublicRate();//央行贷款利率
		//double[][] dkSfblAdjustArr=uL06BO.getDkSfblAdjustValue();//贷款上浮比例调整比率
		
		//获取该机构对应县联社指定‘计算日期’下的收益率曲线map<curve_no,SCYTCZlineF>
		//Map<String,SCYTCZlineF> curvesF_map=FtpUtil.getMap_AllCurves_sfb(computeDate, xian_brNo, "2");//用试发布的收益率曲线试计算
		Map<String,SCYTCZlineF> curvesF_map=FtpUtil.getMap_AllCurves_sfb(CommonFunctions.GetCurrentTime().substring(0,10).replaceAll("-", ""), brNo, manageLvl);//
		if(curvesF_map == null || curvesF_map.size() == 0) return null;
		//获取该机构对应县联社产品定价方法组合的map
		Map<String,String[]> ftp_methodComb_map=FtpUtil.getMap_FTPMethod(brNo);
		
 		int all_ftp_total_num=0;//被选定定价的总笔数
//     	int all_ftp_success_num=0;//定价成功总笔数
 		int all_ftp_fault_num=0;//定价失败总笔数
 		double sbbal=0;
 		double zcbal=0;
 		double fzbal=0;
        double cgzcbal=0;
        double cgfzbal=0;
		//##############################################################################################
		//                  所有底层机构分组循环
		//##############################################################################################
		//所有底层机构分组循环定价，因为一次性全查询出来行数太多，超出内存无法保存 --- onceBrNum个机构为一组循环一次
		//【没用UID时：6个一组时大概花时25分钟；12个一组时，约20分钟】
		//【用UID时：34个机构一组，花时7分30秒，最大内存400M；12个一组，花时9分20秒，最大内存190M】
		int onceBrNum=12;//一次性执行(一组)查询业务记录、定价计算、并保存定价结果记录的机构数量。
		List<String[]> ftped_data_successList=new ArrayList<String[]>();//定价成功的业务记录list
		for(int m=0;m<( brNoList.size()%onceBrNum==0 ? (brNoList.size()/onceBrNum) : (brNoList.size()/onceBrNum+1) );m++){
//			List<FtpBusinessInfo> ftped_data_errorList=new ArrayList<FtpBusinessInfo>();//定价失败的业务记录list
			
			//构造一次性查询的onceBrNum个机构的sql
			String brNos_sql="(";
			for(int a=0;a<onceBrNum;a++ ){
				if((m*onceBrNum+a)<brNoList.size()){
					brNos_sql+="'"+brNoList.get(m*onceBrNum+a).toString()+"',";
				}				
			}
			brNos_sql=brNos_sql.substring(0, brNos_sql.length()-1)+")";//去掉最后一个逗号，因为brNos_sql里不可能没机构所以不用判断无逗号
			
			System.out.println("############################################");
			System.out.println("* 开始执行第 "+(m+1)+ "组机构"+brNos_sql);
			System.out.println("############################################");
			
			/*if(brNos_sql.indexOf("1801033610")<0){//测试
				break;
			}*/
			
			System.out.println("* 分账户业务数据记录查询获取中...");
			sql="select * from ftp.Ftp_Qxpp_Business t where t.BAL!=0 and t.BR_NO in "+brNos_sql;			
			List listdata = daoFactory.query1(sql, null);
			System.out.println("共查询获取 "+listdata.size()+" 条业务数据");
					
			/*if(true){//测试
				System.out.println("listdata.size()="+listdata.size());
				continue;
			}*/
			
			int ftp_once_num=listdata.size();		
			all_ftp_total_num+=ftp_once_num;
			List<FtpBusinessInfo> ftpBusinessInfoList = new ArrayList<FtpBusinessInfo>();
			if (listdata != null && listdata.size() > 0) {
				for (int i = 0; i < listdata.size(); i++) {
					//System.out.println("######  "+i+"  ##########");
					Object[] o = (Object[])listdata.get(i);
					listdata.set(i, null);//释放list循环定价过程中，该笔记录的所占内存(在list中该笔记录被使用后)
					
					FtpBusinessInfo ftpBusinessInfo = new FtpBusinessInfo();
					ftpBusinessInfo.setBrNo(o[0] == null? null : o[0].toString().trim());
					ftpBusinessInfo.setBusinessNo(o[5] == null? null : o[5].toString().trim());
					ftpBusinessInfo.setPrdtNo(o[7] == null? null : o[7].toString().trim());
					ftpBusinessInfo.setAmt(o[10] == null? null : o[10].toString().trim());
					ftpBusinessInfo.setBal(o[11] == null? null : o[11].toString().trim());
					ftpBusinessInfo.setRate(o[12] == null? null : String.valueOf(Double.valueOf(o[12].toString().trim())/100));
					ftpBusinessInfo.setTerm(o[13] == null? null : o[13].toString().trim());
					ftpBusinessInfo.setFivSts(o[20] == null? null : o[20].toString().trim());
					ftpBusinessInfoList.add(ftpBusinessInfo);
				}
			}
			
			listdata.clear(); System.gc();//释放整个list所占内存(在整个list被使用后)
			//###########################################################################
			//         开始定价
			//###########################################################################
			System.out.println("* "+ftp_once_num+" 笔业务定价计算中...");
			//对所选的业务行 依次定价 ，并将定价结果存入ftped_data_successList 与  all_ftped_data_errorList
			for(int i=0;i<ftpBusinessInfoList.size();i++){
				FtpBusinessInfo ftp_business_info=ftpBusinessInfoList.get(i);
				ftpBusinessInfoList.set(i, null);//释放list循环定价过程中，该笔记录的所占内存(在list中该笔记录被使用后)
				
				//String[] ftp_methodComb=FtpUtil.getFTPMethod_byPrdtNo(ftp_business_info.getPrdtNo(),ftp_business_info.getBrNo());
				
				
				
				String[] ftp_methodComb=ftp_methodComb_map.get(ftp_business_info.getPrdtNo());
				if(ftp_methodComb==null){//没在配置表中找到对应定价方法记录行，则不对该笔业务定价
//					ftp_business_info.setMethodName("还未配置");
//					if(all_ftped_data_errorList.size()<20000){
//						all_ftped_data_errorList.add(ftp_business_info);
//					}				
					sbbal+=Double.valueOf(ftp_business_info.getBal());
					if(ftp_business_info.getPrdtNo().indexOf("P1")!=-1) {
						zcbal+=Double.valueOf(ftp_business_info.getBal());
					}else {
						fzbal+=Double.valueOf(ftp_business_info.getBal());
					}
					all_ftp_fault_num++;
					continue;
				}
				
				
				double adjust_rate=Double.valueOf(ftp_methodComb[2]);//调整利率
				String method_no=ftp_methodComb[0];//具体定价方法编号
				int term=(ftp_business_info.getTerm()==null || ftp_business_info.getTerm().equals(""))?0:Integer.valueOf(ftp_business_info.getTerm());
				if(method_no.equals("06")){//只有‘利率代码差额法06’才使用‘参考期限’ 
					term=Integer.valueOf(ftp_methodComb[1]);//参考期限
				}
				String curve_no=ftp_methodComb[3];//使用的收益率曲线编号
				
				/*
				 * String curve_date="";//选用收益率曲线的日期条件,实现‘曲线选用严格到每日’新加。
				//如果活期存款业务，则其选用收益率曲线的日期条件为数据库系统日期
				if(("YW201").equals(ftp_business_info.getBusinessNo())){
					curve_date=String.valueOf(CommonFunctions.GetDBSysDate());
				}else{//其他业务 ,则其选用收益率曲线的日期条件为该业务发生日期<开户日期>---若发生日为空，则默认取数据库系统日期
					curve_date=(ftp_business_info.getOpnDate()==null || ftp_business_info.getOpnDate().equals(""))?String.valueOf(CommonFunctions.GetDBSysDate()):ftp_business_info.getOpnDate();
				}*/
				if(!"无".equals(curve_no) && curvesF_map.get(curve_no)==null){
					//ftp_business_info.setCurveName("还未发布");
//					if(all_ftped_data_errorList.size()<20000){
//						all_ftped_data_errorList.add(ftp_business_info);
//					}
					sbbal+=Double.valueOf(ftp_business_info.getBal());
					if(ftp_business_info.getPrdtNo().indexOf("P1")!=-1) {
						zcbal+=Double.valueOf(ftp_business_info.getBal());
					}else {
						fzbal+=Double.valueOf(ftp_business_info.getBal());
					}
					all_ftp_fault_num++;
					continue;
				}
				double appoint_rate=Double.valueOf(ftp_methodComb[4]);//指定利率
				double appoint_delta_rate=Double.valueOf(ftp_methodComb[5]);//固定利差
				
				double ftp_price=-1;
				if(method_no.equals("01")){//## 原始期限匹配法
					ftp_price=FtpUtil.getFTPPrice_ysqxppf(term, curvesF_map.get(curve_no), adjust_rate);
				}else if(method_no.equals("02")){//## 指定利率法
					//ftp_price=FtpUtil.getFTPPrice_zdllf(term, curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
					ftp_price=appoint_rate+adjust_rate;
				}else if(method_no.equals("03")){//## 重定价期限匹配法
					/////
				}else if(method_no.equals("04")){//## 现金流法,还款周期
					ftp_price=FtpUtil.getFTPPrice_xjlf(term,30,curvesF_map.get(curve_no), adjust_rate);
				}else if(method_no.equals("05")){//## 久期法
					if(ftp_business_info.getPrdtNo().substring(0, 4).equals("P109")){//按揭贷款，还款周期
						ftp_price=FtpUtil.getFTPPrice_jqf(term,30,0,curvesF_map.get(curve_no), adjust_rate);
					}else{//固定资产,默认设置残值率为0.4，折旧周期
						ftp_price=FtpUtil.getFTPPrice_jqf(term,365,0.4,curvesF_map.get(curve_no), adjust_rate);
					}		
				}else if(method_no.equals("06")){//## 利率代码差额法
					ftp_price=FtpUtil.getFTPPrice_lldmcef(term, curvesF_map.get(curve_no), adjust_rate);
				}else if(method_no.equals("07")){//## 加权利率法
					ftp_price=FtpUtil.getFTPPrice_jqllf(curvesF_map.get(curve_no), adjust_rate);
				}else if(method_no.equals("08")){//## 固定利差法
					ftp_price=Double.parseDouble(ftp_business_info.getRate())+appoint_delta_rate+adjust_rate;
				}else{
//					ftp_business_info.setMethodName("方法"+method_no+"配置错误");
//					if(all_ftped_data_errorList.size()<20000){
//						all_ftped_data_errorList.add(ftp_business_info);
//					}
					sbbal+=Double.valueOf(ftp_business_info.getBal());
					if(ftp_business_info.getPrdtNo().indexOf("P1")!=-1) {
						zcbal+=Double.valueOf(ftp_business_info.getBal());
					}else {
						fzbal+=Double.valueOf(ftp_business_info.getBal());
					}
					all_ftp_fault_num++;
					continue;
				}
				//FTP调整
				double adjustValue = 0;
				if(ftp_methodComb[9].equals("0")) {//是否进行调整
					if(curve_no.equals("0100")) {//使用存贷款收益率曲线的产品，要进行各项调整
						double amt = (ftp_business_info.getAmt()==null||ftp_business_info.getAmt().equals(""))?Double.NaN:Double.valueOf(ftp_business_info.getAmt());
						adjustValue = FtpUtil.getCdkFtpAdjustValue(ftp_business_info.getBusinessNo(), term, ckzbjAdjustMap, ldxAdjustMap, irAdjustMap, prepayList);
					}else if(curve_no.startsWith("02")) {//使用市场收益率曲线 +流动性风险加点+敞口占用加点
						adjustValue += Double.valueOf(ftp_methodComb[7])+Double.valueOf(ftp_methodComb[8]);
					}else{//其他暂时什么也不做
						
					}
					
					//策略调整，根据产品获取对应的数据
					adjustValue += clAdjustMap.get(ftp_business_info.getPrdtNo()) == null ? 0 : clAdjustMap.get(ftp_business_info.getPrdtNo());
					
				}else {
					double amt = (ftp_business_info.getAmt()==null||ftp_business_info.getAmt().equals(""))?Double.NaN:Double.valueOf(ftp_business_info.getAmt());
					double rate = (ftp_business_info.getRate()==null||ftp_business_info.getRate().equals(""))?Double.NaN:Double.valueOf(ftp_business_info.getRate());
					adjustValue = FtpUtil.getDkAmtAdjust(ftp_business_info.getPrdtNo(), term, amt, dkAdjustArr, publicRate,rate);
				}
				ftp_price += adjustValue;
				
				//查看五级分类状态，如果是不良贷款(03、04、05)，则价格=利率			
				if(ftp_business_info.getBusinessNo().equals("YW101") && (ftp_business_info.getFivSts().equals("03")||ftp_business_info.getFivSts().equals("04")||ftp_business_info.getFivSts().equals("05"))) {
					ftp_price = Double.valueOf(ftp_business_info.getRate());
				}
				ftp_business_info.setFtpPrice(ftp_price);
				if(Double.isNaN(ftp_price)){
//					if(all_ftped_data_errorList.size()<20000){
//						all_ftped_data_errorList.add(ftp_business_info);
//					}
					if(ftp_business_info.getPrdtNo().indexOf("P1")!=-1) {
						zcbal+=Double.valueOf(ftp_business_info.getBal());
					}else {
						fzbal+=Double.valueOf(ftp_business_info.getBal());
					}
					sbbal+=Double.valueOf(ftp_business_info.getBal());
					all_ftp_fault_num++;
				}else{
					if(ftp_business_info.getPrdtNo().indexOf("P1")!=-1) {
						cgzcbal+=Double.valueOf(ftp_business_info.getBal());
					}else {
						cgfzbal+=Double.valueOf(ftp_business_info.getBal());
					}
					String[] result = new String[6];
					result[0] = ftp_business_info.getPrdtNo();
					result[1] = ftp_business_info.getAmt();
					result[2] = ftp_business_info.getBal();
					result[3] = ftp_business_info.getRate();
					result[4] = String.valueOf(ftp_business_info.getFtpPrice());
					result[5] = ftp_business_info.getBrNo();
					ftped_data_successList.add(result);
				}
				
			}
			
			ftpBusinessInfoList.clear();
			System.gc();//释放整个list所占内存(在整个list被使用后)
		}
		String ETime=CommonFunctions.GetCurrentTime();
		int costTime=CommonFunctions.GetCostTimeInSecond(STime, ETime);
		int CostFen=costTime/60;
		int CostMiao=costTime%60;
		System.out.println("耗时"+CostFen+"分"+CostMiao+"秒");		
		String ftpResultDescribe="定价完成！总共选中"+all_ftp_total_num+"笔；其中失败"+all_ftp_fault_num+"笔，失败资产余额"+zcbal+"，失败负债余额"+fzbal+"，成功资产余额"+cgzcbal+"，成功负债余额"+cgfzbal;
		System.out.println(ftpResultDescribe);
		return ftped_data_successList;
	}
	
	/**
	 * 根据机构号和机构级别，获取其对应的县联社brno
	 * 
	 * @param brNo
	 * @param manageLvl
	 * @return
	 */
	public String getXlsBrNo(String brNo, String manageLvl) {
		if (manageLvl.equals("2")) {
			return brNo;
		} else if (manageLvl.equals("1")) {
			String hsql = "from BrMst where brNo = '" + brNo + "'";
			BrMst brMst = (BrMst) daoFactory.getBean(hsql, null);
			return brMst.getSuperBrNo();// 如果是1级，获取它的父级即为它对应的县联社
		} else if (manageLvl.equals("0")) {
			String hsql = "from BrMst where brNo = '" + brNo + "'";
			BrMst brMst = (BrMst) daoFactory.getBean(hsql, null);
			String hsql2 = "from BrMst where brNo = '" + brMst.getSuperBrNo()
					+ "'";
			brMst = (BrMst) daoFactory.getBean(hsql2, null);
			return brMst.getSuperBrNo();// 如果是0级，要循环两次获取它的父级的父级即为它对应的县联社
		}
		return "";
	}
	
	/**
	 * 根据县联社brNo和要获取的机构级别，获取该联社下该级别的所有机构
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
		}
		return brMstList;
	}
}
