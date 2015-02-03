package clases;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import java.sql.Connection;
import java.util.ArrayList;

import Tablas.*;



public class Principal extends JFrame {

	private JPanel contentPane;
	private int idUsuario;
	private Connection conexion;	
	
	public static ArrayList<Piezas> carrito = new ArrayList<Piezas>();
	
	public Principal(final Connection conexion, Usuarios usuario, Boolean esAdmin) {
		
		setTitle("Oscaro Recambios");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1260, 660);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		setResizable(false);
		setLocationRelativeTo(null);
				

		Tienda pantallaTienda = new Tienda(contentPane, usuario, conexion, esAdmin);
		pantallaTienda.setVisible(true);
		contentPane.add(pantallaTienda);

		SwingUtilities.updateComponentTreeUI(pantallaTienda);

	}

}
