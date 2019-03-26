<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.ftp.util.*,java.util.Map,java.util.List"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Expires " content="0 ">
        <meta http-equiv="Cache-Control " content="no-cache,must-revalidate ">
        <meta http-equiv="Pragma " content="no-cache ">
        <base target="_self"> 
		<title>收益率曲线</title>
<!-- 1. Add these JavaScript inclusions in the head of your page -->
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/highcharts.src.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/excanvas.compiled.js"></script>
<script rel="stylesheet" src="<%=request.getContextPath()%>/pages/js/artDialog4.1.6/artDialog.source.js?skin=blue"></script>
<script src="<%=request.getContextPath()%>/pages/js/artDialog4.1.6/plugins/iframeTools.source.js"></script>
</head>
	<body>
		<!-- 3. Add the container --> 
<form name="form1" method="post">
<div id="container" style="width: 900px; height: 370px" align="center"></div>
<%
  double[] basicCurve_01 = (double[])session.getAttribute("basicCurve_01");
  double[] basicCurve_02 = (double[])session.getAttribute("basicCurve_02");
  double[] COF_01 = (double[])session.getAttribute("COF_01");
  double[] VOF_01 = (double[])session.getAttribute("VOF_01");
  double[] COF_02 = (double[])session.getAttribute("COF_02");
  double[] VOF_02 = (double[])session.getAttribute("VOF_02");
  String curveType = request.getParameter("curveType");
     %>
     
	<script type="text/javascript">
var COF_01 = [],VOF_01 = [],COF_02 = [],VOF_02 = [],basicCurve_01 = [],basicCurve_02 = [];
var xSeries = [];
var m = 3, n = 0, j = 0, k = 0, s = 0,t = 0, p= 0, q = 0,o = 0;
<%for(int i = 0; COF_01 != null && i < COF_01.length; i++){ %>
COF_01[s] = <%=CommonFunctions.doublecut(COF_01[i]*100, 4)%>;
s++;
<%}%>
<%for(int i = 0; VOF_01 != null && i < VOF_01.length; i++){ %>
VOF_01[t] = <%=CommonFunctions.doublecut(VOF_01[i]*100, 4)%>;
t++;
<%}%>
<%for(int i = 0; basicCurve_01 != null && i < basicCurve_01.length; i++){ %>
basicCurve_01[o] = <%=CommonFunctions.doublecut(basicCurve_01[i]*100, 4)%>;
o++;
<%}%>
<%for(int i = 0; COF_02 != null && i < COF_02.length+14; i++){ 
    if (i < 14){%>
      COF_02[p] = null;
      basicCurve_02[p] = null;
  <%}else{%>
	  COF_02[p] = <%=CommonFunctions.doublecut(COF_02[i-14]*100, 4)%>;
	  basicCurve_02[p] = <%=CommonFunctions.doublecut(basicCurve_02[i-14]*100, 4)%>;
  <%}%>
    p++;
<%}%>
<%for(int i = 0; VOF_02 != null && i < VOF_02.length+14; i++){ 
	if (i < 14){%>
	  VOF_02[q] = null;
  <%}else{%>
	  VOF_02[q] = <%=CommonFunctions.doublecut(VOF_02[i-14]*100, 4)%>;
  <%}%>
	q++;
<%}%>
xSeries[0] = '1</br>天',xSeries[1] = '7</br>天',xSeries[2] = '14</br>天';
for (var i = 1; i < 12; i++) {
	xSeries[m]=i+'</br>月';
	m++;
}
for (var i = 1; i < 31; i++) {
	xSeries[m]=i+'</br>年';
	m++;
}
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
					margin: [20, 20, 50, 60]
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
					name: '1年期以内资产',
					color:'#5e8bc0',
					data: COF_01
				},{name: '1年期以内负债',
					color:'#c35f5c',
					data: VOF_01					
				},{name: '1年期以内基准线',
					color:'#a2be67',
					data: basicCurve_01					
				},{name: '1年期以上资产',
					color:'#5e8bc0',
					data: COF_02
				},{name: '1年期以上负债',
					color:'#c35f5c',
					data: VOF_02
				},{name: '1年期以上基准线',
					color:'#a2be67',
					data: basicCurve_02					
				}]
			});
			
				});
function doSave(Form)
{
	$('save').request({   
        method:"post",
        parameters:{t:new Date().getTime()},
        onComplete: function(){  
       	 art.dialog({
        	    title:'成功',
    		    icon: 'succeed',
    		    content: '保存成功！'
     	 });
      } 
    });
} 
		</script>		
	</body>
</html>
