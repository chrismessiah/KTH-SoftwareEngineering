
// ****************************************** 
//				author: Christian Abdelmassih
// ******************************************

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Square extends JButton implements ActionListener{

	private final int[][] fModel;
    private final int fX;
    private final int fY;
    private final FifteenModel fullModel;
    private final Square[][] btnBoard;
    private final JLabel message;
	
	public Square(final int x, final int y, final FifteenModel model, final Square[][] btnMatrix, JLabel message) {
		fX= x;
        fY= y;
        fullModel = model;
        btnBoard = btnMatrix;
        fModel= model.getBoard();
		updateValue();
		addActionListener(this);
		this.message = message;

	}

	public void actionPerformed(ActionEvent e) {
		boolean status = fullModel.move(fX,fY);
		if (status) {
			updateAllValues();
		}
		message.setText(fullModel.getMessage());
	}

	private void updateValue() {
		if (fModel[fX][fY] == -1) {
			setText("-");
		} else {
			setText(String.valueOf( fModel[fX][fY] ));
		}
	}

	private void updateAllValues() {
		for (int i=0;i<btnBoard.length; i++) {
			for (int i2=0;i2<btnBoard[i].length; i2++) {
				btnBoard[i][i2].updateValue();
			}
		}
	}
}