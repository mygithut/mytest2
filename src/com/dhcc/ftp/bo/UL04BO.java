package com.dhcc.ftp.bo;

/**
 * @desc:收益率曲线维护
 * @author :孙红玉
 * @date ：2012-04-16
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.Region;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.Ftp1PrdtCkzbjAdjust;
import com.dhcc.ftp.entity.FtpInterestMarginDivide;
import com.dhcc.ftp.entity.FtpPoolInfo;
import com.dhcc.ftp.entity.FtpPublicRate;
import com.dhcc.ftp.entity.FtpYieldCurve;
import com.dhcc.ftp.entity.FtpYieldCurveSfb;
import com.dhcc.ftp.entity.TelMst;
import com.dhcc.ftp.util.CommonFunctions;
import com.dhcc.ftp.util.DateUtil;
import com.dhcc.ftp.util.FtpUtil;
import com.dhcc.ftp.util.IDUtil;
import com.dhcc.ftp.util.LrmUtil;
import com.dhcc.ftp.util.PageUtil;
import com.dhcc.ftp.util.SCYTCZ_Compute;
import com.dhcc.ftp.util.SCYTCZlineF;

public class UL04BO extends BaseBo{

	//list分页查询
	public PageUtil dofind(String brNo,
			String curveMarketType, String curveAssetsType, String curveDate,
			TelMst telMst, int currentPage, int rowsCount) {
		int pageSize = 6;
		String sql = "select m.*,b.br_name from (select substr(t.curve_no,1,4) curveNo, t.curve_name, " +
				"t.curve_market_type, t.curve_assets_type, t.curve_date,t.br_no, " +
				"row_number() over(partition by br_no,substr(curve_no,1,4) order by curve_date desc, curve_No ) rn" +
				" from ftp.ftp_yield_curve t where 1=1 ";
		if (curveDate != null && !curveDate.equals("") && !curveDate.equals("null")) {
			sql += " and curve_date <= "+curveDate;
		}
		sql +=") m left join ftp.br_mst b on b.br_no=m.br_no  where rn=1 ";
		//sql = "select distinct substr(curve_no,1,4) curveNo, substr(curve_name,1,13), curve_market_type, curve_assets_type, curve_date from ftp_yield_curve where 1 = 1";
		if (brNo != null && !brNo.equals("") && !brNo.equals("null")) {
			sql += " and m.br_no = '"+brNo+"'";
		}else if(Integer.valueOf(telMst.getBrMst().getManageLvl())<=2){//如果不是省联社，要获取对应的县联社机构
			sql += " and m.br_no = '"+FtpUtil.getXlsBrNo(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl())+"'";
		}
		if (curveMarketType != null && !curveMarketType.equals("") && !curveMarketType.equals("null")) {
			sql += " and curve_market_type = '"+curveMarketType+"'";
		}
		if (curveAssetsType != null && !curveAssetsType.equals("") && !curveAssetsType.equals("null")) {
			sql += " and curve_assets_type = '"+curveAssetsType+"'";
		}
		
		sql += " order by br_no desc, curve_date desc, curveNo";
		String pageName = "SYLQXLSCK_history_list.action?brNo="+brNo+"&curveMarketType="+curveMarketType+"&curveAssetsType="+curveAssetsType+"&curveDate="+curveDate;
		if(rowsCount<0){
			rowsCount=daoFactory.query1(sql, null).size();
		}
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		int pageCount=rowsCount/pageSize;
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		int start=(currentPage-1)*pageSize+1;
		int end=currentPage*pageSize;
		if(currentPage==rowsCount/pageSize+1){
			end=((currentPage-1)*pageSize)+rowsCount%pageSize;
		}
		
		sql = "select * from (select m.*, rownumber() over(order by m.br_no desc,m.curve_date desc,m.curveNo) as rownumber from("+sql+") m ) where rownumber between "+start+" and "+end;
		List list=daoFactory.query1(sql, null);	
			
		String pageLine=this.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		return new PageUtil(list,pageLine);
	}

	/**
	 * 获取平均存贷款收益率曲线
	 * @param minDate
	 * @param maxDate
	 * @return
	 */
	public Map<String, double[]> getVCAveYieldKeyRate(String minDate, String maxDate) {
		double[] keyRate = new double[21];// 关键点利率-由昨天的数据生成

		// 存贷款收益率曲线 21个基准点：活期<0>、1个月<1>、45天<1.5>、2个月<2>、75天个月<2.5>、3个月<3>、 6个月<6>、9个月<9>、1年<12>、2年<24>、3年<36>、4年<48>、 5年<60>、 6年<72>、 7年<84>、8年<96>、 9年<108>、 10年<120>、15年<180>、20年<240>、30年<360>

		Map<String, double[]> curveMap = new HashMap<String, double[]>();
		// 1M、3M，采用shiborError利率
		Map<String, Double> shiborRateMap = FtpUtil.getShiborRate(minDate, maxDate);//获取月均的数据
		if (shiborRateMap == null) {
			curveMap.put("shiborAveError", new double[0]);
			return curveMap;
		}
		keyRate[1] =  CommonFunctions.doublecut(shiborRateMap.get("1M"), 7);
		keyRate[5] = CommonFunctions.doublecut(shiborRateMap.get("3M"), 7);// 3个月

		keyRate[2] = (keyRate[5]-keyRate[1])*1/4+keyRate[1];//45D使用1M\3M进行线性插值
		keyRate[3] = (keyRate[5]-keyRate[1])*1/2+keyRate[1];//2M使用1M\3M进行线性插值
		keyRate[4] = (keyRate[5]-keyRate[1])*3/4+keyRate[1];//75D使用1M\3M进行线性插值
		//汇和测试，6、9个月采用央票收益率
		/*
			Map<String, Double> billRateMap = FtpUtil.getBillsRate(minDate, maxDate);//获取月均的数据
			keyRate[6] = CommonFunctions.doublecut(billRateMap.get("6M"), 7);// 6个月
			keyRate[7] = CommonFunctions.doublecut(billRateMap.get("9M"), 7);// 9个月
		*/
		// 6M-30Y，采用国债收益率
		Map<String, Double> stockRateMap = FtpUtil.getStockRate(minDate, maxDate);//获取月均的数据
		if (stockRateMap == null || stockRateMap.size() == 0) {
			curveMap.put("stockAveError", new double[0]);
			return curveMap;
		}
		keyRate[6] = CommonFunctions.doublecut(stockRateMap.get("6M"), 7);// 6个月
		keyRate[7] = CommonFunctions.doublecut(stockRateMap.get("9M"), 7);// 9个月
		keyRate[8] = CommonFunctions.doublecut(stockRateMap.get("1Y"), 7);// 1年期
		keyRate[9] = CommonFunctions.doublecut(stockRateMap.get("2Y"), 7);// 2年期
		keyRate[10] = CommonFunctions.doublecut(stockRateMap.get("3Y"), 7);// 3年期
		keyRate[11] = CommonFunctions.doublecut(stockRateMap.get("4Y"), 7);// 4年期
		keyRate[12] = CommonFunctions.doublecut(stockRateMap.get("5Y"), 7);// 5年期
		keyRate[13] = CommonFunctions.doublecut(stockRateMap.get("6Y"), 7);// 6年期
		keyRate[14] = CommonFunctions.doublecut(stockRateMap.get("7Y"), 7);// 7年期
		keyRate[15] = CommonFunctions.doublecut(stockRateMap.get("8Y"), 7);// 8年期
		keyRate[16] = CommonFunctions.doublecut(stockRateMap.get("9Y"), 7);// 9年期
		keyRate[17] = CommonFunctions.doublecut(stockRateMap.get("10Y"), 7);// 10年期
		keyRate[18] = CommonFunctions.doublecut(stockRateMap.get("15Y"), 7);// 15年期
		keyRate[19] = CommonFunctions.doublecut(stockRateMap.get("20Y"), 7);// 20年期
		keyRate[20] = CommonFunctions.doublecut(stockRateMap.get("30Y"), 7);// 30年期


		curveMap.put("keyRate", keyRate);
		return curveMap;
	}

	/**
	 * 获取存贷款收益率曲线
	 * @param maxDate
	 * @return
	 */
	public Map<String, double[]> getVCYieldKeyRate(String maxDate) throws ParseException {
		double[] keyRate = new double[21];// 关键点利率-由昨天的数据生成

		// 存贷款收益率曲线 21个基准点：活期<0>、1个月<1>、45天<1.5>、2个月<2>、75天个月<2.5>、3个月<3>、 6个月<6>、9个月<9>、1年<12>、2年<24>、3年<36>、4年<48>、 5年<60>、 6年<72>、 7年<84>、8年<96>、 9年<108>、 10年<120>、15年<180>、20年<240>、30年<360>

		Map<String, double[]> curveMap = new HashMap<String, double[]>();
		// 1M、3M，采用shiborError利率
		Map<String, Double> shiborRateMap = FtpUtil.getShiborRate(maxDate);//获取前一天的数据
		if (shiborRateMap == null) {
			curveMap.put("shiborError", new double[0]);
			return curveMap;
		}

		keyRate[1] =  CommonFunctions.doublecut(shiborRateMap.get("1M"), 7);
		keyRate[5] = CommonFunctions.doublecut(shiborRateMap.get("3M"), 7);// 3个月

		keyRate[2] = (keyRate[5]-keyRate[1])*1/4+keyRate[1];//45D使用1M\3M进行线性插值
		keyRate[3] = (keyRate[5]-keyRate[1])*1/2+keyRate[1];//2M使用1M\3M进行线性插值
		keyRate[4] = (keyRate[5]-keyRate[1])*3/4+keyRate[1];//75D使用1M\3M进行线性插值
		// 6M-30Y，采用国债收益率
		Map<String, Double> stockRateMap = FtpUtil.getStockRate(maxDate);//获取前一天的数据
		if (stockRateMap == null || stockRateMap.size() == 0) {
			curveMap.put("stockError", new double[0]);
			return curveMap;
		}
		keyRate[6] = CommonFunctions.doublecut(stockRateMap.get("6M"), 7);// 6个月
		keyRate[7] = CommonFunctions.doublecut(stockRateMap.get("9M"), 7);// 9个月
		keyRate[8] = CommonFunctions.doublecut(stockRateMap.get("1Y"), 7);// 1年期
		keyRate[9] = CommonFunctions.doublecut(stockRateMap.get("2Y"), 7);// 2年期
		keyRate[10] = CommonFunctions.doublecut(stockRateMap.get("3Y"), 7);// 3年期
		keyRate[11] = CommonFunctions.doublecut(stockRateMap.get("4Y"), 7);// 4年期
		keyRate[12] = CommonFunctions.doublecut(stockRateMap.get("5Y"), 7);// 5年期
		keyRate[13] = CommonFunctions.doublecut(stockRateMap.get("6Y"), 7);// 6年期
		keyRate[14] = CommonFunctions.doublecut(stockRateMap.get("7Y"), 7);// 7年期
		keyRate[15] = CommonFunctions.doublecut(stockRateMap.get("8Y"), 7);// 8年期
		keyRate[16] = CommonFunctions.doublecut(stockRateMap.get("9Y"), 7);// 9年期
		keyRate[17] = CommonFunctions.doublecut(stockRateMap.get("10Y"), 7);// 10年期
		keyRate[18] = CommonFunctions.doublecut(stockRateMap.get("15Y"), 7);// 15年期
		keyRate[19] = CommonFunctions.doublecut(stockRateMap.get("20Y"), 7);// 20年期
		keyRate[20] = CommonFunctions.doublecut(stockRateMap.get("30Y"), 7);// 30年期


		curveMap.put("keyRate", keyRate);
		return curveMap;
	}
	/**
	 * 曲线生成
	 * 
	 * @param curveType
	 * @param date
	 * @return
	 */
	public Map<String, double[]> calculateCurve(String curveType, String date) throws ParseException {
		Map<String, double[]> curveMap = new HashMap<String, double[]>();
		//活期沉淀率月初日期
		String cdlDate = date;
		double[] key = null;// 基准关键点
		//double[] keyRateV = null;// 存款收益率曲线关键点利率
		//double[] keyRateC = null;// 贷款收益率曲线关键点利率
		double[] keyRate = null;// 关键点利率
		double[] keyRateAve = null;// 市场平均收益率关键点利率
		/*
		 * 1.构建无风险收益率曲线
		 */
		if (curveType.equals("1")) {
			// 存贷款收益率曲线 21个基准点：活期<0>、1个月<1>、45天<1.5>、2个月<2>、75天个月<2.5>、3个月<3>、 6个月<6>、9个月<9>、1年<12>、2年<24>、3年<36>、4年<48>、 5年<60>、 6年<72>、 7年<84>、8年<96>、 9年<108>、 10年<120>、15年<180>、20年<240>、30年<360>
			key = new double[21];
			key[0] = 0;
			key[1] = 1;
			key[2] = 1.5;
			key[3] = 2;
			key[4] = 2.5;
			key[5] = 3;
			key[6] = 6;
			key[7] = 9;
			key[8] = 12;
			key[9] = 24;
			key[10] = 36;
			key[11] = 48;
			key[12] = 60;
			key[13] = 72;
			key[14] = 84;
			key[15] = 96;
			key[16] = 108;
			key[17] = 120;
			key[18] = 180;
			key[19] = 240;
			key[20] = 360;

			keyRate = new double[21];
			String minDate = null;
			String source_date = date;
			int nain_days = 364;

			//判断是否为闰年
			if(DateUtil.isLeapYear(Integer.valueOf(date.substring(0,4))))
				 nain_days = 365;

			//判断是否为月初，如果是，则计算月均的平均收益率曲线关键点
			//ls:由于前台强制认定日期为一日，此处不用再判断。## 修改月初判断条件：每月第一条发布的曲线为上月平均，不管是不是一号(防止一号为节假日时错过曲线发布)
			if(date.substring(6,8).equals("01")) {
				//if(FtpUtil.checkFisrtDay(date)){
				date = CommonFunctions.dateModifyD(date, -1);//处理到上月末
				minDate = date.substring(0,6)+"01";//获取时段左端点，处理到月初
				curveMap = this.getVCAveYieldKeyRate(minDate, date);//上月月均的收益率曲线关键点
			}else {
				curveMap = this.getVCYieldKeyRate(date);//当天前一天的最新收益率曲线关键点
			}
			if(curveMap.get("keyRate") != null) {
				keyRate =  curveMap.get("keyRate");
				int days = 0;
				String yuefen = "";
				if(source_date.substring(6,8).equals("01")){
					yuefen = date.substring(4,6);
				}else{
					yuefen = CommonFunctions.dateModifyM(date, -1).substring(4,6);
				}
				Map<String,String> bigMonth = new HashMap<String, String>();
				Map<String,String> smallMonth = new HashMap<String, String>();
				Map<String,String> twoMonth = new HashMap<String, String>();
				bigMonth.put("01", "01");
				bigMonth.put("03", "03");
				bigMonth.put("05", "05");
				bigMonth.put("07", "07");
				bigMonth.put("08", "08");
				bigMonth.put("10", "10");
				bigMonth.put("12", "12");
				twoMonth.put("02", "02");
				smallMonth.put("04", "04");
				smallMonth.put("06", "06");
				smallMonth.put("09", "09");
				smallMonth.put("11", "11");

				if(bigMonth.containsKey(yuefen)){
					days=30;
				}else if(smallMonth.containsKey(yuefen)){
					days=29;
				}else{
					if (DateUtil.isLeapYear(Integer.valueOf(date
							.substring(0, 4)))) {
						days = 28;
					} else {
						days = 27;
					}
				}

				Map<String, Double> commonRateMap1 = FtpUtil.getCommonRate(String.valueOf(CommonFunctions.pub_base_deadlineD(Long.valueOf(date),-days)), date);
				if (commonRateMap1 == null || commonRateMap1.size() == 0) {
					curveMap.put("commonAveError", new double[0]);
					return curveMap;
				}

				Map<String, Double> financialMap1 = FtpUtil.getYieldRate(String.valueOf(CommonFunctions.pub_base_deadlineD(Long.valueOf(date),-nain_days)),date);
				if (financialMap1 == null || financialMap1.size() == 0) {
					curveMap.put("yieldAveError", new double[0]);
					return curveMap;
				}
				//对生成的基准曲线进行信用风险加点，获取最近一个月内的本行普通债和金融债点差均值
				String[] adjustTerm = {"6M", "9M", "1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "15Y", "20Y", "30Y"};
				for (int i =6; i < 21; i++) {
					//for (int i =8; i < 21; i++) {//测试，6m,9m不加风险点差
					double rate1 = commonRateMap1.get(adjustTerm[i-6])==null?0.0:commonRateMap1.get(adjustTerm[i-6]);
					double rate2 = financialMap1.get(adjustTerm[i-6])==null?0.0:financialMap1.get(adjustTerm[i-6]);
					double key_rate_orgnl=keyRate[i];
					keyRate[i] += CommonFunctions.doublecut(rate1+rate2, 7);
					System.out.println(adjustTerm[i-6]+":"+key_rate_orgnl+","+rate1+","+rate2+"--->"+keyRate[i] );
				}

				//活期关键点：判断是否为月初，如果是，则计算月均的平均收益率曲线关键点
				//ls:由于前台强制认定日期为一日，此处不用再判断。## 修改月初判断条件：每月第一条发布的曲线为上月平均，不管是不是一号(防止一号为节假日时错过曲线发布)
				//if(FtpUtil.checkFisrtDay(cdlDate)){
				if(cdlDate.substring(6,8).equals("01")) {
					//活期点
					Map<String,Double> gyll = FtpUtil.getShiborRate(minDate,date);//待定，隔夜利率
					double cdl1 = FtpUtil.computeCdlByHP(24)*0.5;//12个月的沉淀率
					cdl1=0.7;//沉淀率汇和银行手动设定
					keyRate[0] = gyll.get("O/N")*(1-cdl1)+keyRate[8]*cdl1;
					//ls:前台进行曲线生成时需要显示沉淀率，在Map中加入相关沉淀率的值。
					curveMap.put("cdl", new double[]{cdl1});
					System.out.println("活期="+keyRate[0]+"="+ gyll.get("O/N")+"*"+(1-cdl1)+"+"+keyRate[8]+"*"+cdl1);
				}else {
					//活期点
					double gyll = FtpUtil.getShiborRate(cdlDate,1);//待定，隔夜利率
					double cdl1 = FtpUtil.computeCdlByHP(24)*0.5;//12个月的沉淀率
					cdl1=0.6;//沉淀率汇和银行手动设定
					keyRate[0] = gyll*(1-cdl1)+keyRate[8]*cdl1;
					//ls:前台进行曲线生成时需要显示沉淀率，在Map中加入相关沉淀率的值。
					curveMap.put("cdl", new double[]{cdl1});
					System.out.println("活期="+keyRate[0]+"="+gyll+"*"+(1-cdl1)+"+"+keyRate[8]+"*"+cdl1);
				}
				//现存贷款收益率曲线构建为：活期点由shibor活期点*（1-沉淀率）+存款款曲线1Y点*沉淀率，3个月以下由shibor曲线直接取得（有5个点），
				//6个月以上由 （本行平均普通债收益+平均金融债收益） 替换  原来的（国债收益率）
			}else {
				return curveMap;
			}
		} else if (curveType.equals("2")) {
			// 市场收益率曲线，与存贷款取数规则相同，从shibor和国债中
			// 22个基准点：1天（即隔夜）、7天、14天、21天、1个月、2个月、3个月、6个月、9个月、1年、2年、3年、4年、5年、6年、7年、8年、9年、10年、15年、20年、30年
			key = new double[22];
			key[0] = 1 / 30.0;
			key[1] = 7 / 30.0;
			key[2] = 14 / 30.0;
			key[3] = 21 / 30.0;
			key[4] = 1;
			key[5] = 2;
			key[6] = 3;
			key[7] = 6;
			key[8] = 9;
			key[9] = 12;
			key[10] = 24;
			key[11] = 36;
			key[12] = 48;
			key[13] = 60;
			key[14] = 72;
			key[15] = 84;
			key[16] = 96;
			key[17] = 108;
			key[18] = 120;
			key[19] = 180;
			key[20] = 240;
			key[21] = 360;

			keyRate = new double[22];
			curveMap = this.getTodayMarketKeyRate(date);//当天的收益率曲线关键点
			int nain_days = 364;

			//判断是否为闰年
			if(DateUtil.isLeapYear(Integer.valueOf(date.substring(0,4))))
				nain_days = 365;

			date = String.valueOf(CommonFunctions.pub_base_deadlineD(Long.valueOf(date),-1));

			if(curveMap.get("keyRate") != null) {
				keyRate = curveMap.get("keyRate");
				Map<String, Double> commonRateMap = FtpUtil.getCommonRate(String.valueOf(CommonFunctions.pub_base_deadlineD(Long.valueOf(date),-29)), date);
				if (commonRateMap == null || commonRateMap.size() == 0) {
					curveMap.put("commonAveError", new double[0]);
					return curveMap;
				}

				Map<String, Double> financialMap = FtpUtil.getYieldRate(String.valueOf(CommonFunctions.pub_base_deadlineD(Long.valueOf(date),-nain_days)),date);
				if (financialMap == null || financialMap.size() == 0) {
					curveMap.put("yieldAveError", new double[0]);
					return curveMap;
				}
				//对生成的基准曲线中长期进行信用风险加点
				String[] adjustTerm = {"6M", "9M", "1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "15Y", "20Y", "30Y"};
				for (int i = 7; i < 22; i++) {
					double rate1 = commonRateMap.get(adjustTerm[i-7])==null?0.0:commonRateMap.get(adjustTerm[i-7]);
					double rate2 = financialMap.get(adjustTerm[i-7])==null?0.0:financialMap.get(adjustTerm[i-7]);
					keyRate[i] += CommonFunctions.doublecut(rate1+rate2, 7);
				}
			}else {
				return curveMap;
			}
		}else if(curveType.equals("3")) {
			// 国债收益率曲线
			// 15个基准点： 6个月、9个月、1年、2年、3年、4年、5年、6年、7年、8年、9年、10年、15年、20年、30年
			key = new double[15];
			key[0] = 6;
			key[1] = 9;
			key[2] = 12;
			key[3] = 24;
			key[4] = 36;
			key[5] = 48;
			key[6] = 60;
			key[7] = 72;
			key[8] = 84;
			key[9] = 98;
			key[10] = 102;
			key[11] = 120;
			key[12] = 180;
			key[13] = 240;
			key[14] = 360;


			int nain_days = 364;

			//判断是否为闰年
			if(DateUtil.isLeapYear(Integer.valueOf(date.substring(0,4))))
				nain_days = 365;

			date = String.valueOf(CommonFunctions.pub_base_deadlineD(Long.valueOf(date),-1));

			keyRate = new double[15];
			curveMap = this.getFshzcKeyRate(date,String.valueOf(CommonFunctions.pub_base_deadlineD(Long.valueOf(date),-nain_days)));//获取月均的收益率曲线关键点

			if(curveMap.get("keyRate") == null) {
				return curveMap;
			}else {
				keyRate = curveMap.get("keyRate");
			}
		}else if (curveType.equals("4")) {
			// shibor收益率曲线，与存贷款取数规则相同，从shibor和国债中
			// 10个基准点：1天（即隔夜）、7天、14天、21天、1个月、3个月、3个月、6个月、9个月、1年
			key = new double[10];
			key[0] = 1 / 30.0;
			key[1] = 7 / 30.0;
			key[2] = 14 / 30.0;
			key[3] = 21 / 30.0;
			key[4] = 1;
			key[5] = 2;
			key[6] = 3;
			key[7] = 6;
			key[8] = 9;
			key[9] = 12;


			keyRate = new double[10];
			curveMap = this.getTodayMarketKeyRate(date);//当天的收益率曲线关键点
			String shiborDate=CommonFunctions.dateModifyD(date, 1);
			Map<String, Double> shiborRateMap = FtpUtil.getShiborRate(shiborDate);//获取曲线日期前一天的数据 ##修改为当天
			if (shiborRateMap == null) {
				curveMap.put("shiborError", new double[0]);
				return curveMap;
			}
			keyRate[0] = CommonFunctions.doublecut(shiborRateMap.get("O/N"), 7);// 隔夜
			keyRate[1] = CommonFunctions.doublecut(shiborRateMap.get("1W"), 7);// 1周
			keyRate[2] = CommonFunctions.doublecut(shiborRateMap.get("2W"), 7);// 2周
			keyRate[4] = CommonFunctions.doublecut(shiborRateMap.get("1M"), 7);// 1个月
			keyRate[3] = (keyRate[4]-keyRate[2])*(21-14)/(30-14)+keyRate[2];//3w使用2w\1m进行线性插值
			keyRate[6] = CommonFunctions.doublecut(shiborRateMap.get("3M"), 7);// 3个月
			keyRate[5] = (keyRate[6]-keyRate[4])*1/2+keyRate[4];//2M使用1M\3M进行线性插值
			keyRate[7] =CommonFunctions.doublecut(shiborRateMap.get("6M"), 7);// 6个月
			keyRate[8] =CommonFunctions.doublecut(shiborRateMap.get("9M"), 7);// 9个月
			keyRate[9] =CommonFunctions.doublecut(shiborRateMap.get("1Y"), 7);// 12个月
			int nain_days = 364;
			//判断是否为闰年
			if(DateUtil.isLeapYear(Integer.valueOf(date.substring(0,4))))
				nain_days = 365;

			date = String.valueOf(CommonFunctions.pub_base_deadlineD(Long.valueOf(date),-1));
		}

		curveMap.put("key", key);
		curveMap.put("keyRate", keyRate);
		curveMap.put("keyRateAve", keyRateAve);
		return curveMap;
	}

	/**
	 * 检查数据库中是否有该天，curveNo相同的数据，如果存在则删除
	 * 
	 * @param curveType
	 * @param date
	 * @param brNo
	 */
	public void checkSaveAddDel(String curveType, String date, String brNo, String tableName) {
		//先检查数据库中是否有当天，市场类型相同的数据，如果存在则删除后再添加
		String hsql1 = "delete from "+tableName+" where curveDate = '"+date+"' and curveNo like '"+curveType+"%' and brNo = '"+brNo+"'";
		daoFactory.delete(hsql1,null);
	}

	/**
	 * 生成市场收益率曲线
	 * @param date
	 * @param brNo
	 * @param curveType
	 * @param key
	 * @param a3
	 * @param b2
	 * @param c1
	 * @param d0
	 * @return
	 */
	public Map generateMarketCurve(String date, String brNo, String curveType, double[] key, double[] a3, double[] b2, 
			double[] c1, double[] d0) throws ParseException {
		//先将这一个周期内的多条曲线的数据存到list中，当判断所有曲线都可正常生成后，一次进行保存；如果某条数据有问题，则直接返回，所有的都不保存
		String type = curveType;
		if (type.length() == 1) type = "0"+type;
		
		Map resultMap = new HashMap();
		List<String> newDateList = new ArrayList<String>();
        List<double[]> keyList = new ArrayList<double[]>();
        List<double[]> a3List = new ArrayList<double[]>();
        List<double[]> b2List = new ArrayList<double[]>();
        List<double[]> c1List = new ArrayList<double[]>();
        List<double[]> d0List = new ArrayList<double[]>();
        
        //存放当天的收益率曲线
        newDateList.add(date);
        keyList.add(key);
        a3List.add(a3);
        b2List.add(b2);
        c1List.add(c1);
        d0List.add(d0);
		/*
		 * 对一个周期内，前（N-1）天的数据进行循环生成
		 */
		int N=10;//一个周期内的天数，一般一旬为10天
		if(date.endsWith("31")){
			N=11;
		}else if(date.endsWith("28")){
			N=8;
		}
		else if(date.endsWith("29")){
			N=9;
		}else{
			N=10;
		}
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date0 = new Date();
		try {
			date0 = dateFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//for(int i = 1; i < N; i++) {
        for(int i = 0; i < 1; i++) {//改成每天生成收益率曲线
			String newDate = DateUtil.fmtDateToStr(DateUtil.addDay(date0, -i), "yyyyMMdd");
			System.out.println("-----------开始生成"+newDate+"天的收益率曲线-----------");
			
			//获取收益率曲线------------------------
			Map<String, double[]> curveMap = this.calculateCurve(curveType, newDate);
	 		
			if(curveMap.get("shiborError") != null){
				resultMap.put("shiborError", "shiborError"+newDate);
		        return resultMap;
			}
	 		if(curveMap.get("billsError") != null){
				resultMap.put("billsError", "billsError_"+newDate);
		        return resultMap;
			}
	 		if(curveMap.get("yieldMap") != null){
				resultMap.put("yieldMap", "yieldMap_"+newDate);
		        return resultMap;
			}
	 		if(curveMap.get("ofRateSpreadMap") != null){
				resultMap.put("ofRateSpreadMap", "ofRateSpreadMap");
		        return resultMap;
			}
	 		
			double[] Key = curveMap.get("key");
	 	    double[] keyRate = curveMap.get("keyRate");
	    	SCYTCZlineF F = SCYTCZ_Compute.getSCYTCZline(key, keyRate);

	    	double[][] matics1 = F.A;
	 	    
	 	    double[] A3 = new double[matics1.length];
		    double[] B2 = new double[matics1.length];
		    double[] C1 = new double[matics1.length];
		    double[] D0 = new double[matics1.length];
	 	    for (int m = 0; m < matics1.length; m++) {
	 	    	A3[m] = matics1[m][0];
	 	        B2[m] = matics1[m][1];
	 	        C1[m] = matics1[m][2];
	 	        D0[m] = matics1[m][3];
	 	    }
	 	    
	 	    //存放当天的收益率曲线
	        newDateList.add(newDate);
	        keyList.add(Key);
	        a3List.add(A3);
	        b2List.add(B2);
	        c1List.add(C1);
	        d0List.add(D0);
        }
        resultMap.put("newDateList", newDateList);
        resultMap.put("keyList", keyList);
        resultMap.put("a3List", a3List);
        resultMap.put("b2List", b2List);
        resultMap.put("c1List", c1List);
        resultMap.put("d0List", d0List);
        return resultMap;
	}
	/**
	 * 存贷款收益率曲线--发布
	 * @param curveType
	 * @param date
	 * @param brNo
	 * @param key
	 * @param a3
	 * @param b2
	 * @param c1
	 * @param d0
	 * @param a3_1
	 * @param b2_1
	 * @param c1_1
	 * @param d0_1
	 * @param a3_2
	 * @param b2_2
	 * @param c1_2
	 * @param d0_2
	 */
	public void internalCurveSave(String curveType, String date, String brNo,
								  double[] key, double[] a3, double[] b2, double[] c1, double[] d0, double[] a3_1, double[] b2_1,
								  double[] c1_1, double[] d0_1, double[] a3_2, double[] b2_2, double[] c1_2, double[] d0_2) {

		double[][] A=new double[a3.length][4];
		double[] x=new double[a3.length+1];

		// 对每一段的三次多项式进行循环添加,添加三条曲线：基准线00（基准线调整值为0.0）、存款01、贷款02
		//20140714修改，只有一条基准线00
		for (int i = 0; i < a3.length; i++) {
			// 基准线
			FtpYieldCurve ftpYieldCurve0 = new FtpYieldCurve();
			ftpYieldCurve0.setCurveNo(curveType + "00");
			//ftpYieldCurve0.setCurveName("存贷款收益率曲线-基准线");
			ftpYieldCurve0.setCurveName("存贷款收益率曲线");
			ftpYieldCurve0.setBrNo(brNo);
			ftpYieldCurve0.setSectionNum(i + 1);
			ftpYieldCurve0.setA3(a3[i]);
			ftpYieldCurve0.setB2(b2[i]);
			ftpYieldCurve0.setC1(c1[i]);
			ftpYieldCurve0.setD0(d0[i]);
			ftpYieldCurve0.setXMin(key[i]);
			ftpYieldCurve0.setXMax(key[i + 1]);
			ftpYieldCurve0.setCurveMarketType(curveType);
			ftpYieldCurve0.setCurveAssetsType("00");
			ftpYieldCurve0.setCurveDate(date);
			ftpYieldCurve0.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve0);
			// 存款
//			FtpYieldCurve ftpYieldCurve1 = new FtpYieldCurve();
//			ftpYieldCurve1.setCurveNo(curveType + "02");
//			ftpYieldCurve1.setCurveName("存贷款收益率曲线-存款线");
//			ftpYieldCurve1.setBrNo(brNo);
//			ftpYieldCurve1.setSectionNum(i + 1);
//			ftpYieldCurve1.setA3(a3_2[i]);
//			ftpYieldCurve1.setB2(b2_2[i]);
//			ftpYieldCurve1.setC1(c1_2[i]);
//			ftpYieldCurve1.setD0(d0_2[i]);
//			ftpYieldCurve1.setXMin(key[i]);
//			ftpYieldCurve1.setXMax(key[i + 1]);
//			ftpYieldCurve1.setCurveMarketType(curveType);
//			ftpYieldCurve1.setCurveAssetsType("02");
//			ftpYieldCurve1.setCurveDate(date);
//			ftpYieldCurve1.setCurveId(IDUtil.getInstanse().getUID());
//			daoFactory.insert(ftpYieldCurve1);
			// 贷款
//			FtpYieldCurve ftpYieldCurve2 = new FtpYieldCurve();
//			ftpYieldCurve2.setCurveNo(curveType + "01");
//			ftpYieldCurve2.setCurveName("存贷款收益率曲线-贷款线");
//			ftpYieldCurve2.setBrNo(brNo);
//			ftpYieldCurve2.setSectionNum(i + 1);
//			ftpYieldCurve2.setA3(a3_1[i]);
//			ftpYieldCurve2.setB2(b2_1[i]);
//			ftpYieldCurve2.setC1(c1_1[i]);
//			ftpYieldCurve2.setD0(d0_1[i]);
//			ftpYieldCurve2.setXMin(key[i]);
//			ftpYieldCurve2.setXMax(key[i + 1]);
//			ftpYieldCurve2.setCurveMarketType(curveType);
//			ftpYieldCurve2.setCurveAssetsType("01");
//			ftpYieldCurve2.setCurveDate(date);
//			ftpYieldCurve2.setCurveId(IDUtil.getInstanse().getUID());
//			daoFactory.insert(ftpYieldCurve2);

			A[i][0] = Double.valueOf(a3[i]);
			A[i][1] = Double.valueOf(b2[i]);
			A[i][2] = Double.valueOf(c1[i]);
			A[i][3] = Double.valueOf(d0[i]);
			x[i] = key[i];
			x[i+1] = key[i + 1];
		}
		//计算存贷准备金调整值，并保存
		SCYTCZlineF f=new SCYTCZlineF(A,key);
		String dsql = "delete from Ftp1PrdtCkzbjAdjust where adjustDate = '"+date+"'";
		daoFactory.delete(dsql, null);

		String hsql = "from  FtpPublicRate t where rateNo = '3' and adDate<='"+date+"' and adDate=(select max(adDate) from FtpPublicRate where rateNo = '3'  and adDate<='"+date+"' )" ;
		FtpPublicRate ftpPublicRate = (FtpPublicRate) daoFactory.getBean(hsql,
				null);
		Double ckzbjl = ftpPublicRate.getRate();

		String hsql2 = "from  FtpPublicRate t where rateNo = '4' and adDate<='"+date+"' and adDate=(select max(adDate) from FtpPublicRate where rateNo = '4'  and adDate<='"+date+"' )";
		FtpPublicRate ftpPublicRate2 = (FtpPublicRate) daoFactory.getBean(
				hsql2, null);
		Double ckzbjll = ftpPublicRate2.getRate();

		//期限从活期-61M，逐月增加
		for (int j = 0; j < 62; j++) {
			Ftp1PrdtCkzbjAdjust ftp1PrdtCkzbjAdjust = new Ftp1PrdtCkzbjAdjust();
			ftp1PrdtCkzbjAdjust.setAdjustId(IDUtil.getInstanse().getUID());
			ftp1PrdtCkzbjAdjust.setAdjustDate(date);
			ftp1PrdtCkzbjAdjust.setTermType(j);
			ftp1PrdtCkzbjAdjust.setAdjustValue((f.getY_SCYTCZline(j) - ckzbjll)*ckzbjl);
			daoFactory.insert(ftp1PrdtCkzbjAdjust);
		}
	}


	/**
	 * 存贷款收益率曲线--试发布-保存到FtpYieldCurveSfb中
	 * @param curveType
	 * @param date
	 * @param brNo
	 * @param key
	 * @param a3
	 * @param b2
	 * @param c1
	 * @param d0
	 */
	public void internalCurveSfb(String curveType, String date, String brNo, double[] key, double[] a3, double[] b2, double[] c1, double[] d0, double[] a3_1, double[] b2_1,
								 double[] c1_1, double[] d0_1, double[] a3_2, double[] b2_2, double[] c1_2, double[] d0_2) {
		//对每一段的三次多项式进行循环添加,添加三条曲线：基准线00（基准线调整值为0.0）、存款01、贷款02
		for (int i = 0; i < a3.length; i++) {
			//基准线
			FtpYieldCurveSfb ftpYieldCurve0 = new FtpYieldCurveSfb();
			ftpYieldCurve0.setCurveNo(curveType+"00");
			ftpYieldCurve0.setCurveName("存贷款收益率曲线-基准化曲线");
			ftpYieldCurve0.setBrNo(brNo);
			ftpYieldCurve0.setSectionNum(i + 1);
			ftpYieldCurve0.setA3(a3[i]);
			ftpYieldCurve0.setB2(b2[i]);
			ftpYieldCurve0.setC1(c1[i]);
			ftpYieldCurve0.setD0(d0[i]);
			ftpYieldCurve0.setXMin(key[i]);
			ftpYieldCurve0.setXMax(key[i + 1]);
			ftpYieldCurve0.setCurveMarketType(curveType);
			ftpYieldCurve0.setCurveAssetsType("00");
			ftpYieldCurve0.setCurveDate(date);
			ftpYieldCurve0.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve0);
			//存款
			FtpYieldCurveSfb ftpYieldCurve1 = new FtpYieldCurveSfb();
			ftpYieldCurve1.setCurveNo(curveType+"02");
			ftpYieldCurve1.setCurveName("存贷款收益率曲线-存款VOF");
			ftpYieldCurve1.setBrNo(brNo);
			ftpYieldCurve1.setSectionNum(i + 1);
			ftpYieldCurve1.setA3(a3_2[i]);
			ftpYieldCurve1.setB2(b2_2[i]);
			ftpYieldCurve1.setC1(c1_2[i]);
			ftpYieldCurve1.setD0(d0_2[i]);
			ftpYieldCurve1.setXMin(key[i]);
			ftpYieldCurve1.setXMax(key[i + 1]);
			ftpYieldCurve1.setCurveMarketType(curveType);
			ftpYieldCurve1.setCurveAssetsType("02");
			ftpYieldCurve1.setCurveDate(date);
			ftpYieldCurve1.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve1);
			//贷款
			FtpYieldCurveSfb ftpYieldCurve2 = new FtpYieldCurveSfb();
			ftpYieldCurve2.setCurveNo(curveType+"01");
			ftpYieldCurve2.setCurveName("存贷款收益率曲线-贷款COF");
			ftpYieldCurve2.setBrNo(brNo);
			ftpYieldCurve2.setSectionNum(i + 1);
			ftpYieldCurve2.setA3(a3_1[i]);
			ftpYieldCurve2.setB2(b2_1[i]);
			ftpYieldCurve2.setC1(c1_1[i]);
			ftpYieldCurve2.setD0(d0_1[i]);
			ftpYieldCurve2.setXMin(key[i]);
			ftpYieldCurve2.setXMax(key[i + 1]);
			ftpYieldCurve2.setCurveMarketType(curveType);
			ftpYieldCurve2.setCurveAssetsType("01");
			ftpYieldCurve2.setCurveDate(date);
			ftpYieldCurve2.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve2);
		}
	}

	/**
	 * 市场收益率曲线--发布
	 * @param curveType
	 * @param date
	 * @param brNo
	 * @param key
	 * @param a3
	 * @param b2
	 * @param c1
	 * @param d0
	 */
	public void marketCurveSave(String curveType,String assetsType, String date, String brNo,
								double[] key, double[] a3, double[] b2, double[] c1, double[] d0) {
		// 对每一段的三次多项式进行循环添加,添加一条曲线
		for (int i = 0; i < a3.length; i++) {
			// 基准线
			FtpYieldCurve ftpYieldCurve0 = new FtpYieldCurve();
			ftpYieldCurve0.setCurveNo(curveType + assetsType);
			ftpYieldCurve0.setCurveName("市场收益率曲线-"+(assetsType.equals("01")?"平均线":"基准线"));
			//ftpYieldCurve0.setCurveName("市场收益率曲线");
			ftpYieldCurve0.setBrNo(brNo);
			ftpYieldCurve0.setSectionNum(i + 1);
			ftpYieldCurve0.setA3(a3[i]);
			ftpYieldCurve0.setB2(b2[i]);
			ftpYieldCurve0.setC1(c1[i]);
			ftpYieldCurve0.setD0(d0[i]);
			ftpYieldCurve0.setXMin(key[i]);
			ftpYieldCurve0.setXMax(key[i + 1]);
			ftpYieldCurve0.setCurveMarketType(curveType);
			ftpYieldCurve0.setCurveAssetsType(assetsType);
			ftpYieldCurve0.setCurveDate(date);
			ftpYieldCurve0.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve0);
		}
	}

	/**
	 * 市场收益率曲线--试发布-保存到FtpYieldCurveSfb
	 * @param curveType
	 * @param date
	 * @param brNo
	 * @param key
	 * @param a3
	 * @param b2
	 * @param c1
	 * @param d0
	 */
	public void marketCurveSfb(String curveType, String date, String brNo, double[] key, double[] a3, double[] b2, double[] c1, double[] d0) {
		//对每一段的三次多项式进行循环添加,添加一条曲线
		for (int i = 0; i < a3.length; i++) {
			FtpYieldCurveSfb ftpYieldCurve0 = new FtpYieldCurveSfb();
			ftpYieldCurve0.setCurveNo(curveType+"00");
			ftpYieldCurve0.setCurveName("市场收益率曲线");
			ftpYieldCurve0.setBrNo(brNo);
			ftpYieldCurve0.setSectionNum(i + 1);
			ftpYieldCurve0.setA3(a3[i]);
			ftpYieldCurve0.setB2(b2[i]);
			ftpYieldCurve0.setC1(c1[i]);
			ftpYieldCurve0.setD0(d0[i]);
			ftpYieldCurve0.setXMin(key[i]);
			ftpYieldCurve0.setXMax(key[i + 1]);
			ftpYieldCurve0.setCurveMarketType(curveType);
			ftpYieldCurve0.setCurveAssetsType("00");
			ftpYieldCurve0.setCurveDate(date);
			ftpYieldCurve0.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve0);
		}
	}
	/**
	 * 导出收益率曲线
	 * 
	 * @param curveTerm
	 * @param curveValue
	 * @param curveType
	 * @param date
	 * @param brNo
	 * @return
	 */
	public HSSFWorkbook getWorkbook(String[] curveTerm, double[][] curveValue,
			String curveType, String date, String brNo) {

		HSSFWorkbook workbook = new HSSFWorkbook();
		String[] sheetName = new String[3];
		String[] sheetName1 = { "基准线"};
		String[] sheetName2 = { "基准线", "贷款线", "存款线" };
		if(curveType.equals("1")) {//存贷款
			sheetName = sheetName2;
		}else {
			sheetName = sheetName1;
		}

		for (int i = 0; i < (curveType.equals("1")?sheetName2.length:sheetName1.length); i++) {
			HSSFSheet sheet = workbook.createSheet(sheetName[i]);

			// 生成一个样式
			HSSFCellStyle style = workbook.createCellStyle();
			// 设置这些样式
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// 生成一个字体
			HSSFFont font = workbook.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// 把字体应用到当前的样式
			style.setFont(font);

			try {
				// 表头
				sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 7));
				HSSFRow row = sheet.createRow(0); // 创建第一行，也就是输出表头
				row.setHeight((short) 400);
				HSSFCell cell = row.createCell(0);
				cell.setCellStyle(style);
				cell.setCellValue(date + "日机构" + brNo + "的"
						+ (curveType.equals("1") ? "存贷款" : "市场") + "收益率曲线-"
						+ sheetName[i]);
				HSSFRow row2 = sheet.createRow(1); // 创建第二行
				HSSFCell cell1 = row2.createCell(0);
				cell1.setCellValue("期限");
				HSSFCell cell2 = row2.createCell(1);
				cell2.setCellValue("利率值(%)");
				// 输出各行的数据
				for (int j = 0; j < curveTerm.length; j++) {
					HSSFRow rowData = sheet.createRow(j + 2);
					HSSFCell cellData1 = rowData.createCell(0);
					cellData1
							.setCellValue(new HSSFRichTextString(curveTerm[j]));
					HSSFCell cellData2 = rowData.createCell(1);
					cellData2.setCellValue(new HSSFRichTextString(String
							.valueOf(curveValue[j][i])));
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
		return workbook;
	}

	/**
	 * 导出资金池结果
	 * 
	 * @param poleList
	 * @param ftpResultValue
	 * @param brNo
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public HSSFWorkbook getzjcWorkbook(List<FtpPoolInfo> poleList,
			List<Double[]> ftpResultValue, String brNo,String date) {

		HSSFWorkbook workbook = new HSSFWorkbook();

		String sheetName = "多资金池定价结果";

		HSSFSheet sheet = workbook.createSheet(sheetName);
		// 生成一个样式
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFCellStyle style1 = workbook.createCellStyle();
		HSSFCellStyle style2 = workbook.createCellStyle();
		HSSFCellStyle style3 = workbook.createCellStyle();
		// 设置这些样式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style3.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		// 生成一个字体
		HSSFFont font = workbook.createFont();
		HSSFFont font1 = workbook.createFont();
		font1.setFontHeightInPoints((short) 11);
		font1.setFontName("宋体");
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// 把字体应用到当前的样式
		style.setFont(font);
		style1.setFont(font1);
		style2.setFont(font1);
		style3.setFont(font1);

		try {
			// 表头
			sheet.setColumnWidth(0, 3766);
			sheet.setColumnWidth(1, 10000);
			sheet.setColumnWidth(2, 6000);
			sheet.setColumnWidth(3, 6000);
			sheet.setColumnWidth(4, 5000);
		

			Region region = new Region(0, (short) 0, 0, (short) 7);
			sheet.addMergedRegion(region);
			HSSFRow row = sheet.createRow(0); // 创建第一行，也就是输出表头
			row.setHeight((short) 400);
			HSSFCell cell = row.createCell(0);
			cell.setCellStyle(style);
			cell.setCellValue(date+"@"+LrmUtil.getBrName(brNo) + sheetName);
			HSSFRow row2 = sheet.createRow(1); // 创建第二行
			HSSFCell cell1 = row2.createCell(0);
			cell1.setCellValue("编号");
			cell1.setCellStyle(style2);
			
			HSSFCell cell2 = row2.createCell(1);
			cell2.setCellValue("资金池名称");
			cell2.setCellStyle(style2);
			
			HSSFCell cell3 = row2.createCell(2);
			cell3.setCellValue("资产类型");
			cell3.setCellStyle(style2);
			
			HSSFCell cell4 = row2.createCell(3);
			cell4.setCellValue("经营策略调整(%)");
			cell4.setCellStyle(style2);
			
			HSSFCell cell5 = row2.createCell(4);
			cell5.setCellValue("转移价格(%)");
			cell5.setCellStyle(style2);
			// 输出各行的数据
			for (int j = 0; j < ftpResultValue.size(); j++) {
				HSSFRow rowData = sheet.createRow(j + 2);
				HSSFCell cellData1 = rowData.createCell(0);
				cellData1.setCellStyle(style1);
				cellData1.setCellValue(poleList.get(j).getPoolNo());
				cellData1.setCellStyle(style1);
				HSSFCell cellData2 = rowData.createCell(1);
				cellData2.setCellValue(poleList.get(j).getPoolName());
				cellData2.setCellStyle(style1);
				HSSFCell cellData3 = rowData.createCell(2);
				cellData3.setCellValue(poleList.get(j).getPoolType());
				cellData3.setCellStyle(style1);
				HSSFCell cellData4 = rowData.createCell(3);
				cellData4.setCellValue(CommonFunctions.doublecut(ftpResultValue.get(j)[0],4));
				cellData4.setCellStyle(style1);
				HSSFCell cellData5 = rowData.createCell(4);
				cellData5.setCellValue(CommonFunctions.doublecut(ftpResultValue.get(j)[1],4));
				cellData5.setCellStyle(style3);
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return workbook;
	}

	public HSSFWorkbook getHistoryWorkbook(List<String> list_curveName,
			List<SCYTCZlineF> list_F) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		//生成一个样式
	    HSSFCellStyle style=workbook.createCellStyle();
	    //设置这些样式
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //生成一个字体
        HSSFFont font=workbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //把字体应用到当前的样式
        style.setFont(font);
		for(int i = 0; i < list_curveName.size(); i++) {
			String[] curveInfos = list_curveName.get(i).split("-");
			String listInfo = list_curveName.get(i);
			System.out.println("list_curveName.get(i)"+list_curveName.get(i));
			HSSFSheet sheet = workbook.createSheet(listInfo.replace(curveInfos[1], brInfoDAO.getBrNo(curveInfos[1])));
			 
			try {
				// 表头
				sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 9));//合并列
				HSSFRow row = sheet.createRow(0); // 创建第一行，也就是输出表头
				row.setHeight((short)400);
				HSSFCell cell = row.createCell(0);
				cell.setCellStyle(style);
				cell.setCellValue(listInfo);
				HSSFRow row2 = sheet.createRow(1); // 创建第二行
				HSSFCell cell1 = row2.createCell(0);
				cell1.setCellValue("期限");
				HSSFCell cell2 = row2.createCell(1);
				cell2.setCellValue("利率值(%)");
				
				SCYTCZlineF f = list_F.get(i);
				
				int o = 0;
				//存贷款的基准线和存款线要输出活期
				if(listInfo.indexOf("存贷款-基准线")!=-1||listInfo.indexOf("存贷款-存款")!=-1) {
					o++;
					HSSFRow rowData = sheet.createRow(2);
					HSSFCell cellData1 = rowData.createCell(0);
					cellData1.setCellValue(new HSSFRichTextString("活期"));
					HSSFCell cellData2 = rowData.createCell(1);
					cellData2.setCellValue(new HSSFRichTextString(String.valueOf(CommonFunctions.doublecut(f.getY_SCYTCZline(0)*100, 4))));
				}
				
				// 输出各行的数据
				String year = list_curveName.get(i).substring(0, 4);
				Integer dayNum = DateUtil.isLeapYear(Integer.valueOf(year)) == true ? 366 : 365;// 所在年总的日期数
				
				for (int j = 0; j < dayNum; j++) {
					HSSFRow rowData = sheet.createRow(j+2+o);
					HSSFCell cellData1 = rowData.createCell(0);
					cellData1.setCellValue(new HSSFRichTextString(j == dayNum - 1 ? "1年" : (j + 1) + "天"));
					HSSFCell cellData2 = rowData.createCell(1);
					cellData2.setCellValue(new HSSFRichTextString(String.valueOf(CommonFunctions.doublecut(f.getY_SCYTCZline((j + 1) / 30.0)*100, 4))));
				}
				int m = 13;
				for (int j = dayNum; j < dayNum + 348; j++) {
					HSSFRow rowData = sheet.createRow(j+2+o);
					HSSFCell cellData1 = rowData.createCell(0);
					cellData1.setCellValue(new HSSFRichTextString(m % 12 == 0 ? m / 12 + "年" : m + "月"));
					HSSFCell cellData2 = rowData.createCell(1);
					cellData2.setCellValue(new HSSFRichTextString(String.valueOf(CommonFunctions.doublecut(f.getY_SCYTCZline(m)*100, 4))));
					m++;
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
       
		return workbook;
		}

	public String formartPageLine(int pageSize, int currentPage, int rowsCount,
			String pageName) {

		pageSize = pageSize < 1 ? 12 : pageSize;
		currentPage = currentPage < 1 ? 1 : currentPage;
		StringBuffer buffer = new StringBuffer();
		int pageCount = rowsCount / pageSize;
		pageCount = pageCount < 1 ? 1 : pageCount;
		pageCount = pageCount * pageSize < rowsCount ? pageCount + 1
				: pageCount;
		currentPage = currentPage > pageCount ? pageCount : currentPage;

		if (currentPage == 1) {
			buffer.append("首页&nbsp;");
			buffer.append("上一页&nbsp;");
		} else {
			buffer.append("<a href=\"" + pageName + "&page=1&rowsCount="
					+ rowsCount + "\">首页</a>&nbsp;");
			buffer.append("<a href=\"" + pageName + "&page="
					+ (currentPage - 1) + "&rowsCount=" + rowsCount
					+ "\">上一页</a>&nbsp;");
		}

		if (currentPage == pageCount) {
			buffer.append("下一页&nbsp;");
			buffer.append("末页");
		} else {
			buffer.append("<a href=\"" + pageName + "&page="
					+ (currentPage + 1) + "&rowsCount=" + rowsCount
					+ "\">下一页</a>&nbsp;");
			buffer.append("<a href=\"" + pageName + "&page=" + pageCount
					+ "&rowsCount=" + rowsCount + "\">末页</a>&nbsp;");
		}
		buffer.append("&nbsp;&nbsp;共检索出" + rowsCount + "条数据，每页" + pageSize
				+ "条数据，页次<font color='red'>" + currentPage + "</font>/"
				+ pageCount);

		// buffer.setLength(0);
		buffer.append("&nbsp;&nbsp;跳转到：");
		buffer.append("\r\n<select onchange=\"window.location.replace('"
				+ pageName + "&page='+this.value+'&rowsCount=" + rowsCount
				+ "')\">\r\n");
		for (int i = 0; i < pageCount; i++) {
			String selected = "";
			if (i == currentPage - 1) {
				selected = "selected";
			}
			buffer.append("<option " + selected + " value=\"" + (i + 1)
					+ "\">第" + (i + 1) + "页</option>\r\n");
		}
		buffer.append("</select>");

		return buffer.toString();
	}

	/**
	 * 获取存贷款收益率曲线的存款收益率曲线关键点对应的利率值
	 * 取央行基准利率+上浮比例
	 * @param dgckZb
	 * @return
	 */
    public double[] getVCYieldVKeyRate(double dgckZb) {
    	double[] keyRateV = new double[15];
    	String hsql = "from FtpPublicRate";// 获取市场公共利率
    	List<FtpPublicRate> ftpPublicRateList = daoFactory.query(hsql, null);
    	// 用线性插值计算个关键点的利率值
    	keyRateV[0] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "2M0", dgckZb), 7);
    	keyRateV[1] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "2M3", dgckZb), 7);
    	keyRateV[4] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "2M6", dgckZb), 7);
    	keyRateV[2] = (keyRateV[4]-keyRateV[1])/3+keyRateV[1];//4M使用3M\6M进行线性插值
    	keyRateV[3] = (keyRateV[4]-keyRateV[1])*2/3+keyRateV[1];//5M使用3M\6M进行线性插值
    	keyRateV[5] = CommonFunctions.doublecut((FtpUtil.getPublicRate(ftpPublicRateList, "2M6", dgckZb)+FtpUtil.getPublicRate(ftpPublicRateList, "2Y1", dgckZb))/2, 7);
    	keyRateV[6] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "2Y1", dgckZb), 7);
    	keyRateV[7] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "2Y2", dgckZb), 7);
    	keyRateV[8] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "2Y3", dgckZb), 7);
    	keyRateV[9] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "2Y5", dgckZb), 7);
    	keyRateV[10] = keyRateV[9];
    	keyRateV[11] = keyRateV[9];
    	keyRateV[12] = keyRateV[9];
    	keyRateV[13] = keyRateV[9];
    	keyRateV[14] = keyRateV[9];
    	return keyRateV;
    }
    
    /**
	 * 获取存贷款收益率曲线的贷款收益率曲线关键点对应的利率值
	 * 取央行基准利率+上浮比例
	 * @return
	 */
   /* public double[] getVCYieldCKeyRate_yh() {
    	double[] keyRateV = new double[15];
    	String hsql = "from FtpPublicRate";// 获取市场公共利率
    	List<FtpPublicRate> ftpPublicRateList = daoFactory.query(hsql, null);
    	// 用线性插值计算个关键点的利率值
    	keyRateV[4] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "1M1"), 7);//6个月
    	keyRateV[1] = keyRateV[4];//3M=6M
    	keyRateV[2] = keyRateV[4];//4M=6M
    	keyRateV[3] = keyRateV[4];//5M=6M
    	keyRateV[0] = keyRateV[1];//活=6M
    	keyRateV[6] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "1M2"), 7);//1年
    	keyRateV[5] = (keyRateV[6]-keyRateV[4])/2+keyRateV[4];//9M使用6M\12Y进行线性插值
    	keyRateV[8] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "1Y1"), 7);//3年
    	keyRateV[7] = (keyRateV[8]-keyRateV[6])/2+keyRateV[6];//2Y使用1Y\3Y进行线性插值
    	keyRateV[9] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "1Y2"), 7);//5年
    	keyRateV[10] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "1Y3"), 7);//6年

    	keyRateV[11] = keyRateV[10];//7年
    	keyRateV[12] = keyRateV[10];//8年
    	keyRateV[13] = keyRateV[10];//9年
    	keyRateV[14] = keyRateV[10];//10年
    	
    	return keyRateV;
    }*/
    
    /**
	 * 获取存贷款收益率曲线的贷款收益率曲线关键点对应的利率值
	 * 对不同期限范围内的贷款利率进行加权平均，取以往12个月内新增的贷款<往前数365天>
	 * @return
	 */
    public double[] getVCYieldCKeyRate(String date) {
    	double[] keyRateC = new double[15];
    	String startDate = CommonFunctions.dateModifyD(date, -365);//往前数365天
    	String endDdate = CommonFunctions.dateModifyD(date, -1);//昨天
    	String sql = "select (days(to_date(END_DATE,'yyyymmdd'))-days(to_date(LOAN_DATE,'yyyymmdd'))) term,INTEREST_RATE*1.2/100,nvl(LOAN_MONEY,0.0)" +
    			  " from bips.CMS_MIR_LISTLOANBALANCE" +
    			  " where STATE5_LOAN in('01','02')" +//五级分类状态为正常和关注
    					" and LOAN_ITEM != '1289'" +//排除:贴现
    					" and length(END_DATE)=10" +//排除:呆账
    					" and to_date(LOAN_DATE,'yyyymmdd')>=to_date('"+startDate+"','yyyymmdd')" +
    			        " and to_date(LOAN_DATE,'yyyymmdd')<=to_date('"+endDdate+"','yyyymmdd')";
    	List list = daoFactory.query1(sql, null);

    	double[][] amt = new double[8][2];//每行对应各个关键点的分子amt和分母amt
    	//每个关键点的期限范围，单位是天
    	double[][] term = {{70,110},{160,200},{250,290},{345,385},{710,750},{1075,1115},{1440,2170},{2940,999999}};
    	for(int i = 0; i < list.size(); i++) {
    		Object[] obj = (Object[])list.get(i);
    		for (int j = 0; j < term.length; j++) {
				if(Integer.valueOf(String.valueOf(obj[0])) >= term[j][0] && Integer.valueOf(String.valueOf(obj[0])) <= term[j][1]) {
					amt[j][0] += Double.valueOf(String.valueOf(obj[1])) * Double.valueOf(String.valueOf(obj[2]));
					amt[j][1] += Double.valueOf(String.valueOf(obj[2]));
	    			break;
				}
			}
    	}
    	double keyRate[] = new double[8];//先对从数据库获取的这几个关键点的值进行处理，对于值为NaN的药进行插值
    	double key[] = {3,6,9,12,24,36,60,120};//keyRate对应的x坐标
    	keyRate[0] = amt[0][0]/amt[0][1];
    	keyRate[1] = amt[1][0]/amt[1][1];
    	keyRate[2] = amt[2][0]/amt[2][1];
    	keyRate[3] = amt[3][0]/amt[3][1];
    	keyRate[4] = amt[4][0]/amt[4][1];
    	keyRate[5] = amt[5][0]/amt[5][1];
    	keyRate[6] = amt[6][0]/amt[6][1];
    	keyRate[7] = amt[7][0]/amt[7][1];
    	keyRate = this.handleKeyRate(key, keyRate);//处理其中为NAN的值

    	keyRateC[0] = keyRate[0];//相等于活期那个点，设置成跟3M相同
    	keyRateC[1] = keyRate[0];//3M
    	keyRateC[2] = (keyRate[1]-keyRate[0])*1/3+keyRate[0];//4M使用3M\6M进行线性插值
    	keyRateC[3] = (keyRate[1]-keyRate[0])*2/3+keyRate[0];//5M使用3M\6M进行线性插值
    	keyRateC[4] = keyRate[1];//6M
    	keyRateC[5] = keyRate[2];//9M
    	keyRateC[6] = keyRate[3];//1Y
    	keyRateC[7] = keyRate[4];//2Y
    	keyRateC[8] = keyRate[5];//3Y
    	keyRateC[9] = keyRate[6];//5Y
    	keyRateC[10] = (keyRate[7]-keyRate[6])*1/5+keyRate[6];//6Y进行线性插值
    	keyRateC[11] = (keyRate[7]-keyRate[6])*2/5+keyRate[6];//7Y进行线性插值
    	keyRateC[12] = (keyRate[7]-keyRate[6])*3/5+keyRate[6];//8Y进行线性插值
    	keyRateC[13] = (keyRate[7]-keyRate[6])*4/5+keyRate[6];//9Y进行线性插值
    	keyRateC[14] = keyRate[7];//10Y
    	
    	return keyRateC;
    }
    /**
     * 获取存贷款收益率曲线的收益率曲线关键点对应的利率值
	 * 根据存贷款收益率曲线和存贷款利差分割比例设置进行计算
     * @param keyRateV 贷款
     * @param keyRateC 存款
     * @return
     */
    public double[] getVCYieldKeyRate(double[] keyRateV, double[] keyRateC, String date, double dgckZb) {
    	double[] keyRate = new double[15];
    	String hsql = "from FtpInterestMarginDivide";//获取 存贷款利差分割比例设置
    	//与关键点的期限一一对应
    	List<FtpInterestMarginDivide> ftpInterestMarginDivideList = daoFactory.query(hsql, null);
    	String[] key = {"3M", "4M", "5M", "6M", "9M", "1Y", "2Y", "3Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y"};
    	//"3M", "6M", "9M", "1Y", "2Y", "3Y", "5Y", "10Y"
    	Map<String, Double> keyMap = new HashMap<String, Double>();
    	for(FtpInterestMarginDivide ftpInterestMarginDivide : ftpInterestMarginDivideList) {
    		if(ftpInterestMarginDivide.getAssetsType().equals("02")){
    			keyMap.put(ftpInterestMarginDivide.getTermType(), ftpInterestMarginDivide.getRate());
    	    }
    	}
    	//线性差值4M\5M\6Y\7Y\8Y\9Y的点
    	keyMap.put("4M", (keyMap.get("6M")-keyMap.get("3M"))*1/3+keyMap.get("3M"));
    	keyMap.put("5M", (keyMap.get("6M")-keyMap.get("3M"))*2/3+keyMap.get("3M"));
    	keyMap.put("6Y", (keyMap.get("10Y")-keyMap.get("5Y"))*1/5+keyMap.get("5Y"));
    	keyMap.put("7Y", (keyMap.get("10Y")-keyMap.get("5Y"))*2/5+keyMap.get("5Y"));
    	keyMap.put("8Y", (keyMap.get("10Y")-keyMap.get("5Y"))*3/5+keyMap.get("5Y"));
    	keyMap.put("9Y", (keyMap.get("10Y")-keyMap.get("5Y"))*4/5+keyMap.get("5Y"));
    	for(int i = 0; i < key.length; i++) {
    		//关键点利率=存款关键点利率+利差*存款所占比例
    		keyRate[i+1] = keyRateV[i+1] + (keyRateC[i+1] - keyRateV[i+1]) * keyMap.get(key[i]);
    	}
    	
    	
    	//double cdl = 0.6;//待定，沉淀率
    	//double gyll = FtpUtil.getShiborRate(date, 30);//待定，隔夜利率
    	//System.out.println("隔夜利率:"+gyll);
    	//System.out.println("keyRate1Y:"+keyRate[6]);
    	//keyRate[0] = cdl*keyRate[6]+(1-cdl)*gyll;//活期
    	double rate = 0.033;//活期点的利率。。。。。计算方法一年期央行存款利率*(1+上浮比率)
    	String hsql2 = "from FtpPublicRate";//获取存款利率
    	List<FtpPublicRate> ftpPublicRateList = (List<FtpPublicRate>)daoFactory.query(hsql2, null);
    	if(ftpPublicRateList != null&&ftpPublicRateList.size()>0) {
    		//1年期存款
    		rate = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "2Y1", dgckZb), 7);
    	}

    	keyRate[0] = CommonFunctions.doublecut(rate, 7);//活期点
    	
    	return keyRate;
    }
    /**
     * 对关键点的值进行处理，如果某个点的值为NAN，则根据两侧的点进行插值
     * @return
     */
    public double[] handleKeyRate(double[] key, double[] keyRate) {
    	if(Double.isNaN(keyRate[0])) {//第一个点特殊处理，如果=NAN，则向右找不为NAN的第一个值，直接相等
    		for (int i = 1; i < keyRate.length; i++) {
				if(!Double.isNaN(keyRate[i])) {
					keyRate[0] = keyRate[i];
					break;
				}
			}
    	}
    	//从第二个点循环判断是否为NAN
    	for (int i = 1; i < keyRate.length; i++) {
    		if(Double.isNaN(keyRate[i])) {
    			//向右找不为NAN的第一个值
    			int m = 0;
    			for (int j = i + 1; j < keyRate.length; j++) {
    				if(!Double.isNaN(keyRate[j])) {
    					//进行线性插值
    					keyRate[i] = (keyRate[j]-keyRate[i-1])*(key[i]-key[i-1])/(key[j]-key[i-1])+keyRate[i-1];
    					m++;
    					break;
    				}
    			}
    			if(m == 0) {//如果右侧的值都为NAN,则直接=左侧那个点的值
    				keyRate[i] = keyRate[i-1];
    			}
    		}
		}
    	return keyRate;	
    }
    
    /**
	 * 获取最新一天的对公存款余额占对私定期存款总余额的比例
	 * @return
	 */
	public Double getDgCkZb() {
		DaoFactory df = new DaoFactory();
		
        String sql = "select nvl(sum(bal),0),substr(CUST_NO,1,1) from ftp.fzh_history where " +
				"business_no='YW202' group by substr(CUST_NO,1,1)";
		
		List list = df.query1(sql, null);
		double dgCkBal = 0, dsCkBal = 0;
		for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[])list.get(i);
			if(String.valueOf(obj[1]).equals("1")){
				dsCkBal = Double.valueOf(String.valueOf(obj[1]));//对私存款余额
			}else if(String.valueOf(obj[1]).equals("2")){
				dgCkBal = Double.valueOf(String.valueOf(obj[1]));//对公存款余额
			}
		}
		double dgCkZb = dgCkBal/(dgCkBal+dsCkBal);//对公存款占比
		dgCkZb = (Double.isInfinite(dgCkZb)||Double.isNaN(dgCkZb))?0.5:dgCkZb;
		return dgCkZb;
	}


	/**
	 * 获取非生息资产的收益率曲线
	 * @param date
	 * @return
	 */
	public Map<String, double[]> getFshzcKeyRate(String date,String minDate) {
    /*	int assessScope = -1;//时间范围  -1 为月
    	date = CommonFunctions.dateModifyD(date, -1);//处理到上月末
		String minDate = FtpUtil.getMinDate(date, assessScope);//获取时段左端点
*/    	Map<String, double[]> curveMap = new HashMap<String, double[]>();
		double[] keyRate = new double[15];// 关键点利率
		// 6M-3Y，采用国债收益率
		Map<String, Double> stockRateMap = FtpUtil.getStockRate(minDate, date);//获取平均数据
		if (stockRateMap == null || stockRateMap.size() == 0) {
			curveMap.put("aveStockError", new double[0]);
			return curveMap;
		}
		keyRate[0] = CommonFunctions.doublecut(stockRateMap.get("6M"), 7);// 6个月
		keyRate[1] = CommonFunctions.doublecut(stockRateMap.get("9M"), 7);// 9个月
		keyRate[2] = CommonFunctions.doublecut(stockRateMap.get("1Y"), 7);// 1年期
		keyRate[3] = CommonFunctions.doublecut(stockRateMap.get("2Y"), 7);// 2年期
		keyRate[4] = CommonFunctions.doublecut(stockRateMap.get("3Y"), 7);// 3年期
		keyRate[5] = CommonFunctions.doublecut(stockRateMap.get("4Y"), 7);// 4年期
		keyRate[6] = CommonFunctions.doublecut(stockRateMap.get("5Y"), 7);// 5年期
		keyRate[7] = CommonFunctions.doublecut(stockRateMap.get("6Y"), 7);// 6年期
		keyRate[8] = CommonFunctions.doublecut(stockRateMap.get("7Y"), 7);// 7年期
		keyRate[9] = CommonFunctions.doublecut(stockRateMap.get("8Y"), 7);// 8年期
		keyRate[10] = CommonFunctions.doublecut(stockRateMap.get("9Y"), 7);// 9年期
		keyRate[11] = CommonFunctions.doublecut(stockRateMap.get("10Y"), 7);// 10年期
		keyRate[12] = CommonFunctions.doublecut(stockRateMap.get("15Y"), 7);// 15年期
		keyRate[13] = CommonFunctions.doublecut(stockRateMap.get("20Y"), 7);// 20年期
		keyRate[14] = CommonFunctions.doublecut(stockRateMap.get("30Y"), 7);// 30年期
		curveMap.put("keyRate", keyRate);
		return curveMap;
	}

	/**
	 * 获取曲线日期前一天的市场收益率曲线
	 * @param date
	 * @return
	 */
	public Map<String, double[]> getTodayMarketKeyRate(String date) throws ParseException {
		Map<String, double[]> curveMap = new HashMap<String, double[]>();
		double[] keyRate = new double[22];// 关键点利率
		// O/N-3M，采用shiborError利率
		// ##修改shibor获取当天利率而非昨天。 只修改市场利率利率曲线中的shibor利率的取值，其他的保持不变
		String shiborDate=CommonFunctions.dateModifyD(date, 1);
		Map<String, Double> shiborRateMap = FtpUtil.getShiborRate(shiborDate);//获取曲线日期前一天的数据 ##修改为当天
//			repoRateMap = FtpUtil.getRepoRate(date);//获取当天的数据
		if (shiborRateMap == null) {
			curveMap.put("shiborError", new double[0]);
			return curveMap;
		}
		keyRate[0] = CommonFunctions.doublecut(shiborRateMap.get("O/N"), 7);// 隔夜
		keyRate[1] = CommonFunctions.doublecut(shiborRateMap.get("1W"), 7);// 1周
		keyRate[2] = CommonFunctions.doublecut(shiborRateMap.get("2W"), 7);// 2周
		keyRate[4] = CommonFunctions.doublecut(shiborRateMap.get("1M"), 7);// 1个月
		keyRate[3] = (keyRate[4]-keyRate[2])*(21/30-14/30)/(30-14/30)+keyRate[2];//3w使用2w\1m进行线性插值
		System.out.println(21.0/30.0);
		keyRate[6] = CommonFunctions.doublecut(shiborRateMap.get("3M"), 7);// 3个月
		keyRate[5] = (keyRate[6]-keyRate[4])*1/2+keyRate[4];//2M使用1M\3M进行线性插值
		// 6M-30Y，采用国债收益率
		Map<String, Double> stockRateMap = FtpUtil.getStockRate(date);//获取当天的数据
		if (stockRateMap == null || stockRateMap.size() == 0) {
			curveMap.put("stockError", new double[0]);
			return curveMap;
		}
		keyRate[7] = CommonFunctions.doublecut(stockRateMap.get("6M"), 7);// 6个月
		keyRate[8] = CommonFunctions.doublecut(stockRateMap.get("9M"), 7);// 9个月
		keyRate[9] = CommonFunctions.doublecut(stockRateMap.get("1Y"), 7);// 1年期
		keyRate[10] = CommonFunctions.doublecut(stockRateMap.get("2Y"), 7);// 2年期
		keyRate[11] = CommonFunctions.doublecut(stockRateMap.get("3Y"), 7);// 3年期
		keyRate[12] = CommonFunctions.doublecut(stockRateMap.get("4Y"), 7);// 4年期
		keyRate[13] = CommonFunctions.doublecut(stockRateMap.get("5Y"), 7);// 5年期
		keyRate[14] = CommonFunctions.doublecut(stockRateMap.get("6Y"), 7);// 6年期
		keyRate[15] = CommonFunctions.doublecut(stockRateMap.get("7Y"), 7);// 7年期
		keyRate[16] = CommonFunctions.doublecut(stockRateMap.get("8Y"), 7);// 8年期
		keyRate[17] = CommonFunctions.doublecut(stockRateMap.get("9Y"), 7);// 9年期
		keyRate[18] = CommonFunctions.doublecut(stockRateMap.get("10Y"), 7);// 10年期
		keyRate[19] = CommonFunctions.doublecut(stockRateMap.get("15Y"), 7);// 15年期
		keyRate[20] = CommonFunctions.doublecut(stockRateMap.get("20Y"), 7);// 20年期
		keyRate[21] = CommonFunctions.doublecut(stockRateMap.get("30Y"), 7);// 30年期
		curveMap.put("keyRate", keyRate);
		return curveMap;
	}


	/**
	 * 国债收益率曲线--发布
	 * @param curveType
	 * @param date
	 * @param brNo
	 * @param key
	 * @param a3
	 * @param b2
	 * @param c1
	 * @param d0
	 */
	public void fsxzcCurveSave(String curveType, String date, String brNo,
							   double[] key, double[] a3, double[] b2, double[] c1, double[] d0) {

		for (int i = 0; i < a3.length; i++) {
			// 基准线
			FtpYieldCurve ftpYieldCurve0 = new FtpYieldCurve();
			ftpYieldCurve0.setCurveNo(curveType + "00");
			ftpYieldCurve0.setCurveName("国债收益率曲线");
			ftpYieldCurve0.setBrNo(brNo);
			ftpYieldCurve0.setSectionNum(i + 1);
			ftpYieldCurve0.setA3(a3[i]);
			ftpYieldCurve0.setB2(b2[i]);
			ftpYieldCurve0.setC1(c1[i]);
			ftpYieldCurve0.setD0(d0[i]);
			ftpYieldCurve0.setXMin(key[i]);
			ftpYieldCurve0.setXMax(key[i + 1]);
			ftpYieldCurve0.setCurveMarketType(curveType);
			ftpYieldCurve0.setCurveAssetsType("00");
			ftpYieldCurve0.setCurveDate(date);
			ftpYieldCurve0.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve0);

		}
	}

	/**
	 * 外币收益率曲线--发布
	 * @param curveType
	 * @param date
	 * @param brNo
	 * @param key
	 * @param a3
	 * @param b2
	 * @param c1
	 * @param d0
	 */
	public void wbCurveSave(String curveType, String date, String brNo,
							double[] key, double[] a3, double[] b2, double[] c1, double[] d0) {

		for (int i = 0; i < a3.length; i++) {
			// 基准线
			FtpYieldCurve ftpYieldCurve0 = new FtpYieldCurve();
			ftpYieldCurve0.setCurveNo(curveType + "00");
			ftpYieldCurve0.setCurveName("外币收益率曲线");
			ftpYieldCurve0.setBrNo(brNo);
			ftpYieldCurve0.setSectionNum(i + 1);
			ftpYieldCurve0.setA3(a3[i]);
			ftpYieldCurve0.setB2(b2[i]);
			ftpYieldCurve0.setC1(c1[i]);
			ftpYieldCurve0.setD0(d0[i]);
			ftpYieldCurve0.setXMin(key[i]);
			ftpYieldCurve0.setXMax(key[i + 1]);
			ftpYieldCurve0.setCurveMarketType(curveType);
			ftpYieldCurve0.setCurveAssetsType("00");
			ftpYieldCurve0.setCurveDate(date);
			ftpYieldCurve0.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve0);

		}
	}
	/**
	 * shibor收益率曲线--发布
	 * @param curveType
	 * @param date
	 * @param brNo
	 * @param key
	 * @param a3
	 * @param b2
	 * @param c1
	 * @param d0
	 */
	public void shiborCurveSave(String curveType, String date, String brNo,
								double[] key, double[] a3, double[] b2, double[] c1, double[] d0) {

		for (int i = 0; i < a3.length; i++) {
			// 基准线
			FtpYieldCurve ftpYieldCurve0 = new FtpYieldCurve();
			ftpYieldCurve0.setCurveNo(curveType + "00");
			ftpYieldCurve0.setCurveName("shibor收益率曲线");
			ftpYieldCurve0.setBrNo(brNo);
			ftpYieldCurve0.setSectionNum(i + 1);
			ftpYieldCurve0.setA3(a3[i]);
			ftpYieldCurve0.setB2(b2[i]);
			ftpYieldCurve0.setC1(c1[i]);
			ftpYieldCurve0.setD0(d0[i]);
			ftpYieldCurve0.setXMin(key[i]);
			ftpYieldCurve0.setXMax(key[i + 1]);
			ftpYieldCurve0.setCurveMarketType(curveType);
			ftpYieldCurve0.setCurveAssetsType("00");
			ftpYieldCurve0.setCurveDate(date);
			ftpYieldCurve0.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve0);
		}
	}
}
