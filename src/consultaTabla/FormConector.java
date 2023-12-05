package consultaTabla;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.Arrays;

import javax.swing.JComboBox;
import javax.swing.JTable;

public class FormConector extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtFiledConsulta;
	private JTextField txtFieldServidor;
	private JTextField txtFieldPuerto;
	private JTextField txtFieldUser;
	private JPasswordField txtFieldPass;
	private JComboBox<String> comboBoxBase;
	private JTextField txtFieldConsulta;
	private JTable table;
	private DefaultTableModel tableModel;
	private ConexionBD miConexion;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormConector frame = new FormConector();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FormConector() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//JLabel´s
		
		JLabel lblServidorYPuerto = new JLabel("Servidor y puerto");
		lblServidorYPuerto.setBounds(42, 53, 116, 16);
		contentPane.add(lblServidorYPuerto);
		
		JLabel lblBD = new JLabel("Base de datos");
		lblBD.setEnabled(false);
		lblBD.setBounds(42, 174, 94, 16);
		contentPane.add(lblBD);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(42, 90, 61, 16);
		contentPane.add(lblUsuario);
		
		JLabel lblContrasea = new JLabel("Contraseña");
		lblContrasea.setBounds(42, 130, 94, 16);
		contentPane.add(lblContrasea);
		
		JLabel lblConsulta = new JLabel("Consulta");
		lblConsulta.setBounds(44, 220, 46, 14);
		contentPane.add(lblConsulta);
		
	
		//JTextField´s
		
		txtFieldServidor = new JTextField();
		txtFieldServidor.setText("localhost");
		txtFieldServidor.setBounds(156, 48, 130, 26);
		contentPane.add(txtFieldServidor);
		txtFieldServidor.setColumns(10);
		
		txtFieldPuerto = new JTextField();
		txtFieldPuerto.setText("3306");
		txtFieldPuerto.setColumns(10);
		txtFieldPuerto.setBounds(309, 48, 61, 26);
		contentPane.add(txtFieldPuerto);
		
		txtFieldUser = new JTextField();
		txtFieldUser.setText("root");
		txtFieldUser.setBounds(156, 85, 130, 26);
		contentPane.add(txtFieldUser);
		
		
		txtFieldPass = new JPasswordField();
		txtFieldPass.setText("1234");
		txtFieldPass.setBounds(156, 125, 130, 26);
		contentPane.add(txtFieldPass);
		
		txtFieldConsulta = new JTextField();
		txtFieldConsulta.setText("SELECT * FROM");
		txtFieldConsulta.setBounds(106, 219, 101, 16);
		contentPane.add(txtFieldConsulta);
		
		//JComboBox´s 
		comboBoxBase = new JComboBox();
		comboBoxBase.setEnabled(false);
		comboBoxBase.setBounds(156, 169, 130, 27);
		contentPane.add(comboBoxBase);
		
		JComboBox<String> comboBoxTabla = new JComboBox();
		comboBoxTabla.setBounds(217, 215, 82, 24);
		contentPane.add(comboBoxTabla);
		comboBoxTabla.addItem("categories");
		comboBoxTabla.addItem("city");
		
		
		//JButton´s
		
		JButton btnConectar = new JButton("Conectar");
		btnConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String host=txtFieldServidor.getText();
				String user=txtFieldUser.getText();
				String puerto=txtFieldPuerto.getText();
				
//				char [] password=passwordField.getPassword();
//				String pass=new String(password);
				
				String pass=txtFieldPass.getText();
				
				miConexion=new ConexionBD(host,puerto,user,pass);
				Connection con=miConexion.getCon();
				Statement st;
				
				try {
					
					st=con.createStatement();
					ResultSet rs=st.executeQuery("SHOW DATABASES");
					
	
					while(rs.next()) {
						
					String dbName = rs.getString(1);
					
					 if (dbName.equals("northwind") || dbName.equals("world")) {
		                  
		                    comboBoxBase.addItem(rs.getString(1));
		                }
					}
					comboBoxBase.setEnabled(true);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
					
			}
		});
		
		
		btnConectar.setBounds(309, 168, 117, 29);
		contentPane.add(btnConectar);
		
		JButton btnConsultar = new JButton("Consultar");
		btnConsultar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				String host=txtFieldServidor.getText();
				String user=txtFieldUser.getText();
				String puerto=txtFieldPuerto.getText();
				
//				char [] password=passwordField.getPassword();
//				String pass=new String(password);
				
				
				char [] papa = txtFieldPass.getPassword();
				String pass=new String (papa);
				
				Arrays.fill(papa, ' ');
				
				
				
				pass = null;
				
				Connection con=miConexion.getCon();
				Statement st;
				try {
					
					st = con.createStatement();
					st.execute("USE " + comboBoxBase.getSelectedItem());
					ResultSet rs=st.executeQuery("SELECT * FROM " + comboBoxTabla.getSelectedItem());
					
					tableModel.setRowCount(0);
					
					ResultSetMetaData rsmd=rs.getMetaData();
					int columnCount = rsmd.getColumnCount();
					System.out.println("Número de columnas totales: " + rsmd.getColumnCount());
					
					
					 // Agregar columnas al modelo de la tabla
	                for (int i = 1; i <= columnCount; i++) {
	                    tableModel.addColumn(rsmd.getColumnName(i));
	                }
	                
	                Object[] rowData = new Object[columnCount];
	                // Agregar filas al modelo de la tabla
	                while (rs.next()) {
	                   
	                    for (int i = 0; i < columnCount; i++) {
	           
	                        rowData[i] = rs.getObject(i + 1);
	                    }
	                    tableModel.addRow(rowData);
	                }
					
			
			
				
				} catch (SQLException e1) {
				
					e1.printStackTrace();
				}
				
				try {
					con.close();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
		btnConsultar.setBounds(309, 216, 117, 24);
		contentPane.add(btnConsultar);
		
		 	tableModel = new DefaultTableModel();
	        table = new JTable(tableModel);
	        JScrollPane scrollPane = new JScrollPane(table);
	        scrollPane.setBounds(510, 29, 238, 203);
	        contentPane.add(scrollPane);
		
	
		
		
		
	
	
		
	}
}
