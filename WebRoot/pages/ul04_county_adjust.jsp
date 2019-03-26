<%@ page language="java" import="java.util.*,com.dhcc.ftp.util.*" pageEncoding="GB18030"%>
<html>
  <head>
    <title>县联社定价策略--查看</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    

	<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
	<jsp:include page="commonJs.jsp" />
  </head>
  
  <body>
  <%//获取县联社的策略调整值
	Map<String, Double> adjustMap = (Map<String, Double>)request.getAttribute("adjustMap"); 
	String curveType = (String)request.getAttribute("curveType");
  %>
  <table class="table" id="table1" style="display:none" width="698">
	     <tr bgColor="#EEF2E6"><td rowspan="2" width="5%" align="center">存&nbsp;款：</td><td width="20%" align="right">
	        3个月以内
		    <input type="text" size="5" id="c0M" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-02-0M"),2) %>"/>
		    </td><td width="15%" align="right">
		    3个月
		    <input type="text" size="5" id="c3M" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-02-3M"),2) %>"/>
		    </td><td width="15%" align="right">
		    6个月
		    <input type="text" size="5" id="c6M" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-02-6M"),2) %>"/>
		    </td><td width="15%" align="right">
		    1年
		    <input type="text" size="5" id="c1Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-02-1Y"),2) %>"/>
		    </td><td width="15%" align="right">
		    2年
		    <input type="text" size="5" id="c2Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-02-2Y"),2) %>"/>
		    </td><td width="15%" align="right">
		    3年
		    <input type="text" size="5" id="c3Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-02-3Y"),2) %>"/>
		    </td></tr>
		    <tr bgColor="#EEF2E6"><td align="right">
		    5年
		    <input type="text" size="5" id="c5Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-02-5Y"),2) %>"/>
		    </td><td align="right">
		    7年
		    <input type="text" size="5" id="c7Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-02-7Y"),2) %>"/>
		    </td><td align="right">
		    10年
		    <input type="text" size="5" id="c10Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-02-10Y"),2) %>"/>
		    </td><td align="right">
		    15年
		    <input type="text" size="5" id="c15Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-02-15Y"),2) %>"/>
		    </td><td align="right">
		    20年
		    <input type="text" size="5" id="c20Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-02-20Y"),2) %>"/>
		    </td><td align="right">
		    30年
		    <input type="text" size="5" id="c30Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-02-30Y"),2) %>"/>
		    </td></tr>
		    <tr><td rowspan="2">贷&nbsp;款：</td><td width="20%" align="right">
	        3个月以内
		    <input type="text" size="5" id="d0M" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-01-0M"),2) %>"/>
		    </td><td width="15%" align="right">
		    3个月
		    <input type="text" size="5" id="d3M" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-01-3M"),2) %>"/>
		    </td><td width="15%" align="right">
		    6个月
		    <input type="text" size="5" id="d6M" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-01-6M"),2) %>"/>
		    </td><td width="15%" align="right">
		    1年
		    <input type="text" size="5" id="d1Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-01-1Y"),2) %>"/>
		    </td><td width="15%" align="right">
		    2年
		    <input type="text" size="5" id="d2Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-01-2Y"),2) %>"/>
		    </td><td width="15%" align="right">
		    3年
		    <input type="text" size="5" id="d3Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-01-3Y"),2) %>"/>
		    </td></tr>
		    <tr><td align="right">
		    5年
		    <input type="text" size="5" id="d5Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-01-5Y"),2) %>"/>
		    </td><td align="right">
		    7年
		    <input type="text" size="5" id="d7Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-01-7Y"),2) %>"/>
		    </td><td align="right">
		    10年
		    <input type="text" size="5" id="d10Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-01-10Y"),2)%>"/>
		    </td><td align="right">
		    15年
		    <input type="text" size="5" id="d15Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-01-15Y"),2) %>"/>
		    </td><td align="right">
		    20年
		    <input type="text" size="5" id="d20Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-01-20Y"),2) %>"/>
		    </td><td align="right">
		    30年
		    <input type="text" size="5" id="d30Y" name="adjustValueC1" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("01-01-30Y"),2) %>"/>
		    </td></tr>
	     </table>
	     <table class="table"  id="table2" style="display:none" width="698">
	      <tr bgColor="#EEF2E6"><td rowspan="3" width="5%">存&nbsp;款：</td><td width="15%" align="right">
	                       隔夜
		    <input type="text" size="5" id="cO/N" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-O/N"),2) %>"/>
		    </td><td width="15%" align="right">
		    7天
		    <input type="text" size="5" id="c1W" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-1W"),2) %>"/>
		    </td><td width="17%" align="right">
		    14天
		    <input type="text" size="5" id="c2W" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-2W"),2) %>"/>
		    </td><td width="17%" align="right">
		    1个月
		    <input type="text" size="5" id="c1M" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-1M"),2) %>"/>
		    </td><td width="15%" align="right">
		    3个月
		    <input type="text" size="5" id="c3M" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-3M"),2) %>"/>
		    </td><td width="16%" align="right">
		    6个月
		    <input type="text" size="5" id="c6M" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-6M"),2) %>"/>
		    </td></tr>
		    <tr bgColor="#EEF2E6"><td align="right">
		    9个月
		    <input type="text" size="5" id="c9M" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-9M"),2) %>"/>
		    </td><td align="right">
		    1年shibor
		    <input type="text" size="5" id="c1YS" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-1YS"),2) %>"/>
		    </td><td align="right">
		    1年国债
		    <input type="text" size="5" id="c1YG" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-1YG"),2) %>"/>
		    </td><td align="right">
		    2年
		    <input type="text" size="5" id="c2Y" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-2Y"),2) %>"/>
		    </td><td align="right">
		    3年
		    <input type="text" size="5" id="c3Y" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-3Y"),2) %>"/>
		    </td><td align="right">
		    5年
		    <input type="text" size="5" id="c5Y" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-5Y"),2) %>"/>
		    </td></tr>
		    <tr bgColor="#EEF2E6"><td align="right">
		    7年
		    <input type="text" size="5" id="c7Y" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-7Y"),2) %>"/>
		    </td><td align="right">
		    10年
		    <input type="text" size="5" id="c10Y" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-10Y"),2) %>"/>
		    </td><td align="right">
		    15年
		    <input type="text" size="5" id="c15Y" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-15Y"),2) %>"/>
		    </td><td align="right">
		    20年
		    <input type="text" size="5" id="c20Y" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-20Y"),2) %>"/>
		    </td><td align="right">
		    30年
		    <input type="text" size="5" id="c30Y" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-02-30Y"),2) %>"/>
		    </td><td></td></tr>
		    <tr ><td rowspan="3" width="5%">贷&nbsp;款：</td><td width="15%" align="right">
	                       隔夜
		    <input type="text" size="5" id="dO/N" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-O/N"),2) %>"/>
		    </td><td width="17%" align="right">
		    7天
		    <input type="text" size="5" id="d1W" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-1W"),2) %>"/>
		    </td><td width="17%" align="right">
		    14天
		    <input type="text" size="5" id="d2W" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-2W"),2) %>"/>
		    </td><td width="15%" align="right">
		    1个月
		    <input type="text" size="5" id="d1M" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-1M"),2) %>"/>
		    </td><td width="15%" align="right">
		    3个月
		    <input type="text" size="5" id="d3M" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-3M"),2) %>"/>
		    </td><td width="16%" align="right">
		    6个月
		    <input type="text" size="5" id="d6M" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-6M"),2) %>"/>
		    </td></tr>
		    <tr><td align="right">
		    9个月
		    <input type="text" size="5" id="d9M" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-9M"),2) %>"/>
		    </td><td align="right">
		    1年shibor
		    <input type="text" size="5" id="d1YS" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-1YS"),2) %>"/>
		    </td><td align="right">
		    1年国债
		    <input type="text" size="5" id="d1YG" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-1YG"),2) %>"/>
		    </td><td align="right">
		    2年
		    <input type="text" size="5" id="d2Y" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-2Y"),2) %>"/>
		    </td><td align="right">
		    3年
		    <input type="text" size="5" id="d3Y" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-3Y"),2) %>"/>
		    </td><td align="right">
		    5年
		    <input type="text" size="5" id="d5Y" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-5Y"),2) %>"/>
		    </td></tr>
		    <tr><td align="right">
		    7年
		    <input type="text" size="5" id="d7Y" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-7Y"),2) %>"/>
		    </td><td align="right">
		    10年
		    <input type="text" size="5" id="d10Y" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-10Y"),2) %>"/>
		    </td><td align="right">
		    15年
		    <input type="text" size="5" id="d15Y" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-15Y"),2) %>"/>
		    </td><td align="right">
		    20年
		    <input type="text" size="5" id="d20Y" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-20Y"),2) %>"/>
		    </td><td align="right">
		    30年
		    <input type="text" size="5" id="d30Y" name="adjustValueC2" onblur="isFloat(this,'调整值')" value="<%=CommonFunctions.doublecut(adjustMap.get("02-01-30Y"),2) %>"/>
		    </td><td></td></tr>
	     </table>
<%--	     <div align="center">--%>
<%--	         <input type="button" name="Submit1" class="button" onClick="doSave()" value="保&nbsp;&nbsp;存">--%>
<%--	     </div>--%>
  </body>
  
<script type="text/javascript" language="javascript">
if(<%=curveType%> == '1') {
	$('table1').style.display="block";
	$('table2').style.display="none";
}else if(<%=curveType%> == '2'){
	$('table2').style.display="block";
	$('table1').style.display="none";
}
<%--function doSave(){--%>
<%--	window.parent.doSaveCountyAdjust();--%>
<%--}--%>
</script>
</html>
