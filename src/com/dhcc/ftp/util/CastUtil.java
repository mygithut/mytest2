package com.dhcc.ftp.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * <p>
 * Title:类型转换Util
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
 * @date Feb 22, 2011 4:55:14 PM
 * 
 * @version 1.0
 */
public class CastUtil {
	public static String toText(Object obj) {
		if (obj == null)
			return "";
		// if (obj instanceof Date) {
		// return DateUtil.fmtDateToYMD((Date) obj);
		// } else if (obj instanceof Number) {
		// return FormatUtil.formatNumber(((Number) obj));
		// } else if (obj instanceof Boolean) {
		// if (((Boolean) obj).booleanValue() == true)
		// return "true";
		// else
		// return "false";
		// } else {
		// return obj.toString();
		// }
		return obj.toString();
	}

	/**
	 * 
	 * Description:将一个Object对象转换为确定的类型 例如Object为String的2007-01-01 Class
	 * c为Date返回的Object实际为Date类型返回后可强制类型转换,或用于其他不需要明确类型的地方,此方法多用于反射的情况
	 * 
	 * @param val待确定类型的对象
	 * @param c目标类型的class
	 * @return
	 * @author 李瑞盛
	 * @since：2008-1-10 下午02:08:38
	 */
	public static Object castValue(Object val, Class c) {
		if (val == null)
			return null;
		if ((java.lang.String.class).equals(c))
			return CastUtil.toString(val, null);
		if ((java.lang.Integer.class).equals(c) || Integer.TYPE.equals(c))
			return CastUtil.toInteger(val, null);
		if ((java.lang.Double.class).equals(c) || Double.TYPE.equals(c))
			return CastUtil.toDouble(val, null);
		if ((java.lang.Short.class).equals(c) || Short.TYPE.equals(c))
			return CastUtil.toShort(val, null);
		if ((java.lang.Long.class).equals(c) || Long.TYPE.equals(c))
			return CastUtil.toLong(val, null);
		if ((java.lang.Float.class).equals(c) || Float.TYPE.equals(c))
			return CastUtil.toFloat(val, null);
		if ((java.lang.Byte.class).equals(c) || Byte.TYPE.equals(c))
			return CastUtil.toByte(val, null);
		if ((java.lang.Boolean.class).equals(c) || Boolean.TYPE.equals(c))
			return CastUtil.toBoolean(val, null);
		// if ((java.util.Date.class).equals(c) ||
		// (java.sql.Date.class).equals(c)) {
		// return DateUtil.fmtStrToDate(String.valueOf(val));
		// }
		return null;
	}

	/**
	 * Byte非零既真 null为假
	 * 
	 * @param be
	 * @return
	 * @author 李瑞盛
	 * @since：2008-2-1 下午01:20:37
	 */
	public static boolean toBoolean(Byte be) {
		if (be == null || be.intValue() == 0)
			return false;
		return true;
	}

	/**
	 * 
	 * true为1 false为0
	 * 
	 * @param be
	 * @return
	 * @author 李瑞盛
	 * @since：2008-2-1 下午04:38:51
	 */
	public static Byte toByte(boolean be) {
		if (be == false)
			return new Byte("0");
		return new Byte("1");
	}

	/**
	 * 强制类型转化Object为String型
	 * 
	 * @param obj
	 * @param def
	 *            出现异常返回def
	 * @return
	 */
	public static String toString(Object obj, String def) {
		if (obj == null || "null".equalsIgnoreCase(obj.toString().trim())
				|| obj.toString().trim().equals(""))
			return def;
		try {
			return String.valueOf(obj).trim();
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 强制类型转化Object为byte型
	 * 
	 * @param obj
	 * @param def
	 *            出现异常返回def
	 * @return
	 */
	public static Byte toByte(Object obj, Byte def) {
		if (obj == null)
			return def;
		try {
			return Byte.valueOf(String.valueOf(obj));
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 强制类型转化Object为Short型
	 * 
	 * @param obj
	 * @param def
	 *            出现异常返回def
	 * @return
	 */
	public static Short toShort(Object obj, Short def) {
		if (obj == null)
			return def;
		try {
			return Short.valueOf(String.valueOf(obj));
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 强制类型转化Object为int型
	 * 
	 * @param obj
	 * @param def
	 *            出现异常返回def
	 * @return
	 */
	public static int toInt(Object obj, int def) {
		if (obj == null)
			return def;
		try {
			return Integer.parseInt(String.valueOf(obj));
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 强制类型转化Object为Integer型
	 * 
	 * @param obj
	 * @param def
	 *            出现异常返回def
	 * @return
	 */
	public static Integer toInteger(Object obj, Integer def) {
		if (obj == null)
			return def;
		try {
			if (obj instanceof Number) {
				Number n = (Number) obj;
				return new Integer(n.intValue());
			}
			return Integer.valueOf(String.valueOf(obj));
		} catch (Exception e) {
			return def;
		}
	}

	public static Integer toInteger(Object obj) {
		return toInteger(obj, null);
	}

	/**
	 * 强制类型转化Object为Long型
	 * 
	 * @param obj
	 * @param def
	 *            出现异常返回def
	 * @return
	 */
	public static Long toLong(Object obj, Long def) {
		if (obj == null)
			return def;
		try {
			return Long.valueOf(String.valueOf(obj));
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 强制类型转化Object为long型
	 * 
	 * @param obj
	 * @param def
	 *            出现异常返回def
	 * @return
	 */
	public static long toLong(Object obj, long def) {
		if (obj == null)
			return def;
		try {
			return Long.valueOf(String.valueOf(obj)).longValue();
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 强制类型转化Object为float型
	 * 
	 * @param obj
	 * @param def
	 *            出现异常返回def
	 * @return
	 */
	public static Float toFloat(Object obj, Float def) {
		if (obj == null)
			return def;
		try {
			return Float.valueOf(String.valueOf(obj));
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 强制类型转化Object为Double型
	 * 
	 * @param obj
	 * @param def
	 *            出现异常返回def
	 * @return
	 */
	public static Double toDouble(Object obj, Double def) {
		if (obj == null)
			return def;
		try {
			if (obj instanceof Number) {
				Number n = (Number) obj;
				return new Double(n.doubleValue());
			}
			if (obj.getClass().equals(java.lang.String.class))
				obj = ((String) obj).trim().replaceAll(",", "");
			return Double.valueOf(String.valueOf(obj));
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 强制类型转化Object为Double型
	 * 
	 * @param obj
	 * @param def
	 *            出现异常返回def
	 * @return
	 */
	public static Double toDouble(Object obj) {
		if (obj == null)
			return null;
		try {
			if (obj instanceof Number) {
				Number n = (Number) obj;
				return new Double(n.doubleValue());
			}
			if (obj.getClass().equals(java.lang.String.class))
				obj = ((String) obj).trim().replaceAll(",", "");
			return Double.valueOf(String.valueOf(obj));
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 强制类型转化Object为double型
	 * 
	 * @param obj
	 * @param def
	 *            出现异常返回def
	 * @return
	 */
	public static double toDouble(Object obj, double def) {
		if (obj == null)
			return def;
		try {
			if (obj.getClass().equals(java.lang.String.class))
				obj = ((String) obj).trim().replaceAll(",", "");
			return Double.valueOf(String.valueOf(obj)).doubleValue();
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 强制类型转化Object为double型
	 * 
	 * @param obj
	 * @param def
	 *            出现异常返回def
	 * @return
	 */
	public static double toDouble(String obj, double def) {
		if (obj == null)
			return def;
		try {
			obj = obj.replaceAll(",", "");
			return Double.valueOf(String.valueOf(obj)).doubleValue();
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 强制类型转化Object为Boolean型
	 * 
	 * @param obj
	 * @param def
	 *            出现异常返回def
	 * @return
	 */
	public static Boolean toBoolean(Object obj, Boolean def) {
		if (obj == null)
			return def;
		try {
			return Boolean.valueOf(String.valueOf(obj));
		} catch (Exception e) {
			return def;
		}
	}

	/**
	 * 
	 * Description:Object转化为String
	 * 
	 * @param obj
	 * @return
	 * @author 李瑞盛
	 * @since：2007-7-9 下午06:19:03
	 */
	public static String trimNull(Object obj) {
		if (obj == null || "null".equalsIgnoreCase(obj.toString()))
			return "";
		return obj.toString().trim();
	}

	public static String trimNullIsNumber(Object obj, String dev) {
		if (obj == null || "null".equalsIgnoreCase(obj.toString()) || "".equals(obj.toString()))
			return dev;
		return obj.toString().trim();
	}

	public static String trimNullOrBlank(Object obj) {
		if (obj == null || "null".equalsIgnoreCase(obj.toString()) || "".equals(obj.toString()))
			return "0";
		return obj.toString().trim();
	}

	public static String trimNull(Object obj, String dev) {
		if (obj == null || "null".equalsIgnoreCase(obj.toString()))
			return dev;
		return obj.toString().trim();
	}

	public static String repalceNull(String obj, String dev) {
		if (obj == null || "null".equalsIgnoreCase(obj.toString())
				|| "".equals(obj))
			return dev;
		return obj;
	}

	public static boolean isEmpty(Object obj) {
		return obj == null || "".equals(obj.toString().trim());
	}

	public static String toTextForHtml(Object obj) {
		if (isEmpty(obj) || "null".equalsIgnoreCase(obj.toString()))
			return "&nbsp;";
		return toString(obj, "&nbsp;").replaceAll("&", "&amp;").replaceAll("<",
				"&lt;").replaceAll(">", "&gt;").replaceAll("\"", "&quot;")
				.replaceAll("'", "&#146;").replaceAll("\n", "<br>").replaceAll(
						"\r", "").replaceAll("  ", " &nbsp;");
	}

	public static String firstCharToLower(String str) {
		if (str == null || str.trim().equals(""))
			return str;
		return (str.charAt(0) + "").toLowerCase() + str.substring(1);
	}

	public static boolean toBoolean(Integer inte) {
		return !(inte == null || inte.intValue() == 0);
	}

	public static void main(String[] args) {
		System.out.println(toInteger(new Double(1)));
	}

	public static String firstCharToUpper(String str) {
		if (str == null || str.trim().equals(""))
			return str;
		return (str.charAt(0) + "").toUpperCase() + str.substring(1);
	}

	public static boolean isGBK(String s) {
		if (s == null)
			return true;
		try {
			return new String(s.getBytes("GB2312")).equals(s);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 将ISO-8859-1转化为GBK
	 * 
	 * @param para
	 * @return
	 */
	public static String isoToGbk(String para) {
		try {
			return new String(para.getBytes("ISO-8859-1"), "GBK");
		} catch (UnsupportedEncodingException e) {
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	public static String toGBK(String s) {
		if (isGBK(s))
			return s;
		else
			return isoToGbk(s);
	}

	public static String isoToUTF8(String para) {
		try {
			return new String(para.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		} catch (Exception e) {
			return "";
		}
	}

	public static boolean isEmpty(String s) {
		return s == null || s.trim().equals("");
	}

	/**
	 * 
	 * Description:将Double类型转换为BigDecimal类型
	 * 
	 * @param d
	 * @return
	 * @author 李瑞盛
	 * @since：Aug 24, 2008 12:45:47 PM
	 */
	public static BigDecimal toBigDecimal(Double d) {
		return d == null ? null : new BigDecimal(d.doubleValue()).setScale(3,
				BigDecimal.ROUND_HALF_EVEN);
	}

	/**
	 * Description:
	 * 
	 * @param d
	 * @return
	 */
	public static Integer tozaynCheck(String obj) {
		if (obj != null && !"".equals(obj) && "true".equals(obj.trim()))
			return Integer.valueOf(1);
		return Integer.valueOf(0);
	}

	/**
	 * Description:
	 * 
	 * @param d
	 * @return
	 */
	public static String CastDouble(String obj, String frt) {
		String s = trimNull(obj);
		if (s == null || "".equals(s))
			return s;
		if (frt == null || "".equals(frt))
			frt = "0.00";
		DecimalFormat df = new DecimalFormat(frt);
		return df.format(s);
	}

	/**
	 * Description:
	 * 
	 * @param d
	 * @return
	 */
	public static String CastDouble(Double obj, String frt) {
		if (obj == null || "null".equalsIgnoreCase(obj.toString()))
			return "";
		if (frt == null || "".equals(frt))
			frt = "0.00";
		DecimalFormat df = new DecimalFormat(frt);
		return df.format(obj);
	}

	/**
	 * Description:
	 * 
	 * @param d
	 * @return
	 */
	public static String CastDouble(Double obj) {
		return CastDouble(obj, null);
	}

	/**
	 * 
	 * Description:将Double保留两位小数
	 * 
	 * @param d
	 * @return
	 * @author 李瑞盛
	 * @since：Aug 24, 2008 12:45:47 PM
	 */
	public static double doubleSet2(Double d) {
		if (d == null || d.isNaN())
			return 0;
		BigDecimal bg = new BigDecimal(d);
		double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return f1;
	}

	/**
	 * 
	 * Description:将Double保留两位小数
	 * 
	 * @param d
	 */
	public static double formatDouble2(Double d) {
		double f1 = d;
		if (!d.equals(Double.NaN)) {
			BigDecimal bg = new BigDecimal(d);
			f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}
		return f1;
	}

	/**
	 * 判断是字符串否为年份
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isYear(String value) {
		try {
			int year = Integer.parseInt(value);
			if (year >= 1900 && year <= 2100)
				return true;
			else
				return false;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static String formatDoubleE(Double value) {
		if (value == null)
			return "";
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(value.doubleValue());
	}

	public static String formatDoubleE(double value) {
		if (value == 0)
			return "";
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(value);
	}

	/**
	 * 转化Double为String，保留一位小数，不用科学计数法
	 * 
	 * @param value
	 * @return
	 */
	public static String formatDouble1(Double value) {
		if (value == null)
			return "";
		DecimalFormat df = new DecimalFormat("0.0");
		return df.format(value.doubleValue());
	}

	public static String formatDouble1(double value) {
		if (value == 0)
			return "";
		DecimalFormat df = new DecimalFormat("0.0");
		return df.format(value);
	}

	/**
	 * 
	 * Description:将Double保留两位小数每三位用逗号隔开
	 * 
	 * @param d
	 * 
	 */
	public static String formatNumber1(double num) {
		String fn = String.valueOf(num);
		String number = String.valueOf(num).replaceAll(",", ""); // 去掉所有逗号
		java.text.DecimalFormat df = new java.text.DecimalFormat(
				"##,###,##0.00");
		if (!new Double(num).equals(Double.NaN)) {
			fn = df.format(Double.valueOf(number));
		}
		return fn;
	}

	/**
	 * Description:
	 * 
	 * @param d
	 * @return
	 */
	public static String CastModelType(Integer id, String obj) {
		if (obj == null || "null".equalsIgnoreCase(obj))
			obj = "";
		if (id == null || "null".equalsIgnoreCase(id.toString())
				|| "".equals(id.toString()))
			return obj;
		switch (id.intValue()) {
		case 0:
			obj = "市场法";
			break;
		case 1:
			obj = "成本法";
			break;
		case 2:
			obj = "收益法";
		}
		return obj;
	}
	/**
	 * Description:
	 * 
	 * @param d
	 * @return
	 */
	public static String CastRoleLvl(String id, String obj) {
		if (obj == null || "null".equalsIgnoreCase(obj))
			obj = "";
		if (id == null || "null".equalsIgnoreCase(id.toString())
				|| "".equals(id.toString()))
			return obj;
		if(id.equals("4")) {
			obj = "高级";
		}else if(id.equals("3")) {
			obj = "次高级";
		}else if(id.equals("2")) {
			obj = "中级";
		}else if(id.equals("1")) {
			obj = "低级";
		}
		return obj;
	}
	
}
