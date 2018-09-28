package com.test.notice.user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.test.notice.util.DBUtil;

public class UserDAO {
	private Connection conn;
	private PreparedStatement stat;
	
	public UserDAO() {
		this.conn = DBUtil.getConnection("localhost","notice","java1234");
	}
	
	public int register(UserDTO uDTO) {
		
		try {
			String sql = "insert into tblUser values(userSeq.nextval,?,?,?,?,?)";
			stat = conn.prepareStatement(sql);
			
			stat.setString(1, uDTO.getId());
			stat.setString(2, uDTO.getPw());
			stat.setString(3, uDTO.getName());
			stat.setString(4, uDTO.getEmail());
			stat.setString(5, uDTO.getNick());
			
			return stat.executeUpdate();
		} catch (Exception e) {
			System.out.println("UserDAO.register :" + e.toString());
		}
		
		return 0;
	}

	public int auth(UserDTO uDTO) {
		try {
			String sql = "select count(*) as cnt from tblUser where id = ? and pw = ?";
			stat = conn.prepareStatement(sql);
			
			stat.setString(1, uDTO.getId());
			stat.setString(2, uDTO.getPw());
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("cnt");
			}
		} catch (Exception e) {
			System.out.println("UserDAO.auth :" + e.toString());
		}
		return 0;
	}
	
	public UserDTO auth1(UserDTO uDTO) {
		
		//UserDTO result = new UserDTO();
		
		try {
			String sql = "select userseq,name,id from tblUser where id = ? and pw = ?";
			stat = conn.prepareStatement(sql);
			
			stat.setString(1, uDTO.getId());
			stat.setString(2, uDTO.getPw());
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				uDTO.setSeq(rs.getString("userSeq"));
				uDTO.setName(rs.getString("name"));
				uDTO.setId(rs.getString("id"));
				
				return uDTO;
			}
		} catch (Exception e) {
			System.out.println("UserDAO.auth :" + e.toString());
		}
		return null;
	}

	public void addHistory(String userSeq) {
		//회원번호 + 로그인 시각 > insert
		try {
			String sql = "insert into tblHistory values(historySeq.nextval,default,null,?)";
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, userSeq);
			
			stat.executeUpdate();
		} catch (Exception e) {
			System.out.println("UserDAO.addHistory :" + e.toString());
		}
	}

	public void updateHistory(String userSeq) {
		//회원번호 + 로그아웃 시각 > update
		try {
			String sql = "update tblHistory set logout = sysdate where userSeq = ? and seq = (select max(seq) from tblHistory where userSeq = ?)";
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, userSeq);
			stat.setString(2, userSeq);
			
			stat.executeUpdate();
		} catch (Exception e) {
			System.out.println("UserDAO.addHistory :" + e.toString());
		}
	}

	public int addWithdraw(String userSeq) {
		//회원번호 + 로그인 시각 > insert
		try {
			String sql = "insert into tblWithraw values(withrawSeq.nextval,1,?)";
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, userSeq);
			
			return stat.executeUpdate();
		} catch (Exception e) {
			System.out.println("UserDAO.addHistory :" + e.toString());
		}
		return 0;
	}

	public int getNoticeCount(String userSeq) {
		try {
			String sql = "select count(*) as cnt from tblNotice where userSeq = ?";
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, userSeq);
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("cnt");
			}
		} catch (Exception e) {
			System.out.println("UserDAO.getNoticeCount :" + e.toString());
		}
		return 0;
	}
	
	public ArrayList<HistoryDTO> getHistory(String usr) {
		ArrayList<HistoryDTO> list = new ArrayList<>();
		
		try {
			String sql = "select login, round((logout-login) *24 *60 *60) as time from tblHistory where usr = ?";
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, usr);
			ResultSet rs = stat.executeQuery();
			
			while(rs.next()) {
				//레코드 1줄 > DTO 1개
				HistoryDTO hDTO = new HistoryDTO();
				hDTO.setLogin(rs.getString("login"));
				hDTO.setTime(rs.getInt("time"));
				list.add(hDTO);
			}
		} catch (Exception e) {
			System.out.println("UserDAO.getHistory :" + e.toString());
		}
		return null;
	}
}








