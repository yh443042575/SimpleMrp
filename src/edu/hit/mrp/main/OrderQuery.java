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

	Map<String, Boolean> setVacationMap = new HashMap<String, Boolean>();// ���ڱ�������ڵ��޸�
	List<String> monthOrderList = new ArrayList<String>();// ������ʾ��ǰ�·����ݿ��д洢�ļ���
	OrderDao orderDao = new OrderDao();
	StockTransitDao stockTransitDao =new StockTransitDao();

	Label labelDay[] = new Label[42]; // ��������������������
	Button titleName[] = new Button[7]; // ��һ������İ���
	String name[] = { "��", "һ", "��", "��", "��", "��", "��" };
	TextField text1, text2; // �����������ݺ��·�
	Button nextMonth, previousMonth, Enter; // �¸��£��ϸ��£�ȷ��
	Label lab1, lab2, lab3; // �����ı����͵��ַ���
	int year = 2015, month = 3;// ��ǰ����·�
	CalendarBean calendar; // ��һ����
	Label showMessage = new Label("", Label.CENTER); // ����һ��������ʾ ��ǰ���µ�label

	// ��������ǩ��ӦΪ���ġ�
	public void lauchFrame(Frame frame) {

		final Frame f = frame;
		this.setResizable(false);

		Panel pCenter = new Panel(); // ��Ƕ
		pCenter.setLayout(new GridLayout(7, 7));
		for (int i = 0; i < 7; i++) { // �����յ�������button�����ʾ�ı�
			titleName[i] = new Button(name[i]);
			pCenter.add(titleName[i]);
		}
		for (int i = 0; i < 42; i++) {
			labelDay[i] = new Label("", Label.CENTER);
			pCenter.add(labelDay[i]);
			labelDay[i].addMouseListener(new labelDayMonitor());
		}
		calendar = new CalendarBean();
		calendar.setYear(year); // �趨��
		calendar.setMonth(month);
		String day[] = calendar.getCalendar(); // �趨��
		for (int i = 0; i < 42; i++) { // ������λ��ѭ�������ʾ������
			labelDay[i].setText(day[i]);
		}
		lab1 = new Label("����������"); // ����һ������ newһ������
		lab2 = new Label("���");
		lab3 = new Label("�·�");
		Enter = new Button("ȷ��");
		text1 = new TextField(10);
		text2 = new TextField(5);
		nextMonth = new Button("����");
		previousMonth = new Button("����");
		Enter.addActionListener(this); // ���ָ���Ķ���������
		nextMonth.addActionListener(this);
		previousMonth.addActionListener(this);

		Panel pNorth = new Panel(), pSouth = new Panel();
		pNorth.add(lab1);// ��������
		pNorth.add(lab2);// ���
		pNorth.add(text1);
		pNorth.add(lab3);// �·�
		pNorth.add(text2);
		pNorth.add(Enter);// ȷ��
		pNorth.add(previousMonth);// ����
		pNorth.add(nextMonth);// ����
		pSouth.add(showMessage);
		showMessage.setText("������" + calendar.getYear() + "��"
				+ calendar.getMonth() + "��");
		ScrollPane scrollPane = new ScrollPane(); // ʵ�ֵ�����������Զ�ˮƽ��/��ֱ������������
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
		this.setTitle("���ü���");
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

		if (e.getSource() == nextMonth) { // �����õĲ���ʱ�¸��µ�
			month = month + 1;
			if (month > 12)
				month = 1;
			calendar.setMonth(month);
			String day[] = calendar.getCalendar();
			for (int i = 0; i < 42; i++) {
				labelDay[i].setText(day[i]);
			}

			resetLabelColor();// ���ݵ�ǰ�������ü���
		} else if (e.getSource() == previousMonth) {// �����õĲ���ʱ�¸��µ�����
			month = month - 1;
			if (month < 1)
				month = 12;
			calendar.setMonth(month);
			String day[] = calendar.getCalendar();
			for (int i = 0; i < 42; i++) {
				labelDay[i].setText(day[i]);
			}
			resetLabelColor();
		} else {// ȷ����ť
			String yea = text1.getText();
			String mon = text2.getText();
			try {
				year = Integer.parseInt(yea); // ��stringת��int����
				month = Integer.parseInt(mon);
				if (month > 12 || month < 1 || year < 1) { // ��������Ĵ���
					JOptionPane.showMessageDialog(null, "��������ȷ�·ݻ��·�");
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
				JOptionPane.showMessageDialog(null, "��������ȷ����ݼ��·�");
			}
		}
		showMessage.setText("������" + calendar.getYear() + "��"
				+ calendar.getMonth() + "��" );
	}

	/**
	 * ������������������������ȷ����ť�У�
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

	private boolean hasOrder(Label l) {// �жϵ�ǰlabel��Ӧ�������Ƿ�����Ϊ���ڣ����ݿ��м���+����map�е����ã�
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
			String title ="������������������";
			String date = calendar.getYear() + "-" + calendar.getMonth() + "-"
					+ label.getText();
			if (label.getBackground().equals(Color.YELLOW)) {
				try {
					String dayOrderStock = orderDao.queryOrders(date);
					dayOrderStock+= "\n�������\n"+stockTransitDao.getStockInformation(date);
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
					JOptionPane.showMessageDialog(null, "�������\n"+dayStock,title,
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
