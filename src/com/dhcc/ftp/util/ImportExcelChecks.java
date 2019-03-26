package com.dhcc.ftp.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/** 
 * @author Sunhongyu
 *
 */
public class ImportExcelChecks {
	
	//检查是否为整数
	public static boolean isIntNumberic(String s){ 
                if (Pattern.matches("^{0,1}\\d+$", s))   { 
                     return true; 
                } 
                //if   (Pattern.matches( "^0[x|X][\\da-eA-E]+$ ",   s))   { 
                  //      return   true; 
                //} 

                return false; 
        }
	//检查是否为浮点数或整数
	public static boolean isNumberic(String s){
		
		if (Pattern.matches("-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$", s)||Pattern.matches("^{0,1}\\d+$", s)) {
	        return true; 
	    } 
	    return false;  
	}
	//检查是否为日期
	public static boolean isDate(String s){ 
		s = s.replace("/", "-");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
		try { 
			sdf.setLenient(false); 
			sdf.parse(s); 
			return true; 
		} catch (Exception e){
			return false; 
		}  
    }
	//转化日期
	public static Date changeDate(String s){ 
		//将日期从2011-1-1转为2011-01-01
		s = s.replace("/", "-");
		String s1="";
		String s2="";
		String s3="";
    	s1 = s.substring(0,4);
    	s2 = s.substring(5,7);
    	s3 = s.substring(s.lastIndexOf("-")+1,s.length());
    	if (s2.indexOf("-")==s2.length()-1){
    		s2 = "0"+s2.substring(0,1);
    	}else{
    	    s2 = s2.substring(0,2);
    	}
    	if ((s.length()-s.lastIndexOf("-"))==2){
    		s3 = "0"+s3;
    	}
    	return Date.valueOf(s1+"-"+s2+"-"+s3); 
   }
    
}