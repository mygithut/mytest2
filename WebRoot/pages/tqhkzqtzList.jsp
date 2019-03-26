<%@ page contentType="text/html;charset=GBK"%>
<%@page import="com.dhcc.ftp.entity.TelMst"%>
<%@page import="com.dhcc.ftp.entity.Ftp1PrepayAdjust"%>
<%@ page	import="java.sql.*,java.util.*,java.text.*"%>
<%@page	import="com.dhcc.ftp.util.CastUtil,com.dhcc.ftp.util.DateUtil,com.dhcc.ftp.util.CommonFunctions"%>
<%
	String[] repayId = (String[])request.getAttribute("repayId");
    String[] ajustValue = (String[])request.getAttribute("adjustValue");
%>
<html>
	<meta http-equiv="expires" content="0" />
	<meta http-equiv="cache-control" content="no-cache, must-revalidate" />
	<meta http-equiv="pragram" content="no-cache" />
	<head>
		<title>提前还款/支取调整</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
	</head>
	<body>
		<form action="" method="post" id="cdb" name="cdb">
			<table class="table" width="50%" align="center">
				<tr>
					<th align="center" width="30%" class="ClASSTD">业务类型</th>
					<th align="center" width="30%" class="ClASSTD">贷款期限</th>
					<th align="center" width="40%" class="ClASSTD">提前还款/支取调整值(BP)</th>
				</tr>
				<%
				if(repayId==null||repayId.length==0){
				%>
				<tr>
				    <td rowspan="4" align="center">贷款</td>
				    <td align="center">5年以上</td>
				    <td align="center"><input size="10" value="0" name="ajustValue" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/></td>
				</tr>
				<tr>
				    <td align="center">3-5年(含)</td>
				    <td align="center"><input size="10" value="0" name="ajustValue" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/></td>
				</tr>
				<tr>
				    <td align="center">1-3年(含)</td>
				    <td align="center"><input size="10" value="0" name="ajustValue" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/></td>
				</tr>
				<tr>
				    <td align="center">1年(含)及以下</td>
				    <td align="center"><input size="10" value="0" name="ajustValue" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/></td>
				</tr>
				<tr>
				    <td rowspan="5" align="center">存款</td>
				    <td align="center">5年以上</td>
				    <td align="center"><input size="10" value="0" name="ajustValue" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/></td>
				</tr>
				<tr>
				    <td align="center">3-5年(含)</td>
				    <td align="center"><input size="10" value="0" name="ajustValue" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/></td>
				</tr>s
				<tr>
				    <td align="center">2-3年(含)</td>
				    <td align="center"><input size="10" value="0" name="ajustValue" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/></td>
				</tr>
				<tr>
				    <td align="center">1-2年(含)</td>
				    <td align="center"><input size="10" value="0" name="ajustValue" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/></td>
				</tr>
				<tr>
				    <td align="center">1年(含)及以下</td>
				    <td align="center"><input size="10" value="0" name="ajustValue" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/></td>
				</tr>
				<% 
				}else{
				%>
				<tr>
				    <td rowspan="4" align="center">贷款</td>
				    <td align="center">5年以上</td>
				    <td align="center">
				    <input type="text" id="up5Yd" name="ajustValue" size="10" value="<%=ajustValue[0]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[0]),4)%>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <input type="hidden" id="Idd5Y" name="IdAjustValue" value="<%=repayId[0]==null?"0":repayId[0] %>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    </td>
				</tr>
				<tr>
				    <td align="center">3-5年(含)</td>
				    <td align="center">
				    <%if(repayId.length-1>1){%>
				    <input type="text" id="3Yd" name="ajustValue" size="10" value="<%=ajustValue[1]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[1]),4)%>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <input type="hidden" id="Idd3Y" name="IdAjustValue" value="<%=repayId[1]==null?"0":repayId[1]%>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <%}else{ %>
				    <input type="text" id="up5Yd" name="ajustValue" size="10" value="0"/>
				    <%} %>
				    </td>
				</tr>
				<tr>
				    <td align="center">1-3年(含)</td>
				    <td align="center">
				    <%if(repayId.length-1>2){%>
				    <input type="text" id="1Yd" name="ajustValue" size="10" value="<%=ajustValue[2]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[2]),4)%>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <input type="hidden" id="Idd1Y" name="IdAjustValue" value="<%=repayId[2]==null?"0":repayId[2]%>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <%}else{ %>
				    <input type="text" id="1Yd" name="ajustValue" size="10" value="0"/>
				    <%} %>
				    </td>
				</tr>
				<tr>
				    <td align="center">1年以下</td>
				    <td align="center">
				    <%if(repayId.length-1>3){%>
				    <input type="text" id="down1Yd" name="ajustValue" size="10" value="<%=ajustValue[3]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[3]),4)%>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <input type="hidden" id="Idd0Y" name="IdAjustValue" value="<%=repayId[3]==null?"0":repayId[3]%>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <%}else{ %>
				    <input type="text" id="down1Yd" name="justValue" size="10" value="0"/>
				    <%} %>
				    </td>
				</tr>
				<tr>
				    <td rowspan="5" align="center">存款</td>
				    <td align="center">5年以上</td>
				    <td align="center">
				    <%if(repayId.length-1>4){%>
				    <input type="text" id="6Yc" name="ajustValue" size="10" value="<%=ajustValue[4]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[4]),4)%>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <input type="hidden" id="Idc6Y" name="IdAjustValue" value="<%=repayId[4]==null?"0":repayId[4]%>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <%}else{ %>
				    <input type="text" id="6Yc" name="ajustValue" size="10" value="0"/>
				    <%} %>
                    </td>
				</tr>
				<tr>
				    <td align="center">3-5年(含)</td>
				    <td align="center">
				    <%if(repayId.length-1>5){%>
				    <input type="text" id="5Yc" name="ajustValue" size="10" value="<%=ajustValue[5]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[5]),4)%>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <input type="hidden" id="Idc5Y" name="IdAjustValue" value="<%=repayId[5]==null?"0":repayId[5]%>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <%}else{ %>
				    <input type="text" id="5Yc" name="ajustValue" size="10" value="0"/>
				    <%} %>
                    </td>
				</tr>
				<tr>
				    <td align="center">2-3年(含)</td>
				    <td align="center">
				    <%if(repayId.length-1>6){%>
				    <input type="text" id="3Yc" name="ajustValue" size="10" value="<%=ajustValue[6]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[6]),4)%>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <input type="hidden" id="Idc3Y" name="IdAjustValue" value="<%=repayId[6]==null?"0":repayId[6]%>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <%}else{ %>
				    <input type="text" id="3Yc" name="ajustValue" size="10" value="0"/>
				    <%}%>
                    </td>
				</tr>
				<tr>
				    <td align="center">1-2年(含)</td>
				    <td align="center">
				    <%if(repayId.length-1>7){%>
				    <input type="text" id="2Yc" name="ajustValue" size="10" value="<%=ajustValue[7]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[7]),4)%>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <input type="hidden" id="Idc2Y" name="IdAjustValue" value="<%=repayId[7]==null?"0":repayId[7]%>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <%}else{ %>
				    <input type="text" id="2Yc" name="ajustValue" size="10" value="0"/>
				    <%} %>
                    </td>
				</tr>
				<tr>
				    <td align="center">1年(含)及以下</td>
				    <td align="center">
				    <%if(repayId.length-1>=8){%>
				    <input type="text" id="0Yc" name="ajustValue" size="10" value="<%=ajustValue[8]==null?"0":CommonFunctions.doublecut(Double.parseDouble(ajustValue[8]),4)%>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <input type="hidden" id="Idc0Y" name="IdAjustValue" value="<%=repayId[8]==null?"0":repayId[8] %>" onkeyup="value=value.replace(/[^\d\.-]/g,'')"/>
				    <%}else{ %>
				    <input type="text" id="0Yc" name="ajustValue" size="10" value="0"/>
				    <%}}%>
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
   
/*     var ajustValue = document.getElementById("ajustValue").value; */
	var url = "<%=request.getContextPath()%>/TQHKZQTZ_save.action";
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
