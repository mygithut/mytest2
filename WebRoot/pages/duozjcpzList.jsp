<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.entity.FtpPoolInfo,com.dhcc.ftp.entity.FtpBusinessPrdt" errorPage=""%>
<html>
	<head>
		<jsp:include page="commonJs.jsp" />
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<title>���ʽ��->���ʽ������</title>
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
				        <select id="poolNum" name="poolNum" onchange="setPoolNum()">
				           <option value="0">--��ѡ��--</option>
				           <%for(int i = 1; i < 51; i++) { %>
				           <option value="<%=i %>"><%=i %></option>
				           <%} %>
				        </select>
			  </td>
		      
		    </tr>
		    <tr style="display:none" id="addAndDelRow" >
			          <td align="center" >
			              <input type="button" value="�����" onclick="doAddRow()" class="button">
			          </td>
			        </tr>
			        
	    </table>
	    </td>
	    <td width="50%">
	    <DIV id="Layer1" style="OVERFLOW: auto;  height=300;width="90%">
		          <table width="100%" align="left" class="table" style="display:none" id="poolTable">
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
			           <td align="center">����</td>
			        </tr>
			        <%if(ftpPoolInfoList!=null && ftpPoolInfoList.size() > 0){
			        	for(FtpPoolInfo ftpPoolInfo : ftpPoolInfoList){%>
			        <tr id="<%=ftpPoolInfo.getPoolNo() %>">
			        	<td align="center"><input type="radio" name="poolNo" id="poolNo<%=ftpPoolInfo.getPoolNo() %>" value="<%=ftpPoolInfo.getPoolNo() %>" readonly="readonly" size="15" onclick="doRefresh(<%=ftpPoolInfo.getPoolNo() %>)"/>&nbsp;<%=ftpPoolInfo.getPoolNo() %></td>
			        	<td align="center"><input type="text" name="poolName" id="poolName<%=ftpPoolInfo.getPoolNo() %>" value="<%=ftpPoolInfo.getPoolName() %>"/></td>
			        	<td align="center"><select name="poolType" id="poolType<%=ftpPoolInfo.getPoolNo() %>" onChange=doChange(<%=ftpPoolInfo.getPoolNo() %>)>
			        	      <option value="">��ѡ��</option>
                              <option value="1">�ʲ�</option>
                              <option value="2">��ծ</option>
			        	</select></td>
			        	<td align="center"><input type="text" name="contentObject" id="contentObject<%=ftpPoolInfo.getPoolNo() %>" value="<%=ftpPoolInfo.getContentObject() %>" readonly="readonly" /></td>
			            <td align="center"><input type="button" class="button" value="ɾ����" onclick="doDelRow(<%=ftpPoolInfo.getPoolNo() %>)"/></td>
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
		   <input type="button" value="��&nbsp;&nbsp;��" onclick="doSave()" class="button">
		  </td>
		</tr>
		</table>
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
	for(var i=0;i<$("poolNum").length;i++){
		if($("poolNum").options[i].value=="<%=ftpPoolInfoList.size()%>"){
			$("poolNum").selectedIndex=i;break;}
	} 
	$("poolNum").disabled="disabled";
	document.getElementById("poolTable").style.display = "block";
    document.getElementById("addAndDelRow").style.display = "block";
    <%}%>
    //ѡ���ʽ���������Ҳ��Զ����poolNum���ʽ����Ϣ
    function setPoolNum() {
    	document.getElementById("poolTable").style.display = "block";
    	var poolNum = $("poolNum").value;
    	//�õ�table����
        var poolTable = document.getElementById("poolTable");
        if(poolTable.rows.length > 3) deleteRow(poolTable);//����ı�����������ɾ��ԭ�ȵ��У������
    	for (var i = 1; i <= poolNum; i++) {
    		if(i < 10) 
    			addRow(poolTable, '30' + i);
            else 
            	addRow(poolTable, '3' + i);
        }
    }
    //"�����"��ť��Ӧ����
    function doAddRow() {
        var poolNum = document.getElementById("poolNum").value;
        if(parseInt(poolNum)+1 > 50) {
	    	   art.dialog({
              	    title:'ʧ��',
          		    icon: 'error',
          		    content: '�ʽ���������ֻ��50����',
          		    cancelVal: '�ر�',
          		    cancel: true
           	 });
			return;
        }
    	//�õ�table����
        var poolTable = document.getElementById("poolTable");
        var poolNos = document.getElementsByName("poolNo");
        var lastPoolNo = '300';
        if(poolNos.length > 0) lastPoolNo = poolNos[poolNos.length-1].value;//��ȡ����poolNo
        addRow(poolTable, parseInt(lastPoolNo)+1);
        //�����ʽ��������+1
        for(var i=0;i<$("poolNum").length;i++){
    		if($("poolNum").options[i].value==parseInt($("poolNum").value)+1){
    			$("poolNum").selectedIndex=i;break;}
    	} 
    }
    //"ɾ����"��ť��Ӧ����
    function doDelRow(poolNo) {
    	var prdtList_isToBeDisappear='0';//�����Ʒ�б��Ƿ���ʧ---��ɾ�������Ǳ�ѡ�е���ʱ�������Ʒ�б����ʧ
        if($('poolNo'+poolNo).checked == true){//������ɾ���в���֮ǰ�ж� 
        	prdtList_isToBeDisappear="1";
        }
    	var poolTable = document.getElementById("poolTable");
    	for(var j = 2;j < poolTable.rows.length; j++) {
        	if(poolTable.rows[j].getAttribute("id") == poolNo) {
        		poolTable.deleteRow(j);
        		break;
        	}
    	}
    	//���ʽ������-1
        for(var i=0;i<$("poolNum").length;i++){
    		if($("poolNum").options[i].value==parseInt($("poolNum").value)-1){
    			$("poolNum").selectedIndex=i;break;}
    	}
    	if(prdtList_isToBeDisappear == "1"){//��ɾ�������Ǳ�ѡ�е���ʱ�������Ʒ�б����ʧ  	 
    		window.frames.rightFrame.location.href = "<%=request.getContextPath()%>/DUOZJCPZ_.action";
    	}else{//����ˢ�������Ʒ�б�---�ձ�ɾ�����ʽ���еĲ�ƷӦ�����뵽�����Ʒ�б�
	    	//�жϵ�ǰ��ѡ�е��ʽ�ر��
	    	var poolNos = document.getElementsByName("poolNo");
	        var select_poolNo = '';
	        for(var i = 0; i < poolNos.length; i++) {
	        	if(poolNos[i].checked == true){ 
	            	select_poolNo=poolNos[i].id.substring(6);
	            	break;
	            }
	        }
	        if(select_poolNo!=''){//���֮ǰ�б�ѡ�е��ʽ���У���ˢ�������Ʒ�б�
	        	doRefresh(select_poolNo);
	        }
    	}
    }
  //�ʽ������select��Ӧ����
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
        	$('poolType'+poolNo).selectedIndex=1;//���û��ѡ���ʽ���ʲ����͡���Ĭ��Ϊ���ʲ��࡯
        	poolType='1';
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
        outContentObject = outContentObject.replace(new RegExp(/\+/g),',');//��+�滻Ϊ,
        window.frames.rightFrame.location.href = "<%=request.getContextPath()%>/SZJCPZ_getPrdt.action?outContentObject="+outContentObject+"&poolNo="+poolNo+"&poolType="+poolType;
    }
    function doSave() {
        if($('poolNum').value == '0' || $('poolNum').value == '') {
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
        var url = "<%=request.getContextPath()%>/DUOZJCPZ_save.action";
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
	           		    ok: function () {
	           		    	ok();
	           		        return true;
	           		    }
	            	 });
			}
		});
    }
  //ҳ����������ܣ������
    function addRow(table, i){
        //�����ʽ�ر��
        var lastRow = table.rows[table.rows.length-1];
        var newRow = lastRow.cloneNode(true);
        table.tBodies[0].appendChild(newRow);
        newRow.setAttribute("id", i);
        // newRow.setAttribute("onclick",new Function("doChange("+i+")"));
        newRow.cells[0].innerHTML="<input type=\"radio\" name=\"poolNo\" id=\"poolNo"+i+"\" value=\""+i+"\" readonly=\"readonly\" size=\"15\" onclick = doRefresh("+i+") />&nbsp;"+i+"";
        newRow.cells[1].innerHTML="<input type=\"text\" name=\"poolName\" id=\"poolName"+i+"\" value=\"\" />";
        newRow.cells[2].innerHTML="<select name=\"poolType\" id=\"poolType"+i+"\" onChange=doChange("+i+")>"+
                                  "<option value=\"\">��ѡ��</option>"+
                                  "<option value=\"1\">�ʲ�</option>"+
                                  "<option value=\"2\">��ծ</option>"+
                                  "</select>";
        newRow.cells[3].innerHTML="<input type=\"text\" name=\"contentObject\" id=\"contentObject"+i+"\" value=\"\" readonly=\"readonly\" />";
        newRow.cells[4].innerHTML="<input type=\"button\" class=\"button\" value=\"ɾ����\" onclick=\"doDelRow("+i+")\"/>";
        newRow.cells[0].setAttribute("align", "center");
        newRow.cells[1].setAttribute("align", "center");
        newRow.cells[2].setAttribute("align", "center");
        newRow.cells[3].setAttribute("align", "center");
        newRow.cells[4].setAttribute("align", "center");
        }
    //ɾ����
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
