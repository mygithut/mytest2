<%@ page contentType="text/html;charset=gb2312" pageEncoding="gb2312"%>
<%@page
	import="java.sql.*,java.util.*,com.dhcc.ftp.entity.*,com.dhcc.ftp.util.*"%>
<html>
	<head>
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/themes/green/css/core.css"
			type="text/css">
		<link rel="stylesheet"
			href="<%=request.getContextPath()%>/pages/css/inpage.css"
			type="text/css">
		<jsp:include page="../commonJs.jsp"></jsp:include>
		<title>Ա���˻�����-��������</title>
	</head>
	<%
	String[][] relatedEmp = (String[][])request.getAttribute("relatedEmps");
	String ac_id = (String)request.getAttribute("ac_id");
	String relType = (String)request.getAttribute("relType");
	String prdtNo = (String)request.getAttribute("prdtNo");
	Integer pcount = (Integer)request.getAttribute("pcount");
	if(null==pcount||pcount.equals("")){
	   pcount = 0;
	}
	%>
	<body>
		<form id="form1" name="form1" action="<%=request.getContextPath()%>/splcsz_modify.action?"	method="post">
			<table align="center" border="0">
				<tr>
					<td>
						<div align="center"><input type="button" value="���Ա��" onclick="addFirstTr(this)" class="button" id="addButton"/></div>
						<table class="table" width="750" align="center" id="editTable">
							<tr>
								<th width="100" align="center">
									Ա�����
								</th>
								<th width="100" align="center">
									Ա������
								</th>
								<th width="250" align="center">
									��������
								</th>
								<th width="100" align="center">
									���(Ԫ)
								</th>
								<th width="80" align="center">
								             ��ע
								</th>
								<th width="50" align="center">
									����
								</th>
							</tr>

							<%
							if(relatedEmp!=null){ for(int i=0;i<relatedEmp.length;i++){
					  	%>
							<tr>
								<td align="center" name="flowCode" width="100">
									<%=relatedEmp[i][0]%>
								</td>
								<td align="center" width="100">
									<%=relatedEmp[i][1] %>
								</td>
								<td align="center" width="250">
									<%=relatedEmp[i][3]%><%if(null==relatedEmp[i][3]||relatedEmp[i][3].equals("")){%><%}else{%>[<%=relatedEmp[i][2]%>]<%} %>
								</td>
								<td align="center" width="100">
									<input type="text" value="<%=relatedEmp[i][4]%>" id="rate" name="rate" size="10" onkeyup="value=value.replace(/[^\d\.]/g,'')">
								</td>
								<td>
								    <%if(i==0){ %>
								        �̶����+���
								    <%}else{ %>
								        �̶����
								    <%} %>
								</td>
								<td align="center">
									<input type="button" value="ɾ����" onclick="del(this)"
										class="button" />
								</td>
							</tr>
							<%}%>
						<%} %>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="4" align="center">
						<input type="button" class="button" onClick="doSubmit()"
							value="��&nbsp;&nbsp;��">
						<input type="button" name="Submit1" class="button"
							onClick="javascript:window.parent.location.reload();" value="��&nbsp;&nbsp;��">
					</td>
				</tr>
			</table>
	</form>
	<script type="text/javascript"> 
	var count =parseInt(<%=pcount%>); 
	//ɾ��һ��
	function del(t){
		j(t).parent().parent().remove();
		count = document.getElementById("editTable").rows.length-1;
	}
    //����ʱ��ӵ�һ������
    function addFirstTr(t){
    	var table = document.getElementById("editTable");
    	//��ȡ�Ѿ���ӵ�Ա��
	  	var row = table.rows;
	  	if(row.length > 10) {
	  		art.dialog({
          	    title:'��ʾ',
      		    icon: 'error',
      		    content: '����Ա����಻�ܳ���10λ��',
      		    cancel:true
       	    });
       	    return;
	  	}
	  	var relatedEmpNos="";
	  	for(var i=1; i<row.length;i++){
	  		relatedEmpNos += row[i].cells[0].innerHTML+",";
	  	}
	  	art.dialog.open('<%=request.getContextPath()%>/pages/sjgl/ygzhglEmpSearch.jsp?relatedEmpNos='+relatedEmpNos, {
		    title: 'Ա���б�',
		    width: 650,
		    height:300,
		    close: function (){
		    
	    	var empNo = art.dialog.data('empNo');
    		var empName = art.dialog.data('empName');
    		var brInfos = art.dialog.data('brInfos');
    		if(empNo!=null&&empNo!=""){
        		count++;
    			var newRow = table.insertRow(row.length); 
    	        var td1 = newRow.insertCell(0);  
    	  	    td1.innerHTML = empNo;   
    	  	    td1.setAttribute("align","center");
    	  	    td1.setAttribute("name","flowCode");
    	        var td2 = newRow.insertCell(1);  
    	  	    td2.innerHTML = empName;  
    	  	    td2.setAttribute("align","center"); 
    	        var td3 = newRow.insertCell(2);  
    	  	    td3.innerHTML = brInfos;  
    	  	    td3.setAttribute("align","center"); 
    	        var td4 = newRow.insertCell(3); 
    	  	    td4.setAttribute("align","center"); 
    	  	    td4.innerHTML = "<input type=\"text\" value=\"\"  size=\"10\" id=\"rate\" name=\"rate\" onblur=\"isInt(this,'���(Ԫ)')\"/>";   
    	  	    var td5 = newRow.insertCell(4); 
  	  	        td5.setAttribute("align","center"); 
                if(count==1){
  	  	            td5.innerHTML = "�̶����+���";   
                }else{
                	td5.innerHTML = "�̶����";   
                }
      	     	var td6 = newRow.insertCell(5); 
  	  	        td6.setAttribute("align","center"); 
  	  	        td6.innerHTML = "<input type=\"button\" value=\"ɾ����\" onclick=\"del(this)\" class=\"button\" />";   
  	            art.dialog.data('empNo',"");
    		    art.dialog.data('empName',"");
    		    art.dialog.data('brInfos',"");
	    	}
	     }
		});	
        
    }
    function doSubmit(url) {
		var empNo="";
		var rate="";
		var table=document.getElementById("editTable");
		if(table.rows.length == 1) {
			art.dialog({
          	    title:'��ʾ',
      		    icon: 'error',
      		    content: '������У�',
      		    cancel:true
       	    });
		}
		var rates = document.getElementsByName("rate");
<%--		var sumRate = 0.0;--%>
		for(var m = 0; m < rates.length;m++) {
			if(rates[m].value==""){
	    		art.dialog({
              	    title:'��ʾ',
          		    icon: 'error',
          		    content: '��������ʣ�',
          		    cancel:true
           	    });
	           	return;
			}
			else {
				    rate+=rates[m].value+"@";
<%--				sumRate+=parseFloat(rates[m].value);--%>
<%--				rate += (parseFloat(rates[m].value)/100.0).toFixed(3)+"@";--%>
			}
		}
<%--		if (sumRate!=100){--%>
<%--			 art.dialog({--%>
<%--             	    title:'ʧ��',--%>
<%--         		    icon: 'error',--%>
<%--         		    content: '������ı��ʲ���򳬹�100%��',--%>
<%--         		    cancel:true--%>
<%--          	 });--%>
<%--			return;--%>
<%--		}--%>
		for (var i = 1; i < table.rows.length; i++)
		{
			var e = table.rows[i].cells[0].innerHTML;
	    	empNo += trim(e)+"@";
		}
     	var url="<%=request.getContextPath()%>/YGZHGL_save.action";
		new Ajax.Request(url, {
			method : 'post',
			parameters : {
			ac_id:'<%=ac_id%>',empNo:empNo,rate:rate,relType:'<%=relType%>',prdtNo:'<%=prdtNo%>'
			},
			onSuccess : function(res) {
			    debugger;
			    console.info(res.responseText);
			    if(res.responseText=='1'){
                    art.dialog({
                        title:'��ʾ',
                        icon: 'succeed',
                        content: '����ɹ���',
                        ok: function() {
                            parent.parent.parent.parent.parent.openNewDiv();
                            window.parent.parent.frames.downframe.location.reload();
                        }
                    });
				}else{
                    art.dialog({
                        title:'��ʾ',
                        icon: 'warning',
                        content: 'ͳ�Ʊ������ڸ��£����Ժ����ԣ�',
                        ok: function() {
                            parent.parent.parent.parent.parent.openNewDiv();
                            window.parent.parent.frames.downframe.location.reload();
                        }
                    });
				}

			}
		});
	}	
    function trim(str){ //ɾ���������˵Ŀո�
        return str.replace(/(^\s*)|(\s*$)/g, "");
    }
	</script>
	</body>
</html>