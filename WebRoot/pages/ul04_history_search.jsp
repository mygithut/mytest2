<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.util.*,com.dhcc.ftp.util.*,java.util.Map,java.util.List"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="Expires " content="0 ">
        <meta http-equiv="Cache-Control " content="no-cache,must-revalidate ">
        <meta http-equiv="Pragma " content="no-cache ">
		<title>����������-��ʷ</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="commonJs.jsp" />
<jsp:include page="commonDatePicker.jsp" />
 </head>
	<body>
	<div class="cr_header">
			��ǰλ�ã�����������->������������ʷ
    </div>
    <%  
    String computeDate =  DateUtil.getCurrentDay();//��ȡ���������
    %>
	<table width="900" border="0" align="center" class="table">
	<tr>
				<td class="middle_header" colspan="6">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">��ѯ</font>
				</td>
			</tr>
				<tr>
				    <td width="12%" align="right">�������ƣ�</td>
		            <td colspan="3">
			           <select name="brNo" id="brNo"></select>
		            </td>
		        </tr>
		        <tr>
					<td width="12%" align="right">
						�г����ͣ�
					</td>
					<td width="13%" height="11" align="left">
					<select id="curveMarketType">
					  <option value="">--��ѡ��--</option>
					  <option value="01">�����</option>
					  <option value="02">�г�</option>
					</select>
					</td>
					<td width="12%" align="right">
									 ���ڣ�
					</td>
					<td width="13%" height="11" align="left">
						<input type="text" name="curveDate" id="curveDate" size="10" maxlength="10" value="<%=computeDate %>" />
<%--						<img style="CURSOR:hand" src="/ftp/pages/images/calendar.gif" width="16" height="16" alt="date" align="absmiddle" onClick="getDate0('curveDate')">	--%>
					</td>
				</tr>
				<tr>
				    <td colspan="4" align="center">
				    <input name="query" class="button" type="button" id="query" height="20" onClick="onclick_query()" value="��&nbsp;&nbsp;ѯ" /> 
                 <input name="back" class="button" type="button" id="back" height="20" onClick="onclick_reset()" value="��&nbsp;&nbsp;��" /> 
       </td>
				</tr>
			</table>
  <iframe  id="downframe" name="downframe" width="100%" height="350" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" align="middle"></iframe>
</form>	
	<script type="text/javascript">
	jQuery(function(){// domԪ�ؼ������
		jQuery(".table tr:even").addClass("tr-bg1");
		jQuery(".table tr:odd").addClass("tr-bg2");
	});
    fillSelectLast('brNo','<%=request.getContextPath()%>/fillSelect_getBrNoByLvl2_sylqx');
	j(function() {
        j("#curveDate").datepicker(
    	{
    		dateFormat: 'yymmdd',
    		showOn: 'button', 
    		buttonImage: 'pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
    		buttonImageOnly: true
    	});
    });
	function onclick_query(){
	    debugger;
	    	var brNo =document.getElementById("brNo").value;
		   var curveMarketType =document.getElementById("curveMarketType").value;
		   var curveDate =document.getElementById("curveDate").value;
		   window.frames.downframe.location.href ='<%=request.getContextPath()%>/SYLQXLSCK_history_list.action?brNo='+brNo+'&curveMarketType='+curveMarketType+'&curveDate='+curveDate;
	}
		
		function onclick_reset(){
		   window.location.reload();
		}
		
		</script>		
	</body>
</html>
