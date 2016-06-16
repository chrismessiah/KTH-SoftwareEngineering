import javax.swing.table.DefaultTableModel;

public class MyOwnModel extends DefaultTableModel {

	public MyOwnModel(String[] columns) {
		for (String column : columns) {
				addColumn(column);
		}
	}

	public boolean isCellEditable(int row, int column){
		return false;
	}

}
