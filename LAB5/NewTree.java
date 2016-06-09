// http://www.csc.kth.se/utbildning/kth/kurser/DD2385/prutt16/laborationer/labb5.php

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;

class NewTree extends TreeFrame {

	String filePath;

	public static void main(String[] args) {
		new NewTree(args);
	}

	public NewTree(String[] argArray) {
		getFilePath(argArray);
		// initTree(); // DO NOT CALL FROM CONSTRUCT
	}

	public void getFilePath(String[] fileStr) {
		if (fileStr.length == 0) {filePath = "Life.xml";}
		else {filePath = fileStr[0];}
	}

	// should create root, treeModel and tree.
  public void initTree() {
		//try {
			File xmlFile = new File(filePath);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(xmlFile);
			doc.getDocumentElement().normalize(); // Reason: http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			System.out.println(doc.getDocumentElement().getNodeName());

			root = new DefaultMutableTreeNode(doc.getDocumentElement().getNodeName());
			//root = new DefaultMutableTreeNode("Liv");
			treeModel = new DefaultTreeModel(root);
			tree = new JTree(treeModel);

			DefaultMutableTreeNode child = new DefaultMutableTreeNode("Växter");
			root.add(child);
			child.add(new DefaultMutableTreeNode("Djur"));
			child.add(new DefaultMutableTreeNode("Svampar"));


		//} catch (SAXException|ParserConfigurationException|IOException e) {
		//	System.out.println(e);
		//}
  }

}
