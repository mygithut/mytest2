package com.dhcc.ftp.bo;

/**
 * @desc:����������ά��
 * @author :�����
 * @date ��2012-04-16
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

	//list��ҳ��ѯ
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
		}else if(Integer.valueOf(telMst.getBrMst().getManageLvl())<=2){//�������ʡ���磬Ҫ��ȡ��Ӧ�����������
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
	 * ��ȡƽ�����������������
	 * @param minDate
	 * @param maxDate
	 * @return
	 */
	public Map<String, double[]> getVCAveYieldKeyRate(String minDate, String maxDate) {
		double[] keyRate = new double[21];// �ؼ�������-���������������

		// ��������������� 21����׼�㣺����<0>��1����<1>��45��<1.5>��2����<2>��75�����<2.5>��3����<3>�� 6����<6>��9����<9>��1��<12>��2��<24>��3��<36>��4��<48>�� 5��<60>�� 6��<72>�� 7��<84>��8��<96>�� 9��<108>�� 10��<120>��15��<180>��20��<240>��30��<360>

		Map<String, double[]> curveMap = new HashMap<String, double[]>();
		// 1M��3M������shiborError����
		Map<String, Double> shiborRateMap = FtpUtil.getShiborRate(minDate, maxDate);//��ȡ�¾�������
		if (shiborRateMap == null) {
			curveMap.put("shiborAveError", new double[0]);
			return curveMap;
		}
		keyRate[1] =  CommonFunctions.doublecut(shiborRateMap.get("1M"), 7);
		keyRate[5] = CommonFunctions.doublecut(shiborRateMap.get("3M"), 7);// 3����

		keyRate[2] = (keyRate[5]-keyRate[1])*1/4+keyRate[1];//45Dʹ��1M\3M�������Բ�ֵ
		keyRate[3] = (keyRate[5]-keyRate[1])*1/2+keyRate[1];//2Mʹ��1M\3M�������Բ�ֵ
		keyRate[4] = (keyRate[5]-keyRate[1])*3/4+keyRate[1];//75Dʹ��1M\3M�������Բ�ֵ
		//��Ͳ��ԣ�6��9���²�����Ʊ������
		/*
			Map<String, Double> billRateMap = FtpUtil.getBillsRate(minDate, maxDate);//��ȡ�¾�������
			keyRate[6] = CommonFunctions.doublecut(billRateMap.get("6M"), 7);// 6����
			keyRate[7] = CommonFunctions.doublecut(billRateMap.get("9M"), 7);// 9����
		*/
		// 6M-30Y�����ù�ծ������
		Map<String, Double> stockRateMap = FtpUtil.getStockRate(minDate, maxDate);//��ȡ�¾�������
		if (stockRateMap == null || stockRateMap.size() == 0) {
			curveMap.put("stockAveError", new double[0]);
			return curveMap;
		}
		keyRate[6] = CommonFunctions.doublecut(stockRateMap.get("6M"), 7);// 6����
		keyRate[7] = CommonFunctions.doublecut(stockRateMap.get("9M"), 7);// 9����
		keyRate[8] = CommonFunctions.doublecut(stockRateMap.get("1Y"), 7);// 1����
		keyRate[9] = CommonFunctions.doublecut(stockRateMap.get("2Y"), 7);// 2����
		keyRate[10] = CommonFunctions.doublecut(stockRateMap.get("3Y"), 7);// 3����
		keyRate[11] = CommonFunctions.doublecut(stockRateMap.get("4Y"), 7);// 4����
		keyRate[12] = CommonFunctions.doublecut(stockRateMap.get("5Y"), 7);// 5����
		keyRate[13] = CommonFunctions.doublecut(stockRateMap.get("6Y"), 7);// 6����
		keyRate[14] = CommonFunctions.doublecut(stockRateMap.get("7Y"), 7);// 7����
		keyRate[15] = CommonFunctions.doublecut(stockRateMap.get("8Y"), 7);// 8����
		keyRate[16] = CommonFunctions.doublecut(stockRateMap.get("9Y"), 7);// 9����
		keyRate[17] = CommonFunctions.doublecut(stockRateMap.get("10Y"), 7);// 10����
		keyRate[18] = CommonFunctions.doublecut(stockRateMap.get("15Y"), 7);// 15����
		keyRate[19] = CommonFunctions.doublecut(stockRateMap.get("20Y"), 7);// 20����
		keyRate[20] = CommonFunctions.doublecut(stockRateMap.get("30Y"), 7);// 30����


		curveMap.put("keyRate", keyRate);
		return curveMap;
	}

	/**
	 * ��ȡ���������������
	 * @param maxDate
	 * @return
	 */
	public Map<String, double[]> getVCYieldKeyRate(String maxDate) throws ParseException {
		double[] keyRate = new double[21];// �ؼ�������-���������������

		// ��������������� 21����׼�㣺����<0>��1����<1>��45��<1.5>��2����<2>��75�����<2.5>��3����<3>�� 6����<6>��9����<9>��1��<12>��2��<24>��3��<36>��4��<48>�� 5��<60>�� 6��<72>�� 7��<84>��8��<96>�� 9��<108>�� 10��<120>��15��<180>��20��<240>��30��<360>

		Map<String, double[]> curveMap = new HashMap<String, double[]>();
		// 1M��3M������shiborError����
		Map<String, Double> shiborRateMap = FtpUtil.getShiborRate(maxDate);//��ȡǰһ�������
		if (shiborRateMap == null) {
			curveMap.put("shiborError", new double[0]);
			return curveMap;
		}

		keyRate[1] =  CommonFunctions.doublecut(shiborRateMap.get("1M"), 7);
		keyRate[5] = CommonFunctions.doublecut(shiborRateMap.get("3M"), 7);// 3����

		keyRate[2] = (keyRate[5]-keyRate[1])*1/4+keyRate[1];//45Dʹ��1M\3M�������Բ�ֵ
		keyRate[3] = (keyRate[5]-keyRate[1])*1/2+keyRate[1];//2Mʹ��1M\3M�������Բ�ֵ
		keyRate[4] = (keyRate[5]-keyRate[1])*3/4+keyRate[1];//75Dʹ��1M\3M�������Բ�ֵ
		// 6M-30Y�����ù�ծ������
		Map<String, Double> stockRateMap = FtpUtil.getStockRate(maxDate);//��ȡǰһ�������
		if (stockRateMap == null || stockRateMap.size() == 0) {
			curveMap.put("stockError", new double[0]);
			return curveMap;
		}
		keyRate[6] = CommonFunctions.doublecut(stockRateMap.get("6M"), 7);// 6����
		keyRate[7] = CommonFunctions.doublecut(stockRateMap.get("9M"), 7);// 9����
		keyRate[8] = CommonFunctions.doublecut(stockRateMap.get("1Y"), 7);// 1����
		keyRate[9] = CommonFunctions.doublecut(stockRateMap.get("2Y"), 7);// 2����
		keyRate[10] = CommonFunctions.doublecut(stockRateMap.get("3Y"), 7);// 3����
		keyRate[11] = CommonFunctions.doublecut(stockRateMap.get("4Y"), 7);// 4����
		keyRate[12] = CommonFunctions.doublecut(stockRateMap.get("5Y"), 7);// 5����
		keyRate[13] = CommonFunctions.doublecut(stockRateMap.get("6Y"), 7);// 6����
		keyRate[14] = CommonFunctions.doublecut(stockRateMap.get("7Y"), 7);// 7����
		keyRate[15] = CommonFunctions.doublecut(stockRateMap.get("8Y"), 7);// 8����
		keyRate[16] = CommonFunctions.doublecut(stockRateMap.get("9Y"), 7);// 9����
		keyRate[17] = CommonFunctions.doublecut(stockRateMap.get("10Y"), 7);// 10����
		keyRate[18] = CommonFunctions.doublecut(stockRateMap.get("15Y"), 7);// 15����
		keyRate[19] = CommonFunctions.doublecut(stockRateMap.get("20Y"), 7);// 20����
		keyRate[20] = CommonFunctions.doublecut(stockRateMap.get("30Y"), 7);// 30����


		curveMap.put("keyRate", keyRate);
		return curveMap;
	}
	/**
	 * ��������
	 * 
	 * @param curveType
	 * @param date
	 * @return
	 */
	public Map<String, double[]> calculateCurve(String curveType, String date) throws ParseException {
		Map<String, double[]> curveMap = new HashMap<String, double[]>();
		//���ڳ������³�����
		String cdlDate = date;
		double[] key = null;// ��׼�ؼ���
		//double[] keyRateV = null;// ������������߹ؼ�������
		//double[] keyRateC = null;// �������������߹ؼ�������
		double[] keyRate = null;// �ؼ�������
		double[] keyRateAve = null;// �г�ƽ�������ʹؼ�������
		/*
		 * 1.�����޷�������������
		 */
		if (curveType.equals("1")) {
			// ��������������� 21����׼�㣺����<0>��1����<1>��45��<1.5>��2����<2>��75�����<2.5>��3����<3>�� 6����<6>��9����<9>��1��<12>��2��<24>��3��<36>��4��<48>�� 5��<60>�� 6��<72>�� 7��<84>��8��<96>�� 9��<108>�� 10��<120>��15��<180>��20��<240>��30��<360>
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

			//�ж��Ƿ�Ϊ����
			if(DateUtil.isLeapYear(Integer.valueOf(date.substring(0,4))))
				 nain_days = 365;

			//�ж��Ƿ�Ϊ�³�������ǣ�������¾���ƽ�����������߹ؼ���
			//ls:����ǰ̨ǿ���϶�����Ϊһ�գ��˴��������жϡ�## �޸��³��ж�������ÿ�µ�һ������������Ϊ����ƽ���������ǲ���һ��(��ֹһ��Ϊ�ڼ���ʱ������߷���)
			if(date.substring(6,8).equals("01")) {
				//if(FtpUtil.checkFisrtDay(date)){
				date = CommonFunctions.dateModifyD(date, -1);//��������ĩ
				minDate = date.substring(0,6)+"01";//��ȡʱ����˵㣬�����³�
				curveMap = this.getVCAveYieldKeyRate(minDate, date);//�����¾������������߹ؼ���
			}else {
				curveMap = this.getVCYieldKeyRate(date);//����ǰһ����������������߹ؼ���
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
				//�����ɵĻ�׼���߽������÷��ռӵ㣬��ȡ���һ�����ڵı�����ͨծ�ͽ���ծ����ֵ
				String[] adjustTerm = {"6M", "9M", "1Y", "2Y", "3Y", "4Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y", "15Y", "20Y", "30Y"};
				for (int i =6; i < 21; i++) {
					//for (int i =8; i < 21; i++) {//���ԣ�6m,9m���ӷ��յ��
					double rate1 = commonRateMap1.get(adjustTerm[i-6])==null?0.0:commonRateMap1.get(adjustTerm[i-6]);
					double rate2 = financialMap1.get(adjustTerm[i-6])==null?0.0:financialMap1.get(adjustTerm[i-6]);
					double key_rate_orgnl=keyRate[i];
					keyRate[i] += CommonFunctions.doublecut(rate1+rate2, 7);
					System.out.println(adjustTerm[i-6]+":"+key_rate_orgnl+","+rate1+","+rate2+"--->"+keyRate[i] );
				}

				//���ڹؼ��㣺�ж��Ƿ�Ϊ�³�������ǣ�������¾���ƽ�����������߹ؼ���
				//ls:����ǰ̨ǿ���϶�����Ϊһ�գ��˴��������жϡ�## �޸��³��ж�������ÿ�µ�һ������������Ϊ����ƽ���������ǲ���һ��(��ֹһ��Ϊ�ڼ���ʱ������߷���)
				//if(FtpUtil.checkFisrtDay(cdlDate)){
				if(cdlDate.substring(6,8).equals("01")) {
					//���ڵ�
					Map<String,Double> gyll = FtpUtil.getShiborRate(minDate,date);//��������ҹ����
					double cdl1 = FtpUtil.computeCdlByHP(24)*0.5;//12���µĳ�����
					cdl1=0.7;//�����ʻ�������ֶ��趨
					keyRate[0] = gyll.get("O/N")*(1-cdl1)+keyRate[8]*cdl1;
					//ls:ǰ̨������������ʱ��Ҫ��ʾ�����ʣ���Map�м�����س����ʵ�ֵ��
					curveMap.put("cdl", new double[]{cdl1});
					System.out.println("����="+keyRate[0]+"="+ gyll.get("O/N")+"*"+(1-cdl1)+"+"+keyRate[8]+"*"+cdl1);
				}else {
					//���ڵ�
					double gyll = FtpUtil.getShiborRate(cdlDate,1);//��������ҹ����
					double cdl1 = FtpUtil.computeCdlByHP(24)*0.5;//12���µĳ�����
					cdl1=0.6;//�����ʻ�������ֶ��趨
					keyRate[0] = gyll*(1-cdl1)+keyRate[8]*cdl1;
					//ls:ǰ̨������������ʱ��Ҫ��ʾ�����ʣ���Map�м�����س����ʵ�ֵ��
					curveMap.put("cdl", new double[]{cdl1});
					System.out.println("����="+keyRate[0]+"="+gyll+"*"+(1-cdl1)+"+"+keyRate[8]+"*"+cdl1);
				}
				//�ִ�������������߹���Ϊ�����ڵ���shibor���ڵ�*��1-�����ʣ�+��������1Y��*�����ʣ�3����������shibor����ֱ��ȡ�ã���5���㣩��
				//6���������� ������ƽ����ͨծ����+ƽ������ծ���棩 �滻  ԭ���ģ���ծ�����ʣ�
			}else {
				return curveMap;
			}
		} else if (curveType.equals("2")) {
			// �г����������ߣ�������ȡ��������ͬ����shibor�͹�ծ��
			// 22����׼�㣺1�죨����ҹ����7�졢14�졢21�졢1���¡�2���¡�3���¡�6���¡�9���¡�1�ꡢ2�ꡢ3�ꡢ4�ꡢ5�ꡢ6�ꡢ7�ꡢ8�ꡢ9�ꡢ10�ꡢ15�ꡢ20�ꡢ30��
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
			curveMap = this.getTodayMarketKeyRate(date);//��������������߹ؼ���
			int nain_days = 364;

			//�ж��Ƿ�Ϊ����
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
				//�����ɵĻ�׼�����г��ڽ������÷��ռӵ�
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
			// ��ծ����������
			// 15����׼�㣺 6���¡�9���¡�1�ꡢ2�ꡢ3�ꡢ4�ꡢ5�ꡢ6�ꡢ7�ꡢ8�ꡢ9�ꡢ10�ꡢ15�ꡢ20�ꡢ30��
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

			//�ж��Ƿ�Ϊ����
			if(DateUtil.isLeapYear(Integer.valueOf(date.substring(0,4))))
				nain_days = 365;

			date = String.valueOf(CommonFunctions.pub_base_deadlineD(Long.valueOf(date),-1));

			keyRate = new double[15];
			curveMap = this.getFshzcKeyRate(date,String.valueOf(CommonFunctions.pub_base_deadlineD(Long.valueOf(date),-nain_days)));//��ȡ�¾������������߹ؼ���

			if(curveMap.get("keyRate") == null) {
				return curveMap;
			}else {
				keyRate = curveMap.get("keyRate");
			}
		}else if (curveType.equals("4")) {
			// shibor���������ߣ�������ȡ��������ͬ����shibor�͹�ծ��
			// 10����׼�㣺1�죨����ҹ����7�졢14�졢21�졢1���¡�3���¡�3���¡�6���¡�9���¡�1��
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
			curveMap = this.getTodayMarketKeyRate(date);//��������������߹ؼ���
			String shiborDate=CommonFunctions.dateModifyD(date, 1);
			Map<String, Double> shiborRateMap = FtpUtil.getShiborRate(shiborDate);//��ȡ��������ǰһ������� ##�޸�Ϊ����
			if (shiborRateMap == null) {
				curveMap.put("shiborError", new double[0]);
				return curveMap;
			}
			keyRate[0] = CommonFunctions.doublecut(shiborRateMap.get("O/N"), 7);// ��ҹ
			keyRate[1] = CommonFunctions.doublecut(shiborRateMap.get("1W"), 7);// 1��
			keyRate[2] = CommonFunctions.doublecut(shiborRateMap.get("2W"), 7);// 2��
			keyRate[4] = CommonFunctions.doublecut(shiborRateMap.get("1M"), 7);// 1����
			keyRate[3] = (keyRate[4]-keyRate[2])*(21-14)/(30-14)+keyRate[2];//3wʹ��2w\1m�������Բ�ֵ
			keyRate[6] = CommonFunctions.doublecut(shiborRateMap.get("3M"), 7);// 3����
			keyRate[5] = (keyRate[6]-keyRate[4])*1/2+keyRate[4];//2Mʹ��1M\3M�������Բ�ֵ
			keyRate[7] =CommonFunctions.doublecut(shiborRateMap.get("6M"), 7);// 6����
			keyRate[8] =CommonFunctions.doublecut(shiborRateMap.get("9M"), 7);// 9����
			keyRate[9] =CommonFunctions.doublecut(shiborRateMap.get("1Y"), 7);// 12����
			int nain_days = 364;
			//�ж��Ƿ�Ϊ����
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
	 * ������ݿ����Ƿ��и��죬curveNo��ͬ�����ݣ����������ɾ��
	 * 
	 * @param curveType
	 * @param date
	 * @param brNo
	 */
	public void checkSaveAddDel(String curveType, String date, String brNo, String tableName) {
		//�ȼ�����ݿ����Ƿ��е��죬�г�������ͬ�����ݣ����������ɾ���������
		String hsql1 = "delete from "+tableName+" where curveDate = '"+date+"' and curveNo like '"+curveType+"%' and brNo = '"+brNo+"'";
		daoFactory.delete(hsql1,null);
	}

	/**
	 * �����г�����������
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
		//�Ƚ���һ�������ڵĶ������ߵ����ݴ浽list�У����ж��������߶����������ɺ�һ�ν��б��棻���ĳ�����������⣬��ֱ�ӷ��أ����еĶ�������
		String type = curveType;
		if (type.length() == 1) type = "0"+type;
		
		Map resultMap = new HashMap();
		List<String> newDateList = new ArrayList<String>();
        List<double[]> keyList = new ArrayList<double[]>();
        List<double[]> a3List = new ArrayList<double[]>();
        List<double[]> b2List = new ArrayList<double[]>();
        List<double[]> c1List = new ArrayList<double[]>();
        List<double[]> d0List = new ArrayList<double[]>();
        
        //��ŵ��������������
        newDateList.add(date);
        keyList.add(key);
        a3List.add(a3);
        b2List.add(b2);
        c1List.add(c1);
        d0List.add(d0);
		/*
		 * ��һ�������ڣ�ǰ��N-1��������ݽ���ѭ������
		 */
		int N=10;//һ�������ڵ�������һ��һѮΪ10��
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
        for(int i = 0; i < 1; i++) {//�ĳ�ÿ����������������
			String newDate = DateUtil.fmtDateToStr(DateUtil.addDay(date0, -i), "yyyyMMdd");
			System.out.println("-----------��ʼ����"+newDate+"�������������-----------");
			
			//��ȡ����������------------------------
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
	 	    
	 	    //��ŵ��������������
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
	 * ���������������--����
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

		// ��ÿһ�ε����ζ���ʽ����ѭ�����,����������ߣ���׼��00����׼�ߵ���ֵΪ0.0�������01������02
		//20140714�޸ģ�ֻ��һ����׼��00
		for (int i = 0; i < a3.length; i++) {
			// ��׼��
			FtpYieldCurve ftpYieldCurve0 = new FtpYieldCurve();
			ftpYieldCurve0.setCurveNo(curveType + "00");
			//ftpYieldCurve0.setCurveName("���������������-��׼��");
			ftpYieldCurve0.setCurveName("���������������");
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
			// ���
//			FtpYieldCurve ftpYieldCurve1 = new FtpYieldCurve();
//			ftpYieldCurve1.setCurveNo(curveType + "02");
//			ftpYieldCurve1.setCurveName("���������������-�����");
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
			// ����
//			FtpYieldCurve ftpYieldCurve2 = new FtpYieldCurve();
//			ftpYieldCurve2.setCurveNo(curveType + "01");
//			ftpYieldCurve2.setCurveName("���������������-������");
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
		//������׼�������ֵ��������
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

		//���޴ӻ���-61M����������
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
	 * ���������������--�Է���-���浽FtpYieldCurveSfb��
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
		//��ÿһ�ε����ζ���ʽ����ѭ�����,����������ߣ���׼��00����׼�ߵ���ֵΪ0.0�������01������02
		for (int i = 0; i < a3.length; i++) {
			//��׼��
			FtpYieldCurveSfb ftpYieldCurve0 = new FtpYieldCurveSfb();
			ftpYieldCurve0.setCurveNo(curveType+"00");
			ftpYieldCurve0.setCurveName("���������������-��׼������");
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
			//���
			FtpYieldCurveSfb ftpYieldCurve1 = new FtpYieldCurveSfb();
			ftpYieldCurve1.setCurveNo(curveType+"02");
			ftpYieldCurve1.setCurveName("���������������-���VOF");
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
			//����
			FtpYieldCurveSfb ftpYieldCurve2 = new FtpYieldCurveSfb();
			ftpYieldCurve2.setCurveNo(curveType+"01");
			ftpYieldCurve2.setCurveName("���������������-����COF");
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
	 * �г�����������--����
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
		// ��ÿһ�ε����ζ���ʽ����ѭ�����,���һ������
		for (int i = 0; i < a3.length; i++) {
			// ��׼��
			FtpYieldCurve ftpYieldCurve0 = new FtpYieldCurve();
			ftpYieldCurve0.setCurveNo(curveType + assetsType);
			ftpYieldCurve0.setCurveName("�г�����������-"+(assetsType.equals("01")?"ƽ����":"��׼��"));
			//ftpYieldCurve0.setCurveName("�г�����������");
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
	 * �г�����������--�Է���-���浽FtpYieldCurveSfb
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
		//��ÿһ�ε����ζ���ʽ����ѭ�����,���һ������
		for (int i = 0; i < a3.length; i++) {
			FtpYieldCurveSfb ftpYieldCurve0 = new FtpYieldCurveSfb();
			ftpYieldCurve0.setCurveNo(curveType+"00");
			ftpYieldCurve0.setCurveName("�г�����������");
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
	 * ��������������
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
		String[] sheetName1 = { "��׼��"};
		String[] sheetName2 = { "��׼��", "������", "�����" };
		if(curveType.equals("1")) {//�����
			sheetName = sheetName2;
		}else {
			sheetName = sheetName1;
		}

		for (int i = 0; i < (curveType.equals("1")?sheetName2.length:sheetName1.length); i++) {
			HSSFSheet sheet = workbook.createSheet(sheetName[i]);

			// ����һ����ʽ
			HSSFCellStyle style = workbook.createCellStyle();
			// ������Щ��ʽ
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			// ����һ������
			HSSFFont font = workbook.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			// ������Ӧ�õ���ǰ����ʽ
			style.setFont(font);

			try {
				// ��ͷ
				sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 7));
				HSSFRow row = sheet.createRow(0); // ������һ�У�Ҳ���������ͷ
				row.setHeight((short) 400);
				HSSFCell cell = row.createCell(0);
				cell.setCellStyle(style);
				cell.setCellValue(date + "�ջ���" + brNo + "��"
						+ (curveType.equals("1") ? "�����" : "�г�") + "����������-"
						+ sheetName[i]);
				HSSFRow row2 = sheet.createRow(1); // �����ڶ���
				HSSFCell cell1 = row2.createCell(0);
				cell1.setCellValue("����");
				HSSFCell cell2 = row2.createCell(1);
				cell2.setCellValue("����ֵ(%)");
				// ������е�����
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
	 * �����ʽ�ؽ��
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

		String sheetName = "���ʽ�ض��۽��";

		HSSFSheet sheet = workbook.createSheet(sheetName);
		// ����һ����ʽ
		HSSFCellStyle style = workbook.createCellStyle();
		HSSFCellStyle style1 = workbook.createCellStyle();
		HSSFCellStyle style2 = workbook.createCellStyle();
		HSSFCellStyle style3 = workbook.createCellStyle();
		// ������Щ��ʽ
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style3.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
		// ����һ������
		HSSFFont font = workbook.createFont();
		HSSFFont font1 = workbook.createFont();
		font1.setFontHeightInPoints((short) 11);
		font1.setFontName("����");
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		// ������Ӧ�õ���ǰ����ʽ
		style.setFont(font);
		style1.setFont(font1);
		style2.setFont(font1);
		style3.setFont(font1);

		try {
			// ��ͷ
			sheet.setColumnWidth(0, 3766);
			sheet.setColumnWidth(1, 10000);
			sheet.setColumnWidth(2, 6000);
			sheet.setColumnWidth(3, 6000);
			sheet.setColumnWidth(4, 5000);
		

			Region region = new Region(0, (short) 0, 0, (short) 7);
			sheet.addMergedRegion(region);
			HSSFRow row = sheet.createRow(0); // ������һ�У�Ҳ���������ͷ
			row.setHeight((short) 400);
			HSSFCell cell = row.createCell(0);
			cell.setCellStyle(style);
			cell.setCellValue(date+"@"+LrmUtil.getBrName(brNo) + sheetName);
			HSSFRow row2 = sheet.createRow(1); // �����ڶ���
			HSSFCell cell1 = row2.createCell(0);
			cell1.setCellValue("���");
			cell1.setCellStyle(style2);
			
			HSSFCell cell2 = row2.createCell(1);
			cell2.setCellValue("�ʽ������");
			cell2.setCellStyle(style2);
			
			HSSFCell cell3 = row2.createCell(2);
			cell3.setCellValue("�ʲ�����");
			cell3.setCellStyle(style2);
			
			HSSFCell cell4 = row2.createCell(3);
			cell4.setCellValue("��Ӫ���Ե���(%)");
			cell4.setCellStyle(style2);
			
			HSSFCell cell5 = row2.createCell(4);
			cell5.setCellValue("ת�Ƽ۸�(%)");
			cell5.setCellStyle(style2);
			// ������е�����
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
		//����һ����ʽ
	    HSSFCellStyle style=workbook.createCellStyle();
	    //������Щ��ʽ
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //����һ������
        HSSFFont font=workbook.createFont();
        font.setFontHeightInPoints((short)12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        //������Ӧ�õ���ǰ����ʽ
        style.setFont(font);
		for(int i = 0; i < list_curveName.size(); i++) {
			String[] curveInfos = list_curveName.get(i).split("-");
			String listInfo = list_curveName.get(i);
			System.out.println("list_curveName.get(i)"+list_curveName.get(i));
			HSSFSheet sheet = workbook.createSheet(listInfo.replace(curveInfos[1], brInfoDAO.getBrNo(curveInfos[1])));
			 
			try {
				// ��ͷ
				sheet.addMergedRegion(new Region(0, (short) 0, 0, (short) 9));//�ϲ���
				HSSFRow row = sheet.createRow(0); // ������һ�У�Ҳ���������ͷ
				row.setHeight((short)400);
				HSSFCell cell = row.createCell(0);
				cell.setCellStyle(style);
				cell.setCellValue(listInfo);
				HSSFRow row2 = sheet.createRow(1); // �����ڶ���
				HSSFCell cell1 = row2.createCell(0);
				cell1.setCellValue("����");
				HSSFCell cell2 = row2.createCell(1);
				cell2.setCellValue("����ֵ(%)");
				
				SCYTCZlineF f = list_F.get(i);
				
				int o = 0;
				//�����Ļ�׼�ߺʹ����Ҫ�������
				if(listInfo.indexOf("�����-��׼��")!=-1||listInfo.indexOf("�����-���")!=-1) {
					o++;
					HSSFRow rowData = sheet.createRow(2);
					HSSFCell cellData1 = rowData.createCell(0);
					cellData1.setCellValue(new HSSFRichTextString("����"));
					HSSFCell cellData2 = rowData.createCell(1);
					cellData2.setCellValue(new HSSFRichTextString(String.valueOf(CommonFunctions.doublecut(f.getY_SCYTCZline(0)*100, 4))));
				}
				
				// ������е�����
				String year = list_curveName.get(i).substring(0, 4);
				Integer dayNum = DateUtil.isLeapYear(Integer.valueOf(year)) == true ? 366 : 365;// �������ܵ�������
				
				for (int j = 0; j < dayNum; j++) {
					HSSFRow rowData = sheet.createRow(j+2+o);
					HSSFCell cellData1 = rowData.createCell(0);
					cellData1.setCellValue(new HSSFRichTextString(j == dayNum - 1 ? "1��" : (j + 1) + "��"));
					HSSFCell cellData2 = rowData.createCell(1);
					cellData2.setCellValue(new HSSFRichTextString(String.valueOf(CommonFunctions.doublecut(f.getY_SCYTCZline((j + 1) / 30.0)*100, 4))));
				}
				int m = 13;
				for (int j = dayNum; j < dayNum + 348; j++) {
					HSSFRow rowData = sheet.createRow(j+2+o);
					HSSFCell cellData1 = rowData.createCell(0);
					cellData1.setCellValue(new HSSFRichTextString(m % 12 == 0 ? m / 12 + "��" : m + "��"));
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
			buffer.append("��ҳ&nbsp;");
			buffer.append("��һҳ&nbsp;");
		} else {
			buffer.append("<a href=\"" + pageName + "&page=1&rowsCount="
					+ rowsCount + "\">��ҳ</a>&nbsp;");
			buffer.append("<a href=\"" + pageName + "&page="
					+ (currentPage - 1) + "&rowsCount=" + rowsCount
					+ "\">��һҳ</a>&nbsp;");
		}

		if (currentPage == pageCount) {
			buffer.append("��һҳ&nbsp;");
			buffer.append("ĩҳ");
		} else {
			buffer.append("<a href=\"" + pageName + "&page="
					+ (currentPage + 1) + "&rowsCount=" + rowsCount
					+ "\">��һҳ</a>&nbsp;");
			buffer.append("<a href=\"" + pageName + "&page=" + pageCount
					+ "&rowsCount=" + rowsCount + "\">ĩҳ</a>&nbsp;");
		}
		buffer.append("&nbsp;&nbsp;��������" + rowsCount + "�����ݣ�ÿҳ" + pageSize
				+ "�����ݣ�ҳ��<font color='red'>" + currentPage + "</font>/"
				+ pageCount);

		// buffer.setLength(0);
		buffer.append("&nbsp;&nbsp;��ת����");
		buffer.append("\r\n<select onchange=\"window.location.replace('"
				+ pageName + "&page='+this.value+'&rowsCount=" + rowsCount
				+ "')\">\r\n");
		for (int i = 0; i < pageCount; i++) {
			String selected = "";
			if (i == currentPage - 1) {
				selected = "selected";
			}
			buffer.append("<option " + selected + " value=\"" + (i + 1)
					+ "\">��" + (i + 1) + "ҳ</option>\r\n");
		}
		buffer.append("</select>");

		return buffer.toString();
	}

	/**
	 * ��ȡ��������������ߵĴ�����������߹ؼ����Ӧ������ֵ
	 * ȡ���л�׼����+�ϸ�����
	 * @param dgckZb
	 * @return
	 */
    public double[] getVCYieldVKeyRate(double dgckZb) {
    	double[] keyRateV = new double[15];
    	String hsql = "from FtpPublicRate";// ��ȡ�г���������
    	List<FtpPublicRate> ftpPublicRateList = daoFactory.query(hsql, null);
    	// �����Բ�ֵ������ؼ��������ֵ
    	keyRateV[0] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "2M0", dgckZb), 7);
    	keyRateV[1] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "2M3", dgckZb), 7);
    	keyRateV[4] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "2M6", dgckZb), 7);
    	keyRateV[2] = (keyRateV[4]-keyRateV[1])/3+keyRateV[1];//4Mʹ��3M\6M�������Բ�ֵ
    	keyRateV[3] = (keyRateV[4]-keyRateV[1])*2/3+keyRateV[1];//5Mʹ��3M\6M�������Բ�ֵ
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
	 * ��ȡ��������������ߵĴ������������߹ؼ����Ӧ������ֵ
	 * ȡ���л�׼����+�ϸ�����
	 * @return
	 */
   /* public double[] getVCYieldCKeyRate_yh() {
    	double[] keyRateV = new double[15];
    	String hsql = "from FtpPublicRate";// ��ȡ�г���������
    	List<FtpPublicRate> ftpPublicRateList = daoFactory.query(hsql, null);
    	// �����Բ�ֵ������ؼ��������ֵ
    	keyRateV[4] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "1M1"), 7);//6����
    	keyRateV[1] = keyRateV[4];//3M=6M
    	keyRateV[2] = keyRateV[4];//4M=6M
    	keyRateV[3] = keyRateV[4];//5M=6M
    	keyRateV[0] = keyRateV[1];//��=6M
    	keyRateV[6] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "1M2"), 7);//1��
    	keyRateV[5] = (keyRateV[6]-keyRateV[4])/2+keyRateV[4];//9Mʹ��6M\12Y�������Բ�ֵ
    	keyRateV[8] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "1Y1"), 7);//3��
    	keyRateV[7] = (keyRateV[8]-keyRateV[6])/2+keyRateV[6];//2Yʹ��1Y\3Y�������Բ�ֵ
    	keyRateV[9] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "1Y2"), 7);//5��
    	keyRateV[10] = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "1Y3"), 7);//6��

    	keyRateV[11] = keyRateV[10];//7��
    	keyRateV[12] = keyRateV[10];//8��
    	keyRateV[13] = keyRateV[10];//9��
    	keyRateV[14] = keyRateV[10];//10��
    	
    	return keyRateV;
    }*/
    
    /**
	 * ��ȡ��������������ߵĴ������������߹ؼ����Ӧ������ֵ
	 * �Բ�ͬ���޷�Χ�ڵĴ������ʽ��м�Ȩƽ����ȡ����12�����������Ĵ���<��ǰ��365��>
	 * @return
	 */
    public double[] getVCYieldCKeyRate(String date) {
    	double[] keyRateC = new double[15];
    	String startDate = CommonFunctions.dateModifyD(date, -365);//��ǰ��365��
    	String endDdate = CommonFunctions.dateModifyD(date, -1);//����
    	String sql = "select (days(to_date(END_DATE,'yyyymmdd'))-days(to_date(LOAN_DATE,'yyyymmdd'))) term,INTEREST_RATE*1.2/100,nvl(LOAN_MONEY,0.0)" +
    			  " from bips.CMS_MIR_LISTLOANBALANCE" +
    			  " where STATE5_LOAN in('01','02')" +//�弶����״̬Ϊ�����͹�ע
    					" and LOAN_ITEM != '1289'" +//�ų�:����
    					" and length(END_DATE)=10" +//�ų�:����
    					" and to_date(LOAN_DATE,'yyyymmdd')>=to_date('"+startDate+"','yyyymmdd')" +
    			        " and to_date(LOAN_DATE,'yyyymmdd')<=to_date('"+endDdate+"','yyyymmdd')";
    	List list = daoFactory.query1(sql, null);

    	double[][] amt = new double[8][2];//ÿ�ж�Ӧ�����ؼ���ķ���amt�ͷ�ĸamt
    	//ÿ���ؼ�������޷�Χ����λ����
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
    	double keyRate[] = new double[8];//�ȶԴ����ݿ��ȡ���⼸���ؼ����ֵ���д�������ֵΪNaN��ҩ���в�ֵ
    	double key[] = {3,6,9,12,24,36,60,120};//keyRate��Ӧ��x����
    	keyRate[0] = amt[0][0]/amt[0][1];
    	keyRate[1] = amt[1][0]/amt[1][1];
    	keyRate[2] = amt[2][0]/amt[2][1];
    	keyRate[3] = amt[3][0]/amt[3][1];
    	keyRate[4] = amt[4][0]/amt[4][1];
    	keyRate[5] = amt[5][0]/amt[5][1];
    	keyRate[6] = amt[6][0]/amt[6][1];
    	keyRate[7] = amt[7][0]/amt[7][1];
    	keyRate = this.handleKeyRate(key, keyRate);//��������ΪNAN��ֵ

    	keyRateC[0] = keyRate[0];//����ڻ����Ǹ��㣬���óɸ�3M��ͬ
    	keyRateC[1] = keyRate[0];//3M
    	keyRateC[2] = (keyRate[1]-keyRate[0])*1/3+keyRate[0];//4Mʹ��3M\6M�������Բ�ֵ
    	keyRateC[3] = (keyRate[1]-keyRate[0])*2/3+keyRate[0];//5Mʹ��3M\6M�������Բ�ֵ
    	keyRateC[4] = keyRate[1];//6M
    	keyRateC[5] = keyRate[2];//9M
    	keyRateC[6] = keyRate[3];//1Y
    	keyRateC[7] = keyRate[4];//2Y
    	keyRateC[8] = keyRate[5];//3Y
    	keyRateC[9] = keyRate[6];//5Y
    	keyRateC[10] = (keyRate[7]-keyRate[6])*1/5+keyRate[6];//6Y�������Բ�ֵ
    	keyRateC[11] = (keyRate[7]-keyRate[6])*2/5+keyRate[6];//7Y�������Բ�ֵ
    	keyRateC[12] = (keyRate[7]-keyRate[6])*3/5+keyRate[6];//8Y�������Բ�ֵ
    	keyRateC[13] = (keyRate[7]-keyRate[6])*4/5+keyRate[6];//9Y�������Բ�ֵ
    	keyRateC[14] = keyRate[7];//10Y
    	
    	return keyRateC;
    }
    /**
     * ��ȡ��������������ߵ����������߹ؼ����Ӧ������ֵ
	 * ���ݴ�������������ߺʹ��������ָ�������ý��м���
     * @param keyRateV ����
     * @param keyRateC ���
     * @return
     */
    public double[] getVCYieldKeyRate(double[] keyRateV, double[] keyRateC, String date, double dgckZb) {
    	double[] keyRate = new double[15];
    	String hsql = "from FtpInterestMarginDivide";//��ȡ ���������ָ��������
    	//��ؼ��������һһ��Ӧ
    	List<FtpInterestMarginDivide> ftpInterestMarginDivideList = daoFactory.query(hsql, null);
    	String[] key = {"3M", "4M", "5M", "6M", "9M", "1Y", "2Y", "3Y", "5Y", "6Y", "7Y", "8Y", "9Y", "10Y"};
    	//"3M", "6M", "9M", "1Y", "2Y", "3Y", "5Y", "10Y"
    	Map<String, Double> keyMap = new HashMap<String, Double>();
    	for(FtpInterestMarginDivide ftpInterestMarginDivide : ftpInterestMarginDivideList) {
    		if(ftpInterestMarginDivide.getAssetsType().equals("02")){
    			keyMap.put(ftpInterestMarginDivide.getTermType(), ftpInterestMarginDivide.getRate());
    	    }
    	}
    	//���Բ�ֵ4M\5M\6Y\7Y\8Y\9Y�ĵ�
    	keyMap.put("4M", (keyMap.get("6M")-keyMap.get("3M"))*1/3+keyMap.get("3M"));
    	keyMap.put("5M", (keyMap.get("6M")-keyMap.get("3M"))*2/3+keyMap.get("3M"));
    	keyMap.put("6Y", (keyMap.get("10Y")-keyMap.get("5Y"))*1/5+keyMap.get("5Y"));
    	keyMap.put("7Y", (keyMap.get("10Y")-keyMap.get("5Y"))*2/5+keyMap.get("5Y"));
    	keyMap.put("8Y", (keyMap.get("10Y")-keyMap.get("5Y"))*3/5+keyMap.get("5Y"));
    	keyMap.put("9Y", (keyMap.get("10Y")-keyMap.get("5Y"))*4/5+keyMap.get("5Y"));
    	for(int i = 0; i < key.length; i++) {
    		//�ؼ�������=���ؼ�������+����*�����ռ����
    		keyRate[i+1] = keyRateV[i+1] + (keyRateC[i+1] - keyRateV[i+1]) * keyMap.get(key[i]);
    	}
    	
    	
    	//double cdl = 0.6;//������������
    	//double gyll = FtpUtil.getShiborRate(date, 30);//��������ҹ����
    	//System.out.println("��ҹ����:"+gyll);
    	//System.out.println("keyRate1Y:"+keyRate[6]);
    	//keyRate[0] = cdl*keyRate[6]+(1-cdl)*gyll;//����
    	double rate = 0.033;//���ڵ�����ʡ������������㷽��һ�������д������*(1+�ϸ�����)
    	String hsql2 = "from FtpPublicRate";//��ȡ�������
    	List<FtpPublicRate> ftpPublicRateList = (List<FtpPublicRate>)daoFactory.query(hsql2, null);
    	if(ftpPublicRateList != null&&ftpPublicRateList.size()>0) {
    		//1���ڴ��
    		rate = CommonFunctions.doublecut(FtpUtil.getPublicRate(ftpPublicRateList, "2Y1", dgckZb), 7);
    	}

    	keyRate[0] = CommonFunctions.doublecut(rate, 7);//���ڵ�
    	
    	return keyRate;
    }
    /**
     * �Թؼ����ֵ���д������ĳ�����ֵΪNAN�����������ĵ���в�ֵ
     * @return
     */
    public double[] handleKeyRate(double[] key, double[] keyRate) {
    	if(Double.isNaN(keyRate[0])) {//��һ�������⴦�����=NAN���������Ҳ�ΪNAN�ĵ�һ��ֵ��ֱ�����
    		for (int i = 1; i < keyRate.length; i++) {
				if(!Double.isNaN(keyRate[i])) {
					keyRate[0] = keyRate[i];
					break;
				}
			}
    	}
    	//�ӵڶ�����ѭ���ж��Ƿ�ΪNAN
    	for (int i = 1; i < keyRate.length; i++) {
    		if(Double.isNaN(keyRate[i])) {
    			//�����Ҳ�ΪNAN�ĵ�һ��ֵ
    			int m = 0;
    			for (int j = i + 1; j < keyRate.length; j++) {
    				if(!Double.isNaN(keyRate[j])) {
    					//�������Բ�ֵ
    					keyRate[i] = (keyRate[j]-keyRate[i-1])*(key[i]-key[i-1])/(key[j]-key[i-1])+keyRate[i-1];
    					m++;
    					break;
    				}
    			}
    			if(m == 0) {//����Ҳ��ֵ��ΪNAN,��ֱ��=����Ǹ����ֵ
    				keyRate[i] = keyRate[i-1];
    			}
    		}
		}
    	return keyRate;	
    }
    
    /**
	 * ��ȡ����һ��ĶԹ�������ռ��˽���ڴ�������ı���
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
				dsCkBal = Double.valueOf(String.valueOf(obj[1]));//��˽������
			}else if(String.valueOf(obj[1]).equals("2")){
				dgCkBal = Double.valueOf(String.valueOf(obj[1]));//�Թ�������
			}
		}
		double dgCkZb = dgCkBal/(dgCkBal+dsCkBal);//�Թ����ռ��
		dgCkZb = (Double.isInfinite(dgCkZb)||Double.isNaN(dgCkZb))?0.5:dgCkZb;
		return dgCkZb;
	}


	/**
	 * ��ȡ����Ϣ�ʲ�������������
	 * @param date
	 * @return
	 */
	public Map<String, double[]> getFshzcKeyRate(String date,String minDate) {
    /*	int assessScope = -1;//ʱ�䷶Χ  -1 Ϊ��
    	date = CommonFunctions.dateModifyD(date, -1);//��������ĩ
		String minDate = FtpUtil.getMinDate(date, assessScope);//��ȡʱ����˵�
*/    	Map<String, double[]> curveMap = new HashMap<String, double[]>();
		double[] keyRate = new double[15];// �ؼ�������
		// 6M-3Y�����ù�ծ������
		Map<String, Double> stockRateMap = FtpUtil.getStockRate(minDate, date);//��ȡƽ������
		if (stockRateMap == null || stockRateMap.size() == 0) {
			curveMap.put("aveStockError", new double[0]);
			return curveMap;
		}
		keyRate[0] = CommonFunctions.doublecut(stockRateMap.get("6M"), 7);// 6����
		keyRate[1] = CommonFunctions.doublecut(stockRateMap.get("9M"), 7);// 9����
		keyRate[2] = CommonFunctions.doublecut(stockRateMap.get("1Y"), 7);// 1����
		keyRate[3] = CommonFunctions.doublecut(stockRateMap.get("2Y"), 7);// 2����
		keyRate[4] = CommonFunctions.doublecut(stockRateMap.get("3Y"), 7);// 3����
		keyRate[5] = CommonFunctions.doublecut(stockRateMap.get("4Y"), 7);// 4����
		keyRate[6] = CommonFunctions.doublecut(stockRateMap.get("5Y"), 7);// 5����
		keyRate[7] = CommonFunctions.doublecut(stockRateMap.get("6Y"), 7);// 6����
		keyRate[8] = CommonFunctions.doublecut(stockRateMap.get("7Y"), 7);// 7����
		keyRate[9] = CommonFunctions.doublecut(stockRateMap.get("8Y"), 7);// 8����
		keyRate[10] = CommonFunctions.doublecut(stockRateMap.get("9Y"), 7);// 9����
		keyRate[11] = CommonFunctions.doublecut(stockRateMap.get("10Y"), 7);// 10����
		keyRate[12] = CommonFunctions.doublecut(stockRateMap.get("15Y"), 7);// 15����
		keyRate[13] = CommonFunctions.doublecut(stockRateMap.get("20Y"), 7);// 20����
		keyRate[14] = CommonFunctions.doublecut(stockRateMap.get("30Y"), 7);// 30����
		curveMap.put("keyRate", keyRate);
		return curveMap;
	}

	/**
	 * ��ȡ��������ǰһ����г�����������
	 * @param date
	 * @return
	 */
	public Map<String, double[]> getTodayMarketKeyRate(String date) throws ParseException {
		Map<String, double[]> curveMap = new HashMap<String, double[]>();
		double[] keyRate = new double[22];// �ؼ�������
		// O/N-3M������shiborError����
		// ##�޸�shibor��ȡ�������ʶ������졣 ֻ�޸��г��������������е�shibor���ʵ�ȡֵ�������ı��ֲ���
		String shiborDate=CommonFunctions.dateModifyD(date, 1);
		Map<String, Double> shiborRateMap = FtpUtil.getShiborRate(shiborDate);//��ȡ��������ǰһ������� ##�޸�Ϊ����
//			repoRateMap = FtpUtil.getRepoRate(date);//��ȡ���������
		if (shiborRateMap == null) {
			curveMap.put("shiborError", new double[0]);
			return curveMap;
		}
		keyRate[0] = CommonFunctions.doublecut(shiborRateMap.get("O/N"), 7);// ��ҹ
		keyRate[1] = CommonFunctions.doublecut(shiborRateMap.get("1W"), 7);// 1��
		keyRate[2] = CommonFunctions.doublecut(shiborRateMap.get("2W"), 7);// 2��
		keyRate[4] = CommonFunctions.doublecut(shiborRateMap.get("1M"), 7);// 1����
		keyRate[3] = (keyRate[4]-keyRate[2])*(21/30-14/30)/(30-14/30)+keyRate[2];//3wʹ��2w\1m�������Բ�ֵ
		System.out.println(21.0/30.0);
		keyRate[6] = CommonFunctions.doublecut(shiborRateMap.get("3M"), 7);// 3����
		keyRate[5] = (keyRate[6]-keyRate[4])*1/2+keyRate[4];//2Mʹ��1M\3M�������Բ�ֵ
		// 6M-30Y�����ù�ծ������
		Map<String, Double> stockRateMap = FtpUtil.getStockRate(date);//��ȡ���������
		if (stockRateMap == null || stockRateMap.size() == 0) {
			curveMap.put("stockError", new double[0]);
			return curveMap;
		}
		keyRate[7] = CommonFunctions.doublecut(stockRateMap.get("6M"), 7);// 6����
		keyRate[8] = CommonFunctions.doublecut(stockRateMap.get("9M"), 7);// 9����
		keyRate[9] = CommonFunctions.doublecut(stockRateMap.get("1Y"), 7);// 1����
		keyRate[10] = CommonFunctions.doublecut(stockRateMap.get("2Y"), 7);// 2����
		keyRate[11] = CommonFunctions.doublecut(stockRateMap.get("3Y"), 7);// 3����
		keyRate[12] = CommonFunctions.doublecut(stockRateMap.get("4Y"), 7);// 4����
		keyRate[13] = CommonFunctions.doublecut(stockRateMap.get("5Y"), 7);// 5����
		keyRate[14] = CommonFunctions.doublecut(stockRateMap.get("6Y"), 7);// 6����
		keyRate[15] = CommonFunctions.doublecut(stockRateMap.get("7Y"), 7);// 7����
		keyRate[16] = CommonFunctions.doublecut(stockRateMap.get("8Y"), 7);// 8����
		keyRate[17] = CommonFunctions.doublecut(stockRateMap.get("9Y"), 7);// 9����
		keyRate[18] = CommonFunctions.doublecut(stockRateMap.get("10Y"), 7);// 10����
		keyRate[19] = CommonFunctions.doublecut(stockRateMap.get("15Y"), 7);// 15����
		keyRate[20] = CommonFunctions.doublecut(stockRateMap.get("20Y"), 7);// 20����
		keyRate[21] = CommonFunctions.doublecut(stockRateMap.get("30Y"), 7);// 30����
		curveMap.put("keyRate", keyRate);
		return curveMap;
	}


	/**
	 * ��ծ����������--����
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
			// ��׼��
			FtpYieldCurve ftpYieldCurve0 = new FtpYieldCurve();
			ftpYieldCurve0.setCurveNo(curveType + "00");
			ftpYieldCurve0.setCurveName("��ծ����������");
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
	 * �������������--����
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
			// ��׼��
			FtpYieldCurve ftpYieldCurve0 = new FtpYieldCurve();
			ftpYieldCurve0.setCurveNo(curveType + "00");
			ftpYieldCurve0.setCurveName("�������������");
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
	 * shibor����������--����
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
			// ��׼��
			FtpYieldCurve ftpYieldCurve0 = new FtpYieldCurve();
			ftpYieldCurve0.setCurveNo(curveType + "00");
			ftpYieldCurve0.setCurveName("shibor����������");
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
