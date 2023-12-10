package ejercicioFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

import ejerTablas.FromTabla;

public class Conexion {
		
		private Connection con;
		
		public Conexion(String host, String puerto, String user, String pswd, boolean mostrar) {
			
			this.con=conectar(host, puerto, user, pswd, mostrar);
		}
		
		private Connection conectar(String host,String puerto,String user,String pswd, boolean mostrar) {
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				String URL="jdbc:mysql://"+host+":"+puerto;
				Connection con=DriverManager.getConnection(URL,user,pswd);
				
				if(mostrar) {
					JOptionPane.showMessageDialog(null, "Conexión establecida");
				}
			
				
		
				return con;
			} catch (ClassNotFoundException | SQLException e) {
				
				JOptionPane.showMessageDialog(null, "Conexión fallida");
		
				System.out.println(e.getMessage());
				return null;
			}
		}
		
		
		
		public Connection getCon() {
			return this.con;
		}


}
