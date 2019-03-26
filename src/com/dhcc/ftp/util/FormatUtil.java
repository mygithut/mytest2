package com.dhcc.ftp.util;

import java.text.DecimalFormat;

/**
 * <p>
 * Title: FormatUtil
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
 * @date Jul 6, 2011 2:38:15 PM
 * 
 * @version 1.0
 */
public class FormatUtil{
	
	public static String formatDoubleE(Double value){
		  if(value==null)
			  return "";
		  DecimalFormat df = new DecimalFormat("0.00");
		  return df.format(value.doubleValue());
	}
	
	public static String formatDoubleE(double value){
		  DecimalFormat df = new DecimalFormat("0.00");
		  return df.format(value);
	}
	
	public static String FormatMoney(String d) {
		if(d==null || "".equals(d))
			return "";
		try{
			if(d.indexOf("E") < 0){
				return toMoney(d);
			}else{
				return toMoney(conversion(d));
			}
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
			
		
	}
	//比率*100，保留两位小数
	public static String FormatRate(String d) {
		if(d==null || "".equals(d))
			return "";
		try{
			String s="";
			Double r=null;
			DecimalFormat t = new DecimalFormat( "###,###,##0.00 "); 
			r=Double.parseDouble(d)*100;
			if(r!=null) s=  t.format(r); 
			return s+"%";
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	
	public static String toMoney(Double d) {
		if(d==null || "".equals(d))
			return "";
		String s="";
		try{
			DecimalFormat t = new DecimalFormat( "###,###,##0.00 "); 
			s=  t.format(d); 
		}catch(Exception e){
			e.printStackTrace();
			return s;
		}
		return s;
	}
	
	public static String toMoney(String d) {
		if(d==null || "".equals(d))
			return "";
		String s="";
		Double r=null;
		try{
			DecimalFormat t = new DecimalFormat( "###,###,##0.00 "); 
			r=Double.parseDouble(d);
			if(r!=null)
				s=  t.format(r); 
		}catch(Exception e){
			e.printStackTrace();
			return s;
		}
		return s;
	}
	
	public static double conversion(String str)
	{
		String[] s = str.split("E");
		double num1 = Double.parseDouble(s[0]);
		double num2 = Double .parseDouble(s[1]);
		return num1 * Math.pow(10d, num2);
	}
	
	
	
	public static void main(String[] args) {
		System.out.println(toMoney(conversion("1.932914397E8")));
		System.out.println("1.9329143978E".indexOf("E"));
		
		System.out.println(FormatMoney("0.12313"));
	} 
}
