package edu.hit.mrp.dao;

import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.hit.mrp.tool.DBUtil;

public class OrderDao {

	DBUtil dbUtil = new DBUtil();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	public boolean insertOrder(String[] orderList, String orderName)
			throws ParseException {

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

				stat
						.execute("insert into `order` (date,material,count,order_name) values ('"
								+ date
								+ "','"
								+ material
								+ "','"
								+ count
								+ "','" + orderName + "')");
			}

			stat.close();
			myConnection.close();
			return true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return false;
		}

	}

	public List<String> getMonthOrderList(String date) throws Exception {
		List<String> monthOrderliList = new ArrayList<String>();

		Connection myConnection = dbUtil.ConnectMySql();
		Statement stat = myConnection.createStatement();

		ResultSet rs = stat
				.executeQuery("select date from `order` where date BETWEEN '"
						+ date + "-01' and '" + date + "-31' GROUP BY date");
		while (rs.next()) {
			monthOrderliList.add(sdf.format(rs.getDate("date")).split("-")[2]);
		}

		stat.close();
		myConnection.close();
		return monthOrderliList;
	}

	public String queryOrders(String date) throws Exception {
		StringBuffer orderliList = new StringBuffer();
		Connection myConnection = dbUtil.ConnectMySql();
		Statement stat = myConnection.createStatement();

		ResultSet rs = stat.executeQuery("select * from `order` where date='"
				+ date + "'");
		while (rs.next()) {
			orderliList.append((rs.getString("order_name") + " "
					+ rs.getString("material") + "£º" + rs.getInt("count"))
					+ "\n\r");
		}

		stat.close();
		myConnection.close();
		return orderliList.toString();
	}

	public List<String> getOrderChoice() throws Exception {
		List<String> orderChoices = new ArrayList<String>();
		Connection myConnection = dbUtil.ConnectMySql();
		Statement stat = myConnection.createStatement();

		ResultSet rs = stat
				.executeQuery("select order_name from `order` GROUP BY order_name");
		while (rs.next()) {
			orderChoices.add(rs.getString("order_name"));
		}

		stat.close();
		myConnection.close();
		return orderChoices;

	}

	public String queryOrderInformation(String selectedItem) throws Exception {
		String orderInformation = "";
		Connection myConnection = dbUtil.ConnectMySql();
		Statement stat = myConnection.createStatement();

		ResultSet rs = stat
				.executeQuery("select order_name from `order` where order_name = '"
						+ selectedItem + "'GROUP BY order_name");
		while (rs.next()) {
			orderInformation += rs.getString("order_name");
		}

		stat.close();
		myConnection.close();
		return orderInformation;
	}

	public boolean removeOrder(String selectedItem) {

		Connection myConnection = dbUtil.ConnectMySql();
		Statement stat;
		try {
			stat = myConnection.createStatement();
			stat.execute("DELETE FROM `order` WHERE order_name ='"
					+ selectedItem + "'");
			stat.close();
			myConnection.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public void insertSubPlan(Date date, String order_name, String material,
			int count) throws Exception {
		Connection myConnection = dbUtil.ConnectMySql();
		PreparedStatement prep = myConnection
				.prepareStatement("insert into `order` (date,material,count,order_name) values (?,?,?,?)");

		prep.setString(1, sdf.format(date));
		prep.setString(2, material);
		prep.setInt(3, count);
		prep.setString(4, order_name);

		prep.execute();
		prep.close();
		myConnection.close();

	}
}
