              /************************************************************************************************

              *    ���֤������֤����

              *    ����һ��checkCard(cId) ��ݺ�����麯��

              *        ����cIdΪҪ��֤�����֤���룬����ֵΪtrueʱ����֤ͨ����

              *        ��һЩλ�ñ�����alert()��ע�ͣ��ɹ����ѵ���ʹ��

              *    ��������C15ToC18(c15)  15λ���֤��������Ϊ18λ����

              *        ����c15ΪҪת����15λ���֤���룬����ֵΪת�����18λ����

              *         �������������Ч15λ���룬����ֵΪ"undefined"��

              *    ��������isdate(intYear,intMonth,intDay) ���ڼ��麯��

              *        �����ֱ��������գ�����ֵΪtrueʱ����֤ͨ����

              ************************************************************************************************/
                               
                   //����15λ���֤���뵽18λ
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
              //������֤�Ƿ�����ȷ��ʽ

              function checkCard(cId) 
              {
                 var pattern;
                 if (cId.length==15)
                 {
                   pattern= /^\d{15}$/;//������ʽ,15λ��ȫ������
									 if (pattern.exec(cId)==null)
                   {
                          alert("15λ���֤�������Ϊ���֣�")
                       return false;
										}
                    if (!isdate("19"+cId.substring(6,8),cId.substring(8,10),cId.substring(10,12)))
                    {
                       alert("���֤�������������ڲ���ȷ") 
											 return false;
										}
                  }
                  else if (cId.length==18)
                  {
                     pattern= /^\d{17}(\d|x|X)$/;//������ʽ,18λ��ǰ17λȫ�����֣����һλֻ������,x,X
                     if (pattern.exec(cId)==null)
                     {
												alert("18λ���֤�������Ϊ���֣�")
												return false;
					 }
                     if (!isdate(cId.substring(6,10),cId.substring(10,12),cId.substring(12,14)))
                     {
                          alert("���֤�������������ڲ���ȷ") 
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
							   alert("���֤��֤��ʧ�ܣ�")
                          return false;
                     }
									 }
                   else
                   {
                          alert("���ȱ���Ϊ15��18��")
                          return false;
										}
                    return true;
                }
                                   //����15λ���֤���뵽18λ

                   function C15ToC18(c15)

                   {

                       var cId;

                       if (c15.length==15)

                            {

                                 pattern= /^\d{15}$/;

                                 if (pattern.exec(c15)==null)

                                 {

                                     //   alert("15λ���֤�������Ϊ���֣�")

                                      return  ;

                                 }

                                 if (!isdate("19"+c15.substring(6,8),c15.substring(8,10),c15.substring(10,12)))

                                 {

                                     //alert("���֤�������������ڲ���ȷ") 

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
                