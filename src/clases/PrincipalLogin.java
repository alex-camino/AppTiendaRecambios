package clases;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

import Tablas.Piezas;
import Tablas.Usuarios;

public class PrincipalLogin extends JFrame {

	private static Connection conexion;
	private JPanel contentPane;
	private ImageIcon logo = new ImageIcon("/Users/Alex/Eclipse/Proyecto_AplicacionRecambios/recambios.jpg");
	private JTextField txtUsuario;
	private JPasswordField txtPassword;
	
	private String usuario, password;
	
	public static ArrayList<Piezas> carrito= new ArrayList<Piezas>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
				
					PrincipalLogin frame = new PrincipalLogin();
					frame.setVisible(true);
					
				} catch (Exception ex) {
					
					JOptionPane.showMessageDialog(null, ex.getMessage(),"Advertencia",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws SQLException 
	 */
	public PrincipalLogin() throws SQLException {
		
		setTitle("Logueandose...");		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 670, 628);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel cuadroImagen = new JLabel();
		cuadroImagen.setBounds(30, 6, 613, 196);
		contentPane.add(cuadroImagen);
		cuadroImagen.setIcon((Icon) logo);
		
		JLabel lblUsuario = new JLabel("Usuario:");
		lblUsuario.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblUsuario.setBounds(183, 290, 69, 16);
		contentPane.add(lblUsuario);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		lblPassword.setBounds(183, 363, 85, 16);
		contentPane.add(lblPassword);
		
		txtUsuario = new JTextField();
		txtUsuario.setBounds(326, 285, 134, 28);
		contentPane.add(txtUsuario);
		txtUsuario.setColumns(10);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(326, 363, 134, 23);
		contentPane.add(txtPassword);
		
		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cargarCrearUsuario();
			}
		});
		btnCrearUsuario.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnCrearUsuario.setBounds(183, 460, 141, 29);
		contentPane.add(btnCrearUsuario);
		
		JButton btnLogearse = new JButton("Logearse");
		btnLogearse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				cargarLogearse();
				
			}
		});
		btnLogearse.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		btnLogearse.setBounds(343, 461, 117, 29);
		contentPane.add(btnLogearse);
		
			
		
	}
	
	public void cargarLogearse(){
		
		usuario=txtUsuario.getText();
		password=txtPassword.getText();
		
		if(usuario.equals("")){
			
			JOptionPane.showMessageDialog(null,	"El usuario no puede estar vacio.", "CONECTANDOSE....", 0);
		}else if(password.equals("")){
			
			JOptionPane.showMessageDialog(null,	"Tiene que introducir su password.", "CONECTANDOSE....", 0);
		}else{
			
			Usuarios login = new Usuarios();
			
			try{
				
				// CREAMOS LA CONEXION CON LA BASE DE DATOS.
				conexion = DriverManager.getConnection("jdbc:mysql://localhost/bbdd_oscaro", "root", "");
				
				login.setUserAlias(usuario);
				login.setUserPass(password);
				
				
				if(login.validarUsuario(conexion)){
					
					if(login.getAdministrador().equals("N")){
						
						JOptionPane.showMessageDialog(null,	"  Bienvenido a la APP de Oscaro Recambios "+txtUsuario.getText(), "CONECTANDOSE.....", 1);
						Thread.sleep(1000);
						Principal pantallaPrincipal = new Principal(conexion,login, login.comprobarAdmin(conexion));
						pantallaPrincipal.setVisible(true);
						dispose();//Cierro la ventana actual
						
						
					}else{
						
						JOptionPane.showMessageDialog(null,	"  APP de Oscaro Recambios (Modo Administrador) ", "CONECTANDOSE.....", 1);
						Thread.sleep(1000);
						TiendaAdministrador pantallaAdmin = new TiendaAdministrador(conexion, login);
						pantallaAdmin.setVisible(true);
						dispose();//Cierro la ventana actual
					}
					
					
				}else{
					
					JOptionPane.showMessageDialog(null, "El usuario o password no son validos");
				}
				
			}catch (SQLException e) {
				
				JOptionPane.showMessageDialog(null, "Error en la conexión","CONECTANDOSE....",0);
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			
		}
		
	}
	
	
	public void cargarCrearUsuario(){
		
		try{
			
			
			// CREAMOS LA CONEXION CON LA BASE DE DATOS.
			conexion = DriverManager.getConnection("jdbc:mysql://localhost/oscaro", "alex", "1234");
		
			JOptionPane.showMessageDialog(null,"Redireccionándolo al menú de crear usuarios...", "CONECTANDOSE.....", 1);
			Thread.sleep(2000);
			PantallaCrearUsuario pantallaUsuario = new PantallaCrearUsuario(conexion);
			pantallaUsuario.setVisible(true);
			dispose();
				
		}catch (SQLException e) {
			
			JOptionPane.showMessageDialog(null, "Error en la conexión","CONECTANDOSE....",0);
			
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
			
		
		
	}
}