package util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class DBManager {
	public static Connection getConnection() {
		Connection conn = null;
		
		try {
			Context initctx = new InitialContext();
			
			Context envctx = (Context) initctx.lookup("java:/comp/env");
			
			DataSource ds =(DataSource) envctx.lookup("jdbc/OracleDB");
			//jdbc/myoracle이란 이름의 객체를 찾아서 DataSource가 받는다.
			
			conn = ds.getConnection();
			//ds가 생성됐으니 Connection 을 구한다.
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return conn;
		
	}
	
	public static void close(Connection conn, Statement stmt, ResultSet rs) {
		try {
			rs.close();
			stmt.close();
			conn.close();

		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Connection conn, Statement stmt) {
		try {
			stmt.close();
			conn.close();
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
