package com.dhcc.ftp.bo;
/**
 * @desc:数据管理：国债信息管理
 * @author :冯少云
 * @date ：2011-08-24
 */
import com.dhcc.ftp.util.PageUtil;

public class FtpStockBO extends BaseBo{

	//list分页查询
	public PageUtil dofind(int page) {
		return ftpStockDAO.dofind(page);
	}
	
}
