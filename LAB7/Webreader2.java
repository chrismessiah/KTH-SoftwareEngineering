//
//  Diffrence to Webreader.java
//  This method uses "HTMLEditorKit().read(reader,doc,0);" to display the html
//  however it breaks relative image-paths, preventing them from rendering
//  On the other hand it handles åäö-chars fine.

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
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class Webreader2 extends JEditorPane implements ActionListener, HyperlinkListener {

  JTextField addressBar;
  static String webpage;
  ArrayList<String> href, descr, history;
  int history_index = -1;
  DefaultTableModel model;
  JTable table;
  JFrame frame;
  String html;
  HTMLEditorKit kit;
  JButton forwardButton, backButton;

  public static void main(String[] args) {
    webpage = initalWebpage();
    Webreader2 obj = new Webreader2();
  }

  public void getHtml() {
    try {
      InputStream in = new URL(webpage).openConnection().getInputStream();
      InputStreamReader reader = new InputStreamReader(in, "ISO-8859-1");
      html = "";
      while(reader.ready()) {
         html += String.valueOf((char)reader.read());
      }
    } catch (IOException e) {
      System.out.println(e);
    }
  }

  public void getHrefLinks() {
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
  }

  public void updateTableModel() {
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

  public void buildTableModel() {
    model = new DefaultTableModel();
    table = new JTable(model);
    model.addColumn("Webbadress");
    model.addColumn("Beskrivning");
  }

  public Webreader2() {
    history = new ArrayList<String>();
    kit = new HTMLEditorKit();
    setEditable(false);
    setContentType("text/html");

    buildFrame();
    buildTableModel();
    addHyperlinkListener(this);

    JPanel buttonPanel = new JPanel();

    backButton = new JButton("<");
    backButton.setEnabled(false);
    backButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {backAction();}
    });

    forwardButton = new JButton(">");
    forwardButton.setEnabled(false);
    forwardButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {forwardAction();}
    });
    buttonPanel.add(backButton);
    buttonPanel.add(forwardButton);
    buttonPanel.setLayout(new GridLayout());

    JPanel parentPanel = new JPanel(new BorderLayout());
    JScrollPane web = new JScrollPane(this);
    JScrollPane links = new JScrollPane(table);

    Container cont = new Container();
    cont.add(web);
    cont.add(links);
    cont.setLayout(new GridLayout());
    parentPanel.add(buttonPanel, BorderLayout.PAGE_START);
    parentPanel.add(cont, BorderLayout.CENTER);

    frame.add(parentPanel);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);


    updatePage();
  }

  public void backAction() {
    history_index -= 1;
    String url = history.get(history_index);
    addressBar.setText(url);
    updatePage(false);
  }

  public void forwardAction() {
    history_index += 1;
    String url = history.get(history_index);
    addressBar.setText(url);
    updatePage(false);
  }

  public void activateNavButtons() {
    if (history_index > 0) {
      backButton.setEnabled(true);
    } else {
      backButton.setEnabled(false);
    }

    if (history_index < history.size()-1) {
      forwardButton.setEnabled(true);
    } else {
      forwardButton.setEnabled(false);
    }
  }

  public void setPageHanlder(String url) {
    try {
      Document doc = kit.createDefaultDocument();
      doc.putProperty("IgnoreCharsetDirective", new Boolean(true));
      InputStream in = new URL(webpage).openConnection().getInputStream();
      InputStreamReader reader = new InputStreamReader(in, "ISO-8859-1");
      kit.read(reader,doc,0);
      setDocument(doc);
    } catch(IOException|BadLocationException e) {
      System.out.println(e);
      e.printStackTrace();
      addressBar.setText("ERROR BAD URL");
    }
  }

  public void updatePage() {
    updatePage(true);
  }

  public void updatePage(Boolean addToHistory) {
     try {
      webpage = addressBar.getText();
      if (addToHistory) {
        history.add(webpage);
        history_index += 1;
      }
      activateNavButtons();
      setPageHanlder(webpage);
      getHtml();
      getHrefLinks();
      updateTableModel();
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  public void actionPerformed(ActionEvent e) {
    for (int i=0; i<(history.size()-1-history_index); i++) {
      history.remove(history.size()-1);
    }
    updatePage();
  }

  public void hyperlinkUpdate(HyperlinkEvent e) {
    HyperlinkEvent.EventType eventType = e.getEventType();
    if (eventType == HyperlinkEvent.EventType.ACTIVATED) {
      String url = e.getURL().toString();
      addressBar.setText(url);
      updatePage();
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
