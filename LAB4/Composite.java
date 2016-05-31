import java.util.ArrayList;
import java.util.List;

public class Composite implements Component {
	// Detta är en resväska, necessär, påse

	private String name;
	private int weight;
	private List<Component> content;

	public void Composite(String name, int weight) {
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

	public String toString() {
		// behållarens namn följt av namnen på alla saker som finns i behållaren.
		String ret = "Container: " + name + "\n\n Content:\n";
		for (Component thing : content) {
			ret += (thing.toString() + "\n");
		}
		return ret;
	}
}