public class ParaleloThread extends Thread{
	int registros, begin, end;
	int[] tmpSaldo, posicion;
	public ParaleloThread(int registros, int begin, int end, int[] tmpSaldo){
		this.registros 	= registros; 
		this.begin		= begin; 
		this.end 		= end;
		this.tmpSaldo 	= tmpSaldo; 
		posicion = new int[end-begin];
		for(int i=0; i<end-begin; i++)
			posicion[i] = -1;
	}
	public void run(){
		int tmpPosicion = 0;
		int aux = 0;
		for (int i=begin; i<end; i++) {
			tmpPosicion = 0;
			for (int j=0; j<registros; j++) {
				if(i != j){
					if(tmpSaldo[i] > tmpSaldo[j])
						tmpPosicion++;
				}
			}
			posicion[aux] = tmpPosicion;
			aux++;
		}
	}
}