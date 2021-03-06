package com.test.notice.user;

public class HistoryDTO {
	private String seq;
	private String login;
	private String logout;
	private String userSeq;
	private int time;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getLogout() {
		return logout;
	}
	public void setLogout(String logout) {
		this.logout = logout;
	}
	public String getUserSeq() {
		return userSeq;
	}
	public void setUserSeq(String userSeq) {
		this.userSeq = userSeq;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	@Override
	public String toString() {
		return "HistoryDTO [seq=" + seq + ", login=" + login + ", logout=" + logout + ", userSeq=" + userSeq + "]";
	}
}
