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
	JrBzj jrty=(JrBzj)request.getAttribute("JrBzj");
	String acId=jrty==null?"":jrty.getAcId();
	System.out.println("jsp��acId==========="+acId);
	
	 %>
		<form action="<%=request.getContextPath()%>/Blzjbl_update.action"  method="post" id="update" name="update" >	
		<table  width="90%" align="center" class="table" >
			                <tr>
								<td align="right">�˺ţ�</td>
								<td><%=jrty.getAcId()==null?"":(jrty.getAcId().indexOf("_")==-1?jrty.getAcId():jrty.getAcId().substring(0,jrty.getAcId().indexOf("_")))%>
								<input type="hidden" name="acId" id="acId" value="<%=jrty.getAcId()==null?"":jrty.getAcId()%>"/>
								</td>
								<td align="right" >������</td>
								<td >
									<div id='comboxWithTree1' style="width: 300px;"></div>
									<input name="brNo" id="brNo" type="hidden" value="<%=jrty.getBrNo()==null?"":jrty.getBrNo() %>" />
									<input name="brName" id="brName" type="hidden"  value="<%=jrty.getBrName()==null?"":jrty.getBrName() %>[<%=jrty.getBrNo()==null?"":jrty.getBrNo() %>]" />
								
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
								<td><input type="text" name="EmpNo" id="EmpNo" value="<%=jrty.getEmpNo()==null?"":jrty.getEmpNo()%>"/></td>
							</tr>
							<tr>
								<td align="right">�ͻ����ƣ�</td>
								<td><input type="text" name="cusName" id="cusName"  value="<%=jrty.getCusName()==null?"":jrty.getCusName()%>"/></td>
								<td align="right">�ͻ���ţ�</td>
								<td><input type="text" name="custNo" id="custNo" value="<%=jrty.getCustNo()==null?"":jrty.getCustNo()%>"/></td>
							</tr>
							<tr>
							<tr>
							    
							    <td align="right">�������ڣ�</td>
								<td><input type="text" name="startDate" readonly="readonly" id="startDate" value="<%=jrty.getOpnDate()==null?"":jrty.getOpnDate()%>" /></td>
								<td align="right">�������ڣ�</td>
								<td><input type="text" name="endDate" readonly id="endDate" value="<%=jrty.getMtrDate()==null?"":jrty.getMtrDate()%>" /></td>
							</tr>
							<tr>
							    
								<td align="right">���(Ԫ)��</td>
								<td colspan="1"><input type="text"  onblur="sameP(this)" onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="amt" id="amt" value="<%=jrty.getAmt()==null?"": CommonFunctions.doubleFormat(jrty.getAmt(),2)%>"/></td>
								<td align="right">���(Ԫ)��</td>
								<td colspan="1"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  value="<%=jrty.getBal() == null ? "" : CommonFunctions.doubleFormat(jrty.getBal(),2)%>" name="bal" id="bal" /></td>
								  
							</tr>
							<tr>
								<td align="right">����(%)��</td>
								<td colspan="1"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')" name="rate" id="rate" value="<%=jrty.getRate()==null?"":CommonFunctions.doubleFormat(jrty.getRate()*100,2)%>"/></td>
							  
							    <td align="right">������</td>
								<td colspan="3">
									<%=jrty.getDays()==null?"":jrty.getDays()%>
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

    function doSelEmp(){
        
    	art.dialog.open('<%=request.getContextPath()%>/pages/selEmpS.jsp?Rnd='+Math.random(), {
		    title: 'ѡ��',
		    width: 800,
		    height:400,
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

    function doSave(FormName) {   
    	 if(!(isNull(FormName.acId,"�˺�"))) {
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
		if(!(isNull(FormName.EmpNo,"����Ա"))) {
			return false;			
		}

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
         if(!(isNull(FormName.startDate,"��ʼ��"))) {
     		return false;			
         }
         if(!(isNull(FormName.endDate,"������"))) {
     		return false;			
         }
      
<%--         if(!(isNull(FormName.days,"����"))) {--%>
<%--     		return false;			--%>
<%--         }--%>
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
	function sameP(obj){
		var result=obj.value;
		if(result=="")
			result=0;
		document.getElementById("bal").value=parseFloat(result,10);
	}
	seled('prdtNo','<%=jrty.getPrdtNo()==null?"":jrty.getPrdtNo()%>@@<%=jrty.getKmh()==null?"":jrty.getKmh()%>');
	</script>
</html>

