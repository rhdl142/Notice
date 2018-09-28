package com.test.notice.access;

import java.sql.Connection;

import com.test.notice.util.DBUtil;

public class AccessDAO {
	private Connection conn;
	
	public AccessDAO() {
		this.conn = DBUtil.getConnection("localhost","notice","java1234");
	}
}
