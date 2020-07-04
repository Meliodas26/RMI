Notas:

- El ordenamiento PosicionCorrecta se vuelve ineficiente de manera secuencial y concurrente a tener que ordena mas de 100,000 cuentas y de manera paralela si se tiene menos de 4 equipos.
ya que hace falta una mejora que reduzca el numero de comparaciones. 
Esta consiste en que cuando un numero a ordenarse este en el inicio o final, este sea
eliminado del array de de comparacion, para que así ya no lo compare con el como tambien cambiar 
el intervalo de comparación, ya que ahora no se debera iterar el numero de cuentas que hay, 
sino, el numero de cuentas menos aquellos numeros que ya estan ordenados y se encuentrna en un extremo, 
así reduciendo el intervalo cada vez que se encuentre un numero en un extremo.

- El programa por default intentara hacer la conexión a la base de datos y si no logra
hacerla creara los valores por el mismo almacenandolos en un arraylist.

- Puedes modificar hacer que por default no intente hacer la conexión en la clase Account, alojada en 
el package others, en la primera linea hay un comentario que dice que debes hacer.

- Si haras uso de la base de datos necesitas, tener en mysql la base de datos Cuentas que esta en la carpeta
others y el driver mysql-connector-java-8.0.20.jar, como también: 
	
	* Configurar la variable de entorno:
		$ export CLASSPATH=others/mysql-connector-java-8.0.20.jar:.
	
	Y quizás la hora global en mysql
		mysql SET GLOBAL time_zone = '+3:00'; 

Recursos:

- Prototipo del programa:
https://www.figma.com/file/MTLUyUgxpzbIDaf8hmJcks/Proyecto-3?node-id=0%3A1

- Repositorio del programa:
https://github.com/Meliodas26/RMI.git