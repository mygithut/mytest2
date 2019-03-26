<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
<script type="text/javascript" src="/ftp/pages/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="/ftp/pages/js/ajaxupload.js"></script>
<style type="text/css">
     #loading,ol{
      font-size:14px;
      display:none;
      color:green;
      display:none;
     }
     ol{
      display:block;
     }
    </style>
<script type="text/javascript" language="JavaScript1.2">
  $(function(){

   new AjaxUpload('#ClientExcelFile', {
    action: '<%=request.getContextPath()%>/uploadExcel.action',
    name: 'ClientExcelFile',
    autoSubmit:true,
     onSubmit:function(ClientExcelFile, extension){
     if (extension && /^(xls)$/.test(extension))
     {
     /*
      $("#loading").html('<img src="<%=request.getContextPath()%>/pages/images/loading.gif">');
      $("#loading").show();
      */
     }
     else
     {
      $("#loading").html("你所选择的文件不受系统支持,请选择.xls文件");
      $("#loading").show();
      return false;
     }
    },
    onComplete : function(ClientExcelFile, extension){
      $("#loading").html(ClientExcelFile+"文件上传成功");
      $("#loading").show();
      $("#ClientExcelFile").attr("disabled","disabled");
     document.getElementById("savePath").value = extension;
    }
   });
  });
  
  function my_check(Form){
    var o = Form.savePath.value;
  //先检查是否有文件上传上来了
	  if(o==null||o==""){
		  alert("请先选择导入的文件!");
		  return false;	
		}
    Form.action = "<%=request.getContextPath()%>/ZYSHGLL_doImport.action";
    Form.submit();
<%--  	var url = "importExcel.action";--%>
<%--        new Ajax.Request( --%>
<%--        url, --%>
<%--         {   --%>
<%--          method: 'post',   --%>
<%--          parameters: {savePath:savePath,t:new Date().getTime()},--%>
<%--          onSuccess: function(extension) --%>
<%--           {  --%>
<%--              if(extension.responseText=="false"){--%>
<%--                 ymPrompt.errorInfo({message:'Excel文件内容格式不正确，请检查！！',title:'failure',width:250,height:180,showMask:false,handler: ok ,btn:[['确定','ok']]})--%>
<%--             }else{--%>
<%--                 ymPrompt.succeedInfo({message:'Excel文件导入成功！！',title:'success',width:250,height:150,showMask:false,handler: ok ,btn:[['确定','ok']]})--%>
<%--             }}--%>
<%--          }--%>
<%--       );--%>
  }
  function doReload() {
     window.location.reload();
  }
  function doBack() {
	 window.location.href = "<%=request.getContextPath()%>/ZYSHGLL_List.action";
  }
 </script>
</head>

<body>
<%Integer successNum = (Integer)request.getAttribute("successNum");
 %>
<form action="" method="post">
 <table class="table" width="80%" align="center">
	<tr>
		<td height="30" colspan="2" align="right"><a href="<%=request.getContextPath()%>/doc/Pledge_repo_rate.xls" class="button">下载模板</a></td>
	</tr>
	<tr>
		<th width="36%">
		<p align="right">选择Excel文件：</p>
		</th>
		<td width="64%"><input type="file" id="ClientExcelFile" name="ClientExcelFile" size="50">
		<div id="loading"></div></td>
	</tr>
	<tr>
		<td height="30" colspan="2">
			<div align="center"><input type="button" name="Submit1" value="导&nbsp;&nbsp;入" onclick="my_check(this.form)" class="button"> &nbsp;&nbsp;
		 <input type="button" name="Reset" value="重&nbsp;&nbsp;置" class="button" onclick="doReload()">&nbsp;&nbsp;
		 <input type="button" name="Submit1" value="返&nbsp;&nbsp;回" onclick="doBack()" class="button">
		 </div>
		</td>
	</tr>
	
</table>
<br/>
<div align="center">
<%if(successNum != null) out.print("成功导入"+successNum+"条数据！！"); %>
</div>
<input name="savePath" id="savePath" type="hidden" value=""/>
</form>
</body>
</html>

