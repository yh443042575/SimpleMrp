package edu.hit.mrp.main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class MrpMain extends Frame {

	private static int FRAME_LOCATION_X = 300, FRAME_LOCATION_Y = 100;

	private TextField deadlineTextField = new TextField("100", 0);
	private TextField countTextField = new TextField("5", 0);

	private DateCalculator dateCalculator = new DateCalculator();

	/**
	 * 界面初始化布局
	 */
	private void lauchFrame() {
		this.setLocation(FRAME_LOCATION_X, FRAME_LOCATION_Y);
		this.setSize(800, 600);
		this.setVisible(true);
		this.setTitle("SimpleMrp");
		this.setBackground(Color.CYAN);
		this.setLayout(new FlowLayout(FlowLayout.CENTER));

		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}

		});

		this.add(this.deadlineTextField);
		deadlineTextField.setVisible(true);
		deadlineTextField.setLocation(FRAME_LOCATION_X + 100,
				FRAME_LOCATION_Y + 150);
		deadlineTextField.setSize(20, 30);

		this.add(this.countTextField);
		countTextField.setVisible(true);
		countTextField.setLocation(FRAME_LOCATION_X + 200,
				FRAME_LOCATION_Y + 150);
		countTextField.setSize(20, 30);
	}

	public void outPutResult(String solutionName, int deadline, int count) {
		List<String> solution = this.dateCalculator.getResult(solutionName,
				deadline, count);
		Graphics g = this.getGraphics();

		for (int i = 0; i < solution.size(); i++) {
			g.drawString(solution.get(i), FRAME_LOCATION_X + 50,
					FRAME_LOCATION_Y + (i + 1) * 20);
		}

	}

	public static void main(String args[]) {

		MrpMain mrpMain = new MrpMain();
		mrpMain.lauchFrame();

		mrpMain.outPutResult("方桌方案", Integer.valueOf(mrpMain.deadlineTextField
				.getText()), Integer.valueOf(mrpMain.countTextField.getText()));

	}

}
