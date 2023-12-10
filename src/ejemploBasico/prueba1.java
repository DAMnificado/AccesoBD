package ejemploBasico;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


public class prueba1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/","root","abc123");
			Statement st=con.createStatement();
			st.execute("USE world");
			ResultSet rs=st.executeQuery("SELECT * FROM city");
			ResultSetMetaData rsmd=rs.getMetaData();
			System.out.println("Número de columnas totales: " + rsmd.getColumnCount());
			for(int i=1;i<=rsmd.getColumnCount();i++) {
				System.out.println("Columna " + i);
				System.out.println("	Nombre: " + rsmd.getColumnName(i));
				System.out.println("	Tipo de dato: " + rsmd.getColumnTypeName(i));
				
				//Te devuelve un ejemplo de la clase: System.out.println(rsmd.getColumnType(i));
				
			}
			
			while(rs.next()) {
				
				System.out.println(rs.getInt(1)+": "+rs.getString(2)+": "+rs.getString(3)+rs.getString(4)+rs.getInt(5));
				
			}
					

				
			
			

			con.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}