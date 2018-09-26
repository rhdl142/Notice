package com.test.notice.user;

public class WithdrawDTO {
	private String seq;
	private String state;
	private String userSeq;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}
	@Override
	public String toString() {
		return "WithdrawDTO [seq=" + seq + ", state=" + state + ", userSeq=" + userSeq + "]";
	}
}
