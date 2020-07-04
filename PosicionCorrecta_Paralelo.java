import java.util.ArrayList;
public class PosicionCorrecta_Paralelo extends Thread{
	int client, registros;
	int[] saldo, tasks_computer;
	ArrayList<RemoteInterface> ri;
	public PosicionCorrecta_Paralelo(int client, int[] saldo, int registros, int[] tasks_computer, ArrayList<RemoteInterface> ri){
		this.client			= client;
		this.registros		= registros;
		this.saldo			= saldo;
		this.tasks_computer = tasks_computer;
		this.ri 			= ri;
	}

	public void run(){
		try{
			ri.get(client).posicionCorrectaParalelo(saldo, registros, tasks_computer, client);
		}catch(Exception event){}
	}
}