<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.entity.Ftp1BankBillsRate,com.dhcc.ftp.util.FtpUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List,com.dhcc.ftp.util.CommonFunctions"%>
<%@page import="com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>央行票据利率维护</title>
		<jsp:include page="../commonJs.jsp" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
	</head>
	<body>	
		<form id="form1" name="form1" method="get" action="" style="display: none;">
			<%
				PageUtil ftpBankBillsRateUtil = (PageUtil) request.getAttribute("ftpBankBillsRateUtil");
			    List<Ftp1BankBillsRate> ftpBankBillsRateList = ftpBankBillsRateUtil.getList();
			    if (ftpBankBillsRateList == null) {
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
						央行票据利率(%)
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
					for (Ftp1BankBillsRate ftpBankRate : ftpBankBillsRateList) {
				%>
				<tbody>
				<tr>
					<td align="center">
						<input type="checkbox" name="checkbox"
							value="<%=ftpBankRate.getBillsId() %>" />
					</td>
					<td align="center">
						<%=CommonFunctions.doublecut(ftpBankRate.getBillsRate()*100,4)%>
					</td>
					<td align="center">
						<%=CastUtil.trimNull(ftpBankRate.getTermType())%>
					</td>
					<td align="center">						
						<%=CastUtil.trimNull(ftpBankRate.getBillsDate())%>						
					</td>
					<td align="center">						
					<a href="javascript:doEdit(<%=ftpBankRate.getBillsId()%>)">编辑</a>
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
					<td align="right"><%=ftpBankBillsRateUtil.getPageLine()%></td>
				</tr>
			</table>
		</form>

		<script language="javascript">
		document.getElementById("form1").style.display="block";
	    j(function(){
		    j('#tableList').flexigrid({
		    		height: 280,width:800,
		    		theFirstIsShown: false,//第一列在是否显示下拉列表中是否出现(某些列表第一列是全选按钮)
		    		title: '央行票据利率列表',
		    		buttons : [
		    			   		{name: '新增', bclass: 'add', onpress : doAdd},
		    			   		{name: '删除', bclass: 'delete', onpress : doDelete},
		    			   		{name: '导入', bclass: 'import', onpress : doImport}
		    			   		]});
	    });
	    function doAdd(){
			art.dialog.open('<%=request.getContextPath()%>/pages/sjgl/yhpjllAdd.jsp?Rnd='+Math.random(), {
			    title: '添加央行票据利率',
			    width: 400,
			    height:350
			});
		}
		function doEdit(billsId){
			art.dialog.open('<%=request.getContextPath()%>/YHPJLL_Query.action?billsId='+billsId+'&Rnd='+Math.random(), {
			    title: '编辑央行票据 利率',
			    width: 550,
			    height:220
			});
		}


function doDelete()
{
   var o;
   o = document.getElementsByName("checkbox");
   var m =0;
   var billsId="";
   for(var i=0;i<o.length;i++){
       if(o[i].checked){
           billsId += o[i].value+",";
       }
   }
   if (billsId==""){
	   art.dialog({
      	    title:'提示',
  		    icon: 'error',
  		    content: '请至少选择一项！',
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
		     var url = "<%=request.getContextPath()%>/YHPJLL_del.action";
	         new Ajax.Request( 
	         url, 
	           {   
	             method: 'post',   
	             parameters: {billsIdStr:billsId,t:new Date().getTime()},
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
    window.location.href = "<%=request.getContextPath()%>/pages/sjgl/yhpjllImport.jsp";
	
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
