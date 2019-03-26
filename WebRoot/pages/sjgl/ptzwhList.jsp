<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.entity.Ftp1CommonYield,com.dhcc.ftp.util.FtpUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List,com.dhcc.ftp.util.CommonFunctions"%>
<%@page import="com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>本行普通债收益率维护</title>
		<jsp:include page="../commonJs.jsp" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
	</head>
	<body>	
		<form id="form1" name="form1" method="get" action="">
			<%
				PageUtil ftp1CommonYieldUtil = (PageUtil) request.getAttribute("ftp1CommonYieldUtil");
			    List<Ftp1CommonYield> ftp1CommonYieldList = ftp1CommonYieldUtil.getList();
			    if (ftp1CommonYieldList == null) {
				    out.print("无该类型对应的数据！");
			    } else {
			%>
			<div align="center">
		    <table id="tableList">
		        <thead>
				<tr>
					<th align="center" width="90">
						<input type="checkbox" name="all" value="checkbox"
							onClick="checkAll()" />
						全选
					</th>
					<th width="150">
						收益率(%)
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
					<a href="javascript:doEdit(<%=ftp1CommonYield.getCommonId()%>)">编辑</a>
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
		    		theFirstIsShown: false,//第一列在是否显示下拉列表中是否出现(某些列表第一列是全选按钮)
		    		title: '本行普通债收益率列表',
		    		buttons : [
		    			   		{name: '新增', bclass: 'add', onpress : doAdd},
		    			   		{name: '删除', bclass: 'delete', onpress : doDelete},
		    			   		{name: '导入', bclass: 'import', onpress : doImport}
		    			   		]});
	    });
	    function doAdd(){
			art.dialog.open('<%=request.getContextPath()%>/pages/sjgl/ptzwhAdd.jsp?Rnd='+Math.random(), {
			    title: '添加本行普通债收益率',
			    width: 400,
			    height:390
			});
		}
		function doEdit(commonId){
			art.dialog.open('<%=request.getContextPath()%>/PTZWH_Query.action?commonId='+commonId+'&Rnd='+Math.random(), {
			    title: '编辑本行普通债收益率',
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
     	    title:'失败',
 		    icon: 'error',
 		    content: '请选择要删除的记录！',
 		    ok: function () {
 		        return
 		    }
  	 });
	  return false;
   }else{
	   art.dialog({
	        title:'删除',
	    	icon: 'question',
	        content: '确定删除?',
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
		               	    title:'成功',
		           		    icon: 'succeed',
		           		    content: '删除成功！',
		           		    ok: function () {
			    		        close();
		           		        return true;
		           		    }
		            	 });
	             }
	          }
	         );
	        },
	        cancelVal: '取消',
	        cancel: true //为true等价于function(){}
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
		selectFlags[i].checked = window.event.srcElement.checked;//通过单击的按钮判断是选中还是未选
	}
}
</script>
	</body>
</html>
