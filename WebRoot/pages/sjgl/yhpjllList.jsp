<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.entity.Ftp1BankBillsRate,com.dhcc.ftp.util.FtpUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List,com.dhcc.ftp.util.CommonFunctions"%>
<%@page import="com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>����Ʊ������ά��</title>
		<jsp:include page="../commonJs.jsp" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
	</head>
	<body>	
		<form id="form1" name="form1" method="get" action="" style="display: none;">
			<%
				PageUtil ftpBankBillsRateUtil = (PageUtil) request.getAttribute("ftpBankBillsRateUtil");
			    List<Ftp1BankBillsRate> ftpBankBillsRateList = ftpBankBillsRateUtil.getList();
			    if (ftpBankBillsRateList == null) {
				    out.print("�޸����Ͷ�Ӧ�����ݣ�");
			    } else {
			%>
			<div align="center">
		    <table id="tableList">
		        <thead>
				<tr>
					<th align="center" width="90">
						<input type="checkbox" name="all" value="checkbox"
							onClick="checkAll()" />
						ȫѡ
					</th>
					<th width="150">
						����Ʊ������(%)
					</th>
					<th width="150">
						����
					</td>
					<th width="150">
						����
					</th>
                    <th width="150">
						����
					</th>
				</tr>
				</thead>
				<%
					for (Ftp1BankBillsRate ftpBankRate : ftpBankBillsRateList) {
				%>
				<tbody>
				<tr>
					<td align="center">
						<input type="checkbox" name="checkbox"
							value="<%=ftpBankRate.getBillsId() %>" />
					</td>
					<td align="center">
						<%=CommonFunctions.doublecut(ftpBankRate.getBillsRate()*100,4)%>
					</td>
					<td align="center">
						<%=CastUtil.trimNull(ftpBankRate.getTermType())%>
					</td>
					<td align="center">						
						<%=CastUtil.trimNull(ftpBankRate.getBillsDate())%>						
					</td>
					<td align="center">						
					<a href="javascript:doEdit(<%=ftpBankRate.getBillsId()%>)">�༭</a>
					</td>
				</tr>
				</tbody>
				<%
					}
					}
				%>
			</table>
				<tbody>
				
			</table>
			</div>
			<table border="0" width="800" class="tb1"
				style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
				align="center">
				<tr>
					<td align="right"><%=ftpBankBillsRateUtil.getPageLine()%></td>
				</tr>
			</table>
		</form>

		<script language="javascript">
		document.getElementById("form1").style.display="block";
	    j(function(){
		    j('#tableList').flexigrid({
		    		height: 280,width:800,
		    		theFirstIsShown: false,//��һ�����Ƿ���ʾ�����б����Ƿ����(ĳЩ�б��һ����ȫѡ��ť)
		    		title: '����Ʊ�������б�',
		    		buttons : [
		    			   		{name: '����', bclass: 'add', onpress : doAdd},
		    			   		{name: 'ɾ��', bclass: 'delete', onpress : doDelete},
		    			   		{name: '����', bclass: 'import', onpress : doImport}
		    			   		]});
	    });
	    function doAdd(){
			art.dialog.open('<%=request.getContextPath()%>/pages/sjgl/yhpjllAdd.jsp?Rnd='+Math.random(), {
			    title: '�������Ʊ������',
			    width: 400,
			    height:350
			});
		}
		function doEdit(billsId){
			art.dialog.open('<%=request.getContextPath()%>/YHPJLL_Query.action?billsId='+billsId+'&Rnd='+Math.random(), {
			    title: '�༭����Ʊ�� ����',
			    width: 550,
			    height:220
			});
		}


function doDelete()
{
   var o;
   o = document.getElementsByName("checkbox");
   var m =0;
   var billsId="";
   for(var i=0;i<o.length;i++){
       if(o[i].checked){
           billsId += o[i].value+",";
       }
   }
   if (billsId==""){
	   art.dialog({
      	    title:'��ʾ',
  		    icon: 'error',
  		    content: '������ѡ��һ�',
  		    ok: function () {
  		        return
  		    }
   	 });
	  return false;
   }else{
	   art.dialog({
	        title:'ɾ��',
	    	icon: 'question',
	        content: 'ȷ��ɾ��?',
	        ok: function () {
		     var url = "<%=request.getContextPath()%>/YHPJLL_del.action";
	         new Ajax.Request( 
	         url, 
	           {   
	             method: 'post',   
	             parameters: {billsIdStr:billsId,t:new Date().getTime()},
	             onSuccess: function() 
	             {  
			    	   art.dialog({
		               	    title:'�ɹ�',
		           		    icon: 'succeed',
		           		    content: 'ɾ���ɹ���',
		           		    ok: function () {
			    		        close();
		           		        return true;
		           		    }
		            	 });
	             }
	          }
	         );
	        },
	        cancelVal: '�ر�',
	        cancel: true //Ϊtrue�ȼ���function(){}
	    });
  }
}
function doImport() {
    window.location.href = "<%=request.getContextPath()%>/pages/sjgl/yhpjllImport.jsp";
	
}
function close(){
	window.location.reload();
}
function checkAll() {
	var selectFlags = document.getElementsByName("checkbox");
	for (var i=0; i<selectFlags.length; i++) {
		selectFlags[i].checked = window.event.srcElement.checked;//ͨ�������İ�ť�ж���ѡ�л���δѡ
	}
}
</script>
	</body>
</html>
