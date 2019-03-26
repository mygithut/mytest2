<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="java.text.*,java.util.Date,com.dhcc.ftp.entity.TelMst,com.dhcc.ftp.util.CommonFunctions"%>
<html>
<head>
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/style1.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/style_body.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="commonJs.jsp" />
	
</head>

<body>
<div class="cr_header">
			当前位置：单资金池->单资金池定价计算
</div>
		
<div align="center"><div style="margin-top: 20px; height: 65px; width: 79%; background-repeat: repeat; border: none; background: url('/ftp/pages/images/bg_head.gif');">
<div style="float: left; width: 400px">
<div style="float: left;"><img src="/ftp/pages/images/left_head.gif" border="0px" /></div>
<div style="float: right; padding-top: 30px; padding-right: 80px"><font style="font-size: 20px; font-weight: bold; font-family: '微软雅黑'; color: black;">单资金池</font></div>
</div>
<div style="float: right;"><img src="/ftp/pages/images/right_head.gif" border="0px" /></div>
</div></div>
<form action="<%=request.getContextPath()%>/UL01_report.action" method="get">
<table class="tb1" border=1 width="80%" align="center" bordercolor="#79B242">
	<tr>
		<td height="30" colspan="2" class="top_bg">&nbsp;</td>
	</tr>
	<tr>
		<td width="36%">
		<p align="right">日&nbsp;&nbsp;期：</p>
		</td>
		<td width="64%">
		<input disabled="disabled" type="text" name="date" maxlength="10" value="<%=CommonFunctions.GetDBSysDate() %>" size="10" /> 
		<!-- <img style="CURSOR:hand" src="/ftp/pages/images/calendar.gif" width="16" height="16" alt="date" align="absmiddle" onClick="getDate1('date')"></td>
	 -->
	</tr>
	<tr bgColor="#EEF2E6">
		<td width="36%" align="right">机构名称：</td>
		<td width="64%">
          <select name="brNo" id="brNo"></select>
		</td>
	</tr>
	<tr>
		<td width="36%"><p align="right">选择币种：</p></td>
			<td width="64%">
				<select style="width: 100" name="currencySelectId" id="currencySelectId">
				</select>
			</td>
	</tr>
	<tr bgColor="#EEF2E6">
		<td width="36%"><p align="right">定价方法：</p></td>
		<td width="64%">
			<select style="width: 120" name="method" id="method" onclick="setValue()">
<%--			   <option value="">--请选择--</option>--%>
			   <option value="1">成本收益法</option>
			   <option value="2">加权平均成本法</option>
			</select>
		</td>
	</tr>
	<tr>
		<td width="36%">
		<p align="right">经营策略调整：</p>
		</td>
		<td width="64%">
		<input type="text" name="adjustValue" value="0" size="15" onchange="setValue()" /> %
	</tr>
	<tr bgColor="#EEF2E6">
		<td width="36%">
		<p align="right">转移价格：</p>
		</td>
		<td width="64%">
		<input type="text" name="resValue" value="" readonly="readonly" size="15" /> %&nbsp;&nbsp;&nbsp;
		<input type="button" name="Submit1" value="定价计算" onclick="priceCalculate(this.form)" class="button"/>
		<input type="button" name="Submit1" value="数据分析" onclick="dataAnalyze(this.form)" class="button"/>
		<input type="button" name="Submit1" value="历史转移价格" onclick="showHistoryTransPrice(this.form)" class="button"/>
		</td>
	</tr>
	<tr>
		<td height="41" colspan="2" class="top_bg">
			 <div align="center">
			 <input type="button" name="Submit1" value="定价发布" onclick="priceSubmit(this.form)" class="but_02"> &nbsp;
<%--			 <input type="button" name="Submit1" value="盈利分析" onclick="analyzeReport(this.form)" class="but_02"> &nbsp;--%>
			 <input type="reset" name="Reset" value="重置" class="but_02"></div>
		</td>
	</tr>
	
</table>
<input type="hidden" name="methodName" value="" id="methodName"/>
<input type="hidden" name="url" value="dzjcList" id="url"/>
</form>
<div style="display: none" id="data1" align="center">
<br/>
   <table class="table" width="650" align="center">
   <tr>
   <th width="150" align="center">盈利性资产回报率</th>
   <th width="100" align="center">资产损失率</th>
   <th width="100" align="center">筹资成本率</th>
   <th width="100" align="center">筹资费用率</th>
   <th width="100" align="center">经营策略调整</th>
   <th width="100" align="center">转移价格</th>
   </tr>
   <tr>
   <td align="center" id="zchbl"></td>
   <td align="center" id="zcssl"></td>
   <td align="center" id="czcbl"></td>
   <td align="center" id="czfyl"></td>
   <td align="center" id="jycltz"></td>
   <td align="center" id="zyjg1"></td>
   </tr>
</table>
</div>
<div style="display: none" id="data2" align="center">
<br/>
   <table class="table" width="560" align="center">
   <tr>
   <th width="120" align="center">存款平均成本率</th>
   <th width="120" align="center">贷款平均收益率</th>
   <th width="120" align="center">平均存贷比</th>
   <th width="100" align="center">经营策略调整</th>
   <th width="100" align="center">转移价格</th>
   </tr>
   <tr>
   <td align="center" id="ckpjcbl"></td>
   <td align="center" id="dkpjsyl"></td>
   <td align="center" id="pjcdb"></td>
   <td align="center" id="jycltz"></td>
   <td align="center" id="zyjg2"></td>
   </tr>
</table>
</div>
<div id="mydiv" style="display:none">
<center>
计算中，请稍候...<br>
<img src="/ftp/pages/images/load.gif"/><!---动态图片，这里可以省略-->
</center>
</div>
</body>
</html>
<script type="text/javascript" language="javascript">
fillSelectContent('currencySelectId');
fillSelect('brNo','fillSelect_getBrNoByLvl2');
var timer;
function showMessage(){
    var obj=document.getElementById("mydiv");
    obj.style.display="block";
}
function hideMessage(){
    document.getElementById("mydiv").style.display="none";
}

function priceCalculate(FormName)
{
	if(!(isNull(FormName.date,"日期"))) {
		return false;			
	}	
	if(!(isNull(FormName.brNo,"机构"))) {
		return  ;			
	}
	if(!(isNull(FormName.method,"定价方法"))) {
		return false;			
	}
	timer=setInterval(showMessage,0);	
       	var url = "<%=request.getContextPath()%>/UL01_calculate.action";
        new Ajax.Request( 
        url, 
         {   
          method: 'post',   
          parameters: {brNo:FormName.brNo.value,date:FormName.date.value,currencySelectId:FormName.currencySelectId.value,method:FormName.method.value,adjustValue:FormName.adjustValue.value,t:new Date().getTime()},
          onSuccess: function(resp) 
           {  
	    	   art.dialog({
             	    title:'成功',
         		    icon: 'succeed',
         		    content: '定价成功！',
          		    cancelVal: '关闭',
          		    cancel: true
          	 });
             var result = resp.responseText;
             var resultArray = result.split(',');
             $('resValue').value = resultArray[0];
             if (FormName.method.value == '1') {
            	 $('zyjg1').innerText = resultArray[0]+"%";
                 $('zchbl').innerText = resultArray[1]+"%";
                 $('zcssl').innerText = resultArray[2]+"%";
                 $('czcbl').innerText = resultArray[3]+"%";
                 $('czfyl').innerText = resultArray[4]+"%";
                 $('jycltz').innerText = resultArray[5]+"%";
             }else if (FormName.method.value == '2') {
            	 $('zyjg2').innerText = resultArray[0]+"%";
                 $('ckpjcbl').innerText = resultArray[1]+"%";
                 $('dkpjsyl').innerText = resultArray[2]+"%";
                 $('pjcdb').innerText = resultArray[3]+"%";
                 $('jycltz').innerText = resultArray[4]+"%";
             }
             clearInterval(timer);
             hideMessage();
            }
          }
       );
}        
function priceSubmit(FormName)
{
	if(!(isNull(FormName.date,"日期"))) {
		return false;			
	}	
	if(!(isNull(FormName.brNo,"机构"))) {
		return  ;			
	}
	if(!(isNull(FormName.method,"定价方法"))) {
		return false;			
	}
	if(!(isNull(FormName.resValue,"转移价格"))) {
		return false;			
	}
	
       	var url = "<%=request.getContextPath()%>/UL01_save.action";
        new Ajax.Request( 
        url, 
         {   
          method: 'post',   
          parameters: {brNo:FormName.brNo.value,method:FormName.method.value,date:FormName.date.value,currencySelectId:FormName.currencySelectId.value,resValue:FormName.resValue.value,adjustValue:FormName.adjustValue.value,t:new Date().getTime()},
          onSuccess: function() 
           {  
        	    art.dialog({
           	    title:'成功',
       		    icon: 'succeed',
       		    content: '发布成功！',
        		cancelVal: '关闭',
        		cancel: true
        	 });
            }
          }
       );
}        
function dataAnalyze(FormName){
	var method = $('method').value;
	var content;
	if(!(isNull(FormName.brNo,"机构"))) {
		return  ;			
	}
	if ($('resValue').value == '') {
		alert("请先进行定价计算！！");
		return;
	}
	if (method == '1') {
		content = document.getElementById("data1").innerHTML;
	}else if (method == '2') {
		content = document.getElementById("data2").innerHTML;
	}
	art.dialog({
     	    title:'数据分析-'+$('method').options[$('method').selectedIndex].text,
 		    content: content
  	 });
}
function methodSelect(){
   var obj = document.getElementById("method");
   document.getElementById("methodName").value=obj.options[obj.selectedIndex].text;
}
function showHistoryTransPrice(FormName) {
	if(!(isNull(FormName.date,"日期"))) {
		return false;			
	}	
	if(!(isNull(FormName.brNo,"机构"))) {
		return  ;			
	}
	if(!(isNull(FormName.method,"定价方法"))) {
		return false;			
	}
	var w=800; //弹出窗口的宽度;
   	var h=500; //弹出窗口的高度;
   	sw=Math.floor((window.screen.width/2-w/2));
   	sh=Math.floor((window.screen.height/2-h/2));
   	pra="height="+h+", width="+w+", top="+sh+", left="+sw+"menubar=no, scrollbars=yes, resizable=no,location=no, status=no";
   	window.open('<%=request.getContextPath()%>/UL01_history.action?prcMode=1&currencySelectId='+$('currencySelectId').value+'&brNo='+$('brNo').value+'&date='+$('date').value+'&methodName='+$('method').options[$('method').selectedIndex].text+'&method='+$('method').value,'newwindow',pra);
}

function analyzeReport(FormName) {
	if ($('resValue').value == '') {
		alert("请先进行定价计算！！");
		return;
	}
	FormName.submit();
}
function setValue() {
	$('resValue').value = '';
}
</script>
