import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
public interface RemoteInterface extends Remote{
	public void add(RemoteInterface client) throws RemoteException;
	public void update(String user, String computers) throws RemoteException;
	
	public void posicionCorrectaParalelo(int [] tmpSaldo, int registros, int[] tasks_computer, int iComputer) throws RemoteException;
	
	public ArrayList<RemoteInterface> getClient() throws RemoteException;
	public String getOS() throws RemoteException;
	public String getUser() throws RemoteException;
	public String getIP() throws RemoteException;
	public String getComputers() throws RemoteException;
	public int getComputersInteger() throws RemoteException;
	public int[] getPosicion() throws RemoteException;
}