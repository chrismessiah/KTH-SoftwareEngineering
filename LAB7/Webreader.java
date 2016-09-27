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

// Websites that might be useable
//
// http://www.nada.kth.se/~orjan
// http://www.nada.kth.se/~ala
// http://www.nada.kth.se/~henrik
// http://www.nada.kth.se/~viggo
// http://www.nada.kth.se/~vahid
// http://www.nada.kth.se/~johanh
// http://www.nada.kth.se/~ann

public class Webreader extends JEditorPane implements ActionListener, HyperlinkListener {

  boolean showedError;
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
  JButton forwardButton, backButton, addBookmarkButton, deleteBookmarkButton;
  String fileContent;

  public static void main(String[] args) {
    webpage = "http://www.nada.kth.se/~henrik";
    Webreader obj = new Webreader();
  }

  // stores the a-href attributes and content
  // in the global ArrayLists
  public void getHrefLinks() {
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
      //System.out.println(e);
      showErrorPopup("BAD URL: " + webpage);
    }
  }
  
  // updates the model of the link table
  // displayed in the frame.
  public void updateLinkTableModel() {
    int rows = model.getRowCount();
    for (int i=0;i<rows;i++) {
      model.removeRow(0);
    }
    for (int i=0;i<href.size(); i++) {
      model.addRow(new Object[]{href.get(i), descr.get(i)});
    }
  }

  // used once in beginning of run
  // to create browser-frame
  public void buildFrame() {
    frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setPreferredSize(new Dimension(800,600));
    frame.setTitle("LAB7");
    addressBar = new JTextField();
    addressBar.addActionListener(this);
    addressBar.setText(webpage);
    frame.add(addressBar, BorderLayout.NORTH);
  }

  // a general table creator used for both urls and bookmarks
  public JTable buildTable(String[] columns, Boolean isBookmarkModel, Boolean isEditable) {
    DefaultTableModel tableModel;
    if (isBookmarkModel) {
      tableModel = new BookmarkModel(isEditable, columns);
    } else {
      tableModel = new MyOwnModel(columns);
    }
    JTable jTable = new JTable(tableModel);
    return jTable;
  }

  // Action: click on add-bookmark button
  // will run methods on BookmarkModel class
  public void addBookmarkAction() {
    bookmarkModel.addBookmark(addressBar.getText());
    deleteBookmarkButton.setEnabled(true);
  }

  // Action: click on remove-bookmark button
  // will run methods on BookmarkModel class
  public void deleteBookmarkAction() {
    bookmarkModel.removeBookmark(addressBar.getText());
    if (bookmarkModel.getRowCount() == 0) {
      deleteBookmarkButton.setEnabled(false);
    }
  }

  // Action: click on specific bookmark 
  public void handleAllBookmarkTableClicks(MouseEvent e) {
    int row = bookmarkTable.rowAtPoint(e.getPoint());
    int col = bookmarkTable.columnAtPoint(e.getPoint());
    if ((SwingUtilities.isRightMouseButton(e))) {
      
      // right mouse-key: edit mode
      bookmarkTable.editCellAt(row, col);
    } else {
      
      // left mouse-key (or other): goto bokmark url mode
      if (row > -1 && col > -1) {
        String clickedString = (String)(bookmarkTable.getValueAt(row, col));
        String clickedLink = bookmarkModel.getLinkByName(clickedString);
        addressBar.setText(clickedLink);
        clearForwardHistory();
        updatePage();
      }
    }
  }

  public Webreader() {
    history = new ArrayList<String>();
    kit = new HTMLEditorKit();
    setEditable(false);

    buildFrame();
    addHyperlinkListener(this); // makes link clickable
    showedError = false;

    // create the url-table
    table = buildTable(new String[] {"Webbadress", "Beskrivning"}, false, false);
    model = (MyOwnModel)(table.getModel());

    // create the bookmark-table with mouse-listener
    bookmarkTable = buildTable(new String[] {"Bokmärkesnamn"}, true, true);
    bookmarkModel = (BookmarkModel)(bookmarkTable.getModel());
    bookmarkTable.addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {handleAllBookmarkTableClicks(e);}
    });

    JPanel masterButtonPanel = new JPanel();
    masterButtonPanel.setLayout(new GridLayout(2,2));



    // ********** Repetitive code bellow **********
    addBookmarkButton = new JButton("Add Bookmark");
    masterButtonPanel.add(addBookmarkButton);
    addBookmarkButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {addBookmarkAction();}
    });

    deleteBookmarkButton = new JButton("Delete Bookmark");
    masterButtonPanel.add(deleteBookmarkButton);
    deleteBookmarkButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {deleteBookmarkAction();}
    });
    // ********** Repetitive code above **********
    
    
    
    
    // ********** Repetitive code bellow **********
    // wish I could use JS "pass-function-as-variable" here for 
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
    // ********** Repetitive code above **********
    
    
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

    activateOrDeactivateNavButtons();
    updatePage();
    //addBookmarks();
    //writeBookMarksToFile();
  }

  // handles navigation upon press on backward or forward
  public void backOrForwardNavigationAction(String direction) {
    if(direction == "back") {
      history_index -= 1;
    } else if(direction == "forward") {
      history_index += 1;
    } else {System.out.println("ERROR in backOrForwardNavigationAction");}
    String url = history.get(history_index);
    addressBar.setText(url);
    updatePage(false);
  }
  
  // Action: click on back-button
  public void backAction() {
    backOrForwardNavigationAction("back");
  }

  // Action: click on back-button
  public void forwardAction() {
    backOrForwardNavigationAction("forward");
  }

  // handles whenether nav buttons should be active or not
  public void activateOrDeactivateNavButtons() {
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

  // sets the page of the url, makes
  // it viewable in browser.
  public void setPageHanlder(String url) {
    try {
      setPage(url);
    } catch(Exception e) {
      //System.out.println(e);
      //e.printStackTrace();
      showErrorPopup("BAD URL: " + url);
    }
  }

  // refer to complete function, this one assumes
  // the url should be added to history
  public void updatePage() {
    updatePage(true);
  }

  // handles all the stuff needed for when 
  // a new url has been entered.
  // with ERROR DETECTION to prevent adding
  // faulty links to history.
  public void updatePage(Boolean addToHistory) {
    showedError = false;
     try {
      webpage = addressBar.getText();
      activateOrDeactivateNavButtons();
      setPageHanlder(webpage);
      getHrefLinks();
      
      // Error detection incorp.
      if (addToHistory && !showedError) {
        history.add(webpage);
        history_index += 1;
        activateOrDeactivateNavButtons();
      }
      
      updateLinkTableModel();
    } catch (Throwable t) {
      //t.printStackTrace();
    }
  }

  // removes all urls from global history list
  // with respect to current index position
  public void clearForwardHistory() {
    int current_size = history.size();
    for (int i=0; i<(current_size-1-history_index); i++) {
      history.remove(history.size()-1);
    }
  }

  // Triggered on pressing enter on url-bar,
  // will clear history
  public void actionPerformed(ActionEvent e) {
    clearForwardHistory();
    updatePage();
  }

  // Triggered upon click on link in browser,
  // will clear history
  public void hyperlinkUpdate(HyperlinkEvent e) {
    HyperlinkEvent.EventType eventType = e.getEventType();
    if (eventType == HyperlinkEvent.EventType.ACTIVATED) {
      String url = e.getURL().toString();
      addressBar.setText(url);
      updatePage();
    }
    clearForwardHistory();
  }

  // JOptionPane popup, prevents multiple popups
  // occuring at the same time 
  public void showErrorPopup(String message) {
    if (!showedError) {
      showedError = true;
      JOptionPane dialog = new JOptionPane();
      dialog.showMessageDialog(this, "Error: " + message);
    }
  }


}
