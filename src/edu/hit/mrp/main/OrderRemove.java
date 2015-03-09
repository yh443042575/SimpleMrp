package edu.hit.mrp.main;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import edu.hit.mrp.dao.OrderDao;
import edu.hit.mrp.main.MrpMain.LauchFrameMonitor;

public class OrderRemove extends Frame{
	
	private Label chooseOrderLabel= new Label("选择方案");
	private Choice orderNameChoice =new Choice();
	
	private Label orderInformationLabel =new Label("订单详细信息");
	private TextArea orderInformation = new TextArea();
	
	private Button ensureRemoveButton =new Button("确认删除");
	
	
	OrderDao orderDao= new OrderDao();
	public void LauchFrame(Frame frame){
		
		final Frame f=frame;
		this.setLocation(400, 230);
		this.setSize(600,300);
		this.setVisible(true);
		this.setTitle("删除实施方案");
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				f.setEnabled(true);
				f.setFocusable(true);
				dispose();
			
			}
		
		});
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		GridBagConstraints c =new GridBagConstraints();
		c.fill=GridBagConstraints.BOTH;
		c.insets.right=15;
		c.insets.left=15;
		c.insets.top=10;
		c.insets.bottom=10;
		
		this.add(chooseOrderLabel);
		chooseOrderLabel.setVisible(true);
		c.gridx=0;
		c.gridy=0;
		c.gridwidth=1;
		c.gridheight=1;
		gridBagLayout.setConstraints(chooseOrderLabel, c);
		
		
		this.add(orderNameChoice);
		orderNameChoice.setVisible(true);
		c.gridx=1;
		c.gridy=0;
		c.gridwidth=1;
		c.gridheight=1;
		gridBagLayout.setConstraints(orderNameChoice, c);
		
	
		
		this.add(orderInformationLabel);
		orderInformationLabel.setVisible(true);
		c.gridx=0;
		c.gridy=1;
		c.gridwidth=1;
		c.gridheight=1;
		gridBagLayout.setConstraints(orderInformationLabel, c);
		
		this.add(orderInformation);
		orderInformation.setVisible(true);
		c.gridx=1;
		c.gridy=1;
		c.gridwidth=1;
		c.gridheight=1;
		gridBagLayout.setConstraints(orderInformation, c);
		
		this.add(ensureRemoveButton);
		ensureRemoveButton.setVisible(true);
		c.gridx=1;
		c.gridy=2;
		c.gridwidth=1;
		c.gridheight=1;
		gridBagLayout.setConstraints(ensureRemoveButton, c);
		
		this.setLayout(gridBagLayout);
		
		/**
		 * 监听事件
		 */
		orderNameChoice.addItemListener(new ItemChangedListener());
		ensureRemoveButton.addActionListener(new ensureRemoveListener());
		resetOrderNameChoice();
		
		
	}
	private void resetOrderNameChoice() {
		try {
			orderNameChoice.removeAll();
			List<String> choiceList = orderDao.getOrderChoice();
			for(String s:choiceList){
				orderNameChoice.addItem(s);
			}
			orderNameChoice.repaint();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	class ItemChangedListener implements ItemListener{

		@Override
		public void itemStateChanged(ItemEvent e) {
			
			try {
				orderInformation.setText(orderDao.queryOrderInformation(orderNameChoice.getSelectedItem()));
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}

	class ensureRemoveListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(orderDao.removeOrder(orderNameChoice.getSelectedItem())){
				JOptionPane.showMessageDialog(null, "删除成功!", "",
						JOptionPane.OK_CANCEL_OPTION);
			resetOrderNameChoice();
			orderInformation.setText("");
			orderInformation.repaint();
			}
			
		}
		
		
	}
}
