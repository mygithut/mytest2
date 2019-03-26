package com.dhcc.ftp.util;

import java.util.List;

public class QueryResult{

	private int totalRecords;
	private List list;

	public QueryResult(int totalRecords, List list) {
		this.totalRecords = totalRecords;
		this.list = list;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}


}
