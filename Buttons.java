import others.*;
import tables.*;
import frames.FrameStatus;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
public class Buttons extends JPanel{
	
	Colors color = new Colors();
	PosicionCorrecta_Secuencial secuencial = new PosicionCorrecta_Secuencial();
	PosicionCorrecta_Concurrente[] concurrente;
	PosicionCorrecta_Paralelo[] paralelo;
	
	JButton buttonConsultar, buttonSecuencial, buttonConcurrente, buttonParalelo;
	Temporal temporal;
	long begin, end;
	double time;
	int tasksThread, beginThread, endThread;
	
	public Buttons(Account account, TAccount tAccount, TTime tTime, TProcess tProcess, JLabel lThread, FrameStatus fStatus, TThread tThread, TComputer tComputer, ArrayList<RemoteInterface> client){
		setLayout(null);
		setBounds(0, 95, 750, 85);
		setBackground(color.white);

		buttonConsultar = new JButton("Consultar cuentas");
		buttonConsultar.setBounds(207, 10, 336, 30);
		buttonConsultar.setBackground(color.celeste);
		buttonConsultar.setForeground(color.white);
		buttonConsultar.setFont(new Font("DejaVu Sans", 1, 20));
		buttonConsultar.setBorder(BorderFactory.createLineBorder(color.black, 2));

		buttonSecuencial = new JButton("Secuencial");
		buttonSecuencial.setBounds(140, 45, 142, 30);
		buttonSecuencial.setBackground(color.red);
		buttonSecuencial.setForeground(color.black);
		buttonSecuencial.setFont(new Font("DejaVu Sans", 1, 15));
		buttonSecuencial.setBorder(BorderFactory.createLineBorder(color.black, 2));

		buttonConcurrente = new JButton("Concurrente");
		buttonConcurrente.setBounds(302, 45, 158, 30);
		buttonConcurrente.setBackground(color.yellow);
		buttonConcurrente.setForeground(color.black);
		buttonConcurrente.setFont(new Font("DejaVu Sans", 1, 15));
		buttonConcurrente.setBorder(BorderFactory.createLineBorder(color.black, 2));

		buttonParalelo = new JButton("Paralelo");
		buttonParalelo.setBounds(480, 45, 112, 30);
		buttonParalelo.setBackground(color.green);
		buttonParalelo.setForeground(color.black);
		buttonParalelo.setFont(new Font("DejaVu Sans", 1, 15));
		buttonParalelo.setBorder(BorderFactory.createLineBorder(color.black, 2));

		add(buttonConsultar);
		add(buttonSecuencial);
		add(buttonConcurrente);
		add(buttonParalelo);

		buttonConsultar.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				System.out.println("Tarea: Consaltando cuentas...");
  				tAccount.putData(account);
  				System.out.println("Tarea finalizada.");
				System.out.println("---------------------------------");
  			}	
		});

		buttonSecuencial.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				Runnable segundoPlano = (new Runnable(){
  					public void run(){
  						try{
  							tProcess.clearTable();
			  				System.out.println("Tarea: Ordenando secuencialmente las cuentas...");
			  				begin = System.currentTimeMillis();
							temporal = new Temporal(account.registros);
							secuencial.ordenar(account, temporal);
							end = System.currentTimeMillis();
							time = (end - begin);
							time /= 1000;
							tAccount.putData(account.registros, temporal);
							tTime.updateTime(time, 0);
							int suggestedThreads = tProcess.update(time, account);
							lThread.setText("Hilos por equipo sugeridos para el ordenamiento: "+suggestedThreads);
							System.out.println("Tarea finalizada.");
							System.out.println("---------------------------------");
						}catch(Exception e){System.out.println(e.getMessage());}
					}
				});
				Thread hilo = new Thread(segundoPlano);
    			hilo.start();
  			}	
		});
		
		buttonConcurrente.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				Runnable segundoPlano = (new Runnable(){
  					public void run(){
  						try{
  							System.out.println("Tarea: Ordenando concurrentemente las cuentas...");
  							System.out.println("Con "+fStatus.getThreads()+" threads.");
  							posicionCorrectaConcurrente(fStatus.getThreads(), account, tThread);
  							tAccount.putData(account.registros, temporal);
  							tTime.updateTime(time, 1);
  							System.out.println("Tarea finalizada.");
							System.out.println("---------------------------------");
  						}catch(Exception e){System.out.println(e.getMessage());}
					}
				});
				Thread hilo = new Thread(segundoPlano);
    			hilo.start();
  			}	
		});

		buttonParalelo.addActionListener(new ActionListener() {
  			public void actionPerformed(ActionEvent e) {
  				Runnable segundoPlano = (new Runnable(){
  					public void run(){
  						try{
			  				begin = System.currentTimeMillis();
			  				System.out.println("Tarea: Ordenando paralelamente-concurrentemente las cuentas...");
			  				int registros = account.registros;
			  				int[] saldo = account.saldo;
			  				try{
			  					int computers = client.get(0).getComputersInteger();
			  					System.out.println("Con "+computers+" equipos.");
			  					int[] tasks_computer = new int[computers];
			  					
			  					for (int i=0; i<computers; i++) 
			  						tasks_computer[i]=registros/computers;

			  					if(registros%computers != 0){
			  						int pTasks = registros%computers;
			  						for (int i=pTasks; i>0; i--) {
			  							tasks_computer[i-1]++;
			  						}
			  					}

			  					paralelo = new PosicionCorrecta_Paralelo[3];
			  					for (int i=0; i<computers; i++){
			  						paralelo[i] = new PosicionCorrecta_Paralelo(i, saldo, registros, tasks_computer, client);
			  						paralelo[i].start();
			  					}

								for (int i=0; i<computers; i++){
									paralelo[i].join();
								}

								int [] posicion = new int[registros];
								int tmpPosicion = 0;
								for (int i=0; i<computers; i++) {
									int [] tmpArray = client.get(i).getPosicion();
									for (int j=0; j<tmpArray.length; j++) {
										posicion[tmpPosicion] = tmpArray[j];
										tmpPosicion++;
									}
								}
								
								tAccount.clear();
			  					for (int i=0; i<registros; i++)
			  						tAccount.putData(posicion[i], account, i);
			  				}catch(Exception event){}

			  				end = System.currentTimeMillis();
							time = (end - begin);
							time /= 1000;
			  				tTime.updateTime(time, 2);
			  				System.out.println("Tarea finalizada.");
							System.out.println("----------------------------------------------------------------");
						}catch(Exception e){System.out.println(e.getMessage());}
					}
				});
				Thread hilo = new Thread(segundoPlano);
				hilo.start();
  			}	
		});
	}

	public void posicionCorrectaConcurrente(int threads, Account account, TThread tThread){
		begin = System.currentTimeMillis();
		temporal = new Temporal(account.registros);
		concurrente = new PosicionCorrecta_Concurrente[threads];
		beginThread = 0;
		endThread = 0;
		tasksThread = account.registros/threads;

		tThread.clear();

		if(threads > tThread.getRow())
			for (int i=tThread.getRow(); i<threads; i++)
				tThread.add("Thread-"+(i+1));
		
		if((account.registros%threads) == 0)
			for (int i=0; i<threads; i++){
				endThread += tasksThread;
				tThread.updateStatus("Creandose",i);
				concurrente[i] = new PosicionCorrecta_Concurrente(account, temporal, beginThread,endThread,i);
				concurrente[i].start();
				tThread.updateStatus("Ejecutándose",i);
				beginThread = endThread;
			}
		else{
			int pTasksThread = account.registros%threads;
			for (int i=0; i<threads; i++) {
				tThread.updateStatus("Creandose",i);
				if(pTasksThread>0){
					endThread += (tasksThread+1);	
					pTasksThread--;
				}
				else
					endThread += tasksThread;
				concurrente[i] = new PosicionCorrecta_Concurrente(account, temporal, beginThread,endThread, i);
				concurrente[i].start();	
				tThread.updateStatus("Ejecutándose",i);
				beginThread = endThread;
			}
		}
		try{
			for (int i=0; i<threads; i++){
				concurrente[i].join();
				tThread.updateStatus("Finalizado",i);
			}
		}catch(Exception ex){}
		end = System.currentTimeMillis();
		time = (end - begin);
		time /= 1000;
	}

}