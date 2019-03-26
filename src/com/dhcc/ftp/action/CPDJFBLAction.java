package com.dhcc.ftp.action;
/**
 * @desc:期限匹配：产品定价公布栏action
 * @author :孙红玉
 * @date ：2013-09-09
 */
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.Ftp1PrepayAdjust;
import com.dhcc.ftp.entity.FtpPrdtPricePublishDetail;
import com.dhcc.ftp.entity.FtpPrdtPricePublishMain;
import com.dhcc.ftp.entity.FtpProductMethodRel;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.DateUtil;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.PageUtil;
import com.dhcc.ftp.util.SCYTCZlineF;

public class CPDJFBLAction extends BoBuilder {
	private int pageSize = 10;
	private int rowsCount = -1;
	private int page = 1;
	private String startDate;
	private String endDate;
	private String currentDate;
	private int isSave;
    HttpServletRequest request = getRequest();
	DaoFactory df = new DaoFactory();
	private PageUtil ftpPrdtUtil = null;
	
    public String execute() throws Exception {
		return super.execute();
	}
    public String list() {
    	String sql = "from FtpPrdtPricePublishMain where substr(publishTime,1,8) >= '"+startDate+"' " +
    			" and substr(publishTime,1,8) <= '"+endDate+"' order by publishTime asc";
    	
    	String pageName = "cpdjfbl_list.action?startDate="+startDate+"&endDate="+endDate;
    	ftpPrdtUtil = queryPageBO.queryPage(sql, pageSize, page, rowsCount, pageName);

		return "list";
    }
    
    /**
     * 产品定价
     * @return
     * @throws Exception
     */
    public String ftpCompute() {
    	TelMst telMst = (TelMst)request.getSession().getAttribute("userBean"); 
    	currentDate = CommonFunctions.dateModifyD(String.valueOf(CommonFunctions.GetDBTodayDate()),1);//获取数据库当前日期+1
    	String hsql = "from FtpProductMethodRel order by productNo";
    	List<FtpProductMethodRel>ftpProductMethodRellist = df.query(hsql, null);//所有要公布的产品
    	//要公布的具体期限
		int[] prdtTerm = new int[65];//0M-60M,120M,180M,240M,360M
		String[] termType = new String[65];
		String[][] prdtInfo = new String[ftpProductMethodRellist.size()][2];//编号+名称
		for(int i = 0; i < 61; i++) {//0M-60M
			prdtTerm[i] = i*30;
//			if(i == 12 || i == 24 || i == 36 || i == 48 || i == 60) {
//				termType[i] = i/12+"Y";
//			}else {
				termType[i] = i+"M";
//			}
		}
		prdtTerm[61] = 120*30;
		prdtTerm[62] = 180*30;
		prdtTerm[63] = 240*30;
		prdtTerm[64] = 360*30;
		termType[61] = "120M";
		termType[62] = "180M";
		termType[63] = "240M";
		termType[64] = "360M";
//		termType[61] = "10Y";
//		termType[62] = "15Y";
//		termType[63] = "20Y";
//		termType[64] = "30Y";
		
		//将计算结果放到二维数组里
		double[][] result = new double[ftpProductMethodRellist.size()][prdtTerm.length];
    	
    	//收益率曲线MAP
    	Map<String,SCYTCZlineF> curvesF_map=FtpUtil.getMap_AllCurves_N(currentDate, telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());
    	//获取其所有产品定价方法组合的map
    	Map<String,String[]> ftpMethodMap = FtpUtil.getMap_FTPMethod(telMst.getBrMst().getBrNo());
		
		//存贷款产品的各项策略调整
		Map<Integer, Double> ckzbjAdjustMap = uL06BO.getCkzbjAdjustValue();//存款准备金调整
		Map<Integer, Double> ldxAdjustMap = uL06BO.getLdxAdjustValue();//流动性调整
		Map<String, Double> irAdjustMap = uL06BO.getIrAdjustValue();//信用风险调整
		List<Ftp1PrepayAdjust> prepayList = uL06BO.getPrepayAdjustValue();//提前还款/提前支取调整
		Map<String, Double> clAdjustMap = uL06BO.getClAdjustValue();//策略调整
		
		//循环对产品进行定价
		for(int i = 0; i < ftpProductMethodRellist.size(); i++) {
    		FtpProductMethodRel ftpProductMethodRel = (FtpProductMethodRel)ftpProductMethodRellist.get(i);
    		prdtInfo[i][0] = ftpProductMethodRel.getProductNo();
    		prdtInfo[i][1] = ftpProductMethodRel.getProductName();
    		String[] ftp_methodComb=ftpMethodMap.get(ftpProductMethodRel.getProductNo());
    		
    		double adjust_rate=Double.valueOf(ftp_methodComb[2]);//调整利率
    		
			String method_no=ftp_methodComb[0];//具体定价方法编号
			String curve_no=ftp_methodComb[3];//使用的收益率曲线编号
			String curve_date=currentDate;//选用收益率曲线的日期条件,实现‘曲线选用严格到每日’新加。
			
			//循环每个期限
			for (int j = 0; j < prdtTerm.length; j++) {
				int term=prdtTerm[j];
				if(method_no.equals("06")){//只有‘利率代码差额法06’才使用‘参考期限’ 
					term=Integer.valueOf(ftp_methodComb[1]);//参考期限
				}
			
				if(!"无".equals(curve_no) && curvesF_map.get(curve_date+"-"+curve_no)==null){
					continue;
				}
				double appoint_rate=Double.valueOf(ftp_methodComb[4]);//指定利率
				double appoint_delta_rate=Double.valueOf(ftp_methodComb[5]);//固定利差
				
				double ftp_price=-1;
				if(method_no.equals("01")){//## 原始期限匹配法
					ftp_price=FtpUtil.getFTPPrice_ysqxppf(term, curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
				}else if(method_no.equals("02")){//## 指定利率法
					//ftp_price=FtpUtil.getFTPPrice_zdllf(term, curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
					ftp_price=appoint_rate+adjust_rate;
				}else if(method_no.equals("03")){//## 重定价期限匹配法
					/////
				}else if(method_no.equals("04")){//## 现金流法,还款周期
					ftp_price=FtpUtil.getFTPPrice_xjlf(term,30,curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
				}else if(method_no.equals("05")){//## 久期法
					if(ftpProductMethodRel.getProductNo().substring(0, 4).equals("P109")){//按揭贷款，还款周期
						ftp_price=FtpUtil.getFTPPrice_jqf(term,30,0,curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
					}else{//固定资产,默认设置残值率为0.4，折旧周期
						ftp_price=FtpUtil.getFTPPrice_jqf(term,365,0.4,curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
					}		
				}else if(method_no.equals("06")){//## 利率代码差额法
					ftp_price=FtpUtil.getFTPPrice_lldmcef(term, curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
				}else if(method_no.equals("07")){//## 加权利率法
					ftp_price=FtpUtil.getFTPPrice_jqllf(curvesF_map.get(curve_date+"-"+curve_no), adjust_rate);
				}else if(method_no.equals("08")){//## 固定利差法
					ftp_price=appoint_delta_rate+adjust_rate;//显示净利差
					if(j == 0)prdtInfo[i][1] = ftpProductMethodRel.getProductName()+"-净利差";
				}else{
					continue;
				}
				
				//FTP调整
				double adjustValue = 0;
				if(ftp_methodComb[9].equals("1")) {//是否进行调整
					if(curve_no.equals("0100")) {//使用存贷款收益率曲线的产品，要进行各项调整
						adjustValue = FtpUtil.getCdkFtpAdjustValue(ftpProductMethodRel.getBusinessNo(), term, ckzbjAdjustMap, ldxAdjustMap, irAdjustMap, prepayList);
					}else {//使用市场收益率曲线 +流动性风险加点+敞口占用加点
						adjustValue += Double.valueOf(ftp_methodComb[7])+Double.valueOf(ftp_methodComb[8]);
					}
					//策略调整，根据产品获取对应的数据
					adjustValue += clAdjustMap.get(ftpProductMethodRel.getProductNo()) == null ? 0 : clAdjustMap.get(ftpProductMethodRel.getProductNo());
					ftp_price += adjustValue;
				}
				
				result[i][j] = ftp_price;
			}
			request.getSession().setAttribute("result", result);
			request.getSession().setAttribute("prdtInfo", prdtInfo);
			request.getSession().setAttribute("termType", termType);
			request.setAttribute("currentDate", currentDate);
    	}
		return "computeResult";
    }

    public String detail() {
    	String[] termType = new String[65];
		for(int i = 0; i < 61; i++) {//0M-60M
			termType[i] = i+"M";
		}
		termType[61] = "120M";
		termType[62] = "180M";
		termType[63] = "240M";
		termType[64] = "360M";
		
    	String hql = "from FtpPrdtPricePublishDetail where substr(publishTime,1,8) = '"+currentDate+"' order by productNo";
		List<FtpPrdtPricePublishDetail> ftpPrdtPricePublishDetailList = df.query(hql, null);
		
		String[][] prdtInfo = new String[ftpPrdtPricePublishDetailList.size()][2];
		double[][] result = new double[prdtInfo.length][termType.length];
		for (int i = 0; i < ftpPrdtPricePublishDetailList.size(); i++) {
			FtpPrdtPricePublishDetail ftpPrdtPricePublishDetail = ftpPrdtPricePublishDetailList.get(i);
			prdtInfo[i][0] = ftpPrdtPricePublishDetail.getProductNo();
    		prdtInfo[i][1] = ftpPrdtPricePublishDetail.getProductName();
    		result[i][0] = ftpPrdtPricePublishDetail.getTermPrice0()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice0();
    		result[i][1] = ftpPrdtPricePublishDetail.getTermPrice1()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice1();
    		result[i][2] = ftpPrdtPricePublishDetail.getTermPrice2()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice2();
    		result[i][3] = ftpPrdtPricePublishDetail.getTermPrice3()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice3();
    		result[i][4] = ftpPrdtPricePublishDetail.getTermPrice4()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice4();
    		result[i][5] = ftpPrdtPricePublishDetail.getTermPrice5()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice5();
    		result[i][6] = ftpPrdtPricePublishDetail.getTermPrice6()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice6();
    		result[i][7] = ftpPrdtPricePublishDetail.getTermPrice7()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice7();
    		result[i][8] = ftpPrdtPricePublishDetail.getTermPrice8()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice8();
    		result[i][9] = ftpPrdtPricePublishDetail.getTermPrice9()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice9();
    		result[i][10] = ftpPrdtPricePublishDetail.getTermPrice10()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice10();
    		result[i][11] = ftpPrdtPricePublishDetail.getTermPrice11()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice11();
    		result[i][12] = ftpPrdtPricePublishDetail.getTermPrice12()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice12();
    		result[i][13] = ftpPrdtPricePublishDetail.getTermPrice13()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice13();
    		result[i][14] = ftpPrdtPricePublishDetail.getTermPrice14()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice14();
    		result[i][15] = ftpPrdtPricePublishDetail.getTermPrice15()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice15();
    		result[i][16] = ftpPrdtPricePublishDetail.getTermPrice16()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice16();
    		result[i][17] = ftpPrdtPricePublishDetail.getTermPrice17()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice17();
    		result[i][18] = ftpPrdtPricePublishDetail.getTermPrice18()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice18();
    		result[i][19] = ftpPrdtPricePublishDetail.getTermPrice19()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice19();
    		result[i][20] = ftpPrdtPricePublishDetail.getTermPrice20()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice20();
    		result[i][21] = ftpPrdtPricePublishDetail.getTermPrice21()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice21();
    		result[i][22] = ftpPrdtPricePublishDetail.getTermPrice22()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice22();
    		result[i][23] = ftpPrdtPricePublishDetail.getTermPrice23()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice23();
    		result[i][24] = ftpPrdtPricePublishDetail.getTermPrice24()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice24();
    		result[i][25] = ftpPrdtPricePublishDetail.getTermPrice25()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice25();
    		result[i][26] = ftpPrdtPricePublishDetail.getTermPrice26()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice26();
    		result[i][27] = ftpPrdtPricePublishDetail.getTermPrice27()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice27();
    		result[i][28] = ftpPrdtPricePublishDetail.getTermPrice28()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice28();
    		result[i][29] = ftpPrdtPricePublishDetail.getTermPrice29()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice29();
    		result[i][30] = ftpPrdtPricePublishDetail.getTermPrice30()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice30();
    		result[i][31] = ftpPrdtPricePublishDetail.getTermPrice31()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice31();
    		result[i][32] = ftpPrdtPricePublishDetail.getTermPrice32()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice32();
    		result[i][33] = ftpPrdtPricePublishDetail.getTermPrice33()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice33();
    		result[i][34] = ftpPrdtPricePublishDetail.getTermPrice34()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice34();
    		result[i][35] = ftpPrdtPricePublishDetail.getTermPrice35()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice35();
    		result[i][36] = ftpPrdtPricePublishDetail.getTermPrice36()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice36();
    		result[i][37] = ftpPrdtPricePublishDetail.getTermPrice37()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice37();
    		result[i][38] = ftpPrdtPricePublishDetail.getTermPrice38()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice38();
    		result[i][39] = ftpPrdtPricePublishDetail.getTermPrice39()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice39();
    		result[i][40] = ftpPrdtPricePublishDetail.getTermPrice40()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice40();
    		result[i][41] = ftpPrdtPricePublishDetail.getTermPrice41()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice41();
    		result[i][42] = ftpPrdtPricePublishDetail.getTermPrice42()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice42();
    		result[i][43] = ftpPrdtPricePublishDetail.getTermPrice43()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice43();
    		result[i][44] = ftpPrdtPricePublishDetail.getTermPrice44()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice44();
    		result[i][45] = ftpPrdtPricePublishDetail.getTermPrice45()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice45();
    		result[i][46] = ftpPrdtPricePublishDetail.getTermPrice46()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice46();
    		result[i][47] = ftpPrdtPricePublishDetail.getTermPrice47()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice47();
    		result[i][48] = ftpPrdtPricePublishDetail.getTermPrice48()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice48();
    		result[i][49] = ftpPrdtPricePublishDetail.getTermPrice49()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice49();
    		result[i][50] = ftpPrdtPricePublishDetail.getTermPrice50()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice50();
    		result[i][51] = ftpPrdtPricePublishDetail.getTermPrice51()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice51();
    		result[i][52] = ftpPrdtPricePublishDetail.getTermPrice52()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice52();
    		result[i][53] = ftpPrdtPricePublishDetail.getTermPrice53()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice53();
    		result[i][54] = ftpPrdtPricePublishDetail.getTermPrice54()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice54();
    		result[i][55] = ftpPrdtPricePublishDetail.getTermPrice55()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice55();
    		result[i][56] = ftpPrdtPricePublishDetail.getTermPrice56()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice56();
    		result[i][57] = ftpPrdtPricePublishDetail.getTermPrice57()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice57();
    		result[i][58] = ftpPrdtPricePublishDetail.getTermPrice58()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice58();
    		result[i][59] = ftpPrdtPricePublishDetail.getTermPrice59()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice59();
    		result[i][60] = ftpPrdtPricePublishDetail.getTermPrice60()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice60();
    		result[i][61] = ftpPrdtPricePublishDetail.getTermPrice120()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice120();
    		result[i][62] = ftpPrdtPricePublishDetail.getTermPrice180()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice180();
    		result[i][63] = ftpPrdtPricePublishDetail.getTermPrice240()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice240();
    		result[i][64] = ftpPrdtPricePublishDetail.getTermPrice360()==null?Double.NaN:ftpPrdtPricePublishDetail.getTermPrice360();
		}
		request.getSession().setAttribute("result", result);
		request.getSession().setAttribute("prdtInfo", prdtInfo);
		request.getSession().setAttribute("termType", termType);
    	return "computeResult";
    }
    /**
     * 发布结果保存
     */
    public String save() {
    	TelMst telMst = (TelMst)request.getSession().getAttribute("userBean");
    	double[][] result = (double[][]) request.getSession().getAttribute("result");
		String[][] prdtInfo = (String[][])request.getSession().getAttribute("prdtInfo");
		String currentDate = DateUtil.getCurrentDay();
		String currentTime = DateUtil.getCurrentTime();
		//获取当天定价的次数
		String hql = "from FtpPrdtPricePublishMain where substr(publishTime,1,8) = '"+currentDate+"'";
		FtpPrdtPricePublishMain oldFtpPrdtPricePublishMain = (FtpPrdtPricePublishMain)df.getBean(hql, null);
		
		Integer publishNum = oldFtpPrdtPricePublishMain == null ? 1:oldFtpPrdtPricePublishMain.getPublishNum()+1;
		
		String dhql1 = "delete from FtpPrdtPricePublishMain where substr(publishTime,1,8) = '"+currentDate+"'";
		df.delete(dhql1, null);//先删除后添加
		String dhql2 = "delete from FtpPrdtPricePublishDetail where substr(publishTime,1,8) = '"+currentDate+"'";
		df.delete(dhql2, null);//先删除后添加
		
		String time = currentDate+currentTime;
		//新加主表
		FtpPrdtPricePublishMain ftpPrdtPricePublishMain = new FtpPrdtPricePublishMain();
		ftpPrdtPricePublishMain.setPublishId(IDUtil.getInstanse().getUID());
		ftpPrdtPricePublishMain.setPublishNum(publishNum);
		ftpPrdtPricePublishMain.setPublishTime(time);
		ftpPrdtPricePublishMain.setTelMst(new TelMst(telMst.getTelNo()));
		df.insert(ftpPrdtPricePublishMain);
		
//		List<FtpPrdtPricePublishDetail> resultList = new ArrayList<FtpPrdtPricePublishDetail>();
		for (int i = 0; i < result.length; i++) {//循环产品
			FtpPrdtPricePublishDetail ftpPrdtPricePublishDetail = new FtpPrdtPricePublishDetail();
			ftpPrdtPricePublishDetail.setPublishId(IDUtil.getInstanse().getUID());
			ftpPrdtPricePublishDetail.setPublishTime(time);
			ftpPrdtPricePublishDetail.setProductNo(prdtInfo[i][0]);
			ftpPrdtPricePublishDetail.setProductName(prdtInfo[i][1]);
			ftpPrdtPricePublishDetail.setTermPrice0(Double.isNaN(result[i][0])?null:result[i][0]);
    		ftpPrdtPricePublishDetail.setTermPrice1(Double.isNaN(result[i][1])?null:result[i][1]);
    		ftpPrdtPricePublishDetail.setTermPrice2(Double.isNaN(result[i][2])?null:result[i][2]);
    		ftpPrdtPricePublishDetail.setTermPrice3(Double.isNaN(result[i][3])?null:result[i][3]);
    		ftpPrdtPricePublishDetail.setTermPrice4(Double.isNaN(result[i][4])?null:result[i][4]);
    		ftpPrdtPricePublishDetail.setTermPrice5(Double.isNaN(result[i][5])?null:result[i][5]);
    		ftpPrdtPricePublishDetail.setTermPrice6(Double.isNaN(result[i][6])?null:result[i][6]);
    		ftpPrdtPricePublishDetail.setTermPrice7(Double.isNaN(result[i][7])?null:result[i][7]);
    		ftpPrdtPricePublishDetail.setTermPrice8(Double.isNaN(result[i][8])?null:result[i][8]);
    		ftpPrdtPricePublishDetail.setTermPrice9(Double.isNaN(result[i][9])?null:result[i][9]);
    		ftpPrdtPricePublishDetail.setTermPrice10(Double.isNaN(result[i][10])?null:result[i][10]);
    		ftpPrdtPricePublishDetail.setTermPrice11(Double.isNaN(result[i][11])?null:result[i][11]);
    		ftpPrdtPricePublishDetail.setTermPrice12(Double.isNaN(result[i][12])?null:result[i][12]);
    		ftpPrdtPricePublishDetail.setTermPrice13(Double.isNaN(result[i][13])?null:result[i][13]);
    		ftpPrdtPricePublishDetail.setTermPrice14(Double.isNaN(result[i][14])?null:result[i][14]);
    		ftpPrdtPricePublishDetail.setTermPrice15(Double.isNaN(result[i][15])?null:result[i][15]);
    		ftpPrdtPricePublishDetail.setTermPrice16(Double.isNaN(result[i][16])?null:result[i][16]);
    		ftpPrdtPricePublishDetail.setTermPrice17(Double.isNaN(result[i][17])?null:result[i][17]);
    		ftpPrdtPricePublishDetail.setTermPrice18(Double.isNaN(result[i][18])?null:result[i][18]);
    		ftpPrdtPricePublishDetail.setTermPrice19(Double.isNaN(result[i][19])?null:result[i][19]);
    		ftpPrdtPricePublishDetail.setTermPrice20(Double.isNaN(result[i][20])?null:result[i][20]);
    		ftpPrdtPricePublishDetail.setTermPrice21(Double.isNaN(result[i][21])?null:result[i][21]);
    		ftpPrdtPricePublishDetail.setTermPrice22(Double.isNaN(result[i][22])?null:result[i][22]);
    		ftpPrdtPricePublishDetail.setTermPrice23(Double.isNaN(result[i][23])?null:result[i][23]);
    		ftpPrdtPricePublishDetail.setTermPrice24(Double.isNaN(result[i][24])?null:result[i][24]);
    		ftpPrdtPricePublishDetail.setTermPrice25(Double.isNaN(result[i][25])?null:result[i][25]);
    		ftpPrdtPricePublishDetail.setTermPrice26(Double.isNaN(result[i][26])?null:result[i][26]);
    		ftpPrdtPricePublishDetail.setTermPrice27(Double.isNaN(result[i][27])?null:result[i][27]);
    		ftpPrdtPricePublishDetail.setTermPrice28(Double.isNaN(result[i][28])?null:result[i][28]);
    		ftpPrdtPricePublishDetail.setTermPrice29(Double.isNaN(result[i][29])?null:result[i][29]);
    		ftpPrdtPricePublishDetail.setTermPrice30(Double.isNaN(result[i][30])?null:result[i][30]);
    		ftpPrdtPricePublishDetail.setTermPrice31(Double.isNaN(result[i][31])?null:result[i][31]);
    		ftpPrdtPricePublishDetail.setTermPrice32(Double.isNaN(result[i][32])?null:result[i][32]);
    		ftpPrdtPricePublishDetail.setTermPrice33(Double.isNaN(result[i][33])?null:result[i][33]);
    		ftpPrdtPricePublishDetail.setTermPrice34(Double.isNaN(result[i][34])?null:result[i][34]);
    		ftpPrdtPricePublishDetail.setTermPrice35(Double.isNaN(result[i][35])?null:result[i][35]);
    		ftpPrdtPricePublishDetail.setTermPrice36(Double.isNaN(result[i][36])?null:result[i][36]);
    		ftpPrdtPricePublishDetail.setTermPrice37(Double.isNaN(result[i][37])?null:result[i][37]);
    		ftpPrdtPricePublishDetail.setTermPrice38(Double.isNaN(result[i][38])?null:result[i][38]);
    		ftpPrdtPricePublishDetail.setTermPrice39(Double.isNaN(result[i][39])?null:result[i][39]);
    		ftpPrdtPricePublishDetail.setTermPrice40(Double.isNaN(result[i][40])?null:result[i][40]);
    		ftpPrdtPricePublishDetail.setTermPrice41(Double.isNaN(result[i][41])?null:result[i][41]);
    		ftpPrdtPricePublishDetail.setTermPrice42(Double.isNaN(result[i][42])?null:result[i][42]);
    		ftpPrdtPricePublishDetail.setTermPrice43(Double.isNaN(result[i][43])?null:result[i][43]);
    		ftpPrdtPricePublishDetail.setTermPrice44(Double.isNaN(result[i][44])?null:result[i][44]);
    		ftpPrdtPricePublishDetail.setTermPrice45(Double.isNaN(result[i][45])?null:result[i][45]);
    		ftpPrdtPricePublishDetail.setTermPrice46(Double.isNaN(result[i][46])?null:result[i][46]);
    		ftpPrdtPricePublishDetail.setTermPrice47(Double.isNaN(result[i][47])?null:result[i][47]);
    		ftpPrdtPricePublishDetail.setTermPrice48(Double.isNaN(result[i][48])?null:result[i][48]);
    		ftpPrdtPricePublishDetail.setTermPrice49(Double.isNaN(result[i][49])?null:result[i][49]);
    		ftpPrdtPricePublishDetail.setTermPrice50(Double.isNaN(result[i][50])?null:result[i][50]);
    		ftpPrdtPricePublishDetail.setTermPrice51(Double.isNaN(result[i][51])?null:result[i][51]);
    		ftpPrdtPricePublishDetail.setTermPrice52(Double.isNaN(result[i][52])?null:result[i][52]);
    		ftpPrdtPricePublishDetail.setTermPrice53(Double.isNaN(result[i][53])?null:result[i][53]);
    		ftpPrdtPricePublishDetail.setTermPrice54(Double.isNaN(result[i][54])?null:result[i][54]);
    		ftpPrdtPricePublishDetail.setTermPrice55(Double.isNaN(result[i][55])?null:result[i][55]);
    		ftpPrdtPricePublishDetail.setTermPrice56(Double.isNaN(result[i][56])?null:result[i][56]);
    		ftpPrdtPricePublishDetail.setTermPrice57(Double.isNaN(result[i][57])?null:result[i][57]);
    		ftpPrdtPricePublishDetail.setTermPrice58(Double.isNaN(result[i][58])?null:result[i][58]);
    		ftpPrdtPricePublishDetail.setTermPrice59(Double.isNaN(result[i][59])?null:result[i][59]);
    		ftpPrdtPricePublishDetail.setTermPrice60(Double.isNaN(result[i][60])?null:result[i][60]);
    		ftpPrdtPricePublishDetail.setTermPrice120(Double.isNaN(result[i][61])?null:result[i][61]);
    		ftpPrdtPricePublishDetail.setTermPrice180(Double.isNaN(result[i][62])?null:result[i][62]);
    		ftpPrdtPricePublishDetail.setTermPrice240(Double.isNaN(result[i][63])?null:result[i][63]);
    		ftpPrdtPricePublishDetail.setTermPrice360(Double.isNaN(result[i][64])?null:result[i][64]);
			df.insert(ftpPrdtPricePublishDetail);
		}
//		boolean save_success=df.insert_s(resultList);
		return null;
    }
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PageUtil getFtpPrdtUtil() {
		return ftpPrdtUtil;
	}

	public void setFtpPrdtUtil(PageUtil ftpPrdtUtil) {
		this.ftpPrdtUtil = ftpPrdtUtil;
	}
	public String getCurrentDate() {
		return currentDate;
	}
	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}
	public int getIsSave() {
		return isSave;
	}
	public void setIsSave(int isSave) {
		this.isSave = isSave;
	}
    
}
