package com.dhcc.ftp.bo;
/**
 * @desc:���ݹ�����ծ��Ϣ����
 * @author :������
 * @date ��2011-08-24
 */
import com.dhcc.ftp.util.PageUtil;

public class FtpStockBO extends BaseBo{

	//list��ҳ��ѯ
	public PageUtil dofind(int page) {
		return ftpStockDAO.dofind(page);
	}
	
}
