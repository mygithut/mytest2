<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.*,java.util.Map,java.util.List"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Expires " content="0 ">
        <meta http-equiv="Cache-Control " content="no-cache,must-revalidate ">
        <meta http-equiv="Pragma " content="no-cache ">
        <base target="_self"> 
		<title>期限匹配-历史</title>
<!-- 1. Add these JavaScript inclusions in the head of your page -->
<script type="text/javascript" src="/ftp/pages/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/ftp/pages/js/highcharts.src.js"></script>
<script type="text/javascript" src="/ftp/pages/js/excanvas.compiled.js"></script>
</head>
	<body>
		<!-- 3. Add the container --> 
<form name="form1" method="post">
<div id="container" style="width: 1170px; height: 600px" align="center"></div>
<%
  List x = (List)request.getAttribute("x");
  Double[][] y = (Double[][])request.getAttribute("y");
  String[] curve_name = (String[])request.getAttribute("curve_name");
  
  
     %>
     
	<script type="text/javascript"> 
	var colors = ['#4572A7', '#AA4643', '#89A54E', '#80699B', '#3D96AE', 
	 		'#DB843D', '#92A8CD', '#A47D7C', '#B5CA92','FFE835']
	var m = 0;
	var xSeries = [];
	var curveName = [];
	<%for(int i = 0; x != null && i < x.size(); i++){ %>
	     xSeries[m] = '<%=x.get(i)%>';
	     m++;
    <%}%>
	var color = [];
	var obj=[];
	var n = 0;
	<%for(int i = 0; i < y.length; i++){%>
	     curveName[n] = '<%=curve_name[i]%>';
         var k = 0, s = 0;
		 var curve = [];
		 <%for(int j = 0; j < y[i].length; j++){%>
		    <%if (y[i][j] == null) { %> 
		        s++;
			<%}else{ %>
			curve[k]=[];
            curve[k][0]=s;
			    curve[k][1]=<%=CommonFunctions.doublecut(y[i][j]*100, 4)%>;
			   
                k++;s++;
	         <%}%> 
	  <%}%>obj[n]=curve;n++;<%}%>
	  var i =0;
      var title = '期限匹配历史定价结果曲线';
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
					text: '日期'
				}
			},
			yAxis: {
				title: {
					text: 'FTP(%)'
				},
				min: 2,
				max: 10,
				minorGridLineWidth: 0, 
				gridLineWidth: 0,
				alternateGridColor: null,
				plotBands: [{  
					from: 2,
					to: 4,
					color: 'rgba(68, 170, 213, .1)'
				},{  
					from: 4,
					to: 6,
					color: 'rgba(253, 211, 058, .1)'
				}, {  
					from: 6,
					to: 8,
					color: 'rgba(68, 170, 213, .1)'
				},{  
					from: 8,
					to: 10,
					color: 'rgba(253, 211, 058, .1)'
				}, {  
					from: 10,
					to: 12,
					color: 'rgba(68, 170, 213, .1)'
				},]
			},
			legend: {
				layout: 'vertical',
				style: {
					position: 'absolute',
					bottom: 'auto',
					left: '100px',
					right: 'auto',
					top: '25px'
				},
				borderWidth: 1,
				backgroundColor: '#FFFFFF'
			},

			tooltip: {
				formatter: function() {
				var x = this.x;
				return '<b>'+ (this.point.name || this.series.name) +'</b><br/>'+
				'日期: '+ x+'<br/>'+
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
			series: [<%for(int i = 0; i < y.length; i++){%>{
				name: curveName[<%=i%>],
				color: colors[<%=i%>],
				data: obj[<%=i%>]
				//dataURL: 'tokyo.json' 
			},<%}%>]
		});
		
			});
		</script>		
	</body>
</html>
