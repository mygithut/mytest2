<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.FtpUtil,java.util.Map,java.util.List"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Expires " content="0 ">
        <meta http-equiv="Cache-Control " content="no-cache,must-revalidate ">
        <meta http-equiv="Pragma " content="no-cache ">
        <base target="_self"> 
		<title>收益率曲线</title>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
<!-- 1. Add these JavaScript inclusions in the head of your page -->
<script type="text/javascript" src="/assets/assets/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/assets/assets/js/highcharts.js"></script>
<script type="text/javascript" src="/assets/assets/js/excanvas.compiled.js"></script>
</head>
	<body>
		<!-- 3. Add the container --> 
<form name="form1" method="post">
<div id="container1" style="width: 970px; height: 300px" align="center"></div>
<div id="container2" style="width: 970px; height: 300px" align="center"></div>
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
	 int j = 0, k = 0;
     Double[] COF41 = null,VOF41 = null;//1年以内
     if (curveType.equals("1")) {
    	 j = 5;
    	 COF41 = new Double[j];//内部收益率，3个月以内分成1月、2月,加上1年期的来获得10、11月的平滑的数据
    	 COF41[0] = COF4[0];
    	 COF41[1] = COF4[0];
    	 COF41[2] = COF4[1];
    	 COF41[3] = COF4[2];
    	 COF41[4] = COF4[3];
    	 VOF41 = new Double[j];//内部收益率，3个月以内分成1月、2月
    	 VOF41[0] = VOF4[0];
    	 VOF41[1] = VOF4[0];
    	 VOF41[2] = VOF4[1];
    	 VOF41[3] = VOF4[2];
    	 VOF41[4] = VOF4[3];
		 k = 3;//内部收益率从第4个关键点开始为1年以内
	 }else if (curveType.equals("2")) {
		 j = 8;
		 COF41 = new Double[j];
		 VOF41 = new Double[j];
		 System.arraycopy(COF4,0,COF41, 0, COF41.length);//获取从1年期开始的关键点数组
		 System.arraycopy(VOF4,0,VOF41, 0, VOF41.length);//获取从1年期开始的关键点数组
		 k = 7;//市场收益率从第8个关键点开始为1年以内
	 }
     Double[] key1Double = new Double[j];
     if (curveType.equals("1")) {
    	 key1Double[0] = 1.0;
    	 key1Double[1] = 2.0;
    	 key1Double[2] = 3.0;
    	 key1Double[3] = 6.0;
    	 key1Double[4] = 12.0;
     }else {
    	 String[] key1 = new String[j];
         System.arraycopy(key,0,key1, 0, key1.length);//获取1年期以内的关键点数组
         for (int i = 0; i < key1.length; i++) {
        	 if (key1[i].indexOf("天") != -1) {
        		 key1Double[i] = Double.valueOf(key1[i].substring(0, key1[i].indexOf("天")))/30;//转化成月
        	 }
        	 if (key1[i].indexOf("个月") != -1) {
        		 key1Double[i] = Double.valueOf(key1[i].substring(0, key1[i].indexOf("个月")));
        	 }if (key1[i].indexOf("年") != -1) {
        		 key1Double[i] = Double.valueOf(12);
        	 }
         }
     }
     
     String[] key2 = new String[9];
     System.arraycopy(key,k,key2, 0, key2.length);//获取从1年期开始的关键点数组
     Double[] key2Double = new Double[key2.length];
     for (int i = 0; i < key2.length; i++) {
    	 key2Double[i] = Double.valueOf(key2[i].substring(0, key2[i].toString().length() - 1));//截取期限，获取所在年份。
    	 System.out.println(key2Double[i]);
     }
	 Double[] COF42 = new Double[9];
     System.arraycopy(COF4,k,COF42, 0, COF42.length);//获取从1年期开始的关键点数组
     Double[] VOF42 = new Double[9];
     System.arraycopy(VOF4,k,VOF42, 0, COF42.length);//获取从1年期开始的关键点数组
     
     Double[] COF01 = FtpUtil.Spline(key1Double, COF41, curveType.equals("1")?12:15);
	 Double[] VOF01 = FtpUtil.Spline(key1Double, VOF41, curveType.equals("1")?12:15);
	 Double[] COF02 = FtpUtil.Spline(key2Double, COF42, 30);
	 Double[] VOF02 = FtpUtil.Spline(key2Double, VOF42, 30);
	 System.out.println(COF01.length);
	 System.out.println("keyRate"+keyRate[0]+":"+keyRate[1]+":"+keyRate[2]+":"+keyRate[3]+":"+keyRate[4]+":"+keyRate[5]+":"+keyRate[6]+":"+keyRate[7]+":"+keyRate[8]+":"+keyRate[9]+":"+keyRate[10]+":"+keyRate[11]);
	 System.out.println("VOF1"+VOF1[0]+":"+VOF1[1]+":"+VOF1[2]+":"+VOF1[3]+":"+VOF1[4]+":"+VOF1[5]+":"+VOF1[6]+":"+VOF1[7]+":"+VOF1[8]+":"+VOF1[9]+":"+VOF1[10]+":"+VOF1[11]);
	 System.out.println("COF1"+COF1[0]+":"+COF1[1]+":"+COF1[2]+":"+COF1[3]+":"+COF1[4]+":"+COF1[5]+":"+COF1[6]+":"+COF1[7]+":"+COF1[8]+":"+COF1[9]+":"+COF1[10]+":"+COF1[11]);
	 System.out.println("VOF2"+VOF2[0]+":"+VOF2[1]+":"+VOF2[2]+":"+VOF2[3]+":"+VOF2[4]+":"+VOF2[5]+":"+VOF2[6]+":"+VOF2[7]+":"+VOF2[8]+":"+VOF2[9]+":"+VOF2[10]+":"+VOF2[11]);
	 System.out.println("COF2"+COF2[0]+":"+COF2[1]+":"+COF2[2]+":"+COF2[3]+":"+COF2[4]+":"+COF2[5]+":"+COF2[6]+":"+COF2[7]+":"+COF2[8]+":"+COF2[9]+":"+COF2[10]+":"+COF2[11]);
	 System.out.println("VOF3"+VOF3[0]+":"+VOF3[1]+":"+VOF3[2]+":"+VOF3[3]+":"+VOF3[4]+":"+VOF3[5]+":"+VOF1[6]+":"+VOF3[7]+":"+VOF3[8]+":"+VOF3[9]+":"+VOF3[10]+":"+VOF3[11]);
	 System.out.println("COF3"+COF3[0]+":"+COF3[1]+":"+COF3[2]+":"+COF3[3]+":"+COF3[4]+":"+COF3[5]+":"+COF3[6]+":"+COF3[7]+":"+COF3[8]+":"+COF3[9]+":"+COF3[10]+":"+COF3[11]);
	 System.out.println("VOF4"+VOF4[0]+":"+VOF4[1]+":"+VOF4[2]+":"+VOF4[3]+":"+VOF4[4]+":"+VOF4[5]+":"+VOF4[6]+":"+VOF4[7]+":"+VOF4[8]+":"+VOF4[9]+":"+VOF4[10]+":"+VOF4[11]);
	 System.out.println("COF4"+COF4[0]+":"+COF4[1]+":"+COF4[2]+":"+COF4[3]+":"+COF4[4]+":"+COF4[5]+":"+COF4[6]+":"+COF4[7]+":"+COF4[8]+":"+COF4[9]+":"+COF4[10]+":"+COF4[11]);
	 System.out.println("COF42"+COF42[0]+":"+COF42[1]+":"+COF42[2]+":"+COF42[3]+":"+COF42[4]+":"+COF42[5]+":"+COF42[6]+":"+COF42[7]+":"+COF42[8]);
	 System.out.println("VOF42"+VOF42[0]+":"+VOF42[1]+":"+VOF42[2]+":"+VOF42[3]+":"+VOF42[4]+":"+VOF42[5]+":"+VOF42[6]+":"+VOF42[7]+":"+VOF42[8]);
	   	%>
		<div align="center">FTP定价分析表—存款</div>
	<table class="table" align="center" cellspacing="0"
				cellpadding="0" style="border-top: none;" id="tabProduct">
				<tr height="35">
	   <th>序号</th>
	   <th>基准点</th>
	   <th>利率（%）</th>
	   <th>信用风险修正（BP）</th>
	   <th>存款准备金修正（BP）</th>
	   <th>期权修正（BP）</th>
	   <th>流动性风险修正（BP）</th>
	   <th>政策性调整（BP）</th>
	   <th>FTP（%）</th>
	 </tr>
	 <%for (int i = 0; i < key.length; i++) { %>
	 <tr>
	     <td><%=i %></td>
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
	<div align="center">FTP定价分析表—贷款</div>
	<table class="table" align="center" cellspacing="0"
				cellpadding="0" style="border-top: none;" id="tabProduct">
				<tr height="35">
	   <th>序号</th>
	   <th>基准点</th>
	   <th>利率（%）</th>
	   <th>信用风险修正（BP）</th>
	   <th>存款准备金修正（BP）</th>
	   <th>期权修正（BP）</th>
	   <th>流动性风险修正（BP）</th>
	   <th>政策性调整（BP）</th>
	   <th>FTP（%）</th>
	 </tr>
	 <%for (int i = 0; i < key.length; i++) { %>
	 <tr>
	     <td><%=i %></td>
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
</form>	
	<script type="text/javascript">
var COF51 = [];
var VOF51 = [];
var xSeries1 = [];
var COF52 = [];
var VOF52 = [];
var xSeries2 = [];
var m = 0, n = 0, j = 0, k = 0;
<%for(int i = 0; i < COF01.length; i++){ %>
var basic1 = [];
basic1[0] =  getTerm(<%=curveType%>, <%=i+1%>);
basic1[1] = <%=CommonFunctions.doublecut(COF01[i]*100, 4)%>;
COF51[j] = basic1;
<%--       COF51[j] = <%=CommonFunctions.doublecut(COF01[i]*100, 4)%>;--%>
j++;
<%}%>
<%for(int i = 0; i < VOF01.length; i++){ %>
var basic2 = [];
basic2[0] = getTerm(<%=curveType%>, <%=i+1%>);
basic2[1] = <%=CommonFunctions.doublecut(VOF01[i]*100, 4)%>;
VOF51[k] = basic2;
<%--      VOF51[k] = <%=CommonFunctions.doublecut(VOF01[i]*100, 4)%>;--%>
k++;
<%}%>

<%--<%if (curveType.equals("2")) {%>--%>
<%--xSeries1[0] = '1天',xSeries1[1] = '7天',xSeries1[2] = '14天';--%>
<%--for (var i = 1; i < 12; i++) {--%>
<%--	xSeries1[i + 2]=i+'月';--%>
<%--}--%>
<%--xSeries1[14] = '1年';--%>
<%--<%}else{%>--%>
<%--for (var i = 1; i < 13; i++) {--%>
<%--	xSeries1[i -1]=i+'月';--%>
<%--}--%>
<%--<%}%>--%>
<%for(int i = 0; i < COF02.length; i++){ %>
var basic1 = [];
basic1[0] =  <%=i+1%>;
basic1[1] = <%=CommonFunctions.doublecut(COF02[i]*100, 4)%>;
COF52[m] = basic1;
<%--       COF52[m] = <%=CommonFunctions.doublecut(COF02[i]*100, 4)%>;--%>
<%--       xSeries2[m] =  '<%=i+1%>年';--%>
m++;
<%}%>
<%for(int i = 0; i < VOF02.length; i++){ %>
var basic2 = [];
basic2[0] = <%=i+1%>;
basic2[1] = <%=CommonFunctions.doublecut(VOF02[i]*100, 4)%>;
VOF52[n] = basic2;
<%--      VOF52[n] = <%=CommonFunctions.doublecut(VOF02[i]*100, 4)%>;--%>
n++;
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
					renderTo: 'container1',
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
<%--				    categories: xSeries1,--%>
<%--				    tickmarkPlacement: 'on',--%>
					title: {
					    enabled: true,
						text: '期限(月)'
					}
				},
				yAxis: {
					title: {
						text: 'FTP(%)'
					},
					plotLines: [{
						value: 0,
						width: 1,
						color: '#808080'
					}]
				},
				legend: {
					layout: 'vertical',
					style: {
						position: 'absolute',
						bottom: 'auto',
						left: 'auto',
						right: '100px',
						top: '10px'
					},
					borderWidth: 1,
					backgroundColor: '#FFFFFF'
				},

				tooltip: {
					formatter: function() {
					var x = this.x;
					if (x < 1) x = x*30+'天'; 
					else x = x+'月';
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
					data: VOF51
				},{name: '存款',
					data: COF51
				}]
			});
			
			 var chart2 = new Highcharts.Chart({
					chart: {
						renderTo: 'container2',
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
<%--					    categories: xSeries2,--%>
<%--					    tickmarkPlacement: 'on',--%>
						title: {
						    enabled: true,
							text: '期限(年)'
						}
					},
					yAxis: {
						title: {
							text: 'FTP(%)'
						},
						plotLines: [{
							value: 0,
							width: 1,
							color: '#808080'
						}]
					},
					legend: {
						layout: 'vertical',
						style: {
							position: 'absolute',
							bottom: 'auto',
							left: 'auto',
							right: '100px',
							top: '10px'
						},
						borderWidth: 1,
						backgroundColor: '#FFFFFF'
					},
					tooltip: {
						formatter: function() {
						return '<b>'+ (this.point.name || this.series.name) +'</b><br/>'+
						'期限: '+ this.x+'年<br/>'+
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
						data: VOF52
					},{name: '存款',
						data: COF52
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
