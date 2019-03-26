package com.dhcc.ftp.entity;



public class SysRoleMenu  implements java.io.Serializable {

     private String id;
     private String roleNo;
     private String menuNo;

    public SysRoleMenu() {
    }
    public SysRoleMenu(String id) {
        this.id = id;
    }
    public SysRoleMenu(String id, String roleNo, String menuNo) {
        this.id = id;
        this.roleNo = roleNo;
        this.menuNo = menuNo;
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleNo() {
		return roleNo;
	}
	public void setRoleNo(String roleNo) {
		this.roleNo = roleNo;
	}
	public String getMenuNo() {
		return menuNo;
	}
	public void setMenuNo(String menuNo) {
		this.menuNo = menuNo;
	}


}