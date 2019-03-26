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
 * Company: 东华软件股份公司金融事业部
 * </p>
 * 
 * @author 李瑞盛
 * 
 * @date Mar 4, 2011 1:57:56 PM
 * 
 * @version 1.0
 */
public class DateUtil {
	/**
	 * 将字符串转化为DATE
	 * 
	 * @param dtFormat
	 *            格式yyyy-MM-dd HH:mm:ss 或 yyyy-MM-dd或 yyyy-M-dd或 yyyy-M-d或
	 *            yyyy-MM-d或 yyyy-M-dd
	 * @param def
	 *            如果格式化失败返回null
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
	 * Description:格式化日期,如果格式化失败返回def
	 * 
	 * @param dtFormat
	 * @param def
	 * @return
	 * @author 李瑞盛
	 * @since：2008-2-15 下午05:01:37
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
	 * 返回当日短日期型
	 * 
	 * @return
	 * @author 李瑞盛
	 * @since：2008-2-15 下午05:03:13
	 */
	public static Date getToDay() {
		return toShortDate(new Date());
	}

	/**
	 * 
	 * Description:格式化日期,String字符串转化为Date
	 * 
	 * @param date
	 * @param dtFormat
	 *            例如:yyyy-MM-dd HH:mm:ss yyyyMMdd
	 * @return
	 * @author 李瑞盛
	 * @since：2007-7-10 上午11:24:00
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
	 * Description:按指定格式 格式化日期
	 * 
	 * @param date
	 * @param dtFormat
	 * @return
	 * @author 李瑞盛
	 * @since：2007-12-10 上午11:25:07
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

	// 例：n=-2即获取前天的日期
	public static String getDate(int n) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, n);
		return sdf.format(cal.getTime());
	}

	/**
	 * 获取当前数据库系统日期(8位长整型)，是从数据库系统参数表中查询获得
	 * 
	 * @return (long) 当前数据库系统时间 格式为 "年月日" ，20091202
	 */
//	public static long GetDBSysDate() {
//		long SysDate = 0;
//		String sql = "from ComSysParm order by sysDate DESC";
//		DaoFactory mydao = new DaoFactory();
//		List list = mydao.query(sql, null);
//		ComSysParm com_sys_parm = new ComSysParm();
//		if (list.size() == 0) {
//			System.out.println("系统日期获取出错，请检查数据库公共参数表com_sys_parm！");
//			return 0;
//		}
//		com_sys_parm = (ComSysParm) list.get(0);
//		SysDate = com_sys_parm.getSysDate();
//		return SysDate;
//	}

	/**
	 * 
	 * Description:只保留日期中的年月日
	 * 
	 * @param date
	 * @return
	 * @author 李瑞盛
	 * @since：2007-12-10 上午11:25:50
	 */
	public static Date toShortDate(Date date) {
		String strD = fmtDateToStr(date, "yyyy-MM-dd");
		return fmtStrToDate(strD);
	}

	/**
	 * 
	 * Description:只保留日期中的年月日
	 * 
	 * @param date格式要求yyyy
	 *            -MM-dd……………………
	 * @return
	 * @author 李瑞盛
	 * @since：2007-12-10 上午11:26:12
	 */
	public static Date toShortDate(String date) {
		if (date != null && date.length() >= 10) {
			return fmtStrToDate(date.substring(0, 10));
		} else
			return fmtStrToDate(date);
	}

	/**
	 * 求对日
	 * 
	 * @param countMonth
	 *            :月份的个数(几个月)
	 * @param flag
	 *            :true 求前countMonth个月的对日:false 求下countMonth个月的对日
	 * @return
	 */
	public static Date getCounterglow(int countMonth, boolean before) {
		Calendar ca = Calendar.getInstance();
		return getCounterglow(ca.getTime(), before ? -countMonth : countMonth);
	}

	/**
	 * 
	 * Description: 求对日 加月用+ 减月用-
	 * 
	 * @param date
	 * @param countMonth
	 * @return
	 * @since：2007-12-13 下午03:16:47
	 */
	public static Date getCounterglow(Date date, int num) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(Calendar.MONTH, num);
		return ca.getTime();
	}

	/**
	 * 
	 * Description:加一天
	 * 
	 * @param date
	 * @return
	 * @author 李瑞盛
	 * @since：2007-12-13 下午02:57:38
	 */
	public static Date addDay(Date date) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		cd.add(Calendar.DAY_OF_YEAR, 1);
		return cd.getTime();
	}

	/**
	 * 
	 * Description:判断一个日期是否为工作日(非周六周日)
	 * 
	 * @param date
	 * @return
	 * @author 李瑞盛
	 * @since：2007-12-13 下午03:01:35
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
	 * Description:取一个月的最后一天
	 * 
	 * @param date1
	 * @return
	 * @author 李瑞盛
	 * @since：2007-12-13 下午03:28:21
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
	 * 求开始截至日期之间的天数差.
	 * 
	 * @param d1
	 *            开始日期
	 * @param d2
	 *            截至日期
	 * @return 返回相差天数
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
		return "周" + toChNumber(cl.get(Calendar.DAY_OF_WEEK) - 1);
	}

	/**
	 * 将数字转为中文。 "0123456789"->"〇一二三四五六七八九"
	 * 
	 * @param num
	 *            长度为1,'0'-'9'的字符串
	 * @return
	 */
	private static String toChNumber(int num) {
		final String str = "〇一二三四五六七八九";
		return str.substring(num, num + 1);
	}

	/**
	 * 
	 * Description:指定日期加或减days天
	 * 
	 * @param date1日期
	 * @param days天数
	 * @return
	 * @author 李瑞盛
	 * @since：2007-12-17 下午03:47:12
	 */
	public static Date addDay(Date date1, int days) {
		Calendar date = Calendar.getInstance();
		date.setTime(date1);
		date.add(Calendar.DAY_OF_YEAR, days);
		return toShortDate(date.getTime());
	}

	/**
	 * 
	 * Description:指定日期加或减months月
	 * 
	 * @param date1
	 * @param months
	 * @return
	 * @author 李瑞盛
	 * @since：2008-3-5 下午05:17:26
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
			cDate = df.parse(datetimeStr); // 时间格式化
		} catch (ParseException e) {
		}
		Date newTime = new Date(cDate.getTime());
		CurCalendar.setTime(newTime); // 设置新的时间
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

	//是否为闰年
	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0) && ((year % 100 != 0) | (year % 400 == 0))) {
			return true;
		} else {
			return false;
		}
	}/**
	 * 获取当前系统时间
	 * @return  (String)  当前系统时间 格式为 "年月日"，如 "20091202"
	 */
	public static String getCurrentDay(){
		
		Calendar ca = Calendar.getInstance();
	    int year = ca.get(Calendar.YEAR);//获取年份
	    String Stryear=String.valueOf(year);
	    
	    int month=ca.get(Calendar.MONTH)+1;//获取月份  月份系统从0开始算起的
	    String Strmonth=String.valueOf(month);
	    if(month<10){
	    	Strmonth="0"+Strmonth;
	    }
	    
	    int day=ca.get(Calendar.DATE);//获取日
	    String Strday=String.valueOf(day);
	    if(day<10){
	    	Strday="0"+Strday;
	    }
	    
	    String currentDay=Stryear+Strmonth+Strday;
	    return currentDay;
	}
	/**
	 * 获取当前系统时间
	 * @return  (String)  当前系统时间 格式为 "时分秒"，如 "120154"
	 */
	public static String getCurrentTime(){
		
		Calendar ca = Calendar.getInstance();
		int hour=ca.get(Calendar.HOUR_OF_DAY);//小时
	    String Strhour=String.valueOf(hour);
	    if(hour<10){
	    	Strhour="0"+Strhour;
	    }
	    
	    int minute=ca.get(Calendar.MINUTE);//分
	    String Strminute=String.valueOf(minute);
	    if(minute<10){
	    	Strminute="0"+Strminute;
	    }
	    
	    int second=ca.get(Calendar.SECOND);//秒    
	    String Strsecond=String.valueOf(second);
	    if(second<10){
	    	Strsecond="0"+Strsecond;
	    }
	    
	    String CurrentTime=Strhour+Strminute+Strsecond;
	    return CurrentTime;
	}
}