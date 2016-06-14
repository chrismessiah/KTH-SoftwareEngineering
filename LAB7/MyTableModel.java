import javax.swing.table.DefaultTableModel;

public class MyTableModel extends DefaultTableModel {
	private Boolean isEditable;

	public MyTableModel(Boolean bool){
		isEditable = bool;
	}

	public boolean isCellEditable(int row, int column){
		return isEditable;
	}

}
