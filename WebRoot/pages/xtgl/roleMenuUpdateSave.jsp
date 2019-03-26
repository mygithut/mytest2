<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<style type="text/css">
<!--
.STYLE1 {font-size: 9px}
.STYLE2 {font-size: 9pt}
-->
</style>
<body  topmargin=0 leftmargin=0 onload="">
<style type="text/css">
<!--
.STYLE1 {font-size: 12px}
-->
</style>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
      	  <tr>
            <td height="39" background="<%=request.getContextPath() %>/pages/images/input_c61_03.gif" align="center" valign="middle">
              <table width="100%" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	              <td width="3%" height="19">&nbsp;</td>
	              <td width="3%"><img src="<%=request.getContextPath() %>/pages/images/input_c61_06.gif" alt="" width="9" height="13"></td>
	              <td width="94%"><font color="#498700"><span class="STYLE1">系统管理&gt;&gt;角色管理&gt;&gt;菜单设置已成功</span></font></td>  
	            </tr>
              </table>
            </td>
          </tr>
          </table>
    </td>
  </tr>
  <tr>
    <td><img src="<%=request.getContextPath() %>/pages/images/system.gif" alt="" width="53" height="69"></td>
  </tr>
  <tr>
    <td height="44"><div align="center"><font color="#ff9c00"><b>菜单设置已成功</b></font><br></div>
    <br></td>
  </tr>
  <tr>
    <td>
	
     <table width="70%" border="0" align="center" cellpadding="0" cellspacing="0" >
    
	
	   <tr>
        <td width="4%"><img src="<%=request.getContextPath() %>/pages/images/input_c1_10.gif" alt="" width="25" height="25"></td>
        <td width="91%" background="<%=request.getContextPath() %>/pages/images/input_c11_12.gif">&nbsp;</td>
        <td width="5%"><img src="<%=request.getContextPath() %>/pages/images/input_c1_12.gif" alt="" width="34" height="25"></td>
      </tr>
    
	
	 <tr>
        <td background="<%=request.getContextPath() %>/pages/images/input_c21_18.gif" height="150">&nbsp;</td>
     
	    <td bgcolor="#F5FFDE">
	 
<form action="/creditapp/tlrctlChgpwd.do" method="post"> 

	  <table width="100%" border="0" cellspacing="0" cellpadding="0">
		   <tr>
		     <td colspan="2"><p align="right"><font color="#316400"><span class="STYLE1">&nbsp;&nbsp;&nbsp;&nbsp;</span></font></td>
          
         </tr>
		 <tr>
		     <td colspan="2" align="center">&nbsp;<span class="STYLE1"><font color="#ff9c00"><B><h4><%=request.getAttribute("rolename") %>对应菜单设置成功!</h4></B></font></td>
         </tr>
		<tr>
		     <td colspan="2">&nbsp;</td>
         </tr>
		 
		 <tr >
		     <td align="center" colspan="2">  
			  <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
		    	     <tr>
		    	      <br>  
		    	      <td width="47%" height="26" align="center" colspan="2"><input  type="button" name="Submit1"  onClick="javascript:golist()" style="background:#FFFFFF url('<%=request.getContextPath() %>/pages/images/back.gif') repeat-x center; width: 79px; height: 27px; border-width:0px">&nbsp;&nbsp;</td>
		    	      
		    	   </tr>  
		     </table>
			</td>
		   </tr>
		 </table>
		 
		</form>
		
	</td>
	
        <td background="<%=request.getContextPath() %>/pages/images/input_c41_19.gif">&nbsp;</td>
      </tr>
	  
	  
      <tr>
        <td><img src="<%=request.getContextPath() %>/pages/images/input_c51_17.gif" alt="" width="25" height="24"></td>
        <td background="<%=request.getContextPath() %>/pages/images/input_c31_32.gif">&nbsp;</td>
        <td><img src="<%=request.getContextPath() %>/pages/images/input_c1_27.gif" alt="" width="34" height="24"></td>
      </tr>
	  
	  
    </table>
	
</td>
  </tr>
  
</table>
  
</body>

</html>

<script  type="text/javascript" language="JavaScript1.2" >
function my_check(FormName)
{
 newpasswd=document.all("newpasswd").value;

 if(newpasswd.length!=6)
 {
  alert("密码长度必须为6位");
  return false;
}


	FormName.submit()	;
	
}
function golist(){
	window.open("<%=request.getContextPath()%>/role.action","_self","");
}
</script>
