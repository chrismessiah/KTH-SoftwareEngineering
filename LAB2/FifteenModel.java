// FifteenModel innehåller ingen grafik !!!

import java.util.*;
import java.util.Random;

public class FifteenModel implements Boardgame {
  // Implementera Boardgame-metoderna
  // Deklarera variabler och övriga metoder som ni
  // tycker behövs för ett femtonspel
	
	private String currentMessage = "No message yet";
	private int[][] status = new int[4][4];  // spelplanen

	public int[][] getBoard() {
		return status;
	}

	public FifteenModel() {
		fillBoard();
		shuffleBoard();
		//printBoard();
	}

	public int[] findValueInBoard(int val) {
		int[] ret = new int[2];
		for (int i=0;i<status.length; i++ ) {
			for (int i2=0;i2<status[i].length; i2++ ) {
				if (status[i][i2] == val) {
					ret[0] = i;
					ret[1] = i2;
					break;
				}
			}
		}
		return ret;
	}

	private void shuffleBoard() {
		Random gen = new Random();
		for (int i = 0; i<1000 ; i++) {
			List<String> goodMoves = getAcceptableMove();
			int n = gen.nextInt(goodMoves.size());
			String[] splited = goodMoves.get(n).split("-");
			move( Integer.parseInt(splited[0])  , Integer.parseInt(splited[1]) );
		}
	}

	public boolean checkMove(int i, int j) {
		int[] zeroPos = getEmptySpace();
		if (zeroPos[0] != i && zeroPos[0] != i+1 && zeroPos[0] != i-1) {
			return false;
		}

		if (zeroPos[1] != j && zeroPos[1] != j+1 && zeroPos[1] != j-1) {
			return false;
		}

		if ( (zeroPos[0] == i+1 && zeroPos[1] == j+1) || (zeroPos[0] == i-1 && zeroPos[1] == j-1) || (zeroPos[0] == i-1 && zeroPos[1] == j+1) || (zeroPos[0] == i+1 && zeroPos[1] == j-1)) {
			return false;
		}

		try {
			int temp = status[i][j];
			return true;
		} catch (IndexOutOfBoundsException e){return false;}
	}

	private int[] getEmptySpace() {
		int[] pos = new int[2];
		for (int i = 0; i<status.length; i++) {
			for (int i2 = 0; i2<status[i].length; i2++) {
				if (status[i][i2] == -1) {
					pos[0] = i;
					pos[1] = i2;
					break;
				}
			}
		}
		return pos;
	}

	private List<String> getAcceptableMove() {
		int[] pos = getEmptySpace();
		int x = pos[1];
		int y = pos[0];

		List<String> outputStrList = new ArrayList<String>();
		if (checkMove(y+1,x)) {
			outputStrList.add(Integer.toString(y+1) + "-" + Integer.toString(x));
		}
		if (checkMove(y-1,x)) {
			outputStrList.add(Integer.toString(y-1) + "-" + Integer.toString(x));
		}
		if (checkMove(y,x+1)) {
			outputStrList.add(Integer.toString(y) + "-" + Integer.toString(x+1));
		}
		if (checkMove(y,x-1)) {
			outputStrList.add(Integer.toString(y) + "-" + Integer.toString(x-1));
		}
		return outputStrList;
	}

	private void fillBoard() {
		int i3 = 1;
		for (int i = 0; i<status.length; i++) {
			for (int i2 = 0; i2<status[i].length; i2++) {
				status[i][i2] = i3;
				i3 += 1;
			}
		}
		status[3][3] = -1;
	}

	public void printBoard() {
		for (int i = 0; i < status.length; i++) {
		    for (int j = 0; j < status[0].length; j++) {
		    	if (status[i][j] == -1) {
		    		System.out.print("- ");
		    	} else {
		        	System.out.print(status[i][j] + " ");
		        }
		    }
		    System.out.print("\n");
		}

		System.out.print("\n");
	}

	public boolean move(int i, int j) {
		if (!checkMove(i,j)) {
			currentMessage = "BAD MOVE";
			return false;
		}
		int[] pos = getEmptySpace();
		status[ pos[0] ][ pos[1] ] = status[i][j];
		status[ i ][ j ] = -1;
		currentMessage = "Nice!";
		return true;
	}

	public String getStatus(int i, int j) {
		int num = status[i][j];
		if (num == -1) {
	   		return "-";
	   	}
		return Integer.toString(num);
	}   

	public String getMessage() {
		return currentMessage;
	}

}