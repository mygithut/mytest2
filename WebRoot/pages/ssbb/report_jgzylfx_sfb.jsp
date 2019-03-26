<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst" %>
<html>
<head>
<title>试发布统计报表-机构总盈利分析</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="../commonJs.jsp" />
<jsp:include page="../commonExt2.0.2.jsp" /><!-- 需放到prototype.js后面 -->
<script type="text/javascript" src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'></script>
</head>
<body>
<div class="cr_header">
			当前位置：试发布统计报表->机构总盈利分析_试发布
		</div>
		<%
		String nowDate = String.valueOf(CommonFunctions.GetDBSysDate());
		//判断日期是否是月末：日期加一天后如果是下月月初，则为本月月末，否则，取上月末
		//if(!CommonFunctions.dateModifyD(nowDate, 1).substring(6, 8).equals("01")){
		//	nowDate = CommonFunctions.dateModifyM(nowDate, -1);
		//} 
		  TelMst telmst = (TelMst) request.getSession().getAttribute("userBean"); %>
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
		<div id='comboxWithTree1'></div>
		<input name="brNo" id="brNo" type="hidden" value="<%=telmst.getBrMst().getBrNo()%>" />
		<input name="manageLvl" id="manageLvl" type="hidden" value="<%=telmst.getBrMst().getManageLvl()%>" />
		<input name="brName" id="brName" type="hidden"  value="<%=telmst.getBrMst().getBrName() + "[" + telmst.getBrMst().getBrNo() + "]"%>" />
		</td>
	</tr>
	<tr>
				<td colspan="3" align="center">
				        &nbsp;&nbsp;&nbsp;<input type="button" name="Submit1" class="button" onClick="doQuery()" value="查&nbsp;&nbsp;询">
				</td>
				<td colspan="1" align="right">
						<input type="button" name="Submit1" class="button" onClick="doRefresh()" value="刷新缓存数据">&nbsp;&nbsp;&nbsp;
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
	if(!(isNull(document.getElementById("date"),"日期"))) {
		return  ;			
	}
	var brNo =document.getElementById("brNo").value;
	var manageLvl =document.getElementById("manageLvl").value;
	var date =document.getElementById("date").value;
	var assessScope = document.getElementById("assessScope");
	var assessScopeText = assessScope.options[assessScope.selectedIndex].text;
	var brName = document.getElementById("brName").value;
	if(manageLvl > '2') {
        alert("请选择县联社及以下的机构！！");
        return;
	}
	var isMx = 0;
	//直接将action计算完的页面赋值给iframe：当action花时太长时(大概6、7分钟以上时)，就会出现action计算得到的页面最终实际没有赋值给iframe【action计算的页面自身是会加载形成的，但形成后没有赋值给iframe】
	//window.frames.downFrame.location.href ='/ftp/REPORTSFB_jgzylfxReport.action?brName='+encodeURI(brName)+'&assessScopeText='+encodeURI(assessScopeText)+'&brNo='+brNo+'&manageLvl='+manageLvl+'&date='+date+'&assessScope='+assessScope.value+'&isMx='+isMx+'&isFirst=1';
	//document.getElementById("downFrame").src ='/ftp/REPORTSFB_jgzylfxReport.action?brName='+encodeURI(brName)+'&assessScopeText='+encodeURI(assessScopeText)+'&brNo='+brNo+'&manageLvl='+manageLvl+'&date='+date+'&assessScope='+assessScope.value+'&isMx='+isMx+'&isFirst=1';
	parent.parent.parent.openNewDiv();
	var url = "<%=request.getContextPath()%>/REPORTSFB_jgzylfxReport.action";
	new Ajax.Request(url, {
		method : 'post',
		parameters : {
		 	brName:brName,assessScopeText:assessScopeText,brNo:brNo,manageLvl:manageLvl,date:date,assessScope:assessScope.value,isMx:isMx,t:new Date().getTime()
		},
		onSuccess : function() {
			//alert('后台计算成功');
			document.getElementById("downFrame").src="pages/ssbb/report_jgzylfx_sfb_list.jsp?isFirst=1"; 
	    }
    });
}
function doRefresh() {
	if(!(isNull(document.getElementById("date"),"日期"))) {
		return  ;			
	}
	var brNo =document.getElementById("brNo").value;
	var manageLvl =document.getElementById("manageLvl").value;
	var date =document.getElementById("date").value;
	if(manageLvl > '2') {
        alert("请选择县联社及以下的机构！！");
        return;
	}
	var url = "<%=request.getContextPath()%>/REPORTSFB_clearCache.action";
	new Ajax.Request(url, {
		method : 'post',
		parameters : {
			brNo:brNo,manageLvl:manageLvl,date:date,t:new Date().getTime()
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
