import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;
import javax.swing.text.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class Webreader extends JEditorPane implements ActionListener {

  JTextField addressBar;
  static String webpage;
  ArrayList<String> href, descr;

  public static void main(String[] args) {
    webpage = initalWebpage();
    Webreader obj = new Webreader();
    //obj.run();
  }

  public void getHrefLinks(String url) {
    try {
      InputStream in = new URL(url).openConnection().getInputStream();
      InputStreamReader reader = new InputStreamReader(in, "UTF-8");
      String html = "";
      while(reader.ready()) {
         html += String.valueOf((char)reader.read());
      }
      String trimmedHtml = html.replaceAll("\n","");
      href = new ArrayList<String>();
      descr = new ArrayList<String>();
      String[] lines = trimmedHtml.split("<");
      String[] subLines;
      for (String line : lines) {
        if (line.length() > 5) {
          if (line.substring(0,6).equals("A HREF")) {
            //System.out.println(line);
            subLines = line.split(">");
            String textUrl = subLines[0].substring(8, subLines[0].length()-1);
            //System.out.println(textUrl);
            href.add(textUrl);
            if (subLines.length > 1) {
              System.out.println(subLines[1]);
              descr.add(subLines[1]);
            } else {
              descr.add("");
            }
          }
        }
      }
    } catch (IOException e) {
      System.out.println(e);
      // setContentType("text/html");
      // HTMLEditorKit kit = new HTMLEditorKit();
      // setEditorKit(kit);
      // Document doc = kit.createDefaultDocument();
      // setDocument(doc);
      // String htmlString = "<html><body><h1>Bad URL!</h1></body></html>";
      // setText(htmlString);
    }
  }

  public Webreader() {
    setEditable(false);
    try {
      //setContentType("text/html");
      setPage(webpage);
      getHrefLinks(webpage);

      // InputStream in = new URL(webpage).openConnection().getInputStream();
      // InputStreamReader reader = new InputStreamReader(in, "UTF-8");

      //HTMLEditorKit kit = new HTMLEditorKit();
      //Document doc = kit.createDefaultDocument();
      //doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
      //setDocument(doc);

      // String html = "";
      // while(reader.ready()) {
      //    html += String.valueOf((char)reader.read());
      // }
      // //setText(html);
      // String trimmedHtml = html.replaceAll("\\s+","");
      // ArrayList<String> href = new ArrayList<String>();
      // ArrayList<String> descr = new ArrayList<String>();
      // String[] lines = trimmedHtml.split("<");
      // String[] subLines;
      // for (String line : lines) {
      //   if (line.length() > 5) {
      //     if (line.substring(0,5).equals("AHREF")) {
      //       System.out.println(line);
      //       subLines = line.split(">");
      //       href.add(subLines[0]);
      //       descr.add(subLines[1]);
      //     }
      //   }
      // }

    } catch (IOException e) {
      System.out.println(e);
      setContentType("text/html");
      HTMLEditorKit kit = new HTMLEditorKit();
      setEditorKit(kit);
      Document doc = kit.createDefaultDocument();
      setDocument(doc);
      String htmlString = "<html><body><h1>Bad URL!</h1></body></html>";
      setText(htmlString);
    }

    JScrollPane web = new JScrollPane(this);
    JFrame frame = new JFrame();

    DefaultTableModel model = new DefaultTableModel();
    JTable table = new JTable(model);
    model.addColumn("Webbadress");
    model.addColumn("Beskrivning");
    for (int i=0;i<href.size(); i++) {
      model.addRow(new Object[]{href.get(i), descr.get(i)});
    }
    JScrollPane links = new JScrollPane(table);

    addressBar = new JTextField();
    addressBar.addActionListener(this);
    addressBar.setText(webpage);
    frame.add(addressBar, BorderLayout.NORTH);

    JPanel parentPanel = new JPanel(new BorderLayout());
    Container cont = new Container();
    cont.add(web);
    cont.add(links);
    cont.setLayout(new GridLayout());
    parentPanel.add(cont);


    frame.add(parentPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(800,600));
    frame.setTitle("LAB6");
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);

    //JTable table = new JTable(50,2);
    // add(table);
  }

  public void actionPerformed(ActionEvent e) {
    try {
      webpage = addressBar.getText();
      getHrefLinks(webpage);
      setPage(webpage);
      for (String link : href) {
        System.out.println(link);
      }
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  // public void run() {
  //
  // }

  public static String initalWebpage() {
    String url;
    //url = "http://www.nada.kth.se/~orjan";
    //url = "http://www.nada.kth.se/~ala";
    url = "http://www.nada.kth.se/~henrik";
    //url = "http://www.nada.kth.se/~viggo";
    //url = "http://www.nada.kth.se/~vahid";
    //url = "http://www.nada.kth.se/~johanh";
    //url = "http://www.nada.kth.se/~ann";
    return url;
  }
}
