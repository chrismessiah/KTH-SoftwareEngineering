import human.*;

public class Tester {

	public static void main(String[] args) {
		Tester obj = new Tester();
		obj.run();
	}

	public void run () {
		Human.create("930429-1212"); // man 
		Human.create("930429-2121"); // woman 
		Human.create("930429-2101"); // woman 
		Human.create("930429-2191"); // man 
		//Woman h = new Woman(){};
	}

}