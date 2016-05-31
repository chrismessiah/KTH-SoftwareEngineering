public interface Component {
	// attribut och metoder som är meningsfulla i både Leaf och Composite

	String name = "";
	int weight = 0;
	public int getWeight();
	public String toString();

}