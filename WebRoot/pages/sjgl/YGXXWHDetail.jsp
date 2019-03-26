<%@page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.entity.TelMst"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<%@ page	import="java.sql.*,java.util.*,com.dhcc.ftp.entity.FtpEmpInfo,java.text.*"%>
<%@page	import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil,com.dhcc.ftp.util.CommonFunctions"%>

<html>
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
	<meta http-equiv="pragram" content="no-cache" />
	<head>
		<title>���/�޸�Ա����Ϣ</title>
		<jsp:include page="../commonJs.jsp" />
		<jsp:include page="../commonDatePicker.jsp" />
		<jsp:include page="../commonExt2.0.2.jsp" /><!-- ��ŵ�prototype.js���� -->
		<script type="text/javascript" src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'></script>
	</head>
	<body>
		<br />
		<form action="" method="post">
			<%
				FtpEmpInfo ftpEmpInfo = (FtpEmpInfo) request.getAttribute("ftpEmpInfo");
				TelMst telmst = (TelMst) request.getSession().getAttribute("userBean"); 
			%>
			<table class="table" width="70%" align="center">
				<tr>
					<th width="20%" align="right">
						Ա����ţ�
					</th>
					<td width="30%">
						<c:if test="${!empty requestScope.ftpEmpInfo }">${requestScope.ftpEmpInfo.empNo }
							<input type="hidden" id="empNo" name="empNo"  readonly="readonly"	value="${requestScope.ftpEmpInfo.empNo }" />
						</c:if>
						<c:if test="${empty requestScope.ftpEmpInfo }">
							<input type="text" id="empNo" name="empNo"  onkeyup="value=value.replace(/[^\da-zA-Z]/g,'')" 	value="" />
						</c:if>
					</td>
				</tr>
				
				<tr>
					<th width="20%" align="right">
						Ա��������
					</th>
					<td width="30%">
						<input type="text" id="empName" name="empName" 	value="${requestScope.ftpEmpInfo.empName }" />
					</td>
				</tr>
				
				<tr>
					<th width="20%" align="right">
						����������
					</th>
					<td width="30%">
						<div id='comboxWithTree1'></div>
						<c:if test="${!empty requestScope.ftpEmpInfo }">
							<input name="brNo" id="brNo" type="hidden" value="${requestScope.ftpEmpInfo.brMst.brNo }" />
							<input name="brName" id="brName" type="hidden"  value="${requestScope.ftpEmpInfo.brMst.brName } [${requestScope.ftpEmpInfo.brMst.brNo }]" />
						</c:if>
						<c:if test="${empty requestScope.ftpEmpInfo }">
							<input name="brNo" id="brNo" type="hidden" value="<%=telmst.getBrMst().getBrNo()%>" />
							<input name="brName" id="brName" type="hidden"  value="<%=telmst.getBrMst().getBrName() + "[" + telmst.getBrMst().getBrNo() + "]"%>" />
						</c:if>
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						Ա����λ����
					</th>
					<td width="30%">
						<select name="empLvl" id="empLvl">
							<option value="">��ѡ��</option>
							<option value="1"  <c:if test="${ftpEmpInfo.empLvl=='1' }">selected="selected"</c:if> >�߲�</option>
							<option value="2"  <c:if test="${ftpEmpInfo.empLvl=='2' }">selected="selected"</c:if>>�в�</option>
							<option value="3"  <c:if test="${ftpEmpInfo.empLvl=='3' }">selected="selected"</c:if>>�ײ�</option>
						</select>
						<%--<input type="text" id="brNo" name="brNo" 	value="${requestScope.ftpEmpInfo.empName }" />--%>
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						��λ��
					</th>
					<td width="30%">
					<select id="postNo" name="postNo">
							<option value="">��ѡ��</option>
							<option value="4"  <c:if test="${ftpEmpInfo.postNo=='4' }">selected="selected"</c:if> >�ͻ�����</option>
							<option value=""  <c:if test="${ftpEmpInfo.postNo=='' }">selected="selected"</c:if>>�ǿͻ�����</option>
						</select>
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						�������ڣ�
					</th>
					<td width="30%">
						<input type="text" id="birthdaydate"  name="birthdaydate"  readonly="readonly"	value="${requestScope.ftpEmpInfo.birthdaydate}"  maxlength="10"	 size="10" />
					</td>
				</tr>
				
				<tr>
					<th width="20%" align="right">
						�Ա�
					</th>
					<td width="30%">
						<select name="sex" id="sex">
							<option value="">��ѡ��</option>
							<option value="1"  <c:if test="${ftpEmpInfo.sex=='1' }">selected="selected"</c:if> >��</option>
							<option value="2"  <c:if test="${ftpEmpInfo.sex=='2' }">selected="selected"</c:if>>Ů</option>
						</select>
						<%--<input type="text" id="job" name="job" 	value="${requestScope.ftpEmpInfo.job }" />--%>
					</td>
				</tr>
				<tr>
					<th width="20%" align="right">
						Ա��״̬��
					</th>
					<td width="30%">
						<select name="empStatus" id="empStatus">
<%--							<option value="">��ѡ��</option>--%>
							<option value="1"  <c:if test="${ftpEmpInfo.empStatus=='1' }">selected="selected"</c:if>>����</option>
							<option value="2"  <c:if test="${ftpEmpInfo.empStatus=='2' }">selected="selected"</c:if>>��ְ</option>
						</select>
					</td>
				</tr>

				<tr>
					<td height="41" colspan="2">
						<div align="center">
						<c:if test="${empty requestScope.ftpEmpInfo }">
							<input type="button" name="Submit1" value="��&nbsp;&nbsp;��"		onClick="checkEmp(this.form)" class="button">
						</c:if>
						<c:if test="${!empty requestScope.ftpEmpInfo }">
							<input type="button" name="Submit1" value="��&nbsp;&nbsp;��"		onClick="save(this.form)" class="button">
						</c:if>
							&nbsp;&nbsp;
							<input type="reset" name="Reset" value="��&nbsp;&nbsp;��" class="button">
						</div>
					</td>
				</tr>
			</table>
<%--			<input type="hidden" id="empId" name="empId" value="${requestScope.ftpEmpInfo.empId }" />--%>
		</form>
	</body>
	<script type="text/javascript">	
	j(function() {
        j("#birthdaydate").datepicker(
    	{
    		dateFormat: 'yymmdd',
    		showOn: 'button', 
    		buttonImage: '<%=request.getContextPath()%>/pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
    		buttonImageOnly: true
    	});
    });

function checkEmp(FormName){
	var url = "<%=request.getContextPath()%>/YGXXWH_checkEmp.action";
	   new Ajax.Request( 
	        url, 
	         {  
	          method: 'post',   
	          parameters: {
	            empNo:FormName.empNo.value,
	            t:new Date().getTime()
	            },
	          onSuccess: function(val){
	        	  //alert(val.responseText);
	        	  if(val.responseText=='ok'){
	        		  save(FormName);
	        	  }else{
	        		  art.dialog({
		              	    title:'��ʾ',
		          		    icon: 'error',
		          		    content: '�˱���ѱ�ռ�ã�',
		          		    ok: function () {
		          		    	document.getElementById("empNo").select();
		          		    }
		           	 	});
	        	  }
		         }
	          });
}

function save(FormName) {
    if(!(isNull(FormName.empNo,"Ա�����"))) {
		return false;			
    }
    if(!(isNull(FormName.empName,"Ա������"))) {
		return false;			
    }
    if(!(isNull(FormName.brNo,"��������"))) {
		return false;			
    }
<%--    if(!(isNull(FormName.empLvl,"Ա����λ����"))) {--%>
<%--		return false;			--%>
<%--    }--%>
    if(FormName.brNo.value=='3403111034'){
        alert("����ѡ��[����ũ��������] !! ����");
        return ;
    }
	var url = "<%=request.getContextPath()%>/YGXXWH_save.action";
   new Ajax.Request( 
        url, 
         {  
          method: 'post',   
          parameters: {
/*            	empId:FormName.empId.value, */
            empNo:FormName.empNo.value,
            empName:FormName.empName.value,
            brNo:FormName.brNo.value,
            empLvl:FormName.empLvl.value,
            postNo:FormName.postNo.value,
            birthdaydate:FormName.birthdaydate.value,
            sex:FormName.sex.value,
            empStatus:FormName.empStatus.value,
            t:new Date().getTime()
            },
          onSuccess: function(){
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
<%--function selPost(){--%>
<%--	var url = "<%=request.getContextPath()%>/FtpBrPostRel_getPostByBrNo.action";--%>
<%--	   new Ajax.Request( --%>
<%--	        url, --%>
<%--	         {  --%>
<%--	          method: 'post',   --%>
<%--	          parameters: {--%>
<%--	            brNo:FormName.brNo.value,--%>
<%--	            t:new Date().getTime()--%>
<%--	            },--%>
<%--	          onSuccess: function(val){--%>
<%--	            	addOption(result);--%>
<%--		         }--%>
<%--	          });--%>
<%--}--%>
<%--function addOption(result){--%>
<%--	var json=eval("("+result+")"):--%>
<%--	var objSel=document.getElementById("postNo");--%>
<%--	var varItem = new Option("��ѡ��", ""); --%>
<%--	objSel.options.add(varItem);--%>
<%--	--%>
<%--	for(var i=0;i<json.length;i++){--%>
<%--		var varItem = new Option(json[i].name, json[i].value);      --%>
<%--		objSel.options.add(varItem);--%>
<%--		//var option=document.createElement ("option");--%>
<%--        // option.text=json[i].name;//name ��һ��ֵ--%>
<%--        // option.value=json[i].value;//id ��һ��ֵ--%>
<%--	}--%>
<%--}--%>
<%--selPost();--%>
</script>
</html>
