package clases;

import javax.swing.JPanel;

import Tablas.Usuarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;

public class Tienda extends JPanel {

	private JPanel contentPane;
	private ImageIcon logo = new ImageIcon("/Users/Alex/Eclipse/Proyecto_AplicacionRecambios/logoCarrito.png");
	private Connection conexion;
	private JTextField txtReferencia;
	private JTable tabla;
	private DefaultTableModel modelo;
	private Object[][] filas;
	private String[] columnas={"ID", "Referencia", "Nombre", "Descripci\u00F3n", "Stock", "Precio", "Proveedor", "SubCategoria"};
	JScrollPane scrollPane;
	/**
	 * Create the panel.
	 */
	public Tienda(JPanel contentPane, Usuarios usuario, final Connection conexion) {

		this.contentPane=contentPane;
		this.conexion=conexion;

		setBounds(100, 100, 1260, 660);
		setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelMarca = new JPanel();
		panel.add(panelMarca, BorderLayout.NORTH);
		
		JComboBox comboBoxMarca = new JComboBox();
		
		JComboBox comboBoxModelos = new JComboBox();
		
		JButton btnBuscar = new JButton("Buscar Piezas");
		
		JLabel lblMarca = new JLabel("Elige una marca:");
		lblMarca.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		
		JLabel lblModelo = new JLabel("Elige un modelo:");
		
		JLabel lblEligeUnTipo = new JLabel("Elige un tipo:");
		lblEligeUnTipo.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		
		JComboBox comboBox = new JComboBox();
		
		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		
		JComboBox comboBoxCategorias = new JComboBox();
		
		JLabel lblSubcategoria = new JLabel("Subcategoria:");
		
		JComboBox comboBoxSubcategorias = new JComboBox();
		GroupLayout gl_panelMarca = new GroupLayout(panelMarca);
		gl_panelMarca.setHorizontalGroup(
			gl_panelMarca.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMarca.createSequentialGroup()
					.addGroup(gl_panelMarca.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelMarca.createSequentialGroup()
							.addGap(38)
							.addComponent(lblMarca)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxMarca, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblModelo)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxModelos, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblEligeUnTipo)
							.addGap(29)
							.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelMarca.createSequentialGroup()
							.addGap(173)
							.addComponent(lblCategoria)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxCategorias, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
							.addGap(49)
							.addComponent(lblSubcategoria)
							.addGap(18)
							.addComponent(comboBoxSubcategorias, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
							.addGap(144)
							.addComponent(btnBuscar, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(182, Short.MAX_VALUE))
		);
		gl_panelMarca.setVerticalGroup(
			gl_panelMarca.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelMarca.createSequentialGroup()
					.addContainerGap(10, Short.MAX_VALUE)
					.addGroup(gl_panelMarca.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMarca)
						.addComponent(comboBoxMarca, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblModelo)
						.addComponent(comboBoxModelos, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEligeUnTipo)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
					.addGroup(gl_panelMarca.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCategoria)
						.addComponent(comboBoxCategorias, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSubcategoria)
						.addComponent(comboBoxSubcategorias, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBuscar))
					.addContainerGap())
		);
		panelMarca.setLayout(gl_panelMarca);
		
		JPanel panelCentral = new JPanel();
		panel.add(panelCentral, BorderLayout.CENTER);
		
		scrollPane = new JScrollPane();
		panelCentral.add(scrollPane);
		
		JPanel panelBotones = new JPanel();
		panel.add(panelBotones, BorderLayout.WEST);
		
		JButton btnCuenta = new JButton("Tu cuenta");
		
		JButton btnPedidos = new JButton("Pedidos");
		
		JButton btnCarrito = new JButton("Carrito");
		GroupLayout gl_panelBotones = new GroupLayout(panelBotones);
		gl_panelBotones.setHorizontalGroup(
			gl_panelBotones.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelBotones.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelBotones.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnPedidos, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnCuenta, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnCarrito, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panelBotones.setVerticalGroup(
			gl_panelBotones.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelBotones.createSequentialGroup()
					.addGap(95)
					.addComponent(btnCuenta, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(49)
					.addComponent(btnPedidos, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addGap(39)
					.addComponent(btnCarrito, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(199, Short.MAX_VALUE))
		);
		panelBotones.setLayout(gl_panelBotones);
		
		JPanel panelDerecha = new JPanel();
		panel.add(panelDerecha, BorderLayout.EAST);
		
		JLabel lblNewLabel = new JLabel("Carrito");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		
		JLabel cuadroImagen = new JLabel("");
		cuadroImagen.setIcon((Icon) logo);
		
		JLabel lblCantProductos = new JLabel("");
		
		JLabel lblProductos = new JLabel("Productos");
		
		JLabel lblReferenciaPieza = new JLabel("Referencia Pieza:");
		
		txtReferencia = new JTextField();
		txtReferencia.setColumns(10);
		
		JButton btnBuscar_1 = new JButton("Buscar");
		btnBuscar_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		GroupLayout gl_panelDerecha = new GroupLayout(panelDerecha);
		gl_panelDerecha.setHorizontalGroup(
			gl_panelDerecha.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDerecha.createSequentialGroup()
					.addGroup(gl_panelDerecha.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_panelDerecha.createSequentialGroup()
							.addContainerGap()
							.addComponent(btnBuscar_1, GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_panelDerecha.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panelDerecha.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCantProductos)
								.addGroup(gl_panelDerecha.createSequentialGroup()
									.addComponent(cuadroImagen)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panelDerecha.createParallelGroup(Alignment.LEADING)
										.addComponent(lblProductos)
										.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)))
								.addComponent(lblReferenciaPieza)))
						.addComponent(txtReferencia, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panelDerecha.setVerticalGroup(
			gl_panelDerecha.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDerecha.createSequentialGroup()
					.addGroup(gl_panelDerecha.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelDerecha.createSequentialGroup()
							.addContainerGap()
							.addComponent(cuadroImagen))
						.addGroup(gl_panelDerecha.createSequentialGroup()
							.addGap(18)
							.addComponent(lblNewLabel)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelDerecha.createParallelGroup(Alignment.LEADING)
						.addComponent(lblCantProductos)
						.addComponent(lblProductos))
					.addGap(112)
					.addComponent(lblReferenciaPieza)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtReferencia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnBuscar_1, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(240, Short.MAX_VALUE))
		);
		panelDerecha.setLayout(gl_panelDerecha);
		
		mostrarProductos();
	}
	
	public void mostrarProductos(){
		
		boolean encontrado;
		
		tabla = new JTable();
		
		modelo = new DefaultTableModel(filas, columnas);
		
		tabla.setModel(modelo);
		scrollPane.setViewportView(tabla);
		
		
	}
}
