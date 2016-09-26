import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.html.*;
import javax.swing.text.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.util.Arrays;
import java.util.ArrayList;

public class Webreader extends JEditorPane implements ActionListener, HyperlinkListener {

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
  
  public Webreader() {
    setEditable(false);
    buildFrame();
    buildModel();
    addHyperlinkListener(this);

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
    
    updatePage();
  }
  
  public void updatePage() {
    try {
      webpage = addressBar.getText();
      getHrefLinks(webpage);
      setPageHanlder(webpage);
      updateModel();
    } catch (Throwable t) {
      //t.printStackTrace();
    }
  }

  public void getHrefLinks(String webpage) {
    try {
      InputStream in = new URL(webpage).openConnection().getInputStream();
      InputStreamReader reader = new InputStreamReader(in, "ISO-8859-1");
      
      HTMLDocument doc = new HTMLDocument();
      doc.putProperty("IgnoreCharsetDirective", Boolean.TRUE);
      new HTMLEditorKit().read(reader,doc,0);	//puts the website html in a document	
      
      href = new ArrayList<String>();
      descr = new ArrayList<String>();
      
      for (HTMLDocument.Iterator iter = doc.getIterator(HTML.Tag.A); iter.isValid(); iter.next()) {
        String elementAttribute = (String) iter.getAttributes().getAttribute(HTML.Attribute.HREF); 

        int firstCharPos = iter.getStartOffset();
        int lastCharPos = iter.getEndOffset();
        String elementContent = doc.getText(firstCharPos, lastCharPos-firstCharPos);

        if (href.size() < 50) {
          href.add(elementAttribute);
          descr.add(elementContent);  
        } else {break;}
      }
    } catch (Exception e) {
      System.out.println(e);
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

  public void setPageHanlder(String url) {
    try {
      setPage(url);
    } catch (IOException e) {
      // JOptionPane
      // show message dialoh
      addressBar.setText("ERROR BAD URL");
    }
  }

  public void actionPerformed(ActionEvent e) {
    updatePage();
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
  
  public void hyperlinkUpdate(HyperlinkEvent e) {
    HyperlinkEvent.EventType eventType = e.getEventType();
    if (eventType == HyperlinkEvent.EventType.ACTIVATED) {
      String url = e.getURL().toString();
      addressBar.setText(url);
      updatePage();
    }
  }
}
