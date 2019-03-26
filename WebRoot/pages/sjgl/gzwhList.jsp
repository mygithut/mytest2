<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.entity.FtpStockYield,com.dhcc.ftp.util.FtpUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List,com.dhcc.ftp.util.CommonFunctions"%>
<%@page import="com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>��ծ������ά��</title>
		<jsp:include page="../commonJs.jsp" />
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="">
			<%
				PageUtil FtpStockUtil = (PageUtil) request.getAttribute("FtpStockUtil");
					List<FtpStockYield> ftpStocklist = FtpStockUtil.getList();
					if (ftpStocklist == null) {
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
						��ծ������(%)
					</th>
					<th width="150">
						����
					</th>
					<th width="150">
						����
					</th>
                    <th width="150">
						����
					</th>
				</tr>
				</thead>
				<%
					for (FtpStockYield ftpStock : ftpStocklist) {
				%>
				<tbody>
				<tr>
					<td align="center">
						<input type="checkbox" name="checkbox"
							value="<%=ftpStock.getStockId()%>" />
					</td>
					<td align="center">
						<%=CommonFunctions.doublecut(ftpStock.getStockYield()*100,4)%>
					</td>
					<td align="center">
						<%=ftpStock.getStockTerm()%>
					</td>
					<td align="center">						
						<%=CastUtil.trimNull(ftpStock.getStockDate())%>						
					</td>
					<td align="center">						
					<a href="javascript:doEdit(<%=ftpStock.getStockId()%>)">�༭</a>
					</td>
				</tr>
				</tbody>
				<%
					}
					}
				%>
			</table>
			</div>
			<table border="0" width="800" class="tb1"
				style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
				align="center">
				<tr>
					<td align="right"><%=FtpStockUtil.getPageLine()%></td>
				</tr>
			</table>
		</form>

		<script language="javascript">
	    j(function(){
		    j('#tableList').flexigrid({
		    		height: 280,width:800,
		    		theFirstIsShown: false,//��һ�����Ƿ���ʾ�����б����Ƿ����(ĳЩ�б��һ����ȫѡ��ť)
		    		title: '���м��ծ�������б�',
		    		buttons : [
		    			   		{name: '����', bclass: 'add', onpress : doAdd},
		    			   		{name: 'ɾ��', bclass: 'delete', onpress : doDelete},
		    			   		{name: '����', bclass: 'import', onpress : doImport}
		    			   		]});
	    });
		function doAdd(){
			art.dialog.open('<%=request.getContextPath()%>/pages/sjgl/gzwhAdd.jsp?Rnd='+Math.random(), {
			    title: '��ӹ�ծ������',
			    width: 550,
			    height:390
			});
		}
		function doEdit(stockId){
			art.dialog.open('<%=request.getContextPath()%>/GZWH_Query.action?stockId='+stockId+'&Rnd='+Math.random(), {
			    title: '�༭��ծ������',
			    width: 550,
			    height:220
			});
		}


function doDelete()
{
   var o;
   o = document.getElementsByName("checkbox");
   var m =0;
   var stockId="";
   for(var i=0;i<o.length;i++){
       if(o[i].checked){
           stockId = stockId +o[i].value+",";
       }
   }
   
   if (stockId==""){
	   art.dialog({
      	    title:'ʧ��',
  		    icon: 'error',
  		    content: '��ѡ��Ҫɾ���ļ�¼��',
  		    cancelVal: '�ر�',
  		    cancel: true
   	  });
	  return false;
   }else{
	   art.dialog({
	        title:'ɾ��',
	    	icon: 'question',
	        content: '��ȷ��Ҫɾ����?',
	        ok: function () {
		     var url = "<%=request.getContextPath()%>/GZWH_del.action";
	         new Ajax.Request( 
	         url, 
	           {   
	             method: 'post',   
	             parameters: {stockIdStr:stockId,t:new Date().getTime()},
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
	        cancelVal: 'ȡ��',
	        cancel: true //Ϊtrue�ȼ���function(){}
	    });
  }
}
function doImport() {
    window.location.href = "<%=request.getContextPath()%>/pages/sjgl/gzwhImport.jsp";
	
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
