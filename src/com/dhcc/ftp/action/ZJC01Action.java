package com.dhcc.ftp.action;

import javax.servlet.http.HttpServletRequest;

import com.dhcc.ftp.dao.DaoFactory;
import com.dhcc.ftp.entity.FtpPool;
import com.dhcc.ftp.util.PageUtil;

public class ZJC01Action extends BoBuilder {
	
	private String flag;
    private String[] itemsList;
    private String poolId;
    private int page = 1;
    private PageUtil FtpPoolUtil = null;
    
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		HttpServletRequest request = getRequest();
		DaoFactory df = new DaoFactory();

		//显示列表
		if (flag.equals("List")) {
			String hsql = "from FtpPool";
			FtpPoolUtil = ftpPoolBO.dofind(page);
			request.setAttribute("FtpPoolUtil", FtpPoolUtil);
			return flag;
		}
		//保存
		if(flag.equals("Save")) {
			for(int i = 0; i < itemsList.length; i++){
			    if(itemsList[i].lastIndexOf("|")+1==itemsList[i].length()){
				   itemsList[i] = itemsList[i] +"%";
			    }
			    String[] itemStr = itemsList[i].split("\\|"); 
			    if(!itemStr[0].trim().equals("0")){
					//如果ID不等于0，则为更新
					FtpPool ftpPool = new FtpPool();
					ftpPool.setPoolId(Integer.valueOf(itemStr[0]));
					ftpPool.setPoolNo(itemStr[1]);
					ftpPool.setPoolName(itemStr[2]);
					ftpPool.setPoolType(itemStr[3]);
					df.update(ftpPool);
					}else{
					//如果ID等于0，则为添加
						FtpPool ftpPool = new FtpPool();
						ftpPool.setPoolNo(itemStr[1]);
						ftpPool.setPoolName(itemStr[2]);
						ftpPool.setPoolType(itemStr[3]);
					df.insert(ftpPool);
					}
			}
			return null;
		}
		if(flag.equals("del")){
			String[] typeIdStr = poolId.split(",");
			for(int i = 0; i < typeIdStr.length; i++){
				String hsql="delete from FtpPool where poolId="+typeIdStr[i]+"";
				df.delete(hsql, null);
			}
		}
		return null;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String[] getItemsList() {
		return itemsList;
	}

	public void setItemsList(String[] itemsList) {
		this.itemsList = itemsList;
	}

	public String getPoolId() {
		return poolId;
	}

	public void setPoolId(String poolId) {
		this.poolId = poolId;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public PageUtil getFtpPoolUtil() {
		return FtpPoolUtil;
	}

	public void setFtpPoolUtil(PageUtil ftpPoolUtil) {
		FtpPoolUtil = ftpPoolUtil;
	}
	

}
