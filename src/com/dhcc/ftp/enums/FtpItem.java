package com.dhcc.ftp.enums;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * <p>
 * Title: FtpItem
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
 * @date Aug 12, 2011 3:10:36 PM
 * 
 * @version 1.0
 */
/**
 * <p>
 * Title: FtpItem
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
 * @date Aug 12, 2011 3:12:14 PM
 * 
 * @version 1.0
 */
public class FtpItem implements Serializable{
	 
	private static final long serialVersionUID = 1L;
    private static TreeMap map = new TreeMap();
    private String code;
    private String name;
    
    public static FtpItem FI_WHOLE_A = new FtpItem("0010001","总资产");
    public static FtpItem FI_WHOLE_B= new FtpItem("0020001","总负债");
    public static FtpItem FI_WHOLE_ZZC= new FtpItem("0000001","总支出");
    public static FtpItem FI_WHOLE_XJJZYHCK= new FtpItem("0010002","现金及在央行存款");
    public static FtpItem FI_WHOLE_GNPZCX= new FtpItem("0010003","国内凭证储蓄和定期存款准备金");
    
    private FtpItem(String code, String name){
   	 	this.code = code;
        this.name = name;
        map.put(this.code, this);
     }
    public static String getItemName(String code) {
		switch(Integer.valueOf(code)){
			case 0010001: return FtpItem.FI_WHOLE_A.name;
			case 0020001: return FtpItem.FI_WHOLE_B.name;
			case 0000001: return FtpItem.FI_WHOLE_ZZC.name;
			case 0010002: return FtpItem.FI_WHOLE_XJJZYHCK.name;
			case 0010003: return FtpItem.FI_WHOLE_GNPZCX.name;
		}
		return "";
	}
    


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public static String getName(String code) {
		return ((FtpItem)map.get(code)).getName();
	}
	
	
	public static FtpItem getFtpItem(String kind) {
		return (FtpItem)map.get(kind);
	}
}
