
<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.*,com.dhcc.ftp.util.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>	
<jsp:include page="commonJs.jsp" />
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		
<title>贷款上浮比例调整</title>
<style type="text/css">
</style>
</head>
<% 
 
 	PageUtil pageUtil =(PageUtil)request.getAttribute("ftpDkSfblUtil"); 
 	List<Ftp1DkSfblAdjust> list = pageUtil.getList(); 
%>
<body>
<div class="cr_header">
			当前位置：期限匹配->定价参数设置->贷款上浮比例调整
</div>
<div align="center"  style="text-align: center; margin-top: 20px; margin-left: 30px;" >
<form name="form1" method="post">
             <table  border="0" cellspacing="0" cellpadding="0" id="tableList"    >	
	          <thead> 
	           <tr >
					<th width="120">
						操作
					</th>
                  <th width="150">
						最小上浮比例(%) 
					</th>
					<th width="150">
						最大上浮比例(%)[含]
					</th>
					<th width="150">
						调整值(%)
					</th>
	       </tr>
	       </thead>
	       <tbody>
	<% 
	 for (int i = 0; i < list.size(); i++){
	   if(list.size()>0){
	 %>
			 <tr>
		     	<td align="center"><a href="javascript:doEdit('<%=CastUtil.trimNull(list.get(i).getId())%>')">编辑</a></td>
		        <td align="center"><%=list.get(i).getMinPercent()==null?0:CommonFunctions.doubleFormat(list.get(i).getMinPercent(),2)%></td>
		     	<td align="center">
		     		<%
		     		String ve=list.get(i).getMaxPercent()==null?" 0" : CommonFunctions.doubleFormat(list.get(i).getMaxPercent(),2);
		     		if(list.get(i).getMaxPercent()==9999999999.99){
		     			out.write("+∞");
		     		}else{
		     			out.write(ve);
		     		}%>
		     	</td>
		     	<td align="center"><%= CommonFunctions.doubleFormat(list.get(i).getAdjustValue()*100,1) %></td>
			</tr>
	<%} else {} } %>
	</tbody>
	</table>
		</form>
</div>
</body>
<script type="text/javascript">	
j(function(){
    j('#tableList').flexigrid({
    		height: 230,width:1000,
    		theFirstIsShown: false,//第一列在是否显示下拉列表中是否出现(某些列表第一列是全选按钮)
    		title: '数据列表',
    		buttons : []});
});

var selectFlags = document.getElementsByName("checkbox");
function doCheck() {
	var selectFlags = document.getElementsByName("checkbox");
	for (var i=0; i<selectFlags.length; i++) {
		selectFlags[i].checked = window.event.srcElement.checked;//通过单击的按钮判断是选中还是未选
	}
}
function do_Add(){}

function doEdit(id){
	art.dialog.open('<%=request.getContextPath()%>/FtpDksfblAdjust_getDetail.action?id='+id+'&rand='+Math.random(), {
	    title: '编辑',
	    width: 500,
	    height:200 
	});
}
function do_Del() {}   
function ok(){
   window.location.reload();
}
</script>
</html>
