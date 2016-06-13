import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {

	public boolean isCellEditable(int row, int column){  
		return false;  
	}

}