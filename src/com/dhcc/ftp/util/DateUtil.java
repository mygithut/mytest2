package com.dhcc.ftp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * Title: DateUtil
 * </p>
 * 
 * <p>
 * Description: TODO
 * </p>
 * 
 * <p>
 * Company: ��������ɷݹ�˾������ҵ��
 * </p>
 * 
 * @author ����ʢ
 * 
 * @date Mar 4, 2011 1:57:56 PM
 * 
 * @version 1.0
 */
public class DateUtil {
	/**
	 * ���ַ���ת��ΪDATE
	 * 
	 * @param dtFormat
	 *            ��ʽyyyy-MM-dd HH:mm:ss �� yyyy-MM-dd�� yyyy-M-dd�� yyyy-M-d��
	 *            yyyy-MM-d�� yyyy-M-dd
	 * @param def
	 *            �����ʽ��ʧ�ܷ���null
	 * @return
	 */
	public static Date fmtStrToDate(String dtFormat) {
		if (dtFormat == null)
			return null;
		try {
			if (dtFormat.length() == 9 || dtFormat.length() == 8) {
				String[] dateStr = dtFormat.split("-");
				dtFormat = dateStr[0] + (dateStr[1].length() == 1 ? "-0" : "-")
						+ dateStr[1] + (dateStr[2].length() == 1 ? "-0" : "-")
						+ dateStr[2];
			}
			if (dtFormat.length() != 10 & dtFormat.length() != 19)
				return null;
			if (dtFormat.length() == 10)
				dtFormat = dtFormat + " 00:00:00";
			SimpleDateFormat dateFormat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			return dateFormat.parse(dtFormat);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * Description:��ʽ������,�����ʽ��ʧ�ܷ���def
	 * 
	 * @param dtFormat
	 * @param def
	 * @return
	 * @author ����ʢ
	 * @since��2008-2-15 ����05:01:37
	 */
	public static Date fmtStrToDate(String dtFormat, Date def) {
		Date d = fmtStrToDate(dtFormat);
		if (d == null)
			return def;
		return d;
	}

	public static Date fmtStrToYMDate(String dtFormat) {
		if (dtFormat == null)
			return null;
		int len = dtFormat.split("-").length;
		if (len > 2) {
			return fmtStrToDate(dtFormat);
		} else if (len < 2) {
			return null;
		} else {
			String[] dateStr = dtFormat.split("-");
			dtFormat = dateStr[0] + (dateStr[1].length() == 1 ? "-0" : "-")
					+ dateStr[1] + "-01";
			return fmtStrToDate(dtFormat);
		}
	}

	public static Date fmtStrToYMDate(String dtFormat, Date def) {
		Date d = fmtStrToYMDate(dtFormat);
		if (d == null)
			return def;
		return d;
	}

	/**
	 * ���ص��ն�������
	 * 
	 * @return
	 * @author ����ʢ
	 * @since��2008-2-15 ����05:03:13
	 */
	public static Date getToDay() {
		return toShortDate(new Date());
	}

	/**
	 * 
	 * Description:��ʽ������,String�ַ���ת��ΪDate
	 * 
	 * @param date
	 * @param dtFormat
	 *            ����:yyyy-MM-dd HH:mm:ss yyyyMMdd
	 * @return
	 * @author ����ʢ
	 * @since��2007-7-10 ����11:24:00
	 */
	public static String fmtDateToStr(Date date, String dtFormat) {
		if (date == null)
			return "";
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(dtFormat);
			return dateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * Description:��ָ����ʽ ��ʽ������
	 * 
	 * @param date
	 * @param dtFormat
	 * @return
	 * @author ����ʢ
	 * @since��2007-12-10 ����11:25:07
	 */
	public static Date fmtStrToDate(String date, String dtFormat) {
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(dtFormat);
			return dateFormat.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String fmtDateToYMDHM(Date date) {
		return fmtDateToStr(date, "yyyy-MM-dd HH:mm");
	}

	public static String fmtDateToYMD(Date date) {
		return fmtDateToStr(date, "yyyy-MM-dd");
	}

	public static String fmtDateToYM(Date date) {
		return fmtDateToStr(date, "yyyy-MM");
	}

	public static String fmtDateToM(Date date) {
		return fmtDateToStr(date, "MM");
	}

	public static String fmtDateToMD(Date date) {
		return fmtDateToStr(date, "MM-dd");
	}

	// ����n=-2����ȡǰ�������
	public static String getDate(int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, n);
		return sdf.format(cal.getTime());
	}

	/**
	 * ��ȡ��ǰ���ݿ�ϵͳ����(8λ������)���Ǵ����ݿ�ϵͳ�������в�ѯ���
	 * 
	 * @return (long) ��ǰ���ݿ�ϵͳʱ�� ��ʽΪ "������" ��20091202
	 */
//	public static long GetDBSysDate() {
//		long SysDate = 0;
//		String sql = "from ComSysParm order by sysDate DESC";
//		DaoFactory mydao = new DaoFactory();
//		List list = mydao.query(sql, null);
//		ComSysParm com_sys_parm = new ComSysParm();
//		if (list.size() == 0) {
//			System.out.println("ϵͳ���ڻ�ȡ�����������ݿ⹫��������com_sys_parm��");
//			return 0;
//		}
//		com_sys_parm = (ComSysParm) list.get(0);
//		SysDate = com_sys_parm.getSysDate();
//		return SysDate;
//	}

	/**
	 * 
	 * Description:ֻ���������е�������
	 * 
	 * @param date
	 * @return
	 * @author ����ʢ
	 * @since��2007-12-10 ����11:25:50
	 */
	public static Date toShortDate(Date date) {
		String strD = fmtDateToStr(date, "yyyy-MM-dd");
		return fmtStrToDate(strD);
	}

	/**
	 * 
	 * Description:ֻ���������е�������
	 * 
	 * @param date��ʽҪ��yyyy
	 *            -MM-dd����������������
	 * @return
	 * @author ����ʢ
	 * @since��2007-12-10 ����11:26:12
	 */
	public static Date toShortDate(String date) {
		if (date != null && date.length() >= 10) {
			return fmtStrToDate(date.substring(0, 10));
		} else
			return fmtStrToDate(date);
	}

	/**
	 * �����
	 * 
	 * @param countMonth
	 *            :�·ݵĸ���(������)
	 * @param flag
	 *            :true ��ǰcountMonth���µĶ���:false ����countMonth���µĶ���
	 * @return
	 */
	public static Date getCounterglow(int countMonth, boolean before) {
		Calendar ca = Calendar.getInstance();
		return getCounterglow(ca.getTime(), before ? -countMonth : countMonth);
	}

	/**
	 * 
	 * Description: ����� ������+ ������-
	 * 
	 * @param date
	 * @param countMonth
	 * @return
	 * @since��2007-12-13 ����03:16:47
	 */
	public static Date getCounterglow(Date date, int num) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MONTH, num);
		return ca.getTime();
	}

	/**
	 * 
	 * Description:��һ��
	 * 
	 * @param date
	 * @return
	 * @author ����ʢ
	 * @since��2007-12-13 ����02:57:38
	 */
	public static Date addDay(Date date) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.DAY_OF_YEAR, 1);
		return cd.getTime();
	}

	/**
	 * 
	 * Description:�ж�һ�������Ƿ�Ϊ������(����������)
	 * 
	 * @param date
	 * @return
	 * @author ����ʢ
	 * @since��2007-12-13 ����03:01:35
	 */
	public static boolean isWorkDay(Date date) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
		if (dayOfWeek != Calendar.SUNDAY || dayOfWeek != Calendar.SATURDAY)
			return false;
		return true;
	}

	/**
	 * 
	 * Description:ȡһ���µ����һ��
	 * 
	 * @param date1
	 * @return
	 * @author ����ʢ
	 * @since��2007-12-13 ����03:28:21
	 */
	public static Date getLastDayOfMonth(Date date1) {
		Calendar date = Calendar.getInstance();
		date.setTime(date1);
		date.set(Calendar.DAY_OF_MONTH, 1);
		date.add(Calendar.MONTH, 1);
		date.add(Calendar.DAY_OF_YEAR, -1);
		return toShortDate(date.getTime());
	}

	/**
	 * ��ʼ��������֮���������.
	 * 
	 * @param d1
	 *            ��ʼ����
	 * @param d2
	 *            ��������
	 * @return �����������
	 */
	public static int getDaysInterval(Date d1, Date d2) {
		if (d1 == null || d2 == null)
			return 0;
		Date[] d = new Date[2];
		d[0] = d1;
		d[1] = d2;
		Calendar[] cal = new Calendar[2];
		for (int i = 0; i < cal.length; i++) {
			cal[i] = Calendar.getInstance();
			cal[i].setTime(d[i]);
			cal[i].set(Calendar.HOUR_OF_DAY, 0);
			cal[i].set(Calendar.MINUTE, 0);
			cal[i].set(Calendar.SECOND, 0);
		}
		long m = cal[0].getTime().getTime();
		long n = cal[1].getTime().getTime();
		int ret = (int) Math.abs((m - n) / 1000 / 3600 / 24);
		return ret;
	}

	public static String getDayOfWeek(Date date) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(date);
		return "��" + toChNumber(cl.get(Calendar.DAY_OF_WEEK) - 1);
	}

	/**
	 * ������תΪ���ġ� "0123456789"->"��һ�����������߰˾�"
	 * 
	 * @param num
	 *            ����Ϊ1,'0'-'9'���ַ���
	 * @return
	 */
	private static String toChNumber(int num) {
		final String str = "��һ�����������߰˾�";
		return str.substring(num, num + 1);
	}

	/**
	 * 
	 * Description:ָ�����ڼӻ��days��
	 * 
	 * @param date1����
	 * @param days����
	 * @return
	 * @author ����ʢ
	 * @since��2007-12-17 ����03:47:12
	 */
	public static Date addDay(Date date1, int days) {
		Calendar date = Calendar.getInstance();
		date.setTime(date1);
		date.add(Calendar.DAY_OF_YEAR, days);
		return toShortDate(date.getTime());
	}

	/**
	 * 
	 * Description:ָ�����ڼӻ��months��
	 * 
	 * @param date1
	 * @param months
	 * @return
	 * @author ����ʢ
	 * @since��2008-3-5 ����05:17:26
	 */
	public static Date addMonth(Date date1, int months) {
		Calendar date = Calendar.getInstance();
		date.setTime(date1);
		date.add(Calendar.MONTH, months);
		return date.getTime();
	}

	Calendar CurCalendar = null;

	public DateUtil() {
		CurCalendar = Calendar.getInstance();
		Date time = new Date();
		CurCalendar.setTime(time);
	}

	public void ReSetCalendar() {
		Date time = new Date();
		CurCalendar.setTime(time);
	}

	public String RelativeDate(String datetimeStr, int n) {
		int iCurYear, iCurMonth, iCurDay, iCurHour;
		String TimeStr, strCurMonth, strCurDay, strCurHour;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date cDate = new Date();
		try {
			cDate = df.parse(datetimeStr); // ʱ���ʽ��
		} catch (ParseException e) {
		}
		Date newTime = new Date(cDate.getTime());
		CurCalendar.setTime(newTime); // �����µ�ʱ��
		CurCalendar.add(Calendar.DAY_OF_YEAR, n);
		iCurYear = CurCalendar.get(Calendar.YEAR);
		iCurMonth = CurCalendar.get(Calendar.MONTH) + 1;
		if (iCurMonth < 10) {
			strCurMonth = "0" + Integer.toString(iCurMonth);
		} else {
			strCurMonth = Integer.toString(iCurMonth);
		}
		iCurDay = CurCalendar.get(Calendar.DAY_OF_MONTH);
		if (iCurDay < 10) {
			strCurDay = "0" + Integer.toString(iCurDay);
		} else {
			strCurDay = Integer.toString(iCurDay);
		}
		TimeStr = Integer.toString(iCurYear) + "-" + strCurMonth + "-"
				+ strCurDay;
		ReSetCalendar();
		return TimeStr;
	}

	//�Ƿ�Ϊ����
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0) && ((year % 100 != 0) | (year % 400 == 0))) {
			return true;
		} else {
			return false;
		}
	}/**
	 * ��ȡ��ǰϵͳʱ��
	 * @return  (String)  ��ǰϵͳʱ�� ��ʽΪ "������"���� "20091202"
	 */
	public static String getCurrentDay(){
		
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
	    
	    String currentDay=Stryear+Strmonth+Strday;
	    return currentDay;
	}
	/**
	 * ��ȡ��ǰϵͳʱ��
	 * @return  (String)  ��ǰϵͳʱ�� ��ʽΪ "ʱ����"���� "120154"
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
	    
	    String CurrentTime=Strhour+Strminute+Strsecond;
	    return CurrentTime;
	}
}