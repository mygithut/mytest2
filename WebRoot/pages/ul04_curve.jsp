<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.*,java.util.Map,java.util.List"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Expires " content="0 ">
        <meta http-equiv="Cache-Control " content="no-cache,must-revalidate ">
        <meta http-equiv="Pragma " content="no-cache ">
        <base target="_self"> 
		<title>收益率曲线</title>
<jsp:include page="commonJs.jsp" />
 </head>
	<body>
 
<%
     String curveType = (String)request.getAttribute("curveType");
     String date = (String)request.getAttribute("date");
     Map<String, double[]> curveMap = (Map<String, double[]>)request.getAttribute("curveMap");
     double[] key = curveMap.get("key");
     double[] keyRate = curveMap.get("keyRate");
     double[] keyRateV = curveMap.get("keyRateV");//存款
     double[] keyRateC = curveMap.get("keyRateC");//贷款
     //内部:活期<0>、3个月<3>、6个月<6>、9个月<9>、1年<12>、2年<24>、3年<36>、4年<48>、5年<60>、6年<72>、7年<84>、8年<96>、9年<108>、10年<120>
     //市场:1天（即隔夜）、7天、14天、1个月、3个月、6个月、9个月、12个月、1年、2年、3年、5年、7年、10年、15年、20年、30年
     // 从月开始进行计算
	 double[] y = new double[key.length];
	 for (int i = 0; i < key.length; i++) {
		 y[i] = keyRate[i];
     }
	 //进行插值
	 SCYTCZlineF F = null;SCYTCZlineF F1 = null;SCYTCZlineF F2 = null;
	 double[] COF = null,VOF = null,COF_01 = null,COF_02 = null,VOF_01 = null,VOF_02 = null,basicCurve = null,basicCurve_01 = null,basicCurve_02 = null; 
	 //y = (x-x1)(y2-y1)/(x2-x1)+y1
	 
     int[] x = null;
     if (curveType.equals("1")) {  
    	 /*COF = SCYTCZ_Compute.getSCYTCZline(keyRateC, y);
    	 VOF = SCYTCZ_Compute.getSCYTCZline(keyRateV, x);
    	 basicCurve = SCYTCZ_Compute.getSCYTCZline(keyRate, x);
         session.setAttribute("basicCurve",basicCurve);
         session.setAttribute("COF",COF);
         session.setAttribute("VOF",VOF);
         session.setAttribute("key",key);*/
     }else { 
    	 //市场:1天（即隔夜）、7天、14天、1个月、3个月、6个月、9个月、12个月、1年、2年、3年、5年、7年、10年、15年、20年、30年
         //分成两段，shibor和国债
    	 /*double[] key_01 = new double[8];
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
         session.setAttribute("VOF_02",VOF_02);*/
     }
     %>
     <%String url = "";
     if (curveType.equals("1"))  {
    	 url="/ftp/pages/ul04_curve_internal.jsp?curveType=1";
     }else{
    	 url="/ftp/pages/ul04_curve_market.jsp?curveType=2";
     }%>
  
    <iframe src="<%=url %>" id="iframe" width="100%"
									height="340px" frameborder="no" marginwidth="0" marginheight="0"
									scrolling="no" allowtransparency="yes" align="middle"></iframe>
     <form name="form1" method="post" action="<%=request.getContextPath()%>/UL04_save.action" id="save">
  <div align="center">
     <input class="button" type="button" value="发&nbsp;&nbsp;布" onclick="doSave(this.form)"/>&nbsp;
	 <input class="button" type="button" value="试发布" onclick="doSfb(this.form)"/>&nbsp;
	 <input class="button" type="button" value="导&nbsp;&nbsp;出" onclick="doExport()"/>
	 <input type="hidden" value="<%=request.getAttribute("brNo") %>" name="brNo" id="brNo"/>
	 <input type="hidden" value="<%=curveType %>" name="curveType" id="curveType"/>
	 <input type="hidden" value="<%=date %>" name="date" id="date"/>
  </div>
  <br/>
  <div align="center"><font style="font-size: 15px; font-weight: bold;"><%=date %> <%=curveType.equals("1")?"存贷款收益率曲线":"市场收益率曲线"   %>标准期限及其FTP值</font></div>
</form>	
<%--<form name="form2"><input type="hidden" value="<%=date %>" name="date"/>--%>
<%--	 <input type="hidden" value="<%=curveType %>" name="curveType"/>--%>
<%--	 </form>--%>
	<script type="text/javascript">
	jQuery(document).ready(function(){ 
		parent.parent.parent.parent.cancel();
	});
function doSave(Form)
{
	   parent.parent.parent.parent.openNewDiv();
	$('save').request({   
        method:"post",
        parameters:{t:new Date().getTime()},
        onComplete: function(resp){ 
			parent.parent.parent.parent.cancel();
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
