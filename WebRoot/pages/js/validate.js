	
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
            alert(name+"中不能使用数字");
            strValue.focus();
            return false;
          }
 
	     }
	     return true;
	}
	
	
//检测是否为数字
function array(array,fieldNote){

if(array.value.length != 0){
  var el = /^\d+$/;
 if(!array.value.match(el))
 {
 alert(fieldNote+"必须为整数");
 
  array.focus();
  return false;
 }
 }
 
 return true;
 }
 //日期的验证
 function arraylength(array,leg,fieldNote){

if(array.value.length !=0)
{
  var el = /^\d+$/;
 if(!array.value.match(el))
 {
 alert(fieldNote+"必须为整数");
 
  array.focus();
  return false;
 }


 if(array.value.length!=leg*1){
 
  alert(fieldNote+"长度必须为"+leg);
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
	//去掉空格
	function trimba(val)
{
	var str = val+"";
	if (str.length == 0) return str;
	var re = /^\s*/;
	str = str.replace(re,'');
	re = /\s*$/;
	return str.replace(re,'');
}
	// 正确的非 HTML 文字串。
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
	
//测试是否为e-mail地址，true：正确；false：错误
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
			alert(FieldNote+"格式输入不正确！");
			FieldName.focus();
			FieldName.select();
			return false;
		}

		// 检查E-Mail是否正确！
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
				alert(FieldNote+"格式输入不正确！");
				FieldName.focus();
				FieldName.select();
				return false;
			}
		}
		if( cnt1!=1 || cnt2<1)
		{
			alert(FieldNote+"格式输入不正确！");
			FieldName.focus();
			FieldName.select();
			return false;
		}
		return true;
	}
	
//检查输入字符的长度.	
	
	  function Checklen(inputobj,len,FieldNote)
  {
          var strValue = inputobj.value;
          var strLen   =  strValue.length;
          if (strLen <= 0 ) {
                  alert(FieldNote+"不可为空!");
                  inputobj.focus();
                  return false;
          }  else {
                  if (strLen!=len ) {
                         alert(FieldNote+"输入不正确，应该是"+len+"位！");                        
			 inputobj.focus();
			 inputobj.select();
                          return false;
                  }
      }
          return true;
  }
  
  //是否为空
  function isNull(fieldObj,fieldNote){
     var strValue = trimba(fieldObj.value);
     var strLen   =  strValue.length;
     if (strLen <= 0 ) {
            alert(fieldNote+"不可为空!");
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
            alert(fieldNote+"不可同时为空!");
            fieldObj01.focus();
            return false;
      }
      
      return true;
  }
  //输入字符是否有全角字符
  function strIsAllAngle(fieldObj,fieldNote){
   	var strValue = fieldObj.value;
  	if(strValue.length > 0 && /[ａ-ｚＡ-Ｚ０-９！＠＃￥％＾＆＊（）＿＋｛｝［］｜：＂＇．，／？＜＞｀～　]/.test(strValue)){
  		alert(fieldNote+"包含全角字符，请用半角输入!");
  		fieldObj.focus();
      return false;
  		}
  		return true;
  	}
  	//校验整个页面输入的全角（fuww）
  	function strIsAllAngleAllPage(FormName){
  		for(var i=0;i<FormName.length;i++){
	    if(FormName[i].value.length > 0){
        if(!(strIsAllAngle(FormName[i],FormName[i].value))) {
			return false;		
		  } }}	return true ;
		   }
  //输入字符的长度是否太长
  function strLenIsLong(fieldObj,len,fieldNote){
     var strValue=fieldObj.value
     var strLen=strValue.length
     
     if(strLen>len){
         alert(fieldNote+"输入长度不能大于"+len+"！")
         fieldObj.focus()
         fieldObj.select()
         return false
     }
     
     return true
  }
  
    //校验身份证号码：
  function CheckIDNo(numStr,fieldNote)
  {
  	var num=numStr.value;
        var len = num.length, re; 
        if (len == 15){
          if (isNaN(num)) {alert("输入的"+fieldNote+"不是数字！"); return false;}
          re = new RegExp(/^(\d{6})()?(\d{2})(\d{2})(\d{2})(\d{3})$/);
        }else if (len == 18){
        	if(isNaN(num.substring(0,17))){alert("输入的"+fieldNote+"前17位不是数字！"); return false;}
          re = new RegExp(/^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\S)$/);
        }else {
        	alert("输入的"+fieldNote+"数字位数不对！"); 
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
          if (!B) {alert("输入的身份证号 "+ a[0] +" 里出生日期不对！"); return false;}
        }
        return true;
  }
  
   //输入字符的长度必须等于len
  function strLenIsEqual(fieldObj,len,fieldNote){
     var strValue=fieldObj.value
     var strLen=strValue.length
     
     if(strLen!=len){
         alert(fieldNote+"长度必须为"+len+"位！")
         fieldObj.focus()
         fieldObj.select()
         return false
     }
     
     return true  
   } 
   
   //输入字符的长度太短
  function strLenIsShort(fieldObj,len,fieldNote){
     var strValue=fieldObj.value
     var strLen=strValue.length
     
     if(strLen<len){
         alert(fieldNote+"输入长度不能小于"+len+"！")
         fieldObj.focus()
         fieldObj.select()
         return false
     }
     
     return true
   
   }
  	
	// 正确的数字文字串。用于身份证等。
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
				alert(FieldNote+"输入不正确，其中包括字符"+str.charAt(i));
//				FieldName.value=""
				FieldName.focus();
				FieldName.select();
				return false;
			}
		}
		return true;
	}
	// 正确的电话。
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
				alert(FieldNote+"输入不正确，电话号码只能包括数字和\“-\”");
//				FieldName.value=""
				FieldName.focus();
				FieldName.select();
				return false;
			}
		}
		return true;
	}

//测试是否为整数，true：正确；false：错误
	function isInt(FieldName,FieldNote)
	{
		if (javaTrim(FieldName)==""){
			return true;
		}

		if (isNaN(parseInt(FieldName.value))) 
		{
			alert(FieldNote+"您输入的不是整数,请重输");
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

//测试是否为实数，true：正确；false：错误
	function isFloat(FieldName,FieldNote)
	{
		if (javaTrim(FieldName)==""){
			return true;
		}
		if (isNaN(parseFloat(FieldName.value))) 
		{
			alert(FieldNote+"输入的不是数字,请重新输入");
			
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

function isFloatZ(FieldName,FieldNote)//可空 ， 非空 大于 0
	{
		if (javaTrim(FieldName)==""){
			return true;
		}
		if (isNaN(parseFloat(FieldName.value))) 
		{
			alert(FieldNote+"输入的不是数字,请重输");
			
			FieldName.select();
			FieldName.focus();
			return false;
		}
		else if(FieldName.value*1<=0)
		{
		alert(FieldNote+"必须大于0 !");
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
	function isFloatZ1(FieldName,FieldNote)// 非空 大于等于 0
	{
		if (javaTrim(FieldName)==""){
			return false;
		}
		if (isNaN(parseFloat(FieldName.value))) 
		{
			alert(FieldNote+"输入的不是数字,请重输");
			
			FieldName.select();
			FieldName.focus();
			return false;
		}
		else if(FieldName.value*1<0)
		{
		alert(FieldNote+"必须大于等于0 !");
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
function isFloatDZ(FieldName,FieldNote)//可空 ， 非空 可以等于0 0
	{
		if (javaTrim(FieldName)==""){
			return true;
		}
		if (isNaN(parseFloat(FieldName.value))) 
		{
			alert(FieldNote+"输入的不是数字,请重输");
			
			FieldName.select();
			FieldName.focus();
			return false;
		}else if(FieldName.value*1<0)
		{
		alert(FieldNote+"不能小于0 !");
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
function isFloatA(FieldName,FieldNote)// 非空 大于 0
	{
		if (javaTrim(FieldName)==""){
		alert(FieldNote+"不可为空,请重输");
			return false;
		}
		if (isNaN(parseFloat(FieldName.value))) 
		{
			alert(FieldNote+"输入的不是数字,请重输");
			
			FieldName.select();
			FieldName.focus();
			return false;
		}
		else if(FieldName.value*1<=0)
		{
		alert(FieldNote+"必须大于0 !");
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


//测试是否为日期型，true：正确；false：错误
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
			alert(FieldNote+"输入格式不正确,格式应该为（YYYY/MM/DD）（如：2002/02/25）");
			FieldName.focus();
			return false;
		}
		
		var DateArray=new Array(5);
		DateArray=FieldName.value.split("/");

//		if ((DateArray[0]<'1949')||(DateArray[0]>'2010'))
//		{
//			alert("输入的年份不正确");
//			FieldName.focus();
//			return false;
//		} 

		if ((DateArray[1]<'01')||(parseInt(DateArray[1])>12))
		{
			alert(FieldNote+"输入的月份不正确！"+DateArray[1]);
			FieldName.focus();
			return false;
		}
		if ((DateArray[2]<'01')||(parseInt(DateArray[2])>31))
		{
			alert(FieldNote+"输入的日期不正确！");
			FieldName.focus();
			return false;
		}
		return true;
	}
   
 /**  //检测日期
   function onCheckDate(dateObj,fieldNote){
      iYear=new Number(dateObj.value.substr(0,4))
      iMonth=new Number(dateObj.value.substr(4,2))
      iDate=new Number(dateObj.value.substr(6,2))
      
      sDay=iYear+""+iMonth+""+iDate
      
      dDate=new Date(iYear,iMonth,iDate)
      sNewDate=dDate.getYear()+""+dDate.getMonth()+""+dDate.getDate()
      if(sNewDate!=sDay){
           alert("请输入正确的"+fieldNote)
           dateObj.focus()
           dateObj.select()
           return false
      }
      
      return true
   }**/
	// 比较两个值是否相等dongyusuo－－20051206chaoyang。
	function compareEquals(a,b,fieldNote)
	{	
		var margin = 0.001;		
		var discrepancy= b-a;
		discrepancy = Math.abs(discrepancy);				
		if (margin < discrepancy){			
			alert(fieldNote+"不相等!")
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
				alert(FieldNote+"输入不正确,其中包括字符!"+str.charAt(i));
				FieldName.focus();
				FieldName.select();
				return false;
			}
		}
		return true;
	}
//检查输入参数中是否含有空格。
    function containBlank(str,tname) {
      	var separator=" ";
      	if(str.value.indexOf(separator)!=-1){
      		alert(tname+"中不能有空格！")
      		str.focus();
      		return false;
      	}
      	 return true;
    }
    //检测日期格式(现在用的方法，fuww)Z
  function onCheckDate(dateObj,fieldNote){
//先判断时间是否为整数
if(dateObj.value.length != 0){
  var el = /^\d+$/;
 if(!dateObj.value.match(el))
 {
 alert(fieldNote+"必须为整数");
  dateObj.select();
 dateObj.focus();
 return false;
 }

//判断时间是否是8位
if(dateObj.value.length != 8){
  alert(fieldNote+"必须是8位");
  dateObj.select();
  dateObj.focus();
  return false;	
 }
//判断时间输入是否合法
 var yy = dateObj.value.substring(0,4);
 var mm = dateObj.value.substring(4,6);
 var dd = dateObj.value.substring(6,8);
 
 	 if(!onCheckDateAddition(dateObj,fieldNote)){
         return false;
      }  
 if(mm*1 <= 0 || mm*1 >=13){
	alert(fieldNote +"输入月份不正确");
	dateObj.focus();
	dateObj.select();
	return false;
	}
 if(dd*1 <=0 || dd*1 >= 32){
		alert(fieldNote +"输入日期不正确");
		dateObj.focus();
		dateObj.select();
			return false;
	}
	if(mm == 4||mm ==6||mm ==9||(mm ==11)){
	 	if(dd ==31){
	 	alert(fieldNote +"该月份为30天");
	 	dateObj.focus();
	 	dateObj.select();
	 	return false;
	 	}
	}
	if((parseInt(yy)%4==0&&parseInt(yy)%100!=0)||parseInt(yy)%400==0){
	 if(parseInt(mm)==2){
	 if(parseInt(dd)>29){
	 alert(yy+"年是闰年，二月最多有29天");
	 dateObj.focus();
	dateObj.select();
	return false;
	 }
	 }
	} else{
	 if(parseInt(mm)==2){
	  if(parseInt(dd)>28){
	  alert(yy+"年是平年，二月最多有28天！");
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
//日期检查补充For GSNX
function onCheckDateAddition(dateObj,fieldNote){

	var yy = dateObj.value.substring(0,4);
 	if(yy*1< 1900 || yy*1 >2099){
		//alert(yy*1+"  : ");
		alert(fieldNote +": 输入年份超出范围(1900~2099)");
		dateObj.focus();
		dateObj.select();
		return false;
	}
	return true;
}
  // 检查输入框中输入字符的长度
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
		alert(tishi+" 最多只能录入 "+lim_len+" 字节(字母算一个字节，汉字算两个字节), 但实际录入为 " + len + "字节");
		result = false;
	}
	return result;
}  
    //检测日期格式(现在用的方法，fuww)
  function onCheckDate_ff(dateObj,fieldNote){
//先判断时间是否为整数
if(dateObj.value.length != 0){
  var el = /^\d+$/;
 if(!dateObj.value.match(el))
 {
 alert(fieldNote+"必须为整数");
  dateObj.select();
 dateObj.focus();
 return false;
 }

//判断时间是否是8位
if(dateObj.value.length != 8){
  alert(fieldNote+"必须是8位");
  dateObj.select();
  dateObj.focus();
  return false;	
 }
//判断时间输入是否合法
 var yy = dateObj.value.substring(0,4);
 var mm = dateObj.value.substring(4,6);
 var dd = dateObj.value.substring(6,8);
 if(mm*1 <= 0 || mm*1 >=13){
	alert(fieldNote +"输入月份不正确");
	dateObj.focus();
	dateObj.select();
	return false;
	}
 if(dd*1 <=0 || dd*1 >= 32){
		alert(fieldNote +"输入日期不正确");
		dateObj.focus();
		dateObj.select();
			return false;
	}
	if(mm == 4||mm ==6||mm ==9||(mm ==11)){
	 	if(dd ==31){
	 	alert(fieldNote +"该月份为30天");
	 	dateObj.focus();
	 	dateObj.select();
	 	return false;
	 	}
	}
	if((parseInt(yy)%4==0&&parseInt(yy)%100!=0)||parseInt(yy)%400==0){
	 if(parseInt(mm)==2){
	 if(parseInt(dd)>29){
	 alert(yy+"年是闰年，二月最多有29天");
	 dateObj.focus();
	dateObj.select();
	return false;
	 }
	 }
	} else{
	 if(parseInt(mm)==2){
	  if(parseInt(dd)>28){
	  alert(yy+"年是平年，二月最多有28天！");
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
//校验期限种类与申请对应	
//三个参数， 第一个是期限种类，第二个是时间月，第三个是时间天
function typeMatchTime(dateTypeObj,dateTimeObj,otherTimeObj){
	if(dateTypeObj && dateTypeObj.value){
		if((dateTimeObj && dateTimeObj.value) || (otherTimeObj && otherTimeObj.value)) {
			otherTimeObj = otherTimeObj || {};
			var month = dateTimeObj || {};
			if(dateTypeObj.value != getTimeKind(dateTimeObj.value, otherTimeObj.value)) {
				alert("期限种类与申请期限不对应");
				return false;
			}
		}
	}
	return true;
}
	
	
	//校验期限种类与申请对应	
//三个参数， 第一个是期限种类，第二个是时间月，第三个是时间天
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
		alert("申请期限录入不正确");
	}
}
	
	
	///用于调查人审查人查询(fuww)
function my_seach_cepperAndCheckper(num1,num2,num3,brno){
 var sreturn=self.showModalDialog("tlrctlBybrnoList.jsp?brno="+brno,"_self","");
 if(sreturn != null){
 var ss = sreturn.split("@");
 if(num3!='' && document.all("name"+num3).value.length != 0 && document.all("name"+num3).value == ss[0]){
 	 alert("受理客户经理和核查人不能是同一个人");
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
		alert(name+"长度为8位！");
		return false;
	}
	var p = /^(\d{4})((0([1-9]{1}))|(1[012]))((0[1-9]{1})|([1-2]([0-9]{1}))|(3[0|1]))$/;
	
	if(!p.test(data)){
		tdata.focus();
		alert(name+"日期格式错误！");
		return false;
	}	
	
	var dayNum = new Date(data.substring(0, 4), data.substring(4, 6), 0).getDate();
	
	if (parseInt(data.substring(6, 8)) > dayNum) {
		tdata.focus();
		alert(name+" 中 "+data.substring(4, 6)+" 月的天数错误！");
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
		alert(name+" 不能大于当前日期！");
		return false;
	}	
	return true;
 }
 
 //判断日期是否大于当前日期
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
		alert(fieldNote+" 不能大于当前日期["+nowDate+"]");
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
 //hnnx 密码输入必须是数字或者字母 add loudawei 
  function isStrOrNumber(data,idea){
  	var p =/^[A-Za-z0-9]+$/ ;
  	if (!p.test(data))
  	{
  		alert(idea+"必须是字母或数字");
  	}
  	return p.test(data);
 }
 //hnnx 用户名必须是数字
  function mustNum(data,idea){
  	var p =/^\d+$/  ;
  	if (!p.test(data))
  	{
  		alert(idea+"必须是数字");
  	}
  	return p.test(data);
 }
 //验证是否为电话号码, gsnx定制版
 function isPhoneForGSNX(FieldName,FieldNote){
		if (javaTrim(FieldName)==""){
			return true;
		}			
		var len = FieldName.value.length;
        if(len<7||len>12){
            alert("【"+FieldNote+"】不正确，必须是7~12个数字");
        	FieldName.focus();
			return false;
        }
        var str=FieldName.value;
		for(var i=0; i<len; i++) {
			if (!((str.charAt(i)>='0' && str.charAt(i)<='9'))) {
				alert("【"+FieldNote+"】不正确，只能是数字");
				FieldName.focus();
				return false;
			}
		}
		return true;
}

//校验操作员合法性
function checkOperator(fieldName, fieldNote) {
	var op = fieldName.value;
	var ops = op.split('@');
	if (typeof(checkPass) == 'undefined') {
		alert('请在页面上加入checkPass脚本!');
		return false;
	}
	DWREngine.setAsync(false);
	var callbackresult = true;
	for(var i=0; i<ops.length; i++) {
		checkPass.checkOperator(ops[i],function(data){
			if(!data) {
				alert(fieldNote + '不存在');
				fieldName.select();
				fieldName.focus();
				callbackresult = false;
			}
		});
	}
	return callbackresult;
}

//校验操作员的密码的合法性
function checkPassword(userfield, passwordfield, fieldnote) {
	var user = userfield.value;
	var password = passwordfield.value;
	if (typeof(checkPass) == 'undefined') {
		alert('请在页面上加入checkPass脚本!');
		return false;
	}
	DWREngine.setAsync(false);
	var callbackresult = true;
	checkPass.checkPassword(user,password,function(data){
		if(data == 'no') {
			alert('操作员不存在');
			userfield.select();
			userfield.focus();
			callbackresult = false;
		} else if(data == 'false') {
			alert('密码错误');
			passwordfield.select();
			passwordfield.focus();
			callbackresult = false;
		}
	});
	return callbackresult;
}

//存在兼容性问题，由banBackSpace替代  add by zhoubin
/*window.attachEvent("onload", function() {
	document.body.attachEvent("onkeydown", function() {
		con_key();
	});
});*/

//屏蔽返回键
/*function con_key(){
	//返回 和删除键的验证
	if((event.keyCode == 8||event.keyCode == 46) &&  
	//返回 判断文本的类型	
      (event.srcElement.type == "text" || event.srcElement.type == "textarea" ||event.srcElement.type == "password")
	//是否只读的判断      
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

//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外 
function banBackSpace(e){ 
	var ev = e || window.event;//获取event对象 
	var obj = ev.target || ev.srcElement;//获取事件源 

	var t = obj.type || obj.getAttribute('type');//获取事件源类型 

	//获取作为判断条件的事件类型 
	var vReadOnly = obj.getAttribute('readonly'); 
	var vEnabled = obj.getAttribute('enabled'); 
	//处理null值情况 
	vReadOnly = (vReadOnly == null) ? false : vReadOnly; 
	vEnabled = (vEnabled == null) ? true : vEnabled; 

	//当敲Backspace键时，事件源类型为密码或单行、多行文本的， 
	//并且readonly属性为true或enabled属性为false的，则退格键失效 
	var flag1=(ev.keyCode == 8 && (t=="password" || t=="text" || t=="textarea") 
	&& (vReadOnly==true || vEnabled!=true))?true:false; 

	//当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效 
	var flag2=(ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea") 
	?true:false; 

	//判断 
	if(flag2){ 
	return false; 
	} 
	if(flag1){ 
	return false; 
	} 
} 
//禁止后退键 作用于Firefox、Opera 
document.onkeypress=banBackSpace; 
//禁止后退键 作用于IE、Chrome 
document.onkeydown=banBackSpace; 

// 将数字转化成3位一逗的形式
function commafy(i){
	var num = document.all("value"+i).value;
	num=num.split(",").join("");
	if(isNaN(parseFloat(num))){
			alert("非合法数字，请重新输入！ ");
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
//证件类型为身份证时再次检查性别
function checkSexAgain(idType,id,sex,fieldNote){
	if(idType.value=="0"){//证件类型为身份证
	     if(sex.value!=(parseInt(id.value.charAt(16))%2==0 ? "2":"1")){
		     alert(fieldNote+"错误，请重新选择");
		     sex.focus();
	     	 return false;
	     }
	 }
     return true;
}
//登入操作员号码,登入机构,另一个管理员号码,密码的对象
function  checkController(logtlrno,brno,tlrnoO,passO){
    var logtlrno=logtlrno; 
 	var brno =brno;
	var tlrno = tlrnoO.value;
 	var pass = passO.value;
 	var flag;
	DWREngine.setAsync(false);
	if(!(isNull(tlrnoO,"另一个管理员"))){
		return false;
	}
	if(!(isNull(passO,"另一个管理员密码"))){
		return false;
	}
	if(logtlrno==tlrno){
		alert("另一个管理员号与当前操作员不能是同一个人!");
		return false;
	}

	checkPass.checkPass(brno,tlrno,pass,function(data){
		flag=data;
	});
	if(flag=='1'){
		alert("两个操作员不是同一个机构！");
		tlrnoO.focus();
		return false;
	}
	if(flag=='2'){
		alert("另一个管理员的密码不正确！");
		passO.focus();
		return false;
	}
	if(flag=='3'){
		alert("管理员不存在，请重新输入！");
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
     	"y+" : this.getYear(),		//年
        "M+" : this.getMonth() + 1, //月份 
        "d+" : this.getDate(), //日 
        "h+" : this.getHours(), //小时 
        "m+" : this.getMinutes(), //分 
        "s+" : this.getSeconds(), //秒 
        "q+" : Math.floor((this.getMonth() + 3) / 3), //季度 
        "S" : this.getMilliseconds() //毫秒 
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


 //客户号长度必须等于len，列表页面使用。限制发送失败的客户新增附属信息
function CifnoLen(fieldvalue,len,fieldNote){
  // var strValue=fieldObj.value
   var strLen=fieldvalue.length
   
   if(strLen!=len){
       alert(fieldNote+"非法，长度应该为"+len+"位，请先查证。")
     //  fieldObj.focus()
      // fieldObj.select()
       return false
   }
   
   return true  
 } 
   
//检查是否为正确的姓名全拼,现在这个方法不太有效，还要改改。
function ispinyin(fieldObj,fieldNote){
   	var strValue = fieldObj.value;
   	//alert(fieldObj.value);
   	var reg=/[Ａ-Ｚ] /i;
  	if(strValue.length > 0){
	  	if(reg.test(strValue)){
	  		alert(fieldNote+"包含非法字符，请修改。");
	  		fieldObj.focus();
	        return false;
	     }
  	}
  	return true;
}
//将数字转化成3位一逗的形式
function transformNumToMoney(num){
	num=num.split(",").join("");
	num = num+"";
	var re=/(-?\d+)(\d{3})/
	while(re.test(num)){
		num=num.replace(re,"$1,$2")
	}
	return num;
}