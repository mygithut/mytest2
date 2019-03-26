
<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.*,com.dhcc.ftp.util.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="commonJs.jsp" />
<script type="text/javascript" language=javascript src="<%=request.getContextPath()%>/pages/js/prototype.js"></script>
<title>金融市场业务导入-同业</title>
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
                     <input type="checkbox" name="all" value="checkbox" onClick="doCheck()" />全选
                  </th>
					<th width="80">
						操作
					</th>
					<th width="80">
						序号
					</th>
					<th width="250">
						机构
					</th>
					<th width="150">
						产品名称
					</th>
					<th width="150">
						科目号
					</th>
					<th width="80">
						交易对手编号
					</th>
					<th width="80">
						交易对手名称
					</th>
					<th width="100">
						金额(元)
					</th>
					<th width="100">
						余额(元)
					</th>
					<th width="80">
						利率(%)
					</th>
					<th width="80">
						开始日期
					</th>
					<th width="80">
					    到期日期
					</th>
					<th width="80">
						计息天数
					</th>
					<th width="80">
						交易员
					</th>
	       </tr>
	       </thead>
	<% 
	   if(list.size()>0){
	 for (int i = 0; i < list.size(); i++){
	 %>
			 <tr>
			 	<td align="center"><input type="checkbox" name="checkbox" value="<%=CastUtil.trimNull(list.get(i).getAcId())%>" /></td>
		     
		     	<td align="center"><a href="javascript:doEdit('<%=CastUtil.trimNull(list.get(i).getAcId())%>')">编辑</a></td>
		     	
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
    		theFirstIsShown: false,//第一列在是否显示下拉列表中是否出现(某些列表第一列是全选按钮)
    		title: '数据列表',
    		buttons : [
    			   		{name: '新增', bclass: 'add', onpress : do_Add},
    			   		{name: '删除', bclass: 'delete', onpress : do_Del},
    			   		{name: '导入', bclass: 'import', onpress : do_Import}
    			   		]});
});

var selectFlags = document.getElementsByName("checkbox");



function doCheck() {
	var selectFlags = document.getElementsByName("checkbox");
	for (var i=0; i<selectFlags.length; i++) {
		selectFlags[i].checked = window.event.srcElement.checked;//通过单击的按钮判断是选中还是未选
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
	        		 title: '添加',
	        		 width: 1000,
	        		 height:300,
	        		 id:'add'
	        	 });
		    }
	});
}
function do_Import(){
	art.dialog.open('<%=request.getContextPath()%>/pages/jrscywdr_ty_import.jsp?rand='+Math.random(), {
	    title: '导入',
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
	    title: '编辑',
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
     		  alert("请选择一项！");
     		  return false;
     	   }else{ 
     	      if(confirm("您确定要删除吗?")){   
     	   		var url = "JRSCYWDRTY_del.action";
     			new Ajax.Request(url, {
     				method : 'post',
     				parameters : {
     				acId : acId
     				},
     				onSuccess : function() {
     					 art.dialog({
     	            	    title:'成功',
     	        		    icon: 'succeed',
     	        		    content: '删除成功！',
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
