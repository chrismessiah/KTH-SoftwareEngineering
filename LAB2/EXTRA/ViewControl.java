
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ViewControl extends JFrame implements ActionListener{

    private Square[][] btnBoard = new Square[3][3];        // Square är subklass till JButton
    private JLabel message = new JLabel("Some Message", SwingConstants.CENTER);

    TicTacToeModel model = new TicTacToeModel();

    JPanel parentPanel = new JPanel();
	JPanel squarePanel = new JPanel(new GridLayout(3,3,0,0));

    public static void main(String[] args) {
		ViewControl obj = new ViewControl();
		obj.runProgram();
	}

	public void runProgram() {
		fillBtnBoard();
		parentPanel.setLayout(new GridLayout(2, 1));
		parentPanel.setBackground(Color.GRAY);
		squarePanel.setBackground(Color.GRAY);
		parentPanel.add(message);
		parentPanel.add(squarePanel);

		add(parentPanel);
		setVisible(true);
	}

	// Boardgame gm, int n
    ViewControl() {   	
       	setTitle("Lab2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);
		setLocationRelativeTo(null);
    }

    public Square findPressedButton(ActionEvent e) {
    	for (Square[] row : btnBoard) {
    		for (Square btn : row) {
    			if (btn == e.getSource()) {return btn;}
    		}
    	}
    	return null;
    }

    public void actionPerformed(ActionEvent e) {
    	Square button = findPressedButton(e);
    	int[] pos = button.getIndex();
		boolean status = model.move(pos[0],pos[1]);
		if (status) {
			button.changeValue(model.getStatus(pos[0],pos[1]));
		}
		message.setText(model.getMessage());
	}

    private void fillBtnBoard() {
    	String[][] board = model.getBoard();
    	for (int i=0;i<btnBoard.length; i++) {
			for (int i2=0;i2<btnBoard.length; i2++) {
				Square sq = new Square(i, i2, board[i][i2]);
				sq.addActionListener(this);
				btnBoard[i][i2] = sq;
				squarePanel.add(sq);
			}
		}
    }
}