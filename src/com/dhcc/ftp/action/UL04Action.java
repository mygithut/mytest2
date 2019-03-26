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
 * Title: ����������
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Company: ��������ɷݹ�˾������ҵ��
 * </p>
 * 
 * @author �����
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
	 * ��������
	 */
	public String calculate() throws Exception {
		Map<String, double[]> curveMap = uL04BO.calculateCurve(curveType, date);
		if(curveMap.get("shiborError") != null){
			String errorMessage="������"+CommonFunctions.dateModifyD(date, -1)+"��Shibor�������ݣ��޷��������������ߣ����ȵ������ݹ���ά��";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("shiborAveError") != null){
			String errorMessage="�����һ���µ�Shibor�������ݣ��޷��������������ߣ����ȵ������ݹ���ά��";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("stockError") != null){
			String errorMessage="������"+CommonFunctions.dateModifyD(date, -1)+"�����м��ծ���������ݣ��޷��������������ߣ����ȵ������ݹ���ά��";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("stockAveError") != null){
			String errorMessage="�����һ���µ����м��ծ���������ݣ��޷��������������ߣ����ȵ������ݹ���ά��";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("commonError") != null){
			String errorMessage="������"+CommonFunctions.dateModifyD(date, -1)+"�ı�����ͨծ���������ݣ��޷��������������ߣ����ȵ������ݹ���ά��";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("commonAveError") != null){
			String errorMessage="�����һ���µı�����ͨծ���������ݣ��޷��������������ߣ����ȵ������ݹ���ά��";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("yieldError") != null){
			String errorMessage="������"+CommonFunctions.dateModifyD(date, -1)+"�������Խ���ծ���������ݣ��޷��������������ߣ����ȵ������ݹ���ά��";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("yieldAveError") != null){
			String errorMessage="�����һ���µ������Խ���ծ���������ݣ��޷��������������ߣ����ȵ������ݹ���ά��";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("wbCurveError") != null){
			String errorMessage="��������ʹ������ߣ��޷��������������ߣ����ȵ������ݹ���ά��";
			request.setAttribute("errorMessage", errorMessage);
			return "curve_error";
		}
		if(curveMap.get("tycdError") != null){
			String errorMessage="��ͬҵ�浥���ݹ������ߣ��޷��������������ߣ����ȵ������ݹ���ά��";
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
	//�ֶα�������������
	public String save() throws Exception {
		String type = curveType;
		if (type.length() == 1) type = "0"+type;

		if (curveType.equals("1")) {//���������������
			//�ȼ�����ݿ����Ƿ��е��죬�г�������ͬ�����ݣ����������ɾ���������
			uL04BO.checkSaveAddDel(type, date, brNo, "FtpYieldCurve");
			uL04BO.internalCurveSave(type, date, brNo, key, a3, b2, c1, d0, a3_1, b2_1, c1_1, d0_1, a3_2, b2_2, c1_2, d0_2);
		}else if(curveType.equals("2")){//�г�����������
			//���浱�������������
			uL04BO.marketCurveSave(type,"00", date, brNo, key, a3, b2, c1, d0);
			if(a3_ave != null) {
				uL04BO.marketCurveSave(type,"01", date, brNo, key, a3_ave, b2_ave, c1_ave, d0_ave);
			}
		}else if(curveType.equals("3")){//����Ϣ����������
			uL04BO.fsxzcCurveSave(type, date, brNo, key, a3, b2, c1, d0);
		}else if((curveType.equals("4"))){//ͬҵ����������
			uL04BO.shiborCurveSave(type, date, brNo, key, a3, b2, c1, d0);
		}
//		uL04BO.saveAdjust(curveType, date, brNo, adjustKeyC, adjustRateC);
		return null;
	}


	//�Է���--���浽ftp_yield_curve_sfb����-�ֶα�������������
	public String sfb() throws Exception {
		String type = curveType;
		if (type.length() == 1) type = "0"+type;

		if (curveType.equals("1")) {//���������������
			//�ȼ�����ݿ����Ƿ��е��죬�г�������ͬ�����ݣ����������ɾ���������
			uL04BO.checkSaveAddDel(type, date, brNo, "FtpYieldCurveSfb");
			uL04BO.internalCurveSfb(type, date, brNo, key, a3, b2, c1, d0, a3_1, b2_1, c1_1, d0_1, a3_2, b2_2, c1_2, d0_2);
		}else {//�г����������ߣ���Ҫ����һ�������ڵ����������ߣ�10��
			Map curveMap = uL04BO.generateMarketCurve(date, brNo, curveType, key, a3, b2, c1, d0);
			if(curveMap.get("repoError") != null){
	 			String errorMessage="����"+date+"���10�������յ���Ѻʽ�ع��������ݲ�ȫ���޷��������������ߣ����ȵ������ݹ�������";
				request.setAttribute("errorMessage", errorMessage);
				return "curve_error";
			}
			if(curveMap.get("billsError") != null){
	 			String errorMessage="����"+date+"���10�������յ�����Ʊ���������ݲ�ȫ���޷��������������ߣ����ȵ������ݹ�������";
				request.setAttribute("errorMessage", errorMessage);
				return "curve_error";
			}
	 		if(curveMap.get("yieldMap") != null){
	 			String errorMessage="����"+date+"���10�������յ������Խ���ծ���������ݲ�ȫ���޷��������������ߣ����ȵ������ݹ�������";
				request.setAttribute("errorMessage", errorMessage);
				return "curve_error";
			}
	 		if(curveMap.get("ofRateSpreadMap") != null){
	 			String errorMessage="�����30�������յ���ͨծ�ͽ���ծ�����ʵ�����ݣ��޷��������������ߣ����ȵ������ݹ�������";
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
	        	//��������������
		 	    System.out.println("------------��ʼ����"+newDateList.get(i)+"�������������--------");
		 	    //�ȼ�����ݿ����Ƿ��е��죬�г�������ͬ�����ݣ����������ɾ���������
				uL04BO.checkSaveAddDel(type, newDateList.get(i), brNo, "FtpYieldCurveSfb");
		 		uL04BO.marketCurveSfb(type, newDateList.get(i), brNo, keyList.get(i), a3List.get(i),
		 				b2List.get(i), c1List.get(i), d0List.get(i));
	        }

		}
		return null;
	}

	//�鿴��ʷ�����������б�
	public String history_list() throws Exception {
		TelMst telMst = (TelMst)request.getSession().getAttribute("userBean");
		UL04Util = uL04BO.dofind(brNo, curveMarketType, curveAssetsType, curveDate, telMst, page, rowsCount);
		request.setAttribute("UL04Util", UL04Util);
		return "history_list";
	}
	//����ѡ������߻�ȡ�ؼ���
	public String curve_history() throws Exception {
		String[] curveStr = curve.split(",");
		List<Double[]> list_curve = new ArrayList<Double[]>();
		List<String> list_curveName = new ArrayList<String>();
		List<String> list_curveType = new ArrayList<String>();
		List<SCYTCZlineF> list_F = new ArrayList<SCYTCZlineF>();
		for (int i = 0; i < curveStr.length; i++) {
			String[] curve2 = curveStr[i].split("\\|");//���ơ��г����͡��ʲ����͡����ڡ�����
			SCYTCZlineF F = FtpUtil.getSCYTCZlineF_fromDB(curve2[1]+""+curve2[2], curve2[3], curve2[4]);
			Double[] curve = new Double[29];
			//��������������ߵĴ���ߺͻ�׼�ߣ���ʾ"����"
			if((curve2[1].equals("01")&&(curve2[2].equals("00")||curve2[2].equals("02"))) || curve2[1].equals("06")) {
				curve[0] = F.getY_SCYTCZline(0);
			}
			curve[1] = F.getY_SCYTCZline(1/30.0);
			curve[2] = F.getY_SCYTCZline(7/30.0);
			curve[3] = F.getY_SCYTCZline(14/30.0);
			curve[4] = F.getY_SCYTCZline(21/30.0);
			for (int j = 1; j < 12; j++) {
				curve[j+4] = F.getY_SCYTCZline(j);//ÿ��
			}
			for (int k = 1; k < 11; k++) {//1Y��10Y
				curve[k+15] = F.getY_SCYTCZline(k*12);
			}
			curve[26] = F.getY_SCYTCZline(15*12);//15Y
			curve[27] = F.getY_SCYTCZline(20*12);//20Y
			curve[28] = F.getY_SCYTCZline(30*12);//30Y
			list_curve.add(curve);
//			list_curveName.add(curve2[3]+"-"+brInfoBo.getInfo(curve2[4]).getBrName()+"-"+(curve2[1].equals("01")? "�����":"�г�")+"-"+FtpUtil.getAssetsType(curve2[1],curve2[2]));
			//list_curveName.add(curve2[3]+"-"+(curve2[1].equals("01")? "�����":(curve2[1].equals("02")? "�г�":"����Ϣ�ʲ�"))+"-"+FtpUtil.getAssetsType(curve2[1],curve2[2]));
			list_curveName.add(curve2[3]+"-"+(curve2[1].equals("01")? "�����":(curve2[1].equals("02")? (curve2[2].equals("00")? "�г�":"�г�-ƽ����"):(curve2[1].equals("03")?"����Ϣ�ʲ�":(curve2[1].equals("04")?"ͬҵ":(curve2[1].equals("05")?"shibor":"ͬҵ�浥"))))));
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
	 * �������ɵ�����������EXCEL
	 */
	public String curveExport() {
		SCYTCZlineF f00 = null;// ��׼
		SCYTCZlineF f01 = null;// ����
//		SCYTCZlineF f02 = null;// ���


		double[][] A00 = null;
		double[][] A01 = null;
//		double[][] A02 = null;
		double[] x = null;
		String[] curveTerm = null;
		double[][] curveValue = null;

		String year = date.substring(0, 4);
		//Integer dayNum = 360;//DateUtil.isLeapYear(Integer.valueOf(year)) == true ? 366 : 365;// �������ܵ�������
		Integer dayNum = DateUtil.isLeapYear(Integer.valueOf(year)) == true ? 366 : 365;// �������ܵ�������

		A00 = new double[a3.length][4];
		A01 = new double[a3.length][4];
//		A02 = new double[a3.length][4];
		x = new double[a3.length + 1];
		if(curveType.equals("1")) {//���������������
			for (int i = 0; i < a3.length; i++) {
				// ��׼
				A00[i][0] = a3[i];
				A00[i][1] = b2[i];
				A00[i][2] = c1[i];
				A00[i][3] = d0[i];

				// ����
//				A01[i][0] = a3_1[i];
//				A01[i][1] = b2_1[i];
//				A01[i][2] = c1_1[i];
//				A01[i][3] = d0_1[i];

				// ���
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

			curveTerm = new String[dayNum + 1 + 348];// ��׼�ߺʹ������ޣ�����˻���
			curveValue = new double[dayNum + 1 + 348][1];// 1������ֵ
			curveTerm[0] = "����";
			curveValue[0][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(0)*100, 4);
//			curveValue[0][1] = Double.NaN;
//			curveValue[0][2] = CommonFunctions.doublecut(f02.getY_SCYTCZline(0)*100, 4);
			for (int i = 0; i < dayNum; i++) {
				//curveTerm[i+1] = i == dayNum - 1 ? "1��" : (i + 1) + "��";
				curveTerm[i+1] = (i + 1) + "��";
				curveValue[i+1][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
//				curveValue[i+1][1] = CommonFunctions.doublecut(f01.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
//				curveValue[i+1][2] = CommonFunctions.doublecut(f02.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
			}
			int m = 13;
			for (int j = dayNum; j < dayNum + 348; j++) {
				curveTerm[j+1] = m % 12 == 0 ? m / 12 + "��" : m + "��";
				curveValue[j+1][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(m)*100, 4);
//				curveValue[j+1][1] = CommonFunctions.doublecut(f01.getY_SCYTCZline(m)*100, 4);
//				curveValue[j+1][2] = CommonFunctions.doublecut(f02.getY_SCYTCZline(m)*100, 4);
				m++;
			}
		}else if(curveType.equals("2")) {//�г�����������
			for (int i = 0; i < a3.length; i++) {
				//��׼��
				A00[i][0] = a3[i];
				A00[i][1] = b2[i];
				A00[i][2] = c1[i];
				A00[i][3] = d0[i];

				//ƽ����
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
				f01 = new SCYTCZlineF(A01, x);//ƽ����
				n = 2;
			}
			curveTerm = new String[dayNum + 348];// ����

			curveValue = new double[dayNum + 348][n];// 1������ֵ
			for (int i = 0; i < dayNum; i++) {
				//curveTerm[i] = i == dayNum - 1 ? "1��" : (i + 1) + "��";
				curveTerm[i] = (i + 1) + "��";
				curveValue[i][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
				if(a3_ave != null)curveValue[i][1] = CommonFunctions.doublecut(f01.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
			}
			int m = 13;
			for (int j = dayNum; j < dayNum + 348; j++) {
				curveTerm[j] = m % 12 == 0 ? m / 12 + "��" : m + "��";
				curveValue[j][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(m)*100, 4);
				if(a3_ave != null)curveValue[j][1] = CommonFunctions.doublecut(f01.getY_SCYTCZline(m)*100, 4);
				m++;
			}
		}else if(curveType.equals("3")) {//��ծ����������
			for (int i = 0; i < a3.length; i++) {
				// ��׼
				A00[i][0] = a3[i];
				A00[i][1] = b2[i];
				A00[i][2] = c1[i];
				A00[i][3] = d0[i];

				x[i] = key[i];
				x[i + 1] = key[i + 1];
			}
			f00 = new SCYTCZlineF(A00, x);
			curveTerm = new String[7 + 348];
			curveValue = new double[7 + 348][1];// 1������ֵ
			for (int i = 6; i < 12; i++) {//��6�¿�ʼ
				curveTerm[i-6] = i + "��";
				curveValue[i-6][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(i)*100, 4);
			}
			curveTerm[6] ="1��";
			curveValue[6][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(12)*100, 4);
			int m = 13;
			for (int j = 7; j < 7 + 348; j++) {
				curveTerm[j] = m % 12 == 0 ? m / 12 + "��" : m + "��";
				curveValue[j][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(m)*100, 4);
//				curveValue[j+1][1] = CommonFunctions.doublecut(f01.getY_SCYTCZline(m)*100, 4);
//				curveValue[j+1][2] = CommonFunctions.doublecut(f02.getY_SCYTCZline(m)*100, 4);
				m++;
			}
		}
		else if(curveType.equals("4")) {//�������������
			dayNum=360;//�����������ֻ�е�360��ģ��˴���������Ϊ360��
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
			curveTerm = new String[dayNum ];// ����

			curveValue = new double[dayNum ][n];// 1������ֵ
			for (int i = 0; i < dayNum; i++) {
				curveTerm[i] = (i + 1) + "��";
				curveValue[i][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
			}
			int m = 13;
			for (int j = dayNum; j < dayNum ; j++) {
				curveTerm[j] = m % 12 == 0 ? m / 12 + "��" : m + "��";
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
			curveTerm = new String[dayNum ];// ����

			curveValue = new double[dayNum ][n];// 1������ֵ
			for (int i = 0; i < dayNum; i++) {
				curveTerm[i] = (i + 1) + "��";
				curveValue[i][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
			}
			int m = 13;
			for (int j = dayNum; j < dayNum ; j++) {
				curveTerm[j] = m % 12 == 0 ? m / 12 + "��" : m + "��";
				curveValue[j][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(m)*100, 4);
				m++;
			}
		}else if(curveType.equals("6")) {//ͬҵ�浥����������
			for (int i = 0; i < a3.length; i++) {
				// ��׼
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

			curveTerm = new String[dayNum + 1 + 348];// ��׼�ߺʹ������ޣ�����˻���
			curveValue = new double[dayNum + 1 + 348][1];// 1������ֵ
			curveTerm[0] = "����";
			curveValue[0][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(0)*100, 4);
			for (int i = 0; i < dayNum; i++) {
				curveTerm[i+1] = (i + 1) + "��";
				curveValue[i+1][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
			}
			int m = 13;
			for (int j = dayNum; j < dayNum + 24; j++) {
				curveTerm[j+1] = m % 12 == 0 ? m / 12 + "��" : m + "��";
				curveValue[j+1][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(m)*100, 4);
				m++;
			}

		}

		HSSFWorkbook workbook = uL04BO.getWorkbook(curveTerm, curveValue, curveType, date, brNo);

		if (workbook != null) {
			//���EXCEL
			ExcelExport.workbookInputStream(response, workbook, date + "��-����"+brNo+"-"+(curveType.equals("1")?"�����":(curveType.equals("2")?"�г�":(curveType.equals("3")?"��ծ":(curveType.equals("4")?"���":(curveType.equals("5")?"shibor":"ͬҵ�浥")))))+"����������");
		}
		return null;
	}

	/**
	 * ������������ʷ��-����EXCEL
	 */
	public String curveHistoryExport() {

		List<String> list_curveName = (List<String>)request.getSession().getAttribute("list_curveName");
		List<SCYTCZlineF> list_F = (List<SCYTCZlineF>)request.getSession().getAttribute("list_F");
		HSSFWorkbook workbook = uL04BO.getHistoryWorkbook(list_curveName,list_F);

		if (workbook != null) {
			// ���Ĳ�����������д�������涨���InputStream��-����ΪexcelStream��������ֶ�Ӧstruts.xml�����õ�inputName������
			workbook2InputStream(workbook, "������������ʷ");
			return "success";
		} else {
			request.setAttribute("message", "����Excelʧ��");
			return "error";
		}
	}
	
	//����workbook,��workbookд�뵽InputStream
	public void workbook2InputStream(HSSFWorkbook workbook,String fileName) {     
		 try{ 
			 this.fileName = FtpUtil.toUtf8String(fileName); //����fileName,     
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
