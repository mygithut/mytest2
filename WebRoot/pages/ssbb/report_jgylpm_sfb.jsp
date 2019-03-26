<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst" %>
<html>
<head>
<title>试发布统计报表-机构盈利排名</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="../commonJs.jsp" />
</head>
<body>
<div class="cr_header">
			当前位置：试发布统计报表->机构盈利排名_试发布
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
  <table width="900" border="0" align="left" class="table">
    <tr>
				<td class="middle_header" colspan="4">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">查询</font>
				</td>
			</tr>
	<tr>
		<td width="15%" align="right">
		日&nbsp;&nbsp;期：
		</td>
		<td>
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
		<td width="15%" align="right">机构统计级别：</td>
		<td>
		<select id="brCountLvl" style="width:100">
		   <option value="1">支行</option>
		   <option value="0">分理处</option>
		</select>
		</td>
		<td width="15%" align="right">机构名称：</td>
		<td>
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
	if(!(isNull(document.getElementById("date"),"日期"))) {
		return  ;			
	}
	if(!(isNull(document.getElementById("brNo"),"机构"))) {
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
