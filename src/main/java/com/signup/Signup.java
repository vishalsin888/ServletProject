package com.signup;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.DbUtil;

@WebServlet("/signup")
public class Signup extends HttpServlet {
	public Connection conn= null;
	public void init(ServletConfig config) throws ServletException {
		try {
			 conn = DbUtil.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ename = request.getParameter("empName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
//		System.out.println(ename + " "+email + " "+ password + " "+ role);
		
		String query = "insert into users values(?,?,?,?)";
		PreparedStatement pst = null;
		try {
			 pst = conn.prepareStatement(query);
			 pst.setString(1, ename);
			 pst.setString(2, email);
			 pst.setString(3, password);
			 pst.setString(4, role);
			 int result = pst.executeUpdate();
			 
			 if(result > 0) {
				 out.println("<h2 style='background : cyan'>Successfully Reg. as " +role +"</h2>");
//				 out.println("<a href='login.html'>Go to Login Page</a>");
				 RequestDispatcher rd = request.getRequestDispatcher("login.html");
				 rd.include(request, response);
				 
			 }else {
				 out.println("<h2>failed to  Reg. as " +role+"</h2>");
//				 out.println("<a href='index.html'>Go to signup Page</a>");
				 RequestDispatcher rd = request.getRequestDispatcher("index.html");
				 rd.include(request, response);
			 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
