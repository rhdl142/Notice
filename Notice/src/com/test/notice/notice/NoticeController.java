package com.test.notice.notice;

import java.util.ArrayList;

import com.test.notice.MainClass;
import com.test.notice.access.GroupAccessDTO;
import com.test.notice.access.UserAccessDTO;
import com.test.notice.util.UtilPrint;
import com.test.notice.util.UtilScanner;

public class NoticeController {
	private UtilScanner scan;
	private UtilPrint out;
	private NoticeDAO dao;
	
	public NoticeController() {
		this.scan = new UtilScanner();
		this.out = new UtilPrint();
		this.dao = new NoticeDAO();
	}

	public void main() {
		while(true)	 {
			out.title("알림");
			out.menu(UtilPrint.NOTICE);
			int input = scan.nextInt("선택");
	
			//1 > 알림, 2 > 유저, 3 > 관리자
			if (input == 1) {
				MainClass.crumb.in("알림열람");
				list();
				MainClass.crumb.out();
			} else if (input == 2) {
				MainClass.crumb.in("알림작성");
				if(MainClass.isAuth != null) {
					add();
				} else {
					out.pause("인증 받은 사용자만 사용이 가능합니다.");
				}
				MainClass.crumb.out();
			} else {
				break;
			}
		}
	}
	
	//NoticeController.list()
	private void list() {
		out.title("알림열람");
		
		ArrayList<NoticeDTO> list = dao.list(MainClass.isAuth != null ? true : false);
		
		out.header(new String[] {"번호","\\t알림\t\t","날짜\t","유저","카테고리"});
		
		//실명접속시 모든 리스트 출력
		//익명접속시 ㅇ익명권한이 있는 알림만 출력
			for(NoticeDTO notice : list) {
				out.data(new Object[] {
						notice.getNoticeSeq(),
						notice.getContent(),
						notice.getRegdate(),
						notice.getUserSeq(),
						notice.getCategorySeq()
				});
			}
		
		//if(MainClass.isAuth != null) {
			String noticeSeq = scan.next("알림번호 선택(미선택 > 0입력) : ");
			
			if(!noticeSeq.equals("0")) {
				
				//해댱 알림을 읽을 권한(사용자가 적은것만 볼 수 있음.)
				//인증 티켓을 조건으로 DB에 select(글쓴이)
				
				//상세 선택
				//선택한 알림의 모든 내용 출력하기
				NoticeDTO nDTO = dao.get(noticeSeq);
				
				//권한 체크  (글쓴당사자 + 읽기 권한이 있는 유저), 로그인계정 + 알림번호 > 유저권한 테이블 확인 가능
				//읽기 권한이 있는 그룹에 속한 유저도 보기 가능
				if(nDTO.getUserSeq().equals(MainClass.isAuth) 
						|| isRead(MainClass.seq,noticeSeq) 
						|| isReadGroup(MainClass.seq, noticeSeq)
						|| isReadAnonyomous(noticeSeq)) {
					System.out.println("[번호] " + nDTO.getNoticeSeq());
					System.out.println("[알림] " + nDTO.getContent());
					System.out.println("[날짜] " + nDTO.getRegdate());
					System.out.println("[유저] " + nDTO.getUserSeq());
					System.out.println("[분류] " + nDTO.getCategorySeq());	
					
					if(nDTO.getUserSeq().equals(MainClass.isAuth)) {
						//해댱 알림의 권한 관리
						out.menu(UtilPrint.ACCESS);
						int input = scan.nextInt("선택");
						
						if(input == 1 ) {
							//유저 권한
							//해당 알림에 걸려있는 권한 관리
							out.menu(UtilPrint.ACCESSDETAIL);
							input = scan.nextInt("선택");
							
							if(input == 1) {
								//유저 권한
								//해당 알림을 볼 수 있는 유저를 가져오기 > 출력
								//1. 권한 부여 회원 추가
								//2. 권한 삭제 회원 추가
								//3. 돌아가기
								out.menu(UtilPrint.ACCESSDETAILUSER);
								input = scan.nextInt("선택");
								
								if(input == 1) {
									//권한부여
									//보여줄유저의 아이디를 입력
									String id = scan.next("권한을 부여할 회원 아이디");
									
									UserAccessDTO uaDTO = new UserAccessDTO();
									uaDTO.setNoticeSeq(nDTO.getNoticeSeq());
									uaDTO.setUserSeq(id);
									
									int result = dao.addUserAccess(uaDTO);
									
									out.result(result,"권한을 부여했습니다.");
								} else if(input == 2 ) {
									//권한제거
									//권한을 뺏을 유저 아이디 입력
									String id = scan.next("권한을 삭제할 회원 아이디");
									
									UserAccessDTO uaDTO = new UserAccessDTO();
									uaDTO.setNoticeSeq(nDTO.getNoticeSeq());
									uaDTO.setUserSeq(id);
									
									int result = dao.addUserAccess(uaDTO);
									
									out.result(result,"권한을 삭제했습니다.");
								} 
							} else if(input == 2) {
								//그룹 권한
								//1. 권한 부여 그룹 추가
								//2. 권한 삭제 그룹 추가
								//3. 돌아가기
								out.menu(UtilPrint.ACCESSDETAILGROUP);
								input = scan.nextInt("선택");
								
								if(input == 1) {
									//권한 부여 그룹
									String groupSeq = scan.next("권한을 부여할 그룹명");
									
									GroupAccessDTO gaDTO = new GroupAccessDTO();
									gaDTO.setGroupSeq(groupSeq);  //그룹명
									gaDTO.setNoticeSeq(noticeSeq);   //알림번호
									gaDTO.setUserSeq(MainClass.seq); //그룹 소유주 번호 = 로그인한 계정
									
									int result = dao.addGroupAccess(gaDTO);
									
									out.result(result,"권한을 부여했습니다.");
								} else if(input == 2){
									//권한 삭제 그룹
									String groupSeq = scan.next("권한을 삭제할 그룹명");
									
									GroupAccessDTO gaDTO = new GroupAccessDTO();
									gaDTO.setGroupSeq(groupSeq);
									gaDTO.setNoticeSeq(noticeSeq);
									gaDTO.setUserSeq(MainClass.seq); //그룹 소유주 번호 = 로그인한 계정
									
									int result = dao.removeGroupAccess(gaDTO);
									
									out.result(result,"권한을 삭제했습니다.");
								}
								
							} else if(input == 3) {
								//익명 권한
								input = scan.nextInt("익명 사용자에게 권한 부여(Y:1/N:2) : ");
								if(input == 1) {
									//익명 사용자에게 읽기 권한 부여
									int result = dao.addAnonoymousAccess(noticeSeq);
									
									out.result(result,"권한을 부여했습니다.");
								} else if (input == 2) {
									//익명 사용자에게 읽기 권한 삭제
									int result = dao.removeAnonoymousAccess(noticeSeq);
									
									out.result(result,"권한을 삭제했습니다.");									
								}
							}
							
							
						//} else {
						//	out.result("보기 권한이 없습니다.");
						//}
					}
				} else {
					out.result("열기 권한이 없습니다.");
				}
			}
		}
		
		out.pause();
	}

	private boolean isReadAnonyomous(String noticeSeq) {
		//tblAnonymousAccess 테이블에 notice글이 등록 되어 있는지 확인?
		if(dao.isReadAnonymous(noticeSeq) > 0) {
			return true;
		}
		return false;
	}

	private boolean isReadGroup(String userSeq, String noticeSeq) {
		//tblGroupAccess 테이블에 userSeq + noticeSeq가 권한이 등록되어 있는지 확인.
		if(dao.isReadGroup(userSeq,noticeSeq) > 0) {
			return true;
		}
		
		return false;
	}

	private boolean isRead(String userSeq, String noticeSeq) {
		//tblUserAccess 테이블에 userSeq + noticeSeq가 권한이 등록되어 있는지 확인.
		if(dao.isRead(userSeq,noticeSeq) > 0) {
			return true;
		}
		
		return false;
	}

	private void add() {
		//알림작성
		out.title("알림작성");
		
		ArrayList<CategoryDTO> clist = dao.categoryList();

		for(CategoryDTO c : clist) {
			System.out.printf("%s, %s\n",c.getCategorySeq(),c.getName());
		}
		
		String cseq = scan.next("카테고리 선택: ");
		String content = scan.next("내용");
		
		//DAO위임 > insert
		NoticeDTO nDTO = new NoticeDTO();
		
		nDTO.setContent(content);
		nDTO.setUserSeq(MainClass.seq);
		nDTO.setCategorySeq(cseq);
		
		int result = dao.add(nDTO);
		out.result(result,"알림 작성 성공");
		out.pause();
	}
}













