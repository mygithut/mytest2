<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.entity.FtpPoolInfo,com.dhcc.ftp.entity.FtpBusinessPrdt" errorPage=""%>
<html>
	<head>
		<jsp:include page="commonJs.jsp" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<title>多资金池->多资金池配置</title>
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
				        <select id="poolNum" name="poolNum" onchange="setPoolNum()">
				           <option value="0">--请选择--</option>
				           <%for(int i = 1; i < 51; i++) { %>
				           <option value="<%=i %>"><%=i %></option>
				           <%} %>
				        </select>
			  </td>
		      
		    </tr>
		    <tr style="display:none" id="addAndDelRow" >
			          <td align="center" >
			              <input type="button" value="添加行" onclick="doAddRow()" class="button">
			          </td>
			        </tr>
			        
	    </table>
	    </td>
	    <td width="50%">
	    <DIV id="Layer1" style="OVERFLOW: auto;  height=300;width="90%">
		          <table width="100%" align="left" class="table" style="display:none" id="poolTable">
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
			           <td align="center">操作</td>
			        </tr>
			        <%if(ftpPoolInfoList!=null && ftpPoolInfoList.size() > 0){
			        	for(FtpPoolInfo ftpPoolInfo : ftpPoolInfoList){%>
			        <tr id="<%=ftpPoolInfo.getPoolNo() %>">
			        	<td align="center"><input type="radio" name="poolNo" id="poolNo<%=ftpPoolInfo.getPoolNo() %>" value="<%=ftpPoolInfo.getPoolNo() %>" readonly="readonly" size="15" onclick="doRefresh(<%=ftpPoolInfo.getPoolNo() %>)"/>&nbsp;<%=ftpPoolInfo.getPoolNo() %></td>
			        	<td align="center"><input type="text" name="poolName" id="poolName<%=ftpPoolInfo.getPoolNo() %>" value="<%=ftpPoolInfo.getPoolName() %>"/></td>
			        	<td align="center"><select name="poolType" id="poolType<%=ftpPoolInfo.getPoolNo() %>" onChange=doChange(<%=ftpPoolInfo.getPoolNo() %>)>
			        	      <option value="">请选择</option>
                              <option value="1">资产</option>
                              <option value="2">负债</option>
			        	</select></td>
			        	<td align="center"><input type="text" name="contentObject" id="contentObject<%=ftpPoolInfo.getPoolNo() %>" value="<%=ftpPoolInfo.getContentObject() %>" readonly="readonly" /></td>
			            <td align="center"><input type="button" class="button" value="删除行" onclick="doDelRow(<%=ftpPoolInfo.getPoolNo() %>)"/></td>
			        </tr>
			        <% }} %>
		          </table>
		          </DIV>
	    </td>
	    <td width="40%">
	    <iframe src="" id="rightFrame" width="100%" height="100%" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="auto" align="middle"></iframe> 
	    </td>
	    </tr>
		<tr>
		  <td colspan="3" align="center">
		   <input type="button" value="保&nbsp;&nbsp;存" onclick="doSave()" class="button">
		  </td>
		</tr>
		</table>
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
	for(var i=0;i<$("poolNum").length;i++){
		if($("poolNum").options[i].value=="<%=ftpPoolInfoList.size()%>"){
			$("poolNum").selectedIndex=i;break;}
	} 
	$("poolNum").disabled="disabled";
	document.getElementById("poolTable").style.display = "block";
    document.getElementById("addAndDelRow").style.display = "block";
    <%}%>
    //选中资金池数量后，右侧自动添加poolNum行资金池信息
    function setPoolNum() {
    	document.getElementById("poolTable").style.display = "block";
    	var poolNum = $("poolNum").value;
    	//得到table对象
        var poolTable = document.getElementById("poolTable");
        if(poolTable.rows.length > 3) deleteRow(poolTable);//如果改变数量，则先删除原先的行，再添加
    	for (var i = 1; i <= poolNum; i++) {
    		if(i < 10) 
    			addRow(poolTable, '30' + i);
            else 
            	addRow(poolTable, '3' + i);
        }
    }
    //"添加行"按钮响应函数
    function doAddRow() {
        var poolNum = document.getElementById("poolNum").value;
        if(parseInt(poolNum)+1 > 50) {
	    	   art.dialog({
              	    title:'失败',
          		    icon: 'error',
          		    content: '资金池数量最多只能50个！',
          		    cancelVal: '关闭',
          		    cancel: true
           	 });
			return;
        }
    	//得到table对象
        var poolTable = document.getElementById("poolTable");
        var poolNos = document.getElementsByName("poolNo");
        var lastPoolNo = '300';
        if(poolNos.length > 0) lastPoolNo = poolNos[poolNos.length-1].value;//获取最大的poolNo
        addRow(poolTable, parseInt(lastPoolNo)+1);
        //将‘资金池数量’+1
        for(var i=0;i<$("poolNum").length;i++){
    		if($("poolNum").options[i].value==parseInt($("poolNum").value)+1){
    			$("poolNum").selectedIndex=i;break;}
    	} 
    }
    //"删除行"按钮响应函数
    function doDelRow(poolNo) {
    	var prdtList_isToBeDisappear='0';//右面产品列表是否消失---当删除的行是被选中的行时，右面产品列表才消失
        if($('poolNo'+poolNo).checked == true){//必须在删除行操作之前判断 
        	prdtList_isToBeDisappear="1";
        }
    	var poolTable = document.getElementById("poolTable");
    	for(var j = 2;j < poolTable.rows.length; j++) {
        	if(poolTable.rows[j].getAttribute("id") == poolNo) {
        		poolTable.deleteRow(j);
        		break;
        	}
    	}
    	//将资金池数量-1
        for(var i=0;i<$("poolNum").length;i++){
    		if($("poolNum").options[i].value==parseInt($("poolNum").value)-1){
    			$("poolNum").selectedIndex=i;break;}
    	}
    	if(prdtList_isToBeDisappear == "1"){//当删除的行是被选中的行时，右面产品列表才消失  	 
    		window.frames.rightFrame.location.href = "<%=request.getContextPath()%>/DUOZJCPZ_.action";
    	}else{//否则刷新右面产品列表---刚被删除的资金池中的产品应当加入到右面产品列表
	    	//判断当前被选中的资金池编号
	    	var poolNos = document.getElementsByName("poolNo");
	        var select_poolNo = '';
	        for(var i = 0; i < poolNos.length; i++) {
	        	if(poolNos[i].checked == true){ 
	            	select_poolNo=poolNos[i].id.substring(6);
	            	break;
	            }
	        }
	        if(select_poolNo!=''){//如果之前有被选中的资金池行，才刷新右面产品列表
	        	doRefresh(select_poolNo);
	        }
    	}
    }
  //资金池类型select响应函数
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
        	$('poolType'+poolNo).selectedIndex=1;//如果没有选择‘资金池资产类型’，默认为‘资产类’
        	poolType='1';
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
        outContentObject = outContentObject.replace(new RegExp(/\+/g),',');//将+替换为,
        window.frames.rightFrame.location.href = "<%=request.getContextPath()%>/SZJCPZ_getPrdt.action?outContentObject="+outContentObject+"&poolNo="+poolNo+"&poolType="+poolType;
    }
    function doSave() {
        if($('poolNum').value == '0' || $('poolNum').value == '') {
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
        var url = "<%=request.getContextPath()%>/DUOZJCPZ_save.action";
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
	           		    ok: function () {
	           		    	ok();
	           		        return true;
	           		    }
	            	 });
			}
		});
    }
  //页面表格基础功能：添加行
    function addRow(table, i){
        //创建资金池编号
        var lastRow = table.rows[table.rows.length-1];
        var newRow = lastRow.cloneNode(true);
        table.tBodies[0].appendChild(newRow);
        newRow.setAttribute("id", i);
        // newRow.setAttribute("onclick",new Function("doChange("+i+")"));
        newRow.cells[0].innerHTML="<input type=\"radio\" name=\"poolNo\" id=\"poolNo"+i+"\" value=\""+i+"\" readonly=\"readonly\" size=\"15\" onclick = doRefresh("+i+") />&nbsp;"+i+"";
        newRow.cells[1].innerHTML="<input type=\"text\" name=\"poolName\" id=\"poolName"+i+"\" value=\"\" />";
        newRow.cells[2].innerHTML="<select name=\"poolType\" id=\"poolType"+i+"\" onChange=doChange("+i+")>"+
                                  "<option value=\"\">请选择</option>"+
                                  "<option value=\"1\">资产</option>"+
                                  "<option value=\"2\">负债</option>"+
                                  "</select>";
        newRow.cells[3].innerHTML="<input type=\"text\" name=\"contentObject\" id=\"contentObject"+i+"\" value=\"\" readonly=\"readonly\" />";
        newRow.cells[4].innerHTML="<input type=\"button\" class=\"button\" value=\"删除行\" onclick=\"doDelRow("+i+")\"/>";
        newRow.cells[0].setAttribute("align", "center");
        newRow.cells[1].setAttribute("align", "center");
        newRow.cells[2].setAttribute("align", "center");
        newRow.cells[3].setAttribute("align", "center");
        newRow.cells[4].setAttribute("align", "center");
        }
    //删除行
    function deleteRow(table){
    	for(var i = table.rows.length - 1; i>2;i--){
    	    table.deleteRow(i);
    	}
    }
    function ok () {
		window.location.href = "<%=request.getContextPath()%>/DUOZJCPZ_list.action?brNo="+window.parent.document.getElementById("brNo").value;
	}
    </script>
</html>
