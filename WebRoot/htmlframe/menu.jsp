<%@ page language="java" import="java.util.*,com.dhcc.ftp.entity.TelMst" pageEncoding="GBK"%>
<html>
<style type="text/css">
html,body {margin:0; padding:0;}
</style>
<jsp:include page="../pages/commonJs.jsp" />
<body>
<%//Integer ftpSetNum = (Integer)session.getAttribute("ftpSetNum");//�ж��ٸ�������δ���ж��۲������� 
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
<%--<%if((bean.getRoleMst().getRoleNo().equals("216") || bean.getRoleMst().getRoleNo().equals("217") || bean.getRoleMst().getRoleNo().equals("218")) && ftpSetNum != 0) {%>//ϵͳ����Ա���룬����δ��ɵ�����--%>
<%--    var alertStr = "";--%>
<%--    <%if(bean.getRoleMst().getRoleNo().equals("216")){%>//ʡ������Ա--%>
<%--    alertStr = '��<%=ftpSetNum%>��������δ���ж��۲�������,�Ƿ�ȥ����?';--%>
<%--    <%}else {%> --%>
<%--    alertStr = '��������δ���ж��۲�������,�Ƿ�ȥ����?';--%>
<%--    <%}%>--%>
<%--    art.dialog({--%>
<%--        title:'����',--%>
<%--    	icon: 'question',--%>
<%--        content: alertStr,--%>
<%--        ok: function () {--%>
<%--    	    window.frames.Ganame.frames.topFrame.changed("7");--%>
<%--    	    window.frames.Ganame.frames.centerFrame.location.href="left_center.jsp?isFirst=1&menu_parent=I";--%>
<%--        	window.frames.Ganame.frames.rightFrame.location.href="<%=request.getContextPath()%>/distribute.action?url=DJCLPZ";--%>
<%--        },--%>
<%--        cancelVal: '�ر�',--%>
<%--        cancel: true //Ϊtrue�ȼ���function(){}--%>
<%--    });--%>
<%--<%}else if((!bean.getRoleMst().getRoleNo().equals("216") && !bean.getRoleMst().getRoleNo().equals("217") && !bean.getRoleMst().getRoleNo().equals("218")) && ftpSetNum == 0) {%>--%>
<%--   document.getElementById("Ganame").style.display = "none";--%>
<%--   art.dialog({--%>
<%--  	        title:'ʧ��',--%>
<%--		    icon: 'error',--%>
<%--		    content: 'ϵͳ��δ��ʼ��-�����ã�����ϵͳ����Ա��ϵ��',--%>
<%--   		    ok: function () {--%>
<%--	            window.parent.location.href = "../login.jsp";--%>
<%--   		        return true;--%>
<%--   		    }--%>
<%--	 });--%>
<%--<%}%>--%>
//<�������ڴ�����>����
var overlayID="overlay";
var msgID = "overlayMsg";
var docEle = function() {
  return document.getElementById(arguments[0]) || false;
 }
 function openNewDiv() {
    
  if (docEle(overlayID)) document.removeChild(docEle(overlayID));
  if (docEle(msgID)) document.removeChild(docEle(msgID));
  // ��Ϣ��ʾ
  var newDiv = document.createElement("div");
  newDiv.id = msgID;
  newDiv.style.position = "absolute";
  newDiv.style.zIndex = "999";
  newDiv.style.width = "220px";
  newDiv.style.height = "90px";
  var scrolltop = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
   
  var _clientheight=0;

  //ie FF ����DOCTYPEʱ��������  
  _clientheight = Math.min(document.body.clientHeight , document.documentElement.clientHeight);
  if(_clientheight==0)
  _clientheight= Math.max(document.body.clientHeight , document.documentElement.clientHeight);
    
  var _clientwidth= document.documentElement.clientWidth || document.body.clientWidth;
  //����ҳ��ĸ߶�
  var _pageheight = Math.max(document.body.scrollHeight,document.documentElement.scrollHeight);
    
  var msgtop = (scrolltop+((_clientheight)/2)-50)+"px";
  var msgleft = (_clientwidth-200)/2+"px";
  newDiv.style.top = msgtop;
  newDiv.style.left =msgleft; // ��Ļ����
  newDiv.style.background = "#EFEFEF";
  newDiv.style.border = "1px solid #860001";
  newDiv.style.padding = "10px";
  newDiv.innerHTML = "<br/>&nbsp&nbsp���ڴ������ݣ��������ĵȴ�...<br/>"+"<img src=\"<%=request.getContextPath()%>/pages/images/loading.gif\"/>";
  document.body.appendChild(newDiv);
  // ����ͼ��
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
  // �ر�����
<%--  var newA = document.createElement("a");--%>
<%--  newA.href = "#";--%>
<%--  newA.innerHTML = "�رռ����";--%>
<%--  newA.onclick = function() {--%>
<%--  document.body.removeChild(docEle(overlayID));--%>
<%--  document.body.removeChild(docEle(msgID));--%>
<%--  return false;--%>
<%--  }--%>
<%--  newDiv.appendChild(newA);--%>
 }
  //�ر�����
 function cancel() 
 { 
	 if(docEle(overlayID)) {
		 document.body.removeChild(docEle(overlayID));
	 }
	 if(docEle(msgID))document.body.removeChild(docEle(msgID));
 }
 </script>
</html>
