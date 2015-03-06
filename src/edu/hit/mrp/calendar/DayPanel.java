package edu.hit.mrp.calendar;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * ����������
 * 
 * @author �ο�
 * 
 */
public class DayPanel extends JPanel implements MouseListener {

	private JLabel label;

	// ��������������������ʱ��
	private Calendar calendar;

	// �Ƿ���Ӧ�ڲ����õ�����¼���Ĭ��Ϊ����Ӧ
	private boolean actionMoseEvent = false;
	// �Ƿ�Ϊ��ǰ���ڵı�־
	private boolean isCurrentDay = false;

	/**
	 * ���췽��
	 * 
	 * @param day
	 */
	public DayPanel(Calendar calendar) {
		this(calendar.get(Calendar.DAY_OF_MONTH) + "");
		this.calendar = Calendar.getInstance();
		this.calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
		this.calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
		this.calendar.set(Calendar.DATE, calendar.get(Calendar.DATE));
		if ((Calendar.SATURDAY == calendar.get(Calendar.DAY_OF_WEEK) || Calendar.SUNDAY == calendar
				.get(Calendar.DAY_OF_WEEK))) {
			label.setForeground(Color.red);
		}
	}

	/**
	 * ���췽��
	 * 
	 * @param day
	 */
	public DayPanel(String day) {
		label = new JLabel();
		label.setText(day);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.addMouseListener(this);
		setLayout(new GridBagLayout());
		setMinimumSize(new Dimension(20, 20));
		setPreferredSize(new Dimension(20, 20));
		add(label, new GridBagConstraints(0, 0, 1, 1, 0, 0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(
						0, 0, 0, 0), 0, 0));
	}

	/**
	 * ������ʾ���ڵ���ɫ
	 * 
	 * @param color
	 */
	public void setLabelForeground(Color color) {
		label.setForeground(color);
	}

	/**
	 * �Ƿ�Ϊ��ǰ����
	 * 
	 * @return
	 */
	public boolean isCurrentDay() {
		return isCurrentDay;
	}

	/**
	 * ����Ϊ��ǰ���ڵı��
	 * 
	 * @param isCurrentDay
	 */
	public void setCurrentDay(boolean isCurrentDay) {
		this.isCurrentDay = isCurrentDay;
		setBackground(Color.cyan);
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public JLabel getLabel() {
		return label;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (isCurrentDay() || !isActionMoseEvent()) {
			return;
		}
		setOpaque(true);
		setBackground(Color.gray);
		setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if (isCurrentDay() || !isActionMoseEvent()) {
			return;
		}
		setOpaque(false);
		setBorder(null);

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	/**
	 * �Ƿ���Ӧ�ڲ����õ���궯���¼�
	 * 
	 * @return
	 */
	public boolean isActionMoseEvent() {
		return actionMoseEvent;
	}

	/**
	 * �����Ƿ���ʾ�ڲ���������¼�
	 * 
	 * @param actionMoseEvent
	 */
	public void setActionMoseEvent(boolean actionMoseEvent) {
		this.actionMoseEvent = actionMoseEvent;
	}

}
