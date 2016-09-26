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
  DefaultTableModel model;
  JTable table;
  JFrame frame;

  public static void main(String[] args) {
    webpage = initalWebpage();
    Webreader obj = new Webreader();
  }

  public void getHrefLinks(String url) {
    try {
      InputStream in = new URL(url).openConnection().getInputStream();
      InputStreamReader reader = new InputStreamReader(in, "ISO-8859-1");
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
            subLines = line.split(">");
            String textUrl = subLines[0].substring(8, subLines[0].length()-1);
            href.add(textUrl);
            if (subLines.length > 1) {
              descr.add(subLines[1].trim());
            } else {
              descr.add("");
            }
          }
        }
      }
    } catch (IOException e) {
      //System.out.println(e);
    }
  }

  public void updateModel() {
    int rows = model.getRowCount();
    for (int i=0;i<rows;i++) {
      model.removeRow(0);
    }
    for (int i=0;i<href.size(); i++) {
      model.addRow(new Object[]{href.get(i), descr.get(i)});
    }
  }

  public void buildFrame() {
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(800,600));
    frame.setTitle("LAB6");
    addressBar = new JTextField();
    addressBar.addActionListener(this);
    addressBar.setText(webpage);
    frame.add(addressBar, BorderLayout.NORTH);
  }

  public void buildModel() {
    model = new DefaultTableModel();
    table = new JTable(model);
    model.addColumn("Webbadress");
    model.addColumn("Beskrivning");
  }

  public void errorMessage() {
    setContentType("text/html");
    HTMLEditorKit kit = new HTMLEditorKit();
    setEditorKit(kit);
    Document doc = kit.createDefaultDocument();
    setDocument(doc);
    String htmlString = "<html><body><h1>Bad URL!</h1></body></html>";
    setText(htmlString);
  }

  public Webreader() {
    setEditable(false);

    setPageHanlder(webpage);
    getHrefLinks(webpage);
    buildFrame();
    buildModel();
    updateModel();

    JPanel parentPanel = new JPanel(new BorderLayout());
    JScrollPane web = new JScrollPane(this);
    JScrollPane links = new JScrollPane(table);

    Container cont = new Container();
    cont.add(web);
    cont.add(links);
    cont.setLayout(new GridLayout());
    parentPanel.add(cont);

    frame.add(parentPanel);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);
  }

  public void setPageHanlder(String url) {
    try {
      setPage(url);
    } catch (IOException e) {
      addressBar.setText("ERROR BAD URL");
    }
  }

  public void actionPerformed(ActionEvent e) {
    try {
      webpage = addressBar.getText();
      getHrefLinks(webpage);
      setPageHanlder(webpage);
      updateModel();
    } catch (Throwable t) {
      //t.printStackTrace();
    }
  }

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
