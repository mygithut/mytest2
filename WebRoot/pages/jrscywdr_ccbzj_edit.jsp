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
		JrCcbzj jrccbzj = (JrCcbzj) request.getAttribute("jrccbzj");
		String acId = jrccbzj == null ? "" : jrccbzj.getAcId();
		System.out.println("jsp��acId===========" + acId);
	%>
		<form action="<%=request.getContextPath()%>/JRSCYWDRCCBZJ_update.action"  method="post" id="update" name="update" >	    
		<table  width="80%" align="center" border="0" class="table">
			                <tr>
								<td align="right" width="25%">��ţ�</td>
								<td width="25%"><input type="hidden" name="acId" id="acId" value="<%=jrccbzj.getAcId() == null ? "" : jrccbzj.getAcId()%>" />
								<%=jrccbzj.getAcId() == null ? "" : (jrccbzj.getAcId().indexOf("_")==-1?jrccbzj.getAcId():jrccbzj.getAcId().substring(0,jrccbzj.getAcId().indexOf("_")))%></td>
								<td align="right" width="25%">���� ��</td>
							<td width="25%">
									<div id='comboxWithTree1' style="width: 300px;"></div>
									<input name="brNo" id="brNo" type="hidden" value="<%=jrccbzj.getBrNo() == null ? "" : jrccbzj.getBrNo()%>" />
									<input name="brName" id="brName" type="hidden"  value="<%=jrccbzj.getBrName() + "[" + jrccbzj.getBrNo() + "]"%>" />
								</td>
							</tr>
							
							<tr>
								<td align="right">���׶������ƣ�</td>
								<td><input type="text" name="cusName" id="cusName" value="<%=jrccbzj.getCusName() == null ? "" : jrccbzj.getCusName()%>"/></td>
								<td align="right">���׶��ֱ�ţ�</td>
								<td><input type="text" name="custNo" id="custNo"  value="<%=jrccbzj.getCustNo() == null ? "" : jrccbzj.getCustNo()%>"/></td>
							</tr>
							<tr>
								<td align="right">��Ʒ���ƣ�</td>
								<td>
								<select name="prdtNo" id="prdtNo" >
									<option value="P1036@@10310101-1">���ʡ���籣֤�����</option>
									<option value="P1037@@10310101-2">���ʡ���籣֤����</option>
									<option value="P1038@@10310102-1">��Ź��б�֤�����</option>
									<option value="P1039@@10310102-2">��Ź��б�֤����</option>
									<option value="P1040@@10310103-1">���ũ�б�֤�����</option>
									<option value="P1041@@10310103-2">���ũ�б�֤����</option>
									<option value="P1042@@10310104-1">������б�֤�����</option>
									<option value="P1043@@10310104-2">������б�֤����</option>
									<option value="P1044@@10310105-1">��Ž��б�֤�����</option>
									<option value="P1045@@10310105-2">��Ž��б�֤����</option>
									<option value="P1046@@10310106-1">����������б�֤�����</option>
									<option value="P1047@@10310106-2">����������б�֤����</option>
									<option value="P1048@@10310200-1">�����ұ�֤�����(��Ԫ)</option>
									<option value="P1049@@10310200-2">�����ұ�֤����(��Ԫ)</option>
									<option value="P1050@@10310200-1">�����ұ�֤�����(��Ԫ)</option>
									<option value="P1051@@10310200-2">�����ұ�֤����(��Ԫ)</option>
									<option value="P1052@@10310200-1">�����ұ�֤�����(ŷԪ)</option>
									<option value="P1053@@10310200-2">�����ұ�֤����(ŷԪ)</option>
									<option value="P1054@@10310200-1">�����ұ�֤�����(Ӣ��)</option>
									<option value="P1055@@10310200-2">�����ұ�֤����(Ӣ��)</option>
									<option value="P1056@@10310200-1">�����ұ�֤�����(����)</option>
									<option value="P1057@@10310200-2">�����ұ�֤����(����)</option>
									<option value="P1058@@10310200-1">�����ұ�֤�����(��Ԫ)</option>
									<option value="P1059@@10310200-2">�����ұ�֤����(��Ԫ)</option>
									<option value="P1060@@10310200-1">�����ұ�֤�����(��Ԫ)</option>
									<option value="P1061@@10310200-2">�����ұ�֤����(��Ԫ)</option>
									<option value="P1062@@10310200-1">�����ұ�֤�����(��Ԫ)</option>
									<option value="P1063@@10310200-2">�����ұ�֤����(��Ԫ)</option>
								</select>
								<input type="hidden" name="prdtName" id="prdtName" />
								</td>
								   <td align="right">����Ա��</td>
								<td>
								<input type="hidden" name="operator" id="operator" value="<%=jrccbzj.getOperator() == null ? "" : jrccbzj.getOperator()%>"/>
								<input type="text" name="empName" id="empName" readonly="readonly"  value="<%=jrccbzj.getEmpName()==null?"":jrccbzj.getEmpName()%>"/>
									<input type="button" value="ѡ��" class="red button" onclick="javascript:doSelEmp()" />
								</td>
							</tr>
							<tr>
								<td align="right">��ʼ�գ�</td>
								<td ><input type="text" name="startDate" id="startDate" readonly="readonly" value="<%=jrccbzj.getStartDate() == null ? "" : jrccbzj	.getStartDate()%>"/></td>
							    
							    <td align="right">�����գ�</td>
								<td><input type="text" name="endDate" id="endDate"  readonly value="<%=jrccbzj.getEndDate() == null ? "" : jrccbzj.getEndDate()%>" /></td>
							</tr>
							<tr>
								<td align="right">���(Ԫ)��</td>
								<td><input type="text" name="amt" id="amt" onblur="sameP(this)"  onkeyup="value=value.replace(/[^\d\.]/g,'')" value="<%=jrccbzj.getAmt() == null ? "" : CommonFunctions.doubleFormat(jrccbzj.getAmt(),2)%>"/></td>
								<td align="right">���(Ԫ)��</td>
								<td colspan="1"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  value="<%=jrccbzj.getBal() == null ? 0 : CommonFunctions.doubleFormat(jrccbzj.getBal(),2)%>" name="bal" id="bal" /></td>
							</tr>
							<tr>
								<td align="right">����(%)��</td>
								<td><input type="text" name="rate" id="rate" onkeyup="value=value.replace(/[^\d\.]/g,'')"  value="<%=jrccbzj.getRate() == null ? "" : CommonFunctions.doubleFormat(jrccbzj.getRate()*100,2)%>" /></td>
								<td align="right">��Ϣ������</td>
								<td colspan="3">
								<%=jrccbzj.getDays() == null ? "" : jrccbzj.getDays()%>
								</td>
							 
							</tr>
			<tr>
				<td align="center" colspan="4">
					<input type="button" value="��&nbsp;&nbsp;��" class="red button" onclick="javascript:doSave(this.form)" />
				</td>
			</tr>
		</table>
	</form>
	</body>
	<script type="text/javascript">
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
		var bal=document.getElementById("bal");
<%--		if(bal.value!=""){--%>
<%--			return ;--%>
<%--		}--%>
		if(result=="")
			result=0;
		bal.value=parseFloat(result,10);
	}
    function doSave(FormName){
    	 if(!(isNull(FormName.acId,"���"))) {
      		return false;			
          }
          if(!(isNull(FormName.brNo,"��������"))) {
      		return false;			
          }
          if(!(isNull(FormName.prdtNo,"��Ʒ����"))) {
      		return false;			
          }
<%--          if(!(isNull(FormName.kmh,"��Ŀ��"))) {--%>
<%--      		return false;			--%>
<%--          }--%>
          if(!(isNull(FormName.cusName,"���׶���"))) {
      		return false;			
          }
          if(!(isNull(FormName.custNo,"���׶��ֱ��"))) {
      		return false;			
          }
          if(!(isNull(FormName.amt,"���"))) {
      		return false;			
          }
          if(!(isNull(FormName.rate,"����"))) {
      		return false;			
          }
          if(!(isNull(FormName.startDate,"��Ϣ��"))) {
      		return false;			
          }
          if(!(isNull(FormName.endDate,"��Ϣ��"))) {
      		return false;			
          }
<%--          if(!(isNull(FormName.days,"��Ϣ����"))) {--%>
<%--      		return false;			--%>
<%--          }--%>
          if(!(isNull(FormName.operator,"����Ա"))) {
      		return false;			
          }
          if(!(isNull(FormName.bal,"���"))) {
              return false;			
      }
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
		    		document.getElementById("operator").value=path[0];
		    		document.getElementById("empName").value=path[1];
		    	}
		     }
		});
    }
    function ok(){
		  window.parent.location.reload();
	}
    function seled(id,value){
		var selectFlags = document.getElementById(id);
		for (var i=0; i<selectFlags.options.length; i++) {
			if(selectFlags.options[i].value==value){
				selectFlags.selectedIndex=i;
				return ;
			}
		}
	}
	seled('prdtNo','<%=jrccbzj.getPrdtNo()==null?"":jrccbzj.getPrdtNo()%>@@<%=jrccbzj.getKmh()==null?"":jrccbzj.getKmh()%>');	
	</script>
</html>

