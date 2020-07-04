package tables;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import java.awt.Color;

public class TInterface{
	public JTable table;
  	DefaultTableModel model = new DefaultTableModel();
  	DefaultTableCellRenderer render = new DefaultTableCellRenderer();

  	public TInterface(){
	    model.addColumn("Tipo");
	    model.addColumn("SO");
	    model.addColumn("User");
	    model.addColumn("IP");
	    model.addColumn("Estado");
	    table = new JTable(model);

	    render.setHorizontalAlignment(SwingConstants.CENTER);
	    render.setBackground(Color.blue);
	    render.setForeground(Color.white);
	    //Column-1
	    table.getColumnModel().getColumn(0).setPreferredWidth(75);
	    table.getColumnModel().getColumn(0).setMaxWidth(75);
	    table.getColumnModel().getColumn(0).setCellRenderer(render);
	    //Column-2
	    table.getColumnModel().getColumn(1).setPreferredWidth(90);
	    table.getColumnModel().getColumn(1).setMaxWidth(90);
	    table.getColumnModel().getColumn(1).setCellRenderer(render);
	    //Column-3
	    table.getColumnModel().getColumn(2).setPreferredWidth(120);
	    table.getColumnModel().getColumn(2).setMaxWidth(130);
	    table.getColumnModel().getColumn(2).setCellRenderer(render);
	    //Column-4
	    table.getColumnModel().getColumn(3).setPreferredWidth(120);
	    table.getColumnModel().getColumn(3).setMaxWidth(120);
	    table.getColumnModel().getColumn(3).setCellRenderer(render);
	    //Column-5
	    table.getColumnModel().getColumn(4).setPreferredWidth(100);
	    table.getColumnModel().getColumn(4).setMaxWidth(100);
	    table.getColumnModel().getColumn(4).setCellRenderer(render);
		table.setRowHeight(40);
  	}

	public void add(String type, String os, String user, String ip, String status){
		model.addRow(new String [] {
	    	type,os,user,ip,status
		});
	}

	public void updateStatus(int row, int column, String value){
		table.setValueAt(value, row, column);
	} 

}