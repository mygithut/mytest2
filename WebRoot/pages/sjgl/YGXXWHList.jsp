<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page	import="java.util.ArrayList,java.util.List,com.dhcc.ftp.util.*"%>
<%@page import="com.dhcc.ftp.entity.FtpEmpInfo"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>员工数据维护</title>
		<jsp:include page="../commonJs.jsp" />
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="" style="display: none;">
			<%
				PageUtil frpEmpInfoUtil = (PageUtil) request.getAttribute("frpEmpInfoUtil");
				List<FtpEmpInfo> frpEmpInfoList = frpEmpInfoUtil.getList();
				
				if (frpEmpInfoList == null) {
					out.print("无该类型对应的数据！");
				} else {
			%>
				<table align="center" id="tableList" >
					<thead>
						<tr>
							<th width="80">
								<input type="checkbox" name="all" value="checkbox"	onClick="checkAll()" />
								全选
							</th>
						     <th width="80">
								操作
						    </th>
							<th width="80">
								员工编号
							</th>
							<th width="150">
								员工姓名
							</th>
							<th width="270">
								所属机构
							</th>
							<th width="80">
								岗位级别
						    </th>
						    <th width="80">
								现任岗位
						    </th>
						    <th width="100">
								出生日期
						    </th>
						    <th width="60">
								性别
						    </th>
						    <th width="120">
								员工状态
						    </th>
						</tr>
					</thead>
					<%
					for (FtpEmpInfo po : frpEmpInfoList) {
				%>
					<tbody>
						<tr>
							<td align="center">
								<input type="checkbox" name="checkbox" value="<%=CastUtil.trimNull(po.getEmpNo())%>" />
							</td>
							<td align="center">
								<a href="javascript:doEdit('<%=CastUtil.trimNull(po.getEmpNo())%>')">编辑</a>
							</td>
							<td align="center">
								<%=CastUtil.trimNull(po.getEmpNo())%>
							</td>
							<td align="center">
								<%=CastUtil.trimNull(po.getEmpName())%>
							</td>
							<td align="center">
								<%=po.getBrMst()==null?"":CastUtil.trimNull(po.getBrMst().getBrName()+"["+po.getBrMst().getBrNo()+"]")%>
							</td>
							<td align="center">
								<%if(!CastUtil.trimNull(po.getEmpLvl()).equals("")){
									if(po.getEmpLvl().equals("1")){
										out.write("高层");
									}else if(po.getEmpLvl().equals("2")){
										out.write("中层");
									}else if(po.getEmpLvl().equals("3")){
										out.write("底层");
									}
								}%>
							</td>
							<td align="center">
								<%=CastUtil.trimNull(po.getPostNo().equals("4")?"客户经理":"非客户经理")%>
							</td>
							<td align="center">
								<%=CastUtil.trimNull(po.getBirthdaydate())%>
							</td>
							<td align="center">
								<%if(!CastUtil.trimNull(po.getSex()).equals("")){
									if(po.getSex().equals("1")){
										out.write("男");
									}else if(po.getSex().equals("2")){
										out.write("女");
									}
								}%>
							</td>
							<td align="center">
								<%if(!CastUtil.trimNull(po.getEmpStatus()).equals("")){
									if(po.getEmpStatus().equals("1")){
										out.write("正常");
									}else if(po.getEmpStatus().equals("2")){
										out.write("离职");
									}
								}%>
							</td>
						</tr>
					</tbody>
					<%
					}
					}
				%>
				</table>
			<table border="0" width="100%" class="tb1" align="center">
				<tr>
					<td align="right"><%=frpEmpInfoUtil.getPageLine()%></td>
				</tr>
			</table>
		</form>
		<script language="javascript">
	    j(function(){
	      document.getElementById("form1").style.display="block";
		    j('#tableList').flexigrid({
		    		height: 230,width:1080,
		    		theFirstIsShown: false,//第一列在是否显示下拉列表中是否出现(某些列表第一列是全选按钮)
		    		title: '数据列表',
		    		buttons : [
		    			   		{name: '新增', bclass: 'add', onpress : doAdd},
		    			   		{name: '删除', bclass: 'delete', onpress : doDelete}
		    			   		]});
	    });
	    function checkAll() {
	    	var selectFlags = document.getElementsByName("checkbox");
	    	for (var i=0; i<selectFlags.length; i++) {
	    		selectFlags[i].checked = window.event.srcElement.checked;//通过单击的按钮判断是选中还是未选
	    	}
	    }
		function doAdd(){
			art.dialog.open('<%=request.getContextPath()%>/pages/sjgl/YGXXWHDetail.jsp?Rnd='+Math.random(), {
			    title: '添加员工信息',
			    width: 650,
			    height:380
			});
		}
		function doEdit(empId){
			art.dialog.open('<%=request.getContextPath()%>/YGXXWH_Query.action?empNo='+empId+'&Rnd='+Math.random(), {
			    title: '编辑员工信息',
			    width: 650,
			    height:380
			});
		}
		function doDelete(){
		   var o;
		   o = document.getElementsByName("checkbox");
		   var m =0;
		   var empId="";
		   for(var i=0;i<o.length;i++){
		       if(o[i].checked){
		    	   empId +=o[i].value+",";
		       }
		   }
		   if (empId==""){
			  alert("请选择一项！");
			  return false;
		   }else{
			   art.dialog({
			        title:'删除',
			    	icon: 'question',
			        content: '确定删除?',
			        ok: function () {
				     var url = "<%=request.getContextPath()%>/YGXXWH_del.action";
			         new Ajax.Request( 
			         url, 
			           {   
			             method: 'post',   
			             parameters: {empNo:empId,t:new Date().getTime()},
			             onSuccess: function(val){  
				             var img,content;
				             if(val.responseText=="ok"){
				            	 img='succeed';
				            	 content='删除成功！';
					         }else{
					        	 img='error';
				            	 content="编号为 "+val.responseText+" 的员工已与帐号关联,无法删除 ";
						     }
					    	   art.dialog({
				               	    title:'提示',
				           		    icon: img,
				           		    content: content,
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
		function close(){
			window.location.reload();
		}

</script>
	</body>
</html>
