<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="java.text.*,java.util.Map,java.util.Date,com.dhcc.ftp.entity.TelMst,com.dhcc.ftp.util.CommonFunctions"%>
<html>
<head>
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="commonJs.jsp" />
</head>
<body>
<div class="cr_header">
			��ǰλ�ã�����������->��������������
</div>
<form action="<%=request.getContextPath()%>/UL03_report.action" method="get">
<%  long computeDate = CommonFunctions.GetCurrentDateInLong();//��ȡ���������
    long dataDate = CommonFunctions.GetDBSysDate();//��ȡ���ݿ�����
%>
<br/>
		<table width="90%" align="center" class="table">
			<tr>
				<td class="middle_header" colspan="8">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">��������</font>
				</td>
			</tr>
	<tr>
		<td width="20%"><p align="right">�������ͣ�</p></td>
		<td width="35%">
			<select style="width: 110" name="curveType" id="curveType" onchange="doCurveChange(this.id)">
			<option value="">--��ѡ��--</option>
			<option value="1">�ڲ�����������</option>
			<option value="2">�г�����������</option>
			</select>
		</td>
		<td width="15%">
		<p align="right">��&nbsp;&nbsp;�ڣ�</p>
		</td>
		<td width="30%">
		<input type="text" name="date" disabled="disabled" maxlength="10" value="<%=computeDate %>" size="15" /> 
<%--		<img style="CURSOR:hand" src="/ftp/pages/images/calendar.gif" width="16" height="16" alt="date" align="absmiddle" onClick="getDate0('date')"></td>--%>
	</tr>
	<tr>
		<td width="20%" align="right">�������ƣ�</td>
		<td width="35%">
			<select name="brNo" id="brNo" onchange="doBrChange(this.value)"></select>
		</td>
		<td width="15%">
		<p align="right">��Ȩ�ɱ�(%)��</p>
		</td>
		<td width="30%">
		<input type="text" name="optionsCost" onkeyup="value=value.replace(/[^\d\.]/g,'')" value="0.4" size="15"/>
	</tr>
	<tr>
	   <td width="20%">
		<p align="right">���Ե���(%)��</p>
		</td>
	   <td colspan="3">
	   <iframe id="countyAdjust" style="display:none" width="100%" src=""></iframe>
	   </td>
	</tr>
	<tr>
		<td height="41" colspan="4">
			<div align="center">
			 <input type="button" name="Submit1" value="��������" onclick="priceCalculate(this.form)" class="button">  
<%--			 <input type="button" name="Submit1" value="�鿴��ʷ" onclick="viewHistory(this.form)" class="but_02">  --%>
			 <input type="reset" name="button" onclick="doReset()" value="��&nbsp;&nbsp;��" class="button"></div>
		</td>
	</tr>
</table>
<div style="display: none" id="data2" align="center">
<br/>
   <table class="table" width="500" align="center">
   <tr>
   <th width="100" align="center">���ʳɱ���</th>
   <th width="100" align="center">���ղ���</th>
   <th width="100" align="center">���������ʵ���</th>
   <th width="100" align="center">��Ӫ���Ե���</th>
   <th width="100" align="center">ת�Ƽ۸�</th>
   </tr>
   <tr>
   <td align="center" id="rzcbl"></td>
   <td align="center" id="fxbc"></td>
   <td align="center" id="qwsyltz2"></td>
   <td align="center" id="jycltz2"></td>
   <td align="center" id="zyjg2"></td>
   </tr>
</table>
</div>
<div id="mydiv" style="display:none">
<center>
�����У����Ժ�...<br>
<img src="/ftp/pages/images/load.gif"/><!---��̬ͼƬ���������ʡ��-->
</center>
</div>
<input type="hidden" name="methodName" value="" id="methodName"/>
<input type="hidden" name="url" value="dzjcList" id="url"/>
</form>
</body>
</html>
<script type="text/javascript" language="javascript">
fillSelect('brNo','/ftp/fillSelect_getBrNoByLvl2');
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
	if(!(isNull(FormName.curveType,"��������"))) {
		FormName.curveType.focus();
		return;			
	}
	if(!(isNull(FormName.brNo,"����"))) {
		FormName.brNo.focus();
		return;			
	}
	//������Ĳ��Ե���
	var adjustKeyC = new Array();
    var adjustRateC= new Array();
	var adjustValueC = window.frames.countyAdjust.document.getElementsByName("adjustValueC"+$('curveType').value);
	for (var i = 0; i < adjustValueC.length; i++) {
		if (adjustValueC[i].value == null || adjustValueC[i].value == '')
			adjustValueC[i].value = '0';
		if(isNaN(adjustValueC[i].value)) {
			alert("��"+adjustValueC[i].value+"���������֣����������룡");
			adjustValueC[i].focus();
			return;
		}
		adjustRateC.push(adjustValueC[i].value);
		adjustKeyC.push(adjustValueC[i].id);
	}
	art.dialog.open('<%=request.getContextPath()%>/UL04_calculate.action?curveType='+$('curveType').value+'&brNo='+$('brNo').value+'&optionsCost='+$('optionsCost').value+'&adjustRateC='+adjustRateC+'&adjustKeyC='+adjustKeyC+'&date='+$('date').value+'&Rnd='+Math.random(), {
	    title: '��������',
	    width: '900',
	    height:'400'
	});
}   
function setValue() {
	$('resValue').value = '';
}
function doCurveChange(id) {
	if ($(id).value == '1') {
		$('date').value = <%=computeDate%>;
	}
	if ($(id).value == '2') {
		$('date').value = <%=dataDate%>;
	}
	if ($(id).value == '') {
		$('date').value = <%=dataDate%>;
	}
	doBrChange($('brNo').value);
}
function doBrChange(brNo) {
	if($('curveType').value != '' && brNo != '') {
		$('countyAdjust').style.display = "block";
		if($('curveType').value == '1')$('countyAdjust').height = "130";
		else $('countyAdjust').height = "180";
		$('countyAdjust').src = "<%=request.getContextPath()%>/UL04_getCountyAdjustValue.action?curveType="+$('curveType').value+"&brNo="+$('brNo').value+"&date="+$('date').value;
	}else {
		$('countyAdjust').src = "";
		$('countyAdjust').style.display = "none";
	}
	
}
function doReset() {
	window.location.reload();
}
function viewHistory(form) {
	window.location.href="<%=request.getContextPath()%>/UL04_.action";
}
<%--function doSaveCountyAdjust() {--%>
<%--	//����������Ĳ��Ե���--%>
<%--	var adjustKey = new Array();--%>
<%--    var adjustRate= new Array();--%>
<%--	var adjustValue = window.frames.countyAdjust.document.getElementsByName("adjustValueC"+$('curveType').value);--%>
<%--	for (var i = 0; i < adjustValue.length; i++) {--%>
<%--		if (adjustValue[i].value == null || adjustValue[i].value == '')--%>
<%--			adjustValue[i].value = '0';--%>
<%--		if(isNaN(adjustValue[i].value)) {--%>
<%--			alert("��"+adjustValue[i].value+"���������֣����������룡");--%>
<%--			adjustValue[i].focus();--%>
<%--			return;--%>
<%--		}--%>
<%--		adjustRate.push(adjustValue[i].value);--%>
<%--		adjustKey.push(adjustValue[i].id);--%>
<%--	}--%>
<%--	var url = "UL04_saveCountyAdjust.action";--%>
<%--	new Ajax.Request(url, {--%>
<%--		method : 'post',--%>
<%--		parameters : {--%>
<%--		   adjustRate:adjustRate,adjustKey:adjustKey,curveType:$('curveType').value,brNo:$('brNo').value,date:$('date').value--%>
<%--		},--%>
<%--		onSuccess : function() {--%>
<%--			ymPrompt.succeedInfo({message:'����ɹ�����',title:'success',width:200,height:150,showMask:false})--%>
<%--		}--%>
<%--	});--%>
<%--}--%>
</script>
