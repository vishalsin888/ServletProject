package com.login;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.DbUtil;

@WebServlet("/login")
public class Login extends HttpServlet {
	public Connection conn = null;
	public void init(ServletConfig config) throws ServletException {
		try {
			conn = DbUtil.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ename = request.getParameter("empName");
		String password = request.getParameter("password");
		String[] roles = request.getParameterValues("role");
		String myRole = "";
		if(roles == null) {
			myRole = "Employee";
		}else {
			myRole = "Admin";
		}
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		PreparedStatement pst = null;
		String query = "select count(*) from users where empname = ? and password = ? and role = ?";
		try {
			pst = conn.prepareStatement(query);
			pst.setString(1,ename );
			pst.setString(2,password );
			pst.setString(3,myRole);
			ResultSet result = pst.executeQuery();
			//BFR<-- cursor   ALR
			result.next();
			if(result.getInt(1) > 0) {
				out.println("<h2>Successfully Logged In. as " +myRole +"</h2>");
				if(myRole == "Admin") {
					out.println("<form action='getAllEmps'><input type=\"submit\" value=\"Get All Emps\"></form>");
					out.println("<form action='getEmp'><input type=\"submit\" value=\"Search Emp\"></form>");					
				}else {
					
				}
			}else {
				out.println("<h2 class='color: red;'>User Not Found </h2>");
//				out.println("<a href='login.html'>Try Again</a>");
				RequestDispatcher rd = request.getRequestDispatcher("login.html");
				rd.include(request, response);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
