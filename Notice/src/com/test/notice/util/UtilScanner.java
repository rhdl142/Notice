package com.test.notice.util;

import java.util.Scanner;

public class UtilScanner {
	private Scanner scan;
	
	public UtilScanner() {
		 scan = new Scanner(System.in);
	}
	
	public String next(String label) {
		System.out.printf(label+" :");
		return scan.nextLine();		
	}
	
	public String nextln(String label) {
		System.out.println(label+" :");
		return scan.nextLine();		
	}
	
	public int nextInt(String label) {
		System.out.printf(label+" :");
		return Integer.parseInt(scan.nextLine());		
	}
	
	public int nextIntln(String label) {
		System.out.println(label+" :");
		return Integer.parseInt(scan.nextLine());		
	}
	
	public String nextLine() {
		return scan.nextLine();
	}
	
	public int nextInt() {
		return Integer.parseInt(scan.nextLine());
	}
}
