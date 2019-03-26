<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page
	import="java.sql.*,java.util.*,com.dhcc.ftp.entity.*,com.dhcc.ftp.util.*"%>
<html>
	<head>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/themes/green/css/core.css"
			type="text/css">
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/css/inpage.css"
			type="text/css">
		<jsp:include page="../commonJs.jsp"></jsp:include>
		<title>员工账户管理-关联操作</title>
	</head>
	<%
	String[][] relatedEmp = (String[][])request.getAttribute("relatedEmps");
	String ac_id = (String)request.getAttribute("ac_id");
	String relType = (String)request.getAttribute("relType");
	String prdtNo = (String)request.getAttribute("prdtNo");
	Integer pcount = (Integer)request.getAttribute("pcount");
	if(null==pcount||pcount.equals("")){
	   pcount = 0;
	}
	%>
	<body>
		<form id="form1" name="form1" action="<%=request.getContextPath()%>/splcsz_modify.action?"	method="post">
			<table align="center" border="0">
				<tr>
					<td>
						<div align="center"><input type="button" value="添加员工" onclick="addFirstTr(this)" class="button" id="addButton"/></div>
						<table class="table" width="750" align="center" id="editTable">
							<tr>
								<th width="100" align="center">
									员工编号
								</th>
								<th width="100" align="center">
									员工名称
								</th>
								<th width="250" align="center">
									所属机构
								</th>
								<th width="100" align="center">
									金额(元)
								</th>
								<th width="80" align="center">
								             备注
								</th>
								<th width="50" align="center">
									操作
								</th>
							</tr>

							<%
							if(relatedEmp!=null){ for(int i=0;i<relatedEmp.length;i++){
					  	%>
							<tr>
								<td align="center" name="flowCode" width="100">
									<%=relatedEmp[i][0]%>
								</td>
								<td align="center" width="100">
									<%=relatedEmp[i][1] %>
								</td>
								<td align="center" width="250">
									<%=relatedEmp[i][3]%><%if(null==relatedEmp[i][3]||relatedEmp[i][3].equals("")){%><%}else{%>[<%=relatedEmp[i][2]%>]<%} %>
								</td>
								<td align="center" width="100">
									<input type="text" value="<%=relatedEmp[i][4]%>" id="rate" name="rate" size="10" onkeyup="value=value.replace(/[^\d\.]/g,'')">
								</td>
								<td>
								    <%if(i==0){ %>
								        固定金额+余额
								    <%}else{ %>
								        固定金额
								    <%} %>
								</td>
								<td align="center">
									<input type="button" value="删除行" onclick="del(this)"
										class="button" />
								</td>
							</tr>
							<%}%>
						<%} %>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<input type="button" class="button" onClick="doSubmit()"
							value="保&nbsp;&nbsp;存">
						<input type="button" name="Submit1" class="button"
							onClick="javascript:window.parent.location.reload();" value="重&nbsp;&nbsp;置">
					</td>
				</tr>
			</table>
	</form>
	<script type="text/javascript"> 
	var count =parseInt(<%=pcount%>); 
	//删除一行
	function del(t){
		j(t).parent().parent().remove();
		count = document.getElementById("editTable").rows.length-1;
	}
    //新增时添加第一行数据
    function addFirstTr(t){
    	var table = document.getElementById("editTable");
    	//获取已经添加的员工
	  	var row = table.rows;
	  	if(row.length > 10) {
	  		art.dialog({
          	    title:'提示',
      		    icon: 'error',
      		    content: '关联员工最多不能超过10位！',
      		    cancel:true
       	    });
       	    return;
	  	}
	  	var relatedEmpNos="";
	  	for(var i=1; i<row.length;i++){
	  		relatedEmpNos += row[i].cells[0].innerHTML+",";
	  	}
	  	art.dialog.open('<%=request.getContextPath()%>/pages/sjgl/ygzhglEmpSearch.jsp?relatedEmpNos='+relatedEmpNos, {
		    title: '员工列表',
		    width: 650,
		    height:300,
		    close: function (){
		    
	    	var empNo = art.dialog.data('empNo');
    		var empName = art.dialog.data('empName');
    		var brInfos = art.dialog.data('brInfos');
    		if(empNo!=null&&empNo!=""){
        		count++;
    			var newRow = table.insertRow(row.length); 
    	        var td1 = newRow.insertCell(0);  
    	  	    td1.innerHTML = empNo;   
    	  	    td1.setAttribute("align","center");
    	  	    td1.setAttribute("name","flowCode");
    	        var td2 = newRow.insertCell(1);  
    	  	    td2.innerHTML = empName;  
    	  	    td2.setAttribute("align","center"); 
    	        var td3 = newRow.insertCell(2);  
    	  	    td3.innerHTML = brInfos;  
    	  	    td3.setAttribute("align","center"); 
    	        var td4 = newRow.insertCell(3); 
    	  	    td4.setAttribute("align","center"); 
    	  	    td4.innerHTML = "<input type=\"text\" value=\"\"  size=\"10\" id=\"rate\" name=\"rate\" onblur=\"isInt(this,'金额(元)')\"/>";   
    	  	    var td5 = newRow.insertCell(4); 
  	  	        td5.setAttribute("align","center"); 
                if(count==1){
  	  	            td5.innerHTML = "固定金额+余额";   
                }else{
                	td5.innerHTML = "固定金额";   
                }
      	     	var td6 = newRow.insertCell(5); 
  	  	        td6.setAttribute("align","center"); 
  	  	        td6.innerHTML = "<input type=\"button\" value=\"删除行\" onclick=\"del(this)\" class=\"button\" />";   
  	            art.dialog.data('empNo',"");
    		    art.dialog.data('empName',"");
    		    art.dialog.data('brInfos',"");
	    	}
	     }
		});	
        
    }
    function doSubmit(url) {
		var empNo="";
		var rate="";
		var table=document.getElementById("editTable");
		if(table.rows.length == 1) {
			art.dialog({
          	    title:'提示',
      		    icon: 'error',
      		    content: '请添加行！',
      		    cancel:true
       	    });
		}
		var rates = document.getElementsByName("rate");
<%--		var sumRate = 0.0;--%>
		for(var m = 0; m < rates.length;m++) {
			if(rates[m].value==""){
	    		art.dialog({
              	    title:'提示',
          		    icon: 'error',
          		    content: '请输入比率！',
          		    cancel:true
           	    });
	           	return;
			}
			else {
				    rate+=rates[m].value+"@";
<%--				sumRate+=parseFloat(rates[m].value);--%>
<%--				rate += (parseFloat(rates[m].value)/100.0).toFixed(3)+"@";--%>
			}
		}
<%--		if (sumRate!=100){--%>
<%--			 art.dialog({--%>
<%--             	    title:'失败',--%>
<%--         		    icon: 'error',--%>
<%--         		    content: '您输入的比率不足或超过100%！',--%>
<%--         		    cancel:true--%>
<%--          	 });--%>
<%--			return;--%>
<%--		}--%>
		for (var i = 1; i < table.rows.length; i++)
		{
			var e = table.rows[i].cells[0].innerHTML;
	    	empNo += trim(e)+"@";
		}
     	var url="<%=request.getContextPath()%>/YGZHGL_save.action";
		new Ajax.Request(url, {
			method : 'post',
			parameters : {
			ac_id:'<%=ac_id%>',empNo:empNo,rate:rate,relType:'<%=relType%>',prdtNo:'<%=prdtNo%>'
			},
			onSuccess : function(res) {
			    debugger;
			    console.info(res.responseText);
			    if(res.responseText=='1'){
                    art.dialog({
                        title:'提示',
                        icon: 'succeed',
                        content: '保存成功！',
                        ok: function() {
                            parent.parent.parent.parent.parent.openNewDiv();
                            window.parent.parent.frames.downframe.location.reload();
                        }
                    });
				}else{
                    art.dialog({
                        title:'提示',
                        icon: 'warning',
                        content: '统计表报表正在更新，请稍后再试！',
                        ok: function() {
                            parent.parent.parent.parent.parent.openNewDiv();
                            window.parent.parent.frames.downframe.location.reload();
                        }
                    });
				}

			}
		});
	}	
    function trim(str){ //删除左右两端的空格
        return str.replace(/(^\s*)|(\s*$)/g, "");
    }
	</script>
	</body>
</html>