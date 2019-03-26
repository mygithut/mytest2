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
		<title>存贷比调整标准设置</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
	</head>
	<body>
		<div class="cr_header">
			当前位置：数据管理-存贷比调整标准设置
		</div>
		<form action="" method="post" id="cdb" name="cdb">
			<table class="table" width="70%" align="center">
				<tr>
					<th align="right" colspan="8">
						存贷比标准设置
					</th>
				</tr>
				<tr>
				    <th width="150" align="center">存贷比范围(%)</th>
				    <td align="center">最小月均存贷比</td><td align="center">
				    <%if(id!=null){ %>
				    <input type="text" id="minRate" name="minRate" value="<%=CommonFunctions.doublecut(Double.parseDouble(minRate)*100,3)%>" size="10"></td>
				    <%}else{ %>
				    <input type="text" id="minRate" name="minRate" size="10"></td>
				    <%} %>
    			    <td align="center"><select id="frontOperator" name="frontOperator">
				    <%if(id!=null&&frontOperator.equals("1")){ %>
				       <option value="1" selected>≤</option>
				       <option value="2">＜</option>
				    <%}else if(id!=null&&frontOperator.equals("2")){ %>
				       <option value="1">≤</option>
				       <option value="2" selected>＜</option>
				    <%}else{ %>
				       <option value="1" selected>≤</option>
				       <option value="2">＜</option>
				    <%} %>
				    </select></td>	
				    <td align="center">当月存贷比</td>
				    <td align="center"><select id="backOperator" name="backOperator">
				    <%if(id!=null&&backOperator.equals("1")){ %>
				       <option value="1" selected>≤</option>
				       <option value="2">＜</option>
				    <%}else if(id!=null&&backOperator.equals("2")){ %>
				       <option value="1">≤</option>
				       <option value="2" selected>＜</option>
				    <%}else{ %>
				       <option value="1">≤</option>
				       <option value="2" selected>＜</option>
				    <%} %>
				    </select></td>
				    <td align="center">最大月存贷比</td><td align="center">
				    <%if(id!=null){ %>
				    <input type="text" id="maxRate" name="maxRate" value="<%=CommonFunctions.doublecut(Double.parseDouble(maxRate)*100,3)%>" size="10"></td>
				    <%}else{ %>
				    <input type="text" id="maxRate" name="maxRate" size="10"></td>
				    <%} %>
				</tr>
				<tr>
				    <table class="table" width="70%" align="center">
				       <tr>
				          <th width="150" align="center">期限</th>
					      <td align="center">一年以下(BP)</td>
					      <td align="center">1-3年(BP)</td>
					      <td align="center">3-5年(BP)</td>
					      <td align="center">5年以上(BP)</td>
				       </tr>
				       <tr>
				          <th width="150" align="center">调整值(BP)</th>
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
							<input type="button" name="Submit1" value="保&nbsp;&nbsp;存"		onClick="save(this.form)" class="button">
							&nbsp;&nbsp;
							<input type="reset" name="Reset" value="重&nbsp;&nbsp;置" class="button">
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
        alert("输入数据不能为空！");
		return;			
    }
    if(FormName.frontOperator.value==""||FormName.backOperator.value==""){
    	alert("请输入月均比间关系！");
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
		alert("请检测月均比或调整值，输入大于0的整数或小数！");
		return;
	}
	if(parseFloat(FormName.minRate.value)>parseFloat(FormName.maxRate.value)){
		alert("提示：最小月均比应小于最大月均比！");
		document.getElementById("minRate").select();
		return;
	}
	if(FormName.minRate.value<0||FormName.maxRate.value>100){
		alert("最小或最大月均比的范围为[0,100]！");
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
              	    title:'提示',
          		    icon: 'error',
          		    content: '存贷比范围有误，保存失败！',
          		    ok: function () {
            		  document.getElementById("minRate").select();
          		    }
           	 	});
            }
            if(val.responseText=='3'){
            	art.dialog({
              	    title:'提示',
          		    icon: 'error',
          		    content: '最小月存贷比或最大月存贷比端点已占用！',
          		    ok: function () {
            		  document.getElementById("minRate").select();
          		    }
           	 	});
            }
            if(val.responseText=='0'){
   	    	   art.dialog({
              	    title:'成功',
          		    icon: 'succeed',
          		    content: '保存成功！',
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
