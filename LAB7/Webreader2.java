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
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class Webreader2 extends JEditorPane implements ActionListener, HyperlinkListener {

  JTextField addressBar;
  static String webpage;
  ArrayList<String> href, descr, history;
  int history_index = -1;
  BookmarkModel bookmarkModel;
  MyOwnModel model;
  JTable table, bookmarkTable;
  JFrame frame;
  String html;
  HTMLEditorKit kit;
  JButton forwardButton, backButton, bookmarkButton, bookmarkButtonDelete;
  String fileContent;

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
      //System.out.println(e);
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

  public JTable buildTableModel(String[] columns, Boolean isBookmarkModel, Boolean isEditable) {
    DefaultTableModel tableModel;
    if (isBookmarkModel) {
      tableModel = new BookmarkModel(isEditable, columns);
    } else {
      tableModel = new MyOwnModel(columns);
    }
    JTable jTable = new JTable(tableModel);
    return jTable;
  }

  public void bookmarkAction() {
    bookmarkModel.addBookmark(addressBar.getText());
    bookmarkButtonDelete.setEnabled(true);
  }

  public void deleteBookmarkAction() {
    bookmarkModel.removeBookmark(addressBar.getText());
    if (bookmarkModel.getRowCount() == 0) {
      bookmarkButtonDelete.setEnabled(false);
    }
  }

  public void bookmarkClick(MouseEvent e) {
    int row = bookmarkTable.rowAtPoint(e.getPoint());
    int col = bookmarkTable.columnAtPoint(e.getPoint());
    if (row >= 0 && col >= 0) {
      String clickedString = (String)(bookmarkTable.getValueAt(row, col));
      String clickedLink = bookmarkModel.getLinkByName(clickedString);
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

    table = buildTableModel(new String[] {"Webbadress", "Beskrivning"}, false, false);
    model = (MyOwnModel)(table.getModel());

    bookmarkTable = buildTableModel(new String[] {"Bokmärkesnamn"}, true, true);
    bookmarkModel = (BookmarkModel)(bookmarkTable.getModel());
    bookmarkTable.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if ((SwingUtilities.isRightMouseButton(e))) {
          int row = bookmarkTable.rowAtPoint(e.getPoint());
          int col = bookmarkTable.columnAtPoint(e.getPoint());
          bookmarkTable.editCellAt(row, col);
        } else {
          bookmarkClick(e);
        }
      }
    });

    JPanel masterButtonPanel = new JPanel();
    masterButtonPanel.setLayout(new GridLayout(2,2));

    bookmarkButton = new JButton("Add Bookmark");
    masterButtonPanel.add(bookmarkButton);
    bookmarkButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {bookmarkAction();}
    });

    bookmarkButtonDelete = new JButton("Delete Bookmark");
    if (bookmarkModel.getRowCount() == 0) {
      bookmarkButtonDelete.setEnabled(false);
    } else {
      bookmarkButtonDelete.setEnabled(true);
    }
    masterButtonPanel.add(bookmarkButtonDelete);
    bookmarkButtonDelete.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {deleteBookmarkAction();}
    });

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
    masterButtonPanel.add(backButton);
    masterButtonPanel.add(forwardButton);

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
    //addBookmarks();
    //writeBookMarksToFile();
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
      //System.out.println(e);
      //e.printStackTrace();
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
      //t.printStackTrace();
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
    clearForwardHistory();
  }

  public static String initalWebpage() {
    return "http://www.nada.kth.se/~henrik";
  }

}
