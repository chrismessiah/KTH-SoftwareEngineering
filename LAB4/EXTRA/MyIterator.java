import java.util.Stack;

public class MyIterator implements Iterator {
  List<Component> componentData;
  Stack<Component> staq;
  int index = 0;
  int depthIndex = 0;

  public MyIterator(List<Component> componentData) {
    this.componentData = componentData;
    staq = new Stack<Component>();
  }

  public boolean hasNext() {
    if (componentData.size() == index) {
      return true;
    }
    return false;
  }

  // breadth first search
  //public Component next() {}

  // depth first search
  public Component next() {
    Component node = componentData.get(index);
    if (node instanceof Composite) {
      MyIterator iter = new MyIterator(node);
      Component ret = iter.next();

    }

    // cannot return it directly... what if it's a composite?
    index += 1;
    return componentData.get(index);
  }

  // not needed for lab
  public void remove() {}
}
