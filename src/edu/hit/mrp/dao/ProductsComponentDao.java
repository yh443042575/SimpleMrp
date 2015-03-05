package edu.hit.mrp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import edu.hit.mrp.bean.ProductsComponent;
import edu.hit.mrp.tool.DBUtil;

public class ProductsComponentDao {

	private DBUtil dbUtil = new DBUtil();

	public List<ProductsComponent> getSolution(String solutionName) throws Exception{
		List<ProductsComponent> solution=new ArrayList<ProductsComponent>();
		Connection myConnection=dbUtil.ConnectMySql();
		Statement stat=myConnection.createStatement();
		ResultSet rs=stat.executeQuery("select * from PRODUCTS_COMPONENT where DISPLAY_TEXT = '"+solutionName+"';");
		while(rs.next())
		{
			solution.add(new ProductsComponent(
					rs.getInt("@id"), 
					rs.getInt("ID"), 
					rs.getInt("PARENT_ID"), 
					rs.getInt("COUNT"), 
					rs.getInt("PROJECT_TIME_LIMIT"), 
					rs.getString("DISPLAY_TEXT"), 
					rs.getString("COMPONENT_NAME"), 
					rs.getString("COMPONENT_CODE")));
		}
		stat.close();
		myConnection.close();
		return solution;
	}
}
