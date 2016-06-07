// ******************************************
//				author: Christian Abdelmassih
// ******************************************

public class Mock implements Boardgame {

	private int sizeX, sizeY;
	private int[][] status = new int[sizeX][sizeY];  // spelplanen

	public Mock(int x, int y) {
		sizeX = x;
		sizeY = y;
	}

	public int[] getSize() {
		return new int[] {sizeX, sizeY};
	}

	public int[] findValueInBoard(String val) {
		return new int[] {1, 2};
	}

	public boolean move(int i, int j) {
		return true;
	}

	public String getStatus(int i, int j) {
		return "Picachu used Thunder!";
	}

	public String getMessage() {
		return "It's super effective!";
	}

}
