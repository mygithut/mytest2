<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.entity.FtpPoolInfo,com.dhcc.ftp.entity.FtpBusinessPrdt" errorPage=""%>
<html>
	<head>
		<jsp:include page="commonJs.jsp" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<title>双资金池->双资金池配置</title>
	</head>
	<%
	List<FtpPoolInfo> ftpPoolInfoList =(List<FtpPoolInfo>)request.getAttribute("ftpPoolInfoList");
    %>
	<body>
		<form name="chooseform" method="post">
		<table width="100%" align="center" class="table">
			<tr>
				<td class="middle_header" colspan="3">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">配置</font>
				</td>
			</tr>
		    <tr><td width="10%">
			  <table border="0" cellspacing="0" cellpadding="0"
				align="center" class="table" id="tbColor">
				<tr>
				    <td class="middle_header" >
					     <font style="padding-left:10px">资金池数量</font>
				       </td>
				</tr>
		        <tr>
				    <td align="center">
				        <select id="poolNum" name="poolNum" style="width:70">
				           <option value="2">2</option>
				        </select>
			  </td>
		      
		    </tr>
	    </table>
	    </td>
	    <td width="45%">
		          <table width="100%" align="left" class="table" id="poolTable">
		            <tr>
				       <td class="middle_header" colspan="5">
					     <font style="padding-left:10px">资金池配置</font>
				       </td>
			        </tr>
			        <tr>
			           <td align="center">编号</td>
			           <td align="center">资金池名称</td>
			           <td align="center">资产类型</td>
			           <td align="center">包含业务对象</td>
			        </tr>
			        <%if(ftpPoolInfoList!=null && ftpPoolInfoList.size() > 0){
			        	for(FtpPoolInfo ftpPoolInfo : ftpPoolInfoList){%>
			        <tr id="<%=ftpPoolInfo.getPoolNo() %>">
			        	<td align="center"><input type="radio" name="poolNo" id="poolNo<%=ftpPoolInfo.getPoolNo() %>" value="<%=ftpPoolInfo.getPoolNo() %>" readonly="readonly" size="15" onclick="doRefresh(<%=ftpPoolInfo.getPoolNo() %>)"/>&nbsp;<%=ftpPoolInfo.getPoolNo() %></td>
			        	<td align="center"><input type="text" name="poolName" id="poolName<%=ftpPoolInfo.getPoolNo() %>" value="<%=ftpPoolInfo.getPoolName() %>"/></td>
			        	<td align="center"><%=ftpPoolInfo.getPoolType().equals("1") ? "资产" : "负债" %>
			        	<input type="hidden" name="poolType" id="poolType<%=ftpPoolInfo.getPoolNo() %>" value="<%=ftpPoolInfo.getPoolType() %>"/>
			        	</td>
			        	<td align="center"><input type="text" name="contentObject" id="contentObject<%=ftpPoolInfo.getPoolNo() %>" value="<%=ftpPoolInfo.getContentObject() %>" readonly="readonly" /></td>
			        </tr>
			        <% }} %>
			        
		          </table>
	    </td>
	    <td width="45%">
	    <iframe src="" id="rightFrame" width="100%" height="250" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto" align="middle"></iframe> 
	    </td>
	    </tr>
		<tr>
		  <td colspan="3" align="center">
		   <input type="button" value="保&nbsp;&nbsp;存" onclick="doSave()" class="button">
		  </td>
		</tr>
		</form>
	</body>

	<script type="text/javascript">
	//如果是修改
	<%if(ftpPoolInfoList!=null && ftpPoolInfoList.size() > 0){
		//循环设置资金池资产类型的选中状态
		for(FtpPoolInfo ftpPoolInfo : ftpPoolInfoList){%>
		   for(var i=0;i<$("poolType<%=ftpPoolInfo.getPoolNo()%>").length;i++){
			   if($("poolType<%=ftpPoolInfo.getPoolNo()%>").options[i].value=="<%=ftpPoolInfo.getPoolType()%>"){
				   $("poolType<%=ftpPoolInfo.getPoolNo()%>").selectedIndex=i;break;}
		   } 
		<%}
	%>
    <%}else{%>//动态添加两行
  //得到table对象
    var poolTable = document.getElementById("poolTable");
    addRow(poolTable, '201', '资产','1');
    addRow(poolTable, '202', '负债','2');
    <%}%>
    //资金池类型selet响应函数
    function doChange(poolNo) {
        if($('poolNo'+poolNo).checked == false) $('poolNo'+poolNo).checked = true;//让按钮设置为选中
        document.getElementById('contentObject'+poolNo).value = "";
        if($('poolType'+poolNo).value == '') {
        	window.frames.rightFrame.location.href = "<%=request.getContextPath()%>/SZJCPZ_.action";
        	return;
        }
        doRefresh(poolNo);
    }
    //点击不同的资金池，刷新右侧frame根据条件显示不同的产品
    function doRefresh(poolNo) {
    	var poolType = $('poolType'+poolNo).value;
        if (poolType == '') {
        	$('poolType'+poolNo).selectedIndex=1;
            //alert("请先选择“资金池资产类型”,才能进行产品选择！！");
           // return false;
        }
        //获取已经被选中的产品，在资金池中不能重复出现
        var contentObjects = document.getElementsByName("contentObject");
        var outContentObject = '';
        for(var i = 0; i < contentObjects.length; i++) {
        	if(contentObjects[i].id != 'contentObject'+poolNo && contentObjects[i].value != '') 
            	outContentObject += contentObjects[i].value + '+';
        }
        outContentObject = outContentObject.substr(0, outContentObject.length-1);
       // var reg = new RegExp("+","g");
        outContentObject = outContentObject.replace(new RegExp(/\+/g),',');
        window.frames.rightFrame.location.href = "<%=request.getContextPath()%>/SZJCPZ_getPrdt.action?outContentObject="+outContentObject+"&poolNo="+poolNo+"&poolType="+poolType;
    }
    function doSave() {
        if($('poolNum').value == '') {
            alert("您还未进行任何配置，无法保存！！");
            return false;
        }
        var poolNos = new Array();
        var poolNames = new Array();
        var poolTypes = new Array();
        var contentObjects = new Array();
        var poolNo = document.getElementsByName("poolNo");
        var poolType = document.getElementsByName("poolType");
        var poolName = document.getElementsByName("poolName");
        var contentObject = document.getElementsByName("contentObject");
        for(var i = 0; i < poolNo.length; i++) {
        	poolNos.push(poolNo[i].value);
        }
        for(var i = 0; i < poolName.length; i++) {
            if(poolName[i].value == '') {
                alert('编号为“'+poolName[i].id.substr(8, 3)+'”的资金池名称为空，请输入！！');
                return false;
            }else {
            	poolNames.push(poolName[i].value);
            }
        }
        for(var i = 0; i < poolType.length; i++) {
            if(poolType[i].value == '') {
                alert('编号为“'+poolType[i].id.substr(8, 3)+'”的资金池资产类型未配置，请配置！！');
                return false;
            }else {
            	poolTypes.push(poolType[i].value);
            }
        }
        for(var i = 0; i < contentObject.length; i++) {
            if(contentObject[i].value == '') {
                alert('编号为“'+contentObject[i].id.substr(13, 3)+'”的资金池包含业务对象未配置，请配置！！');
                return false;
            }else {
            	contentObjects.push(contentObject[i].value);
            }
        }
        var brNo = window.parent.document.getElementById("brNo").value;
        var url = "<%=request.getContextPath()%>/SZJCPZ_save.action";
		new Ajax.Request(url, {
			method : 'post',
			parameters : {
			brNo : brNo, poolNos : poolNos, poolNames : poolNames, poolTypes : poolTypes, contentObjects : contentObjects
			},
			onSuccess : function() {
		    	   art.dialog({
	              	    title:'成功',
	          		    icon: 'succeed',
	          		    content: '保存成功！',
	           		    cancelVal: '关闭',
	           		    cancel: true
	           	 });
			}
		});
    }
  //添加行
    function addRow(table, i, poolType,poolType2){
        //创建资金池编号
        var lastRow = table.rows[table.rows.length-1];
        var newRow = lastRow.cloneNode(true);
        table.tBodies[0].appendChild(newRow);
        newRow.setAttribute("id", i);
        // newRow.setAttribute("onclick",new Function("doChange("+i+")"));
        newRow.cells[0].innerHTML="<input type=\"radio\" name=\"poolNo\" id=\"poolNo"+i+"\" value=\""+i+"\" readonly=\"readonly\" size=\"15\" onclick = doRefresh("+i+") />&nbsp;"+i+"";
        newRow.cells[1].innerHTML="<input type=\"text\" name=\"poolName\" id=\"poolName"+i+"\" value=\""+poolType+"资金池\" readonly=\"readonly\" />";
        newRow.cells[2].innerHTML=""+poolType+"<input type=\"hidden\" name=\"poolType\" id=\"poolType"+i+"\" value=\""+poolType2+"\"/>";
        newRow.cells[3].innerHTML="<input type=\"text\" name=\"contentObject\" id=\"contentObject"+i+"\" value=\"\" readonly=\"readonly\" />";
        newRow.cells[0].setAttribute("align", "center");
        newRow.cells[1].setAttribute("align", "center");
        newRow.cells[2].setAttribute("align", "center");
        newRow.cells[3].setAttribute("align", "center");
  }
    </script>
</html>
