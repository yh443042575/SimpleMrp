package edu.hit.mrp.tool;

import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;
import javax.swing.JOptionPane;

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

public class CalendarFrame extends Panel implements ActionListener {
	Label labelDay[] = new Label[42]; // ��������������������
	Button titleName[] = new Button[7]; // ��һ������İ���
	String name[] = { "��", "һ", "��", "��", "��", "��", "��" };
	TextField text1, text2; // �����������ݺ��·�
	Button nextMonth, previousMonth, Enter; // �¸��£��ϸ��£�ȷ��
	Label lab1, lab2, lab3; // �����ı����͵��ַ���
	int year = 2012, month = 9;
	CalendarBean calendar; // ��һ����
	Label showMessage = new Label("", Label.CENTER); // ����һ��������ʾ ��ǰ���µ�label
														// ��������ǩ��ӦΪ���ġ�

	public CalendarFrame() { // ������

		Panel pCenter = new Panel(); // ��Ƕ
		pCenter.setLayout(new GridLayout(7, 7));
		for (int i = 0; i < 7; i++) { // �����յ�������button�����ʾ�ı�
			titleName[i] = new Button(name[i]);
			pCenter.add(titleName[i]);
		}
		for (int i = 0; i < 42; i++) {
			labelDay[i] = new Label("", Label.CENTER);
			pCenter.add(labelDay[i]);
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
		pNorth.add(lab1);//��������
		pNorth.add(lab2);//���
		pNorth.add(text1);
		pNorth.add(lab3);//�·�
		pNorth.add(text2);
		pNorth.add(Enter);//ȷ��
		pNorth.add(previousMonth);//����
		pNorth.add(nextMonth);//����
		pSouth.add(showMessage);
		showMessage.setText("������" + calendar.getYear() + "��"
				+ calendar.getMonth() + "��");
		ScrollPane scrollPane = new ScrollPane(); // ʵ�ֵ�����������Զ�ˮƽ��/��ֱ������������
		scrollPane.add(pCenter);
		add(scrollPane, BorderLayout.CENTER);
		add(pNorth, BorderLayout.NORTH);
		add(pSouth, BorderLayout.SOUTH);
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
			} catch (NumberFormatException ee) {
				JOptionPane.showMessageDialog(null, "��������ȷ����ݼ��·�");
			}
		}
		showMessage.setText("������" + calendar.getYear() + "��"
				+ calendar.getMonth() + "��");
	}
}


