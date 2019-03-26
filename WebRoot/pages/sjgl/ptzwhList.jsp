<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.entity.Ftp1CommonYield,com.dhcc.ftp.util.FtpUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List,com.dhcc.ftp.util.CommonFunctions"%>
<%@page import="com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>������ͨծ������ά��</title>
		<jsp:include page="../commonJs.jsp" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
	</head>
	<body>	
		<form id="form1" name="form1" method="get" action="">
			<%
				PageUtil ftp1CommonYieldUtil = (PageUtil) request.getAttribute("ftp1CommonYieldUtil");
			    List<Ftp1CommonYield> ftp1CommonYieldList = ftp1CommonYieldUtil.getList();
			    if (ftp1CommonYieldList == null) {
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
						������(%)
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
					for (Ftp1CommonYield ftp1CommonYield : ftp1CommonYieldList) {
				%>
				<tbody>
				<tr>
					<td align="center">
						<input type="checkbox" name="checkbox"
							value="<%=ftp1CommonYield.getCommonId()%>" />
					</td>
					<td align="center">
						<%=CommonFunctions.doublecut(ftp1CommonYield.getCommonRate()*100,4)%>
					</td>
					<td align="center">
						<%=CastUtil.trimNull(ftp1CommonYield.getTermType())%>
					</td>
					<td align="center">						
						<%=CastUtil.trimNull(ftp1CommonYield.getCommonDate())%>						
					</td>
					<td align="center">						
					<a href="javascript:doEdit(<%=ftp1CommonYield.getCommonId()%>)">�༭</a>
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
					<td align="right"><%=ftp1CommonYieldUtil.getPageLine()%></td>
				</tr>
			</table>
		</form>

		<script language="javascript">
	    j(function(){
		    j('#tableList').flexigrid({
		    		height: 280,width:800,
		    		theFirstIsShown: false,//��һ�����Ƿ���ʾ�����б����Ƿ����(ĳЩ�б��һ����ȫѡ��ť)
		    		title: '������ͨծ�������б�',
		    		buttons : [
		    			   		{name: '����', bclass: 'add', onpress : doAdd},
		    			   		{name: 'ɾ��', bclass: 'delete', onpress : doDelete},
		    			   		{name: '����', bclass: 'import', onpress : doImport}
		    			   		]});
	    });
	    function doAdd(){
			art.dialog.open('<%=request.getContextPath()%>/pages/sjgl/ptzwhAdd.jsp?Rnd='+Math.random(), {
			    title: '��ӱ�����ͨծ������',
			    width: 400,
			    height:390
			});
		}
		function doEdit(commonId){
			art.dialog.open('<%=request.getContextPath()%>/PTZWH_Query.action?commonId='+commonId+'&Rnd='+Math.random(), {
			    title: '�༭������ͨծ������',
			    width: 550,
			    height:250
			});
		}


function doDelete()
{
   var o;
   o = document.getElementsByName("checkbox");
   var m =0;
   var commonId="";
   for(var i=0;i<o.length;i++){
       if(o[i].checked){
           commonId += o[i].value+",";
       }
   }
   if (commonId==""){
	   art.dialog({
     	    title:'ʧ��',
 		    icon: 'error',
 		    content: '��ѡ��Ҫɾ���ļ�¼��',
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
		     var url = "<%=request.getContextPath()%>/PTZWH_del.action";
	         new Ajax.Request( 
	         url, 
	           {   
	             method: 'post',   
	             parameters: {commonIdStr:commonId,t:new Date().getTime()},
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
    window.location.href = "<%=request.getContextPath()%>/pages/sjgl/ptzwhImport.jsp";
	
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
