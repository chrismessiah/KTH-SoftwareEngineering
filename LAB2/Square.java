
// ******************************************
//				author: Christian Abdelmassih
// ******************************************

import javax.swing.*;

public class Square extends JButton{

    private final int fX;
    private final int fY;

	public Square(int x, int y, int value) {
		fX= x;
    fY= y;
		changeValue(value);
	}

	public void changeValue(int newValue) {
		if (newValue == -1) {
			setText("-");
		} else {
			setText(String.valueOf( newValue ));
		}
	}
}
