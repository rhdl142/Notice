package com.test.notice.access;

public class AnonymousAccessDTO {
	private String seq;
	private String noticeSeq;
	
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
	@Override
	public String toString() {
		return "AnonymousAccessDTO [seq=" + seq + ", noticeSeq=" + noticeSeq + "]";
	}
}
