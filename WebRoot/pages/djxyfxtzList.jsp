<%@ page contentType="text/html;charset=GBK"%>
<%@page import="com.dhcc.ftp.entity.TelMst"%>
<%@page import="com.dhcc.ftp.entity.Ftp1PrdtIrAdjust"%>
<%@ page	import="java.sql.*,java.util.*,java.text.*"%>
<%@page	import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil,com.dhcc.ftp.util.CommonFunctions"%>
<%
    String[] ajustValue = (String[])request.getAttribute("adjustValue");
    String[] adjustLevel = (String[])request.getAttribute("adjustLevel");
%>
<html>
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
	<meta http-equiv="pragram" content="no-cache" />
	<head>
		<title>定价信用风险调整</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
	</head>
	<body>
		<form action="" method="post" id="cdb" name="cdb">
			<table class="table" width="30%" align="center">
				<tr>
				    <th align="center" class="ClASSTD">客户类型</th>
					<th align="center" class="ClASSTD">客户信用等级</th>
					<th align="center" class="ClASSTD">信用风险加点(BP)</th>
				</tr>
				<tr>
				    <td align="center" rowspan="6">企事业</td>
				    <td align="center">AAA</td>
				    <td align="center">
				    <%if(ajustValue!=null&&ajustValue.length-1>=0){%>
				    <input type="text" id="0101" name="ajustValue" size="10" onkeyup="value=value.replace(/[^\d\.-]/g,'')"  value="<%=ajustValue[0]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[0]),4)%>"/>
				    <%}else{ %>
				    <input type="text" id="0101" name="ajustValue" size="10" value="0" onkeyup="value=value.replace(/[^\d\.-]/g,'')" />
				    <%} %>
				    </td>
				</tr>
				<tr>
				    <td align="center">AA</td>
				    <td align="center">
				    <%if(ajustValue!=null&&ajustValue.length-1>1){%>
				    <input type="text" id="0102" name="ajustValue" size="10" onkeyup="value=value.replace(/[^\d\.-]/g,'')"  value="<%=ajustValue[1]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[1]),4)%>"/>
				    <%}else{ %>
				    <input type="text" id="0102" name="ajustValue" size="10" onkeyup="value=value.replace(/[^\d\.-]/g,'')"  value="0"/>
				    <%} %>
				    </td>
				</tr>
				<tr>
				    <td align="center">A</td>
				    <td align="center">
				    <%if(ajustValue!=null&&ajustValue.length-1>2){%>
				    <input type="text" id="0103" name="ajustValue" size="10"  onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=ajustValue[2]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[2]),4)%>"/>
				    <%}else{ %>
				    <input type="text" id="0103" name="ajustValue" size="10" value="0" onkeyup="value=value.replace(/[^\d\.-]/g,'')" />
				    <%} %>
				    </td>
				</tr>
				<tr>
				    <td align="center">BBB</td>
				    <td align="center">
				    <%if(ajustValue!=null&&ajustValue.length-1>3){%>
				    <input type="text" id="0104" name="ajustValue" size="10"  onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=ajustValue[3]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[3]),4)%>"/>
				    <%}else{ %>
				    <input type="text" id="0104" name="ajustValue" size="10" value="0" onkeyup="value=value.replace(/[^\d\.-]/g,'')" />
				    <%} %>
				    </td>
				</tr>
				<tr>
				    <td align="center">BB</td>
				    <td align="center">
				    <%if(ajustValue!=null&&ajustValue.length-1>4){%>
				    <input type="text" id="0105" name="ajustValue" size="10" onkeyup="value=value.replace(/[^\d\.-]/g,'')"  value="<%=ajustValue[4]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[4]),4)%>"/>
				    <%}else{ %>
				    <input type="text" id="0105" name="ajustValue" size="10" value="0"  onkeyup="value=value.replace(/[^\d\.-]/g,'')" />
				    <%} %>
                    </td>
				</tr>
				<tr>
				    <td align="center">未评级</td>
				    <td align="center">
				    <%if(ajustValue!=null&&ajustValue.length-1>=9){%>
				    <input type="text" id="0106" name="ajustValue" size="10"  onkeyup="value=value.replace(/[^\d\.-]/g,'')"  value="<%=ajustValue[5]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[5]),4)%>"/>
				    <%}else{ %>
				    <input type="text" id="0106" name="ajustValue" size="10" value="0"   onkeyup="value=value.replace(/[^\d\.-]/g,'')" />
				    <%}%>
                    </td>
				</tr>
				<tr>
				    <td align="center" rowspan="4">个人</td>
				    <td align="center">优秀</td>
				    <td align="center">
				    <%if(ajustValue!=null&&ajustValue.length-1>=0){%>
				    <input type="text" id="0201" name="ajustValue" size="10"  onkeyup="value=value.replace(/[^\d\.-]/g,'')"  value="<%=ajustValue[6]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[6]),4)%>"/>
				    <%}else{ %>
				    <input type="text" id="0201" name="ajustValue" size="10" value="0"  onkeyup="value=value.replace(/[^\d\.-]/g,'')" />
				    <%} %>
				    </td>
				</tr>
				<tr>
				    <td align="center">较好</td>
				    <td align="center">
				    <%if(ajustValue!=null&&ajustValue.length-1>=0){%>
				    <input type="text" id="0202" name="ajustValue" size="10"   onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=ajustValue[7]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[7]),4)%>"/>
				    <%}else{ %>
				    <input type="text" id="0202" name="ajustValue" size="10" value="0"  onkeyup="value=value.replace(/[^\d\.-]/g,'')" />
				    <%} %>
				    </td>
				</tr>
				<tr>
				    <td align="center">一般</td>
				    <td align="center">
				    <%if(ajustValue!=null&&ajustValue.length-1>=0){%>
				    <input type="text" id="0203" name="ajustValue" size="10"  onkeyup="value=value.replace(/[^\d\.-]/g,'')"  value="<%=ajustValue[8]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[8]),4)%>"/>
				    <%}else{ %>
				    <input type="text" id="0203" name="ajustValue" size="10" value="0"  onkeyup="value=value.replace(/[^\d\.-]/g,'')" />
				    <%} %>
				    </td>
				</tr>
				<tr>
				    <td align="center">未评级</td>
				    <td align="center">
				    <%if(ajustValue!=null&&ajustValue.length-1>=0){%>
				    <input type="text" id="0204" name="ajustValue" size="10"   onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=ajustValue[9]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[9]),4)%>"/>
				    <%}else{ %>
				    <input type="text" id="0204" name="ajustValue" size="10" value="0"  onkeyup="value=value.replace(/[^\d\.-]/g,'')" />
				    <%} %>
				    </td>
				</tr>
				<tr>
                    <td align="center" colspan="3">
                       <input name="save" class="button" type="button" id="save" height="20" onClick="onclick_save()" value="保&nbsp;&nbsp;存" /> 
                       <input name="back" class="button" type="button" id="back" height="20" onClick="onclick_back()" value="重&nbsp;&nbsp;置" /> 
                    </td>
                </tr>
                
			</table>
		</form>
	</body>
<script type="text/javascript">	
function onclick_save(){
	var ajustValue = document.getElementsByName("ajustValue");
    var rates="";
    for(var i=0;i<ajustValue.length;i++){
    	rates+=ajustValue[i].value+",";
    }
   
   /*  var ajustValue = document.getElementById("ajustValue").value; */
	var url = "<%=request.getContextPath()%>/DJXYFXTZ_save.action";
        new Ajax.Request( 
        url, 
         {  
          method: 'post',   
          parameters: {
        	adjustValue:rates,
            t:new Date().getTime()},
          onSuccess: function() 
           {
	    	   art.dialog({
              	    title:'成功',
          		    icon: 'succeed',
          		    content: '保存成功！',
          		    ok: function () {
	    		        close();
          		        return true;
          		    }
           	 });
	           	 }
          }
       );
        
	}
function onclick_back(){
	   window.location.reload();
	}
</script>
</html>
