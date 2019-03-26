
//checkbox
function fillCheckbox(id1,id2,url){
    //alert(id);
   	var List ;
   	var url = url;
   	var str="";
    new Ajax.Request( 
    url, 
     {   
      method: 'post',   
      parameters: {t:new Date().getTime()},
      onSuccess: function(transport) 
       {  
          List = transport.responseText.split(",");
          $A(List).each(
             function(index){
            	 str+="&nbsp;&nbsp;<input type=\"checkbox\" name="+id1+" id="+id1+" value="+index+" />&nbsp;&nbsp;"+index;
             	
             }
          );
          document.getElementById(id2).innerHTML=str;
        }
      }
   );  
}