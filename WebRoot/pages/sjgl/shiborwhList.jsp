<%@page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page
	import="com.dhcc.ftp.entity.FtpShibor,java.util.ArrayList,java.util.List,com.dhcc.ftp.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>SHIBOR����ά��</title>
		<jsp:include page="../commonJs.jsp" />
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="" style="display: none;">
			<%
				PageUtil ftpShiborUtil = (PageUtil) request.getAttribute("ftpShiborUtil");
				List<FtpShibor> ftpShiborlist = ftpShiborUtil.getList();
				
				if (ftpShiborlist == null) {
					out.print("�޸����Ͷ�Ӧ�����ݣ�");
				} else {
			%>
			<div align="center">
				<table id="tableList">
					<thead>
						<tr>
							<th width="90">
								<input type="checkbox" name="all" value="checkbox"
									onClick="checkAll()" />
								ȫѡ
							</th>
							<th width="150">
								����(%)
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
					for (FtpShibor ftpShibor : ftpShiborlist) {
				%>
					<tbody>
						<tr>
							<td align="center">
								<input type="checkbox" name="checkbox" value="<%=ftpShibor.getShiborId()%>" />
							</td>
							<td align="center">
								<%=CommonFunctions.doublecut(ftpShibor.getShiborRate() * 100, 4)%>
							</td>
							<td align="center">
								<%=ftpShibor.getShiborTerm()%>
							</td>
							<td align="center">
								<%=CastUtil.trimNull(ftpShibor.getShiborDate())%>
							</td>
							<td align="center">
								<a href="javascript:doEdit(<%=ftpShibor.getShiborId()%>)">�༭</a>
							</td>
						</tr>
					</tbody>
					<%
					}
					}
				%>
				</table>
			</div>
			<table border="0" width="800" class="tb1" align="center">
				<tr>
					<td align="right"><%=ftpShiborUtil.getPageLine()%></td>
				</tr>
			</table>
		</form>

		<script language="javascript">
		document.getElementById("form1").style.display="block";
	    j(function(){
		    j('#tableList').flexigrid({
		    		height: 280,width:800,
		    		theFirstIsShown: false,//��һ�����Ƿ���ʾ�����б����Ƿ����(ĳЩ�б��һ����ȫѡ��ť)
		    		title: 'SHIBOR�����б�',
		    		buttons : [
		    			   		{name: '����', bclass: 'add', onpress : doAdd},
		    			   		{name: 'ɾ��', bclass: 'delete', onpress : doDelete},
		    			   		{name: '����', bclass: 'import', onpress : doImport}
		    			   		]});
	    });

	    function checkAll() {
	    	var selectFlags = document.getElementsByName("checkbox");
	    	for (var i=0; i<selectFlags.length; i++) {
	    		selectFlags[i].checked = window.event.srcElement.checked;//ͨ�������İ�ť�ж���ѡ�л���δѡ
	    	}
	    }

        function doAdd(){
            art.dialog.open('<%=request.getContextPath()%>/pages/sjgl/shiborwhAdd.jsp?Rnd='+Math.random(), {
                title: '���SHIBOR����',
                width: 400,
                height:350
            });
        }
		function doEdit(shiborId){
			art.dialog.open('<%=request.getContextPath()%>/SHIBORWH_Query.action?shiborId='+shiborId+'&Rnd='+Math.random(), {
			    title: '�༭SHIBOR����',
			    width: 550,
			    height:220
			});
		}

		function doDelete()
		{
		   var o;
		   o = document.getElementsByName("checkbox");
		   var m =0;
		   var shiborId="";
		   for(var i=0;i<o.length;i++){
		       if(o[i].checked){
		           shiborId = shiborId +o[i].value+",";
		       }
		   }
		   if (shiborId==""){
			  alert("��ѡ��һ�");
			  return false;
		   }else{
			   art.dialog({
			        title:'ɾ��',
			    	icon: 'question',
			        content: 'ȷ��ɾ��?',
			        ok: function () {
				     var url = "<%=request.getContextPath()%>/SHIBORWH_del.action";
			         new Ajax.Request( 
			         url, 
			           {   
			             method: 'post',   
			             parameters: {shiborIdStr:shiborId,t:new Date().getTime()},
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

		function doImport() {
			window.location.href = "<%=request.getContextPath()%>/pages/sjgl/shiborwhImport.jsp";
		}

		function close(){
			window.location.reload();
		}

</script>
	</body>
</html>
