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
	JrTy jrty=(JrTy)request.getAttribute("jrty");
	String acId=jrty==null?"":jrty.getAcId();
	System.out.println("jsp��acId==========="+acId);
	
	 %>
		<form action="<%=request.getContextPath()%>/JRSCYWDRTY_update.action"  method="post" id="update" name="update" >	
		<table  width="90%" align="center" class="table" >
			                <tr>
								<td align="right">��ţ�</td>
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
								<td>
									<select  name="prdtNo" id="prdtNo">
										<option value="P1006@@10110102">��Ź���Լ�ڴ��</option>
										<option value="P1008@@10110104">���ũ��Լ�ڴ��</option>
										<option value="P1010@@10110106">�������Լ�ڴ��</option>
										<option value="P1012@@10110108">��Ž���Լ�ڴ��</option>
										<option value="P1014@@10110110">�������������Լ�ڴ��</option>
										<option value="P1016@@10110112">��Źɷ�������Լ�ڴ��</option>
										<option value="P1017@@10110113-1">��ž�������ͬҵһ����</option>
										<option value="P1018@@10110113-2">��ž�������ͬҵԼ�ڴ��</option>
										<option value="P1019@@10110200-1">��ž��ڷ�����ͬҵһ����</option>
										<option value="P1020@@10110200-2">��ž��ڷ�����ͬҵԼ�ڴ��</option>
										<option value="P1021@@10110300-1">��ž���ͬҵһ����</option>
										<option value="P1022@@10110300-2">��ž���ͬҵԼ�ڴ��</option>
										<option value="P1025@@10120103">��ʡ����Լ�ڴ��</option>
										<option value="P1027@@10120202">��ŵ��м�Լ�ڴ��</option>
										<option value="P1029@@10120302">����ؼ�Լ�ڴ��</option>
										<option value="P2072@@20170100-1">��������ͬҵ��Ż���</option>
										<option value="P2073@@20170100-2">��������ͬҵ��Ŷ���</option>
										<option value="P2074@@20170200-1">���ڷ�����ͬҵ��Ż���</option>
										<option value="P2075@@20170200-2">���ڷ�����ͬҵ��Ŷ���</option>
										<option value="P2076@@20170300-1">����ͬҵ��Ż���</option>
										<option value="P2077@@20170300-2">����ͬҵ��Ŷ���</option>
										<option value="P2079@@20180102">ϵͳ�ڵ��м��ϴ�Լ�ڴ��</option>
										<option value="P2081@@20180202">ϵͳ���ؼ��ϴ�Լ�ڴ��</option>
										<option value="P2083@@20180302">ϵͳ���ϴ�ʡ�����Լ�ڴ��</option>
									</select>
									<input type="hidden" name="prdtName" id="prdtName" />
								</td>
								  <td align="right">����Ա��</td>
								<td><input type="hidden" name="khjl" id="khjl" value="<%=jrty.getKhjl()==null?"":jrty.getKhjl()%>"/>
									<input type="text" name="empName" id="empName" readonly="readonly"  value="<%=jrty.getEmpName()==null?"":jrty.getEmpName()%>"/>
									<input type="button" value="ѡ��" class="red button" onclick="javascript:doSelEmp()" />
								</td>
							</tr>
							<tr>
								<td align="right">���׶��֣�</td>
								<td><input type="text" name="cusName" id="cusName"  value="<%=jrty.getCusName()==null?"":jrty.getCusName()%>"/></td>
								<td align="right">���׶��ֱ�ţ�</td>
								<td><input type="text" name="custNo" id="custNo" value="<%=jrty.getCustNo()==null?"":jrty.getCustNo()%>"/></td>
							</tr>
							<tr>
							<tr>
							    
							    <td align="right">��Ϣ�գ�</td>
								<td><input type="text" name="startDate" readonly="readonly" id="startDate" value="<%=jrty.getStartDate()==null?"":jrty.getStartDate()%>" /></td>
								<td align="right">��Ϣ�գ�</td>
								<td><input type="text" name="endDate" readonly id="endDate" value="<%=jrty.getEndDate()==null?"":jrty.getEndDate()%>" /></td>
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
         if(!(isNull(FormName.khjl,"����Ա"))) {
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

