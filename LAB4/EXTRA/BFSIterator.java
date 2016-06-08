import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.LinkedList;

public class BFSIterator implements Iterator {
  Queue<Component> q;

  public BFSIterator(Composite compositeData) {
    q = new LinkedList<Component>();
    q.add(compositeData);
  }

  public boolean hasNext() {
    if (q.peek() == null) {
      return false;
    }
    return true;
  }

  // breadth first search
  public Component next() {
    Component first = q.remove();
    if (first instanceof Composite) {
      Composite topComp  = (Composite)first;
      for (Component comp : topComp.getContent()) {
        q.add(comp);
      }
    }
    return first;
  }


  public void remove() {} // not needed for lab
}
