// http://www.csc.kth.se/utbildning/kth/kurser/DD2385/prutt16/laborationer/labb5.php

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;

class NewTree extends TreeFrame {

	public static void main(String[] args) {
		new NewTree();
	}

	public NewTree() {}

	// should create root, treeModel and tree.
  public void initTree() {
		root = new DefaultMutableTreeNode("Liv");
		treeModel = new DefaultTreeModel(root);
		tree = new JTree(treeModel);

		DefaultMutableTreeNode child = new DefaultMutableTreeNode("Växter");
		root.add(child);

		child.add(new DefaultMutableTreeNode("Djur"));
		child.add(new DefaultMutableTreeNode("Svampar"));
  }

}
