import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;
import javax.swing.text.*;

public class Webreader extends JEditorPane {

  public static void main(String[] args) {
    Webreader obj = new Webreader();
    //obj.run();
  }

  public Webreader() {
    //JFrame frame = new JFrame();
    setEditable(false);
    //setContentType("text/html");
    JScrollPane links = new JScrollPane(this);
    HTMLEditorKit kit = new HTMLEditorKit();
    setEditorKit(kit);
    String htmlString = "<html>\n" + "<body>\n" + "<h1>Welcome!</h1>\n" + "<h2>This is an H2 header</h2>\n" + "<p>This is some sample text</p>\n" + "<p><a href=\"http://devdaily.com/blog/\">devdaily blog</a></p>\n" + "</body>\n";

    Document doc = kit.createDefaultDocument();
    setDocument(doc);
    setText(htmlString);

    JFrame frame = new JFrame();
    frame.getContentPane().add(links, BorderLayout.CENTER);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(new Dimension(300,200));
    frame.setTitle("LAB6");
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    //JTable table = new JTable(50,2);
    // add(table);
  }

  public void run() {
    JTable table = new JTable(50,2);
    JScrollPane links = new JScrollPane(table);
  }
}
