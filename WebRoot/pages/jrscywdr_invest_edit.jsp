<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.*,com.dhcc.ftp.util.*" errorPage="" %>

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
	<%
		JrInvest jrinvest = (JrInvest) request.getAttribute("jrinvest");
		String acId = jrinvest == null ? "" : jrinvest.getAcId();
		System.out.println("jsp��acId===========" + acId);
	%>
		
		<form action="<%=request.getContextPath()%>/JRSCYWDRINVEST_update.action"  method="post" id="update" name="update" >	    
		<table  width="90%" align="center" border="0" class="table">
			                <tr>
								<td align="right">��ţ�</td>
								<td><%=jrinvest.getAcId() == null ? "" : (jrinvest.getAcId().indexOf("_")==-1?jrinvest.getAcId():jrinvest.getAcId().substring(0,jrinvest.getAcId().indexOf("_")))%>
								<input type="hidden" name="acId" id="acId" value="<%=jrinvest.getAcId() == null ? "" : jrinvest.getAcId()%>"/>
								</td>
								<td align="right" >�����ţ�</td>
								<td >
										<div id='comboxWithTree1' style="width: 300px;"></div>
									<input name="brNo" id="brNo" type="hidden" value="<%=jrinvest.getBrNo()==null?"":jrinvest.getBrNo() %>" />
									<input name="brName" id="brName" type="hidden"  value="<%=jrinvest.getBrName() + "[" + jrinvest.getBrNo() + "]"%>" />
								
								</td>
							</tr>
							<tr>
								<td align="right">��Ʒ���ƣ�</td>
								<td>
									<select name="prdtNo" id="prdtNo"> 
										<option value="P1192@@15010101">����������Ͷ��-��ծ</option>
										<option value="P1193@@15010102">����������Ͷ��-����ծȯ</option>
										<option value="P1194@@15010103">����������Ͷ��-����������ծ</option>
										<option value="P1195@@15010104">����������Ͷ��-���빫����ҵծȯ</option>
										<option value="P1196@@15010105">����������Ͷ��-��ҵ����ծȯ</option>
										<option value="P1197@@15010106">����������Ͷ��-�ط�����ծȯ</option>
										<option value="P1198@@15010107">����������Ͷ��-����Ͷ��</option>
										<option value="P1226@@15310101">Ӧ�տ�����Ͷ��-ƾ֤ʽ��ծ</option>
										<option value="P1227@@15310102">Ӧ�տ�����Ͷ��-������Ʊ</option>
										<option value="P1228@@15310103">Ӧ�տ�����Ͷ��-����</option>
									</select>
									<input type="hidden" name="prdtName" id="prdtName" />
								</td>
									<td align="right">����Ա��</td>
								<td colspan="3">
									<input type="hidden" name="khjl" id="khjl" value="<%=jrinvest.getKhjl()==null?"":jrinvest.getKhjl()%>"/>
									<input type="text" name="empName" id="empName" readonly="readonly"  value="<%=jrinvest.getEmpName()==null?"":jrinvest.getEmpName()%>"/>
									<input type="button" value="ѡ��" class="red button" onclick="javascript:doSelEmp()" />
								</td>
							</tr>
							<tr>
								<td align="right">���׶��֣�</td>
								<td><input type="text" name="cusName" id="cusName"  value="<%=jrinvest.getCusName() == null ? "" : jrinvest.getCusName()%>"/></td>
								<td align="right">���׶��ֱ�ţ�</td>
								<td><input type="text" name="custNo" id="custNo" value="<%=jrinvest.getCustNo() == null ? "" : jrinvest.getCustNo()%>"/></td>
							</tr>
							<tr>
							    
							</tr>
							<tr>
								<td align="right">��Ϣ�գ�</td>
								<td><input type="text" name="startDate" id="startDate" readonly="readonly" value="<%=jrinvest.getStartDate() == null ? "" : jrinvest.getStartDate()%>" /></td>
							    <td align="right">�����գ�</td>
								<td colspan="3"><input type="text" name="endDate" id="endDate" readonly="readonly" value="<%=jrinvest.getEndDate() == null ? "" : jrinvest.getEndDate()%>"/></td>
							</tr>
							<tr>
								<td align="right">Ʊ����(Ԫ)��</td>
								<td colspan="1"><input type="text" name="holdPrincipal"   onkeyup="value=value.replace(/[^\d\.]/g,'')" id="holdPrincipal" value="<%=jrinvest.getHoldPrincipal() == null ? "" :CommonFunctions.doubleFormat( jrinvest.getHoldPrincipal(),2)%>"/></td>
							    <td align="right">Ʊ������(%)��</td>
								<td><input type="text" name="billYield" id="billYield"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  value="<%=jrinvest.getBillYield() == null ? "" : CommonFunctions.doubleFormat(jrinvest.getBillYield()*100,2)%>" /></td>
							</tr>
							<tr>
								<td align="right">�ɽ����(Ԫ)��</td>
								<td><input type="text" name="dealAmt" id="dealAmt" onblur="sameP(this)"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  value="<%=jrinvest.getDealAmt() == null ? "" : CommonFunctions.doubleFormat(jrinvest.getDealAmt(),2)%>" /></td>
								<td align="right">���(Ԫ)��</td>
								<td colspan="1"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  value="<%=jrinvest.getBal() == null ? "" : CommonFunctions.doubleFormat(jrinvest.getBal(),2)%>" name="bal" id="bal" /></td>
							</tr>
							<tr>
							    <td align="right">��Ϣ������</td>
								<td colspan="3">
									  <%=jrinvest.getJxts() == null ? "" : jrinvest.getJxts()%></td>
							</tr>
			<tr>
				<td align="center"  colspan="4">
					<input type="button" value="��&nbsp;&nbsp;��" class="red button" onclick="javascript:doSave(this.form)" />
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
	        j("#startDate").datepicker(
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
				    j('#endDate').datepicker('option', 'minDate',new Date(dateText.replace('-',',')));}
			});

			j("#endDate").datepicker(
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
				    j('#startDate').datepicker('option', 'maxDate', new Date(dateText.replace('-',',')));}
			});
		});
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
<%--         if(!(isNull(FormName.kmh,"��Ŀ��"))) {--%>
<%--     		return false;			--%>
<%--         }--%>
         if(!(isNull(FormName.cusName,"���׶���"))) {
     		return false;			
         }
         if(!(isNull(FormName.custNo,"���׶��ֱ��"))) {
     		return false;			
         }
         if(!(isNull(FormName.holdPrincipal,"Ʊ����"))) {
     		return false;			
         }
         if(!(isNull(FormName.billYield,"Ʊ������"))) {
     		return false;			
         }
         if(!(isNull(FormName.startDate,"��������"))) {
     		return false;			
         }
         if(!(isNull(FormName.endDate,"������"))) {
     		return false;			
         }
         if(!(isNull(FormName.dealAmt,"�ɱ����"))) {
     		return false;			
         }
<%--         if(!(isNull(FormName.jxts,"��Ϣ����"))) {--%>
<%--     		return false;			--%>
<%--         }--%>
alert(FormName.khjl.value);
alert(FormName.empName.value);
         if(!(isNull(FormName.empName,"����Ա"))) {
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
    function ok(){
		  window.parent.location.reload();
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
	function seled(id,value){
		var selectFlags = document.getElementById(id);
		for (var i=0; i<selectFlags.options.length; i++) {
			//alert(selectFlags.options[i].value);
			if(selectFlags.options[i].value==value){
				//alert(2);
				selectFlags.selectedIndex=i;
				return ;
			}
		}
	}
	seled('prdtNo','<%=jrinvest.getPrdtNo()==null?"":jrinvest.getPrdtNo()%>@@<%=jrinvest.getKmh()==null?"":jrinvest.getKmh()%>');
	</script>
</html>

