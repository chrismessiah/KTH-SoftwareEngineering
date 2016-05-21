
// ****************************************** 
//				author: Christian Abdelmassih
// ******************************************

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton{

	String state1, state2, currentState;
	
	public MyButton(Color bgColor, Color textColor, String state1, String state2) {
		setBackground(bgColor);
		setForeground(textColor);

		this.state1 = state1;
		this.currentState = state1;
		this.state2 = state2;

		setBackground(bgColor);
		setForeground(textColor);
		setText(this.currentState);
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