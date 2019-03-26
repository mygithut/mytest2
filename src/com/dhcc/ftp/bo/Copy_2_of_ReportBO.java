//package com.dhcc.ftp.bo;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.dhcc.ftp.entity.BrMst;
//import com.dhcc.ftp.entity.FtpPoolInfo;
//import com.dhcc.ftp.entity.FtpProductMethodRel;
//import com.dhcc.ftp.entity.FtpSystemInitial;
//import com.dhcc.ftp.entity.YlfxReport;
//import com.dhcc.ftp.util.CommonFunctions;
//import com.dhcc.ftp.util.FtpUtil;
//import com.dhcc.ftp.util.LrmUtil;
//
//public class Copy_2_of_ReportBO extends BaseBo {
//
//	/**
//	 * 机构总盈利分析
//	 * @param request
//	 * @param date
//	 * @param brNo
//	 * @param manageLvl 机构级别
//	 * @param assessScope 统计维度 月-1、-3、-12
//	 * @param isMx 是否查看明细
//	 * @return
//	 */
//	public List<YlfxReport> brPayOffProfile(HttpServletRequest request, String date, String superBrNo, String brNo, String manageLvl, 
//			Integer assessScope, Integer isMx) {
//		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
//		String xlsBrNo = this.getXlsBrNo(brNo, manageLvl);// 县联社
//		System.out.println("县联社：" + xlsBrNo);
//		String prcMode = getPrcModeByBrNo(xlsBrNo, manageLvl);// 所配置的资金池
//		if (prcMode == null)
//			return null;
//		request.getSession().setAttribute("prcMode", prcMode);
//		System.out.println("所配置的定价策略：" + prcMode);
//		String prdtNoZc = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "1"); // 对应资金池或期限匹配所配置的资产类产品
//		String prdtNoFz = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "2"); // 对应资金池或期限匹配所配置的负债类产品
//		System.out.println("资产资金池或期限匹配产品：" + prdtNoZc);
//		System.out.println("负债资金池或期限匹配产品：" + prdtNoFz);
//		if (prdtNoZc == null || prdtNoFz == null)
//			return null;
//		List ftpResultList = null;
//		if(!prcMode.equals("4")) {//如果不是期限匹配法
//			ftpResultList = this.getFtpResult(date, xlsBrNo, prcMode);//获取对应县联社的定价结果list
//		}
//		String date2 = CommonFunctions.dateModifyM(date, assessScope);
//		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//如果跨过当年，则只取当年的数据，从1月份开始
//	    	date2 = date2.substring(0,4)+"1231";
//		
//		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//获取所统计的天数
//	    //获取存款不能用发生日期和结束日期的查询条件
//		
//		if (isMx == 0) {// 如果不是查看子机构的盈利分析，则直接查看该机构的盈利分析
//			YlfxReport ylfxReport = new YlfxReport();
//			String brSql = LrmUtil.getBrSql(brNo);
//			ylfxReport.setBrName(brInfoDAO.getInfo(brNo).getBrName());
//			ylfxReport.setBrNo(brNo);
//			ylfxReport.setManageLvl(manageLvl);
//			if(!prcMode.equals("4")) {//不是期限匹配
//				ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, prdtNoZc, "3", assessScope, true));// 资产余额
//				System.out.println("资产余额:" + ylfxReport.getZcye());
//				ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoZc, "3", assessScope, true));// 生息率：加权平均利率
//				System.out.println("生息率:" + ylfxReport.getSxl());
//				ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//资产平均期限
//		        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
//		        ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//负债平均期限
//		        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
//		        ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, prdtNoFz, "3", assessScope, true));// 负债余额
//				System.out.println("负债余额:" + ylfxReport.getFzye());
//				ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoFz, "3", assessScope, true));// 付息率：加权平均利率
//				System.out.println("付息率:" + ylfxReport.getFxl());
//				Double[] resultPrice = this.getFtpResultPrice(ftpResultList, date, brSql, prcMode, assessScope);
//				ylfxReport.setZczyjg(resultPrice[0]);// 资产转移价格
//				System.out.println("资产转移价格:" + ylfxReport.getZczyjg());
//				ylfxReport.setFzzyjg(resultPrice[1]);// 负债转移价格
//				System.out.println("负债转移价格:" + ylfxReport.getFzzyjg());
//				ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);// 利息收入
//				System.out.println("利息收入:" + ylfxReport.getLxsr());
//				ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);// 利息支出
//				System.out.println("利息支出:" + ylfxReport.getLxzc());
//				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
//				System.out.println("资产净利差:" + ylfxReport.getZcjlc());
//				ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);// 资产转移支出
//				System.out.println("资产转移支出:" + ylfxReport.getZczyzc());
//				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
//				System.out.println("资产净收入:" + ylfxReport.getZcjsr());
//				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
//				System.out.println("负债净利差:" + ylfxReport.getFzjlc());
//				ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);// 负债转移收入
//				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
//				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// 净收入
//			}else {
//				//期限匹配：获取盈利分析时间范围内的定价周期次数
//				//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
//				double[] qxppZcValue = this.getQxppValue(brSql, prdtNoZc, date2, date);
//				ylfxReport.setZcye(qxppZcValue[5]);// 资产余额
//				System.out.println("资产余额:"+ylfxReport.getZcye());
//				ylfxReport.setSxl(qxppZcValue[0]);// 生息率
//				System.out.println("生息率:"+ylfxReport.getSxl());
//		        ylfxReport.setZcpjqx(qxppZcValue[1]);//资产平均期限
//		        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
//		        ylfxReport.setZczyjg(qxppZcValue[2]);// 资产转移价格
//				System.out.println("资产转移价格:"+ylfxReport.getZczyjg());
//				ylfxReport.setLxsr(qxppZcValue[3]);// 利息收入
//				System.out.println("利息收入:"+ylfxReport.getLxsr());
//				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
//				System.out.println("资产净利差:" + ylfxReport.getZcjlc());
//				ylfxReport.setZczyzc(qxppZcValue[4]);//资产转移支出
//				System.out.println("资产转移支出:" + ylfxReport.getZczyzc());
//				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
//				System.out.println("资产净收入:" + ylfxReport.getZcjsr());
//				
//				System.out.println("负债余额:"+ylfxReport.getFzye());
//				double[] qxppFzValue = this.getQxppValue(brSql, prdtNoFz, date2, date);
//				ylfxReport.setFzye(qxppFzValue[5]);// 负债余额
//				System.out.println("负债余额:"+ylfxReport.getFzye());
//				ylfxReport.setFxl(qxppFzValue[0]);// 付息率
//				System.out.println("付息率:"+ylfxReport.getFxl());
//		        ylfxReport.setFzpjqx(qxppFzValue[1]);//负债平均期限
//		        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
//		        ylfxReport.setFzzyjg(qxppFzValue[2]);// 负债转移价格
//				System.out.println("负债转移价格:"+ylfxReport.getFzzyjg());
//				ylfxReport.setLxzc(qxppFzValue[3]);// 利息支出
//				System.out.println("利息支出:"+ylfxReport.getLxzc());
//				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
//				System.out.println("负债净利差:" + ylfxReport.getFzjlc());
//				ylfxReport.setFzzysr(qxppFzValue[4]);//负债转移支出
//				System.out.println("负债转移支出:" + ylfxReport.getZczyzc());
//				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
//				System.out.println("负债净收入:" + ylfxReport.getZcjsr());
//				
//				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// 净收入
//			}
//			ylfxReportList.add(ylfxReport);
//		} else {
//			String hsql = "from BrMst where superBrNo = '" + brNo + "' order by brNo";// 获取该机构的所有下级机构
//			List<BrMst> list = daoFactory.query(hsql, null);
//			for (int i = 0; i < list.size(); i++) {
//				BrMst brMst = list.get(i);
//				String brSql = LrmUtil.getBrSql(brMst.getBrNo());
//				YlfxReport ylfxReport = new YlfxReport();
//				ylfxReport.setBrName(brMst.getBrName());
//				ylfxReport.setBrNo(brMst.getBrNo());
//				ylfxReport.setManageLvl(brMst.getManageLvl());
//				if(!prcMode.equals("4")) {//不是期限匹配
//					ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, prdtNoZc, "3", assessScope, true));// 资产余额
//					System.out.println("资产余额:" + ylfxReport.getZcye());
//					ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoZc, "3", assessScope, true));// 生息率：加权平均利率
//					System.out.println("生息率:" + ylfxReport.getSxl());
//					ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, prdtNoFz, "3", assessScope, true));// 负债余额
//					System.out.println("负债余额:" + ylfxReport.getFzye());
//					ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoFz, "3", assessScope, true));// 付息率：加权平均利率
//					System.out.println("付息率:" + ylfxReport.getFxl());
//					ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//资产平均期限
//			        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
//			        ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//负债平均期限
//			        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
//			        Double[] resultPrice = this.getFtpResultPrice(ftpResultList, date, brSql, prcMode, assessScope);
//					ylfxReport.setZczyjg(resultPrice[0]);// 资产转移价格
//					System.out.println("资产转移价格:" + ylfxReport.getZczyjg());
//					ylfxReport.setFzzyjg(resultPrice[1]);// 负债转移价格
//					System.out.println("负债转移价格:" + ylfxReport.getFzzyjg());
//					ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);// 利息收入
//					System.out.println("利息收入:" + ylfxReport.getLxsr());
//					ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);// 利息支出
//					System.out.println("利息支出:" + ylfxReport.getLxzc());
//					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
//					ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);// 资产转移支出
//					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
//					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
//					ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);// 负债转移收入
//					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
//					ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// 净收入
//				}else {					
//					//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
//					double[] qxppZcValue = this.getQxppValue(brSql, prdtNoZc, date2, date);
//					ylfxReport.setZcye(qxppZcValue[5]);// 资产余额
//					System.out.println("资产余额:"+ylfxReport.getZcye());
//					ylfxReport.setSxl(qxppZcValue[0]);// 生息率
//					System.out.println("生息率:"+ylfxReport.getSxl());
//			        ylfxReport.setZcpjqx(qxppZcValue[1]);//资产平均期限
//			        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
//			        ylfxReport.setZczyjg(qxppZcValue[2]);// 资产转移价格
//					System.out.println("资产转移价格:"+ylfxReport.getZczyjg());
//					ylfxReport.setLxsr(qxppZcValue[3]);// 利息收入
//					System.out.println("利息收入:"+ylfxReport.getLxsr());
//					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
//					System.out.println("资产净利差:" + ylfxReport.getZcjlc());
//					ylfxReport.setZczyzc(qxppZcValue[4]);//资产转移支出
//					System.out.println("资产转移支出:" + ylfxReport.getZczyzc());
//					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
//					System.out.println("资产净收入:" + ylfxReport.getZcjsr());
//					
//					//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
//					double[] qxppFzValue = this.getQxppValue(brSql, prdtNoFz, date2, date);
//					ylfxReport.setFzye(qxppFzValue[5]);// 负债余额
//					System.out.println("负债余额:"+ylfxReport.getFzye());
//					ylfxReport.setFxl(qxppFzValue[0]);// 付息率
//					System.out.println("付息率:"+ylfxReport.getFxl());
//			        ylfxReport.setFzpjqx(qxppFzValue[1]);//负债平均期限
//			        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
//			        ylfxReport.setFzzyjg(qxppFzValue[2]);// 负债转移价格
//					System.out.println("负债转移价格:"+ylfxReport.getFzzyjg());
//					ylfxReport.setLxzc(qxppFzValue[3]);// 利息支出
//					System.out.println("利息支出:"+ylfxReport.getLxzc());
//					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
//					System.out.println("负债净利差:" + ylfxReport.getFzjlc());
//					ylfxReport.setFzzysr(qxppFzValue[4]);//负债转移支出
//					System.out.println("负债转移支出:" + ylfxReport.getZczyzc());
//					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
//					System.out.println("负债净收入:" + ylfxReport.getZcjsr());
//					
//					ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// 净收入
//				}
//				
//				ylfxReportList.add(ylfxReport);
//			}
//		}
//		System.out.println("????????????????????");
//		return ylfxReportList;
//	}
//
//	/**
//	 * 业务条线盈利分析--- 所有业务类型的数据获取
//	 * @param request
//	 * @param date
//	 * @param brNo
//	 * @param manageLvl
//	 * @param assessScope
//	 * @return
//	 */
//	public List<YlfxReport> busPayOffProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope) {
//		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
//		String xlsBrNo = this.getXlsBrNo(brNo, manageLvl);// 县联社
//		System.out.println("县联社：" + xlsBrNo);
//		String prcMode = getPrcModeByBrNo(xlsBrNo, manageLvl);// 所配置的资金池
//		if (prcMode == null) return null;
//		System.out.println("所配置的资金池：" + prcMode);
//		request.getSession().setAttribute("prcMode", prcMode);
//		List<FtpPoolInfo> ftpPoolInfoList = new ArrayList<FtpPoolInfo>();
//		if(!prcMode.equals("4")) {//如果不是期限匹配法，则获取对应的定价策略所配置的资金池
//			ftpPoolInfoList = this.getFtpPoolInfoList(xlsBrNo, prcMode);// 对应的定价策略所配置资金池
//			if (ftpPoolInfoList == null || ftpPoolInfoList.size() < 1) return null;
//		}
//		String[] businessNo = new String[14];//产品所对应的业务编号ftp_product_method_rel表
//		// 贷款
//		businessNo[0]  = "'YW101'";
//		// 存放中央银行款项
//		businessNo[1] = "'YW102'";
//		// 存放同业
//		businessNo[2] = "'YW103'";
//		// 拆放同业
//		businessNo[3] = "'YW104'";
//		// 投资业务
//		businessNo[4] = "'YW105'";
//		// 买入贩售
//		businessNo[5] = "'YW106'";
//		// 现金
//		businessNo[6] = "'YW107'";
//		// 其他资产
//		businessNo[7] = "'YW108'";
//		// 存款
//		businessNo[8]  = "'YW201','YW202','YW203','YW204'";
//		// 同业拆入
//		businessNo[9] = "'YW205'";
//		// 向中央银行借款
//		businessNo[10] = "'YW206'";
//		// 贴现负债
//		businessNo[11] = "'YW207'";
//		// 卖出回购
//		businessNo[12] = "'YW208'";
//		// 其他负债
//		businessNo[13] = "'YW209'";
//		
//		String brSql = LrmUtil.getBrSql(brNo);
//		
//		ylfxReportList = this.getYlfxReportList(brSql, xlsBrNo, date, prcMode, assessScope, businessNo, ftpPoolInfoList);
//		
//		return ylfxReportList;
//	}
//	
//	/**
//	 * 业务条线盈利分析---某一大业务(目前只支持：存款、贷款、投资业务)下所有产品盈利分析 数据获取
//	 * @param request
//	 * @param date
//	 * @param brNo
//	 * @param manageLvl
//	 * @param assessScope
//	 * @param prdtType
//	 * @return
//	 */
//	public List<YlfxReport> prdtPayOffProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope, String prdtType) {
//		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
//		String xlsBrNo = this.getXlsBrNo(brNo, manageLvl);// 县联社
//		System.out.println("县联社：" + xlsBrNo);
//		String prcMode = getPrcModeByBrNo(xlsBrNo, manageLvl);// 所配置的资金池
//		if (prcMode == null) return null;
//		System.out.println("所配置的资金池：" + prcMode);
//		request.getSession().setAttribute("prcMode", prcMode);
//		List<FtpPoolInfo> ftpPoolInfoList = new ArrayList<FtpPoolInfo>();
//		if(!prcMode.equals("4")) {//如果不是期限匹配法，则获取对应的定价策略所配置的资金池
//			ftpPoolInfoList = this.getFtpPoolInfoList(xlsBrNo, prcMode);// 对应的定价策略所配置资金池
//			if (ftpPoolInfoList == null || ftpPoolInfoList.size() < 1) return null;
//		}
//		String[][] prdtNo = null;//产品编号+产品名称
//		if(prdtType.equals("ck")) {//存款
//			prdtNo = new String[19][2];
//			prdtNo[0][0] = "'P2010','P2038'";
//			prdtNo[0][1] = "个人活期存款";
//			prdtNo[1][0] = "'P2011','P2012','P2039','P2040'";
//			prdtNo[1][1] = "个人定期存款1年期以下(不含1年)";
//			prdtNo[2][0] = "'P2013','P2014','P2017','P2020','P2023','P2028','P2041','P2043','P2046','P2048','P2051'";
//			prdtNo[2][1] = "个人定期存款1-2年期";
//			prdtNo[3][0] = "'P2015','P2016','P2018','P2019','P2021','P2022','P2024','P2025','P2029','P2030','P2042','P2044','P2045','P2047','P2049','P2050','P2052','P2053'";
//			prdtNo[3][1] = "个人定期存款3-5年期";
//			prdtNo[4][0] = "'P2056'";
//			prdtNo[4][1] = "个人信用卡存款";
//			prdtNo[5][0] = "'P2026','P2054'";
//			prdtNo[5][1] = "个人定活两便存款";
//			prdtNo[6][0] = "'P2027'";
//			prdtNo[6][1] = "个人通知存款";
//			prdtNo[7][0] = "'P2001','P2031'";
//			prdtNo[7][1] = "单位活期存款";
//			prdtNo[8][0] = "'P2002','P2003','P2032','P2033'";
//			prdtNo[8][1] = "单位定期存款1年以下(不含1年)";
//			prdtNo[9][0] = "'P2004','P2005','P2034','P2035'";
//			prdtNo[9][1] = "单位定期存款1-2年期";
//			prdtNo[10][0] = "'P2006','P2007','P2036','P2037'";
//			prdtNo[10][1] = "单位定期存款3-5年期";
//			prdtNo[11][0] = "'P2057'";
//			prdtNo[11][1] = "财政性存款";
//			prdtNo[12][0] = "'P2059','P2060','P2061'";
//			prdtNo[12][1] = "应解汇款、汇出汇款、开出本票";
//			prdtNo[13][0] = "'P2062'";
//			prdtNo[13][1] = "保证金存款";
//			prdtNo[14][0] = "'P2055'";
//			prdtNo[14][1] = "单位信用卡存款";
//			prdtNo[15][0] = "'P2008'";
//			prdtNo[15][1] = "单位协议存款";
//			prdtNo[16][0] = "'P2009'";
//			prdtNo[16][1] = "单位通知存款";
//			prdtNo[17][0] = "'P2065'";
//			prdtNo[17][1] = "同业存放款项";
//			prdtNo[18][0] = "'P2066'";
//			prdtNo[18][1] = "系统内存放款项";
//		}else if(prdtType.equals("dk")) {//存款
//			prdtNo = new String[23][2];
//			prdtNo[0][0] = "'P1018','P1023','P1028','P1033','P1038','P1043'";
//			prdtNo[0][1] = "农户贷款6个月(含)以内";
//			prdtNo[1][0] = "'P1019','P1024','P1029','P1034','P1039','P1044'";
//			prdtNo[1][1] = "农户贷款6个月-1年(含1年)";
//			prdtNo[2][0] = "'P1020','P1025','P1030','P1035','P1040','P1045'";
//			prdtNo[2][1] = "农户贷款1年-3年(含3年)";
//			prdtNo[3][0] = "'P1021','P1026','P1031','P1036','P1041','P1046'";
//			prdtNo[3][1] = "农户贷款3年-5年(含5年)";
//			prdtNo[4][0] = "'P1022','P1027','P1032','P1037','P1042','P1047'";
//			prdtNo[4][1] = "农户贷款5年以上(不含5年)";
//			prdtNo[5][0] = "'P1048','P1053'";
//			prdtNo[5][1] = "农村经济组织贷款6个月(含)以内";
//			prdtNo[6][0] = "'P1049','P1054'";
//			prdtNo[6][1] = "农村经济组织贷款6个月-1年(含1年)";
//			prdtNo[7][0] = "'P1050','P1055'";
//			prdtNo[7][1] = "农村经济组织贷款1年-3年(含3年)";
//			prdtNo[8][0] = "'P1051','P1056'";
//			prdtNo[8][1] = "农村经济组织贷款3年-5年(含5年)";
//			prdtNo[9][0] = "'P1052','P1057'";
//			prdtNo[9][1] = "农村经济组织贷款5年以上(不含5年)";
//			prdtNo[10][0] = "'P1058','P1063','P1068'";
//			prdtNo[10][1] = "农村企业贷款6个月(含)以内";
//			prdtNo[11][0] = "'P1059','P1064','P1069'";
//			prdtNo[11][1] = "农村企业贷款6个月-1年(含1年)";
//			prdtNo[12][0] = "'P1060','P1065','P1070'";
//			prdtNo[12][1] = "农村企业贷款1年-3年(含3年)";
//			prdtNo[13][0] = "'P1061','P1066','P1071'";
//			prdtNo[13][1] = "农村企业贷款3年-5年(含5年)";
//			prdtNo[14][0] = "'P1062','P1067','P1072'";
//			prdtNo[14][1] = "农村企业贷款5年以上(不含5年)";
//			prdtNo[15][0] = "'P1073','P1078','P1083','P1088','P1093','P1098'";
//			prdtNo[15][1] = "非农贷款6个月(含)以内";
//			prdtNo[16][0] = "'P1074','P1079','P1084','P1089','P1094','P1099'";
//			prdtNo[16][1] = "非农贷款6个月-1年(含1年)";
//			prdtNo[17][0] = "'P1075','P1080','P1085','P1090','P1095','P1100'";
//			prdtNo[17][1] = "非农贷款1年-3年(含3年)";
//			prdtNo[18][0] = "'P1076','P1081','P1086','P1091','P1096','P1101'";
//			prdtNo[18][1] = "非农贷款3年-5年(含5年)";
//			prdtNo[19][0] = "'P1077','P1082','P1087','P1092','P1097','P1102'";
//			prdtNo[19][1] = "非农贷款5年以上(不含5年)";
//			prdtNo[20][0] = "'P1103'";
//			prdtNo[20][1] = "信用卡透支";
//			prdtNo[21][0] = "'P1104'";
//			prdtNo[21][1] = "贴现资产";
//			prdtNo[22][0] = "'P1106'";
//			prdtNo[22][1] = "垫款";
//		}else if(prdtType.equals("tzyw")) {//投资业务
//			prdtNo = new String[4][2]; 
//			prdtNo[0][0] = "'P1011'";
//			prdtNo[0][1] = "交易性金融资产";
//			prdtNo[1][0] = "'P1114'";
//			prdtNo[1][1] = "持有至到期投资";
//			prdtNo[2][0] = "'P1116'";
//			prdtNo[2][1] = "可供出售金融资产";
//			prdtNo[3][0] = "'P1123'";
//			prdtNo[3][1] = "应收款项类投资";
//		}
//		String brSql = LrmUtil.getBrSql(brNo);
//		ylfxReportList = this.getYlfxReportListByPrdtNo(brSql, xlsBrNo, date, prcMode, assessScope, prdtNo, ftpPoolInfoList);
//		
//		return ylfxReportList;
//	}
//	
//	/**
//	 * 机构盈利排名
//	 * @param request
//	 * @param date
//	 * @param brNo 操作员所属机构
//	 * @param manageLvl 机构的级别
//	 * @param brCountLvl 机构统计级别
//	 * @param assessScope 统计维度
//	 * @return
//	 */
//	public List<YlfxReport> brPayOffRanking(HttpServletRequest request, String date, String brNo, String manageLvl, String brCountLvl, Integer assessScope) {
//		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
//		String xlsBrNo = brNo;// 县联社
//		System.out.println("县联社：" + xlsBrNo);
//		String prcMode = getPrcModeByBrNo(xlsBrNo, manageLvl);// 所配置的资金池
//		if (prcMode == null)
//			return null;
//		System.out.println("所配置的资金池：" + prcMode);
//		request.getSession().setAttribute("prcMode", prcMode);
//		String prdtNoZc = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "1"); // 对应资金池所配置的资产类产品
//		String prdtNoFz = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "2"); // 对应资金池所配置的负债类产品
//		System.out.println("资产资金池产品：" + prdtNoZc);
//		System.out.println("负债资金池产品：" + prdtNoFz);
//		if (prdtNoZc == null || prdtNoFz == null)
//			return null;
//		List ftpResultList = null;
//		if(!prcMode.equals("4")) {//如果不是期限匹配法
//			ftpResultList = this.getFtpResult(date, xlsBrNo, prcMode);//获取对应县联社的定价结果list
//		}
//		//根据机构级别获取该县联社下该级别下的所有机构
//		List<BrMst> brMstList = getBrMstList(brNo, brCountLvl);
//		String date2 = CommonFunctions.dateModifyM(date, assessScope);
//		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//如果跨过当年，则只取当年的数据，从1月份开始
//	    	date2 = date2.substring(0,4)+"1231";
//		
//		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//获取所统计的天数
//		for(BrMst brMst : brMstList) {
//			String brSql = LrmUtil.getBrSql(brMst.getBrNo());
//			YlfxReport ylfxReport = new YlfxReport();
//			ylfxReport.setBrName(brMst.getBrName());
//			ylfxReport.setBrNo(brMst.getBrNo());
//			if(!prcMode.equals("4")) {
//				ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, prdtNoZc, "3", assessScope, true));// 资产余额
//				System.out.println("资产余额:" + ylfxReport.getZcye());
//				ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoZc, "3", assessScope, true));// 生息率：加权平均利率
//				System.out.println("生息率:" + ylfxReport.getSxl());
//				ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, prdtNoFz, "3", assessScope, true));// 负债余额
//				System.out.println("负债余额:" + ylfxReport.getFzye());
//				ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, prdtNoFz, "3", assessScope, true));// 付息率：加权平均利率
//				System.out.println("付息率:" + ylfxReport.getFxl());
//				ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//资产平均期限
//		        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
//		        ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//负债平均期限
//		        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
//		        Double[] resultPrice = this.getFtpResultPrice(ftpResultList, date, brSql, prcMode, assessScope);
//				ylfxReport.setZczyjg(resultPrice[0]);// 资产转移价格
//				System.out.println("资产转移价格:" + ylfxReport.getZczyjg());
//				ylfxReport.setFzzyjg(resultPrice[1]);// 负债转移价格
//				System.out.println("负债转移价格:" + ylfxReport.getFzzyjg());
//				ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);// 利息收入
//				System.out.println("利息收入:" + ylfxReport.getLxsr());
//				ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);// 利息支出
//				System.out.println("利息支出:" + ylfxReport.getLxzc());
//				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
//				ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);// 资产转移支出
//				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
//				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
//				ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);// 负债转移收入
//				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
//				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// 净收入
//			}else {
//				//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额 --期限匹配
//				double[] qxppZcValue = this.getQxppValue(brSql, prdtNoZc, date2, date);
//				ylfxReport.setZcye(qxppZcValue[5]);// 资产余额
//				System.out.println("资产余额:"+ylfxReport.getZcye());
//				ylfxReport.setSxl(qxppZcValue[0]);// 生息率
//				System.out.println("生息率:"+ylfxReport.getSxl());
//		        ylfxReport.setZcpjqx(qxppZcValue[1]);//资产平均期限
//		        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
//		        ylfxReport.setZczyjg(qxppZcValue[2]);// 资产转移价格
//				System.out.println("资产转移价格:"+ylfxReport.getZczyjg());
//				ylfxReport.setLxsr(qxppZcValue[3]);// 利息收入
//				System.out.println("利息收入:"+ylfxReport.getLxsr());
//				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
//				System.out.println("资产净利差:" + ylfxReport.getZcjlc());
//				ylfxReport.setZczyzc(qxppZcValue[4]);//资产转移支出
//				System.out.println("资产转移支出:" + ylfxReport.getZczyzc());
//				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
//				System.out.println("资产净收入:" + ylfxReport.getZcjsr());
//				
//				//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
//				double[] qxppFzValue = this.getQxppValue(brSql, prdtNoFz, date2, date);
//				ylfxReport.setFzye(qxppFzValue[5]);// 负债余额
//				System.out.println("负债余额:"+ylfxReport.getFzye());
//				ylfxReport.setFxl(qxppFzValue[0]);// 付息率
//				System.out.println("付息率:"+ylfxReport.getFxl());
//		        ylfxReport.setFzpjqx(qxppFzValue[1]);//负债平均期限
//		        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
//		        ylfxReport.setFzzyjg(qxppFzValue[2]);// 负债转移价格
//				System.out.println("负债转移价格:"+ylfxReport.getFzzyjg());
//				ylfxReport.setLxzc(qxppFzValue[3]);// 利息支出
//				System.out.println("利息支出:"+ylfxReport.getLxzc());
//				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
//				System.out.println("负债净利差:" + ylfxReport.getFzjlc());
//				ylfxReport.setFzzysr(qxppFzValue[4]);//负债转移支出
//				System.out.println("负债转移支出:" + ylfxReport.getZczyzc());
//				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
//				System.out.println("负债净收入:" + ylfxReport.getZcjsr());
//				
//				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// 净收入
//			}
//			ylfxReportList.add(ylfxReport);
//		}
//		//对ylfxReportList按净收入大小进行降序排序
//		Collections.sort(ylfxReportList, new Comparator<YlfxReport>() {
//			 public int compare(YlfxReport arg0, YlfxReport arg1) {
//				 return arg1.getJsr().compareTo(arg0.getJsr());
//				 }
//			 }
//		);
//		return ylfxReportList;
//	}
//	/**
//	 * 资金中心盈利分析
//	 * @param request
//	 * @param date
//	 * @param brNo
//	 * @param manageLvl
//	 * @param assessScope
//	 * @return
//	 */
//	public YlfxReport financialCenterLCProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope) {
//		String xlsBrNo = this.getXlsBrNo(brNo, manageLvl);// 县联社
//		System.out.println("县联社：" + xlsBrNo);
//		String prcMode = getPrcModeByBrNo(xlsBrNo, manageLvl);// 所配置的资金池
//		if (prcMode == null)
//			return null;
//		System.out.println("所配置的资金池：" + prcMode);
//		request.getSession().setAttribute("prcMode", prcMode);
//		String prdtNoZc = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "1"); // 对应资金池所配置的资产类产品
//		String prdtNoFz = this.getPrdtNoByPrcMode(xlsBrNo, prcMode, "2"); // 对应资金池所配置的负债类产品
//		System.out.println("资产资金池产品：" + prdtNoZc);
//		System.out.println("负债资金池产品：" + prdtNoFz);
//		if (prdtNoZc == null || prdtNoFz == null)
//			return null;
//		List ftpResultList = null;
//		if(!prcMode.equals("4")) {//如果不是期限匹配法
//			ftpResultList = this.getFtpResult(date, xlsBrNo, prcMode);//获取对应县联社的定价结果list
//		}
//		String date2 = CommonFunctions.dateModifyM(date, assessScope);
//		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//如果跨过当年，则只取当年的数据，从1月份开始
//	    	date2 = date2.substring(0,4)+"1231";
//		
//		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//获取所统计的天数
//	    YlfxReport ylfxReport = new YlfxReport();
//		String brSql = LrmUtil.getBrSql(brNo);
//		ylfxReport.setBrName(brInfoDAO.getInfo(brNo).getBrName());
//		ylfxReport.setBrNo(brNo);
//		ylfxReport.setManageLvl(manageLvl);
//		if(!prcMode.equals("4")) {
//			ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, prdtNoZc, "3", assessScope, true));// 资产余额
//			System.out.println("资产余额:" + ylfxReport.getZcye());
//			ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, prdtNoFz, "3", assessScope, true));// 负债余额
//			System.out.println("负债余额:" + ylfxReport.getFzye());
//			Double[] resultPrice = this.getFtpResultPrice(ftpResultList, date, brSql, prcMode, assessScope);
//			ylfxReport.setZczyjg(resultPrice[0]);// 资产转移价格
//			System.out.println("资产转移价格:" + ylfxReport.getZczyjg());
//			ylfxReport.setFzzyjg(resultPrice[1]);// 负债转移价格
//			System.out.println("负债转移价格:" + ylfxReport.getFzzyjg());
//			ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//资产平均期限
//	        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
//	        ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//负债平均期限
//	        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
//	        ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);// 资金中心的资产转移收入=机构的资产转移支出
//			System.out.println("资产转移收入:" + ylfxReport.getZczyzc());
//			ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);// 资金中心的负债转移支出=机构的负债转移收入
//			System.out.println("负债转移支出:" + ylfxReport.getFzzysr());
//		}else {//如果是期限匹配
//			//期限匹配：获取盈利分析时间范围内的定价周期次数
//			//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
//			double[] qxppZcValue = this.getQxppValue(brSql, prdtNoZc, date2, date);
//			//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入--期限匹配
//			double[] qxppFzValue = this.getQxppValue(brSql, prdtNoFz, date2, date);
//			ylfxReport.setZcye(qxppZcValue[5]);// 资产余额
//			System.out.println("资产余额:"+ylfxReport.getZcye());
//			ylfxReport.setZczyjg(qxppZcValue[2]);// 资产转移价格
//			System.out.println("资产转移价格:"+ylfxReport.getZczyjg());
//			ylfxReport.setFzye(qxppFzValue[5]);// 负债余额
//			System.out.println("负债余额:"+ylfxReport.getFzye());
//			ylfxReport.setFzzyjg(qxppFzValue[2]);// 负债转移价格
//			System.out.println("负债转移价格:"+ylfxReport.getFzzyjg());
//			ylfxReport.setZcpjqx(qxppZcValue[1]);//资产平均期限
//	        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
//	        ylfxReport.setFzpjqx(qxppFzValue[1]);//负债平均期限
//	        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
//	        ylfxReport.setZczyzc(qxppZcValue[4]);// 资金中心的资产转移收入=机构的资产转移支出
//			System.out.println("资产转移收入:" + ylfxReport.getZczyzc());
//			ylfxReport.setFzzysr(qxppFzValue[4]);// 资金中心的负债转移支出=机构的负债转移收入
//			System.out.println("负债转移支出:" + ylfxReport.getFzzysr());
//		}
//		
//		return ylfxReport;
//	}
//	/**
//	 * 根据机构获取它对应资金池所配置的产品
//	 * 
//	 * @param brNo
//	 *            县联社机构号
//	 * @param prcMode
//	 *            资金池
//	 * @param poolType
//	 *            资金池资产类型:资产/负债
//	 * @return
//	 */
//	public String getPrdtNoByPrcMode(String brNo, String prcMode, String poolType) {
//		StringBuffer prdtNo = new StringBuffer();
//		if(!prcMode.equals("4")) {//单双多资金池，期限匹配和单双多定价查询方法不同，查不同的数据表
//			String hsql = "from FtpPoolInfo where prcMode = '" + prcMode + "' and brNo = '" + brNo + "'";
//			if (!prcMode.equals("1")) {// 如果是单资金池，无法根据poolType来区分资产还是负债
//				hsql += " and poolType = '" + poolType + "'";
//			}
//			List<FtpPoolInfo> poolList = daoFactory.query(hsql, null);
//			if (poolList == null || poolList.size() < 1) {
//				return null;// 对应的定价策略未配置资金池
//			} else {
//				if (prcMode.equals("1")) {// 如果是单资金池，根据ContentObject里prdtNo来区分是资产还是负债，P1打头为资产，P2打头为负债
//					FtpPoolInfo ftpPoolInfo = poolList.get(0);
//					String contentObject = ftpPoolInfo.getContentObject();
//					String[] contentObjects = contentObject.split("\\+");
//					for (int i = 0; i < contentObjects.length; i++) {
//						if (contentObjects[i].indexOf("P" + poolType) != -1)
//							prdtNo.append(contentObjects[i] + ",");
//					}
//				} else {
//					for (FtpPoolInfo ftpPoolInfo : poolList) {
//						prdtNo.append(ftpPoolInfo.getContentObject() + ",");
//					}
//				}
//			}
//		}else {//期限匹配法
//			String hsql = "from FtpProductMethodRel t where t.productNo like 'P"+poolType+"%' " +
//					"and t.brNo = '"+brNo+"'";
//			List<FtpProductMethodRel> ftpProductMethodRelList = daoFactory.query(hsql, null);
//			for(FtpProductMethodRel ftpProductMethodRel : ftpProductMethodRelList) {
//				prdtNo.append("'" + ftpProductMethodRel.getProductNo() + "',");
//			}
//		}
//		if (prdtNo.lastIndexOf(",") == prdtNo.length() - 1)
//			prdtNo = prdtNo.deleteCharAt(prdtNo.length() - 1);
//		return prdtNo.toString().replace("+", ",");
//	}
//
//	/**
//	 * 获取某个机构所配置的资金池
//	 * 
//	 * @param brNo
//	 * @param manageLvl
//	 * @return
//	 */
//	public String getPrcModeByBrNo(String brNo, String manageLvl) {
//		String hsql = "from FtpSystemInitial where brNo = '" + brNo
//				+ "' and setValidMark = '1'";
//		FtpSystemInitial ftpSystemInitial = (FtpSystemInitial) daoFactory.getBean(hsql, null);
//		return ftpSystemInitial == null ? null : ftpSystemInitial.getSetResult();
//	}
//
//	/**
//	 * 获取机构对应的资金转移价格，资产+负债
//	 * 
//	 * @param list 定价结果list
//	 * @param date
//	 * @param brSql
//	 * @param prcMode
//	 *            资金池
//	 * @param assessScope
//	 *            统计维度
//	 * @return
//	 */
//	public Double[] getFtpResultPrice(List list, String date, String brSql, String prcMode, Integer assessScope) {
//		Double[] resultPrice = {0.0, 0.0};// 资产+负债
//		if(list == null || list.size() < 1) {
//			return resultPrice;
//		}
//		if (prcMode.equals("1")) {// 如果是单资金池，则直接获取资金转移价格,资产转移价格和负债转移价格为同一个
//			Object obj = list.get(0);
//			Object[] o = (Object[]) obj;
//			resultPrice[0] = Double.valueOf(o[0].toString());
//			resultPrice[1] = Double.valueOf(o[0].toString());
//		} else if (prcMode.equals("2")) {// 如果是双资金池，根据pool_no获取转移价格，pool_no=201为资产，pool_no为202为负债
//			for (int i = 0; i < list.size(); i++) {
//				Object obj = list.get(i);
//				Object[] o = (Object[]) obj;
//				if (o[1].equals("201")) {// pool_no=201为资产
//					resultPrice[0] = Double.valueOf(o[0].toString());
//				} else if (o[1].equals("202")) {// pool_no为202为负债
//					resultPrice[1] = Double.valueOf(o[0].toString());
//				}
//			}
//		} else if (prcMode.equals("3")) {// 如果是多资金池，则对多个池子加权平均获取转移价格
//			double zc = 0.0, zcye = 0.0, fz = 0.0, fzye = 0.0;
//			for (int i = 0; i < list.size(); i++) {
//				Object obj = list.get(i);
//				Object[] o = (Object[]) obj;
//				String prdtNo = o[3].toString().replace("+", ",");
//				System.out.println("prdtNo"+prdtNo);
//				double ye = FtpUtil.getAverageAmount(brSql, date, prdtNo, "3", assessScope, true);
//				if (o[2].equals("1")) {// 资产
//					zc += ye * Double.valueOf(o[0].toString());
//					zcye += ye;
//				} else if (o[2].equals("2")) {// 负债
//					fz += ye * Double.valueOf(o[0].toString());
//					fzye += ye;
//				}
//			}
//			resultPrice[0] = zc / zcye;
//			resultPrice[1] = fz / fzye;
//		}
//		resultPrice[0] = (resultPrice[0].isInfinite() || resultPrice[0].isNaN()) ? 0.0 : resultPrice[0];
//		resultPrice[1] = (resultPrice[1].isInfinite() || resultPrice[1].isNaN()) ? 0.0 : resultPrice[1];
//
//		return resultPrice;
//
//	}
//	
//	/**
//	 * 获取县联社定价结果相关数据
//	 * @param date
//	 * @param xlsBrNo
//	 * @param prcMode
//	 * @return
//	 */
//	public List getFtpResult(String date, String xlsBrNo, String prcMode) {
//		String sql = "select * from (select t.Result_price, t.pool_no, f.pool_type, f.Content_Object, f.prc_mode, "
//			+ "row_number() over(partition by t.pool_no order by t.RES_DATE desc,T.RESULT_ID desc ) rn from ftp.Ftp_Result t "
//			+ "left join ftp.ftp_pool_info f on t.prc_mode = f.prc_mode and t.br_no = f.br_no and t.pool_no = f.pool_no "
//			+ "where t.prc_Mode ='" + prcMode
//			+ "' and t.cur_no = '01' and t.br_no = '" + xlsBrNo + "' and to_date(t.res_date,'yyyyMMdd') <= to_date('"+date+"','yyyyMMdd') ";
//	    if (prcMode.equals("3")) {
//		     sql += " and t.pool_no in (select pool_no from ftp.ftp_pool_info where prc_mode='3' and br_no=t.br_no)";
//	    }
//	    sql += ") where rn=1 order by pool_type, pool_no";
//	    List list = daoFactory.query1(sql, null);
//	    return list;
//	}
//
//	/**
//	 * 获取机构对应某类业务的资金转移价格
//	 * 
//	 * @return
//	 */
//	public Double getFtpResultPrice(String date, String brSql, String xlsBrNo,
//			String prcMode, String prdtNo, Integer assessScope) {
//		Double resultPrice = null;
//		String sql = "select * from (select t.Result_price, t.pool_no, f.pool_type, f.Content_Object, "
//				+ "row_number() over(partition by t.pool_no order by t.RES_DATE desc,T.RESULT_ID desc ) rn from ftp.Ftp_Result t "
//				+ "left join ftp.ftp_pool_info f on t.prc_mode = f.prc_mode and t.br_no = f.br_no and t.pool_no = f.pool_no "
//				+ "where t.prc_Mode ='" + prcMode + "' and t.cur_no = '01' and t.br_no = '" + xlsBrNo + "' and to_date(t.res_date,'yyyyMMdd') <= to_date('"+date+"','yyyyMMdd') ";
//		if (prcMode.equals("3")) {
//			sql += " and t.pool_no in (select pool_no from ftp.ftp_pool_info where prc_mode='3' and br_no=t.br_no)";
//		}
//		sql += ") where rn=1 order by pool_type, pool_no";
//		List list = daoFactory.query1(sql, null);
//		if (prcMode.equals("1")) {// 如果是单资金池，则直接获取资金转移价格
//			Object obj = list.get(0);
//			Object[] o = (Object[]) obj;
//			resultPrice = Double.valueOf(o[0].toString());
//		} else if (prcMode.equals("2") || prcMode.equals("3")) {// 如果是双/多资金池，对每个产品通过余额对资金转移价格进行加权平均，获取某类业务的资金转移价格
//			double fz = 0.0;// 加权平均公式的分子
//			double fm = 0.0;// 加权平均公式的分母
//			System.out.println("prdtNo:"+prdtNo);
//			for (int i = 0; i < list.size(); i++) {// 循环每个池子
//				Object obj = list.get(i);
//				Object[] o = (Object[]) obj;
//				String[] contentObjects = o[3].toString().split("\\+");
//				
//				for (int j = 0; j < contentObjects.length; j++) {// 循环每个池子对应的产品，判断该产品是否在prdtNo中存在，如果存在，进行加权
//					if (prdtNo.indexOf(contentObjects[j]) != -1) {
//						// 获取该产品的月均余额
//						double ye = FtpUtil.getAverageAmount(brSql, date, contentObjects[j], "3", assessScope, true);
//						System.out.println("ye"+ye);
//						fz += ye * Double.valueOf(o[0].toString());
//						fm += ye;
//					}
//				}
//			}
//			resultPrice = fz / fm;
//		}
//
//		return (resultPrice.isNaN() || resultPrice.isInfinite()) ? 0.0 : resultPrice;
//
//	}
//	
//	/**
//	 * 根据业务类型编号数组 获取 数组内各个业务类型的盈利分析报表数据
//	 * @param brSql 机构查询sql
//	 * @param xlsBrNo 县联社
//	 * @param date
//	 * @param prcMode 资金池类型
//	 * @param assessScope 统计维度
//	 * @param businessNo 业务编号[]
//	 * @param ftpPoolInfoList 资金池配置list
//	 * @return
//	 */
//	public List<YlfxReport> getYlfxReportList(String brSql,String xlsBrNo, String date,String prcMode, Integer assessScope, String[] businessNo, List<FtpPoolInfo> ftpPoolInfoList) {
//		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
//		String date2 = CommonFunctions.dateModifyM(date, assessScope);
//		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//如果跨过当年，则只取当年的数据，从1月份开始
//	    	date2 = date2.substring(0,4)+"1231";
//		
//		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//获取所统计的天数
//		for(int i = 0; i < businessNo.length; i++) {
//			//根据业务编号获取对应的产品编号
//			String hsql = "from FtpProductMethodRel where businessNo in ("+businessNo[i]+") and brNo = '"+xlsBrNo+"'";
//			List<FtpProductMethodRel> ftpProductMethodRelList = daoFactory.query(hsql, null);
//			StringBuilder prdtNo = new StringBuilder();
//			for (FtpProductMethodRel ftpProductMethodRel : ftpProductMethodRelList) {
//				prdtNo.append("'" + ftpProductMethodRel.getProductNo() + "',");
//			}
//			prdtNo = prdtNo.deleteCharAt(prdtNo.length() - 1);
//			// 获取对应资金池所配置的产品
//			String settedPrdtNo = "";
//			//如果单双多资金池，要根据单双多资金池的配置进行获取
//			if(!prcMode.equals("4"))settedPrdtNo = this.getPrdtNoByPrcModeAndPrdtNo(ftpPoolInfoList, prdtNo.toString());
//			else settedPrdtNo = prdtNo.toString();
//			String businessName = ftpProductMethodRelList.get(0).getBusinessName();//非‘存款’大业务的业务只有一个，则直接取结果list的第一个的业务名字
//			if(businessNo[i].indexOf("YW201") != -1) {//‘存款’大业务，由于包含4个业务，所以直接判断如果包含"活期存款"，则业务名为存款；不从查数据库配置表获取
//				businessName = "存款";
//			}
//			System.out.println(businessName+"["+businessNo[i]+"]"+"所对应的产品"+settedPrdtNo);
//			YlfxReport ylfxReport = new YlfxReport();
//			ylfxReport.setBusinessNo(businessNo[i]);// 业务编号
//			ylfxReport.setBusinessName(businessName);// 业务名称
//			if(settedPrdtNo.equals("")) {
//				ylfxReportList.add(ylfxReport);
//				continue;
//			}
//			if(businessNo[i].indexOf("YW1") != -1) {//资产类
//				if(!prcMode.equals("4")) {
//					ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, settedPrdtNo, "3", assessScope, true));//资产余额
//					System.out.println(businessName+"-资产余额:" + ylfxReport.getZcye());
//					ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, settedPrdtNo, "3", assessScope, true));//生息率：加权平均利率
//					System.out.println(businessName+"-生息率:" + ylfxReport.getSxl());
//					ylfxReport.setZczyjg(this.getFtpResultPrice(date, brSql, xlsBrNo, prcMode, settedPrdtNo, assessScope));//资产转移价格
//					ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//资产平均期限
//			        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
//			        System.out.println(businessName+"-资产转移价格:" + ylfxReport.getZczyjg());
//					ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);//利息收入
//					System.out.println(businessName+"-利息收入:" + ylfxReport.getLxsr());
//					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());//资产净利差
//					System.out.println(businessName+"-资产净利差:" + ylfxReport.getZcjlc());
//					ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);//资产转移支出
//					System.out.println(businessName+"-资产转移支出:" + ylfxReport.getZczyzc());
//					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());//资产净收入
//					System.out.println(businessName+"-资产净收入:" + ylfxReport.getZcjsr());
//				}else {//如果是期限匹配法
//					//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
//					double[] qxppZcValue = this.getQxppValue(brSql, settedPrdtNo, date2, date);
//					ylfxReport.setZcye(qxppZcValue[5]);// 资产余额
//					System.out.println(businessName+"-资产余额:"+ylfxReport.getZcye());
//					ylfxReport.setSxl(qxppZcValue[0]);// 生息率
//					System.out.println(businessName+"-生息率:"+ylfxReport.getSxl());
//			        ylfxReport.setZcpjqx(qxppZcValue[1]);//资产平均期限
//			        System.out.println(businessName+"-资产平均期限:"+ylfxReport.getZcpjqx());
//					ylfxReport.setZczyjg(qxppZcValue[2]);// 资产转移价格
//					System.out.println(businessName+"-资产转移价格:"+ylfxReport.getZczyjg());
//					ylfxReport.setLxsr(qxppZcValue[3]);// 利息收入
//					System.out.println(businessName+"-利息收入:"+ylfxReport.getLxsr());
//					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
//					System.out.println(businessName+"-资产净利差:" + ylfxReport.getZcjlc());
//					ylfxReport.setZczyzc(qxppZcValue[4]);//资产转移支出
//					System.out.println(businessName+"-资产转移支出:" + ylfxReport.getZczyzc());
//					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
//					System.out.println(businessName+"-资产净收入:" + ylfxReport.getZcjsr());
//				}
//			}else if(businessNo[i].indexOf("YW2") != -1) {//负债类
//				if(!prcMode.equals("4")) {
//					ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, settedPrdtNo, "3", assessScope, true));//负债余额
//					System.out.println(businessName+"-负债余额:"+ylfxReport.getFzye());
//					ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, settedPrdtNo, "3", assessScope, true));//付息率：加权平均利率
//					System.out.println(businessName+"-付息率:"+ylfxReport.getFxl());
//					ylfxReport.setFzzyjg(this.getFtpResultPrice(date, brSql, xlsBrNo, prcMode, settedPrdtNo, assessScope));//资产转移价格
//					ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//负债平均期限
//			        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
//			        System.out.println(businessName+"-负债转移价格:"+ylfxReport.getFzzyjg());
//					ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);//利息支出
//					System.out.println(businessName+"-利息支出:"+ylfxReport.getLxzc());
//					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());//负债净利差
//					System.out.println(businessName+"-负债净利差:"+ylfxReport.getFzjlc());
//					ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);//负债转移收入
//					System.out.println(businessName+"-负债转移收入:"+ylfxReport.getFzzysr());
//					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());//负债净收入
//					System.out.println(businessName+"-负债净收入:"+ylfxReport.getFzjsr());
//				}else {//如果是期限匹配法
//					//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
//					double[] qxppFzValue = this.getQxppValue(brSql, settedPrdtNo, date2, date);
//					ylfxReport.setFzye(qxppFzValue[5]);// 负债余额
//					System.out.println(businessName+"-负债余额:"+ylfxReport.getFzye());
//					ylfxReport.setFxl(qxppFzValue[0]);// 付息率
//					System.out.println(businessName+"-付息率:"+ylfxReport.getFxl());
//			        ylfxReport.setFzpjqx(qxppFzValue[1]);//负债平均期限
//			        System.out.println(businessName+"-负债平均期限:"+ylfxReport.getFzpjqx());
//			        ylfxReport.setFzzyjg(qxppFzValue[2]);// 负债转移价格
//					System.out.println(businessName+"-负债转移价格:"+ylfxReport.getFzzyjg());
//					ylfxReport.setLxzc(qxppFzValue[3]);// 利息支出
//					System.out.println(businessName+"-利息支出:"+ylfxReport.getLxzc());
//					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
//					System.out.println(businessName+"-负债净利差:" + ylfxReport.getFzjlc());
//					ylfxReport.setFzzysr(qxppFzValue[4]);//负债转移支出
//					System.out.println(businessName+"-负债转移支出:" + ylfxReport.getZczyzc());
//					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
//					System.out.println(businessName+"-负债净收入:" + ylfxReport.getZcjsr());
//				}
//			}
//			ylfxReportList.add(ylfxReport);
//		}
//	 return ylfxReportList;
//	}
//	
//	/**
//	 * 获取不同类产品的盈利分析数据
//	 * @param brSql 机构查询sql
//	 * @param xlsBrNo 县联社
//	 * @param date
//	 * @param prcMode 资金池类型
//	 * @param assessScope 统计维度
//	 * @param prdtNo 产品编号+产品名称[][]
//	 * @param ftpPoolInfoList 资金池配置list
//	 * @return
//	 */
//	public List<YlfxReport> getYlfxReportListByPrdtNo(String brSql,String xlsBrNo, String date,String prcMode, Integer assessScope, String[][] prdtNo, List<FtpPoolInfo> ftpPoolInfoList) {
//		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
//		String date2 = CommonFunctions.dateModifyM(date, assessScope);
//		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//如果跨过当年，则只取当年的数据，从1月份开始
//	    	date2 = date2.substring(0,4)+"1231";
//		
//		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//获取所统计的天数
//		for(int i = 0; i < prdtNo.length; i++) {
//			YlfxReport ylfxReport = new YlfxReport();
//			// 获取对应资金池所配置的产品
//			String settedPrdtNo = "";
//			//如果单双多资金池，要根据单双多资金池的配置进行获取
//			if(!prcMode.equals("4"))settedPrdtNo = this.getPrdtNoByPrcModeAndPrdtNo(ftpPoolInfoList, prdtNo[i][0]);
//			else settedPrdtNo = prdtNo[i][0];
//			ylfxReport.setPrdtName(prdtNo[i][1]);
//			if(settedPrdtNo.equals("")) {
//				ylfxReportList.add(ylfxReport);
//				continue;
//			}
//			if(prdtNo[i][0].indexOf("P1") != -1) {//资产类
//				if(!prcMode.equals("4")) {
//					ylfxReport.setZcye(FtpUtil.getAverageAmount(brSql, date, settedPrdtNo, "3", assessScope, true));//资产余额
//					System.out.println(prdtNo[i][1]+"-资产余额:" + ylfxReport.getZcye());
//					ylfxReport.setSxl(FtpUtil.getWeightedAveRate(brSql, date, settedPrdtNo, "3", assessScope, true));//生息率：加权平均利率
//					System.out.println(prdtNo[i][1]+"-生息率:" + ylfxReport.getSxl());
//					ylfxReport.setZczyjg(this.getFtpResultPrice(date, brSql, xlsBrNo, prcMode, settedPrdtNo, assessScope));//资产转移价格
//					ylfxReport.setZcpjqx(Double.valueOf(daysSubtract));//资产平均期限
//			        System.out.println("资产平均期限:"+ylfxReport.getZcpjqx());
//			        System.out.println(prdtNo[i][1]+"-资产转移价格:" + ylfxReport.getZczyjg());
//					ylfxReport.setLxsr(ylfxReport.getZcye() * ylfxReport.getSxl() * ylfxReport.getZcpjqx()/365);//利息收入
//					System.out.println(prdtNo[i][1]+"-利息收入:" + ylfxReport.getLxsr());
//					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());//资产净利差
//					System.out.println(prdtNo[i][1]+"-资产净利差:" + ylfxReport.getZcjlc());
//					ylfxReport.setZczyzc(ylfxReport.getZcye() * ylfxReport.getZczyjg() * ylfxReport.getZcpjqx()/365);//资产转移支出
//					System.out.println(prdtNo[i][1]+"-资产转移支出:" + ylfxReport.getZczyzc());
//					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());//资产净收入
//					System.out.println(prdtNo[i][1]+"-资产净收入:" + ylfxReport.getZcjsr());
//				}else {//如果是期限匹配法
//					//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
//					double[] qxppZcValue = this.getQxppValue(brSql, settedPrdtNo, date2, date);
//					ylfxReport.setZcye(qxppZcValue[5]);// 资产余额
//					System.out.println(prdtNo[i][1]+"-资产余额:"+ylfxReport.getZcye());
//					ylfxReport.setSxl(qxppZcValue[0]);// 生息率
//					System.out.println(prdtNo[i][1]+"-生息率:"+ylfxReport.getSxl());
//			        ylfxReport.setZcpjqx(qxppZcValue[1]);//资产平均期限
//			        System.out.println(prdtNo[i][1]+"-资产平均期限:"+ylfxReport.getZcpjqx());
//			        ylfxReport.setZczyjg(qxppZcValue[2]);// 资产转移价格
//					System.out.println(prdtNo[i][1]+"-资产转移价格:"+ylfxReport.getZczyjg());
//					ylfxReport.setLxsr(qxppZcValue[3]);// 利息收入
//					System.out.println(prdtNo[i][1]+"-利息收入:"+ylfxReport.getLxsr());
//					ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
//					System.out.println(prdtNo[i][1]+"-资产净利差:" + ylfxReport.getZcjlc());
//					ylfxReport.setZczyzc(qxppZcValue[4]);//资产转移支出
//					System.out.println(prdtNo[i][1]+"-资产转移支出:" + ylfxReport.getZczyzc());
//					ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
//					System.out.println(prdtNo[i][1]+"-资产净收入:" + ylfxReport.getZcjsr());
//				}
//			}else if(settedPrdtNo.indexOf("P2") != -1) {//负债类
//				if(!prcMode.equals("4")) {
//					ylfxReport.setFzye(FtpUtil.getAverageAmount(brSql, date, settedPrdtNo, "3", assessScope, true));//负债余额
//					System.out.println(prdtNo[i][1]+"-负债余额:"+ylfxReport.getFzye());
//					ylfxReport.setFxl(FtpUtil.getWeightedAveRate(brSql, date, settedPrdtNo, "3", assessScope, true));//付息率：加权平均利率
//					System.out.println(prdtNo[i][1]+"-付息率:"+ylfxReport.getFxl());
//					ylfxReport.setFzzyjg(this.getFtpResultPrice(date, brSql, xlsBrNo, prcMode, settedPrdtNo, assessScope));//资产转移价格
//					ylfxReport.setFzpjqx(Double.valueOf(daysSubtract));//负债平均期限
//			        System.out.println("负债平均期限:"+ylfxReport.getFzpjqx());
//			        System.out.println(prdtNo[i][1]+"-负债转移价格:"+ylfxReport.getFzzyjg());
//					ylfxReport.setLxzc(ylfxReport.getFzye() * ylfxReport.getFxl() * ylfxReport.getFzpjqx()/365);//利息支出
//					System.out.println(prdtNo[i][1]+"-利息支出:"+ylfxReport.getLxzc());
//					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());//负债净利差
//					System.out.println(prdtNo[i][1]+"-负债净利差:"+ylfxReport.getFzjlc());
//					ylfxReport.setFzzysr(ylfxReport.getFzye() * ylfxReport.getFzzyjg() * ylfxReport.getFzpjqx()/365);//负债转移收入
//					System.out.println(prdtNo[i][1]+"-负债转移收入:"+ylfxReport.getFzzysr());
//					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());//负债净收入
//					System.out.println(prdtNo[i][1]+"-负债净收入:"+ylfxReport.getFzjsr());
//				}else {//如果是期限匹配法
//					//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
//					double[] qxppFzValue = this.getQxppValue(brSql, settedPrdtNo, date2, date);
//					ylfxReport.setFzye(qxppFzValue[5]);// 负债余额
//					System.out.println(prdtNo[i][1]+"-负债余额:"+ylfxReport.getFzye());
//					ylfxReport.setFxl(qxppFzValue[0]);// 付息率
//					System.out.println(prdtNo[i][1]+"-付息率:"+ylfxReport.getFxl());
//			        ylfxReport.setFzpjqx(qxppFzValue[1]);//负债平均期限
//			        System.out.println(prdtNo[i][1]+"-负债平均期限:"+ylfxReport.getFzpjqx());
//			        ylfxReport.setFzzyjg(qxppFzValue[2]);// 负债转移价格
//					System.out.println(prdtNo[i][1]+"-负债转移价格:"+ylfxReport.getFzzyjg());
//					ylfxReport.setLxzc(qxppFzValue[3]);// 利息支出
//					System.out.println(prdtNo[i][1]+"-利息支出:"+ylfxReport.getLxzc());
//					ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
//					System.out.println(prdtNo[i][1]+"-负债净利差:" + ylfxReport.getFzjlc());
//					ylfxReport.setFzzysr(qxppFzValue[4]);//负债转移支出
//					System.out.println(prdtNo[i][1]+"-负债转移支出:" + ylfxReport.getZczyzc());
//					ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
//					System.out.println(prdtNo[i][1]+"-负债净收入:" + ylfxReport.getZcjsr());
//				}
//				
//			}
//			ylfxReportList.add(ylfxReport);
//		}
//	 return ylfxReportList;
//	}
//
//	/**
//	 * 根据机构获取它对应资金池所配置的某个业务下对应的产品
//	 * 
//	 * @param poolList
//	 *            资金池list
//	 * @param prdtNo
//	 *            某个业务所对应的所有产品
//	 * @return
//	 */
//	public String getPrdtNoByPrcModeAndPrdtNo(List<FtpPoolInfo> poolList,
//			String prdtNo) {
//		StringBuffer settedPrdtNo = new StringBuffer();
//		for (FtpPoolInfo ftpPoolInfo : poolList) {
//			String contentObject = ftpPoolInfo.getContentObject();
//			String[] contentObjects = contentObject.split("\\+");
//			for (int i = 0; i < contentObjects.length; i++) {
//				// 循环判断所配置的产品是否在prdtNo中
//				if (prdtNo.indexOf(contentObjects[i]) != -1)
//					settedPrdtNo.append(contentObjects[i] + ",");
//			}
//		}
//		System.out.println("settedPrdtNo:"+settedPrdtNo);
//		if(settedPrdtNo.length() > 0) {
//			if (settedPrdtNo.lastIndexOf(",") == settedPrdtNo.length() - 1)
//				settedPrdtNo = settedPrdtNo.deleteCharAt(settedPrdtNo.length() - 1);
//		}
//		
//		return settedPrdtNo.toString();
//	}
//
//	/**
//	 * 根据prcMode和brNo，获取对应的资金池配置
//	 * 
//	 * @param brNo
//	 *            县联社机构号
//	 * @param prcMode
//	 *            资金池
//	 * @return
//	 */
//	public List<FtpPoolInfo> getFtpPoolInfoList(String brNo, String prcMode) {
//		String hsql = "from FtpPoolInfo where prcMode = '" + prcMode
//				+ "' and brNo = '" + brNo + "'";
//		List<FtpPoolInfo> poolList = daoFactory.query(hsql, null);
//		return poolList;
//	}
//
//	/**
//	 * 获取某类产品的加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、总余额--期限匹配
//	 * @param datesql
//	 * @param brSql
//	 * @param prdtNo
//	 * @param ye 总余额
//	 * @param countTerm 定价次数
//	 * @param date2 时间段左端点
//	 * @param date时间段右端点
//	 * @return 加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、总余额
//	 */
//	public double[] getQxppValue(String brSql, String prdtNo, String date2, String date) {
//		double[] returnValue = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
//		double amt = 0.0, rate_fz = 0.0, ftpPrice_fz = 0.0, term_fz = 0.0, lxAmt = 0.0, zyAmt = 0.0;
//
//		//1.先处理多次定价的活期存款
//		//先判断要计算的产品里是否有这几类产品
//		StringBuffer gehqPrdtNo = new StringBuffer();//个人活期类存款产品编号
//		if(prdtNo.indexOf("P2010") != -1) gehqPrdtNo.append("'P2010'" + ",");
//		if(prdtNo.indexOf("P2038") != -1) gehqPrdtNo.append("'P2038'" + ",");
//		if(prdtNo.indexOf("P2056") != -1) gehqPrdtNo.append("'P2056'" + ",");
//		StringBuffer dwhqPrdtNo = new StringBuffer();//单位活期类存款产品编号
//		if(prdtNo.indexOf("P2001") != -1) dwhqPrdtNo.append("'P2001'" + ",");
//		if(prdtNo.indexOf("P2055") != -1) dwhqPrdtNo.append("'P2055'" + ",");
//		if(prdtNo.indexOf("P2031") != -1) dwhqPrdtNo.append("'P2031'" + ",");
//		
//		if(!gehqPrdtNo.toString().equals("") || !dwhqPrdtNo.toString().equals("")) {
//			if(!gehqPrdtNo.toString().equals(""))gehqPrdtNo.deleteCharAt(gehqPrdtNo.length()-1);
//			else gehqPrdtNo.append("''");
//			if(!dwhqPrdtNo.toString().equals(""))dwhqPrdtNo.deleteCharAt(dwhqPrdtNo.length()-1);
//			else dwhqPrdtNo.append("''");
//			String hq_sql = "select * from ((select rate, count(*) count,nvl(sum(case when t.amt is null then t.bal else t.amt end),0.0) amt," +
//					"max(t.ftp_price),min(t.ftp_price),max(t.wrk_sys_date), min(t.wrk_sys_date) from ftp.ftp_qxpp_result t " +
//			   "where t.br_no "+brSql+" and t.wrk_sys_date>'"+date2+"' and t.wrk_sys_date<='"+date+"' " +
//			   "and t.business_no='YW201' and t.product_no in ("+gehqPrdtNo.toString()+") group by t.br_no,t.kmh,t.rate)" +//个人活期类存款
//			   "union " +
//			   "(select max(rate), count(*) count,nvl(sum(case when t.amt is null then t.bal else t.amt end),0.0) amt," +
//			   "max(t.ftp_price),min(t.ftp_price),max(t.wrk_sys_date), min(t.wrk_sys_date) from ftp.ftp_qxpp_result t " +
//			   "where t.br_no  "+brSql+" and t.wrk_sys_date>'"+date2+"' and t.wrk_sys_date<='"+date+"' " +
//			   "and t.business_no='YW201' and t.product_no in ("+dwhqPrdtNo.toString()+") group by t.br_no,t.ac_id))";//单位活期类存款
//
//			List hqList = daoFactory.query1(hq_sql, null);
//			if(hqList.size() > 0) {
//				for(Object object : hqList) {
//					Object[] obj = (Object[])object;
//					double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
//					Integer count = obj[1] == null ? 0 : Integer.valueOf(obj[1].toString());
//					double amt1 = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());
//					double max_ftpPrice = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());
//					double min_ftpPrice = obj[4] == null ? 0.0 : Double.valueOf(obj[4].toString());
//					//期限：最大定价系统日期-最小定价系统日期的上一系统日期
//					int term = CommonFunctions.daysSubtract(obj[5].toString(), CommonFunctions.dateModifyD(obj[6].toString(),-10));
//					amt += amt1/count;//总余额
//					rate_fz += rate*amt1/count;//加权平均利率计算的分子
//					term_fz += term*amt1/count;//加权平均期限计算的分子
//					ftpPrice_fz += (max_ftpPrice+min_ftpPrice)/2*amt1/count;//加权平均转移价格计算的分子
//					lxAmt += amt1/count*rate*term/365;//利息收入/利息支出=余额*利率*期限/365
//					zyAmt += amt1/count*(max_ftpPrice+min_ftpPrice)/2*term/365;//资产转移支出/负债转移收入=余额*转移价格*期限/365
//				}
//			}
//		}
//		
//		//2.再处理单次定价的非活期存款(!YW201)
//		String dcfhq_sql = "select max(rate), count(*), nvl(sum(case when t.amt is null then t.bal else t.amt end),0.0) amt," +
//				"max(t.ftp_price),(case when max(t.term) <0 then 0 " +
//				   "when max(t.term) is null then 0 else (days(least(to_date(max(mtr_date),'yyyymmdd'), to_date('"+date+"','yyyymmdd')))-days(greatest(to_date(max(opn_date),'yyyymmdd'), to_date('"+date2+"','yyyymmdd')))) end) term from ftp.ftp_qxpp_result t " +
//				"where t.business_no!='YW201' and t.product_no in ("+prdtNo+") and  br_no "+brSql+" and (t.Opn_date <= '"+date+"' " +
//                "and t.Mtr_date > '"+date2+"') group by  br_no,ac_id ";
//                //+" having count(*)=1";
//		List dcfhqList = daoFactory.query1(dcfhq_sql, null);
//		if(dcfhqList.size() > 0) {
//			for(Object object : dcfhqList) {
//				Object[] obj = (Object[])object;
//				double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
//				Integer count = obj[1] == null ? 0 : Integer.valueOf(obj[1].toString());
//				double amt1 = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());
//				double ftpPrice = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());
//				double term = obj[4] == null ? 0.0 : Double.valueOf(obj[4].toString());
//				amt += amt1/count;//总余额
//				rate_fz += rate*amt1/count;//加权平均利率计算的分子
//				term_fz += term*amt1/count;//加权平均期限计算的分子
//				ftpPrice_fz += ftpPrice*amt1/count;//加权平均转移价格计算的分子
//				lxAmt += amt1/count*rate*term/365;//利息收入/利息支出=余额*利率*期限/365
//				zyAmt += amt1/count*ftpPrice*term/365;//资产转移支出/负债转移收入=余额*转移价格*期限/365
//			}
//		}
//		
//		//3.最后处理多次定价的非活期存款(!YW201)：需要查出所有ac_id后，再循环每个ac_id查询其所有次定价结果后单独分段计算
//		//#### 暂时放在上一步大概处理：取多次定价中的金额平均值、最大利率、最大ftp价格来全段一次计算。
//		////////####
//		
//		returnValue[0] = rate_fz/amt;//加权平均利率
//		returnValue[1] = CommonFunctions.doublecut(term_fz/amt, 1);//加权平均期限
//		returnValue[2] = ftpPrice_fz/amt;//加权平均转移价格
//		returnValue[3] = lxAmt;//利息收入/利息支出
//		returnValue[4] = zyAmt;//资产转移支出/负债转移收入
//		returnValue[5] = amt;//总余额
//		return returnValue;
//	}
//	
//	/**
//	 * 根据机构号和机构级别，获取其对应的县联社brno
//	 * 
//	 * @param brNo
//	 * @param manageLvl
//	 * @return
//	 */
//	public String getXlsBrNo(String brNo, String manageLvl) {
//		if (manageLvl.equals("2")) {
//			return brNo;
//		} else if (manageLvl.equals("1")) {
//			String hsql = "from BrMst where brNo = '" + brNo + "'";
//			BrMst brMst = (BrMst) daoFactory.getBean(hsql, null);
//			return brMst.getSuperBrNo();// 如果是1级，获取它的父级即为它对应的县联社
//		} else if (manageLvl.equals("0")) {
//			String hsql = "from BrMst where brNo = '" + brNo + "'";
//			BrMst brMst = (BrMst) daoFactory.getBean(hsql, null);
//			String hsql2 = "from BrMst where brNo = '" + brMst.getSuperBrNo()
//					+ "'";
//			brMst = (BrMst) daoFactory.getBean(hsql2, null);
//			return brMst.getSuperBrNo();// 如果是0级，要循环两次获取它的父级的父级即为它对应的县联社
//		}
//		return "";
//	}
//	
//	/**
//	 * 根据县联社brNo和要获取的机构级别，获取该联社下该级别的所有机构
//	 * @param brNo 县联社brNo
//	 * @param manageLvl
//	 * @return
//	 */
//	public List<BrMst> getBrMstList(String xlsBrNo, String manageLvl) {
//		String hsql = "";
//		List<BrMst> brMstList = new ArrayList<BrMst>();
//		if(manageLvl.equals("1")) {
//			hsql = "from BrMst where superBrNo = '"+xlsBrNo+"'";
//			brMstList = daoFactory.query(hsql, null);
//		}else if(manageLvl.equals("0")) {
//			hsql = "from BrMst where superBrNo = '"+xlsBrNo+"'";
//			List<BrMst> brMstList1 = daoFactory.query(hsql, null);
//			for(BrMst brMst : brMstList1) {
//				hsql = "from BrMst where superBrNo = '"+brMst.getBrNo()+"'";
//				List<BrMst> brMstList2 = daoFactory.query(hsql, null);
//				for(BrMst brMst2 : brMstList2) {
//					brMstList.add(brMst2);
//				}
//			}
//		}
//		return brMstList;
//	}
//}
