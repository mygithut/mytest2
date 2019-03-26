<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Expires " content="0 ">
        <meta http-equiv="Cache-Control " content="no-cache,must-revalidate ">
        <meta http-equiv="Pragma " content="no-cache ">
        <base target="_self"> 
		<title>历史转移价格</title>
<!-- 1. Add these JavaScript inclusions in the head of your page -->
<script type="text/javascript" src="/ftp/pages/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/ftp/pages/js/highcharts.js"></script>
<script type="text/javascript" src="/ftp/pages/js/excanvas.compiled.js"></script>
<script type="text/javascript" language=javascript src="/ftp/pages/js/fpopdate.js"></script>
<script language="JavaScript" src="/ftp/pages/js/GetDate.js" type="text/javascript"></script>
</head>
	<body>
		<!-- 3. Add the container -->
<form name="form1" method="post">
<div id="container" style="width: 750px; height: 350px" align="center"></div>
		<input name="methodName" type="hidden" value="<%=request.getAttribute("methodName") %>" id="methodName"> 
		<input name="yTransPrice" type="hidden" value="<%=request.getAttribute("yTransPrice") %>" id="yTransPrice"> 
		<input name="xPricingDate" type="hidden" value="<%=request.getAttribute("xPricingDate") %>" id="xPricingDate"> 
</form>	
	<script type="text/javascript">
	    var obj1 = new Object();
	    obj1.yPoint="";
		var yTransPrice = document.getElementById("yTransPrice").value;
		var ySeries = yTransPrice.split(",");
		var yArr = [];
		
		for(var i=0;i<ySeries.length;i++){
		    yArr[i] = parseFloat(ySeries[i]);
		}  
		var xPricingDate = document.getElementById("xPricingDate").value;
		var xSeries = xPricingDate.split(",");
		$(document).ready(function() {
		if(ySeries==""){
		    alert("该日期区间内无转移价格！");
		    window.close();
		}else if(xSeries.length==1){
		    obj1.yPoint=ySeries;
		    alert("仅存在一条记录，定价日期："+xSeries+"，转移价格："+ySeries);
		    window.close();
		}else if(xSeries.length>1){
			var chart = new Highcharts.Chart({
				chart: {
					renderTo: 'container',
					defaultSeriesType: 'spline',
					margin: [50, 50, 100, 80]
				},
				title: {
					text: '定价趋势图',
					style: {
						margin: '10px 10px 0 0' // center it
					}
				},
				subtitle: {
					text: $('methodName').value
				},
				xAxis: {
				    categories: xSeries,
					title: {
					    enabled: true,
						text: '定价日期'
					}
				},
				yAxis: {
					title: {
						text: '转移价格(%)'
					},
					plotLines: [{
						value: 0,
						width: 1,
						color: '#808080'
					}]
				},
				legend: {
					enabled: false
				},
				tooltip: {
					formatter: function() {
			                return '<b>'+ this.series.name +'</b><br/>'+
							this.x +','+ this.y+'%';
					}
				},
				plotOptions: {
					spline: {
						marker: {
							radius: 4,
							lineColor: '#666666',
							lineWidth: 1
						},
						point: {
							events: {
								click: function() {
									//alert ('期限: '+ this.category +'\n利率: '+ this.y);
									obj1.yPoint = this.y;
		                            window.close();
								}
							}
						}
					}
				},
				series: [{
					name: '定价日期,转移价格',
					marker:{
					  symbol:'square'
					},
					data: yArr
					
				}]
			});
			}
			
		});
		window.returnValue=obj1;
        //window.close();
        
		</script>		
	</body>
</html>
