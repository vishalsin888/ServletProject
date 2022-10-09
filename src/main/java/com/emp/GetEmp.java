package com.emp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.DbUtil;

@WebServlet("/getEmp")
public class GetEmp extends HttpServlet {
	public Connection conn = null;
	public void init(ServletConfig config) throws ServletException {
		try {
			conn = DbUtil.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		PreparedStatement pst = null;
		String query = "select * from users where empname like ?";
		int rowCount = 0;
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1, request.getParameter("ename"));
			ResultSet result = pst.executeQuery();
			out.println("<table><tr><th>Name</th><th>Email</th><th>Password</th><th>Role</th></tr>");
			while(result.next()) {
				rowCount ++;
				out.println("<tr><td>"+result.getString(1)+"</td><td>"+result.getString(2)+"</td><td>"+result.getString(3)+"</td><td>"+result.getString(4)+"</td></tr>");
			}
			out.println("</table>");
			out.println("<h1>Total "+rowCount+" Records</h1>");
			out.println("<a href='EmpSearch.html'></a>");
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
