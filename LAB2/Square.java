
// ******************************************
//				author: Christian Abdelmassih
// ******************************************

import javax.swing.*;

public class Square extends JButton{

    private final int fX;
    private final int fY;

	public Square(int x, int y, String value) {
		fX= x;
    	fY= y;
		changeValue(value);
	}

	public void changeValue(String newValue) {
		if (newValue.equals("-1")) {
			setText("-");
		} else {
			setText(newValue);
		}
	}
}
