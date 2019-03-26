<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@page	import="java.util.ArrayList,java.util.List,com.dhcc.ftp.util.*"%>
<%@page import="com.dhcc.ftp.entity.FtpEmpInfo"%>
<%@page import="java.util.*"%>
	<head>
		<title>员工数据列表</title>
		<jsp:include page="../commonJs.jsp" />
	</head>
	<body>
		<form id="form1" name="form1" method="get" action="">
			<%
				PageUtil empInfoUtil = (PageUtil) request.getAttribute("empInfoUtil");
				List<FtpEmpInfo> list = (List<FtpEmpInfo>)empInfoUtil.getList();
			%> 
			<table width="750" id="tableList" class="table" align="center">
			 <tr>
				    <th width="45">
					   选择
				    </th>
				    <th width="45">
						序号
					</th>
					<th width="90">
						员工编号
					</th>
					<th width="100">
						员工姓名
					</td>
					<th width="300">
						所属机构
					</th>
					<th width="50">
						员工状态
					</th>
				</tr>
					<%
					int i = 1;
					for (FtpEmpInfo ftpEmpInfo : list) {
				     %>
					<tr>
						<td align="center" width="45">
						<input type="radio" onclick="doChoose('<%=ftpEmpInfo.getEmpNo()%>','<%=ftpEmpInfo.getEmpName()%>','<%=ftpEmpInfo.getBrMst()==null?"":ftpEmpInfo.getBrMst().getBrName()+"["+ftpEmpInfo.getBrMst().getBrNo()+"]"%>')" />
						</td>
						<td align="center" width="45">
							<%=i++%>
						</td>
						<td align="center" width="90">
							<%=ftpEmpInfo.getEmpNo()%>
						</td>
						<td align="center" width="100">
							<%=ftpEmpInfo.getEmpName()%>
						</td>
						<td align="center" width="300">
							<%=ftpEmpInfo.getBrMst()==null?"":ftpEmpInfo.getBrMst().getBrName()+"["+ftpEmpInfo.getBrMst().getBrNo()+"]"%>
						</td>
						<td align="center" width="80">
						<%if(!CastUtil.trimNull(ftpEmpInfo.getEmpStatus()).equals("")){
									if(ftpEmpInfo.getEmpStatus().equals("1")){
										out.write("正常");
									}else if(ftpEmpInfo.getEmpStatus().equals("2")){
										out.write("离职");
									}
								}%>
						</td>
					</tr>
					<%
					}
				%>
				</table>
			<table border="0" width="600" class="tb1" align="center">
				<tr>
					<td align="right"><%=empInfoUtil.getPageLine()%></td>
				</tr>
			</table>
		</form>

		<script language="javascript">
		function doChoose(empNo, empName, brInfos) {
			parent.art.dialog.data('empNo', empNo);// 存储数据
			parent.art.dialog.data('empName', empName);// 存储数据
			parent.art.dialog.data('brInfos', brInfos);// 存储数据
			parent.art.dialog.close();
		}
        function changeRate(){
           for(var i=0;i<<%=list.size()%>;i++){
              var check1 = document.getElementById("check"+i);
              var rate = document.getElementById("rate"+i);
              if(check1.checked){
                rate.style.color="#000000";
                rate.removeAttribute("disabled"); 
              }else{
                if(rate.value==""){
                    rate.disabled="disabled";
                }else{
                    rate.style.color="#FFFFFF";
                }
            }
           }
        }

	    function checkAll() {
	    	var selectFlags = document.getElementsByName("checkbox");
	    	for (var i=0; i<selectFlags.length; i++) {
	    		selectFlags[i].checked = window.event.srcElement.checked;//通过单击的按钮判断是选中还是未选
	    		if(window.event.srcElement.checked){
		    		changeRate();
	    		}else{
		    		changeRate();
	    		}
	    	}
	    }
	    
		function doSave(){
			var url="YGZHGL_save.action";
			var o = document.getElementsByName("checkbox");
			var r = document.getElementsByName("rate");
			alert(r[0].value);
			var m =0.0;
			var empNo="";
			var rate="";
			for(var i=0;i<o.length;i++){
			   if(o[i].checked){
			    	empNo +=o[i].value+"@";
			    	
			    	if(r[i]==null||r[i].value==""){
			    		art.dialog({
		              	    title:'提示',
		          		    icon: 'error',
		          		    content: '请输入比率！',
		          		    cancel:true
		           	 });
						return;
			    	}else{
			    	    m+=parseFloat(r[i].value,10).toFixed(5)/100.0;//10进制，保留5位小数
			    	    rate +=(parseFloat(r[i].value,10).toFixed(5)/100.0)+"@";
			    	}
			   }
			}
			
			if (empNo==""){
				art.dialog({
              	    title:'失败',
          		    icon: 'error',
          		    content: '请选择一项！',
          		    cancel:true
           	 });
				return;
			}
			if (m<0.00||m>1.00){
				 art.dialog({
	              	    title:'失败',
	          		    icon: 'error',
	          		    content: '您输入的比率不足100%或超额！',
	          		    cancel:true
	           	 });
				return;
			}
	           new Ajax.Request( 
	           url, 
	           {  
	             method: 'post',   
	             parameters: {
	        	   empNo:empNo,
	               rate:rate,
	               t:new Date().getTime()
	            },
	             onSuccess: function() 
	             {
		    	   art.dialog({
	              	    title:'成功',
	          		    icon: 'succeed',
	          		    content: '保存成功！',
	          		    ok: function () {
	          		    	//ok();
	          		        return true;
	          		    }
	           	 });
		        }
	          }
	       );
	           
		}
		function ok(){
		    window.parent.parent.frames.downframe.location.reload();
		    parent.parent.parent.parent.parent.openNewDiv();
		}
<%--		function doCancle()--%>
<%--		{--%>
<%--		   var o = document.getElementsByName("checkbox");--%>
<%--		   for(var i=0;i<o.length;i++){--%>
<%--		       if(o[i].checked){--%>
<%--		    	   o[i].checked=false;--%>
<%--		       }--%>
<%--		       changeRate();--%>
<%--		       --%>
<%--		   }--%>
<%--		}--%>
		function doBack(){
			window.parent.parent.location.reload();
		}
		function close(){
			window.location.reload();
		}

</script>
	</body>
</html>
