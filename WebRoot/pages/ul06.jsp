<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@ page import="java.util.*,java.text.*,com.dhcc.ftp.entity.TelMst,com.dhcc.ftp.util.CommonFunctions" errorPage="" %>
<html>
<head>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<%//long date=CommonFunctions.GetDBSysDate(); 
  long date=CommonFunctions.GetDBSysDate();
  if(date!=20121029){//-----����
  	//ÿ���µ�(���ڶ���Ϊ1��)����Դ����  ��Ҫ����������Ϊ���¡�������
  	long next_date=CommonFunctions.pub_base_deadlineD(date, 1);
  	if(next_date%100!=1){//date��Ϊ�µף����Ϊ�ϸ��µ�
		 date=CommonFunctions.pub_base_deadlineD((date/100*100+1), -1);//date���³�-1����Ϊ�ϸ��µ�
  	}
  }//------- 
  long start_date=date/100*100+1;
//String brNo = ((TelMst) request.getSession().getAttribute("userBean")).getBrMst().getBrNo();
String brNo = "";
%>
<title>����ƥ��->����ƥ�䶨�ۼ���</title>
</head>
  <body>
  <div class="cr_header">
			��ǰλ�ã�����ƥ��->����ƥ�䶨�ۼ���
</div>

    <iframe src="<%=request.getContextPath()%>/pages/ul06Search.jsp" id="upframe" width="1020" height="110" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes" align="middle"></iframe> 
    <!--<iframe src="UL06_List.action?opnDate1=<%=start_date%>&isQuery=1&opnDate2=<%=date %>&brNo=<%=brNo%>" id="downframe" width="1040" height="350" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes" align="middle"></iframe>-->
    <iframe src="" id="downframe" width="1040" height="300" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="yes" allowtransparency="yes" align="middle"></iframe> 
  </body>
</html>

