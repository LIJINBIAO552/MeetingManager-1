package com.meeting.pojo;

public class MyPageInfo {
	
	//�ڼ�ҳ
	private int pageNum;
	//�ܹ��м�ҳ
    private int pages;
    //�ܹ���������¼
    private int total;
    //�Ƿ�����һҳ
    private boolean hasPreviousPage;
    //�Ƿ�����һҳ
    private boolean hasNextPage;
    //ҳ��
    private int[] navigatepageNums;
    
    
	public int[] getNavigatepageNums() {
		return navigatepageNums;
	}
	public void setNavigatepageNums(int[] navigatepageNums) {
		this.navigatepageNums = navigatepageNums;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}
	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}
	public boolean isHasNextPage() {
		return hasNextPage;
	}
	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}
    
    
}
