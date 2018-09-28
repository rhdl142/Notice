package com.test.notice.access;

import com.test.notice.notice.NoticeDAO;
import com.test.notice.util.UtilPrint;
import com.test.notice.util.UtilScanner;

public class AccessController {
	private UtilScanner scan;
	private UtilPrint out;
	private AccessDAO dao;
	
	public AccessController() {
		this.scan = new UtilScanner();
		this.out = new UtilPrint();
		this.dao = new AccessDAO();
	}
}
