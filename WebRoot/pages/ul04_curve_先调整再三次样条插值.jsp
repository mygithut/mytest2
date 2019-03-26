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
<!-- 1. Add these JavaScript inclusions in the head of your page -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core1.css" type="text/css">
<script type="text/javascript" src="/assets/assets/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/assets/assets/js/highcharts.js"></script>
<script type="text/javascript" src="/assets/assets/js/excanvas.compiled.js"></script>
</head>
	<body>
		<!-- 3. Add the container --> 
<form name="form1" method="post">
<div id="container" style="width: 1170px; height: 300px" align="center"></div>
<%
     String curveType = (String)request.getAttribute("curveType");
     String[] key = (String[])request.getAttribute("key");
     Double[] keyRate = (Double[])request.getAttribute("keyRate");
     Double[] COF1 = (Double[])request.getAttribute("COF1");
     Double[] VOF1 = (Double[])request.getAttribute("VOF1");
     Double[] COF2 = (Double[])request.getAttribute("COF2");
     Double[] VOF2 = (Double[])request.getAttribute("VOF2");
     Double[] COF3 = (Double[])request.getAttribute("COF3");
     Double[] VOF3 = (Double[])request.getAttribute("VOF3");
     Double[] COF4 = (Double[])request.getAttribute("COF4");
     Double[] VOF4 = (Double[])request.getAttribute("VOF4");
     //内部:3个月以内 、3个月 、6个月、1年、2年、3年、5年、7年、10年、15年、20年、30年
     //市场:1天（即隔夜）、7天、14天、1个月、3个月、6个月、9个月、1年、2年、3年、5年、7年、10年、15年、20年、30年
     //将曲线分成1年以内(月)和1年以上(年)两个部分
     double[] COF5 = null,VOF5 = null;//1年以内
     if (curveType.equals("1")) {
    	 COF5 = new double[13];//内部收益率，3个月以内分成1月、2月,加上1年期的来获得10、11月的平滑的数据
    	 COF5[0] = COF4[0];
    	 COF5[1] = COF4[0];
    	 VOF5 = new double[13];//内部收益率，3个月以内分成1月、2月
    	 VOF5[0] = VOF4[0];
    	 VOF5[1] = VOF4[0];
    	 for (int i = 2; i < 13; i++) {
        	 COF5[i] = COF4[i-1] ;
        	 VOF5[i] = VOF4[i-1] ;
         }
	 }else if (curveType.equals("2")) {
		 COF5 = new double[16];
		 VOF5 = new double[16];
		 System.arraycopy(COF4,0,COF5, 0, COF4.length);//获取从1年期以内的关键点数组
		 System.arraycopy(VOF4,0,VOF5, 0, VOF4.length);//获取从1年期以内的关键点数组
	 }
     double[] keyDouble = null;
     if (curveType.equals("1")) {
    	 keyDouble = new double[13];
    	 keyDouble[0] = 1.0;
    	 keyDouble[1] = 2.0;
    	 keyDouble[2] = 3.0;
    	 keyDouble[3] = 6.0;
    	 keyDouble[4] = 12.0;
    	 keyDouble[5] = 24.0;
    	 keyDouble[6] = 36.0;
    	 keyDouble[7] = 60.0;
    	 keyDouble[8] = 84.0;
    	 keyDouble[9] = 120.0;
    	 keyDouble[10] = 180.0;
    	 keyDouble[11] = 240.0;
    	 keyDouble[12] = 360.0;
     }else {
    	 keyDouble = new double[16];
    	 keyDouble[0] = 1.0/30;
    	 keyDouble[1] = 7.0/30;
    	 keyDouble[2] = 14.0/30;
    	 keyDouble[3] = 1.0;
    	 keyDouble[4] = 3.0;
    	 keyDouble[5] = 6.0;
    	 keyDouble[6] = 9.0;
    	 keyDouble[7] = 12.0;
    	 keyDouble[8] = 24.0;
    	 keyDouble[9] = 36.0;
    	 keyDouble[10] = 60.0;
    	 keyDouble[11] = 84.0;
    	 keyDouble[12] = 120.0;
    	 keyDouble[13] = 180.0;
    	 keyDouble[14] = 240.0;
    	 keyDouble[15] = 360.0;
     }
     SCYTCZlineF F11=SCYTCZ_Compute.getSCYTCZline(keyDouble,COF5);
     SCYTCZlineF F12=SCYTCZ_Compute.getSCYTCZline(keyDouble,VOF5);
     Double[] COF = null, VOF = null;
     if (curveType.equals("1")) {
    	 COF = new Double[41];
    	 VOF = new Double[41];
    	 for (int i = 1; i < 13; i++) {
        	 COF[i-1] = F11.getY_SCYTCZline(i);
        	 VOF[i-1] = F12.getY_SCYTCZline(i);
         }
    	 for (int i = 2; i < 31; i++) {
        	 COF[i+10] = F11.getY_SCYTCZline(i*12);
        	 VOF[i+10] = F12.getY_SCYTCZline(i*12);
         }
	 }else {
		 COF = new Double[44];
    	 VOF = new Double[44];
    	 COF[0] = F11.getY_SCYTCZline(1.0/30);
    	 COF[1] = F11.getY_SCYTCZline(7.0/30);
    	 COF[2] = F11.getY_SCYTCZline(14.0/30);
    	 VOF[0] = F12.getY_SCYTCZline(1.0/30);
    	 VOF[1] = F12.getY_SCYTCZline(7.0/30);
    	 VOF[2] = F12.getY_SCYTCZline(14.0/30);
    	 for (int i = 1; i < 13; i++) {
        	 COF[i+2] = F11.getY_SCYTCZline(i);
        	 VOF[i+2] = F12.getY_SCYTCZline(i);
         }
    	 for (int i = 2; i < 31; i++) {
        	 COF[i+13] = F11.getY_SCYTCZline(i*12);
        	 VOF[i+13] = F12.getY_SCYTCZline(i*12);
         }
	 }
     System.out.println(VOF.length);
	 System.out.println("keyRate"+keyRate[0]+":"+keyRate[1]+":"+keyRate[2]+":"+keyRate[3]+":"+keyRate[4]+":"+keyRate[5]+":"+keyRate[6]+":"+keyRate[7]+":"+keyRate[8]+":"+keyRate[9]+":"+keyRate[10]+":"+keyRate[11]);
	 System.out.println("VOF1"+VOF1[0]+":"+VOF1[1]+":"+VOF1[2]+":"+VOF1[3]+":"+VOF1[4]+":"+VOF1[5]+":"+VOF1[6]+":"+VOF1[7]+":"+VOF1[8]+":"+VOF1[9]+":"+VOF1[10]+":"+VOF1[11]);
	 System.out.println("COF1"+COF1[0]+":"+COF1[1]+":"+COF1[2]+":"+COF1[3]+":"+COF1[4]+":"+COF1[5]+":"+COF1[6]+":"+COF1[7]+":"+COF1[8]+":"+COF1[9]+":"+COF1[10]+":"+COF1[11]);
	 System.out.println("VOF2"+VOF2[0]+":"+VOF2[1]+":"+VOF2[2]+":"+VOF2[3]+":"+VOF2[4]+":"+VOF2[5]+":"+VOF2[6]+":"+VOF2[7]+":"+VOF2[8]+":"+VOF2[9]+":"+VOF2[10]+":"+VOF2[11]);
	 System.out.println("COF2"+COF2[0]+":"+COF2[1]+":"+COF2[2]+":"+COF2[3]+":"+COF2[4]+":"+COF2[5]+":"+COF2[6]+":"+COF2[7]+":"+COF2[8]+":"+COF2[9]+":"+COF2[10]+":"+COF2[11]);
	 System.out.println("VOF3"+VOF3[0]+":"+VOF3[1]+":"+VOF3[2]+":"+VOF3[3]+":"+VOF3[4]+":"+VOF3[5]+":"+VOF1[6]+":"+VOF3[7]+":"+VOF3[8]+":"+VOF3[9]+":"+VOF3[10]+":"+VOF3[11]);
	 System.out.println("COF3"+COF3[0]+":"+COF3[1]+":"+COF3[2]+":"+COF3[3]+":"+COF3[4]+":"+COF3[5]+":"+COF3[6]+":"+COF3[7]+":"+COF3[8]+":"+COF3[9]+":"+COF3[10]+":"+COF3[11]);
	 System.out.println("VOF4"+VOF4[0]+":"+VOF4[1]+":"+VOF4[2]+":"+VOF4[3]+":"+VOF4[4]+":"+VOF4[5]+":"+VOF4[6]+":"+VOF4[7]+":"+VOF4[8]+":"+VOF4[9]+":"+VOF4[10]+":"+VOF4[11]);
	 System.out.println("COF4"+COF4[0]+":"+COF4[1]+":"+COF4[2]+":"+COF4[3]+":"+COF4[4]+":"+COF4[5]+":"+COF4[6]+":"+COF4[7]+":"+COF4[8]+":"+COF4[9]+":"+COF4[10]+":"+COF4[11]);
	   	%>
		<div align="center"><font style="font-size: 15px; font-weight: bold;">FTP定价分析表—存款</font></div>
	<table class="table" align="center" cellspacing="0"
				cellpadding="0" style="border-top: none;" id="ta%roduct">
				<tr height="35">
	   <th>序号</th>
	   <th>基准点</th>
	   <th>利率（%）</th>
	   <th>信用风险修正（%）</th>
	   <th>存款准备金修正（%）</th>
	   <th>期权修正（%）</th>
	   <th>流动性风险修正（%）</th>
	   <th>政策性调整（%）</th>
	   <th>FTP（%）</th>
	 </tr>
	 <%for (int i = 0; i < key.length; i++) { %>
	 <tr>
	     <td><%=i+1 %></td>
	     <td><%=key[i] %></td>
	     <td><%=CommonFunctions.doublecut(keyRate[i]*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut((VOF1[i]-keyRate[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut((VOF2[i]-VOF1[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut((VOF3[i]-VOF2[i])*100, 4) %></td>
	     <td>*</td>
	     <td><%=CommonFunctions.doublecut((VOF4[i]-VOF3[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut(VOF4[i]*100, 4) %></td>
	 </tr>
	 <%} %>
	</table>
	<div align="center"><font style="font-size: 15px; font-weight: bold;">FTP定价分析表—贷款</font></div>
	<table class="table" align="center" cellspacing="0"
				cellpadding="0" style="border-top: none;" id="ta%roduct">
				<tr height="35">
	   <th>序号</th>
	   <th>基准点</th>
	   <th>利率（%）</th>
	   <th>信用风险修正（%）</th>
	   <th>存款准备金修正（%）</th>
	   <th>期权修正（%）</th>
	   <th>流动性风险修正（%）</th>
	   <th>政策性调整（%）</th>
	   <th>FTP（%）</th>
	 </tr>
	 <%for (int i = 0; i < key.length; i++) { %>
	 <tr>
	     <td><%=i+1 %></td>
	     <td><%=key[i] %></td>
	     <td><%=CommonFunctions.doublecut(keyRate[i]*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut((COF1[i]-keyRate[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut((COF2[i]-COF1[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut((COF3[i]-COF2[i])*100, 4) %></td>
	     <td>*</td>
	     <td><%=CommonFunctions.doublecut((COF4[i]-COF3[i])*100, 4) %></td>
	     <td><%=CommonFunctions.doublecut(COF4[i]*100, 4) %></td>
	 </tr>
	 <%} %>
	</table>
</form>	
	<script type="text/javascript">
var COF51 = [];
var VOF51 = [];
var xSeries = [];
var m = 3, n = 0, j = 0, k = 0;
<%for(int i = 0; i < COF.length-1; i++){ %>
<%--var basic1 = [];--%>
<%--basic1[0] =  getTerm(<%=curveType%>, <%=i+1%>);--%>
<%--basic1[1] = <%=CommonFunctions.doublecut(COF01[i]*100, 4)%>;--%>
<%--COF51[j] = basic1;--%>
       COF51[j] = <%=CommonFunctions.doublecut(COF[i]*100, 4)%>;
j++;
<%}%>
<%for(int i = 0; i < VOF.length-1; i++){ %>
<%--var basic2 = [];--%>
<%--basic2[0] = getTerm(<%=curveType%>, <%=i+1%>);--%>
<%--basic2[1] = <%=CommonFunctions.doublecut(VOF01[i]*100, 4)%>;--%>
<%--VOF51[k] = basic2;--%>
      VOF51[k] = <%=CommonFunctions.doublecut(VOF[i]*100, 4)%>;
k++;
<%}%>
<%if (curveType.equals("1")) {%>
for (var i = 1; i < 12; i++) {
	xSeries[n]=i+'</br>月';
	n++;
}
for (var i = 1; i < 31; i++) {
	xSeries[n]=i+'</br>年';
	n++;
}
<%}else if (curveType.equals("2")) {%>
xSeries[0] = '1</br>天',xSeries[1] = '7</br>天',xSeries[2] = '14</br>天';
for (var i = 1; i < 12; i++) {
	xSeries[m]=i+'</br>月';
	m++;
}
for (var i = 1; i < 31; i++) {
	xSeries[m]=i+'</br>年';
	m++;
}
<%}%>
function getTerm (type, i) {
    if (type == 1) {
         return i;
    }else {
         if (i == 1) return 1/30;
         else if (i == 2) return 7/30;
         else if (i == 3) return 14/30; 
         else return i- 3; 
    }
    return null;
}
var title = <%=curveType%>=='1'?'内部收益率曲线':'市场收益率曲线';
		$(document).ready(function() {
		 var chart1 = new Highcharts.Chart({
				chart: {
					renderTo: 'container',
					defaultSeriesType: 'spline',
					margin: [50, 50, 50, 80]
				},
				title: {
					text: title,
					style: {
						margin: '5px -30px 0 0' // center it
					}
				},
				xAxis: {
				    categories: xSeries,
					title: {
					    enabled: true,
						text: '期限'
					}
				},
				yAxis: {
					title: {
						text: 'FTP(%)'
					},
					min: 0,
					max: 10,
					minorGridLineWidth: 0, 
					gridLineWidth: 0,
					alternateGridColor: null,
					plotBands: [{  
						from: 0,
						to: 2.5,
						color: 'rgba(253, 211, 058, .1)'
					},{  
						from: 2.5,
						to: 5,
						color: 'rgba(68, 170, 213, .1)'
					},{  
						from: 5,
						to: 7.5,
						color: 'rgba(253, 211, 058, .1)'
					}, {  
						from: 7.5,
						to: 10,
						color: 'rgba(68, 170, 213, .1)'
					}]
				},
				legend: {
					layout: 'vertical',
					style: {
						position: 'absolute',
						bottom: 'auto',
						left: '150px',
						right: 'auto',
						top: '20px'
					},
					borderWidth: 1,
					backgroundColor: '#FFFFFF'
				},

				tooltip: {
					formatter: function() {
					var x = this.x;
					x = x.substring(0,x.indexOf('</br>'))+x.substring(x.indexOf('</br>')+5,x.length);
					return '<b>'+ (this.point.name || this.series.name) +'</b><br/>'+
					'期限: '+ x+'<br/>'+
					'FTP : '+ this.y+'%';
					}
				},
				plotOptions: {
					spline: {
					lineWidth: 2,
					marker: {
						enabled: false
					},
					states: {
						hover: {
							marker: {
								enabled: true,
								symbol: 'circle',
								radius: 3,
								lineWidth: 1
							}
						}
					}
				}

				},
				series: [{
					name: '贷款',
					data: COF51
				},{name: '存款',
					data: VOF51
				}]
			});
			
				});
jQuery(document).ready(function(){
	 // 接收父窗口传过的 window对象.
	 var wind=self.opener;
	 wind.hideMessage();
		});
		</script>		
	</body>
</html>
