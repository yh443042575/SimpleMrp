package edu.hit.mrp.main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
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

import edu.hit.mrp.dao.VacationDao;

class CalendarBean { // ����һ��������
	String day[]; // ��������
	int year = 2011, month = 9; // ����һ����ʼ����

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

	public String[] getCalendar() { // �������
		String a[] = new String[42]; // ����һ�����ַ�������
		Calendar ���� = Calendar.getInstance();
		����.set(year, month - 1, 1);
		int ���ڼ� = ����.get(Calendar.DAY_OF_WEEK) - 1;
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
		for (int i = ���ڼ�, n = 1; i < ���ڼ� + day; i++) {
			a[i] = String.valueOf(n);
			n++;
		}
		return a;
	}
}

public class VacationSettings extends Frame implements ActionListener {

	Map<String, Boolean> setVacationMap = new HashMap<String, Boolean>();// ���ڱ�������ڵ��޸�
	List<String> monthVacationList = new ArrayList<String>();// ������ʾ��ǰ�·����ݿ��д洢�ļ���
	VacationDao vacationDao = new VacationDao();

	Label labelDay[] = new Label[42]; // ��������������������
	Button titleName[] = new Button[7]; // ��һ������İ���
	String name[] = { "��", "һ", "��", "��", "��", "��", "��" };
	TextField text1, text2; // �����������ݺ��·�
	Button nextMonth, previousMonth, Enter, saveSettings; // �¸��£��ϸ��£�ȷ��
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
		saveSettings = new Button("��������");
		Enter.addActionListener(this); // ���ָ���Ķ���������
		nextMonth.addActionListener(this);
		previousMonth.addActionListener(this);
		saveSettings.addActionListener(this);

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
		pSouth.add(saveSettings);
		showMessage.setText("������" + calendar.getYear() + "��"
				+ calendar.getMonth() + "��");
		ScrollPane scrollPane = new ScrollPane(); // ʵ�ֵ�����������Զ�ˮƽ��/��ֱ������������
		scrollPane.add(pCenter);
		add(scrollPane, BorderLayout.CENTER);
		add(pNorth, BorderLayout.NORTH);
		add(pSouth, BorderLayout.SOUTH);
		try {
			monthVacationList = vacationDao.getMonthVacationList("2015-03");
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
		} else if (e.getSource() == saveSettings) {// ��������
			try {
				if (vacationDao.setVacation(setVacationMap)) {
					JOptionPane.showMessageDialog(null, "���óɹ�", "",
							JOptionPane.DEFAULT_OPTION);
					setVacationMap.clear();
				}

			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
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
				+ calendar.getMonth() + "��");
	}

	/**
	 * ������������������������ȷ����ť�У�
	 */
	private void resetLabelColor() {
		String date = calendar.getYear() + "-" + calendar.getMonth();
		try {
			this.monthVacationList = vacationDao.getMonthVacationList(date);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Label l : labelDay) {
			if (!isVacation(l))
				l.setBackground(Color.WHITE);
			else
				l.setBackground(Color.PINK);
		}
	}

	private boolean isVacation(Label l) {// �жϵ�ǰlabel��Ӧ�������Ƿ�����Ϊ���ڣ����ݿ��м���+����map�е����ã�
		String day = l.getText();
		if (day == null)
			return false;
		else {
			if (day.length() == 1)
				day = "0" + day;
			String date = calendar.getYear() + "-" + calendar.getMonth() + "-"
					+ day;

			if ((setVacationMap.containsKey(date) && setVacationMap.get(date) == false)
					|| (!monthVacationList.contains(day) && !setVacationMap
							.containsKey(date)))
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
			if (!label.getBackground().equals(Color.PINK)) {
				label.setBackground(Color.PINK);
				setVacationMap.put(calendar.getYear() + "-"
						+ calendar.getMonth() + "-" + label.getText(), true);
			} else {
				label.setBackground(Color.WHITE);
				String date = calendar.getYear() + "-" + calendar.getMonth()
						+ "-" + label.getText();
				if (!setVacationMap.containsKey(date)) {
					setVacationMap.put(date, false);
				} else {
					setVacationMap.remove(date);
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
