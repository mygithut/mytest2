<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page
	import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst"%>
<html>
	<head>
		<title>统计报表-全行所有机构FTP利润汇总表</title>
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
			当前位置：统计报表->机构报表->全行所有机构FTP利润汇总表
		</div>
		<% TelMst telmst = (TelMst) request.getSession().getAttribute("userBean");
		String nowDate = String.valueOf(CommonFunctions.GetDBTodayDate());
		
		%>
		<form name="thisform" action="" method="post">
			<table width="80%" border="0" align="center">
				<tr>
					<td>
						<table width="950" border="0" align="left" class="table">
							<tr>
								<td class="middle_header" colspan="6">
									<font
										style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">查询</font>
								</td>
							</tr>
							<tr>
								<td width="15%" align="right">
									考核维度：
								</td>
								<td width="15%">
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
							<%--	<td width="15%" align="right">统计级别：</td>--%>
							<%--	<td>
									<select id="brCountLvl" style="width:100">
										<option value="1" selected="selected">支行</option>
										<option value="0">分理处</option>
									</select>
								</td>--%>
								<td width="15%" align="right">
									日&nbsp;&nbsp;期：
								</td>
								<td width="15%">
								     <select style="width: 100;" name="date" id="date">
								     </select>
	                            </td>
								<td width="15%" align="right">
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
							</tr>
							<tr>
							<td align="center" colspan="6">
							<input type="button" name="Submit1" class="button"	onClick="doQuery()" value="查&nbsp;&nbsp;询"/>
							 <!-- <input id = 'doRefresh'  style="display: none" type="button" name="Submit1" class="button"			onClick="doRefresh_A()" value="刷新缓存数据"/> -->  
							</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr height="350">
					<td align="left" height="350">
						<iframe src="" id="downFrame" width="950" height="100%"
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
<%--     if(<%=telmst.getTelNo()%>=='000000'){
          var doRefresh = document.getElementById('doRefresh');
          doRefresh.style.display='inline';
        } --%>
   
	
	fillSelectLast('date','<%=request.getContextPath()%>/fillSelect_getReportDate.action?assessScope=-1&nowDate=<%=nowDate%>','<%=nowDate%>');

    function changeAssessScope(assessScope) {
    	fillSelectLast('date','<%=request.getContextPath()%>/fillSelect_getReportDate.action?assessScope='+assessScope+'&nowDate=<%=nowDate%>','<%=nowDate%>');
    }
function doQuery(){
	if(!isNull(document.getElementById("date"),"日期")) {
	    return;	
	}
	var brNo =document.getElementById("brNo").value;
	var manageLvl =document.getElementById("manageLvl").value;
	var date = document.getElementById("date").value;
	var assessScope = document.getElementById("assessScope");
 /*   var brCountLvl = document.getElementById("brCountLvl").value;*/
    var brCountLvl ="1";
	var assessScopeText = assessScope.options[assessScope.selectedIndex].text;
	var brName = document.getElementById("brName").value;
	//直接将action计算完的页面赋值给iframe：当action花时太长时(大概6、7分钟以上时)，就会出现action计算得到的页面最终实际没有赋值给iframe【action计算的页面自身是会加载形成的，但形成后没有赋值给iframe】
	parent.parent.parent.openNewDiv();
	var url ="<%=request.getContextPath()%>/REPORTBB_qhsyjglrhzbReport.action";
	new Ajax.Request(url, {
		method : 'post',
		parameters : {
		 	assessScopeText:assessScopeText,
		 	brName:brName,
		 	brNo:brNo,
		 	superBrNo:brNo,
		 	manageLvl:manageLvl,
            brCountLvl:brCountLvl,
		 	date:date,
			isMx:'0',
			isFirst:'1',
		 	assessScope:assessScope.value
		},
		onSuccess : function() {
			document.getElementById("downFrame").src="pages/tjbb/reportBb_qhsyjglrhzb_list.jsp"; 
	    }
    });
}

function doRefresh_A() {
	if(!isNull(document.getElementById("date"),"日期")) {
	    return;	
	}
	var brNo =document.getElementById("brNo").value;
	var manageLvl =document.getElementById("manageLvl").value;
	var date = document.getElementById("date").value;
	var assessScope = document.getElementById("assessScope").value;
	if(manageLvl > '2') {
        alert("请选择县联社及以下的机构！！");
        return;
	}
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
