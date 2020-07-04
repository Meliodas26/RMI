import frames.FrameInterface;
import frames.FrameStatus;
import panels.Titles;
import tables.*;
import others.Account;

import javax.swing.JLabel;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.rmi.Naming;
import java.net.InetAddress;
import java.util.ArrayList;
public class Server extends UnicastRemoteObject implements RemoteInterface{
	static FrameInterface frameInterface;
	
	FrameServer frameServer;
	FrameStatus frameStatus;
	
	Buttons buttons;

	TInterface tInterface;
	TAccount tAccount;
	TTime tTime;
	TProcess tProcess;
	TComputer tComputer;
	TThread tThread;

	Account account;
	//---------------------------------
	public int threads, computers = 1;
	int [] posicion;
	JLabel lSuggestedThread, lComputers, lThreads;
	RemoteInterface server;
	//El server ocupara la posicion 0, los demas seran clientes.
	ArrayList<RemoteInterface> client = new ArrayList<RemoteInterface>();
	String os, user, ip, port, ipClient;

	public Server() throws RemoteException{

	}

	public Server(String port) throws RemoteException{
		client.add(this);
		tInterface 	= new TInterface();
		frameInterface = new FrameInterface(tInterface);
		server = this;
		this.port = port;
		os = System.getProperty("os.name");
		user = System.getProperty("user.name");
		try{
			ip = InetAddress.getLocalHost().getHostAddress();
		}catch(Exception e){e.printStackTrace();}
		tInterface.add("Servidor", os, user, ip, "Dormido");
		try{
			Naming.rebind( "//"+ip+":"+port+"/Proyecto-3",server);
		}catch(Exception e){ e.printStackTrace(); }
		tInterface.updateStatus(0, 4, "Disponible");
		System.out.println("Tarea finalizada.");
		System.out.println("---------------------------------");

		Runtime hardware = Runtime.getRuntime();
		threads = hardware.availableProcessors()/2;

		account = new Account();

		tAccount 	= new TAccount(account.registros);
		tTime 		= new TTime();
		tProcess 	= new TProcess();
		tComputer 	= new TComputer();
		tThread		= new TThread();

		lSuggestedThread = new JLabel("Hilos por equipo sugeridos para el ordenamiento: ?", JLabel.CENTER);
		lComputers = new JLabel("Equipos: 1");
		lThreads = new JLabel("Threads: "+threads);

		frameStatus = new FrameStatus(tComputer, lComputers, tThread, lThreads, threads, user);

		buttons = new Buttons(account, tAccount, tTime, tProcess, lSuggestedThread, frameStatus, tThread, tComputer, client);
		
		frameServer = new FrameServer(tAccount, tTime, tProcess, buttons, lSuggestedThread);
		tComputer.add(user);
	}
	
	public ArrayList<RemoteInterface> getClient() throws RemoteException{return client;}
	public String getUser() throws RemoteException{return user;}
	public void add(RemoteInterface ri) throws RemoteException{
		computers++;
		client.add(ri);
		tComputer.add(ri.getUser());
		ri.add(server);
		tInterface.add("Client", ri.getOS(), ri.getUser(), ri.getIP(), "Disponible");
		lComputers.setText("Equipos: "+computers);
	}
	public void update(String user, String computers) throws RemoteException{}
	public String getOS() throws RemoteException{return "";}
	public String getIP() throws RemoteException{return "";}

	public String getComputers() throws RemoteException{return computers+"";}
	public int getComputersInteger() throws RemoteException{return computers;}
	public int[] getPosicion() throws RemoteException{return posicion;}
	public void posicionCorrectaParalelo(int [] tmpSaldo, int registros, int[] tasks_computer, int iComputer){
		tThread.clear();
		System.out.println("Hay "+registros+" registros.");
		System.out.println("Ordenare "+tasks_computer[iComputer]+" numeros.");
		
		threads = frameStatus.nThread;
		int begin, end;
		begin = 0;
		end = 0;
		int tasks = tasks_computer[iComputer]/threads;
		int [] tasks_thread = new int[threads];
		int pTasks = tasks_computer[iComputer]%threads;

		for (int i=0; i<threads; i++) {
			if(pTasks>0){
				tasks_thread[i] = tasks+1;//!
				pTasks--;
			}
			else 
				tasks_thread[i] = tasks;	
		}

		ParaleloThread[] paralelo = new ParaleloThread[threads];
		try{
			for (int i=0; i<threads; i++){
				end += tasks_thread[i];
				System.out.println("Begin: "+begin);
				System.out.println("End: "+end);
				paralelo[i] = new ParaleloThread(registros, begin, end, tmpSaldo);
				tThread.updateStatus("Ejecutándose",i);
				paralelo[i].start();
				begin += tasks_thread[i]; //!
			}

			for (int i=0; i<threads; i++){
				paralelo[i].join();
				tThread.updateStatus("Finalizado",i);
			}

			posicion = new int[tasks_computer[iComputer]];
			int tmpPosicion = 0;
			for (int i=0; i<threads; i++) {
				int[] tmpArray = paralelo[i].posicion;
				for (int j=0; j<tmpArray.length; j++) {
					posicion[tmpPosicion] = tmpArray[j];
					tmpPosicion++;
				}
			}

		}catch(Exception e){System.out.println(e.getMessage());}

	}

	public static void main(String[] args) {
		System.out.println("Tarea: Levantando el servidor...");
		try{
			new Server(args[0]);
		}catch(Exception e){e.printStackTrace();}
	}
}	