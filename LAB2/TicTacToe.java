
public class TicTacToe implements Boardgame {

	private String[][] board = new String[3][3];

	public boolean move(int i, int j) {
		board[i][j] = "moved";
		return true;
	}

	public String getStatus(int i, int j) {
		return board[i][j];
	}
	public String getMessage() {
		return "Error 404";
	}

}