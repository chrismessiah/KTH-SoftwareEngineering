public class Tester {

	public static void main(String[] args) {
		Tester obj = new Tester();
		obj.run();
	}

	public void run () {
		Composite root = new Composite("root", 20);

		Composite box = new Composite("box", 0);
		Composite bag = new Composite("bag", 0);

		root.add(new Leaf("shirt", 4));
		root.add(box);
		root.add(new Leaf("shoes", 7));
		root.add(bag);
		root.add(new Leaf("socks", 1));

		box.add(new Leaf("camera", 1));
		box.add(new Leaf("phone", 2));
		box.add(new Leaf("laptop", 9));

		bag.add(new Leaf("shampoo", 2));
		bag.add(new Leaf("conditioner", 2));
		bag.add(new Leaf("hair_masque", 3));
		bag.add(new Leaf("soap", 3));

		MyIterator iter = root.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next().toString());
		}

		/*
		STRUCTURE

		shirt
		BOX
			camera
			phone
			laptop
		shoes
		BAG
			shampoo
			conditioner
			hair_masque
			soap
		socks

		*/
	}

}
