<%@page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page	import="java.util.ArrayList,java.util.List,com.dhcc.ftp.util.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>������б�</title>
		<jsp:include page="commonJs.jsp" />
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="">
			<%
				List list = (List)request.getAttribute("list");
				
				if (list == null) {
					out.print("�޸����Ͷ�Ӧ�����ݣ�");
				} else {
			%>
			<div align="center">
				<table id="tableList"  border="0">
					<thead>
						<tr>
							<th width="100">
								<input type="checkbox" name="all" value="checkbox"	onClick="checkAll()" />
								ȫѡ
							</th>
							<th width="150">
								�����(%)
							</th>
							<th width="90">
								һ������(BP)
							</td>
							<th width="90">
								1-3��(BP)
							</th>
							<th width="90">
								3-5��(BP)
						    </th>
						    <th width="90">
								5������(BP)
						    </th>
						    <th width="90">
								����
						    </th>
						</tr>
					</thead>
					<%
					for (int i = 0; i < list.size(); i++) {
						Object[] obj = (Object[])list.get(i);
				%>
					<tbody>
						<tr>
							<td align="center">
								<input type="checkbox" name="checkbox" value="<%=CastUtil.trimNull(obj[0])%>" />
							</td>
							<td align="center">
							<%=CommonFunctions.doublecut(Double.parseDouble(CastUtil.trimNull(obj[1]))*100,3)%>
							<%if((obj[3]!=null)&&CastUtil.trimNull(obj[3]).equals("1")){ %>
							    ��
							<%}else{ %>
							           ��
							<%} %>�����
							<%if((obj[4]!=null)&&CastUtil.trimNull(obj[4]).equals("1")){ %>
							    ��
							<%}else{ %>
							           ��
							<%} %>
								<%=CommonFunctions.doublecut(Double.parseDouble(CastUtil.trimNull(obj[2]))*100,3)%>
							</td>
							<td align="center">
								<%=CommonFunctions.doublecut(Double.parseDouble(CastUtil.trimNull(obj[8]))*10000,3)%>
							</td>
							<td align="center">
								<%=CommonFunctions.doublecut(Double.parseDouble(CastUtil.trimNull(obj[7]))*10000,3)%>
							</td>
							<td align="center">
								<%=CommonFunctions.doublecut(Double.parseDouble(CastUtil.trimNull(obj[6]))*10000,3)%>
							</td>
							<td align="center">
								<%=CommonFunctions.doublecut(Double.parseDouble(CastUtil.trimNull(obj[5]))*10000,3)%>
							</td>
							<td align="center">
							    <a href="javascript:doEdit1('<%=CastUtil.trimNull(obj[0])%>','<%=CastUtil.trimNull(obj[1])%>','<%=CastUtil.trimNull(obj[2])%>','<%=CastUtil.trimNull(obj[3])%>','<%=CastUtil.trimNull(obj[4])%>','<%=CastUtil.trimNull(obj[8])%>','<%=CastUtil.trimNull(obj[7])%>','<%=CastUtil.trimNull(obj[6])%>','<%=CastUtil.trimNull(obj[5])%>')">�޸�</a>
							</td>
						</tr>
					</tbody>
					<%
					}
					}
				%>
				</table>
			</div>
		</form>

		<script language="javascript">
	    j(function(){
		    j('#tableList').flexigrid({
		    		height: 280,width:1000,
		    		theFirstIsShown: false,//��һ�����Ƿ���ʾ�����б����Ƿ����(ĳЩ�б��һ����ȫѡ��ť)
		    		title: '�����б�',
		    		buttons : [
                                {name: '�½�', bclass: 'add', onpress : doAdd},
		    			   		{name: 'ɾ��', bclass: 'delete', onpress : doDelete}
		    			   		]});
	    });

	    function checkAll() {
	    	var selectFlags = document.getElementsByName("checkbox");
	    	for (var i=0; i<selectFlags.length; i++) {
	    		selectFlags[i].checked = window.event.srcElement.checked;//ͨ�������İ�ť�ж���ѡ�л���δѡ
	    	}
	    }
	    		
		
<%--			var o;
			o = document.getElementsByName("checkbox");
			var count=0;
			var rateId="";
			
			for(var i=0;i<o.length;i++){
			   if(o[i].checked){
			       count++;
			    }
			 }
			 if(count==0){
				alert("��ѡ��һ�");
				return false;
			 }else if(count>1){
				alert("ֻ��ѡ��һ�");
				return false;
			 }else{

     		 var maxRate = document.getElementById("maxRate").value;
			 var frontOperator = document.getElementById("frontOperator").value;
			 var backOperator = document.getElementById("backOperator").value;
			 var value4 = document.getElementById("value4").value;
			 var value3 = document.getElementById("value3").value;
			 var value2 = document.getElementById("value2").value;
			 var value1 = document.getElementById("value1").value;
			 alert(minRate);--%>

		function doEdit1(id,minRate,maxRate,frontOperator,backOperator,value4,value3,value2,value1){	 
			 window.parent.location.href="<%=request.getContextPath()%>/pages/cdbtz.jsp?id="+id+"&minRate="+minRate+
			 "&maxRate="+maxRate+"&frontOperator="+frontOperator+"&backOperator="+backOperator+"&adjustValue4="+value4+
			 "&adjustValue3="+value3+"&adjustValue2="+value2+"&adjustValue1="+value1;
			 	 
			
		}

		function doAdd(){
			window.parent.location.reload();
		}

		function doDelete()
		{
		   var o;
		   o = document.getElementsByName("checkbox");
		   var m =0;
		   var rateId="";
		   for(var i=0;i<o.length;i++){
		       if(o[i].checked){
		           rateId = rateId +o[i].value+",";
		       }
		   }
		   if (rateId==""){
			  alert("��ѡ��һ�");
			  return false;
		   }else{
			   art.dialog({
			        title:'ɾ��',
			    	icon: 'question',
			        content: 'ȷ��ɾ��?',
			        ok: function () {
				     var url = "<%=request.getContextPath()%>/CDBTZ_del.action";
			         new Ajax.Request( 
			         url, 
			           {   
			             method: 'post',   
			             parameters: {id:rateId,t:new Date().getTime()},
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
			          });
			        },
			        cancelVal: '�ر�',
			        cancel: true //Ϊtrue�ȼ���function(){}
			    });
			}
		}		

		function close(){
			window.location.reload();
		}

</script>
	</body>
</html>
