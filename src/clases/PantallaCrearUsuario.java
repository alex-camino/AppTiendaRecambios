package clases;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Tablas.Usuarios;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextPane;

import com.toedter.calendar.JDateChooser;

public class PantallaCrearUsuario extends JFrame {

	private JPanel contentPane;
	private ImageIcon logo;//new ImageIcon("/Users/Alex/Eclipse/Proyecto_AplicacionRecambios/logoCarrito.png");
	private Connection conexion;
	private JTextField txtNombreUser;
	private JPasswordField pass1;
	private JPasswordField pass2;
	private JLabel txtExiste;
	private boolean creado;
	private JPanel panelDatos;
	private JTabbedPane tabbedPane;
	private JTextField txtNombre;
	private JTextField txtApellidos;
	private JTextField txtEmail;
	private JTextField txtTelefono;
	private JLabel txtUsuario;
	private JTextPane txtDireccion;
	private JTextField txtDni;
	private JDateChooser fechaNacimiento;
	
	
	public PantallaCrearUsuario(Connection conexion) {
		
		this.conexion=conexion;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1260, 660);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		

		JPanel panel = new JPanel();
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelCentral = new JPanel();
		panel.add(panelCentral, BorderLayout.CENTER);
		panelCentral.setLayout(null);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(144, 43, 921, 540);
		panelCentral.add(tabbedPane);
		
		JPanel panelUsuario = new JPanel();
		tabbedPane.addTab("Usuario", null, panelUsuario, null);
		panelUsuario.setLayout(null);
		
		JLabel lblNombreUsuario = new JLabel("Nombre Usuario:");
		lblNombreUsuario.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNombreUsuario.setBounds(126, 73, 131, 16);
		panelUsuario.add(lblNombreUsuario);
		
		txtNombreUser = new JTextField();
		txtNombreUser.setBounds(400, 68, 185, 28);
		panelUsuario.add(txtNombreUser);
		txtNombreUser.setColumns(10);
		
		JButton btnComprobar = new JButton("Comprobar");
		btnComprobar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comprobarUsuario(txtNombreUser.getText())){
					
					txtExiste.setText("El usuario introducido ya existe.");
				}else{
					
					txtExiste.setText("El usuario esta disponible.");
				}
			}
		});
		btnComprobar.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnComprobar.setBounds(438, 138, 117, 29);
		panelUsuario.add(btnComprobar);
		
		JLabel lblComprobarSiExiste = new JLabel("Comprobar si existe el usuario");
		lblComprobarSiExiste.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblComprobarSiExiste.setBounds(126, 143, 227, 16);
		panelUsuario.add(lblComprobarSiExiste);
		
		txtExiste = new JLabel("");
		txtExiste.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		txtExiste.setBounds(597, 73, 264, 21);
		panelUsuario.add(txtExiste);
		
		JLabel lblIntroduzcaSuPassword = new JLabel("Introduzca su Password:");
		lblIntroduzcaSuPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblIntroduzcaSuPassword.setBounds(126, 213, 185, 16);
		panelUsuario.add(lblIntroduzcaSuPassword);
		
		pass1 = new JPasswordField();
		pass1.setBounds(400, 208, 185, 28);
		panelUsuario.add(pass1);
		
		JLabel lblIntroduzcaDeNuevo = new JLabel("Introduzca de nuevo su Password");
		lblIntroduzcaDeNuevo.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblIntroduzcaDeNuevo.setBounds(126, 277, 242, 16);
		panelUsuario.add(lblIntroduzcaDeNuevo);
		
		pass2 = new JPasswordField();
		pass2.setBounds(400, 272, 185, 28);
		panelUsuario.add(pass2);
		
		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				creado=cargarCrearUsuario(txtNombreUser.getText(), pass1.getText(), pass2.getText());
				
				if(creado){
					
					tabbedPane.setEnabledAt(1, true);
					txtUsuario.setText(txtNombreUser.getText());
					
				}
			}
		});
		btnCrearUsuario.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnCrearUsuario.setBounds(236, 376, 132, 45);
		panelUsuario.add(btnCrearUsuario);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					
						cargarVolver();
						
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
			}
		});
		btnVolver.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnVolver.setBounds(438, 376, 117, 45);
		panelUsuario.add(btnVolver);
		
		panelDatos = new JPanel();
		panelDatos.setToolTipText("");
		tabbedPane.addTab("Datos Usuario", null, panelDatos, null);
		panelDatos.setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblUsuario.setBounds(155, 65, 61, 16);
		panelDatos.add(lblUsuario);
		
		txtUsuario = new JLabel("");
		txtUsuario.setBounds(228, 66, 164, 16);
		panelDatos.add(txtUsuario);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblNombre.setBounds(65, 125, 73, 16);
		panelDatos.add(lblNombre);
		
		txtNombre = new JTextField();
		txtNombre.setBounds(185, 120, 164, 28);
		panelDatos.add(txtNombre);
		txtNombre.setColumns(10);
		
		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblApellidos.setBounds(65, 226, 73, 16);
		panelDatos.add(lblApellidos);
		
		txtApellidos = new JTextField();
		txtApellidos.setBounds(185, 221, 164, 28);
		panelDatos.add(txtApellidos);
		txtApellidos.setColumns(10);
		
		JLabel lblDireccin = new JLabel("Dirección:");
		lblDireccin.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblDireccin.setBounds(65, 274, 85, 16);
		panelDatos.add(lblDireccin);
		
		txtDireccion = new JTextPane();
		txtDireccion.setBounds(185, 274, 164, 120);
		panelDatos.add(txtDireccion);
		
		JLabel lblFechaDeNacimiento = new JLabel("Fecha de Nacimiento:");
		lblFechaDeNacimiento.setBounds(446, 126, 147, 15);
		panelDatos.add(lblFechaDeNacimiento);
		
		fechaNacimiento = new JDateChooser();
		fechaNacimiento.setBounds(627, 120, 193, 28);
		panelDatos.add(fechaNacimiento);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblEmail.setBounds(446, 194, 61, 16);
		panelDatos.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(627, 188, 164, 28);
		panelDatos.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblTelefono = new JLabel("Telefono:");
		lblTelefono.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblTelefono.setBounds(446, 275, 79, 16);
		panelDatos.add(lblTelefono);
		
		txtTelefono = new JTextField();
		txtTelefono.setBounds(627, 269, 164, 28);
		panelDatos.add(txtTelefono);
		txtTelefono.setColumns(10);
		
		JButton btnAadirDatos = new JButton("Añadir datos");
		btnAadirDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cargarAddDatosUsuario(txtNombreUser.getText());
			}
		});
		btnAadirDatos.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnAadirDatos.setBounds(507, 385, 117, 46);
		panelDatos.add(btnAadirDatos);
		
		JLabel lblDni = new JLabel("DNI:");
		lblDni.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblDni.setBounds(65, 175, 61, 16);
		panelDatos.add(lblDni);
		
		txtDni = new JTextField();
		txtDni.setBounds(185, 170, 164, 28);
		panelDatos.add(txtDni);
		txtDni.setColumns(10);
		tabbedPane.setEnabledAt(1, false);
	}

	public void cargarVolver() throws SQLException{
		
		PrincipalLogin pantallaPrincipal = new PrincipalLogin();
		pantallaPrincipal.setVisible(true);
		dispose();//Cierro la ventana actual
	}
	
	public boolean comprobarUsuario(String nombreUser){
		
		boolean existe=false;
		
		try{
			
			PreparedStatement pstmt= conexion.prepareStatement("select * from usuarios where user_alias=?");
			pstmt.setString(1, nombreUser);
			ResultSet usuario =pstmt.executeQuery();
			
			while(usuario.next()){
			
				existe=true;
			}
		
			usuario.close();
			pstmt.close();
		}catch(SQLException ex){
			
			JOptionPane.showMessageDialog(null,	"Error en la consulta.", "Buscando usuarios....", 0);
		}
	
		return existe;
	}
	
	public boolean cargarCrearUsuario(String nombreUser, String pass1, String pass2){
		
		boolean creado=false;
		
		
		if(!pass1.equals(pass2)){
			
			JOptionPane.showMessageDialog(null,	"Los passwords no coinciden.", "Creando Usuario....", 0);
		}else if(comprobarUsuario(nombreUser)){
			
			JOptionPane.showMessageDialog(null,	"Ese usuario ya esta en uso.", "Creando Usuario....", 0);
		}else{
			
			try{
				
				PreparedStatement pstmt= conexion.prepareStatement("insert into usuarios(user_alias,user_pass) VALUES (?, md5(?))");
				pstmt.setString(1, nombreUser);
				pstmt.setString(2, pass1);
				pstmt.executeUpdate();
				
				JOptionPane.showInputDialog(null,	"Usuario creaado correctamente.", "Creando usuario....", 0);
				creado=true;
				pstmt.close();
			}catch(SQLException ex){
				
				JOptionPane.showMessageDialog(null,	"Error en la consulta.", "Creando usuario....", 0);
				creado=false;
			}
			
		}
		
		return creado;
	}
	
	public void cargarAddDatosUsuario(String nombreUsuario){
		
		String nombre,apellidos,direccion,telefono,email,dni;
		Date fechaNac;
		
		nombre=txtNombre.getText();
		apellidos=txtApellidos.getText();
		direccion=txtDireccion.getText();
		telefono=txtTelefono.getText();
		email=txtEmail.getText();
		dni=txtDni.getText();
		fechaNac=fechaNacimiento.getDate();
		
		try{
			
			PreparedStatement pstmt= conexion.prepareStatement("insert into datosPersonales(dp_dni,dp_nombre,dp_apellidos,dp_direccion,dp_telefono,dp_email,dp_fechanac) "
			+"VALUES (?,?,?,?,?,?,?) where user_codigo in (select user_codigo from usuarios where user_alias=?)");
			pstmt.setString(1, dni);
			pstmt.setString(2, nombre);
			pstmt.setString(3, apellidos);
			pstmt.setString(4, direccion);
			pstmt.setString(5, telefono);
			pstmt.setString(6, email);
			pstmt.setDate(7, (java.sql.Date) fechaNac);
			pstmt.setString(8, nombreUsuario);

			pstmt.executeUpdate();
			
			JOptionPane.showInputDialog(null,	"Datos de usuario añadidos correctamente.", "Añadiendo datos usuario....", 0);
			creado=true;
			pstmt.close();
		}catch(SQLException ex){
			
			JOptionPane.showMessageDialog(null,	"Error en la consulta.", "Creando usuario....", 0);
			creado=false;
		}
	}
}
