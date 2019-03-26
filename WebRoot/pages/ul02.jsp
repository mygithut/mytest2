<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page import="java.text.*,java.util.Date,com.dhcc.ftp.entity.TelMst,com.dhcc.ftp.util.CommonFunctions"%>
<html>
<head>
<title></title>
<link rel="stylesheet" href="/ftp/pages/css/style1.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/style_body.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/css/inpage.css" type="text/css">
<jsp:include page="commonJs.jsp" />
</head>

<body>
<div class="cr_header">
			当前位置：双资金池->双资金池
</div>

<div align="center"><div style="margin-top: 20px; height: 65px; width: 79%; background-repeat: repeat; border: none; background: url('/ftp/pages/images/bg_head.gif');">
<div style="float: left; width: 400px">
<div style="float: left;"><img src="/ftp/pages/images/left_head.gif" border="0px" /></div>
<div style="float: right; padding-top: 30px; padding-right: 80px"><font style="font-size: 20px; font-weight: bold; font-family: '微软雅黑'; color: black;">双资金池</font></div>
</div>
<div style="float: right;"><img src="/ftp/pages/images/right_head.gif" border="0px" /></div>
</div></div>
<form action="<%=request.getContextPath()%>/UL02_report.action" method="get">
<table class="tb1" border=1 width="80%" align="center" bordercolor="#79B242">
	<tr>
		<td height="30" colspan="2" class="top_bg">&nbsp;</td>
	</tr>
	<tr>
		<td width="36%">
		<p align="right">日&nbsp;&nbsp;期：</p>
		</td>
		<td width="64%">
		<input type="text" disabled="disabled" name="date" maxlength="10" value="<%=CommonFunctions.GetDBSysDate() %>" size="10" /> 
	<!--	<img style="CURSOR:hand" src="/ftp/pages/images/calendar.gif" width="16" height="16" alt="date" align="absmiddle" onClick="getDate0('date')"></td>
	  --></tr>	
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
	<tr bgColor="#EEF2E6" id="kind">
		<td width="36%"><p align="right">资金池种类：</p></td>
		<td width="64%">
			<select style="width: 120" name="poolNo" id="poolNo" onchange="setValue()">
<%--			   <option value="">--请选择--</option>--%>
			   <option value="201">资产</option>
			   <option value="202">负债</option>
			</select>
		</td>
	</tr>
	<tr>
		<td width="36%"><p align="right">定价方法：</p></td>
		<td width="64%">
			<select style="width: 120" name="method" id="method" onchange="setValue()">
<%--			   <option value="">--请选择--</option>--%>
			   <option value="1">成本收益法</option>
			   <option value="2">加权平均成本法</option>
			</select>
		</td>
	</tr>
	
	<tr bgColor="#EEF2E6">
		<td width="36%">
		<p align="right">经营策略调整：</p>
		</td>
		<td width="64%">
		<input type="text" name="adjustValue" value="0" size="15" onchange="setValue()" /> %
	</tr>
	<tr>
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
			<div align="center"><input type="button" name="Submit1" value="定价发布" onclick="priceSubmit(this.form)" class="but_02"> &nbsp; 
<%--			<input type="button" name="Submit1" value="盈利分析" onclick="analyzeReport(this.form)" class="but_02"> &nbsp;--%>
			 <input type="reset" name="Reset" value="重置" class="but_02"></div>
		</td>
	</tr>
	
</table>
<input type="hidden" name="resValue2" value="" id="resValue2"/>
<input type="hidden" name="methodName" value="" id="methodName"/>
<input type="hidden" name="url" value="dzjcList" id="url"/>
</form>
<div style="display: none" id="data1" align="center">
<br/>
   <table class="table" width="580" align="center">
   <tr>
   <th width="100" align="center">基准价格</th>
   <th width="100" align="center">信用风险修正</th>
   <th width="100" align="center">流动性风险修正</th>
   <th width="100" align="center">期限风险修正</th>
   <th width="100" align="center">经营策略调整</th>
   <th width="80" align="center">转移价格</th>
   </tr>
   <tr>
   <td align="center" id="jzjg1"></td>
   <td align="center" id="xyfxxz1"></td>
   <td align="center" id="ldxfxxz1"></td>
   <td align="center" id="qxfxcs1"></td>
   <td align="center" id="jycltzcs1"></td>
   <td align="center" id="zyjg1"></td>
   </tr>
</table>
</div>
<div style="display: none" id="data2" align="center">
<br/>
   <table class="table" width="580" align="center">
   <tr>
   <th width="100" align="center">基准价格</th>
   <th width="100" align="center">信用风险修正</th>
   <th width="100" align="center">流动性风险修正</th>
   <th width="100" align="center">期限风险修正</th>
   <th width="100" align="center">经营策略调整</th>
   <th width="80" align="center">转移价格</th>
   </tr>
   <tr>
   <td align="center" id="jzjg2"></td>
   <td align="center" id="xyfxxz2"></td>
   <td align="center" id="ldxfxxz2"></td>
   <td align="center" id="qxfxcs2"></td>
   <td align="center" id="jycltzcs2"></td>
   <td align="center" id="zyjg2"></td>
   </tr>
</table>
</div>
<div style="display: none" id="data3" align="center">
<br/>
   <table class="table" width="640" align="center">
   <tr>
   <th width="100" align="center">贷款平均收益率</th>
   <th width="100" align="center">信用风险修正</th>
   <th width="100" align="center">流动性风险修正</th>
   <th width="100" align="center">期限风险修正</th>
   <th width="100" align="center">经营策略调整</th>
   <th width="80" align="center">利差修正</th>
   <th width="80" align="center">转移价格</th>
   </tr>
   <tr>
   <td align="center" id="dkpjsyl"></td>
   <td align="center" id="xyfxxz3"></td>
   <td align="center" id="ldxfxxz3"></td>
   <td align="center" id="qxfxxz1"></td>
   <td align="center" id="jycltzcs3"></td>
   <td align="center" id="lcxz1"></td>
   <td align="center" id="zyjg3"></td>
   </tr>
</table>
</div>
<div style="display: none" id="data4" align="center">
<br/>
   <table class="table" width="640" align="center">
   <tr>
   <th width="100" align="center">存款平均成本率</th>
   <th width="100" align="center">信用风险修正</th>
   <th width="100" align="center">流动性风险修正</th>
   <th width="100" align="center">期限风险修正</th>
   <th width="100" align="center">经营策略调整</th>
   <th width="80" align="center">利差修正</th>
   <th width="80" align="center">转移价格</th>
   </tr>
   <tr>
   <td align="center" id="ckpjcbl"></td>
   <td align="center" id="xyfxxz4"></td>
   <td align="center" id="ldxfxxz4"></td>
   <td align="center" id="qxfxxz2"></td>
   <td align="center" id="jycltzcs4"></td>
   <td align="center" id="lcxz2"></td>
   <td align="center" id="zyjg4"></td>
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
	if(!(isNull(FormName.poolNo,"资金池种类"))) {
		return false;			
	}
	timer=setInterval(showMessage,0);	
       	var url = "<%=request.getContextPath()%>/UL02_calculate.action";
        new Ajax.Request( 
        url, 
         {   
          method: 'post',   
          parameters: {brNo:FormName.brNo.value,date:FormName.date.value,poolNo:FormName.poolNo.value,currencySelectId:FormName.currencySelectId.value,method:FormName.method.value,adjustValue:FormName.adjustValue.value,t:new Date().getTime()},
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
                 if (FormName.poolNo.value == '201') {
                	 $('zyjg1').innerText = resultArray[0]+"%";
                     $('jzjg1').innerText = resultArray[1]+"%";
                     $('xyfxxz1').innerText = resultArray[2]+"%";
                     $('ldxfxxz1').innerText = resultArray[3]+"%";
                     $('qxfxcs1').innerText = resultArray[4]+"%";
                     $('jycltzcs1').innerText = resultArray[5]+"%";
                 }else {
                	 $('zyjg2').innerText = resultArray[0]+"%";
                     $('jzjg2').innerText = resultArray[1]+"%";
                     $('xyfxxz2').innerText = resultArray[2]+"%";
                     $('ldxfxxz2').innerText = resultArray[2]+"%";
                     $('qxfxcs2').innerText = resultArray[3]+"%";
                     $('jycltzcs2').innerText = resultArray[4]+"%";
                 }
             }else if (FormName.method.value == '2') {
            	 if (FormName.poolNo.value == '201') {
            		 $('zyjg3').innerText = resultArray[0]+"%";
                     $('dkpjsyl').innerText = resultArray[1]+"%";
                     $('xyfxxz3').innerText = resultArray[2]+"%";
                     $('ldxfxxz3').innerText = resultArray[3]+"%";
                     $('qxfxxz1').innerText = resultArray[4]+"%";
                     $('jycltzcs3').innerText = resultArray[5]+"%";
                     $('lcxz1').innerText = resultArray[6]+"%";
                 }else {
                	 $('zyjg4').innerText = resultArray[0]+"%";
                     $('ckpjcbl').innerText = resultArray[1]+"%";
                     $('xyfxxz4').innerText = resultArray[2]+"%";
                     $('ldxfxxz4').innerText = resultArray[3]+"%";
                     $('qxfxxz2').innerText = resultArray[4]+"%";
                     $('jycltzcs4').innerText = resultArray[5]+"%";
                     $('lcxz2').innerText = resultArray[6]+"%";
                 }
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
	if(!(isNull(FormName.poolNo,"资金池种类"))) {
		return false;			
	}
    var url = "<%=request.getContextPath()%>/UL02_save.action";
    new Ajax.Request( 
    url, 
    {   
        method: 'post',   
        parameters: {method:FormName.method.value,brNo:FormName.brNo.value,poolNo:FormName.poolNo.value,date:FormName.date.value,currencySelectId:FormName.currencySelectId.value,adjustValue:FormName.adjustValue.value,resValue:FormName.resValue.value,t:new Date().getTime()},
        onSuccess: function(resp) 
        {  
	    	   art.dialog({
             	    title:'成功',
         		    icon: 'succeed',
         		    content: '发布成功！',
          		    cancelVal: '关闭',
          		    cancel: true
          	 });
        }
     });
}     
function dataAnalyze(FormName){
	var method = $('method').value;
	var poolNo = $('poolNo').value;
	var content;
	if(!(isNull(FormName.brNo,"机构"))) {
		return  ;			
	}
	if ($('resValue').value == '') {
		alert("请先进行定价计算！！");
		return;
	}
	if (method == '1') {
		if (poolNo == '201') {
			content = document.getElementById("data1").innerHTML;
		}else {
			content = document.getElementById("data2").innerHTML;
		}
	}else if (method == '2') {
		if (poolNo == '201') {
			content = document.getElementById("data3").innerHTML;
		}else {
			content = document.getElementById("data4").innerHTML;
		}
	}
	art.dialog({
 	        title:'数据分析-'+$('method').options[$('method').selectedIndex].text,
		    content: content
	 });
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
	if(!(isNull(FormName.poolNo,"资金池种类"))) {
		return false;			
	}
	var w=800; //弹出窗口的宽度;
   	var h=500; //弹出窗口的高度;
   	sw=Math.floor((window.screen.width/2-w/2));
   	sh=Math.floor((window.screen.height/2-h/2));
   	pra="height="+h+", width="+w+", top="+sh+", left="+sw+"menubar=no, scrollbars=yes, resizable=no,location=no, status=no";
   	window.open('<%=request.getContextPath()%>/UL02_history.action?poolNo='+$('poolNo').value+'&currencySelectId='+$('currencySelectId').value+'&brNo='+$('brNo').value+'&date='+$('date').value+'&methodName='+$('method').options[$('method').selectedIndex].text+'&method='+$('method').value,'newwindow',pra);
}
function analyzeReport(FormName) {
	if ($('resValue').value == '') {
		alert("请先进行定价计算！！");
		return;
	}
	//先检查是否资产和负债都已经进行了定价
	var url = "<%=request.getContextPath()%>/UL02_check.action";
    new Ajax.Request( 
    url, 
     {   
      method: 'post',   
      parameters: {brNo:FormName.brNo.value,date:FormName.date.value,poolNo:FormName.poolNo.value,resValue:FormName.resValue.value,currencySelectId:FormName.currencySelectId.value,t:new Date().getTime()},
      onSuccess: function(resp) 
       {  
          var result = resp.responseText;
          var resultArray = result.split(',');
          if (resultArray[0]== 'success') {
        	  $('resValue2').value = resultArray[1];
        	  FormName.submit();
          }else {
              alert(resp.responseText);
          }
      }
     }
   );
	
}
function setValue() {
	$('resValue').value = '';
}
</script>
