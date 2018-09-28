package com.test.notice.access;

public class UserAccessDTO {
	private String seq;
	private String userSeq;
	private String noticeSeq;
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
	public String getNoticeSeq() {
		return noticeSeq;
	}
	public void setNoticeSeq(String noticeSeq) {
		this.noticeSeq = noticeSeq;
	}
	@Override
	public String toString() {
		return "UserAccessDTO [seq=" + seq + ", userSeq=" + userSeq + ", noticeSeq=" + noticeSeq + "]";
	}
}
