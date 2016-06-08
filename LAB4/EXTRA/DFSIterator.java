import java.util.Stack;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class DFSIterator implements Iterator {
  Stack<Component> staq;

  public DFSIterator(Composite compositeData) {
    staq = new Stack<Component>();
    staq.push(compositeData);
  }

  private void listElemsToStack(Composite compositeData, Stack<Component> stack) {
    List<Component> componentList = compositeData.getContent();
    int size = componentList.size();
    for (int i=size-1; i<size; i--) {
      Component temp = componentList.get(i);
      stack.push(temp);
      if (i == 0) {break;}
    }
  }

  public boolean hasNext() {
    if (staq.empty()) {
      return false;
    }
    return true;
  }

  // depth first search
  public Component next() {
    Component top = staq.pop();
    if (top instanceof Composite) {
      Composite topComp  = (Composite)top;
      listElemsToStack(topComp, staq);
    }
    return top;
  }


  public void remove() {} // not needed for lab
}
