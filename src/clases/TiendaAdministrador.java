package clases;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Tablas.Usuarios;

import java.sql.Connection;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TiendaAdministrador extends JFrame {

	private JPanel contentPane;
	private Usuarios user;
	private Connection conexion;	


	public TiendaAdministrador(final Connection conexion, Usuarios usuario) {
		
		this.conexion=conexion;
		this.user=usuario;
		
		setTitle("Oscaro Recambios (Modo Administrador)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1260, 660);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		JMenu mnMenu = new JMenu();
		mnMenu.setText("Menu");
		menuBar.add(mnMenu);
		
		JMenu mnProductos = new JMenu("Productos");
		mnMenu.add(mnProductos);
		
		JMenuItem mntmAadirPiezas = new JMenuItem("Añadir Piezas");
		mntmAadirPiezas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				contentPane.removeAll();
				addProductos nuevoProducto = new addProductos(contentPane, conexion);
				nuevoProducto.setVisible(true);
				contentPane.add(nuevoProducto);

				SwingUtilities.updateComponentTreeUI(nuevoProducto);
			}
		});
		mnProductos.add(mntmAadirPiezas);
		
		JMenuItem mntmModificarPiezas = new JMenuItem("Modificar Piezas");
		mntmModificarPiezas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				contentPane.removeAll();
				visualizarProductosModificar visualizarProductos = new visualizarProductosModificar(contentPane, conexion);
				visualizarProductos.setVisible(true);
				contentPane.add(visualizarProductos);
				
				SwingUtilities.updateComponentTreeUI(visualizarProductos);
			}
		});
		mnProductos.add(mntmModificarPiezas);
		
		JMenu mnUsuarios = new JMenu("Usuarios");
		mnMenu.add(mnUsuarios);
		
		JMenu mnPedidos = new JMenu("Pedidos");
		mnMenu.add(mnPedidos);
		
		JMenuItem mntmCopiaDeSeguridad = new JMenuItem("Copia de Seguridad");
		mnMenu.add(mntmCopiaDeSeguridad);
		
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mnMenu.add(mntmSalir);
		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
