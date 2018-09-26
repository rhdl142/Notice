package com.test.notice.notice;

public class NoticeDTO {
	private String noticeSeq;
	private String content;
	private String regdate;
	private String userSeq;
	private String categorySeq;
	
	public String getNoticeSeq() {
		return noticeSeq;
	}
	public void setNoticeSeq(String noticeSeq) {
		this.noticeSeq = noticeSeq;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRegdate() {
		return regdate;
	}
	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}
	public String getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}
	public String getCategorySeq() {
		return categorySeq;
	}
	public void setCategorySeq(String categorySeq) {
		this.categorySeq = categorySeq;
	}
	@Override
	public String toString() {
		return "NoticeDTO [noticeSeq=" + noticeSeq + ", content=" + content + ", regdate=" + regdate + ", userSeq="
				+ userSeq + ", categorySeq=" + categorySeq + "]";
	}
}
