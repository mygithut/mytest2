package com.dhcc.ftp.bo;
/**
 * @desc:���ݹ��������Խ���ծ������ά��
 * @author :�����
 * @date ��2012-03-28
 */
import com.dhcc.ftp.util.PageUtil;

public class FtpFinacialBO extends BaseBo{

	//list��ҳ��ѯ
	public PageUtil dofind(int page) {
		return ftpFinacialDAO.dofind(page);
	}
	
}
