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
		<form action="<%=request.getContextPath()%>/JRSCYWDRCJ_saveCj.action" method="post" id="update" name="update" >	    
		<table  width="90%" align="center" border="0" class="table">
			                <tr>
								<td align="right">��ţ�</td>
							<td><%=acId %>
								<input type="hidden" name="acId" id="acId" value="<%=acId %>" /></td>
								<td align="right">������</td>
							<td>
									<div id='comboxWithTree1' style="width: 300px;"></div>
									<input name="brNo" id="brNo" type="hidden" value="<%=telmst.getBrMst().getBrNo()%>" />
									<input name="brName" id="brName" type="hidden"  value="<%=telmst.getBrMst().getBrName() + "[" + telmst.getBrMst().getBrNo() + "]"%>" />
								</td>
							</tr>
							<tr>
									<td align="right">��Ʒ���ƣ�</td>
								<td>
									<select name="prdtNo" id="prdtNo" >
										<option value="P1031@@10130100">��ž�������ͬҵ</option>
										<option value="P1032@@10130200">��ž��ڷ�����ͬҵ</option>
										<option value="P1033@@10130300">��ž���ͬҵ</option>
										<option value="P1034@@10140100">���ʡ��</option>
										<option value="P1035@@10140200">���ʡ��</option>
										<option value="P2084@@20190101">������ҵ���в���</option>
										<option value="P2085@@20190102">���������в���</option>
										<option value="P2086@@20190103">�ɷ������в���</option>
										<option value="P2087@@20190104">�����Թ�˾����</option>
										<option value="P2088@@20190105">����ͬҵ����-����</option>
										<option value="P2089@@20190200">������ͬҵ����</option>
										<option value="P2090@@20190300">����ͬҵ����</option>
										<option value="P2091@@20200000">ϵͳ�ڲ���</option>
									</select>
									<input type="hidden" name="prdtName" id="prdtName" />
								</td>
								<td align="right">����Ա��</td>
								<td>
									<input type="hidden" name="khjl" id="khjl" />
									<input type="text" name="empName" id="empName" readonly="readonly" />
									<input type="button" value="ѡ��" class="red button" onclick="javascript:doSelEmp()" />
								</td>
<%--									<td align="right">��Ŀ�ţ�</td>--%>
<%--								<td>--%>
<%--									<input type="text" name="kmh" id="kmh" />--%>
<%--								</td>--%>
								
							</tr>
							<tr>
								<td align="right">���׶��֣�</td>
								<td><input type="text" name="cusName" id="cusName" /></td>
								<td align="right">���׶��ֱ�ţ�</td>
								<td><input type="text" name="custNo" id="custNo" /></td>
							</tr>
							<tr>
							    
							    <td align="right">���ڽ����գ�</td>
								<td><input type="text" name="qxDate" id="qxDate" readonly="readonly" /></td>
								<td align="right">���ڽ����գ�</td>
								<td><input type="text" name="dqDate" id="dqDate" readonly="readonly" /></td>
							</tr>
							<tr>
							    
								<td align="right">�����(Ԫ)��</td>
								<td><input type="text" name="chaijieAmt"  onblur="sameP(this)" onkeyup="value=value.replace(/[^\d\.]/g,'')"  id="chaijieAmt" /></td>
								<td align="right">���(Ԫ)��</td>
								<td colspan="1"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="bal" id="bal" /></td>
								
							</tr>
							<tr>
								<td align="right">�������(%)��</td>
								<td colspan="3"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="rate" id="rate" /></td>
								
							</tr>
<%--							<tr>--%>
<%----%>
<%--								<td align="right">--%>
<%--									��Ϣ������--%>
<%--								</td>--%>
<%--								<td colspan="3">--%>
<%--									<input type="text" name="jxts" id="jxts"	maxlength="5"	 onkeyup="value=value.replace(/[^\d\.]/g,'')"  />--%>
<%--								</td>--%>
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
	        j("#qxDate").datepicker(
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
				    j('#dqDate').datepicker('option', 'minDate',new Date(dateText.replace('-',',')));}
			});

			j("#dqDate").datepicker(
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
				    j('#qxDate').datepicker('option', 'maxDate', new Date(dateText.replace('-',',')));}
			});
		});
	function checkPo(FormName){
		var url = "<%=request.getContextPath()%>/JRSCYWDRCJ_checkId.action";
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
	function sameP(obj){
		var result=obj.value;
		if(result=="")
			result=0;
		document.getElementById("bal").value=parseFloat(result,10);
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
            if(!(isNull(FormName.custNo,"���׶��ֱ��"))) {
        		return false;			
            }
            if(!(isNull(FormName.cusName,"���׶���"))) {
        		return false;			
            }
            if(!(isNull(FormName.chaijieAmt,"���"))) {
        		return false;			
            }
            if(!(isNull(FormName.rate,"����"))) {
        		return false;			
            }
            if(!(isNull(FormName.qxDate,"���ڽ�����"))) {
        		return false;			
            }
            if(!(isNull(FormName.dqDate,"���ڽ�����"))) {
        		return false;			
            }
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
	</script>
</html>

