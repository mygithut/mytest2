package com.dhcc.ftp.bo;

import com.dhcc.ftp.util.PageUtil;


public class FtpCreditRatingBO extends BaseBo{

	//list∑÷“≥≤È—Ø
	public PageUtil dofind(int page) {
		return ftpCreditRatingDAO.dofind(page);
	}
	
}
