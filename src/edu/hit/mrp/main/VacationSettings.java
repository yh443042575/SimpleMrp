package edu.hit.mrp.main;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import edu.hit.mrp.tool.*;

public class VacationSettings extends Frame{

	private CalendarFrame calendarFrame=new CalendarFrame();
	public void lauchFrame(){
		
		this.setTitle("…Ë÷√»’∆⁄");
		this.setSize(700, 450);
		this.setLayout(new BorderLayout());
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
			
		});
		
		this.add(calendarFrame,BorderLayout.CENTER);
		calendarFrame.setVisible(true);
		this.setVisible(true);
		
	}
}
