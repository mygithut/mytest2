<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst" %>
<html>
<head>
<title>�Է���ͳ�Ʊ���-����ӯ������</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="../commonJs.jsp" />
</head>
<body>
<div class="cr_header">
			��ǰλ�ã��Է���ͳ�Ʊ���->����ӯ������_�Է���
		</div>
		<%
		String nowDate = String.valueOf(CommonFunctions.GetDBSysDate());
		//�ж������Ƿ�����ĩ�����ڼ�һ�������������³�����Ϊ������ĩ������ȡ����ĩ
		//if(!CommonFunctions.dateModifyD(nowDate, 1).substring(6, 8).equals("01")){
		//	nowDate = CommonFunctions.dateModifyM(nowDate, -1);
		//} 
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
		<td>
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
		<td width="15%" align="right">����ͳ�Ƽ���</td>
		<td>
		<select id="brCountLvl" style="width:100">
		   <option value="1">֧��</option>
		   <option value="0">����</option>
		</select>
		</td>
		<td width="15%" align="right">�������ƣ�</td>
		<td>
          <select name="brNo" id="brNo"></select>
          <input name="manageLvl" id="manageLvl" type="hidden" value="2"/>
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
fillSelect('brNo','fillSelect_getBrNoByLvl2');
function doQuery(){
	if(!(isNull(document.getElementById("date"),"����"))) {
		return  ;			
	}
	if(!(isNull(document.getElementById("brNo"),"����"))) {
		return  ;			
	}
	   var brNo =document.getElementById("brNo");
	   var manageLvl =document.getElementById("manageLvl").value;
	   var date =document.getElementById("date").value;
	   var assessScope = document.getElementById("assessScope");
	   var brCountLvl = document.getElementById("brCountLvl");
	   var brCountLvlText = brCountLvl.options[brCountLvl.selectedIndex].text;
	   var assessScopeText = assessScope.options[assessScope.selectedIndex].text;
	   var brName = brNo.options[brNo.selectedIndex].text;
	   window.frames.downFrame.location.href ='<%=request.getContextPath()%>/REPORTSFB_jgylpmReport.action?brName='+encodeURI(brName)+'&brCountLvlText='+encodeURI(brCountLvlText)+'&assessScopeText='+encodeURI(assessScopeText)+'&brNo='+brNo.value+'&manageLvl='+manageLvl+'&date='+date+'&assessScope='+assessScope.value+'&brCountLvl='+brCountLvl.value;
	   parent.parent.parent.openNewDiv();
		}
</script>
</html>
