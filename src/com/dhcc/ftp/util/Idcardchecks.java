/** *//**
 * 
 */
package com.dhcc.ftp.util;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import com.dhcc.ftp.dao.DaoFactory;

/** *//**
 * @author ������
 *
 */
public class Idcardchecks {
	private static final Logger logger=Logger.getLogger(DaoFactory.class);
//    λȨֵ����
    private static byte[] Wi=new byte[17];
//    ���֤ǰ�����ַ���
    private static final byte fPart = 6;
//    ���֤�㷨��ģ�ؼ�ֵ
    private static final byte fMod = 11;
//    �����֤����
    private static final byte oldIDLen = 15;
//    �����֤����
    private static final byte newIDLen = 18;
//    �����֤��ݱ�־
    private static final String yearFlag = "19";
//    У���봮 
    private static final String CheckCode="10X98765432"; 
//    ��С������������
    private static final int minCode = 150000;
//    ��������������
    private static final int maxCode = 700000;
//    �����֤����
//    private String oldIDCard="";
//    �����֤����
//    private String newIDCard="";
//    ����������
    
    
    //private String Area[][2] = 
    private static void setWiBuffer(){
        for(int i=0;i<Wi.length;i++){    
            int k = (int) Math.pow(2, (Wi.length-i));
            Wi[i] = (byte)(k % fMod);
        }
    }
    
    //��ȡ�����֤�����һλ:����λ
    private static String getCheckFlag(String idCard){
        int sum = 0;
        //���м�Ȩ��� 
        for(int i=0; i<17; i++){        
            sum += Integer.parseInt(idCard.substring(i,i+1)) * Wi[i];
        }
        //ȡģ���㣬�õ�ģֵ
        byte iCode = (byte) (sum % fMod); 
        return CheckCode.substring(iCode,iCode+1);
    }
    
    //�жϴ����ȵĺϷ���
    private static boolean checkLength(final String idCard,boolean newIDFlag){
        boolean right = (idCard.length() == oldIDLen) || (idCard.length() == newIDLen);
        newIDFlag = false;
        if(right){
            newIDFlag = (idCard.length() == newIDLen);
        }
        return right;
    }
    
    //��ȡʱ�䴮
   public static String getIDDate(final String idCard,boolean newIDFlag){
        String dateStr = "";
        if(newIDFlag)
            dateStr = idCard.substring(fPart,fPart+8);
        else
            dateStr = yearFlag + idCard.substring(fPart,fPart+6);
        return dateStr;
    }
    
    //�ж�ʱ��Ϸ���
    private static boolean checkDate(final String dateSource){
        String dateStr = dateSource.substring(0,4)+"-"+dateSource.substring(4,6)+"-"+dateSource.substring(6,8);
      //  System.out.println(dateStr);
        DateFormat df = DateFormat.getDateInstance();
        df.setLenient(false);
        try {
            Date date= df.parse(dateStr);
            return (date!=null);
        } catch (ParseException e){
            // TODO Auto-generated catch block
            return false;
        }
    }
    
    //�����֤ת���������֤����
    public static String getNewIDCard(final String oldIDCard){
        //��ʼ������
        Idcardchecks.setWiBuffer();
        if(!checkIDCard(oldIDCard)){
            return oldIDCard;
        }
        String newIDCard = oldIDCard.substring(0, fPart);
        newIDCard += yearFlag;
        newIDCard += oldIDCard.substring(fPart, oldIDCard.length());
        String ch = getCheckFlag(newIDCard);
        newIDCard += ch;
        return newIDCard;
    }
    
    //�����֤ת���ɾ����֤����
    public static String getOldIDCard(final String newIDCard){
        //��ʼ������
        Idcardchecks.setWiBuffer();
        //if(!checkIDCard(newIDCard)){
          //  return newIDCard;
       // }
        String oldIDCard = newIDCard.substring(0,fPart)+
                    newIDCard.substring(fPart+yearFlag.length(),newIDCard.length()-1);
//                    System.out.println("------------"+oldIDCard);
//                    System.out.println("++++++++++++"+newIDCard);
        return oldIDCard;
    }
    
    //�ж����֤����ĺϷ���
    public static boolean checkIDCard(final String idCard){
        //��ʼ������
        Idcardchecks.setWiBuffer();
        boolean isNew = false;
        //String message = "";
        if (!checkLength(idCard,isNew)){
            //message = "ID�����쳣";
            return false;
        }
        String idDate = getIDDate(idCard, isNew);
        if(!checkDate(idDate)){
            //message = "IDʱ���쳣";
            return false;
        }
        if(isNew){
            String checkFlag = getCheckFlag(idCard);
            String theFlag = idCard.substring(idCard.length()-1,idCard.length());
            if(!checkFlag.equals(theFlag)){
                //message = "�����֤У��λ�쳣";
                return false;
            }
        }
        return true;
    }
    
    //��ȡһ�������"α"���֤����
    public static String getRandomIDCard(final boolean idNewID){
        //��ʼ������
        Idcardchecks.setWiBuffer();
        Random ran = new Random();
        String idCard = getAddressCode(ran)+getRandomDate(ran,idNewID)+getIDOrder(ran);
        if(idNewID){
            String ch = getCheckFlag(idCard);
            idCard += ch;
        }
        return idCard;
    }
    
    //��������ĵ�������
    private static String getAddressCode(Random ran){
        if(ran==null){
            return "";
        }else{
            int addrCode = minCode + ran.nextInt(maxCode-minCode);
            return Integer.toString(addrCode);
        }
    }
    
    //��������ĳ�������
    private static String getRandomDate(Random ran, boolean idNewID) {
        // TODO Auto-generated method stub
        if(ran==null){
            return "";
        }
        int year = 0;
        if(idNewID){
            year = 1900 + ran.nextInt(2007-1900);
        }else{
            year = 1 + ran.nextInt(99);
        }
        int month = 1+ran.nextInt(12);
        int day = 0;
        if(month==2){
            day= 1+ran.nextInt(28);
        }else if(month==1 || month==3 || month==5 || month==7 || month==8 || month==10 || month==12){
            day= 1+ran.nextInt(31);
        }else{
            day= 1+ran.nextInt(30);
        }
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setMaximumIntegerDigits(2);
        nf.setMinimumIntegerDigits(2);
        String dateStr = Integer.toString(year)+nf.format(month)+nf.format(day);
        return dateStr;
    }
    
    // �ж����֤����ĺϷ��� Ϊ�˴�����ִ�����ʾ
	public static String checkIDCard2(String idCard) {
		// ��ʼ������
		Idcardchecks.setWiBuffer();
		boolean isNew = false;
		String aaaa = "5";
		// String message = "";
		if (!checkLength(idCard, isNew)) {
			// message = "ID�����쳣";
			logger.info("ID�����쳣1");
			aaaa = "1";
		} else {
			isNew = (idCard.length() == newIDLen);
			String idDate = getIDDate(idCard, isNew);
			if (!checkDate(idDate)) {
				// message = "IDʱ���쳣";
				logger.info("IDʱ���쳣");
				aaaa = "2";
			} else {
				if (isNew) {
					String checkFlag = getCheckFlag(idCard);
					String theFlag = idCard.substring(idCard.length() - 1, idCard.length());
					if (!checkFlag.equals(theFlag)) {
						// message = "�����֤У��λ�쳣";
						logger.info("�����֤У��λ�쳣");
						aaaa = "3";
					}
				}
			}
		}
		return aaaa;
	}

    //������������к�
    private static String getIDOrder(Random ran){
        // TODO Auto-generated method stub
        NumberFormat nf = NumberFormat.getIntegerInstance();
        nf.setMaximumIntegerDigits(3);
        nf.setMinimumIntegerDigits(3);
        if(ran==null){
            return "";
        }else{
            int order = 1+ran.nextInt(999);
            return nf.format(order);
        }
    }

    public Idcardchecks(){
        setWiBuffer();
    }
    /** *//**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        String randomID=Idcardchecks.getRandomIDCard(true);
        logger.info("������֤��"+randomID);
        /**//*
        String oldID="";
        String newID=idcardchecks.getNewIDCard(oldID);
        System.out.println("�����֤��"+oldID);
        System.out.println("�����֤��"+newID);
        String oldCard = idcardchecks.getOldIDCard(newID);
        System.out.println("�����֤��"+oldCard);
        /*
        String dateSource="2000-9-30";
        if(id.checkDate(dateSource))
            System.out.println("��ȷʱ�䴮��"+dateSource);
        else
            System.out.println("����ʱ�䴮��"+dateSource);
         * 
         * 
         */
    }
}