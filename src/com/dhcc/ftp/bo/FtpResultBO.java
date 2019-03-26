package com.dhcc.ftp.bo;

import com.dhcc.ftp.util.PageUtil;


public class FtpResultBO extends BaseBo{

	//list∑÷“≥≤È—Ø
	public PageUtil dofind(int page,String poolName,String brName,String curNo){
		return ftpResultDAO.dofind(page,poolName,brName,curNo);
	}
	
}
