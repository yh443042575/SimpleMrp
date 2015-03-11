package edu.hit.mrp.dao;

import java.awt.Component;
import java.security.interfaces.RSAKey;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import edu.hit.mrp.bean.OrderBean;
import edu.hit.mrp.bean.StockTransitBean;
import edu.hit.mrp.main.DateCalculator;
import edu.hit.mrp.main.VacationSettings;
import edu.hit.mrp.tool.DBUtil;

public class StockTransitDao {

	DBUtil dbUtil = new DBUtil();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	String[] baseComponent = { "O", "P", "R" };

	/**
	 * 若有今日库存记录则将今日库存记录提取出来，否则提取最近一次的库存记录
	 * 
	 * @param date
	 * @return
	 */
	public String getStockInformation(String date) {

		String information = "";
		try {
			Connection myConnection = dbUtil.ConnectMySql();
			PreparedStatement prep = myConnection
					.prepareStatement("select * from stock_transit where date = ? and material = ?");

			for (String s : baseComponent) {
				prep.setString(1, date);
				prep.setString(2, s);
				ResultSet rs = prep.executeQuery();
				if(!rs.next()) {	
						rs.close();
						ResultSet rs1=myConnection.createStatement().executeQuery("select MAX(date) from stock_transit where material = '"+s+"' and date<'"+date+"'");
						rs1.next();
						prep.setString(1, rs1.getString("MAX(date)"));
						rs = prep.executeQuery();
						rs.next();
				}
				information += rs.getString("material") + ":";
				information += rs.getInt("in_stock") + "\n";	
			}
		myConnection.close();	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return information;

	}

	/**
	 * 根据每个基础组件和每个基础组件的交付时间更新库存表
	 * 
	 * @param orderBeanList
	 * @param material
	 * @param count
	 */
	public void flashStockAndTransit(List<OrderBean> orderBeanList)
			throws Exception {
		;
		for (OrderBean component : orderBeanList) {
			if (hasNotSetDate(component)) {// 如果有未设置的日子

				Date smDate, bDate;
				List<String> workDaysList;

				DateCalculator dateCalculator = new DateCalculator();
				try {
					Connection myConnection = dbUtil.ConnectMySql();
					Statement stat = myConnection.createStatement();
					ResultSet rs = stat
							.executeQuery("SELECT MAX(date) FROM stock_transit where material ='"
									+ component.getMaterial() + "'");
					while (rs.next()) {
						smDate = sdf.parse(rs.getString("MAX(date)"));

						bDate = getPreviousDay(component.getDate(), 1);
						int days = dateCalculator.daysBetween(smDate, bDate);

						workDaysList = dateCalculator.getDateList(sdf
								.format(bDate), days);

						StockTransitBean purchaseComponent = getRecentlyComponent(
								component.getMaterial(), sdf.format(smDate));
						Statement stat2 = myConnection.createStatement();

						for (String s : workDaysList) {
							stat2
									.execute("insert into stock_transit (date,material,in_stock,in_transit) values ('"
											+ s
											+ "','"
											+ purchaseComponent.getMaterial()
											+ "','"
											+ purchaseComponent.getInStock()
											+ "','"
											+ purchaseComponent.getInTransit()
											+ "')");
						}

						stat2.close();
					}
					rs.close();
					stat.close();
					myConnection.close();

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

			else {

			}
		}

	}

	public boolean hasNotSetDate(OrderBean orderBean) {

		return true;
	}

	/**
	 * 在下达计划之前，执行计划与已知库存之间的空档
	 * 
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public StockTransitBean getRecentlyComponent(String componentName,
			String date) throws Exception {

		StockTransitBean purchaseComponent = null;
		Connection myConnection = dbUtil.ConnectMySql();
		Statement stat = myConnection.createStatement();
		ResultSet rs = stat
				.executeQuery("SELECT * FROM stock_transit WHERE date='" + date
						+ "' and material ='" + componentName + "'");

		while (rs.next()) {
			purchaseComponent = new StockTransitBean(date, rs
					.getString("material"), rs.getInt("in_stock"), rs
					.getInt("in_transit"));
		}

		return purchaseComponent;

	}

	public void insertStockTransit(Date date, String material, int inStock,
			int inTransit) throws Exception {
		Connection myConnection = dbUtil.ConnectMySql();
		PreparedStatement prep = myConnection
				.prepareStatement("insert into stock_transit (date,material,in_stock,in_transit) values (?,?,?,?)");

		prep.setString(1, sdf.format(date));
		prep.setString(2, material);
		prep.setInt(3, inStock);
		prep.setInt(4, inTransit);

		prep.execute();
		prep.close();
		myConnection.close();

	}

	public Date getPreviousDay(Date date, int pre) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.setTimeInMillis(cal.getTimeInMillis() - (1000L * 3600 * 24) * pre);
		return cal.getTime();

	}

}
