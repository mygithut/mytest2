<%@ page contentType="text/html;charset=GBK"%>
<%@page import="com.dhcc.ftp.entity.TelMst"%>
<%@page import="com.dhcc.ftp.entity.FtpBrPostRel"%>
<%@ page	import="java.sql.*,java.util.*,java.text.*"%>
<%@page	import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil,com.dhcc.ftp.util.CommonFunctions"%>
<%
    TelMst telmst = (TelMst) request.getSession().getAttribute("userBean"); 
	String brNo=String.valueOf(request.getParameter("brNo"));
	String id =(String)request.getParameter("id");
	String minRate =(String)request.getParameter("minRate");
	String maxRate =(String)request.getParameter("maxRate");
	String frontOperator =(String)request.getParameter("frontOperator");
	String backOperator =(String)request.getParameter("backOperator");
	String adjustValue4 =(String)request.getParameter("adjustValue4");
	String adjustValue3 =(String)request.getParameter("adjustValue3");
	String adjustValue2 =(String)request.getParameter("adjustValue2");
	String adjustValue1 =(String)request.getParameter("adjustValue1");
%>
<html>
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
	<meta http-equiv="pragram" content="no-cache" />
	<head>
		<title>����ȵ�����׼����</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
	</head>
	<body>
		<div class="cr_header">
			��ǰλ�ã����ݹ���-����ȵ�����׼����
		</div>
		<form action="" method="post" id="cdb" name="cdb">
			<table class="table" width="70%" align="center">
				<tr>
					<th align="right" colspan="8">
						����ȱ�׼����
					</th>
				</tr>
				<tr>
				    <th width="150" align="center">����ȷ�Χ(%)</th>
				    <td align="center">��С�¾������</td><td align="center">
				    <%if(id!=null){ %>
				    <input type="text" id="minRate" name="minRate" value="<%=CommonFunctions.doublecut(Double.parseDouble(minRate)*100,3)%>" size="10"></td>
				    <%}else{ %>
				    <input type="text" id="minRate" name="minRate" size="10"></td>
				    <%} %>
    			    <td align="center"><select id="frontOperator" name="frontOperator">
				    <%if(id!=null&&frontOperator.equals("1")){ %>
				       <option value="1" selected>��</option>
				       <option value="2">��</option>
				    <%}else if(id!=null&&frontOperator.equals("2")){ %>
				       <option value="1">��</option>
				       <option value="2" selected>��</option>
				    <%}else{ %>
				       <option value="1" selected>��</option>
				       <option value="2">��</option>
				    <%} %>
				    </select></td>	
				    <td align="center">���´����</td>
				    <td align="center"><select id="backOperator" name="backOperator">
				    <%if(id!=null&&backOperator.equals("1")){ %>
				       <option value="1" selected>��</option>
				       <option value="2">��</option>
				    <%}else if(id!=null&&backOperator.equals("2")){ %>
				       <option value="1">��</option>
				       <option value="2" selected>��</option>
				    <%}else{ %>
				       <option value="1">��</option>
				       <option value="2" selected>��</option>
				    <%} %>
				    </select></td>
				    <td align="center">����´����</td><td align="center">
				    <%if(id!=null){ %>
				    <input type="text" id="maxRate" name="maxRate" value="<%=CommonFunctions.doublecut(Double.parseDouble(maxRate)*100,3)%>" size="10"></td>
				    <%}else{ %>
				    <input type="text" id="maxRate" name="maxRate" size="10"></td>
				    <%} %>
				</tr>
				<tr>
				    <table class="table" width="70%" align="center">
				       <tr>
				          <th width="150" align="center">����</th>
					      <td align="center">һ������(BP)</td>
					      <td align="center">1-3��(BP)</td>
					      <td align="center">3-5��(BP)</td>
					      <td align="center">5������(BP)</td>
				       </tr>
				       <tr>
				          <th width="150" align="center">����ֵ(BP)</th>
				          <td align="center">
					      <%if(id!=null){ %>
					      <input type="text" id="adjustValue4" name="adjustValue4" value="<%=CommonFunctions.doublecut(Double.parseDouble(adjustValue4)*10000,3)%>" size="20"/></td>
					      <%}else{ %>
					      <input type="text" id="adjustValue4" name="adjustValue4" size="20"/>
					      <%} %></td>
					      <td align="center">
                          <%if(id!=null){ %>
					      <input type="text" id="adjustValue3" name="adjustValue3" value="<%=CommonFunctions.doublecut(Double.parseDouble(adjustValue3)*10000,3)%>" size="20"/></td>
					      <%}else{ %>
					      <input type="text" id="adjustValue3" name="adjustValue3" size="20"/>
					      <%} %></td>
					      <td align="center">
                          <%if(id!=null){ %>
					      <input type="text" id="adjustValue2" name="adjustValue2" value="<%=CommonFunctions.doublecut(Double.parseDouble(adjustValue2)*10000,3)%>" size="20"/></td>
					      <%}else{ %>
					      <input type="text" id="adjustValue2" name="adjustValue2" size="20"/>
					      <%} %></td>
					      <td align="center">
                          <%if(id!=null){ %>
					      <input type="text" id="adjustValue1" name="adjustValue1" value="<%=CommonFunctions.doublecut(Double.parseDouble(adjustValue1)*10000,3) %>" size="20"/></td>
					      <%}else{ %>
					      <input type="text" id="adjustValue1" name="adjustValue1" size="20"/>
					      <%} %></td>
				       </tr>
				    </table>
				</tr>
				<tr>
					<td height="41" colspan="8">
						<div align="center">
							<input type="button" name="Submit1" value="��&nbsp;&nbsp;��"		onClick="save(this.form)" class="button">
							&nbsp;&nbsp;
							<input type="reset" name="Reset" value="��&nbsp;&nbsp;��" class="button">
						</div>
					</td>
				</tr>
			</table>
			<table class="table" width="70%" align="center" id="rate" name="rate" border="0">
			    <tr>
                   <td align="left">
                      <iframe src="<%=request.getContextPath()%>/CDBTZ_List.action" id="downframe" width="100%" height="350" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" align="middle"></iframe> 
                   </td>
                </tr>
			</table>
		</form>
	</body>
	<script type="text/javascript">	

function save(FormName) {

    if(FormName.minRate.value==""||FormName.maxRate.value==""||FormName.adjustValue4.value==""
        ||FormName.adjustValue3.value==""||FormName.adjustValue2.value==""||FormName.adjustValue1.value=="") {
        alert("�������ݲ���Ϊ�գ�");
		return;			
    }
    if(FormName.frontOperator.value==""||FormName.backOperator.value==""){
    	alert("�������¾��ȼ��ϵ��");
    	if(FormName.frontOperator.value==""){
    	   document.getElementById("frontOperator").focus();
    	}
    	if(FormName.backOperator.value==""){
    		document.getElementById("backOperator").selected=true;
    	}
    	return;
    }
   
	var url = "<%=request.getContextPath()%>/CDBTZ_save.action";
	var frontOperator = document.getElementById("frontOperator").value;
	var backOperator = document.getElementById("backOperator").value;
	var adjustValue4 = document.getElementById("adjustValue4").value;
	var adjustValue3 = document.getElementById("adjustValue3").value;
	var adjustValue2 = document.getElementById("adjustValue2").value;
	var adjustValue1 = document.getElementById("adjustValue1").value;
	var re = /^\d+\.?\d{0,4}$/;///^-?[1-9]*(.d*)?$|^-?d^(.d*)?$/;
	if ((!re.test(FormName.minRate.value))||(!re.test(FormName.maxRate.value))||(!re.test(adjustValue4))||(!re.test(adjustValue3))||(!re.test(adjustValue2))||(!re.test(adjustValue1))){
		alert("�����¾��Ȼ����ֵ���������0��������С����");
		return;
	}
	if(parseFloat(FormName.minRate.value)>parseFloat(FormName.maxRate.value)){
		alert("��ʾ����С�¾���ӦС������¾��ȣ�");
		document.getElementById("minRate").select();
		return;
	}
	if(FormName.minRate.value<0||FormName.maxRate.value>100){
		alert("��С������¾��ȵķ�ΧΪ[0,100]��");
		document.getElementById("maxRate").select();
		return;
	}
    	   
   new Ajax.Request( 
        url, 
         {  
          method: 'post',   
          parameters: {
            id:<%=id%>,
        	minRate:FormName.minRate.value,
            maxRate:FormName.maxRate.value,
            frontOperator:frontOperator,
            backOperator:backOperator,
            adjustValue4:adjustValue4,
            adjustValue3:adjustValue3,
            adjustValue2:adjustValue2,
            adjustValue1:adjustValue1,
            t:new Date().getTime()
            },
          onSuccess: function(val){
            if(val.responseText=='1'||val.responseText=='2'){
            	art.dialog({
              	    title:'��ʾ',
          		    icon: 'error',
          		    content: '����ȷ�Χ���󣬱���ʧ�ܣ�',
          		    ok: function () {
            		  document.getElementById("minRate").select();
          		    }
           	 	});
            }
            if(val.responseText=='3'){
            	art.dialog({
              	    title:'��ʾ',
          		    icon: 'error',
          		    content: '��С�´���Ȼ�����´���ȶ˵���ռ�ã�',
          		    ok: function () {
            		  document.getElementById("minRate").select();
          		    }
           	 	});
            }
            if(val.responseText=='0'){
   	    	   art.dialog({
              	    title:'�ɹ�',
          		    icon: 'succeed',
          		    content: '����ɹ���',
          		    ok: function () {
	    		        window.location.href ='<%=request.getContextPath()%>/pages/cdbtz.jsp';
	    		        //ok();
          		        return true;
          		    }
           	 	});
	         }
          }
         });
}

function ok(){
    window.location.reload();
}
</script>
</html>
