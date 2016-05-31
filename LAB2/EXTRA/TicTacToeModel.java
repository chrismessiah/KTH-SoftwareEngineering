import java.util.*;
import java.util.Random;

public class TicTacToeModel implements Boardgame {
	String user1 = "USER";
	String user2 = "COMPUTER";
	String message = "No message yet";
	private String[][] board = new String[3][3];
	Random gen = new Random();
	String turn;

	public String[][] getBoard(){
		return board;
	}

	public TicTacToeModel() {
		int num = gen.nextInt(2);
		turn = user1;
		if (num == 0) {turn = user2;}
	}

	private void toggleTurn() {
		if (turn == user1) {
			turn = user2;
		} else {
			turn = user1;
		}
	}

	public boolean move(int i, int j) {
		if (checkIfFree(i,j)) {
			board[i][j] = turn;
			toggleTurn();
			String wonMessage = checkForThreeInARow();
			message = "Nice!";
			if (!wonMessage.equals("None")) {
				message = wonMessage;
			}
			return true;	
		}
		message = "BAD MOVE";
		return false;
	}

	public boolean checkIfFree(int i, int j) {
		if (board[i][j] != user2 && board[i][j] != user1) {
			return true;
		}
		return false;
	}

	public String getStatus(int i, int j) {
		return board[i][j];
	}
	public String getMessage() {
		return message;
	}
	public String checkForThreeInARow() {
		message = "None";
		for (int i=0; i<3; i++) {

			// check horizontal
			if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[2][i] != null) {
				message = board[0][i] + " WON!";
			}

			// check vertical
			if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][2] != null) {
				message = board[i][0] + " WON!";
			}
		}

		// check diagonal
		if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[2][2] != null) {
			message = board[1][1] + " WON!";
		}
		if (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[0][2] != null) {
			message = board[1][1] + " WON!";
		}
		return message;
	}


}