<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page
	import="java.text.*,java.sql.*,java.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.TelMst"%>
<html>
	<head>
		<title>ͳ�Ʊ���-ȫ�����л���FTP������ܱ�</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="../commonJs.jsp" />
        <jsp:include page="../commonDatePicker.jsp" />
		<jsp:include page="../commonExt2.0.2.jsp" /><!-- ��ŵ�prototype.js���� -->
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
			��ǰλ�ã�ͳ�Ʊ���->��������->ȫ�����л���FTP������ܱ�
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
										style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">��ѯ</font>
								</td>
							</tr>
							<tr>
								<td width="15%" align="right">
									����ά�ȣ�
								</td>
								<td width="15%">
									<select id="assessScope" style="width: 100" onchange="changeAssessScope(this.value)">
										<option value="-1">
											�¶�
										</option>
										<option value="-3">
											����
										</option>
										<option value="-12">
											���
										</option>
									</select>
								</td>
							<%--	<td width="15%" align="right">ͳ�Ƽ���</td>--%>
							<%--	<td>
									<select id="brCountLvl" style="width:100">
										<option value="1" selected="selected">֧��</option>
										<option value="0">����</option>
									</select>
								</td>--%>
								<td width="15%" align="right">
									��&nbsp;&nbsp;�ڣ�
								</td>
								<td width="15%">
								     <select style="width: 100;" name="date" id="date">
								     </select>
	                            </td>
								<td width="15%" align="right">
									�������ƣ�
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
							<input type="button" name="Submit1" class="button"	onClick="doQuery()" value="��&nbsp;&nbsp;ѯ"/>
							 <!-- <input id = 'doRefresh'  style="display: none" type="button" name="Submit1" class="button"			onClick="doRefresh_A()" value="ˢ�»�������"/> -->  
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
	jQuery(function(){// domԪ�ؼ������
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
	if(!isNull(document.getElementById("date"),"����")) {
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
	//ֱ�ӽ�action�������ҳ�渳ֵ��iframe����action��ʱ̫��ʱ(���6��7��������ʱ)���ͻ����action����õ���ҳ������ʵ��û�и�ֵ��iframe��action�����ҳ�������ǻ�����γɵģ����γɺ�û�и�ֵ��iframe��
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
	if(!isNull(document.getElementById("date"),"����")) {
	    return;	
	}
	var brNo =document.getElementById("brNo").value;
	var manageLvl =document.getElementById("manageLvl").value;
	var date = document.getElementById("date").value;
	var assessScope = document.getElementById("assessScope").value;
	if(manageLvl > '2') {
        alert("��ѡ�������缰���µĻ�������");
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
             	    title:'�ɹ�',
         		    icon: 'succeed',
         		    content: 'ˢ�³ɹ���',
          		    cancelVal: '�ر�',
          		    cancel: true
          	 });
	   }
    });
}
</script>
</html>
