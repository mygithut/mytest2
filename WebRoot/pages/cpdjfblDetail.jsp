<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="com.dhcc.ftp.util.*,com.dhcc.ftp.entity.FtpProductMethodRel,java.util.*"%>
	<head>
		<title>��Ʒ���۹�����</title>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/css/inpage.css"
			type="text/css">
		<jsp:include page="commonJs.jsp" />
	</head>
	<body>
	    <div class="cr_header">
			��ǰλ�ã�����ƥ��->��Ʒ���۹�����
		</div>
		<form id="form1" name="form1" method="get" action="">
			<%
			double[][] result = (double[][]) session.getAttribute("result");
			String[][] prdtInfo = (String[][])session.getAttribute("prdtInfo");
			String[] termType = (String[]) session.getAttribute("termType");
			String currentDate = (String) request.getAttribute("currentDate");
			Integer isSave = (Integer) request.getAttribute("isSave");
			%>
		<div style="width:1000px;height:370px;overflow: auto;" id="dv">
		<table id="tableList" class="table" align="left" width="4500" 
							cellpadding="0" cellspacing="0">
		        <tr>
		            <td colspan="<%=termType.length+2 %>" class="middle_header" style="height:25px">
		                <font style="padding-left: 10px; color: #333; font-size: 12px; font-weight: bold">
		                                               ���۹������-��������<%=currentDate %>
		                </font>
		            </td>
		        </tr>
				<tr>
				    <th width="60" class="td_relative">
						���
					</th>
				    <th width="300" class="td_relative">
						��Ʒ/����
					</th>
					<th width="60" class="td_relative">
						����
					</th>
				<%for(int i = 1; i < termType.length; i++) {%>
				    <th width="60" class="td_relative">
						<%=termType[i] %>
					</th>
				<%} %>
				</tr>
				<%
					for (int j = 0; j < result.length; j++) {
				%>
				<tr>
				    <td align="center" width="60">
						<%=j+1%>
					</td>
					<td align="center" width="300">
						<%=prdtInfo[j][1].indexOf("-������")==-1?prdtInfo[j][1]:prdtInfo[j][1].substring(0,prdtInfo[j][1].indexOf("-������"))+"["+prdtInfo[j][0]+"]"%><font color="blue"><%=prdtInfo[j][1].indexOf("-������")==-1?"":"-������" %></font>
					</td>
					<%for(int k = 0; k < result[j].length; k++) { %>
					<td align="center" width="60">
						<%if (Double.isNaN(result[j][k])){ out.print("-");
     		            }else{ out.print(CommonFunctions.doublecut(result[j][k]*100,3)+"%");
     		            }%>
					</td>
					<%} %>
				</tr>
				<%
					}
				%>
			</table>
			</div>
			
		<DIV align="center"><%if(isSave==1) {%><input name="save" class="button" type="button" id="save"
										height="20" onClick="doSave()" value="��&nbsp;&nbsp;��" />
										&nbsp;
										<%} %>
						  <input name="export" class="button" type="button" id="export"
										height="20" onClick="doExport()" value="��&nbsp;&nbsp;��" />
										&nbsp;
						  <input name="back" class="button" type="button" id="back"
										height="20" onClick="history.back()" value="��&nbsp;&nbsp;��" />
									</DIV>
		</form>
	</body>
	<script type="text/javascript">
	jQuery(document).ready(function(){ 
		parent.parent.parent.cancel();
	});
	function doExport() {
		document.form1.action='<%=request.getContextPath()%>/pages/cpdjfblDetailExport.jsp';
		document.form1.submit();
	}
	function doSave() {
		  parent.parent.parent.openNewDiv();
		  var url = "<%=request.getContextPath()%>/cpdjfbl_save.action";
		  new Ajax.Request( 
		        url, 
		         {  
		          method: 'post',   
		          parameters: {
		            t:new Date().getTime()
		          },
		          onSuccess: function(){
		      		   parent.parent.parent.cancel();
			    	   art.dialog({
		              	    title:'�ɹ�',
		          		    icon: 'succeed',
		          		    content: '����ɹ���',
		          		    ok: true
		           	   });
			     }
		 });
	}
   </script>
        <style type="text/css">
             .td_relative,.middle_header {
	            background-color: #CCCCCC;
	            top: expression(document.getElementById ( "dv") . scrollTop-1 );
	            position: relative;
	            z-index: 1
             }
        </style>
</html>
