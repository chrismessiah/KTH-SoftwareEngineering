
// ****************************************** 
//				author: Christian Abdelmassih
// ******************************************

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Square extends JButton{

    private final int fX;
    private final int fY;
	
	public Square(int x, int y, String value) {
		fX= x;
        fY= y;
		changeValue(value);
	}

	public void changeValue(String value) {
		setText(value);
	}

	public int[] getIndex() {
		return new int[] {fX,fY};
	}
}