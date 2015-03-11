package edu.hit.mrp.main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;

import javax.swing.JOptionPane;

import edu.hit.mrp.dao.OrderDao;

public class ReleaseOrderInputOrderName extends Frame{

	MrpMain mrpMain;
	private Label messageLabel=new Label("������ִ�мƻ�����");
	
	private TextField inpuTextField =new TextField();
	
	private Button ensureButton =new Button("ȷ��");
	
	public ReleaseOrderInputOrderName(MrpMain mrpMain) throws HeadlessException {
		this.mrpMain = mrpMain;
	}
	
	
	
	public void lauchFrame(){
		
		setLocation(MrpMain.FRAME_LOCATION_X+200,MrpMain.FRAME_LOCATION_Y+150 );
		this.setSize(300,200);
		this.setVisible(true);
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				dispose();
			}
		
		});
		this.setLayout(new BorderLayout());
		
		this.add(messageLabel,BorderLayout.NORTH);
		
		this.add(inpuTextField,BorderLayout.CENTER);
		
		this.add(ensureButton,BorderLayout.SOUTH);
		ensureButton.addActionListener(new EnsureSaving());
		
		
	}
	class EnsureSaving implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			OrderDao orderDao =new OrderDao();
			String[] orderList= mrpMain.resultTextArea.getText().split("\n");
			try {
				String input=inpuTextField.getText();
				if(input.equals("")){
					JOptionPane.showMessageDialog(null, "�����붩������", "",
							JOptionPane.ERROR_MESSAGE);
				}
				else if(orderDao.insertOrder(orderList, input)){
					try {
						ReleaseOrder releaseOrder =new ReleaseOrder(input,mrpMain);
						releaseOrder.release();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JOptionPane.showMessageDialog(null, "��������ɹ�", "",
							JOptionPane.DEFAULT_OPTION);
					dispose();
				}
			} catch (HeadlessException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
	
}
