public class Tester {

	public static void main(String[] args) {
		Tester obj = new Tester();
		obj.run();
	}

	public void run () {
		Composite suitcase = new Composite("suitcase", 20);

		Leaf shirt = new Leaf("shirt", 4);
		Leaf shoes = new Leaf("shoes", 7);
		Leaf socks = new Leaf("socks", 1);
		Composite bag = new Composite("bag", 0);
		suitcase.add(shirt);
		suitcase.add(shoes);
		suitcase.add(socks);
		suitcase.add(bag);

		Leaf shampoo = new Leaf("shampoo", 2);
		Leaf conditioner = new Leaf("conditioner", 2);
		Leaf hair_masque = new Leaf("hair_masque", 3);
		Leaf soap = new Leaf("soap", 3);

		bag.add(shampoo);
		bag.add(conditioner);
		bag.add(hair_masque);
		bag.add(soap);

		System.out.println(suitcase.toString());
		System.out.println(suitcase.getWeight());
	}

}