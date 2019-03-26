package com.dhcc.ftp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * �ο��������ϣ�15λ���֤ת��18λ����18λת����15λ������У�����֤�Ƿ�Ϸ���Ҳ��һ���Լ�д�ķ��� 
 * ��һ�����֤����������һ�����֤����
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
     * 15λ���֤����ת��Ϊ18λ�����֤�������18λ�����֤��ֱ�ӷ��أ������κα仯��
     * @param idCard,15λ����Ч���֤����
     * @return idCard18 ����18λ����Ч���֤
     */
    public String IdCard15to18(String idCard){
        idCard = idCard.trim();
        StringBuffer idCard18 = new StringBuffer(idCard);
        //��Ȩ����
        //int[] weight = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
        //У����ֵ
        char[] checkBit = {'1','0','X','9','8','7','6','5','4','3','2'};
        int sum = 0;
        //15λ�����֤
        if(idCard != null && idCard.length()==15){
            idCard18.insert(6, "19");
            for(int index=0;index<idCard18.length();index++){
                char c = idCard18.charAt(index);
                int ai = Integer.parseInt(new Character(c).toString());
//                logger.debug(new Integer(ai));
                //sum = sum+ai*weight[index];
                //��Ȩ���ӵ��㷨
                int Wi = ((int)Math.pow(2, idCard18.length()-index))%11;
                sum = sum+ai*Wi;
            }
            int indexOfCheckBit = sum%11; //ȡģ
            idCard18.append(checkBit[indexOfCheckBit]);
        }
//        logger.debug(idCard18);
        return idCard18.toString();
    }
    /**
     * �˷���Ϊ20�����У�����֤�����,��֪��20��18���֤�����㷨���ݺ�20ǰ���㷨һ����0........                              
     * @param idCard
     * @return
     */
    public String IdCard15to18for20hou(String idCard){
        idCard = idCard.trim();
        StringBuffer idCard18 = new StringBuffer(idCard);
        //��Ȩ����
        //int[] weight = {7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
        //У����ֵ
        char[] checkBit = {'1','0','X','9','8','7','6','5','4','3','2'};
        int sum = 0;
        //15λ�����֤
        if(idCard != null && idCard.length()==15){
            idCard18.insert(6, "20");
            for(int index=0;index<idCard18.length();index++){
                char c = idCard18.charAt(index);
                int ai = Integer.parseInt(new Character(c).toString());
//                logger.debug(new Integer(ai));
                //sum = sum+ai*weight[index];
                //��Ȩ���ӵ��㷨
                int Wi = ((int)Math.pow(2, idCard18.length()-index))%11;
                sum = sum+ai*Wi;
            }
            int indexOfCheckBit = sum%11; //ȡģ
            idCard18.append(checkBit[indexOfCheckBit]);
        }
//        logger.debug(idCard18);
        return idCard18.toString();
    }
    
    /** *//**
     * ת��18λ���֤λ15λ���֤������������15λ�����֤�����κ�ת����ֱ�ӷ��ء�
     * @param idCard 18λ���֤����
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
     * У���Ƿ���һ����Ч�����֤�������18�����֤����У��18λ�����֤��15λ�����֤��У�飬Ҳ�޷�У��
     * @param idCart
     * @return
     */
    public boolean checkIDCard(String idCard){
        boolean isIDCard = false;
        Pattern pattern = Pattern.compile("\\d{15}|\\d{17}[x,X,0-9]");
        Matcher matcher = pattern.matcher(idCard);
        if(matcher.matches()){//������һ�����֤
            isIDCard = true;
            if(idCard.length()==18){//�����18�����֤����У��18λ�����֤��15λ�����֤�ݲ�У��
                String IdCard15 = IdCard18to15(idCard);
                String IdCard18 =null;
                if(idCard.substring(6,8).equals("20")){
                IdCard18 = IdCard15to18for20hou(IdCard15);
                }else{
                IdCard18 = IdCard15to18(IdCard15);
                }
         //       System.out.println(IdCard18+"����--У��������");
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
    /**�˷�����ȡһ�����֤����һ������
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
          //System.out.println(idCard+"--�����֤����������");
    	}
    	return otherNum;
    }
    //����
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
