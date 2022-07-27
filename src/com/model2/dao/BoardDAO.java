package com.model2.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.model2.dao.BoardDAO;
import com.model2.dto.BoardVO;

import util.DBManager;

public class BoardDAO {

private static BoardDAO instance = new BoardDAO();
	
	public static BoardDAO getInstance() {
		return instance;
	}
	
	//게시글 페이지 카운트
	public int getListCount() {
	      
	      int x = 0;
	      String sql = "select count(*) from board";
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      
	      try{
	         
	         conn = DBManager.getConnection();
	         pstmt=conn.prepareStatement(sql);
	         rs = pstmt.executeQuery();
	         
	         if(rs.next()) {
	            x = rs.getInt(1);
	         }
	      }catch(Exception ex){
	         System.out.println("getListCount 에러: " + ex);         
	      }finally{
	         if(rs!=null) try{rs.close();}catch(SQLException ex){}
	         if(pstmt!=null) try{pstmt.close();}catch(SQLException ex){}
	         if(conn!=null) try{conn.close();}catch(SQLException ex){}
	      }
	      
	      return x;
	   }
	
	public    List<BoardVO> getBoardList(int page,int limit){
	      String sql = "select * from "+
	            "(select rownum rnum,num,name,"+
	            "email,pass,title,content,"+
	            "readcount,writedate from "+
	            "(select * from board order by writedate desc)) "+
	            "where rnum>=? and rnum<=?";
	      
	      List<BoardVO> list = new ArrayList<>();
	      
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      ResultSet rs = null;
	      
	      int startrow = (page - 1) * 10 + 1;
	      int endrow = startrow + limit - 1;
	      
	      try{
	         
	         conn = DBManager.getConnection();
	         pstmt=conn.prepareStatement(sql);
	         
	         pstmt.setInt(1, startrow);
	         pstmt.setInt(2, endrow);   
	         rs = pstmt.executeQuery();
	         
	         while(rs.next()) {
	            BoardVO bVo = new BoardVO();
	            bVo.setNum(rs.getInt("num"));
	            bVo.setName(rs.getString("name"));
	            bVo.setEmail(rs.getString("email"));
	            bVo.setPass(rs.getString("pass"));
	            bVo.setTitle(rs.getString("title"));
	            bVo.setContent(rs.getString("content"));
	            bVo.setReadcount(rs.getInt("readcount"));
	            bVo.setWritedate(rs.getTimestamp("writedate"));
	            list.add(bVo);
	         }
	         
	      }catch(Exception ex){
	         System.out.println("getListCount 에러: " + ex);         
	      }finally {
	         DBManager.close(conn, pstmt, rs);
	      }
	            
	      return list;
	   }

	public List<BoardVO> selectAB() {
		String sql = "select * from board order by num desc";
	
		
		List<BoardVO> list = new ArrayList<BoardVO>();
		//VO에는 object형태의 데이터가 있는데 private으로 되어있어서 가져오려면
		// ArrayList<>에 넣어 제네릭화 시킨다 그리고 List에 넣어서 가져올수있게한다.
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try { //연결을 시도할것이고. 잘 되엇는지 확인하는 절차 그리고 닫아줌
			conn = DBManager.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()) {
				BoardVO bVo = new BoardVO();
				bVo.setNum(rs.getInt("num"));
				bVo.setName(rs.getString("name"));
				bVo.setEmail(rs.getString("email"));
				bVo.setPass(rs.getString("pass"));
				bVo.setTitle(rs.getString("title"));
				bVo.setContent(rs.getString("content"));
				bVo.setReadcount(rs.getInt("readcount"));
				bVo.setWritedate(rs.getTimestamp("writedate"));
				
				list.add(bVo);
	
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			DBManager.close(conn,stmt,rs);
		}
		return list;
	}
	
	public void insertBoard(BoardVO bVo) {
		String sql="insert into board(" 
	+ " num, name, email, pass, title, content)" 
	+ " values(board_seq.nextval, ?, ?, ?, ?, ?)";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bVo.getName());
			pstmt.setString(2, bVo.getEmail());
			pstmt.setString(3, bVo.getPass());
			pstmt.setString(4, bVo.getTitle());
			pstmt.setString(5, bVo.getContent());
			pstmt.executeUpdate();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
	}
	
	public void updateReadCount(String num) {
		String sql = "update board set readcount = readcount+1 where num=?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = DBManager.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, num);
			
			pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt);
		}
	}
	
	public BoardVO selectOneBoardByNum(String num) {
		// 게시판 글 상세 내용 보기 : 글 번호로 찾아온다 : 실패 null
		String sql = "select * from board where num=?";
		
		BoardVO bVo = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		

			try {
				conn = DBManager.getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,  num);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					bVo = new BoardVO();
					
					bVo.setNum(rs.getInt("num"));
					bVo.setName(rs.getString("name"));
					bVo.setPass(rs.getString("pass"));
					bVo.setEmail(rs.getString("email"));
					bVo.setTitle(rs.getString("title"));
					bVo.setContent(rs.getString("content"));
					bVo.setWritedate(rs.getTimestamp("writedate"));
					bVo.setReadcount(rs.getInt("readcount"));
					
				}
			}catch(Exception e){
				e.printStackTrace();
			}finally {
				DBManager.close(conn, pstmt, rs);
			}
			
		
		return bVo;
		
	}
	
	public void updateBoard(BoardVO bVo) {
	      String sql = "update board set name=?, email=?, pass=?," + "title=?, content=? where num=?";
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      try {
	         conn = DBManager.getConnection();
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, bVo.getName());
	         pstmt.setString(2, bVo.getEmail());
	         pstmt.setString(3, bVo.getPass());
	         pstmt.setString(4, bVo.getTitle());
	         pstmt.setString(5, bVo.getContent());
	         pstmt.setInt(6, bVo.getNum());
	         pstmt.executeUpdate();// 쿼리문 실행한다.
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         DBManager.close(conn, pstmt);
	      }
	   }
	
	public BoardVO checkPassWord(String pass, String num) {
		String sql = "select * from board where pass=? and num=?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		BoardVO bVo = null;
		try {
			conn = DBManager.getConnection();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, pass);
			pstmt.setString(2, num);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				bVo = new BoardVO();
				
				bVo.setNum(rs.getInt("num"));
				bVo.setName(rs.getString("name"));
				bVo.setEmail(rs.getString("email"));
				bVo.setPass(rs.getString("pass"));
				bVo.setTitle(rs.getString("title"));
				bVo.setContent(rs.getString("content"));
				bVo.setReadcount(rs.getInt("readcount"));
				bVo.setWritedate(rs.getTimestamp("writedate"));
				
			}
		}catch(SQLException e){
			e.printStackTrace();
			
		}
		return bVo;
	}
	
	public void deleteBoard(String num) {
	      String sql = "delete board where num=?";
	      Connection conn = null;
	      PreparedStatement pstmt = null;
	      try {
	         conn = DBManager.getConnection();
	         pstmt = conn.prepareStatement(sql);
	         pstmt.setString(1, num);
	
	         pstmt.executeUpdate();// 쿼리문 실행한다.
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
	   }
}

