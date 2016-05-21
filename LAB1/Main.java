
// ****************************************** 
//				author: Christian Abdelmassih
// ******************************************

import javax.swing.*;
import java.awt.*;

public class Main {

	JFrame f = new JFrame("Lab1");

	MyButton b1Obj = new MyButton(Color.white, Color.cyan, "On", "Off");
	MyButton b2Obj = new MyButton(Color.green, Color.red, "Run", "Stop");
	JPanel parentPanel = new JPanel();
	JPanel buttonPanel = new JPanel();

	JLabel label = new JLabel("Author: Christian Abdelmassih", SwingConstants.LEFT); // only horizontal alignment

	public static void main(String[] args) {
		Main obj = new Main();
		obj.runProgram();
	}

	public void runProgram() {
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(400, 400);
		//f.setResizable(false);
		f.setLocationRelativeTo(null);

		buttonPanel.add(b1Obj.b);
		buttonPanel.add(b2Obj.b);

		parentPanel.add(buttonPanel);
		parentPanel.add(label);
		parentPanel.setBackground(Color.GRAY);
		buttonPanel.setBackground(Color.GRAY);

		//f.pack();
		f.add(parentPanel);
		f.setVisible(true);
	}
}