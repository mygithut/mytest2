<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<TITLE>欢迎进入商业银行内部资金转移定价管理系统！</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link type="text/css" href="<%=request.getContextPath()%>/pages/css/login.css" rel="stylesheet">
<%String error = (String)request.getAttribute("error"); %>
<script type="text/javascript" language=javascript src="<%=request.getContextPath()%>/pages/js/prototype.js"></script>
<script rel="stylesheet" src="<%=request.getContextPath()%>/pages/js/artDialog4.1.6/artDialog.source.js?skin=blue"></script>
</head>
<body>
<form action="<%=request.getContextPath() %>/zuayLogin_login.action"  method="post" name="loginForm" id="loginForm">
<div class="input_box">
	<div class="input">
    <input type="text" name="username" id="username" value=""  maxlength="6" class="user_name" />
    <input type="password" class="password" id="password" name="password" maxlength="6" value=""/>
<%--    <input type="text" name="rand" size="15" class="code"/>--%>
<%--    <img name="randImage" id="randImage" src="<%=request.getContextPath()%>/zuayLogin.action?image=rand" width="80" height="30" border="0" align="absmiddle" />--%>
<%--    <a href="javascript:loadimage();">换一张</a>--%>
    </div>
    <div class="nav_box">
    <a href="#" onclick="submit_onclick(this.form)"><img src="images/login/nav.png" border="0" /></a>
    </div>
</div>
</form>
<br/>
<div id="mydiv" style="display:none">
<center>
登录中，请稍候...<br>
<img src="/ftp/pages/images/load.gif"/><!---动态图片，这里可以省略-->
</center>
</div>
</body>
<script type="text/javascript">
<%if(error != null){%>
   alert('<%=error%>');
<%}%>
document.loginForm.username.focus();
var timer;
function showMessage(){
    var obj=document.getElementById("mydiv");
    obj.style.display="block";
}
function hideMessage(){
    document.getElementById("mydiv").style.display="none";
}

function submit_onclick(){
	var tlr =document.getElementById("username").value;
	var pas=document.getElementById("password").value;
	if(tlr.length==0||pas.length==0){
		art.dialog({
       	    title:'失败',
   		    icon: 'error',
   		    content: '操作员号或密码为空！',
   		    cancelVal: '关闭',
   		    cancel: true
    	 });
		return;
	}
	timer=setInterval(showMessage,0);	
	$('loginForm').request({   
        method:"post",
        parameters:{t:new Date().getTime()},
        onComplete: function(res){ 
        	if('error1'==res.responseText){
        		art.dialog({
               	    title:'失败',
           		    icon: 'error',
           		    content: '操作员不存在！',
           		    cancelVal: '关闭',
           		    cancel: true
            	 });
        	}else if('error2'==res.responseText){
        		art.dialog({
               	    title:'失败',
           		    icon: 'error',
           		    content: '密码错误！',
           		    cancelVal: '关闭',
           		    cancel: true
            	 });
        	}else if('error3'==res.responseText) {
        		art.dialog({
        	        title:'提示',
        	    	icon: 'question',
        	        content: '该账号已经有用户登录，是否继续?',
        	        ok: function () {
        			    var url = "<%=request.getContextPath()%>/zuayLogin_removeSessionAndLogin.action";
     			        new Ajax.Request(url, {
     				       method : 'post',
     				       parameters : {
       				           username : tlr
     				       },
     				       onSuccess : function() {
       	                       window.location.href = "<%=request.getContextPath()%>/htmlframe/index.html";
     				      }
     			        });
        	        },
        	        cancelVal: '关闭',
        	        cancel: true //为true等价于function(){}
        	    });
        	}else{
                window.location.href = "<%=request.getContextPath()%>/htmlframe/index.html";
        	}
        	clearInterval(timer);
            hideMessage();
      } 
    });
	
}
function loadimage(){ 
	document.getElementById("randImage").src = "<%=request.getContextPath()%>/zuayLogin_login.action?image=rand?"+Math.random(); 
} 
document.onkeydown=function()
{
    if ((event.keyCode == 13) ) {
       submit_onclick();
    }
}

</script>
</html>
