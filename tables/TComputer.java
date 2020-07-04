package tables;
import others.Colors;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Font;
import java.util.ArrayList;
public class TComputer{
	Colors color = new Colors();

	public JTable table;
  	DefaultTableModel model = new DefaultTableModel();
  	DefaultTableCellRenderer render = new DefaultTableCellRenderer();
	public TComputer(){
		render.setHorizontalAlignment(SwingConstants.CENTER);
	  	render.setBackground(color.black);
	  	render.setForeground(color.white);
	
	  	model.addColumn("Equipo");
	  	model.addColumn("Estado");

	  	table = new JTable(model);
	  	table.setRowHeight(50);
	  	//Column-1
	  	table.getColumnModel().getColumn(0).setPreferredWidth(80);
	    table.getColumnModel().getColumn(0).setMaxWidth(80);
	    //Column-2
	    table.getColumnModel().getColumn(1).setPreferredWidth(105);
	    table.getColumnModel().getColumn(1).setMaxWidth(105);
	    for (int i=0; i<2; i++)
	    	table.getColumnModel().getColumn(i).setCellRenderer(render);
	}

	public void add(String user){
      	model.addRow(new String [] {
        	user,"Disponible"
      	});
	}

	public void updateStatus(String status, int row){
    	table.setValueAt(status, row, 1);
	}
}