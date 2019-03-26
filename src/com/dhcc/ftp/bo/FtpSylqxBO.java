package com.dhcc.ftp.bo;

import java.util.Date;

import com.dhcc.ftp.util.PageUtil;


public class FtpSylqxBO extends BaseBo{

	//list∑÷“≥≤È—Ø
	public PageUtil dofind(int page,String curveName,String curveType,String curNo,Date curveDate){
		return ftpSylqxDAO.dofind(page,curveName,curveType,curNo,curveDate);
	}
}
