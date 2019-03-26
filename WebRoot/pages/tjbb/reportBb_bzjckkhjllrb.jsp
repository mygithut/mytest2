<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page
	import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst"%>
<html>
	<head>
		<title>统计报表-保证金存款客户经理FTP利润表</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="../commonJs.jsp" />
        <jsp:include page="../commonDatePicker.jsp" />
		<jsp:include page="../commonExt2.0.2.jsp" /><!-- 需放到prototype.js后面 -->
		<script type="text/javascript"
			src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'></script>
        <style type="text/css">
        .ui-datepicker-calendar { 
           display: none; 
        }
        </style>
	</head>
	<body>
		<div class="cr_header">
			当前位置：统计报表->保证金存款统计报表->保证金存款客户经理FTP利润明细表
		</div>
		<% TelMst telmst = (TelMst) request.getSession().getAttribute("userBean");
		String nowDate = String.valueOf(CommonFunctions.GetDBTodayDate());
		%>
		<form name="thisform" action="" method="post">
			<table width="80%" border="0" align="center">
				<tr>
					<td>
						<table width="1000" border="0" align="left" class="table">
							<tr>
								<td class="middle_header" colspan="8">
									<font
										style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">查询</font>
								</td>
							</tr>
							<tr>
								<td width="7%" align="right">
									考核维度：
								</td>
								<td>
									<select id="assessScope" style="width: 100" onchange="changeAssessScope(this.value)">
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
								<td width="7%" align="right">
									日&nbsp;&nbsp;期：
								</td>
								<td>
								     <select style="width: 100;" name="date" id="date">
								     </select>
	                            </td>
								<td width="7%" align="right">
									机构名称：
								</td>
								<td>
									<div id='comboxWithTree1'></div>
									<input name="brNo" id="brNo" type="hidden"
										value="<%=telmst.getBrMst().getBrNo()%>" />
									<input name="manageLvl" id="manageLvl" type="hidden"
										value="<%=telmst.getBrMst().getManageLvl()%>" />
									<input name="brName" id="brName" type="hidden"
										value="<%=telmst.getBrMst().getBrName() + "[" + telmst.getBrMst().getBrNo() + "]"%>" />
								</td>
								<td width="7%" align="right">客户经理：</td>
								<td>
									<!-- 如果对应角色级别为低级，则只能查看本操作员对应的员工 -->
								<%if(telmst.getRoleMst() != null && telmst.getRoleMst().getRoleLvl().equals("1")) {%>
									<input type="hidden" name="empNo" id="empNo" value="<%=telmst.getFtpEmpInfo()==null?"":telmst.getFtpEmpInfo().getEmpNo() %>" />
									<input type="text" name="empName" id="empName" value="<%=telmst.getFtpEmpInfo()==null?"":telmst.getFtpEmpInfo().getEmpName() %>" readonly="readonly" size="15"/>
								<%}else{ %>
									<input type="hidden" name="empNo" id="empNo" value="" />
									<input type="text" name="empName" id="empName" readonly="readonly" size="15" disabled onfocus=this.blur() placeholder="请选择客户经理" value=""/>
									<input type="button" value="选&nbsp;择" class="red button" onclick="javascript:doSelEmp()" />
								<%} %>
								</td>
							</tr>
							<tr>
							<td align="center" colspan="8"> 
							<input type="button" name="Submit1" class="button"
										onClick="doQuery()" value="查&nbsp;&nbsp;询">&nbsp;&nbsp;
<%--									<input type="button" name="Submit1" class="button"--%>
<%--										onClick="doRefresh()" value="刷新缓存数据"> --%>
							</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr height="350">
					<td align="left" height="350">
						<iframe src="" id="downFrame" width="1010" height="100%"
							frameborder="no" border="0" marginwidth="0" marginheight="0"
							scrolling="no" align="middle"></iframe>
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript">	
	jQuery(function(){// dom元素加载完毕
		jQuery(".table tr:even").addClass("tr-bg1");
		jQuery(".table tr:odd").addClass("tr-bg2");
	});
	fillSelectLast('date','<%=request.getContextPath()%>/fillSelect_getReportDate.action?assessScope=-1&nowDate=<%=nowDate%>','<%=nowDate%>');
	
    function changeAssessScope(assessScope) {
    	fillSelectLast('date','<%=request.getContextPath()%>/fillSelect_getReportDate.action?assessScope='+assessScope+'&nowDate=<%=nowDate%>','<%=nowDate%>');
    }
	 function doSelEmp(){
	    	art.dialog.open('<%=request.getContextPath()%>/pages/selEmpS.jsp?Rnd='+Math.random(), {
			    title: '选择',
			    width: 800,
			    height:400,
			    id:'sel',
			    close: function (){
		    		var paths = art.dialog.data('bValue');
		    		if(paths!=null&&paths!=""){
			    		var path=paths.split("@@");
			    		document.getElementById("empNo").value=path[0];
			    		document.getElementById("empName").value=path[1];
			    	}
			     }
			});
	    }
	 function doQuery(){
			if(!isNull(document.getElementById("date"),"日期")) {
			    return;	
			}
			var date = document.getElementById("date").value;
			var assessScope = document.getElementById("assessScope");
			var assessScopeText = assessScope.options[assessScope.selectedIndex].text;
			var empNo = document.getElementById("empNo").value;
			var empName = document.getElementById("empName").value;
			var brNo =document.getElementById("brNo").value;
			var manageLvl =document.getElementById("manageLvl").value;
			var brName = document.getElementById("brName").value;
			parent.parent.parent.openNewDiv();
			var url ="<%=request.getContextPath()%>/REPORTBB_bzjckkhjllrbReport.action";
			new Ajax.Request(url, {
				method : 'post',
				parameters : {
				 	assessScopeText:assessScopeText,
				 	empName:empName,
				 	empNo:empNo,
				 	date:date,
				 	brName:brName,
				 	brNo:brNo,
				 	manageLvl:manageLvl,
				 	assessScope:assessScope.value
				},
				onSuccess : function() {
					document.getElementById("downFrame").src="pages/tjbb/reportBb_bzjckkhjllrb_list.jsp"; 
			    }
		    });
		}

function doRefresh() {
	if(!isNull(document.getElementById("date"),"日期")) {
	    return;	
	}
	var brNo = <%=telmst.getBrMst().getBrNo()%>;
	var manageLvl =<%=telmst.getBrMst().getManageLvl()%>;
	var date = document.getElementById("date").value;
	var assessScope = document.getElementById("assessScope").value;
	var url = "<%=request.getContextPath()%>/REPORTBB_clearCache.action";
	new Ajax.Request(url, {
		method : 'post',
		parameters : {
			brNo:brNo,manageLvl:manageLvl,date:date,assessScope:assessScope,t:new Date().getTime()
		},
		onSuccess : function() {
	    	   art.dialog({
             	    title:'成功',
         		    icon: 'succeed',
         		    content: '刷新成功！',
          		    cancelVal: '关闭',
          		    cancel: true
          	 });
	   }
    });
}
</script>
</html>
