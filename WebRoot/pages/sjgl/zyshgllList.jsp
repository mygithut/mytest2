<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.entity.Ftp1PledgeRepoRate,com.dhcc.ftp.util.FtpUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List,com.dhcc.ftp.util.CommonFunctions"%>
<%@page import="com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>��Ѻʽ�ع�����ά��</title>
		<jsp:include page="../commonJs.jsp" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
	</head>
	<body>	
		<form id="form1" name="form1" method="get" action="">
			<%
				PageUtil ftpPledgeRepoUtil = (PageUtil) request.getAttribute("ftpPledgeRepoUtil");
			    List<Ftp1PledgeRepoRate> ftpRepoList = ftpPledgeRepoUtil.getList();
			    if (ftpRepoList == null) {
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
						��Ѻʽ�ع�����(%)
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
					for (Ftp1PledgeRepoRate ftpRepoRate : ftpRepoList) {
				%>
				<tbody>
				<tr>
					<td align="center">
						<input type="checkbox" name="checkbox"
							value="<%=ftpRepoRate.getRepoId() %>" />
					</td>
					<td align="center">
						<%=CommonFunctions.doublecut(ftpRepoRate.getRepoRate()*100,4)%>
					</td>
					<td align="center">
						<%=CastUtil.trimNull(ftpRepoRate.getTermType())%>
					</td>
					<td align="center">						
						<%=CastUtil.trimNull(ftpRepoRate.getRepoDate())%>						
					</td>
					<td align="center">						
					<a href="javascript:doEdit(<%=ftpRepoRate.getRepoId()%>)">�༭</a>
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
					<td align="right"><%=ftpPledgeRepoUtil.getPageLine()%></td>
				</tr>
			</table>
		</form>

		<script language="javascript">
	    j(function(){
		    j('#tableList').flexigrid({
		    		height: 280,width:800,
		    		theFirstIsShown: false,//��һ�����Ƿ���ʾ�����б����Ƿ����(ĳЩ�б��һ����ȫѡ��ť)
		    		title: '��Ѻʽ�ع������б�',
		    		buttons : [
		    			   		{name: '����', bclass: 'add', onpress : doAdd},
		    			   		{name: 'ɾ��', bclass: 'delete', onpress : doDelete},
		    			   		{name: '����', bclass: 'import', onpress : doImport}
		    			   		]});
	    });
	    function doAdd(){
			art.dialog.open('<%=request.getContextPath()%>/pages/sjgl/zyshgllAdd.jsp?Rnd='+Math.random(), {
			    title: '�����Ѻʽ�ع�����',
			    width: 400,
			    height:350
			});
		}
		function doEdit(repoId){
			art.dialog.open('<%=request.getContextPath()%>/ZYSHGLL_Query.action?repoId='+repoId+'&Rnd='+Math.random(), {
			    title: '�༭��Ѻʽ�ع�����',
			    width: 550,
			    height:220
			});
		}


function doDelete()
{
   var o;
   o = document.getElementsByName("checkbox");
   var m =0;
   var repoId="";
   for(var i=0;i<o.length;i++){
       if(o[i].checked){
           repoId += o[i].value+",";
       }
   }
   if (repoId==""){
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
		     var url = "<%=request.getContextPath()%>/ZYSHGLL_del.action";
	         new Ajax.Request( 
	         url, 
	           {   
	             method: 'post',   
	             parameters: {repoIdStr:repoId,t:new Date().getTime()},
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
    window.location.href = "<%=request.getContextPath()%>/pages/sjgl/zyshgllImport.jsp";
	
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
