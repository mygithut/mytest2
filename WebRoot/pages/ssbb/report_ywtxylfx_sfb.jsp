<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst" %>
<html>
<head>
<title>�Է���ͳ�Ʊ���-ҵ������ӯ������</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="../commonJs.jsp" />
<jsp:include page="../commonExt2.0.2.jsp" /><!-- ��ŵ�prototype.js���� -->
<script type="text/javascript" src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'></script>
</head>
<body>
<div class="cr_header">
			��ǰλ�ã��Է���ͳ�Ʊ���->ҵ��������ӯ������_�Է���
		</div>
		<%
		String nowDate = String.valueOf(CommonFunctions.GetDBSysDate());
		//�ж������Ƿ�����ĩ�����ڼ�һ�������������³�����Ϊ������ĩ������ȡ����ĩ
		//if(!CommonFunctions.dateModifyD(nowDate, 1).substring(6, 8).equals("01")){
		//	nowDate = CommonFunctions.dateModifyM(nowDate, -1);
		//} 
		TelMst telmst = (TelMst) request.getSession().getAttribute("userBean"); 
		%>
<form name="thisform" action="" method="post">
<table width="80%" border="0" align="center">
		   <tr>
		      <td>
  <table width="900" border="0" align="left" class="table">
    <tr>
				<td class="middle_header" colspan="4">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">��ѯ</font>
				</td>
			</tr>
	<tr>
		<td width="15%" align="right">
		��&nbsp;&nbsp;�ڣ�
		</td>
		<td width="35%">
		<%=nowDate %>
		<input type="hidden" value="<%=nowDate %>" id="date" name="date"/>
		</td>
	    <td width="15%" align="right">����ά�ȣ�</td>
		<td>
		<select id="assessScope" style="width:100">
		    <option value="-1">�¶�</option>
		   <option value="-3">����</option>
		   <option value="-12">���</option>
		</select>
		</td>
		</tr>
	    <tr>
	    <td width="15%" align="right">�������ƣ�</td>
		<td colspan="3">
		<div id='comboxWithTree1'></div>
		<input name="brNo" id="brNo" type="hidden" value="<%=telmst.getBrMst().getBrNo()%>" />
		<input name="manageLvl" id="manageLvl" type="hidden" value="<%=telmst.getBrMst().getManageLvl()%>" />
		<input name="brName" id="brName" type="hidden"  value="<%=telmst.getBrMst().getBrName() + "[" + telmst.getBrMst().getBrNo() + "]"%>" />
		</td>
	</tr>
	<tr>
				<td colspan="4" align="center">
						<input type="button" name="Submit1" class="button" onClick="doQuery()" value="��&nbsp;&nbsp;ѯ">
				</td>
			</tr>
		</table>
		      </td>
		   </tr>
		   <tr height="350">
		      <td align="left">
		        <iframe src="" id="downFrame" width="900" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" align="middle"></iframe> 
		      </td>
		   </tr>
		</table>
 </form>
</body>
<script type="text/javascript">	
function doQuery(){
	if(!(isNull(document.getElementById("date"),"����"))) {
		return  ;			
	}
	var brNo =document.getElementById("brNo").value;
	   var manageLvl =document.getElementById("manageLvl").value;
	   var date =document.getElementById("date").value;
	   var assessScope = document.getElementById("assessScope");
	   var assessScopeText = assessScope.options[assessScope.selectedIndex].text;
	   var brName = document.getElementById("brName").value;
	   if(manageLvl == '4') {
           alert("��ѡ�������缰���µĻ�������");
           return;
	   }
	   window.frames.downFrame.location.href ='<%=request.getContextPath()%>/REPORTSFB_ywtxylfxReport.action?brName='+encodeURI(brName)+'&assessScopeText='+encodeURI(assessScopeText)+'&brNo='+brNo+'&manageLvl='+manageLvl+'&date='+date+'&assessScope='+assessScope.value;
	   parent.parent.parent.openNewDiv();
		}
</script>
</html>
