<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="java.text.*,java.io.*,java.util.*,com.dhcc.ftp.entity.FtpPoolInfo,com.dhcc.ftp.util.CommonFunctions"%>
<%@page import="org.omg.CORBA.Request"%>
<html>
<head>
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="commonJs.jsp" />

</head>
<body>
<%List<FtpPoolInfo> poolList = (List<FtpPoolInfo>)request.getAttribute("poolList"); %>
<form action="<%=request.getContextPath()%>/UL03_report.action" method="get">
<table width="80%" border="0" align="left">
		   <tr>
		      <td>
  <table width="900" border="0" align="left" class="table">
    <tr>
		<td class="middle_header">
			<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">多资金池定价计算</font>
		</td>
	</tr>
	<tr>
	    <td align="center">
	    <div id="mydiv" style="display:none">
<center>
计算中，请稍候...<br>
<img src="/ftp/pages/images/load.gif"/><!---动态图片，这里可以省略-->
</center>
</div>
	    <table border="0"><tr><td><input type="button" name="Submit1" value="定价计算" onclick="priceCalculate(this.form)" class="button"/><div align="center">
	        </td><td><input type="button" name="Submit1" value="定价发布" onclick="priceSubmit(this.form)" class="button">
	    </td><td><input type="button" name="Submit2" value="定价试发布" onclick="priceSfb(this.form)" class="button">
	    </td><td><input type="button" name="Submit2" value="导&nbsp;出" onclick="doExport(this.form)" class="button">
	    </td></tr>
	    </table>
	    </td>
	</tr>
	<tr>
		<td>
		          <table width="95%" align="center" class="table" id="poolTable">
			        <tr>
			           <th align="center" width="8%">编号</th>
			           <th align="center" width="27%">资金池名称</th>
			           <th align="center" width="10%">资产类型</th>
			           <th align="center" width="15%">经营策略调整(%)</th>
			           <th align="center" width="15%">转移价格(%)</th>
			           <th align="center" width="10%">数据分析</th>
			           <th align="center" width="15%">历史转移价格</th>
<%--			           <td align="center">操作</td>--%>
			        </tr>
			        <%if(poolList!=null && poolList.size() > 0){
			        	for(FtpPoolInfo ftpPoolInfo : poolList){%>
			        <tr id="<%=ftpPoolInfo.getPoolNo() %>">
			        	<td align="center"><%=ftpPoolInfo.getPoolNo() %>
			        	<input type="hidden" value="<%=ftpPoolInfo.getPoolNo() %>" id="poolNo" name="poolNo"/>
			        	</td>
			        	<td align="center"><%=ftpPoolInfo.getPoolName() %></td>
			        	<td align="center"><%=ftpPoolInfo.getPoolType().equals("1")?"资产":"负债" %>
			        	<input type="hidden" value="<%=ftpPoolInfo.getPoolType() %>" id="poolType" name="poolType"/>
			        	</td>
			        	<td align="center"><input type="text" name="adjustValue" size="15" id="adjustValue<%=ftpPoolInfo.getPoolNo() %>" value="0.0" onchange="setValue(<%=ftpPoolInfo.getPoolNo() %>)" /></td>
			        	<td align="center"><input type="text" name="resultPrice" size="15" id="resultPrice<%=ftpPoolInfo.getPoolNo() %>" value="" readonly="readonly" /></td>
			           <td align="center"><input type="button" name="Submit1" value="数据分析" onclick="dataAnalyze(<%=ftpPoolInfo.getPoolNo() %>)" class="button"/></td>
			           <td align="center"><input type="button" name="Submit1" value="历史转移价格" onclick="showHistoryTransPrice(<%=ftpPoolInfo.getPoolNo() %>)" class="button"/></td>
			        </tr>
			        <% }} %>
			        
		          </table></td>
	</tr>
</table>
<%if(poolList!=null && poolList.size() > 0){
	for(FtpPoolInfo ftpPoolInfo : poolList){
	  if(ftpPoolInfo.getPoolType().equals("1")){%>
<div style="display: none" id="result<%=ftpPoolInfo.getPoolNo() %>" align="center">
   <table class="table" width="600" align="center">
   <tr>
   <th width="100" align="center">投资收益率</th>
   <th width="100" align="center">信用风险修正</th>
   <th width="120" align="center">债券收益差值修正</th>
   <th width="100" align="center">经营策略调整</th>
   <th width="100" align="center">利差修正</th>
   <th width="100" align="center">转移价格</th>
   </tr>
   <tr>
   <td align="center" id="tzsyl<%=ftpPoolInfo.getPoolNo() %>"></td>
   <td align="center" id="xyfxxz1<%=ftpPoolInfo.getPoolNo() %>"></td>
   <td align="center" id="qwsyltz1<%=ftpPoolInfo.getPoolNo() %>"></td>
   <td align="center" id="jycltz1<%=ftpPoolInfo.getPoolNo() %>"></td>
   <td align="center" id="lcxz1<%=ftpPoolInfo.getPoolNo() %>"></td>
   <td align="center" id="zyjg1<%=ftpPoolInfo.getPoolNo() %>"></td>
   </tr>
</table>
</div>
<%}else{ %>
<div style="display: none" id="result<%=ftpPoolInfo.getPoolNo() %>" align="center">
   <table class="table" width="600" align="center">
   <tr>
   <th width="100" align="center">融资成本率</th>
   <th width="100" align="center">信用风险修正</th>
   <th width="120" align="center">债券收益差值修正</th>
   <th width="100" align="center">经营策略调整</th>
   <th width="100" align="center">利差修正</th>
   <th width="80" align="center">转移价格</th>
   </tr>
   <tr>
   <td align="center" id="rzcbl<%=ftpPoolInfo.getPoolNo() %>"></td>
   <td align="center" id="xyfxxz2<%=ftpPoolInfo.getPoolNo() %>"></td>
   <td align="center" id="qwsyltz2<%=ftpPoolInfo.getPoolNo() %>"></td>
   <td align="center" id="jycltz2<%=ftpPoolInfo.getPoolNo() %>"></td>
   <td align="center" id="lcxz2<%=ftpPoolInfo.getPoolNo() %>"></td>
   <td align="center" id="zyjg2<%=ftpPoolInfo.getPoolNo() %>"></td>
   </tr>
</table>
</div>
<%}}} %>

<input type="hidden" name="methodName" value="" id="methodName"/>
<input type="hidden" name="url" value="dzjcList" id="url"/>

</body>
</html>
<script type="text/javascript" language="javascript">
function change(pool){//select的value包括poolNo+poolType
	var pools = pool.split('+');
	$('poolNo').value = pools[0];	
	$('poolType').value = pools[1];	
}
var timer;
function showMessage(){
    var obj=document.getElementById("mydiv");
    obj.style.display="block";
}
function hideMessage(){
    document.getElementById("mydiv").style.display="none";
}

function priceCalculate(FormName)
{
	var date = window.parent.document.getElementById("date").value;
	var brNo = window.parent.document.getElementById("brNo").value;
	var currencySelectId = window.parent.document.getElementById("currencySelectId").value;
	var adjustValues = document.getElementsByName("adjustValue");
	var poolNos = document.getElementsByName("poolNo");
	var poolTypes = document.getElementsByName("poolType");
	var adjustValue = new Array();
	var poolNo = new Array();
	var poolType = new Array();
	for(var i = 0; i < adjustValues.length; i++) {
		adjustValue.push(adjustValues[i].value);
	}
	for(var i = 0; i < poolNos.length; i++) {
		poolNo.push(poolNos[i].value);
	}
	for(var i = 0; i < poolTypes.length; i++) {
		poolType.push(poolTypes[i].value);
	}
	parent.parent.parent.parent.openNewDiv();	
	var url = "<%=request.getContextPath()%>/UL03_calculate.action";
    new Ajax.Request( 
    url, 
     {   
      method: 'post',   
      parameters: {poolNo:poolNo,brNo:brNo,date:date,poolType:poolType,currencySelectId:currencySelectId,adjustValue:adjustValue,t:new Date().getTime()},
      onSuccess: function(resp) 
       {  
   	   art.dialog({
     	    title:'成功',
 		    icon: 'succeed',
 		    content: '定价成功！',
  		    cancelVal: '关闭',
  		    cancel: true
  	 });
         var result = resp.responseText;
         var resultArray = result.split('|');
         for(var j = 0; j < resultArray.length; j++) {
        	 var results = resultArray[j].split(',');
             $('resultPrice'+results[0]).value = results[2];
             if (results[1] == '1') {
            	 $('zyjg1'+results[0]).innerText = results[2]+"%";
                 $('tzsyl'+results[0]).innerText = results[3]+"%";
                 $('xyfxxz1'+results[0]).innerText = results[4]+"%";
                 $('qwsyltz1'+results[0]).innerText = results[5]+"%";
                 $('jycltz1'+results[0]).innerText = results[6]+"%";
                 $('lcxz1'+results[0]).innerText = results[7]+"%";
             }else {
            	 $('zyjg2'+results[0]).innerText = results[2]+"%";
                 $('rzcbl'+results[0]).innerText = results[3]+"%";
                 $('xyfxxz2'+results[0]).innerText = results[4]+"%";
                 $('qwsyltz2'+results[0]).innerText = results[5]+"%";
                 $('jycltz2'+results[0]).innerText = results[6]+"%";
                 $('lcxz2'+results[0]).innerText = results[7]+"%";
             }
         }

     	parent.parent.parent.parent.cancel();
        }
      }
   );   	
} 
function doExport(FornName)
{   
   if ($('resultPrice').value == '') {
		alert("请先对产品进行定价计算！");
		return;
	}
	var date = window.parent.document.getElementById("date").value;
  	var brNo = window.parent.document.getElementById("brNo").value;
    window.location.href="<%=request.getContextPath()%>/UL03_doExport.action?brNo="+brNo+"&date="+date;


}  
function priceSubmit(FormName)
{
	var date = window.parent.document.getElementById("date").value;
	var brNo = window.parent.document.getElementById("brNo").value;
	var currencySelectId = window.parent.document.getElementById("currencySelectId").value;
	var adjustValues = document.getElementsByName("adjustValue");
	var poolNos = document.getElementsByName("poolNo");
	var resultPrices = document.getElementsByName("resultPrice");
	var adjustValue = new Array();
	var poolNo = new Array();
	var resultPrice = new Array();
	for(var i = 0; i < adjustValues.length; i++) {
		adjustValue.push(adjustValues[i].value);
	}
	for(var i = 0; i < poolNos.length; i++) {
		poolNo.push(poolNos[i].value);
	}
	for(var i = 0; i < resultPrices.length; i++) {
		if (resultPrices[i].value == '') {
			alert("请先对产品："+poolNos[i].value+"进行定价计算！！");
			return;
		}
		resultPrice.push(resultPrices[i].value);
	}
	timer=setInterval(showMessage,0);	
	var url = "<%=request.getContextPath()%>/UL03_save.action";
    new Ajax.Request( 
    url, 
    {   
        method: 'post',   
        parameters: {brNo:brNo,poolNo:poolNo,date:date,currencySelectId:currencySelectId,adjustValue:adjustValue,resultPrice:resultPrice,t:new Date().getTime()},
        onSuccess: function(resp) 
        {  
	    	   art.dialog({
             	    title:'成功',
         		    icon: 'succeed',
         		    content: '发布成功！',
          		    cancelVal: '关闭',
          		    cancel: true
          	 });
            clearInterval(timer);
            hideMessage();
        }
     });
}     
function priceSfb(FormName)
{
	var date = window.parent.document.getElementById("date").value;
	var brNo = window.parent.document.getElementById("brNo").value;
	var currencySelectId = window.parent.document.getElementById("currencySelectId").value;
	var adjustValues = document.getElementsByName("adjustValue");
	var poolNos = document.getElementsByName("poolNo");
	var resultPrices = document.getElementsByName("resultPrice");
	var adjustValue = new Array();
	var poolNo = new Array();
	var resultPrice = new Array();
	for(var i = 0; i < adjustValues.length; i++) {
		adjustValue.push(adjustValues[i].value);
	}
	for(var i = 0; i < poolNos.length; i++) {
		poolNo.push(poolNos[i].value);
	}
	for(var i = 0; i < resultPrices.length; i++) {
		if (resultPrices[i].value == '') {
			alert("请先对产品："+poolNos[i].value+"进行定价计算！！");
			return;
		}
		resultPrice.push(resultPrices[i].value);
	}
	timer=setInterval(showMessage,0);	
	var url = "<%=request.getContextPath()%>/UL03_save_sfb.action";
    new Ajax.Request( 
    url, 
    {   
        method: 'post',   
        parameters: {brNo:brNo,poolNo:poolNo,date:date,currencySelectId:currencySelectId,adjustValue:adjustValue,resultPrice:resultPrice,t:new Date().getTime()},
        onSuccess: function(resp) 
        {  
	    	   art.dialog({
             	    title:'成功',
         		    icon: 'succeed',
         		    content: '试发布成功！',
          		    cancelVal: '关闭',
          		    cancel: true
          	 });
            clearInterval(timer);
            hideMessage();
        }
     });
}  
function dataAnalyze(poolNo){
	var content;
	if ($('resultPrice'+poolNo).value == '') {
		alert("请先对产品："+poolNo+"进行定价计算！！");
		return;
	}
	content = document.getElementById("result"+poolNo).innerHTML;
	art.dialog({
 	        title:'数据分析-'+poolNo,
		    content: content
	 });
}
function showHistoryTransPrice(poolNo) {
	var date = window.parent.document.getElementById("date").value;
	var brNo = window.parent.document.getElementById("brNo").value;
	var currencySelectId = window.parent.document.getElementById("currencySelectId").value;
   	art.dialog.open('<%=request.getContextPath()%>/UL03_history.action?poolNo='+poolNo+'&currencySelectId='+currencySelectId+'&date='+date+'&brNo='+brNo+'&random='+<%=Math.random()%>, {
	    title: '历史转移价格',
	    width: 800,
	    height:400
	});
}
function setValue(poolNo) {
	$('resultPrice'+poolNo).value = '';
}
</script>
