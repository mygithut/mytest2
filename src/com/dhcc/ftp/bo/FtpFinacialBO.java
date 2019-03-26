package com.dhcc.ftp.bo;
/**
 * @desc:数据管理：政策性金融债收益率维护
 * @author :孙红玉
 * @date ：2012-03-28
 */
import com.dhcc.ftp.util.PageUtil;

public class FtpFinacialBO extends BaseBo{

	//list分页查询
	public PageUtil dofind(int page) {
		return ftpFinacialDAO.dofind(page);
	}
	
}
