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
<script type="text/javascript" src="/ftp/pages/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/ftp/pages/js/highcharts.js"></script>
<script type="text/javascript" src="/ftp/pages/js/excanvas.compiled.js"></script>
<script rel="stylesheet" src="<%=request.getContextPath()%>/pages/js/artDialog4.1.6/artDialog.source.js?skin=blue"></script>
<script src="<%=request.getContextPath()%>/pages/js/artDialog4.1.6/plugins/iframeTools.source.js"></script>
</head>
	<body>
		<!-- 3. Add the container --> 
<form name="form1" method="post">
<div id="container" style="width: 1170px; height: 600px" align="center"></div>
<%
  List<Double[]> list_curve = (List<Double[]>)request.getAttribute("list_curve");
  List<String> list_curveName = (List<String>)session.getAttribute("list_curveName");
     %>
     
	<script type="text/javascript">
	var colors = ['#4572A7', '#AA4643', '#89A54E', '#80699B', '#3D96AE', 
	 		'#DB843D', '#92A8CD', '#A47D7C', '#B5CA92','FFE835']
	var xSeries = [];
	var m =3;
	xSeries[0] = '1</br>天',xSeries[1] = '7</br>天',xSeries[2] = '14</br>天';
	for (var i = 1; i < 12; i++) {
		xSeries[m]=i+'</br>月';
		m++;
	}
	for (var i = 1; i < 31; i++) {
		xSeries[m]=i+'</br>年';
		m++;
	}
	var curve2 = [];
	var color = [];
	var n = 0;
	<%for(int i = 0; i < list_curve.size(); i++){
	     Double[] curve = (Double[])list_curve.get(i);%>
		 var k = 0;
		 curve2[n] =[];
		 <%for(int j = 0; j < curve.length; j++){ %>
		    <%if (curve[j] == null) { %>
		        curve2[n][k] = null;
		        k++;
			<%}else{ %>
                curve2[n][k] = <%=CommonFunctions.doublecut(curve[j]*100, 4)%>;
                k++;
	        <%}%>
	     <%}%>
	     n++;
	  <%}%>
	  var p = 0, q = 0;
	  <%for(int i = 0; i < list_curveName.size(); i++){
	     //对于市场收益率曲线，判断当前条与上一条的前半部分是否相同，相同则这两条曲线的颜色相同
	     //例如：'市场收益率曲线-基准化曲线(1年期以内)'和'市场收益率曲线-基准化曲线(1年期以上)'这两条曲线的颜色应该相同
	     if (i != 0) {
	    	 System.out.println(list_curveName.get(i).substring(0, 27));
		     }
	     if (i != 0 && list_curveName.get(i).indexOf("市场") != -1 && list_curveName.get(i).substring(0, 27).equals(list_curveName.get(i-1).substring(0, 27))) {
	     %> 
	    	 color[p] = colors[q-1];	 
	   <%}else {%>
	         color[p] = colors[q];
	         q++;
	   <%}%>
          p++;
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
	
        var title = '收益率曲线';
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
					max: 12.5,
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
					}, {  
						from: 10,
						to: 12.5,
						color: 'rgba(253, 211, 058, .1)'
					}, {  
						from: 12.5,
						to: 15,
						color: 'rgba(68, 170, 213, .1)'
					}, {  
						from: 15,
						to: 17.5,
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
						top: '2px'
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
				series: [<%for(int i = 0; i < list_curveName.size(); i++){%>{
					name: '<%=list_curveName.get(i)%>',
					color: color[<%=i%>],
					data: curve2[<%=i%>]
					//dataURL: 'tokyo.json' 
				},<%}%>]
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
<%--    Form.action = "UL04_save.action";--%>
<%--    Form.submit();--%>
<%--    self.close();--%>
} 
		</script>		
	</body>
</html>
