<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>��Ʒ�ֻ���FTP�����</title>
<jsp:include page="../commonJs.jsp" />
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("cpfjglrbReportList");
Integer isFirst = (Integer)session.getAttribute("cpfjglrbIsFirst");//�Ƿ���ʾ�����ء���ť
String minDate = (String)session.getAttribute("cpfjglrbMinDate");
String maxDate = (String)session.getAttribute("cpfjglrbMaxDate");
String brName = (String)session.getAttribute("cpfjglrbBrName");
String businessName = (String)session.getAttribute("cpfjglrbBusinessName");
String prdtCtgName = (String)session.getAttribute("cpfjglrbPrdtCtgName");
String prdtCtgNo = (String)session.getAttribute("cpfjglrbPrdtCtgNo");
String prdtName = (String)session.getAttribute("cpfjglrbPrdtName");
String prdtNo = (String)session.getAttribute("cpfjglrbPrdtNo");

int days = CommonFunctions.daysSubtract(maxDate, minDate);//����
%>
<br/>
<%if(ylfxBbReportList == null) { %>
   <p>����ͳ�ƴ�������ϵ����Ա����</p>
<%}else{ 
	double zftplr = 0;//�ܵ�ftp����
	for(YlfxBbReport ylfxBbReport : ylfxBbReportList) {
		zftplr += ylfxBbReport.getFtplr();
	}
	System.out.println("zftplr"+zftplr);
%>
<div align="center">������<%= brName%>
&nbsp;&nbsp;&nbsp;&nbsp;����ʱ�Σ�<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
&nbsp;&nbsp;&nbsp;&nbsp;��λ����Ԫ��%(������)</div>
<table id="tableList">
					<thead>
     <tr >
      <th align="center" width="300" >��������</th>
		 <th align="center" width="100" >���</th>
      <th align="center" width="100" >�վ����</th>
      <th align="center" width="100" >����(%)</th>
      <th align="center" width="100" >FTP����</th>
      <th align="center" width="100" >FTP����ռ��(%)</th>
      <th align="center" width="100" >����</th>
      <th align="center" width="80" >��ϸ</th>
       </tr>
					</thead>
					<tbody>
     <%  
         double rjye=0, ftplr=0, lc=0,bal=0;
         for (int i = 0; i < ylfxBbReportList.size(); i++){
        	 YlfxBbReport entity = ylfxBbReportList.get(i);
        	 rjye += entity.getRjye();
        	 ftplr += entity.getFtplr();
        	 bal+=entity.getBal();
     %>
        <tr>
     	<td align="center"><%=entity.getBrName()%>[<%=entity.getBrNo()%>]</td>
			<td align="center"><%=FormatUtil.toMoney(entity.getBal()/10000)%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getRjye()/10000)%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getLc()*100,6)%></td>
		<td align="center"><%=FormatUtil.toMoney(entity.getFtplr()/10000)%></td>
		<td align="center"><%=CommonFunctions.doublecut(entity.getFtplr()/zftplr*100,2)%></td>
		<td align="center"><%=i+1%></td>
		<td align="center">
		<%if(!entity.isLeaf()) { %>
		<a href="javascript:doQuery('<%=entity.getBrNo() %>','<%=entity.getManageLvl() %>')">��ϸ</a>
		<%}else{ %>
		��
		<%} %>
		</td>
		</tr>
		<%} %>
     <tr>
     	<td align="center"><font style="font-weight: bold">�ϼ�</font></td>
		 <td align="center"><font style="font-weight: bold"><%=FormatUtil.toMoney(bal/10000)%></font></td>
		<td align="center"><font style="font-weight: bold"><%=FormatUtil.toMoney(rjye/10000)%></font></td>
		<td align="center"><font style="font-weight: bold"><%=CommonFunctions.doublecut(rjye==0?0.00:ftplr/rjye*360/days*100,6)%></font></td>
		<td align="center"><font style="font-weight: bold"><%=FormatUtil.toMoney(ftplr/10000)%></font></td>
		<td align="center"><font style="font-weight: bold"><%=CommonFunctions.doublecut(100,2)%></font></td>
		<td align="center"><font style="font-weight: bold">-</font></td>
		<td align="center"><font style="font-weight: bold">-</font></td>
		</tr>
					</tbody>
				</table>
<div align="center" width="1770">
<input type="button" name="Submit1" style="color: #fff; background-image: url(<%=request.getContextPath()%>/pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					  onClick="doExport()" value="��&nbsp;&nbsp;��">
<%if(isFirst == 0){ %>
<input type="button" name="Submit2" style="color: #fff; background-image: url(pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					   onclick="doBack()" value="��&nbsp;&nbsp;��">	
<%} %>
</div>
<%} %>
</form>
</body>

<script type="text/javascript">
window.onload=function(){
	j(function(){
	    j('#tableList').flexigrid({
	    		height: 230,width:900,
	    		title: '<%=(prdtNo==null||prdtNo.equals(""))?((prdtCtgNo==null||prdtCtgNo.equals(""))?businessName:prdtCtgName):prdtName%>-��Ʒ�ֻ���FTP�����'});
	});
}

j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/reportBb_cpfjglrb_export.jsp';
	window.parent.document.thisform.submit();
}
function doQuery(brNo, manageLvl){
	   var date = <%=maxDate%>;
	   var assessScope = window.parent.document.getElementById("assessScope").value;
		var businessNo =window.parent.document.getElementById("businessNo").value;
		var prdtCtgNo =window.parent.document.getElementById("prdtCtgNo").value;
		var prdtNo =window.parent.document.getElementById("prdtNo").value;
		var jgName="<%=brName%>";
	   document.location.href ='<%=request.getContextPath()%>/REPORTBB_cpfjglrbReport.action?isMx=1&brNo='+brNo+'&manageLvl='+manageLvl+'&date='+date+'&assessScope='+assessScope+'&businessNo='+businessNo+'&prdtCtgNo='+prdtCtgNo+'&prdtNo='+prdtNo+'&isFirst=0'+'&brName='+encodeURI(jgName);
	   parent.parent.parent.parent.openNewDiv();
}
function doBack(){
	   var brNo = window.parent.document.getElementById("brNo").value;
	   var manageLvl = window.parent.document.getElementById("manageLvl").value;
	   var date = window.parent.document.getElementById("date").value;
	   var assessScope = window.parent.document.getElementById("assessScope").value;
		var businessNo =window.parent.document.getElementById("businessNo").value;
		var prdtCtgNo =window.parent.document.getElementById("prdtCtgNo").value;
		var prdtNo =window.parent.document.getElementById("prdtNo").value;
		var jgName="<%=brName%>";
	   document.location.href ='<%=request.getContextPath()%>/REPORTBB_cpfjglrbReport.action?isMx=0&brNo='+brNo+'&manageLvl='+manageLvl+'&date='+date+'&assessScope='+assessScope+'&businessNo='+businessNo+'&prdtCtgNo='+prdtCtgNo+'&prdtNo='+prdtNo+'&isFirst=1'+'&brName='+encodeURI(jgName)+"&isBack=true";
	   parent.parent.parent.parent.openNewDiv();
}
</script>
</html>
