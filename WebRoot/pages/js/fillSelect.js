//���������б�����ݲ����
function fillSelect(id,url){
      	//alert(0);
    	document.getElementById(id).options.length=0;
        document.getElementById(id).add(new Option("--��ѡ��--",""));
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
                 	//Option������ǰ����text��������value
                      var opt= new Option(index.split('|')[1],index.split('|')[0]);
			          document.getElementById(id).add(opt);
                 }
              );
            }
          }
       );
       
}
///�������ݿ⣬��ʼ�������б��Ĭ��ѡ�У�
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
             	//Option������ǰ����text��������value
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
//���������б�����ݲ����
function fillSelect_wb(id,url){
      	//alert(0);
    	document.getElementById(id).options.length=0;
  //     	$('currencySelectId').disabled = true;
    	document.getElementById(id).add(new Option("��ѡ��",""));
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
                 	//Option������ǰ����text��������value
                      var opt= new Option(index.split('|')[1],index.split('|')[0]);
			          document.getElementById(id).add(opt);
                 }
              );
            }
          }
       );
       
}

//�˵����������������б�����ݲ����
function fillSelected(id,url,id2){
	 	//alert(0);
    	document.getElementById(id).options.length=0;
  //     	$('currencySelectId').disabled = true;
    	document.getElementById(id).add(new Option("��ѡ��",""));
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
                 	//Option������ǰ����text��������value
                      var opt= new Option(index.split('|')[1],index.split('|')[0]);
			          document.getElementById(id).add(opt);
                 }
              );
            }
          }
       );
       
}
//�������ݿ⣬��ʼ�������б��Ĭ��ѡ�У�
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
             	//Option������ǰ����text��������value
                  var opt= new Option(index.split('|')[1],index.split('|')[0]);
		          document.getElementById(id).add(opt);
             }
          );
        }
      }
   );  
}
//�������ݿ⣬��ʼ�������б��Ĭ��ѡ�У�
function fillSelectById2(id,value,show,uri){
	var url;
   	document.getElementById(id).options.length=0;
   	if(value==""){
   		document.getElementById(id).add(new Option("--��ѡ��--",""));
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
             	//Option������ǰ����text��������value
                  var opt= new Option(index.split('|')[1],index.split('|')[0]);
		          document.getElementById(id).add(opt);
             }
          );
        }
      }
   );  
}
//���������ļ���������б��
function fillSelectByDispose(id1,id2){
      	//alert(0);
    	document.getElementById(id1).options.length=0;
  //     	$('currencySelectId').disabled = true;
    	document.getElementById(id1).add(new Option("��ѡ��",""));
    	var List = document.getElementById(id2).value.split(",");
    	$A(List).each(
             function(index){
                	//Option������ǰ����text��������value
                 var opt= new Option(index.split('|')[0],index.split('|')[1]);
			     document.getElementById(id1).add(opt);
             }
        );
       
}
//���ֲ��������б�����ݲ����
function fillSelectContent(id){
      	//alert(0);
    	document.getElementById(id).options.length=0;
  //     	$('currencySelectId').disabled = true;
    	document.getElementById(id).add(new Option("�����","01"));
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
                 	//Option������ǰ����text��������value
                      var opt= new Option(index.split('|')[1],index.split('|')[0]);
			          document.getElementById(id).add(opt);
                 }
              );
            }
          }
       );
       
}


//����ҵ���������ɲ�Ʒ���������б��
function getPrdtCtgName(businessNo) {
	var businessN = $('businessNo').value;
	if (businessN != '') {
		$("prdtCtgNo").disabled = false;
		document.getElementById("prdtCtgNo").options.length=0;
		document.getElementById("prdtCtgNo").add(new Option("--��ѡ��--",""));
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
	             	//Option������ǰ����text��������value
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
//���ݲ�Ʒ�������ɲ�Ʒ�����б��
function getPrdtNameByPCN(prdtCtgNo) {
	var prdtCtg = $(prdtCtgNo).value;
	
	if (prdtCtg != '') {
		
		$("prdtNo").disabled = false;
		document.getElementById("prdtNo").options.length=0;
		document.getElementById("prdtNo").add(new Option("--��ѡ��--",""));
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
	             	//Option������ǰ����text��������value
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
