package edu.hit.mrp.tool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

	public Connection ConnectMySql() {

		String url = "jdbc:mysql://localhost:3306/test";
		String username = "root";
		String password = "8828092";
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("�Ҳ�����Ӧ��������");
			// TODO: handle exception
		}

		try {
			con = DriverManager.getConnection(url, username, password);
		} catch (SQLException se) {
			System.out.println("���ݿ�����ʧ�ܣ�");
			se.printStackTrace();
		}
		return con;
	}
}
