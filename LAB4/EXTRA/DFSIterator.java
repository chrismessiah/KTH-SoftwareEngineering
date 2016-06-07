import java.util.Stack;
import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class DFSIterator implements Iterator {
  List<Component> componentData;
  Stack<Component> staq;
  int index = 0;
  int depthIndex = 0;

  public DFSIterator(List<Component> componentData) {
    this.componentData = componentData;
    staq = new Stack<Component>();
    listElemsToStack(componentData, staq);
  }

  private void listElemsToStack(List<Component> componentList, Stack<Component> stack) {
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
      Iterator tempIterator = topComp.iterator();
      DFSIterator iter = (DFSIterator)tempIterator;
      listElemsToStack(iter.componentData, staq);
      //top = next();
    }
    return top;
  }


  public void remove() {} // not needed for lab
}
