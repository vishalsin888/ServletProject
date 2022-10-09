package com.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbUtil {

	public static String URL = "jdbc:mysql://localhost:3306/dbtest";
	public static String USER = "root";
	public static String PASSWORD = "root";
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection(URL,USER,PASSWORD);
		System.out.println("DB Connected");
		return conn;
	}
}
