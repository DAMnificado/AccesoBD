package practicaFinal;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.cj.xdevapi.Statement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JPasswordField;

public class FromConexionH extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textHOST;
	private JTextField textPUERTO;
	private JTextField textUSER;
	private JPasswordField textPASS;
	private JButton btnCONECTAR;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FromConexionH frame = new FromConexionH();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public FromConexionH() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		// LABEL

		JLabel lblHOST = new JLabel("host");
		lblHOST.setBounds(86, 72, 46, 14);
		contentPane.add(lblHOST);

		JLabel lblPUERTO = new JLabel("puerto");
		lblPUERTO.setBounds(83, 141, 46, 14);
		contentPane.add(lblPUERTO);

		JLabel lblUSER = new JLabel("user");
		lblUSER.setBounds(85, 238, 46, 14);
		contentPane.add(lblUSER);

		JLabel lblPASS = new JLabel("pass");
		lblPASS.setBounds(87, 342, 46, 14);
		contentPane.add(lblPASS);

		// TEXTFIELD
		
		// HOST
		textHOST = new JTextField();
		textHOST.setBounds(188, 68, 86, 20);
		contentPane.add(textHOST);
		textHOST.setColumns(10);
	
		//PUERTO
		textPUERTO = new JTextField();
		textPUERTO.setBounds(189, 136, 86, 20);
		contentPane.add(textPUERTO);
		textPUERTO.setColumns(10);
		textPUERTO.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
			    char c = e.getKeyChar();
			    
			    if (!Character.isDigit(c) && c != '.') {
			    	  if (c != KeyEvent.VK_BACK_SPACE) {
			                JOptionPane.showMessageDialog(null, "Solo se permiten números");
			                e.consume();
			            }
			    }
			   
			}
			
		});
		
		//USER
		textUSER = new JTextField();
		textUSER.setBounds(182, 235, 86, 20);
		contentPane.add(textUSER);
		textUSER.setColumns(10);
	
		//PASS
		textPASS = new JPasswordField();
		textPASS.setBounds(143, 339, 42, 20);
		contentPane.add(textPASS);;
	
		// BOTONES
		btnCONECTAR = new JButton("Conectar");
		btnCONECTAR.setBounds(422, 65, 89, 23);
		contentPane.add(btnCONECTAR);
		btnCONECTAR.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnCONECTAR) {

			
			if (textHOST.getText().isEmpty() || textPUERTO.getText().isEmpty() || textUSER.getText().isEmpty() || new String(textPASS.getPassword()).isEmpty()) {
	            JOptionPane.showMessageDialog(null, "Por favor introduce todos los campos");
	            return;
	        }
			
			String host = textHOST.getText();
			String puerto = textPUERTO.getText();
			String user = textUSER.getText();
			char[] pswd = textPASS.getPassword();
			String pass = new String(pswd);

			ConexionH miConexion = new ConexionH(host, puerto, user, pass);

			if (miConexion.getCon() != null) {
				dispose();

				FromProductsH frame = new FromProductsH(miConexion.getCon());
				frame.setVisible(true);
			}

		}
	}
}
