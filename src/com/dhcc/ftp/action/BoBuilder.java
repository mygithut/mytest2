package com.dhcc.ftp.action;

import java.util.HashMap;

import com.dhcc.ftp.bo.BrInfoBo;
import com.dhcc.ftp.bo.FtpCreditRatingBO;
import com.dhcc.ftp.bo.FtpFinacialBO;
import com.dhcc.ftp.bo.FtpPoolBO;
import com.dhcc.ftp.bo.FtpPoolInfoBO;
import com.dhcc.ftp.bo.FtpProductMethodRelBO;
import com.dhcc.ftp.bo.FtpResultBO;
import com.dhcc.ftp.bo.FtpShiborBO;
import com.dhcc.ftp.bo.FtpStockBO;
import com.dhcc.ftp.bo.FtpSylqxBO;
import com.dhcc.ftp.bo.FtpSystemInitialBO;
import com.dhcc.ftp.bo.QueryPageBO;
import com.dhcc.ftp.bo.ReportBO;
import com.dhcc.ftp.bo.ReportBbBO;
import com.dhcc.ftp.bo.ReportSfbBO;
import com.dhcc.ftp.bo.RoleListBo;
import com.dhcc.ftp.bo.SYLQXSJCLTZBO;
import com.dhcc.ftp.bo.TelMstBO;
import com.dhcc.ftp.bo.UL03BO;
import com.dhcc.ftp.bo.UL04BO;
import com.dhcc.ftp.bo.UL06BO;
import com.dhcc.ftp.bo.UL07BO;
import com.dhcc.ftp.bo.UserBo;
import com.dhcc.ftp.common.PropertiesReader;


/**
 * <p>
 * Title: BoBuilder
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Company: 东华软件股份公司金融事业部
 * </p>
 * 
 * @rename By 李瑞盛
 * 
 * @date Feb 22, 2011 21:28:31 PM
 * 
 * @version 1.0
 */
public abstract class BoBuilder extends BaseAction{
	
	private static final long serialVersionUID = 4070681627461851267L;
	protected BrInfoBo brInfoBo = (BrInfoBo) PropertiesReader.getPorperty("brInfoBo");
	protected TelMstBO telMstBO = (TelMstBO) PropertiesReader.getPorperty("telMstBO");
	protected RoleListBo roleListBo = (RoleListBo) PropertiesReader.getPorperty("roleListBo");
	protected UserBo userBo = (UserBo) PropertiesReader.getPorperty("userBo");
	protected QueryPageBO queryPageBO = (QueryPageBO) PropertiesReader.getPorperty("queryPageBO");
	protected FtpCreditRatingBO ftpCreditRatingBO = (FtpCreditRatingBO) PropertiesReader.getPorperty("ftpCreditRatingBO");
	protected FtpPoolBO ftpPoolBO = (FtpPoolBO) PropertiesReader.getPorperty("ftpPoolBO");
	protected FtpStockBO ftpStockBO = (FtpStockBO) PropertiesReader.getPorperty("ftpStockBO");
	protected FtpShiborBO ftpShiborBO = (FtpShiborBO) PropertiesReader.getPorperty("ftpShiborBO");
	protected FtpResultBO ftpResultBO = (FtpResultBO) PropertiesReader.getPorperty("ftpResultBO");
	protected FtpSylqxBO ftpSylqxBO = (FtpSylqxBO) PropertiesReader.getPorperty("ftpSylqxBO");
	protected FtpFinacialBO ftpFinacialBO = (FtpFinacialBO) PropertiesReader.getPorperty("ftpFinacialBO");
	protected UL03BO uL03BO = (UL03BO) PropertiesReader.getPorperty("uL03BO");
	protected UL04BO uL04BO = (UL04BO) PropertiesReader.getPorperty("uL04BO");
	protected UL06BO uL06BO = (UL06BO) PropertiesReader.getPorperty("uL06BO");
	protected UL07BO uL07BO = (UL07BO) PropertiesReader.getPorperty("uL07BO");
	protected SYLQXSJCLTZBO sYLQXSJCLTZBO = (SYLQXSJCLTZBO) PropertiesReader.getPorperty("sYLQXSJCLTZBO");
	protected ReportBO reportBO = (ReportBO) PropertiesReader.getPorperty("reportBO");
	protected ReportBbBO reportBbBO = (ReportBbBO) PropertiesReader.getPorperty("reportBbBO");
	protected ReportSfbBO reportSfbBO = (ReportSfbBO) PropertiesReader.getPorperty("reportSfbBO");
	protected FtpSystemInitialBO ftpSystemInitialBO = (FtpSystemInitialBO) PropertiesReader.getPorperty("ftpSystemInitialBO");
	protected FtpPoolInfoBO ftpPoolInfoBO = (FtpPoolInfoBO) PropertiesReader.getPorperty("ftpPoolInfoBO");
	protected FtpProductMethodRelBO ftpProductMethodRelBO = (FtpProductMethodRelBO) PropertiesReader.getPorperty("ftpProductMethodRelBO");
	protected static final HashMap<String, String> termTypeMap = new HashMap<String, String>();
	private int pageNum;
	
	static{
		initTermTypeMap();
	}
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	static void initTermTypeMap() {
		termTypeMap.put("1", "5");
		termTypeMap.put("2", "7");
		termTypeMap.put("3", "10");
		termTypeMap.put("4", "15");
		termTypeMap.put("5", "30");
		termTypeMap.put("6", "90");
		termTypeMap.put("7", "182");
		termTypeMap.put("8", "365");
	}	
	
}
