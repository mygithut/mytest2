<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.util.CommonFunctions"%>
<%@page import="com.dhcc.ftp.entity.TelMst"%>
<%
	TelMst telmst = (TelMst) request.getSession().getAttribute("userBean");
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
		<form action="<%=request.getContextPath()%>/Bzjbl_saveTy.action" method="post" id="update" name="update" >	    
		<table  width="90%" align="center" border="0" class="table">
			                <tr>
								<td align="right">�˺ţ�</td>
								<td>
								<input type="text" name="acId" id="acId" value="" /></td>
								<td align="right" >������</td>
								<td >
									<div id='comboxWithTree1' style="width: 300px;"></div>
									<input name="brNo" id="brNo" type="hidden" value="<%=telmst.getBrMst().getBrNo()%>" />
									<input name="brName" id="brName" type="hidden"  value="<%=telmst.getBrMst().getBrName() + "[" + telmst.getBrMst().getBrNo() + "]"%>" />
								</td>
							</tr>
							<tr>
								<td align="right">��Ʒ���ƣ�</td>
								<td >
									<select  name="prdtNo" id="prdtNo">
										<option value="P2051@@20140100-1">����֤��֤��֤�����</option>
										<option value="P2052@@20140100-2">����֤��֤��֤����</option>
										<option value="P2053@@20140200-1">������֤�����</option>
										<option value="P2058@@20140401-2">���б�Ʊ��֤����</option>
										<option value="P2059@@20140402-1">���л�Ʊ��֤�����</option>
										<option value="P2060@@20140402-2">���л�Ʊ��֤����</option>
										<option value="P2061@@20140403-1">���Ѵ��֤�����</option>
										<option value="P2062@@20140403-2">���Ѵ��֤����</option>
										<option value="P2063@@20140404-1">�жһ�Ʊ���ڱ�֤�����</option>
										<option value="P2064@@20140404-2">�жһ�Ʊ���ڱ�֤����</option>
										<option value="P2065@@20140405-1">������֤�����</option>
										<option value="P2066@@20140405-2">������֤����</option>
										<option value="P2067@@20140406-1">����-�жһ�Ʊ��֤�����</option>
										<option value="P2068@@20140406-2">����-�жһ�Ʊ��֤����</option>
										<option value="P2054@@20140200-2">������֤����</option>
										<option value="P2055@@20140300-1">���гжһ�Ʊ��֤�����</option>
										<option value="P2056@@20140300-2">���гжһ�Ʊ��֤����</option>
										<option value="P2057@@20140401-1">���б�Ʊ��֤�����</option>
									</select>
									<input type="hidden" name="prdtName" id="prdtName" />
								</td>
								
									<td align="right">����Ա��ţ�</td>
								<td><input type="text" name="EmpNo" id="EmpNo" value=""/></td>
<%--								<td align="right">��Ŀ�ţ�</td>--%>
<%--								<td>--%>
<%--									<input type="text" name="kmh" id="kmh" />--%>
<%--								</td>--%>
<%--								<td align="right">��Ʒ���ƣ�</td>--%>
<%--								<td><input type="text" name="prdtName" id="prdtName" /></td>--%>
<%--								<td align="right">��Ʒ���룺</td>--%>
<%--								<td><input type="text" name="orgnlbusntyp" id="orgnlbusntyp" /></td>--%>
							</tr>
<%--							<tr>--%>
<%--								<td align="right" width="25%">���֣�</td>--%>
<%--								<td width="25%">--%>
<%--									<select style="width: 100" name="curNo" id="curNo">	</select>--%>
<%--								</td>--%>
							
<%--							</tr>--%>
							<tr>
								<td align="right">�ͻ����ƣ�</td>
								<td><input type="text" name="cusName" id="cusName" /></td>
								<td align="right">�ͻ���ű�ţ�</td>
								<td><input type="text" name="custNo" id="custNo" /></td>
							</tr>
						
								
							
							<tr>
							    
							    <td align="right">��ʼ�գ�</td>
								<td><input type="text" name="opnDate" id="opnDate" readonly="readonly"/></td>
								<td align="right">�����գ�</td>
								<td><input type="text" name="mtrDate" id="mtrDate" readonly="readonly" /></td>
							</tr>
							<tr>
								<td align="right">���(Ԫ)��</td>
								<td colspan="1"><input type="text"  onblur="sameP(this)" onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="amt" id="amt" /></td>
								<td align="right">���(Ԫ)��</td>
								<td colspan="1"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="bal" id="bal" /></td>
							</tr>
							<tr>
								<td align="right">����(%)��</td>
								<td colspan="3"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="rate" id="rate" /></td>
							</tr>
<%--							<tr>--%>
<%--							    --%>
<%--							    <td align="right">��Ϣ������</td>--%>
<%--								<td colspan="3"><input type="text" name="days" maxlength="5"  onkeyup="value=value.replace(/[^\d\.]/g,'')" id="days" /></td>--%>
<%--							  	--%>
<%--							</tr>--%>
<%--							<tr>--%>
<%--							    --%>
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
	        j("#opnDate").datepicker(
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
				    j('#mtrDate').datepicker('option', 'minDate',new Date(dateText.replace('-',',')));}
			});

			j("#mtrDate").datepicker(
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
				    j('#opnDate').datepicker('option', 'maxDate', new Date(dateText.replace('-',',')));}
			});
		});
	function sameP(obj){
		var result=obj.value;
		if(result=="")
			result=0;
		document.getElementById("bal").value=parseFloat(result,10);
	}
	
	function checkPo(FormName){
		var url = "<%=request.getContextPath()%>/Bzjbl_checkId.action";
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
<%--        if(!(isNull(FormName.kmh,"��Ŀ��"))) {--%>
<%--    		return false;			--%>
<%--        }--%>
        if(!(isNull(FormName.cusName,"�ͻ�����"))) {
    		return false;			
        }
        if(!(isNull(FormName.custNo,"�ͻ����"))) {
    		return false;			
        }
        if(!(isNull(FormName.amt,"���"))) {
    		return false;			
        }
        if(!(isNull(FormName.rate,"����"))) {
    		return false;			
        }
        if(!(isNull(FormName.opnDate,"��ʼ��"))) {
    		return false;			
        }
        if(!(isNull(FormName.mtrDate,"������"))) {
    		return false;			
        }
        if(!(isNull(FormName.EmpNo,"����Ա"))) {
    		return false;			
        }
        if(!(isNull(FormName.bal,"���"))) {
            return false;			
    }
<%--        if(!(isNull(FormName.days,"����"))) {--%>
<%--    		return false;			--%>
<%--        }--%>
    	var obj =document.getElementById("prdtNo");
    	//alert(obj.options[obj.selectedIndex].text);
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
		    id:'sel',
		    close: function (){
	    		var paths = art.dialog.data('bValue');
	    		if(paths!=null&&paths!=""){
		    		var path=paths.split("@@");
		    		document.getElementById("khjl").value=path[0];
		    		document.getElementById("empName").value=path[1];
		    	}
		     }
		});
    }
	function ok(){
		  window.parent.location.reload();
	}
	function seled(id,value){
		var selectFlags = document.getElementsByName(id);
		for (var i=0; i<selectFlags.length; i++) {
			if(selectFlags[i].value==value){
				selectFlags[i].checked = true;
			}
		}
	}
	</script>
	
</html>

