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
		
		<form action="<%=request.getContextPath()%>/JRSCYWDRXQMM_saveXqmm.action" method="post" id="update" name="update" >	    
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
								<td align="right">��Ʒ���ƣ�</td>
								<td>
									<select name="prdtNo" id="prdtNo" >
										<option value="P1064@@11010101">�����Խ����ʲ����ڹ�ծ</option>
										<option value="P1065@@11010102">�����Խ����ʲ����ڽ���ծ</option>
										<option value="P1067@@11010300">�����Խ����ʲ�����</option>
										<option value="P1204@@15030101">�ɹ����۽����ʲ�-��ծ</option>
										<option value="P1205@@15030102">�ɹ����۽����ʲ�-����ծȯ</option>
										<option value="P1206@@15030103">�ɹ����۽����ʲ�-����������ծ</option>
										<option value="P1207@@15030104">�ɹ����۽����ʲ�-���빫����ҵծ</option>
										<option value="P1208@@15030105">�ɹ����۽����ʲ�-��ҵ����ծ</option>
										<option value="P1209@@15030106">�ɹ����۽����ʲ�-�ط�����ծ</option>
										<option value="P1210@@15030107">�ɹ����۽����ʲ�-����</option>
										<option value="P2096@@21010101">�Թ��ʼ�ֵ��������䶯���뵱������Ľ��ڸ�ծ</option>
										<option value="P2097@@21010102">���������Խ��ڸ�ծ</option>
									</select>
									<input type="hidden" name="prdtName" id="prdtName" />
								</td>
									<td align="right">
									����Ա��
								</td>
								<td colspan="1">
									<input type="hidden" name="khjl" id="khjl" />
									<input type="text" name="empName" id="empName" readonly="readonly" />
									<input type="button" value="ѡ��" class="red button" onclick="javascript:doSelEmp()" />
								</td>
							</tr>
							<tr>
								<td align="right">���׶��֣�</td>
								<td><input type="text" name="cusName" id="cusName" /></td>
								<td align="right">���׶��ֱ�ţ�</td>
								<td><input type="text" name="custNo" id="custNo" /></td>
							</tr>
							<tr>
								<td align="right">�������ڣ�</td>
								<td><input type="text" name="buyDate" id="buyDate" readonly="readonly" /></td>
								<td align="right">�ɽ����(Ԫ)��</td>
								<td><input type="text" onkeyup="value=value.replace(/[^\d\.]/g,'')" onblur="sameP(this)"   name="fullpriceAmt" id="fullpriceAmt" /></td>
							</tr>
							<tr>
<td align="right">���(Ԫ)��</td>
<td colspan="1"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="bal" id="bal" /></td>
								<td align="right">Ʊ���</td>
								<td><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="bondAmt" id="bondAmt" /></td>
							</tr>
							<tr>
							    <td align="right">Ʊ�����ʣ�</td>
								<td colspan="3"><input type="text"  onkeyup="value=value.replace(/[^\d\.]/g,'')"  name="rate" id="rate" /></td>
							</tr>
			<tr>
				<td align="center" colspan="4">
					<input type="button" value="��&nbsp;&nbsp;��" class="red button" onclick="javascript:checkPo(this.form)" />
				</td>
			</tr>
		</table>
	</form>
	</body>
	<script type="text/javascript">
function sameP(obj){
		var result=obj.value;
		if(result=="")
			result=0;
		document.getElementById("bal").value=parseFloat(result,10);
	}
	/*��ʼ����-�������ڣ������������js*/
	j(function() {
	        j("#buyDate").datepicker(
				{
					changeMonth: true,
					changeYear: true,
					dateFormat: 'yymmdd',
					showOn: 'button', 
					buttonImage: '<%=request.getContextPath()%>/pages/js/JQueryDatePicker/themes/base/images/calendar.gif',
					buttonImageOnly: true
			});
		});
	function checkPo(FormName){
		var url = "<%=request.getContextPath()%>/JRSCYWDRXQMM_checkId.action";
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
<%--            if(!(isNull(FormName.kmh,"��Ŀ��"))) {--%>
<%--        		return false;			--%>
<%--            }--%>
            if(!(isNull(FormName.prdtNo,"��Ʒ����"))) {
        		return false;			
            }
            if(!(isNull(FormName.custNo,"���׶��ֱ��"))) {
        		return false;			
            }
            if(!(isNull(FormName.cusName,"���׶���"))) {
        		return false;			
            }
            if(!(isNull(FormName.buyDate,"��������"))) {
        		return false;			
            }
            if(!(isNull(FormName.fullpriceAmt,"�ɽ����"))) {
        		return false;			
            }
            if(!(isNull(FormName.rate,"Ʊ������"))) {
        		return false;			
            }
            if(!(isNull(FormName.bondAmt,"Ʊ����"))) {
        		return false;			
            }
           
            if(!(isNull(FormName.khjl,"����Ա"))) {
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

