package com.test.notice;

import com.test.adminstrator.AdministratorController;
import com.test.notice.notice.NoticeController;
import com.test.notice.user.UserController;
import com.test.notice.util.BreadCrumb;
import com.test.notice.util.UtilPrint;
import com.test.notice.util.UtilScanner;

public class MainClass {
	private static UtilScanner scan;
	private static UtilPrint out;

	//프로그램 실행 중 어디서든 항상 사용할 자원 필요 > main() 소유 클래스의 정적 public 변수 생성
	public static BreadCrumb crumb;
	public static String isAuth;
	public static String name;
	public static String seq;
	public static boolean isAdmin;//관리자? 유저?

	static {
		scan = new UtilScanner();
		out = new UtilPrint();
		crumb = new BreadCrumb();
		isAuth = null;
		name = null;
		seq = null;
		isAdmin = false;
	}

	public static void main(String[] args) {
		NoticeController notice = new NoticeController();
		UserController user = new UserController();
		AdministratorController admin = new AdministratorController();
		
		MainClass.crumb.in("알림장");
		
		while (true) {
			out.title("알림장");
			out.menu(UtilPrint.MAIN);
			int input = scan.nextInt("선택");

			//1 > 알림, 2 > 유저, 3 > 관리자
			if (input == 1) {
				MainClass.crumb.in("알림");
				notice.main();
				MainClass.crumb.out();
			} else if (input == 2) {
				MainClass.crumb.in("유저");
				user.main();
				MainClass.crumb.out();
			} else if (input == 3) {
				MainClass.crumb.in("관리자");
				MainClass.crumb.out();
			} else {
				break;
			}
		}
		
		MainClass.crumb.out();

		out.result("알림장을 종료합니다.");
	}
}
