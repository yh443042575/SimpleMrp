package edu.hit.mrp.tool;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TestJava {

	public static void main(String args[])
	{
		Date now = new Date(); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//���Է�����޸����ڸ�ʽ
		String hehe = dateFormat.format( now ); 
		System.out.println(hehe); 
	}
}
