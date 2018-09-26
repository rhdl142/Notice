package com.test.notice.util;

import java.util.Stack;

public class BreadCrumb {
	private  Stack<String> stack;
	
	public BreadCrumb() {
		this.stack = new Stack<String>();
	}
	
	public void in(String title) {
		stack.push(title);
	}
	
	public void out() {
		stack.pop();
	}
	
	public void now() {
		//stack loop 출력
		
	}
}
