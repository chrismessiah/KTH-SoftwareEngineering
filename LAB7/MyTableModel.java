import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;

public class MyTableModel extends DefaultTableModel {
	private Boolean isEditable;
	private String currentValue, oldValue;
	private ArrayList<String> names, links;

	public MyTableModel(Boolean bool){
		isEditable = bool;
		names = new ArrayList<String>();
		links = new ArrayList<String>();
	}

	public void addBookmark(String url){
		names.add(url);
		links.add(url);
		addARow(url);
	}

	public void removeBookmark(String url){
		int index = links.indexOf(url);
		String name = names.get(index);
		names.remove(index);
		links.remove(index);
		deleteARow(name);
	}

	public boolean isCellEditable(int row, int column){
		return isEditable;
	}

	public void setValueAt(Object val, int row, int col){
		oldValue = (String)getValueAt(row, col);
		currentValue = (String)val;
		names.set(names.indexOf(oldValue), currentValue);
		sortAlpha();
		super.setValueAt(val, row, col);
	}

	public void sortAlpha(){
		int length = names.size();
		int[] indexList = new int[length];
		ArrayList<String> namesNewList = new ArrayList<String>(names);
		ArrayList<String> linksNewList = new ArrayList<String>();
		Collections.sort(namesNewList);
		for (int i=0;i<length;i++) {
			indexList[i] = namesNewList.indexOf(names.get(i));
		}
		for (int i=0;i<length;i++) {
			linksNewList.add( links.get(indexList[i]) );
		}
		links = linksNewList;
		names = namesNewList;
		// has to be rewritten to handle the arraylists

		for (int i=0;i<length;i++) {
			System.out.println(names.get(i));
			System.out.println(links.get(i));
			System.out.println("");
		}


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

	private void addARow(String str){
		addRow(new Object[]{str});
    sortAlpha();
	}

}
