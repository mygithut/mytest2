<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst" %>
<html>
<head>
<title>试发布统计报表-资金中心利差分析</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="../commonJs.jsp" />

</head>
<body>
<div class="cr_header">
			当前位置：试发布统计报表->资金中心利差分析_试发布
		</div>
		<%
		String nowDate = String.valueOf(CommonFunctions.GetDBSysDate());
		//判断日期是否是月末：日期加一天后如果是下月月初，则为本月月末，否则，取上月末
		//if(!CommonFunctions.dateModifyD(nowDate, 1).substring(6, 8).equals("01")){
		//	nowDate = CommonFunctions.dateModifyM(nowDate, -1);
		//} 
		%>
<form name="thisform" action="" method="post">
<table width="80%" border="0" align="center">
		   <tr>
		      <td>
  <table width="700" border="0" align="center" class="table">
    <tr>
				<td class="middle_header" colspan="4">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">查询</font>
				</td>
			</tr>
	<tr>
		<td width="15%" align="right">
		日&nbsp;&nbsp;期：
		</td>
		<td width="35%">
		<%=nowDate %>
		<input type="hidden" value="<%=nowDate %>" id="date" name="date"/>
		</td>
	    <td width="15%" align="right">考核维度：</td>
		<td>
		<select id="assessScope" style="width:100">
		   <option value="-1">月度</option>
		   <option value="-3">季度</option>
		   <option value="-12">年度</option>
		</select>
		</td>
	</tr>
	<tr>
		<td width="15%" align="right">机构名称：</td>
		<td colspan="3">
          <select name="brNo" id="brNo"></select>
          <input name="manageLvl" id="manageLvl" type="hidden" value="2"/>
		</td>
	</tr>
	<tr>
				<td colspan="4" align="center">
						<input type="button" name="Submit1" class="button" onClick="doQuery()" value="查&nbsp;&nbsp;询">
				</td>
			</tr>
		</table>
		      </td>
		   </tr>
		   <tr height="350">
		      <td align="center">
		        <iframe src="" id="downFrame" width="700" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" align="middle"></iframe> 
		      </td>
		   </tr>
		</table>
 </form>
</body>
<script type="text/javascript">	
fillSelect('brNo','fillSelect_getBrNoByLvl2');
function doQuery(){
	if(!(isNull(document.getElementById("date"),"日期"))) {
		return  ;			
	}
	if(!(isNull(document.getElementById("brNo"),"机构"))) {
		return  ;			
	}
	   var brNo =$("brNo");
	   var manageLvl =document.getElementById("manageLvl").value;
	   var date =document.getElementById("date").value;
	   var assessScope = document.getElementById("assessScope");
	   var assessScopeText = assessScope.options[assessScope.selectedIndex].text;
	   var brName = brNo.options[brNo.selectedIndex].text;
	   if(manageLvl == '4') {
           alert("请选择县联社及以下的机构！！");
           return;
	   }
	   window.frames.downFrame.location.href ='<%=request.getContextPath()%>/REPORTSFB_zjzxlcfxReport.action?brName='+encodeURI(brName)+'&assessScopeText='+encodeURI(assessScopeText)+'&brNo='+brNo.value+'&manageLvl='+manageLvl+'&date='+date+'&assessScope='+assessScope.value;
	   parent.parent.parent.openNewDiv();
}
</script>
</html>
