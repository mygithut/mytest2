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

import com.dhcc.ftp.entity.FtpPoolInfo;
import com.dhcc.ftp.entity.FtpPublicRate;
import com.dhcc.ftp.entity.FtpYieldCurve;
import com.dhcc.ftp.entity.FtpYieldCurveAdjust;
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

public class Copy_2_of_UL04BO extends BaseBo{

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
		
		sql += " order by br_no, curve_date desc, curveNo";
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
		
		sql = "select * from (select m.*, rownumber() over(order by m.br_no,m.curve_date desc,m.curveNo) as rownumber from("+sql+") m ) where rownumber between "+start+" and "+end;
		List list=daoFactory.query1(sql, null);	
			
		String pageLine=this.formartPageLine(pageSize, currentPage, rowsCount, pageName);
		return new PageUtil(list,pageLine);
	}


	/**
	 * ��ȡĳ��������ĳ�����������µĲ��Ե���ֵ
	 * @param brNo
	 * @param date
	 * @return
	 */
	public Map<String, Double> getAdjustValue(String brNo) {
		long computeDate = CommonFunctions.GetCurrentDateInLong();//��ȡ���������

		Map<String, Double> adjustMap = new HashMap<String, Double>();

		List adjustList01 = this.getAdjustList(brNo, String
				.valueOf(computeDate), "01");// �ڲ����������������ȡ
		// List adjustList02 = this.getAdjustList(brNo,
		// String.valueOf(dataDate), "02");//�г������ݿ���������ȡ
		List adjustList02 = this.getAdjustList(brNo, String
				.valueOf(computeDate), "02");// �г�Ҳ�޸�Ϊ���������������ȡ

		if (adjustList01 != null && adjustList01.size() > 0) {
			for (Object object : adjustList01) {
				Object[] obj = (Object[]) object;
				// map��keyֵΪ:�����г�����-�����ʲ�����-���ޣ�����01-01-1W(�ڲ����������ߣ��ʲ�������Ϊ7���)
				adjustMap.put(obj[0] + "-" + obj[1] + "-" + obj[2], Double.valueOf(String.valueOf(obj[3]==null?"0.00":obj[3])));
			}
		}
		if (adjustList02 != null && adjustList02.size() > 0) {
			for (Object object : adjustList02) {
				Object[] obj = (Object[]) object;
				// map��keyֵΪ:�����г�����-�����ʲ�����-���ޣ�����02-01-1W(�г����������ߣ��ʲ�������Ϊ7���)
				adjustMap.put(obj[0] + "-" + obj[1] + "-" + obj[2], Double.valueOf(String.valueOf(obj[3]==null?"0.00":obj[3])));
			}
		}
		// System.out.println("adjustMap.get(02-01-O/N)="+adjustMap.get("02-01-O/N"));
		return adjustMap;
	}

	/**
	 * ��ȡĳ��������ĳ��������ǰ�����µĲ��Ե���ֵ,���ڻ��������������������Ĳ��Ե���ֵ
	 * 
	 * @param brNo
	 * @param date
	 * @return
	 */
	public String[] getCountyAdjustValue(String brNo, String date,
			String curveType) {
		String[] adjustValue = new String[2];
		StringBuffer adjustKey = new StringBuffer();
		StringBuffer adjustRate = new StringBuffer();

		List adjustList = this.getAdjustList(brNo, date, curveType);

		if (adjustList != null && adjustList.size() > 0) {
			for (Object object : adjustList) {
				Object[] obj = (Object[]) object;
				adjustKey.append(obj[1].equals("01") ? "d" : "c" + obj[2] + ",");
				adjustRate.append(obj[3] + ",");
			}
			adjustValue[0] = adjustKey.toString();
			adjustValue[1] = adjustRate.toString();
		}
		return adjustValue;
	}

	/**
	 * ��ȡĳ��������ĳ��������ǰ�����µĲ��Ե���
	 * 
	 * @param brNo
	 * @param date
	 * @param curveType
	 * @return
	 */
	public List getAdjustList(String brNo, String date, String curveType) {

		String hsql = "select CURVE_MARKET_TYPE, CURVE_ASSETS_TYPE, TERM_TYPE, ADJUST_VALUE "
				+ "from ftp.ftp_yield_curve_adjust t where br_no='"
				+ brNo
				+ "' and adjust_date <= '" + date + "' ";
		if (curveType != null) {
			hsql += " and CURVE_MARKET_TYPE = '" + curveType + "'";
		}
		// ��ȡ�������
		hsql += "and ADJUST_DATE=(select max(ADJUST_DATE) from ftp.ftp_yield_curve_adjust where br_no='"
				+ brNo + "' " + "and adjust_date <= '" + date + "'";
		if (curveType != null) {
			hsql += " and CURVE_MARKET_TYPE = '" + curveType + "'";
		}
		hsql += " ) ";

		List adjustList = daoFactory.query1(hsql, null);

		return adjustList;
	}

	/**
	 * ��������
	 * 
	 * @param curveType
	 * @param date
	 * @param optionsCost
	 * @param adjustKeyP
	 *            ʡ����
	 * @param adjustRateP
	 *            ʡ����
	 * @param adjustKeyC
	 *            ������
	 * @param adjustRateC
	 *            ������
	 * @return
	 */
	public Map<String, double[]> calculateCurve(String curveType, String date,
			String optionsCost, String adjustKeyC, String adjustRateC) {
		Map<String, double[]> curveMap = new HashMap<String, double[]>();
		double[] key = null;// ��׼�ؼ���
		double[] keyRate = null;// ��׼�ؼ�������
		System.out.println("curveType" + curveType);
		/*
		 * 1.�����޷�������������
		 */
		if (curveType.equals("1")) {
			// �ڲ����������� 15����׼�㣺(3�������ڷֳ��ĸ�)1�졢7�졢1���¡������� ��3����
			// ��6���¡�1�ꡢ2�ꡢ3�ꡢ5�ꡢ7�ꡢ10�ꡢ15�ꡢ20�ꡢ30��
			key = new double[15];
			key[0] = 1 / 30.0;
			key[1] = 7 / 30.0;
			key[2] = 1;
			key[3] = 2;
			key[4] = 3;
			key[5] = 6;
			key[6] = 12;
			key[7] = 24;
			key[8] = 36;
			key[9] = 60;
			key[10] = 84;
			key[11] = 120;
			key[12] = 180;
			key[13] = 240;
			key[14] = 360;

			keyRate = new double[15];
			String hsql = "from FtpPublicRate";// ��ȡ�г���������
			List<FtpPublicRate> ftpPublicRateList = daoFactory.query(hsql, null);
			// �����Բ�ֵ������ؼ��������ֵ
			/*keyRate[0] = CommonFunctions.doublecut((FtpUtil.getPublicRate(
					ftpPublicRateList, "2D1") + FtpUtil.getPublicRate(
					ftpPublicRateList, "1M1")) / 2, 7);
			keyRate[1] = CommonFunctions.doublecut((FtpUtil.getPublicRate(
					ftpPublicRateList, "2D7") + FtpUtil.getPublicRate(
					ftpPublicRateList, "1M1")) / 2, 7);
			double ck1 = FtpUtil.getPublicRate(ftpPublicRateList, "2D7")
					+ (FtpUtil.getPublicRate(ftpPublicRateList, "2M3") - FtpUtil
							.getPublicRate(ftpPublicRateList, "2D7"))
					/ (90 - 7) * (30 - 7);// 1���´������
			double ck2 = FtpUtil.getPublicRate(ftpPublicRateList, "2D7")
					+ (FtpUtil.getPublicRate(ftpPublicRateList, "2M3") - FtpUtil
							.getPublicRate(ftpPublicRateList, "2D7"))
					/ (90 - 7) * (60 - 7);// 2���´������
			keyRate[2] = CommonFunctions.doublecut((ck1 + FtpUtil
					.getPublicRate(ftpPublicRateList, "1M1")) / 2, 7);
			keyRate[3] = CommonFunctions.doublecut((ck2 + FtpUtil
					.getPublicRate(ftpPublicRateList, "1M1")) / 2, 7);
			keyRate[4] = CommonFunctions.doublecut((FtpUtil.getPublicRate(
					ftpPublicRateList, "2M3") + FtpUtil.getPublicRate(
					ftpPublicRateList, "1M1")) / 2, 7);
			keyRate[5] = CommonFunctions.doublecut((FtpUtil.getPublicRate(
					ftpPublicRateList, "2M6") + FtpUtil.getPublicRate(
					ftpPublicRateList, "1M1")) / 2, 7);
			keyRate[6] = CommonFunctions.doublecut((FtpUtil.getPublicRate(
					ftpPublicRateList, "2Y1") + FtpUtil.getPublicRate(
					ftpPublicRateList, "1M2")) / 2, 7);
			keyRate[7] = CommonFunctions.doublecut((FtpUtil.getPublicRate(
					ftpPublicRateList, "2Y2") + FtpUtil.getPublicRate(
					ftpPublicRateList, "1Y1")) / 2, 7);
			keyRate[8] = CommonFunctions.doublecut((FtpUtil.getPublicRate(
					ftpPublicRateList, "2Y3") + FtpUtil.getPublicRate(
					ftpPublicRateList, "1Y1")) / 2, 7);
			keyRate[9] = CommonFunctions.doublecut((FtpUtil.getPublicRate(
					ftpPublicRateList, "2Y5") + FtpUtil.getPublicRate(
					ftpPublicRateList, "1Y2")) / 2, 7);
			keyRate[10] = CommonFunctions.doublecut((FtpUtil.getPublicRate(
					ftpPublicRateList, "2Y5") + FtpUtil.getPublicRate(
					ftpPublicRateList, "1Y3")) / 2, 7);
			keyRate[11] = CommonFunctions.doublecut(keyRate[10], 7);
			keyRate[12] = CommonFunctions.doublecut(keyRate[10], 7);
			keyRate[13] = CommonFunctions.doublecut(keyRate[10], 7);
			keyRate[14] = CommonFunctions.doublecut(keyRate[10], 7);*/
		} else if (curveType.equals("2")) {
			// �г�����������
			// 17����׼�㣺1�죨����ҹ����7�졢14�졢1���¡�3���¡�6���¡�9���¡�12���¡�1�ꡢ2�ꡢ3�ꡢ5�ꡢ7�ꡢ10�ꡢ15�ꡢ20�ꡢ30��
			// ����12������shibor�е�1���ڣ�1���ǹ�ծ�е�1���ڣ��ֳ����������г�����������
			key = new double[17];
			key[0] = 1 / 30.0;
			key[1] = 7 / 30.0;
			key[2] = 14 / 30.0;
			key[3] = 1;
			key[4] = 3;
			key[5] = 6;
			key[6] = 9;
			key[7] = 12;
			key[8] = 12;
			key[9] = 24;
			key[10] = 36;
			key[11] = 60;
			key[12] = 84;
			key[13] = 120;
			key[14] = 180;
			key[15] = 240;
			key[16] = 360;

			keyRate = new double[17];
			// һ�����ڲ��ֲ��û����г��ع����ʣ��ο�Shibor����,��ȡǰ5��(��������ĩ)��������ƽ��
			Map<String, Double> shiborRateMap = null;
			//try {
				//shiborRateMap = FtpUtil.getShiborRate(date,-7);
			//} catch (ParseException e) {
			//	e.printStackTrace();
			//}// ����ʱ���ᵼ����ĩ�����ݣ����ǰ7��͵��ڲ�������ĩ��ǰ5��
			if (shiborRateMap == null) {
				curveMap.put("shiborError", new double[0]);
				return curveMap;
			}
			keyRate[0] = CommonFunctions.doublecut(shiborRateMap.get("O/N"), 7);// ��ҹ
			keyRate[1] = CommonFunctions.doublecut(shiborRateMap.get("1W"), 7);// 1��
			keyRate[2] = CommonFunctions.doublecut(shiborRateMap.get("2W"), 7);// 2��
			keyRate[3] = CommonFunctions.doublecut(shiborRateMap.get("1M"), 7);// 1����
			keyRate[4] = CommonFunctions.doublecut(shiborRateMap.get("3M"), 7);// 3����
			keyRate[5] = CommonFunctions.doublecut(shiborRateMap.get("6M"), 7);// 6����
			keyRate[6] = CommonFunctions.doublecut(shiborRateMap.get("9M"), 7);// 9����
			keyRate[7] = CommonFunctions.doublecut(shiborRateMap.get("1Y"), 7);// 12����
			// һ�����ϲ��ֲο����м�̶����ʹ�ծ������,��ȡǰ5��(��������ĩ)��������ƽ��
			Map<String, Double> yieldMap = null;
			try {
				yieldMap = FtpUtil.getGzpjsyl(-7, date);
			} catch (ParseException e) {
				e.printStackTrace();
			}// ����ʱ���ᵼ����ĩ�����ݣ����ǰ7��͵��ڲ�������ĩ��ǰ5��
			if (yieldMap == null || yieldMap.size() == 0) {
				curveMap.put("stockError", new double[0]);
				return curveMap;
			}
			keyRate[8] = CommonFunctions.doublecut(yieldMap.get("1"), 7);// 1����
			keyRate[9] = CommonFunctions.doublecut(yieldMap.get("2"), 7);// 2����
			keyRate[10] = CommonFunctions.doublecut(yieldMap.get("3"), 7);// 3����
			keyRate[11] = CommonFunctions.doublecut(yieldMap.get("5"), 7);// 5����
			keyRate[12] = CommonFunctions.doublecut(yieldMap.get("7"), 7);// 7����
			keyRate[13] = CommonFunctions.doublecut(yieldMap.get("10"), 7);// 10����
			keyRate[14] = CommonFunctions.doublecut(yieldMap.get("15"), 7);// 15����
			keyRate[15] = CommonFunctions.doublecut(yieldMap.get("20"), 7);// 20����
			keyRate[16] = CommonFunctions.doublecut(yieldMap.get("30"), 7);// 30����
			System.out.println("keyRate[16]" + keyRate[16]);
		}
		curveMap.put("key", key);
		curveMap.put("keyRate", keyRate);
		/*
		 * 2.���õȼ����� ����ֵ=���ҿ������������Խ��ڻ���ծȯ������-��ծ�����ʡ��������ʶ���ǰ90���ƽ��ֵ��
		 */
		double[] xyAdjust = new double[10];
		// ǰ90���ƽ������ծ������
		Map<String, Double> finacialMap = null;
		try {
			finacialMap = FtpUtil.getJrzpjsyl(-90, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (finacialMap == null || finacialMap.size() == 0) {
			curveMap.put("finacialError", new double[0]);
			return curveMap;
		}
		// ǰ90���ƽ����ծ������
		Map<String, Double> stockMap = null;
		try {
			stockMap = FtpUtil.getGzpjsyl(-90, date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.println("finacialMap"+finacialMap);
		System.out.println("finacialMap.get"+finacialMap.get("1"));
		xyAdjust[0] = finacialMap.get("1") - stockMap.get("1");
		xyAdjust[1] = finacialMap.get("2") - stockMap.get("2");
		xyAdjust[2] = finacialMap.get("3") - stockMap.get("3");
		xyAdjust[3] = finacialMap.get("5") - stockMap.get("5");
		xyAdjust[4] = finacialMap.get("7") - stockMap.get("7");
		xyAdjust[5] = finacialMap.get("10") - stockMap.get("10");
		xyAdjust[6] = finacialMap.get("15") - stockMap.get("15");
		xyAdjust[7] = finacialMap.get("20") - stockMap.get("20");
		xyAdjust[8] = finacialMap.get("30") - stockMap.get("30");
		for (int i = 0; i < xyAdjust.length; i++) {
			System.out.println("xyAdjust" + xyAdjust[i]);
		}
		String[] x = {"1","2","3","5","7","10","15","20","30"};
		//���δ�����õȼ�����
		double[] VOF1 = keyRate.clone();//.clone()�����������飬�Ҳ���������
		double[] COF1 = keyRate.clone();
		// �����1�����Ͻ�������
		int j = 0;
		if (curveType.equals("1")) {
			j = 6; // �ڲ������ʴӵ�7���ؼ��㿪ʼ����
		} else if (curveType.equals("2")) {
			j = 8; // �г������ʴӵ�9���ؼ��㿪ʼ����
		}
		for (int i = j; i < COF1.length; i++) {
			COF1[i] = CommonFunctions.doublecut(COF1[i] + xyAdjust[i - j], 7); // ��ÿ���ؼ�������ƽ��
		}
		curveMap.put("COF1", COF1);
		curveMap.put("VOF1", VOF1);
		/*
		 * 3.�������׼����ɱ�����
		 */
		String hsql = "from FtpPublicRate where rateNo = '3'";// ��ȡ���׼������
		FtpPublicRate ftpPublicRate = (FtpPublicRate) daoFactory.getBean(hsql,
				null);
		Double ckzbjl = ftpPublicRate.getRate();
		String hsql2 = "from FtpPublicRate where rateNo = '4'";// ��ȡ���׼��������
		FtpPublicRate ftpPublicRate2 = (FtpPublicRate) daoFactory.getBean(
				hsql2, null);
		Double ckzbjbl = ftpPublicRate2.getRate();
		double[] VOF2 = VOF1.clone();
		double[] COF2 = COF1.clone();
		for (int i = 0; i < COF2.length; i++) {
			VOF2[i] = CommonFunctions.doublecut(VOF1[i] * (1 - ckzbjl) + ckzbjl
					* ckzbjbl, 7);
			COF2[i] = CommonFunctions.doublecut((COF1[i] - (ckzbjl * ckzbjbl))
					/ (1 - ckzbjl), 7);
		}
		curveMap.put("COF2", COF2);
		curveMap.put("VOF2", VOF2);
		/*
		 * 4.��Ȩ�ɱ�����
		 */
		double[] COF3 = COF2.clone();
		double[] VOF3 = VOF2.clone();
		if (optionsCost != null && !optionsCost.equals("")) {
			for (int i = 0; i < COF3.length; i++) {
				COF3[i] = CommonFunctions.doublecut(COF2[i]
						+ Double.valueOf(optionsCost) / 2 / 100, 7);
				VOF3[i] = CommonFunctions.doublecut(VOF2[i]
						- Double.valueOf(optionsCost) / 2 / 100, 7);
			}
		}
		curveMap.put("COF3", COF3);
		curveMap.put("VOF3", VOF3);
		/*
		 * 5.�����Ե���
		 */
		double[] COF4 = COF3.clone();
		double[] VOF4 = VOF3.clone();
		String[] adjustRateStrC = null;
		String[] adjustKeyStrC = null;

		if(adjustKeyC!= null && !adjustKeyC.equals("")) {
			adjustRateStrC = adjustRateC.split(",");
			adjustKeyStrC = adjustKeyC.split(",");
			// ֵ��key��һһ��Ӧ�ģ�
			for (int i = 0; i < adjustRateStrC.length; i++) {
				int num = FtpUtil.getKeyPoint(curveType, adjustKeyStrC[i].substring(1, adjustKeyStrC[i].length()));
				if (adjustKeyStrC[i].substring(0, 1).equals("c")) {// ���
					if (curveType.equals("1") && num == 0) {// �ڲ������������ڵķֳ����ĸ���
						VOF4[0] = CommonFunctions.doublecut(VOF3[0]
								+ (Double.valueOf(adjustRateStrC[i])) / 100, 7);
						VOF4[1] = CommonFunctions.doublecut(VOF3[1]
								+ (Double.valueOf(adjustRateStrC[i])) / 100, 7);
						VOF4[2] = CommonFunctions.doublecut(VOF3[2]
								+ (Double.valueOf(adjustRateStrC[i])) / 100, 7);
						VOF4[3] = CommonFunctions.doublecut(VOF3[3]
								+ (Double.valueOf(adjustRateStrC[i])) / 100, 7);
					} else {
						VOF4[num] = CommonFunctions.doublecut(VOF3[num]
								+ (Double.valueOf(adjustRateStrC[i])) / 100, 7);
					}
				} else if (adjustKeyStrC[i].substring(0, 1).equals("d")) {// ����
					if (curveType.equals("1") && num == 0) {// �ڲ������������ڵķֳ����ĸ���
						COF4[0] = CommonFunctions.doublecut(COF3[0]
								+ (Double.valueOf(adjustRateStrC[i])) / 100, 7);
						COF4[1] = CommonFunctions.doublecut(COF3[1]
								+ (Double.valueOf(adjustRateStrC[i])) / 100, 7);
						COF4[2] = CommonFunctions.doublecut(COF3[2]
								+ (Double.valueOf(adjustRateStrC[i])) / 100, 7);
						COF4[3] = CommonFunctions.doublecut(COF3[3]
								+ (Double.valueOf(adjustRateStrC[i])) / 100, 7);
					} else {
						COF4[num] = CommonFunctions.doublecut(COF3[num]
								+ (Double.valueOf(adjustRateStrC[i])) / 100, 7);
					}
				}
			}
		}
		
		curveMap.put("COF4", COF4);
		curveMap.put("VOF4", VOF4);
		return curveMap;
	}

	/**
	 * ������ݿ����Ƿ��и��죬�г�������ͬ�����ݣ����������ɾ��
	 * 
	 * @param curveType
	 * @param date
	 * @param brNo
	 */
	public void checkSaveAddDel(String curveType, String date, String brNo, String tableName) {
		//�ȼ�����ݿ����Ƿ��е��죬�г�������ͬ�����ݣ����������ɾ���������
		String hsql1 = "delete from "+tableName+" where curveDate = '"+date+"' and curveMarketType = '"+curveType+"' and brNo = '"+brNo+"'";
		daoFactory.delete(hsql1,null);
	}

	/**
	 * �����г�����������
	 * @param date
	 * @param brNo
	 * @param curveType
	 * @param key
	 * @param COF_adjust
	 * @param VOF_adjust
	 * @param a3_1
	 * @param b2_1
	 * @param c1_1
	 * @param d0_1
	 * @param a3_2
	 * @param b2_2
	 * @param c1_2
	 * @param d0_2
	 * @param optionsCost
	 * @return
	 */
	public Map generateMarketCurve(String date, String brNo, String curveType, double[] key, double[] COF_adjust, double[] VOF_adjust, double[] a3_1, double[] b2_1, 
			double[] c1_1, double[] d0_1, double[] a3_2, double[] b2_2, double[] c1_2, double[] d0_2, String optionsCost) {
		//�Ƚ���һ�������ڵĶ������ߵ����ݴ浽list�У����ж��������߶����������ɺ�һ�ν��б��棻���ĳ�����������⣬��ֱ�ӷ��أ����еĶ�������
		String type = curveType;
		if (type.length() == 1) type = "0"+type;
		
		Map resultMap = new HashMap();
		List<String> newDateList = new ArrayList<String>();
        List<double[]> keyList = new ArrayList<double[]>();
        List<double[]> COF_adjustList = new ArrayList<double[]>();
        List<double[]> VOF_adjustList = new ArrayList<double[]>();
        List<double[]> a3_1List = new ArrayList<double[]>();
        List<double[]> b2_1List = new ArrayList<double[]>();
        List<double[]> c1_1List = new ArrayList<double[]>();
        List<double[]> d0_1List = new ArrayList<double[]>();
        List<double[]> a3_2List = new ArrayList<double[]>();
        List<double[]> b2_2List = new ArrayList<double[]>();
        List<double[]> c1_2List = new ArrayList<double[]>();
        List<double[]> d0_2List = new ArrayList<double[]>();
        
        //��ŵ��������������
        newDateList.add(date);
        keyList.add(key);
        COF_adjustList.add(COF_adjust);
        VOF_adjustList.add(VOF_adjust);
        a3_1List.add(a3_1);
        b2_1List.add(b2_1);
        c1_1List.add(c1_1);
        d0_1List.add(d0_1);
        a3_2List.add(a3_2);
        b2_2List.add(b2_2);
        c1_2List.add(c1_2);
        d0_2List.add(d0_2);
        //uL04BO.marketCurveSave(type, date, brNo, key, a3_1, b2_1, c1_1, d0_1, a3_2, b2_2, c1_2, d0_2, COF_adjust, VOF_adjust);
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
        for(int i = 1; i < N; i++) {
			String newDate = DateUtil.fmtDateToStr(DateUtil.addDay(date0, -i), "yyyyMMdd");
			System.out.println("-----------��ʼ����"+newDate+"�������������-----------");
			
			//��ȡ����������------------------------
			//��ȡ��������newDate֮ǰ���µĲ��Ե���ֵ
			String[] adjustValueC = this.getCountyAdjustValue(brNo, newDate, type);
			Map<String, double[]> curveMap = this.calculateCurve(curveType, newDate, optionsCost, adjustValueC[0], adjustValueC[1]);
	 		
			if(curveMap.get("shiborError") != null){
				resultMap.put("shiborError", "shiborError");
			}
	 		if(curveMap.get("stockError") != null){
				resultMap.put("stockError", "stockError");
			}

	 		
			double[] Key = curveMap.get("key");
	 	    double[] keyRate = curveMap.get("keyRate");
	 	    double[] COF4 = curveMap.get("COF4");
	 	    double[] VOF4 = curveMap.get("VOF4");
	 		//�г�:1�죨����ҹ����7�졢14�졢1���¡�3���¡�6���¡�9���¡�12���¡�1�ꡢ2�ꡢ3�ꡢ5�ꡢ7�ꡢ10�ꡢ15�ꡢ20�ꡢ30��
	        //�ֳ����Σ�shibor�͹�ծ
	 	    
	 	    double[] y = new double[Key.length];
	    	double[] COFAdjust = new double[Key.length];//ÿ���ڵ��ܵĵ���ֵ
	 	    double[] VOFAdjust = new double[Key.length];
	 		for (int j = 0; j < Key.length; j++) {
	 			y[j] = keyRate[j];
	 			COFAdjust[i] = COF4[i] - keyRate[i];
	 			VOFAdjust[i] = VOF4[i] - keyRate[i];
	        }
	 		
	    	double[] key_01 = new double[8];
	    	double[] key_02 = new double[9];
	    	double[] y_01 = new double[8];
	    	double[] y_02 = new double[9];
	    	SCYTCZlineF F1 = null;
	    	SCYTCZlineF F2 = null;
	    	System.arraycopy(Key,0,key_01, 0, key_01.length);
	    	System.arraycopy(Key,8,key_02, 0, key_02.length);
	    	System.arraycopy(y,0,y_01, 0, y_01.length);
	    	System.arraycopy(y,8,y_02, 0, y_02.length);
	    	F1 = SCYTCZ_Compute.getSCYTCZline(key_01, y_01);
	    	F2 = SCYTCZ_Compute.getSCYTCZline(key_02, y_02);

	    	double[][] matics1 = F1.A;
	 	    double[][] matics2 = F2.A;
	 	    
	 	    double[] A3_1 = new double[matics1.length];
		    double[] B2_1 = new double[matics1.length];
		    double[] C1_1 = new double[matics1.length];
		    double[] D0_1 = new double[matics1.length];
		    double[] A3_2 = new double[matics2.length];
		    double[] B2_2 = new double[matics2.length];
		    double[] C1_2 = new double[matics2.length];
		    double[] D0_2 = new double[matics2.length];
	 	    for (int m = 0; m < matics1.length; m++) {
	 	    	A3_1[m] = matics1[m][0];
	 	        B2_1[m] = matics1[m][1];
	 	        C1_1[m] = matics1[m][2];
	 	        D0_1[m] = matics1[m][3];
	 	    }
	 	    for (int n = 0; n < matics2.length; n++) {
	 	        A3_2[n] = matics2[n][0];
	 	        B2_2[n] = matics2[n][1];
	 	        C1_2[n] = matics2[n][2];
	 	        D0_2[n] = matics2[n][3];
	 	    }
	 	    
	 	    //��ŵ��������������
	        newDateList.add(newDate);
	        keyList.add(Key);
	        COF_adjustList.add(COFAdjust);
	        VOF_adjustList.add(VOFAdjust);
	        a3_1List.add(A3_1);
	        b2_1List.add(B2_1);
	        c1_1List.add(C1_1);
	        d0_1List.add(D0_1);
	        a3_2List.add(A3_2);
	        b2_2List.add(B2_2);
	        c1_2List.add(C1_2);
	        d0_2List.add(D0_2);
        }
        resultMap.put("newDateList", newDateList);
        resultMap.put("keyList", keyList);
        resultMap.put("COF_adjustList", COF_adjustList);
        resultMap.put("VOF_adjustList", VOF_adjustList);
        resultMap.put("a3_1List", a3_1List);
        resultMap.put("b2_1List", b2_1List);
        resultMap.put("c1_1List", c1_1List);
        resultMap.put("d0_1List", d0_1List);
        resultMap.put("a3_2List", a3_2List);
        resultMap.put("b2_2List", b2_2List);
        resultMap.put("c1_2List", c1_2List);
        resultMap.put("d0_2List", d0_2List);
        return resultMap;
	}
	/**
	 * �ڲ�����������--����
	 * @param curveType
	 * @param date
	 * @param brNo
	 * @param key
	 * @param a3
	 * @param b2
	 * @param c1
	 * @param d0
	 * @param COF_adjust
	 * @param VOF_adjust
	 */
	public void internalCurveSave(String curveType, String date, String brNo,
			double[] key, double[] a3, double[] b2, double[] c1, double[] d0,
			double[] COF_adjust, double[] VOF_adjust) {
		// ��ÿһ�ε����ζ���ʽ����ѭ�����,����������ߣ���׼��00����׼�ߵ���ֵΪ0.0�������01������02
		for (int i = 0; i < a3.length; i++) {
			// ��׼��
			FtpYieldCurve ftpYieldCurve0 = new FtpYieldCurve();
			ftpYieldCurve0.setCurveNo(curveType + "00");
			ftpYieldCurve0.setCurveName("�ڲ�����������-��׼������");
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
			FtpYieldCurve ftpYieldCurve1 = new FtpYieldCurve();
			ftpYieldCurve1.setCurveNo(curveType + "02");
			ftpYieldCurve1.setCurveName("�ڲ�����������-���VOF");
			ftpYieldCurve1.setBrNo(brNo);
			ftpYieldCurve1.setSectionNum(i + 1);
			ftpYieldCurve1.setA3(a3[i]);
			ftpYieldCurve1.setB2(b2[i]);
			// ���ζ���ʽ+���Ҷ˵�ֱ�߷���
			// k = (y2-y1)/(x2-x1)
			// b = y1-kx1
			double k1 = (VOF_adjust[i + 1] - VOF_adjust[i])
					/ (key[i + 1] - key[i]);
			double b1 = VOF_adjust[i] - k1 * key[i];
			// ��������������ֵʱ���ζ���ʽ���������ƹ�Ԫ�����Ե�����ֱ��Ҳ��Ҫ�������ƣ���+k1*key[i]
			b1 = b1 + k1 * key[i];// ��Ԫ
			ftpYieldCurve1.setC1(c1[i] + k1);
			ftpYieldCurve1.setD0(d0[i] + b1);
			ftpYieldCurve1.setXMin(key[i]);
			ftpYieldCurve1.setXMax(key[i + 1]);
			ftpYieldCurve1.setCurveMarketType(curveType);
			ftpYieldCurve1.setCurveAssetsType("02");
			ftpYieldCurve1.setCurveDate(date);
			ftpYieldCurve1.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve1);
			// ����
			FtpYieldCurve ftpYieldCurve2 = new FtpYieldCurve();
			ftpYieldCurve2.setCurveNo(curveType + "01");
			ftpYieldCurve2.setCurveName("�ڲ�����������-����COF");
			ftpYieldCurve2.setBrNo(brNo);
			ftpYieldCurve2.setSectionNum(i + 1);
			ftpYieldCurve2.setA3(a3[i]);
			ftpYieldCurve2.setB2(b2[i]);
			double k2 = (COF_adjust[i + 1] - COF_adjust[i])
					/ (key[i + 1] - key[i]);
			double d02 = COF_adjust[i] - k2 * key[i];
			d02 = d02 + k2 * key[i];// ��Ԫ
			ftpYieldCurve2.setC1(c1[i] + k2);
			ftpYieldCurve2.setD0(d0[i] + d02);
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
	 * ������Ե���
	 * @param curveType
	 * @param date
	 * @param brNo
	 * @param adjustKeyC
	 * @param adjustRateC
	 */
	public void saveAdjust(String curveType, String date, String brNo, String adjustKeyC, String adjustRateC) {
		//��ɾ�������
		String delSql = "delete from FtpYieldCurveAdjust where brNo = '"+brNo+"' " +
				"and curveMarketType = '0"+curveType+"' and adjustDate = '"+date+"'";
		daoFactory.delete(delSql, null);
		String[] adjustKey = adjustKeyC.split(",");
		String[] adjustRate = adjustRateC.split(",");
		for(int i = 0; i < adjustKey.length; i++) {
			FtpYieldCurveAdjust ftpYieldCurveAdjust = new FtpYieldCurveAdjust();
			ftpYieldCurveAdjust.setBrNo(brNo);
			ftpYieldCurveAdjust.setCurveMarketType("0"+curveType);
			ftpYieldCurveAdjust.setCurveAssetsType(adjustKey[i].substring(0,1).equals("c") ? "02" : "01");
			ftpYieldCurveAdjust.setTermType(adjustKey[i].substring(1, adjustKey[i].length()));
			ftpYieldCurveAdjust.setAdjustValue(Double.valueOf(adjustRate[i]));
			ftpYieldCurveAdjust.setAdjustDate(date);
			ftpYieldCurveAdjust.setAdjustId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurveAdjust);
		}
	}
	
	/**
	 * �ڲ�����������--�Է���-���浽FtpYieldCurveSfb��
	 * @param curveType
	 * @param date
	 * @param brNo
	 * @param key
	 * @param a3
	 * @param b2
	 * @param c1
	 * @param d0
	 * @param COF_adjust
	 * @param VOF_adjust
	 */
	public void internalCurveSfb(String curveType, String date, String brNo, double[] key, double[] a3, double[] b2, double[] c1, double[] d0, double[] COF_adjust, double[] VOF_adjust) {
		//��ÿһ�ε����ζ���ʽ����ѭ�����,����������ߣ���׼��00����׼�ߵ���ֵΪ0.0�������01������02
		for (int i = 0; i < a3.length; i++) {
			//��׼��
			FtpYieldCurveSfb ftpYieldCurve0 = new FtpYieldCurveSfb();
			ftpYieldCurve0.setCurveNo(curveType+"00");
			ftpYieldCurve0.setCurveName("�ڲ�����������-��׼������");
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
			ftpYieldCurve1.setCurveName("�ڲ�����������-���VOF");
			ftpYieldCurve1.setBrNo(brNo);
			ftpYieldCurve1.setSectionNum(i + 1);
			ftpYieldCurve1.setA3(a3[i]);
			ftpYieldCurve1.setB2(b2[i]);
			// ���ζ���ʽ+���Ҷ˵�ֱ�߷���
			// k = (y2-y1)/(x2-x1)
			// b = y1-kx1
			double k1 = (VOF_adjust[i + 1] - VOF_adjust[i])
					/ (key[i + 1] - key[i]);
			double b1 = VOF_adjust[i] - k1 * key[i];
			// ��������������ֵʱ���ζ���ʽ���������ƹ�Ԫ�����Ե�����ֱ��Ҳ��Ҫ�������ƣ���+k1*key[i]
			b1 = b1 + k1 * key[i];// ��Ԫ
			ftpYieldCurve1.setC1(c1[i] + k1);
			ftpYieldCurve1.setD0(d0[i] + b1);
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
			ftpYieldCurve2.setCurveName("�ڲ�����������-����COF");
			ftpYieldCurve2.setBrNo(brNo);
			ftpYieldCurve2.setSectionNum(i + 1);
			ftpYieldCurve2.setA3(a3[i]);
			ftpYieldCurve2.setB2(b2[i]);
			double k2 = (COF_adjust[i + 1] - COF_adjust[i])
					/ (key[i + 1] - key[i]);
			double d02 = COF_adjust[i] - k2 * key[i];
			d02 = d02 + k2 * key[i];// ��Ԫ
			ftpYieldCurve2.setC1(c1[i] + k2);
			ftpYieldCurve2.setD0(d0[i] + d02);
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
	 * @param a3_1
	 * @param b2_1
	 * @param c1_1
	 * @param d0_1
	 * @param a3_2
	 * @param b2_2
	 * @param c1_2
	 * @param d0_2
	 * @param COF_adjust
	 * @param VOF_adjust
	 */
	public void marketCurveSave(String curveType, String date, String brNo,
			double[] key, double[] a3_1, double[] b2_1, double[] c1_1,
			double[] d0_1, double[] a3_2, double[] b2_2, double[] c1_2,
			double[] d0_2, double[] COF_adjust, double[] VOF_adjust) {
		// ��ÿһ�ε����ζ���ʽ����ѭ�����,����������ߣ���׼��00����׼�ߵ���ֵΪ0.0�������01������02
		for (int i = 0; i < a3_1.length; i++) {
			// ��׼��
			FtpYieldCurve ftpYieldCurve0 = new FtpYieldCurve();
			ftpYieldCurve0.setCurveNo(curveType + "00" + "_01");
			ftpYieldCurve0.setCurveName("�г�����������-��׼������(1��������)");
			ftpYieldCurve0.setBrNo(brNo);
			ftpYieldCurve0.setSectionNum(i + 1);
			ftpYieldCurve0.setA3(a3_1[i]);
			ftpYieldCurve0.setB2(b2_1[i]);
			ftpYieldCurve0.setC1(c1_1[i]);
			ftpYieldCurve0.setD0(d0_1[i]);
			ftpYieldCurve0.setXMin(key[i]);
			ftpYieldCurve0.setXMax(key[i + 1]);
			ftpYieldCurve0.setCurveMarketType(curveType);
			ftpYieldCurve0.setCurveAssetsType("00");
			ftpYieldCurve0.setCurveDate(date);
			ftpYieldCurve0.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve0);
			// ��ծ
			FtpYieldCurve ftpYieldCurve1 = new FtpYieldCurve();
			ftpYieldCurve1.setCurveNo(curveType + "02" + "_01");
			ftpYieldCurve1.setCurveName("�г�����������-��ծVOF(1��������)");
			ftpYieldCurve1.setBrNo(brNo);
			ftpYieldCurve1.setSectionNum(i + 1);
			ftpYieldCurve1.setA3(a3_1[i]);
			ftpYieldCurve1.setB2(b2_1[i]);

			double k1 = (VOF_adjust[i + 1] - VOF_adjust[i])
					/ (key[i + 1] - key[i]);
			double b1 = VOF_adjust[i] - k1 * key[i];
			b1 = b1 + k1 * key[i];// ��Ԫ
			ftpYieldCurve1.setC1(c1_1[i] + k1);
			ftpYieldCurve1.setD0(d0_1[i] + b1);
			ftpYieldCurve1.setXMin(key[i]);
			ftpYieldCurve1.setXMax(key[i + 1]);
			ftpYieldCurve1.setCurveMarketType(curveType);
			ftpYieldCurve1.setCurveAssetsType("02");
			ftpYieldCurve1.setCurveDate(date);
			ftpYieldCurve1.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve1);
			// �ʲ�
			FtpYieldCurve ftpYieldCurve2 = new FtpYieldCurve();
			ftpYieldCurve2.setCurveNo(curveType + "01" + "_01");
			ftpYieldCurve2.setCurveName("�г�����������-�ʲ�COF(1��������)");
			ftpYieldCurve2.setBrNo(brNo);
			ftpYieldCurve2.setSectionNum(i + 1);
			ftpYieldCurve2.setA3(a3_1[i]);
			ftpYieldCurve2.setB2(b2_1[i]);
			double k2 = (COF_adjust[i + 1] - COF_adjust[i])
					/ (key[i + 1] - key[i]);
			double b2 = COF_adjust[i] - k2 * key[i];
			b2 = b2 + k2 * key[i];// ��Ԫ
			ftpYieldCurve2.setC1(c1_1[i] + k2);
			ftpYieldCurve2.setD0(d0_1[i] + b2);
			ftpYieldCurve2.setXMin(key[i]);
			ftpYieldCurve2.setXMax(key[i + 1]);
			ftpYieldCurve2.setCurveMarketType(curveType);
			ftpYieldCurve2.setCurveAssetsType("01");
			ftpYieldCurve2.setCurveDate(date);
			ftpYieldCurve2.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve2);
		}
		for (int i = 0; i < a3_2.length; i++) {
			// ��׼��
			FtpYieldCurve ftpYieldCurve3 = new FtpYieldCurve();
			ftpYieldCurve3.setCurveNo(curveType + "00" + "_02");
			ftpYieldCurve3.setCurveName("�г�����������-��׼������(1��������)");
			ftpYieldCurve3.setBrNo(brNo);
			ftpYieldCurve3.setSectionNum(i + 1);
			ftpYieldCurve3.setA3(a3_2[i]);
			ftpYieldCurve3.setB2(b2_2[i]);
			ftpYieldCurve3.setC1(c1_2[i]);
			ftpYieldCurve3.setD0(d0_2[i]);
			ftpYieldCurve3.setXMin(key[a3_1.length + 1 + i]);
			ftpYieldCurve3.setXMax(key[a3_1.length + 1 + i + 1]);
			ftpYieldCurve3.setCurveMarketType(curveType);
			ftpYieldCurve3.setCurveAssetsType("00");
			ftpYieldCurve3.setCurveDate(date);
			ftpYieldCurve3.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve3);
			// ��ծ
			FtpYieldCurve ftpYieldCurve4 = new FtpYieldCurve();
			ftpYieldCurve4.setCurveNo(curveType + "02" + "_02");
			ftpYieldCurve4.setCurveName("�г�����������-��ծVOF(1��������)");
			ftpYieldCurve4.setBrNo(brNo);
			ftpYieldCurve4.setSectionNum(i + 1);
			ftpYieldCurve4.setA3(a3_2[i]);
			ftpYieldCurve4.setB2(b2_2[i]);
			double k1 = (VOF_adjust[a3_1.length + 1 + i + 1] - VOF_adjust[a3_1.length
					+ 1 + i])
					/ (key[a3_1.length + 1 + i + 1] - key[a3_1.length + 1 + i]);
			double b1 = VOF_adjust[a3_1.length + 1 + i] - k1
					* key[a3_1.length + 1 + i];
			b1 = b1 + k1 * key[a3_1.length + 1 + i];// ��Ԫ
			ftpYieldCurve4.setC1(c1_2[i] + k1);
			ftpYieldCurve4.setD0(d0_2[i] + b1);
			ftpYieldCurve4.setXMin(key[a3_1.length + 1 + i]);
			ftpYieldCurve4.setXMax(key[a3_1.length + 1 + i + 1]);
			ftpYieldCurve4.setCurveMarketType(curveType);
			ftpYieldCurve4.setCurveAssetsType("02");
			ftpYieldCurve4.setCurveDate(date);
			ftpYieldCurve4.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve4);
			// �ʲ�
			FtpYieldCurve ftpYieldCurve5 = new FtpYieldCurve();
			ftpYieldCurve5.setCurveNo(curveType + "01" + "_02");
			ftpYieldCurve5.setCurveName("�г�����������-�ʲ�COF(1��������)");
			ftpYieldCurve5.setBrNo(brNo);
			ftpYieldCurve5.setSectionNum(i + 1);
			ftpYieldCurve5.setA3(a3_2[i]);
			ftpYieldCurve5.setB2(b2_2[i]);
			double k2 = (COF_adjust[a3_1.length + 1 + i + 1] - COF_adjust[a3_1.length
					+ 1 + i])
					/ (key[a3_1.length + 1 + i + 1] - key[a3_1.length + 1 + i]);
			double b2 = COF_adjust[a3_1.length + 1 + i] - k2
					* key[a3_1.length + 1 + i];
			b2 = b2 + k2 * key[a3_1.length + 1 + i];// ��Ԫ
			ftpYieldCurve5.setC1(c1_2[i] + k2);
			ftpYieldCurve5.setD0(d0_2[i] + b2);
			ftpYieldCurve5.setXMin(key[a3_1.length + 1 + i]);
			ftpYieldCurve5.setXMax(key[a3_1.length + 1 + i + 1]);
			ftpYieldCurve5.setCurveMarketType(curveType);
			ftpYieldCurve5.setCurveAssetsType("01");
			ftpYieldCurve5.setCurveDate(date);
			ftpYieldCurve5.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve5);
		}
	}

	/**
	 * �г�����������--�Է���-���浽FtpYieldCurveSfb
	 * @param curveType
	 * @param date
	 * @param brNo
	 * @param key
	 * @param a3_1
	 * @param b2_1
	 * @param c1_1
	 * @param d0_1
	 * @param a3_2
	 * @param b2_2
	 * @param c1_2
	 * @param d0_2
	 * @param COF_adjust
	 * @param VOF_adjust
	 */
	public void marketCurveSfb(String curveType, String date, String brNo, double[] key, double[] a3_1, double[] b2_1, double[] c1_1, double[] d0_1,
			double[] a3_2, double[] b2_2, double[] c1_2, double[] d0_2, double[] COF_adjust, double[] VOF_adjust) {
		//��ÿһ�ε����ζ���ʽ����ѭ�����,����������ߣ���׼��00����׼�ߵ���ֵΪ0.0�������01������02
		for (int i = 0; i < a3_1.length; i++) {
			//��׼��
			FtpYieldCurveSfb ftpYieldCurve0 = new FtpYieldCurveSfb();
			ftpYieldCurve0.setCurveNo(curveType+"00"+"_01");
			ftpYieldCurve0.setCurveName("�г�����������-��׼������(1��������)");
			ftpYieldCurve0.setBrNo(brNo);
			ftpYieldCurve0.setSectionNum(i + 1);
			ftpYieldCurve0.setA3(a3_1[i]);
			ftpYieldCurve0.setB2(b2_1[i]);
			ftpYieldCurve0.setC1(c1_1[i]);
			ftpYieldCurve0.setD0(d0_1[i]);
			ftpYieldCurve0.setXMin(key[i]);
			ftpYieldCurve0.setXMax(key[i + 1]);
			ftpYieldCurve0.setCurveMarketType(curveType);
			ftpYieldCurve0.setCurveAssetsType("00");
			ftpYieldCurve0.setCurveDate(date);
			ftpYieldCurve0.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve0);
			//��ծ
			FtpYieldCurveSfb ftpYieldCurve1 = new FtpYieldCurveSfb();
			ftpYieldCurve1.setCurveNo(curveType+"02"+"_01");
			ftpYieldCurve1.setCurveName("�г�����������-��ծVOF(1��������)");
			ftpYieldCurve1.setBrNo(brNo);
			ftpYieldCurve1.setSectionNum(i + 1);
			ftpYieldCurve1.setA3(a3_1[i]);
			ftpYieldCurve1.setB2(b2_1[i]);

			double k1 = (VOF_adjust[i + 1] - VOF_adjust[i])
					/ (key[i + 1] - key[i]);
			double b1 = VOF_adjust[i] - k1 * key[i];
			b1 = b1 + k1 * key[i];// ��Ԫ
			ftpYieldCurve1.setC1(c1_1[i] + k1);
			ftpYieldCurve1.setD0(d0_1[i] + b1);
			ftpYieldCurve1.setXMin(key[i]);
			ftpYieldCurve1.setXMax(key[i + 1]);
			ftpYieldCurve1.setCurveMarketType(curveType);
			ftpYieldCurve1.setCurveAssetsType("02");
			ftpYieldCurve1.setCurveDate(date);
			ftpYieldCurve1.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve1);
			//�ʲ�
			FtpYieldCurveSfb ftpYieldCurve2 = new FtpYieldCurveSfb();
			ftpYieldCurve2.setCurveNo(curveType+"01"+"_01");
			ftpYieldCurve2.setCurveName("�г�����������-�ʲ�COF(1��������)");
			ftpYieldCurve2.setBrNo(brNo);
			ftpYieldCurve2.setSectionNum(i + 1);
			ftpYieldCurve2.setA3(a3_1[i]);
			ftpYieldCurve2.setB2(b2_1[i]);
			double k2 = (COF_adjust[i + 1] - COF_adjust[i])
					/ (key[i + 1] - key[i]);
			double b2 = COF_adjust[i] - k2 * key[i];
			b2 = b2 + k2 * key[i];// ��Ԫ
			ftpYieldCurve2.setC1(c1_1[i] + k2);
			ftpYieldCurve2.setD0(d0_1[i] + b2);
			ftpYieldCurve2.setXMin(key[i]);
			ftpYieldCurve2.setXMax(key[i + 1]);
			ftpYieldCurve2.setCurveMarketType(curveType);
			ftpYieldCurve2.setCurveAssetsType("01");
			ftpYieldCurve2.setCurveDate(date);
			ftpYieldCurve2.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve2);
		}
		for (int i = 0; i < a3_2.length; i++) {
			//��׼��
			FtpYieldCurveSfb ftpYieldCurve3 = new FtpYieldCurveSfb();
			ftpYieldCurve3.setCurveNo(curveType+"00"+"_02");
			ftpYieldCurve3.setCurveName("�г�����������-��׼������(1��������)");
			ftpYieldCurve3.setBrNo(brNo);
			ftpYieldCurve3.setSectionNum(i + 1);
			ftpYieldCurve3.setA3(a3_2[i]);
			ftpYieldCurve3.setB2(b2_2[i]);
			ftpYieldCurve3.setC1(c1_2[i]);
			ftpYieldCurve3.setD0(d0_2[i]);
			ftpYieldCurve3.setXMin(key[a3_1.length + 1 + i]);
			ftpYieldCurve3.setXMax(key[a3_1.length + 1 + i + 1]);
			ftpYieldCurve3.setCurveMarketType(curveType);
			ftpYieldCurve3.setCurveAssetsType("00");
			ftpYieldCurve3.setCurveDate(date);
			ftpYieldCurve3.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve3);
			//��ծ
			FtpYieldCurveSfb ftpYieldCurve4 = new FtpYieldCurveSfb();
			ftpYieldCurve4.setCurveNo(curveType+"02"+"_02");
			ftpYieldCurve4.setCurveName("�г�����������-��ծVOF(1��������)");
			ftpYieldCurve4.setBrNo(brNo);
			ftpYieldCurve4.setSectionNum(i + 1);
			ftpYieldCurve4.setA3(a3_2[i]);
			ftpYieldCurve4.setB2(b2_2[i]);
			double k1 = (VOF_adjust[a3_1.length + 1 + i + 1] - VOF_adjust[a3_1.length
					+ 1 + i])
					/ (key[a3_1.length + 1 + i + 1] - key[a3_1.length + 1 + i]);
			double b1 = VOF_adjust[a3_1.length + 1 + i] - k1
					* key[a3_1.length + 1 + i];
			b1 = b1 + k1 * key[a3_1.length + 1 + i];// ��Ԫ
			ftpYieldCurve4.setC1(c1_2[i] + k1);
			ftpYieldCurve4.setD0(d0_2[i] + b1);
			ftpYieldCurve4.setXMin(key[a3_1.length + 1 + i]);
			ftpYieldCurve4.setXMax(key[a3_1.length + 1 + i + 1]);
			ftpYieldCurve4.setCurveMarketType(curveType);
			ftpYieldCurve4.setCurveAssetsType("02");
			ftpYieldCurve4.setCurveDate(date);
			ftpYieldCurve4.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve4);
			//�ʲ�
			FtpYieldCurveSfb ftpYieldCurve5 = new FtpYieldCurveSfb();
			ftpYieldCurve5.setCurveNo(curveType+"01"+"_02");
			ftpYieldCurve5.setCurveName("�г�����������-�ʲ�COF(1��������)");
			ftpYieldCurve5.setBrNo(brNo);
			ftpYieldCurve5.setSectionNum(i + 1);
			ftpYieldCurve5.setA3(a3_2[i]);
			ftpYieldCurve5.setB2(b2_2[i]);
			double k2 = (COF_adjust[a3_1.length + 1 + i + 1] - COF_adjust[a3_1.length
					+ 1 + i])
					/ (key[a3_1.length + 1 + i + 1] - key[a3_1.length + 1 + i]);
			double b2 = COF_adjust[a3_1.length + 1 + i] - k2
					* key[a3_1.length + 1 + i];
			b2 = b2 + k2 * key[a3_1.length + 1 + i];// ��Ԫ
			ftpYieldCurve5.setC1(c1_2[i] + k2);
			ftpYieldCurve5.setD0(d0_2[i] + b2);
			ftpYieldCurve5.setXMin(key[a3_1.length + 1 + i]);
			ftpYieldCurve5.setXMax(key[a3_1.length + 1 + i + 1]);
			ftpYieldCurve5.setCurveMarketType(curveType);
			ftpYieldCurve5.setCurveAssetsType("01");
			ftpYieldCurve5.setCurveDate(date);
			ftpYieldCurve5.setCurveId(IDUtil.getInstanse().getUID());
			daoFactory.insert(ftpYieldCurve5);
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
		String[] sheetName1 = { "��׼��", "�ʲ�", "��ծ" };
		String[] sheetName2 = { "��׼��", "����COF", "���VOF" };
		if(curveType.equals("1")) {//�ڲ�
			sheetName = sheetName2;
		}else {
			sheetName = sheetName1;
		}

		for (int i = 0; i < sheetName1.length; i++) {
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
						+ (curveType.equals("1") ? "�ڲ�" : "�г�") + "����������-"
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
			String listInfo = curveInfos[2].equals("�ڲ�")?list_curveName.get(i):list_curveName.get(i).substring(0,list_curveName.get(i).length()-7);
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
				// ������е�����
				String year = list_curveName.get(i).substring(0, 4);
				Integer dayNum = DateUtil.isLeapYear(Integer.valueOf(year)) == true ? 366 : 365;// �������ܵ�������
				SCYTCZlineF f = list_F.get(i);
				
				if(curveInfos[2].equals("�ڲ�")) {
					for (int j = 0; j < dayNum; j++) {
						HSSFRow rowData = sheet.createRow(j + 2);
						HSSFCell cellData1 = rowData.createCell(0);
						cellData1.setCellValue(new HSSFRichTextString(j == dayNum - 1 ? "1��" : (j + 1) + "��"));
						HSSFCell cellData2 = rowData.createCell(1);
						cellData2.setCellValue(new HSSFRichTextString(String.valueOf(CommonFunctions.doublecut(f.getY_SCYTCZline((j + 1) / 30.0)*100, 4))));
					}
					int m = 13;
					for (int j = dayNum; j < dayNum + 348; j++) {
						HSSFRow rowData = sheet.createRow(j + 2);
						HSSFCell cellData1 = rowData.createCell(0);
						cellData1.setCellValue(new HSSFRichTextString(m % 12 == 0 ? m / 12 + "��" : m + "��"));
						HSSFCell cellData2 = rowData.createCell(1);
						cellData2.setCellValue(new HSSFRichTextString(String.valueOf(CommonFunctions.doublecut(f.getY_SCYTCZline(m)*100, 4))));
						m++;
					}
				}else {
					for (int j = 0; j < dayNum; j++) {
						HSSFRow rowData = sheet.createRow(j + 2);
						HSSFCell cellData1 = rowData.createCell(0);
						cellData1.setCellValue(new HSSFRichTextString(j == dayNum - 1 ? "365��(ǰ���)" : (j + 1) + "��"));
						HSSFCell cellData2 = rowData.createCell(1);
						cellData2.setCellValue(new HSSFRichTextString(String.valueOf(CommonFunctions.doublecut(f.getY_SCYTCZline((j + 1) / 30.0)*100, 4))));
					}
					//���г�-**(1��������)���г�-**(1��������)�ϲ���һ��sheet��
					SCYTCZlineF f2 = list_F.get(i+1);
					HSSFRow rowData = sheet.createRow(dayNum + 2);
					HSSFCell cellData1 = rowData.createCell(0);
					cellData1.setCellValue(new HSSFRichTextString("1��(����)"));
					HSSFCell cellData2 = rowData.createCell(1);
					cellData2.setCellValue(new HSSFRichTextString(String.valueOf(CommonFunctions.doublecut(f2.getY_SCYTCZline(12)*100, 4))));
					int m = 13;
					for (int j = dayNum + 1; j < dayNum + 1 + 348; j++) {
						HSSFRow rowData1 = sheet.createRow(j + 2);
						HSSFCell cellData_1 = rowData1.createCell(0);
						cellData_1.setCellValue(new HSSFRichTextString(m % 12 == 0 ? m / 12 + "��" : m + "��"));
						HSSFCell cellData_2 = rowData1.createCell(1);
						cellData_2.setCellValue(new HSSFRichTextString(String.valueOf(CommonFunctions.doublecut(f2.getY_SCYTCZline(m)*100, 4))));
						m++;
					}
					i++;
				}
			}

			catch (Exception e) {
				e.printStackTrace();
			}
		}
       
		return workbook;
		}
public String formartPageLine(int pageSize, int currentPage, int rowsCount,String pageName){
		
		pageSize=pageSize<1?12:pageSize;
		currentPage=currentPage<1?1:currentPage;
		StringBuffer buffer=new StringBuffer();
		int pageCount=rowsCount/pageSize;
		pageCount=pageCount<1?1:pageCount;
		pageCount=pageCount*pageSize<rowsCount?pageCount+1:pageCount;
		currentPage=currentPage>pageCount?pageCount:currentPage;
		
		
		if(currentPage==1){
			buffer.append("��ҳ&nbsp;");
			buffer.append("��һҳ&nbsp;");
		}
		else{
			buffer.append("<a href=\""+ pageName +"&page=1&rowsCount="+ rowsCount +"\">��ҳ</a>&nbsp;");
			buffer.append("<a href=\""+ pageName +"&page="+ (currentPage-1) +"&rowsCount="+ rowsCount +"\">��һҳ</a>&nbsp;");
		}
		
		if(currentPage==pageCount){
			buffer.append("��һҳ&nbsp;");
			buffer.append("ĩҳ");
		}
		else{
			buffer.append("<a href=\""+ pageName +"&page="+ (currentPage+1) +"&rowsCount="+ rowsCount +"\">��һҳ</a>&nbsp;");
			buffer.append("<a href=\""+ pageName +"&page="+ pageCount +"&rowsCount="+ rowsCount +"\">ĩҳ</a>&nbsp;");
		}
		buffer.append("&nbsp;&nbsp;��������"+ rowsCount +"�����ݣ�ÿҳ"+ pageSize +"�����ݣ�ҳ��<font color='red'>"+ currentPage+"</font>/"+ pageCount);
		
		//buffer.setLength(0);
		buffer.append("&nbsp;&nbsp;��ת����");
		buffer.append("\r\n<select onchange=\"window.location.replace('"+ pageName + "&page='+this.value+'&rowsCount="+ rowsCount +"')\">\r\n");
		for(int i=0;i<pageCount;i++){
			String selected="";
			if(i==currentPage-1){
				selected="selected";
			}
			buffer.append("<option "+ selected +" value=\""+ (i+1) +"\">��"+ (i+1) +"ҳ</option>\r\n");
		}
		buffer.append("</select>");
		
		
		return buffer.toString();
	}
}
