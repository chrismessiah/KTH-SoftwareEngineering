import java.util.Stack;
import java.util.Collections;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class MyIterator implements Iterator {
  List<Component> componentData;
  Stack<Component> staq;
  int index = 0;
  int depthIndex = 0;

  public MyIterator(List<Component> componentData) {
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

  // private List<Component> reverseThis(List<Component> cList) {
  //   List<Component> reversed = new ArrayList<Component>(cList);
  //   Collections.reverse(reversed);
  //   return reversed;
  // }

  // breadth first search
  //public Component next() {}

  // depth first search
  public Component next() {
    Component top = staq.pop();
    if (top instanceof Composite) {
      Composite topComp  = (Composite)top;
      MyIterator tempIterator = topComp.iterator();
      listElemsToStack(tempIterator.componentData, staq);
      //top = next();
    }

    return top;
  }



    // if (node instanceof Composite) {
    //   MyIterator iter = new MyIterator(node);
    //   Component ret = iter.next();
    // } else {
    //   return node;
    //   //index += 1;
    // }

    // cannot return it directly... what if it's a composite? 

  // not needed for lab
  public void remove() {}
}
