
<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.*,com.dhcc.ftp.util.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="commonJs.jsp" />
<script type="text/javascript" language=javascript src="<%=request.getContextPath()%>/pages/js/prototype.js"></script>
<title>�����г�ҵ����-ͬҵ</title>
<style type="text/css">
<!--
.STYLE1 {font-size: medium}
.STYLE2 {font-size: large}
.STYLE3 {font-size: small;color:#2907F0; }
-->
</style>
</head>
<% 
 
 	PageUtil JrTyUtil =(PageUtil)request.getAttribute("JrTyUtil"); 
 	List<JrTy> list = JrTyUtil.getList(); 
%>
<body>
<form name="form1" method="post">
           <table  border="0" cellspacing="0" cellpadding="0" align="center"  id="tableList" >	
             <thead>  <tr >
	              <th align="center" Name="basedataid" width="80">
                     <input type="checkbox" name="all" value="checkbox" onClick="doCheck()" />ȫѡ
                  </th>
					<th width="80">
						����
					</th>
					<th width="80">
						���
					</th>
					<th width="250">
						����
					</th>
					<th width="150">
						��Ʒ����
					</th>
					<th width="150">
						��Ŀ��
					</th>
					<th width="80">
						���׶��ֱ��
					</th>
					<th width="80">
						���׶�������
					</th>
					<th width="100">
						���(Ԫ)
					</th>
					<th width="100">
						���(Ԫ)
					</th>
					<th width="80">
						����(%)
					</th>
					<th width="80">
						��ʼ����
					</th>
					<th width="80">
					    ��������
					</th>
					<th width="80">
						��Ϣ����
					</th>
					<th width="80">
						����Ա
					</th>
	       </tr>
	       </thead>
	<% 
	   if(list.size()>0){
	 for (int i = 0; i < list.size(); i++){
	 %>
			 <tr>
			 	<td align="center"><input type="checkbox" name="checkbox" value="<%=CastUtil.trimNull(list.get(i).getAcId())%>" /></td>
		     
		     	<td align="center"><a href="javascript:doEdit('<%=CastUtil.trimNull(list.get(i).getAcId())%>')">�༭</a></td>
		     	
		     	<td align="center"><%=(list.get(i).getAcId().indexOf("_")==-1?list.get(i).getAcId():list.get(i).getAcId().substring(0,list.get(i).getAcId().indexOf("_")))%></td>
		     	<td align="center"><%=CastUtil.trimNull(list.get(i).getBrName())%>[<%=CastUtil.trimNull(list.get(i).getBrNo())%>]</td>
		     	<td align="center"><%=CastUtil.trimNull(list.get(i).getPrdtName())%></td>
		     	<td align="center"><%=CastUtil.trimNull(list.get(i).getKmh())%></td>
		     	<td align="center"><%=CastUtil.trimNull(list.get(i).getCustNo())%></td>
		     	<td align="center"><%=CastUtil.trimNull(list.get(i).getCusName())%></td>
		     
<td align="center"><%= list.get(i).getAmt()==null ? "":FormatUtil.toMoney(list.get(i).getAmt())%></td>
<td align="center"><%= list.get(i).getBal()==null ? "":FormatUtil.toMoney(list.get(i).getBal())%></td>
<td align="center"><%= list.get(i).getRate()==null ? "":CommonFunctions.doubleFormat(list.get(i).getRate()*100,2)%></td>
		     	<td align="center"><%=CastUtil.trimNull(list.get(i).getStartDate())%></td>
		     	<td align="center"><%=CastUtil.trimNull(list.get(i).getEndDate())%></td>
		     	<td align="center"><%=list.get(i).getDays()==null?0:list.get(i).getDays()%></td>
		     	<td align="center"><%=CastUtil.trimNull(list.get(i).getEmpName())%></td>
			</tr>
	<%}} else {%>
		<tr><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
		<td></td><td></td><td></td><td></td><td></td><td></td></tr>
	<%}  %>
    		</table>
			<table border="0" width="800" class="tb1" align="center">
				<tr>
					<td align="right"><%=JrTyUtil.getPageLine()%></td>
				</tr>
			</table>
		</form>

</body>

<script type="text/javascript">	
j(function(){
    j('#tableList').flexigrid({
    		height: 230,width:950,
    		theFirstIsShown: false,//��һ�����Ƿ���ʾ�����б����Ƿ����(ĳЩ�б��һ����ȫѡ��ť)
    		title: '�����б�',
    		buttons : [
    			   		{name: '����', bclass: 'add', onpress : do_Add},
    			   		{name: 'ɾ��', bclass: 'delete', onpress : do_Del},
    			   		{name: '����', bclass: 'import', onpress : do_Import}
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
	var url = "<%=request.getContextPath()%>/JRSCYWDRTY_getNextAcId.action";
	new Ajax.Request( 
	    url, 
	    {  
	        method: 'post',   
	        parameters: {
	            t:new Date().getTime()
	        },
	        onSuccess: function(val){
	        	 art.dialog.open('<%=request.getContextPath()%>/pages/jrscywdr_ty_add.jsp?acId='+val.responseText+'&rand='+Math.random(), {
	        		 title: '���',
	        		 width: 1000,
	        		 height:300,
	        		 id:'add'
	        	 });
		    }
	});
}
function do_Import(){
	art.dialog.open('<%=request.getContextPath()%>/pages/jrscywdr_ty_import.jsp?rand='+Math.random(), {
	    title: '����',
	    width: 1000,
	    height:300
	});
<%--	 var returnValue = window.showModalDialog(--%>
<%--				"<%=request.getContextPath()%>/pages/jrscywdr_ty_import.jsp?rand="--%>
<%--				+ Math.random(), window,--%>
<%--				"dialogWidth=750px;dialogHeight=350px");--%>
<%--	 if (returnValue == "true") {--%>
<%--			window.location.reload();--%>
<%--		} else if (returnValue == "false") {--%>
<%--			--%>
<%--		}--%>
}
function doEdit(acId){
	art.dialog.open('<%=request.getContextPath()%>/JRSCYWDRTY_edit.action?acId='	+ acId+'&rand='+Math.random(), {
	    title: '�༭',
	    width: 1000,
	    height:300
	});
 
}
function do_Del() {
           var o;
     	   o = document.getElementsByName("checkbox");
     	   
     	   var acId="";
     	   for(var i=0;i<o.length;i++){
     	       if(o[i].checked){
     	    	   acId = acId +o[i].value+"@@";//, to @@
     	    	 //  alert(acId);
     	       }
     	   }
     	  if (acId==""){
     		  alert("��ѡ��һ�");
     		  return false;
     	   }else{ 
     	      if(confirm("��ȷ��Ҫɾ����?")){   
     	   		var url = "JRSCYWDRTY_del.action";
     			new Ajax.Request(url, {
     				method : 'post',
     				parameters : {
     				acId : acId
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
