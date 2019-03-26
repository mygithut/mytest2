<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.entity.Ftp1OfRateSpread,com.dhcc.ftp.util.FtpUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List,com.dhcc.ftp.util.CommonFunctions"%>
<%@page import="com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>��ͨծ�����ծ�����ʵ��ά��</title>
		<jsp:include page="../commonJs.jsp" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
	</head>
	<body>	
		<form id="form1" name="form1" method="get" action="" style="display: none;">
			<%
				PageUtil ftpRateSpreadUtil = (PageUtil) request.getAttribute("ftpRateSpreadUtil");
			    List<Ftp1OfRateSpread> ftpRateSpreadList = ftpRateSpreadUtil.getList();
			    if (ftpRateSpreadList == null) {
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
						�����ʵ��(%)
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
					for (Ftp1OfRateSpread ftpRateSpread : ftpRateSpreadList) {
				%>
				<tbody>
				<tr>
					<td align="center">
						<input type="checkbox" name="checkbox"
							value="<%=ftpRateSpread.getSpreadId()%>" />
					</td>
					<td align="center">
						<%=CommonFunctions.doublecut(ftpRateSpread.getSpreadRate()*100,4)%>
					</td>
					<td align="center">
						<%=CastUtil.trimNull(ftpRateSpread.getTermType())%>
					</td>
					<td align="center">						
						<%=CastUtil.trimNull(ftpRateSpread.getSpreadDate())%>						
					</td>
					<td align="center">						
					<a href="javascript:doEdit(<%=ftpRateSpread.getSpreadId()%>)">�༭</a>
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
					<td align="right"><%=ftpRateSpreadUtil.getPageLine()%></td>
				</tr>
			</table>
		</form>

		<script language="javascript">
		document.getElementById("form1").style.display="block";
	    j(function(){
		    j('#tableList').flexigrid({
		    		height: 280,width:800,
		    		theFirstIsShown: false,//��һ�����Ƿ���ʾ�����б����Ƿ����(ĳЩ�б��һ����ȫѡ��ť)
		    		title: '��ͨծ�����ծ�����ʵ���б�',
		    		buttons : [
		    			   		{name: '����', bclass: 'add', onpress : doAdd},
		    			   		{name: 'ɾ��', bclass: 'delete', onpress : doDelete},
		    			   		{name: '����', bclass: 'import', onpress : doImport}
		    			   		]});
	    });
	    function doAdd(){
			art.dialog.open('<%=request.getContextPath()%>/pages/sjgl/ptzjrzsyldcwhAdd.jsp?Rnd='+Math.random(), {
			    title: '�����ͨծ�����ծ�����ʵ��',
			    width: 400,
			    height:350
			});
		}
		function doEdit(spreadId){
			art.dialog.open('<%=request.getContextPath()%>/PTZJRZSYLDCWH_Query.action?spreadId='+spreadId+'&Rnd='+Math.random(), {
			    title: '�༭��ͨծ�����ծ�����ʵ��',
			    width: 550,
			    height:220
			});
		}


function doDelete()
{
   var o;
   o = document.getElementsByName("checkbox");
   var m =0;
   var spreadId="";
   for(var i=0;i<o.length;i++){
       if(o[i].checked){
           spreadId += o[i].value+",";
       }
   }
   if (spreadId==""){
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
		     var url = "<%=request.getContextPath()%>/PTZJRZSYLDCWH_del.action";
	         new Ajax.Request( 
	         url, 
	           {   
	             method: 'post',   
	             parameters: {spreadIdStr:spreadId,t:new Date().getTime()},
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
    window.location.href = "<%=request.getContextPath()%>/pages/sjgl/ptzjrzsyldcwhImport.jsp";
	
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
