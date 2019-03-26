<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.*,java.util.Map,java.util.List"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Expires " content="0 ">
        <meta http-equiv="Cache-Control " content="no-cache,must-revalidate ">
        <meta http-equiv="Pragma " content="no-cache ">
        <base target="_self"> 
		<title>收益率曲线1</title>
<jsp:include page="commonJs.jsp" />
 </head>
	<body>
 
<%
     String curveType = (String)request.getAttribute("curveType");
     String date = (String)request.getAttribute("date");
     Map<String, double[]> curveMap = (Map<String, double[]>)request.getAttribute("curveMap");
     double[] key = curveMap.get("key");
     double[] keyRate = curveMap.get("keyRate");
     double[] COF1 = curveMap.get("COF1");
     double[] VOF1 = curveMap.get("VOF1");
     double[] COF2 = curveMap.get("COF2");
     double[] VOF2 = curveMap.get("VOF2");
     double[] COF3 = curveMap.get("COF3");
     double[] VOF3 = curveMap.get("VOF3");
     double[] COF4 = curveMap.get("COF4");
     double[] VOF4 = curveMap.get("VOF4");
     //内部:1天、7天、1个月、2个月 、3个月 、6个月、1年、2年、3年、5年、7年、10年、15年、20年、30年
     //市场:1天（即隔夜）、7天、14天、1个月、3个月、6个月、9个月、12个月、1年、2年、3年、5年、7年、10年、15年、20年、30年
     // 从月开始进行计算
     double[] COF_adjust = new double[key.length];//每个节点总的调整值
     double[] VOF_adjust = new double[key.length];
	 double[] y = new double[key.length];
	 for (int i = 0; i < key.length; i++) {
		 y[i] = keyRate[i];
    	 COF_adjust[i] = COF4[i] - keyRate[i];
    	 VOF_adjust[i] = VOF4[i] - keyRate[i];
     }
	 //进行插值
	 SCYTCZlineF F = null;SCYTCZlineF F1 = null;SCYTCZlineF F2 = null;
	 double[] COF = null,VOF = null,COF_01 = null,COF_02 = null,VOF_01 = null,VOF_02 = null,basicCurve = null,basicCurve_01 = null,basicCurve_02 = null; 
	 //y = (x-x1)(y2-y1)/(x2-x1)+y1
     if (curveType.equals("1")) {    
    	 F = SCYTCZ_Compute.getSCYTCZline(key, y);
    	 COF = new double[43];
    	 VOF = new double[43];
    	 basicCurve = new double[43];
    	 basicCurve[0] = F.getY_SCYTCZline(1/30.0);
    	 basicCurve[1] = F.getY_SCYTCZline(7/30.0);
    	 for (int j = 0; j < 11;j++) {
    		 basicCurve[j+2] = F.getY_SCYTCZline(j+1);
    	 }
    	 for (int j = 1; j < 31;j++) {
    		 basicCurve[j+12] = F.getY_SCYTCZline(j*12);
    	 }
    	 COF[0] = F.getY_SCYTCZline(1/30.0) + COF_adjust[0];
         VOF[0] = F.getY_SCYTCZline(1/30.0) + VOF_adjust[0];
         COF[1] = F.getY_SCYTCZline(7/30.0) + COF_adjust[1];
    	 VOF[1] = F.getY_SCYTCZline(7/30.0) + VOF_adjust[1];
    	 COF[2] = F.getY_SCYTCZline(1) + COF_adjust[2];
         VOF[2] = F.getY_SCYTCZline(1) + VOF_adjust[2];
         COF[3] = F.getY_SCYTCZline(2) + COF_adjust[3];
    	 VOF[3] = F.getY_SCYTCZline(2) + VOF_adjust[3];
    	 COF[4] = F.getY_SCYTCZline(3) + COF_adjust[4];
    	 VOF[4] = F.getY_SCYTCZline(3) + VOF_adjust[4];
    	 COF[5] = F.getY_SCYTCZline(4) + (4-3)*(COF_adjust[5]-COF_adjust[4])/(6-3)+COF_adjust[4];
    	 VOF[5] = F.getY_SCYTCZline(4) + (4-3)*(VOF_adjust[5]-VOF_adjust[4])/(6-3)+VOF_adjust[4];
    	 COF[6] = F.getY_SCYTCZline(5) + (5-3)*(COF_adjust[5]-COF_adjust[4])/(6-3)+COF_adjust[4];
    	 VOF[6] = F.getY_SCYTCZline(5) + (5-3)*(VOF_adjust[5]-VOF_adjust[4])/(6-3)+VOF_adjust[4];
    	 COF[7] = F.getY_SCYTCZline(6) + COF_adjust[5];
    	 VOF[7] = F.getY_SCYTCZline(6) + VOF_adjust[5];
    	 COF[8] = F.getY_SCYTCZline(7) + (7-6)*(COF_adjust[6]-COF_adjust[5])/(12-6)+COF_adjust[5];
    	 VOF[8] = F.getY_SCYTCZline(7) + (7-6)*(VOF_adjust[6]-VOF_adjust[5])/(12-6)+VOF_adjust[5];
    	 COF[9] = F.getY_SCYTCZline(8) + (8-6)*(COF_adjust[6]-COF_adjust[5])/(12-6)+COF_adjust[5];
    	 VOF[9] = F.getY_SCYTCZline(8) + (8-6)*(VOF_adjust[6]-VOF_adjust[5])/(12-6)+VOF_adjust[5];
    	 COF[10] = F.getY_SCYTCZline(9) + (9-6)*(COF_adjust[6]-COF_adjust[5])/(12-6)+COF_adjust[5];
    	 VOF[10] = F.getY_SCYTCZline(9) + (9-6)*(VOF_adjust[6]-VOF_adjust[5])/(12-6)+VOF_adjust[5];
    	 COF[11] = F.getY_SCYTCZline(10) + (10-6)*(COF_adjust[6]-COF_adjust[5])/(12-6)+COF_adjust[5];
    	 VOF[11] = F.getY_SCYTCZline(10) + (10-6)*(VOF_adjust[6]-VOF_adjust[5])/(12-6)+VOF_adjust[5];
    	 COF[12] = F.getY_SCYTCZline(11) + (11-6)*(COF_adjust[6]-COF_adjust[5])/(12-6)+COF_adjust[5];
    	 VOF[12] = F.getY_SCYTCZline(11) + (11-6)*(VOF_adjust[6]-VOF_adjust[5])/(12-6)+VOF_adjust[5];
    	 COF[13] = F.getY_SCYTCZline(12) + COF_adjust[6];
    	 VOF[13] = F.getY_SCYTCZline(12) + VOF_adjust[6];
         COF[14] = F.getY_SCYTCZline(24) + COF_adjust[7];
         VOF[14] = F.getY_SCYTCZline(24) + VOF_adjust[7];
         COF[15] = F.getY_SCYTCZline(36) + COF_adjust[8];
         VOF[15] = F.getY_SCYTCZline(36) + VOF_adjust[8];
         COF[16] = F.getY_SCYTCZline(48) + (48-36)*(COF_adjust[9]-COF_adjust[8])/(60-36)+COF_adjust[8];
    	 VOF[16] = F.getY_SCYTCZline(48) + (48-36)*(VOF_adjust[9]-VOF_adjust[8])/(60-36)+VOF_adjust[8];
    	 COF[17] = F.getY_SCYTCZline(60) + COF_adjust[9];
         VOF[17] = F.getY_SCYTCZline(60) + VOF_adjust[9];
         COF[18] = F.getY_SCYTCZline(72) + (72-60)*(COF_adjust[10]-COF_adjust[9])/(84-60)+COF_adjust[9];
    	 VOF[18] = F.getY_SCYTCZline(72) + (72-60)*(VOF_adjust[10]-VOF_adjust[9])/(84-60)+VOF_adjust[9];
    	 COF[19] = F.getY_SCYTCZline(84) + COF_adjust[10];
         VOF[19] = F.getY_SCYTCZline(84) + VOF_adjust[10];
         COF[20] = F.getY_SCYTCZline(96) + (96-84)*(COF_adjust[11]-COF_adjust[8])/(120-84)+COF_adjust[8];
    	 VOF[20] = F.getY_SCYTCZline(96) + (96-84)*(VOF_adjust[11]-VOF_adjust[8])/(120-84)+VOF_adjust[8];
    	 COF[21] = F.getY_SCYTCZline(108) + (96-84)*(COF_adjust[11]-COF_adjust[8])/(120-84)+COF_adjust[8];
    	 VOF[21] = F.getY_SCYTCZline(108) + (96-84)*(VOF_adjust[11]-VOF_adjust[8])/(120-84)+VOF_adjust[8];
    	 COF[22] = F.getY_SCYTCZline(120) + COF_adjust[11];
         VOF[22] = F.getY_SCYTCZline(120) + VOF_adjust[11];
         COF[23] = F.getY_SCYTCZline(132) + (132-120)*(COF_adjust[12]-COF_adjust[11])/(180-120)+COF_adjust[11];
    	 VOF[23] = F.getY_SCYTCZline(132) + (132-120)*(VOF_adjust[12]-VOF_adjust[11])/(180-120)+VOF_adjust[11];
    	 COF[24] = F.getY_SCYTCZline(144) + (144-120)*(COF_adjust[12]-COF_adjust[11])/(180-120)+COF_adjust[11];
    	 VOF[24] = F.getY_SCYTCZline(144) + (144-120)*(VOF_adjust[12]-VOF_adjust[11])/(180-120)+VOF_adjust[11];
    	 COF[25] = F.getY_SCYTCZline(156) + (156-120)*(COF_adjust[12]-COF_adjust[11])/(180-120)+COF_adjust[11];
    	 VOF[25] = F.getY_SCYTCZline(156) + (156-120)*(VOF_adjust[12]-VOF_adjust[11])/(180-120)+VOF_adjust[11];
    	 COF[26] = F.getY_SCYTCZline(168) + (168-120)*(COF_adjust[12]-COF_adjust[11])/(180-120)+COF_adjust[11];
    	 VOF[26] = F.getY_SCYTCZline(168) + (168-120)*(VOF_adjust[12]-VOF_adjust[11])/(180-120)+VOF_adjust[11];
    	 COF[27] = F.getY_SCYTCZline(180) + COF_adjust[12];
         VOF[27] = F.getY_SCYTCZline(180) + VOF_adjust[12];
         COF[28] = F.getY_SCYTCZline(192) + (192-180)*(COF_adjust[13]-COF_adjust[12])/(240-180)+COF_adjust[12];
    	 VOF[28] = F.getY_SCYTCZline(192) + (192-180)*(VOF_adjust[13]-VOF_adjust[12])/(240-180)+VOF_adjust[12];
    	 COF[29] = F.getY_SCYTCZline(204) + (204-180)*(COF_adjust[13]-COF_adjust[12])/(240-180)+COF_adjust[12];
    	 VOF[29] = F.getY_SCYTCZline(204) + (204-180)*(VOF_adjust[13]-VOF_adjust[12])/(240-180)+VOF_adjust[12];
    	 COF[30] = F.getY_SCYTCZline(216) + (216-180)*(COF_adjust[13]-COF_adjust[12])/(240-180)+COF_adjust[12];
    	 VOF[30] = F.getY_SCYTCZline(216) + (216-180)*(VOF_adjust[13]-VOF_adjust[12])/(240-180)+VOF_adjust[12];
    	 COF[31] = F.getY_SCYTCZline(228) + (228-180)*(COF_adjust[13]-COF_adjust[12])/(240-180)+COF_adjust[12];
    	 VOF[31] = F.getY_SCYTCZline(228) + (228-180)*(VOF_adjust[13]-VOF_adjust[12])/(240-180)+VOF_adjust[12];
    	 COF[32] = F.getY_SCYTCZline(240) + COF_adjust[13];
         VOF[32] = F.getY_SCYTCZline(240) + VOF_adjust[13];
         COF[33] = F.getY_SCYTCZline(252) + (252-240)*(COF_adjust[14]-COF_adjust[13])/(360-240)+COF_adjust[13];
    	 VOF[33] = F.getY_SCYTCZline(252) + (252-240)*(VOF_adjust[14]-VOF_adjust[13])/(360-240)+VOF_adjust[13];
    	 COF[34] = F.getY_SCYTCZline(264) + (264-240)*(COF_adjust[14]-COF_adjust[13])/(360-240)+COF_adjust[13];
    	 VOF[34] = F.getY_SCYTCZline(264) + (264-240)*(VOF_adjust[14]-VOF_adjust[13])/(360-240)+VOF_adjust[13];
    	 COF[35] = F.getY_SCYTCZline(276) + (276-240)*(COF_adjust[14]-COF_adjust[13])/(360-240)+COF_adjust[13];
    	 VOF[35] = F.getY_SCYTCZline(276) + (276-240)*(VOF_adjust[14]-VOF_adjust[13])/(360-240)+VOF_adjust[13];
    	 COF[36] = F.getY_SCYTCZline(288) + (288-240)*(COF_adjust[14]-COF_adjust[13])/(360-240)+COF_adjust[13];
    	 VOF[36] = F.getY_SCYTCZline(288) + (288-240)*(VOF_adjust[14]-VOF_adjust[13])/(360-240)+VOF_adjust[13];
    	 COF[37] = F.getY_SCYTCZline(300) + (300-240)*(COF_adjust[14]-COF_adjust[13])/(360-240)+COF_adjust[13];
    	 VOF[37] = F.getY_SCYTCZline(300) + (300-240)*(VOF_adjust[14]-VOF_adjust[13])/(360-240)+VOF_adjust[13];
    	 COF[38] = F.getY_SCYTCZline(312) + (312-240)*(COF_adjust[14]-COF_adjust[13])/(360-240)+COF_adjust[13];
    	 VOF[38] = F.getY_SCYTCZline(312) + (312-240)*(VOF_adjust[14]-VOF_adjust[13])/(360-240)+VOF_adjust[13];
    	 COF[39] = F.getY_SCYTCZline(324) + (324-240)*(COF_adjust[14]-COF_adjust[13])/(360-240)+COF_adjust[13];
    	 VOF[39] = F.getY_SCYTCZline(324) + (324-240)*(VOF_adjust[14]-VOF_adjust[13])/(360-240)+VOF_adjust[13];
    	 COF[40] = F.getY_SCYTCZline(336) + (336-240)*(COF_adjust[14]-COF_adjust[13])/(360-240)+COF_adjust[13];
    	 VOF[40] = F.getY_SCYTCZline(336) + (336-240)*(VOF_adjust[14]-VOF_adjust[13])/(360-240)+VOF_adjust[13];
    	 COF[41] = F.getY_SCYTCZline(348) + (348-240)*(COF_adjust[14]-COF_adjust[13])/(360-240)+COF_adjust[13];
    	 VOF[41] = F.getY_SCYTCZline(348) + (348-240)*(VOF_adjust[14]-VOF_adjust[13])/(360-240)+VOF_adjust[13];
    	 COF[42] = F.getY_SCYTCZline(360) + COF_adjust[14];
         VOF[42] = F.getY_SCYTCZline(360) + VOF_adjust[14];
         session.setAttribute("basicCurve",basicCurve);
         session.setAttribute("COF",COF);
         session.setAttribute("VOF",VOF);
     }else { 
    	 //市场:1天（即隔夜）、7天、14天、1个月、3个月、6个月、9个月、12个月、1年、2年、3年、5年、7年、10年、15年、20年、30年
         //分成两段，shibor和国债
    	 double[] key_01 = new double[8];
    	 double[] key_02 = new double[9];
    	 double[] y_01 = new double[8];
    	 double[] y_02 = new double[9];
    	 System.arraycopy(key,0,key_01, 0, key_01.length);
    	 System.arraycopy(key,8,key_02, 0, key_02.length);
    	 System.arraycopy(y,0,y_01, 0, y_01.length);
    	 System.arraycopy(y,8,y_02, 0, y_02.length);
    	 F1 = SCYTCZ_Compute.getSCYTCZline(key_01, y_01);
    	 F2 = SCYTCZ_Compute.getSCYTCZline(key_02, y_02);
    	 COF_01 = new double[15];
    	 COF_02 = new double[30];
    	 VOF_01 = new double[15];
    	 VOF_02 = new double[30];
    	 basicCurve_01 = new double[15];
    	 basicCurve_02 = new double[30];
    	 basicCurve_01[0] = F1.getY_SCYTCZline(1/30.0);
    	 basicCurve_01[1] = F1.getY_SCYTCZline(7/30.0);
    	 basicCurve_01[2] = F1.getY_SCYTCZline(14/30.0);
    	 for (int j = 0; j < 12;j++) {
    		 basicCurve_01[j+3] = F1.getY_SCYTCZline(j+1);
    	 }
    	 for (int j = 0; j < 30;j++) {
    		 basicCurve_02[j] = F2.getY_SCYTCZline((j+1)*12);
    	 }
    	 COF_01[0] = F1.getY_SCYTCZline(1/30.0) + COF_adjust[0];
         VOF_01[0] = F1.getY_SCYTCZline(1/30.0) + VOF_adjust[0];
         COF_01[1] = F1.getY_SCYTCZline(7/30.0) + COF_adjust[1];
    	 VOF_01[1] = F1.getY_SCYTCZline(7/30.0) + VOF_adjust[1];
    	 COF_01[2] = F1.getY_SCYTCZline(14/30.0) + COF_adjust[2];
    	 VOF_01[2] = F1.getY_SCYTCZline(14/30.0) + VOF_adjust[2];
    	 COF_01[3] = F1.getY_SCYTCZline(1) + COF_adjust[3];
    	 VOF_01[3] = F1.getY_SCYTCZline(1) + VOF_adjust[3];
    	 COF_01[4] = F1.getY_SCYTCZline(2) + (2-1)*(COF_adjust[4]-COF_adjust[3])/(3-1)+COF_adjust[3];
    	 VOF_01[4] = F1.getY_SCYTCZline(2) + (2-1)*(VOF_adjust[4]-VOF_adjust[3])/(3-1)+VOF_adjust[3];
    	 COF_01[5] = F1.getY_SCYTCZline(3) + COF_adjust[4];
    	 VOF_01[5] = F1.getY_SCYTCZline(3) + VOF_adjust[4];
    	 COF_01[6] = F1.getY_SCYTCZline(4) + (4-3)*(COF_adjust[5]-COF_adjust[4])/(6-3)+COF_adjust[4];
    	 VOF_01[6] = F1.getY_SCYTCZline(4) + (4-3)*(VOF_adjust[5]-VOF_adjust[4])/(6-3)+VOF_adjust[4];
    	 COF_01[7] = F1.getY_SCYTCZline(5) + (5-3)*(COF_adjust[5]-COF_adjust[4])/(6-3)+COF_adjust[4];
    	 VOF_01[7] = F1.getY_SCYTCZline(5) + (5-3)*(VOF_adjust[5]-VOF_adjust[4])/(6-3)+VOF_adjust[4];
    	 COF_01[8] = F1.getY_SCYTCZline(6) + COF_adjust[5];
    	 VOF_01[8] = F1.getY_SCYTCZline(6) + VOF_adjust[5];
    	 COF_01[9] = F1.getY_SCYTCZline(7) + (7-6)*(COF_adjust[6]-COF_adjust[5])/(9-6)+COF_adjust[5];
    	 VOF_01[9] = F1.getY_SCYTCZline(7) + (7-6)*(VOF_adjust[6]-VOF_adjust[5])/(9-6)+VOF_adjust[5];
    	 COF_01[10] = F1.getY_SCYTCZline(8) + (8-6)*(COF_adjust[6]-COF_adjust[5])/(9-6)+COF_adjust[5];
    	 VOF_01[10] = F1.getY_SCYTCZline(8) + (8-6)*(VOF_adjust[6]-VOF_adjust[5])/(9-6)+VOF_adjust[5];
    	 COF_01[11] = F1.getY_SCYTCZline(9) + COF_adjust[6];
    	 VOF_01[11] = F1.getY_SCYTCZline(9) + VOF_adjust[6];
    	 COF_01[12] = F1.getY_SCYTCZline(10) + (10-9)*(COF_adjust[7]-COF_adjust[6])/(12-9)+COF_adjust[6];
    	 VOF_01[12] = F1.getY_SCYTCZline(10) + (10-9)*(VOF_adjust[7]-VOF_adjust[6])/(12-9)+VOF_adjust[6];
    	 COF_01[13] = F1.getY_SCYTCZline(11) + (11-9)*(COF_adjust[7]-COF_adjust[6])/(12-9)+COF_adjust[6];
    	 VOF_01[13] = F1.getY_SCYTCZline(11) + (11-9)*(VOF_adjust[7]-VOF_adjust[6])/(12-9)+VOF_adjust[6];
    	 COF_01[14] = F1.getY_SCYTCZline(12) + COF_adjust[7];
    	 VOF_01[14] = F1.getY_SCYTCZline(12) + VOF_adjust[7];
    	 COF_02[0] = F2.getY_SCYTCZline(12) + COF_adjust[8];
         VOF_02[0] = F2.getY_SCYTCZline(12) + VOF_adjust[8];
         COF_02[1] = F2.getY_SCYTCZline(24) + COF_adjust[9];
         VOF_02[1] = F2.getY_SCYTCZline(24) + VOF_adjust[9];
         COF_02[2] = F2.getY_SCYTCZline(36) + COF_adjust[10];
         VOF_02[2] = F2.getY_SCYTCZline(36) + VOF_adjust[10];
         COF_02[3] = F2.getY_SCYTCZline(48) + (48-36)*(COF_adjust[11]-COF_adjust[10])/(60-36)+COF_adjust[10];
    	 VOF_02[3] = F2.getY_SCYTCZline(48) + (48-36)*(VOF_adjust[11]-VOF_adjust[10])/(60-36)+VOF_adjust[10];
    	 COF_02[4] = F2.getY_SCYTCZline(60) + COF_adjust[11];
         VOF_02[4] = F2.getY_SCYTCZline(60) + VOF_adjust[11];
         COF_02[5] = F2.getY_SCYTCZline(72) + (72-60)*(COF_adjust[12]-COF_adjust[11])/(84-60)+COF_adjust[11];
    	 VOF_02[5] = F2.getY_SCYTCZline(72) + (72-60)*(VOF_adjust[12]-VOF_adjust[11])/(84-60)+VOF_adjust[11];
    	 COF_02[6] = F2.getY_SCYTCZline(84) + COF_adjust[12];
         VOF_02[6] = F2.getY_SCYTCZline(84) + VOF_adjust[12];
         COF_02[7] = F2.getY_SCYTCZline(96) + (96-84)*(COF_adjust[13]-COF_adjust[12])/(120-84)+COF_adjust[12];
    	 VOF_02[7] = F2.getY_SCYTCZline(96) + (96-84)*(VOF_adjust[13]-VOF_adjust[12])/(120-84)+VOF_adjust[12];
    	 COF_02[8] = F2.getY_SCYTCZline(108) + (108-84)*(COF_adjust[13]-COF_adjust[12])/(120-84)+COF_adjust[12];
    	 VOF_02[8] = F2.getY_SCYTCZline(108) + (108-84)*(VOF_adjust[13]-VOF_adjust[12])/(120-84)+VOF_adjust[12];
    	 COF_02[9] = F2.getY_SCYTCZline(120) + COF_adjust[13];
         VOF_02[9] = F2.getY_SCYTCZline(120) + VOF_adjust[13];
         COF_02[10] = F2.getY_SCYTCZline(132) + (132-120)*(COF_adjust[14]-COF_adjust[13])/(180-120)+COF_adjust[13];
    	 VOF_02[10] = F2.getY_SCYTCZline(132) + (132-120)*(VOF_adjust[14]-VOF_adjust[13])/(180-120)+VOF_adjust[13];
    	 COF_02[11] = F2.getY_SCYTCZline(144) + (144-120)*(COF_adjust[14]-COF_adjust[13])/(180-120)+COF_adjust[13];
    	 VOF_02[11] = F2.getY_SCYTCZline(144) + (144-120)*(VOF_adjust[14]-VOF_adjust[13])/(180-120)+VOF_adjust[13];
    	 COF_02[12] = F2.getY_SCYTCZline(156) + (156-120)*(COF_adjust[14]-COF_adjust[13])/(180-120)+COF_adjust[13];
    	 VOF_02[12] = F2.getY_SCYTCZline(156) + (156-120)*(VOF_adjust[14]-VOF_adjust[13])/(180-120)+VOF_adjust[13];
    	 COF_02[13] = F2.getY_SCYTCZline(168) + (168-120)*(COF_adjust[14]-COF_adjust[13])/(180-120)+COF_adjust[13];
    	 VOF_02[13] = F2.getY_SCYTCZline(168) + (168-120)*(VOF_adjust[14]-VOF_adjust[13])/(180-120)+VOF_adjust[13];
    	 COF_02[14] = F2.getY_SCYTCZline(180) + COF_adjust[14];
         VOF_02[14] = F2.getY_SCYTCZline(180) + VOF_adjust[14];
         COF_02[15] = F2.getY_SCYTCZline(192) + (192-180)*(COF_adjust[15]-COF_adjust[14])/(240-180)+COF_adjust[14];
    	 VOF_02[15] = F2.getY_SCYTCZline(192) + (192-180)*(VOF_adjust[15]-VOF_adjust[14])/(240-180)+VOF_adjust[14];
    	 COF_02[16] = F2.getY_SCYTCZline(204) + (204-180)*(COF_adjust[15]-COF_adjust[14])/(240-180)+COF_adjust[14];
    	 VOF_02[16] = F2.getY_SCYTCZline(204) + (204-180)*(VOF_adjust[15]-VOF_adjust[14])/(240-180)+VOF_adjust[14];
    	 COF_02[17] = F2.getY_SCYTCZline(216) + (216-180)*(COF_adjust[15]-COF_adjust[14])/(240-180)+COF_adjust[14];
    	 VOF_02[17] = F2.getY_SCYTCZline(216) + (216-180)*(VOF_adjust[15]-VOF_adjust[14])/(240-180)+VOF_adjust[14];
    	 COF_02[18] = F2.getY_SCYTCZline(228) + (228-180)*(COF_adjust[15]-COF_adjust[14])/(240-180)+COF_adjust[14];
    	 VOF_02[18] = F2.getY_SCYTCZline(228) + (228-180)*(VOF_adjust[15]-VOF_adjust[14])/(240-180)+VOF_adjust[14];
    	 COF_02[19] = F2.getY_SCYTCZline(240) + COF_adjust[15];
         VOF_02[19] = F2.getY_SCYTCZline(240) + VOF_adjust[15];
         COF_02[20] = F2.getY_SCYTCZline(252) + (252-240)*(COF_adjust[16]-COF_adjust[15])/(360-240)+COF_adjust[15];
    	 VOF_02[20] = F2.getY_SCYTCZline(252) + (252-240)*(VOF_adjust[16]-VOF_adjust[15])/(360-240)+VOF_adjust[15];
    	 COF_02[21] = F2.getY_SCYTCZline(264) + (264-240)*(COF_adjust[16]-COF_adjust[15])/(360-240)+COF_adjust[15];
    	 VOF_02[21] = F2.getY_SCYTCZline(264) + (264-240)*(VOF_adjust[16]-VOF_adjust[15])/(360-240)+VOF_adjust[15];
    	 COF_02[22] = F2.getY_SCYTCZline(276) + (276-240)*(COF_adjust[16]-COF_adjust[15])/(360-240)+COF_adjust[15];
    	 VOF_02[22] = F2.getY_SCYTCZline(276) + (276-240)*(VOF_adjust[16]-VOF_adjust[15])/(360-240)+VOF_adjust[15];
    	 COF_02[23] = F2.getY_SCYTCZline(288) + (288-240)*(COF_adjust[16]-COF_adjust[15])/(360-240)+COF_adjust[15];
    	 VOF_02[23] = F2.getY_SCYTCZline(288) + (288-240)*(VOF_adjust[16]-VOF_adjust[15])/(360-240)+VOF_adjust[15];
    	 COF_02[24] = F2.getY_SCYTCZline(300) + (300-240)*(COF_adjust[16]-COF_adjust[15])/(360-240)+COF_adjust[15];
    	 VOF_02[24] = F2.getY_SCYTCZline(300) + (300-240)*(VOF_adjust[16]-VOF_adjust[15])/(360-240)+VOF_adjust[15];
    	 COF_02[25] = F2.getY_SCYTCZline(312) + (312-240)*(COF_adjust[16]-COF_adjust[15])/(360-240)+COF_adjust[15];
    	 VOF_02[25] = F2.getY_SCYTCZline(312) + (312-240)*(VOF_adjust[16]-VOF_adjust[15])/(360-240)+VOF_adjust[15];
    	 COF_02[26] = F2.getY_SCYTCZline(324) + (324-240)*(COF_adjust[16]-COF_adjust[15])/(360-240)+COF_adjust[15];
    	 VOF_02[26] = F2.getY_SCYTCZline(324) + (324-240)*(VOF_adjust[16]-VOF_adjust[15])/(360-240)+VOF_adjust[15];
    	 COF_02[27] = F2.getY_SCYTCZline(336) + (336-240)*(COF_adjust[16]-COF_adjust[15])/(360-240)+COF_adjust[15];
    	 VOF_02[27] = F2.getY_SCYTCZline(336) + (336-240)*(VOF_adjust[16]-VOF_adjust[15])/(360-240)+VOF_adjust[15];
    	 COF_02[28] = F2.getY_SCYTCZline(348) + (348-240)*(COF_adjust[16]-COF_adjust[15])/(360-240)+COF_adjust[15];
    	 VOF_02[28] = F2.getY_SCYTCZline(348) + (348-240)*(VOF_adjust[16]-VOF_adjust[15])/(360-240)+VOF_adjust[15];
    	 COF_02[29] = F2.getY_SCYTCZline(360) + COF_adjust[16];
         VOF_02[29] = F2.getY_SCYTCZline(360) + VOF_adjust[16];
         session.setAttribute("basicCurve_01",basicCurve_01);
         session.setAttribute("basicCurve_02",basicCurve_02);
         session.setAttribute("COF_01",COF_01);
         session.setAttribute("VOF_01",VOF_01);
         session.setAttribute("COF_02",COF_02);
         session.setAttribute("VOF_02",VOF_02);
     }
     %>
     <%String url = "";
     if (curveType.equals("1"))  {
    	 url="/ftp/pages/ul04_curve_internal.jsp?curveType=1";
     }else{
    	 url="/ftp/pages/ul04_curve_market.jsp?curveType=2";
     }%>
  
    <iframe src="<%=url %>" id="iframe" width="100%"
									height="370px" frameborder="no" marginwidth="0" marginheight="0"
									scrolling="no" allowtransparency="yes" align="middle"></iframe>
     <form name="form1" method="post" action="<%=request.getContextPath()%>/UL04_save.action" id="save">
  <div align="center">
     <input class="button" type="button" value="发&nbsp;&nbsp;布" onclick="doSave(this.form)"/>&nbsp;
	 <input class="button" type="button" value="试发布" onclick="doSfb(this.form)"/>&nbsp;
	 <input class="button" type="button" value="导&nbsp;&nbsp;出" onclick="doExport()"/>
	      <input type="hidden" value="<%=request.getAttribute("optionsCost") %>" name="optionsCost"/>
	      <input type="hidden" value="<%=request.getAttribute("adjustKeyC") %>" name="adjustKeyC"/>
	      <input type="hidden" value="<%=request.getAttribute("adjustRateC") %>" name="adjustRateC"/>
	      <input type="hidden" value="<%=request.getAttribute("brNo") %>" name="brNo" id="brNo"/>
	      <input type="hidden" value="<%=curveType %>" name="curveType" id="curveType"/>
	      <input type="hidden" value="<%=date %>" name="date" id="date"/>
	 <%for (int i = 0; i < key.length; i++) {%>
	      <input type="hidden" value="<%=key[i] %>" name="key" id="key" />
	      <input type="hidden" value="<%=COF_adjust[i] %>" name="COF_adjust" id="COF_adjust"/>
	      <input type="hidden" value="<%=VOF_adjust[i] %>" name="VOF_adjust" id="VOF_adjust"/>
	 <%} %>
	 <%if (curveType.equals("1")) {
	     double[][] matics = F.A;
         for (int i = 0; i < matics.length; i++) {%>
          <input type="hidden" value="<%=matics[i][0] %>" name="a3" id="a3"/>
	      <input type="hidden" value="<%=matics[i][1] %>" name="b2" id="b2"/>
	      <input type="hidden" value="<%=matics[i][2] %>" name="c1" id="c1"/>
	      <input type="hidden" value="<%=matics[i][3] %>" name="d0" id="d0"/>
	 <% }
       }else{
         double[][] matics1 = F1.A;
         double[][] matics2 = F2.A;
         for (int i = 0; i < matics1.length; i++) {%>
          <input type="hidden" value="<%=matics1[i][0] %>" name="a3_1" id="a3_1"/>
	      <input type="hidden" value="<%=matics1[i][1] %>" name="b2_1" id="b2_1"/>
	      <input type="hidden" value="<%=matics1[i][2] %>" name="c1_1" id="c1_1"/>
	      <input type="hidden" value="<%=matics1[i][3] %>" name="d0_1" id="d0_1"/>
       <%}
         for (int i = 0; i < matics2.length; i++) {%>
          <input type="hidden" value="<%=matics2[i][0] %>" name="a3_2" id="a3_2"/>
	      <input type="hidden" value="<%=matics2[i][1] %>" name="b2_2" id="b2_2"/>
	      <input type="hidden" value="<%=matics2[i][2] %>" name="c1_2" id="c1_2"/>
	      <input type="hidden" value="<%=matics2[i][3] %>" name="d0_2" id="d0_2"/>
        <%}
        }%>
	      
  </div>
  <br/>
  <div align="center"><font style="font-size: 15px; font-weight: bold;"><%=date %> FTP定价关键点分析表—<%=curveType.equals("1")?"存款":"负债" %></font></div>
	<table class="table" align="center" cellspacing="0"
				cellpadding="0" style="border-top: none;" id="">
				<tr height="35">
	   <th width="30">序号</th>
	   <th width="80">基准点</th>
	   <th>利率（%）</th>
	   <th>信用风险修正（%）</th>
	   <th>存款准备金修正（%）</th>
	   <th>期权修正（%）</th>
	   <th>政策性调整（%）</th>
	   <th>FTP（%）</th>
	 </tr>
	 <%for (int i = 0; i < key.length; i++) { %>
	 <tr>
	     <td align="center"><%=i+1 %></td>
	     <td><%=key[i] < 1 ? (int)(key[i]*30)+"天" : (key[i] < 12 ?  (int)key[i]+"个月" : ((int)(key[i]/12)+"年"+(curveType.equals("2")?(i==7?"shibor":(i==8?"国债":"")):""))) %></td>
	     <td><%=CommonFunctions.doubleFormat(keyRate[i]*100, 4) %></td>
	     <td><%=CommonFunctions.doubleFormat((VOF1[i]-keyRate[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doubleFormat((VOF2[i]-VOF1[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doubleFormat((VOF3[i]-VOF2[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doubleFormat((VOF4[i]-VOF3[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doubleFormat(VOF4[i]*100, 4) %></td>
	 </tr>
	 <%} %>
	</table>
	<div align="center"><font style="font-size: 15px; font-weight: bold;"><%=date %> FTP定价关键点分析表—<%=curveType.equals("1")?"贷款":"资产" %></font></div>
	<table class="table" align="center" cellspacing="0"
				cellpadding="0" style="border-top: none;" id="ta%roduct">
				<tr height="35">
	   <th width="30">序号</th>
	   <th width="80">基准点</th>
	   <th>利率（%）</th>
	   <th>信用风险修正（%）</th>
	   <th>存款准备金修正（%）</th>
	   <th>期权修正（%）</th>
	   <th>政策性调整（%）</th>
	   <th>FTP（%）</th>
	 </tr>
	 <%for (int i = 0; i < key.length; i++) { %>
	 <tr>
	     <td align="center"><%=i+1 %></td>
	     <td><%=key[i] < 1 ? (int)(key[i]*30)+"天" : (key[i] < 12 ?  (int)key[i]+"个月" : ((int)(key[i]/12)+"年"+(curveType.equals("2")?(i==7?"shibor":(i==8?"国债":"")):""))) %></td>
	     <td><%=CommonFunctions.doubleFormat(keyRate[i]*100, 4) %></td>
	     <td><%=CommonFunctions.doubleFormat((COF1[i]-keyRate[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doubleFormat((COF2[i]-COF1[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doubleFormat((COF3[i]-COF2[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doubleFormat((COF4[i]-COF3[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doubleFormat(COF4[i]*100, 4) %></td>
	 </tr>
	 <%} %>
	</table>
</form>	
<%--<form name="form2"><input type="hidden" value="<%=date %>" name="date"/>--%>
<%--	 <input type="hidden" value="<%=curveType %>" name="curveType"/>--%>
<%--	 </form>--%>
	<script type="text/javascript">
function doSave(Form)
{
	$('save').request({   
        method:"post",
        parameters:{t:new Date().getTime()},
        onComplete: function(resp){ 
            if(resp.responseText != "") {
		    	   art.dialog({
	               	    title:'失败',
	           		    icon: 'error',
	           		    content: resp.responseText,
	           		    cancelVal: '关闭',
	           		    cancel: true
	            	 });
            }else{
 	    	   art.dialog({
             	    title:'成功',
         		    icon: 'succeed',
         		    content: '发布成功！',
          		    cancelVal: '关闭',
          		    cancel: true
          	 });
            }
            //alert("保存成功！！");
      } 
    });
} 
function doSfb(Form)
{
	$('save').action="<%=request.getContextPath()%>/UL04_sfb.action";
	$('save').request({   
        method:"post",
        parameters:{t:new Date().getTime()},
        onComplete: function(resp){ 
            if(resp.responseText != "") {
		    	   art.dialog({
	               	    title:'失败',
	           		    icon: 'error',
	           		    content: resp.responseText,
	           		    cancelVal: '关闭',
	           		    cancel: true
	            	 });
            }else{
 	    	   art.dialog({
             	    title:'成功',
         		    icon: 'succeed',
         		    content: '试发布成功！',
          		    cancelVal: '关闭',
          		    cancel: true
          	 });
            }
            //alert("保存成功！！");
      } 
    });
	$('save').action="<%=request.getContextPath()%>/UL04_save.action";
} 
function doExport(){
<%--	$('save').action = "UL04_curveExport.action";--%>
<%--	$('save').request({   --%>
<%--        method:"post",--%>
<%--        parameters:{t:new Date().getTime()}--%>
<%--    });--%>
   //  document.form2.action='/ftp/pages/ul04_export.jsp';
   //  document.form2.submit();
   document.form1.action="<%=request.getContextPath()%>/UL04_curveExport.action";
   document.form1.submit();
}

		</script>		
	</body>
</html>
