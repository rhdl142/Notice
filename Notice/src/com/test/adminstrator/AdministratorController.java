package com.test.adminstrator;

import java.util.ArrayList;

import com.test.notice.MainClass;
import com.test.notice.user.UserDAO;
import com.test.notice.user.UserDTO;
import com.test.notice.util.UtilPrint;
import com.test.notice.util.UtilScanner;

public class AdministratorController {
	private UtilScanner scan;
	private UtilPrint out;
	private AdministratorDAO dao;
	
	public AdministratorController() {
		this.scan = new UtilScanner();
		this.out = new UtilPrint();
		this.dao = new AdministratorDAO();
	}
	
	public void main() {
		while(true) {
			out.title("관리자");
			out.menu(UtilPrint.ADMINISTRATOR);
			
			int input = scan.nextInt("선택");
			
			if(input == 1) {
				MainClass.isAuth = "admin";
				MainClass.name = "관리자";
				MainClass.seq = "1";
				MainClass.isAdmin = true;
			} else if(input == 2) {
				
			} else if(input == 3) {
				MainClass.crumb.in("탈퇴처리");
				withdraw();
				MainClass.crumb.out();
			} else if(input == 4) {
				
			} else {
				break;
			}
		}
	}

	private void withdraw() {
		out.title("탈퇴처리");
		//목록출력 > 유저선택 > 탈퇴 승인 처리
		ArrayList<UserDTO> list = dao.userlist();
		
		out.header(new String[] {"아이디","이름"}); 
		
		for(UserDTO uDTO : list) {
			out.data(new Object[] {uDTO.getId() + "\t" + uDTO.getName()});
		}
		
		//선택
		String id = scan.next("선택[아이디]");
		int result = dao.withdraw(id);
		
		out.result(result,"탈퇴 처리가 완료되었습니다.");
		out.pause();
	}
}















