
<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.sql.*,java.util.*,com.dhcc.ftp.util.*,com.dhcc.ftp.util.CommonFunctions,com.dhcc.ftp.util.PageUtil,com.dhcc.ftp.entity.FtpBusinessInfo,com.dhcc.ftp.util.*" errorPage="" %>
<html>
<head>
<jsp:include page="commonJs.jsp" />
<title>����ƥ��--����</title>
<style type="text/css">
<!--
.STYLE1 {font-size: medium}
.STYLE2 {font-size: large}
.STYLE3 {font-size: small;color:#2907F0; }
-->
</style>
</head>
<%  
    String checkAll = request.getParameter("checkAll");
    PageUtil ULFtp06Util = (PageUtil)session.getAttribute("ULFtpSuccess06Util"); 
    List<FtpBusinessInfo> list = ULFtp06Util.getList(); 
    String ftpResultDescribe = (String)request.getAttribute("ftpResultDescribe");
%>
<body>
<form name="chooseform" method="post">
<%if(list.size() == 0) {%>
û�ж��۳ɹ��Ľ������
<%}else{ %>
<table width="90%" border="0" align="center" >
      <tr>
        <td align="center">  
           <input name="add" class="button" type="button" id="add" height="20" onClick="ftp_save()" value="��&nbsp;&nbsp;��" />              
           <input type="button" name="Submit1" class="button" onClick="doExport()" value="��&nbsp;&nbsp;��"></div>
       </td>
      </tr>
  </table>
 <table width="2100" cellspacing="0" cellpadding="0" align="center" class="table" id="tbColor">	
	<tr >
        <th width="40">
			���
		</th>
		<th width="80">
			����
		</th>
		<th width="50">
			��Ա
		</th>
		<th width="70">
			�ͻ�����
		</th>
		<th width="50">
			����
		</th>
		<th width="150">
		           ҵ���˺�
		</th>
		<th width="250">
			�ͻ���
		</th>
		<th width="150">
			ҵ������
		</th>
		<th width="270">
			��Ʒ����
		</th>
		<th width="70">
			��������
		</th>
		<th width="110">
			���
		</th>
		<th width="110">
			���
		</th>
		<th width="50">
			����(%)
		</th>
		<th width="70">
			����(��)
		</th>
		<th width="70">
			��������
		</th>
		<th width="70">
			�Ƿ�չ��
		</th>
		<th width="70">
			�弶����
		</th>
		<th width="100">
			���۽��ֵ(%)
		</th>
		<th width="100">
			���۷���
		</th>
		<th width="150">
			������
		</th>
		<th width="80">
			����ϵͳ����
		</th>
	</tr>
	<% 
	 for (int i = 0; i < list.size(); i++){
	 %>
	 <tr>
<%--	 	<td align="center"><input type="checkbox" name="checkbox" value="<%=i%>" /></td>--%>
     	<td align="center"><%=i+1%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getBrNo())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getTel())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getKhjl())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getCurNo())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getAcId())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getCustomName())%></td>
     	<td align="center"><%=CastUtil.trimNull(FtpUtil.getBusinessName(list.get(i).getBusinessNo().toString()))%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getPrdtName())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getOpnDate())%></td>
     	<td align="center"><%=FormatUtil.toMoney(list.get(i).getAmt())%></td>
     	<td align="center"><%=FormatUtil.toMoney(list.get(i).getBal())%></td>
     	<td align="center"><%=list.get(i).getRate() == null ? "" :CommonFunctions.doublecut(Double.valueOf(list.get(i).getRate())*100,3)%></td>
     	<td align="center"><%=list.get(i).getBusinessNo().equals("YW201")?"-":CastUtil.trimNull(list.get(i).getTerm())%></td>
     	<td align="center"><%=list.get(i).getBusinessNo().equals("YW201")?"-":CastUtil.trimNull(list.get(i).getMtrDate())%></td>
     	<td align="center"><%=(!list.get(i).getBusinessNo().equals("YW101"))?"-":(list.get(i).getIsZq().equals("0")?"��":"��")%></td>
     	<td align="center"><%=FtpUtil.getFivSys(list.get(i).getFivSts())%></td>
     	<td align="center"><%if(list.get(i).getFtpPrice()==null){
     		    out.print("");
     		}else if (Double.isNaN(list.get(i).getFtpPrice())){
     			out.print(Double.NaN);
     		}else{
     			out.print(CommonFunctions.doublecut(list.get(i).getFtpPrice()*100,3));
     		}%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getMethodName())%></td>
     	<td align="center"><%=FtpUtil.getCurveName_byCurveNo(list.get(i).getCurveNo())%></td>
     	<td align="center"><%=CastUtil.trimNull(list.get(i).getWrkDate())%></td>
	</tr>
	<% } %>
</table>
<table border="0" width="100%" class="tb1" style="BORDER-COLLAPSE: collapse" bordercolor="#b3b5b0" align="center">
 	<tr><td align="right"><%=ULFtp06Util.getPageLine()%></td></tr>
</table>
<%} %>
		</form>

</body>

<script type="text/javascript">	
function ftp_save(){ 
	parent.parent.parent.parent.parent.openNewDiv();
	var brNo =window.parent.parent.document.getElementById("brNo").value;
	var businessNo =window.parent.parent.document.getElementById("businessNo").value;
	var url = "<%=request.getContextPath()%>/UL06_Save.action?brNo="+brNo+"&businessNo="+businessNo+"&checkAll=<%=checkAll%>";
    new Ajax.Request( 
    url, 
     {   
      method: 'post',   
      parameters: {t:new Date().getTime()},
      onSuccess: function(resp) 
       {  
    	 parent.parent.parent.parent.parent.cancel();
  	   art.dialog({
      	    title:'�ɹ�',
  		    icon: 'succeed',
  		    content: resp.responseText,
  		    ok: function () {
  		    	ok();
  		        return true;
  		    }
   	 });
        }
      }
   );
}
function ok () {
	var brNo =window.parent.parent.document.getElementById("brNo").value;
	var curNo =window.parent.parent.document.getElementById("curNo").value;
	var businessNo =window.parent.parent.document.getElementById("businessNo").value;	 
	var opnDate1=window.parent.parent.document.getElementById("opnDate1").value;
	var opnDate2=window.parent.parent.document.getElementById("opnDate2").value;
	var mtrDate1=window.parent.parent.document.getElementById("mtrDate1").value;
	var mtrDate2=window.parent.parent.document.getElementById("mtrDate2").value;
    //window.parent.location.href = '/ftp/UL06_List.action?isQuery=0&brNo='+brNo+'&curNo='+curNo+'&businessNo='+businessNo+'&opnDate1='+opnDate1+'&opnDate2='+opnDate2+'&mtrDate1='+mtrDate1+'&mtrDate2='+mtrDate2;
    
    //alert(document.getElementById("add").getAttribute("type"));
    document.getElementById("add").style.visibility="hidden";//����ɹ��󽫡����水ť����Ϊ���ɼ� 	
}

function doExport() {
	document.chooseform.action='<%=request.getContextPath()%>/pages/ul06SaveExport.jsp';
	document.chooseform.submit();
}
</script>
</html>
