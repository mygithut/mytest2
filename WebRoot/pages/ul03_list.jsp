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
			<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">���ʽ�ض��ۼ���</font>
		</td>
	</tr>
	<tr>
	    <td align="center">
	    <div id="mydiv" style="display:none">
<center>
�����У����Ժ�...<br>
<img src="/ftp/pages/images/load.gif"/><!---��̬ͼƬ���������ʡ��-->
</center>
</div>
	    <table border="0"><tr><td><input type="button" name="Submit1" value="���ۼ���" onclick="priceCalculate(this.form)" class="button"/><div align="center">
	        </td><td><input type="button" name="Submit1" value="���۷���" onclick="priceSubmit(this.form)" class="button">
	    </td><td><input type="button" name="Submit2" value="�����Է���" onclick="priceSfb(this.form)" class="button">
	    </td><td><input type="button" name="Submit2" value="��&nbsp;��" onclick="doExport(this.form)" class="button">
	    </td></tr>
	    </table>
	    </td>
	</tr>
	<tr>
		<td>
		          <table width="95%" align="center" class="table" id="poolTable">
			        <tr>
			           <th align="center" width="8%">���</th>
			           <th align="center" width="27%">�ʽ������</th>
			           <th align="center" width="10%">�ʲ�����</th>
			           <th align="center" width="15%">��Ӫ���Ե���(%)</th>
			           <th align="center" width="15%">ת�Ƽ۸�(%)</th>
			           <th align="center" width="10%">���ݷ���</th>
			           <th align="center" width="15%">��ʷת�Ƽ۸�</th>
<%--			           <td align="center">����</td>--%>
			        </tr>
			        <%if(poolList!=null && poolList.size() > 0){
			        	for(FtpPoolInfo ftpPoolInfo : poolList){%>
			        <tr id="<%=ftpPoolInfo.getPoolNo() %>">
			        	<td align="center"><%=ftpPoolInfo.getPoolNo() %>
			        	<input type="hidden" value="<%=ftpPoolInfo.getPoolNo() %>" id="poolNo" name="poolNo"/>
			        	</td>
			        	<td align="center"><%=ftpPoolInfo.getPoolName() %></td>
			        	<td align="center"><%=ftpPoolInfo.getPoolType().equals("1")?"�ʲ�":"��ծ" %>
			        	<input type="hidden" value="<%=ftpPoolInfo.getPoolType() %>" id="poolType" name="poolType"/>
			        	</td>
			        	<td align="center"><input type="text" name="adjustValue" size="15" id="adjustValue<%=ftpPoolInfo.getPoolNo() %>" value="0.0" onchange="setValue(<%=ftpPoolInfo.getPoolNo() %>)" /></td>
			        	<td align="center"><input type="text" name="resultPrice" size="15" id="resultPrice<%=ftpPoolInfo.getPoolNo() %>" value="" readonly="readonly" /></td>
			           <td align="center"><input type="button" name="Submit1" value="���ݷ���" onclick="dataAnalyze(<%=ftpPoolInfo.getPoolNo() %>)" class="button"/></td>
			           <td align="center"><input type="button" name="Submit1" value="��ʷת�Ƽ۸�" onclick="showHistoryTransPrice(<%=ftpPoolInfo.getPoolNo() %>)" class="button"/></td>
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
   <th width="100" align="center">Ͷ��������</th>
   <th width="100" align="center">���÷�������</th>
   <th width="120" align="center">ծȯ�����ֵ����</th>
   <th width="100" align="center">��Ӫ���Ե���</th>
   <th width="100" align="center">��������</th>
   <th width="100" align="center">ת�Ƽ۸�</th>
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
   <th width="100" align="center">���ʳɱ���</th>
   <th width="100" align="center">���÷�������</th>
   <th width="120" align="center">ծȯ�����ֵ����</th>
   <th width="100" align="center">��Ӫ���Ե���</th>
   <th width="100" align="center">��������</th>
   <th width="80" align="center">ת�Ƽ۸�</th>
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
function change(pool){//select��value����poolNo+poolType
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
     	    title:'�ɹ�',
 		    icon: 'succeed',
 		    content: '���۳ɹ���',
  		    cancelVal: '�ر�',
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
		alert("���ȶԲ�Ʒ���ж��ۼ��㣡");
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
			alert("���ȶԲ�Ʒ��"+poolNos[i].value+"���ж��ۼ��㣡��");
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
             	    title:'�ɹ�',
         		    icon: 'succeed',
         		    content: '�����ɹ���',
          		    cancelVal: '�ر�',
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
			alert("���ȶԲ�Ʒ��"+poolNos[i].value+"���ж��ۼ��㣡��");
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
             	    title:'�ɹ�',
         		    icon: 'succeed',
         		    content: '�Է����ɹ���',
          		    cancelVal: '�ر�',
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
		alert("���ȶԲ�Ʒ��"+poolNo+"���ж��ۼ��㣡��");
		return;
	}
	content = document.getElementById("result"+poolNo).innerHTML;
	art.dialog({
 	        title:'���ݷ���-'+poolNo,
		    content: content
	 });
}
function showHistoryTransPrice(poolNo) {
	var date = window.parent.document.getElementById("date").value;
	var brNo = window.parent.document.getElementById("brNo").value;
	var currencySelectId = window.parent.document.getElementById("currencySelectId").value;
   	art.dialog.open('<%=request.getContextPath()%>/UL03_history.action?poolNo='+poolNo+'&currencySelectId='+currencySelectId+'&date='+date+'&brNo='+brNo+'&random='+<%=Math.random()%>, {
	    title: '��ʷת�Ƽ۸�',
	    width: 800,
	    height:400
	});
}
function setValue(poolNo) {
	$('resultPrice'+poolNo).value = '';
}
</script>
