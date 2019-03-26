package com.dhcc.ftp.bo;

import com.dhcc.ftp.util.PageUtil;


public class FtpPoolBO extends BaseBo{

	//list∑÷“≥≤È—Ø
	public PageUtil dofind(int page) {
		return ftpPoolDAO.dofind(page);
	}
	
}
