package com.test.notice.user;

public class MemberDTO {
	private String seq;
	private String userSeq;
	private String groupSeq;
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}
	public String getGroupSeq() {
		return groupSeq;
	}
	public void setGroupSeq(String groupSeq) {
		this.groupSeq = groupSeq;
	}
	@Override
	public String toString() {
		return "MemberDTO [seq=" + seq + ", userSeq=" + userSeq + ", groupSeq=" + groupSeq + "]";
	}
}
