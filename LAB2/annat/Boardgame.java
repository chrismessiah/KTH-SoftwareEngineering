
import java.util.Arrays;

public interface Boardgame {
	
	public int[][] boardMatrix = new int[3][3];
	
	for (int i=0; i<boardMatrix.length; i++) {
		Arrays.fill(boardMatrix[i], 0);	
	}

	

	public boolean checkWin();
	public int getWinner();
	public boolean checkThreeInARow();

	public boolean move(int i, int j); //ger true om draget gick bra, annars false 
	public String getStatus(int i, int j);      
	public String getMessage();

}