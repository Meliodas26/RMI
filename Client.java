import frames.FrameStatus;
import tables.TComputer;
import tables.TThread;

import javax.swing.JLabel;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.net.InetAddress;
import java.rmi.Naming;
import java.util.ArrayList;
public class Client extends UnicastRemoteObject implements RemoteInterface{
	RemoteInterface server; 
	ArrayList<RemoteInterface> interfaces = new ArrayList<RemoteInterface>();
	Client client;
	FrameStatus status;
	TComputer tComputer;
	TThread tThread;
	
	JLabel lComputers, lThreads;
	String os, user, ip, ipServer, port;
	int threads;
	int [] posicion;
	public Client(String ipServer, String port) throws RemoteException{
		client = this;
		this.ipServer = ipServer;
		this.port = port;

		os = System.getProperty("os.name");
		user = System.getProperty("user.name");
		try{
			ip = InetAddress.getLocalHost().getHostAddress();
		}catch(Exception e){e.printStackTrace();}

		Runtime hardware = Runtime.getRuntime();
		threads = hardware.availableProcessors()/2;

		tComputer = new TComputer();
	 	tThread  = new TThread();
		
		lComputers = new JLabel("Equipos: ");
		lThreads = new JLabel("Threads: "+threads);

		connection();
		status = new FrameStatus(tComputer, lComputers, tThread, lThreads, threads, user);
	}

	public String getOS() throws RemoteException{return os;}
	public String getIP() throws RemoteException{return ip;}
	public String getUser() throws RemoteException{return user;}
	public void add(RemoteInterface server) throws RemoteException{
		lComputers.setText("Equipos: "+server.getComputers());
		ArrayList<RemoteInterface> client = server.getClient();
		for (int i=0; i<client.size(); i++)
			tComputer.add(client.get(i).getUser());
		for (int i=1; i<client.size()-1; i++)
			client.get(i).update(user, server.getComputers());
	}
	public void update(String user, String computers) throws RemoteException{
		lComputers.setText("Equipos: "+computers);
		tComputer.add(user);
	}
	public ArrayList<RemoteInterface> getClient() throws RemoteException{ ArrayList<RemoteInterface> client = new ArrayList<RemoteInterface>(); return client;}

	public int[] getPosicion() throws RemoteException{return posicion;}
	public void posicionCorrectaParalelo(int[] tmpSaldo, int registros, int[] tasks_computer, int iComputer) throws RemoteException{
		tThread.clear();
		System.out.println("Tarea: Ordenando paralelamente-concurrentemente las cuentas...");
		
		System.out.println("Hay "+registros+" registros.");
		System.out.println("Ordenare "+tasks_computer[iComputer]+" numeros.");
		
		threads = status.nThread;
		
		int begin, end;
		int tasks = tasks_computer[iComputer]/threads;
		int [] tasks_thread = new int[threads];
		int pTasks = tasks_computer[iComputer]%threads;

		for (int i=0; i<threads; i++) {
			if(pTasks>0){
				tasks_thread[i] = tasks+1;
				pTasks--;
			}
			else 
				tasks_thread[i] = tasks;	
		}

		begin = tasks_computer[0];
		end = tasks_computer[0];
		System.out.println("Tasks computer: "+tasks_computer[iComputer]);
		for (int i=1; i<iComputer; i++) {
			begin 	+= tasks_computer[i];
			end = begin;
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
				begin += tasks_thread[i];
			}

			for (int i=0; i<threads; i++){
				paralelo[i].join();
				tThread.updateStatus("Finalizado",i);
			}
		}catch(Exception e){}

		posicion = new int[tasks_computer[iComputer]];
		int tmpPosicion = 0;
		for (int i=0; i<threads; i++) {
			int [] arrayTmp = paralelo[i].posicion;
			for (int j=0; j<arrayTmp.length; j++) {
				posicion[tmpPosicion] = arrayTmp[j];
				tmpPosicion++;
			}
		}

		System.out.println("Tarea finalizada.");
		System.out.println("----------------------------------------------------------------");
	}

	public String getComputers() throws RemoteException{ return ""; }
	public int getComputersInteger() throws RemoteException{ return 0;}

	public static void main(String[] args) {
		try{
			new Client(args[0], args[1]);
		}catch(Exception e){ e.printStackTrace(); }
	}

	public void connection(){
		try{
			ip = InetAddress.getLocalHost().getHostAddress();
			server = (RemoteInterface)Naming.lookup("//"+ipServer+":"+port+"/Proyecto-3");
			interfaces.add(server);
			server.add(client);
		}catch(Exception e){ e.printStackTrace(); }
	}

	public TComputer getTComputer(){
		return tComputer;
	}

}