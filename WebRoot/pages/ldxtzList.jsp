<%@page contentType="text/html;charset=utf-8" pageEncoding="utf-8"%>
<%@page	import="java.util.ArrayList,java.util.List,com.dhcc.ftp.util.*"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.dhcc.ftp.entity.Ftp1PrdtLdxAdjust"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Map<String,Ftp1PrdtLdxAdjust> map=(Map<String,Ftp1PrdtLdxAdjust>)request.getAttribute("LDMap");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>流动性调整</title>
		<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
		<jsp:include page="commonJs.jsp" />
		<jsp:include page="commonDatePicker.jsp" />
		<style type="text/css">
		input{ width: 50px; border: 1px solid #cfcfcf;}
		.table td{ text-align: center;}
		.table td input { text-align: center;}
		</style>
	</head>
	<body>
	<div class="cr_header"  style="margin: 0px 0px 20px 0px">
			当前位置：期限匹配->流动性调整
		</div>
		<form id="form1" name="form1" method="post" action="<%=basePath%>/LDXTZ_save.action">
				<table class="table"  cellpadding="0" cellspacing="0" align="center">
					<tr>
						<th colspan="13" style="height: 35px; " class="ClASSTD" align="left">流动性调整值 (单位：BP)</th>
					</tr>
<%--					<tr>--%>
<%--							<th width="100">--%>
<%--								标准期限--%>
<%--							</th>--%>
<%--							<th width="100">--%>
<%--								活期--%>
<%--							</th>--%>
<%--							<th width="100"></th>--%>
<%--							<th width="100"></th>--%>
<%--							<th width="100"></th>--%>
<%--							<th width="100"></th>--%>
<%--							<th width="100"></th>--%>
<%--							<th width="100"></th>--%>
<%--							<th width="100"></th>--%>
<%--							<th width="100"></th>--%>
<%--							<th width="100"></th>--%>
<%--							<th width="100"></th>--%>
<%--							<th width="100"></th>--%>
<%--						</tr>--%>
<%--					<tr>--%>
<%--						<th>调整值</th>--%>
<%--						<td>--%>
							<!-- 0M -->
							<input  name="pos[0].adjustId" id="pos[0].id"  value="<%=map.get("0")==null?"":map.get("0").getAdjustId() %>" type="hidden"/>
							<input  name="pos[0].termType" id="pos[0].termType"  value="0" type="hidden"/>
							<input  name="pos[0].adjustValue" id="0M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("0")==null?0:CommonFunctions.doublecut(map.get("0").getAdjustValue()*10000,1) %>" type="hidden"/>
<%--						</td>--%>
<%--						<td></td>--%>
<%--						<td></td>--%>
<%--						<td></td>--%>
<%--						<td></td>--%>
<%--						<td></td>--%>
<%--						<td></td>--%>
<%--						<td></td>--%>
<%--						<td></td>--%>
<%--						<td></td>--%>
<%--						<td></td>--%>
<%--						<td></td>--%>
<%--					</tr>--%>
						<tr>
							<th width="100">
								标准期限
							</th>
							<th width="100">
								1M
							</th>
							<th width="100">
								2M
							</th>
							<th width="100">
								3M
						    </th>
							<th width="100">
								4M
							</td>
							<th width="100">
								5M
							</th>
						     <th width="100">
								6M
						    </th>
						     <th width="100">
								7M
						    </th>
						     <th width="100">
								8M
						    </th>
						     <th width="100">
								9M
						    </th>
						     <th width="100">
								10M
						    </th>
						     <th width="100">
								11M
						    </th>
						     <th width="100">
								1Y
						    </th>
						</tr>
					<tr>
						<th>调整值</th>
						<td>
							<!-- 1M -->
							<input  name="pos[1].adjustId" id="pos[1].id"  value="<%=map.get("1")==null?"":map.get("1").getAdjustId() %>" type="hidden"/>
							<input  name="pos[1].termType" id="pos[1].termType"  value="1" type="hidden"/>
							<input  name="pos[1].adjustValue" id="1M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("1")==null?0:CommonFunctions.doublecut(map.get("1").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 2M -->
							<input  name="pos[2].adjustId" id="pos[2].id"  value="<%=map.get("2")==null?"":map.get("2").getAdjustId() %>" type="hidden"/>
							<input  name="pos[2].termType" id="pos[2].termType"  value="2" type="hidden"/>
							<input  name="pos[2].adjustValue" id="2M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("2")==null?0:CommonFunctions.doublecut(map.get("2").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 3M -->
							<input  name="pos[3].adjustId" id="pos[3].id"  value="<%=map.get("3")==null?"":map.get("3").getAdjustId()%>" type="hidden"/>
							<input  name="pos[3].termType" id="pos[3].termType"  value="3" type="hidden"/>
							<input  name="pos[3].adjustValue" id="3M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("3")==null?0:CommonFunctions.doublecut(map.get("3").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 4M -->
							<input  name="pos[4].adjustId" id="pos[4].id"  value="<%=map.get("4")==null?"":map.get("4").getAdjustId() %>" type="hidden"/>
							<input  name="pos[4].termType" id="pos[4].termType"  value="4" type="hidden"/>
							<input  name="pos[4].adjustValue" id="4M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("4")==null?0:CommonFunctions.doublecut(map.get("4").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 5M -->
							<input  name="pos[5].adjustId" id="pos[5].id"  value="<%=map.get("5")==null?"":map.get("5").getAdjustId() %>" type="hidden"/>
							<input  name="pos[5].termType" id="pos[5].termType"  value="5" type="hidden"/>
							<input  name="pos[5].adjustValue" id="5M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("5")==null?0:CommonFunctions.doublecut(map.get("5").getAdjustValue()*10000 ,1)%>" type="text"/>
						</td>
						<td>
							<!-- 6M -->
							<input  name="pos[6].adjustId" id="pos[6].id"  value="<%=map.get("6")==null?"":map.get("6").getAdjustId() %>" type="hidden"/>
							<input  name="pos[6].termType" id="pos[6].termType"  value="6" type="hidden"/>
							<input  name="pos[6].adjustValue" id="6M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("6")==null?0:CommonFunctions.doublecut(map.get("6").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 7M -->
							<input  name="pos[7].adjustId" id="pos[7].id"  value="<%=map.get("7")==null?"":map.get("7").getAdjustId() %>" type="hidden"/>
							<input  name="pos[7].termType" id="pos[7].termType"  value="7" type="hidden"/>
							<input  name="pos[7].adjustValue" id="7M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("7")==null?0:CommonFunctions.doublecut(map.get("7").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 8M -->
							<input  name="pos[8].adjustId" id="pos[8].id"  value="<%=map.get("8")==null?"":map.get("8").getAdjustId() %>" type="hidden"/>
							<input  name="pos[8].termType" id="pos[8].termType"  value="8" type="hidden"/>
							<input  name="pos[8].adjustValue" id="8M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("8")==null?0:CommonFunctions.doublecut(map.get("8").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 9M -->
							<input  name="pos[9].adjustId" id="pos[9].id"  value="<%=map.get("9")==null?"":map.get("9").getAdjustId() %>" type="hidden"/>
							<input  name="pos[9].termType" id="pos[9].termType"  value="9" type="hidden"/>
							<input  name="pos[9].adjustValue" id="9M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("9")==null?0:CommonFunctions.doublecut(map.get("9").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 10M -->
							<input  name="pos[10].adjustId" id="pos[10].id"  value="<%=map.get("10")==null?"":map.get("10").getAdjustId() %>" type="hidden"/>
							<input  name="pos[10].termType" id="pos[10].termType"  value="10" type="hidden"/>
							<input  name="pos[10].adjustValue" id="10M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("10")==null?0:CommonFunctions.doublecut(map.get("10").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 11M -->
							<input  name="pos[11].adjustId" id="pos[11].id"  value="<%=map.get("11")==null?"":map.get("11").getAdjustId() %>" type="hidden"/>
							<input  name="pos[11].termType" id="pos[11].termType"  value="11" type="hidden"/>
							<input  name="pos[11].adjustValue" id="11M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("11")==null?0:CommonFunctions.doublecut(map.get("11").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 12M -->
							<input  name="pos[12].adjustId" id="pos[12].id"  value="<%=map.get("12")==null?"":map.get("12").getAdjustId() %>" type="hidden"/>
							<input  name="pos[12].termType" id="pos[12].termType"  value="12" type="hidden"/>
							<input  name="pos[12].adjustValue" id="12M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("12")==null?0:CommonFunctions.doublecut(map.get("12").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
					</tr>



					<tr>
							<th width="100">
								标准期限
							</th>
							<th >
								13M
							</th>
							<th >
								14M
							</th>
							<th >
								15M
						    </th>
							<th >
								16M
							</td>
							<th >
								17M
							</th>
						     <th >
								18M
						    </th>
						     <th >
								19M
						    </th>
						     <th >
								20M
						    </th>
						     <th >
								21M
						    </th>
						     <th >
								22M
						    </th>
						     <th >
								23M
						    </th>
						     <th >
								2Y
						    </th>
						</tr>
					<tr>
						<th>调整值</th>
						<td>
							<!-- 13M -->
							<input  name="pos[13].adjustId" id="pos[13].id"  value="<%=map.get("13")==null?"":map.get("13").getAdjustId() %>" type="hidden"/>
							<input  name="pos[13].termType" id="pos[13].termType"  value="13" type="hidden"/>
							<input  name="pos[13].adjustValue" id="13M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("13")==null?0:CommonFunctions.doublecut(map.get("13").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 14M -->
							<input  name="pos[14].adjustId" id="pos[14].id"  value="<%=map.get("14")==null?"":map.get("14").getAdjustId() %>" type="hidden"/>
							<input  name="pos[14].termType" id="pos[14].termType"  value="14" type="hidden"/>
							<input  name="pos[14].adjustValue" id="14M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("14")==null?0:CommonFunctions.doublecut(map.get("14").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 15M -->
							<input  name="pos[15].adjustId" id="pos[15].id"  value="<%=map.get("15")==null?"":map.get("15").getAdjustId()%>" type="hidden"/>
							<input  name="pos[15].termType" id="pos[15].termType"  value="15" type="hidden"/>
							<input  name="pos[15].adjustValue" id="15M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("15")==null?0:CommonFunctions.doublecut(map.get("15").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 16M -->
							<input  name="pos[16].adjustId" id="pos[16].id"  value="<%=map.get("16")==null?"":map.get("16").getAdjustId() %>" type="hidden"/>
							<input  name="pos[16].termType" id="pos[16].termType"  value="16" type="hidden"/>
							<input  name="pos[16].adjustValue" id="16M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("16")==null?0:CommonFunctions.doublecut(map.get("16").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 17M -->
							<input  name="pos[17].adjustId" id="pos[17].id"  value="<%=map.get("17")==null?"":map.get("17").getAdjustId() %>" type="hidden"/>
							<input  name="pos[17].termType" id="pos[17].termType"  value="17" type="hidden"/>
							<input  name="pos[17].adjustValue" id="17M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("17")==null?0:CommonFunctions.doublecut(map.get("17").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 18M -->
							<input  name="pos[18].adjustId" id="pos[18].id"  value="<%=map.get("18")==null?"":map.get("18").getAdjustId() %>" type="hidden"/>
							<input  name="pos[18].termType" id="pos[18].termType"  value="18" type="hidden"/>
							<input  name="pos[18].adjustValue" id="18M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("18")==null?0:CommonFunctions.doublecut(map.get("18").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 19M -->
							<input  name="pos[19].adjustId" id="pos[19].id"  value="<%=map.get("19")==null?"":map.get("19").getAdjustId() %>" type="hidden"/>
							<input  name="pos[19].termType" id="pos[19].termType"  value="19" type="hidden"/>
							<input  name="pos[19].adjustValue" id="19M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("19")==null?0:CommonFunctions.doublecut(map.get("19").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 20M -->
							<input  name="pos[20].adjustId" id="pos[20].id"  value="<%=map.get("20")==null?"":map.get("20").getAdjustId() %>" type="hidden"/>
							<input  name="pos[20].termType" id="pos[20].termType"  value="20" type="hidden"/>
							<input  name="pos[20].adjustValue" id="20M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("20")==null?0:CommonFunctions.doublecut(map.get("20").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 21M -->
							<input  name="pos[21].adjustId" id="pos[21].id"  value="<%=map.get("21")==null?"":map.get("21").getAdjustId() %>" type="hidden"/>
							<input  name="pos[21].termType" id="pos[21].termType"  value="21" type="hidden"/>
							<input  name="pos[21].adjustValue" id="21M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("21")==null?0:CommonFunctions.doublecut(map.get("21").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 22M -->
							<input  name="pos[22].adjustId" id="pos[22].id"  value="<%=map.get("22")==null?"":map.get("22").getAdjustId() %>" type="hidden"/>
							<input  name="pos[22].termType" id="pos[22].termType"  value="22" type="hidden"/>
							<input  name="pos[22].adjustValue" id="22M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("22")==null?0:CommonFunctions.doublecut(map.get("22").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 23M -->
							<input  name="pos[23].adjustId" id="pos[23].id"  value="<%=map.get("23")==null?"":map.get("23").getAdjustId() %>" type="hidden"/>
							<input  name="pos[23].termType" id="pos[23].termType"  value="23" type="hidden"/>
							<input  name="pos[23].adjustValue" id="23M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("23")==null?0:CommonFunctions.doublecut(map.get("23").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 24M -->
							<input  name="pos[24].adjustId" id="pos[24].id"  value="<%=map.get("24")==null?"":map.get("24").getAdjustId() %>" type="hidden"/>
							<input  name="pos[24].termType" id="pos[24].termType"  value="24" type="hidden"/>
							<input  name="pos[24].adjustValue" id="24M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("24")==null?0:CommonFunctions.doublecut(map.get("24").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
					</tr>
					<tr>
							<th >
								标准期限
							</th>
							<th >
								25M
							</th>
							<th >
								26M
							</th>
							<th >
								27M
						    </th>
							<th >
								28M
							</td>
							<th >
								29M
							</th>
						     <th >
								30M
						    </th>
						     <th >
								31M
						    </th>
						     <th >
								32M
						    </th>
						     <th >
								33M
						    </th>
						     <th >
								34M
						    </th>
						     <th >
								35M
						    </th>
						     <th >
								3Y
						    </th>
						</tr>




					<tr>
						<th>调整值</th>
						<td>
							<!-- 25M -->
							<input  name="pos[25].adjustId" id="pos[25].id"  value="<%=map.get("25")==null?"":map.get("25").getAdjustId() %>" type="hidden"/>
							<input  name="pos[25].termType" id="pos[25].termType"  value="25" type="hidden"/>
							<input  name="pos[25].adjustValue" id="25M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("25")==null?0:CommonFunctions.doublecut(map.get("25").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 26M -->
							<input  name="pos[26].adjustId" id="pos[26].id"  value="<%=map.get("26")==null?"":map.get("26").getAdjustId() %>" type="hidden"/>
							<input  name="pos[26].termType" id="pos[26].termType"  value="26" type="hidden"/>
							<input  name="pos[26].adjustValue" id="26M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("26")==null?0:CommonFunctions.doublecut(map.get("26").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 27M -->
							<input  name="pos[27].adjustId" id="pos[27].id"  value="<%=map.get("27")==null?"":map.get("27").getAdjustId()%>" type="hidden"/>
							<input  name="pos[27].termType" id="pos[27].termType"  value="27" type="hidden"/>
							<input  name="pos[27].adjustValue" id="27M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("27")==null?0:CommonFunctions.doublecut(map.get("27").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 28M -->
							<input  name="pos[28].adjustId" id="pos[28].id"  value="<%=map.get("28")==null?"":map.get("28").getAdjustId() %>" type="hidden"/>
							<input  name="pos[28].termType" id="pos[28].termType"  value="28" type="hidden"/>
							<input  name="pos[28].adjustValue" id="28M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("28")==null?0:CommonFunctions.doublecut(map.get("28").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 29M -->
							<input  name="pos[29].adjustId" id="pos[29].id"  value="<%=map.get("29")==null?"":map.get("29").getAdjustId() %>" type="hidden"/>
							<input  name="pos[29].termType" id="pos[29].termType"  value="29" type="hidden"/>
							<input  name="pos[29].adjustValue" id="29M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("29")==null?0:CommonFunctions.doublecut(map.get("29").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 30M -->
							<input  name="pos[30].adjustId" id="pos[30].id"  value="<%=map.get("30")==null?"":map.get("30").getAdjustId() %>" type="hidden"/>
							<input  name="pos[30].termType" id="pos[30].termType"  value="30" type="hidden"/>
							<input  name="pos[30].adjustValue" id="30M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("30")==null?0:CommonFunctions.doublecut(map.get("30").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 31M -->
							<input  name="pos[31].adjustId" id="pos[31].id"  value="<%=map.get("31")==null?"":map.get("31").getAdjustId() %>" type="hidden"/>
							<input  name="pos[31].termType" id="pos[31].termType"  value="31" type="hidden"/>
							<input  name="pos[31].adjustValue" id="31M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("31")==null?0:CommonFunctions.doublecut(map.get("31").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 32M -->
							<input  name="pos[32].adjustId" id="pos[32].id"  value="<%=map.get("32")==null?"":map.get("32").getAdjustId() %>" type="hidden"/>
							<input  name="pos[32].termType" id="pos[32].termType"  value="32" type="hidden"/>
							<input  name="pos[32].adjustValue" id="32M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("32")==null?0:CommonFunctions.doublecut(map.get("32").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 33M -->
							<input  name="pos[33].adjustId" id="pos[33].id"  value="<%=map.get("33")==null?"":map.get("33").getAdjustId() %>" type="hidden"/>
							<input  name="pos[33].termType" id="pos[33].termType"  value="33" type="hidden"/>
							<input  name="pos[33].adjustValue" id="33M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("33")==null?0:CommonFunctions.doublecut(map.get("33").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 34M -->
							<input  name="pos[34].adjustId" id="pos[34].id"  value="<%=map.get("34")==null?"":map.get("34").getAdjustId() %>" type="hidden"/>
							<input  name="pos[34].termType" id="pos[34].termType"  value="34" type="hidden"/>
							<input  name="pos[34].adjustValue" id="34M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("34")==null?0:CommonFunctions.doublecut(map.get("34").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 35M -->
							<input  name="pos[35].adjustId" id="pos[35].id"  value="<%=map.get("35")==null?"":map.get("35").getAdjustId() %>" type="hidden"/>
							<input  name="pos[35].termType" id="pos[35].termType"  value="35" type="hidden"/>
							<input  name="pos[35].adjustValue" id="35M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("35")==null?0:CommonFunctions.doublecut(map.get("35").getAdjustValue()*10000 ,1)%>" type="text"/>
						</td>
						<td>
							<!-- 36M -->
							<input  name="pos[36].adjustId" id="pos[36].id"  value="<%=map.get("36")==null?"":map.get("36").getAdjustId() %>" type="hidden"/>
							<input  name="pos[36].termType" id="pos[36].termType"  value="36" type="hidden"/>
							<input  name="pos[36].adjustValue" id="36M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("36")==null?0:CommonFunctions.doublecut(map.get("36").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
					</tr>


					<tr>
							<th >
								标准期限
							</th>
							<th >
								37M
							</th>
							<th >
								38M
							</th>
							<th >
								39M
						    </th>
							<th >
								40M
							</td>
							<th >
								41M
							</th>
						     <th >
								42M
						    </th>
						     <th >
								43M
						    </th>
						     <th >
								44M
						    </th>
						     <th >
								45M
						    </th>
						     <th >
								46M
						    </th>
						     <th >
								47M
						    </th>
						     <th >
								4Y
						    </th>
						</tr>
					<tr>
						<th>调整值</th>
						<td>
							<!-- 37M -->
							<input  name="pos[37].adjustId" id="pos[37].id"  value="<%=map.get("37")==null?"":map.get("37").getAdjustId() %>" type="hidden"/>
							<input  name="pos[37].termType" id="pos[37].termType"  value="37" type="hidden"/>
							<input  name="pos[37].adjustValue" id="37M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("37")==null?0:CommonFunctions.doublecut(map.get("37").getAdjustValue()*10000 ,1)%>" type="text"/>
						</td>
						<td>
							<!-- 38M -->
							<input  name="pos[38].adjustId" id="pos[38].id"  value="<%=map.get("38")==null?"":map.get("38").getAdjustId() %>" type="hidden"/>
							<input  name="pos[38].termType" id="pos[38].termType"  value="38" type="hidden"/>
							<input  name="pos[38].adjustValue" id="38M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("38")==null?0:CommonFunctions.doublecut(map.get("38").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 39M -->
							<input  name="pos[39].adjustId" id="pos[39].id"  value="<%=map.get("39")==null?"":map.get("39").getAdjustId()%>" type="hidden"/>
							<input  name="pos[39].termType" id="pos[39].termType"  value="39" type="hidden"/>
							<input  name="pos[39].adjustValue" id="39M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("39")==null?0:CommonFunctions.doublecut(map.get("39").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 40M -->
							<input  name="pos[40].adjustId" id="pos[40].id"  value="<%=map.get("40")==null?"":map.get("40").getAdjustId() %>" type="hidden"/>
							<input  name="pos[40].termType" id="pos[40].termType"  value="40" type="hidden"/>
							<input  name="pos[40].adjustValue" id="40M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("40")==null?0:CommonFunctions.doublecut(map.get("40").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 41M -->
							<input  name="pos[41].adjustId" id="pos[41].id"  value="<%=map.get("41")==null?"":map.get("41").getAdjustId() %>" type="hidden"/>
							<input  name="pos[41].termType" id="pos[41].termType"  value="41" type="hidden"/>
							<input  name="pos[41].adjustValue" id="41M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("41")==null?0:CommonFunctions.doublecut(map.get("41").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 42M -->
							<input  name="pos[42].adjustId" id="pos[42].id"  value="<%=map.get("42")==null?"":map.get("42").getAdjustId() %>" type="hidden"/>
							<input  name="pos[42].termType" id="pos[42].termType"  value="42" type="hidden"/>
							<input  name="pos[42].adjustValue" id="42M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("42")==null?0:CommonFunctions.doublecut(map.get("42").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 43M -->
							<input  name="pos[43].adjustId" id="pos[43].id"  value="<%=map.get("43")==null?"":map.get("43").getAdjustId() %>" type="hidden"/>
							<input  name="pos[43].termType" id="pos[43].termType"  value="43" type="hidden"/>
							<input  name="pos[43].adjustValue" id="43M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("43")==null?0:CommonFunctions.doublecut(map.get("43").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 44M -->
							<input  name="pos[44].adjustId" id="pos[44].id"  value="<%=map.get("44")==null?"":map.get("44").getAdjustId() %>" type="hidden"/>
							<input  name="pos[44].termType" id="pos[44].termType"  value="44" type="hidden"/>
							<input  name="pos[44].adjustValue" id="44M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("44")==null?0:CommonFunctions.doublecut(map.get("44").getAdjustValue()*10000,1)  %>" type="text"/>
						</td>
						<td>
							<!-- 45M -->
							<input  name="pos[45].adjustId" id="pos[45].id"  value="<%=map.get("45")==null?"":map.get("45").getAdjustId() %>" type="hidden"/>
							<input  name="pos[45].termType" id="pos[45].termType"  value="45" type="hidden"/>
							<input  name="pos[45].adjustValue" id="45M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("45")==null?0:CommonFunctions.doublecut(map.get("45").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 46M -->
							<input  name="pos[46].adjustId" id="pos[46].id"  value="<%=map.get("46")==null?"":map.get("46").getAdjustId() %>" type="hidden"/>
							<input  name="pos[46].termType" id="pos[46].termType"  value="46" type="hidden"/>
							<input  name="pos[46].adjustValue" id="46M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("46")==null?0:CommonFunctions.doublecut(map.get("46").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 47M -->
							<input  name="pos[47].adjustId" id="pos[47].id"  value="<%=map.get("47")==null?"":map.get("47").getAdjustId() %>" type="hidden"/>
							<input  name="pos[47].termType" id="pos[47].termType"  value="47" type="hidden"/>
							<input  name="pos[47].adjustValue" id="47M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("47")==null?0:CommonFunctions.doublecut(map.get("47").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 48M -->
							<input  name="pos[48].adjustId" id="pos[48].id"  value="<%=map.get("48")==null?"":map.get("48").getAdjustId() %>" type="hidden"/>
							<input  name="pos[48].termType" id="pos[48].termType"  value="48" type="hidden"/>
							<input  name="pos[48].adjustValue" id="48M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("48")==null?0:CommonFunctions.doublecut(map.get("48").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
					</tr>

							<tr>
							<th >
								标准期限
							</th>
							<th >
								49M
							</th>
							<th >
								50M
							</th>
							<th >
								51M
						    </th>
							<th >
								52M
							</td>
							<th >
								53M
							</th>
						     <th >
								54M
						    </th>
						     <th >
								55M
						    </th>
						     <th >
								56M
						    </th>
						     <th >
								57M
						    </th>
						     <th >
								58M
						    </th>
						     <th >
								59M
						    </th>
						     <th >
								5Y
						    </th>
						</tr>
					<tr>
						<th>调整值</th>
						<td>
							<!-- 49M -->
							<input  name="pos[49].adjustId" id="pos[49].id"  value="<%=map.get("49")==null?"":map.get("49").getAdjustId() %>" type="hidden"/>
							<input  name="pos[49].termType" id="pos[49].termType"  value="49" type="hidden"/>
							<input  name="pos[49].adjustValue" id="49M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("49")==null?0:CommonFunctions.doublecut(map.get("49").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 50M -->
							<input  name="pos[50].adjustId" id="pos[50].id"  value="<%=map.get("50")==null?"":map.get("50").getAdjustId() %>" type="hidden"/>
							<input  name="pos[50].termType" id="pos[50].termType"  value="50" type="hidden"/>
							<input  name="pos[50].adjustValue" id="50M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("50")==null?0:CommonFunctions.doublecut(map.get("50").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 51M -->
							<input  name="pos[51].adjustId" id="pos[51].id"  value="<%=map.get("51")==null?"":map.get("51").getAdjustId()%>" type="hidden"/>
							<input  name="pos[51].termType" id="pos[51].termType"  value="51" type="hidden"/>
							<input  name="pos[51].adjustValue" id="51M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("51")==null?0:CommonFunctions.doublecut(map.get("51").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 52M -->
							<input  name="pos[52].adjustId" id="pos[52].id"  value="<%=map.get("52")==null?"":map.get("52").getAdjustId() %>" type="hidden"/>
							<input  name="pos[52].termType" id="pos[52].termType"  value="52" type="hidden"/>
							<input  name="pos[52].adjustValue" id="52M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("52")==null?0:CommonFunctions.doublecut(map.get("52").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 53M -->
							<input  name="pos[53].adjustId" id="pos[53].id"  value="<%=map.get("53")==null?"":map.get("53").getAdjustId() %>" type="hidden"/>
							<input  name="pos[53].termType" id="pos[53].termType"  value="53" type="hidden"/>
							<input  name="pos[53].adjustValue" id="53M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("53")==null?0:CommonFunctions.doublecut(map.get("53").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 54M -->
							<input  name="pos[54].adjustId" id="pos[54].id"  value="<%=map.get("54")==null?"":map.get("54").getAdjustId() %>" type="hidden"/>
							<input  name="pos[54].termType" id="pos[54].termType"  value="54" type="hidden"/>
							<input  name="pos[54].adjustValue" id="54M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("54")==null?0:CommonFunctions.doublecut(map.get("54").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 55M -->
							<input  name="pos[55].adjustId" id="pos[55].id"  value="<%=map.get("55")==null?"":map.get("55").getAdjustId() %>" type="hidden"/>
							<input  name="pos[55].termType" id="pos[55].termType"  value="55" type="hidden"/>
							<input  name="pos[55].adjustValue" id="55M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("55")==null?0:CommonFunctions.doublecut(map.get("55").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 56M -->
							<input  name="pos[56].adjustId" id="pos[56].id"  value="<%=map.get("56")==null?"":map.get("56").getAdjustId() %>" type="hidden"/>
							<input  name="pos[56].termType" id="pos[56].termType"  value="56" type="hidden"/>
							<input  name="pos[56].adjustValue" id="56M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("56")==null?0:CommonFunctions.doublecut(map.get("56").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 57M -->
							<input  name="pos[57].adjustId" id="pos[57].id"  value="<%=map.get("57")==null?"":map.get("57").getAdjustId() %>" type="hidden"/>
							<input  name="pos[57].termType" id="pos[57].termType"  value="57" type="hidden"/>
							<input  name="pos[57].adjustValue" id="57M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("57")==null?0:CommonFunctions.doublecut(map.get("57").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 58M -->
							<input  name="pos[58].adjustId" id="pos[58].id"  value="<%=map.get("58")==null?"":map.get("58").getAdjustId() %>" type="hidden"/>
							<input  name="pos[58].termType" id="pos[58].termType"  value="58" type="hidden"/>
							<input  name="pos[58].adjustValue" id="58M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("58")==null?0:CommonFunctions.doublecut(map.get("58").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 59M -->
							<input  name="pos[59].adjustId" id="pos[59].id"  value="<%=map.get("59")==null?"":map.get("59").getAdjustId() %>" type="hidden"/>
							<input  name="pos[59].termType" id="pos[59].termType"  value="59" type="hidden"/>
							<input  name="pos[59].adjustValue" id="59M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("59")==null?0:CommonFunctions.doublecut(map.get("59").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 60M -->
							<input  name="pos[60].adjustId" id="pos[60].id"  value="<%=map.get("60")==null?"":map.get("60").getAdjustId() %>" type="hidden"/>
							<input  name="pos[60].termType" id="pos[60].termType"  value="60" type="hidden"/>
							<input  name="pos[60].adjustValue" id="60M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("60")==null?0:CommonFunctions.doublecut(map.get("60").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
					</tr>


					<tr>
							<th >
								标准期限
							</th>
							<th >60M以上</th>
							<th ></th>
							<th ></th>
							<th ></th>
							<th ></th>
							<th ></th>
							<th ></th>
							<th ></th>
							<th ></th>
							<th ></th>
							<th ></th>
							<th ></th>
						</tr>
					<tr>
						<th>调整值</th>
						<td>
							<!-- 61M -->
							<input  name="pos[61].adjustId" id="pos[61].id"  value="<%=map.get("61")==null?"":map.get("61").getAdjustId() %>" type="hidden"/>
							<input  name="pos[61].termType" id="pos[61].termType"  value="61" type="hidden"/>
							<input  name="pos[61].adjustValue" id="61M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("61")==null?0:CommonFunctions.doublecut(map.get("61").getAdjustValue()*10000,1) %>" type="text"/>
						</td>
						<td>
							<!-- 7y-->
<%--							<input  name="pos[62].adjustId" id="pos[62].id"  value="<%=map.get("84")==null?"":map.get("84").getAdjustId() %>" type="hidden"/>--%>
<%--							<input  name="pos[62].termType" id="pos[62].termType"  value="84" type="hidden"/>--%>
<%--							<input  name="pos[62].adjustValue" id="62M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("84")==null?0:CommonFunctions.doublecut(map.get("84").getAdjustValue()*10000,0) %>" type="text"/>--%>
						</td>
						<td>
							<!-- 61M -->
<%--							<input  name="pos[63].adjustId" id="pos[63].id"  value="<%=map.get("96")==null?"":map.get("96").getAdjustId() %>" type="hidden"/>--%>
<%--							<input  name="pos[63].termType" id="pos[63].termType"  value="96" type="hidden"/>--%>
<%--							<input  name="pos[63].adjustValue" id="63M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("96")==null?0:CommonFunctions.doublecut(map.get("96").getAdjustValue()*10000,0) %>" type="text"/>--%>
						</td>
						<td>
							<!-- 61M -->
<%--							<input  name="pos[64].adjustId" id="pos[64].id"  value="<%=map.get("108")==null?"":map.get("108").getAdjustId() %>" type="hidden"/>--%>
<%--							<input  name="pos[64].termType" id="pos[64].termType"  value="108" type="hidden"/>--%>
<%--							<input  name="pos[64].adjustValue" id="64M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("108")==null?0:CommonFunctions.doublecut(map.get("108").getAdjustValue()*10000,0) %>" type="text"/>--%>
						</td>
						<td>
							<!-- 61M -->
<%--							<input  name="pos[65].adjustId" id="pos[65].id"  value="<%=map.get("120")==null?"":map.get("120").getAdjustId() %>" type="hidden"/>--%>
<%--							<input  name="pos[65].termType" id="pos[65].termType"  value="120" type="hidden"/>--%>
<%--							<input  name="pos[65].adjustValue" id="65M" onkeyup="value=value.replace(/[^\d\.-]/g,'')" value="<%=map.get("120")==null?0:CommonFunctions.doublecut(map.get("120").getAdjustValue()*10000,0) %>" type="text"/>--%>
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
					<tr>
						<td colspan="13" align="center">
							<input name="query" class="button" type="submit" id="query" height="20" value="保&nbsp;&nbsp;存" /> 
		      				<%--<input name="back" class="button" type="reset" id="back" height="20"  value="重&nbsp;&nbsp;置" />--%>
						</td>
					</tr>
				</table>
		</form>
	</body>
<script language="javascript">
</script>
</html>
