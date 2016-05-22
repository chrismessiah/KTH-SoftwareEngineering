
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ViewControl extends JFrame {

    private int size;
    private Square[][] btnBoard = new Square[4][4];        // Square är subklass till JButton
    private JLabel message = new JLabel("Some Message");

    FifteenModel model = new FifteenModel();

    JPanel parentPanel = new JPanel();
	JPanel squarePanel = new JPanel(new GridLayout(4,4,0,0));

    public static void main(String[] args) {
		ViewControl obj = new ViewControl();
		obj.runProgram();
	}

	// Boardgame gm, int n
    ViewControl () {   	
       	setTitle("Lab2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 400);
		setLocationRelativeTo(null);
    }

    private void fillBtnBoard() {
    	for (int i=0;i<btnBoard.length; i++) {
			for (int i2=0;i2<btnBoard.length; i2++) {
				Square sq = new Square(i, i2, model, btnBoard, message);
				btnBoard[i][i2] = sq;
				squarePanel.add(sq);
			}
		}
    }

	public void runProgram() {
		fillBtnBoard();
		squarePanel.setBackground(Color.GRAY);
		parentPanel.add(squarePanel);

		parentPanel.add(message);
		parentPanel.setBackground(Color.GRAY);

		add(parentPanel);
		setVisible(true);
	}
}