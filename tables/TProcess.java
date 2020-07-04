package tables;

import others.*;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Font;
import java.lang.Math;

public class TProcess{
	
	Colors color = new Colors();
	long begin, end;;
	double time, timeAux, aceleracion, auxAceleracion, diferencia, maxDiferencia, eficiencia;
	int tasksThread, beginThread, endThread;

	int process = 12;
	public JTable table;
  	DefaultTableModel model = new DefaultTableModel();
  	DefaultTableCellRenderer render = new DefaultTableCellRenderer();

	public TProcess(){
		render.setHorizontalAlignment(SwingConstants.CENTER);
	  	render.setBackground(color.darkGreen);
	  	render.setForeground(color.white);

	  	model.addColumn("Procesos");
	  	model.addColumn("Tiempo");
	  	model.addColumn("Aceleración");
	  	model.addColumn("Eficiencia");

	  	table = new JTable(model);
	  	table.setRowHeight(20);
	  	//Column-1
	  	table.getColumnModel().getColumn(0).setPreferredWidth(80);
	    table.getColumnModel().getColumn(0).setMaxWidth(80);
	    //Column-2
	    table.getColumnModel().getColumn(1).setPreferredWidth(70);
	    table.getColumnModel().getColumn(1).setMaxWidth(70);
	    //Column-3
	    table.getColumnModel().getColumn(2).setPreferredWidth(85);
	    table.getColumnModel().getColumn(2).setMaxWidth(85);
	    //Column-4
	    table.getColumnModel().getColumn(3).setPreferredWidth(80);
	    table.getColumnModel().getColumn(3).setMaxWidth(80);
	    for (int i=0; i<4; i++)
	    	table.getColumnModel().getColumn(i).setCellRenderer(render);
	    inicializar();
	}

	public void inicializar(){
		for (int i=0; i<2; i++)
			model.addRow(new String [] {
        		(i+1)+"","?","?","?"
      		});
		model.addRow(new String [] {
    		(4)+"","?","?","?"
  		});
  		model.addRow(new String [] {
    		(12)+"","?","?","?"
  		});
	}

	public int update(double time, Account account){
		int suggestedThreads = 2;
		table.setValueAt(String.format("%.4f", time)+"s", 	0, 1);
		table.setValueAt("1.0", 							0, 2);
		table.setValueAt("100%", 							0, 3);

		auxAceleracion = 1.0;
		maxDiferencia = 0.0;

		int row = 1;
		int i=2;
		calculate(row, i, time, account);
		row++;
		i+=2;

		maxDiferencia = diferencia;
		auxAceleracion = aceleracion;

		boolean searching = true;
		while(searching) {
			if(i >= process){
				process+=4;
				model.addRow(new String [] {
    				(process)+"","?","?","?"
  				});
			}
			calculate(row, i, time, account);
			diferencia = aceleracion - auxAceleracion;
			if(maxDiferencia >= diferencia)
				searching = false;
			else
				suggestedThreads = process;
			maxDiferencia = diferencia;
			auxAceleracion = aceleracion;
			i+=4;
			row++;
		}
		return suggestedThreads;
	}

	public void calculate(int row, int i, double time, Account account){
		//timeAux = (time/i); //Para estimar lo que tardara en ordenarlo con nProcesos
		timeConcurrente(i, account); //Para medir lo que tarda en ordenarlo con nProcesos
		aceleracion = time/timeAux;
		diferencia = aceleracion - auxAceleracion;
		eficiencia = aceleracion / i;
		if(eficiencia > 100)
			eficiencia*=10;
		else
			eficiencia*=100;
		table.setValueAt(String.format("%.4f", timeAux)+"s", 		row, 1);
		table.setValueAt(String.format("%.2f", aceleracion), 		row, 2);
		table.setValueAt(String.format("%.0f", eficiencia)+"%", 	row, 3);
	}

	public void clearTable(){
	    try{
	      	int threads = table.getRowCount();
	      	for (int i=0; i<=threads; i++)
	      		for (int j=1; j<4; j++)
					table.setValueAt("",i,j);
	    }catch(Exception e){}
  	}

  	/*Este metodo sirve para que los tiempos de la tabla no sean estimaciones ya que manda hacer 
	el ordenamiento de acuerdo al numero de procesos de la fila que se esta llenando.
	*/
  	public void timeConcurrente(int threads, Account account){
		begin = System.currentTimeMillis();
		Temporal temporal = new Temporal(account.registros);
		PosicionCorrecta_Concurrente[] concurrente = new PosicionCorrecta_Concurrente[threads];
		beginThread = 0;
		endThread = 0;
		tasksThread = account.registros/threads;
		if((account.registros%threads) == 0)
			for (int i=0; i<threads; i++){
				endThread += tasksThread;
				concurrente[i] = new PosicionCorrecta_Concurrente(account, temporal, beginThread,endThread,i);
				concurrente[i].start();
				beginThread = endThread;
			}
		else{
			int pTasksThread = account.registros%threads;
			for (int i=0; i<threads; i++) {
				if(pTasksThread>0){
					endThread += (tasksThread+1);	
					pTasksThread--;
				}
				else
					endThread += tasksThread;
				concurrente[i] = new PosicionCorrecta_Concurrente(account, temporal, beginThread,endThread, i);
				concurrente[i].start();	
				beginThread = endThread;
			}
		}
		try{
			for (int i=0; i<threads; i++)
				concurrente[i].join();
		}catch(Exception ex){}
		end = System.currentTimeMillis();
		timeAux = (end - begin);
		timeAux /= 1000;
	}

}