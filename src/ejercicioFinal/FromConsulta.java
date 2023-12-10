package ejercicioFinal;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.sound.midi.Soundbank;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import java.sql.PreparedStatement;

public class FromConsulta extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel tableModel;
	private JTable tableX;
	private JScrollPane scrollPaneX;
	private JButton btnInsertar;
	private JButton btnGuardar;
	private JButton btnProductos;
	private Connection con;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FromConsulta(Connection con) {
		this.con = con;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		tableModel = new DefaultTableModel();
		tableX = new JTable(tableModel);
		scrollPaneX = new JScrollPane(tableX);
		scrollPaneX.setBounds(10, 69, 614, 381);
		contentPane.add(scrollPaneX);

		btnProductos = new JButton("MOSTRAR PRODUCTOS");
		btnProductos.addActionListener(this);
		btnProductos.setBounds(221, 11, 157, 47);
		contentPane.add(btnProductos);

		btnInsertar = new JButton("Insertar");
		btnInsertar.setBounds(462, 23, 89, 23);
		contentPane.add(btnInsertar);
		btnInsertar.addActionListener(this);

		btnGuardar = new JButton("Guardar");
		btnGuardar.setBounds(45, 23, 89, 23);
		contentPane.add(btnGuardar);
		btnGuardar.addActionListener(this);
	}

	public void mostrarProductos() {

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
			
//			System.out.println("Número de columnas totales: " + columnCount);
//			for(int i=1;i<=rsmd.getColumnCount();i++) {
//				System.out.println("Columna " + i);
//				System.out.println("	Nombre: " + rsmd.getColumnName(i));
//				System.out.println("	Tipo de dato: " + rsmd.getColumnTypeName(i));
//			}
//			
//			while (rs.next()) {
//
//				for (int i = 1; i < columnCount; i++) {
//					String producto = rs.getObject(i).toString();
//					System.out.println(producto);
//				}
//
//			}

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

	public File seleccionarArchivo() {

		JFileChooser fc = new JFileChooser();
		File archivo = null;
	
		JOptionPane.showMessageDialog(null, "Selecciona el archivo de donde quieras extraer los datos a añadir");
		int result = fc.showOpenDialog(null);

		if (result == fc.APPROVE_OPTION) {
			archivo = fc.getSelectedFile();
		}
		return archivo;
	}

	public String obtenerNombreColumnas() {

		Statement st;
		ResultSetMetaData rsmd;
		ResultSet rs;

		try {
			st = con.createStatement();
			st.execute("USE northwind");
			rs = st.executeQuery("SELECT * FROM products");
			rsmd = rs.getMetaData();
			int maxColumn= rsmd.getColumnCount();
			
			StringBuilder linea = new StringBuilder();

			for (int i = 0; i < maxColumn-1; i++) {
			
					linea = linea.append(rsmd.getColumnName(i+1) + " ,");
					
			}
			
			linea = linea.append(rsmd.getColumnName(maxColumn));
			
			return linea.toString();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public String interrogantes(int max) {

	
		StringBuilder interrogantes = new StringBuilder();

		interrogantes.append("(");
		for (int i = 0; i < max - 1; i++) {
			interrogantes.append("?, ");

		}
		interrogantes.append("?)");

		return interrogantes.toString();

	}

	public void insertProduct(String[]lineaArray) {

		Statement st;
		ResultSetMetaData rsmd;
		ResultSet rs;

		try {
			st = con.createStatement();
			st.execute("USE northwind");
			rs = st.executeQuery("SELECT * FROM products");
			rsmd = rs.getMetaData();

			int columnCount = rsmd.getColumnCount();
			File archivo = seleccionarArchivo();

		
			StringBuilder sqlBuilder = new StringBuilder("INSERT INTO products(" + obtenerNombreColumnas() + ")" + " VALUES " + interrogantes(columnCount));

			PreparedStatement preparedStatement = con.prepareStatement(sqlBuilder.toString());

			
				for (int i = 0; i < lineaArray.length; i++) {
					switch (rsmd.getColumnType(i + 1)) {
					case Types.INTEGER:
						String intValue = lineaArray[i].trim();
						if (!intValue.isEmpty()) {
							preparedStatement.setInt(i + 1, Integer.parseInt(intValue));
						} else {
							preparedStatement.setNull(i + 1, Types.INTEGER);
						}
						break;
					case Types.VARCHAR:
						preparedStatement.setString(i + 1, lineaArray[i]);
						break;
					case Types.DOUBLE:
						String doubleValue = lineaArray[i].trim();
						if (!doubleValue.isEmpty()) {
							preparedStatement.setDouble(i + 1, Double.parseDouble(doubleValue));
						} else {
							preparedStatement.setNull(i + 1, Types.DOUBLE);
						}
						break;
					case Types.SMALLINT:
						String shortValue = lineaArray[i].trim();
						if (!shortValue.isEmpty()) {
							preparedStatement.setShort(i + 1, Short.parseShort(shortValue));
						} else {
							preparedStatement.setNull(i + 1, Types.SMALLINT);
						}
						break;
					case Types.BIT:
						// Verificar si es un valor booleano válido
						String booleanValue = lineaArray[i].trim();
						if ("true".equalsIgnoreCase(booleanValue) || "false".equalsIgnoreCase(booleanValue)) {
							preparedStatement.setBoolean(i + 1, Boolean.parseBoolean(booleanValue));
						} else {
							preparedStatement.setNull(i + 1, Types.BOOLEAN);
						}
						break;
					}
				}

				preparedStatement.executeUpdate();
			

			preparedStatement.close();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void insertProductsFromFile() {
		
		File file = seleccionarArchivo();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
		
			String linea;
			String [] lineaArray;
			while ((linea = br.readLine())!=null) {
				lineaArray=linea.split(",");
				insertProduct(lineaArray);
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public ArrayList<String> obtenerTablaProducts() {

		Statement st;
		ResultSetMetaData rsmd;
		ResultSet rs;

		try {
			st = con.createStatement();
			st.execute("USE northwind");
			rs = st.executeQuery("SELECT * FROM products");
			rsmd = rs.getMetaData();
			StringBuilder cola = new StringBuilder();
			
			int numCol = rsmd.getColumnCount();
			ArrayList<String> linea = new ArrayList<String>();

			while (rs.next()) {

				for (int i = 0; i < numCol; i++) {
					
					cola.append(rs.getObject(i+1)+", ");
					
				}
				
				linea.add(cola.toString());
			}

			return linea;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public File crearFichero() {

		JFileChooser fc;

		// Crear un JFileChooser para que el usuario seleccione la ubicación y el nombre
		// del archivo
		fc = new JFileChooser();

		JOptionPane.showMessageDialog(null, "Crea un archivo donde quieras guardar la tabla modificada");
		int resultado = fc.showOpenDialog(null);
		File archivoNuevo = null;

		if (resultado == JFileChooser.APPROVE_OPTION) {

			archivoNuevo = fc.getSelectedFile();

			// Verificar si la extensión del archivo es .txt
			if (!archivoNuevo.getName().toLowerCase().endsWith(".txt")) {
				archivoNuevo = new File(archivoNuevo.getAbsolutePath() + ".txt");
			}

			// Intentar crear el archivo
			try {
				if (archivoNuevo.createNewFile()) {
					JOptionPane.showMessageDialog(null,
							"Archivo creado correctamente: " + archivoNuevo.getAbsolutePath());
				} else {
					JOptionPane.showMessageDialog(null, "El archivo ya existe.", "Error", JOptionPane.ERROR_MESSAGE);
				}
			} catch (IOException e2) {
				e2.printStackTrace();
				JOptionPane.showMessageDialog(null, "Error al crear el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
			}

		}
		return archivoNuevo;
	}

	public void guardarTablaEnFichero() {

		File archivo = crearFichero();
		ArrayList<String> tabla = obtenerTablaProducts();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {

			// Escribir encabezados
			writer.write(
					"ProductID, ProductName, SupplierID, CategoryID, QuantityPerUnit, UnitPrice, UnitsInStock, UnitsOnOrder, ReorderLevel, Discontinued");
			writer.newLine();
			
			for (String linea : tabla) {
				writer.write(linea + "\n");
			}
			

			JOptionPane.showMessageDialog(null, "Exportación completada.");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnProductos) {
			mostrarProductos();

		} else if (e.getSource() == btnInsertar) {
			insertProductsFromFile();
		}

		else if (e.getSource() == btnGuardar) {
			guardarTablaEnFichero();
		}

	}
}
