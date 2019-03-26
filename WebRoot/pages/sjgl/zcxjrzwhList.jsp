<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.entity.FtpFinacialRate,com.dhcc.ftp.util.FtpUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List,com.dhcc.ftp.util.CommonFunctions"%>
<%@page import="com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>政策性金融债收益率维护</title>
		<jsp:include page="../commonJs.jsp" />
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="" style="display: none;">
			<%
				PageUtil FtpFinacialUtil = (PageUtil) request.getAttribute("FtpFinacialUtil");
					List<FtpFinacialRate> ftpFinacialList = FtpFinacialUtil.getList();
					if (ftpFinacialList == null) {
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
						金融债收益率(%)
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
					for (FtpFinacialRate ftpFinacialRate : ftpFinacialList) {
				%>
				<tbody>
				<tr>
					<td align="center">
						<input type="checkbox" name="checkbox"
							value="<%=ftpFinacialRate.getFinacialId()%>" />
					</td>
					<td align="center">
						<%=CommonFunctions.doublecut(ftpFinacialRate.getFinacialRate()*100,4)%>
					</td>
					<td align="center">
						<%=ftpFinacialRate.getFinacialTerm()%>
					</td>
					<td align="center">						
						<%=CastUtil.trimNull(ftpFinacialRate.getFinacialDate())%>						
					</td>
					<td align="center">						
					<a href="javascript:doEdit(<%=ftpFinacialRate.getFinacialId()%>)">编辑</a>
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
					<td align="right"><%=FtpFinacialUtil.getPageLine()%></td>
				</tr>
			</table>
		</form>

		<script language="javascript">
		document.getElementById("form1").style.display="block";
	    j(function(){
		    j('#tableList').flexigrid({
		    		height: 280,width:800,
		    		theFirstIsShown: false,//第一列在是否显示下拉列表中是否出现(某些列表第一列是全选按钮)
		    		title: '政策性金融债收益率列表',
		    		buttons : [
		    			   		{name: '新增', bclass: 'add', onpress : doAdd},
		    			   		{name: '删除', bclass: 'delete', onpress : doDelete},
		    			   		{name: '导入', bclass: 'import', onpress : doImport}
		    			   		]});
	    });
	    function doAdd(){
			art.dialog.open('<%=request.getContextPath()%>/pages/sjgl/zcxjrzwhAdd.jsp?Rnd='+Math.random(), {
			    title: '添加政策性金融债收益率',
			    width: 400,
			    height:350
			});
		}
		function doEdit(finacialId){
			art.dialog.open('<%=request.getContextPath()%>/ZCXJRZWH_Query.action?finacialId='+finacialId+'&Rnd='+Math.random(), {
			    title: '编辑政策性金融债收益率',
			    width: 550,
			    height:220
			});
		}


function doDelete()
{
   var o;
   o = document.getElementsByName("checkbox");
   var m =0;
   var finacialId="";
   for(var i=0;i<o.length;i++){
       if(o[i].checked){
           finacialId = finacialId +o[i].value+",";
       }
   }
   if (finacialId==""){
	  alert("请选择一项！");
	  return false;
   }else{
	   art.dialog({
	        title:'删除',
	    	icon: 'question',
	        content: '确定删除?',
	        ok: function () {
		     var url = "<%=request.getContextPath()%>/ZCXJRZWH_del.action";
	         new Ajax.Request( 
	         url, 
	           {   
	             method: 'post',   
	             parameters: {finacialIdStr:finacialId,t:new Date().getTime()},
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
	        cancelVal: '关闭',
	        cancel: true //为true等价于function(){}
	    });
  }
}
function doImport() {
    window.location.href = "<%=request.getContextPath()%>/pages/sjgl/zcxjrzwhImport.jsp";
	
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
