package edu.hit.mrp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import edu.hit.mrp.tool.DBUtil;

public class VacationDao {

	DBUtil dbUtil = new DBUtil();

	public int getVacationCount() throws Exception {

		Connection myConnection = dbUtil.ConnectMySql();
		Statement stat = myConnection.createStatement();

		ResultSet rs = stat.executeQuery("select * from vacation");
		int i = 0;
		while (rs.next()) {
			rs.getString("date");
			i++;
		}

		stat.close();
		myConnection.close();

		return i;
	}

	public List<String> getVacationList(String deadline) throws Exception {
		List<String> vacationliList = new ArrayList<String>();
		Date d;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Connection myConnection = dbUtil.ConnectMySql();
		Statement stat = myConnection.createStatement();

		ResultSet rs = stat.executeQuery("select * from vacation");
		while (rs.next()) {

			vacationliList.add(sdf.format(rs.getDate("date")));
		}

		stat.close();
		myConnection.close();
		return vacationliList;

	}

	public List<String> getMonthVacationList(String date)throws Exception {// 根据月份得到日期
		
		List<String> monthVacationliList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Connection myConnection = dbUtil.ConnectMySql();
		Statement stat = myConnection.createStatement();
		
		ResultSet rs=stat.executeQuery("select * from vacation where date BETWEEN '"+date+"-01' and '"+date+"-31'");
		while(rs.next()){
			monthVacationliList.add(sdf.format(rs.getDate("date")).split("-")[2]);
		}
		
		stat.close();
		myConnection.close();
		return monthVacationliList;
	}
	
	public boolean setVacation(Map<String,Boolean> setVacationMap){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Connection myConnection = dbUtil.ConnectMySql();
		Statement stat;
		try {
			stat = myConnection.createStatement();
		 
		
		for(Map.Entry<String, Boolean> entry:setVacationMap.entrySet()){
			String date=entry.getKey();
			boolean isVacation=entry.getValue();
			if (isVacation) {
				
					stat.execute("insert into vacation VALUE('"+date+"')");				 
			}
			else {
				stat.execute("DELETE from vacation where date='"+date+"'");
			}			
		}
		stat.close();
		myConnection.close();
		return true;
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		
	}
}
