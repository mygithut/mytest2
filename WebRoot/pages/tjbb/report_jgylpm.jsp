<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page
	import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.entity.TelMst"%>
<html>
	<head>
		<title>统计报表-机构盈利排名</title>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/themes/green/css/core.css"
			type="text/css">
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/css/inpage.css"
			type="text/css">
		<jsp:include page="../commonJs.jsp" />
	</head>
	<body>
	<%
	TelMst telmst = (TelMst) request.getSession().getAttribute("userBean");
	String nowDate = String.valueOf(CommonFunctions.GetDBSysDate());
	if(!CommonFunctions.dateModifyD(nowDate, 1).substring(6, 8).equals("01")){
		nowDate = CommonFunctions.dateModifyM(nowDate, -1);
	}
	%>
		<div class="cr_header">
			当前位置：统计报表->机构盈利排名
		</div>
		<form name="thisform" action="" method="post">
			<table width="80%" border="0" align="center">
				<tr>
					<td>
						<table width="900" border="0" align="left" class="table">
							<tr>
								<td class="middle_header" colspan="6">
									<font
										style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">查询</font>
								</td>
							</tr>
							<tr>
								<td width="15%" align="right" style="display:none">
									统计类型：
								</td>
								<td width="20%" style="display:none">
									<select id="tjType" name="tjType" style="width: 100" onchange="changeTjType(this.value)">
										<option value="1">
											增量
										</option>
										<option value="2" selected="selected">
											存量
										</option>
									</select>
								</td>
								<td width="20%" align="right">
									日&nbsp;&nbsp;期：
								</td>
								<td width="30%">
									<select style="width: 100;display:none" name="zlDate" id="zlDate">
									</select>
		                            <input style="display:block" value="<%=CommonFunctions.GetDBSysDate() %>" id="clDate" name="clDate" readonly="readonly"/>
								</td>
								<td width="20%" align="right">
									考核维度：
								</td>
								<td width="30%">
									<select id="assessScope" style="width: 100">
										<option value="-1">
											月度
										</option>
										<option value="-3">
											季度
										</option>
										<option value="-12">
											年度
										</option>
									</select>
								</td>
							</tr>
							<tr>
								<td width="20%" align="right">
									机构统计级别：
								</td>
								<td width="30%">
									<select id="brCountLvl" style="width: 100">
										<option value="1">
											支行
										</option>
										<option value="0">
											分理处
										</option>
									</select>
								</td>
								<td width="20%" align="right">
									机构名称：
								</td>
								<td colspan="3">
									<select name="brNo" id="brNo"></select>
									<input name="manageLvl" id="manageLvl" type="hidden" value="2" />
								</td>
							</tr>
							<tr>
								<td colspan="6" align="center">
									<input type="button" name="Submit1" class="button"
										onClick="doQuery()" value="查&nbsp;&nbsp;询">
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr height="350">
					<td align="center">
						<iframe src="" id="downFrame" width="900" height="100%"
							frameborder="no" border="0" marginwidth="0" marginheight="0"
							scrolling="no" align="middle"></iframe>
					</td>
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript">	
fillSelectLast('zlDate','<%=request.getContextPath()%>/fillSelect_getReportDate.action','<%=nowDate%>');
fillSelectLast('brNo','fillSelect_getBrNoByLvl2','<%=FtpUtil.getXlsBrNo(telmst.getBrMst().getBrNo(),telmst.getBrMst().getManageLvl())%>');
function changeTjType(tjType) {
    if(tjType == '1') {
    	document.getElementById("zlDate").style.display="block";
    	document.getElementById("clDate").style.display="none";
    }else if(tjType == '2') {
    	document.getElementById("clDate").style.display="block";
    	document.getElementById("zlDate").style.display="none";
    }else {
    	document.getElementById("clDate").style.display="none";
    	document.getElementById("zlDate").style.display="none";
    }
}

function doQuery(){
	var tjType =document.getElementById("tjType").value;
	if(tjType == '1') {
		if(!isNull(document.getElementById("zlDate"),"日期")) {
    	    return;	
		}
    }
    if(tjType == '2') {
		if(!isNull(document.getElementById("clDate"),"日期")) {
    	    return;	
		}
    }
	if(!(isNull(document.getElementById("brNo"),"机构"))) {
		return  ;			
	}
	   var brNo =document.getElementById("brNo");
	   var manageLvl =document.getElementById("manageLvl").value;
		var date = "";
		if(tjType == '1')date = document.getElementById("zlDate").value;
		else date = document.getElementById("clDate").value;
	   var assessScope = document.getElementById("assessScope");
	   var brCountLvl = document.getElementById("brCountLvl");
	      var tjType =document.getElementById("tjType").value;
	   var brCountLvlText = brCountLvl.options[brCountLvl.selectedIndex].text;
	   var assessScopeText = assessScope.options[assessScope.selectedIndex].text;
	   var brName = brNo.options[brNo.selectedIndex].text;
	   window.frames.downFrame.location.href ='<%=request.getContextPath()%>/REPORT_jgylpmReport.action?brName='+encodeURI(brName)+'&brCountLvlText='+encodeURI(brCountLvlText)+'&assessScopeText='+encodeURI(assessScopeText)+'&tjType='+tjType+'&brNo='+brNo.value+'&manageLvl='+manageLvl+'&date='+date+'&assessScope='+assessScope.value+'&brCountLvl='+brCountLvl.value;
	   parent.parent.parent.openNewDiv();
		}
</script>
</html>
