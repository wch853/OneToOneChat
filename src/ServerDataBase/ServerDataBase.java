package ServerDataBase;

import java.sql.*;

/**
 * 服务器端数据库
 *
 */
public class ServerDataBase {
	private static final String URL = "jdbc:mysql://127.0.0.1:3306/internet";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "admin";
	private Connection conn;
	public ServerDataBase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection() {
		try {
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}

