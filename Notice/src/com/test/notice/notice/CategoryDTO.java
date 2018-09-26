package com.test.notice.notice;

public class CategoryDTO {
	private String categorySeq;
	private String name;
	
	public String getCategorySeq() {
		return categorySeq;
	}
	public void setCategorySeq(String categorySeq) {
		this.categorySeq = categorySeq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "CategoryDTO [categorySeq=" + categorySeq + ", name=" + name + "]";
	}
}
