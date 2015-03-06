package edu.hit.mrp.calendar;


import java.awt.event.MouseEvent;



/**
 *author mengke 
 * e_mail wqjsir@foxmail.com 
 *version 2010-12-9下午11:34:16
 *日期组件事件
 */
public interface  DayClickListener {

	/**
	 * 一个日期组件被点击事件
	 * @param day
	 */
	public  void dayClicked(DayPanel day,MouseEvent e);

}
