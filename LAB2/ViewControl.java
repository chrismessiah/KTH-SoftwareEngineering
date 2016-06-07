
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class ViewControl extends JFrame implements ActionListener{

    private int size;
    private Square[][] btnBoard;
    private int[] matrixSize;
    private JLabel message = new JLabel("Some Message", SwingConstants.CENTER);

    FifteenModel model = new FifteenModel();
    //Mock model = new Mock(2,2);

    JPanel parentPanel = new JPanel();
	JPanel squarePanel;

    public static void main(String[] args) {
		ViewControl obj = new ViewControl();
		obj.runProgram();
	}

	ViewControl() {   	
       	setTitle("Lab2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 400);
		setLocationRelativeTo(null);

		matrixSize = model.getSize();
		btnBoard = new Square[matrixSize[0]][matrixSize[1]];
		squarePanel = new JPanel(new GridLayout(matrixSize[0],matrixSize[1],0,0));
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

    public void actionPerformed(ActionEvent e) {
    	String buttonLabel = e.getActionCommand();
    	if (buttonLabel == "-") {buttonLabel = "-1";}
    	int[] newPos = model.findValueInBoard(buttonLabel);
		boolean status = model.move(newPos[0],newPos[1]);
		if (status) {
			updateAllValues();
		}
		message.setText(model.getMessage());
	}

	private void updateAllValues() {
		for (int i=0;i<btnBoard.length; i++) {
			for (int i2=0;i2<btnBoard[i].length; i2++) {
				btnBoard[i][i2].changeValue(model.getStatus(i,i2));
			}
		}
	}

    private void fillBtnBoard() {
    	for (int i=0;i<btnBoard.length; i++) {
			for (int i2=0;i2<btnBoard[i].length; i2++) {

				Square sq = new Square(i, i2, model.getStatus(i,i2));
				sq.addActionListener(this);
				btnBoard[i][i2] = sq;
				squarePanel.add(sq);
			}
		}
    }
}