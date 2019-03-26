package com.dhcc.ftp.util;

import java.awt.Component;
import java.io.File;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.ComSysParm;
import com.liferay.portal.kernel.util.ServerDetector;


/**
 * ��������
 * @author wang
 *
 */
public class CommonFunctions {
	/**
	 * ���ݿ����ӹ���DaoFactory
	 */
	public static DaoFactory mydao=new DaoFactory();
	//public static String ProjectPath=System.getProperty("user.dir");
	/**
	 * ���ݿ��ѯ�������List
	 */
	public static List list=null;
	
	
	//��׼�����������
	/**
	 * ��ȡ��ǰϵͳʱ���ַ������������ļ����ļ��е����������Բ������ļ�����֧�ֵ��ַ�
	 * @return  (String)  ��ǰϵͳʱ�� ��ʽΪ "������-ʱ����" ���� 20091202-204506
	 */
	public static String GetCurrentTimeInFileName(){
		Calendar ca = Calendar.getInstance(); 
	    int year = ca.get(Calendar.YEAR);//��ȡ���
	    String Stryear=String.valueOf(year);
	    
	    int month=ca.get(Calendar.MONTH);//��ȡ�·�
	    String Strmonth=String.valueOf(month);
	    if(month<10){
	    	Strmonth="0"+Strmonth;
	    }
	    
	    int day=ca.get(Calendar.DATE);//��ȡ��
	    String Strday=String.valueOf(day);
	    if(day<10){
	    	Strday="0"+Strday;
	    }
	    
	    int hour=ca.get(Calendar.HOUR_OF_DAY);//Сʱ
	    String Strhour=String.valueOf(hour);
	    if(hour<10){
	    	Strhour="0"+Strhour;
	    }
	    
	    int minute=ca.get(Calendar.MINUTE);//��
	    String Strminute=String.valueOf(minute);
	    if(minute<10){
	    	Strminute="0"+Strminute;
	    }
	    
	    int second=ca.get(Calendar.SECOND);//��    
	    String Strsecond=String.valueOf(second);
	    if(second<10){
	    	Strsecond="0"+Strsecond;
	    }
	    
	    String CurrentTimeInFileName=Stryear+Strmonth+Strday+"-"+Strhour+Strminute+Strsecond;
	    return CurrentTimeInFileName;
	}
	
	/**
	 * ��ȡ��ǰ���ݿ�ϵͳ����(8λ������)���Ǵ����ݿ�ϵͳ�������в�ѯ���
	 * @return  (long)  ��ǰ���ݿ�ϵͳʱ�� ��ʽΪ "������" ��20091202
	 */
	public static long GetDBSysDate(){
		long SysDate=0;
		String sql="from ComSysParm order by sysDate DESC";
		DaoFactory mydao=new DaoFactory();
	    list=mydao.query(sql, null);
	    ComSysParm com_sys_parm=new ComSysParm();
	    if(list.size()==0){
	    	System.out.println("ϵͳ���ڻ�ȡ�����������ݿ⹫��������com_sys_parm��");
	    	return 0;
	    }
	    com_sys_parm=(ComSysParm)list.get(0);
	    SysDate=com_sys_parm.getSysDate();
	    return SysDate;
	}
	/**
	 * ��ȡ�ϴ����ݿ�ϵͳ����(8λ������)���Ǵ����ݿ�ϵͳ�������в�ѯ���
	 * @return  (long)  ��ǰ���ݿ�ϵͳʱ�� ��ʽΪ "������" ��20091202
	 */
	public static long GetDBLastSysDate(){
		long lstDate=0;
		String sql="from ComSysParm order by sysDate DESC";
		DaoFactory mydao=new DaoFactory();
	    list=mydao.query(sql, null);
	    ComSysParm com_sys_parm=new ComSysParm();
	    if(list.size()==0){
	    	System.out.println("ϵͳ���ڻ�ȡ�����������ݿ⹫��������com_sys_parm��");
	    	return 0;
	    }
	    com_sys_parm=(ComSysParm)list.get(0);
	    lstDate=com_sys_parm.getLstDate();
	    return lstDate;
	}
	/**
	 * ��ȡ��ǰ���ݿ�ϵͳ��ǰ��������(8λ������)���Ǵ����ݿ�ϵͳ�������в�ѯ���
	 * @return  (long)  ��ǰ���ݿ�ϵͳʱ�� ��ʽΪ "������" ��20091202
	 */
	public static long GetDBTodayDate(){
		long todayDate=0;
		String sql="from ComSysParm order by sysDate DESC";
		DaoFactory mydao=new DaoFactory();
	    list=mydao.query(sql, null);
	    ComSysParm com_sys_parm=new ComSysParm();
	    if(list.size()==0){
	    	System.out.println("ϵͳ���ڻ�ȡ�����������ݿ⹫��������com_sys_parm��");
	    	return 0;
	    }
	    com_sys_parm=(ComSysParm)list.get(0);
	    todayDate=com_sys_parm.getTodayDate();
	    return todayDate;
	}
	/**
	 * ��ȡ��ǰϵͳ����(8λ������)���ǵ��ü����ϵͳ������ȡ�����ʱ��
	 * @return  (long)  ��ǰϵͳʱ�� ��ʽΪ "������" ��20091202
	 */
	public static long GetCurrentDateInLong(){
		Calendar ca = Calendar.getInstance(); 
	    int year = ca.get(Calendar.YEAR);//��ȡ���
	    String Stryear=String.valueOf(year);
	    
	    int month=ca.get(Calendar.MONTH)+1;//��ȡ�·�
	    String Strmonth=String.valueOf(month);
	    if(month<10){
	    	Strmonth="0"+Strmonth;
	    }
	    
	    int day=ca.get(Calendar.DATE);//��ȡ��
	    String Strday=String.valueOf(day);
	    if(day<10){
	    	Strday="0"+Strday;
	    }
	    
	    int hour=ca.get(Calendar.HOUR_OF_DAY);//Сʱ
	    String Strhour=String.valueOf(hour);
	    if(hour<10){
	    	Strhour="0"+Strhour;
	    }
	    
	    int minute=ca.get(Calendar.MINUTE);//��
	    String Strminute=String.valueOf(minute);
	    if(minute<10){
	    	Strminute="0"+Strminute;
	    }
	    
	    int second=ca.get(Calendar.SECOND);//��    
	    String Strsecond=String.valueOf(second);
	    if(second<10){
	    	Strsecond="0"+Strsecond;
	    }
	    
	    String CurrentDateStr=Stryear+Strmonth+Strday;
	    long CurrentTimeInLong=Long.parseLong(CurrentDateStr);
	    return CurrentTimeInLong;
	}
	//��׼�����������
	/**
	 * ��ȡ��ǰϵͳʱ��
	 * @return  (String)  ��ǰϵͳʱ�� ��ʽΪ "��-��-�� ʱ:��:��"���� "2009-12-02 20:45:06"
	 */
	public static String GetCurrentTime(){
		/*
		//SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy'-'MM'-'dd' 'hh':'mm':'ss");
		DateFormat dateformat = DateFormat.getDateTimeInstance(); 
		Calendar date = Calendar.getInstance();
		String CurrentTime=dateformat.format(date.getTime());
		//System.out.println("######��ǰʱ��Ϊ�� "+CurrentTime);
		*/
		
		Calendar ca = Calendar.getInstance();
	    int year = ca.get(Calendar.YEAR);//��ȡ���
	    String Stryear=String.valueOf(year);
	    
	    int month=ca.get(Calendar.MONTH)+1;//��ȡ�·�  �·�ϵͳ��0��ʼ�����
	    String Strmonth=String.valueOf(month);
	    if(month<10){
	    	Strmonth="0"+Strmonth;
	    }
	    
	    int day=ca.get(Calendar.DATE);//��ȡ��
	    String Strday=String.valueOf(day);
	    if(day<10){
	    	Strday="0"+Strday;
	    }
	    
	    int hour=ca.get(Calendar.HOUR_OF_DAY);//Сʱ
	    String Strhour=String.valueOf(hour);
	    if(hour<10){
	    	Strhour="0"+Strhour;
	    }
	    
	    int minute=ca.get(Calendar.MINUTE);//��
	    String Strminute=String.valueOf(minute);
	    if(minute<10){
	    	Strminute="0"+Strminute;
	    }
	    
	    int second=ca.get(Calendar.SECOND);//��    
	    String Strsecond=String.valueOf(second);
	    if(second<10){
	    	Strsecond="0"+Strsecond;
	    }
	    
	    String CurrentTime=Stryear+"-"+Strmonth+"-"+Strday+" "+Strhour+":"+Strminute+":"+Strsecond;
	    return CurrentTime;
	}
	
	
	/**
	 * ��ȡͬһ������ʱ���ַ�����ʱ��� ����Ϊ��λ,  ʱ���ַ�����ʽΪ2009-12-02 20:45:06
	 * 
	 * 
	 */
	public static int GetCostTimeInSecond(String BeginTime,String EndTime){
		int CostTimeInSecond=0;
		BeginTime=BeginTime.substring(BeginTime.length()-8);
		EndTime=EndTime.substring(EndTime.length()-8);
		int IntShi_BeginTime=Integer.parseInt(BeginTime.substring(0,2)),
		    IntFen_BeginTime=Integer.parseInt(BeginTime.substring(3,5)),
		    IntMiao_BeginTime=Integer.parseInt(BeginTime.substring(6)),
		    IntShi_EndTime=Integer.parseInt(EndTime.substring(0,2)),
		    IntFen_EndTime=Integer.parseInt(EndTime.substring(3,5)),
		    IntMiao_EndTime=Integer.parseInt(EndTime.substring(6));
		int DeltaShi=IntShi_EndTime-IntShi_BeginTime,
		    DeltaFen=IntFen_EndTime-IntFen_BeginTime,
		    DeltaMiao=IntMiao_EndTime-IntMiao_BeginTime;
		//System.out.println("###########"+IntMiao_EndTime);
		CostTimeInSecond=DeltaShi*3600+DeltaFen*60+DeltaMiao;
		return CostTimeInSecond;
	}
	
	/** �޸���������
	    * @param baseDate ��׼����String (��ʽyyyy-MM-dd)
	    * @param amount Ҫ���ӵ�����������Ϊ����
	    * @return ��׼�������ӻ���������������� 
	    */
	    public static String dateModify(String baseDate,int amount )
	    {
	       StringBuffer sb = new StringBuffer();
	      
	       SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd"); // ���ַ�����ʽ��
	       Date dt=sdf.parse(baseDate,new ParsePosition(0)); // �ɸ�ʽ������ַ�������һ��Date����
	      
	       Calendar c = Calendar.getInstance(); // ��ʼ��һ��Calendar
	       try{
	    	    c.setTime(dt); // ���û�׼����
	       }catch(Exception e){
	    	   System.out.println("��������"+baseDate+"��ʽ�����ⲻ���ϸ�ʽyyyy-MM-dd��");
	    	   return null;
	    	   //e.printStackTrace();
	       }
	      
	       c.add(Calendar.DATE, amount); //��Ҫ�Ӽ������� 
	       Date dt1=c.getTime(); // ��Calendar�����û�׼���������������
	       sb=sdf.format(dt1,sb,new FieldPosition(0)); // �õ�����ַ���

	       return sb.toString();
	    }
	    /** �޸���������
		    * @param baseDate ��׼����String (��ʽyyyyMMdd)
		    * @param amount Ҫ���ӵ�����������Ϊ����
		    * @return ��׼�������ӻ���������������� 
		    */
		    
	    public static String dateModifyD(String baseDate,int amount)
	    {
	       StringBuffer sb = new StringBuffer();
	      
	       SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); // ���ַ�����ʽ��
	       Date dt=sdf.parse(baseDate,new ParsePosition(0)); // �ɸ�ʽ������ַ�������һ��Date����
	      
	       Calendar c = Calendar.getInstance(); // ��ʼ��һ��Calendar
	       try{
	    	    c.setTime(dt); // ���û�׼����
	       }catch(Exception e){
	    	   System.out.println("��������"+baseDate+"��ʽ�����ⲻ���ϸ�ʽyyyyMMdd��");
	    	   return null;
	    	   //e.printStackTrace();
	       }

	       c.add(Calendar.DATE, amount); //��Ҫ�Ӽ������� 
	       Date dt1=c.getTime(); // ��Calendar�����û�׼���������������
	       sb=sdf.format(dt1,sb,new FieldPosition(0)); // �õ�����ַ���

	       return sb.toString();
	    }
	    
	    /** �޸������,�������µ�
		 * @param baseDate ��׼����String (��ʽyyyyMMdd)
		 * @param amount Ҫ���ӵ��£�����Ϊ����
		 * @return ��׼�������ӻ���������º������ 
		*/
	    public static String dateModifyM(String baseDate,int amount)
	    {
	    	//�����µ�
			String date2_nm=dateModifyM_normal(baseDate, amount+1);		
		    long d=Long.valueOf(date2_nm);
		    d=d/100*100+1;
		    d=pub_base_deadlineD(d, -1);	       
	        return String.valueOf(d);
	    }
	    
	    /** �޸������
		 * @param baseDate ��׼����String (��ʽyyyyMMdd)
		 * @param amount Ҫ���ӵ��£�����Ϊ����
		 * @return ��׼�������ӻ���������º������ 
		*/
	    public static String dateModifyM_normal(String baseDate,int amount)
	    {
	       StringBuffer sb = new StringBuffer();
	      
	       SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); // ���ַ�����ʽ��
	       Date dt=sdf.parse(baseDate,new ParsePosition(0)); // �ɸ�ʽ������ַ�������һ��Date����
	      
	       Calendar c = Calendar.getInstance(); // ��ʼ��һ��Calendar
	       try{
	    	    c.setTime(dt); // ���û�׼����
	       }catch(Exception e){
	    	   System.out.println("��������"+baseDate+"��ʽ�����ⲻ���ϸ�ʽyyyyMMdd��");
	    	   return null;
	    	   //e.printStackTrace();
	       }
	      
	       c.add(Calendar.MONTH, amount); //��Ҫ�Ӽ������� 
	       Date dt1=c.getTime(); // ��Calendar�����û�׼���������������
	       sb=sdf.format(dt1,sb,new FieldPosition(0)); // �õ�����ַ���
	       
	       
	       return sb.toString();
	    }
	    
	    /** �޸������
		 * @param baseDate ��׼����String (��ʽyyyyMMdd)
		 * @param amount Ҫ���ӵ��꣨����Ϊ����
		 * @return ��׼�������ӻ���������������� 
		*/
	    public static String dateModifyY(String baseDate,int amount)
	    {
	       StringBuffer sb = new StringBuffer();
	      
	       SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); // ���ַ�����ʽ��
	       Date dt=sdf.parse(baseDate,new ParsePosition(0)); // �ɸ�ʽ������ַ�������һ��Date����
	      
	       Calendar c = Calendar.getInstance(); // ��ʼ��һ��Calendar
	       try{
	    	    c.setTime(dt); // ���û�׼����
	       }catch(Exception e){
	    	   System.out.println("��������"+baseDate+"��ʽ�����ⲻ���ϸ�ʽyyyyMMdd��");
	    	   return null;
	    	   //e.printStackTrace();
	       }
	      
	       c.add(Calendar.YEAR, amount); //��Ҫ�Ӽ������� 
	       Date dt1=c.getTime(); // ��Calendar�����û�׼���������������
	       sb=sdf.format(dt1,sb,new FieldPosition(0)); // �õ�����ַ���

	       return sb.toString();
	    }
	    /**
	     * �������޸�����
	     * @param baseDateLong (long) ��׼����long 8λ (��ʽyyyyMMdd)
	     * @param changeDays  (int) Ҫ���ӵ�����������Ϊ����
	     * @return (long) ��׼�������ӻ����������������(��ʽyyyyMMdd)
	     */
	    public static long pub_base_deadlineD(long baseDateLong,int changeDays){   	
	    	long ResultDateLong=0;
	    	
	    	String baseDate=String.valueOf(baseDateLong);
            if(baseDate.length()!=8){
            	System.out.println("��������"+baseDate+"��ʽ�����ⲻ���ϸ�ʽyyyyMMdd��");
	    	    return 0;
	    	}
	    	StringBuffer sb = new StringBuffer();
	        
	        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); // ���ַ�����ʽ��
	        Date dt=sdf.parse(baseDate,new ParsePosition(0)); // �ɸ�ʽ������ַ�������һ��Date����
	       
	        Calendar c = Calendar.getInstance(); // ��ʼ��һ��Calendar
	        
	        try{
	    	    c.setTime(dt); // ���û�׼����
	        }catch(Exception e){
	    	    System.out.println("��������"+baseDate+"��ʽ�����ⲻ���ϸ�ʽyyyyMMdd��");
	    	    return 0;
	    	    //e.printStackTrace();
	        }
	        
	        c.add(Calendar.DATE, changeDays); //��Ҫ�Ӽ������� 
	        Date dt1=c.getTime(); // ��Calendar�����û�׼���������������
	        sb=sdf.format(dt1,sb,new FieldPosition(0)); // �õ�����ַ���        
	        String ResultDateStr=sb.toString();
	        
	        ResultDateLong=Long.parseLong(ResultDateStr);
	        System.out.println(baseDateLong+"�仯 "+changeDays+" �����Ϊ:"+ResultDateLong);
	    	return ResultDateLong;
	    }
	    
	    /**
	     * ����������֮�������·� ----DateLong1-DateLong2֮�������·�
	     * @param DateLong1 (long) ����������long 8λ (��ʽyyyyMMdd)
	     * @param DateLong2 (long) ��������long 8λ (��ʽyyyyMMdd)
	     * @return  DateLong1-DateLong2֮�������·�
	     */
	    public static int monthsSubtract(long DateLong1,long DateLong2){
	    	int m1=(int)(DateLong1/100%100);
	    	int m2=(int)(DateLong2/100%100);
	    	int y1=(int)(DateLong1/10000);
	    	int y2=(int)(DateLong2/10000);
	    	int delta_m=(y1-y2)*12+(m1-m2);
	    	return delta_m;
	    }
	    
	    /**
	     * ��������������Ľ��������������ʾdateStr1��dateStr2ǰ��dateStr1��ʽΪ"yyyyMMdd"
	     * @param dateStr1 String  �����������ַ���
	     * @param dateStr2 String ���������ַ���
	     * @return int dateStr1-dateStr2������
	     */
	    public static int daysSubtract(String dateStr1, String dateStr2) {
	    	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd"); // ���ַ�����ʽ��
	        Date dt1=sdf.parse(dateStr1,new ParsePosition(0)); // �ɸ�ʽ������ַ�������һ��Date����
	        Date dt2=sdf.parse(dateStr2,new ParsePosition(0)); // �ɸ�ʽ������ַ�������һ��Date����
	    	long mills =dt1.getTime() - dt2.getTime();
	    	return (int) (mills / 1000 / 3600 / 24);
	    }
	    
	    /**  
	     *   �ļ�ѡ����
	     *   @param   DialogMode  (int)   �ļ�ѡ����ģʽ���Ǵ��ļ����Ǳ����ļ�(0��򿪣�����������)  
	     *   @param   FileDescription   (String)   ͨ���ļ���(�磺ExcelFile)
	     *   @param   FileSuffix   (String)   �ļ���׺��(�磺xls)
	     *   @param   parent   (Component)   ���ļ�ѡ��������ĸ�����  
	     *   @return  ExcelFilePath   (String)   ѡ�������ļ�ȫ·��,��ѡ��ȡ�����򷵻ؿ��ַ���"" 
	     */ 
	 	public static String FileChooser(int DialogMode,String FileDescription, String FileSuffix,Component parent){
	 		JFileChooser fc=new JFileChooser();
	 		String DialogTitle="";
	 		if(DialogMode==0){
	 			DialogTitle="��"+FileDescription;
	 		}else{
	 			DialogTitle="����"+FileDescription;
	 		}
	 		final String FileSuffixLowerCase=FileSuffix.toLowerCase(),
	 		             FileSuffixUpperCase=FileSuffix.toUpperCase();
	 		final String Description=FileDescription+"s";
	 		fc.setDialogTitle(DialogTitle);
	 		fc.setFileFilter(new FileFilter() {
	 			public boolean accept(File f) {
	 				return f.isDirectory() || f.getPath().endsWith(FileSuffixLowerCase)
	 						|| f.getPath().endsWith(FileSuffixUpperCase);
	 			}

	 			public String getDescription() {
	 				return Description;
	 			}
	 		});
	 		int intRetVal=0;
	 		if(DialogMode==0){
	 			intRetVal=fc.showOpenDialog(parent);
	 		}else{
	 			intRetVal=fc.showSaveDialog(parent);
	 		}
	 			
	 		if(intRetVal==JFileChooser.APPROVE_OPTION){
	 			String ExcelFilePath=fc.getSelectedFile().getPath();
	 			//System.out.println("Ҫ"+DialogTitle+"��·��Ϊ: "+ExcelFilePath);
	 			return ExcelFilePath;
	 		}else{
	 			return "";
	 		}
	 	}
	
		/**
		* С������λ����ȡ ����������,���֧�ֱ���9λС��������9λʱ�����κδ��� ֱ�ӷ���
		* @param d (double) Ҫ���н�λ��С��
		* @param n (int) ������С��λ��
		* @return ��λ��Ľ��
		*/
	    public static double doublecut(double d,int n){
	    	if(d==Double.POSITIVE_INFINITY || d==Double.NEGATIVE_INFINITY ||d==Double.NaN){
				 return  Double.NaN;
			 }
			 
			 if(n>=10){
				 return d;
			 }
			 boolean isLowZero=false;
			 if(d<0){
				 d=-d;
				 isLowZero=true;
			 }
			 long jishu=(int)Math.pow(10, n);
		     long longd=(long)(d*jishu);
		     if(d*jishu>=(longd+0.5)){
		    	 longd++;
		     }
		     d=longd/(double)jishu;
		     
		     if(isLowZero){
		    	 d=-d;
		     }
		     
		     return d;
	   }
	    /**
		* С������λ����ȡ ����������,���֧�ֱ���9λС��������9λʱ�����κδ��� ֱ�ӷ���
		* @param d (double) Ҫ���н�λ��С��
		* @param n (int) ������С��λ��
		* @return ��λ���String���
		*/
	    public static String doubleFormat(double d,int n){
	    	if(d==Double.POSITIVE_INFINITY || d==Double.NEGATIVE_INFINITY ||d==Double.NaN){
				 return  "NaN";
			 }
			 
			 if(n>=10){
				 return String.valueOf(d);
			 }
			 boolean isLowZero=false;
			 if(d<0){
				 d=-d;
				 isLowZero=true;
			 }
			 long jishu=(int)Math.pow(10, n);
		     long longd=(long)(d*jishu);
		     if(d*jishu>=(longd+0.5)){
		    	 longd++;
		     }
		     d=longd/(double)jishu;
		     
		     if(isLowZero){
		    	 d=-d;
		     }
		     if(n>=1){
		    	 String mark="0.";
		    	 for(int i=0;i<n;i++){
		    		 mark+="0";
		    	 }
		    	 DecimalFormat df = new DecimalFormat(mark);
		    	 return df.format(d);
		     }
		     
		     return String.valueOf(d);
	   }
	    /*public static String getSuper_item_no(String item_no){
			String super_item_no="";
			String sql="select superItemNo from ItemToAcc where itemNo='"+item_no+"'";
			List list=mydao.query(sql, null);
			if(list.size()==0){
				System.out.println("��Ŀ��Ӧ��Ŀ���ﲻ������Ŀ "+item_no);
				return null;
			}
			super_item_no=(String)list.get(0);
			return super_item_no;
		}
	    */
	    /**
		 * ��ȡ��ǰϵͳʱ���룬�ǵ��ü����ϵͳ������ȡ�����ʱ����
		 * @return   ��ǰϵͳʱ�� ��ʽΪ "hhmmss"
		 */
		public static String getCurrentTime(){
			Calendar ca = Calendar.getInstance(); 
		    
		    int hour=ca.get(Calendar.HOUR_OF_DAY);//Сʱ
		    String Strhour=String.valueOf(hour);
		    if(hour<10){
		    	Strhour="0"+Strhour;
		    }
		    
		    int minute=ca.get(Calendar.MINUTE);//��
		    String Strminute=String.valueOf(minute);
		    if(minute<10){
		    	Strminute="0"+Strminute;
		    }
		    
		    int second=ca.get(Calendar.SECOND);//��    
		    String Strsecond=String.valueOf(second);
		    if(second<10){
		    	Strsecond="0"+Strsecond;
		    }
		    
		    String CurrentDateStr=Strhour+Strminute+Strsecond;
		    return CurrentDateStr;
		}
		/**
		 * ��ȡ��ʱ���ַ�����ʱ��� ����Ϊ��λ,  ʱ���ַ�����ʽΪ20091202204506
		 * @param BeginTime ��ʼʱ��
		 * @param EndTime ����ʱ��
		 * 
		 */
		public static long GetTimeInSecond(String BeginTime,String EndTime){
			String beginTime = BeginTime.substring(0,4)+"-"+BeginTime.substring(4,6)+"-"+BeginTime.substring(6,8)+" "+BeginTime.substring(8,10)+":"+BeginTime.substring(10,12)+":"+BeginTime.substring(12,14);
			String endTime = EndTime.substring(0,4)+"-"+EndTime.substring(4,6)+"-"+EndTime.substring(6,8)+" "+EndTime.substring(8,10)+":"+EndTime.substring(10,12)+":"+EndTime.substring(12,14);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long times = 0;
			try
			{
				Date d1 = df.parse(endTime);
				Date d2 = df.parse(beginTime);
				long diff = d1.getTime() - d2.getTime();
				times = diff / 1000;
			}
			catch (Exception e) {}
			return times;
		}
		
		/**
		 * ��ȡ��ǰ�����е�����
		 * @param yyyymm
		 * @return
		 * @throws ParseException
		 */
		public static int getDaysOfMonth(String strDate) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			Calendar calendar = new GregorianCalendar();
			Date date;
			try {
				date = sdf.parse(strDate);
				calendar.setTime(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			return day;

		}

	/**
	 * jspҳ��get��ʽ����(��html�ؼ� = "xxx.action?brNo="+brNo����ʽ)���������ַ�ת�� ---��jsp�����Ĳ���������encodeURI
	 * �ж��Ƿ���websphere ��������������ǣ������ת��
	 * @param orgnl_str
	 * @return
	 */
	public static String Chinese_CodeChange(String orgnl_str) {
		String changed_str=orgnl_str;
		Properties props=System.getProperties();
		String osName = props.getProperty("os.name");
		System.out.println("����ϵͳ="+osName);
		String webcontainer=getWebContainer();
		System.out.println("web����="+webcontainer);
		if(osName.toLowerCase().indexOf("windows")>=0){
			if(webcontainer.indexOf("WebSphere")<0){
				try {
					changed_str=new String(orgnl_str.getBytes("iso-8859-1"),"UTF-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}else{
			//changed_str=orgnl_str;
			if(webcontainer.indexOf("WebSphere")<0){
				try {
					changed_str=new String(orgnl_str.getBytes("iso-8859-1"),"UTF-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return changed_str;
	}

	/**
	 * ��ȡWeb���������ͣ��������м��������(��Tomcat��WebLogic��WebSphere)
	 * @return
	 */
	public static String getWebContainer(){
		String serverName = null;
		if (ServerDetector.isWebLogic()) { //
			serverName = "WebLogic";
			//System.out.println("WebLogic");
		} else if (ServerDetector.isTomcat()) {//
			serverName = "Tomcat";
			//System.out.println("Tomcat");
		} else if (ServerDetector.isWebSphere()) { //
			serverName = "WebSphere";
			//System.out.println("WebSphere");
		} else if (ServerDetector.isSupportsComet()) { //
			serverName = "SupportsComet";
			//System.out.println("SupportsComet");
		} else if (ServerDetector.isResin()) { //
			serverName = "Resin";
			//System.out.println("Resin");
		} else if (ServerDetector.isOC4J()) { //
			serverName = "OC4J";
			//System.out.println("OC4J");
		} else if (ServerDetector.isJOnAS()) { //
			serverName = "JOnAS";
			//System.out.println("JOnAS");
		} else if (ServerDetector.isJetty()) { //
			serverName = "Jetty";
			//System.out.println("Jetty");
		} else if (ServerDetector.isJBoss()) { //
			serverName = "JBoss";
			//System.out.println("JBoss");
		} else if (ServerDetector.isGeronimo()) { //
			serverName = "Geronimo";
			//System.out.println("Geronimo");
		} else if (ServerDetector.isGlassfish()) { //
			serverName = "Glassfish";
			//System.out.println("Glassfish");
		} else if (ServerDetector.isGlassfish2()) { //
			serverName = "Glassfish2";
			//System.out.println("Glassfish2");
		} else if (ServerDetector.isGlassfish3()) { //
			serverName = "Glassfish3";
			//System.out.println("Glassfish3");
		}
		//System.out.println(serverName);
		return serverName;

	}
	
	    public static void main(String[] args) {
	    	System.out.println(GetTimeInSecond("20111321","20111321"));
		}
}
