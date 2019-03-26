<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.entity.FtpPoolInfo,com.dhcc.ftp.entity.FtpBusinessPrdt" errorPage=""%>
<html>
	<head>
		<jsp:include page="commonJs.jsp" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<title>˫�ʽ��->˫�ʽ������</title>
	</head>
	<%
	List<FtpPoolInfo> ftpPoolInfoList =(List<FtpPoolInfo>)request.getAttribute("ftpPoolInfoList");
    %>
	<body>
		<form name="chooseform" method="post">
		<table width="100%" align="center" class="table">
			<tr>
				<td class="middle_header" colspan="3">
					<font style="padding-left:10px; color:#333; font-size:12px;font-weight:bold">����</font>
				</td>
			</tr>
		    <tr><td width="10%">
			  <table border="0" cellspacing="0" cellpadding="0"
				align="center" class="table" id="tbColor">
				<tr>
				    <td class="middle_header" >
					     <font style="padding-left:10px">�ʽ������</font>
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
					     <font style="padding-left:10px">�ʽ������</font>
				       </td>
			        </tr>
			        <tr>
			           <td align="center">���</td>
			           <td align="center">�ʽ������</td>
			           <td align="center">�ʲ�����</td>
			           <td align="center">����ҵ�����</td>
			        </tr>
			        <%if(ftpPoolInfoList!=null && ftpPoolInfoList.size() > 0){
			        	for(FtpPoolInfo ftpPoolInfo : ftpPoolInfoList){%>
			        <tr id="<%=ftpPoolInfo.getPoolNo() %>">
			        	<td align="center"><input type="radio" name="poolNo" id="poolNo<%=ftpPoolInfo.getPoolNo() %>" value="<%=ftpPoolInfo.getPoolNo() %>" readonly="readonly" size="15" onclick="doRefresh(<%=ftpPoolInfo.getPoolNo() %>)"/>&nbsp;<%=ftpPoolInfo.getPoolNo() %></td>
			        	<td align="center"><input type="text" name="poolName" id="poolName<%=ftpPoolInfo.getPoolNo() %>" value="<%=ftpPoolInfo.getPoolName() %>"/></td>
			        	<td align="center"><%=ftpPoolInfo.getPoolType().equals("1") ? "�ʲ�" : "��ծ" %>
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
		   <input type="button" value="��&nbsp;&nbsp;��" onclick="doSave()" class="button">
		  </td>
		</tr>
		</form>
	</body>

	<script type="text/javascript">
	//������޸�
	<%if(ftpPoolInfoList!=null && ftpPoolInfoList.size() > 0){
		//ѭ�������ʽ���ʲ����͵�ѡ��״̬
		for(FtpPoolInfo ftpPoolInfo : ftpPoolInfoList){%>
		   for(var i=0;i<$("poolType<%=ftpPoolInfo.getPoolNo()%>").length;i++){
			   if($("poolType<%=ftpPoolInfo.getPoolNo()%>").options[i].value=="<%=ftpPoolInfo.getPoolType()%>"){
				   $("poolType<%=ftpPoolInfo.getPoolNo()%>").selectedIndex=i;break;}
		   } 
		<%}
	%>
    <%}else{%>//��̬�������
  //�õ�table����
    var poolTable = document.getElementById("poolTable");
    addRow(poolTable, '201', '�ʲ�','1');
    addRow(poolTable, '202', '��ծ','2');
    <%}%>
    //�ʽ������selet��Ӧ����
    function doChange(poolNo) {
        if($('poolNo'+poolNo).checked == false) $('poolNo'+poolNo).checked = true;//�ð�ť����Ϊѡ��
        document.getElementById('contentObject'+poolNo).value = "";
        if($('poolType'+poolNo).value == '') {
        	window.frames.rightFrame.location.href = "<%=request.getContextPath()%>/SZJCPZ_.action";
        	return;
        }
        doRefresh(poolNo);
    }
    //�����ͬ���ʽ�أ�ˢ���Ҳ�frame����������ʾ��ͬ�Ĳ�Ʒ
    function doRefresh(poolNo) {
    	var poolType = $('poolType'+poolNo).value;
        if (poolType == '') {
        	$('poolType'+poolNo).selectedIndex=1;
            //alert("����ѡ���ʽ���ʲ����͡�,���ܽ��в�Ʒѡ�񣡣�");
           // return false;
        }
        //��ȡ�Ѿ���ѡ�еĲ�Ʒ�����ʽ���в����ظ�����
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
            alert("����δ�����κ����ã��޷����棡��");
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
                alert('���Ϊ��'+poolName[i].id.substr(8, 3)+'�����ʽ������Ϊ�գ������룡��');
                return false;
            }else {
            	poolNames.push(poolName[i].value);
            }
        }
        for(var i = 0; i < poolType.length; i++) {
            if(poolType[i].value == '') {
                alert('���Ϊ��'+poolType[i].id.substr(8, 3)+'�����ʽ���ʲ�����δ���ã������ã���');
                return false;
            }else {
            	poolTypes.push(poolType[i].value);
            }
        }
        for(var i = 0; i < contentObject.length; i++) {
            if(contentObject[i].value == '') {
                alert('���Ϊ��'+contentObject[i].id.substr(13, 3)+'�����ʽ�ذ���ҵ�����δ���ã������ã���');
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
	              	    title:'�ɹ�',
	          		    icon: 'succeed',
	          		    content: '����ɹ���',
	           		    cancelVal: '�ر�',
	           		    cancel: true
	           	 });
			}
		});
    }
  //�����
    function addRow(table, i, poolType,poolType2){
        //�����ʽ�ر��
        var lastRow = table.rows[table.rows.length-1];
        var newRow = lastRow.cloneNode(true);
        table.tBodies[0].appendChild(newRow);
        newRow.setAttribute("id", i);
        // newRow.setAttribute("onclick",new Function("doChange("+i+")"));
        newRow.cells[0].innerHTML="<input type=\"radio\" name=\"poolNo\" id=\"poolNo"+i+"\" value=\""+i+"\" readonly=\"readonly\" size=\"15\" onclick = doRefresh("+i+") />&nbsp;"+i+"";
        newRow.cells[1].innerHTML="<input type=\"text\" name=\"poolName\" id=\"poolName"+i+"\" value=\""+poolType+"�ʽ��\" readonly=\"readonly\" />";
        newRow.cells[2].innerHTML=""+poolType+"<input type=\"hidden\" name=\"poolType\" id=\"poolType"+i+"\" value=\""+poolType2+"\"/>";
        newRow.cells[3].innerHTML="<input type=\"text\" name=\"contentObject\" id=\"contentObject"+i+"\" value=\"\" readonly=\"readonly\" />";
        newRow.cells[0].setAttribute("align", "center");
        newRow.cells[1].setAttribute("align", "center");
        newRow.cells[2].setAttribute("align", "center");
        newRow.cells[3].setAttribute("align", "center");
  }
    </script>
</html>
