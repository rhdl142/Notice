package com.test.notice.notice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.test.notice.access.GroupAccessDTO;
import com.test.notice.access.UserAccessDTO;
import com.test.notice.util.DBUtil;

public class NoticeDAO {
	private Connection conn;
	
	public NoticeDAO() {
		this.conn = DBUtil.getConnection("localhost","notice","java1234");
	}

	public ArrayList<CategoryDTO> categoryList() {
		ArrayList<CategoryDTO> clist = new ArrayList<CategoryDTO>();
		
		try {
			String sql = "select * from tblCategory order by categorySeq asc";
			Statement stat = conn.createStatement();
			
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				CategoryDTO cDTO = new CategoryDTO();
				cDTO.setCategorySeq(rs.getString("categorySeq"));
				cDTO.setName(rs.getString("name"));
				clist.add(cDTO);
			}
			return clist;
		} catch (Exception e) {
			System.out.println("NoticeDAO.categoryList :" + e.toString());
		}

		return null;
	}

	public int add(NoticeDTO nDTO) {
		try {
			String sql = "insert into tblNotice values (noticeSeq.nextval,?,default,?,?)";
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, nDTO.getContent());
			stat.setString(2, nDTO.getUserSeq());
			stat.setString(3, nDTO.getCategorySeq());
			
			return stat.executeUpdate();
		} catch (Exception e) {
			System.out.println("NoticeDAO.add :" + e.toString());
		}
		return 0;
	}

	public ArrayList<NoticeDTO> list(boolean isAuth) {
		ArrayList<NoticeDTO> list = new ArrayList<>();
		
		try {
			String where = "";
			
			if(!isAuth) {
				where = " and noticeSeq in (select noticeSeq from tblAnonomousAccess) ";
			}

			String sql = String.format("select  " + 
					"    noticeseq," + 
					"    (substr(content,0,10) || '....') as content," + 
					"        case" + 
					"        when to_char(regdate, 'yyyy-mm-dd') = to_char(sysdate,'yyyy-mm-dd') then" + 
					"        to_char(regdate, 'hh24:mi:ss')" + 
					"        else to_char(regdate,'yyyy-mm-dd')" + 
					"    end as regdate," + 
					"    (select id from tblUser where userseq = n.userSeq) as userSeq," + 
					"    (select name from tblCategory where categoryseq = n.categorySeq) as categorySeq" + 
					" from tblNotice n userSeq not in(select userSeq from tblWithraw where state = 2) %s order by noticeSeq desc",where);
			
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			
			while(rs.next()) {
				NoticeDTO dto = new NoticeDTO();
				
				dto.setNoticeSeq(rs.getString("noticeSeq"));
				dto.setContent(rs.getString("content"));
				dto.setRegdate(rs.getString("regdate"));
				dto.setUserSeq(rs.getString("userSeq"));
				dto.setCategorySeq(rs.getString("categorySeq"));
				
				list.add(dto);
			}
			
			return list;
		} catch (Exception e) {
			System.out.println("NoticeDAO.list :" + e.toString());
		}
		
		return null;
	}

	public NoticeDTO get(String seq) {
		NoticeDTO nDTO = new NoticeDTO();
		
		try {
			String sql = "select"
							+ " noticeSeq,"
							+ " content,"
							+ " regdate,"
							+ " (select id from tblUser where userseq = n.userSeq) as userSeq," 
							+ " (select name from tblCategory where categoryseq = n.categorySeq) as categorySeq "
							+ " from tblNotice n where noticeSeq = " + seq;
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery(sql);
			
			if(rs.next()) {
				nDTO.setNoticeSeq(rs.getString("noticeSeq"));
				nDTO.setContent(rs.getString("content"));
				nDTO.setRegdate(rs.getString("regdate"));
				nDTO.setUserSeq(rs.getString("userSeq"));
				nDTO.setCategorySeq(rs.getString("categorySeq"));
				
				return nDTO;
			}
		} catch (Exception e) {
			System.out.println("NoticeDAO.get :" + e.toString());
		}
		
		return null;
	}
	
	public int addUserAccess(UserAccessDTO uaDTO) {
		try {
			String sql = "insert into tblUserAccess values (userAccessSeq.nextval, (select userseq from tblUser where id = ?), ?)";

			System.out.println(uaDTO.getNoticeSeq());
			System.out.println(uaDTO.getUserSeq());
			
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, uaDTO.getUserSeq()); //유저번호
			stat.setString(2, uaDTO.getNoticeSeq()); //알림번호
			
			return stat.executeUpdate();
		} catch (Exception e) {
			System.out.println("NoticeDAO.addUserAccess :" + e.toString());
		}
		
		return 0;
	}

	public int isRead(String userSeq, String noticeSeq) {
		try {
			String sql = "select count(*) as cnt from tblUserAccess where userSeq = ? and noticeSeq = ?";
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, userSeq);
			stat.setString(2, noticeSeq);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("cnt");
			}
		} catch (Exception e) {
			System.out.println("NoticeDAO.isRead :" + e.toString());
		}
		return 0;
	}



	public int removeUserACCccess(UserAccessDTO uaDTO) {
		try {
			String sql = "delete from tblUserAccess where userSeq = (select userseq from tblUser where id=? ) and noticeSeq = ?";
			
			PreparedStatement stat = conn.prepareStatement(sql);
			stat.setString(1, uaDTO.getUserSeq());
			stat.setString(2, uaDTO.getNoticeSeq());
			
			return stat.executeUpdate();
		} catch (Exception e) {
			System.out.println("NoticeDAO.removeUserACCccess :" + e.toString());
		}
		return 0;
	}

	public int addGroupAccess(GroupAccessDTO gaDTO) {
		try {
			String sql = "insert into tblGroupAccess values (groupAccessSeq.nextval,?,(select groupSeq from tblGroup where name = ? and userSeq = ?))";
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, gaDTO.getNoticeSeq());
			stat.setString(2, gaDTO.getGroupSeq());
			stat.setString(3, gaDTO.getUserSeq());
			
			return stat.executeUpdate();
		} catch (Exception e) {
			System.out.println("NoticeDAO.addGroupAccess :" + e.toString());
		}
		return 0;
	}

	public int removeGroupAccess(GroupAccessDTO gaDTO) {
		try {
			String sql = "delete from tblGroupAccess where noticeSeq = ? and groupSeq = (select groupseq from tblGroup where name = ? and userSeq = ?)";
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, gaDTO.getNoticeSeq());
			stat.setString(2, gaDTO.getGroupSeq());
			stat.setString(3, gaDTO.getUserSeq());
			
			return stat.executeUpdate();
		} catch (Exception e) {
			System.out.println("NoticeDAO.addGroupAccess :" + e.toString());
		}
		return 0;
	}

	public int isReadGroup(String userSeq, String noticeSeq) {
		try {
			String sql = "select " + 
					"    count(*) as cnt" + 
					" from tblGroupAccess" + 
					"    where noticeSeq = ? and " + 
					"        groupSeq = (select " + 
					"                        g.groupSeq" + 
					"                    from tblUser u" + 
					"                        inner join tblMember m" + 
					"                            on u.userSeq = m.userSeq" + 
					"                                inner join tblGroup g" + 
					"                                    on g.groupSeq = m.groupSeq" + 
					"                                        where u.userSeq = ?)";
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, noticeSeq);
			stat.setString(2, userSeq);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("cnt");
			}
		} catch (Exception e) {
			System.out.println("NoticeDAO.isReadGroup :" + e.toString());
		}
		return 0;
	}

	public int addAnonoymousAccess(String noticeSeq) {
		try {
			String sql = "insert into tblAnonomousAccess values (anonoymousAccessSeq.nextval,?)";
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, noticeSeq);
			
			return stat.executeUpdate();
		} catch (Exception e) {
			System.out.println("NoticeDAO.addAnonoymousAccess :" + e.toString());
		}
		return 0;
	}

	public int removeAnonoymousAccess(String noticeSeq) {
		try {
			String sql = "delete from tblAnonomouseAccess where noticeSeq = ?";
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, noticeSeq);
			
			return stat.executeUpdate();
		} catch (Exception e) {
			System.out.println("NoticeDAO.addAnonoymousAccess :" + e.toString());
		}
		return 0;
	}

	public int isReadAnonymous(String noticeSeq) {
		try {
			String sql = "select count(*) as cnt from tblAnonomousAccess where noticeSeq = ?";
			PreparedStatement stat = conn.prepareStatement(sql);
			
			stat.setString(1, noticeSeq);
			
			ResultSet rs = stat.executeQuery();
			
			if(rs.next()) {
				return rs.getInt("cnt");
			}
		} catch (Exception e) {
			System.out.println("NoticeDAO.isReadAnonymous :" + e.toString());
		}
		
		return 0;
	}
}


