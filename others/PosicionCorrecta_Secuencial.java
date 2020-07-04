package others;
public class PosicionCorrecta_Secuencial{

	public void ordenar(Account account, Temporal temporal){
		int posicion;
		for (int i=0; i<account.registros; i++) {
			posicion = 0;
			for (int j=0; j<account.registros; j++) {
				if(i != j){
					if(account.saldo[i] > account.saldo[j])
						posicion++;
				}
			}
			while(!temporal.empty[posicion]){
				posicion++;
			}
			temporal.id[posicion] = account.id[i];
			temporal.name[posicion] = account.name[i];
			temporal.saldo[posicion] = account.saldo[i];
			temporal.empty[posicion] = false;
		}
	}

}