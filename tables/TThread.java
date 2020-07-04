package tables;
import others.Colors;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Font;
public class TThread{
	Colors color = new Colors();

	public JTable table;
  	DefaultTableModel model = new DefaultTableModel();
  	DefaultTableCellRenderer render = new DefaultTableCellRenderer();
	public TThread(){
		render.setHorizontalAlignment(SwingConstants.CENTER);
	  	render.setBackground(color.purple);
	  	render.setForeground(color.white);
	
	  	model.addColumn("Thread");
	  	model.addColumn("Estado");

	  	table = new JTable(model);
	  	table.setRowHeight(38);
	  	//Column-1
	  	table.getColumnModel().getColumn(0).setPreferredWidth(80);
	    table.getColumnModel().getColumn(0).setMaxWidth(80);
	    //Column-2
	    table.getColumnModel().getColumn(1).setPreferredWidth(105);
	    table.getColumnModel().getColumn(1).setMaxWidth(105);
	    for (int i=0; i<2; i++)
	    	table.getColumnModel().getColumn(i).setCellRenderer(render);
	    for (int i=0; i<4; i++)
	    	model.addRow(new String [] {
        		"Thread-"+(i+1),""
      		});
	}

	public void add(String name){
      	model.addRow(new String [] {
        	name,""
      	});
	}

	public void clear(){
	    try{
	      	int threads = table.getRowCount();
	      	for (int i=0; i<=threads; i++)
					table.setValueAt("",i,1);
	    }catch(Exception e){}
  	}

	public void updateStatus(String status, int row){
    	table.setValueAt(status, row, 1);
	}

	public int getRow(){
		int row = 0;
	    try{
	      	row = table.getRowCount();
	    }catch(Exception e){}
	    return row;
  	}
}