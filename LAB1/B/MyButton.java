
// ****************************************** 
//				author: Christian Abdelmassih
// ******************************************

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyButton extends JButton implements ActionListener{

	String state1, state2, currentState;
	
	public MyButton(Color bgColor, Color textColor, String state1, String state2) {
		setBackground(bgColor);
		setForeground(textColor);

		addActionListener(this);

		this.state1 = state1;
		this.currentState = state1;
		this.state2 = state2;

		setBackground(bgColor);
		setForeground(textColor);
		setText(this.currentState);
	}

	public void actionPerformed(ActionEvent e) {
		this.toggleState();
	}

	public void toggleState() {
		if (this.currentState == this.state1) {
			this.currentState = this.state2;
		} else {
			this.currentState = this.state1;
		}
		setText(this.currentState);
	}


}