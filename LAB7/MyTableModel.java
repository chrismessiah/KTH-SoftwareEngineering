import javax.swing.table.DefaultTableModel;
import java.util.Arrays;

public class MyTableModel extends DefaultTableModel {
	private Boolean isEditable;
	String currentValue, oldValue;

	public MyTableModel(Boolean bool){
		isEditable = bool;
	}

	public boolean isCellEditable(int row, int column){
		return isEditable;
	}

	public void setValueAt(Object val, int row, int col){
		System.out.println(val);
		super.setValueAt(val, row, col);
	}

	public void sortAlpha(){
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

	public void deleteARow(String str){
		int rows = getRowCount();
    for (int i=0;i<rows;i++) {
      if (((String)(getValueAt(i, 0))).equals(str)) {
        removeRow(i);
        break;
      }
    }
	}

	public void addARow(String str){
		addRow(new Object[]{str});
    sortAlpha();
	}

}
