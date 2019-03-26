<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet"	href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" 	type="text/css">
		<script type="text/javascript" src="/ftp/pages/js/jquery-1.3.2.min.js"></script>
		<script type="text/javascript" src="/ftp/pages/js/ajaxupload.js"></script>
<script rel="stylesheet" src="/ftp/pages/js/artDialog4.1.6/artDialog.source.js?skin=blue"></script>
<script src="/ftp/pages/js/artDialog4.1.6/plugins/iframeTools.source.js"></script>
		<style type="text/css">
#loading,ol {
	font-size: 14px;
	display: none;
	color: green;
	display: none;
}

ol {
	display: block;
}
</style>
		<script type="text/javascript" language="JavaScript1.2">
  $(function(){

   new AjaxUpload('#ClientExcelFile', {
    action: '/ftp/uploadExcel.action',
    name: 'ClientExcelFile',
    autoSubmit:true,
     onSubmit:function(ClientExcelFile, extension){
     if (extension && /^(xls)$/.test(extension))
     {
     /*
      $("#loading").html('<img src="/ftp/pages/images/loading.gif">');
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
      //上传成功设置保存路径
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
		$.ajax({
			url:'/ftp/Bzjbl_doImport.action',
			type: "POST",
			dataType:'json',  
			data: {
				savePath : $('#savePath').val()
			},
			success: function(data){
				if(data.success){
					   // 提示成功导入条数
					   art.dialog({
		            	    title:'成功',
		        		    icon: 'succeed',
		        		    content: '成功导入：'+data.successNum+'条!',
		        		    ok: function () {
						       doReload();
						       art.dialog.close();
		        		    }
		         	 	});
					}else{
						// 提示导入失败信息
						  var result ="<table><tr><th>ID</th><th>  原因     </th></tr>";
						  for(var i=0;i<data.contents.length;i++){
	                          result+="<tr><td>"+data.contents[i].ac_id+"</td><td>"+data.contents[i].reason+"</td></tr>"
	                      }
	                      result+="</table>";
						  art.dialog({
	    	            	    title:'成功',
	    	        		    icon: 'error',
	    	        		    content: '成功导入：'+data.successNum+'条;异常导入：'+data.failNum+'条;'+result,
	    	        		    ok: function () {
							       doReload();
							       art.dialog.close();
	    	        		    }
	    	         	 	});
						   
					}
        			//$(this).addClass("done")
        	}
		});
   // Form.action = "/ftp/JRSCYWDRCJ_doImport.action";
    //Form.submit();
   
  }
  function ok(){
     window.location.href ='/ftp/distribute.action?url=BZJBL';
  }
  function doReload() {
	     window.parent.location.reload();
	  }
  function doBack() {
		// window.location.href = "/ftp/JRSCYWDRCJ_List.action";
		window.returnValue = "true";
     window.close();
	  }
 </script>
	</head>

	<body>
		<form action="/ftp/BZJBL_doImport.action" method="post"
			id="doImport" name="doImport" enctype="multipart/form-data">
			<%
				Integer successNum = (Integer) request.getAttribute("successNum");
			%>
			<table class="table" width="80%" align="center">
				<tr>
					<td height="30" colspan="2" align="right">
						<a href="<%=request.getContextPath()%>/doc/bzjbl.xls"
							class="button">下载模板</a>
					</td>
				</tr>
				<tr>
					<th width="36%">
						<p align="right">
							选择Excel文件：
						</p>
					</th>
					<td width="64%">
						<input type="file" id="ClientExcelFile" name="ClientExcelFile"
							size="50">
						<div id="loading"></div>
					</td>
				</tr>
				<tr>
					<td height="30" colspan="2">
						<div align="center">
							<input type="button" name="Submit1" value="导&nbsp;&nbsp;入"
								onclick="my_check(this.form)" class="button">
							&nbsp;&nbsp;
							<input type="button" name="Reset" value="重&nbsp;&nbsp;置" class="button"
								onclick="javaScript:window.location.reload();">
						</div>
					</td>
				</tr>
			</table>
			<BR />
			<div align="center">
				<%
					if (successNum != null)
						out.print("成功导入" + successNum + "条数据！！");
				%>
			</div>
			<input name="savePath" id="savePath" type="hidden" value="" />
		</form>
	</body>
</html>

