package practicaFinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class ConexionH {

	private Connection con;

	public ConexionH(String host, String puerto, String user, String pass) {
		this.con = conectar(host, puerto, user, pass);
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

//Una forma rapida y segura de saber el nombre de las columnas y sus tipos
//Statement st;
//ResultSet rs;
//ResultSetMetaData rsmd;
//
//
//st = con.createStatement();
//st.execute("USE northwind");
//rs = st.executeQuery("SELECT * FROM products");
//rsmd = rs.getMetaData();
//int maxColumn = rsmd.getColumnCount();
//System.out.println("Nombre columnas:");
//
//for (int i = 1; i < maxColumn+1; i++) {
//	
//	System.out.println(rsmd.getColumnName(i));
//	System.out.println(rsmd.getColumnTypeName(i));
//}