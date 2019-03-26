package com.dhcc.ftp.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.dhcc.ftp.entity.*;
import com.dhcc.ftp.trendAnalysis.HPFilter;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.dhcc.ftp.dao.DaoFactory;

/**
 * 
 * Company: ��������ɷݹ�˾���ڷ��ղ�Ʒ��
 * 
 * @author �����
 * 
 * @date Apr 29, 2011 4:40:30 PM
 * 
 * @version 1.0
 */
public class FtpUtil {


	public  static boolean isDone = true;//ͳ�Ʊ����Ƿ�������

	public static ExecutorService executorService = Executors.newFixedThreadPool (2);
	
	/**
	 * �۳�Ӫҵ˰�����ӵ�ӯ�����ʲ��ر���={[��Ϣ����-(����Ӧ����Ϣ��ĩ���-����Ӧ����Ϣ�ڳ����) +����������+Ͷ�����棨�۳���Ȩ��Ͷ�����棩+�������]*��1-�ۺ�˰�ʣ�+�����ڻ���������Ϣ����-ϵͳ��������Ϣ����}/[����������ί�д���¾����+����ծȯͶ���¾����+���ͬҵ�¾����+���ͬҵ�¾����+�����������׼���𡢱��������ִ��Ͳ����Դ���¾����]
	 * @return
	 */
	public static void main(String[] args) {
		/*Double[] x={1/30.0,7/30.0,14/30.0,1.0,3.0};
		Double[] y={1/15.0,7/15.0,14/15.0,2.0,6.0};
		Double[] re=Spline(x,y,6);
		for(int i=0;i<re.length;i++){
			System.out.print(re[i]+", ");
		}
		System.out.println();*/
		
		/*SCYTCZlineF f= getSCYTCZlineF_fromDB("0102","20120413","1801038009");
		SCYTCZlineF.print_SCYTCZline(f);
		for(int i=0;i<f.X.length;i++){
			double y=f.getY_SCYTCZline(f.X[i]);
			System.out.println(f.X[i]+"="+y);
		}*/
		
		Map<String,SCYTCZlineF> map =getMap_AllCurves_N("20130220", "1801038009", "2");
		System.out.println(map.size());
		
		
	}
	
	/**
	 * �۳�Ӫҵ˰�����ӵ�ӯ�����ʲ��ر���
	 * {[��Ϣ����-(����Ӧ����Ϣ��ĩ���-����Ӧ����Ϣ�ڳ����) +����������+Ͷ�����棨�۳���Ȩ��Ͷ�����棩+�������]*��1-�ۺ�˰�ʣ�+���ڻ���������Ϣ����}/[����������ί�д���¾����+����ծȯͶ���¾����+���ͬҵ�¾����+���ͬҵ�¾����+����������п����¾����]
	 * @param currencySelectId
	 * @param brNo
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Double getYlxzchbl(String currencySelectId, String brNo, String date) throws ParseException {
		//��Ϣ����-����¾����--ÿ���ۼ����Ӷ�
        Double lxsr = getYMRaiseAverage6(currencySelectId, brNo, date, "10001");
		System.out.println("��Ϣ����lxsr#10001:"+lxsr);
		//����Ӧ����Ϣ��ĩ���-����Ӧ����Ϣ�ڳ����=����Ӧ����Ϣ�ۼ����Ӷ�
		Double yslxljzje = getYMRaiseAverage(currencySelectId, brNo, date, "10002");
		System.out.println("����Ӧ����Ϣ�ۼ����Ӷ�yslxljzje#10002:"+yslxljzje);
        //����������-����¾����--ÿ���ۼ����Ӷ�
		Double sxfsr = getYMRaiseAverage6(currencySelectId, brNo, date, "10003");
		System.out.println("����������sxfsr#10003:"+sxfsr);
		//Ͷ�����棨��Ȩ��Ͷ�����棩-����¾����--ÿ���ۼ����Ӷ�
		Double tzsy = getYMRaiseAverage6(currencySelectId, brNo, date, "10004");
		System.out.println("Ͷ�����棨��Ȩ��Ͷ�����棩tzsy#10004:"+tzsy);
		//�������
		Double hdsy = getYMRaiseAverage6(currencySelectId, brNo, date, "10005");
		System.out.println("�������hdsy#10005:"+hdsy);
		//�ۺ�˰��
		Double zhsl = getItemSum("10007", brNo, currencySelectId, date, "");
		System.out.println("�ۺ�˰��zhsl#10007:"+zhsl);
		//���ڻ���������Ϣ����
		Double jrjgwllxsr = getYMRaiseAverage6(currencySelectId, brNo, date, "10006");
		System.out.println("���ڻ���������Ϣ����jrjgwllxsr#10006:"+jrjgwllxsr);
		//�¾����Ϊÿ�»���һ��
		//�����������¾����
		Double gxdkndyjye = getYMAverage(currencySelectId, brNo, date, "10008");
		System.out.println("�������gxdkndyjye#10008:"+gxdkndyjye);
		//���ͬҵ����¾����
		Double cftyndyjye = getYMAverage(currencySelectId, brNo, date, "10009");
		System.out.println("���ͬҵcftyndyjye#10009:"+cftyndyjye);
		//���ͬҵ����¾����
		Double cunftyndyjye = getYMAverage(currencySelectId, brNo, date, "10010");
		System.out.println("���ͬҵcunftyndyjye#10110:"+cunftyndyjye);
		//����������п�������¾����
		Double cfzyyhkxndyjye = getYMAverage(currencySelectId, brNo, date, "10011");
		System.out.println("����������п���cfzyyhkxndyjye#10011:"+cfzyyhkxndyjye);
		//����ծȯͶ������¾����
		Double glzqtzndyjye = getYMAverage(currencySelectId, brNo, date, "10012");
		System.out.println("����ծȯͶ��glzqtzndyjye#10012:"+glzqtzndyjye);
		
		//{[��Ϣ����+(����Ӧ����Ϣ��ĩ���-����Ӧ����Ϣ�ڳ����) +����������+Ͷ�����棨�۳���Ȩ��Ͷ�����棩+�������]*��1-�ۺ�˰�ʣ�+���ڻ���������Ϣ����}/[����������ί�д���¾����+����ծȯͶ���¾����+���ͬҵ�¾����+���ͬҵ�¾����+����������п����¾����]
		Double b = ((lxsr+yslxljzje+sxfsr+tzsy+hdsy)*(1-zhsl)+jrjgwllxsr)/(gxdkndyjye+glzqtzndyjye+cftyndyjye+cunftyndyjye+cfzyyhkxndyjye);
		System.out.println("�۳�Ӫҵ˰�����ӵ�ӯ�����ʲ��ر���"+"(("+lxsr+"+"+yslxljzje+"+"+sxfsr+"+"+tzsy+"+"+hdsy+")*(1-"+zhsl+")+"+jrjgwllxsr+")/("+gxdkndyjye+"+"+glzqtzndyjye+"+"+cftyndyjye+"+"+cunftyndyjye+"+"+cfzyyhkxndyjye+")");
		return (b.isInfinite() || b.isNaN())?0.0:b;
	}
	
	/**
	 * //�ʲ���ʧ�ʣ������÷������أ�=�˶��Ĵ�����׼������ȡ��/����ӯ�����ʲ��¾����
	 * @param currencySelectId
	 * @param brNo
	 * @return
	 * @throws ParseException 
	 */
	public static Double getZcssl(String currencySelectId, String brNo, String date) throws ParseException {
		//�˶��Ĵ�����׼������ȡ��--����¾����
		Double dhzzbjtqe = getYMAverage(currencySelectId, brNo, date, "10013");
		System.out.println("�˶��Ĵ�����׼������ȡ��dhzzbjtqe#10013:"+dhzzbjtqe);
		//�¾����Ϊÿ�»���һ��
		//����ӯ�����ʲ��¾����
		Double gxylxzcyjye = getYMAverage(currencySelectId, brNo, date, "10014");
		System.out.println("����ӯ�����ʲ�����¾����gxylxzcyjye#10014:"+gxylxzcyjye);
		
		//�˶��Ĵ�����׼������ȡ��/����ӯ�����ʲ��¾����
		Double e = dhzzbjtqe/gxylxzcyjye;
		System.out.println("�ʲ���ʧ��"+dhzzbjtqe+"/"+gxylxzcyjye+"");
		return (e.isInfinite() || e.isNaN())?0.0:e;
	}
	
	/**
	 * ���ʳɱ���=[��Ϣ֧��+���ڻ���������Ϣ֧��-������Ӧ����Ϣ��ĩ���-����Ӧ����Ϣ�ڳ���+������֧��+����ծȯ��Ϣ֧��]/���������¾����+ͬҵ����¾����+ͬҵ�����¾����+�������н���¾����+����ծȯ�¾����+���֡������Խ��ڸ�ծ�������ع������ʲ��
	 * @param currencySelectId
	 * @param brNo
	 * @return
	 * @throws ParseException 
	 */
	public static Double getCzcbl(String currencySelectId, String brNo, String date) throws ParseException { 
        //��Ϣ֧��--ÿ���ۼ����Ӷ�
		Double lxzc = getYMRaiseAverage6(currencySelectId, brNo, date, "10015");
		System.out.println("��Ϣ֧��lxzc#10015:"+lxzc);
		//���ڻ���������Ϣ֧��--ÿ���ۼ����Ӷ�
		Double jrjgwllxzc = getYMRaiseAverage6(currencySelectId, brNo, date, "10016");
		System.out.println("���ڻ���������Ϣ֧��jrjgwllxzc#10016:"+jrjgwllxzc);
		//����Ӧ����Ϣ��ĩ���-����Ӧ����Ϣ�ڳ����=����Ӧ����Ϣ�ۼ����Ӷ�
		Double yflxljzje = getYMRaiseAverage(currencySelectId, brNo, date, "10017");
		System.out.println("����Ӧ����Ϣ��ĩ���yflxljzje#10017:"+yflxljzje);
		//������֧��--ÿ���ۼ����Ӷ�
		Double sxfzc = getYMRaiseAverage6(currencySelectId, brNo, date, "10018");
		System.out.println("������֧��sxfzc#10018:"+sxfzc);
		//����ծȯ��Ϣ֧��--ÿ���ۼ����Ӷ�
		//Double fxzqlxzc = getYMRaiseAverage(currencySelectId, brNo, date, "10019");
		Double fxzqlxzc = 0.0;
		System.out.println("����ծȯ��Ϣ֧��fxzqlxzc#10019:"+fxzqlxzc);
		//����������¾����
		Double gxckndyjye = getYMAverage(currencySelectId, brNo, date, "10020");
		System.out.println("����������¾����gxckndyjye#10020:"+gxckndyjye);
		//ͬҵ�������¾����
		Double tycfndyjye = getYMAverage(currencySelectId, brNo, date, "10022");
		System.out.println("ͬҵ�������¾����tycfndyjye#10022:"+tycfndyjye);
		//ͬҵ��������¾����
		Double tycrndyjye = getYMAverage(currencySelectId, brNo, date, "10023");
		System.out.println("ͬҵ��������¾����tycrndyjye#10023:"+tycrndyjye);
		//�������н��������¾����
		Double zyyhjkndyjye = getYMAverage(currencySelectId, brNo, date, "10021");
		System.out.println("�������н��������¾����zyyhjkndyjye#10021:"+zyyhjkndyjye);
		//����ծȯ����¾����
		Double fxzqndyjye = getYMAverage(currencySelectId, brNo, date, "10024");
		System.out.println("����ծȯ����¾����fxzqndyjye#10024:"+fxzqndyjye);
		//�����ع������ʲ�����¾����
		Double mchgjrzcndyjye = getYMAverage(currencySelectId, brNo, date, "10025");
		System.out.println("�����ع������ʲ�����¾����mchgjrzcndyjye#10025:"+mchgjrzcndyjye);
		//���ָ�ծ����¾����
		Double txfzndyjye = getYMAverage(currencySelectId, brNo, date, "10026");
		System.out.println("���ָ�ծ����¾����txfzndyjye#10026:"+txfzndyjye);
		//�����Խ��ڸ�ծ����¾����
		Double jyxjrfzndyjye = getYMAverage(currencySelectId, brNo, date, "10027");
		System.out.println("�����Խ��ڸ�ծ����¾����jyxjrfzndyjye#10027:"+jyxjrfzndyjye);
		
		//[��Ϣ֧��+���ڻ���������Ϣ֧��+������Ӧ����Ϣ��ĩ���-����Ӧ����Ϣ�ڳ���+������֧��+����ծȯ��Ϣ֧��]/���������¾����+ͬҵ����¾����+ͬҵ�����¾����+�������н���¾����+����ծȯ�¾����+���֡������Խ��ڸ�ծ�������ع������ʲ��
		Double a = (lxzc+jrjgwllxzc+yflxljzje+fxzqlxzc+sxfzc)/(gxckndyjye+tycfndyjye+tycrndyjye+zyyhjkndyjye+fxzqndyjye+mchgjrzcndyjye+txfzndyjye+jyxjrfzndyjye);
		System.out.println("���ʳɱ���"+"("+lxzc+"+"+jrjgwllxzc+"+"+yflxljzje+"+"+fxzqlxzc+"+"+sxfzc+")/("+gxckndyjye+"+"+tycfndyjye+"+"+tycrndyjye+"+"+zyyhjkndyjye+"+"+fxzqndyjye+"+"+mchgjrzcndyjye+"+"+txfzndyjye+"+"+jyxjrfzndyjye+")");
		return (a.isInfinite() || a.isNaN())?0.0:a;
	}
	
	/**
	 * ���ʷ�����=ҵ�񼰹����*��ȡ����������ռ��/�������¾����
	 * @param currencySelectId
	 * @param brNo
	 * @return
	 * @throws ParseException 
	 */
	public static Double getCzfyl(String currencySelectId, String brNo, String date) throws ParseException {
		//ҵ�񼰹����--ÿ���ۼ����Ӷ�
		Double ywjglf = getYMRaiseAverage6(currencySelectId, brNo, date, "10028");
		System.out.println("ҵ�񼰹����ywjglf#10028:"+ywjglf);
		//��ȡ����������ռ��
		Double lqcksxfyzb = getItemSum("10029", brNo, currencySelectId, date, "");
		System.out.println("��ȡ����������ռ��lqcksxfyzb#10029:"+lqcksxfyzb);
		//����������¾����
		Double gxckndyjye = getYMAverage(currencySelectId, brNo, date, "10020");
		System.out.println("����������¾����gxckndyjye#10020:"+gxckndyjye);
		
		//ҵ�񼰹����*��ȡ����������ռ��/�������¾����
		Double f = ywjglf * lqcksxfyzb / gxckndyjye;
		System.out.println("���ʷ�����="+ywjglf+"*"+lqcksxfyzb+"/"+gxckndyjye);
		
		return (f.isInfinite() || f.isNaN())?0.0:f;
	}
	
	/**
	 * ���ƽ���ɱ���=����Ȩƽ������+���ʷ�����
	 * ����Ȩƽ������=���˴���Ȩƽ������c1*Ȩ��w1+��λ����Ȩƽ������c2*Ȩ��w2+ͬҵ�������c3*Ȩ��w3
	 * ������û�з��ֱ࣬�Ӷ� ���еĲ�Ʒ���м�Ȩƽ������
	 * ���ʷ�����=ͬ�ɱ����淨
	 * @param currencySelectId
	 * @param brNo
	 * @return ���ƽ���ɱ���
	 * @throws ParseException 
	 */
	public static Double getCkpjcbl(String currencySelectId, String brNo, String date) throws ParseException {
		//���д���Ʒ
//		String ckPrdtNo = "'P2010','P2038','P2011','P2039','P2012','P2040','P2013','P2041','P2014','P2042','P2015'," +
//				"'P2043','P2016','P2044','P2017','P2045','P2018','P2046','P2019','P2047','P2020','P2048','P2021'," +
//				"'P2049','P2022','P2050','P2023','P2051','P2024','P2052','P2025','P2053','P2026','P2054','P2027'," +
//				"'P2028','P2029','P2030','P2056','P2001','P2031','P2002','P2032','P2003','P2033','P2004','P2034'," +
//				"'P2005','P2035','P2006','P2036','P2007','P2037','P2008','P2009','P2055','P2057','P2059','P2062'," +
//				"'P2063','P2065','P2066','P2067','P2068'";
		String ckPrdtNo = "'P2010','P2038','P2011','P2039','P2012','P2040','P2013','P2041','P2014','P2042','P2015'," +
		"'P2043','P2016','P2044','P2017','P2045','P2018','P2046','P2019','P2047','P2020','P2048','P2021'," +
		"'P2049','P2022','P2050','P2023','P2051','P2024','P2052','P2025','P2053','P2026','P2054','P2027'," +
		"'P2028','P2029','P2030','P2056','P2001','P2031','P2002','P2032','P2003','P2033','P2004','P2034'," +
		"'P2005','P2035','P2006','P2036','P2007','P2037','P2008','P2009','P2055'";
		//����Ȩƽ������
		String brSql = LrmUtil.getBrSql(brNo);
		Double ckjqpjlv = getWeightedAveRate(brSql, date, ckPrdtNo, "3", -12, false);//����¾����
		System.out.println("����Ȩƽ������:"+ckjqpjlv);
		//���ʷ�����
		Double czfyl = getCzfyl(currencySelectId, brNo, date);
		System.out.println("���ʷ�����:"+czfyl);
		//���ƽ���ɱ���
		Double ckpjcbl = ckjqpjlv + czfyl;
		System.out.println("���ƽ���ɱ���:"+ckpjcbl);
		return (ckpjcbl.isInfinite() || ckpjcbl.isNaN())?0.0:ckpjcbl;
	}
	
	/**
	 * ����ƽ��������=ũ������ƽ��������d1*Ȩ��z1+ũ�徭����֯����ƽ��������d2*Ȩ��z2+ũ����ҵ����ƽ��������d3*Ȩ��z3+��ũ����ƽ��������d4*Ȩ��z4+���ÿ�͸֧ƽ��������d5*Ȩ��z5+�����ʲ�ƽ��������d6*Ȩ��d6+���ƽ��������d7*Ȩ��z7+����������п���ƽ��������d8*Ȩ��z8+���ͬҵƽ��������d9*Ȩ��z9+���ͬҵƽ��������d10*Ȩ��z10+Ͷ��ҵ��d11*Ȩ��z11
	 * ������û�з��ֱ࣬�Ӷ� ���еĲ�Ʒ���м�Ȩƽ������
	 * �ʲ���ʧ�� = ͬ�ɱ����淨
	 * @param currencySelectId
	 * @param brNo
	 * @return ����ƽ��������
	 * @throws ParseException 
	 */
	public static Double getDkpjsyl(String currencySelectId, String brNo, String date) throws ParseException {
		//���д����Ʒ
//		String dkprdtNo = "'P1018','P1019','P1020','P1021','P1022','P1023','P1024','P1025','P1026','P1027','P1028'," +
//				"'P1029','P1030','P1031','P1032','P1033','P1034','P1035','P1036','P1037','P1038','P1039','P1040'," +
//				"'P1041','P1042','P1043','P1044','P1045','P1046','P1047','P1048','P1049','P1050','P1051','P1052'," +
//				"'P1053','P1054','P1055','P1056','P1057','P1058','P1059','P1060','P1061','P1062','P1063','P1064'," +
//				"'P1065','P1066','P1067','P1068','P1069','P1070','P1071','P1072','P1073','P1074','P1075','P1076'," +
//				"'P1077','P1078','P1079','P1080','P1081','P1082','P1083','P1084','P1085','P1086','P1087','P1088'," +
//				"'P1089','P1090','P1091','P1092','P1093','P1094','P1095','P1096','P1097','P1098','P1099','P1100'," +
//				"'P1101','P1102','P1103','P1104','P1106','P1002','P1005','P1006','P1009','P1007','P1008','P1011'," +
//				"'P1012','P1114','P1116','P1123'";
		String dkprdtNo = "'P1018','P1019','P1020','P1021','P1022','P1023','P1024','P1025','P1026','P1027','P1028'," +
		"'P1029','P1030','P1031','P1032','P1033','P1034','P1035','P1036','P1037','P1038','P1039','P1040'," +
		"'P1041','P1042','P1043','P1044','P1045','P1046','P1047','P1048','P1049','P1050','P1051','P1052'," +
		"'P1053','P1054','P1055','P1056','P1057','P1058','P1059','P1060','P1061','P1062','P1063','P1064'," +
		"'P1065','P1066','P1067','P1068','P1069','P1070','P1071','P1072','P1073','P1074','P1075','P1076'," +
		"'P1077','P1078','P1079','P1080','P1081','P1082','P1083','P1084','P1085','P1086','P1087','P1088'," +
		"'P1089','P1090','P1091','P1092','P1093','P1094','P1095','P1096','P1097','P1098','P1099','P1100'," +
		"'P1101','P1102'";
		String brSql = LrmUtil.getBrSql(brNo);
		//����ƽ������
		Double dkpjll = getWeightedAveRate(brSql, date, dkprdtNo, "3", -12, false);//����¾�
		System.out.println("����ƽ������:"+dkpjll);
		//�ʲ���ʧ��
		Double zcssl = getZcssl(currencySelectId, brNo, date);
		System.out.println("�ʲ���ʧ��:"+zcssl);
		//����ƽ��������
		Double dkpjsyl = dkpjll - zcssl;
		System.out.println("����ƽ��������:"+dkpjsyl);
		
		return (dkpjsyl.isInfinite() || dkpjsyl.isNaN())?0.0:dkpjsyl;
	}
	//ƽ�������
	public static Double getPjcdb(String currencySelectId, String brNo, String date) throws ParseException {
		//����������¾����
		Double gxckndyjye = getYMAverage(currencySelectId, brNo, date, "10020");
		System.out.println("������gxckndyjye#10020:"+gxckndyjye);
		//�����������¾����
		Double gxdkndyjye = getYMAverage(currencySelectId, brNo, date, "10008");
		System.out.println("�������gxdkndyjye#10008:"+gxdkndyjye);
		
        //ƽ�������:����ȣ�Loan-to-depositRatio���������д����ܶ�/����ܶ�
        Double pjcdb = gxdkndyjye/gxckndyjye;
        System.out.println("ƽ�������:"+pjcdb);
        return (pjcdb.isInfinite() || pjcdb.isNaN())?0.0:pjcdb;
	}
	/**
	 * ���÷�������   ����������ע���μ������ɡ���ʧ���������
	 * @param currencySelectId
	 * @param brNo
	 * @return
	 * @throws ParseException 
	 */
	public static Double getXyfxxz(String currencySelectId, String brNo, String date) throws ParseException {
		Double zcldkye = 0.0, gzldkye = 0.0, cjldkye = 0.0, kydkye = 0.0, ssldkye = 0.0;
		//����������¶��վ����
		zcldkye = getMDAverage(currencySelectId, brNo, date, "10030");
	    //��ע������¶��վ����
		gzldkye = getMDAverage(currencySelectId, brNo, date, "10031");
	    //�μ�������¶��վ����
		cjldkye = getMDAverage(currencySelectId, brNo, date, "10032");
	    //����������¶��վ����
		kydkye = getMDAverage(currencySelectId, brNo, date, "10033");
	    //��ʧ������¶��վ����
		ssldkye = getMDAverage(currencySelectId, brNo, date, "10034");
	    System.out.println("����������¶��վ����"+zcldkye);
	    System.out.println("��ע������¶��վ����"+gzldkye);
	    System.out.println("�μ�������¶��վ����"+cjldkye);
	    System.out.println("����������¶��վ����"+kydkye);
	    System.out.println("��ʧ������¶��վ����"+ssldkye);
	    //���������������
		Double zcldkjtbl = getItemSum("10035", brNo, currencySelectId, date, "");
		System.out.println("���������������zcldkjtbl#10035:"+zcldkjtbl);
		//��ע�����������
		Double gzldkjtbl = getItemSum("10036", brNo, currencySelectId, date, "");
		System.out.println("��ע�����������gzldkjtbl#10036:"+gzldkjtbl);
		//�μ������������
		Double cjldkjtbl = getItemSum("10037", brNo, currencySelectId, date, "");
		System.out.println("�μ������������cjldkjtbl#10037:"+cjldkjtbl);
		//���������������
		Double kyldkjtbl = getItemSum("10038", brNo, currencySelectId, date, "");
		System.out.println("���������������kyldkjtbl#10038:"+kyldkjtbl);
		//��ʧ�����������
		Double ssldkjtbl = getItemSum("10039", brNo, currencySelectId, date, "");
		System.out.println("��ʧ�����������ssldkjtbl#10039:"+ssldkjtbl);
	    Double xyfxxz = (zcldkye * zcldkjtbl + gzldkye * gzldkjtbl + cjldkye * cjldkjtbl + kydkye * kyldkjtbl + ssldkye * ssldkjtbl) /(zcldkye + gzldkye + cjldkye + kydkye + ssldkye);
	    return (xyfxxz.isInfinite() || xyfxxz.isNaN())?0.0:xyfxxz;
	}
	
	/**
	 * �����Է�������
	 * @param currencySelectId
	 * @param brNo
	 * @return
	 * @throws ParseException 
	 */
	public static Double getLdxfxxz(String currencySelectId, String brNo, String date) throws ParseException {
        DaoFactory df = new DaoFactory();
		
        //�������¶��վ����
		Double gxckydrjye = getMDAverage(currencySelectId, brNo, date, "10020");
		System.out.println("������gxckydrjye#10020:"+gxckydrjye);
		//��������¶��վ����
		Double gxdkydrjye = getMDAverage(currencySelectId, brNo, date, "10008");
		System.out.println("�������gxdkydrjye#10008:"+gxdkydrjye);
		//�վ���������֮��
        Double rjcdkyezc = gxckydrjye - gxdkydrjye;
        System.out.println("�վ���������֮��:"+rjcdkyezc);
		String date2 = CommonFunctions.dateModifyD(date, -30);//��ǰ��30��
        //��ȡ���ʹ���ÿ������֮�Ĭ�ϴ��ʹ�����������Ӧ
		String hsql = "select nvl((t.item_amount-u.item_amount),0) as item_amount from ftp.ftp_mid_item t " +
				"left join ftp.ftp_mid_item u on t.cur_no = u.cur_no and t.br_no = u.br_no " +
				"and t.item_date=u.item_date and t.ftp_Term=u.ftp_Term and u.item_no='10008' " +
				"where t.item_no='10020' " +
				"and t.ftp_Term = '3' and t.cur_No = '"+currencySelectId+"' and t.br_No = '"+brNo+"' " +
		        "and to_date(t.item_Date, 'yyyymmdd') <= to_date('"+date+"', 'yyyymmdd') " +
		        "and to_date(t.item_Date, 'yyyymmdd') > to_date('"+date2+"', 'yyyymmdd') " +
		        "order by t.item_Date desc";
		List list = df.query1(hsql, null);
		Double sumrcdkyezc = 0.0;
        for(int i = 0; i < list.size(); i++) {
			Double cdkyezc = Double.valueOf(list.get(i).toString());//�մ�������֮��
			sumrcdkyezc += (cdkyezc-rjcdkyezc)*(cdkyezc-rjcdkyezc);
		}
        System.out.println("�����Է�����������sumrcdkyezc"+sumrcdkyezc);
        //�����Է�������
        Double ldxfxxz = Math.sqrt(sumrcdkyezc/list.size())/rjcdkyezc;
        return (ldxfxxz.isInfinite() || ldxfxxz.isNaN())?0.0:ldxfxxz;
	}
	
	/**
	 * ����poolNo,��ftpPoolInfo���л�ȡ��Ʒ�����в�Ʒ�ļ�Ȩƽ�����ʵļ���
	 * @param currencySelectId
	 * @param brNo
	 * @return ��Ʒ��Ȩƽ������
	 * @throws ParseException 
	 */
	public static Double getCpjqpjRate(String currencySelectId, String brNo, String date, String poolNo) throws ParseException {
		DaoFactory df = new DaoFactory();
		
		String hsql = "from FtpPoolInfo where poolNo = '"+poolNo+"'";
		FtpPoolInfo ftpPoolInfo = (FtpPoolInfo)df.getBean(hsql, null);
		String prdtNo = ftpPoolInfo.getContentObject();
		prdtNo = prdtNo.replace("+", ",");//��ò�Ʒ����'P1001'+'P1002',Ҫ��+�滻Ϊ,

		//��Ʒ��Ȩƽ������
		String brSql = LrmUtil.getBrSql(brNo);
		Double jqpjll = getWeightedAveRate(brSql, date, prdtNo, "3", -12, false);//����¾�
		System.out.println("��Ʒ��Ȩƽ������:"+jqpjll);

		return (jqpjll.isInfinite() || jqpjll.isNaN())?0.0:jqpjll;
	}
	
	/**
	 * ���޷��������� ���޲�ƥ��ķ��������ге�
              һ���ڹ�ծ��1���ڴ�����ʵĲ�ֵ
	 * @return
	 * @throws ParseException 
	 */
	public static Double getQxfxxz(String brNo, String date) throws ParseException {
        DaoFactory df = new DaoFactory();
		
        //��ǰ����30��
        String before = CommonFunctions.dateModifyD(date, -30);//��ǰ��30��
		Double gzsyl = 0.0;
		//һ���ڹ�ծǰ30��ľ�ֵ
		String hsql = "select nvl(sum(stock_yield),0),count(*) from ftp.ftp_stock_yield where stock_term = '1' " +
				"and to_date(stock_Date, 'yyyymmdd') > to_date('"+before+"', 'yyyymmdd') " +
				"and to_date(stock_Date, 'yyyymmdd') <= to_date('"+date+"', 'yyyymmdd')";
		List list1 = df.query1(hsql, null);
		Iterator it1 = list1.iterator();
	    while (it1.hasNext()) {
	        Object[] o = (Object[]) it1.next();
	        int count = Integer.valueOf(o[1].toString());
	        if (count != 0) gzsyl = Double.valueOf(o[0].toString())/count;
	    }
	    //1���ڵĴ���Ʒ
		String prdtNo = "'P2004','P2013','P2017','P2020','P2023','P2028','P2034','P2041','P2045','P2048','P2051'";
		//1���ڵĴ���Ʒ��Ȩƽ������
		String brSql = LrmUtil.getBrSql(brNo);
		Double ckcpjqpjll = getWeightedAveRate(brSql, date, prdtNo, "3", -12, false);//����¾�
		System.out.println("1���ڹ�ծ��ƽ��Ͷ��������:"+gzsyl);
		System.out.println("1���ڵĴ���Ʒ��Ȩƽ������:"+ckcpjqpjll);
		//Double qxfxcs = gzsyl - ckcpjqpjll;
		Double qxfxcs = ckcpjqpjll - gzsyl;
		return (qxfxcs.isInfinite() || qxfxcs.isNaN())?0.0:qxfxcs;
	}
//	/**
//	 * ����¾����
//	 * ��ƽ��=12����ƽ�����֮�͡�12={(���+��ĩ)��2+1��+2��+����+11��}��12 
//                   ��ƽ��={(����+��ĩ)��2+ǰ��������}��3 
//                   ��ƽ��=(�³�+��ĩ)��2
//	 * @param currencySelectId
//	 * @param brNo
//	 * @param lastYear
//	 * @param itemNo
//	 * @return
//	 */
//	public static Double getYMAverage(String currencySelectId, String brNo, int lastYear, String itemNo) {
//		DaoFactory df = new DaoFactory();
//		int beforeLastYear = lastYear-1;
//		//��ȡ�������ǰ��12����ĩ���
//		String hsql = "from FtpMidItem where curNo = '" + currencySelectId
//		+ "' and brNo = '" + brNo + "' and itemDate like '" + beforeLastYear + "12%' and ftpTerm = '5' and itemNo='"+itemNo+"'";
//		FtpMidItem ftpMidItem = (FtpMidItem)df.getBean(hsql, null);
//		Double ncye = ftpMidItem ==null ? 0.0 : ftpMidItem.getItemAmount();
//		//��ȡ��ĩ����ȥ��12����ĩ���
//		hsql = "from FtpMidItem where curNo = '" + currencySelectId
//		+ "' and brNo = '" + brNo + "' and itemDate like '" + lastYear + "12%' and ftpTerm = '5' and itemNo='"+itemNo+"'";
//		ftpMidItem = (FtpMidItem)df.getBean(hsql, null);
//		Double nmye = ftpMidItem ==null ? 0.0 : ftpMidItem.getItemAmount();
//		//��ȡ1-11�����ĺ�
//		hsql = "select sum(item_Amount) from Ftp_Mid_Item where cur_No = '" + currencySelectId
//		+ "' and br_No = '" + brNo + "' and item_Date like '" + lastYear + "%' and ftp_Term = '5' and item_No='"+itemNo+"' and rownum <12 order by item_date";
//		List list = df.query1(hsql, null);
//		Double sum11 = 0.0;
//		if (list != null && list.get(0) != null) {
//			sum11 = Double.valueOf(list.get(0).toString());
//		}
//		Double nypjye = (ncye/2+nmye/2+sum11)/12;
//		System.out.println(""+itemNo+"����¾���("+ncye+"/2+"+nmye+"/2+"+sum11+")/12");
//		return (nypjye.isNaN()?0.0:nypjye;
//	}
	
	/**
	 * ����¾������ϸ�����ǰ��12���µ��µ����ĺ�/12
	 * @param currencySelectId
	 * @param brNo
	 * @param date
	 * @param itemNo
	 * @return
	 */
	public static Double getYMAverage(String currencySelectId, String brNo, String date, String itemNo) {
		DaoFactory df = new DaoFactory();
		Double returnValue = 0.0;
		String date2 = CommonFunctions.dateModifyM(date, -12);//��ǰ��12����
		String hsql = "select nvl(sum(item_amount),0), count(*) from ftp.Ftp_Mid_Item where item_No = '"+itemNo+"' " +
				"and ftp_Term = '3' and cur_No = '"+currencySelectId+"' and br_No = '"+brNo+"' " +
		        "and to_date(item_Date, 'yyyymmdd') <= to_date('"+date+"', 'yyyymmdd') " +
		        "and to_date(item_Date, 'yyyymmdd') > to_date('"+date2+"', 'yyyymmdd')";
		//����Ϊ5-�£�ȡǰ12���µ�����
		
		List list = df.query1(hsql, null);
		Object obj = list.get(0);
		Object[] o = (Object[])obj;
		returnValue = Double.valueOf(o[0].toString())/Double.valueOf(o[1].toString());
		return (returnValue.isInfinite() || returnValue.isNaN()) ? 0.0 : returnValue;
	}
	
	/**
	 * �¶��վ���������ǰ��30����µ����ĺ�/30
	 * @param currencySelectId
	 * @param brNo
	 * @param date
	 * @param itemNo
	 * @return
	 */
	public static Double getMDAverage(String currencySelectId, String brNo, String date, String itemNo) {
		DaoFactory df = new DaoFactory();
		Double returnValue = 0.0;
		String date2 = CommonFunctions.dateModifyD(date, -30);//��ǰ��30��
		String hsql = "select nvl(sum(item_amount),0), count(*) from ftp.Ftp_Mid_Item where item_No = '"+itemNo+"' " +
				"and ftp_Term = '3' and cur_No = '"+currencySelectId+"' and br_No = '"+brNo+"' " +
		        "and to_date(item_Date, 'yyyymmdd') <= to_date('"+date+"', 'yyyymmdd') " +
		        "and to_date(item_Date, 'yyyymmdd') > to_date('"+date2+"', 'yyyymmdd')";
		//����Ϊ0-�죬ȡǰ��30�������
		
		List list = df.query1(hsql, null);
		Object obj = list.get(0);
		Object[] o = (Object[])obj;
		returnValue = Double.valueOf(o[0].toString())/Double.valueOf(o[1].toString());
		return (returnValue.isInfinite() || returnValue.isNaN()) ? 0.0 : returnValue;
	}
	/**
	 * ����ۼ����Ӷ��¾����
	 * ��Ŀ��6��ͷ�ģ�������˻����㣬����ȡ�����ܳ������
	 * @param currencySelectId
	 * @param brNo
	 * @param itemNo
	 * @return
	 */
	public static Double getYMRaiseAverage6(String currencySelectId, String brNo, String date, String itemNo) {
		//"SELECT to_char(last_day(to_date('20080902','yyyymmdd' )),'yyyymmdd') FROM DUAL";
		DaoFactory df = new DaoFactory();
		Double returnValue = 0.0;
		String hsql = "from FtpMidItem t where t.itemNo = '"+itemNo+"' and t.ftpTerm = '5' " +
				"and t.curNo = '"+currencySelectId+"' and t.brNo = '"+brNo+"' " +
				"and to_date(t.itemDate, 'yyyymmdd') <= to_date('"+date+"', 'yyyymmdd')" +
				"order by t.itemDate desc";//����Ϊ5-��
		List<FtpMidItem> list = df.query(hsql, null);
		if(list == null || list.size() == 0) {//���û�����ݣ�����ֻ��һ����¼����ֱ�ӷ���0(��ΪҪ��ȡÿ�µ�����)
			return 0.0;
		} 
		FtpMidItem ftpMidItem = list.get(0);
		//(�ϸ������-ǰ13���µ��µ����)/12
		returnValue = ftpMidItem.getItemAmount()/(Long.valueOf(date)%10000/100)*12;
		System.out.println(list.get(0).getItemName()+"����ۼ����Ӷ��¾���"+returnValue);
		return (returnValue.isInfinite() || returnValue.isNaN()) ? 0.0 : returnValue;
	}
	
	/**
	 * ����ۼ����Ӷ��¾����
	 * ��ȡǰ12���µ�ÿ���ۼ����Ӷ�/12*12 = (��ǰ�����-ǰ13���µ��µ����)/�·ݲ�*12
	 * @param currencySelectId
	 * @param brNo
	 * @param date
	 * @param itemNo
	 * @return
	 */
	public static Double getYMRaiseAverage(String currencySelectId, String brNo, String date, String itemNo) {
		//"SELECT to_char(last_day(to_date('20080902','yyyymmdd' )),'yyyymmdd') FROM DUAL";
		String date2 = CommonFunctions.dateModifyM(date, -13);//��ǰ��13����
		DaoFactory df = new DaoFactory();
		Double returnValue = 0.0;
		String hsql = "from FtpMidItem t where t.itemNo = '"+itemNo+"' and t.ftpTerm = '5' " +
				"and t.curNo = '"+currencySelectId+"' and t.brNo = '"+brNo+"' " +
				"and to_date(t.itemDate, 'yyyymmdd') <= to_date('"+date+"', 'yyyymmdd')" +
				"and to_date(t.itemDate, 'yyyymmdd') > to_date('"+date2+"', 'yyyymmdd')" +
				"order by t.itemDate desc";//����Ϊ5-�£�ȡǰ13���µ�����
		List<FtpMidItem> list = df.query(hsql, null);
		if(list == null || list.size() <= 1) {//���û�����ݣ�����ֻ��һ����¼����ֱ�ӷ���0(��ΪҪ��ȡÿ�µ�����)
			return 0.0;
		}
		FtpMidItem ftpMidItem1 = list.get(0);//����
		FtpMidItem ftpMidItem2 = list.get(list.size() - 1);
		int months = CommonFunctions.monthsSubtract(Long.valueOf(ftpMidItem1.getItemDate()), Long.valueOf(ftpMidItem2.getItemDate()));
		System.out.println("�·ݲmonths"+months);
		//(��ǰ�����-ǰ13���µ��µ����)/�·ݲ�*12
		returnValue = (ftpMidItem1.getItemAmount() - ftpMidItem2.getItemAmount())/months*12;
		System.out.println(list.get(0).getItemName()+"����ۼ����Ӷ��¾���"+returnValue);
		return (returnValue.isInfinite() || returnValue.isNaN()) ? 0.0 : returnValue;
	}
	/**
	 * ��ȡĳ���Ʒ�ļ�Ȩʵ��ƽ������--�¾����վ�
	 * @param brSql �Ѿ�ƴ�ӺõĻ�����ѯ����
	 * @param date
	 * @param prdtNo ��Ʒ���sql���ַ������� �� 'prdtNo1','prdtNo2'...
	 * @param termType ����
	 * @param num ��ǰ�������ڣ��� num=-12������ǰ��12���� 
	 * @param isCrossYear �Ƿ����
	 * @return
	 */
	public static Double getWeightedAveRate(String brSql, String date, String prdtNo, String termType, Integer num, boolean isCrossYear) {
		DaoFactory df = new DaoFactory();
		String date2 = CommonFunctions.dateModifyM(date, num);//��ǰ��num��
		if(isCrossYear){//����ǿ��꣬���ֹ���������
			if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//���������꣬��ֻȡ��������ݣ���1�·ݿ�ʼ
		    	date2 = date2.substring(0,4)+"1231";
		}
		
		//�Ȼ�ȡ�ܵ����
		String hsql1 = "select nvl(sum(bal),0.0) from ftp.ftp_averate_basic where " +
	       "term_type = '"+termType+"' and br_no "+brSql+" " +
	       "and prdt_no in ("+prdtNo+")" +
		   "and to_date(cyc_date,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') " +
		   "and to_date(cyc_date, 'yyyyMMdd') > to_date('"+date2+"', 'yyyyMMdd')";
		List yeList = df.query1(hsql1, null);
		Double sumYe = Double.valueOf(yeList.get(0).toString());
		if(sumYe == 0.0) return 0.0;
		//��Ȩƽ��
		String hsql2 = "select nvl(sum(bal * rate)/"+sumYe+",0.0) from ftp.ftp_averate_basic where " +
	       "term_type = '"+termType+"' and br_no "+brSql+" " +
	       "and prdt_no in ("+prdtNo+")" +
		   "and to_date(cyc_date,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') " +
		   "and to_date(cyc_date, 'yyyyMMdd') > to_date('"+date2+"', 'yyyyMMdd')";
		List jqyeList = df.query1(hsql2, null);
		Double returnValue = Double.valueOf(jqyeList.get(0).toString());
		
		return (returnValue.isInfinite() || returnValue.isNaN()) ? 0.0 : returnValue;
	}
	/**
	 * ��ȡĳ�������Ʒ���¾��������վ����
	 * @param brSql �Ѿ�ƴ�ӺõĻ�����ѯ����
	 * @param date
	 * @param prdtNo ��Ʒ���
	 * @param termType ����
	 * @param num ��ǰ�����·ݣ����� num=-12������ǰ��12����
	 * @param isCrossYear �Ƿ����
	 * @return
	 */
	public static Double getAverageAmount(String brSql, String date, String prdtNo, String termType, Integer num, boolean isCrossYear) {
		DaoFactory df = new DaoFactory();
		String date2 = CommonFunctions.dateModifyM(date, num);//��ǰ��num��
		if(isCrossYear){//����ǿ��꣬���ֹ���������
			if(Integer.valueOf(date.substring(0,4)+"0101") > Integer.valueOf(date2))//���������꣬��ֻȡ��������ݣ���1�·ݿ�ʼ
		    	date2 = date2.substring(0,4)+"1231";
		}
		String hsql1 = "select sum(bal), count(*) from ftp.ftp_averate_basic where " +
	        "term_type = '"+termType+"' and br_no "+brSql+" " +
	        "and prdt_no in ("+prdtNo+") " +
	       	"and to_date(cyc_date,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') " +
	       	"and to_date(cyc_date, 'yyyyMMdd') > to_date('"+date2+"', 'yyyyMMdd')";
		System.out.println("hsql"+hsql1);
		List list = df.query1(hsql1, null);
		Object obj = list.get(0);
	 	Object[] o = (Object[])obj;
		String[] prdtNos = prdtNo.split(",");
		int prdtNum = prdtNos.length;//��Ʒ����
		String[] brNos = brSql.split(",");
		int brNum = brNos.length;//��������
		
		int dateNum = Integer.valueOf(o[1].toString())/(prdtNum*brNum);//�����˶����ڵ����ݣ�--Ĭ�����в�Ʒÿ�ڶ������˻���
		if(dateNum == 0){
			return 0.0;
		}
		Double returnValue = Double.valueOf(o[0].toString())/dateNum;//��ƽ��ֵ
		return (returnValue.isInfinite() || returnValue.isNaN()) ? 0.0 : returnValue;
	}
	/**
	 * ���ʽ�أ���ȡ��ծ����������=(I(bond)-I(deposit)) 
	 * (I(bond)=��Ȩ1��5���ڹ�ծ������
	 * I(deposit)=��Ȩ1��5���ڴ��ʵ������
	 * @param brNo
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Double getGzqwsyl(String brNo, String date) throws ParseException {
		//��ծƽ��������(��ȡǰ90��ƽ��)
		Map<String, Double> map = FtpUtil.getGzpjsyl(-90, date);
		Double gzpjsyl1 = map.get("1");//һ����
		Double gzpjsyl2 = map.get("2");//������
		Double gzpjsyl3 = map.get("3");//������
		Double gzpjsyl4 = map.get("5");//������
		System.out.println("һ���ڹ�ծƽ��������"+gzpjsyl1);
		System.out.println("�����ڹ�ծƽ��������"+gzpjsyl2);
		System.out.println("�����ڹ�ծƽ��������"+gzpjsyl3);
		System.out.println("�����ڹ�ծƽ��������"+gzpjsyl4);
		String brSql = LrmUtil.getBrSql(brNo);
		//һ���ڶ��ڴ������¾����
		Double dqckndyjey1 = getAverageAmount(brSql, date, "'P2004','P2013','P2017','P2020','P2023','P2028','P2034','P2041','P2045','P2048','P2051'", "3", -12, false);
		//�����ڶ��ڴ������¾����
		Double dqckndyjey2 = getAverageAmount(brSql, date, "'P2005','P2014','P2035','P2042'", "3", -12, false);
		//�����ڶ��ڴ������¾����
		Double dqckndyjey3 = getAverageAmount(brSql, date, "'P2006','P2015','P2018','P2021','P2024','P2029','P2036','P2043','P2046','P2049','P2052'", "3", -12, false);
		//�����ڶ��ڴ������¾����
		Double dqckndyjey4 = getAverageAmount(brSql, date, "'P2007','P2016','P2019','P2022','P2025','P2030','P2037','P2044','P2047','P2050','P2053'", "3", -12, false);
		System.out.println("һ���ڶ��ڴ������¾����"+dqckndyjey1);
		System.out.println("�����ڶ��ڴ������¾����"+dqckndyjey2);
		System.out.println("�����ڶ��ڴ������¾����"+dqckndyjey3);
		System.out.println("�����ڶ��ڴ������¾����"+dqckndyjey4);
		//��Ȩ1��5���ڹ�ծ������
		Double jqgzsyl = (gzpjsyl1*dqckndyjey1+gzpjsyl2*dqckndyjey2+gzpjsyl3*dqckndyjey3+gzpjsyl4*dqckndyjey4)/(dqckndyjey1+dqckndyjey2+dqckndyjey3+dqckndyjey4);
		//1��5���ڵĴ���Ʒ
		String prdtNo = "'P2004','P2005','P2013','P2014','P2017','P2020','P2023','P2028','P2034','P2035'," +
				"'P2041','P2042','P2045','P2048','P2051','P2006','P2007','P2015','P2016','P2018','P2019'," +
				"'P2021','P2022','P2024','P2025','P2029','P2030','P2036','P2037','P2043','P2044','P2046'," +
				"'P2047','P2049','P2050','P2052','P2053'";
		//1��5���ڵĴ���Ʒ��Ȩƽ������
		Double ckcpjqpjll = getWeightedAveRate(brSql, date, prdtNo, "3", -12, false);//����¾�
		System.out.println("��Ȩ1��5���ڹ�ծ������"+jqgzsyl);
		System.out.println("1��5���ڵĴ���Ʒ��Ȩƽ������"+ckcpjqpjll);
		//Double gzqwsyl = jqgzsyl - ckcpjqpjll;
		Double gzqwsyl = ckcpjqpjll - jqgzsyl;
		return (gzqwsyl.isInfinite() || gzqwsyl.isNaN()) ? 0.0 : gzqwsyl;
	}
	/**
	 * ��ծ������
	 * ��ȡ<=date���һ��Ĺ�ծ������
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, Double> getGzsyl(String date) throws ParseException {
        DaoFactory df = new DaoFactory();
        Map<String, Double> map = new HashMap<String, Double>();
		
        String hsql = "select stock_Yield, stock_Term from ftp.Ftp_Stock_Yield " +
        		"where stock_Date = (select max(stock_Date) from ftp.Ftp_Stock_Yield where to_date(stock_Date,'yyyymmdd') <= to_date('"+date+"','yyyymmdd'))";
        List list = df.query1(hsql, null);
        if(list==null || list.size()==0){
        	return null;
        }
        for(int i = 0; i < list.size(); i++) {
        	Object object = list.get(i);
            Object[] obj = (Object[])object;
            double yield = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
        	map.put(obj[1].toString(), yield);
        }
        return map;
	}
	/**
	 * ��ծƽ��������
	 * ��ȡǰnum��Ĳ�ͬ���޵�ƽ����ծ������
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, Double> getGzpjsyl(int num, String date) throws ParseException {
        DaoFactory df = new DaoFactory();
        Map<String, Double> map = new HashMap<String, Double>();
		
		//��ǰ����num��
		String before = CommonFunctions.dateModifyD(date, num);
		
		Double yield = 0.0;
        String hsql = "select count(*), sum(stock_Yield), stock_Term from ftp.Ftp_Stock_Yield " +
        		"where to_date(stock_Date,'yyyymmdd') > to_date('"+before+"','yyyymmdd') " +
        		"and to_date(stock_Date,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') group by stock_Term";
        List list = df.query1(hsql, null);
        
        if(list.size()==0){//���Ŀ��ʱ��������û��¼���ծ������ֱ��ʹ��20121029��ǰ������
        	date="20121029";
        	before = CommonFunctions.dateModifyD(date, num);
        	hsql = "select count(*), sum(stock_Yield), stock_Term from ftp.Ftp_Stock_Yield " +
    		"where to_date(stock_Date,'yyyymmdd') > to_date('"+before+"','yyyymmdd') " +
    		"and to_date(stock_Date,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') group by stock_Term";
        	list = df.query1(hsql, null);
        }
        
        Iterator it = list.iterator();
		try{
		    while (it.hasNext()) {
		        Object[] o = (Object[]) it.next();
		        int count = Integer.valueOf(o[0].toString());
		        if (count != 0) {
		        	yield = o[1] == null ? 0.0 : Double.valueOf(o[1].toString())/count;
		        	map.put(o[2].toString(), yield.isNaN()?0.0:yield);
		        }
		    }
		}catch (Exception e) {
	    	e.printStackTrace();
	    }
        return map;
	}
	/**
	 * ��ȡָ�����ڵ�shibor����
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, Double> getShiborRate(String date) throws ParseException {
        DaoFactory df = new DaoFactory();
        Map<String, Double> map = new HashMap<String, Double>();
		
        //��ǰ����10��
		String before = CommonFunctions.dateModifyD(date, -10);//���ǵ��нڼ��յ����
		
        String hsql = "select Shibor_rate, Shibor_term from ftp.ftp_shibor where " +
        		" shibor_date = (select max(shibor_date) from ftp.ftp_shibor where to_date(shibor_date,'yyyymmdd') <= to_date('"+date+"','yyyymmdd'))"+
        		" and shibor_date >= '"+before+"'";
        List list = df.query1(hsql, null);
        if(list==null || list.size()==0){
        	return null;
        }
        for(int i = 0; i < list.size(); i++) {
        	Object object = list.get(i);
            Object[] obj = (Object[])object;
            double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
        	map.put(obj[1].toString(), rate);
        }
        return map;
	}
	/**
	 * ��ȡָ�����ڵ���Ѻʽ�ع�����
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, Double> getRepoRate(String date) throws ParseException {
        DaoFactory df = new DaoFactory();
        Map<String, Double> map = new HashMap<String, Double>();
		
        //��ǰ����10��
		String before = CommonFunctions.dateModifyD(date, -10);//���ǵ��нڼ��յ����
		
        String hsql = "select repo_RATE, TERM_type from ftp.ftp1_pledge_repo_rate where " +
        		" repo_DATE = (select max(repo_DATE) from ftp.ftp1_pledge_repo_rate where to_date(repo_DATE,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') " +
        		" and repo_DATE >= '"+before+"'";
        List list = df.query1(hsql, null);
        if(list==null || list.size()==0){
        	return null;
        }
        for(int i = 0; i < list.size(); i++) {
        	Object object = list.get(i);
            Object[] obj = (Object[])object;
            double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
        	map.put(obj[1].toString(), rate);
        }
        return map;
	}
	/**
	 * ��ȡָ�����ڵ�����Ʊ������
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, Double> getBillsRate(String date) throws ParseException {
        DaoFactory df = new DaoFactory();
        Map<String, Double> map = new HashMap<String, Double>();
        
        //��ǰ����10��
		String before = CommonFunctions.dateModifyD(date, -10);//���ǵ��нڼ��յ����
        String hsql = "select BILLS_RATE, TERM_type from ftp.ftp1_bank_bills_rate where " +
                  " BILLS_DATE = (select max(BILLS_DATE) from ftp.ftp1_bank_bills_rate where to_date(BILLS_DATE,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') " +
		          " and BILLS_DATE >= '"+before+"'";
        List list = df.query1(hsql, null);
        if(list==null || list.size()==0){
        	return null;
        }
        for(int i = 0; i < list.size(); i++) {
        	Object object = list.get(i);
            Object[] obj = (Object[])object;
            double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
        	map.put(obj[1].toString(), rate);
        }
        return map;
	}
	/**
	 * ��ȡָ�����ڵĽ���ծ������
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, Double> getYieldRate(String date) throws ParseException {
        DaoFactory df = new DaoFactory();
        Map<String, Double> map = new HashMap<String, Double>();
		
        //��ǰ����10��
		String before = CommonFunctions.dateModifyD(date, -10);//���ǵ��нڼ��յ����
        String hsql = "select FINANCIAL_RATE, FINANCIAL_TERM from ftp.ftp_financial_rate where " +
               " FINANCIAL_DATE = (select max(FINANCIAL_DATE) from ftp.ftp_financial_rate where to_date(FINANCIAL_DATE,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') " +
               " and FINANCIAL_DATE >= '"+before+"'";
        List list = df.query1(hsql, null);
        if(list==null || list.size()==0){
        	return null;
        }
        for(int i = 0; i < list.size(); i++) {
        	Object object = list.get(i);
            Object[] obj = (Object[])object;
            double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
        	map.put(obj[1].toString(), rate);
        }
        return map;
	}
	/**
	 * ��ͨծ�ͽ���ծ�����ʵ���ƽ������
	 * ��ȡǰnum��Ĳ�ͬ���޵�ƽ����ͨծ�ͽ���ծ�����ʵ��
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, Double> getOfRateSpreadAveRate(int num, String date) throws ParseException {
        DaoFactory df = new DaoFactory();
        Map<String, Double> map = new HashMap<String, Double>();
		
      //������ǰ����30�������
        String before = "";
		String sql = "select spread_date from ( select rownumber() over(order by spread_dATE desc) n,spread_date from ftp.ftp1_of_rate_spread group by spread_date) where n=30";
		List list = df.query1(sql, null);
        if(list==null || list.size()==0){
        	return null;
        }else {
        	before = String.valueOf(list.get(0));
        }
        
		Double rate = 0.0;
        String sql2 = "select count(*), sum(spread_RATE), TERM_TYPE from ftp.ftp1_of_rate_spread where " +
        		"to_date(spread_DATE,'yyyymmdd') >= to_date('"+before+"','yyyymmdd') " +
        		"and to_date(spread_DATE,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') group by TERM_TYPE";
        List list2 = df.query1(sql2, null);
        if(list2==null || list2.size()==0){
        	return null;
        }
        Iterator it = list2.iterator();
		try{
		    while (it.hasNext()) {
		        Object[] o = (Object[]) it.next();
		        int count = Integer.valueOf(o[0].toString());
		        if (count != 0) {
		        	rate = o[1] == null ? 0.0 : Double.valueOf(o[1].toString())/count;
		        	map.put(o[2].toString(), rate.isNaN()?0.0:rate);
		        }
		    }
		}catch (Exception e) {
	    	e.printStackTrace();
	    }
        return map;
	}
	/**
	 * shiborƽ������
	 * ��ȡ���num���ĸ�ҹ��ƽ��shibor����
	 * @return
	 */
	public static double getShiborRate(String date, int num) {
        DaoFactory df = new DaoFactory();
		
        //���num��,SHIBOR_TERM = 'O/N'������
		String hsql = "select SHIBOR_RATE,rownum  from ( select rownumber() over( order by ftpshibor.SHIBOR_DATE desc)" +
				" as rownum, ftpshibor.SHIBOR_RATE from " +
				" ftp.FTP_SHIBOR ftpshibor" +
				" where SHIBOR_TERM = 'O/N'" +//�����ҹ���޵ľ�ֵ
				" and to_date(SHIBOR_DATE,'yyyymmdd') <= to_date('"+date+"','yyyymmdd')" +
				" order by ftpshibor.SHIBOR_DATE desc, ftpshibor.SHIBOR_RATE ) " +
				" where rownum <= "+num;
		
        List list = df.query1(hsql, null);
        if(list==null || list.size()==0){
        	return 0;
        }
        double sumRate = 0;
        for (int i = 0; i < list.size(); i++) {
			Object[] obj = (Object[])list.get(i);
			sumRate += Double.valueOf(String.valueOf(obj[0]));
		}
        double aveRate = sumRate/list.size();
        return aveRate;
	}
	/**
	 * ����ծƽ��������
	 * ��ȡǰnum��Ĳ�ͬ���޵�ƽ������ծ������
	 * @return
	 * @throws ParseException 
	 */
	public static Map<String, Double> getJrzpjsyl(int num, String date) throws ParseException {
        DaoFactory df = new DaoFactory();
        Map<String, Double> map = new HashMap<String, Double>();
		
		//��ǰ����num��
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = date2Calendar(sdf.parse(date));
		cal.add(Calendar.DATE, num);
		String before = sdf.format(cal.getTime());

		Double yield = 0.0;
        String hsql = "select count(*), sum(FINANCIAL_RATE), FINANCIAL_TERM from ftp.FTP_FINANCIAL_RATE where " +
        		"to_date(FINANCIAL_DATE,'yyyymmdd') > to_date('"+before+"','yyyymmdd') " +
        		"and to_date(FINANCIAL_DATE,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') group by FINANCIAL_TERM";
        List list = df.query1(hsql, null);
        if(list.size()==0){//���Ŀ��ʱ��������û��¼�����ծ������ֱ��ʹ��20121030��ǰ������
        	date="20121030";
        	before = CommonFunctions.dateModifyD(date, num);
        	hsql = "select count(*), sum(FINANCIAL_RATE), FINANCIAL_TERM from ftp.FTP_FINANCIAL_RATE where " +
    		"to_date(FINANCIAL_DATE,'yyyymmdd') > to_date('"+before+"','yyyymmdd') " +
    		"and to_date(FINANCIAL_DATE,'yyyymmdd') <= to_date('"+date+"','yyyymmdd') group by FINANCIAL_TERM";
        	list = df.query1(hsql, null);
        }
        Iterator it = list.iterator();
		try{
		    while (it.hasNext()) {
		        Object[] o = (Object[]) it.next();
		        int count = Integer.valueOf(o[0].toString());
		        if (count != 0) {
		        	yield = o[1] == null ? 0.0 : Double.valueOf(o[1].toString())/count;
		        	map.put(o[2].toString(), yield.isNaN()?0.0:yield);
		        }
		    }
		}catch (Exception e) {
	    	e.printStackTrace();
	    }
        return map;
	}
	/**
	 * �����׼���ʼ��ϸ���֮���ִ������
	 * @param ftpPublicRateList
	 * @param termType
	 * @param dgCkZb
	 * @return
	 */
	public static Double getPublicRate(List<FtpPublicRate> ftpPublicRateList, String termType, double dgCkZb) {
		double dsCkZb = 1-dgCkZb;
		for (FtpPublicRate ftpPublicRate : ftpPublicRateList) {
			if (ftpPublicRate.getRateNo().equals(termType)) {
				double floadPercent = 1;
				//����Ǵ����Ҫ�����ϸ�
				if(ftpPublicRate.getRateNo().substring(0, 1).equals("2") ) {
					//���ݶԹ����Ͷ�˽����ռ�������㸡������
					floadPercent = 1+(ftpPublicRate.getFloatPercent()==null?0:ftpPublicRate.getFloatPercent())*dsCkZb+(ftpPublicRate.getFloatPercentDg()==null?0:ftpPublicRate.getFloatPercentDg())*dgCkZb;
				}
				return ftpPublicRate.getRate()*floadPercent;
			}
		}
		return 0.0;
	}
	
	
	/*
	  * ��ȡ�������ʵ�����
	  */
	 public static String getRateTermType(String id){
		 String name="";
		 if (id == null || "null".equalsIgnoreCase(id) || "".equals(id))
				return name;
		if(id.equals("2D1")){        name = "һ��֪ͨ";
		}else if(id.equals("2D7")){        name = "����֪ͨ";
		}else if(id.equals("2M0")){        name = "����";
		}else if(id.equals("2M3")) { name = "������";
		}else if(id.equals("2M6")){ name = "������";
		}else if(id.equals("2Y1")){ name = "һ��";
		}else if(id.equals("2Y2")){ name = "����";
		}else if(id.equals("2Y3")){name = "����";
		}else if(id.equals("2Y5")){name = "����";
		}else if(id.equals("1M1")){name = "����������(��)";
		}else if(id.equals("1M2")){name = "��������һ��(��)";
		}else if(id.equals("1Y1")){name = "һ��������(��)";
		}else if(id.equals("1Y2")){name = "����������(��)";
		}else if(id.equals("1Y3")){name = "��������";
		}
		 return name;
	 }
	 
	 /*
	  * ��ȡ��ծ�����ʵ�����
	  */
	 public static String getStockTermType(String id){
		 String name="";
		 if (id == null || "null".equalsIgnoreCase(id) || "".equals(id))
				return name;
		if(id.equals("1Y")){       name = "1��";
		}else if(id.equals("2Y")){ name = "2��";
		}else if(id.equals("3Y")){ name = "3��";
		}else if(id.equals("5Y")){ name = "5��";
		}else if(id.equals("7Y")){ name = "7��";
		}else if(id.equals("10Y")){name = "10��";
		}else if(id.equals("15Y")){name = "15��";
		}else if(id.equals("20Y")){name = "20��";
		}else if(id.equals("30Y")){name = "30��";
		}
		 return name;
	 }
	 
	 /*
	  * �������޻�ȡ�ؼ����ʵ����ڵ�λ��
	  */
	 public static int getKeyPoint(String curveType, String term){
		 int num=0;
		 if (term == null || "null".equalsIgnoreCase(term) || "".equals(term))
				return num;
		 if (curveType.equals("1")) {
			 if(term.equals("0M")){       num = 0;
			 }else if(term.equals("3M")){ num = 4;
			 }else if(term.equals("6M")){ num = 5;
			 }else if(term.equals("1Y")){ num = 6;
			 }else if(term.equals("2Y")){ num = 7;
			 }else if(term.equals("3Y")){num = 8;
		  	 }else if(term.equals("5Y")){num = 9;
			 }else if(term.equals("7Y")){num = 10;
		 	 }else if(term.equals("10Y")){num = 11;
			 }else if(term.equals("15Y")){num = 12;
			 }else if(term.equals("20Y")){num = 13;
			 }else if(term.equals("30Y")){num = 14;
			 }
		 }else if (curveType.equals("2")) {
			 if(term.equals("O/N")){       num = 0;
			 }else if(term.equals("1W")){ num = 1;
			 }else if(term.equals("2W")){ num = 2;
			 }else if(term.equals("1M")){ num = 3;
			 }else if(term.equals("3M")){ num = 4;
			 }else if(term.equals("6M")){num = 5;
		  	 }else if(term.equals("9M")){num = 6;
			 }else if(term.equals("1YS")){num = 7;
		 	 }else if(term.equals("1YG")){num = 8;
		 	 }else if(term.equals("2Y")){num = 9;
			 }else if(term.equals("3Y")){num = 10;
			 }else if(term.equals("5Y")){num = 11;
			 }else if(term.equals("7Y")){num = 12;
			 }else if(term.equals("10Y")){num = 13;
			 }else if(term.equals("15Y")){num = 14;
			 }else if(term.equals("20Y")){num = 15;
			 }else if(term.equals("30Y")){num = 16;
			 }
		 }
		
		 return num;
	 }
	 /*
	  * �����������ͻ�ȡ��������
	  */
	 public static String getTermNameByType(String curveType, String term){
		 String termName = "";
		 if (term == null || "null".equalsIgnoreCase(term) || "".equals(term))
				return termName;
		 if (curveType.equals("01")) {
			 if(term.equals("0M")){       termName = "3��������";
			 }else if(term.equals("3M")){ termName = "3����";
			 }else if(term.equals("6M")){ termName = "6����";
			 }else if(term.equals("1Y")){ termName = "1��";
			 }else if(term.equals("2Y")){ termName = "2��";
			 }else if(term.equals("3Y")){termName = "3��";
		  	 }else if(term.equals("5Y")){termName = "5��";
			 }else if(term.equals("7Y")){termName = "7��";
		 	 }else if(term.equals("10Y")){termName = "10��";
			 }else if(term.equals("15Y")){termName = "15��";
			 }else if(term.equals("20Y")){termName = "20��";
			 }else if(term.equals("30Y")){termName = "30��";
			 }
		 }else if (curveType.equals("02")) {
			 if(term.equals("O/N")){       termName = "��ҹ";
			 }else if(term.equals("1W")){ termName = "7��";
			 }else if(term.equals("2W")){ termName = "14��";
			 }else if(term.equals("1M")){ termName = "1����";
			 }else if(term.equals("3M")){ termName = "3����";
			 }else if(term.equals("6M")){termName = "6����";
		  	 }else if(term.equals("9M")){termName = "9����";
			 }else if(term.equals("1YS")){termName = "1��shibor";
		 	 }else if(term.equals("1YG")){termName = "1���ծ";
		 	 }else if(term.equals("2Y")){termName = "2��";
			 }else if(term.equals("3Y")){termName = "3��";
			 }else if(term.equals("5Y")){termName = "5��";
			 }else if(term.equals("7Y")){termName = "7��";
			 }else if(term.equals("10Y")){termName = "10��";
			 }else if(term.equals("15Y")){termName = "15��";
			 }else if(term.equals("20Y")){termName = "20��";
			 }else if(term.equals("30Y")){termName = "30��";
			 }
		 }
		
		 return termName;
	 }
	 /*
	  * ����ҵ���Ż�ȡҵ������
	  */
	 public static String getBusinessName(String businessNo){
		 if (businessNo == null || "null".equalsIgnoreCase(businessNo) || "".equals(businessNo))
				return "";
		 String name = "";
		 String[] business_names={"����","����������п���","���ͬҵ","���ͬҵ","Ͷ��","��ع�","�ֽ�","�����ʲ�",
		 "���ڴ��","���ڴ��","�������","ͬҵ���","ͬҵ����","�����н��","���ָ�ծ","���ع�","������ծ"};

		 if(businessNo.equals("YW101")){       name = business_names[0];
		 }else if(businessNo.equals("YW102")){ name = business_names[1];
		 }else if(businessNo.equals("YW103")){ name = business_names[2];
		 }else if(businessNo.equals("YW104")){ name = business_names[3];
		 }else if(businessNo.equals("YW105")){ name = business_names[4];
		 }else if(businessNo.equals("YW106")){ name = business_names[5];
	  	 }else if(businessNo.equals("YW107")){ name = business_names[6];
	  	 }else if(businessNo.equals("YW108")){ name = business_names[7];
	  	 }else if(businessNo.equals("YW201")){ name = business_names[8];
	  	 }else if(businessNo.equals("YW202")){ name = business_names[9];
	  	 }else if(businessNo.equals("YW203")){ name = business_names[10];
	  	 }else if(businessNo.equals("YW204")){ name = business_names[11];
	  	 }else if(businessNo.equals("YW205")){ name = business_names[12];
	  	 }else if(businessNo.equals("YW206")){ name = business_names[13];
	  	 }else if(businessNo.equals("YW207")){ name = business_names[14];
	  	 }else if(businessNo.equals("YW208")){ name = business_names[15];
	  	 }else if(businessNo.equals("YW209")){ name = business_names[16];
	  	 }
		 return name;
	 }
	 
	 /**
	  * ����������ֵ
	  * 
	  * @param x
	  * @param y
	  * @return
	  */
	 public static Double[] Spline(Double[] x, Double[] y, int nLength)
	 {
		    Double xpoint[],ypoint[];
		    double h[],a[],c[],d[],b[],s2[],s1,s;
		    double m;
		    
		    int N=x.length,i=0;
		    xpoint=new Double[nLength];
	    	ypoint=new Double[nLength];
	        
	        h=new double[N];
	        a=new double[N];
	        c=new double[N];
	        d=new double[N];
	        b=new double[N];
	        //s1=new double[N];
	        s2=new double[N];
	        
	        for(int k=0;k<N-1;k++)
	        {
	        	h[k]=x[k+1]-x[k];	
	        }
	        a[1]=2*(h[0]+h[1]);
	        
	        for(int k=2;k<N-1;k++)
	        {
	        	a[k]=2*(h[k-1]+h[k])-h[k-1]*h[k-1]/a[k-1];
	        }
	        for(int k=1;k<N;k++)
	        {
	        	c[k]=(y[k]-y[k-1])/h[k-1];
	        }
	        for(int k=0;k<N-1;k++)
	        {
	        	d[k]=6*(c[k+1]-c[k]);
	        }
	        b[1]=d[1];
	        for(int k=2;k<N-1;k++)
	        {
	        	b[k]=d[k]-b[k-1]*h[k-1]/a[k];
	        }
	        /*s2[N-1]=b[N-1]/a[N-1];
	        for(int k=N-2;k>0;k--)
	        {
	        	s2[k]=(b[k]-h[k]*s2[k+1])/a[k];
	        }*/
	        s2[0]=0;s2[N-1]=0;
	        for(int k=0;k<N-1;k++)
	        {
	        	for(m=x[k];m<=x[k+1];m++)
	        	{
	        		xpoint[i]=m;
	        		 if (i > 0 && xpoint[i].doubleValue() == xpoint[i - 1].doubleValue()) {
	        			continue;
	        		}
	        		s1=c[k+1]-s2[k+1]*h[k]/6-s2[k]*h[k]/3;
	        		//s=y[k]+s1*(m-x[k])+s2[k]*(m-x[k])*(m-x[k])+(s2[k+1]-s2[k])*(m-x[k])*(m-x[k])*(m-x[k])/(6*h[k]);
	        		s=y[k] + s1 * (m - x[k]) + s2[k] * (m - x[k]) * (m - x[k]) / 2 + (s2[k + 1] - s2[k]) * (m - x[k]) * (m - x[k]) * (m - x[k]) / (6 * h[k]);
	        		ypoint[i]=s;
	        		System.out.println("xpoint["+i+"]"+xpoint[i]);
	        		System.out.println("ypoint["+i+"]"+ypoint[i]);
	        		i++;
	        	}
	        }
	        System.out.println("------------------------------------");
	        return ypoint;
	 }
	 // Date ת��Ϊ Calendar 
	 public static Calendar date2Calendar(Date date) {  
	     if (date == null) {  
	         return null;  
	     }  
	     Calendar calendar = Calendar.getInstance();  
	     calendar.setTime(date);  
	     return calendar;  
	 }
	 
	 /**
	  * ��֪���߱�� ---> (�����ݿ�)��ȡ������������F ��ʧ�ܴ�ӡԭ���ҷ���null��
	  * @param curve_no ���߱��
	  * @param curve_date 8λ�ַ�����ʽ���确20120410������ȡ�����������µ���������
	  * @param br_no �������
	  * @return
	  */
	 public static SCYTCZlineF getSCYTCZlineF_fromDB(String curve_no,String curve_date, String br_no){
		 SCYTCZlineF f=null;
		 String sql="select A3,B2,C1,D0,x_min,x_max from ftp.ftp_yield_curve " +
		 		" where curve_no='"+curve_no+"' and br_no = '"+br_no+"'" +
		 		" and curve_date= (select max(curve_date) from ftp.ftp_yield_curve where curve_no='"+curve_no+"' and curve_date<='"+curve_date+"' and br_no = '"+br_no+"')" +
		 		" order by section_num";
		 DaoFactory df = new DaoFactory();
		 List list=df.query1(sql, null);
		 if(list.size()==0){
			 System.out.println("���ݿ���û������"+curve_date+"(��)��ǰ�����Ϊ"+curve_no+"�����������ߣ�");
			 return f;
		 }
		 double[][] A=new double[list.size()][4];
		 double[] x=new double[list.size()+1];
		 for(int i=0;i<list.size();i++){
			 Object obj=list.get(i);
			 Object[] objs=(Object[])obj;
			 A[i][0]=Double.valueOf(objs[0].toString());
			 A[i][1]=Double.valueOf(objs[1].toString());
			 A[i][2]=Double.valueOf(objs[2].toString());
			 A[i][3]=Double.valueOf(objs[3].toString());
			 x[i]=Double.valueOf(objs[4].toString());
			 x[i+1]=Double.valueOf(objs[5].toString());
		 }
		 f=new SCYTCZlineF(A,x);
		 return f;
	 }
	 
	 /**
	  * ��֪���߱�� ---> (�����ݿ�)��ȡ������������F ��ʧ�ܴ�ӡԭ���ҷ���null��-�Է���
	  * @param curve_no ���߱��
	  * @param curve_date 8λ�ַ�����ʽ���确20120410������ȡ�����������µ���������
	  * @param br_no �������
	  * @return
	  */
	 public static SCYTCZlineF getSCYTCZlineF_fromDB_sfb(String curve_no,String curve_date, String br_no){
		 SCYTCZlineF f=null;
		 String sql="select A3,B2,C1,D0,x_min,x_max from ftp.ftp_yield_curve_sfb " +
		 		" where curve_no='"+curve_no+"' and br_no = '"+br_no+"'" +
		 		" and curve_date= (select max(curve_date) from ftp.ftp_yield_curve_sfb where curve_no='"+curve_no+"' and curve_date<='"+curve_date+"' and br_no = '"+br_no+"')" +
		 		" order by section_num";
		 DaoFactory df = new DaoFactory();
		 List list=df.query1(sql, null);
		 if(list.size()==0){
			 System.out.println("���ݿ���û������"+curve_date+"(��)��ǰ�����Ϊ"+curve_no+"�����������ߣ�");
			 return f;
		 }
		 double[][] A=new double[list.size()][4];
		 double[] x=new double[list.size()+1];
		 for(int i=0;i<list.size();i++){
			 Object obj=list.get(i);
			 Object[] objs=(Object[])obj;
			 A[i][0]=Double.valueOf(objs[0].toString());
			 A[i][1]=Double.valueOf(objs[1].toString());
			 A[i][2]=Double.valueOf(objs[2].toString());
			 A[i][3]=Double.valueOf(objs[3].toString());
			 x[i]=Double.valueOf(objs[4].toString());
			 x[i+1]=Double.valueOf(objs[5].toString());
		 }
		 f=new SCYTCZlineF(A,x);
		 return f;
	 }
	 //��ȡ�ʲ�����
	 public static String getAssetsType(String curve_market_type,String curve_assets_type){
		 String type = "";
		 if (curve_market_type.equals("01")) {
			 if (curve_assets_type.equals("00")) {
				 type = "��׼��";
			 }else if (curve_assets_type.equals("01")) {
				 type = "������";
			 }else if (curve_assets_type.equals("02")) {
				 type = "�����";
			 }
		 }else if (curve_market_type.equals("02")) {
			 if (curve_assets_type.equals("00")) {
				 type = "��׼��";
			 }else if (curve_assets_type.equals("01")) {
				 type = "�ʲ�";
			 }else if (curve_assets_type.equals("02")) {
				 type = "��ծ";
			 }
		 }
		 return type;
	 }
	 
	 /**
	  * FTP���嶨�۷���---01ԭʼ����ƥ�䷨�����ĳһ����ҵ���ftp���۽��ֵ
	  * @param term  �ñ�ҵ���Ӧ������(����ָ������)����λ���졯
	  * @param f ʹ�õ����������ߺ�����
	  * @param adjust_rate �������ʣ�����С��ֵ
	  * @return
	  */
	 public static double getFTPPrice_ysqxppf(int term,SCYTCZlineF f,double adjust_rate){
		 double ftp_price=0;
		 if(term>30*360 && term<30*366){//����ʵ��30���������һ����30���������·������ܴ���360��(һ��С��366��)��ʹ������������ȡ����ֵʧ�ܣ�����ֱ�Ӹ�ֵΪ360��
			 term=30*360;
		 }	 
		 double x=term/30.0;//���޵�λ���㣻��Ϊ���ݿ�洢�����������ߺ������� ���¡� Ϊ��λ��	 
		 ftp_price=f.getY_SCYTCZline(x)+adjust_rate;
		 return ftp_price;
	 }
	 
	 /**
	  * FTP���嶨�۷���---02ָ�����ʷ������ĳһ����ҵ���ftp���۽��ֵ
	  * @param term  �ñ�ҵ��ָ�������ޣ���λ���졯
	  * @param f ʹ�õ����������ߺ�����
	  * @param adjust_rate �������ʣ�����С��ֵ
	  * @return
	  */
	 public static double getFTPPrice_zdllf(int term,SCYTCZlineF f,double adjust_rate){
		 double ftp_price=0;
		 if(term>30*360 && term<30*366){//����ʵ��30���������һ����30���������·������ܴ���360��(һ��С��366��)��ʹ������������ȡ����ֵʧ�ܣ�����ֱ�Ӹ�ֵΪ360��
			 term=30*360;
		 }
		 double x=term/30.0;//���޵�λ���㣻��Ϊ���ݿ�洢�����������ߺ������� ���¡� Ϊ��λ��
		 ftp_price=f.getY_SCYTCZline(x)+adjust_rate;
		 return ftp_price;
	 }
	 
	 /**
	  * FTP���嶨�۷���---04�ֽ����������ĳһ����ҵ���ftp���۽��ֵ<Ŀǰֻ֧�̶ֹ�������ֽ������ҽ��ȶϢ��Ϊ�ȶ����ж���>
	  * @param term  �ñ�ҵ�������ޣ���λ���졯
	  * @param delta_term  �ñ�ҵ���ֽ����̶������������λ���졯
	  * <br>-- @param repay_type ���ʽ����1��--�ȶϢ  ����2��--�ȶ��
	  * @param f ʹ�õ����������ߺ�����
	  * @param adjust_rate �������ʣ�����С��ֵ
	  * @return
	  */
	 public static double getFTPPrice_xjlf(int term,int delta_term,SCYTCZlineF f,double adjust_rate){
		 double ftp_price=0;
		 if(term>30*360 && term<30*366){//����ʵ��30���������һ����30���������·������ܴ���360��(һ��С��366��)��ʹ������������ȡ����ֵʧ�ܣ�����ֱ�Ӹ�ֵΪ360��
			 term=30*360;
		 }
		 int n=term/delta_term;
		 if(term%delta_term!=0){
			 n=n+1;
		 }
		 for(int i=0;i<n;i++){
			 ftp_price+=1.0/n*f.getY_SCYTCZline((i+1)*delta_term/30.0);//���޵�λ���㣻��Ϊ���ݿ�洢�����������ߺ������� ���¡� Ϊ��λ��
		 }
		 return ftp_price;
	 }
	 
	 /**
	  * FTP���嶨�۷���---05���ڷ������ĳһ����ҵ���ftp���۽��ֵ<Ŀǰֻ֧�̶ֹ�������ֽ������ҽ��ȶϢ��Ϊ�ȶ����ж���>
	  * @param term  �ñ�ҵ�������ޣ���λ���졯
	  * @param delta_term  �ñ�ҵ���ֽ����̶������������λ���졯
	  * @param czl  �ñ��ʲ�ҵ��Ĳ�ֵ��---���Ҵ���Ϊ0���̶��ʲ�Ϊ����ͣ��ʱ�ġ�ʣ���ֵ/ԭ�ܼ�ֵ��
	  * <br>-- @param repay_type ���ʽ����1��--�ȶϢ  ����2��--�ȶ��
	  * @param f ʹ�õ����������ߺ�����
	  * @param adjust_rate �������ʣ�����С��ֵ
	  * @return
	  */
	 public static double getFTPPrice_jqf(int term,int delta_term,double czl,SCYTCZlineF f,double adjust_rate){
		 double ftp_price=0;
		 if(term>30*360 && term<30*366){//����ʵ��30���������һ����30���������·������ܴ���360��(һ��С��366��)��ʹ������������ȡ����ֵʧ�ܣ�����ֱ�Ӹ�ֵΪ360��
			 term=30*360;
		 }
		 int n=term/delta_term;
		 if(term%delta_term!=0){
			 n=n+1;
		 }
		 double jiuqi=(n*czl+n-czl+1)/2.0*delta_term;	 
		 //System.out.println("jiuqi="+jiuqi);	 
		 ftp_price=f.getY_SCYTCZline(jiuqi/30);//���޵�λ���㣻��Ϊ���ݿ�洢�����������ߺ������� ���¡� Ϊ��λ��
		
		 return ftp_price;
	 }
	 
	 /**
	  * FTP���嶨�۷���---06���ʴ���������ĳһ����ҵ���ftp���۽��ֵ
	  * @param term  �ñ�ҵ��ָ�������ޣ���λ���졯
	  * @param f ʹ�õ����������ߺ�����
	  * @param adjust_rate �������ʣ�����С��ֵ
	  * @return
	  */
	 public static double getFTPPrice_lldmcef(int term,SCYTCZlineF f,double adjust_rate){
		 double ftp_price=0;
		 if(term>30*360 && term<30*366){//����ʵ��30���������һ����30���������·������ܴ���360��(һ��С��366��)��ʹ������������ȡ����ֵʧ�ܣ�����ֱ�Ӹ�ֵΪ360��
			 term=30*360;
		 }
		 double x=term/30.0;//���޵�λ���㣻��Ϊ���ݿ�洢�����������ߺ������� ���¡� Ϊ��λ��
		 ftp_price=f.getY_SCYTCZline(x)+adjust_rate;
		 return ftp_price;
	 }
	 
	 /**
	  * FTP���嶨�۷���---07��Ȩ���ʷ������ĳһ����ҵ���ftp���۽��ֵ
	  * @param f ʹ�õ����������ߺ�����
	  * @param adjust_rate �������ʣ�����С��ֵ
	  * @return
	  */
	 public static double getFTPPrice_jqllf(SCYTCZlineF f,double adjust_rate){
		 double ftp_price=0;
		 int[] terms={7,30,60,90,120,180,360};
		 for(int i=0;i<terms.length;i++){
			 double x=terms[i]/30.0;//���޵�λ���㣻��Ϊ���ݿ�洢�����������ߺ������� ���¡� Ϊ��λ��
			 ftp_price+=f.getY_SCYTCZline(x);
		 }
		 ftp_price=ftp_price/terms.length+adjust_rate;
		 return ftp_price;
	 }
	 
	 
	 /**
	  * ��ȡָ�����������µ��������������ߵĺ�����F��ӳ��map <��š�F>
	  * @param date ָ�����ڣ�8λ�ַ�����ʽ
	  * @param br_no ��Ӧ�Ļ������
	  * @return Map<String,SCYTCZlineF>
	  */
	 public static Map<String,SCYTCZlineF> getMap_AllCurves(String date, String br_no, String manageLvl){
		 Map<String,SCYTCZlineF> curvesF_map=new HashMap<String,SCYTCZlineF>();
		 br_no = getXlsBrNo_sylqx(br_no, manageLvl);//��ȡ��Ӧ��������
		 String sql="select distinct curve_no from ftp.ftp_yield_curve where br_no ='"+br_no+"' order by curve_no";
		 DaoFactory df = new DaoFactory();
		 List list=df.query1(sql, null);
		 String[] curve_nos=new String[list.size()];
		 for(int i=0;i<list.size();i++){
			 curve_nos[i]=list.get(i).toString();
			 curvesF_map.put(curve_nos[i],getSCYTCZlineF_fromDB(curve_nos[i],date, br_no));
		 }
		 return curvesF_map;
	 }
	 
	 /**
	  * ��ȡָ�����������µ��������������ߵĺ�����F��ӳ��map <��š�F>-�Է���
	  * @param date ָ�����ڣ�8λ�ַ�����ʽ
	  * @param br_no ��Ӧ�Ļ������
	  * @return Map<String,SCYTCZlineF>
	  */
	 public static Map<String,SCYTCZlineF> getMap_AllCurves_sfb(String date, String br_no, String manageLvl){
		 Map<String,SCYTCZlineF> curvesF_map=new HashMap<String,SCYTCZlineF>();
		 br_no = getXlsBrNo_sylqx(br_no, manageLvl);//��ȡ��Ӧ��������
		 String sql="select distinct curve_no from ftp.ftp_yield_curve_sfb where br_no ='"+br_no+"' order by curve_no";
		 DaoFactory df = new DaoFactory();
		 List list=df.query1(sql, null);
		 String[] curve_nos=new String[list.size()];
		 for(int i=0;i<list.size();i++){
			 curve_nos[i]=list.get(i).toString();
			 curvesF_map.put(curve_nos[i],getSCYTCZlineF_fromDB_sfb(curve_nos[i],date, br_no));
		 }
		 return curvesF_map;
	 }
	 
	 /**ʵ�֡�����ѡ���ϸ�ÿ�ա��¼�<br>
	  * ��ȡָ�����ݿ�ϵͳ�����£���ȥ��������ÿ������µ��������������ߵĺ�����F��ӳ��map <��š�F>
	  * @param date ָ�����ڣ�8λ�ַ�����ʽ
	  * @param br_no ��Ӧ�Ļ������
	  * @param manageLvl ��������
	  * @return Map ��String(������-���߱�š�),SCYTCZlineF��
	  */
	 public static Map<String,SCYTCZlineF> getMap_AllCurves_N(String date, String br_no, String manageLvl){
		 Map<String,SCYTCZlineF> curvesF_map=new HashMap<String,SCYTCZlineF>();
		 br_no = getXlsBrNo_sylqx(br_no, manageLvl);//��ȡ��Ӧ��������
		 String sql="select distinct curve_no from ftp.ftp_yield_curve where br_no ='"+br_no+"' order by curve_no";
		 DaoFactory df = new DaoFactory();
		 List list=df.query1(sql, null);
		 String[] curve_nos=new String[list.size()];
		 for(int i=0;i<list.size();i++){
			 curve_nos[i]=list.get(i).toString();
			 int N_days=10;//##########################����ƥ�䶨������ΪѮ(10��)��д��
			 if(date.endsWith("31")){//��Ѯ�Ҹ�����31��ʱ����Ѯ��11��
				 N_days=11;
			 }else{
				 N_days=10;
			 }
			 for(int n=0;n<N_days;n++){
				 String curve_date=String.valueOf(CommonFunctions.pub_base_deadlineD(Long.parseLong(date),-n));
				 curvesF_map.put(curve_date+"-"+curve_nos[i],getSCYTCZlineF_fromDB(curve_nos[i],curve_date, br_no));
				 //System.out.println("curvesF_map.get("+curve_date+"-"+curve_nos[i]+")="+curvesF_map.get(curve_date+"-"+curve_nos[i]));
			 }
			 
		 }
		 return curvesF_map;
	 }
	
	 /**
	  * ���ݲ�Ʒ��Ų�ѯ����Ʒ-����ƥ�䶨�۷����������ñ�FTP_product_method_rel��ȡ�䶨�۷�����ϣ�
	  * <br>���嶨�۷������+�ο�����+��������+���������߱��+ָ������+�̶�����+�����Է��ռӵ�+����ռ�üӵ�+�Ƿ����
	  * @param prdt_no
	  * @param br_no
	  * @return �����嶨�۷������+�ο�����+��������+���������߱��+ָ������+�̶����� +�����Է��ռӵ�+����ռ�üӵ�+�Ƿ���������򹹳ɵ��ַ�������
	  */ 
	 public static String[] getFTPMethod_byPrdtNo(String prdt_no,String br_no ){
		 String[] result=new String[9];
		 String sql="select method_no,assign_term,Adjust_rate,Curve_no,appoint_rate,Appoint_delta_rate,LR_adjust_rate,eps_adjust_rate,is_tz from ftp.FTP_product_method_rel" +
		 		" where product_no='"+prdt_no+"'";
		 DaoFactory df = new DaoFactory();
		 List list=df.query1(sql, null);
		 
		 if(list.size()!=0 && list.get(0)!=null){
			 if(list.size()>1){
				 System.out.println("��Ʒ"+prdt_no+"��Ӧ"+list.size()+"�����ü�¼��");
			 }
			 Object obj=list.get(0);
			 Object[] objs=(Object[])obj;
			 result[0]=objs[0].toString();
			 result[1]=objs[1].toString();
			 result[2]=objs[2].toString();
			 result[3]=objs[3].toString();
			 result[4]=objs[4].toString();
			 result[5]=objs[5].toString();
			 result[6] = objs[6]==null?"0":objs[6].toString();
			 result[7] = objs[7]==null?"0":objs[7].toString();
			 result[8] = objs[8]==null?"0":objs[8].toString();
		 }else{
			 System.out.println("û���ҵ���Ʒ���Ϊ"+prdt_no+"��ftp���۷���������null");
			 return null;
		 }
		 
		 return result;
	 }
	 
	 /**
		 * ���������������Ų�ѯ��ĳ���Ʒ-����ƥ�䶨�۷����������ñ�FTP_product_method_rel��ȡ�����в�Ʒ���۷�����ϵ�map�� <br>
		 * ��product_no --> String[]�����嶨�۷������+�ο�����+��������+���������߱��+ָ������+�̶�����+��Ʒ���+�����Է��ռӵ�+����ռ�üӵ�+�Ƿ���� �����򹹳ɵ��ַ������顿
		 * @param br_no
		 * @return Map ��product_no,String[]�����嶨�۷������+�ο�����+��������+���������߱��+ָ������+�̶�����+��Ʒ���+�����Է��ռӵ�+����ռ�üӵ�+�Ƿ���������򹹳ɵ��ַ������顿
		 */
		 public static Map<String, String[]> getMap_FTPMethod(String br_no) {
			 Map<String, String[]> ftp_methodComb_map = new HashMap<String, String[]>();
			 String sql = "select method_no,assign_term,Adjust_rate,Curve_no,appoint_rate,Appoint_delta_rate, product_no,LR_adjust_rate,eps_adjust_rate,is_tz from ftp.FTP_product_method_rel"
				 + " order by product_no";
			 DaoFactory df = new DaoFactory();
			 List list = df.query1(sql, null);
			 if (list.size() != 0) {		 
				 for (int i = 0; i < list.size(); i++) {
					 String[] result = new String[10];
					 Object obj = list.get(i);
					 Object[] objs = (Object[]) obj;	
					 result[0] = objs[0].toString();
					 result[1] = objs[1].toString();
					 result[2] = objs[2].toString();
					 result[3] = objs[3].toString();
					 result[4] = objs[4].toString();
					 result[5] = objs[5].toString();
					 result[6] = objs[6].toString();
					 result[7] = objs[7]==null?"0":objs[7].toString();
					 result[8] = objs[8]==null?"0":objs[8].toString();
					 result[9] = objs[9]==null?"1":objs[9].toString();
					 ftp_methodComb_map.put(result[6], result);
					 
				}
			 }else{			 
				 System.out.println("û���ҵ�ftp���۷���������null");
				 return null;
			 }
			 return ftp_methodComb_map;
		 }

	/**
	 * ���������������Ų�ѯ��ĳ���Ʒ-����ƥ�䶨�۷����������ñ�FTP_product_method_rel��ȡ�����в�Ʒ���۷�����ϵ�map�� <br>
	 * ��product_no --> String[]�����嶨�۷������+�ο�����+��������+���������߱��+ָ������+�̶�����+��Ʒ���+�����Է��ռӵ�+����ռ�üӵ�+�Ƿ���� �����򹹳ɵ��ַ������顿
	 * @param br_no
	 * @return Map ��product_no,String[]�����嶨�۷������+�ο�����+��������+���������߱��+ָ������+�̶�����+��Ʒ���+�����Է��ռӵ�+����ռ�üӵ�+�Ƿ���������򹹳ɵ��ַ������顿
	 */
	public static Map<String, String[]> getMap_FTPMethod_xls(String br_no,String manageLvl) {
		Map<String, String[]> ftp_methodComb_map = new HashMap<String, String[]>();
		br_no = getXlsBrNo_sylqx(br_no, manageLvl);//��ȡ��Ӧ��������
		String sql = "select method_no,assign_term,Adjust_rate,Curve_no,appoint_rate,Appoint_delta_rate, product_no,LR_adjust_rate,eps_adjust_rate,is_tz from ftp.FTP_product_method_rel"
				+ " where br_no='"+br_no+"' order by product_no";
		DaoFactory df = new DaoFactory();
		List list = df.query1(sql, null);
		if (list.size() != 0) {
			for (int i = 0; i < list.size(); i++) {
				String[] result = new String[10];
				Object obj = list.get(i);
				Object[] objs = (Object[]) obj;
				result[0] = objs[0].toString();
				result[1] = objs[1].toString();
				result[2] = objs[2].toString();
				result[3] = objs[3].toString();
				result[4] = objs[4].toString();
				result[5] = objs[5].toString();
				result[6] = objs[6].toString();
				result[7] = objs[7]==null?"0":objs[7].toString();
				result[8] = objs[8]==null?"0":objs[8].toString();
				result[9] = objs[9]==null?"1":objs[9].toString();
				ftp_methodComb_map.put(result[6], result);

			}
		}else{
			System.out.println("û���ҵ�ftp���۷���������null");
			return null;
		}
		return ftp_methodComb_map;
	}
	 
	 
	 /**
	  * ����ftp���嶨�۷������ ��ȡ �䶨�۷�������
	  * @param method_no
	  * @return
	  */
	 public static String getMethodName_byMethodNo(String method_no){
		 String method_name="";
		 if(method_no==null){
			 return null;
		 }
		 if(method_no.equals("01")){
			 method_name="ԭʼ����ƥ�䷨";
		 }else if(method_no.equals("02")){
			 method_name="ָ�����ʷ�";
		 }else if(method_no.equals("03")){
			 method_name="�ض�������ƥ�䷨";
		 }else if(method_no.equals("04")){
			 method_name="�ֽ�����";
		 }else if(method_no.equals("05")){
			 method_name="���ڷ�";
		 }else if(method_no.equals("06")){
			 method_name="���ʴ����";
		 }else if(method_no.equals("07")){
			 method_name="��Ȩ���ʷ�";
		 }else if(method_no.equals("08")){
			 method_name="�̶����";
		 }else{
			 method_name="δ֪";
		 }
		 return method_name;
	 }
	 /**
	  * �������������߱�� ��ȡ ����������������
	  * @return  method_noΪnullʱ���ء�δ������
	  */
	 public static String getCurveName_byCurveNo(String curve_no){
		 String method_name="δ����";
		 if (curve_no == null) {
			 return method_name;
		 }
		 if(curve_no.equals("��")){
			 return "��";
		 }
		 
		 if(curve_no.equals("0100")){
			 method_name="���������������-��׼��";
		 }else if(curve_no.equals("0101")){
			 method_name="���������������-������";
		 }else if(curve_no.equals("0102")){
			 method_name="���������������-�����";
		 }else if(curve_no.equals("0200")){
			 method_name="�г�����������";
		 }
		 return method_name;
	 } 
	 /**
	  * �Զ�ά�����������
	  * @param ob �ö�ά����
	  * @param order ���ڼ��н�������
	  */
	 public static void sort(long[][] ob, final int[] order) {    
		 Arrays.sort(ob, new Comparator<Object>() {    
	            public int compare(Object o1, Object o2) {    
	            	long[] one = (long[]) o1;    
	                long[] two = (long[]) o2;    
	                for (int i = 0; i < order.length; i++) {    
	                    int k = order[i];    
	                    if (one[k] > two[k]) {    
	                        return 1;    
	                    } else if (one[k] < two[k]) {    
	                        return -1;    
	                    } else {    
	                        continue;  //�����һ�����ȽϽ����ȣ���ʹ�õڶ����������бȽϡ�  
	                    }    
	                }    
	                return 0;    
	            }    
	        });   
	    }
	 /**
	  * ���ݵ�һ�е�һ��ֵ�͵ڶ��е�һ��ֵ����ȡ��ά�����Ӧ�����е����е�ֵ
	  * @param a1 ��һ�е�ĳֵ
	  */
	 public static long getValueFrom(long[][] arrayStr, int a1, int a2) {
		 for (int m = 0; m < arrayStr.length; m++) {
			 if (arrayStr[m][0] == a1 && arrayStr[m][1] == a2){
				 return arrayStr[m][2];
			 }
		 }
		 return 0;
	 }
	 
	 /**
		 * Description:�������ý��
		 * 
		 * @return
		 */
		public static String CastSetResult(Integer id, String obj) {
			if (obj == null || "null".equalsIgnoreCase(obj))
				obj = "";
			if (id == null || "null".equalsIgnoreCase(id.toString())
					|| "".equals(id.toString()))
				return obj;
			switch (id.intValue()) {
			case 0:
				obj = "δ����";
				break;
			case 1:
				obj = "���ʽ��";
				break;
			case 2:
				obj = "˫�ʽ��";
				break;
			case 3:
				obj = "���ʽ��";
				break;
			case 4:
				obj = "����ƥ��";
			}
			return obj;
		}
		/**
		 * Description:�����ʽ�ر�Ż�ȡ���ʽ������
		 * 
		 * @return
		 */
		public static String CastPool_NameByPool_no(String id, String obj) {
			if (obj == null || "null".equalsIgnoreCase(obj))
				obj = "";
			if (id == null || "null".equalsIgnoreCase(id.toString())
					|| "".equals(id.toString()))
				return obj;
			String sql="select pool_name from ftp.ftp_pool_info where pool_no='"+id+"'";
			List list=CommonFunctions.mydao.query1(sql, null);
			if(list==null||list.size()==0){
				return obj;
			}
			obj=list.get(0).toString();
			/*switch (id.intValue()) {
			case 31:
				obj = "1�����ڴ���";
				break;
			case 32:
				obj = "1���ڼ��������޴���";
				break;
			case 33:
				obj = "���ִ���";
				break;
			case 34:
				obj = "�����ʲ�";
				break;
			case 35:
				obj = "���ڴ�����";
				break;
			case 36:
				obj = "��λ���ڴ��";
				break;
			case 37:
				obj = "ͬҵ���ڴ��";
				break;
			case 38:
				obj = "ͳ��ס���ʽ���ڴ��";
				break;
			case 39:
				obj = "1������һ���Զ��ڴ��";
				break;
			case 310:
				obj = "1����һ���Զ��ڴ��";
				break;
			case 311:
				obj = "2���ڼ�����һ���Զ��ڴ��";
				break;
			case 312:
				obj = "ͬҵ���ڴ��";
			}*/
			return obj;
		}
		
		/**
		 * ��ȡ��Ŀ����ֵĳitem_no����Ŀ���ֵ 
		 * @author 
		 * 
		 * @param item_no (String) ��Ŀ��ţ�����Ϊnull
		 * @param br_no (String)������ţ�����Ϊnull
		 * @param cur_no (String)���ֺţ�����Ϊnull
		 * @param riqi (long)Ŀ�����ڣ�����Ϊnull����ʽΪ8λ�ַ���;��ȡ��ǰ�������������µ���Ŀ���ֵ
		 * @param term_type (String)�������ޣ���Ϊnull����ʾ������������
		 * @return  cnt(long)������ amt (double)���ֵ
		 * 
		 */
		public static double getItemSum(String item_no,String br_no,String cur_no,String riqi,String term_type) {
			if(item_no==null||br_no==null||cur_no==null||riqi==null){
				System.out.println("����������㣬�޷���ȡ��Ŀֵ��");
				return Double.NaN;
			}
			String sql = "select to_acc from ftp.Ftp_Item_To_Acc where item_No = '"+item_no+"'";
			List list = CommonFunctions.mydao.query1(sql, null);
			String toAcc  = (String)list.get(0);
			System.out.println("��Ӧ��Ŀ:"+toAcc);
			double amt=0;
			long cnt=0;
			String hsql="";
			if(term_type==null || "".equals(term_type)){
				hsql =  "select ITEM_AMOUNT from("
					+" select row_number() over (order by t.item_date desc) as rownumber,t.* from ftp.ftp_mid_item t "	
					+" where t.item_no = '"+item_no+"'"
					+" and to_date(t.item_date, 'yyyymmdd')<= to_date('"+riqi+"', 'yyyymmdd')";
				if(!toAcc.equals("��¼")) hsql += " and t.br_no = '"+br_no+"'";//����ǲ�¼���򲻼ӻ��������ѯ����
				hsql += " and t.cur_no = '"+cur_no+"' " +
						"order by t.item_date desc ) " +
						"where rownumber <2";
			}else{
				hsql =  "select ITEM_AMOUNT from("
					+" select row_number() over (order by t.item_date desc) as rownumber,t.* from ftp.ftp_mid_item t "
					+" where t.item_no = '"+item_no+"'"
					+" and to_date(t.item_date, 'yyyymmdd')<= to_date('"+riqi+"', 'yyyymmdd')";
				if(!toAcc.equals("��¼")) hsql += " and t.br_no = '"+br_no+"'";//����ǲ�¼���򲻼ӻ��������ѯ����
				hsql += " and t.cur_no = '"+cur_no+"'"
					+" and t.ftp_term ='"+term_type+"'"
					+" order by t.item_date desc )"
					+" where rownumber <2";
			}	
			List item_sumList = CommonFunctions.mydao.query1(hsql,null);
			
			if(item_sumList!=null && item_sumList.size()!=0){
				Object obj=item_sumList.get(0);
				amt=Double.valueOf(obj.toString());
			}
			return amt;
		}
		
		/**
		 * ����ƥ�䣺��ȡӯ���������ڷ�Χ�ڵĶ������ڴ���
		 * @param date2 ʱ�����˵�
		 * @param date ʱ����Ҷ˵�
		 * @return
		 */
		public static Integer getQXPPcountTerm(String date2, String date){
			String sql0 = "select count(distinct t.wrk_sys_date) from ftp.ftp_qxpp_result t where 1=1 " +
					"and to_date(t.wrk_sys_date, 'yyyymmdd') < to_date('"+date+"', 'yyyymmdd') " +
			        "and to_date(t.wrk_sys_date, 'yyyymmdd') >= to_date('"+date2+"', 'yyyymmdd')";
			List countTermList = CommonFunctions.mydao.query1(sql0, null);
			Integer countTerm = Integer.valueOf(countTermList.get(0).toString());//��ȡʵ�ʶ��۵����ڴ���
			return  countTerm;
		}
		
	
		
		/**
		 * �������excel���������������
		 * @return
		 */
	public static String toUtf8String(String fileName) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fileName.length(); i++) {
			char c = fileName.charAt(i);
			if (c >= 0 && c <= 255) {
				sb.append(c);
			} else {
				byte[] b;
				try {
					b = Character.toString(c).getBytes("utf-8");
				} catch (Exception ex) {
					System.out.println(ex);
					b = new byte[0];
				}
				for (int j = 0; j < b.length; j++) {
					int k = b[j];
					if (k < 0)
						k += 256;
					sb.append("%" + Integer.toHexString(k).toUpperCase());
				}
			}
		}
		return sb.toString();
	}
	
	/**
	 * ��ȡĳ�������������õ��ʽ��
	 * 
	 * @param brNo
	 * @return
	 */
	public static String getPrcModeByBrNo(String brNo) {
		String hsql = "from FtpSystemInitial where brNo = '" + brNo
				+ "' and setValidMark = '1'";
		FtpSystemInitial ftpSystemInitial = (FtpSystemInitial) CommonFunctions.mydao.getBean(hsql, null);
		return ftpSystemInitial == null ? null : ftpSystemInitial.getSetResult();
	}
	
	/**
	 * ���ݻ����źͻ������𣬻�ȡ���Ӧ��������brno
	 * 
	 * @param brNo
	 * @param manageLvl
	 * @return
	 */
	public static String getXlsBrNo(String brNo, String manageLvl) {
		if (manageLvl.equals("3")) {
			return brNo;
		}else if (manageLvl.equals("2")) {
			String hsql = "from BrMst where brNo = '" + brNo + "'";
			BrMst brMst = (BrMst) CommonFunctions.mydao.getBean(hsql, null);
			return brMst.getSuperBrNo();// �����1������ȡ���ĸ�����Ϊ����Ӧ��������
		}
		else if (manageLvl.equals("1")) {
			String hsql = "from BrMst where brNo = '" + brNo + "'";
			BrMst brMst = (BrMst) CommonFunctions.mydao.getBean(hsql, null);
			String hsql2 = "from BrMst where brNo = '" + brMst.getSuperBrNo()
					+ "'";
			brMst = (BrMst) CommonFunctions.mydao.getBean(hsql2, null);
			return brMst.getSuperBrNo();// �����0����Ҫѭ�����λ�ȡ���ĸ����ĸ�����Ϊ����Ӧ��������
		} else if (manageLvl.equals("0")) {
			String hsql = "from BrMst where brNo = '" + brNo + "'";
			BrMst brMst = (BrMst) CommonFunctions.mydao.getBean(hsql, null);
			String hsql2 = "from BrMst where brNo = '" + brMst.getSuperBrNo()
					+ "'";
			brMst = (BrMst) CommonFunctions.mydao.getBean(hsql2, null);
			String hsql3 = "from BrMst where brNo = '" + brMst.getSuperBrNo()
					+ "'";
			brMst = (BrMst) CommonFunctions.mydao.getBean(hsql3, null);
			return brMst.getSuperBrNo();// �����0����Ҫѭ�����λ�ȡ���ĸ����ĸ�����Ϊ����Ӧ��������
		}
		return "";
	}


	/**
	 * ���ݻ����źͻ������𣬻�ȡ���Ӧ��������brno
	 *
	 * @param brNo
	 * @param manageLvl
	 * @return
	 */
	public static String getXlsBrNo_qhpm(String brNo, String manageLvl) {
		if (manageLvl.equals("3")) {
			return brNo;
		}else if (manageLvl.equals("2")) {
			return brNo;// �����1������ȡ���ĸ�����Ϊ����Ӧ��������
		}
		else if (manageLvl.equals("1")) {
			String hsql = "from BrMst where brNo = '" + brNo + "'";
			BrMst brMst = (BrMst) CommonFunctions.mydao.getBean(hsql, null);
			return brMst.getSuperBrNo();// �����0����Ҫѭ�����λ�ȡ���ĸ����ĸ�����Ϊ����Ӧ��������
		} else if (manageLvl.equals("0")) {
			String hsql = "from BrMst where brNo = '" + brNo + "'";
			BrMst brMst = (BrMst) CommonFunctions.mydao.getBean(hsql, null);
			String hsql2 = "from BrMst where brNo = '" + brMst.getSuperBrNo()
					+ "'";
			brMst = (BrMst) CommonFunctions.mydao.getBean(hsql2, null);
			return brMst.getSuperBrNo();// �����0����Ҫѭ�����λ�ȡ���ĸ����ĸ�����Ϊ����Ӧ��������
		}
		return "";
	}

	/**
	 * ���ݻ����źͻ������𣬻�ȡ���Ӧ��������brno
	 *
	 * @param brNo
	 * @param manageLvl
	 * @return
	 */
	public static String getXlsBrNo_sylqx(String brNo, String manageLvl) {
		if (manageLvl.equals("3")) {
			return brNo;
		}else if (manageLvl.equals("2")) {
			return brNo;
		}
		else if (manageLvl.equals("1")) {
			String hsql = "from BrMst where brNo = '" + brNo + "'";
			BrMst brMst = (BrMst) CommonFunctions.mydao.getBean(hsql, null);
			return brMst.getSuperBrNo();// �����0����Ҫѭ�����λ�ȡ���ĸ����ĸ�����Ϊ����Ӧ��������
		} else if (manageLvl.equals("0")) {
			String hsql = "from BrMst where brNo = '" + brNo + "'";
			BrMst brMst = (BrMst) CommonFunctions.mydao.getBean(hsql, null);
			String hsql2 = "from BrMst where brNo = '" + brMst.getSuperBrNo()
					+ "'";
			brMst = (BrMst) CommonFunctions.mydao.getBean(hsql2, null);
			return brMst.getSuperBrNo();// �����0����Ҫѭ�����λ�ȡ���ĸ����ĸ�����Ϊ����Ӧ��������
		}
		return "";
	}

	/**
	 *
	 * @return
	 */
	public static Map<String,String> getBrNameMap() {
			String hsql = "from BrMst";
			Map<String,String> brNameMap = new HashMap<String, String>();
			List<BrMst> brMstList = CommonFunctions.mydao.query(hsql,null);
			for(BrMst brMst:brMstList){
				brNameMap.put(brMst.getBrNo(),brMst.getBrName());
			}
			return brNameMap;
	}


	/**
	 *
	 * @return
	 */
	public static Map<String,String> getempNameMap() {
		String hsql = "from FtpEmpInfo";
		Map<String,String> empNameMap = new HashMap<String, String>();
		List<FtpEmpInfo> ftpEmpInfoList = CommonFunctions.mydao.query(hsql,null);
		for(FtpEmpInfo ftpEmpInfo:ftpEmpInfoList){
			empNameMap.put(ftpEmpInfo.getEmpNo(),ftpEmpInfo.getEmpName());
		}
		return empNameMap;
	}


	/**
	  * �����弶����״̬�仯 ��ȡ�弶����״̬
	  * @return
	  */
	 public static String getFivSys(String fivStsNo){
		 String fivSts="-";
		 if (fivStsNo == null || fivStsNo.equals("")) {
			 return fivSts;
		 }
		 
		 if(fivStsNo.equals("01")){
			 fivSts="����";
		 }else if(fivStsNo.equals("02")){
			 fivSts="��ע";
		 }else if(fivStsNo.equals("03")){
			 fivSts="�μ�";
		 }else if(fivStsNo.equals("04")){
			 fivSts="����";
		 }else if(fivStsNo.equals("05")){
			 fivSts="��ʧ";
		 }
		 return fivSts;
	 }
	 
	 /**
	  * ����ʹ�ô�������������ߵĲ�Ʒ��FTP����ֵ
	  * @param businessNo
	  * @param term
	  * @param ckzbjAdjustMap
	  * @param ldxAdjustMap
	  * @param irAdjustMap
	  * @param prepayList
	  * @return
	  */
	public static double getCdkFtpAdjustValue(String businessNo, int term, Map<Integer, Double> ckzbjAdjustMap,
			Map<Integer, Double> ldxAdjustMap, Map<String, Double> irAdjustMap,
			List<Ftp1PrepayAdjust> prepayList) {
		double adjustValue = 0;
		int n = (int)CommonFunctions.doublecut(Math.floor(Double.valueOf(term)/30), 0);//�������������ȡ��
		//ֻ�д�����Ʒ���ܽ������е���������̶��ʲ���ֻ�ܽ��в��Ե���
		if(businessNo.equals("YW101")||businessNo.equals("YW201")||businessNo.equals("YW202")||businessNo.equals("YW203")) {
			String assetsType = "";// �ʲ�����,01����02���
			if (businessNo.equals("YW101")) {// �����Ʒ
				// ׼�������
				adjustValue += ckzbjAdjustMap.get(n < 61 ? n
						: 61) == null ? 0 : ckzbjAdjustMap.get(n < 61 ? n : 61);// ת��Ϊ�£����>60M�����ȡ61M������
				// �����Ե���
				adjustValue += ldxAdjustMap.get(n < 61 ? n : 61) == null ? 0
						: ldxAdjustMap.get(n < 61 ? n : 61);// ת��Ϊ�£����>60M�����ȡ61M������
				// ���÷��յ���
				adjustValue += irAdjustMap.get("δ����") == null ? 0 : irAdjustMap.get("δ����");// ��Ҫ��ȡ�ͻ����õȼ�
				
				assetsType = "01";
			} else {
				assetsType = "02";
			}
			if(!("YW201").equals(businessNo)) {//���ڲ�Ʒ��������ǰ����/֧ȡ����
				// ��ǰ����/֧ȡ����
				for (Ftp1PrepayAdjust ftp1PrepayAdjust : prepayList) {
					// �ʲ�������ͬ����Сֵ<term<=���ֵ
					if (ftp1PrepayAdjust.getAssetsType().equals(assetsType)
							&& n > ftp1PrepayAdjust.getMinTermType()
							&& n <= ftp1PrepayAdjust.getMaxTermType()) {
						adjustValue += ftp1PrepayAdjust.getAdjustValue();// ת��Ϊ�£����>60M�����ȡ61M������
						break;
					}
				}
			}
			
		}
		return adjustValue;
	}
	
	/**
	 * ����������ֵ(��������������ϸ���������)���������ޣ��������ֵ =��Ӧ���޵���������*�����������
	 * @param prdtNo
	 * @param term
	 * @param amt
	 * @param dkAdjustArr
	 * @param publicRate
	 * @param rate ���ʣ�����С��ֵ
	 * @return
	 */
	public static double getDkAmtAdjust(String prdtNo, int term, double amt, double[][] dkAdjustArr, Double[][] publicRate,double rate) {
		double adjustValue = 0;
		
		//��ȡ���Ӧ���޵Ļ�׼����
		double jzll=0;
		for (int j = 0; j< publicRate.length; j++) {//��С����<=term<=�������
			if(publicRate[j][0]<=term&&term<=publicRate[j][1]) {
				jzll = publicRate[j][2];
				break;
			}
		}
		double sfbl=(rate/jzll-1);//�����ϸ�����
		
		// '������' ��������        
		double adjustRate = 0;
		if(!Double.isNaN(amt) && amt != 0 && dkAdjustArr!=null && !Double.isNaN(sfbl)) {
			for (int j = 0; j< dkAdjustArr.length; j++) {//��С���<amt<=������С�ϸ�����<=sfbl<����ϸ�����
				if(dkAdjustArr[j][0]<amt && amt<=dkAdjustArr[j][1] && dkAdjustArr[j][2]<=sfbl && sfbl<dkAdjustArr[j][3]) {
					adjustRate = dkAdjustArr[j][4];
					break;
				}
			}
		}
		
		//�������ֵ =��Ӧ���޵���������*�����������
		adjustValue=jzll*adjustRate;
		
		return adjustValue;
	}
	/**
	 * ��ȡ��������й�������
	 * @return  ��ά����     0 ��С���� 1�������  2 ����ֵ               ��λ����
	 */
	public static Double[][] getFtpPublicRate() {
		Double[][] result = null;
		String hsql = "from FtpPublicRate where substr(rateNo,1,1) = '1' order by rateNo";
		List<FtpPublicRate> ftpPublicRateList = CommonFunctions.mydao.query(hsql, null);
		result = new Double[ftpPublicRateList.size()][3];
		for (int i = 0; i < result.length; i++) {
			FtpPublicRate ftpPublicRate = ftpPublicRateList.get(i);
			if(ftpPublicRate.getRateNo().equals("1M1")) {//����������(��)
				result[i][0] = 0.0;
				result[i][1] = 185.0;
				result[i][2] = ftpPublicRate.getRate();
			}else if(ftpPublicRate.getRateNo().equals("1M2")) {//��������һ��(��)   
				result[i][0] = 186.0;
				result[i][1] = 366.0;
				result[i][2] = ftpPublicRate.getRate();
			}else if(ftpPublicRate.getRateNo().equals("1Y1")) {//һ��������(��)
				result[i][0] = 367.0;
				result[i][1] = 1098.0;
				result[i][2] = ftpPublicRate.getRate();
			}else if(ftpPublicRate.getRateNo().equals("1Y2")) {//����������(��) 
				result[i][0] = 1099.0;
				result[i][1] = 1878.0;
				result[i][2] = ftpPublicRate.getRate();
			}else if(ftpPublicRate.getRateNo().equals("1Y3")) {//��������
				result[i][0] = 1879.0;
				result[i][1] = 99999.0;
				result[i][2] = ftpPublicRate.getRate();
			}
		}
		return result;
	}
	 
	 /**
		 * ���� excel
		 * 
		 * @param file
		 *            : Excel�ļ�
		 * @param pojoClass
		 *            : ��Ӧ�ĵ������ (ÿ�м�¼)
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static List<Object> importExcel(File file, Class pojoClass) {
			try {
				// �������File����ΪFileInputStream;
				FileInputStream in = new FileInputStream(file);
				return importExcelByIs(in, pojoClass);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
		/**
		 * ���� excel
		 * 
		 * @param inputstream
		 *            : �ļ�������
		 * @param pojoClass
		 *            : ��Ӧ�ĵ������ (ÿ�м�¼)
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public static List<Object> importExcelByIs(InputStream inputstream,
				Class pojoClass) {
			List<Object> dist = new ArrayList<Object>();
			try {
				// �õ�Ŀ��Ŀ��������е��ֶ��б�
				Field filed[] = pojoClass.getDeclaredFields();
				// �����б���Annotation���ֶΣ�Ҳ�������������ݵ��ֶ�,���뵽һ��map��
				Map<String, Method> fieldSetMap = new HashMap<String, Method>();
				Map<String, Method> fieldSetConvertMap = new HashMap<String, Method>();
				// ѭ����ȡ�����ֶ�
				for (int i = 0; i < filed.length; i++) {
					Field f = filed[i];
					// �õ������ֶ��ϵ�Annotation
					Excel excel = f.getAnnotation(Excel.class);
					// �����ʶ��Annotationd�Ļ�
					if (excel != null) {
						// ����������Annotation���ֶε�Setter����
						String fieldname = f.getName();
						String setMethodName = "set"
								+ fieldname.substring(0, 1).toUpperCase()
								+ fieldname.substring(1);
						// ������õ�method��
						Method setMethod = pojoClass.getMethod(setMethodName,
								new Class[] { f.getType() });
						// �����method��Annotaion������Ϊkey�����롣
						// �������������� ���� ʧ�ܣ����ڴ˴���������Ҫ
						fieldSetMap.put(excel.exportName(), setMethod);
						// �ж��Ƿ���Ҫת��
						if (excel.importConvertSign() == 1) {
							// ----------------------------------------------------------------
							// update-begin--Author:Quainty Date:20130524
							// for��[8]excel����ʱ������
							// ��get/setXxxxConvert�������Ļ���
							// ����ֱ��ʹ�������ݿ�󶨵�Entity����ע����г�ͻ
							StringBuffer setConvertMethodName = new StringBuffer(
									"convertSet");
							setConvertMethodName.append(fieldname.substring(0, 1)
									.toUpperCase());
							setConvertMethodName.append(fieldname.substring(1));
							// update-end--Author:Quainty Date:20130524
							// for��[8]excel����ʱ������
							// ----------------------------------------------------------------
							Method getConvertMethod = pojoClass.getMethod(
									setConvertMethodName.toString(),
									new Class[] { String.class });
							fieldSetConvertMap.put(excel.exportName(),
									getConvertMethod);
						}
					}
				}
				// �������File����ΪFileInputStream;
				// // �õ�������
				HSSFWorkbook book = new HSSFWorkbook(inputstream);
				// // �õ���һҳ
				HSSFSheet sheet = book.getSheetAt(0);
				// for (int i = 1; i < sheet.getLastRowNum() + 1; i++) {
				// HSSFRow row = sheet.getRow(i);
				// for (int j = 0; j < filed.length; j++) {
				// HSSFCell cell = row.getCell(j);
				// System.out.println(cell.getCellType());
				// }
				// }
				// // �õ���һ���������
				Iterator<Row> row = sheet.rowIterator();
				// �õ���һ�У�Ҳ���Ǳ�����
				Row title = row.next();
				// �õ���һ�е�������
				Iterator<Cell> cellTitle = title.cellIterator();
				// ��������������ݷ��뵽һ��map�С�
				Map titlemap = new HashMap();
				// �ӱ����һ�п�ʼ
				int i = 0;
				// ѭ���������е���
				while (cellTitle.hasNext()) {
					Cell cell = cellTitle.next();
					String value = cell.getStringCellValue();
					titlemap.put(i, value);
					i = i + 1;
				}
				// ������ʽ�����ڵ�DateFormat
				// SimpleDateFormat sf;
				while (row.hasNext()) {
					// �����µĵ�һ��
					Row rown = row.next();
					// �е�������
					Iterator<Cell> cellbody = rown.cellIterator();
					// �õ��������ʵ��
					Object tObject = pojoClass.newInstance();
					int k = 0;
					// ����һ�е���
					while (cellbody.hasNext()) {
						Cell cell = cellbody.next();
						// ����õ����еĶ�Ӧ�ı���
						String titleString = (String) titlemap.get(k);
						// �����һ�еı�������е�ĳһ�е�Annotation��ͬ����ô����ô���ĵ�set������������ֵ
						if (fieldSetMap.containsKey(titleString)) {
							Method setMethod = (Method) fieldSetMap.get(titleString);
							// �õ�setter�����Ĳ���
							Type[] ts = setMethod.getGenericParameterTypes();
							// ֻҪһ������
							String xclass = ts[0].toString();
							// �жϲ�������
							if (Cell.CELL_TYPE_STRING == cell.getCellType() && fieldSetConvertMap.containsKey(titleString)) {
								// Excel excel = f.getAnnotation(Excel.class);
								// if(excel.exportFieldWidth()<cell.getStringCellValue().length()){
								//								

								// }
								// Ŀǰֻ֧�ִ�Stringת��
								fieldSetConvertMap.get(titleString).invoke(tObject,
										cell.getStringCellValue());
							} else {
								if (xclass.equals("class java.lang.String")) {
									String cellValue = "";
									// �����һ���ǔ�����ͣ��t�D������
									// if (Cell.CELL_TYPE_NUMERIC == cell
									// .getCellType()
									// && k == 0) {
									// DecimalFormat df = new DecimalFormat("0");
									// cellValue = df.format(cell
									// .getNumericCellValue());
									// } else {
									cell.setCellType(Cell.CELL_TYPE_STRING);
									cellValue = cell.getStringCellValue();
									// }
									// ������Cell�����ͣ�Ȼ��Ϳ��԰Ѵ�������ΪString���Ͷ������ˣ�
									// cell.setCellType(Cell.CELL_TYPE_STRING);
									setMethod.invoke(tObject, cellValue);
								} else if (xclass.equals("class java.util.Date")) {
									// update-start--Author:Quainty Date:20130523
									// for�������������ݵ��벻��(˳������֧����Excel����������)
									Date cellDate = null;
									if (Cell.CELL_TYPE_NUMERIC == cell
											.getCellType()) {
										// ���ڸ�ʽ
										cellDate = cell.getDateCellValue();
									} else { // ȫ��Ϊ�� Cell.CELL_TYPE_STRING: �������
										// yyyy-mm-dd hh:mm:ss �ĸ�ʽ�Ͳ���(wait to
										// do:�о�����)
										cellDate = stringToDate(cell.getStringCellValue());
									}
									setMethod.invoke(tObject, cellDate);
									// // update-start--Author:lihuan Date:20130423
									// for������bug�޸�ֱ�ӽ�������Excel������ֵ�bug���޸�
									// //
									// --------------------------------------------------------------------------------------------
									// String cellValue = cell.getStringCellValue();
									// Date theDate = stringToDate(cellValue);
									// setMethod.invoke(tObject, theDate);
									// // update-end--Author:lihuan Date:20130423
									// for������bug�޸�ֱ�ӽ�������Excel������ֵ�bug���޸�
									// //
									// --------------------------------------------------------------------------------------------
								} else if (xclass.equals("class java.lang.Boolean")) {
									boolean valBool;
									if (Cell.CELL_TYPE_BOOLEAN == cell.getCellType()) {
										valBool = cell.getBooleanCellValue();
									} else {// ȫ��Ϊ�� Cell.CELL_TYPE_STRING
										valBool = cell.getStringCellValue().equalsIgnoreCase("true")
												|| (!cell.getStringCellValue()
														.equals("0"));
									}
									setMethod.invoke(tObject, valBool);
								} else if (xclass.equals("class java.lang.Integer")) {
									Integer valInt;
									if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
										valInt = (new Double(cell.getNumericCellValue())).intValue();
									}
									//lijin======================
									else if(Cell.CELL_TYPE_FORMULA == cell.getCellType()){
										cell.setCellType(Cell.CELL_TYPE_NUMERIC);
										valInt = (new Double(cell.getNumericCellValue())).intValue();
									}
									else {// ȫ��Ϊ�� Cell.CELL_TYPE_STRING
										valInt = new Integer(cell.getStringCellValue());
									}
									setMethod.invoke(tObject, valInt);
								} else if (xclass.equals("class java.lang.Long")) {
									Long valLong;
									if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
										valLong = (new Double(cell.getNumericCellValue())).longValue();
									} else {// ȫ��Ϊ�� Cell.CELL_TYPE_STRING
										valLong = new Long(cell.getStringCellValue());
									}
									setMethod.invoke(tObject, valLong);
								}
								else if (xclass.equals("class java.lang.Double")) {
									Double valDouble;
									if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
										valDouble = (new Double(cell.getNumericCellValue())).doubleValue();
									} 
									else if(Cell.CELL_TYPE_FORMULA == cell.getCellType()){
										cell.setCellType(Cell.CELL_TYPE_NUMERIC);
										valDouble = (new Double(cell.getNumericCellValue())).doubleValue();
									}
									else {// ȫ��Ϊ�� Cell.CELL_TYPE_STRING
										valDouble = new Double(cell.getStringCellValue());
									}
									setMethod.invoke(tObject, valDouble);
								}
							
								else if (xclass.equals("class java.math.BigDecimal")) {
									BigDecimal valDecimal;
									if (Cell.CELL_TYPE_NUMERIC == cell.getCellType()) {
										valDecimal = new BigDecimal(cell.getNumericCellValue());
									} else {// ȫ��Ϊ�� Cell.CELL_TYPE_STRING
										valDecimal = new BigDecimal(cell.getStringCellValue());
									}
									setMethod.invoke(tObject, valDecimal);
									// //
									// ----------------------------------------------------------------
									// // update-begin--Author:sky Date:20130422
									// for��ȡֵ���͵���cell.getNumberCellValue-->>getStringCellValue
									// setMethod.invoke(tObject, new
									// BigDecimal(cell.getStringCellValue()));
									// // update-end--Author:sky Date:20130422
									// for��ȡֵ���͵���
									// //
									// ----------------------------------------------------------------
									// update-end--Author:Quainty Date:20130523
									// for�������������ݵ��벻��(˳������֧����Excel����������)
								}
							}
						}
						// ��һ��
						k = k + 1;
					}
					dist.add(tObject);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return dist;
		}
		/**
		 * �ַ���ת��ΪDate�������ݣ��޶���ʽ YYYY-MM-DD hh:mm:ss����YYYY/MM/DD hh:mm:ss��
		 * 
		 * @param cellValue
		 *            : �ַ������͵���������
		 * @return
		 */
		private static Date stringToDate(String cellValue) {
			if (cellValue.length() > 19)
				cellValue = cellValue.substring(0, 19);
			Calendar calendar = Calendar.getInstance();
			String[] dateStr = cellValue.split(" ");
			String[] dateInfo = dateStr[0].split("-");
			if (dateInfo.length != 3) {
				dateInfo = dateStr[0].split("/"); // �� yyyy/mm/dd �ĸ�ʽҲ֧��
			}
			if (dateInfo.length == 3) {
				int year = Integer.parseInt(dateInfo[0]);
				int month = Integer.parseInt(dateInfo[1]) - 1; // 0~11
				int day = Integer.parseInt(dateInfo[2]);
				calendar.set(year, month, day);
			} else {
				return null; // ��ʽ����ȷ
			}
			if (dateStr.length > 1) {// ��ʱ�䣨�޶���ʽ hh:mm:ss��
				String[] timeStr = dateStr[1].split(":");
				if (timeStr.length == 3) {
					int hour = Integer.parseInt(timeStr[0]);
					int minute = Integer.parseInt(timeStr[1]);
					int second = Integer.parseInt(timeStr[2]);
					calendar.set(Calendar.HOUR_OF_DAY, hour);
					calendar.set(Calendar.MINUTE, minute);
					calendar.set(Calendar.SECOND, second);
				} else {
					return null; // ��ʽ����ȷ
				}
			}
			return calendar.getTime();
		}

	/**
	 * ��ȡʱ�䷶Χ�ڵ�ƽ����ծ����
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, Double> getStockRate(String minDate, String maxDate) {
		DaoFactory df = new DaoFactory();
		Map<String, Double> map = new HashMap<String, Double>();

		String hsql = "select sum(STOCK_YIELD)/count(*), STOCK_TERM from ftp.ftp_stock_yield where " +
				" STOCK_DATE <= '"+maxDate+"' and STOCK_DATE >= '"+minDate+"' group by STOCK_TERM ";
		List list = df.query1(hsql, null);
		if(list==null || list.size()==0){
			return null;
		}
		for(int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			Object[] obj = (Object[])object;
			double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
			map.put(obj[1].toString(), rate);
		}
		return map;
	}


	/**
	 * ��ȡָ�����ڵĹ�ծ����
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, Double> getStockRate(String date) {
		DaoFactory df = new DaoFactory();
		Map<String, Double> map = new HashMap<String, Double>();

		//��ǰ����10��
		String before = CommonFunctions.dateModifyD(date, -10);//���ǵ��нڼ��յ����

		String hsql = "select STOCK_YIELD, STOCK_TERM from ftp.ftp_stock_yield where " +
				" STOCK_DATE = (select max(STOCK_DATE) from ftp.ftp_stock_yield where STOCK_DATE < '"+date+"')"+
				" and STOCK_DATE >= '"+before+"'";
		List list = df.query1(hsql, null);
		if(list==null || list.size()==0){
			return null;
		}
		for(int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			Object[] obj = (Object[])object;
			double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
			map.put(obj[1].toString(), rate);
		}

		return map;
	}


	/**
	 * ��ȡʱ�䷶Χ�ڵ�ƽ��shibor����
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, Double> getShiborRate(String minDate, String maxDate) {
		DaoFactory df = new DaoFactory();
		Map<String, Double> map = new HashMap<String, Double>();

		String hsql = "select sum(Shibor_rate)/count(*), Shibor_term from ftp.ftp_shibor where " +
				" shibor_date <= '"+maxDate+"' and shibor_date >= '"+minDate+"' group by Shibor_term ";
		List list = df.query1(hsql, null);
		if(list==null || list.size()==0){
			return null;
		}
		for(int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			Object[] obj = (Object[])object;
			double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
			map.put(obj[1].toString(), rate);
		}
		return map;
	}


	/**
	 * ��ȡʱ�䷶Χ�ڵ�ƽ��������ͨծ������
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, Double> getCommonRate(String minDate, String maxDate) {
		DaoFactory df = new DaoFactory();
		Map<String, Double> map = new HashMap<String, Double>();

		String hsql = " SELECT SUM(COMMON_RATE-FINANCIAL_RATE)/COUNT(*), FINANCIAL_TERM FROM ("
				+" SELECT  t.FINANCIAL_RATE,  t1.COMMON_RATE,t.FINANCIAL_TERM,t.FINANCIAL_DATE  FROM  ftp.FTP_FINANCIAL_RATE t INNER JOIN ftp.FTP1_COMMON_YIELD t1 ON"
				+" (  t.FINANCIAL_DATE = t1.COMMON_DATE AND t1.TERM_TYPE = t.FINANCIAL_TERM ))"
				+" WHERE  FINANCIAL_DATE>='"+minDate+"' AND FINANCIAL_DATE<='"+maxDate+"'  GROUP BY FINANCIAL_TERM";
		List list = df.query1(hsql, null);
		if(list==null || list.size()==0){
			return null;
		}
		for(int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			Object[] obj = (Object[])object;
			double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
			map.put(obj[1].toString(), rate);
		}
		return map;
	}


	/**
	 * ��ȡָ����Χ��ƽ������ծ������
	 * @return
	 * @throws ParseException
	 */
	public static Map<String, Double> getYieldRate(String minDate, String maxDate) {
		DaoFactory df = new DaoFactory();
		Map<String, Double> map = new HashMap<String, Double>();

		String hsql  =  "SELECT  SUM(FINANCIAL_RATE-STOCK_YIELD)/COUNT(*), STOCK_TERM FROM"
				+" (  SELECT  t.FINANCIAL_RATE, t.FINANCIAL_TERM, t1.STOCK_YIELD,t1.STOCK_TERM,t1.STOCK_DATE  FROM  ftp.FTP_FINANCIAL_RATE t INNER JOIN"
				+"   ftp.FTP_STOCK_YIELD t1  ON ( t.FINANCIAL_DATE = t1.STOCK_DATE AND t1.STOCK_TERM = t.FINANCIAL_TERM)) WHERE STOCK_DATE>='"+minDate+"' and STOCK_DATE<='"+maxDate+"' GROUP BY STOCK_TERM";
		List list = df.query1(hsql, null);
		if(list==null || list.size()==0){
			return null;
		}
		for(int i = 0; i < list.size(); i++) {
			Object object = list.get(i);
			Object[] obj = (Object[])object;
			double rate = obj[0] == null ? 0.0 : Double.valueOf(obj[0].toString());
			map.put(obj[1].toString(), rate);
		}
		return map;
	}


	/**
	 * �����Ӧ���޵ĳ�����
	 * h-p�˲�
	 * @param term
	 * @return
	 */
	public static double computeCdlByHP(int term) {
		DaoFactory df = new DaoFactory();
		//�����»��ܱ��ȡSUM(������ĩ���-�跽��ĩ���)����Ŀ��'20120000','20170000'(��λ���ڴ����˻��ڴ��)
    	/*String sql = "select * from (select nvl(sum(GL_MONTH_CR_BAL-GL_MONTH_DR_BAL),0) bal from F_GL_GL_TY " +
    			                   " where GL_FIRST_LEVEL_GL_COD||GL_SECOND_LEVEL_GL_CD||GL_THIRD_LEVEL_GL_CD in ('20120000','20170000') and GL_OPUN_COD='803999998' and GL_CURR_COD_AUTHOR='01'"
    	                          +" group by GL_YR_N,GLHDD_KEY order by GL_YR_N desc,to_number(GLHDD_KEY) desc)" +
    			   " where rownum <="+term+"";//oracle
    			   //" fetch first "+term+" rows only";//db2
*/
		String sql = "select * from (select nvl(sum(a.crctbl-a.drctbl),0) bal from HHZZ_GLA_GLIS_H a " +
				" where a.itemcd in ('201101') and a.brchcd='C001' and trim(a.crcycd)='1' and a.systid='99' and GELDTP='M' "
				+" order by a.ACCTDT desc)" +
				" where rownum <="+term+"";//oracle
		List list = df.query1(sql, null);
		//HP����������<ckPlan:double[12]>
		//System.out.println("########historyCKList.get(1).getAmt(): "+l.get(1).toString());
		double[] history = new double[20];
		if (list != null && list.size() > 0) {
			history = new double[list.size()];
			for (int i = 0; i < list.size(); i++) {
				history[list.size() - i - 1] = Double.valueOf(list.get(i).toString())/10000;
			}
		}
		System.out.print("h-p�˲���ʵ����ʷ����ֵ��");
		for(int i=0;i<history.length;i++){
			System.out.print(history[i]+",");
		}

		HPFilter hpFilter1=new HPFilter(14400,history,30);
		double[] g1=hpFilter1.computeModel();
		for(double a:g1){
			System.out.print(a+",");
		}
		System.out.println();

		//��  ########################################################
		//�½�C�����---�����Ĵ����<hxck:double[12]>
		//��ȡ��������ֵC
		double C=hpFilter1.getC(0.01);

		double[] hxck = new double[g1.length];
		for(int i=0;i<g1.length;i++){
			if(g1[i]!=0){
				hxck[i]=CastUtil.doubleSet2((g1[i]-C));
			}

		}
		double[] cdls = new double[g1.length];
		double cdl = 0;
		for(int i=0;i<g1.length;i++) {
			cdls[i] = CastUtil.doubleSet2((hxck[i]/g1[i]));
			System.out.println("������11"+i+"="+cdls[i]);
			cdl += cdls[i];
		}
		cdl = cdl/cdls.length;
		System.out.println("��������ֵC="+C);
		System.out.println("������="+cdl);
		return cdl;
	}
		
}