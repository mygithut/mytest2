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
 * Company: ��������ɷݹ�˾������ҵ��
 * </p>
 * 
 * @author ����ʢ
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
 * Company: ��������ɷݹ�˾������ҵ��
 * </p>
 * 
 * @author ����ʢ
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
    
    public static FtpItem FI_WHOLE_A = new FtpItem("0010001","���ʲ�");
    public static FtpItem FI_WHOLE_B= new FtpItem("0020001","�ܸ�ծ");
    public static FtpItem FI_WHOLE_ZZC= new FtpItem("0000001","��֧��");
    public static FtpItem FI_WHOLE_XJJZYHCK= new FtpItem("0010002","�ֽ������д��");
    public static FtpItem FI_WHOLE_GNPZCX= new FtpItem("0010003","����ƾ֤����Ͷ��ڴ��׼����");
    
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
