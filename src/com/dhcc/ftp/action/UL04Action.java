package com.dhcc.ftp.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.dhcc.ftp.util.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.TelMst;

/**
 * <p>
 * Title: 收益率曲线
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
 * @date 3 29, 2012 10:29:33 AM
 * 
 * @version 1.0
 */
public class UL04Action extends BoBuilder {
	private int page = 1;
	private PageUtil UL04Util = null;
	private String curveType;
	private String brNo;
	private String optionsCost;
    private String resValue;
    private String adjustRateC;
    private String adjustKeyC;
	private String date;
	private String curve;
	private String curveMarketType;
	private String curveAssetsType;
	private String startDate;
	private String endDate;
	private String curveDate;
	private double[] key;
	private double[] COF_adjust;
	private double[] VOF_adjust;
	private double[] a3;
	private double[] b2;
	private double[] c1;
	private double[] d0;
	private double[] a3_1;
	private double[] b2_1;
	private double[] c1_1;
	private double[] d0_1;
	private double[] a3_2;
	private double[] b2_2;
	private double[] c1_2;
	private double[] d0_2;
	private double[] a3_ave;
	private double[] b2_ave;
	private double[] c1_ave;
	private double[] d0_ave;
	private String a1;
	private int pageSize = 10;
	private int rowsCount = -1;
	private PageUtil resultPriceUtil = null;

	HttpServletResponse response = ServletActionContext.getResponse();
	HttpServletRequest request = ServletActionContext.getRequest();
	DaoFactory df = new DaoFactory();
	//=================================
	InputStream excelStream;   
	String fileName;  
	@SuppressWarnings("unchecked")

	public String execute() throws Exception {
		return "history_search";
	}
	
	public String distribute() throws Exception {
		return "distribute";
	}
	/*
	 * 曲线生成
	 */
	public String calculate() throws Exception {
		Map<String, double[]> curveMap = uL04BO.calculateCurve(curveType, date);
		if(curveMap.get("shiborError") != null){
			String errorMessage="无日期"+CommonFunctions.dateModifyD(date, -1)+"的Shibor利率数据，无法构建收益率曲线！请先到‘数据管理’维护";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("shiborAveError") != null){
			String errorMessage="无最近一个月的Shibor利率数据，无法构建收益率曲线！请先到‘数据管理’维护";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("stockError") != null){
			String errorMessage="无日期"+CommonFunctions.dateModifyD(date, -1)+"的银行间国债收益率数据，无法构建收益率曲线！请先到‘数据管理’维护";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("stockAveError") != null){
			String errorMessage="无最近一个月的银行间国债收益率数据，无法构建收益率曲线！请先到‘数据管理’维护";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("commonError") != null){
			String errorMessage="无日期"+CommonFunctions.dateModifyD(date, -1)+"的本行普通债收益率数据，无法构建收益率曲线！请先到‘数据管理’维护";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("commonAveError") != null){
			String errorMessage="无最近一个月的本行普通债收益率数据，无法构建收益率曲线！请先到‘数据管理’维护";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("yieldError") != null){
			String errorMessage="无日期"+CommonFunctions.dateModifyD(date, -1)+"的政策性金融债收益率数据，无法构建收益率曲线！请先到‘数据管理’维护";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("yieldAveError") != null){
			String errorMessage="无最近一个月的政策性金融债收益率数据，无法构建收益率曲线！请先到‘数据管理’维护";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("wbCurveError") != null){
			String errorMessage="无外币利率构建曲线，无法构建收益率曲线！请先到‘数据管理’维护";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("tycdError") != null){
			String errorMessage="无同业存单数据构建曲线，无法构建收益率曲线！请先到‘数据管理’维护";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		request.setAttribute("curveMap", curveMap);
		if(curveType.equals("1")) {
			double cdl = curveMap.get("cdl")[0];
			request.setAttribute("cdl", CommonFunctions.doublecut(cdl*100, 2));
			return "curve_internal";
		}else if(curveType.equals("2")) {
			return "curve_market";
		}else if(curveType.equals("3")){
			return "curve_fsxzc";
		}else{
			return "curve_shibor";
		}
	}
	//分段保存收益率曲线
	public String save() throws Exception {
		String type = curveType;
		if (type.length() == 1) type = "0"+type;

		if (curveType.equals("1")) {//存贷款收益率曲线
			//先检查数据库中是否有当天，市场类型相同的数据，如果存在则删除后再添加
			uL04BO.checkSaveAddDel(type, date, brNo, "FtpYieldCurve");
			uL04BO.internalCurveSave(type, date, brNo, key, a3, b2, c1, d0, a3_1, b2_1, c1_1, d0_1, a3_2, b2_2, c1_2, d0_2);
		}else if(curveType.equals("2")){//市场收益率曲线
			//保存当天的收益率曲线
			uL04BO.marketCurveSave(type,"00", date, brNo, key, a3, b2, c1, d0);
			if(a3_ave != null) {
				uL04BO.marketCurveSave(type,"01", date, brNo, key, a3_ave, b2_ave, c1_ave, d0_ave);
			}
		}else if(curveType.equals("3")){//非生息收益率曲线
			uL04BO.fsxzcCurveSave(type, date, brNo, key, a3, b2, c1, d0);
		}else if((curveType.equals("4"))){//同业收益率曲线
			uL04BO.shiborCurveSave(type, date, brNo, key, a3, b2, c1, d0);
		}
//		uL04BO.saveAdjust(curveType, date, brNo, adjustKeyC, adjustRateC);
		return null;
	}


	//试发布--保存到ftp_yield_curve_sfb表中-分段保存收益率曲线
	public String sfb() throws Exception {
		String type = curveType;
		if (type.length() == 1) type = "0"+type;

		if (curveType.equals("1")) {//存贷款收益率曲线
			//先检查数据库中是否有当天，市场类型相同的数据，如果存在则删除后再添加
			uL04BO.checkSaveAddDel(type, date, brNo, "FtpYieldCurveSfb");
			uL04BO.internalCurveSfb(type, date, brNo, key, a3, b2, c1, d0, a3_1, b2_1, c1_1, d0_1, a3_2, b2_2, c1_2, d0_2);
		}else {//市场收益率曲线，需要保存一个周期内的收益率曲线，10天
			Map curveMap = uL04BO.generateMarketCurve(date, brNo, curveType, key, a3, b2, c1, d0);
			if(curveMap.get("repoError") != null){
	 			String errorMessage="日期"+date+"最近10个工作日的质押式回购利率数据不全，无法构建收益率曲线！请先到‘数据管理’导入";
				request.setAttribute("errorMessage", errorMessage);
				return "curve_error";
			}
			if(curveMap.get("billsError") != null){
	 			String errorMessage="日期"+date+"最近10个工作日的央行票据利率数据不全，无法构建收益率曲线！请先到‘数据管理’导入";
				request.setAttribute("errorMessage", errorMessage);
				return "curve_error";
			}
	 		if(curveMap.get("yieldMap") != null){
	 			String errorMessage="日期"+date+"最近10个工作日的政策性金融债收益率数据不全，无法构建收益率曲线！请先到‘数据管理’导入";
				request.setAttribute("errorMessage", errorMessage);
				return "curve_error";
			}
	 		if(curveMap.get("ofRateSpreadMap") != null){
	 			String errorMessage="无最近30个工作日的普通债和金融债收益率点差数据，无法构建收益率曲线！请先到‘数据管理’导入";
				request.setAttribute("errorMessage", errorMessage);
				return "curve_error";
			}
	        List<String> newDateList = (List<String>)curveMap.get("newDateList");
	        List<double[]> keyList = (List<double[]>)curveMap.get("keyList");
	        List<double[]> a3List = (List<double[]>)curveMap.get("a3List");
	        List<double[]> b2List = (List<double[]>)curveMap.get("b2List");
	        List<double[]> c1List = (List<double[]>)curveMap.get("c1List");
	        List<double[]> d0List = (List<double[]>)curveMap.get("d0List");
	        for (int i = 0; i < newDateList.size(); i++) {
	        	//保存收益率曲线
		 	    System.out.println("------------开始保存"+newDateList.get(i)+"天的收益率曲线--------");
		 	    //先检查数据库中是否有当天，市场类型相同的数据，如果存在则删除后再添加
				uL04BO.checkSaveAddDel(type, newDateList.get(i), brNo, "FtpYieldCurveSfb");
		 		uL04BO.marketCurveSfb(type, newDateList.get(i), brNo, keyList.get(i), a3List.get(i),
		 				b2List.get(i), c1List.get(i), d0List.get(i));
	        }

		}
		return null;
	}

	//查看历史收益率曲线列表
	public String history_list() throws Exception {
		TelMst telMst = (TelMst)request.getSession().getAttribute("userBean");
		UL04Util = uL04BO.dofind(brNo, curveMarketType, curveAssetsType, curveDate, telMst, page, rowsCount);
		request.setAttribute("UL04Util", UL04Util);
		return "history_list";
	}
	//根据选择的曲线获取关键点
	public String curve_history() throws Exception {
		String[] curveStr = curve.split(",");
		List<Double[]> list_curve = new ArrayList<Double[]>();
		List<String> list_curveName = new ArrayList<String>();
		List<String> list_curveType = new ArrayList<String>();
		List<SCYTCZlineF> list_F = new ArrayList<SCYTCZlineF>();
		for (int i = 0; i < curveStr.length; i++) {
			String[] curve2 = curveStr[i].split("\\|");//名称、市场类型、资产类型、日期、机构
			SCYTCZlineF F = FtpUtil.getSCYTCZlineF_fromDB(curve2[1]+""+curve2[2], curve2[3], curve2[4]);
			Double[] curve = new Double[29];
			//存贷款收益率曲线的存款线和基准线，显示"活期"
			if((curve2[1].equals("01")&&(curve2[2].equals("00")||curve2[2].equals("02"))) || curve2[1].equals("06")) {
				curve[0] = F.getY_SCYTCZline(0);
			}
			curve[1] = F.getY_SCYTCZline(1/30.0);
			curve[2] = F.getY_SCYTCZline(7/30.0);
			curve[3] = F.getY_SCYTCZline(14/30.0);
			curve[4] = F.getY_SCYTCZline(21/30.0);
			for (int j = 1; j < 12; j++) {
				curve[j+4] = F.getY_SCYTCZline(j);//每月
			}
			for (int k = 1; k < 11; k++) {//1Y到10Y
				curve[k+15] = F.getY_SCYTCZline(k*12);
			}
			curve[26] = F.getY_SCYTCZline(15*12);//15Y
			curve[27] = F.getY_SCYTCZline(20*12);//20Y
			curve[28] = F.getY_SCYTCZline(30*12);//30Y
			list_curve.add(curve);
//			list_curveName.add(curve2[3]+"-"+brInfoBo.getInfo(curve2[4]).getBrName()+"-"+(curve2[1].equals("01")? "存贷款":"市场")+"-"+FtpUtil.getAssetsType(curve2[1],curve2[2]));
			//list_curveName.add(curve2[3]+"-"+(curve2[1].equals("01")? "存贷款":(curve2[1].equals("02")? "市场":"非生息资产"))+"-"+FtpUtil.getAssetsType(curve2[1],curve2[2]));
			list_curveName.add(curve2[3]+"-"+(curve2[1].equals("01")? "存贷款":(curve2[1].equals("02")? (curve2[2].equals("00")? "市场":"市场-平均线"):(curve2[1].equals("03")?"非生息资产":(curve2[1].equals("04")?"同业":(curve2[1].equals("05")?"shibor":"同业存单"))))));
			list_F.add(F);
			list_curveType.add(curve2[1]);
			request.setAttribute("list_curve", list_curve);
			request.getSession().setAttribute("list_curveName", list_curveName);
			request.getSession().setAttribute("list_F", list_F);
			request.getSession().setAttribute("list_curveType", list_curveType);
		}
		return "curve_history";
	}
	/**
	 * 导出生成的收益率曲线EXCEL
	 */
	public String curveExport() {
		SCYTCZlineF f00 = null;// 基准
		SCYTCZlineF f01 = null;// 贷款
//		SCYTCZlineF f02 = null;// 存款


		double[][] A00 = null;
		double[][] A01 = null;
//		double[][] A02 = null;
		double[] x = null;
		String[] curveTerm = null;
		double[][] curveValue = null;

		String year = date.substring(0, 4);
		//Integer dayNum = 360;//DateUtil.isLeapYear(Integer.valueOf(year)) == true ? 366 : 365;// 所在年总的日期数
		Integer dayNum = DateUtil.isLeapYear(Integer.valueOf(year)) == true ? 366 : 365;// 所在年总的日期数

		A00 = new double[a3.length][4];
		A01 = new double[a3.length][4];
//		A02 = new double[a3.length][4];
		x = new double[a3.length + 1];
		if(curveType.equals("1")) {//存贷款收益率曲线
			for (int i = 0; i < a3.length; i++) {
				// 基准
				A00[i][0] = a3[i];
				A00[i][1] = b2[i];
				A00[i][2] = c1[i];
				A00[i][3] = d0[i];

				// 贷款
//				A01[i][0] = a3_1[i];
//				A01[i][1] = b2_1[i];
//				A01[i][2] = c1_1[i];
//				A01[i][3] = d0_1[i];

				// 存款
//				A02[i][0] = a3_2[i];
//				A02[i][1] = b2_2[i];
//				A02[i][2] = c1_2[i];
//				A02[i][3] = d0_2[i];

				x[i] = key[i];
				x[i + 1] = key[i + 1];
			}
			f00 = new SCYTCZlineF(A00, x);
//			f01 = new SCYTCZlineF(A01, x);
//			f02 = new SCYTCZlineF(A02, x);

			curveTerm = new String[dayNum + 1 + 348];// 基准线和存款的期限，多加了活期
			curveValue = new double[dayNum + 1 + 348][1];// 1条定价值
			curveTerm[0] = "活期";
			curveValue[0][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(0)*100, 4);
//			curveValue[0][1] = Double.NaN;
//			curveValue[0][2] = CommonFunctions.doublecut(f02.getY_SCYTCZline(0)*100, 4);
			for (int i = 0; i < dayNum; i++) {
				//curveTerm[i+1] = i == dayNum - 1 ? "1年" : (i + 1) + "天";
				curveTerm[i+1] = (i + 1) + "天";
				curveValue[i+1][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
//				curveValue[i+1][1] = CommonFunctions.doublecut(f01.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
//				curveValue[i+1][2] = CommonFunctions.doublecut(f02.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
			}
			int m = 13;
			for (int j = dayNum; j < dayNum + 348; j++) {
				curveTerm[j+1] = m % 12 == 0 ? m / 12 + "年" : m + "月";
				curveValue[j+1][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(m)*100, 4);
//				curveValue[j+1][1] = CommonFunctions.doublecut(f01.getY_SCYTCZline(m)*100, 4);
//				curveValue[j+1][2] = CommonFunctions.doublecut(f02.getY_SCYTCZline(m)*100, 4);
				m++;
			}
		}else if(curveType.equals("2")) {//市场收益率曲线
			for (int i = 0; i < a3.length; i++) {
				//基准线
				A00[i][0] = a3[i];
				A00[i][1] = b2[i];
				A00[i][2] = c1[i];
				A00[i][3] = d0[i];

				//平均线
				if(a3_ave != null) {
					A01[i][0] = a3_ave[i];
					A01[i][1] = b2_ave[i];
					A01[i][2] = c1_ave[i];
					A01[i][3] = d0_ave[i];
				}

				x[i] = key[i];
				x[i + 1] = key[i + 1];
			}
			f00 = new SCYTCZlineF(A00, x);
			int n = 1;
			if(a3_ave != null) {
				f01 = new SCYTCZlineF(A01, x);//平均线
				n = 2;
			}
			curveTerm = new String[dayNum + 348];// 期限

			curveValue = new double[dayNum + 348][n];// 1条定价值
			for (int i = 0; i < dayNum; i++) {
				//curveTerm[i] = i == dayNum - 1 ? "1年" : (i + 1) + "天";
				curveTerm[i] = (i + 1) + "天";
				curveValue[i][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
				if(a3_ave != null)curveValue[i][1] = CommonFunctions.doublecut(f01.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
			}
			int m = 13;
			for (int j = dayNum; j < dayNum + 348; j++) {
				curveTerm[j] = m % 12 == 0 ? m / 12 + "年" : m + "月";
				curveValue[j][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(m)*100, 4);
				if(a3_ave != null)curveValue[j][1] = CommonFunctions.doublecut(f01.getY_SCYTCZline(m)*100, 4);
				m++;
			}
		}else if(curveType.equals("3")) {//国债收益率曲线
			for (int i = 0; i < a3.length; i++) {
				// 基准
				A00[i][0] = a3[i];
				A00[i][1] = b2[i];
				A00[i][2] = c1[i];
				A00[i][3] = d0[i];

				x[i] = key[i];
				x[i + 1] = key[i + 1];
			}
			f00 = new SCYTCZlineF(A00, x);
			curveTerm = new String[7 + 348];
			curveValue = new double[7 + 348][1];// 1条定价值
			for (int i = 6; i < 12; i++) {//从6月开始
				curveTerm[i-6] = i + "月";
				curveValue[i-6][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(i)*100, 4);
			}
			curveTerm[6] ="1年";
			curveValue[6][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(12)*100, 4);
			int m = 13;
			for (int j = 7; j < 7 + 348; j++) {
				curveTerm[j] = m % 12 == 0 ? m / 12 + "年" : m + "月";
				curveValue[j][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(m)*100, 4);
//				curveValue[j+1][1] = CommonFunctions.doublecut(f01.getY_SCYTCZline(m)*100, 4);
//				curveValue[j+1][2] = CommonFunctions.doublecut(f02.getY_SCYTCZline(m)*100, 4);
				m++;
			}
		}
		else if(curveType.equals("4")) {//外币收益率曲线
			dayNum=360;//由于外币利率只有到360天的，此处将日期置为360天
			for (int i = 0; i < a3.length; i++) {
				A00[i][0] = a3[i];
				A00[i][1] = b2[i];
				A00[i][2] = c1[i];
				A00[i][3] = d0[i];
				x[i] = key[i];
				x[i + 1] = key[i + 1];
			}
			f00 = new SCYTCZlineF(A00, x);
			int n = 1;
			curveTerm = new String[dayNum ];// 期限

			curveValue = new double[dayNum ][n];// 1条定价值
			for (int i = 0; i < dayNum; i++) {
				curveTerm[i] = (i + 1) + "天";
				curveValue[i][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
			}
			int m = 13;
			for (int j = dayNum; j < dayNum ; j++) {
				curveTerm[j] = m % 12 == 0 ? m / 12 + "年" : m + "月";
				curveValue[j][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(m)*100, 4);
				m++;
			}
		}else if(curveType.equals("5")) {//shibor
			dayNum = 360;
			for (int i = 0; i < a3.length; i++) {
				A00[i][0] = a3[i];
				A00[i][1] = b2[i];
				A00[i][2] = c1[i];
				A00[i][3] = d0[i];
				x[i] = key[i];
				x[i + 1] = key[i + 1];
			}
			f00 = new SCYTCZlineF(A00, x);
			int n = 1;
			curveTerm = new String[dayNum ];// 期限

			curveValue = new double[dayNum ][n];// 1条定价值
			for (int i = 0; i < dayNum; i++) {
				curveTerm[i] = (i + 1) + "天";
				curveValue[i][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
			}
			int m = 13;
			for (int j = dayNum; j < dayNum ; j++) {
				curveTerm[j] = m % 12 == 0 ? m / 12 + "年" : m + "月";
				curveValue[j][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(m)*100, 4);
				m++;
			}
		}else if(curveType.equals("6")) {//同业存单收益率曲线
			for (int i = 0; i < a3.length; i++) {
				// 基准
				A00[i][0] = a3[i];
				A00[i][1] = b2[i];
				A00[i][2] = c1[i];
				A00[i][3] = d0[i];

				x[i] = key[i];
				x[i + 1] = key[i + 1];
			}
			f00 = new SCYTCZlineF(A00, x);
//			f01 = new SCYTCZlineF(A01, x);
//			f02 = new SCYTCZlineF(A02, x);

			curveTerm = new String[dayNum + 1 + 348];// 基准线和存款的期限，多加了活期
			curveValue = new double[dayNum + 1 + 348][1];// 1条定价值
			curveTerm[0] = "活期";
			curveValue[0][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(0)*100, 4);
			for (int i = 0; i < dayNum; i++) {
				curveTerm[i+1] = (i + 1) + "天";
				curveValue[i+1][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
			}
			int m = 13;
			for (int j = dayNum; j < dayNum + 24; j++) {
				curveTerm[j+1] = m % 12 == 0 ? m / 12 + "年" : m + "月";
				curveValue[j+1][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(m)*100, 4);
				m++;
			}

		}

		HSSFWorkbook workbook = uL04BO.getWorkbook(curveTerm, curveValue, curveType, date, brNo);

		if (workbook != null) {
			//输出EXCEL
			ExcelExport.workbookInputStream(response, workbook, date + "日-机构"+brNo+"-"+(curveType.equals("1")?"存贷款":(curveType.equals("2")?"市场":(curveType.equals("3")?"国债":(curveType.equals("4")?"外币":(curveType.equals("5")?"shibor":"同业存单")))))+"收益率曲线");
		}
		return null;
	}

	/**
	 * 收益率曲线历史查-导出EXCEL
	 */
	public String curveHistoryExport() {

		List<String> list_curveName = (List<String>)request.getSession().getAttribute("list_curveName");
		List<SCYTCZlineF> list_F = (List<SCYTCZlineF>)request.getSession().getAttribute("list_F");
		HSSFWorkbook workbook = uL04BO.getHistoryWorkbook(list_curveName,list_F);

		if (workbook != null) {
			// 第四步，将工作簿写入最上面定义的InputStream流-名称为excelStream，这个名字对应struts.xml中配置的inputName参数。
			workbook2InputStream(workbook, "收益率曲线历史");
			return "success";
		} else {
			request.setAttribute("message", "创建Excel失败");
			return "error";
		}
	}
	
	//创建workbook,将workbook写入到InputStream
	public void workbook2InputStream(HSSFWorkbook workbook,String fileName) {     
		 try{ 
			 this.fileName = FtpUtil.toUtf8String(fileName); //设置fileName,     
		     ByteArrayOutputStream baos = new ByteArrayOutputStream();
		     workbook.write(baos);
		     baos.flush();       
		     byte[] aa = baos.toByteArray();      
		     excelStream = new ByteArrayInputStream(aa, 0, aa.length);     
		     baos.close();
		 }
		 catch(Exception e){e.printStackTrace();}
	}
	  
	
	public String getBrNo() {
		return brNo;
	}

	public void setBrNo(String brNo) {
		this.brNo = brNo;
	}

	public String getResValue() {
		return resValue;
	}

	public void setResValue(String resValue) {
		this.resValue = resValue;
	}

	public String getCurveType() {
		return curveType;
	}

	public void setCurveType(String curveType) {
		this.curveType = curveType;
	}

	public String getOptionsCost() {
		return optionsCost;
	}

	public void setOptionsCost(String optionsCost) {
		this.optionsCost = optionsCost;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double[] getKey() {
		return key;
	}

	public void setKey(double[] key) {
		this.key = key;
	}

	public double[] getCOF_adjust() {
		return COF_adjust;
	}

	public void setCOF_adjust(double[] cOFAdjust) {
		COF_adjust = cOFAdjust;
	}

	public double[] getVOF_adjust() {
		return VOF_adjust;
	}

	public void setVOF_adjust(double[] vOFAdjust) {
		VOF_adjust = vOFAdjust;
	}

	public double[] getA3() {
		return a3;
	}

	public void setA3(double[] a3) {
		this.a3 = a3;
	}

	public double[] getB2() {
		return b2;
	}

	public void setB2(double[] b2) {
		this.b2 = b2;
	}

	public double[] getC1() {
		return c1;
	}

	public void setC1(double[] c1) {
		this.c1 = c1;
	}

	public double[] getD0() {
		return d0;
	}

	public void setD0(double[] d0) {
		this.d0 = d0;
	}

	public double[] getA3_1() {
		return a3_1;
	}

	public void setA3_1(double[] a3_1) {
		this.a3_1 = a3_1;
	}

	public double[] getB2_1() {
		return b2_1;
	}

	public void setB2_1(double[] b2_1) {
		this.b2_1 = b2_1;
	}

	public double[] getC1_1() {
		return c1_1;
	}

	public void setC1_1(double[] c1_1) {
		this.c1_1 = c1_1;
	}

	public double[] getD0_1() {
		return d0_1;
	}

	public void setD0_1(double[] d0_1) {
		this.d0_1 = d0_1;
	}

	public double[] getA3_2() {
		return a3_2;
	}

	public void setA3_2(double[] a3_2) {
		this.a3_2 = a3_2;
	}

	public double[] getB2_2() {
		return b2_2;
	}

	public void setB2_2(double[] b2_2) {
		this.b2_2 = b2_2;
	}

	public double[] getC1_2() {
		return c1_2;
	}

	public void setC1_2(double[] c1_2) {
		this.c1_2 = c1_2;
	}

	public double[] getD0_2() {
		return d0_2;
	}

	public void setD0_2(double[] d0_2) {
		this.d0_2 = d0_2;
	}

	public String getCurve() {
		return curve;
	}

	public void setCurve(String curve) throws UnsupportedEncodingException {
		this.curve = new String(curve.getBytes("iso-8859-1"),"UTF-8");
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PageUtil getUL04Util() {
		return UL04Util;
	}

	public void setUL04Util(PageUtil uL04Util) {
		UL04Util = uL04Util;
	}

	public String getCurveMarketType() {
		return curveMarketType;
	}

	public void setCurveMarketType(String curveMarketType) {
		this.curveMarketType = curveMarketType;
	}

	public String getCurveAssetsType() {
		return curveAssetsType;
	}

	public void setCurveAssetsType(String curveAssetsType) {
		this.curveAssetsType = curveAssetsType;
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

	public PageUtil getResultPriceUtil() {
		return resultPriceUtil;
	}

	public void setResultPriceUtil(PageUtil resultPriceUtil) {
		this.resultPriceUtil = resultPriceUtil;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAdjustRateC() {
		return adjustRateC;
	}

	public void setAdjustRateC(String adjustRateC) {
		this.adjustRateC = adjustRateC;
	}

	public String getAdjustKeyC() {
		return adjustKeyC;
	}

	public void setAdjustKeyC(String adjustKeyC) {
		this.adjustKeyC = adjustKeyC;
	}

	public String getA1() {
		return a1;
	}

	public void setA1(String a1) {
		this.a1 = a1;
	}

	public String getCurveDate() {
		return curveDate;
	}

	public void setCurveDate(String curveDate) {
		this.curveDate = curveDate;
	}

}
