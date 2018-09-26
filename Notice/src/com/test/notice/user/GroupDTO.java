package com.test.notice.user;

public class GroupDTO {
	private String groupSeq;
	private String name;
	private String userSeq;
	
	public String getGroupSeq() {
		return groupSeq;
	}
	public void setGroupSeq(String groupSeq) {
		this.groupSeq = groupSeq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}
	@Override
	public String toString() {
		return "GroupDTO [groupSeq=" + groupSeq + ", name=" + name + ", userSeq=" + userSeq + "]";
	}
}
