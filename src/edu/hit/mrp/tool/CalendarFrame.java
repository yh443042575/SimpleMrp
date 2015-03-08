package edu.hit.mrp.tool;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import javax.swing.JOptionPane;

class CalendarBean { // 定义一个日历类
	String day[]; // 天数数组
	int year = 2011, month = 9; // 给定一个初始年月

	public void setYear(int year) {
		this.year = year;
	}

	public int getYear() {
		return year;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getMonth() {
		return month;
	}

	public String[] getCalendar() { // 获得日历
		String a[] = new String[42]; // 定义一个以字符串数组
		Calendar 日历 = Calendar.getInstance();
		日历.set(year, month - 1, 1);
		int 星期几 = 日历.get(Calendar.DAY_OF_WEEK) - 1;
		int day = 0;
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
				|| month == 10 || month == 12) {
			day = 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			day = 30;
		}
		if (month == 2) {
			if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
				day = 29;
			} else {
				day = 28;
			}
		}
		for (int i = 星期几, n = 1; i < 星期几 + day; i++) {
			a[i] = String.valueOf(n);
			n++;
		}
		return a;
	}
}

public class CalendarFrame extends Panel implements ActionListener {
	Label labelDay[] = new Label[42]; // 用来输出日历的天的数组
	Button titleName[] = new Button[7]; // 周一到周天的按键
	String name[] = { "日", "一", "二", "三", "四", "五", "六" };
	TextField text1, text2; // 定义输入的年份和月份
	Button nextMonth, previousMonth, Enter; // 下个月，上个月，确定
	Label lab1, lab2, lab3; // 几个文本类型的字符串
	int year = 2012, month = 9;
	CalendarBean calendar; // 顶一个量
	Label showMessage = new Label("", Label.CENTER); // 定义一个用于显示 当前年月的label
														// 表明，标签上应为中心。

	public CalendarFrame() { // 窗体类

		Panel pCenter = new Panel(); // 镶嵌
		pCenter.setLayout(new GridLayout(7, 7));
		for (int i = 0; i < 7; i++) { // 给周日到周六的button添加显示文本
			titleName[i] = new Button(name[i]);
			pCenter.add(titleName[i]);
		}
		for (int i = 0; i < 42; i++) {
			labelDay[i] = new Label("", Label.CENTER);
			pCenter.add(labelDay[i]);
		}
		calendar = new CalendarBean();
		calendar.setYear(year); // 设定年
		calendar.setMonth(month);
		String day[] = calendar.getCalendar(); // 设定天
		for (int i = 0; i < 42; i++) { // 给日历位置循环添加显示日历天
			labelDay[i].setText(day[i]);
		}
		lab1 = new Label("请输入日期"); // 调用一个方法 new一个对象
		lab2 = new Label("年份");
		lab3 = new Label("月份");
		Enter = new Button("确定");
		text1 = new TextField(10);
		text2 = new TextField(5);
		nextMonth = new Button("下月");
		previousMonth = new Button("上月");
		Enter.addActionListener(this); // 添加指定的动作侦听器
		nextMonth.addActionListener(this);
		previousMonth.addActionListener(this);
		
		Panel pNorth = new Panel(), pSouth = new Panel();
		pNorth.add(lab1);//输入日期
		pNorth.add(lab2);//年份
		pNorth.add(text1);
		pNorth.add(lab3);//月份
		pNorth.add(text2);
		pNorth.add(Enter);//确定
		pNorth.add(previousMonth);//上月
		pNorth.add(nextMonth);//下月
		pSouth.add(showMessage);
		showMessage.setText("日历：" + calendar.getYear() + "年"
				+ calendar.getMonth() + "月");
		ScrollPane scrollPane = new ScrollPane(); // 实现单个子组件的自动水平和/或垂直滚动的容器类
		scrollPane.add(pCenter);
		add(scrollPane, BorderLayout.CENTER);
		add(pNorth, BorderLayout.NORTH);
		add(pSouth, BorderLayout.SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nextMonth) { // 如果获得的操作时下个月的
			month = month + 1;
			if (month > 12)
				month = 1;
			calendar.setMonth(month);
			String day[] = calendar.getCalendar();
			for (int i = 0; i < 42; i++) {
				labelDay[i].setText(day[i]);
			}
		} else if (e.getSource() == previousMonth) {
			month = month - 1;
			if (month < 1)
				month = 12;
			calendar.setMonth(month);
			String day[] = calendar.getCalendar();
			for (int i = 0; i < 42; i++) {
				labelDay[i].setText(day[i]);
			}
		} else {
			String yea = text1.getText();
			String mon = text2.getText();
			try {
				year = Integer.parseInt(yea); // 把string转成int类型
				month = Integer.parseInt(mon);
				if (month > 12 || month < 1 || year < 1) { // 错误输入的处理
					JOptionPane.showMessageDialog(null, "请输入正确月份或月份");
					return;
				} else {
					calendar.setYear(year);
					calendar.setMonth(month);
				}
				String day[] = calendar.getCalendar();
				for (int i = 0; i < 42; i++) {
					labelDay[i].setText(day[i]);
				}
			} catch (NumberFormatException ee) {
				JOptionPane.showMessageDialog(null, "请输入正确的年份及月份");
			}
		}
		showMessage.setText("日历：" + calendar.getYear() + "年"
				+ calendar.getMonth() + "月");
	}
}


