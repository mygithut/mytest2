package com.dhcc.ftp.bo;

import com.dhcc.ftp.common.PropertiesReader;
import com.dhcc.ftp.dao.BrInfoDAO;
import com.dhcc.ftp.dao.CheckIdNumDAO;
import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.dao.FtpCreditRatingDAO;
import com.dhcc.ftp.dao.FtpFinacialDAO;
import com.dhcc.ftp.dao.FtpPoolDAO;
import com.dhcc.ftp.dao.FtpResultDAO;
import com.dhcc.ftp.dao.FtpShiborDAO;
import com.dhcc.ftp.dao.FtpStockDAO;
import com.dhcc.ftp.dao.FtpSylqxDAO;
import com.dhcc.ftp.dao.RoleListDAO;
import com.dhcc.ftp.dao.TelMstDAO;
import com.dhcc.ftp.dao.UL04DAO;
import com.dhcc.ftp.dao.UL06DAO;
import com.dhcc.ftp.dao.UL07DAO;
import com.dhcc.ftp.dao.UserDAO;
public class BaseBo {
	protected DaoFactory daoFactory = (DaoFactory) PropertiesReader.getPorperty("daoFactory");
	protected BrInfoDAO brInfoDAO = (BrInfoDAO) PropertiesReader.getPorperty("brInfoDAO");
	protected UserDAO userDAO = (UserDAO) PropertiesReader.getPorperty("userDAO");
	protected TelMstDAO telMstDAO = (TelMstDAO) PropertiesReader.getPorperty("telMstDAO");
	protected RoleListDAO roleListDAO = (RoleListDAO) PropertiesReader.getPorperty("roleListDAO");
	protected CheckIdNumDAO checkIdNumDAO = (CheckIdNumDAO) PropertiesReader.getPorperty("checkIdNumDAO");
	protected FtpPoolDAO ftpPoolDAO = (FtpPoolDAO) PropertiesReader.getPorperty("ftpPoolDAO");
	protected FtpStockDAO ftpStockDAO = (FtpStockDAO) PropertiesReader.getPorperty("ftpStockDAO");
	protected FtpShiborDAO ftpShiborDAO = (FtpShiborDAO) PropertiesReader.getPorperty("ftpShiborDAO");
	protected FtpResultDAO ftpResultDAO = (FtpResultDAO) PropertiesReader.getPorperty("ftpResultDAO");
	protected FtpSylqxDAO ftpSylqxDAO = (FtpSylqxDAO) PropertiesReader.getPorperty("ftpSylqxDAO");
	protected FtpCreditRatingDAO ftpCreditRatingDAO = (FtpCreditRatingDAO) PropertiesReader.getPorperty("ftpCreditRatingDAO");
	protected FtpFinacialDAO ftpFinacialDAO = (FtpFinacialDAO) PropertiesReader.getPorperty("ftpFinacialDAO");
	protected UL04DAO uL04DAO = (UL04DAO) PropertiesReader.getPorperty("uL04DAO");
	protected UL06DAO uL06DAO = (UL06DAO) PropertiesReader.getPorperty("uL06DAO");
	protected UL07DAO uL07DAO = (UL07DAO) PropertiesReader.getPorperty("uL07DAO");
}
