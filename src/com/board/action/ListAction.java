package com.board.action;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.board.beans.Board;
import com.board.controller.CommandAction;
import java.sql.*;

public class ListAction implements CommandAction{

	@Override
	public String requestPro(HttpServletRequest request,
			HttpServletResponse response) throws Throwable {

		try {
			request.setCharacterEncoding("euc-kr");
			String driverName = "org.gjt.mm.mysql.Driver";
			String url = "jdbc:mysql://localhost:3306/Pengpark";
			
			ResultSet rs = null;
			
			Class.forName(driverName);
			Connection con = DriverManager.getConnection(url, "Pengpark", "miswneh8");
			System.out.println("데이터베이스 접속 성공");
			
			Statement stmt = con.createStatement();
			String sql = "select * from board order by idx asc";
			rs = stmt.executeQuery(sql);
			
			ArrayList<Board> articleList = new ArrayList<Board>();
			
			while(rs.next()) {
				Board article = new Board();
				article.setIdx(rs.getInt("idx"));
				article.setTitle(rs.getString("title"));
				article.setWriter(rs.getString("writer"))	;
				article.setRegdate(rs.getString("regdate"));
				article.setCount(rs.getInt("count"));
				articleList.add(article);
			}
			request.setAttribute("articleList", articleList);
			
			con.close();
			
		} catch (Exception e) {
			System.out.println("데이터베이스 접속 실패");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return "list.jsp";
	}

}
