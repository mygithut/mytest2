
<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.*,com.dhcc.ftp.util.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>	
<jsp:include page="commonJs.jsp" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		
<title>�����г�ҵ����-�����֤��</title>
<style type="text/css">
</style>
</head>
<% 
 
 	PageUtil pageUtil =(PageUtil)request.getAttribute("pageUtil"); 
 	List<Ftp1LdRatioAdjust> list = pageUtil.getList(); 
%>
<body>
<div class="cr_header">
			��ǰλ�ã�����ƥ��->���۲�������->�������ϱȵ���
</div>
<div align="center"  style="text-align: center; margin-top: 20px; margin-left: 30px;" >
<form name="form1" id="form1" method="post" style="display: none;">
             <table  border="0" cellspacing="0" cellpadding="0" id="tableList"   >	
	          <thead> 
	           <tr >
	              <th align="center"  Name="basedataid" width="120">
                     <input type="checkbox" name="all" value="checkbox" onClick="doCheck()" />ȫѡ
                  </th>
					<th width="120">
						����
					</th>
                  <th width="150">
						��С�����(%)[��]
					</th>
					<th width="150">
						�������(%)
					</th>
					<th width="150">
						����ֵ(BP)
					</th>
<%--					<th width="280">--%>
<%--						����--%>
<%--					</th>--%>
	       </tr>
	       </thead>
	       <tbody>
	<% 
	 for (int i = 0; i < list.size(); i++){
	   if(list.size()>0){
	 %>
			 <tr>
			 	<%if(list.get(i).getMinRatio()==-0.01) {%>
			 	<td align="center">	</td>
		     	<td align="center"><a href="javascript:doEdit('<%=CastUtil.trimNull(list.get(i).getId())%>')">�༭</a></td>
		        <td align="center">�޴����</td>
		     	<td align="center">�޴����</td>
			 	<%}else if(list.get(i).getMaxRatio()==99999.99){%>
			 	<td align="center">	</td>
		     	<td align="center"><a href="javascript:doEdit('<%=CastUtil.trimNull(list.get(i).getId())%>')">�༭</a></td>
		        <td align="center"><%=CommonFunctions.doublecut(list.get(i).getMinRatio()*100,2)%></td>
		     	<td align="center">+��</td>
			 	<%	}else{%>
			 	<td align="center">
			 		<input type="checkbox" name="checkbox" value="<%=CastUtil.trimNull(list.get(i).getId())%>" />
			 	</td>
		     	<td align="center"><a href="javascript:doEdit('<%=CastUtil.trimNull(list.get(i).getId())%>')">�༭</a></td>
		        <td align="center"><%=CommonFunctions.doublecut(list.get(i).getMinRatio()*100,2)%></td>
		     	<td align="center"><%=CommonFunctions.doublecut(list.get(i).getMaxRatio()*100,2)%></td>
				<%}%>
		     	<td align="center"><%= CommonFunctions.doublecut(list.get(i).getAdjustValue() *10000,1) %></td>
			</tr>
	<%} else {} } %>
	</tbody>
	</table>
<%--	<table border="0" width="800" class="tb1" align="center">--%>
<%--				<tr>--%>
<%--					<td align="right"><%=pageUtil.getPageLine()%></td>--%>
<%--				</tr>--%>
<%--			</table>--%>
		</form>
</div>
</body>
<script type="text/javascript">	
document.getElementById("form1").style.display="block";
j(function(){
    j('#tableList').flexigrid({
    		height: 230,width:1000,
    		theFirstIsShown: false,//��һ�����Ƿ���ʾ�����б����Ƿ����(ĳЩ�б��һ����ȫѡ��ť)
    		title: '�����б�',
    		buttons : [
    			   		{name: '����', bclass: 'add', onpress : do_Add},
    			   		{name: 'ɾ��', bclass: 'delete', onpress : do_Del}
<%--    			   		{name: '����', bclass: 'import', onpress : do_Import}--%>
    			   		]});
});

var selectFlags = document.getElementsByName("checkbox");
function doCheck() {
	var selectFlags = document.getElementsByName("checkbox");
	for (var i=0; i<selectFlags.length; i++) {
		selectFlags[i].checked = window.event.srcElement.checked;//ͨ�������İ�ť�ж���ѡ�л���δѡ
	}
}
function do_Add(){
	art.dialog.open('<%=request.getContextPath()%>/pages/hdbzbtz_add.jsp?rand='+Math.random(), {
	    title: '���',
	    width: 500,
	    height:200 
	});
}
function doEdit(id){
	art.dialog.open('<%=request.getContextPath()%>/HDBZBTZ_edit.action?Id='+id+'&rand='+Math.random(), {
	    title: '�༭',
	    width: 500,
	    height:200 
	});
}
function do_Del() {
           var o;
     	   o = document.getElementsByName("checkbox");
     	   var id="";
     	   for(var i=0;i<o.length;i++){
     	       if(o[i].checked){
     	    	   id = id +o[i].value+"@@";//, to @@
     	       }
     	   }
     	  if (id==""){
     		  alert("��ѡ��һ�");
     		  return false;
     	   }else{ 
     	      if(confirm("��ȷ��Ҫɾ����?")){   
     	   		var url = "HDBZBTZ_del.action";
     			new Ajax.Request(url, {
     				method : 'post',
     				parameters : {
     				id:id
     				},
     				onSuccess : function() {
     					 art.dialog({
      	            	    title:'�ɹ�',
      	        		    icon: 'succeed',
      	        		    content: 'ɾ���ɹ���',
      	        		    ok: function () {
      	        		    	ok();
      	        		        return true;
      	        		    }
      	         	 	});
     				}
     			});
     		} else {
     			return false;
     		}
     	  }
   }   
   function ok(){
   window.location.reload();
   }
</script>
</html>
