<%@page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.util.CommonFunctions"%>
<%@ page import="com.dhcc.ftp.util.FtpUtil" %>
<%@ page import="com.dhcc.ftp.entity.TelMst" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String now=String.valueOf(CommonFunctions.GetDBSysDate());
	TelMst telmst = (TelMst) request.getSession().getAttribute("userBean");
%>


<html>
	<head>
	<style type="text/css">
	.ui-datepicker-calendar { 
		display: none; 
	}
	</style>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
		<jsp:include page="commonDatePicker.jsp" />
		<jsp:include page="commonExt2.0.2.jsp" /><!-- ��ŵ�prototype.js���� -->

		<title>���Ե���</title>
	</head>
	<body>
		<div class="cr_header">
			��ǰλ�ã�����ƥ��->���Ե���
		</div>
		<table width="1000px" border="0" cellspacing="0" cellpadding="0" align="center">
		<tr>
			<td>
			     <table  class="table"  align="left">
					<tr>
						<td class="middle_header" colspan="6"><font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">��ѯ</font>	</td>
					</tr>
					 <tr>
						 <td width="10%" align="right">�������ƣ�</td>
						 <td width="20%">
							 <select name="brNo" id="brNo"></select>
						 </td>
						 <td width="20%" align="right">ҵ�����ͣ�</td>
						 <td width="10%">
							 <select name="businessNo" id="businessNo" onchange="getPrdtName(this.id)">
							 </select>
						 </td>
						 <td width="20%" align="right">��Ʒ���ƣ�</td>
						 <td>
							 <select name="prdtNo" id="prdtNo" disabled="disabled">
								 <option value="">��ѡ��ҵ������</option>
							 </select>
						 </td>
					 </tr>
			<tr>
		        <td align="center" colspan="6">
		        <input id="query" name="query" class="button" type="button" id="query" height="20" onClick="javascript:onSumbit()" value="��&nbsp;&nbsp;ѯ" />
		        <input name="back" class="button" type="button" id="back" height="20" onClick="doClear()" value="��&nbsp;&nbsp;��" />
		          </td>
		      </tr>
			</table>
			</td>
		</tr>
			<tr>
				<td>
					<table width="100%" align="left">
						<tr>
							<td>
								<iframe src="" id="iframe" name="iframe" width="1000px"	height="350" frameborder="no" marginwidth="0" marginheight="0"		scrolling="no" allowtransparency="yes" align="middle"></iframe>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</body>
	<script type="text/javascript">
	jQuery(function(){// domԪ�ؼ������
		jQuery(".table tr:even").addClass("tr-bg1");
		jQuery(".table tr:odd").addClass("tr-bg2");
	});
    fillSelectLast('brNo','fillSelect_getBrNoByLvl2_sylqx','<%=FtpUtil.getXlsBrNo_sylqx(telmst.getBrMst().getBrNo(),telmst.getBrMst().getManageLvl())%>');
	fillSelect('businessNo','<%=request.getContextPath()%>/fillSelect_getBusinessName.action');
<%--	j(function() {--%>
<%--        j("#adjustDate").datepicker(--%>
<%--    	{--%>
<%--    		changeMonth: true, --%>
<%--    		changeYear: true, --%>
<%--    		showButtonPanel: true, --%>
<%--    		dateFormat: 'yymm01',--%>
<%--    		showOn: 'button', --%>
<%--    		buttonImage: '<%=basePath%>/pages/js/JQueryDatePicker/themes/base/images/calendar.gif',--%>
<%--    		buttonImageOnly: true,--%>
<%--    		onClose: function(dateText, inst) { --%>
<%--    		var month = j("#ui-datepicker-div .ui-datepicker-month :selected").val(); --%>
<%--    		var year = j("#ui-datepicker-div .ui-datepicker-year :selected").val(); --%>
<%--    		j(this).datepicker('setDate', new Date(year, month, 1)); --%>
<%--    		} --%>
<%--    	});--%>
<%--    });--%>

    
	function onSumbit() {
		var brNo=document.getElementById('brNo');
		var prdtNo=document.getElementById('prdtNo');
		var businessNo=document.getElementById("businessNo");
<%--		if (adjustDate == '') {--%>
<%--			alert("��ѡ��ʱ��!!"); --%>
<%--			return;--%>
<%--		}--%>
<%--		if(!(isNull(adjustDate,"����"))) {--%>
<%--			return false;			--%>
<%--		}--%>
<%--		if(!(isNull(prdtNo,"��Ʒ���"))) {--%>
<%--			return false;			--%>
<%--		}--%>
		window.frames.iframe.location.href = "<%=basePath%>/CLTZ_getList.action?productNo="+prdtNo.value+"&businessNo="+businessNo.value+"&brNo="+brNo.value;
	}

	function getPrdtName(businessNo) {
		var business = $(businessNo).value;
		if (business != '') {
			$("prdtNo").disabled = false;
			document.getElementById("prdtNo").options.length=0;
			document.getElementById("prdtNo").add(new Option("--��ѡ��--",""));
		   	var prdtNameList ;
		   	var url = '<%=request.getContextPath()%>/fillSelect_getPrdtName.action?businessNo='+business;
		    new Ajax.Request( 
		    url, 
		     {   
		      method: 'post',   
		      parameters: {t:new Date().getTime()},
		      onSuccess: function(transport) 
		       {  
		    	  prdtNameList = transport.responseText.split(",");
		          $A(prdtNameList).each(
		             function(index){
		             	//Option������ǰ����text��������value
		                  var opt= new Option(index.split('|')[1],index.split('|')[0]);
				          document.getElementById("prdtNo").add(opt);
		             }
		          );
		        }
		      }
		   );
		}else {
			$("prdtNo").value = '';
			$("prdtNo").disabled = true;
		}
	}
	</script>
</html>

