package com.dhcc.ftp.bo;
/**
 * @desc:����ƥ��
 * @author :�����
 * @date ��2012-04-17
 */
import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.util.PageUtil;

public class UL07BO extends BaseBo{

	//��ҳ��ѯ
	public PageUtil dofind(HttpServletRequest request, int page, int rowsCount, String brNo, String curNo, String businessNo, String prdtNo, String wrkTime1, String wrkTime2) {
		return uL07DAO.dofind(request, page, rowsCount, brNo, curNo, businessNo, prdtNo, wrkTime1, wrkTime2);
	}
}
