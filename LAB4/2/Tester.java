import human.*;

public class Tester {

	public static void main(String[] args) {
		Tester obj = new Tester();
		obj.run();
		//obj.error();
	}

	// public void error() {
	// 	new Human(){};
	// 	new Man();
	// 	new Woman();
	// }

	public void run() {
		Human man = Human.create("Charles", "930429-1212");
		Human woman = Human.create("Beatrice", "930429-2121");
		System.out.println(man.toString());
		System.out.println(woman.toString());
	}

}