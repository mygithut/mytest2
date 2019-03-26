//查找下拉列表框内容并填充
function fillSelect(id,url){
      	//alert(0);
    	document.getElementById(id).options.length=0;
        document.getElementById(id).add(new Option("--请选择--",""));
  //     	$('currencySelectId').disabled = true;
       	var currencyList ;
        new Ajax.Request( 
        url, 
         {   
          method: 'post',   
          parameters: {t:new Date().getTime()},
          onSuccess: function(transport) 
           {  
              currencyList = transport.responseText.split(",");
              $A(currencyList).each(
                 function(index){
                 	//Option参数，前面是text，后面是value
                      var opt= new Option(index.split('|')[1],index.split('|')[0]);
			          document.getElementById(id).add(opt);
                 }
              );
            }
          }
       );
       
}
///查找数据库，初始化下拉列表框（默认选中）
function fillSelectLast(id,url,value){
	document.getElementById(id).options.length=0;
   	var currencyList ;
    new Ajax.Request( 
    url, 
     {   
      method: 'post',   
      parameters: {t:new Date().getTime()},
      onSuccess: function(transport) 
       {  
          currencyList = transport.responseText.split(",");
          $A(currencyList).each(
             function(index){
             	//Option参数，前面是text，后面是value
                  var opt= new Option(index.split('|')[1],index.split('|')[0]);
		          document.getElementById(id).add(opt);
		          
             }
          );
          for (var i = 0 ; i < currencyList.length+1; i++) {
	          if(document.getElementById(id).options[i].value==value){
	        	  document.getElementById(id).selectedIndex=i;
	          }
          }
        }
      }
   );  
}
//查找下拉列表框内容并填充
function fillSelect_wb(id,url){
      	//alert(0);
    	document.getElementById(id).options.length=0;
  //     	$('currencySelectId').disabled = true;
    	document.getElementById(id).add(new Option("请选择",""));
       	var currencyList ;
       	var url = url;
        new Ajax.Request( 
        url, 
         {   
          method: 'post',   
          parameters: {selectID:id,t:new Date().getTime()},
          onSuccess: function(transport) 
           {  
              currencyList = transport.responseText.split(",");
              $A(currencyList).each(
                 function(index){
                 	//Option参数，前面是text，后面是value
                      var opt= new Option(index.split('|')[1],index.split('|')[0]);
			          document.getElementById(id).add(opt);
                 }
              );
            }
          }
       );
       
}

//菜单联动，查找下拉列表框内容并填充
function fillSelected(id,url,id2){
	 	//alert(0);
    	document.getElementById(id).options.length=0;
  //     	$('currencySelectId').disabled = true;
    	document.getElementById(id).add(new Option("请选择",""));
       	var currencyList ;
       	var url = url;
        new Ajax.Request( 
        url, 
         {   
          method: 'post',   
          parameters: {typeid:id2,t:new Date().getTime()},
          onSuccess: function(transport) 
           {  
              currencyList = transport.responseText.split(",");
              $A(currencyList).each(
                 function(index){
                 	//Option参数，前面是text，后面是value
                      var opt= new Option(index.split('|')[1],index.split('|')[0]);
			          document.getElementById(id).add(opt);
                 }
              );
            }
          }
       );
       
}
//查找数据库，初始化下拉列表框（默认选中）
function fillSelectById(id,url){
    //alert(id);
	
	//alert(SelectedName);
   	document.getElementById(id).options.length=0;
   	var currencyList ;
   	var url = url;
    new Ajax.Request( 
    url, 
     {   
      method: 'post',   
      parameters: {t:new Date().getTime()},
      onSuccess: function(transport) 
       {  
          currencyList = transport.responseText.split(",");
          $A(currencyList).each(
             function(index){
             	//Option参数，前面是text，后面是value
                  var opt= new Option(index.split('|')[1],index.split('|')[0]);
		          document.getElementById(id).add(opt);
             }
          );
        }
      }
   );  
}
//查找数据库，初始化下拉列表框（默认选中）
function fillSelectById2(id,value,show,uri){
	var url;
   	document.getElementById(id).options.length=0;
   	if(value==""){
   		document.getElementById(id).add(new Option("--请选择--",""));
   		url = uri;
   	}else{
   		document.getElementById(id).add(new Option(show,value));
   		url = uri+"?"+id+"="+value;
   	}
   	var currencyList ;
    new Ajax.Request( 
    url, 
     {   
      method: 'post',   
      parameters: {t:new Date().getTime()},
      onSuccess: function(transport) 
       { // alert(transport.responseText);
          currencyList = transport.responseText.split(",");
          $A(currencyList).each(
             function(index){
             	//Option参数，前面是text，后面是value
                  var opt= new Option(index.split('|')[1],index.split('|')[0]);
		          document.getElementById(id).add(opt);
             }
          );
        }
      }
   );  
}
//根据配置文件填充下拉列表框
function fillSelectByDispose(id1,id2){
      	//alert(0);
    	document.getElementById(id1).options.length=0;
  //     	$('currencySelectId').disabled = true;
    	document.getElementById(id1).add(new Option("请选择",""));
    	var List = document.getElementById(id2).value.split(",");
    	$A(List).each(
             function(index){
                	//Option参数，前面是text，后面是value
                 var opt= new Option(index.split('|')[0],index.split('|')[1]);
			     document.getElementById(id1).add(opt);
             }
        );
       
}
//币种查找下拉列表框内容并填充
function fillSelectContent(id){
      	//alert(0);
    	document.getElementById(id).options.length=0;
  //     	$('currencySelectId').disabled = true;
    	document.getElementById(id).add(new Option("人民币","01"));
       	var currencyList ;
       	var url = "/ftp/queryCurrency.action";
        new Ajax.Request( 
        url, 
         {   
          method: 'post',   
          parameters: {t:new Date().getTime()},
          onSuccess: function(transport) 
           {  
              currencyList = transport.responseText.split(",");
              $A(currencyList).each(
                 function(index){
                 	//Option参数，前面是text，后面是value
                      var opt= new Option(index.split('|')[1],index.split('|')[0]);
			          document.getElementById(id).add(opt);
                 }
              );
            }
          }
       );
       
}


//根据业务条线生成产品大类下拉列表框
function getPrdtCtgName(businessNo) {
	var businessN = $('businessNo').value;
	if (businessN != '') {
		$("prdtCtgNo").disabled = false;
		document.getElementById("prdtCtgNo").options.length=0;
		document.getElementById("prdtCtgNo").add(new Option("--请选择--",""));
	   	var prdtCtgNameList;
	   	var url = '/ftp/fillSelect_getPrdtCtgName.action?businessNo='+businessN;
	    new Ajax.Request( 
	    url, 
	     {   
	      method: 'post',   
	      parameters: {t:new Date().getTime()},
	      onSuccess: function(transport) 
	       {  
	    	  prdtCtgNameList = transport.responseText.split(",");
	          $(prdtCtgNameList).each(
	             function(index){
	             	//Option参数，前面是text，后面是value
	                  var opt= new Option(index.split('|')[1],index.split('|')[0]);
			          document.getElementById("prdtCtgNo").add(opt);
	             }
	          );
	        }
	      }
	   );
	}else {
		$("prdtCtgNo").value = '';
		$("prdtCtgNo").disabled = true;
		if($("prdtNo") != null) {
			$("prdtNo").value = '';
			$("prdtNo").disabled = true;
		}
	}
}
//根据产品大类生成产品下拉列表框
function getPrdtNameByPCN(prdtCtgNo) {
	var prdtCtg = $(prdtCtgNo).value;
	
	if (prdtCtg != '') {
		
		$("prdtNo").disabled = false;
		document.getElementById("prdtNo").options.length=0;
		document.getElementById("prdtNo").add(new Option("--请选择--",""));
	   	var prdtNameList ;
	   	var url = '/ftp/fillSelect_getPrdtName.action?prdtCtgNo='+prdtCtg;
	    new Ajax.Request( 
	    url, 
	     {   
	      method: 'post',   
	      parameters: {t:new Date().getTime()},
	      onSuccess: function(transport) 
	       {  
	    	  prdtNameList = transport.responseText.split(",");
	          $A(prdtNameList).each(
	             function(index){
	             	//Option参数，前面是text，后面是value
	                  var opt= new Option(index.split('|')[1],index.split('|')[0]);
			          document.getElementById("prdtNo").add(opt);
	             }
	          );
	        }
	      }
	   );
	}else {
		$("prdtNo").value = '';
		$("prdtNo").disabled = true;
	}
}
