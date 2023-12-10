package ejercicioFinal;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Iterator;

import com.mysql.cj.xdevapi.DatabaseObject.DbObjectType;

public class Testing {
	public static void main(String[] args) {
	System.out.println(obtenerNombreColumnas());
	}
	
	public static String obtenerNombreColumnas() {

	String [] pirola = new String [5];
	
	for (int i = 0; i < pirola.length; i++) {
		pirola[i]= "nombre" + (i + 1);
		
	}

			int maxColumn= pirola.length;
			
			StringBuilder linea = new StringBuilder();

			for (int i = 0; i < maxColumn-1; i++) {
			
					linea = linea.append(pirola[i] + " ,");
					
			}
			
			linea = linea.append(pirola[pirola.length-1]);
			
			



		return linea.toString();

	

	}
}
