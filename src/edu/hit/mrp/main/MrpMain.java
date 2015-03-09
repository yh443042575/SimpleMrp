package edu.hit.mrp.main;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JOptionPane;

public class MrpMain extends Frame {

	public static int FRAME_LOCATION_X = 300, FRAME_LOCATION_Y = 100;

	/**
	 * �����ÿؼ�
	 */

	private TextField deadlineTextField = new TextField(8);
	private TextField countTextField = new TextField(8);
	protected TextArea resultTextArea = new TextArea();// ������ʾ�����text,δ�ӹ�

	private Button calculateConfirmButton = new Button("ȷ�ϼ���");

	private Button setSolution =new Button("���ò�Ʒ����");
	
	private Button setVacation =new Button("���ù�������");
	
	private Button saveOrder =new Button("���涩��");
	private Button queryOrder =new Button("�鿴ʵʩ����");
	private Button removeOrder =new Button("ɾ��ʵʩ����");
	
	
	private Label selectSolutionLabel = new Label("ѡ�񷽰���");
	private Choice selectSolution =new Choice();
	
	
	private Label dateLabel = new Label("�������ڣ�");
	private Label countLabel = new Label("������");
	
	
	
	/**
	 * �����ʼ������
	 */
	private void lauchFrame() {

		/**
		 * ���񲼾ֹ�����
		 */
		GridBagLayout mapMainLayout =new GridBagLayout();
		GridBagConstraints c =new GridBagConstraints();
		c.fill=GridBagConstraints.BOTH;
		c.insets.right=30;
		c.insets.left=30;
		c.insets.top=10;
		c.insets.bottom=10;
	
		
		
		/**
		 * ��������
		 */
		this.setLocation(FRAME_LOCATION_X, FRAME_LOCATION_Y);
		this.setSize(700, 450);
		
		this.setTitle("SimpleMrp");
		this.setBackground(Color.CYAN);
		

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});

		/**
		 * ����ؼ��ľ�������
		 */
		this.add(selectSolutionLabel);
		selectSolutionLabel.setVisible(true);
		c.gridx=0;
		c.gridy=0;
		c.gridwidth=1;
		c.gridheight=1;
		mapMainLayout.setConstraints(selectSolutionLabel, c);
		
		this.add(selectSolution);
		selectSolution.setVisible(true);
		selectSolution.add("��������");
		c.gridx=1;
		c.gridy=0;
		c.gridwidth=2;
		c.gridheight=1;
		mapMainLayout.setConstraints(selectSolution, c);
		
		this.add(dateLabel);
		c.gridx=0;
		c.gridy=1;
		c.gridwidth=1;
		c.gridheight=1;
		mapMainLayout.setConstraints(dateLabel, c);
		
		this.add(deadlineTextField);
		c.gridx=1;
		c.gridy=1;
		c.gridwidth=2;
		c.gridheight=1;
		mapMainLayout.setConstraints(deadlineTextField, c);
		
		this.add(countLabel);
		c.gridx=0;
		c.gridy=2;
		c.gridwidth=1;
		c.gridheight=1;
		mapMainLayout.setConstraints(countLabel, c);
		
		this.add(countTextField);
		c.gridx=1;
		c.gridy=2;
		c.gridwidth=1;
		c.gridheight=1;
		mapMainLayout.setConstraints(countTextField, c);
		
		
		this.add(calculateConfirmButton);
		c.gridx=2;
		c.gridy=2;
		c.gridwidth=1;
		c.gridheight=1;
		mapMainLayout.setConstraints(calculateConfirmButton, c);
		
		this.add(setSolution);
		c.gridx=0;
		c.gridy=3;
		c.gridwidth=1;
		c.gridheight=1;
		mapMainLayout.setConstraints(setSolution, c);
		
		this.add(setVacation);
		c.gridx=0;
		c.gridy=4;
		c.gridwidth=1;
		c.gridheight=1;
		mapMainLayout.setConstraints(setVacation, c);
		
		this.add(saveOrder);
		c.gridx=0;
		c.gridy=5;
		c.gridwidth=1;
		c.gridheight=1;
		mapMainLayout.setConstraints(saveOrder, c);
		
		this.add(queryOrder);
		c.gridx=1;
		c.gridy=5;
		c.gridwidth=1;
		c.gridheight=1;
		mapMainLayout.setConstraints(queryOrder, c);
		
		this.add(removeOrder);
		c.gridx=2;
		c.gridy=5;
		c.gridwidth=1;
		c.gridheight=1;
		mapMainLayout.setConstraints(removeOrder, c);
		
		this.add(resultTextArea);
		resultTextArea.setEditable(false);
		resultTextArea.setFocusable(false);
		resultTextArea.setBackground(Color.WHITE);
		c.gridx=1;
		c.gridy=3;
		c.gridwidth=2;
		c.gridheight=2;
		
		
		
		mapMainLayout.setConstraints(resultTextArea, c);
		/**
		 * �ؼ������¼�
		 */
		calculateConfirmButton.addActionListener(new CalculateMonitor("��������",
				deadlineTextField, countTextField ,resultTextArea));

		
		setVacation.addActionListener(new LauchFrameMonitor(this,this));
		saveOrder.addActionListener(new LauchFrameMonitor(this,this));
		queryOrder.addActionListener(new LauchFrameMonitor(this,this));
		removeOrder.addActionListener(new LauchFrameMonitor(this,this));
		this.setResizable(false);
		this.setLayout(mapMainLayout);
		this.setVisible(true);
	}

	public static void main(String args[]) {

		MrpMain mrpMain = new MrpMain();
		mrpMain.lauchFrame();

	}

	class LauchFrameMonitor implements ActionListener {
		
		Frame frame;
		MrpMain mrpMain;
		public LauchFrameMonitor(Frame frame ,MrpMain mrpMain) {
			this.frame = frame;
			this.mrpMain=mrpMain;
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource()==setVacation){
			VacationSettings vacationSettings=new VacationSettings();
			vacationSettings.lauchFrame(frame);
			frame.setEnabled(false);
			frame.setFocusable(false);
			}
			else if(e.getSource()==saveOrder){
				if(resultTextArea.getText().equals("")){
					JOptionPane.showMessageDialog(null, "����¼�뷽��!", "",
							JOptionPane.ERROR_MESSAGE);
				}
				else{
				SaveOrder _saveOrder =new SaveOrder(mrpMain);
				_saveOrder.lauchFrame();
				}
			}
			else if(e.getSource()==queryOrder){
				OrderQuery orderQuery=new OrderQuery();
				orderQuery.lauchFrame(frame);
				frame.setEnabled(false);
				frame.setFocusable(false);
			}
			else if(e.getSource()==removeOrder){
				OrderRemove orderRemove = new OrderRemove();
				orderRemove.LauchFrame(frame);
				frame.setEnabled(false);
				frame.setFocusable(false);
			}
		}
	}
}

class CalculateMonitor implements ActionListener {

	String solutionName;
	TextField deadlineTextField;
	TextField countTextField;
	TextArea resultTextArea;
	List<String> solution;

	public CalculateMonitor(String solutionName,TextField deadlineTextField ,
	TextField countTextField,TextArea resultTextArea) {
		super();
		this.solutionName = solutionName;
		this.deadlineTextField = deadlineTextField;
		this.countTextField = countTextField;
		this.resultTextArea = resultTextArea;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DateCalculator dateCalculator = new DateCalculator();
		StringBuffer text=new StringBuffer();
		deadlineTextField.repaint();
		countTextField.repaint();
		solution = dateCalculator.getResult(solutionName,
				deadlineTextField.getText(), Integer.valueOf(countTextField.getText()));
		for (int i = 0; i < solution.size(); i++) {
			text.append(solution.get(i)+"\n\r");
		}
		resultTextArea.setText(text.toString());
		resultTextArea.repaint();
	}
	

	
}

