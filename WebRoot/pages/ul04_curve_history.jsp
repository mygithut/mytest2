<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.dhcc.ftp.util.*,java.util.Map,java.util.List"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Expires " content="0 ">
        <meta http-equiv="Cache-Control " content="no-cache,must-revalidate ">
        <meta http-equiv="Pragma " content="no-cache ">
        <base target="_self"> 
		<title>收益率曲线-历史</title>
<!-- 1. Add these JavaScript inclusions in the head of your page -->
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/highcharts.src.js"></script>
</head>
	<body>
		<!-- 3. Add the container --> 
<form name="form1" method="post"></form>
<div id="container" style="width: 880px; height: 320px" align="center"></div>
<div style="width: 870px;" align="center"><input type="button" style="color: #fff; background-image: url(pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					  value="导&nbsp;&nbsp;出" onclick="doExport()"/></div>
<%
  List<Double[]> list_curve = (List<Double[]>)request.getAttribute("list_curve");
  List<String> list_curveName = (List<String>)session.getAttribute("list_curveName");
     %>
     
	<script type="text/javascript">
	var color = ['#4572A7', '#AA4643', '#89A54E', '#3D96AE', 
	 		'#DB843D', '#92A8CD', '#A47D7C', '#B5CA92','FFE835']
	var xSeries = [];
	var m =5;
	xSeries[0] = '活期</br>',xSeries[1] = '1</br>天',xSeries[2] = '7</br>天',xSeries[3] = '14</br>天',xSeries[4] = '21</br>天';
	for (var i = 1; i < 12; i++) {
		xSeries[m]=i+'</br>月';
		m++;
	}
	for (var i = 1; i < 11; i++) {
		xSeries[m]=i+'</br>年';
		m++;
	}
	xSeries[m]='15</br>年';
	xSeries[m+1]='20</br>年';
	xSeries[m+2]='30</br>年';
	var curve2 = [];
	var color = [];
	var n = 0;
	<%for(int i = 0; i < list_curve.size(); i++){
		Double[] curve = (Double[])list_curve.get(i);%>
	     var isSC = <%=list_curveName.get(i).indexOf("市场")==-1?0:1%>;//是否为市场类型
		 var k = 0,j = 0;
		 curve2[n] =[];
		 <%for(int j = 0; j < curve.length; j++){ %>
		    <%if (curve[j] == null) { %>
		        curve2[n][k] = null;
		        k++;
			<%}else{ %>
			   if(isSC==0&&(k==1||k==2||k==3||k==4)) {//存贷款曲线1天、7天、14天、21天不显示点
			   }else{
                 curve2[n][j] = [k,<%=CommonFunctions.doublecut(curve[j]*100, 4)%>];
                 j++;
			   }
			   k++;
             <%}%>
	     <%}%>
	     n++;
	  <%}%>
	
        var title = '收益率曲线-历史';
		$(document).ready(function() {
		 var chart1 = new Highcharts.Chart({
				chart: {
					renderTo: 'container',
					defaultSeriesType: 'spline',
					margin: [20, 10, 50, 60]
				},
				title: {
					text: title,
					style: {
						margin: '5px -20px 0 0' // center it
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
					minorGridLineWidth: 0, 
					gridLineWidth: 0,
					alternateGridColor: null,
					plotBands: [{  
						from: 0,
						to: 2,
						color: 'rgba(253, 211, 058, .1)'
					},{  
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
					}, {  
						from: 8,
						to: 10,
						color: 'rgba(253, 211, 058, .1)'
					}]
				},
				legend: {
					layout: 'vertical',
					style: {
						position: 'absolute',
						bottom: 'auto',
						left: '100px',
						right: 'auto',
						top: '10px'
					},
					borderWidth: 1,
					backgroundColor: '#FFFFFF'
				},

				tooltip: {
					formatter: function() {
						var x = this.x;
						//alert(x);
						//alert(this.point.name);//undefined
						//alert(this.series.name);
						x = x.substring(0,x.indexOf('</br>'))+x.substring(x.indexOf('</br>')+5,x.length);
						return '<b>'+ (this.point.name || this.series.name) +'</b><br/>'+
						'期限: '+ x+'<br/>'+
						'FTP : '+ this.y+'%';
					}
				},
				plotOptions: {
	                spline: {
	                    lineWidth: 3,
	                    states: {
	                        hover: {
	                            lineWidth: 4
	                        }
	                    },
	                    marker: {
	                        enabled: false,
	                        states: {
	                            hover: {
	                                enabled: true,
	                                symbol: 'circle',
	                                radius: 5,
	                                lineWidth: 1
	                            }
	                        }
	                    }
	                }
	            },
				series: [<%int j = 0;
				for(int i = 0; i < list_curveName.size(); i++){%>{
					name: '<%=list_curveName.get(i)%>',
					color: color[<%=i%>],
					data: curve2[<%=i%>]
					//dataURL: 'tokyo.json'
				}<%if(j != (list_curveName.size() -1) ){%>,<%}j++;}%>]
			});
			
				});
function doExport()
{
	document.form1.action="<%=request.getContextPath()%>/UL04_curveHistoryExport.action";
	document.form1.submit();
} 
		</script>		
	</body>
</html>
