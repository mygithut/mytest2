	
	 function testNumber2208229(strValue,name){

      var aa=0;   
      var strValue1=strValue.value;               
       if(strValue1.length > 0){
          for(var i=0;i<strValue1.length;i++){
          if(parseFloat(strValue1.substring(i,i+1))){
          aa=1;
			}
         }	
           if(1==aa){
            alert(name+"�в���ʹ������");
            strValue.focus();
            return false;
          }
 
	     }
	     return true;
	}
	
	
//����Ƿ�Ϊ����
function array(array,fieldNote){

if(array.value.length != 0){
  var el = /^\d+$/;
 if(!array.value.match(el))
 {
 alert(fieldNote+"����Ϊ����");
 
  array.focus();
  return false;
 }
 }
 
 return true;
 }
 //���ڵ���֤
 function arraylength(array,leg,fieldNote){

if(array.value.length !=0)
{
  var el = /^\d+$/;
 if(!array.value.match(el))
 {
 alert(fieldNote+"����Ϊ����");
 
  array.focus();
  return false;
 }


 if(array.value.length!=leg*1){
 
  alert(fieldNote+"���ȱ���Ϊ"+leg);
   array.focus();
   return false;
 }

 }
 
 return true;
 }
 
 
	
	function strallbank(FieldName){
    	var javatrim = javaTrim(FieldName);
    	var javlist = javatrim.split(" ");
       	var newkk="";
       	for(var i=0; i<javlist.length; i++)
		{  
		newkk += javlist[i];
		}
	  return  newkk;
	}
	
	
	function javaTrim(FieldName){
		var str=FieldName.value;
		var i=0;
		var j;
		var len=str.length;
		
		trimstr="";
		j=len-1;
		if(j<0) return trimstr;
		while (1){
			if (str.charAt(i)==" "){
				i++;
			}
			else{
				break;
			}
		} 
		while (1) {
			if (str.charAt(j)==" "){
				j--;;
			}
			else{
				break;
			}
		}
//		alert("str="+str);
		trimstr=str.substring(i,j+1);
//		alert("trimstr="+trimstr);
		FieldName.value=trimstr;
		return trimstr;
	}
	//ȥ���ո�
	function trimba(val)
{
	var str = val+"";
	if (str.length == 0) return str;
	var re = /^\s*/;
	str = str.replace(re,'');
	re = /\s*$/;
	return str.replace(re,'');
}
	// ��ȷ�ķ� HTML ���ִ���
	function javaValidString(str)
	{
		var len;
		
		len = str.length;
		for(var i=0; i<len; i++) 
		{
			if(str.charAt(i)=='<' || str.charAt(i)=='>' || str.charAt(i)=='\'' || str.charAt(i)=='\"') 
			{
				return false;
			}
		}
		return true;
	}
	
//�����Ƿ�Ϊe-mail��ַ��true����ȷ��false������
	function isEmail(FieldName,FieldNote)
	{
		if (javaTrim(FieldName)==""){
			return true;
		}

		var cnt1, cnt2;
		var len1;
		var str=FieldName.value;
		if(javaValidString(str)==false) 
		{
			alert(FieldNote+"��ʽ���벻��ȷ��");
			FieldName.focus();
			FieldName.select();
			return false;
		}

		// ���E-Mail�Ƿ���ȷ��
		cnt1=0;
		cnt2=0;
		len1 = str.length;
		for(var i=0; i<len1; i++) {
		   if(str.charAt(i)=='@') 
		   {
				cnt1++;
			}
			if(str.charAt(i)=='.') 
			{
				cnt2++;
			}
			if(str.charAt(i)==' ')
			{
				alert(FieldNote+"��ʽ���벻��ȷ��");
				FieldName.focus();
				FieldName.select();
				return false;
			}
		}
		if( cnt1!=1 || cnt2<1)
		{
			alert(FieldNote+"��ʽ���벻��ȷ��");
			FieldName.focus();
			FieldName.select();
			return false;
		}
		return true;
	}
	
//��������ַ��ĳ���.	
	
	  function Checklen(inputobj,len,FieldNote)
  {
          var strValue = inputobj.value;
          var strLen   =  strValue.length;
          if (strLen <= 0 ) {
                  alert(FieldNote+"����Ϊ��!");
                  inputobj.focus();
                  return false;
          }  else {
                  if (strLen!=len ) {
                         alert(FieldNote+"���벻��ȷ��Ӧ����"+len+"λ��");                        
			 inputobj.focus();
			 inputobj.select();
                          return false;
                  }
      }
          return true;
  }
  
  //�Ƿ�Ϊ��
  function isNull(fieldObj,fieldNote){
     var strValue = trimba(fieldObj.value);
     var strLen   =  strValue.length;
     if (strLen <= 0 ) {
            alert(fieldNote+"����Ϊ��!");
            fieldObj.focus();
            return false;                                                
      }
      
      return true;
  }
  function isNullBoth(fieldObj01,fieldObj02,fieldNote){
     var strValue01 = fieldObj01.value;
     var strValue02 = fieldObj02.value;
     var strLen01   =  strValue01.length;
     var strLen02   =  strValue02.length;
     if ((strLen01 <= 0 )&&(strLen02<=0)) {
            alert(fieldNote+"����ͬʱΪ��!");
            fieldObj01.focus();
            return false;
      }
      
      return true;
  }
  //�����ַ��Ƿ���ȫ���ַ�
  function strIsAllAngle(fieldObj,fieldNote){
   	var strValue = fieldObj.value;
  	if(strValue.length > 0 && /[��-����-�ڣ�-�������������ޣ��������ߣ������ۣݣ��������������������࡫��]/.test(strValue)){
  		alert(fieldNote+"����ȫ���ַ������ð������!");
  		fieldObj.focus();
      return false;
  		}
  		return true;
  	}
  	//У������ҳ�������ȫ�ǣ�fuww��
  	function strIsAllAngleAllPage(FormName){
  		for(var i=0;i<FormName.length;i++){
	    if(FormName[i].value.length > 0){
        if(!(strIsAllAngle(FormName[i],FormName[i].value))) {
			return false;		
		  } }}	return true ;
		   }
  //�����ַ��ĳ����Ƿ�̫��
  function strLenIsLong(fieldObj,len,fieldNote){
     var strValue=fieldObj.value
     var strLen=strValue.length
     
     if(strLen>len){
         alert(fieldNote+"���볤�Ȳ��ܴ���"+len+"��")
         fieldObj.focus()
         fieldObj.select()
         return false
     }
     
     return true
  }
  
    //У�����֤���룺
  function CheckIDNo(numStr,fieldNote)
  {
  	var num=numStr.value;
        var len = num.length, re; 
        if (len == 15){
          if (isNaN(num)) {alert("�����"+fieldNote+"�������֣�"); return false;}
          re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
        }else if (len == 18){
        	if(isNaN(num.substring(0,17))){alert("�����"+fieldNote+"ǰ17λ�������֣�"); return false;}
          re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\S)$/);
        }else {
        	alert("�����"+fieldNote+"����λ�����ԣ�"); 
        	return false;
        }
        var a = num.match(re);
        if (a != null)
        {
          if (len==15)
          {
            var D = new Date("19"+a[3]+"/"+a[4]+"/"+a[5]);
            var B = D.getYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
          }
          else
          {
            var D = new Date(a[3]+"/"+a[4]+"/"+a[5]);
            var B = D.getFullYear()==a[3]&&(D.getMonth()+1)==a[4]&&D.getDate()==a[5];
          }
          if (!B) {alert("��������֤�� "+ a[0] +" ��������ڲ��ԣ�"); return false;}
        }
        return true;
  }
  
   //�����ַ��ĳ��ȱ������len
  function strLenIsEqual(fieldObj,len,fieldNote){
     var strValue=fieldObj.value
     var strLen=strValue.length
     
     if(strLen!=len){
         alert(fieldNote+"���ȱ���Ϊ"+len+"λ��")
         fieldObj.focus()
         fieldObj.select()
         return false
     }
     
     return true  
   } 
   
   //�����ַ��ĳ���̫��
  function strLenIsShort(fieldObj,len,fieldNote){
     var strValue=fieldObj.value
     var strLen=strValue.length
     
     if(strLen<len){
         alert(fieldNote+"���볤�Ȳ���С��"+len+"��")
         fieldObj.focus()
         fieldObj.select()
         return false
     }
     
     return true
   
   }
  	
	// ��ȷ���������ִ����������֤�ȡ�
	function isNumber(FieldName,FieldNote)
	{
	
		if (javaTrim(FieldName)==""){
			return true;
		}
		
		var str=FieldName.value;			
		var len = str.length;
		for(var i=0; i<len; i++) {
			if(!(str.charAt(i)>='0' && str.charAt(i)<='9')) 
			{
				alert(FieldNote+"���벻��ȷ�����а����ַ�"+str.charAt(i));
//				FieldName.value=""
				FieldName.focus();
				FieldName.select();
				return false;
			}
		}
		return true;
	}
	// ��ȷ�ĵ绰��
	function isPhone(FieldName,FieldNote)
	{	
		if (javaTrim(FieldName)==""){
			return true;
		}
		var str=FieldName.value;			
		var len = str.length;
		for(var i=0; i<len; i++) {
			if (!((str.charAt(i)>='0' && str.charAt(i)<='9')||(str.charAt(i)=='-'))) 
			{
				alert(FieldNote+"���벻��ȷ���绰����ֻ�ܰ������ֺ�\��-\��");
//				FieldName.value=""
				FieldName.focus();
				FieldName.select();
				return false;
			}
		}
		return true;
	}

//�����Ƿ�Ϊ������true����ȷ��false������
	function isInt(FieldName,FieldNote)
	{
		if (javaTrim(FieldName)==""){
			return true;
		}

		if (isNaN(parseInt(FieldName.value))) 
		{
			alert(FieldNote+"������Ĳ�������,������");
			FieldName.value="0"
			FieldName.focus();
			return false;
		}
		else
		{
			FieldName.value=parseInt(FieldName.value)
			return true;
		}
	}	

//�����Ƿ�Ϊʵ����true����ȷ��false������
	function isFloat(FieldName,FieldNote)
	{
		if (javaTrim(FieldName)==""){
			return true;
		}
		if (isNaN(parseFloat(FieldName.value))) 
		{
			alert(FieldNote+"����Ĳ�������,����������");
			
			FieldName.select();
			FieldName.focus();
			return false;
		}
		else
		{
			FieldName.value=parseFloat(FieldName.value)
			return true;
		}
	}	

function isFloatZ(FieldName,FieldNote)//�ɿ� �� �ǿ� ���� 0
	{
		if (javaTrim(FieldName)==""){
			return true;
		}
		if (isNaN(parseFloat(FieldName.value))) 
		{
			alert(FieldNote+"����Ĳ�������,������");
			
			FieldName.select();
			FieldName.focus();
			return false;
		}
		else if(FieldName.value*1<=0)
		{
		alert(FieldNote+"�������0 !");
		FieldName.select();
			FieldName.focus();
			return false;
		}
		else
		{
			FieldName.value=parseFloat(FieldName.value)
			return true;
		}
	}	
	function isFloatZ1(FieldName,FieldNote)// �ǿ� ���ڵ��� 0
	{
		if (javaTrim(FieldName)==""){
			return false;
		}
		if (isNaN(parseFloat(FieldName.value))) 
		{
			alert(FieldNote+"����Ĳ�������,������");
			
			FieldName.select();
			FieldName.focus();
			return false;
		}
		else if(FieldName.value*1<0)
		{
		alert(FieldNote+"������ڵ���0 !");
		FieldName.select();
			FieldName.focus();
			return false;
		}
		else
		{
			FieldName.value=parseFloat(FieldName.value)
			return true;
		}
	}	
function isFloatDZ(FieldName,FieldNote)//�ɿ� �� �ǿ� ���Ե���0 0
	{
		if (javaTrim(FieldName)==""){
			return true;
		}
		if (isNaN(parseFloat(FieldName.value))) 
		{
			alert(FieldNote+"����Ĳ�������,������");
			
			FieldName.select();
			FieldName.focus();
			return false;
		}else if(FieldName.value*1<0)
		{
		alert(FieldNote+"����С��0 !");
		FieldName.select();
			FieldName.focus();
			return false;
		}
		
		else
		{
			FieldName.value=parseFloat(FieldName.value)
			return true;
		}
	}
function isFloatA(FieldName,FieldNote)// �ǿ� ���� 0
	{
		if (javaTrim(FieldName)==""){
		alert(FieldNote+"����Ϊ��,������");
			return false;
		}
		if (isNaN(parseFloat(FieldName.value))) 
		{
			alert(FieldNote+"����Ĳ�������,������");
			
			FieldName.select();
			FieldName.focus();
			return false;
		}
		else if(FieldName.value*1<=0)
		{
		alert(FieldNote+"�������0 !");
		FieldName.select();
			FieldName.focus();
			return false;
		}
		else
		{
			FieldName.value=parseFloat(FieldName.value)
			return true;
		}
	}	


//�����Ƿ�Ϊ�����ͣ�true����ȷ��false������
	function isDatetime(FieldName,FieldNote)
	{
		if (javaTrim(FieldName)==""){
			return true;
		}
		var regexp=/^(\d{4}\/\d{1}\/\d{1})$/;
		var regexp1=/^(\d{4}\/\d{1}\/\d{2})$/;
		var regexp2=/^(\d{4}\/\d{2}\/\d{1})$/;
		var regexp3=/^(\d{4}\/\d{2}\/\d{2})$/;

		if ((!(regexp.test(FieldName.value)))&&(!(regexp1.test(FieldName.value)))
		 &&(!(regexp2.test(FieldName.value)))&&(!(regexp3.test(FieldName.value))))
		{
			alert(FieldNote+"�����ʽ����ȷ,��ʽӦ��Ϊ��YYYY/MM/DD�����磺2002/02/25��");
			FieldName.focus();
			return false;
		}
		
		var DateArray=new Array(5);
		DateArray=FieldName.value.split("/");

//		if ((DateArray[0]<'1949')||(DateArray[0]>'2010'))
//		{
//			alert("�������ݲ���ȷ");
//			FieldName.focus();
//			return false;
//		} 

		if ((DateArray[1]<'01')||(parseInt(DateArray[1])>12))
		{
			alert(FieldNote+"������·ݲ���ȷ��"+DateArray[1]);
			FieldName.focus();
			return false;
		}
		if ((DateArray[2]<'01')||(parseInt(DateArray[2])>31))
		{
			alert(FieldNote+"��������ڲ���ȷ��");
			FieldName.focus();
			return false;
		}
		return true;
	}
   
 /**  //�������
   function onCheckDate(dateObj,fieldNote){
      iYear=new Number(dateObj.value.substr(0,4))
      iMonth=new Number(dateObj.value.substr(4,2))
      iDate=new Number(dateObj.value.substr(6,2))
      
      sDay=iYear+""+iMonth+""+iDate
      
      dDate=new Date(iYear,iMonth,iDate)
      sNewDate=dDate.getYear()+""+dDate.getMonth()+""+dDate.getDate()
      if(sNewDate!=sDay){
           alert("��������ȷ��"+fieldNote)
           dateObj.focus()
           dateObj.select()
           return false
      }
      
      return true
   }**/
	// �Ƚ�����ֵ�Ƿ����dongyusuo����20051206chaoyang��
	function compareEquals(a,b,fieldNote)
	{	
		var margin = 0.001;		
		var discrepancy= b-a;
		discrepancy = Math.abs(discrepancy);				
		if (margin < discrepancy){			
			alert(fieldNote+"�����!")
			return false;
		}
                   return true
	 }

function isNum(FieldName,FieldNote)
	{
	
		if (javaTrim(FieldName)==""){
			return true;
		}
		
		var str=FieldName.value;			
		var len = str.length;
		for(var i=0; i<len; i++) {
			if(!(str.charAt(i)>='0' && str.charAt(i)<='9'||(str.charAt(i)=='-'))) 
			{
				alert(FieldNote+"���벻��ȷ,���а����ַ�!"+str.charAt(i));
				FieldName.focus();
				FieldName.select();
				return false;
			}
		}
		return true;
	}
//�������������Ƿ��пո�
    function containBlank(str,tname) {
      	var separator=" ";
      	if(str.value.indexOf(separator)!=-1){
      		alert(tname+"�в����пո�")
      		str.focus();
      		return false;
      	}
      	 return true;
    }
    //������ڸ�ʽ(�����õķ�����fuww)Z
  function onCheckDate(dateObj,fieldNote){
//���ж�ʱ���Ƿ�Ϊ����
if(dateObj.value.length != 0){
  var el = /^\d+$/;
 if(!dateObj.value.match(el))
 {
 alert(fieldNote+"����Ϊ����");
  dateObj.select();
 dateObj.focus();
 return false;
 }

//�ж�ʱ���Ƿ���8λ
if(dateObj.value.length != 8){
  alert(fieldNote+"������8λ");
  dateObj.select();
  dateObj.focus();
  return false;	
 }
//�ж�ʱ�������Ƿ�Ϸ�
 var yy = dateObj.value.substring(0,4);
 var mm = dateObj.value.substring(4,6);
 var dd = dateObj.value.substring(6,8);
 
 	 if(!onCheckDateAddition(dateObj,fieldNote)){
         return false;
      }  
 if(mm*1 <= 0 || mm*1 >=13){
	alert(fieldNote +"�����·ݲ���ȷ");
	dateObj.focus();
	dateObj.select();
	return false;
	}
 if(dd*1 <=0 || dd*1 >= 32){
		alert(fieldNote +"�������ڲ���ȷ");
		dateObj.focus();
		dateObj.select();
			return false;
	}
	if(mm == 4||mm ==6||mm ==9||(mm ==11)){
	 	if(dd ==31){
	 	alert(fieldNote +"���·�Ϊ30��");
	 	dateObj.focus();
	 	dateObj.select();
	 	return false;
	 	}
	}
	if((parseInt(yy)%4==0&&parseInt(yy)%100!=0)||parseInt(yy)%400==0){
	 if(parseInt(mm)==2){
	 if(parseInt(dd)>29){
	 alert(yy+"�������꣬���������29��");
	 dateObj.focus();
	dateObj.select();
	return false;
	 }
	 }
	} else{
	 if(parseInt(mm)==2){
	  if(parseInt(dd)>28){
	  alert(yy+"����ƽ�꣬���������28�죡");
	 dateObj.focus();
	dateObj.select();
	return false;
	  }
	 }
	}
 return true;
 }
 return true;
} 
//���ڼ�鲹��For GSNX
function onCheckDateAddition(dateObj,fieldNote){

	var yy = dateObj.value.substring(0,4);
 	if(yy*1< 1900 || yy*1 >2099){
		//alert(yy*1+"  : ");
		alert(fieldNote +": ������ݳ�����Χ(1900~2099)");
		dateObj.focus();
		dateObj.select();
		return false;
	}
	return true;
}
  // ���������������ַ��ĳ���
 function limitLength(field, lim_len, tishi)
{
	//alert(field+"\t"+lim_len+"\t"+tishi);
	if (field.value == null) {
		return true;
	}
	var result = true;
	var field_value = field.value;
	var len = 0;
	for(i=0 ; i<field_value.length ; i++)   
    {  
    if(field_value.charCodeAt(i)>256)   
    {   
       len += 2;   
    }   
    else   
    {   
       len++;   
    }   
   }  
	if(len > lim_len)
	{
		alert(tishi+" ���ֻ��¼�� "+lim_len+" �ֽ�(��ĸ��һ���ֽڣ������������ֽ�), ��ʵ��¼��Ϊ " + len + "�ֽ�");
		result = false;
	}
	return result;
}  
    //������ڸ�ʽ(�����õķ�����fuww)
  function onCheckDate_ff(dateObj,fieldNote){
//���ж�ʱ���Ƿ�Ϊ����
if(dateObj.value.length != 0){
  var el = /^\d+$/;
 if(!dateObj.value.match(el))
 {
 alert(fieldNote+"����Ϊ����");
  dateObj.select();
 dateObj.focus();
 return false;
 }

//�ж�ʱ���Ƿ���8λ
if(dateObj.value.length != 8){
  alert(fieldNote+"������8λ");
  dateObj.select();
  dateObj.focus();
  return false;	
 }
//�ж�ʱ�������Ƿ�Ϸ�
 var yy = dateObj.value.substring(0,4);
 var mm = dateObj.value.substring(4,6);
 var dd = dateObj.value.substring(6,8);
 if(mm*1 <= 0 || mm*1 >=13){
	alert(fieldNote +"�����·ݲ���ȷ");
	dateObj.focus();
	dateObj.select();
	return false;
	}
 if(dd*1 <=0 || dd*1 >= 32){
		alert(fieldNote +"�������ڲ���ȷ");
		dateObj.focus();
		dateObj.select();
			return false;
	}
	if(mm == 4||mm ==6||mm ==9||(mm ==11)){
	 	if(dd ==31){
	 	alert(fieldNote +"���·�Ϊ30��");
	 	dateObj.focus();
	 	dateObj.select();
	 	return false;
	 	}
	}
	if((parseInt(yy)%4==0&&parseInt(yy)%100!=0)||parseInt(yy)%400==0){
	 if(parseInt(mm)==2){
	 if(parseInt(dd)>29){
	 alert(yy+"�������꣬���������29��");
	 dateObj.focus();
	dateObj.select();
	return false;
	 }
	 }
	} else{
	 if(parseInt(mm)==2){
	  if(parseInt(dd)>28){
	  alert(yy+"����ƽ�꣬���������28�죡");
	 dateObj.focus();
	dateObj.select();
	return false;
	  }
	 }
	}
 return true;
 }
 return true;
}
//У�����������������Ӧ	
//���������� ��һ�����������࣬�ڶ�����ʱ���£���������ʱ����
function typeMatchTime(dateTypeObj,dateTimeObj,otherTimeObj){
	if(dateTypeObj && dateTypeObj.value){
		if((dateTimeObj && dateTimeObj.value) || (otherTimeObj && otherTimeObj.value)) {
			otherTimeObj = otherTimeObj || {};
			var month = dateTimeObj || {};
			if(dateTypeObj.value != getTimeKind(dateTimeObj.value, otherTimeObj.value)) {
				alert("�����������������޲���Ӧ");
				return false;
			}
		}
	}
	return true;
}
	
	
	//У�����������������Ӧ	
//���������� ��һ�����������࣬�ڶ�����ʱ���£���������ʱ����
function getTimeKind(month,day) {
	if (!month) {
		month = 0;
	}
	if (!day) {
		day = 0;
	}

	if (month < 12) {
		return 1;
	} else if (month == 12 && day == 0){
		return 1;
	} else if (month == 12 && day > 0){
		return 2;
	} else if (month > 12 && month < 60){
		return 2;
	} else if (month == 60 && day == 0) {
		return 2;
	} else if (month == 60 && day > 0) {
		return 3;
	} else if (month > 60) {
		return 3;
	} else {
		alert("��������¼�벻��ȷ");
	}
}
	
	
	///���ڵ���������˲�ѯ(fuww)
function my_seach_cepperAndCheckper(num1,num2,num3,brno){
 var sreturn=self.showModalDialog("tlrctlBybrnoList.jsp?brno="+brno,"_self","");
 if(sreturn != null){
 var ss = sreturn.split("@");
 if(num3!='' && document.all("name"+num3).value.length != 0 && document.all("name"+num3).value == ss[0]){
 	 alert("����ͻ�����ͺ˲��˲�����ͬһ����");
 	}else{
 
 document.all("name"+num1).value = ss[0];
 document.all("name"+num2).value = ss[1];
}

 } 
 }

 function rq_isDate(tdata,name){
  	var data = tdata.value;
	if (data.length != 8) {
		tdata.focus();
		alert(name+"����Ϊ8λ��");
		return false;
	}
	var p = /^(\d{4})((0([1-9]{1}))|(1[012]))((0[1-9]{1})|([1-2]([0-9]{1}))|(3[0|1]))$/;
	
	if(!p.test(data)){
		tdata.focus();
		alert(name+"���ڸ�ʽ����");
		return false;
	}	
	
	var dayNum = new Date(data.substring(0, 4), data.substring(4, 6), 0).getDate();
	
	if (parseInt(data.substring(6, 8)) > dayNum) {
		tdata.focus();
		alert(name+" �� "+data.substring(4, 6)+" �µ���������");
		return false;
	}
	return true;	
 }

 function rq_isBefoer(data,name){
 	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	if(month<10){
		month="0"+month;
	}
	var y = year+""+month;
	if(parseInt(data.substring(0,6))>parseInt(y)){
		alert(name+" ���ܴ��ڵ�ǰ���ڣ�");
		return false;
	}	
	return true;
 }
 
 //�ж������Ƿ���ڵ�ǰ����
function isLaterThanNowDate(dateObj,fieldNote){
	if(dateObj.length==0){
		return true;
	}
 	var date = new Date();
	var year = date.getFullYear();
	var month = date.getMonth() + 1;
	var day = date.getDate();
	if(month<10){
		month="0"+month;
	}
	if(day<10){
		day="0"+day;
	}
	var nowDate =""+year+month+day;
	//alert(nowDate);
	if(parseInt(dateObj.value)>parseInt(nowDate)){
		alert(fieldNote+" ���ܴ��ڵ�ǰ����["+nowDate+"]");
			dateObj.focus();
		return false;
	}	
	return true;
 }
function rq_daysBetween(from,to){
	
	var datefrom = new Date(from.substring(0,4),from.substring(4,6)-1,from.substring(6,8));
	var dateto = new Date(to.substring(0,4),to.substring(4,6)-1,to.substring(6,8));
	
	days = (dateto.getTime()-datefrom.getTime())/1000/60/60/24;
	return days;
}
 //hnnx ����������������ֻ�����ĸ add loudawei 
  function isStrOrNumber(data,idea){
  	var p =/^[A-Za-z0-9]+$/ ;
  	if (!p.test(data))
  	{
  		alert(idea+"��������ĸ������");
  	}
  	return p.test(data);
 }
 //hnnx �û�������������
  function mustNum(data,idea){
  	var p =/^\d+$/  ;
  	if (!p.test(data))
  	{
  		alert(idea+"����������");
  	}
  	return p.test(data);
 }
 //��֤�Ƿ�Ϊ�绰����, gsnx���ư�
 function isPhoneForGSNX(FieldName,FieldNote){
		if (javaTrim(FieldName)==""){
			return true;
		}			
		var len = FieldName.value.length;
        if(len<7||len>12){
            alert("��"+FieldNote+"������ȷ��������7~12������");
        	FieldName.focus();
			return false;
        }
        var str=FieldName.value;
		for(var i=0; i<len; i++) {
			if (!((str.charAt(i)>='0' && str.charAt(i)<='9'))) {
				alert("��"+FieldNote+"������ȷ��ֻ��������");
				FieldName.focus();
				return false;
			}
		}
		return true;
}

//У�����Ա�Ϸ���
function checkOperator(fieldName, fieldNote) {
	var op = fieldName.value;
	var ops = op.split('@');
	if (typeof(checkPass) == 'undefined') {
		alert('����ҳ���ϼ���checkPass�ű�!');
		return false;
	}
	DWREngine.setAsync(false);
	var callbackresult = true;
	for(var i=0; i<ops.length; i++) {
		checkPass.checkOperator(ops[i],function(data){
			if(!data) {
				alert(fieldNote + '������');
				fieldName.select();
				fieldName.focus();
				callbackresult = false;
			}
		});
	}
	return callbackresult;
}

//У�����Ա������ĺϷ���
function checkPassword(userfield, passwordfield, fieldnote) {
	var user = userfield.value;
	var password = passwordfield.value;
	if (typeof(checkPass) == 'undefined') {
		alert('����ҳ���ϼ���checkPass�ű�!');
		return false;
	}
	DWREngine.setAsync(false);
	var callbackresult = true;
	checkPass.checkPassword(user,password,function(data){
		if(data == 'no') {
			alert('����Ա������');
			userfield.select();
			userfield.focus();
			callbackresult = false;
		} else if(data == 'false') {
			alert('�������');
			passwordfield.select();
			passwordfield.focus();
			callbackresult = false;
		}
	});
	return callbackresult;
}

//���ڼ��������⣬��banBackSpace���  add by zhoubin
/*window.attachEvent("onload", function() {
	document.body.attachEvent("onkeydown", function() {
		con_key();
	});
});*/

//���η��ؼ�
/*function con_key(){
	//���� ��ɾ��������֤
	if((event.keyCode == 8||event.keyCode == 46) &&  
	//���� �ж��ı�������	
      (event.srcElement.type == "text" || event.srcElement.type == "textarea" ||event.srcElement.type == "password")
	//�Ƿ�ֻ�����ж�      
       && event.srcElement.readOnly==true){
        	event.keyCode=0; 
  			event.returnvalue=false; 
  			return false;  
	}
	if((event.keyCode == 8) &&  
		(event.srcElement.type != "text" && event.srcElement.type != "textarea" &&  event.srcElement.type != "password")){
			event.keyCode=0; 
			event.returnvalue=false; 
			return false;  
	}
	if(event.srcElement.type != "textarea"){
		if(event.keyCode==13)event.keyCode=9;
	}
 	
}*/

//��������¼� ��ֹ���˼���Backspace��������С������ı������ 
function banBackSpace(e){ 
	var ev = e || window.event;//��ȡevent���� 
	var obj = ev.target || ev.srcElement;//��ȡ�¼�Դ 

	var t = obj.type || obj.getAttribute('type');//��ȡ�¼�Դ���� 

	//��ȡ��Ϊ�ж��������¼����� 
	var vReadOnly = obj.getAttribute('readonly'); 
	var vEnabled = obj.getAttribute('enabled'); 
	//����nullֵ��� 
	vReadOnly = (vReadOnly == null) ? false : vReadOnly; 
	vEnabled = (vEnabled == null) ? true : vEnabled; 

	//����Backspace��ʱ���¼�Դ����Ϊ������С������ı��ģ� 
	//����readonly����Ϊtrue��enabled����Ϊfalse�ģ����˸��ʧЧ 
	var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea") 
	&& (vReadOnly==true || vEnabled!=true))?true:false; 

	//����Backspace��ʱ���¼�Դ���ͷ�������С������ı��ģ����˸��ʧЧ 
	var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea") 
	?true:false; 

	//�ж� 
	if(flag2){ 
	return false; 
	} 
	if(flag1){ 
	return false; 
	} 
} 
//��ֹ���˼� ������Firefox��Opera 
document.onkeypress=banBackSpace; 
//��ֹ���˼� ������IE��Chrome 
document.onkeydown=banBackSpace; 

// ������ת����3λһ������ʽ
function commafy(i){
	var num = document.all("value"+i).value;
	num=num.split(",").join("");
	if(isNaN(parseFloat(num))){
			alert("�ǺϷ����֣����������룡 ");
		 	document.all("value"+i).select();
		 	document.all("value"+i).value=0.0;
		 	 return false;
	}else{
	num = num+"";
	var re=/(-?\d+)(\d{3})/
	while(re.test(num)){
		num=num.replace(re,"$1,$2")
	}
	document.all("value"+i).value =num;
	}
}
//֤������Ϊ���֤ʱ�ٴμ���Ա�
function checkSexAgain(idType,id,sex,fieldNote){
	if(idType.value=="0"){//֤������Ϊ���֤
	     if(sex.value!=(parseInt(id.value.charAt(16))%2==0 ? "2":"1")){
		     alert(fieldNote+"����������ѡ��");
		     sex.focus();
	     	 return false;
	     }
	 }
     return true;
}
//�������Ա����,�������,��һ������Ա����,����Ķ���
function  checkController(logtlrno,brno,tlrnoO,passO){
    var logtlrno=logtlrno; 
 	var brno =brno;
	var tlrno = tlrnoO.value;
 	var pass = passO.value;
 	var flag;
	DWREngine.setAsync(false);
	if(!(isNull(tlrnoO,"��һ������Ա"))){
		return false;
	}
	if(!(isNull(passO,"��һ������Ա����"))){
		return false;
	}
	if(logtlrno==tlrno){
		alert("��һ������Ա���뵱ǰ����Ա������ͬһ����!");
		return false;
	}

	checkPass.checkPass(brno,tlrno,pass,function(data){
		flag=data;
	});
	if(flag=='1'){
		alert("��������Ա����ͬһ��������");
		tlrnoO.focus();
		return false;
	}
	if(flag=='2'){
		alert("��һ������Ա�����벻��ȷ��");
		passO.focus();
		return false;
	}
	if(flag=='3'){
		alert("����Ա�����ڣ����������룡");
		tlrnoO.focus();
		return false;
	}
 	return true;
}
Date.prototype.Format = function(fmt) 
{
    //author: meizz 
    var o =
     { 
     	"y+" : this.getYear(),		//��
        "M+" : this.getMonth() + 1, //�·� 
        "d+" : this.getDate(), //�� 
        "h+" : this.getHours(), //Сʱ 
        "m+" : this.getMinutes(), //�� 
        "s+" : this.getSeconds(), //�� 
        "q+" : Math.floor((this.getMonth() + 3) / 3), //���� 
        "S" : this.getMilliseconds() //���� 
     }; 
    if (/(y+)/.test(fmt)) 
         fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length)); 
    for (var k in o) 
        if (new RegExp("(" + k + ")").test(fmt)) 
             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length))); 
    return fmt; 
}


Date.prototype.addDays = function(d)
{
    this.setDate(this.getDate() + d);
};


Date.prototype.addWeeks = function(w)
{
    this.addDays(w * 7);
};


Date.prototype.addMonths= function(m)
{
    var d = this.getDate();
    this.setMonth(this.getMonth() + m);

    if (this.getDate() < d)
        this.setDate(0);
};


Date.prototype.addYears = function(y)
{
    var m = this.getMonth();
    this.setFullYear(this.getFullYear() + y);

    if (m < this.getMonth()) 
     {
        this.setDate(0);
     }
};


 //�ͻ��ų��ȱ������len���б�ҳ��ʹ�á����Ʒ���ʧ�ܵĿͻ�����������Ϣ
function CifnoLen(fieldvalue,len,fieldNote){
  // var strValue=fieldObj.value
   var strLen=fieldvalue.length
   
   if(strLen!=len){
       alert(fieldNote+"�Ƿ�������Ӧ��Ϊ"+len+"λ�����Ȳ�֤��")
     //  fieldObj.focus()
      // fieldObj.select()
       return false
   }
   
   return true  
 } 
   
//����Ƿ�Ϊ��ȷ������ȫƴ,�������������̫��Ч����Ҫ�ĸġ�
function ispinyin(fieldObj,fieldNote){
   	var strValue = fieldObj.value;
   	//alert(fieldObj.value);
   	var reg=/[��-��] /i;
  	if(strValue.length > 0){
	  	if(reg.test(strValue)){
	  		alert(fieldNote+"�����Ƿ��ַ������޸ġ�");
	  		fieldObj.focus();
	        return false;
	     }
  	}
  	return true;
}
//������ת����3λһ������ʽ
function transformNumToMoney(num){
	num=num.split(",").join("");
	num = num+"";
	var re=/(-?\d+)(\d{3})/
	while(re.test(num)){
		num=num.replace(re,"$1,$2")
	}
	return num;
}