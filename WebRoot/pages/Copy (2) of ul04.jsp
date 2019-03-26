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
			当前位置：收益率曲线->收益率曲线生成
</div>
<form action="<%=request.getContextPath()%>/UL03_report.action" method="get">
<%  long computeDate = CommonFunctions.GetCurrentDateInLong();//获取计算机日期
    long dataDate = CommonFunctions.GetDBSysDate();//获取数据库日期
%>
<br/>
		<table width="90%" align="center" class="table">
			<tr>
				<td class="middle_header" colspan="8">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">曲线生成</font>
				</td>
			</tr>
	<tr>
		<td width="20%"><p align="right">曲线类型：</p></td>
		<td width="35%">
			<select style="width: 110" name="curveType" id="curveType" onchange="doCurveChange(this.id)">
			<option value="">--请选择--</option>
			<option value="1">内部收益率曲线</option>
			<option value="2">市场收益率曲线</option>
			</select>
		</td>
		<td width="15%">
		<p align="right">日&nbsp;&nbsp;期：</p>
		</td>
		<td width="30%">
		<input type="text" name="date" disabled="disabled" maxlength="10" value="<%=computeDate %>" size="15" /> 
<%--		<img style="CURSOR:hand" src="/ftp/pages/images/calendar.gif" width="16" height="16" alt="date" align="absmiddle" onClick="getDate0('date')"></td>--%>
	</tr>
	<tr>
		<td width="20%" align="right">机构名称：</td>
		<td width="35%">
			<select name="brNo" id="brNo" onchange="doBrChange(this.value)"></select>
		</td>
		<td width="15%">
		<p align="right">期权成本(%)：</p>
		</td>
		<td width="30%">
		<input type="text" name="optionsCost" onkeyup="value=value.replace(/[^\d\.]/g,'')" value="0.4" size="15"/>
	</tr>
	<tr>
	   <td width="20%">
		<p align="right">策略调整(%)：</p>
		</td>
	   <td colspan="3">
	   <iframe id="countyAdjust" style="display:none" width="100%" src=""></iframe>
	   </td>
	</tr>
	<tr>
		<td height="41" colspan="4">
			<div align="center">
			 <input type="button" name="Submit1" value="曲线生成" onclick="priceCalculate(this.form)" class="button">  
<%--			 <input type="button" name="Submit1" value="查看历史" onclick="viewHistory(this.form)" class="but_02">  --%>
			 <input type="reset" name="button" onclick="doReset()" value="重&nbsp;&nbsp;置" class="button"></div>
		</td>
	</tr>
</table>
<div style="display: none" id="data2" align="center">
<br/>
   <table class="table" width="500" align="center">
   <tr>
   <th width="100" align="center">融资成本率</th>
   <th width="100" align="center">风险补偿</th>
   <th width="100" align="center">期望收益率调整</th>
   <th width="100" align="center">经营策略调整</th>
   <th width="100" align="center">转移价格</th>
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
计算中，请稍候...<br>
<img src="/ftp/pages/images/load.gif"/><!---动态图片，这里可以省略-->
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
	if(!(isNull(FormName.curveType,"曲线类型"))) {
		FormName.curveType.focus();
		return;			
	}
	if(!(isNull(FormName.brNo,"机构"))) {
		FormName.brNo.focus();
		return;			
	}
	//县联社的策略调整
	var adjustKeyC = new Array();
    var adjustRateC= new Array();
	var adjustValueC = window.frames.countyAdjust.document.getElementsByName("adjustValueC"+$('curveType').value);
	for (var i = 0; i < adjustValueC.length; i++) {
		if (adjustValueC[i].value == null || adjustValueC[i].value == '')
			adjustValueC[i].value = '0';
		if(isNaN(adjustValueC[i].value)) {
			alert("‘"+adjustValueC[i].value+"’不是数字，请重新输入！");
			adjustValueC[i].focus();
			return;
		}
		adjustRateC.push(adjustValueC[i].value);
		adjustKeyC.push(adjustValueC[i].id);
	}
	art.dialog.open('<%=request.getContextPath()%>/UL04_calculate.action?curveType='+$('curveType').value+'&brNo='+$('brNo').value+'&optionsCost='+$('optionsCost').value+'&adjustRateC='+adjustRateC+'&adjustKeyC='+adjustKeyC+'&date='+$('date').value+'&Rnd='+Math.random(), {
	    title: '曲线生成',
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
<%--	//保存县联社的策略调整--%>
<%--	var adjustKey = new Array();--%>
<%--    var adjustRate= new Array();--%>
<%--	var adjustValue = window.frames.countyAdjust.document.getElementsByName("adjustValueC"+$('curveType').value);--%>
<%--	for (var i = 0; i < adjustValue.length; i++) {--%>
<%--		if (adjustValue[i].value == null || adjustValue[i].value == '')--%>
<%--			adjustValue[i].value = '0';--%>
<%--		if(isNaN(adjustValue[i].value)) {--%>
<%--			alert("‘"+adjustValue[i].value+"’不是数字，请重新输入！");--%>
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
<%--			ymPrompt.succeedInfo({message:'保存成功！！',title:'success',width:200,height:150,showMask:false})--%>
<%--		}--%>
<%--	});--%>
<%--}--%>
</script>
