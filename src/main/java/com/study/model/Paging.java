package com.study.model;

public class Paging {
	
	//현재 페이지
	private int pageNum;
	
	//한 페이지 당 보여질 게시물 갯수
	private int amount;
	
	//pageNum = 1, amount = 10
	public Paging() {
		this(1,10);
	}

	public Paging(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Paging [pageNum=" + pageNum + ", amount=" + amount + "]";
	}
	

	
}
