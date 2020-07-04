package tables;
import others.Colors;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Font;
import java.lang.Math;

public class TTime{
	Colors color = new Colors();

	public JTable table;
  	DefaultTableModel model = new DefaultTableModel();
  	DefaultTableCellRenderer render = new DefaultTableCellRenderer();
  	public TTime(){
  		render.setHorizontalAlignment(SwingConstants.CENTER);
	  	render.setBackground(color.orange);
	  	render.setForeground(color.white);
	
	  	model.addColumn("Tipo");
	  	model.addColumn("Tiempo");

	  	table = new JTable(model);
	  	table.setRowHeight(15);
	  	//Column-1
	  	table.getColumnModel().getColumn(0).setPreferredWidth(100);
	    table.getColumnModel().getColumn(0).setMaxWidth(100);
	    //Column-2
	    table.getColumnModel().getColumn(1).setPreferredWidth(70);
	    table.getColumnModel().getColumn(1).setMaxWidth(70);
	    for (int i=0; i<2; i++)
	    	table.getColumnModel().getColumn(i).setCellRenderer(render);
	    inicializar();
	}

	public void inicializar(){
      model.addRow(new String [] {
        "Secuencial","?"
      });
      model.addRow(new String [] {
        "Concurrente","?"
      });
      model.addRow(new String [] {
        "Paralelo","?"
      });
	}

	public void updateTime(double time, int row){
    	table.setValueAt(String.format("%.2f", time)+"s", row, 1);
	}
}