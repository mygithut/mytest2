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
      $("#loading").html("����ѡ����ļ�����ϵͳ֧��,��ѡ��.xls�ļ�");
      $("#loading").show();
      return false;
     }
    },
    onComplete : function(ClientExcelFile, extension){
      $("#loading").html(ClientExcelFile+"�ļ��ϴ��ɹ�");
      $("#loading").show();
      $("#ClientExcelFile").attr("disabled","disabled");
     document.getElementById("savePath").value = extension;
    }
   });
  });
  
  function my_check(Form){
	  //alert("��ʼ���룡");
      var o = Form.savePath.value;
      //�ȼ���Ƿ����ļ��ϴ�������
	  if(o==null||o==""){
		  art.dialog({
	      	    title:'ʧ��',
	  		    icon: 'error',
	  		    content: '����ѡ������ļ���',
	  		    cancelVal: '�ر�',
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
				      	    title:'ʧ��',
				  		    icon: 'error',
				  		    content: data.typeError+'��',
				  		    cancelVal: '�ر�',
				  		    cancel: function () {
							    doReload();
		        		    }
				   	    });
					}
					else {
							   // ��ʾ�ɹ���������
							  art.dialog({
				            	    title:'�ɹ�',
				        		    icon: 'succeed',
				        		    content: '�ɹ����룺'+data.successNum+'����',
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
		<td height="30" colspan="2" align="right"><a href="<%=request.getContextPath()%>/doc/Common_yield.xls" class="button">����ģ��</a></td>
	</tr>
	<tr>
		<th width="36%">
		<p align="right">ѡ��Excel�ļ���</p>
		</th>
		<td width="64%"><input type="file" id="ClientExcelFile" name="ClientExcelFile" size="50">
		</td>
	</tr>
	 <tr><td colspan="2"><div id="loading" align="center"></div></td></tr>
	<tr>
		<td height="30" colspan="2">
			<div align="center"><input type="button" name="Submit1" value="��&nbsp;&nbsp;��" onclick="my_check(this.form)" class="button"> &nbsp;&nbsp;
		 <input type="button" name="Reset" value="��&nbsp;&nbsp;��" class="button" onclick="doReload()">&nbsp;&nbsp;
		 <input type="button" name="Submit1" value="��&nbsp;&nbsp;��" onclick="doBack()" class="button">
		 </div>
		</td>
	</tr>
	
</table>
<br/>
<div align="center">
<%if(successNum != null) out.print("�ɹ�����"+successNum+"�����ݣ���"); %>
</div>
<input name="savePath" id="savePath" type="hidden" value=""/>
</form>
</body>
</html>

