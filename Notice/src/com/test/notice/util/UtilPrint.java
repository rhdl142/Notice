package com.test.notice.util;

import java.util.Scanner;

import com.test.notice.MainClass;

public class UtilPrint {
	
	public final static int LONG = 80;
	public final static int MEDIUM = 40;
	public final static int SHORT = 20;
	public final static String[] MAIN = {"알림","유저","관리자","종료"};
	public final static String[] NOTICE = {"알림열람","알림작성","돌아가기"};
	public final static String[] USER_IN = {"로그인","회원가입","돌아가기"};
	public final static String[] USER_OUT = {"로그아웃","마이페이지","활동이력","탈퇴신청","돌아가기"};
	public final static String[] ACCESS = {"권한열람","돌아가기"};
	public final static String[] ACCESSDETAIL = {"유저권한","그룹권한","익명권한","돌아가기"};
	public final static String[] ACCESSDETAILUSER = {"권한부여회원추가","권한회원삭제추가","돌아가기"};
	public final static String[] ACCESSDETAILGROUP = {"권한부여그룹추가","권한그룹삭제추가","돌아가기"};
	public final static String[] ADMINISTRATOR = {"관리자 로그인","회원열람","탈퇴처리","알림열람","카테고리","돌아가기"};
	
	public void menu(String[] list) {
		for(int i=0; i<list.length; i++) {
			System.out.printf("%d. %s\n",(i+1),list[i]);
		}
	}
	
	public void line(int size) {
		for(int i=0; i<size; i++) {
			System.out.print("=");
		}
		System.out.println();
	}
	
	public void pause(String label) {
		System.out.println(label);
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		//scan.close();
	}
	
	public void pause() {
		System.out.println("계속하려면 엔터키를 입력하세요.");
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		//scan.close();
	}
	
	public void title(String label) {
		MainClass.crumb.now();
		
		line(UtilPrint.MEDIUM);
		for(int i=0; i<(UtilPrint.MEDIUM/2)-label.length(); i++) {
			System.out.printf(" ");
		}
		System.out.println(label);
		line(UtilPrint.MEDIUM);
	}
	
	public void header(String[] labels) {
		for(String label : labels) {
			System.out.printf("[%s]\t",label);
		}
		System.out.println();
	}
	
	public void data(Object[] datas) {
		for(Object data : datas) {
			System.out.printf("%s\t",data);
		}
		System.out.println();
	}
	
	public void result(String msg) {
		System.out.println("결과]"+ msg);
		System.out.println();
	}
	
	public void result(int result, String msg) {
		if(result > 0) {
			System.out.println("결과]"+ msg);
			System.out.println();
		} else {
			System.out.println("실패했습니다.  관리자에게 문의해주세요~");
		}
	}
}
































