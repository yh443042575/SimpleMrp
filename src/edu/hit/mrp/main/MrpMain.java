package edu.hit.mrp.main;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MrpMain extends Frame{

	private static int FRAME_LOCATION_X=300,FRAME_LOCATION_Y=100;
	
	private TextField textField=new TextField("hello",20);
	
	private void lauchFrame()
	{
		this.setLocation(FRAME_LOCATION_X, FRAME_LOCATION_Y);
		this.setSize(800, 600);
		this.setVisible(true);
		this.setTitle("SimpleMrp");
		this.setBackground(Color.CYAN);
		this.setLayout(new FlowLayout());
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
			
		});
		
		this.add(this.textField);
		textField.setVisible(true);
		textField.setLocation(FRAME_LOCATION_X+100, FRAME_LOCATION_Y+100);
		textField.setSize(20, 50);
	}
	
	public static void main(String args[]){
		MrpMain mrpMain =new MrpMain();
		mrpMain.lauchFrame();
	}
	
}
