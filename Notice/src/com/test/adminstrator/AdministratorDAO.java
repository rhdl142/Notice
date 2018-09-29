package com.test.adminstrator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.test.notice.user.UserDTO;
import com.test.notice.util.DBUtil;

public class AdministratorDAO {
	private Connection conn;
	
	public AdministratorDAO() {
		this.conn = DBUtil.getConnection("localhost","notice","java1234");
	}

	public int withdraw(String id) {
		try {
			//id > 유저번호
			//tblUser 소멸(update)
			//tblWithraw 상태 변경(update)
			//1.
			String sql = "select userSeq from tblUser where id = ?";
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, id);
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				String usr = rs.getString("seq");
				rs.close();
				stat.close();
				//2.
				sql = "update tblUser set id = 'geust', pw = 'guest', name = 'guest', email = null, nick = 'guest' where userSeq = ?";
				stat = conn.prepareStatement(sql);
				stat.setString(1, usr);
				
				stat.executeUpdate();
				//3.
				sql = "update tblWithraw set state = 2 where userSeq = ?";
				stat = conn.prepareStatement(sql);
				stat.setString(1, usr);
				stat.executeUpdate();
				stat.close();
				
				return 1;
			}
		} catch (Exception e) {
			System.out.println("AdministratorDAO.withdraw :" + e.toString());
		}
		return 0;
	}

	public ArrayList<UserDTO> userlist() {
		ArrayList<UserDTO> list = new ArrayList<>();
		
		try {
			String sql = "select * from tblUser where userSeq in (select userSeq from tblwithraw where state = 1)";
			
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				UserDTO uDTO = new UserDTO();
				uDTO.setSeq(rs.getString("seq"));
				uDTO.setId(rs.getString("id"));
				uDTO.setName(rs.getString("name"));
			}
			
			return list;
			
		} catch (Exception e) {
			System.out.println("AdministratorDAO.userlist :" + e.toString());
		}
		return null;
	}
}
