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
    String webpage;
    //webpage = "http://www.nada.kth.se/~orjan";
    webpage = "http://www.nada.kth.se/~ala";
    //webpage = "http://www.nada.kth.se/~viggo";
    //webpage = "http://www.nada.kth.se/~vahid";
    //webpage = "http://www.nada.kth.se/~johanh";
    //webpage = "http://www.nada.kth.se/~ann";
    setEditable(false);
    try {
      setPage(webpage);
    }
    catch (IOException e) {
      System.out.println(e);
      setContentType("text/html");
      HTMLEditorKit kit = new HTMLEditorKit();
      setEditorKit(kit);
      Document doc = kit.createDefaultDocument();
      setDocument(doc);
      String htmlString = "<html>\n" + "<body>\n" + "<h1>Welcome!</h1>\n" + "<h2>This is an H2 header</h2>\n" + "<p>This is some sample text</p>\n" + "<p><a href=\"http://devdaily.com/blog/\">devdaily blog</a></p>\n" + "</body>\n";
      setText(htmlString);
    }

    JScrollPane links = new JScrollPane(this);
    JFrame frame = new JFrame();
    frame.getContentPane().add(links);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(800,600));
    frame.setTitle("LAB6");
    frame.setLocationRelativeTo(null);
    frame.pack();
    frame.setVisible(true);

    //JTable table = new JTable(50,2);
    // add(table);
  }

  public void run() {
    JTable table = new JTable(50,2);
    JScrollPane links = new JScrollPane(table);
  }
}
