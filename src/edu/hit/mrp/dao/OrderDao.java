package edu.hit.mrp.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import edu.hit.mrp.tool.DBUtil;

public class OrderDao {

	DBUtil dbUtil = new DBUtil();

	public boolean insertOrder(String[] orderList, String orderName) throws ParseException  {

		String date;
		String material;
		int count;


		Connection myConnection = dbUtil.ConnectMySql();
		try {

			Statement stat = myConnection.createStatement();
			for (String s : orderList) {
				
				String s1[] = s.split("£¬");
				date = s1[0];
			
				String s2[] = s1[1].split("¸ö");
				material = s2[1];

				count = Integer.parseInt(s2[0].split("³ö")[1]);

				stat.execute("insert into `order` (date,material,count,order_name) values ('"
								+ date + "','" + material + "','"+count+"','"+orderName+"')");
			}
			
			stat.close();
			myConnection.close();
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}

	}
}
