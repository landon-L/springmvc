package org.ldy.springmvc.util;

public class PageModel {
	//分页总数据条数
	private int recordCount;
	//当前页
	private int pageIndex;
	//每页分多少条数据
	private int pageSize = CommonVal.PAGE_DEFAULT_SIZE;
	//总页数
	private int totalPage;
	
	public int getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	} 

	public void setTotalPage() {
		this.totalPage = (recordCount -1)/pageSize + 1;
	}
	/*
	 * 获取当前页对应的第一条记录的行数
	 */
	public int getFirstLimitParam()
	{
		if (this.pageIndex == 0) {
			return 0;
		}			
		return (this.pageIndex -1) * pageSize;
	}
	
}
