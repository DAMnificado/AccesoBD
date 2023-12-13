package consultaFecha;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class Conexion {
	
	private Connection con;
	
	public Conexion(String host, String puerto, String user, String pswd) {
		
		this.con=conectar(host, puerto, user, pswd);
	}
	
	private Connection conectar(String host,String puerto,String user,String pswd) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String URL="jdbc:mysql://"+host+":"+puerto;
			Connection con=DriverManager.getConnection(URL,user,pswd);
			JOptionPane.showMessageDialog(null, "Conexión establecida");
			return con;
		} catch (ClassNotFoundException | SQLException e) {
			
			JOptionPane.showMessageDialog(null, "Conexión fallida");
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}
	
	public Connection getCon() {
		return this.con;
	}

}