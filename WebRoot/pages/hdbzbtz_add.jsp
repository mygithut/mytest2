<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.util.CommonFunctions"%>
<%@page import="com.dhcc.ftp.entity.TelMst"%>
<%
	TelMst telmst = (TelMst) request.getSession().getAttribute(
			"userBean");
%>
<html>
	<head>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
		<jsp:include page="commonExt2.0.2.jsp" /><!-- ��ŵ�prototype.js���� -->
		<title>�����г�ҵ����</title>
	</head>
	<body>
		
		<form action="<%=request.getContextPath()%>/HDBZBTZ_save.action" method="post" id="update" name="update" >
			<table width="80%" align="center" class="table" cellpadding="0"				cellspacing="0">
				<tr>
					<td align="right">
						��С�����(%)��
					</td>
					<td>
						<input type="text" name="minRatio" id="minRatio" maxlength="7"   onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					</td>
				</tr>
				<tr>
					<td align="right">
						�������(%)��
					</td>
					<td>
						<input type="text" name="maxRatio" id="maxRatio"  maxlength="7"	   onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					</td>
				</tr>
<%--				<tr>--%>
<%--					<td align="right">--%>
<%--						���� ��--%>
<%--					</td>--%>
<%--					<td>--%>
<%--						<div id='comboxWithTree1' style="width: 300px;"></div>--%>
<%--						<input name="brNo" id="brNo" type="hidden"		value="<%=telmst.getBrMst().getBrNo()%>" />--%>
<%--						<input name="brName" id="brName" type="hidden"	value="<%=telmst.getBrMst().getBrName() + "["+ telmst.getBrMst().getBrNo() + "]"%>" />--%>
<%--					</td>--%>
<%--				</tr>--%>
				<tr>
					<td align="right">
						����ֵ(BP)��
					</td>
					<td>
						<input type="text" name="adjustValue" id="adjustValue"   onkeyup="value=value.replace(/[^\d\.]/g,'')"/>
					</td>
				</tr>
				<tr>
					<td align="center" colspan="2">
						<input type="button" value="��&nbsp;&nbsp;��" class="red button"	onclick="javascript:doSave(this.form)" />
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript">
<%--	j(document).ready(function(){--%>
<%--		j("input[type='text']").click(function(){--%>
<%--			this.select();--%>
<%--		});--%>
<%--	});--%>
	/*��ʼ����-�������ڣ������������js*/
    function doSave(FormName) {
    	 if(!(isNull(FormName.minRatio,"��С�����"))) {
     		return false;			
         }
         if(!(isNull(FormName.maxRatio,"�������"))) {
     		return false;			
         }
         if(!(isNull(FormName.adjustValue,"����ֵ"))) {
     		return false;			
         }
         var min =FormName.minRatio.value;
         var max =FormName.maxRatio.value;
        // alert(min); alert(max);
         if(parseFloat(min,10)>=parseFloat(max,10)){
        	 alert("��Сֵ���ܴ������ֵ");
        	 return ;
          }
       
		$('update').request({   
		       method:"post",
		       onSuccess : function(response) {
			art.dialog({
        	    title:'�ɹ�',
    		    icon: 'succeed',
    		    content: '����ɹ���',
    		    ok: function () {
    		    	ok();
    		        return true;
    		    }
     	 	});
			   }
		   });	
    }
	function ok(){
		  window.parent.location.reload();
	}
	</script>
</html>

