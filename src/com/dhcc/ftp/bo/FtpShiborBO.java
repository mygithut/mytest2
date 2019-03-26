package com.dhcc.ftp.bo;

import com.dhcc.ftp.util.PageUtil;

public class FtpShiborBO extends BaseBo{

	//list∑÷“≥≤È—Ø
	public PageUtil dofind(int page) {
		return ftpShiborDAO.dofind(page);
	}
	
}
