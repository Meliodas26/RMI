package tables;

import others.Colors;
import others.Account;
import others.Temporal;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Font;

public class TAccount{
  Colors color = new Colors();

	public JTable table;
	DefaultTableModel model = new DefaultTableModel();
  DefaultTableCellRenderer render = new DefaultTableCellRenderer();

  int row;
  String value;
	public TAccount(int registros){
		render.setHorizontalAlignment(SwingConstants.CENTER);
  	render.setBackground(color.celeste);
  	render.setForeground(color.white);
  	model.addColumn("id");
  	model.addColumn("Nombre");
  	model.addColumn("Saldo");
  	
  	table = new JTable(model);
  	table.setRowHeight(25);
  	//Column-1
  	table.getColumnModel().getColumn(0).setPreferredWidth(100);
    table.getColumnModel().getColumn(0).setMaxWidth(100);
    //Column-2
    table.getColumnModel().getColumn(1).setPreferredWidth(150);
    table.getColumnModel().getColumn(1).setMaxWidth(150);
    //Column-3
    table.getColumnModel().getColumn(2).setPreferredWidth(150);
    table.getColumnModel().getColumn(2).setMaxWidth(150);
    for (int i=0; i<3; i++)
    	table.getColumnModel().getColumn(i).setCellRenderer(render);

    inicializar(registros);
	}

	public void inicializar(int registros){
    for(int i=0; i<registros; i++){
      model.addRow(new String [] {
        "","",""
      });
      row++;
    }
	}

	public void putData(Account account){
  	for(int i=0; i<account.registros; i++){
    		table.setValueAt(account.id[i], i, 0);
    		table.setValueAt(account.name[i], i, 1);
    		table.setValueAt(account.saldo[i], i, 2);
  	}
	}

  public void putData(int accounts, Temporal temporal){
    for(int i=0; i<accounts; i++){
      table.setValueAt(temporal.id[i], i, 0);
      table.setValueAt(temporal.name[i], i, 1);
      table.setValueAt(temporal.saldo[i], i, 2);
    }
  }

  public void putData(int row, Account account, int iAccount){
      value = table.getValueAt(row, 2)+"";
      while(!value.equals("")){
        row++;
        value = table.getValueAt(row, 2)+"";
      }

      table.setValueAt(account.id[iAccount], row, 0);
      table.setValueAt(account.name[iAccount], row, 1);
      table.setValueAt(account.saldo[iAccount], row, 2);
  }

  public void clear(){
    try{
        int accounts = table.getRowCount();
        for (int i=0; i<=accounts; i++){
          table.setValueAt("",i,0);
          table.setValueAt("",i,1);
          table.setValueAt("",i,2);
        }
    }catch(Exception e){}
  }

}