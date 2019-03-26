              /************************************************************************************************

              *    身份证号码验证程序

              *    函数一：checkCard(cId) 身份号码检验函数

              *        参数cId为要验证的身份证号码，返回值为true时，验证通过。

              *        对一些位置保留了alert()的注释，可供朋友调试使用

              *    函数二：C15ToC18(c15)  15位身份证号码升级为18位函数

              *        参数c15为要转换的15位身份证号码，返回值为转化后的18位号码

              *         如果参数不是有效15位号码，返回值为"undefined"。

              *    函数三：isdate(intYear,intMonth,intDay) 日期检验函数

              *        参数分别是年月日，返回值为true时，验证通过。

              ************************************************************************************************/
                               
                   //升级15位身份证号码到18位
                   function isdate(intYear,intMonth,intDay){ 

                      if(isNaN(intYear)||isNaN(intMonth)||isNaN(intDay)) return false;     

                      if(intMonth>12||intMonth<1) return false;  

                      if ( intDay<1||intDay>31)return false;  

                      if((intMonth==4||intMonth==6||intMonth==9||intMonth==11)&&(intDay>30)) return false;  

                      if(intMonth==2){  

                         if(intDay>29) return false;    

                         if((((intYear%100==0)&&(intYear%400!=0))||(intYear%4!=0))&&(intDay>28))return false;  

                        }  

                      return true;  

                   }
              //检查身份证是否是正确格式

              function checkCard(cId) 
              {
                 var pattern;
                 if (cId.length==15)
                 {
                   pattern= /^\d{15}$/;//正则表达式,15位且全是数字
									 if (pattern.exec(cId)==null)
                   {
                          alert("15位身份证号码必须为数字！")
                       return false;
										}
                    if (!isdate("19"+cId.substring(6,8),cId.substring(8,10),cId.substring(10,12)))
                    {
                       alert("身份证号码中所含日期不正确") 
											 return false;
										}
                  }
                  else if (cId.length==18)
                  {
                     pattern= /^\d{17}(\d|x|X)$/;//正则表达式,18位且前17位全是数字，最后一位只能数字,x,X
                     if (pattern.exec(cId)==null)
                     {
												alert("18位身份证号码必须为数字！")
												return false;
					 }
                     if (!isdate(cId.substring(6,10),cId.substring(10,12),cId.substring(12,14)))
                     {
                          alert("身份证号码中所含日期不正确") 
                        return false;
                     }
                     var strJiaoYan  =[  "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"];
                     var intQuan =[7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1];
                     var intTemp=0;
                     for(i = 0; i < cId.length - 1; i++)
                     intTemp +=  cId.substring(i, i + 1)  * intQuan[i];  
                     intTemp %= 11;
                     if(cId.substring(cId.length - 1,cId.length).toUpperCase()!=strJiaoYan[intTemp])
                     {
							   alert("身份证验证码失败！")
                          return false;
                     }
									 }
                   else
                   {
                          alert("长度必须为15或18！")
                          return false;
										}
                    return true;
                }
                                   //升级15位身份证号码到18位

                   function C15ToC18(c15)

                   {

                       var cId;

                       if (c15.length==15)

                            {

                                 pattern= /^\d{15}$/;

                                 if (pattern.exec(c15)==null)

                                 {

                                     //   alert("15位身份证号码必须为数字！")

                                      return  ;

                                 }

                                 if (!isdate("19"+c15.substring(6,8),c15.substring(8,10),c15.substring(10,12)))

                                 {

                                     //alert("身份证号码中所含日期不正确") 

                                 return  ;

                                 }

                                cId=c15.substring(0,6)+"19"+c15.substring(6,15);

                                 var strJiaoYan  =[  "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"];

                                 var intQuan =[7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1];

                                 var intTemp=0;

                                 for(i = 0; i < cId.length - 1; i++)

                                 intTemp +=  cId.substring(i, i + 1)  * intQuan[i];  

                                 intTemp %= 11;

                                 cId+=+strJiaoYan[intTemp];

                                 return cId;

                            }

                            return;

                   }
                function getInfo(obj)
                {
                	var obj_value = obj.value;
                	/*if(obj_value.length == 15)
                	{
                		obj_value = C15ToC18(obj_value);
                	}*/
                	checkCard(obj_value); 
                }
                