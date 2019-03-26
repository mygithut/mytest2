package com.dhcc.ftp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 参考网上资料，15位身份证转化18位或者18位转化成15位，还有校验身份证是否合法，也加一个自己写的方法 
 * 由一种身份证号码求到另外一个身份证号码
 * @author fuww
 *
 */
public class IdCardUtil {
//    protected final  Log logger = LogFactory.getLog(IdCardUtil.class);
    
//    static{
//        
//        //PropertyConfigurator.configure("log4j.properties");
//        BasicConfigurator.configure();
//        
//    }

    public IdCardUtil(){
        
    }
    
    /** *//**
     * 15位身份证号码转化为18位的身份证。如果是18位的身份证则直接返回，不作任何变化。
     * @param idCard,15位的有效身份证号码
     * @return idCard18 返回18位的有效身份证
     */
    public String IdCard15to18(String idCard){
        idCard = idCard.trim();
        StringBuffer idCard18 = new StringBuffer(idCard);
        //加权因子
        //int[] weight = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
        //校验码值
        char[] checkBit = {'1','0','X','9','8','7','6','5','4','3','2'};
        int sum = 0;
        //15位的身份证
        if(idCard != null && idCard.length()==15){
            idCard18.insert(6, "19");
            for(int index=0;index<idCard18.length();index++){
                char c = idCard18.charAt(index);
                int ai = Integer.parseInt(new Character(c).toString());
//                logger.debug(new Integer(ai));
                //sum = sum+ai*weight[index];
                //加权因子的算法
                int Wi = ((int)Math.pow(2, idCard18.length()-index))%11;
                sum = sum+ai*Wi;
            }
            int indexOfCheckBit = sum%11; //取模
            idCard18.append(checkBit[indexOfCheckBit]);
        }
//        logger.debug(idCard18);
        return idCard18.toString();
    }
    /**
     * 此方法为20后的人校验身份证服务的,不知道20后18身份证生成算法，暂和20前的算法一样。0........                              
     * @param idCard
     * @return
     */
    public String IdCard15to18for20hou(String idCard){
        idCard = idCard.trim();
        StringBuffer idCard18 = new StringBuffer(idCard);
        //加权因子
        //int[] weight = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
        //校验码值
        char[] checkBit = {'1','0','X','9','8','7','6','5','4','3','2'};
        int sum = 0;
        //15位的身份证
        if(idCard != null && idCard.length()==15){
            idCard18.insert(6, "20");
            for(int index=0;index<idCard18.length();index++){
                char c = idCard18.charAt(index);
                int ai = Integer.parseInt(new Character(c).toString());
//                logger.debug(new Integer(ai));
                //sum = sum+ai*weight[index];
                //加权因子的算法
                int Wi = ((int)Math.pow(2, idCard18.length()-index))%11;
                sum = sum+ai*Wi;
            }
            int indexOfCheckBit = sum%11; //取模
            idCard18.append(checkBit[indexOfCheckBit]);
        }
//        logger.debug(idCard18);
        return idCard18.toString();
    }
    
    /** *//**
     * 转化18位身份证位15位身份证。如果输入的是15位的身份证则不做任何转化，直接返回。
     * @param idCard 18位身份证号码
     * @return idCard15
     */
    public String IdCard18to15(String idCard){
        idCard = idCard.trim();
        StringBuffer idCard15 = new StringBuffer(idCard);
        if(idCard!=null && idCard.length()==18){
            idCard15.delete(17, 18);
            idCard15.delete(6, 8);
        }
//        logger.debug(idCard15);
        return idCard15.toString();

}
    /** *//**
     * 校验是否是一个有效的身份证。如果是18的身份证，则校验18位的身份证。15位的身份证不校验，也无法校验
     * @param idCart
     * @return
     */
    public boolean checkIDCard(String idCard){
        boolean isIDCard = false;
        Pattern pattern = Pattern.compile("\\d{15}|\\d{17}[x,X,0-9]");
        Matcher matcher = pattern.matcher(idCard);
        if(matcher.matches()){//可能是一个身份证
            isIDCard = true;
            if(idCard.length()==18){//如果是18的身份证，则校验18位的身份证。15位的身份证暂不校验
                String IdCard15 = IdCard18to15(idCard);
                String IdCard18 =null;
                if(idCard.substring(6,8).equals("20")){
                IdCard18 = IdCard15to18for20hou(IdCard15);
                }else{
                IdCard18 = IdCard15to18(IdCard15);
                }
         //       System.out.println(IdCard18+"——--校验后产生的");
                if(!idCard.equals(IdCard18)){
                    isIDCard = false;
                }
            }else if(idCard.length()==15){
               isIDCard = true;
            }else{
                isIDCard = false;
            }
        }
        return isIDCard;
    } 
    /**此方法是取一个身份证另外一个号码
     * add by fuww
     * @param args
     */
    public String getOtherIdNum(String idCard){
    	String otherNum ="";
    	if(null != idCard && (!"".equals(idCard)) && idCard.length() == 15){
      		otherNum = IdCard15to18(idCard);
    	}
    	else if(null != idCard && (!"".equals(idCard)) && idCard.length() == 18){
    		otherNum = IdCard18to15(idCard);
    	}else {
          //System.out.println(idCard+"--此身份证不符合条件");
    	}
    	return otherNum;
    }
    //测试
//    public static void main(String[] args){
//    	
//        IdCardUtil u = new IdCardUtil();
//        String idCard = "320925831018611";
//        //idCard = "420106810304041";
//        //u.IdCard15to18(idCard);
//        //u.IdCart18to15(idCart);
//      System.out.println(u.IdCard15to18("320925831018611"));
//        u.checkIDCard(idCard);
//    }

    }
