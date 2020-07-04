package others;
import java.util.ArrayList;

public class Temporal{
	public int[] id;
	public String [] name;
	public int [] saldo;
	public boolean [] empty;
	public boolean zonaCritica = false, finish = false;

	
	public Temporal(int registros){
		id = new int[registros];
		name = new String[registros];
		saldo = new int[registros];
		empty = new boolean[registros];
		for (int i=0; i<registros; i++) {
			id[i] = 0;
			name[i] = "";
			saldo[i] = 0;
			empty[i] = true;
		}
	}

	public synchronized int setEmpty(int posicion, String hilo){
		if(zonaCritica){
			try{
				/*El hilo pasara a estado 'en espera' ya que otro hilo esta en su zona critica.
				Este se va a reanudar cuando salga de la zona critica*/
				wait();
			}catch(InterruptedException e){}
		}
		zonaCritica = true;
		while(empty[posicion]){
			posicion++;
		}
		zonaCritica = false;
		notifyAll();
		return posicion;
	}

}