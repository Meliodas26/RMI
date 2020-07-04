package others;
public class PosicionCorrecta_Concurrente extends Thread{
	Account account;
	Temporal temporal;
	int posicion, begin, end, id;

	public PosicionCorrecta_Concurrente(Account account, Temporal temporal, int begin, int end, int i){
		this.account = account;
		this.temporal = temporal;
		this.begin = begin;
		this.end = end;
		id = i;
	}

	public void run(){

		for (int i=begin; i<end; i++) {
			posicion = 0;
			for (int j=0; j<account.registros; j++) {
				if(i != j){
					if(account.saldo[i] > account.saldo[j])
						posicion++;
				}
			}
			while(!temporal.empty[posicion])
				posicion++;
			temporal.id[posicion] = account.id[i];
			temporal.name[posicion] = account.name[i];
			temporal.saldo[posicion] = account.saldo[i];
			temporal.empty[posicion] = false;
		}
	}
	
}