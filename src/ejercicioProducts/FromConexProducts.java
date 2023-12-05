package ejercicioProducts;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import consultaTabla.FromConsulta;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class FromConexProducts extends JFrame implements ActionListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtHost;
	private JTextField txtPuerto;
	private JTextField txtUser;
	private JPasswordField txtPass;
	private JButton btnConectar;
	private Connection con;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FromConexProducts frame = new FromConexProducts();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FromConexProducts() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtHost = new JTextField();
		txtHost.setText("localhost");
		txtHost.setBounds(169, 90, 86, 20);
		contentPane.add(txtHost);
		txtHost.setColumns(10);
		
		JLabel lblHost = new JLabel("Host");
		lblHost.setBounds(113, 93, 46, 14);
		contentPane.add(lblHost);
		
		JLabel lblPuerto = new JLabel("Puerto");
		lblPuerto.setBounds(113, 147, 46, 14);
		contentPane.add(lblPuerto);
		
		txtPuerto = new JTextField();
		txtPuerto.setText("3306");
		txtPuerto.setBounds(169, 144, 86, 20);
		contentPane.add(txtPuerto);
		txtPuerto.setColumns(10);
		
		JLabel lblUser = new JLabel("User");
		lblUser.setBounds(113, 211, 46, 14);
		contentPane.add(lblUser);
		
		txtUser = new JTextField();
		txtUser.setText("root");
		txtUser.setBounds(169, 208, 86, 20);
		contentPane.add(txtUser);
		txtUser.setColumns(10);
		
		btnConectar = new JButton("CONECTAR");
		btnConectar.setBounds(367, 165, 89, 23);
		contentPane.add(btnConectar);
		btnConectar.addActionListener(this);
		
		txtPass = new JPasswordField();
		txtPass.setBounds(169, 277, 86, 20);
		contentPane.add(txtPass);
		txtUser.setText("1234");
		
		JLabel lblPass = new JLabel("Pass");
		lblPass.setBounds(113, 280, 46, 14);
		contentPane.add(lblPass);
	}
	
	public void conectarse() {
		
		ConexionProducts cp = new ConexionProducts(txtHost.getText(),txtPuerto.getText(),txtUser.getText(),txtPass.getText());
		
		this.con = cp.getCon();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == btnConectar) {
			conectarse();
			dispose();
			FromConsulta frame = new FromConsulta(con);
			frame.setVisible(true);
		}
		
	}
	
	
}
