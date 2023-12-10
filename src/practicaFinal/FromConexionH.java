package practicaFinal;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

		// label

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

		// textField

		textHOST = new JTextField();
		textHOST.setText("localhost");
		textHOST.setBounds(188, 68, 86, 20);
		contentPane.add(textHOST);
		textHOST.setColumns(10);

		textPUERTO = new JTextField();
		textPUERTO.setText("3306");
		textPUERTO.setBounds(189, 136, 86, 20);
		contentPane.add(textPUERTO);
		textPUERTO.setColumns(10);

		textUSER = new JTextField();
		textUSER.setText("root");
		textUSER.setBounds(182, 235, 86, 20);
		contentPane.add(textUSER);
		textUSER.setColumns(10);

		textPASS = new JPasswordField();
		textPASS.setBounds(143, 339, 42, 20);
		contentPane.add(textPASS);

		// botones
		btnCONECTAR = new JButton("Conectar");
		btnCONECTAR.setBounds(422, 65, 89, 23);
		contentPane.add(btnCONECTAR);
		btnCONECTAR.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == btnCONECTAR) {

			String hostP = textHOST.getText();

			if (hostP.isEmpty() || !hostP.matches("^[a-zA-Z.-]+$")) {
				JOptionPane.showMessageDialog(null, "Por favor, introduce un valor no numérico para el puerto", "Error",
						JOptionPane.ERROR_MESSAGE);

			}

			String puertoP = textPUERTO.getText();

			if (puertoP.isEmpty() || !puertoP.matches("\\d+")) {
				JOptionPane.showMessageDialog(null, "Por favor, introduce un valor numérico válido para el puerto",
						"Error", JOptionPane.ERROR_MESSAGE);

			}

			String userP = textUSER.getText();

			if (userP.isEmpty() || !userP.matches("^[a-zA-Z0-9.-]+$")) {
				// Mostrar un mensaje de error
				JOptionPane.showMessageDialog(null, "Por favor, introduce un valor válido para el usuario", "Error",
						JOptionPane.ERROR_MESSAGE);

			}

			char[] pswd = textPASS.getPassword();
			String passP = new String(pswd);

			String pass;
			if (passP.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Por favor, introduce tu contraseña", "Error",
						JOptionPane.ERROR_MESSAGE);

			}

	
			ConexionH miConexion = new ConexionH(hostP, puertoP, userP, passP);

			if (miConexion.getCon() != null) {
				dispose();

				FromProductsH frame = new FromProductsH(miConexion.getCon());
				frame.setVisible(true);
			}

		}
	}
}
