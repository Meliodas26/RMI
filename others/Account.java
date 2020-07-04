//Si desea correr el programa sin tener una base de datos, descomente la linea numero: 44
package others;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Random;
import java.io.Serializable;
import java.io.IOException;
public class Account{
	public int[] id,idAux;
	public String [] name,nameAux;
	public int [] saldo,saldoAux;
	//public int registros = 0, withoutDatabase=10000;	//	Rapido
	public int registros = 0, withoutDatabase=1000000;	// 	Lento
	//public int registros = 0, withoutDatabase=20;	// 	Variado
	boolean dataBase = false;

	Connection connection;
	Statement consulta;
	PreparedStatement insertar;
	ResultSet resultado; 
	
	String url = "jdbc:mysql://localhost:3306/Cuentas";
	String user = "root";
	String password = "GokuSsjBlue131";

	public Account(){
		try{
			connection = DriverManager.getConnection(url,user,password);
			
			consulta = connection.createStatement();
			resultado = consulta.executeQuery("SELECT count(*) as total FROM cuenta");
			
			if(resultado.next())
				registros=Integer.parseInt(resultado.getString("total"));
			connection.close();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		registros = 0;
		/*Si hay registros en la base de datos, hace uso de este
		en caso contrario, el programa los crea. El nuemro de registros que creara
		es igual a la variable: withoutDataBase*/
		if(registros != 0){
			try{
				Class.forName("com.mysql.jdbc.Driver");
				connection = DriverManager.getConnection(url,user,password);
				consulta = connection.createStatement();
				resultado = consulta.executeQuery("SELECT * FROM cuenta");
				id = new int[registros];
				name = new String[registros];
				saldo = new int[registros];
				for (int i=0; i<registros; i++) {
					resultado.next();
					id[i] = resultado.getInt("id");
					name[i] = resultado.getString("nombre");
					saldo[i] = resultado.getInt("saldo");
				}
				connection.close();
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}else{
			System.out.println("El programa genero los registros ya que no lograron obtenerse de la base de datos");
			Random r = new Random();
			id = new int[withoutDatabase];
			name = new String[withoutDatabase];
			saldo = new int[withoutDatabase];
			for (int i=0; i < withoutDatabase; i++) {
				id[i] = i+1;
				name[i] = createName();
				saldo[i] = r.nextInt(10000)+100;
			}
			registros = withoutDatabase;
		}
		System.out.println("---------------------------------------------------------------------------------");
	}

	public String createName(){
		String [] name = {"Aldo","Alex","Andrea","Gerardo","Cesar","Carlos","Edgar","Erick","Gustavo","Jorge","Roberto","Eunice","Pilar","Araceli","Gricelda","Salvador","Ernestina","Karla","Carmelita","Ashley","Dafne","Argel","Cristian","Viridiana","Analia","Angelica", "Martin", "Marcos", "Luis", "Juan"};
		String [] lastName = {"Gutierrez","Rincon","Rodriguez","Flores","Silva","Estrada","Vargas","Lopez","Garibay","Fernandez","Ramirez","Delgado","Pitt","Lozano","Alvarez","Vazquez","Guevara","Holland","Anaya","Arechiga"};
		String fullName = "";
		Random r = new Random();
		int pos = r.nextInt(29)+1;
		fullName = name[pos]+" ";
		pos = r.nextInt(19)+1;
		fullName += lastName[pos];
		return fullName;
	}

	
	public void cloneToArray(){
		for (int i=0; i<registros; i++) {
			id[i] = idAux[i];
			name[i] = nameAux[i];
			saldo[i] = saldoAux[i];
		}
	}
	public void cloneToAux(){
		for (int i=0; i<registros; i++) {
			idAux[i] = id[i];
			nameAux[i] = name[i];
			saldoAux[i] = saldo[i];
		}
	}

	public void addAccount(int newRegistros){
		idAux = new int[newRegistros];
		nameAux = new String[newRegistros];
		saldoAux = new int[newRegistros];
		cloneToAux();
		System.out.println(registros);
		System.out.println(idAux.length);
		Random r = new Random();
		for (int i=0; i<7; i++) {
			idAux[registros+i] = registros+i;
			nameAux[registros+i] = createName();
			saldoAux[registros+i] = r.nextInt(10000)+100;;
		}
		registros+=7;
		id = new int[registros];
		name = new String[registros];
		saldo = new int[registros];
		cloneToArray();
		if(dataBase){
			System.out.println("Consulta");
		}	

	}
	public void rmAccount(int newRegistros){
		idAux = new int[newRegistros];
		nameAux = new String[newRegistros];
		saldoAux = new int[newRegistros];
		registros-=7;
		cloneToAux();
		cloneToArray();
		if(dataBase){
			System.out.println("Consulta");
		}
	}

}
