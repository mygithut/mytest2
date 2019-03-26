<%@page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page	import="java.util.ArrayList,java.util.List,com.dhcc.ftp.util.*"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.dhcc.ftp.entity.Ftp1PrdtCkzbjAdjust"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Map<String,Ftp1PrdtCkzbjAdjust> map=(Map<String,Ftp1PrdtCkzbjAdjust>)request.getAttribute("CKMap");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>员工数据维护</title>
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
		<div class="cr_header" style="margin: 0px 0px 20px 0px">
			当前位置：期限匹配->存款准备金调整
		</div>
		<form id="form1" name="form1" method="post" action="<%=basePath%>/CKLLFGBLSZ_save.action">
				<table class="table"  cellpadding="0" cellspacing="0" align="center">
					<tr>
						<th colspan="13" style="height: 35px; " class="ClASSTD">存款准备金调整 (单位：BP)</th>
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
<%--							<!-- 0M -->--%>
<%--							<%=map.get("0")==null?0:CommonFunctions.doublecut(map.get("0").getAdjustValue()*10000,1) %>--%>
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
							<%=map.get("1")==null?0:CommonFunctions.doublecut(map.get("1").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 2M -->
							<%=map.get("2")==null?0:CommonFunctions.doublecut(map.get("2").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 3M -->
							<%=map.get("3")==null?0:CommonFunctions.doublecut(map.get("3").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 4M -->
							<%=map.get("4")==null?0:CommonFunctions.doublecut(map.get("4").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 5M -->
							<%=map.get("5")==null?0:CommonFunctions.doublecut(map.get("5").getAdjustValue()*10000 ,1)%>
						</td>
						<td>
							<!-- 6M -->
							<%=map.get("6")==null?0:CommonFunctions.doublecut(map.get("6").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 7M -->
							<%=map.get("7")==null?0:CommonFunctions.doublecut(map.get("7").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 8M -->
							<%=map.get("8")==null?0:CommonFunctions.doublecut(map.get("8").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 9M -->
							<%=map.get("9")==null?0:CommonFunctions.doublecut(map.get("9").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 10M -->
							<%=map.get("10")==null?0:CommonFunctions.doublecut(map.get("10").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 11M -->
							<%=map.get("11")==null?0:CommonFunctions.doublecut(map.get("11").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 12M -->
							<%=map.get("12")==null?0:CommonFunctions.doublecut(map.get("12").getAdjustValue()*10000,1) %>
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
							<%=map.get("13")==null?0:CommonFunctions.doublecut(map.get("13").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 14M -->
							<%=map.get("14")==null?0:CommonFunctions.doublecut(map.get("14").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 15M -->
							<%=map.get("15")==null?0:CommonFunctions.doublecut(map.get("15").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 16M -->
							<%=map.get("16")==null?0:CommonFunctions.doublecut(map.get("16").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 17M -->
							<%=map.get("17")==null?0:CommonFunctions.doublecut(map.get("17").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 18M -->
							<%=map.get("18")==null?0:CommonFunctions.doublecut(map.get("18").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 19M -->
							<%=map.get("19")==null?0:CommonFunctions.doublecut(map.get("19").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 20M -->
							<%=map.get("20")==null?0:CommonFunctions.doublecut(map.get("20").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 21M -->
							<%=map.get("21")==null?0:CommonFunctions.doublecut(map.get("21").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 22M -->
							<%=map.get("22")==null?0:CommonFunctions.doublecut(map.get("22").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 23M -->
							<%=map.get("23")==null?0:CommonFunctions.doublecut(map.get("23").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 24M -->
							<%=map.get("24")==null?0:CommonFunctions.doublecut(map.get("24").getAdjustValue()*10000,1) %>
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
							<%=map.get("25")==null?0:CommonFunctions.doublecut(map.get("25").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 26M -->
							<%=map.get("26")==null?0:CommonFunctions.doublecut(map.get("26").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 27M -->
							<%=map.get("27")==null?0:CommonFunctions.doublecut(map.get("27").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 28M -->
							<%=map.get("28")==null?0:CommonFunctions.doublecut(map.get("28").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 29M -->
							<%=map.get("29")==null?0:CommonFunctions.doublecut(map.get("29").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 30M -->
							<%=map.get("30")==null?0:CommonFunctions.doublecut(map.get("30").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 31M -->
							<%=map.get("31")==null?0:CommonFunctions.doublecut(map.get("31").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 32M -->
							<%=map.get("32")==null?0:CommonFunctions.doublecut(map.get("32").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 33M -->
							<%=map.get("33")==null?0:CommonFunctions.doublecut(map.get("33").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 34M -->
							<%=map.get("34")==null?0:CommonFunctions.doublecut(map.get("34").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 35M -->
							<%=map.get("35")==null?0:CommonFunctions.doublecut(map.get("35").getAdjustValue()*10000 ,1)%>
						</td>
						<td>
							<!-- 36M -->
							<%=map.get("36")==null?0:CommonFunctions.doublecut(map.get("36").getAdjustValue()*10000,1) %>
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
							<%=map.get("37")==null?0:CommonFunctions.doublecut(map.get("37").getAdjustValue()*10000 ,1)%>
						</td>
						<td>
							<!-- 38M -->
							<%=map.get("38")==null?0:CommonFunctions.doublecut(map.get("38").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 39M -->
							<%=map.get("39")==null?0:CommonFunctions.doublecut(map.get("39").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 40M -->
							<%=map.get("40")==null?0:CommonFunctions.doublecut(map.get("40").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 41M -->
							<%=map.get("41")==null?0:CommonFunctions.doublecut(map.get("41").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 42M -->
							<%=map.get("42")==null?0:CommonFunctions.doublecut(map.get("42").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 43M -->
							<%=map.get("43")==null?0:CommonFunctions.doublecut(map.get("43").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 44M -->
							<%=map.get("44")==null?0:CommonFunctions.doublecut(map.get("44").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 45M -->
							<%=map.get("45")==null?0:CommonFunctions.doublecut(map.get("45").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 46M -->
							<%=map.get("46")==null?0:CommonFunctions.doublecut(map.get("46").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 47M -->
							<%=map.get("47")==null?0:CommonFunctions.doublecut(map.get("47").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 48M -->
							<%=map.get("48")==null?0:CommonFunctions.doublecut(map.get("48").getAdjustValue()*10000,1) %>
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
							<%=map.get("49")==null?0:CommonFunctions.doublecut(map.get("49").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 50M -->
							<%=map.get("50")==null?0:CommonFunctions.doublecut(map.get("50").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 51M -->
							<%=map.get("51")==null?0:CommonFunctions.doublecut(map.get("51").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 52M -->
							<%=map.get("52")==null?0:CommonFunctions.doublecut(map.get("52").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 53M -->
							<%=map.get("53")==null?0:CommonFunctions.doublecut(map.get("53").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 54M -->
							<%=map.get("54")==null?0:CommonFunctions.doublecut(map.get("54").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 55M -->
							<%=map.get("55")==null?0:CommonFunctions.doublecut(map.get("55").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 56M -->
							<%=map.get("56")==null?0:CommonFunctions.doublecut(map.get("56").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 57M -->
							<%=map.get("57")==null?0:CommonFunctions.doublecut(map.get("57").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 58M -->
							<%=map.get("58")==null?0:CommonFunctions.doublecut(map.get("58").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 59M -->
							<%=map.get("59")==null?0:CommonFunctions.doublecut(map.get("59").getAdjustValue()*10000,1) %>
						</td>
						<td>
							<!-- 60M -->
							<%=map.get("60")==null?0:CommonFunctions.doublecut(map.get("60").getAdjustValue()*10000,1) %>
						</td>
					</tr>


					<tr>
							<th >
								标准期限
							</th>
							<th >
								60M以上
							</th>
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
							<!-- 6y -->
							<%=map.get("61")==null?0:CommonFunctions.doublecut(map.get("61").getAdjustValue()*10000,0) %>
						</td>
						<td>
						<!-- 7y -->
<%--						<%=map.get("84")==null?0:CommonFunctions.doublecut(map.get("84").getAdjustValue()*10000,0) %>--%>
						</td>
						<td>
							<!-- 8y -->
<%--							<%=map.get("96")==null?0:CommonFunctions.doublecut(map.get("96").getAdjustValue()*10000,0) %>--%>
						</td>
						<td>
							<!-- 9y -->
<%--							<%=map.get("108")==null?0:CommonFunctions.doublecut(map.get("108").getAdjustValue()*10000,0) %>--%>
						</td>
						<td>
							<!-- 10y -->
<%--							<%=map.get("120")==null?0:CommonFunctions.doublecut(map.get("120").getAdjustValue()*10000,0) %>--%>
						</td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
						<td></td>
					</tr>
<%--					<tr>--%>
<%--						<td colspan="13" align="center">--%>
<%--							<input name="query" class="button" type="submit" id="query" height="20" value="保&nbsp;&nbsp;存" /> --%>
<%--		      				<input name="back" class="button" type="reset" id="back" height="20"  value="重&nbsp;&nbsp;置" />--%>
<%--						</td>--%>
<%--					</tr>--%>
				</table>
		</form>
	</body>
<script language="javascript">

</script>
</html>
