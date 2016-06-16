import java.io.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class BookmarkModel extends DefaultTableModel {
	private Boolean isEditable;
	private String currentValue, oldValue;
	private ArrayList<String> names, links;
	String filename;

	public BookmarkModel(Boolean bool, String[] columns){
		isEditable = bool;
		for (String column : columns) {
				addColumn(column);
		}
		names = new ArrayList<String>();
		links = new ArrayList<String>();
		filename = "bookmarks.txt";
		readBookmarksFromFile();
	}

	public String getLinkByName(String name) {
		int i = names.indexOf(name);
		return links.get(i);
	}

	public void addBookmark(String url){
		addBookmark(url, url);
	}

	public void addBookmark(String name, String url){
		addBookmark(name, url, true);
	}

	public void addBookmark(String name, String url, Boolean writeToFile){
		names.add(name);
		links.add(url);
		addARow(name);
		if (writeToFile) {
			writeBookMarksToFile();
		}
	}

	public void removeBookmark(String url){
		int index = links.indexOf(url);
		String name = names.get(index);
		names.remove(index);
		links.remove(index);
		deleteARow(name);
		writeBookMarksToFile();
	}

	public boolean isCellEditable(int row, int column){
		return isEditable;
	}

	public void setValueAt(Object val, int row, int col){
		oldValue = (String)getValueAt(row, col);
		currentValue = (String)val;
		names.set(names.indexOf(oldValue), currentValue);
		super.setValueAt(val, row, col);
		sortAlpha();
		writeBookMarksToFile();
	}

	public void sortAlpha(){
		ArrayList<String> namesNewList = new ArrayList<String>(names);
		ArrayList<String> linksNewList = new ArrayList<String>();
		Collections.sort(namesNewList);
		int length = names.size();
		for (int i=0;i<length;i++) {
			int oldIndex = names.indexOf(namesNewList.get(i));
			linksNewList.add(links.get(oldIndex));
		}

		links = linksNewList;
		names = namesNewList;
		int rows = getRowCount();
		if (rows > 1) {
			String[] sortedBookmarks = new String[rows];
			for (int i=0;i<rows;i++) {
				sortedBookmarks[i] = (String)(getValueAt(i, 0));
			}
			Arrays.sort(sortedBookmarks);
			for (int i=0;i<rows;i++) {
				super.setValueAt(sortedBookmarks[i], i, 0);
			}
		}
	}

	private void deleteARow(String str){
		int rows = getRowCount();
    for (int i=0;i<rows;i++) {
      if (((String)(getValueAt(i, 0))).equals(str)) {
        removeRow(i);
        break;
      }
    }
	}

	public void addARow(String str, Boolean sortThis){
		addRow(new Object[]{str});
		if (sortThis) {
			sortAlpha();
		}
	}

	public void addARow(String str){
		addARow(str, true);
	}

	public void writeBookMarksToFile() {
		try {
			FileOutputStream fos = new FileOutputStream(filename);
			OutputStreamWriter out = new OutputStreamWriter(fos, "UTF-8");
			int rows = getRowCount();
			String stringToWrite = "";
			if (rows > 0) {
				for (int i=0;i<rows;i++) {
					String name = names.get(i);
					String link = links.get(i);
					stringToWrite += name + "#" + link + ",";
				}
			} else {
				stringToWrite = ",";
			}
			out.write(stringToWrite, 0, stringToWrite.length());
			out.flush();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public void readBookmarksFromFile() {
		try {
				File f = new File(filename);
				if(!f.exists()) {f.createNewFile();}
				FileInputStream fis = new FileInputStream(f);
				InputStreamReader in = new InputStreamReader(fis, "UTF-8");
				String fileContent = "";
				while(in.ready()) {
					 fileContent += String.valueOf((char)in.read());
				}
				if (!fileContent.equals("")) {
					String[] bookmarks = fileContent.split(",");
					String[] bookmarkData;
					for (String bookmark : bookmarks) {
						if (bookmark != "" && bookmark != "#" && bookmark.indexOf("#") > 0) {
							bookmarkData = bookmark.split("#");
							addBookmark(bookmarkData[0], bookmarkData[1], false);
						}
					}
				}
		} catch (IOException e) {
			System.out.println(e);
		}
	}

}
