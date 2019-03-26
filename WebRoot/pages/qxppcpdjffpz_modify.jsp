<%@ page contentType="text/html;charset=GBK"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.entity.FtpProductMethodRel,java.text.*" %>
<%@page import="com.dhcc.ftp.util.*"%>
<html>
<head>
<meta http-equiv="Expires " content="0 ">
   <meta http-equiv="Cache-Control " content="no-cache ">
   <meta http-equiv="Pragma " content="no-cache ">
   <base target="_self">
<title>配置期限匹配产品定价方法</title>
<jsp:include page="commonJs.jsp" />
</head>
<body>
<br/>
<form action="<%=request.getContextPath()%>/QXPPCPDJFFPZ_save.action"  method="post" name="save" id="save">
<%
FtpProductMethodRel ftpProductMethodRel = (FtpProductMethodRel)request.getAttribute("ftpProductMethodRel");
 %>
			<table class="table" width="95%" align="center">
				<tr>
					<th width="20%" align="right">
						业务名称：
					</th>
					<td width="30%">
					    <%=ftpProductMethodRel.getBusinessName() %>
					</td>
					<th width="20%" align="right">
						产品名称：
					</th>
					<td width="30%">
						<%=ftpProductMethodRel.getProductName() %>
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						定价方法：
					</th>
					<td width="30%">
                        <select name="methodNo" id="methodNo" onchange="changeMethod(this.value)">
			         	</select>
					</td>
					<th width="20%" align="right">
						收益率曲线：
					</th>
					<td width="30%">
                        <select name="curveNo" id="curveNo"  onchange="changeCurveNo(this.value)">
			         	</select>
					</td>
				</tr>

				<tr id="ldck" style="display:none">
					<th width="20%" align="right">
						流动性风险加点(%)：
					</th>
					<td width="30%">
                         <input type="text" id="lrAdjustRate" name="lrAdjustRate"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=FormatUtil.formatDoubleE(ftpProductMethodRel.getLrAdjustRate()*100) %>" />
					</td>
					<th width="20%" align="right" >
					敞口占用加点(%)：
					</th>
					<td width="30%">
                         <input type="text" id="epsAdjustRate" name="epsAdjustRate"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  value="<%=FormatUtil.formatDoubleE(ftpProductMethodRel.getEpsAdjustRate()*100) %>"/>
					</td>
				</tr>

<!--				<tr id="adjustRateTr">-->
<!--				   <th width="20%" align="right">调整利率(%)：</th>-->
<!--				   <td colspan="3">-->
<!--				   <input type="text" id="adjustRate" name="adjustRate" onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=FormatUtil.formatDoubleE(ftpProductMethodRel.getAdjustRate()*100) %>">-->
<!--				   </td>-->
<!--				</tr>-->
				<tr id="assignTermTr" style="display:none">
				   <th width="20%" align="right">参考期限(天)：</th>
				   <td colspan="3">
				   <input type="text" id="assignTerm" name="assignTerm" value="<%=ftpProductMethodRel.getAssignTerm()%>">
				   </td>
				</tr>
				<tr id="appointRateTr" style="display:none">
				   <th width="20%" align="right">指定利率值(%)：</th>
				   <td >
				   <input type="text" id="appointRate" name="appointRate" onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=FormatUtil.formatDoubleE((ftpProductMethodRel.getAppointRate()==null?0:ftpProductMethodRel.getAppointRate())*100) %>">
				   </td>

				   	<th width="20%" align="right">
						是否贷款定价调整：
					</th>
					<td width="30%" >
                        <select name="istz" id="istz" style="width: 115" >
                         <option value='<%=ftpProductMethodRel.getIsTz() %>' selected="selected"><%=ftpProductMethodRel.getIsTz().equals("0")?"否":"是" %></option>
                         <option value='<%=ftpProductMethodRel.getIsTz().equals("0")?"1":"0" %>'><%=ftpProductMethodRel.getIsTz().equals("0")?"是":"否" %></option>
			         	</select>
					</td>
				</tr>





				<tr id="appointDeltaRateTr" style="display:none">
				   <th width="20%" align="right">固定利差值(%)：</th>
				   <td colspan="3">
				   <input type="text" id="appointDeltaRate" onkeyup="value=value.replace(/[^\d\.]/g,'')" name="appointDeltaRate" value="<%=FormatUtil.formatDoubleE((ftpProductMethodRel.getAppointDeltaRate()==null?0:ftpProductMethodRel.getAppointDeltaRate())*100) %>">
				   </td>
				</tr>
				<tr>
					<td height="41" colspan="4">
						<div align="center">
							<input type="button" name="Submit1" value="保&nbsp;&nbsp;存" onclick="doSave()" class="button">
							&nbsp;&nbsp;
							<input type="button" name="Submit1" value="重&nbsp;&nbsp;置" onclick="doReload()" class="button">
						</div>
					</td>
				</tr>
			</table>
			<input type="hidden" name="brNo" id="brNo" value="<%=ftpProductMethodRel.getBrNo() %>">
			<input type="hidden" name="productNo" id="productNo" value="<%=ftpProductMethodRel.getProductNo() %>">
		</form>
</body>
<script type="text/javascript">
fillSelectLast('methodNo','<%=request.getContextPath()%>/fillSelect_getMethodName.action?productNo=<%=ftpProductMethodRel.getProductNo()%>','<%=ftpProductMethodRel.getMethodNo()%>');

//固定利差法和指定利率法没有收益率曲线
fillSelectLast('curveNo','<%=request.getContextPath()%>/fillSelect_getCurveName.action?methodNo=<%=ftpProductMethodRel.getMethodNo()%>','<%=ftpProductMethodRel.getCurveNo()%>')
<%if(ftpProductMethodRel.getMethodNo().equals("06")) {//如果是利率代码差额法，要显示‘参考期限’文本框%>
    $('assignTermTr').style.display = '';
    $('appointRateTr').style.display = 'none';
    $('appointDeltaRateTr').style.display = 'none';
    $('ldck').style.display = 'none';
<%}%>
<%if(ftpProductMethodRel.getMethodNo().equals("02")) {//如果是指定利率法，要显示‘指定利率值’文本框%>
$('assignTermTr').style.display = 'none';
$('appointRateTr').style.display = '';
$('appointDeltaRateTr').style.display = 'none';
$('ldck').style.display = 'none';
<%}%>
<%if(ftpProductMethodRel.getMethodNo().equals("08")) {//如果是固定利差法，要显示‘固定利差值’文本框%>
$('assignTermTr').style.display = 'none';
$('appointRateTr').style.display = 'none';
$('appointDeltaRateTr').style.display = '';
$('ldck').style.display = 'none';
<%}%>
<%if(ftpProductMethodRel.getCurveNo().equals("0200")) {//如果是固定利差法，要显示‘固定利差值’文本框%>
$('assignTermTr').style.display = 'none';
$('appointRateTr').style.display = 'none';
$('appointDeltaRateTr').style.display = 'none';
$('ldck').style.display = '';
<%}%>
function changeMethod(methodNo) {
	//转化为指定利率法，则‘收益率曲线’下拉框变为空，‘参考期限’文本框不显示，显示‘指定利率值’文本框
	if(methodNo == '02') {
		fillSelectLast('curveNo','<%=request.getContextPath()%>/fillSelect_getCurveName.action?methodNo='+methodNo,'无');
	//	fillSelectLast('istz','<%=request.getContextPath()%>/fillSelect_getDkIsTz.action?productNo=<%=ftpProductMethodRel.getProductNo()%>','<%=ftpProductMethodRel.getIsTz()%>');
		$('assignTerm').value = "0";
		$('appointRateTr').style.display = '';
		if(<%=ftpProductMethodRel.getBusinessNo().startsWith("YW2")%>){
		 var appointLoad =  document.getElementById('istz');
		       appointLoad.disabled = true;
		}
        $('assignTermTr').style.display = 'none';
        $('appointRateTr').style.display = '';
        $('appointDeltaRateTr').style.display = 'none';
        $('ldck').style.display = 'none';
	}
	//如果转化为利率代码差额法(有收益率曲线)，则显示‘收益率曲线’下拉框，显示‘参考期限’文本框，不显示‘指定利率值’文本框
	if(methodNo == '06') {
		fillSelectLast('curveNo','<%=request.getContextPath()%>/fillSelect_getCurveName.action?methodNo='+methodNo,'');
		$('appointRate').value = "0";
        $('assignTermTr').style.display = '';
        $('appointRateTr').style.display = 'none';
        $('appointDeltaRateTr').style.display = 'none';
        $('ldck').style.display = 'none';
	}
	//如果从加权利率法转化为指定利率法，则显示‘指定利率值’文本框
<%--	if(oldMethodNo == '07' && methodNo == '02') {--%>
<%--		$('appointRateTr').style.display = "block";--%>
<%--	}--%>
	//如果转化为加权利率法，则不显示‘指定利率值’文本框
	if(methodNo == '07') {
		fillSelectLast('curveNo','<%=request.getContextPath()%>/fillSelect_getCurveName.action?methodNo='+methodNo,'');
		$('appointRate').value = "0";
        $('assignTermTr').style.display = 'none';
        $('appointRateTr').style.display = 'none';
        $('appointDeltaRateTr').style.display = 'none';
        $('ldck').style.display = 'none';
	}
	//如果转化为固定利差法，则‘收益率曲线’下拉框变为空，显示‘固定利差值’文本框
	if(methodNo == '08') {
		fillSelectLast('curveNo','<%=request.getContextPath()%>/fillSelect_getCurveName.action?methodNo='+methodNo,'无');
        $('assignTermTr').style.display = 'none';
        $('appointRateTr').style.display = 'none';
        $('appointDeltaRateTr').style.display = '';
        $('ldck').style.display = 'none';
	}
	//如果转化为原始期限匹配法，则不显示‘固定利差值’文本框
	if(methodNo == '01'||methodNo == '04'||methodNo == '03'||methodNo == '05') {
		fillSelectLast('curveNo','<%=request.getContextPath()%>/fillSelect_getCurveName.action?methodNo='+methodNo,'');
		$('appointDeltaRate').value = "0";
		$('istz').options[$('istz').selectedIndex].value = '0';

        $('assignTermTr').style.display = 'none';
        $('appointRateTr').style.display = 'none';
        $('appointDeltaRateTr').style.display = 'none';
        $('ldck').style.display = 'none';
	}
}

function changeCurveNo(curveNo){
    if(curveNo=='0200'){
       $('ldck').style.display = '';
    }else{
       $('ldck').style.display = "none";
    }
}
function doSave() {
	if($('methodNo').value==''){
        alert("‘定价方法’不能为空！");
        return;
    }
	if($('curveNo').value==''){
        alert("‘收益率曲线’不能为空！");
        return;
    }
<%--	if(isNaN($('adjustRate').value)){--%>
<%--        alert("‘调整利率’必须为数字！");--%>
<%--        return;--%>
<%--    }--%>
	if(isNaN($('appointRate').value)){
        alert("‘指定利率值’必须为数字！");
        return;
    }
	if(isNaN($('assignTerm').value)){
        alert("‘参考期限’必须为数字！");
        return;
    }
	if(isNaN($('appointDeltaRate').value)){
        alert("‘固定利差值’必须为数字！");
        return;
    }
    if(document.getElementById("curveNo").value!='0200'){
    	document.getElementById("epsAdjustRate").value ="";
    	document.getElementById("lrAdjustRate").value ="";

    }
	var methodName = $('methodNo').options[$('methodNo').selectedIndex].text;
	var availableMethod;//其它可用定价方法
	var availableMethodNo;//其它可用定价方法编号
	var isTz = $('istz').options[$('istz').selectedIndex].value;
	for(var i=0;i<$("methodNo").length;i++){
		if($("methodNo").options[i].value!='' && $("methodNo").options[i].value!=$('methodNo').value){
			availableMethodNo = $("methodNo").options[i].value;
			availableMethod = $('methodNo').options[i].text;
			break;
		}
	}
	$('save').request({
        method:"post",
        parameters:{methodName : methodName,availableMethodNo:availableMethodNo,availableMethod:availableMethod,isTz:isTz, t:new Date().getTime()},
        onComplete: function(){
	    	   art.dialog({
              	    title:'成功',
          		    icon: 'succeed',
          		    content: '保存成功！',
          		    ok: function () {
          		    	ok();
          		        return true;
          		    }
           	 });
      }
    });
}
function ok() {
    window.parent.frames.downframe.location.reload();
}
function doReload() {
    window.location.reload();
}
</script>
</html>
