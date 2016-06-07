public class Leaf extends Component  {
	// Detta ör jeans, T-shirt, tvål, schampoo
	
	private String name;
	private int weight;

	public Leaf(String name, int weight) {
		this.name = name;
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}

	public String toString() {
		return name;
	}

}