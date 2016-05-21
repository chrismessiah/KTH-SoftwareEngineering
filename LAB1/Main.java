
// ****************************************** 
//				author: Christian Abdelmassih
// ******************************************

import javax.swing.*;
import java.awt.*;

public class Main {

	Gui f = new Gui("Lab1");

	MyButton myB1 = new MyButton(Color.white, Color.cyan, "On", "Off");
	MyButton myB2 = new MyButton(Color.green, Color.red, "Run", "Stop");
	JPanel parentPanel = new JPanel();
	JPanel buttonPanel = new JPanel();

	JLabel label = new JLabel("Author: Christian Abdelmassih", SwingConstants.LEFT); // only horizontal alignment

	public static void main(String[] args) {
		Main obj = new Main();
		obj.runProgram();
	}

	public void runProgram() {
		buttonPanel.add(myB1);
		buttonPanel.add(myB2);

		parentPanel.add(buttonPanel);
		parentPanel.add(label);
		parentPanel.setBackground(Color.GRAY);
		buttonPanel.setBackground(Color.GRAY);

		//f.pack();
		f.add(parentPanel);
		f.setVisible(true);
	}
}