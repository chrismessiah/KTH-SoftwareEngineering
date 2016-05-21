
// ****************************************** 
//				author: Christian Abdelmassih
// ******************************************

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener{

	MyButton myB1 = new MyButton(Color.white, Color.cyan, "On", "Off");
	MyButton myB2 = new MyButton(Color.green, Color.red, "Run", "Stop");
	JPanel parentPanel = new JPanel();
	JPanel buttonPanel = new JPanel();

	JLabel label = new JLabel("Author: Christian Abdelmassih", SwingConstants.LEFT); // only horizontal alignment

	public static void main(String[] args) {
		Main obj = new Main();
		obj.runProgram();
	}

	public Main() {
		setTitle("Lab1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setLocationRelativeTo(null);
	}

	public void actionPerformed(ActionEvent e) {
		Object btn = e.getSource();

		System.out.println(btn.currentState);
		System.out.println(e.getActionCommand());
		//e.getActionCommand().toggleState();
	}

	public void runProgram() {

		myB1.addActionListener(this);
		myB2.addActionListener(this);

		buttonPanel.add(myB1);
		buttonPanel.add(myB2);

		parentPanel.add(buttonPanel);
		parentPanel.add(label);
		parentPanel.setBackground(Color.GRAY);
		buttonPanel.setBackground(Color.GRAY);

		add(parentPanel);
		setVisible(true);
	}
}