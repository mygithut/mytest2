<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.util.List" %>
<%@ page import="com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.entity.YlfxBbReport,com.dhcc.ftp.util.FormatUtil"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>全行所有机构FTP利润汇总表</title>
	<jsp:include page="../commonJs.jsp" />
</head>
<body>
<form id="form1" name="form1" method="get" action="">
<%
List<YlfxBbReport> ylfxBbReportList = (List<YlfxBbReport>)session.getAttribute("qhsyjglrhzbReportList");
Integer isFirst = (Integer)session.getAttribute("qhsyjglrhzbIsFirst");//是否显示“返回”按钮
String minDate = (String)session.getAttribute("qhsyjglrhzbMinDate");
String maxDate = (String)session.getAttribute("qhsyjglrhzbMaxDate");
String brName = (String)session.getAttribute("qhsyjglrhzbBrName");

int days = CommonFunctions.daysSubtract(maxDate, minDate);//天数
%>
<br/>
<%if(ylfxBbReportList == null) { %>
   <p>数据统计错误，请联系管理员！！</p>
<%}else{ %>
<div align="center">机构：<%= brName%>
&nbsp;&nbsp;&nbsp;&nbsp;报表时段：<%= CommonFunctions.dateModifyD(minDate,1)%>-<%= maxDate%>
&nbsp;&nbsp;&nbsp;&nbsp;单位：万元，%(年利率)</div>
<%double zcrjyehj=0,zcftplrhj=0,zclchj=0,fzrjyehj=0,fzftplrhj=0,fzlchj=0,ftplrhjsum=0,zcbalhj=0,fzbalhj=0,
		 grhqbal=0,grdqbal=0,dwhqbal=0,dwdqbal=0,czxbal=0,yhkbal=0,yjhkbal=0,bzjbal=0,
		 grhqrjye=0,grdqrjye=0,dwhqrjye=0,dwdqrjye=0,czxrjye=0,yhkrjye=0,yjhkrjye=0,bzjrjye=0
	; %>
<table id="tableList">
	<thead>
     <tr >
      <th align="center" width="250" >机构名称</th>
      <th align="right" width="85" ><font  style="text-align: center ; width: 100% ;display: inline-block;">资产余额</font></th>
      <th align="right" width="85" ><font  style="text-align: center ; width: 100% ;display: inline-block;">资产日均余额</font></th>
      <th align="right" width="80"><font style="text-align: center ; width: 100% ;display: inline-block; ">资产利差(%)</font></th>
      <th align="right" width="80" ><font style="text-align: center ; width: 100% ;display: inline-block; ">资产FTP利润</font></th>
		 <th align="right" width="105" ><font style="text-align: center ;  width: 100% ;display: inline-block;">负债余额</font></th>
        <%-- <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">个人活期余额</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">个人定期余额</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">单位活期余额</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">单位定期余额</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">财政性存款余额</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">银行卡存款余额</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">应解汇款余额</font></th>
		 <th align="right" width="105" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">保证金存款余额</font></th>

		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">日均余额</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">个人活期日均余额</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">个人定期日均余额</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">单位活期日均余额</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">单位定期日均余额</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">财政性存款日均余额</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">银行卡存款日均余额</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">应解汇款日均余额</font></th>
		 <th align="right" width="125" ><font style="text-align: center ; font-weight: bold; width: 100% ;display: inline-block;">保证金存款日均余额</font></th>--%>
		 <th align="right" width="125" ><font style="text-align: center ;  width: 100% ;display: inline-block;">负债日均余额</font></th>
      <th align="right" width="80" ><font style="text-align: center ; width: 100% ;display: inline-block; ">负债利差(%)</font></th>
      <th align="right" width="80" ><font style="text-align: center ; width: 100% ;display: inline-block; ">负债FTP利润</font></th>
      <th align="right" width="85" ><font style="text-align: center ;  width: 100% ;display: inline-block;">FTP利润合计</font></th>
      
      <th align="center" width="80" >明细</th>
       </tr>
       </thead>
	<tbody>
     <% 
         for (YlfxBbReport entity:ylfxBbReportList){
        	 zcrjyehj += entity.getZcrjye();
        	 zcbalhj += entity.getZcbal();//资产余额
 			 zcftplrhj += entity.getZcftplr();
 			 zclchj += entity.getZclc();
 			 fzrjyehj += entity.getFzrjye();
			 fzftplrhj += entity.getFzftplr();
			 fzlchj += entity.getFzlc();
			 ftplrhjsum += entity.getFtplrhj();
			 fzbalhj += entity.getFzbal();//负债余额
			 grhqbal += entity.getGrhqbal();//个人活期余额
			 grdqbal += entity.getGrdqbal();//负债余额
			 dwhqbal += entity.getDwhqbal();//负债余额
			 dwdqbal += entity.getDwdqbal();//负债余额
			 czxbal += entity.getCzxbal();//负债余额
			 yhkbal += entity.getYhkbal();//负债余额
			 yjhkbal += entity.getYjhkbal();//负债余额
			 bzjbal += entity.getBzjbal();//负债余额

			 grhqrjye += entity.getGrhqrjye();//个人活期日均余额
			 grdqrjye += entity.getGrdqrjye();//负债日均余额
			 dwhqrjye += entity.getDwhqrjye();//负债日均余额
			 dwdqrjye += entity.getDwdqrjye();//负债日均余额
			 czxrjye += entity.getCzxrjye();//负债日均余额
			 yhkrjye += entity.getYhkrjye();//负债日均余额
			 yjhkrjye += entity.getYjhkrjye();//负债日均余额
			 bzjrjye += entity.getBzjrjye();//负债日均余额


     %> 
        <tr>
     	<td ><%=entity.getBrName()%><%=entity.getBrNo().equals("")?"":"["+entity.getBrNo()+"]"%></td>
		<td ><font ><%=FormatUtil.toMoney(entity.getZcbal()/10000)%></font></td>
		<td ><font ><%=FormatUtil.toMoney(entity.getZcrjye()/10000)%></font></td>
		<td ><%=CommonFunctions.doublecut(entity.getZclc()*100,6)%></td>
		<td ><%=FormatUtil.toMoney(entity.getZcftplr()/10000)%></td>

		<td ><font ><%=FormatUtil.toMoney(entity.getFzbal()/10000)%></font></td>
		<%--	<td ><font ><%=FormatUtil.toMoney(entity.getGrhqbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getGrdqbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getDwhqbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getDwdqbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getCzxbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getYhkbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getYjhkbal()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getBzjbal()/10000)%></font></td>--%>
		<td ><font ><%=FormatUtil.toMoney(entity.getFzrjye()/10000)%></font></td>
			<%--<td ><font ><%=FormatUtil.toMoney(entity.getGrhqrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getGrdqrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getDwhqrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getDwdqrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getCzxrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getYhkrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getYjhkrjye()/10000)%></font></td>
			<td ><font ><%=FormatUtil.toMoney(entity.getBzjrjye()/10000)%></font></td>--%>
		<td ><%=CommonFunctions.doublecut(entity.getFzlc()*100,6)%></td>
		<td ><%=FormatUtil.toMoney(entity.getFzftplr()/10000)%></td>
		<td ><font ><%=FormatUtil.toMoney(entity.getFtplrhj()/10000)%></font></td>
		<td align="center">
		<%if(!entity.isLeaf()) { %>
		<a href="javascript:doQuery('<%=entity.getBrNo() %>','<%=entity.getManageLvl() %>')">明细</a>
		<%}else{ %>
		无
		<%} %>
		</td>
		</tr>
     <% }%>
     <tr>
  	 <td ><font >合计</font></td>
		 <td ><font style="font-weight: bold" ><%=FormatUtil.toMoney(zcbalhj/10000)%></font></td>
		<td ><font  style="font-weight: bold"><%=FormatUtil.toMoney(zcrjyehj/10000)%></font></td>
		<td ><font  style="font-weight: bold"><%=CommonFunctions.doublecut(zcrjyehj==0?0.00:zcftplrhj/zcrjyehj*360/days*100,3)%></font></td>
		<td ><font  style="font-weight: bold"><%=FormatUtil.toMoney(zcftplrhj/10000)%></font></td>

		 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(fzbalhj/10000)%></font></td>

		<%-- <td ><font ><%=FormatUtil.toMoney(grhqbal/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(grdqbal/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(dwhqbal/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(dwdqbal/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(czxbal/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(yhkbal/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(yjhkbal/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(bzjbal/10000)%></font></td>--%>

		 <td ><font style="font-weight: bold"><%=FormatUtil.toMoney(fzrjyehj/10000)%></font></td>

		<%-- <td ><font ><%=FormatUtil.toMoney(grhqrjye/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(grdqrjye/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(dwhqrjye/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(dwdqrjye/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(czxrjye/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(yhkrjye/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(yjhkrjye/10000)%></font></td>
		 <td ><font ><%=FormatUtil.toMoney(bzjrjye/10000)%></font></td>--%>

		<td ><font style="font-weight: bold"><%=CommonFunctions.doublecut(fzrjyehj==0?0.00:fzftplrhj/fzrjyehj*360/days*100,3)%></font></td>
		<td ><font style="font-weight: bold"><%=FormatUtil.toMoney(fzftplrhj/10000)%></font></td>
		<td ><font style="font-weight: bold"><%=FormatUtil.toMoney(ftplrhjsum/10000)%></font></td>
		<td ><font style="font-weight: bold">-</font></td>
		</tr>
     </tbody>
</table>
<div align="center" width="1770">
<input type="button" name="Submit1" style="color: #fff; background-image: url(<%=request.getContextPath()%>/pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					  onClick="doExport()" value="导&nbsp;&nbsp;出">
<%if(isFirst == 0){ %>
<input type="button" name="Submit2" style="color: #fff; background-image: url(pages/themes/green/img/red.png); border: 1px solid red; padding-top: 3px; cursor: hand;"
					   onclick="doBack()" value="返&nbsp;&nbsp;回">	
<%} %>
</div>
<%} %>
</form>
</body>

<script type="text/javascript">
window.onload=function(){
	j(function(){
	    j('#tableList').flexigrid({
	    		height: 230,width:950,
	    		title: '全行所有机构FTP利润汇总表'});
	});
}

j(document).ready(function(){ 
	parent.parent.parent.parent.cancel();
});
function doExport() {
	window.parent.document.thisform.action='<%=request.getContextPath()%>/pages/tjbb/reportBb_qhsyjglrhzb_export.jsp';
	window.parent.document.thisform.submit();
}

function doQuery(brNo, manageLvl){
	   var date = <%=maxDate%>;
	   var assessScope = window.parent.document.getElementById("assessScope").value;
	   var jgName="<%= brName%>";
	   document.location.href ='<%=request.getContextPath()%>/REPORTBB_qhsyjglrhzbReport.action?isMx=1&brNo='+brNo+'&manageLvl='+manageLvl+'&date='+date+'&assessScope='+assessScope+'&isFirst=0'+'&brName='+encodeURI(jgName);
	   parent.parent.parent.parent.openNewDiv();
}

function doBack(){
	   var brNo = window.parent.document.getElementById("brNo").value;
	   var manageLvl = window.parent.document.getElementById("manageLvl").value;
	   var date = window.parent.document.getElementById("date").value;
	   var assessScope = window.parent.document.getElementById("assessScope").value;
	  /* var brCountLvl = window.parent.document.getElementById("brCountLvl").value;*/
	   var jgName="<%= brName%>";
	   document.location.href ='<%=request.getContextPath()%>/REPORTBB_qhsyjglrhzbReport.action?isMx=0&brNo='+brNo+'&manageLvl='+manageLvl+'&date='+date+'&assessScope='+assessScope+'&isFirst=1&brCountLvl=1&brName='+encodeURI(jgName)+"&isBack=true";
	   parent.parent.parent.parent.openNewDiv();
}
</script>
</html>
