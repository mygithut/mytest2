//package com.dhcc.ftp.action;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.InputStream;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.struts2.ServletActionContext;
//import org.apache.struts2.StrutsStatics;
//
//import com.dhcc.ftp.dao.DaoFactory;
//import com.dhcc.ftp.entity.FtpYieldCurveAdjust;
//import com.dhcc.ftp.entity.TelMst;
//import com.dhcc.ftp.util.CommonFunctions;
//import com.dhcc.ftp.util.DateUtil;
//import com.dhcc.ftp.util.FtpUtil;
//import com.dhcc.ftp.util.IDUtil;
//import com.dhcc.ftp.util.PageUtil;
//import com.dhcc.ftp.util.SCYTCZlineF;
//import com.opensymphony.xwork2.ActionContext;
//
//
///**
// * <p>
// * Title: ����������
// * </p>
// * 
// * <p>
// * Description:
// * </p>
// * 
// * <p>
// * Company: ��������ɷݹ�˾������ҵ��
// * </p>
// * 
// * @author �����
// * 
// * @date 3 29, 2012 10:29:33 AM
// * 
// * @version 1.0
// */
//public class CopyOfUL04Action extends BoBuilder {
//	private int page = 1;
//	private PageUtil UL04Util = null;
//	private String curveType;
//    private String resValue;
//	private String date;
//	private String curve;
//	private String curveMarketType;
//	private String curveAssetsType;
//	private String startDate;
//	private String endDate;
//	private String curveDate;
//	private double[] key;
//	private double[] x;
//	private double[] keyRate;
//	private double[] keyRateC;
//	private double[] keyRateV;
//	private int pageSize = 10;
//	private int rowsCount = -1;
//	private PageUtil resultPriceUtil = null;
//
//	HttpServletResponse response = ServletActionContext.getResponse();
//	HttpServletRequest request = ServletActionContext.getRequest();
//	DaoFactory df = new DaoFactory();
//	//=================================
//	InputStream excelStream;   
//	String fileName;  
//	@SuppressWarnings("unchecked")
//
//	public String execute() throws Exception {
//		return "history_search";
//	}
//	
//	public String distribute() throws Exception {
//		return "distribute";
//	}
//
//	//��������
//	public String calculate() throws Exception {
//		Map<String, double[]> curveMap = uL04BO.calculateCurve(curveType, date);
// 		if(curveMap.get("shiborError") != null){
//			//resp.setContentType("text/plain;charset=UTF-8");			
//			//resp.getWriter().print("����"+date+"ǰ7���Shibor���ʻ�δ���룬�޷������г����������ߣ����ȵ������ݹ�������");//	
//			String errorMessage="����"+date+"֮ǰ�ޡ�Shibor���ʡ������ݣ��޷��������������ߣ����ȵ������ݹ�������";
//			request.setAttribute("errorMessage", errorMessage);
//			return "curve_error";
//		}
// 		
// 		if(curveMap.get("stockError") != null){
//			//resp.setContentType("text/plain;charset=UTF-8");			
//			//resp.getWriter().print("����"+date+"ǰ7���Shibor���ʻ�δ���룬�޷������г����������ߣ����ȵ������ݹ�������");//	
//			String errorMessage="����"+date+"֮ǰ�ޡ���ծ�����ʡ������ݣ��޷��������������ߣ����ȵ������ݹ�������";
//			request.setAttribute("errorMessage", errorMessage);
//			return "curve_error";
//		}
//// 		if(curveMap.get("finacialError") != null){
////			//resp.setContentType("text/plain;charset=UTF-8");			
////			//resp.getWriter().print("����"+date+"ǰ7���Shibor���ʻ�δ���룬�޷������г����������ߣ����ȵ������ݹ�������");//	
////			String errorMessage="����"+date+"֮ǰ�������ڵĽ���ծ�����������ݣ��޷��������������ߣ����ȵ������ݹ�������";
////			request.setAttribute("errorMessage", errorMessage);
////			return "curve_error";
////		}
//		request.setAttribute("curveMap", curveMap);
//		return "curve";
//	}
//	//�ֶα�������������
//	public String save() throws Exception {
//	    TelMst telMst = (TelMst)request.getSession().getAttribute("userBean");
//	    String brNo = FtpUtil.getXlsBrNo(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());
//		String type = curveType;
//		if (type.length() == 1) type = "0"+type;
//		
//		if (curveType.equals("1")) {//���������������
//			double[] basicCurve =  (double[])request.getSession().getAttribute("basicCurve");
//			double[] COF =  (double[])request.getSession().getAttribute("COF");
//			double[] VOF =  (double[])request.getSession().getAttribute("VOF");
//			int[] x =  (int[])request.getSession().getAttribute("x");
//		    //�ȼ�����ݿ����Ƿ��е��죬�����ͬ�����ݣ����������ɾ���������
//			uL04BO.checkSaveAddDel(type, date, brNo, "FtpYieldCurve");
////			uL04BO.internalCurveSave(type, date, brNo, x, basicCurve, COF, VOF);
//		}else {//�г����������ߣ���Ҫ����һ�������ڵ����������ߣ�10��
//			Map curveMap = null;//uL04BO.generateMarketCurve(date, brNo, curveType, key, COF_adjust, VOF_adjust, a3_1, b2_1, c1_1, d0_1, a3_2, b2_2, c1_2, d0_2, optionsCost);
//			if(curveMap.get("shiborError") != null){
//				String errorMessage="����"+date+"֮ǰ�ޡ�Shibor���ʡ������ݣ��޷��������������ߣ����ȵ������ݹ�������";
//				request.setAttribute("errorMessage", errorMessage);
//				return "curve_error";
//			}
//	 		if(curveMap.get("stockError") != null){
//				String errorMessage="����"+date+"֮ǰ�ޡ���ծ�����ʡ������ݣ��޷��������������ߣ����ȵ������ݹ�������";
//				request.setAttribute("errorMessage", errorMessage);
//				return "curve_error";
//			}
//	        List<String> newDateList = (List<String>)curveMap.get("newDateList");
//	        List<double[]> keyList = (List<double[]>)curveMap.get("keyList");
//	        List<double[]> COF_adjustList = (List<double[]>)curveMap.get("COF_adjustList");
//	        List<double[]> VOF_adjustList = (List<double[]>)curveMap.get("VOF_adjustList");
//	        List<double[]> a3_1List = (List<double[]>)curveMap.get("a3_1List");
//	        List<double[]> b2_1List = (List<double[]>)curveMap.get("b2_1List");
//	        List<double[]> c1_1List = (List<double[]>)curveMap.get("c1_1List");
//	        List<double[]> d0_1List = (List<double[]>)curveMap.get("d0_1List");
//	        List<double[]> a3_2List = (List<double[]>)curveMap.get("a3_2List");
//	        List<double[]> b2_2List = (List<double[]>)curveMap.get("b2_2List");
//	        List<double[]> c1_2List = (List<double[]>)curveMap.get("c1_2List");
//	        List<double[]> d0_2List = (List<double[]>)curveMap.get("d0_2List");
//	        for (int i = 0; i < newDateList.size(); i++) {
//	        	//��������������
//		 	    System.out.println("------------��ʼ����"+newDateList.get(i)+"�������������--------");
//		 	    //�ȼ�����ݿ����Ƿ��е��죬�г�������ͬ�����ݣ����������ɾ���������
//				uL04BO.checkSaveAddDel(type, newDateList.get(i), brNo, "FtpYieldCurve");
//		 		uL04BO.marketCurveSave(type, newDateList.get(i), brNo, keyList.get(i), a3_1List.get(i), b2_1List.get(i), c1_1List.get(i), d0_1List.get(i), a3_2List.get(i), b2_2List.get(i), c1_2List.get(i), d0_2List.get(i), COF_adjustList.get(i), VOF_adjustList.get(i));
//	        }
//	        
//		}
////		uL04BO.saveAdjust(curveType, date, brNo, adjustKeyC, adjustRateC);
//		return null;
//	}
//	
//	
//	//�Է���--���浽ftp_yield_curve_sfb����-�ֶα�������������
//	public String sfb() throws Exception {
//	    TelMst telMst = (TelMst)request.getSession().getAttribute("userBean");
//	    String brNo = FtpUtil.getXlsBrNo(telMst.getBrMst().getBrNo(), telMst.getBrMst().getManageLvl());
//		String type = curveType;
//		if (type.length() == 1) type = "0"+type;
//		
//		if (curveType.equals("1")) {//���������������
//			//�ȼ�����ݿ����Ƿ��е��죬�г�������ͬ�����ݣ����������ɾ���������
//			double[] basicCurve =  (double[])request.getSession().getAttribute("basicCurve");
//			double[] COF =  (double[])request.getSession().getAttribute("COF");
//			double[] VOF =  (double[])request.getSession().getAttribute("VOF");
//			int[] x =  (int[])request.getSession().getAttribute("x");
//		    //�ȼ�����ݿ����Ƿ��е��죬�����ͬ�����ݣ����������ɾ���������
//			uL04BO.checkSaveAddDel(type, date, brNo, "FtpYieldCurveSfb");
////			uL04BO.internalCurveSfb(type, date, brNo, x, basicCurve, COF, VOF);
//		}else {//�г����������ߣ���Ҫ����һ�������ڵ����������ߣ�10��
//			Map curveMap = null;//uL04BO.generateMarketCurve(date, brNo, curveType, key, COF_adjust, VOF_adjust, a3_1, b2_1, c1_1, d0_1, a3_2, b2_2, c1_2, d0_2, optionsCost);
//			if(curveMap.get("shiborError") != null){
//				String errorMessage="����"+date+"֮ǰ�ޡ�Shibor���ʡ������ݣ��޷��������������ߣ����ȵ������ݹ�������";
//				request.setAttribute("errorMessage", errorMessage);
//				return "curve_error";
//			}
//	 		if(curveMap.get("stockError") != null){
//				String errorMessage="����"+date+"֮ǰ�ޡ���ծ�����ʡ������ݣ��޷��������������ߣ����ȵ������ݹ�������";
//				request.setAttribute("errorMessage", errorMessage);
//				return "curve_error";
//			}
//	        List<String> newDateList = (List<String>)curveMap.get("newDateList");
//	        List<double[]> keyList = (List<double[]>)curveMap.get("keyList");
//	        List<double[]> COF_adjustList = (List<double[]>)curveMap.get("COF_adjustList");
//	        List<double[]> VOF_adjustList = (List<double[]>)curveMap.get("VOF_adjustList");
//	        List<double[]> a3_1List = (List<double[]>)curveMap.get("a3_1List");
//	        List<double[]> b2_1List = (List<double[]>)curveMap.get("b2_1List");
//	        List<double[]> c1_1List = (List<double[]>)curveMap.get("c1_1List");
//	        List<double[]> d0_1List = (List<double[]>)curveMap.get("d0_1List");
//	        List<double[]> a3_2List = (List<double[]>)curveMap.get("a3_2List");
//	        List<double[]> b2_2List = (List<double[]>)curveMap.get("b2_2List");
//	        List<double[]> c1_2List = (List<double[]>)curveMap.get("c1_2List");
//	        List<double[]> d0_2List = (List<double[]>)curveMap.get("d0_2List");
//	        for (int i = 0; i < newDateList.size(); i++) {
//	        	//��������������
//		 	    System.out.println("------------��ʼ����"+newDateList.get(i)+"�������������--------");
//		 	    //�ȼ�����ݿ����Ƿ��е��죬�г�������ͬ�����ݣ����������ɾ���������
////				uL04BO.checkSaveAddDel(type, newDateList.get(i), brNo, "FtpYieldCurveSfb");
////		 		uL04BO.marketCurveSfb(type, newDateList.get(i), brNo, keyList.get(i), a3_1List.get(i), 
////		 				b2_1List.get(i), c1_1List.get(i), d0_1List.get(i), a3_2List.get(i), b2_2List.get(i), 
////		 				c1_2List.get(i), d0_2List.get(i), COF_adjustList.get(i), VOF_adjustList.get(i));
//	        }
//	        
//		}
//		return null;
//	}
//	
//	
//	//�鿴��ʷ�����������б�
//	public String history_list() throws Exception {
//		TelMst telMst = (TelMst)request.getSession().getAttribute("userBean");
////		UL04Util = uL04BO.dofind(brNo, curveMarketType, curveAssetsType, curveDate, telMst, page, rowsCount);
//		request.setAttribute("UL04Util", UL04Util);
//		return "history_list";
//	}
//	//����ѡ������߻�ȡ�ؼ���
//	public String curve_history() throws Exception {
//		String[] curveStr = curve.split(",");
//		List<Double[]> list_curve = new ArrayList<Double[]>();
//		List<String> list_curveName = new ArrayList<String>();
//		List<SCYTCZlineF> list_F = new ArrayList<SCYTCZlineF>();
//		for (int i = 0; i < curveStr.length; i++) {
//			String[] curve2 = curveStr[i].split("\\|");//���ơ��г����͡��ʲ����͡����ڡ�����
//			//������ʱͳһX�����꣬��Ϊ���������14��Ŀ̶ȣ����Ҫ��һ���յĿ̶ȡ�
//			if (curve2[1].equals("01")) {
//				SCYTCZlineF F = FtpUtil.getSCYTCZlineF_fromDB(curve2[1]+""+curve2[2], curve2[3], curve2[4]);
//				Double[] curve = new Double[44];
//				curve[0] = F.getY_SCYTCZline(1/30.0);
//				curve[1] = F.getY_SCYTCZline(7/30.0);
//				curve[2] = F.getY_SCYTCZline(14/30.0);
//				for (int j = 1; j < 12; j++) {
//					curve[j+2] = F.getY_SCYTCZline(j);//ÿ��
//				}
//				for (int k = 1; k < 31; k++) {
//					curve[k+13] = F.getY_SCYTCZline(k*12);
//				}
//				list_curve.add(curve);
//				list_curveName.add(curve2[3]+"-"+brInfoBo.getInfo(curve2[4]).getBrName()+"-"+(curve2[1].equals("01")? "�����":"�г�")+"-"+FtpUtil.getAssetsType(curve2[1],curve2[2]));
//				list_F.add(F);
//			}else if (curve2[1].equals("02")) {
//				//������г�����ֳ�����:1�������ڣ�1��������
//				Double[] curve_01 = new Double[15];
//				Double[] curve_02 = new Double[44];
//				SCYTCZlineF F1 = FtpUtil.getSCYTCZlineF_fromDB(curve2[1]+""+curve2[2]+"_01", curve2[3], curve2[4]);
//				curve_01[0] = F1.getY_SCYTCZline(1/30.0);
//				curve_01[1] = F1.getY_SCYTCZline(7/30.0);
//				curve_01[2] = F1.getY_SCYTCZline(14/30.0);
//				for (int j = 1; j < 13; j++) {
//					curve_01[j+2] = F1.getY_SCYTCZline(j);//ÿ��
//				}
//				list_curve.add(curve_01);
//				list_curveName.add(curve2[3]+"-"+brInfoBo.getInfo(curve2[4]).getBrName()+"-"+(curve2[1].equals("01")? "�����":"�г�")+"-"+FtpUtil.getAssetsType(curve2[1],curve2[2])+"(1��������)");
//				list_F.add(F1);
//				SCYTCZlineF F2 = FtpUtil.getSCYTCZlineF_fromDB(curve2[1]+""+curve2[2]+"_02", curve2[3], curve2[4]);
//				for (int k = 1; k < 31; k++) {
//					curve_02[k+13] = F2.getY_SCYTCZline(k*12);
//				}
//				list_curve.add(curve_02);
//				list_curveName.add(curve2[3]+"-"+brInfoBo.getInfo(curve2[4]).getBrName()+"-"+(curve2[1].equals("01")? "�����":"�г�")+"-"+FtpUtil.getAssetsType(curve2[1],curve2[2])+"(1��������)");
//				list_F.add(F2);
//			}
//			request.setAttribute("list_curve", list_curve);
//			request.getSession().setAttribute("list_curveName", list_curveName);
//			request.getSession().setAttribute("list_F", list_F);
//		}
//		return "curve_history";
//	}
//	//ӯ������
//	public String report() throws Exception {
////		HttpServletRequest request = ServletActionContext.getRequest();
////		List<YlfxReport> list = new ArrayList<YlfxReport>();
////		//ѭ����ȡ�û����µ����л���
////		//
////		String[] brName = {"���������","����ں���������","�����¡��������","����ڳ�ʤ������","����������","��������������","�����г�������","�ܼ�"};
////		Double[] dk = {12200.0,4000.0,5200.0,3000.0,8000.0,3000.0,5000.0,20200.0};
////		Double[] lxsr = {1190.0,390.0,500.0,300.0,780.0,290.0,490.0,1970.0};
////		Double[] ck = {15200.0,5000.0,6500.0,3700.0,9000.0,3500.0,5500.0,24200.0};
////		Double[] lxzc = {1050.0,350.0,450.0,250.0,610.0,210.0,400.0,1660.0};
////		for (int i = 0; i < brName.length ; i++) {
////			YlfxReport ylfxReport = new YlfxReport();
////			ylfxReport.setBrName(brName[i]);
////			ylfxReport.setDk(dk[i]);
////			ylfxReport.setLxsr(lxsr[i]);
////			ylfxReport.setCk(ck[i]);
////			ylfxReport.setLxzc(lxzc[i]);
////			list.add(ylfxReport);
////		}
////		Double resValue21 = 0.0,resValue22=0.0;
////			resValue21=7.54;
////			resValue22=6.23;
////		request.setAttribute("list", list);
////		request.setAttribute("resValue22", resValue22);
////		request.setAttribute("resValue21", resValue21);
//		return "report";
//	}
//	
//	//��ʷת�Ƽ۸�,��ȡ��ǰ��12���µ�
////	public String history() throws Exception {
////		DaoFactory df = new DaoFactory();
////		HttpServletRequest request = ServletActionContext.getRequest();
////		//��ǰ��12����
////		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
////		Calendar cal = Calendar.getInstance();
////		cal.add(Calendar.MONTH, -11);
////		StringBuffer yTransPrice = new StringBuffer();
////		StringBuffer xPricingDate = new StringBuffer();
////		for (int i = 0; i < 12; i++) {
////			String before = sdf.format(cal.getTime());
////			String sql = "from FtpResult where prcMode = '"+prcMode+"' and br_No = '"+brNo+"' and cur_No = '"+currencySelectId+"' and resDate like '"+before+"%'";
////			System.out.println("sql:"+sql);
////			FtpResult ftpResult = (FtpResult)df.getBean(sql,null);
////			xPricingDate.append(before).append(",");
////			if (ftpResult != null) {
////				yTransPrice.append(CommonFunctions.doublecut(ftpResult.getResValue()*100,2)).append(",");
////			}else {
////				yTransPrice.append(0.0).append(",");
////			}
////		    
////		    cal.add(Calendar.MONTH, 1);
////		}
////		if(yTransPrice.length()>0){
////	        yTransPrice = yTransPrice.deleteCharAt(yTransPrice.length()-1);
////		    xPricingDate = xPricingDate.deleteCharAt(xPricingDate.length()-1);
////		}
////	    System.out.println(yTransPrice);
////	    System.out.println(xPricingDate);
////		request.setAttribute("yTransPrice", yTransPrice);
////		request.setAttribute("xPricingDate", xPricingDate);
////		return "history";
////	}
//	/**
//	 * �������ɵ�����������EXCEL
//	 */
//	public String curveExport() {
//		SCYTCZlineF f00 = null;// ��׼
//		SCYTCZlineF f01 = null;// ����
//		SCYTCZlineF f02 = null;// ���
//
//		SCYTCZlineF f00_2 = null;// ��׼
//		SCYTCZlineF f01_2 = null;// ����
//		SCYTCZlineF f02_2 = null;// ���
//
//		double[][] A00 = null;
//		double[][] A01 = null;
//		double[][] A02 = null;
//		double[][] A00_2 = null;
//		double[][] A01_2 = null;
//		double[][] A02_2 = null;
//		double[] x = null;
//		double[] x_2 = null;
//
//		String[] curveTerm = null;
//		double[][] curveValue = null;
//
//		String year = date.substring(0, 4);
//		Integer dayNum = DateUtil.isLeapYear(Integer.valueOf(year)) == true ? 366
//				: 365;// �������ܵ�������
//
//		// �����
//		if (curveType.equals("1")) {
//			f00 = new SCYTCZlineF(A00, x);
//			f01 = new SCYTCZlineF(A01, x);
//			f02 = new SCYTCZlineF(A02, x);
//
//			curveTerm = new String[dayNum + 348];// ����
//			curveValue = new double[dayNum + 348][3];// 3������ֵ
//			for (int i = 0; i < dayNum; i++) {
//				curveTerm[i] = i == dayNum - 1 ? "1��" : (i + 1) + "��";
//				curveValue[i][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
//				curveValue[i][1] = CommonFunctions.doublecut(f01.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
//				curveValue[i][2] = CommonFunctions.doublecut(f02.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
//			}
//			int m = 13;
//			for (int j = dayNum; j < dayNum + 348; j++) {
//				curveTerm[j] = m % 12 == 0 ? m / 12 + "��" : m + "��";
//				curveValue[j][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline(m)*100, 4);
//				curveValue[j][1] = CommonFunctions.doublecut(f01.getY_SCYTCZline(m)*100, 4);
//				curveValue[j][2] = CommonFunctions.doublecut(f02.getY_SCYTCZline(m)*100, 4);
//				m++;
//			}
//		} else {
//
//			f00 = new SCYTCZlineF(A00, x);
//			f01 = new SCYTCZlineF(A01, x);
//			f02 = new SCYTCZlineF(A02, x);
//			f00_2 = new SCYTCZlineF(A00_2, x_2);
//			f01_2 = new SCYTCZlineF(A01_2, x_2);
//			f02_2 = new SCYTCZlineF(A02_2, x_2);
//
//			curveTerm = new String[dayNum + 1 + 348];// ����
//			curveValue = new double[dayNum + 1 + 348][3];// 3������ֵ
//			for (int i = 0; i < dayNum; i++) {
//				curveTerm[i] = i == dayNum - 1 ? "365��(ǰ���)" : (i + 1) + "��";
//				curveValue[i][0] = CommonFunctions.doublecut(f00.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
//				curveValue[i][1] = CommonFunctions.doublecut(f01.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
//				curveValue[i][2] = CommonFunctions.doublecut(f02.getY_SCYTCZline((i + 1) / 30.0)*100, 4);
//			}
//			curveTerm[dayNum] = "1��(����)";
//			curveValue[dayNum][0] = CommonFunctions.doublecut(f00_2.getY_SCYTCZline(12)*100, 4);
//			curveValue[dayNum][1] = CommonFunctions.doublecut(f01_2.getY_SCYTCZline(12)*100, 4);
//			curveValue[dayNum][2] = CommonFunctions.doublecut(f02_2.getY_SCYTCZline(12)*100, 4);
//			int m = 13;
//			for (int j = dayNum + 1; j < dayNum + 1 + 348; j++) {
//				curveTerm[j] = m % 12 == 0 ? m / 12 + "��" : m + "��";
//				curveValue[j][0] = CommonFunctions.doublecut(f00_2.getY_SCYTCZline(m)*100, 4);
//				curveValue[j][1] = CommonFunctions.doublecut(f01_2.getY_SCYTCZline(m)*100, 4);
//				curveValue[j][2] = CommonFunctions.doublecut(f02_2.getY_SCYTCZline(m)*100, 4);
//				m++;
//			}
//		}
//
//		HttpServletRequest request = (HttpServletRequest) ActionContext
//				.getContext().get(StrutsStatics.HTTP_REQUEST);
////		HSSFWorkbook workbook = uL04BO.getWorkbook(curveTerm, curveValue,
////				curveType, date, brNo);
//
////		if (workbook != null) {
////			// ���Ĳ�����������д�������涨���InputStream��-����ΪexcelStream��������ֶ�Ӧstruts.xml�����õ�inputName������
////			workbook2InputStream(workbook, date + "��-����"+brNo+"-"+(curveType.equals("1")?"�����":"�г�")+"����������");
//			return "success";
////		} else {
////			request.setAttribute("message", "����Excelʧ��");
////			return "error";
////		}
//	}
//	
//	/**
//	 * ������������ʷ��-����EXCEL
//	 */
//	public String curveHistoryExport() {
//		
//		List<String> list_curveName = (List<String>)request.getSession().getAttribute("list_curveName");
//		List<SCYTCZlineF> list_F = (List<SCYTCZlineF>)request.getSession().getAttribute("list_F");
//		HSSFWorkbook workbook = uL04BO.getHistoryWorkbook(list_curveName,list_F);
//
//		if (workbook != null) {
//			// ���Ĳ�����������д�������涨���InputStream��-����ΪexcelStream��������ֶ�Ӧstruts.xml�����õ�inputName������
//			workbook2InputStream(workbook, "����������");
//			return "success";
//		} else {
//			request.setAttribute("message", "����Excelʧ��");
//			return "error";
//		}
//	}
//	
//	//����workbook,��workbookд�뵽InputStream
//	public void workbook2InputStream(HSSFWorkbook workbook,String fileName) {     
//		 try{ 
//			 this.fileName = FtpUtil.toUtf8String(fileName); //����fileName,     
//		     ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		     workbook.write(baos);
//		     baos.flush();       
//		     byte[] aa = baos.toByteArray();      
//		     excelStream = new ByteArrayInputStream(aa, 0, aa.length);     
//		     baos.close();
//		 }
//		 catch(Exception e){e.printStackTrace();}
//	}
//	  
//
//	public String getResValue() {
//		return resValue;
//	}
//
//	public void setResValue(String resValue) {
//		this.resValue = resValue;
//	}
//
//	public String getCurveType() {
//		return curveType;
//	}
//
//	public void setCurveType(String curveType) {
//		this.curveType = curveType;
//	}
//
//	public String getDate() {
//		return date;
//	}
//
//	public void setDate(String date) {
//		this.date = date;
//	}
//
//	public double[] getKey() {
//		return key;
//	}
//
//	public void setKey(double[] key) {
//		this.key = key;
//	}
//
//	public String getCurve() {
//		return curve;
//	}
//
//	public void setCurve(String curve) throws UnsupportedEncodingException {
//		this.curve = new String(curve.getBytes("iso-8859-1"),"UTF-8");
//	}
//
//	public int getPage() {
//		return page;
//	}
//
//	public void setPage(int page) {
//		this.page = page;
//	}
//
//	public PageUtil getUL04Util() {
//		return UL04Util;
//	}
//
//	public void setUL04Util(PageUtil uL04Util) {
//		UL04Util = uL04Util;
//	}
//
//	public String getCurveMarketType() {
//		return curveMarketType;
//	}
//
//	public void setCurveMarketType(String curveMarketType) {
//		this.curveMarketType = curveMarketType;
//	}
//
//	public String getCurveAssetsType() {
//		return curveAssetsType;
//	}
//
//	public void setCurveAssetsType(String curveAssetsType) {
//		this.curveAssetsType = curveAssetsType;
//	}
//
//	public String getStartDate() {
//		return startDate;
//	}
//
//	public void setStartDate(String startDate) {
//		this.startDate = startDate;
//	}
//
//	public String getEndDate() {
//		return endDate;
//	}
//
//	public void setEndDate(String endDate) {
//		this.endDate = endDate;
//	}
//
//	public int getPageSize() {
//		return pageSize;
//	}
//
//	public void setPageSize(int pageSize) {
//		this.pageSize = pageSize;
//	}
//
//	public int getRowsCount() {
//		return rowsCount;
//	}
//
//	public void setRowsCount(int rowsCount) {
//		this.rowsCount = rowsCount;
//	}
//
//	public PageUtil getResultPriceUtil() {
//		return resultPriceUtil;
//	}
//
//	public void setResultPriceUtil(PageUtil resultPriceUtil) {
//		this.resultPriceUtil = resultPriceUtil;
//	}
//
//	public InputStream getExcelStream() {
//		return excelStream;
//	}
//
//	public void setExcelStream(InputStream excelStream) {
//		this.excelStream = excelStream;
//	}
//
//	public String getFileName() {
//		return fileName;
//	}
//
//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//	}
//
//	public String getCurveDate() {
//		return curveDate;
//	}
//
//	public void setCurveDate(String curveDate) {
//		this.curveDate = curveDate;
//	}
//
//	public double[] getX() {
//		return x;
//	}
//
//	public void setX(double[] x) {
//		this.x = x;
//	}
//
//}
