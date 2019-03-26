<%@ page contentType="text/html;charset=GB2312" pageEncoding="GB2312"%>
<html>
<head>
<title></title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/themes/green/css/core.css" type="text/css">
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/jquery-1.3.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/ajaxupload.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/artDialog4.1.6/artDialog.source.js?skin=blue"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/artDialog4.1.6/plugins/iframeTools.source.js"></script>
<style type="text/css">
     #loading,ol{
      font-size:14px;
      display:none;
      color:red;
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
	  //alert("开始导入！");
      var o = Form.savePath.value;
      //先检查是否有文件上传上来了
	  if(o==null||o==""){
		  art.dialog({
	      	    title:'失败',
	  		    icon: 'error',
	  		    content: '请先选择导入的文件！',
	  		    cancelVal: '关闭',
	  		    cancel: true
	   	    });
		  return false;	
		}
     // Form.action = "<%=request.getContextPath()%>/PTZWH_doImport.action";
     // Form.submit();
     parent.parent.parent.parent.openNewDiv();
     $.ajax({
			url:'<%=request.getContextPath()%>/PTZWH_doImport.action',
			type: "POST",
			dataType:'json',  
			data: {
				savePath : $('#savePath').val()
			},
			success: function(data){
                parent.parent.parent.parent.cancel();
					if(data.typeError){
						art.dialog({
				      	    title:'失败',
				  		    icon: 'error',
				  		    content: data.typeError+'！',
				  		    cancelVal: '关闭',
				  		    cancel: function () {
							    doReload();
		        		    }
				   	    });
					}
					else {
							   // 提示成功导入条数
							  art.dialog({
				            	    title:'成功',
				        		    icon: 'succeed',
				        		    content: '成功导入：'+data.successNum+'条！',
				        		    ok: function () {
								         doBack();
				        		    }
				         	 	});
							}
        	}
		});
  }
  function doReload() {
     window.location.reload();
  }
  function doBack() {
	 window.location.href = "<%=request.getContextPath()%>/PTZWH_List.action";
  }
 </script>
</head>

<body>
<%Integer successNum = (Integer)request.getAttribute("successNum");
 %>
<form action="" method="post">
 <table class="table" width="80%" align="center">
	<tr>
		<td height="30" colspan="2" align="right"><a href="<%=request.getContextPath()%>/doc/Common_yield.xls" class="button">下载模板</a></td>
	</tr>
	<tr>
		<th width="36%">
		<p align="right">选择Excel文件：</p>
		</th>
		<td width="64%"><input type="file" id="ClientExcelFile" name="ClientExcelFile" size="50">
		</td>
	</tr>
	 <tr><td colspan="2"><div id="loading" align="center"></div></td></tr>
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

