<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*" errorPage=""%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<jsp:include page="commonJs.jsp" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<title>系统管理-定价策略配置</title>
	</head>
	<%
 	PageUtil ftpSystemInitialUtil =(PageUtil)request.getAttribute("ftpSystemInitialUtil"); 
	List ftpSystemInitialList = ftpSystemInitialUtil.getList(); 
%>
	<body>
		<form name="chooseform" method="post">
		
		<table id="tableList">
			<thead>
				<tr>
					<th width="100">机构编号
					</th>
					<th width="250">
						机构名称
					</th>
					<th width="100">
						配置结果
					</th>
					<th width="100">
						配置日期
					</th>
					<th width="100">
						配置操作员
					</th>
					<th width="100">
						配置次数
					</th>
					<th width="60">
						操作
					</th>
				</tr>
				</thead>
				<%if (ftpSystemInitialList != null && ftpSystemInitialList.size() > 0) {
	 for (Object O : ftpSystemInitialList){
		 Object[] o = (Object[])O;
	 %>
	 <tbody>
				<tr>
					<td align="center"><%=CastUtil.trimNull(String.valueOf(o[1]))%></td>
					<td align="center"><%=CastUtil.trimNull(String.valueOf(o[2]))%></td>
					<td align="center"><%=FtpUtil.CastSetResult(Integer.valueOf(o[3].toString()),"")%></td>
					<td align="center"><%=CastUtil.trimNull(String.valueOf(o[4]))%></td>
					<td align="center"><%=CastUtil.trimNull(String.valueOf(o[5]))%></td>
					<td align="center"><%=CastUtil.trimNull(String.valueOf(o[6]))%></td>
					<td id="edit" align="center">
						<a href="javascript:set('<%=o[1]%>','<%=o[3]%>')" >配置
					</td>
				</tr>
				</tbody>
				<% }} %>

			</table>
			<table border="0" width="80%" class="tb1"
			style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0"
			align="center">
			<tr>
					<td align="right"><%=ftpSystemInitialUtil.getPageLine()%></td>
				</tr>
			</table>
		</form>
     <div id="data" style="display:none" align="center">
     <table align="center" class="table">
     <tr><td width="100" align="right">定价策略：</td><td width="150">
     <select style="width: 250" id="set" name="set" onchange="onChange(this.value)">
<!--				        <option value="1" selected="selected">单资金池</option>-->
<!--				        <option value="2">双资金池</option>-->
				        <option value="3">多资金池</option>
				        <option value="4">期限匹配</option>
            		</select>
     </td></tr></table>
				    
     </div>
     <input type="hidden" id="setResult" name="setResult" value="1"/>
	</body>

	<script type="text/javascript">
	j(function(){
	    j('#tableList').flexigrid({
	    		height: 240,width:900,
	    		title: '查询列表'});
	});
	function onChange(setResult) {
		$("setResult").value = setResult;
	}
	function set(brNo,setResult) {
		if(set!='0'){
			for(var i=0;i<$("set").length;i++){
				if($("set").options[i].value==setResult){
					$("set").selectedIndex=i;break;}
			}
	    }
		var content = document.getElementById("data").innerHTML;
		art.dialog({
      	    title:'请选择定价策略',
  		    content: content,
		    okVal:'保存',
  		    ok: function () {
			    doSet(brNo);
  		        return true;
  		    }
   	    });
	}
	function doSet(brNo) {
		if (brNo != 'close') {
			var url = "<%=request.getContextPath()%>/DJCLPZ_save.action";
			new Ajax.Request(url, {
				method : 'post',
				parameters : {
				     brNo : brNo, setResult : $("setResult").value
				},
				onSuccess : function() {
			    	   art.dialog({
		               	    title:'成功',
		           		    icon: 'succeed',
		           		    content: '保存成功！',
		           		    ok: function () {
		           		    	ok();
		           		        return true;
		           		    }
		            	 });
			   }
		  });
		}
	}
	function ok() {
		window.location.reload();
	}
    </script>
</html>
