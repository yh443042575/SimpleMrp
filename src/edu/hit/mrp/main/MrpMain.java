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

public class MrpMain extends Frame {

	private static int FRAME_LOCATION_X = 300, FRAME_LOCATION_Y = 100;

	/**
	 * 布局用控件
	 */

	private TextField deadlineTextField = new TextField(8);
	private TextField countTextField = new TextField(8);
	private TextArea resultTextArea = new TextArea();// 用来显示结果的text,未加工

	private Button calculateConfirmButton = new Button("确认计算");

	private Button setSolution =new Button("设置产品方案");
	
	private Button setVacation =new Button("设置工厂假期");
	
	
	private Label selectSolutionLabel = new Label("选择方案：");
	private Choice selectSolution =new Choice();
	
	
	private Label dateLabel = new Label("交货日期：");
	private Label countLabel = new Label("数量：");
	
	
	
	/**
	 * 界面初始化布局
	 */
	private void lauchFrame() {

		/**
		 * 网格布局管理器
		 */
		GridBagLayout mapMainLayout =new GridBagLayout();
		GridBagConstraints c =new GridBagConstraints();
		c.fill=GridBagConstraints.BOTH;
		c.insets.right=30;
		c.insets.left=30;
		c.insets.top=10;
		c.insets.bottom=10;
	
		
		
		/**
		 * 窗体设置
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
		 * 界面控件的具体设置
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
		selectSolution.add("方桌方案");
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
		 * 控件监听事件
		 */
		calculateConfirmButton.addActionListener(new calculateMonitor("方桌方案",
				deadlineTextField, countTextField ,resultTextArea));

		
		setVacation.addActionListener(new setVacationMonitor());
		
		
		this.setLayout(mapMainLayout);
		this.setVisible(true);
	}

	public static void main(String args[]) {

		MrpMain mrpMain = new MrpMain();
		mrpMain.lauchFrame();

	}

}

class calculateMonitor implements ActionListener {

	String solutionName;
	TextField deadlineTextField;
	TextField countTextField;
	TextArea resultTextArea;

	public calculateMonitor(String solutionName,TextField deadlineTextField ,
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
		List<String> solution = dateCalculator.getResult(solutionName,
				deadlineTextField.getText(), Integer.valueOf(countTextField.getText()));
		for (int i = 0; i < solution.size(); i++) {
			text.append(solution.get(i)+"\n\r");
		}
		resultTextArea.setText(text.toString());
		resultTextArea.repaint();
	}
}

class setVacationMonitor implements ActionListener {

	/**
	 * 日期选择窗口
	 */

	private VacationSettings vacationSettings=new VacationSettings();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		vacationSettings.lauchFrame();
	}
}