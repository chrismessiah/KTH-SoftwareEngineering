public class Tester {

	public static void main(String[] args) {
		Tester obj = new Tester();
		obj.run();
	}

	public void run () {
		Composite suitcase = new Composite("suitcase", 20);

		suitcase.add(new Leaf("shirt", 4));
		suitcase.add(new Leaf("shoes", 7));
		suitcase.add(new Leaf("socks", 1));
		Composite box = new Composite("box", 0);
		suitcase.add(box);

		box.add(new Leaf("camera", 1));
		box.add(new Leaf("phone", 2));
		box.add(new Leaf("laptop", 9));
		Composite bag = new Composite("bag", 0);
		box.add(bag);

		bag.add(new Leaf("shampoo", 2));
		bag.add(new Leaf("conditioner", 2));
		bag.add(new Leaf("hair_masque", 3));
		bag.add(new Leaf("soap", 3));

		System.out.println(suitcase.toString());
		System.out.println(suitcase.getWeight());
	}

}
