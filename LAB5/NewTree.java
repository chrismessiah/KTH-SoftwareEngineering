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

	public static String path;

	public static void main(String[] args) {
		path = getFilePath(args);
		new NewTree();
	}

	public static String getFilePath(String[] fileStr) {
		String filePath = "";
		if (fileStr.length == 0) {filePath = "Life.xml";}
		else {filePath = fileStr[0];}
		return filePath;
	}

	// should create root, treeModel and tree.
  void initTree() {
		try {
			File xmlFile = new File(path);
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


		} catch (SAXException|ParserConfigurationException|IOException e) {
			System.out.println(e);
		}
  }

}
