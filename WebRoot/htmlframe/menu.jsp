<%@ page language="java" import="java.util.*,com.dhcc.ftp.entity.TelMst" pageEncoding="GBK"%>
<html>
<style type="text/css">
html,body {margin:0; padding:0;}
</style>
<jsp:include page="../pages/commonJs.jsp" />
<body>
<%//Integer ftpSetNum = (Integer)session.getAttribute("ftpSetNum");//有多少个县联社未进行定价策略配置 
  TelMst bean=(TelMst)session.getAttribute("userBean");
%>
<div align="center" style="background-color:#E5F3FC">
<iframe style="display:block" name="" id="Ganame" src="main.html" width="100%" height="100%" onload="Javascript:SetWidHeight(this)" scrolling="auto">
</iframe>
</div>
</body>
<script  type="text/javascript" language="JavaScript1.2" >
var hwidth = document.body.clientWidth;
var hheight = document.body.clientHeight;
if(hwidth < 1259) hwidth = 1259;
if(hheight < 630) hheight = 630;
function SetWidHeight(obj) 
{ 
	obj.width = hwidth; 
	obj.height = hheight; 
} 
<%--<%if((bean.getRoleMst().getRoleNo().equals("216") || bean.getRoleMst().getRoleNo().equals("217") || bean.getRoleMst().getRoleNo().equals("218")) && ftpSetNum != 0) {%>//系统管理员进入，且有未完成的配置--%>
<%--    var alertStr = "";--%>
<%--    <%if(bean.getRoleMst().getRoleNo().equals("216")){%>//省级管理员--%>
<%--    alertStr = '有<%=ftpSetNum%>个县联社未进行定价策略配置,是否去配置?';--%>
<%--    <%}else {%> --%>
<%--    alertStr = '本县联社未进行定价策略配置,是否去配置?';--%>
<%--    <%}%>--%>
<%--    art.dialog({--%>
<%--        title:'配置',--%>
<%--    	icon: 'question',--%>
<%--        content: alertStr,--%>
<%--        ok: function () {--%>
<%--    	    window.frames.Ganame.frames.topFrame.changed("7");--%>
<%--    	    window.frames.Ganame.frames.centerFrame.location.href="left_center.jsp?isFirst=1&menu_parent=I";--%>
<%--        	window.frames.Ganame.frames.rightFrame.location.href="<%=request.getContextPath()%>/distribute.action?url=DJCLPZ";--%>
<%--        },--%>
<%--        cancelVal: '关闭',--%>
<%--        cancel: true //为true等价于function(){}--%>
<%--    });--%>
<%--<%}else if((!bean.getRoleMst().getRoleNo().equals("216") && !bean.getRoleMst().getRoleNo().equals("217") && !bean.getRoleMst().getRoleNo().equals("218")) && ftpSetNum == 0) {%>--%>
<%--   document.getElementById("Ganame").style.display = "none";--%>
<%--   art.dialog({--%>
<%--  	        title:'失败',--%>
<%--		    icon: 'error',--%>
<%--		    content: '系统还未初始化-不可用，请与系统管理员联系！',--%>
<%--   		    ok: function () {--%>
<%--	            window.parent.location.href = "../login.jsp";--%>
<%--   		        return true;--%>
<%--   		    }--%>
<%--	 });--%>
<%--<%}%>--%>
//<操作正在处理中>遮罩
var overlayID="overlay";
var msgID = "overlayMsg";
var docEle = function() {
  return document.getElementById(arguments[0]) || false;
 }
 function openNewDiv() {
    
  if (docEle(overlayID)) document.removeChild(docEle(overlayID));
  if (docEle(msgID)) document.removeChild(docEle(msgID));
  // 消息显示
  var newDiv = document.createElement("div");
  newDiv.id = msgID;
  newDiv.style.position = "absolute";
  newDiv.style.zIndex = "999";
  newDiv.style.width = "220px";
  newDiv.style.height = "90px";
  var scrolltop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
   
  var _clientheight=0;

  //ie FF 在有DOCTYPE时各有区别  
  _clientheight = Math.min(document.body.clientHeight , document.documentElement.clientHeight);
  if(_clientheight==0)
  _clientheight= Math.max(document.body.clientHeight , document.documentElement.clientHeight);
    
  var _clientwidth= document.documentElement.clientWidth || document.body.clientWidth;
  //整个页面的高度
  var _pageheight = Math.max(document.body.scrollHeight,document.documentElement.scrollHeight);
    
  var msgtop = (scrolltop+((_clientheight)/2)-50)+"px";
  var msgleft = (_clientwidth-200)/2+"px";
  newDiv.style.top = msgtop;
  newDiv.style.left =msgleft; // 屏幕居中
  newDiv.style.background = "#EFEFEF";
  newDiv.style.border = "1px solid #860001";
  newDiv.style.padding = "10px";
  newDiv.innerHTML = "<br/>&nbsp&nbsp正在处理数据，请您耐心等待...<br/>"+"<img src=\"<%=request.getContextPath()%>/pages/images/loading.gif\"/>";
  document.body.appendChild(newDiv);
  // 锁屏图层
  var newMask = document.createElement("div");
  newMask.id = overlayID;
  newMask.style.position = "absolute";
  newMask.style.zIndex = "998";
  newMask.style.width = _clientwidth + "px";
  newMask.style.height = _pageheight+"px";
  newMask.style.top = "0px";
  newMask.style.left = "0px";
  newMask.style.background = "#777";
  newMask.style.filter ="progid:DXImageTransform.Microsoft.Alpha(style=3,opacity=25,finishOpacity=75";
  //newMask.style.filter = "alpha(opacity=40)";
  newMask.style.opacity = "0.40";
  document.body.appendChild(newMask);
  // 关闭锁屏
<%--  var newA = document.createElement("a");--%>
<%--  newA.href = "#";--%>
<%--  newA.innerHTML = "关闭激活层";--%>
<%--  newA.onclick = function() {--%>
<%--  document.body.removeChild(docEle(overlayID));--%>
<%--  document.body.removeChild(docEle(msgID));--%>
<%--  return false;--%>
<%--  }--%>
<%--  newDiv.appendChild(newA);--%>
 }
  //关闭锁屏
 function cancel() 
 { 
	 if(docEle(overlayID)) {
		 document.body.removeChild(docEle(overlayID));
	 }
	 if(docEle(msgID))document.body.removeChild(docEle(msgID));
 }
 </script>
</html>
