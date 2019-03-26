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
import com.dhcc.ftp.entity.FtpBusinessStaticDivide;
import com.dhcc.ftp.entity.FtpPoolInfo;
import com.dhcc.ftp.entity.FtpProductMethodRel;
import com.dhcc.ftp.entity.YlfxReport;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.LrmUtil;

public class ReportBO extends BaseBo {

	/**
	 * 机构总盈利分析
	 * @param request
	 * @param date
	 * @param brNo
	 * @param manageLvl 机构级别
	 * @param assessScope 统计维度 月-1、-3、-12
	 * @param isMx 是否查看明细
	 * @param tjType 统计类型 1.增量 2.存量
	 * @return
	 */
	public List<YlfxReport> brPayOffProfile(HttpServletRequest request, String date, String brNo, String manageLvl, 
			Integer assessScope, Integer isMx, String tjType) {
		CacheOperation co = CacheOperation.getInstance();//缓存
	    long intervalTime = Long.valueOf("2592000000");//缓存存放一个月
	    int maxVisitCount = 0;//不限制访问次数
	    
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String xlsBrNo = FtpUtil.getXlsBrNo(brNo, manageLvl);// 县联社
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
	    //获取存款不能用发生日期和结束日期的查询条件
		
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
				//期限匹配：获取盈利分析时间范围内的定价周期次数
				List<String[]> ftpResultsList=(List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, date2, date, tjType}, intervalTime, maxVisitCount);
				if(ftpResultsList == null) return null;
				//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
				double[] qxppZcValue = this.getQxppValueByBrNo(ftpResultsList, prdtNoZc, brSql);
				double[] qxppFzValue =this.getQxppValueByBrNo(ftpResultsList, prdtNoFz, brSql);
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
				ylfxReport.setZczyzc(qxppZcValue[4]);//资产转移支出
				System.out.println("资产转移支出:" + ylfxReport.getZczyzc());
				
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
				ylfxReport.setFzzysr(qxppFzValue[4]);//负债转移支出
				System.out.println("负债转移支出:" + ylfxReport.getZczyzc());
				
				ylfxReport.setZcjlc(ylfxReport.getSxl() - ylfxReport.getZczyjg());// 资产净利差
				System.out.println("资产净利差:" + ylfxReport.getZcjlc());
				ylfxReport.setZcjsr(ylfxReport.getLxsr() - ylfxReport.getZczyzc());// 资产净收入
				System.out.println("资产净收入:" + ylfxReport.getZcjsr());
				
				
				ylfxReport.setFzjlc(ylfxReport.getFzzyjg() - ylfxReport.getFxl());// 负债净利差
				System.out.println("负债净利差:" + ylfxReport.getFzjlc());
				ylfxReport.setFzjsr(ylfxReport.getFzzysr() - ylfxReport.getLxzc());// 负债净收入
				System.out.println("负债净收入:" + ylfxReport.getZcjsr());
				
				ylfxReport.setJsr(ylfxReport.getZcjsr() + ylfxReport.getFzjsr());// 净收入
			}
			ylfxReportList.add(ylfxReport);
		} else {
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
				//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
				List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, date2, date, tjType}, intervalTime, maxVisitCount);
				
				if(ftpResultsList == null) return null;
				Map<String,Double[]> QxppValue_map = this.getQxppValueMap_jgzylfx(ftpResultsList, list);
				
				for (int i = 0; i < list.size(); i++) {
					BrMst brMst = list.get(i);
					System.out.println("开始计算机构:"+brMst.getBrNo()+"...");
					YlfxReport ylfxReport = new YlfxReport();
					ylfxReport.setBrName(brMst.getBrName());
					ylfxReport.setBrNo(brMst.getBrNo());
					ylfxReport.setManageLvl(brMst.getManageLvl());
					Double[] qxppZcValue = QxppValue_map.get(brMst.getBrNo()+"-1");
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
					
					//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
					Double[] qxppFzValue = QxppValue_map.get(brMst.getBrNo()+"-2");
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
	public List<YlfxReport> busPayOffProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope, String tjType) {
		
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String xlsBrNo = FtpUtil.getXlsBrNo(brNo, manageLvl);// 县联社
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
		
		ylfxReportList = this.getYlfxReportList(brSql, xlsBrNo, date, prcMode, assessScope, businessNo, ftpPoolInfoList, tjType);

		return ylfxReportList;
	}
	
	/**
	 * 业务条线盈利分析---某一大业务(目前只支持：存款、贷款、投资业务)下所有产品盈利分析 数据获取
	 * @param request
	 * @param date
	 * @param brNo
	 * @param manageLvl
	 * @param assessScope
	 * @param staticNo
	 * @return
	 */
	public List<YlfxReport> prdtPayOffProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope, String prdtType, String tjType) {
		
		List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String xlsBrNo = FtpUtil.getXlsBrNo(brNo, manageLvl);// 县联社
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
		Map<String, String> prdtCatgResult = this.getPrdtCatg();//将产品按照产品大类进行分类
		String[][] prdtNo = null;//产品编号+产品名称
		String businessNo = "";
		if(prdtType.equals("ck")) {//存款
			businessNo = "'YW201','YW202','YW203','YW204'";
		}else if(prdtType.equals("dk")) {//贷款
			businessNo = "'YW101'";
		}else if(prdtType.equals("tzyw")) {//投资业务
			businessNo = "'YW105'";
		}
		String[][] prdtCtgNos = this.getPrdtCtgNos(businessNo);
		prdtNo = new String[prdtCtgNos.length][2];
		for (int i = 0; i < prdtCtgNos.length; i++) {
			prdtNo[i][0] = prdtCatgResult.get(prdtCtgNos[i][0]);//产品大类对应的所有产品编号
			prdtNo[i][1] = prdtCtgNos[i][1];//产品大类名称
		}
		String brSql = LrmUtil.getBrSql(brNo);
		ylfxReportList = this.getYlfxReportListByPrdtNo(brSql, xlsBrNo, date, prcMode, assessScope, prdtNo, ftpPoolInfoList, tjType);
		
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
	public List<YlfxReport> brPayOffRanking(HttpServletRequest request, String date, String brNo, String manageLvl, String brCountLvl, Integer assessScope, String tjType) {
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
		}else {
			List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, date2, date, tjType}, intervalTime, maxVisitCount);
			if(ftpResultsList == null) return null;
			Map<String,Double[]> QxppValue_map=this.getQxppValueMap_jgzylfx(ftpResultsList, brMstList);

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
	public YlfxReport financialCenterLCProfile(HttpServletRequest request, String date, String brNo, String manageLvl, Integer assessScope, String tjType) {
		CacheOperation co = CacheOperation.getInstance();//缓存
	    long intervalTime = Long.valueOf("2592000000");//缓存存放一个月
	    int maxVisitCount = 0;//不限制访问次数
		
	    String xlsBrNo = FtpUtil.getXlsBrNo(brNo, manageLvl);// 县联社
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
			//期限匹配：获取盈利分析时间范围内的定价周期次数
			List<String[]> ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, date2, date, tjType}, intervalTime, maxVisitCount);
			if(ftpResultsList == null) return null;
			
			double[] qxppZcValue = this.getQxppValue(ftpResultsList, date, daysSubtract, prdtNoZc);
			double[] qxppFzValue = this.getQxppValue(ftpResultsList, date, daysSubtract, prdtNoFz);
			
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
	 * 期限匹配下， 根据机构获取它对应资金池所配置的产品
	 * 
	 * @param brNo 县联社机构号
	 * @return
	 */
	public String getPrdtNoQxpp(String brNo) {
		StringBuffer prdtNo = new StringBuffer();
		String hsql = "from FtpProductMethodRel t where t.brNo = '"+brNo+"'";
		
		List<FtpProductMethodRel> ftpProductMethodRelList = daoFactory.query(hsql, null);
		for(FtpProductMethodRel ftpProductMethodRel : ftpProductMethodRelList) {
			prdtNo.append("'" + ftpProductMethodRel.getProductNo() + "',");
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
			+ "row_number() over(partition by t.pool_no order by t.RES_DATE desc,T.RESULT_ID desc ) rn from ftp.Ftp_Result t "
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
				+ "row_number() over(partition by t.pool_no order by t.RES_DATE desc,T.RESULT_ID desc ) rn from ftp.Ftp_Result t "
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
	 * @param date
	 * @param prcMode 资金池类型
	 * @param assessScope 统计维度
	 * @param businessNo 业务编号[]
	 * @param ftpPoolInfoList 资金池配置list
	 * @return
	 */
	public List<YlfxReport> getYlfxReportList(String brSql,String xlsBrNo, String date,String prcMode, Integer assessScope, String[] businessNo, List<FtpPoolInfo> ftpPoolInfoList, String tjType) {
		CacheOperation co = CacheOperation.getInstance();//缓存
	    long intervalTime = Long.valueOf("2592000000");//缓存存放一个月
	    int maxVisitCount = 0;//不限制访问次数
		
	    List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String date2 = CommonFunctions.dateModifyM(date, assessScope);
		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//如果跨过当年，则只取当年的数据，从1月份开始
	    	date2 = date2.substring(0,4)+"1231";
		
		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//获取所统计的天数
		
		List<String[]> ftpResultsList = new ArrayList<String[]>();
		Map<String,Double[]> QxppValue_map = new HashMap<String, Double[]>();
		if(prcMode.equals("4")) {//如果是期限匹配
			ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, date2, date, tjType}, intervalTime, maxVisitCount);
			if(ftpResultsList == null) return null;
			//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
			QxppValue_map = this.getQxppValueMap_ywtxylfx(ftpResultsList, xlsBrNo, brSql);
		}
		
		for(int i = 0; i < businessNo.length; i++) {
			//根据业务编号获取对应的产品编号
			String hsql = "from FtpProductMethodRel where businessNo in ("+businessNo[i]+") and brNo = '"+xlsBrNo+"'";
			List<FtpProductMethodRel> ftpProductMethodRelList = daoFactory.query(hsql, null);
			StringBuilder prdtNo = new StringBuilder();
			for (FtpProductMethodRel ftpProductMethodRel : ftpProductMethodRelList) {
				prdtNo.append("'" + ftpProductMethodRel.getProductNo() + "',");
			}
			prdtNo = prdtNo.deleteCharAt(prdtNo.length() - 1);
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
			YlfxReport ylfxReport = new YlfxReport();
			ylfxReport.setBusinessNo(businessNo[i]);// 业务编号
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
			}else {//如果是期限匹配法
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
	 * @param prdtNo 产品编号+产品大类名称[][]
	 * @param ftpPoolInfoList 资金池配置list
	 * @return
	 */
	public List<YlfxReport> getYlfxReportListByPrdtNo(String brSql,String xlsBrNo, String date,String prcMode, Integer assessScope, String[][] prdtNo, List<FtpPoolInfo> ftpPoolInfoList, String tjType) {
		CacheOperation co = CacheOperation.getInstance();//缓存
	    long intervalTime = Long.valueOf("2592000000");//缓存存放一个月
	    int maxVisitCount = 0;//不限制访问次数
	    
	    List<YlfxReport> ylfxReportList = new ArrayList<YlfxReport>();
		String date2 = CommonFunctions.dateModifyM(date, assessScope);
		if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//如果跨过当年，则只取当年的数据，从1月份开始
	    	date2 = date2.substring(0,4)+"1231";
		
		Integer daysSubtract = CommonFunctions.daysSubtract(date, date2);//获取所统计的天数
		
		List<String[]> ftpResultsList = new ArrayList<String[]>();
		Map<String,Double[]> QxppValue_map = new HashMap<String, Double[]>();
		if(prcMode.equals("4")) {//如果是期限匹配
			ftpResultsList = (List<String[]>)co.getCacheData(this, "getQxppResultList",new Object[]{xlsBrNo, date2, date, tjType}, intervalTime, maxVisitCount);
			if(ftpResultsList == null) return null;
			//加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、余额--期限匹配
			QxppValue_map = this.getQxppValueMap_ywtxylfx(ftpResultsList, xlsBrNo, brSql);
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
	  * 获取某一机构、指定日期下的期限匹配定价 
	  * @param xlsbrNo
	  * @param minDate日期左端点
	  * @param maxDate日期右端点
	  * @param tjType统计类型--1增量，2存量
	  * @return
	  */
	public List<String[]> getQxppResultList(String xlsbrNo, String minDate, String maxDate, String tjType) {
		List<String[]> resultList=new ArrayList<String[]>();

		String prdtNo = this.getPrdtNoQxpp(xlsbrNo);//期限匹配下配置的所有产品
		//先判断要计算的产品里是否有这几类产品
		String grhqPrdtNo = "'P2010','P2012','P2027','P2028'";//打包的产品编号<个人>
		//不打包的产品编号<定期主文件里的个人活期+单位活期>
		String dwhqPrdtNo = "'P2001','P2002','P2003','P2004','P2011','P2025','P2026','P2051','P2053','P2055','P2057','P2059','P2061','P2063','P2065','P2067'";
        
		String lhqPrdtNo = "";//除了活期'YW201'外，需要多次定价的产品
		lhqPrdtNo += "'P1001'";//现金
		
		//1.先处理每期定价的类活期存款<主要是活期存款，其他比如现金>-------------目前存量和增量一样
		String hq_sql = "select rate, count(*) n,nvl(sum(bal),0.0) bal," +
		" max(ftp_price),min(ftp_price),max(wrk_sys_date), min(wrk_sys_date),br_no,product_no,max(opn_date)" +
		" from ftp.ftp_qxpp_result " +
        " where br_no is not null " +//查所有机构
                " and to_date(wrk_sys_date,'yyyymmdd')>to_date('"+minDate+"','yyyymmdd')" +
        		" and to_date(wrk_sys_date,'yyyymmdd')<=to_date('"+maxDate+"','yyyymmdd') " +
                " and business_no='YW201' and product_no in ("+grhqPrdtNo+")" +
                " group by br_no,product_no,kmh,rate" +//个人活期类存款
        " union all " +
        "select max(rate), count(*) n,nvl(sum(bal),0.0) bal," +
        " max(ftp_price),min(ftp_price),max(wrk_sys_date), min(wrk_sys_date),br_no,product_no,max(opn_date)" +
        " from ftp.ftp_qxpp_result " +
        " where br_no is not null " +
                " and to_date(wrk_sys_date,'yyyymmdd')>to_date('"+minDate+"','yyyymmdd')" +
		        " and to_date(wrk_sys_date,'yyyymmdd')<=to_date('"+maxDate+"','yyyymmdd') " +
                " and ((business_no='YW201' and product_no in ("+dwhqPrdtNo+")) or business_no='YW203' or product_no in ("+lhqPrdtNo+"))" +
                " group by br_no,product_no,ac_id ";//单位活期类存款
//        " union all " +
//        "select rate, count(*) n,nvl(sum(bal),0.0) bal," +
//		" max(ftp_price),min(ftp_price),max(wrk_sys_date), min(wrk_sys_date),br_no,product_no" +
//		" from ftp.ftp_qxpp_result_history " +
//        " where br_no is not null " +//查所有机构
//                " and to_date(wrk_sys_date,'yyyymmdd')>to_date('"+minDate+"','yyyymmdd')" +
//        		" and to_date(wrk_sys_date,'yyyymmdd')<=to_date('"+maxDate+"','yyyymmdd') " +
//                " and business_no='YW201' and product_no in ("+grhqPrdtNo+")" +
//                " group by br_no,product_no,kmh,rate" +//个人活期类存款
//        " union all " +
//        "select max(rate), count(*) n,nvl(sum(bal),0.0) bal," +
//        " max(ftp_price),min(ftp_price),max(wrk_sys_date), min(wrk_sys_date),br_no,product_no" +
//        " from ftp.ftp_qxpp_result_history " +
//        " where br_no is not null " +
//                " and to_date(wrk_sys_date,'yyyymmdd')>to_date('"+minDate+"','yyyymmdd')" +
//		        " and to_date(wrk_sys_date,'yyyymmdd')<=to_date('"+maxDate+"','yyyymmdd') " +
//                " and ((business_no='YW201' and product_no in ("+dwhqPrdtNo+")) or product_no in ("+lhqPrdtNo+"))" +
//                " group by br_no,product_no,ac_id";//单位活期类存款
		
		List hqList = daoFactory.query1(hq_sql, null);
		if(hqList.size() > 0) {
			for(Object object : hqList) {
				Object[] obj = (Object[])object;
				String[] result = new String[6];
				result[0] = obj[0] == null ? "0.0" : obj[0].toString();//利率
				//期限：最大定价系统日期-最小定价系统日期的上一系统日期
				int n = 0;
				if(obj[6].toString().substring(6,8).equals("28")) {
					n = 8;
				} else if (obj[6].toString().substring(6,8).equals("29")){
					n = 9;
				}else if(obj[6].toString().substring(6,8).equals("31")) {
					n = 11;
				}else {
					n = 10;
				}
				int opnDate = obj[9]==null?0:Integer.valueOf(obj[9].toString());
				int minFtpDate = Integer.valueOf(CommonFunctions.dateModifyD(obj[6].toString(),-n));
				//取开户日和最小定价日期上一个系统日期两个中最大的当被减数
				int term = CommonFunctions.daysSubtract(obj[5].toString(), opnDate>minFtpDate?""+opnDate:""+minFtpDate);
				
				result[1] = String.valueOf(term);//期限
				Integer count = obj[1] == null ? 0 : Integer.valueOf(obj[1].toString());
				double amt1 = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());
				result[2] = String.valueOf(amt1/count);//余额
				double max_ftpPrice = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());
				double min_ftpPrice = obj[4] == null ? 0.0 : Double.valueOf(obj[4].toString());
				result[3] = String.valueOf((max_ftpPrice+min_ftpPrice)/2);//定价结果
				result[4] = String.valueOf(obj[7]);//机构
				result[5] = String.valueOf(obj[8]);//产品
				
				resultList.add(result);
			}
		}
		
		//2.再处理单次定价的非活期存款(!YW201)
		String dcfhq_sql = "";
		if(tjType.equals("1")) {//增量
			dcfhq_sql = "select max(rate), count(*), nvl(sum(amt),0.0) bal," +
			       " max(ftp_price),(case when max(term) <0 then 0 else (days(least(to_date(case when max(mtr_date) is null then '20991231' else t.mtr_date end,'yyyymmdd'), to_date('"+maxDate+"','yyyymmdd')))-days(greatest(to_date(max(opn_date),'yyyymmdd'), to_date('"+minDate+"','yyyymmdd')))) end) term," +
			       " br_no,product_no " +
			       " from ftp.ftp_qxpp_result " +
			       " where business_no!='YW201' and business_no!='YW203' and product_no in ("+prdtNo+") and product_no  not in ("+lhqPrdtNo+")  " +
			       " and br_no is not null " +
			       " and to_date(mtr_date,'yyyymmdd') > to_date('"+minDate+"','yyyymmdd') " +
			       " and to_date(Opn_date,'yyyymmdd') <= to_date('"+maxDate+"','yyyymmdd') "+
	                " group by  br_no, product_no,ac_id ";
//	               " union all " +
//	               "select max(rate), count(*), nvl(sum(bal),0.0) bal," +
//			       " max(ftp_price),(case when max(term) <0 then 0 " +
//			       " when max(term) is null then 0 else (days(least(to_date(max(mtr_date),'yyyymmdd'), to_date('"+maxDate+"','yyyymmdd')))-days(greatest(to_date(max(opn_date),'yyyymmdd'), to_date('"+minDate+"','yyyymmdd')))) end) term," +
//			       " br_no,product_no " +
//			       " from ftp.ftp_qxpp_result_history " +
//			       " where business_no!='YW201' and product_no in ("+prdtNo+") and product_no not in ("+lhqPrdtNo+") " +
//			       " and br_no is not null " +
//			       " and to_date(Opn_date,'yyyymmdd') > to_date('"+minDate+"','yyyymmdd') " +
//			       " and to_date(Opn_date,'yyyymmdd') <= to_date('"+maxDate+"','yyyymmdd') "+
//	               " group by  br_no, product_no,ac_id ";
		}else {
			//目前取得期限不是实际的期限，而是考核时间段内的有效时间段(case when t.term < 0 then 0 when t.term is null then 0 else t.term end)
			dcfhq_sql = " select * from (select t.rate,1,(case when t.now_bal is null then t.bal else t.now_bal end) now_bal," +
					"(case when ((case when NOW_FIV_STS is null then fiv_sts else NOW_FIV_STS end) not in ('03','04','05') and t.business_no='YW101') OR t.business_no!='YW101' then t.ftp_price else t.rate end) as ftp_price, " +
					"(case when t.term <0 then 0 else (days(least(to_date(case when t.mtr_date is null then '20991231' else t.mtr_date end,'yyyymmdd'), to_date('"+maxDate+"','yyyymmdd')))-days(greatest(to_date(t.opn_date,'yyyymmdd'), to_date('"+minDate+"','yyyymmdd')))) end) term," +
			        " t.br_no,t.product_no, row_number() over(partition by ac_id order by wrk_sys_date desc ) rn " +
	                " from ftp.ftp_qxpp_result t" +
	                " where t.br_no is not null " +
				    " and to_date(t.mtr_date,'yyyymmdd') > to_date('"+minDate+"','yyyymmdd') " +
			        " and to_date(t.Opn_date,'yyyymmdd') <= to_date('"+maxDate+"','yyyymmdd')" +
			       	" and t.business_no!='YW201' and t.business_no!='YW203'" +
			       	" and t.product_no in ("+prdtNo+") and t.product_no not in ("+lhqPrdtNo+") )" +
	                " where rn=1 and now_bal!=0 ";
//	                "union all  select * from (select t.rate,1,t.now_bal,t.ftp_price,(case when t.term < 0 then 0 when t.term is null then 0 else t.term end)," +
//					" t.br_no,t.product_no, row_number() over(partition by ac_id order by wrk_time desc ) rn " +
//	                " from ftp.ftp_qxpp_result_history t" +
//	                " where t.br_no is not null " +
//			        " and to_date(t.Opn_date,'yyyymmdd') <= to_date('"+maxDate+"','yyyymmdd')" +
//			       	" and t.business_no!='YW201'" +
//			       	" and t.product_no in ("+prdtNo+") and product_no not in ("+lhqPrdtNo+") )" +
//	                " where rn=1 and now_bal!=0";
		}
		
		List dcfhqList = daoFactory.query1(dcfhq_sql, null);
		if(dcfhqList.size() > 0) {
			for(Object object : dcfhqList) {
				Object[] obj = (Object[])object;
				String[] result = new String[6];
				result[0] = obj[0] == null ? "0.0" : obj[0].toString();//利率
				result[1] = Double.valueOf(obj[4].toString()) < 0 ? "0" : obj[4].toString();//期限
				Integer count = obj[1] == null ? 0 : Integer.valueOf(obj[1].toString());
				double amt1 = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());
				result[2] = String.valueOf(amt1/count);//余额
				result[3] = obj[3] == null ? "0.0" : obj[3].toString();
				result[4] = String.valueOf(obj[5]);//机构
				result[5] = String.valueOf(obj[6]);//产品
				resultList.add(result);
			}
		}
		
			/*实时存量
			 * 1.先处理多次定价的活期存款
			 * String hq_sql = "select t.rate,nvl(t.bal,0) bal,t.ftp_price,t.br_no,t.product_no from ftp.ftp_qxpp_result t" +
					" where t.br_no is not null and business_no='YW201' and t.product_no in ("+gehqPrdtNo+","+dwhqPrdtNo+")) " +
					" where rn=1 and bal!=0 " +
					" union all " +
					" select * from (select t.rate,nvl(t.bal,0) bal,t.ftp_price,t.br_no,t.product_no, row_number() over(partition by ac_id order by wrk_time desc ) rn " +
					" from ftp.ftp_qxpp_result t" +
					" where t.br_no is not null and business_no='YW201' and t.product_no in ("+dwhqPrdtNo+")) " +
					" where rn=1 and bal!=0";
			   2.再处理单次定价的非活期存款(!YW201)
			   String dcfhq_sql = " select * from (select t.rate,(case when term < 0 then 0 " +
					" when term is null then 0 else term end),nvl(t.bal,0) bal,t.ftp_price,t.br_no,t.product_no, row_number() over(partition by ac_id order by wrk_time desc ) rn " +
			        " from ftp.ftp_qxpp_result t" +
			        " where t.br_no is not null and business_no!='YW201' and t.product_no in ("+prdtNo+")) " +
			        " where rn=1 and bal!=0";
            */

		//3.最后处理多次定价的非活期存款(!YW201)：需要查出所有ac_id后，再循环每个ac_id查询其所有次定价结果后单独分段计算
		//#### 暂时放在上一步大概处理：取多次定价中的金额平均值、最大利率、最大ftp价格来全段一次计算。
		////////####
		
		return resultList;
	}
	/**
	 * 获取某类产品的加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、总余额--期限匹配
	 * @param datesql
	 * @param brSql
	 * @param prdtNo
	 * @param ye 总余额
	 * @param countTerm 定价次数
	 * @param date2 时间段左端点
	 * @param date时间段右端点
	 * @return 加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、总余额
	 */
	public double[] getQxppValue(String brSql, String prdtNo, String date2, String date) {
		double[] returnValue = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		double amt = 0.0, rate_fz = 0.0, ftpPrice_fz = 0.0, term_fz = 0.0, lxAmt = 0.0, zyAmt = 0.0;

		//1.先处理多次定价的活期存款
		//先判断要计算的产品里是否有这几类产品
		StringBuffer gehqPrdtNo = new StringBuffer();//个人活期类存款产品编号
		if(prdtNo.indexOf("P2010") != -1) gehqPrdtNo.append("'P2010'" + ",");
		if(prdtNo.indexOf("P2038") != -1) gehqPrdtNo.append("'P2038'" + ",");
		if(prdtNo.indexOf("P2056") != -1) gehqPrdtNo.append("'P2056'" + ",");
		StringBuffer dwhqPrdtNo = new StringBuffer();//单位活期类存款产品编号
		if(prdtNo.indexOf("P2001") != -1) dwhqPrdtNo.append("'P2001'" + ",");
		if(prdtNo.indexOf("P2055") != -1) dwhqPrdtNo.append("'P2055'" + ",");
		if(prdtNo.indexOf("P2031") != -1) dwhqPrdtNo.append("'P2031'" + ",");
		
		if(!gehqPrdtNo.toString().equals("") || !dwhqPrdtNo.toString().equals("")) {
			if(!gehqPrdtNo.toString().equals(""))gehqPrdtNo.deleteCharAt(gehqPrdtNo.length()-1);
			else gehqPrdtNo.append("''");
			if(!dwhqPrdtNo.toString().equals(""))dwhqPrdtNo.deleteCharAt(dwhqPrdtNo.length()-1);
			else dwhqPrdtNo.append("''");
			String hq_sql = "select * from ((select rate, count(*) n,nvl(sum(case when t.amt is null then t.bal else t.amt end),0.0) amt," +
					"max(t.ftp_price),min(t.ftp_price),max(t.wrk_sys_date), min(t.wrk_sys_date) from ftp.ftp_qxpp_result t " +
			   "where t.br_no "+brSql+" and t.wrk_sys_date>'"+date2+"' and t.wrk_sys_date<='"+date+"' " +
			   "and t.business_no='YW201' and t.product_no in ("+gehqPrdtNo.toString()+") group by t.br_no,t.kmh,t.rate)" +//个人活期类存款
			   "union all " +
			   "(select max(rate), count(*) n,nvl(sum(case when t.amt is null then t.bal else t.amt end),0.0) amt," +
			   "max(t.ftp_price),min(t.ftp_price),max(t.wrk_sys_date), min(t.wrk_sys_date) from ftp.ftp_qxpp_result t " +
			   "where t.br_no  "+brSql+" and t.wrk_sys_date>'"+date2+"' and t.wrk_sys_date<='"+date+"' " +
			   "and t.business_no='YW201' and t.product_no in ("+dwhqPrdtNo.toString()+") group by t.br_no,t.ac_id))";//单位活期类存款

			List hqList = daoFactory.query1(hq_sql, null);
			
			if(hqList.size() > 0) {
				for(Object object : hqList) {
					Object[] obj = (Object[])object;
					double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
					Integer count = obj[1] == null ? 0 : Integer.valueOf(obj[1].toString());
					double amt1 = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());
					double max_ftpPrice = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());
					double min_ftpPrice = obj[4] == null ? 0.0 : Double.valueOf(obj[4].toString());
					//期限：最大定价系统日期-最小定价系统日期的上一系统日期
					int term = CommonFunctions.daysSubtract(obj[5].toString(), CommonFunctions.dateModifyD(obj[6].toString(),-10));
					amt += amt1/count;//总余额
					rate_fz += rate*amt1/count;//加权平均利率计算的分子
					term_fz += term*amt1/count;//加权平均期限计算的分子
					ftpPrice_fz += (max_ftpPrice+min_ftpPrice)/2*amt1/count;//加权平均转移价格计算的分子
					lxAmt += amt1/count*rate*term/365;//利息收入/利息支出=余额*利率*期限/365
					zyAmt += amt1/count*(max_ftpPrice+min_ftpPrice)/2*term/365;//资产转移支出/负债转移收入=余额*转移价格*期限/365
				}
			}
		}
		//2.再处理单次定价的非活期存款(!YW201)
		String dcfhq_sql = "select max(rate), count(*), nvl(sum(case when t.amt is null then t.bal else t.amt end),0.0) amt," +
				"max(t.ftp_price),(case when max(t.term) <0 then 0 " +
				   "when max(t.term) is null then 0 else (days(least(to_date(max(mtr_date),'yyyymmdd'), to_date('"+date+"','yyyymmdd')))-days(greatest(to_date(max(opn_date),'yyyymmdd'), to_date('"+date2+"','yyyymmdd')))) end) term from ftp.ftp_qxpp_result t " +
				"where t.business_no!='YW201' and t.product_no in ("+prdtNo+") and  br_no "+brSql+"" +
				" and (t.Opn_date <= '"+date+"' " +
                "and t.Opn_date > '"+date2+"') group by  br_no,ac_id ";
                //+" having count(*)=1";
		List dcfhqList = daoFactory.query1(dcfhq_sql, null);
		if(dcfhqList.size() > 0) {
			for(Object object : dcfhqList) {
				Object[] obj = (Object[])object;
				double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
				Integer count = obj[1] == null ? 0 : Integer.valueOf(obj[1].toString());
				double amt1 = obj[2] == null ? 0.0 : Double.valueOf(obj[2].toString());
				double ftpPrice = obj[3] == null ? 0.0 : Double.valueOf(obj[3].toString());
				double term = obj[4] == null ? 0.0 : Double.valueOf(obj[4].toString());
				amt += amt1/count;//总余额
				rate_fz += rate*amt1/count;//加权平均利率计算的分子
				term_fz += term*amt1/count;//加权平均期限计算的分子
				ftpPrice_fz += ftpPrice*amt1/count;//加权平均转移价格计算的分子
				lxAmt += amt1/count*rate*term/365;//利息收入/利息支出=余额*利率*期限/365
				zyAmt += amt1/count*ftpPrice*term/365;//资产转移支出/负债转移收入=余额*转移价格*期限/365
			}
		}
		
		//3.最后处理多次定价的非活期存款(!YW201)：需要查出所有ac_id后，再循环每个ac_id查询其所有次定价结果后单独分段计算
		//#### 暂时放在上一步大概处理：取多次定价中的金额平均值、最大利率、最大ftp价格来全段一次计算。
		////////####
		
		returnValue[0] = rate_fz/amt;//加权平均利率
		returnValue[1] = CommonFunctions.doublecut(term_fz/amt, 1);//加权平均期限
		returnValue[2] = ftpPrice_fz/amt;//加权平均转移价格
		returnValue[3] = lxAmt;//利息收入/利息支出
		returnValue[4] = zyAmt;//资产转移支出/负债转移收入
		returnValue[5] = amt;//总余额
		return returnValue;
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
			if(prdtNo.indexOf(result[5]) != -1) {
				double rate = Double.valueOf(result[0]);
				double term = Double.valueOf(result[1]);
				double amt1 = Double.valueOf(result[2]);
				double ftpPrice = Double.valueOf(result[3]);
				amt += amt1;//总余额
				rate_fz += rate*amt1;//加权平均利率计算的分子
				ftpPrice_fz += ftpPrice*amt1;//加权平均转移价格计算的分子
				if(term != -1) {//存量，活期期限不计入
					term_fz += term*amt1;//加权平均期限计算的分子
					lxAmt += amt1*rate*term/365;//利息收入/利息支出=余额*利率*期限/365
					zyAmt += amt1*ftpPrice*term/365;//资产转移支出/负债转移收入=余额*转移价格*期限/365
				}
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
	 * @param prdtNo
	 * @param brNos要汇总的机构字符串，例'1801031009','1801031008'
	 * @return 加权平均利率、加权平均期限、加权平均转移价格、利息收入/利息支出、资产转移支出/负债转移收入、总余额
	 * @throws ParseException 
	 */
	public double[] getQxppValueByBrNo(List<String[]> ftpResultList, String prdtNo, String brNos) {
		double[] returnValue = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
		double amt = 0.0, rate_fz = 0.0, ftpPrice_fz = 0.0, term_fz = 0.0, lxAmt = 0.0, zyAmt = 0.0;
		for (String[] result : ftpResultList) {
			if(prdtNo.indexOf(result[5]) != -1 && (brNos.indexOf(result[4])!= -1|| brNos.equals("is not null"))) {
				double rate = Double.valueOf(result[0]);
				double term = Double.valueOf(result[1]);
				double amt1 = Double.valueOf(result[2]);
				double ftpPrice = Double.valueOf(result[3]);
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
	 * 机构总盈利分析-----机构明细查询，将ftpResultList中的数据，按照机构放到对应的map中
	 * @param ftped_data_successList
	 * @param brMstList 要获取数据值到界面直接显示的机构数据表对象列表(2、1、0级机构都支持)
	 * @return map
	 */
	public Map<String,Double[]> getQxppValueMap_jgzylfx(List<String[]> ftpResultList, List<BrMst> brMstList) {
		Map<String,Double[]> QxppValue_map=new HashMap<String,Double[]>();
		Map<String,Double> amt_map=new HashMap<String,Double>();
		Map<String,Double> rate_fz_map=new HashMap<String,Double>();
		Map<String,Double> ftpPrice_fz_map=new HashMap<String,Double>();
		Map<String,Double> term_fz_map=new HashMap<String,Double>();
		Map<String,Double> lxAmt_map=new HashMap<String,Double>();
		Map<String,Double> zyAmt_map=new HashMap<String,Double>();
	
		for (String[] result : ftpResultList) {
			
			double rate = Double.valueOf(result[0]);
			double term = Double.valueOf(result[1]);
			double amt1 = Double.valueOf(result[2]);
			double ftpPrice = Double.valueOf(result[3]);
			//总余额
			if(amt_map.get(result[4]+"-"+result[5].substring(1,2))==null){
				amt_map.put(result[4]+"-"+result[5].substring(1,2), amt1);
			}else{
				amt_map.put(result[4]+"-"+result[5].substring(1,2), amt1+amt_map.get(result[4]+"-"+result[5].substring(1,2)));
			}
			//加权平均利率计算的分子
			if(rate_fz_map.get(result[4]+"-"+result[5].substring(1,2))==null){
				rate_fz_map.put(result[4]+"-"+result[5].substring(1,2), rate*amt1);
			}else{
				rate_fz_map.put(result[4]+"-"+result[5].substring(1,2), rate*amt1+rate_fz_map.get(result[4]+"-"+result[5].substring(1,2)));
			}
			
			//加权平均期限计算的分子
			if(term_fz_map.get(result[4]+"-"+result[5].substring(1,2))==null){
				term_fz_map.put(result[4]+"-"+result[5].substring(1,2), term*amt1);
			}else{
				term_fz_map.put(result[4]+"-"+result[5].substring(1,2), term*amt1+term_fz_map.get(result[4]+"-"+result[5].substring(1,2)));
			}
			//加权平均转移价格计算的分子
			if(ftpPrice_fz_map.get(result[4]+"-"+result[5].substring(1,2))==null){
				ftpPrice_fz_map.put(result[4]+"-"+result[5].substring(1,2), ftpPrice*amt1);
			}else{
				ftpPrice_fz_map.put(result[4]+"-"+result[5].substring(1,2), ftpPrice*amt1+ftpPrice_fz_map.get(result[4]+"-"+result[5].substring(1,2)));
			}
			//利息收入/利息支出=余额*利率*期限/365
			if(lxAmt_map.get(result[4]+"-"+result[5].substring(1,2))==null){
				lxAmt_map.put(result[4]+"-"+result[5].substring(1,2),amt1*rate*term/365);
			}else{
				lxAmt_map.put(result[4]+"-"+result[5].substring(1,2), amt1*rate*term/365+lxAmt_map.get(result[4]+"-"+result[5].substring(1,2)));
			}
			//资产转移支出/负债转移收入=余额*转移价格*期限/365
			if(zyAmt_map.get(result[4]+"-"+result[5].substring(1,2))==null){
				zyAmt_map.put(result[4]+"-"+result[5].substring(1,2),amt1*ftpPrice*term/365);
			}else{
				zyAmt_map.put(result[4]+"-"+result[5].substring(1,2), amt1*ftpPrice*term/365+zyAmt_map.get(result[4]+"-"+result[5].substring(1,2)));
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
	public Map<String,Double[]> getQxppValueMap_ywtxylfx(List<String[]> ftped_data_successList, String xlsBrNo, String brNos) {
		Map<String,Double[]> QxppValue_map=new HashMap<String,Double[]>();
		Map<String,Double> amt_map=new HashMap<String,Double>();
		Map<String,Double> rate_fz_map=new HashMap<String,Double>();
		Map<String,Double> ftpPrice_fz_map=new HashMap<String,Double>();
		Map<String,Double> term_fz_map=new HashMap<String,Double>();
		Map<String,Double> lxAmt_map=new HashMap<String,Double>();
		Map<String,Double> zyAmt_map=new HashMap<String,Double>();
	
		//按产品编号放到map中
		for (String[] result : ftped_data_successList) {
			if(brNos.indexOf(result[4])!= -1|| brNos.equals("is not null")) {//如果要汇总的机构中包含result[4]
				double rate = Double.valueOf(result[0]);
				double term = Double.valueOf(result[1]);
				double amt1 = Double.valueOf(result[2]);
				double ftpPrice = Double.valueOf(result[3]);
				//总余额
				if(amt_map.get(result[5])==null){
					amt_map.put(result[5], amt1);
				}else{
					amt_map.put(result[5], amt1+amt_map.get(result[5]));
				}
				if(result[5].equals("P2029")||result[5].equals("P2030")) {
					System.out.println("amt1"+amt1);
				}
				//加权平均利率计算的分子
				if(rate_fz_map.get(result[5])==null){
					rate_fz_map.put(result[5], rate*amt1);
				}else{
					rate_fz_map.put(result[5], rate*amt1+rate_fz_map.get(result[5]));
				}
				
				//加权平均期限计算的分子
				if(term_fz_map.get(result[5])==null){
					term_fz_map.put(result[5], term*amt1);
				}else{
					term_fz_map.put(result[5], term*amt1+term_fz_map.get(result[5]));
				}
				//加权平均转移价格计算的分子
				if(ftpPrice_fz_map.get(result[5])==null){
					ftpPrice_fz_map.put(result[5], ftpPrice*amt1);
				}else{
					ftpPrice_fz_map.put(result[5], ftpPrice*amt1+ftpPrice_fz_map.get(result[5]));
				}
				//利息收入/利息支出=余额*利率*期限/365
				if(lxAmt_map.get(result[5])==null){
					lxAmt_map.put(result[5],amt1*rate*term/365);
				}else{
					lxAmt_map.put(result[5], amt1*rate*term/365+lxAmt_map.get(result[5]));
				}
				//资产转移支出/负债转移收入=余额*转移价格*期限/365
				if(zyAmt_map.get(result[5])==null){
					zyAmt_map.put(result[5],amt1*ftpPrice*term/365);
				}else{
					zyAmt_map.put(result[5], amt1*ftpPrice*term/365+zyAmt_map.get(result[5]));
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
	 * 根据业务编号获取产品大类
	 * @param businessNo
	 * @return
	 */
	public String[][] getPrdtCtgNos(String businessNo) {
		String[][] prdtCtgNos = null;
		String sql = "select distinct Product_ctg_no, Product_ctg_name from ftp.FTP_business_static_divide where business_no in ("+businessNo+")";
		List list = daoFactory.query1(sql, null);
		if(list != null && list.size() > 0) {
			prdtCtgNos = new String[list.size()][2];
			for(int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[])list.get(i);
				prdtCtgNos[i][0] = obj[0].toString();
				prdtCtgNos[i][1] = obj[1].toString();
			}
		}
		return prdtCtgNos;
	}
	
	/**
	 * 将产品按照产品大类进行分类
	 * @return Map <产品大类编号,'产品编号','产品编号2'...>
	 */
	public Map<String, String> getPrdtCatg() {
		Map<String, String> result = new HashMap<String, String>();
		String sql = "from FtpBusinessStaticDivide order by productCtgNo, productNo";
		List<FtpBusinessStaticDivide> resultList = daoFactory.query(sql, null);
		String lastproductCtgNo = "";
		String prdtNos = "";
		for(int i = 0; i < resultList.size(); i++) {
			FtpBusinessStaticDivide ftpBusinessStaticDivide = (FtpBusinessStaticDivide)resultList.get(i);
			if(i == 0) {
				lastproductCtgNo = ftpBusinessStaticDivide.getProductCtgNo();
				prdtNos = "'"+ftpBusinessStaticDivide.getProductNo()+"'";
			}else {
				if(!lastproductCtgNo.equals(ftpBusinessStaticDivide.getProductCtgNo())){
					result.put(lastproductCtgNo, prdtNos);
				    lastproductCtgNo = ftpBusinessStaticDivide.getProductCtgNo();
					prdtNos = "'"+ftpBusinessStaticDivide.getProductNo()+"'";
				}else {
					prdtNos += ",'"+ftpBusinessStaticDivide.getProductNo()+"'";
				}
			}
		}
		return result;
	}
	public static void main(String[] args) {
		System.out.println();
	}
}
