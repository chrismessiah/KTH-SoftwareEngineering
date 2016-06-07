public abstract class Component {
	// attribut och metoder som är meningsfulla i både Leaf och Composite

	String name = "";
	int weight = 0;
	public abstract int getWeight();
	public abstract String toString();

}