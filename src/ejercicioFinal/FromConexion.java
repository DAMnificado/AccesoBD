package ejercicioFinal;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ejerTablas.Conec;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Statement;
import java.awt.event.ActionEvent;

public class FromConexion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textUserField;
	private JTextField textPuertoField;
	
	private JPasswordField passwordField;
	private String pswd;
	private String host,puerto,user;
	
	private JTextField textHostField;
	private JLabel hostLabel;
	private JLabel userLabel;
	private JLabel passLabel;
	private JLabel puertoLabel;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FromConexion frame = new FromConexion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FromConexion() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//JLABEL´S
		//USER
		userLabel = new JLabel("Usuario");
		userLabel.setBounds(37, 93, 46, 14);
		contentPane.add(userLabel);
		
		//PASS
		passLabel = new JLabel("Password");
		passLabel.setBounds(39, 136, 46, 14);
		contentPane.add(passLabel);
		
		//PUERTO
		puertoLabel = new JLabel("puerto");
		puertoLabel.setBounds(208, 46, 46, 14);
		contentPane.add(puertoLabel);
		
		//SERVIDOR
		hostLabel = new JLabel("Host");
		hostLabel.setBounds(37, 44, 53, 18);
		contentPane.add(hostLabel);
		
		// JTEXTFIELD´S
		//USER
		textUserField = new JTextField();
		textUserField.setText("root");
		textUserField.setBounds(110, 90, 86, 20);
		contentPane.add(textUserField);
		textUserField.setColumns(10);
		
		//PUERTO
		textPuertoField = new JTextField();
		textPuertoField.setText("3306");
		textPuertoField.setBounds(282, 43, 86, 20);
		contentPane.add(textPuertoField);
		textPuertoField.setColumns(10);
		
		//PASS
		passwordField = new JPasswordField();
		passwordField.setText("abc123");
		passwordField.setBounds(108, 133, 88, 20);
		contentPane.add(passwordField);
		
		//SERVIDOR
		textHostField = new JTextField();
		textHostField.setText("localhost");
		textHostField.setBounds(110, 43, 86, 20);
		contentPane.add(textHostField);
		textHostField.setColumns(10);
		
		JButton btnBotonConectar = new JButton("CONECTAR");
		btnBotonConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			host=textHostField.getText();
			puerto=textPuertoField.getText();
			user=textUserField.getText();
			
			
			//seguridad en contraseña
			char [] password=passwordField.getPassword();
			pswd=new String(password);
			
			Conexion miConexion=new Conexion(host,puerto,user,pswd,true);
	
		 if (miConexion.getCon() != null) {
             dispose();
             
            FromConsulta frame = new FromConsulta(miConexion.getCon());
			frame.setVisible(true);
         }
			
			}
		});
		btnBotonConectar.setBounds(282, 103, 143, 80);
		contentPane.add(btnBotonConectar);
		
	}
}
