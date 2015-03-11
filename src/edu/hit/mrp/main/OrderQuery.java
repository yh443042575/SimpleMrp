package edu.hit.mrp.main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import edu.hit.mrp.dao.OrderDao;
import edu.hit.mrp.dao.StockTransitDao;
import edu.hit.mrp.dao.VacationDao;
import edu.hit.mrp.main.VacationSettings.labelDayMonitor;

public class OrderQuery extends Frame implements ActionListener {

	Map<String, Boolean> setVacationMap = new HashMap<String, Boolean>();// 用于保存对日期的修改
	List<String> monthOrderList = new ArrayList<String>();// 用于显示当前月份数据库中存储的假期
	OrderDao orderDao = new OrderDao();
	StockTransitDao stockTransitDao =new StockTransitDao();

	Label labelDay[] = new Label[42]; // 用来输出日历的天的数组
	Button titleName[] = new Button[7]; // 周一到周天的按键
	String name[] = { "日", "一", "二", "三", "四", "五", "六" };
	TextField text1, text2; // 定义输入的年份和月份
	Button nextMonth, previousMonth, Enter; // 下个月，上个月，确定
	Label lab1, lab2, lab3; // 几个文本类型的字符串
	int year = 2015, month = 3;// 当前年份月份
	CalendarBean calendar; // 顶一个量
	Label showMessage = new Label("", Label.CENTER); // 定义一个用于显示 当前年月的label

	// 表明，标签上应为中心。
	public void lauchFrame(Frame frame) {

		final Frame f = frame;
		this.setResizable(false);

		Panel pCenter = new Panel(); // 镶嵌
		pCenter.setLayout(new GridLayout(7, 7));
		for (int i = 0; i < 7; i++) { // 给周日到周六的button添加显示文本
			titleName[i] = new Button(name[i]);
			pCenter.add(titleName[i]);
		}
		for (int i = 0; i < 42; i++) {
			labelDay[i] = new Label("", Label.CENTER);
			pCenter.add(labelDay[i]);
			labelDay[i].addMouseListener(new labelDayMonitor());
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
		pNorth.add(lab1);// 输入日期
		pNorth.add(lab2);// 年份
		pNorth.add(text1);
		pNorth.add(lab3);// 月份
		pNorth.add(text2);
		pNorth.add(Enter);// 确定
		pNorth.add(previousMonth);// 上月
		pNorth.add(nextMonth);// 下月
		pSouth.add(showMessage);
		showMessage.setText("日历：" + calendar.getYear() + "年"
				+ calendar.getMonth() + "月");
		ScrollPane scrollPane = new ScrollPane(); // 实现单个子组件的自动水平和/或垂直滚动的容器类
		scrollPane.add(pCenter);
		add(scrollPane, BorderLayout.CENTER);
		add(pNorth, BorderLayout.NORTH);
		add(pSouth, BorderLayout.SOUTH);
		try {
			monthOrderList = orderDao.getMonthOrderList("2015-03");
			resetLabelColor();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// -------------------------------------------------------------
		this.setTitle("设置假期");
		this.setLocation(300, 100);
		this.setSize(700, 450);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				f.setEnabled(true);
				f.setFocusable(true);
				dispose();
			}

		});

		this.setVisible(true);
		// --------------------------------------------------------------

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

			resetLabelColor();// 根据当前月历设置假期
		} else if (e.getSource() == previousMonth) {// 如果获得的操作时下个月的上月
			month = month - 1;
			if (month < 1)
				month = 12;
			calendar.setMonth(month);
			String day[] = calendar.getCalendar();
			for (int i = 0; i < 42; i++) {
				labelDay[i].setText(day[i]);
			}
			resetLabelColor();
		} else {// 确定按钮
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

				resetLabelColor();
			} catch (NumberFormatException ee) {
				JOptionPane.showMessageDialog(null, "请输入正确的年份及月份");
			}
		}
		showMessage.setText("日历：" + calendar.getYear() + "年"
				+ calendar.getMonth() + "月" );
	}

	/**
	 * 重置日历（用于上月下月与确定按钮中）
	 */
	private void resetLabelColor() {
		String date = calendar.getYear() + "-" + calendar.getMonth();
		try {
			this.monthOrderList = orderDao.getMonthOrderList(date);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Label l : labelDay) {
			if (!hasOrder(l))
				l.setBackground(Color.WHITE);
			else
				l.setBackground(Color.YELLOW);
		}
	}

	private boolean hasOrder(Label l) {// 判断当前label对应的日期是否被设置为假期（数据库中假期+假期map中的设置）
		String day = l.getText();
		if (day == null)
			return false;
		else {
			if (day.length() == 1)
				day = "0" + day;
			String date = calendar.getYear() + "-" + calendar.getMonth() + "-"
					+ day;
			if (!monthOrderList.contains(day))
				return false;
			else
				return true;
		}
	}

	class labelDayMonitor implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			Label label = (Label) e.getSource();
			String title ="今日生产任务与库存量";
			String date = calendar.getYear() + "-" + calendar.getMonth() + "-"
					+ label.getText();
			if (label.getBackground().equals(Color.YELLOW)) {
				try {
					String dayOrderStock = orderDao.queryOrders(date);
					dayOrderStock+= "\n库存量：\n"+stockTransitDao.getStockInformation(date);
					JOptionPane.showMessageDialog(null, dayOrderStock,title,
							JOptionPane.DEFAULT_OPTION);

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			else if(label.getBackground().equals(Color.WHITE)){
				try {
					StockTransitDao stockTransitDao =new StockTransitDao();
					String dayStock = stockTransitDao.getStockInformation(date);
					JOptionPane.showMessageDialog(null, "库存量：\n"+dayStock,title,
							JOptionPane.DEFAULT_OPTION);

				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}
}
