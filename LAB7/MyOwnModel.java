import javax.swing.table.DefaultTableModel;

/* Class made to prevent editing the links 
*  in the href table.
*/

public class MyOwnModel extends DefaultTableModel {

	// basic constructor for ease use
	public MyOwnModel(String[] columns) {
		for (String column : columns) {
				addColumn(column);
		}
	}
	
	// inherited from DefaultTableModel, overridden here
	public boolean isCellEditable(int row, int column){
		return false;
	}

}
