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
<%String curveType = (String)request.getAttribute("curveType");
  String date = (String)request.getAttribute("date");
  String brNo = (String)request.getAttribute("brNo");
  Map<String, double[]> curveMap = (Map<String, double[]>)request.getAttribute("curveMap");
  double[] key = curveMap.get("key");
  double[] keyRate = curveMap.get("keyRate");
  SCYTCZlineF F = SCYTCZ_Compute.getSCYTCZline(key, keyRate);
     %>
<div id="container" style="width: 980px; height: 320px" align="center"></div>
<form name="saveForm" method="post" action="<%=request.getContextPath()%>/UL04_save.action" id="saveForm">
<div align="center">
     <input class="button" type="button" value="发&nbsp;&nbsp;布" onclick="doSave(this.form)" style="color: #fff; background-image: url(pages/themes/green/img/red.png); height:23px;border: 1px solid red; padding-top: 2px; cursor: hand;"/>&nbsp;
	 <input class="button" type="button" value="试发布" onclick="doSfb(this.form)" style="color: #fff; background-image: url(pages/themes/green/img/red.png); height:23px;border: 1px solid red; padding-top: 3px; cursor: hand;"/>&nbsp;
	 <input class="button" type="button" value="导&nbsp;&nbsp;出" onclick="doExport()" style="color: #fff; background-image: url(pages/themes/green/img/red.png); height:23px;border: 1px solid red; padding-top: 2px; cursor: hand;"/>
	 <input type="hidden" value="<%=request.getAttribute("brNo") %>" name="brNo" id="brNo"/>
	 <input type="hidden" value="<%=curveType %>" name="curveType" id="curveType"/>
	 <input type="hidden" value="<%=date %>" name="date" id="date"/>
  </div>
<%for (int i = 0; i < key.length; i++) {%>
	 <input type="hidden" value="<%=key[i] %>" name="key" id="key" />
<%} %>
<%double[][] matics = F.A;
for (int i = 0; i < matics.length; i++) {%>
<input type="hidden" value="<%=matics[i][0] %>" name="a3" id="a3"/>
<input type="hidden" value="<%=matics[i][1] %>" name="b2" id="b2"/>
<input type="hidden" value="<%=matics[i][2] %>" name="c1" id="c1"/>
<input type="hidden" value="<%=matics[i][3] %>" name="d0" id="d0"/>
<%}%>         
</form>
<script type="text/javascript">
	$(document).ready(function(){ 
		parent.parent.parent.parent.cancel();
	});
var basicCurve = [];
var xSeries = [];
var m = 4, n = 4;
//1天、1W、2W、3W
basicCurve[0] = [0,<%=CommonFunctions.doublecut(F.getY_SCYTCZline(1/30.0)*100, 4)%>];
basicCurve[1] = [1,<%=CommonFunctions.doublecut(F.getY_SCYTCZline(7/30.0)*100, 4)%>];
basicCurve[2] = [2,<%=CommonFunctions.doublecut(F.getY_SCYTCZline(14/30.0)*100, 4)%>];
basicCurve[3] = [3,<%=CommonFunctions.doublecut(F.getY_SCYTCZline(21/30.0)*100, 4)%>];
//1M-11M
<%for(int i = 1; i < 12; i++){ %>
basicCurve[m] = [m,<%=CommonFunctions.doublecut(F.getY_SCYTCZline(i)*100, 4)%>];
m++;
<%}%>
//1Y-10M
<%for(int i = 1; i < 11; i++){ %>
basicCurve[m] = [m,<%=CommonFunctions.doublecut(F.getY_SCYTCZline(i*12)*100, 4)%>];
m++;
<%}%>
//15Y、20Y、30Y
basicCurve[m] = [m,<%=CommonFunctions.doublecut(F.getY_SCYTCZline(15*12)*100, 4)%>];
basicCurve[m+1] = [m+1,<%=CommonFunctions.doublecut(F.getY_SCYTCZline(20*12)*100, 4)%>];
basicCurve[m+2] = [m+2,<%=CommonFunctions.doublecut(F.getY_SCYTCZline(30*12)*100, 4)%>];
xSeries[0] = '1</br>天',xSeries[1] = '7</br>天',xSeries[2] = '14</br>天',xSeries[3] = '21</br>天';
for (var i = 1; i < 12; i++) {
	xSeries[n]=i+'</br>月';
	n++;
}
for (var i = 1; i < 11; i++) {
	xSeries[n]=i+'</br>年';
	n++;
}
xSeries[n] = 15+'</br>年';
xSeries[n+1] = 20+'</br>年';
xSeries[n+2] = 30+'</br>年';
function getTerm (type, i) {
    if (type == 1) {
         return i;
    }else {
         if (i == 1) return 1/30;
         else if (i == 2) return 7/30;
         else if (i == 3) return 14/30; 
         else if (i == 4) return 21/30; 
         else return i- 4; 
    }
    return null;
}
var title = '市场收益率曲线';
		$(document).ready(function() {
		 var chart1 = new Highcharts.Chart({
				chart: {
					renderTo: 'container',
					defaultSeriesType: 'spline',
					margin: [30, 10, 50, 80]
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
					}]
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
                legend:{enabled:false},
				series: [{
					name: '市场收益率曲线',
					data: basicCurve
				}]
			});
			
				});
		</script>	
<script type="text/javascript" language=javascript src="<%=request.getContextPath()%>/pages/js/prototype.js"></script>
		<script>
		function doSave(Form)
		{
			parent.parent.parent.parent.openNewDiv();
			$('saveForm').request({   
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
			parent.parent.parent.parent.openNewDiv();
			$('saveForm').action="<%=request.getContextPath()%>/UL04_sfb.action";
			$('saveForm').request({   
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
		         		    content: '试发布成功！',
		          		    cancelVal: '关闭',
		          		    cancel: true
		          	 });
		            }
		            //alert("保存成功！！");
		      } 
		    });
			$('saveForm').action="<%=request.getContextPath()%>/UL04_save.action";
		} 
		function doExport(){
		<%--	$('save').action = "UL04_curveExport.action";--%>
		<%--	$('save').request({   --%>
		<%--        method:"post",--%>
		<%--        parameters:{t:new Date().getTime()}--%>
		<%--    });--%>
		   //  document.form2.action='/ftp/pages/ul04_export.jsp';
		   //  document.form2.submit();
		   document.saveForm.action="<%=request.getContextPath()%>/UL04_curveExport.action";
		   document.saveForm.submit();
		}
		</script>			
	</body>
</html>
