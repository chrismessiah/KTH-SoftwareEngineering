// http://www.csc.kth.se/utbildning/kth/kurser/DD2385/prutt16/laborationer/labb5.php

import javax.swing.*;
import javax.swing.tree.*;
import java.io.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

class NewTree extends TreeFrame {

	public static String path;
	public static Document doc;

	public static void main(String[] args) {
		path = getFilePath(args);
		doc = getDoc();
		new NewTree();
	}

	public static String getFilePath(String[] fileStr) {
		String filePath = "";
		if (fileStr.length == 0) {filePath = "Life.xml";}
		else {filePath = fileStr[0];}
		return filePath;
	}

	public static Document getDoc() {
		try {
			File xmlFile = new File(path);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document docXML = dBuilder.parse(xmlFile);
			docXML.getDocumentElement().normalize(); // Reason: http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
			return docXML;
		} catch (SAXException|ParserConfigurationException|IOException e) {
			System.out.println(e);
		}
		return null;
	}

	public String getNodeAttributeValue(Node node) {
		return getNodeAttributeValue(node, 0);
	}

	public String getNodeAttributeValue(Node node, int i) {
		NamedNodeMap nodeMap = node.getAttributes();
		Node nodeAttribute = nodeMap.item(i);
		return nodeAttribute.getNodeValue();
	}

	public void recursiveBuildUp(Node node, DefaultMutableTreeNode nodeTree) {
		if (node.hasChildNodes()) {
			NodeList childList = node.getChildNodes();
			for (int i = 0; i < childList.getLength(); i++) {
				Node childNode = childList.item(i);
				if (childNode.getNodeType() == Node.ELEMENT_NODE) {
					String childName = getNodeAttributeValue(childNode);
					DefaultMutableTreeNode childTree = new DefaultMutableTreeNode(childName);
					nodeTree.add(childTree);
					recursiveBuildUp(childNode, childTree);
				}
			}
		}
	}

	// should create root, treeModel and tree.
  public void initTree() {
		Node rootXML = doc.getDocumentElement();
		String rootView = getNodeAttributeValue(rootXML);
		root = new DefaultMutableTreeNode(rootView);
		treeModel = new DefaultTreeModel(root);
		tree = new JTree(treeModel);
		recursiveBuildUp(rootXML, root);
  }

}
