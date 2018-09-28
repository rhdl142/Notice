package com.test.notice.access;

public class GroupAccessDTO {
	private String seq;
	private String noticeSeq;
	private String groupSeq;
	private String userSeq;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getNoticeSeq() {
		return noticeSeq;
	}
	public void setNoticeSeq(String noticeSeq) {
		this.noticeSeq = noticeSeq;
	}
	public String getGroupSeq() {
		return groupSeq;
	}
	public void setGroupSeq(String groupSeq) {
		this.groupSeq = groupSeq;
	}
	@Override
	public String toString() {
		return "GroupAccessDTO [seq=" + seq + ", noticeSeq=" + noticeSeq + ", groupSeq=" + groupSeq + "]";
	}
	public String getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}
}
