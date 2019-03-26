<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.ftp.util.*,java.util.Map,java.util.List"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Expires " content="0 ">
        <meta http-equiv="Cache-Control " content="no-cache,must-revalidate ">
        <meta http-equiv="Pragma " content="no-cache ">
        <base target="_self"> 
		<title>收益率曲线1</title>
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
<%double[] COF = (double[])session.getAttribute("COF");
  double[] VOF = (double[])session.getAttribute("VOF");
  double[] basicCurve = (double[])session.getAttribute("basicCurve");
  String curveType = request.getParameter("curveType");
     %>
     
	<script type="text/javascript">
var COF = [],VOF = [],basicCurve=[];
var xSeries = [];
var m = 3, n = 2, j = 0, k = 0;
<%for(int i = 0; COF != null && i < COF.length; i++){%>
      COF[j] = <%=CommonFunctions.doublecut(COF[i]*100, 4)%>;
      basicCurve[j] = <%=CommonFunctions.doublecut(basicCurve[i]*100, 4)%>;
j++;
<%}%>
<%for(int i = 0; VOF != null && i < VOF.length; i++){ %>
      VOF[k] = <%=CommonFunctions.doublecut(VOF[i]*100, 4)%>;
k++;
<%}%>
xSeries[0] = '1</br>天';
xSeries[1] = '7</br>天';
for (var i = 1; i < 12; i++) {
	xSeries[n]=i+'</br>月';
	n++;
}
for (var i = 1; i < 31; i++) {
	xSeries[n]=i+'</br>年';
	n++;
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
						top: '10px'
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
					name: '贷款COF',
					data: COF
				},{name: '存款VOF',
					data: VOF
				},{name: '基准线',
					data: basicCurve
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
