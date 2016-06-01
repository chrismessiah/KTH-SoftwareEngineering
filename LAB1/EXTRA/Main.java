
// ****************************************** 
//				author: Christian Abdelmassih
// ******************************************

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame {

	boolean useSound = true;
	String[] args;
	MyButton[] buttonList;
	JPanel parentPanel = new JPanel();
	JPanel buttonPanel = new JPanel();

	JLabel label = new JLabel("Author: Christian Abdelmassih", SwingConstants.LEFT); // only horizontal alignment

	public static void main(String[] args) {
		Main obj = new Main(args);
		obj.runProgram();
	}

	public Main(String[] args) {
		this.args = args;
		buttonList = new MyButton[Integer.parseInt(args[0])];
		setTitle("Lab1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setLocationRelativeTo(null);
	}

	public void runProgram() {
		int i=1;
		int i2=0;
		while (i < args.length) {
			MyButton btn = new MyButton(Color.green, Color.red, args[i], args[i+1]);
			buttonPanel.add(btn);
			buttonList[i2] = btn;
			i2 += 1;
			i += 2;
		}

		parentPanel.add(buttonPanel);
		parentPanel.add(label);
		parentPanel.setBackground(Color.GRAY);
		buttonPanel.setBackground(Color.GRAY);

		add(parentPanel);
		setVisible(true);
	}
}