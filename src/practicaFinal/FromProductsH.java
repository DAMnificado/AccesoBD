package practicaFinal;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTable;

public class FromProductsH extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Connection con;
	private JTable miTabla;
	private DefaultTableModel tableModel;
	private JScrollPane scrollPane;
	private JButton btnAÑADIR;
	private JButton btnMOSTRARTABLA;
	private JButton btnEXPORTAR;
	private int contadorImportados = 0;
	
	public FromProductsH(Connection con) {
		
		
		this.con=con;
		//solo la linea de arriba me llevo media hora
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		btnMOSTRARTABLA = new JButton("Mostrar tabla");
		btnMOSTRARTABLA.setBounds(250, 11, 105, 47);
		contentPane.add(btnMOSTRARTABLA);
		btnMOSTRARTABLA.addActionListener(this);
		
		btnAÑADIR = new JButton("Añadir productos");
		btnAÑADIR.setBounds(449, 20, 135, 29);
		contentPane.add(btnAÑADIR);
		btnAÑADIR.addActionListener(this);
		
		tableModel = new DefaultTableModel();
		miTabla = new JTable(tableModel);
		scrollPane = new JScrollPane(miTabla);
		scrollPane.setBounds(10, 69, 614, 381);
		contentPane.add(scrollPane);
		
		btnEXPORTAR = new JButton("Exportar");
		btnEXPORTAR.setBounds(47, 23, 89, 23);
		contentPane.add(btnEXPORTAR);
		btnEXPORTAR.addActionListener(this);
	
	}

	public File seleccionaArchivoALeer() {
		
		File archivo = null;
		
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File("C:\\Users\\elmen\\Desktop"));
		JOptionPane.showMessageDialog(null, "Selecciona el archivo de donde quieras extraer los datos a añadir");
		int result = fc.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			archivo = fc.getSelectedFile();
		}
		return archivo;
	}

	public void insertProduct(String[] lineaArray) {

		Statement st;
		ResultSetMetaData rsmd;
		ResultSet rs;

		   try {
		       
		        st = con.createStatement();
		        st.execute("USE northwind");
		        rs = st.executeQuery("SELECT * FROM products");
		        rsmd = rs.getMetaData();

		        
		        StringBuilder sqlBuilder = new StringBuilder("INSERT INTO products(ProductName, SupplierID, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued) VALUES (?,?,?,?,?,?,?,?,?)");
		        PreparedStatement ps = con.prepareStatement(sqlBuilder.toString());
		        
		        System.out.println(Arrays.toString(lineaArray)+"\n");
		        
		        int j = 1;
		        for (int i = 0; i < lineaArray.length; i++) {
		            ps.setObject(j, lineaArray[i]); 
		            j++;
		       
		        }

		        ps.executeUpdate();
		        ps.close();

		    } catch (SQLException e1) {
		    	
		    	e1.printStackTrace();
		    	
		    }
	}

	public void insertProductsFromFile() {
		
	    File file = seleccionaArchivoALeer();

	    try {
	        BufferedReader br = new BufferedReader(new FileReader(file));

	        String linea;
	        String[] lineaArray;
	        while ((linea = br.readLine()) != null) {
	            lineaArray = linea.split(", ");
	            contadorImportados++;
	            insertProduct(lineaArray);
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}


	
	
	public ArrayList<String> obtenerTablaProducts(){
		
		Statement st;
		ResultSetMetaData rsmd;
		ResultSet rs;

		try {
			st = con.createStatement();
			st.execute("USE northwind");
			rs = st.executeQuery("SELECT * FROM products");
			rsmd = rs.getMetaData();
			
			
			int numCol = rsmd.getColumnCount();
			ArrayList<String> filasProductos = new ArrayList<String>();

			while (rs.next()) {
				StringBuilder cola = new StringBuilder();
				for (int i = 0; i < numCol; i++) {
					
					cola.append(rs.getObject(i+1)+", ");
					
				}
				filasProductos.add(cola.toString());
			}

			return filasProductos;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void guardarTablaEnFichero() {
		
		String nombreArchivo = JOptionPane.showInputDialog("Introduce el nombre del archivo en el que quieras guardar los datos");
		File archivo = new File(nombreArchivo);
		
		if (!archivo.getName().toLowerCase().endsWith(".txt")) {
			archivo = new File(nombreArchivo + ".txt");
		}

		
		ArrayList<String> tabla = obtenerTablaProducts();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
			int contadorExportados = 0;
			// Escribir encabezados
			writer.write(
					"ProductID, ProductName, SupplierID, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued");
			writer.newLine();
			
			for (String linea : tabla) {
				writer.write(linea + "\n");
				contadorExportados++;
			}
			

			JOptionPane.showMessageDialog(null, "Exportación completada." + "\n" + " Se han exportado " + contadorExportados + " productos." + "\n" +
					" Se han importado " + contadorImportados + " productos.");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	public void mostrarTablaProducts() {
		//por si le doy varias veces al boton que no se acumule
		tableModel.setColumnCount(0);

		try {
			Statement st;
			ResultSetMetaData rsmd;
			ResultSet rs;

			st = con.createStatement();
			st.execute("USE northwind");
			rs = st.executeQuery("SELECT * FROM products");
			
			
			tableModel.setRowCount(0);
			
			rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			
			for (int i = 0; i < columnCount; i++) {
				tableModel.addColumn(rsmd.getColumnName(i + 1));
			}

			while (rs.next()) {
				
				Object[] datosFila = new Object[columnCount];
				for (int i = 0; i < columnCount; i++) {
					datosFila[i] = rs.getObject(i + 1);

				}
				tableModel.addRow(datosFila);
			}

		} catch (SQLException e1) {

			e1.printStackTrace();
		}

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource()==btnMOSTRARTABLA) {
			mostrarTablaProducts();
		}

		if (e.getSource()==btnAÑADIR) {
			insertProductsFromFile();
		}
		
		if(e.getSource() == btnEXPORTAR) {
			guardarTablaEnFichero();
		}
	}
}
