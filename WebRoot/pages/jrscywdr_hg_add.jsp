<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.util.CommonFunctions"%>
<%@page import="com.dhcc.ftp.entity.TelMst"%>
<%
	TelMst telmst = (TelMst) request.getSession().getAttribute("userBean"); 
String acId = (String)request.getParameter("acId");
%>
<html>
	<head>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
		<jsp:include page="commonExt2.0.2.jsp" /><!-- ��ŵ�prototype.js���� -->
		<jsp:include page="commonDatePicker.jsp" />
		<script type="text/javascript" src='<%=request.getContextPath()%>/pages/js/ext-2.0.2/myjs/trctlunderorg1.js'></script>
	
		
		<title>�����г�ҵ����</title>
	</head>
	<body>
		
		<form action="<%=request.getContextPath()%>/JRSCYWDRHG_saveHg.action" method="post" id="update" name="update" >	    
		<table  width="90%" align="center" border="0" class="table">
			                <tr>
								<td align="right">��ţ�</td>
								<td><%=acId %>
								<input type="hidden" name="acId" id="acId" value="<%=acId %>" /></td>
								<td align="right" >������</td>
								<td >
									<div id='comboxWithTree1' style="width: 300px;"></div>
									<input name="brNo" id="brNo" type="hidden" value="<%=telmst.getBrMst().getBrNo()%>" />
									<input name="brName" id="brName" type="hidden"  value="<%=telmst.getBrMst().getBrName() + "[" + telmst.getBrMst().getBrNo() + "]"%>" />
								</td>
							</tr>
							<tr>

								<td align="right">
									��Ʒ���� ��
								</td>
								<td>
									<select name="prdtNo" id="prdtNo" >
										<option value="P1068@@11110100">���뷵��ծȯ</option>
										<option value="P1069@@11110200">���뷵���Ŵ��ʲ�</option>
										<option value="P1070@@11110300">���뷵��Ʊ��</option>
										<option value="P1071@@11110400">���뷵�۽����ʲ�-����</option>
										<option value="P2099@@21110100">�����ع�ծȯ��</option>
										<option value="P2100@@21110200">�����ع�����</option>
										<option value="P2101@@21110300">�����ع�Ʊ�ݿ�</option>
										<option value="P2102@@21110400">���������ع������ʲ�����</option>
									</select>
									<input type="hidden" name="prdtName" id="prdtName" />
								</td>
								<td align="right">����Ա��</td>
								<td> 
									<input type="hidden" name="khjl" id="khjl" />
									<input type="text" name="empName" id="empName" readonly="readonly" />
									<input type="button" value="ѡ��" class="red button" onclick="javascript:doSelEmp()" />
								</td>
<%--								<td align="right">��Ŀ�ţ�</td>--%>
<%--								<td>--%>
<%--									<input type="text" name="kmh" id="kmh" />--%>
<%--								</td>--%>
<%--								<td align="right">��Ʒ���</td>--%>
<%--								<td><input type="text" name="orgnlbusntyp" id="orgnlbusntyp" /></td>--%>
							</tr>
							<tr>
								<td align="right">���׶��֣�</td>
								<td><input type="text" name="cusName" id="cusName" /></td>
								<td align="right">���׶��ֱ�ţ�</td>
								<td><input type="text" name="custNo" id="custNo" /></td>
							</tr>
							<tr>
							    <td align="right">���ڽ����գ�</td>
								<td><input type="text" name="sqjgr" id="sqjgr" readonly="readonly"/></td>
								<td align="right">���ڽ����գ�</td>
								<td><input type="text" name="dqjgr" id="dqjgr" readonly="readonly"/></td>
							</tr>
							<tr>
								<td align="right">Ʊ�ţ�</td>
								<td><input type="text" name="ph" id="ph" /></td>
								<td align="right">Ʊ����(Ԫ)��</td>
								<td><input type="text" name="qmje"  onkeyup="value=value.replace(/[^\d\.]/g,'')" id="qmje"  /></td>
							</tr>
							<tr>
							    <td align="right">�ɽ����(Ԫ)��</td>
								<td colspan="1"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="sameP(this)" name="cjje" id="cjje" /></td>
								<td align="right">���(Ԫ)��</td>
								<td colspan="1"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="bal" id="bal" /></td>
							</tr>
							<tr>
							    <td align="right">�ع�����(%)��</td>
								<td colspan="3"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')" name="hglv" id="hglv" /></td>
							</tr>
<%--							<tr>--%>
<%--							    <td align="right">��Ϣ������</td>--%>
<%--								<td colspan="3"><input type="text" name="jyts" id="jyts" maxlength="3"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  /></td>--%>
<%--								--%>
<%--							</tr>--%>
			<tr>
				<td align="center" colspan="4">
					<input type="button" value="��&nbsp;&nbsp;��" class="red button" onclick="javascript:checkPo(this.form)" />
				</td>
			</tr>
		</table>
	</form>
	</body>
	<script type="text/javascript">
	/*��ʼ����-�������ڣ������������js*/
	j(function() {
	    var minDate1 = '<%=CommonFunctions.GetDBSysDate()%>';
	    var maxDate2 = '<%=CommonFunctions.GetDBSysDate()%>';
	        j("#sqjgr").datepicker(
				{
					changeMonth: true,
					changeYear: true,
					dateFormat: 'yymmdd',
					showOn: 'button', 
					maxDate: new Date(maxDate2.substring(0,4),parseFloat(maxDate2.substring(4,6))-1,maxDate2.substring(6,8)),
					buttonImage: '<%=request.getContextPath()%>/pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
					buttonImageOnly: true,
				    onSelect: function(dateText, inst) {
				    dateText = dateText.substring(0,4)+'-'+dateText.substring(4,6)+'-'+dateText.substring(6,8);
				    j('#dqjgr').datepicker('option', 'minDate',new Date(dateText.replace('-',',')));}
			});

			j("#dqjgr").datepicker(
				{
					changeMonth: true,
					changeYear: true,
					dateFormat: 'yymmdd',
					showOn: 'button', 
					buttonImage: '<%=request.getContextPath()%>/pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
					buttonImageOnly: true,
					minDate: new Date(minDate1.substring(0,4),parseFloat(minDate1.substring(4,6))-1,minDate1.substring(6,8)),
				    onSelect: function(dateText, inst) {
					dateText = dateText.substring(0,4)+'-'+dateText.substring(4,6)+'-'+dateText.substring(6,8);
				    j('#sqjgr').datepicker('option', 'maxDate', new Date(dateText.replace('-',',')));}
			});
		});
	function sameP(obj){
		var result=obj.value;
		if(result=="")
			result=0;
		document.getElementById("bal").value=parseFloat(result,10);
	}
	function checkPo(FormName){
		var url = "<%=request.getContextPath()%>/JRSCYWDRHG_checkId.action";
		var acId=document.getElementById("acId").value;
		   new Ajax.Request( 
		        url, 
		         {  
		          method: 'post',   
		          parameters: {
		        	acId:acId,
		            t:new Date().getTime()
		            },
		          onSuccess: function(val){
		        	  if(val.responseText=='ok'){
		        		  doSave(FormName);
		        	  }else{
		        		  art.dialog({
			              	    title:'��ʾ',
			          		    icon: 'error',
			          		    content: '�˱���ѱ�ռ�ã�',
			          		    ok: function () {
			          		    	document.getElementById("acId").select();
			          		    }
			           	 	});
		        	  }
			         }
		          });
	}
    function doSave(FormName) {   
    	 if(!(isNull(FormName.acId,"���"))) {
      		return false;			
          }
          if(!(isNull(FormName.brNo,"��������"))) {
      		return false;			
          }
          if(!(isNull(FormName.prdtNo,"��Ʒ����"))) {
      		return false;			
          }
          if(!(isNull(FormName.cusName,"���׶���"))) {
      		return false;			
          }
          if(!(isNull(FormName.custNo,"���׶��ֱ��"))) {
      		return false;			
          }
          if(!(isNull(FormName.ph,"Ʊ��"))) {
      		return false;			
          }
          if(!(isNull(FormName.qmje,"Ʊ����"))) {
      		return false;			
          }
          if(!(isNull(FormName.hglv,"�ع�����(��)"))) {
      		return false;			
          }
          if(!(isNull(FormName.cjje,"�ɽ����"))) {
      		return false;			
          }
          if(!(isNull(FormName.sqjgr,"���ڽ�����"))) {
      		return false;			
          }
          if(!(isNull(FormName.dqjgr,"���ڽ�����"))) {
      		return false;			
          }
<%--           if(!(isNull(FormName.jyts,"��Ϣ����"))) {--%>
<%--        		return false;			--%>
<%--            }--%>
          if(!(isNull(FormName.khjl,"����Ա"))) {
        		return false;			
            }
          if(!(isNull(FormName.bal,"���"))) {
      		return false;			
          }
    	var obj =document.getElementById("prdtNo");
        document.getElementById("prdtName").value=obj.options[obj.selectedIndex].text;
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
    function doSelEmp(){
    	art.dialog.open('<%=request.getContextPath()%>/pages/selEmpS.jsp?Rnd='+Math.random(), {
		    title: 'ѡ��',
		    width: 800,
		    height:400,
		    close: function (){
	    		var paths = art.dialog.data('bValue');
	    		var path=paths.split("@@");
	    		document.getElementById("khjl").value=path[0];
	    		document.getElementById("empName").value=path[1];
		     }
		});
    }
	function ok(){
		  window.parent.location.reload();
	}
	</script>
</html>

