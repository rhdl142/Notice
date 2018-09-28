package com.test.notice.user;

import java.util.ArrayList;

import com.test.notice.MainClass;
import com.test.notice.util.UtilPrint;
import com.test.notice.util.UtilScanner;

public class UserController {
	private UtilScanner scan;
	private UtilPrint out;
	private UserDAO dao;

	public UserController() {
		this.scan = new UtilScanner();
		this.out = new UtilPrint();
		this.dao = new UserDAO();
	}

	// 유저 업무
	public void main() {
		while (true) {
			if(MainClass.isAuth == null) {
				out.title("유저");
				out.menu(UtilPrint.USER_IN);
				int input = scan.nextInt("선택");

				if (input == 1) {
					MainClass.crumb.in("로그인");
					login();
					MainClass.crumb.out();
				} else if (input == 2) {
					MainClass.crumb.in("회원 가입");
					register();
					MainClass.crumb.out();
				} else {
					break;
				}
			} else {
				out.title("회원");
				out.menu(UtilPrint.USER_OUT);
				int input = scan.nextInt("선택");

				if (input == 1) {
					MainClass.crumb.in("마이페이지");
					logout();
					MainClass.crumb.out();
				} else if (input == 2) {
					MainClass.crumb.in("활동이력열람");
					history();
					MainClass.crumb.out();
				} else if (input == 3) {
					MainClass.crumb.in("탈퇴신청");
					withdraw();
					MainClass.crumb.out();
				} else if (input == 4) {
					MainClass.crumb.in("돌아가기");
					MainClass.crumb.out();
				} else {
					break;
				}
			}
		}
	}

	private void history() {
		//알람 작성 횟수 + 접속 이력
		out.title("활동 이력 열람");
		
		//알림 작성 횟수
		int count = dao.getNoticeCount(MainClass.seq);
		
		out.result(String.format("총 %d회 알림을 작성했습니다.", count));
		
		//접속이력
		ArrayList<HistoryDTO> list = dao.getHistory(MainClass.seq);
		
		//출력
		out.header(new String[] {"접속시간","활동시간"});
		
		int totalTime = 0;
		int totalCount = 0;
		
		for(HistoryDTO hDTO : list) {
			out.data(new Object[] {hDTO.getLogin(),hDTO.getTime()});
			totalTime += hDTO.getTime();
			totalCount++;
		}
		out.result("총 활동 시간 : "+totalTime);
		out.result("총 접속 횟수 : "+totalCount);
		
		out.pause();
	}

	private void withdraw() {
		//탈퇴신청
		out.title("탈퇴신청");
		
		String input = scan.next("정말 탈퇴하실겁니꺄?(Y/N)");
		
		if(input.equals("Y")) {
			int result = dao.addWithdraw(MainClass.seq);
		}
		out.result("탈퇴신청을 하였습니다.");	
		out.pause();
	}

	private void logout() {
		//로그아웃
		out.title("로그아웃");
		
		//로그아웃 처리 > 인증 제거
		MainClass.isAuth = null;
		MainClass.name = null;
		
		dao.updateHistory(MainClass.seq);
		
		MainClass.seq = null;
		out.result("로그아웃 했습니다.");
		out.pause();
	}

	private void login() {
		//로그인
		out.title("로그인");
		
		String id = scan.next("아이디");
		String pw = scan.next("암호");
		
		//DAO 위임 > id, pw > 회원
		UserDTO uDTO = new UserDTO();
		uDTO.setId(id);
		uDTO.setPw(pw);
		
		//int result = dao.auth(uDTO);
		UserDTO result = dao.auth1(uDTO);
		
		if(result != null) {
			MainClass.isAuth = id;
			MainClass.name = result.getName();
			MainClass.seq = result.getSeq();
			
			//로그인 시각 기록(로그)
			dao.addHistory(MainClass.seq);
		} else {
			MainClass.isAuth = null;
			MainClass.name = null;
			MainClass.seq = null;
		}
		
		out.result(result != null ? 1:0,"인증에 성공했습니다.");
		out.pause();
	}

	private void register() {
		//회원가입
		out.title("회원 가입");
		
		//테이블(컬럼명) = DTO(멤버명) = 지역변수명
		String id = scan.next("아이디");
		String pw = scan.next("비밀번호");
		String name = scan.next("이름");
		String email = scan.next("이메일");
		String nick = scan.next("닉네임");
		
		UserDTO uDTO = new UserDTO();
		
		uDTO.setId(id);
		uDTO.setPw(pw);
		uDTO.setName(name);
		uDTO.setEmail(email);
		uDTO.setNick(nick);
		
		int result = dao.register(uDTO);
		
		out.result(result, "회원가입을 성공했습니다.");
		
		out.pause();
	}
}












