package consultaFecha;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class FromConector extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textFieldFechaDesde;
	private JTextField textFieldFechaHasta;
	private JTable table;

	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FromConector frame = new FromConector();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public FromConector() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nombre cliente");
		lblNewLabel.setBounds(43, 40, 96, 16);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(154, 35, 170, 26);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("CONSULTAR");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Conexion con = new Conexion("localhost", "3306", "root", "1234");
				try {
					Statement st=con.getCon().createStatement();
					st.execute("USE northwind;");
					
					String consulta="SELECT * FROM orders WHERE customerID=? AND orderDate BETWEEN ? AND ?";
					PreparedStatement ps=con.getCon().prepareStatement(consulta);
					

				
					
					ps.setString(1, textField.getText());
					
					// Procesa y establece la primera fecha
					String textoDesde = textFieldFechaDesde.getText();
					textoDesde+=" 00:00:00";
					SimpleDateFormat formatoDesde = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.util.Date textoDesdeParsed = formatoDesde.parse(textoDesde);
					java.sql.Timestamp fechaSqlDesde = new java.sql.Timestamp(textoDesdeParsed.getTime());
					ps.setTimestamp(2, fechaSqlDesde);

					// Procesa y establece la segunda fecha
					String fechaEnTextoHasta = textFieldFechaHasta.getText(); // Asumiendo que tienes otro JTextField para la fecha hasta
					fechaEnTextoHasta+=" 23:59:59";
					SimpleDateFormat formatoHasta = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					java.util.Date fechaParsedHasta = formatoHasta.parse(fechaEnTextoHasta);
					java.sql.Timestamp fechaSqlHasta = new java.sql.Timestamp(fechaParsedHasta.getTime());
					ps.setTimestamp(3, fechaSqlHasta);
					
					

					
					
					
					ResultSet rs=ps.executeQuery();	
					
					ResultSetMetaData rsmd = rs.getMetaData();
					
					while(rs.next()) {
						for(int i=1;i<rsmd.getColumnCount();i++) {
							System.out.print(rs.getObject(i)+" ");
						}
						System.out.println();
					
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnNewButton.setBounds(285, 104, 117, 29);
		contentPane.add(btnNewButton);
		
		JLabel lblFechaDesde = new JLabel("Fecha desde");
		lblFechaDesde.setBounds(43, 76, 96, 16);
		contentPane.add(lblFechaDesde);
		
		JLabel lblFechaHasta = new JLabel("Fecha hasta");
		lblFechaHasta.setBounds(43, 109, 96, 16);
		contentPane.add(lblFechaHasta);
		
		textFieldFechaDesde = new JTextField();
		textFieldFechaDesde.setBounds(154, 73, 96, 26);
		contentPane.add(textFieldFechaDesde);
		textFieldFechaDesde.setColumns(10);
		
		textFieldFechaHasta = new JTextField();
		textFieldFechaHasta.setColumns(10);
		textFieldFechaHasta.setBounds(154, 104, 96, 26);
		contentPane.add(textFieldFechaHasta);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(43, 158, 385, 97);
		contentPane.add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
	}
}
