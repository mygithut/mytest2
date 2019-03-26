<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page
	import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst"%>
<html>
	<head>
		<title>统计报表-客户经理FTP利润明细表</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="../commonJs.jsp" />
        <jsp:include page="../commonDatePicker.jsp" />
        <style type="text/css">
        .ui-datepicker-calendar { 
           display: none; 
        }
        </style>
	</head>
	<body>
		<div class="cr_header">
			当前位置：统计报表->客户经理报表->客户经理FTP利润明细表
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
								<td class="middle_header" colspan="6">
									<font
										style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">查询</font>
								</td>
							</tr>
							<tr>
								<td width="12%" align="right">
									考核维度：
								</td>
								<td width="20%">
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
								<td width="12%" align="right">
									日&nbsp;&nbsp;期：
								</td>
								<td width="20%">
								     <select style="width: 100;" name="date" id="date">
								     </select>
	                            </td>
								<td width="12%" align="right">客户经理：</td>
								<td>
									<!-- 如果对应角色级别为低级，则只能查看本操作员对应的员工 -->
								<%if(telmst.getRoleMst() != null && telmst.getRoleMst().getRoleLvl().equals("1")) {%>
									<input type="hidden" name="empNo" id="empNo" value="<%=telmst.getFtpEmpInfo()==null?"":telmst.getFtpEmpInfo().getEmpNo() %>" />
									<input type="text" name="empName" id="empName" value="<%=telmst.getFtpEmpInfo()==null?"":telmst.getFtpEmpInfo().getEmpName() %>" readonly="readonly" size="15"/>
								<%}else{ %>
									<input type="hidden" name="empNo" id="empNo" value="" />
									<input type="text" name="empName" id="empName" readonly="readonly" size="15" disabled="disabled" onfocus=this.blur value="请选择客户经理"/>
									<input type="button" value="选&nbsp;择" class="red button" onclick="javascript:doSelEmp()" />
								<%} %>
								</td>
							</tr>
							<tr>
								<td align="right">
									业务条线：
								</td>
								<td>
									<select style="width: 120" name="businessNo" id="businessNo"
										onchange="getPrdtCtgName(this.id)">
									</select>
								</td>
								<td align="right">
									产品大类：
								</td>
								<td>
									<select name="prdtCtgNo" id="prdtCtgNo" disabled="disabled"onchange="getPrdtNameByPCN(this.id)">
										<option value="">
											先选择业务条线
										</option>
									</select>
								</td>
								<td align="right">
									产品名称：
								</td>
								<td>
									<select style="width: 200" name="prdtNo" id="prdtNo" disabled="disabled">
										<option value="">
											先选择产品大类
										</option>
									</select>
								</td>
							</tr>
							<tr>
							<td align="center" colspan="6"> 
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
	fillSelect('businessNo', '<%=request.getContextPath()%>/fillSelect_getBusinessName.action');
	
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
			if(document.getElementById("empNo").value=='') {
				alert("客户经理不能为空!");
			    return;	
			}
			var date = document.getElementById("date").value;
			var assessScope = document.getElementById("assessScope");
			var assessScopeText = assessScope.options[assessScope.selectedIndex].text;
			var empNo = document.getElementById("empNo").value;
			var empName = document.getElementById("empName").value;
			var businessNo =document.getElementById("businessNo");
			var businessName =businessNo.options[businessNo.options.selectedIndex].text;
			var prdtCtgNo =document.getElementById("prdtCtgNo");
			var prdtCtgName =prdtCtgNo.options[prdtCtgNo.options.selectedIndex].text;
			var prdtNo =document.getElementById("prdtNo");
			var prdtName =prdtNo.options[prdtNo.options.selectedIndex].text;
			parent.parent.parent.openNewDiv();
			var url ="<%=request.getContextPath()%>/REPORTBB_khjllrmxbReport.action";
			new Ajax.Request(url, {
				method : 'post',
				parameters : {
				 	assessScopeText:assessScopeText,
				 	empName:empName,
				 	empNo:empNo,
				 	businessNo:businessNo.value,
				 	businessName:businessName,
				 	prdtCtgNo:prdtCtgNo.value,
				 	prdtCtgName:prdtCtgName,
				 	prdtNo:prdtNo.value,
				 	prdtName:prdtName,
				 	date:date,
				 	assessScope:assessScope.value
				},
				onSuccess : function() {
					document.getElementById("downFrame").src="pages/tjbb/reportBb_khjllrmxb_list.jsp"; 
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
