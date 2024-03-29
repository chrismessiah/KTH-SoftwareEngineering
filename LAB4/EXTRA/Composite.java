import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class Composite extends Component implements Iterable {
	// Detta är en resväska, necessär, påse

	private String name;
	private int weight;
	private List<Component> content;

	// public DFSIterator iterator() {
	// 	return new DFSIterator(this);
	// }

	// public BFSIterator iterator() {
	// 	return new BFSIterator(this);
	// }

	public Iterator iterator() {
		return new BFSIterator(this);
		//return new DFSIterator(this);
	}

	public List<Component> getContent() {
		return content;
	}

	public Composite(String name, int weight) {
		this.name = name;
		this.weight = weight;
		content =  new ArrayList<Component>();
	}

	public void add(Component thing) {
		content.add(thing);
	}

	public void remove(Component thing) {
		content.remove(thing);
	}

	public Component getChild(int i) {
		return content.get(i);
	}

	public int getWeight() {
		// returnera hela behållarens vikt! For-loop?
		int total_weight = weight;
		for (Component thing : content) {
			total_weight += thing.getWeight();
		}
		return total_weight;
	}

	public String toString2() {
		// behållarens namn följt av namnen på alla saker som finns i behållaren.
		String ret = name.toUpperCase() + "{";
		for (Component thing : content) {
			ret += (thing.toString() + ", ");
		}
		ret += "}";
		return ret;
	}

	public String toString() {
		String ret = name.toUpperCase();
		return ret;
	}
}
