
// ****************************************** 
//				author: Christian Abdelmassih
// ******************************************

import javax.swing.*;
import java.awt.*;

public class MyButton extends JButton{

	Color bgColor, textColor;
	String state1, state2, currentState;

	JButton b;	
	
	public MyButton(Color bgColor, Color textColor, String state1, String state2) {
		this.bgColor = bgColor;
		this.textColor = textColor;
		this.state1 = state1;
		this.currentState = state1;
		this.state2 = state2;

		this.b = new JButton(state1);
		// Dimension dim = new Dimension(100,100);
  		// this.b.setSize(dim);
		this.b.setBackground(bgColor);
		this.b.setForeground(textColor);

	}

	public void toggleState() {
		if (this.currentState == this.state1) {
			this.currentState = this.state2;
		} else {
			this.currentState = this.state1;
		}
		this.b.setText(this.currentState);
	}


}