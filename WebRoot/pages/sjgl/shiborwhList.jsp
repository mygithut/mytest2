<%@page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page
	import="com.dhcc.ftp.entity.FtpShibor,java.util.ArrayList,java.util.List,com.dhcc.ftp.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>SHIBOR数据维护</title>
		<jsp:include page="../commonJs.jsp" />
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="" style="display: none;">
			<%
				PageUtil ftpShiborUtil = (PageUtil) request.getAttribute("ftpShiborUtil");
				List<FtpShibor> ftpShiborlist = ftpShiborUtil.getList();
				
				if (ftpShiborlist == null) {
					out.print("无该类型对应的数据！");
				} else {
			%>
			<div align="center">
				<table id="tableList">
					<thead>
						<tr>
							<th width="90">
								<input type="checkbox" name="all" value="checkbox"
									onClick="checkAll()" />
								全选
							</th>
							<th width="150">
								利率(%)
							</th>
							<th width="150">
								期限
							</td>
							<th width="150">
								日期
							</th>
							<th width="150">
								操作
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
								<a href="javascript:doEdit(<%=ftpShibor.getShiborId()%>)">编辑</a>
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
		    		theFirstIsShown: false,//第一列在是否显示下拉列表中是否出现(某些列表第一列是全选按钮)
		    		title: 'SHIBOR数据列表',
		    		buttons : [
		    			   		{name: '新增', bclass: 'add', onpress : doAdd},
		    			   		{name: '删除', bclass: 'delete', onpress : doDelete},
		    			   		{name: '导入', bclass: 'import', onpress : doImport}
		    			   		]});
	    });

	    function checkAll() {
	    	var selectFlags = document.getElementsByName("checkbox");
	    	for (var i=0; i<selectFlags.length; i++) {
	    		selectFlags[i].checked = window.event.srcElement.checked;//通过单击的按钮判断是选中还是未选
	    	}
	    }

        function doAdd(){
            art.dialog.open('<%=request.getContextPath()%>/pages/sjgl/shiborwhAdd.jsp?Rnd='+Math.random(), {
                title: '添加SHIBOR数据',
                width: 400,
                height:350
            });
        }
		function doEdit(shiborId){
			art.dialog.open('<%=request.getContextPath()%>/SHIBORWH_Query.action?shiborId='+shiborId+'&Rnd='+Math.random(), {
			    title: '编辑SHIBOR数据',
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
			  alert("请选择一项！");
			  return false;
		   }else{
			   art.dialog({
			        title:'删除',
			    	icon: 'question',
			        content: '确定删除?',
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
				               	    title:'成功',
				           		    icon: 'succeed',
				           		    content: '删除成功！',
				           		    ok: function () {
					    		        close();
				           		        return true;
				           		    }
				            	 });
			             }
			          });
			        },
			        cancelVal: '关闭',
			        cancel: true //为true等价于function(){}
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
