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
  MyTableModel model, bookmarkModel;
  JTable table, bookmarkTable;
  JFrame frame;
  String html;
  HTMLEditorKit kit;
  JButton forwardButton, backButton, bookmarkButton;

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

  public JTable buildTableModel(String[] columns) {
    MyTableModel tableModel = new MyTableModel();
    JTable jTable = new JTable(tableModel);
    for (String column : columns) {
        tableModel.addColumn(column);
    }
    return jTable;
  }

  public void bookmarkAction() {
    bookmarkModel.addRow(new Object[]{addressBar.getText()});
  }

  public void bookmarkClick(MouseEvent e) {
    int row = bookmarkTable.rowAtPoint(e.getPoint());
    int col = bookmarkTable.columnAtPoint(e.getPoint());
    if (row >= 0 && col >= 0) {
      String clickedLink = (String)(bookmarkTable.getValueAt(row, col));
      addressBar.setText(clickedLink);
      clearForwardHistory();
      updatePage();
    }
  }

  public Webreader2() {
    history = new ArrayList<String>();
    kit = new HTMLEditorKit();
    setEditable(false);
    setContentType("text/html");

    buildFrame();
    addHyperlinkListener(this);

    table = buildTableModel(new String[] {"Webbadress", "Beskrivning"});
    model = (MyTableModel)(table.getModel());

    bookmarkTable = buildTableModel(new String[] {"Bokmärkesnamn"});
    bookmarkModel = (MyTableModel)(bookmarkTable.getModel());
    bookmarkTable.getColumnModel().getColumn(0).setPreferredWidth(27);
    bookmarkTable.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {bookmarkClick(e);}
    });

    JPanel fBButtonPanel = new JPanel();
    JPanel masterButtonPanel = new JPanel();
    masterButtonPanel.setLayout(new GridLayout(2,1));

    bookmarkButton = new JButton("Bookmark");
    bookmarkButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {bookmarkAction();}
    });


    masterButtonPanel.add(fBButtonPanel);
    masterButtonPanel.add(bookmarkButton);

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
    fBButtonPanel.add(backButton);
    fBButtonPanel.add(forwardButton);
    fBButtonPanel.setLayout(new GridLayout());

    JPanel parentPanel = new JPanel(new BorderLayout());
    JScrollPane web = new JScrollPane(this);
    JScrollPane links = new JScrollPane(table);
    JScrollPane bookmarkPane = new JScrollPane(bookmarkTable);

    Container cont = new Container();
    cont.add(web);
    cont.add(links);
    cont.add(bookmarkPane);
    cont.setLayout(new GridLayout());
    parentPanel.add(masterButtonPanel, BorderLayout.PAGE_START);
    parentPanel.add(cont, BorderLayout.CENTER);

    frame.add(parentPanel);
    frame.pack();
    frame.setLocationRelativeTo(null);
    frame.setVisible(true);


    updatePage();
    addBookmarks();
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

  public void clearForwardHistory() {
    int current_size = history.size();
    for (int i=0; i<(current_size-1-history_index); i++) {
      history.remove(history.size()-1);
    }
  }

  public void actionPerformed(ActionEvent e) {
    clearForwardHistory();
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
    return "http://www.nada.kth.se/~henrik";
  }

  public void addBookmarks() {
    String[] bookmarksToAdd = new String[] {"http://www.nada.kth.se/~henrik","http://www.nada.kth.se/~ala", "http://www.nada.kth.se/~karlan","http://www.nada.kth.se/~viggo","http://www.nada.kth.se/~vahid","http://www.nada.kth.se/~johanh","http://www.nada.kth.se/~ann"};
    for (String bookmark : bookmarksToAdd) {
      bookmarkModel.addRow(new Object[]{bookmark});
    }
  }
}
