package edu.hit.mrp.dao;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.hit.mrp.tool.DBUtil;

public class VacationDao {

	DBUtil dbUtil=new DBUtil();
	
	public int getVacationCount() throws Exception{
		
		Connection myConnection=dbUtil.ConnectMySql();
		Statement stat = myConnection.createStatement();
		
		ResultSet rs =stat.executeQuery("select * from vacation");
		int i=0;
		while(rs.next())
		{
			rs.getString("date");
			i++;
		}
		
		stat.close();
		myConnection.close();
		
		return i;
	}
	
	public List<String> getVacationList(String deadline) throws Exception{
		List<String> vacationliList=new ArrayList<String>();
		Date d;
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		Connection myConnection = dbUtil.ConnectMySql();
		Statement stat= myConnection.createStatement();
		
		ResultSet rs=stat.executeQuery("select * from vacation");
		while(rs.next()){
			
			vacationliList.add(sdf.format(rs.getDate("date")));
		}
		
		stat.close();
		myConnection.close();
		return vacationliList;
		
		
	}
}
