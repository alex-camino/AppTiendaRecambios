package clases;

import javax.swing.JPanel;

import Tablas.CatEspecifica;
import Tablas.Piezas;
import Tablas.Proveedores;
import Tablas.Usuarios;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;
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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class Tienda extends JPanel {

	private JPanel contentPane;
	private ImageIcon logo = new ImageIcon("/Users/Alex/Eclipse/Proyecto_AplicacionRecambios/logoCarrito.png");
	private Connection conexion;
	private JTextField txtReferencia;
	private JTable tabla;
	private DefaultTableModel modelo;
	private Object[][] filas;
	private String[] columnas={"ID", "Referencia", "Nombre", "Descripci\u00F3n", "Stock", "Precio", "Proveedor", "SubCategoria"};
	private String[] columnasCarrito={"ID", "Referencia", "Nombre", "Descripci\u00F3n", "Cantidad", "Precio Unidad", "Proveedor", "SubCategoria"};
	private JScrollPane scrollPane;
	private JButton btnCuenta;
	private JButton btnPedidos;
	private JButton btnCarrito;
	private JButton btnAddPieza;
	private JButton btnRealizarPedido;
	private JComboBox comboBoxMarcas;
	private JComboBox comboBoxModelos;
	private JComboBox comboBoxTipos;
	private JComboBox comboBoxCategorias;
	private JComboBox comboBoxSubcategorias;
	private boolean esAdmin;
	private JPanel panelAbajo;
	private JPanel panelBotones;
	private JPanel panelAbajoUser;
	private JLabel lblCantProductos;
	private int cantidadPiezas=0;
	
	/**
	 * Create the panel.
	 */
	public Tienda(JPanel contentPane, Usuarios usuario, final Connection conexion, boolean esAdmin) {

		this.contentPane=contentPane;
		this.conexion=conexion;
		this.esAdmin=esAdmin;
		setBounds(100, 100, 1260, 660);
		setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JPanel panelMarca = new JPanel();
		panel.add(panelMarca, BorderLayout.NORTH);
		
		comboBoxMarcas = new JComboBox();
		
		comboBoxModelos = new JComboBox();
		
		comboBoxTipos = new JComboBox();
		
		
		
		// ComboBoxMarcas
		comboBoxMarcas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				comboBoxModelos.removeAllItems();
				comboBoxTipos.removeAllItems();
				obtenerModelos((comboBoxMarcas.getSelectedIndex()+1));
			}
		});
		
		//ComboBoxModelos
		comboBoxModelos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(comboBoxModelos.getSelectedItem()!=null){
					
					comboBoxTipos.removeAllItems();
					obtenerTipos(comboBoxModelos.getSelectedItem().toString());
				}
			}
		});
		//ComboBoxTipos
		comboBoxTipos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		
		JButton btnBuscar = new JButton("Buscar Piezas");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				buscarProductos();
			}
		});
		
		JLabel lblMarca = new JLabel("Elige una marca:");
		lblMarca.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		
		JLabel lblModelo = new JLabel("Elige un modelo:");
		
		JLabel lblEligeUnTipo = new JLabel("Elige un tipo:");
		lblEligeUnTipo.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
		
		JLabel lblCategoria = new JLabel("Categoria:");
		lblCategoria.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		
		comboBoxCategorias = new JComboBox();
		comboBoxCategorias.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				comboBoxSubcategorias.removeAllItems();
				obtenerSubcategorias((comboBoxCategorias.getSelectedIndex()+1));
			}
		});
		
		JLabel lblSubcategoria = new JLabel("Subcategoria:");
		
		comboBoxSubcategorias = new JComboBox();
		GroupLayout gl_panelMarca = new GroupLayout(panelMarca);
		gl_panelMarca.setHorizontalGroup(
			gl_panelMarca.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMarca.createSequentialGroup()
					.addGroup(gl_panelMarca.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelMarca.createSequentialGroup()
							.addGap(38)
							.addComponent(lblMarca)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxMarcas, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblModelo)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboBoxModelos, GroupLayout.PREFERRED_SIZE, 151, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblEligeUnTipo)
							.addGap(29)
							.addComponent(comboBoxTipos, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE))
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
						.addComponent(comboBoxMarcas, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblModelo)
						.addComponent(comboBoxModelos, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEligeUnTipo)
						.addComponent(comboBoxTipos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
		panelCentral.setLayout(new GridLayout(0, 1, 0, 0));
		
		scrollPane = new JScrollPane();
		panelCentral.add(scrollPane);
		
		panelBotones = new JPanel();
		panel.add(panelBotones, BorderLayout.WEST);
		
		btnCuenta = new JButton("Tu cuenta");
		
		btnPedidos = new JButton("Pedidos");
		
		btnCarrito = new JButton("Carrito");
		btnCarrito.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				mostrarProductosCarrito();
			}
		});
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
		
		lblCantProductos = new JLabel("");
		lblCantProductos.setText(Integer.toString(cantidadPiezas));
		JLabel lblProductos = new JLabel("Productos");
		
		JLabel lblReferenciaPieza = new JLabel("Referencia Pieza:");
		
		txtReferencia = new JTextField();
		txtReferencia.setColumns(10);
		
		JButton btnBuscar_1 = new JButton("Buscar");
		btnBuscar_1.setFont(new Font("Lucida Grande", Font.PLAIN, 15));
		
		btnAddPieza = new JButton("Añadir Pieza");
		btnAddPieza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				obtenerPiezaTabla();
			}
		});
		GroupLayout gl_panelDerecha = new GroupLayout(panelDerecha);
		gl_panelDerecha.setHorizontalGroup(
			gl_panelDerecha.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDerecha.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDerecha.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAddPieza, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_panelDerecha.createParallelGroup(Alignment.LEADING)
							.addComponent(lblCantProductos)
							.addGroup(gl_panelDerecha.createSequentialGroup()
								.addComponent(cuadroImagen)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panelDerecha.createParallelGroup(Alignment.LEADING)
									.addComponent(lblProductos)
									.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))))
						.addComponent(btnBuscar_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
						.addComponent(txtReferencia, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblReferenciaPieza, Alignment.TRAILING))
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
					.addGap(50)
					.addComponent(btnAddPieza, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 157, Short.MAX_VALUE)
					.addComponent(lblReferenciaPieza)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtReferencia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnBuscar_1, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addGap(32))
		);
		panelDerecha.setLayout(gl_panelDerecha);
		
		panelAbajo = new JPanel();
		panel.add(panelAbajo, BorderLayout.SOUTH);
		
		JButton btnPedidosUser = new JButton("Pedidos Usuarios");
		btnPedidosUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		JButton btnBDPiezas = new JButton("BBDD_Piezas");
		btnBDPiezas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnBbddusuarios = new JButton("BBDD_Usuarios");
		
		JButton btnTuCuenta = new JButton("Tu cuenta");
		GroupLayout gl_panelAbajo = new GroupLayout(panelAbajo);
		gl_panelAbajo.setHorizontalGroup(
			gl_panelAbajo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelAbajo.createSequentialGroup()
					.addGap(150)
					.addComponent(btnTuCuenta)
					.addGap(83)
					.addComponent(btnPedidosUser, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
					.addGap(92)
					.addComponent(btnBDPiezas)
					.addGap(93)
					.addComponent(btnBbddusuarios)
					.addContainerGap(335, Short.MAX_VALUE))
		);
		gl_panelAbajo.setVerticalGroup(
			gl_panelAbajo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelAbajo.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelAbajo.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnTuCuenta, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
						.addComponent(btnPedidosUser, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBDPiezas, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnBbddusuarios, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panelAbajo.setLayout(gl_panelAbajo);
		
		panelAbajoUser = new JPanel();
		panel.add(panelAbajoUser, BorderLayout.SOUTH);
		
		btnRealizarPedido = new JButton("Realizar Pedido");
		GroupLayout gl_panelAbajoUser = new GroupLayout(panelAbajoUser);
		gl_panelAbajoUser.setHorizontalGroup(
			gl_panelAbajoUser.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelAbajoUser.createSequentialGroup()
					.addContainerGap(1115, Short.MAX_VALUE)
					.addComponent(btnRealizarPedido)
					.addContainerGap())
		);
		gl_panelAbajoUser.setVerticalGroup(
			gl_panelAbajoUser.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelAbajoUser.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnRealizarPedido, GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
					.addContainerGap())
		);
		panelAbajoUser.setLayout(gl_panelAbajoUser);
		
		//mostrarProductos();
		obtenerMarcas();
		obtenerCategorias();
		
		comprobarAdmin();
	}
	
	
	
	
	public void obtenerMarcas(){
		

		try{
			
			PreparedStatement pstmt= conexion.prepareStatement("select * from marcas");
			ResultSet marca =pstmt.executeQuery();
			
			while(marca.next()){
			
				comboBoxMarcas.addItem(marca.getString("mar_nombre"));
			}
		
			marca.close();
		
			pstmt.close();
		}catch(SQLException ex){
			
			JOptionPane.showMessageDialog(null,	"Error en la consulta.", "Buscando marcas....", 0);
		}
		
		
	}
	
	public void obtenerModelos(int codigoMarca){
		
		comboBoxModelos.setEnabled(true);
		
		try{
			
			PreparedStatement pstmt= conexion.prepareStatement("select * from modelos where mar_codigo=?");
			pstmt.setInt(1, codigoMarca);
			ResultSet modelo =pstmt.executeQuery();
			
			while(modelo.next()){
			
				comboBoxModelos.addItem(modelo.getString("mod_nombre"));
			}
		
			modelo.close();
		
			pstmt.close();
		}catch(SQLException ex){
			
			JOptionPane.showMessageDialog(null,	"Error en la consulta.", "Buscando modelos....", 0);
		}

		
	}
	
	public void obtenerTipos(String nombreModelo){
		
		
		comboBoxTipos.setEnabled(true);
		
		try{
			
			
			PreparedStatement pstmt= conexion.prepareStatement("select * from tipos where mod_codigo in (select mod_codigo from modelos where mod_nombre=?)");
			pstmt.setString(1, nombreModelo);
			ResultSet tipo =pstmt.executeQuery();
			
			while(tipo.next()){
			
				comboBoxTipos.addItem(tipo.getString("tip_descripcion"));
			}
		
			tipo.close();
		
			pstmt.close();
		}catch(SQLException ex){
			
			JOptionPane.showMessageDialog(null,	"Error en la consulta.", "Buscando tipos....", 0);
		}
		
		
	}
	
	public void obtenerCategorias(){
		
		//habilitamos el comboBox
		comboBoxCategorias.setEnabled(true);
	
		
		try{
			
		
			PreparedStatement pstmt= conexion.prepareStatement("select * from cat_piezas");
			ResultSet categorias =pstmt.executeQuery();
			
			
			while(categorias.next()){
			
				comboBoxCategorias.addItem(categorias.getString("cat_nombre"));
			}
		
			categorias.close();
		
			pstmt.close();
		}catch(SQLException ex){
			
			JOptionPane.showMessageDialog(null,	"Error en la consulta.", "Buscando categorias....", 0);
		}
		
		
	}
	
	public void obtenerSubcategorias(int codCategoria){
		
		//habilitamos el comboBox
		comboBoxSubcategorias.setEnabled(true);
	
		
		try{
			
			
			PreparedStatement pstmt= conexion.prepareStatement("select * from cat_especifica where cat_codigo=?");
			pstmt.setInt(1, codCategoria);
			ResultSet subCategoria =pstmt.executeQuery();
			
	
			while(subCategoria.next()){
			
				comboBoxSubcategorias.addItem(subCategoria.getString("cat_esp_nombre"));
			}
		
			subCategoria.close();
		
			pstmt.close();
		}catch(SQLException ex){
			
			JOptionPane.showMessageDialog(null,	"Error en la consulta.", "Buscando categorias....", 0);
		}
		
		
	}
	
	public void buscarProductos(){
		
		if(comboBoxMarcas.getSelectedItem()!=null&&comboBoxModelos.getSelectedItem()!=null&&comboBoxTipos.getSelectedItem()!=null
		&& comboBoxCategorias.getSelectedItem()!=null&&comboBoxSubcategorias.getSelectedItem()!=null){
			
			mostrarProductos(comboBoxTipos.getSelectedItem().toString(), comboBoxSubcategorias.getSelectedItem().toString());
		}else{
			
			JOptionPane.showMessageDialog(null,	"Debes seleccionar todos los campos, para poder buscar las piezas.", "Buscando piezas....", 0);
		}
	}
	
	public void mostrarProductos(String nombreTipo, String nombreSubcategoria){
		
		boolean encontrado;
		
		tabla = new JTable();
		
		modelo = new DefaultTableModel(filas, columnas);
		
		encontrado=rellenarProductos(modelo,nombreTipo, nombreSubcategoria);
		
		if(encontrado){
			
			tabla.setModel(modelo);
			scrollPane.setViewportView(tabla);
			
	
			/* Para que no se puedan modificar los campos
			 * Aporte: David.
			 */
			for (int j = 0; j < tabla.getColumnCount(); j++) {
	
				Class<?> col_class = tabla.getColumnClass(j);
				tabla.setDefaultEditor(col_class, null); // remove editor
	
			}
			
			
		}else{
			
			JOptionPane.showMessageDialog(null, "No existen piezas","Advertencia",JOptionPane.WARNING_MESSAGE);
		}
		
		tabla.setModel(modelo);
		scrollPane.setViewportView(tabla);
		//Habilito los botones
		btnCarrito.setEnabled(true);
		btnAddPieza.setEnabled(true);

		
	}
	
	public  boolean rellenarProductos(DefaultTableModel modelo,String nombreTipo, String nombreSubcategoria){
		
		boolean encontrado=false;
		int codigoTipo=0, codigoSubcategoria=0;
		
		try{
			
			//Buscando el codido del tipo
			PreparedStatement pstmt= conexion.prepareStatement("select * from tipos where tip_descripcion=?");
			pstmt.setString(1, nombreTipo);
			ResultSet rs =pstmt.executeQuery();
			
			while(rs.next()){
			
				codigoTipo=rs.getInt("tip_codigo");
			}
		
			rs.close();
			pstmt.close();
			
			//Buscando el codigo de la subcategoria
			pstmt= conexion.prepareStatement("select * from cat_especifica where cat_esp_nombre=?");
			pstmt.setString(1, nombreSubcategoria);
			rs =pstmt.executeQuery();
			
			while(rs.next()){
			
				codigoSubcategoria=rs.getInt("cat_esp_codigo");
			}
			
			rs.close();
			pstmt.close();
			
			//buscando las piezas a partir del tipo y la subcategoria
			pstmt= conexion.prepareStatement("select * from piezas where cat_esp_codigo=? and pie_codigo in (select pie_codigo from piezas_tipos where tip_codigo=?)");
			pstmt.setInt(1, codigoSubcategoria);
			pstmt.setInt(2, codigoTipo);
			rs =pstmt.executeQuery();
			
			while(rs.next()){
			
				Object[] fila = new Object[8];
			
				fila[0]=rs.getInt("pie_codigo");
				fila[1]=rs.getString("pie_referencia");
				fila[2]=rs.getString("pie_nombre");
				fila[3]=rs.getString("pie_descripcion");
				fila[4]=rs.getInt("pie_cantidad");
				fila[5]=rs.getFloat("pie_precio");
				
				Proveedores nuevoProveedor = new Proveedores();
				
				nuevoProveedor.setProCodigo(rs.getInt("pro_codigo"));
			    obtenerInfoProveedor(nuevoProveedor);
			    
				fila[6]=nuevoProveedor.getProNombre();
				fila[7]=nombreSubcategoria;
						
				modelo.addRow(fila);
				
				encontrado=true;
			}
			
			rs.close();
			pstmt.close();
			
		}catch(SQLException ex){
			
			JOptionPane.showMessageDialog(null,	"Error en la consulta.", "Buscando categorias....", 0);
		}	
		
				
		return encontrado;
	}
	
	public void comprobarAdmin(){
		
		if(this.esAdmin){
			
			//System.out.println("HOLA HOLA HOLA");
			panelBotones.setVisible(false);
			panelAbajoUser.setVisible(false);

		}else{
			
			//System.out.println("ABURRIDOSS");
			panelAbajo.setVisible(false);
			btnCarrito.setEnabled(false);
			btnAddPieza.setEnabled(false);
			panelAbajoUser.setVisible(true);
			btnRealizarPedido.setEnabled(false);
			
		}
	}
	
	public void obtenerPiezaTabla(){
		
		
		int opcion=tabla.getSelectedRow();
		int codPieza, cantidadPiezas=0;
		float precio;
		String referencia, nombre, descripcion, proveedor, subcategoria, cantidad;
		
		if(opcion==-1){
			
			JOptionPane.showMessageDialog(null, "Debe seleccionar un producto para poder añadirlo.","Advertencia",JOptionPane.WARNING_MESSAGE);
		}else{
			
			
			Proveedores nuevoProveedor= new Proveedores();
			CatEspecifica nuevaSubcat = new CatEspecifica();
			Piezas pieza1 = new Piezas();
			
			//Obtengo la información de la fila seleccionada.
			codPieza=(int) tabla.getValueAt(opcion, 0);
			referencia=tabla.getValueAt(opcion, 1).toString();
			nombre=tabla.getValueAt(opcion, 2).toString();
			descripcion=tabla.getValueAt(opcion, 3).toString();	
			precio=(float) tabla.getValueAt(opcion, 5);
			proveedor=tabla.getValueAt(opcion, 6).toString();
			subcategoria=tabla.getValueAt(opcion, 7).toString();
					
			boolean error=false;
			
			
			do{
				
				try{
					
					cantidad = JOptionPane.showInputDialog(null, "Cuantas unidades quiere?", "Añadiendo pieza al carrito.....", 3);
					
					cantidadPiezas=Integer.parseInt(cantidad);
					
					error=false;
					
				}catch(NumberFormatException ex){
					
					JOptionPane.showMessageDialog(null,	"Debe introducir numeros.", "Añadiendo pieza al carrito.....", 0);
					error=true;
				}
				
			}while(error);
			
			nuevoProveedor.setProNombre(proveedor);
			obtenerInfoProveedor(nuevoProveedor);
			nuevaSubcat.setCatEspNombre(subcategoria);
			obtenerInfoSubcategorias(nuevaSubcat);
			
			
			pieza1.setPieCodigo(codPieza);
			pieza1.setPieReferencia(referencia);
			pieza1.setPieNombre(nombre);
			pieza1.setPieDescripcion(descripcion);
			pieza1.setPieCantidad(cantidadPiezas);
			pieza1.setPiePrecio(precio);
			pieza1.setProveedores(nuevoProveedor);
			pieza1.setCatEspecifica(nuevaSubcat);
			
			
			//PIEZA AÑADIDA AL CARRITO
			PrincipalLogin.carrito.add(pieza1);
			this.cantidadPiezas++;
			lblCantProductos.setText(Integer.toString(this.cantidadPiezas));
			
			JOptionPane.showMessageDialog(null,"Pieza añadida al carrito.", "Añadiendo Piezas al carrito.....", 1);
		}
	}
	
	public void obtenerInfoProveedor(Proveedores proveedor){
		
		PreparedStatement pstmt;
		try{
			
			if(proveedor.getProCodigo()!=null){
				//Buscando el codido del tipo
				pstmt= conexion.prepareStatement("select * from proveedores where pro_codigo=?");
				pstmt.setInt(1, proveedor.getProCodigo());
			}else{
				
				//Buscando el codido del tipo
				pstmt= conexion.prepareStatement("select * from proveedores where pro_nombre=?");
				pstmt.setString(1, proveedor.getProNombre());
			}
			
			ResultSet rs =pstmt.executeQuery();
			
			while(rs.next()){
			
				proveedor.setProNombre(rs.getString("pro_nombre"));
				proveedor.setProTelefono(rs.getString("pro_telefono"));
				proveedor.setProDireccion(rs.getString("pro_direccion"));
				proveedor.setProNumcuenta(rs.getString("pro_numcuenta"));
			}
		
			rs.close();
			pstmt.close();
			
			
			
		}catch(SQLException ex){
			
			JOptionPane.showMessageDialog(null,	"Error en la consulta.", "Buscando proveedores....", 0);
		}	

	}
	
	public void obtenerInfoSubcategorias(CatEspecifica subCategoria){
		
		PreparedStatement pstmt;
		try{
			
			if(subCategoria.getCatEspCodigo()!=null){
				
				pstmt= conexion.prepareStatement("select * from cat_especifica where cat_esp_codigo=?");
				pstmt.setInt(1, subCategoria.getCatEspCodigo());
			}else{
				
				pstmt= conexion.prepareStatement("select * from cat_especifica where cat_esp_nombre=?");
				pstmt.setString(1, subCategoria.getCatEspNombre());
			}
			
			
			ResultSet rs =pstmt.executeQuery();
			
			while(rs.next()){
			
				subCategoria.setCatEspCodigo(rs.getInt("cat_esp_codigo"));
				subCategoria.setCatEspNombre(rs.getString("cat_esp_nombre"));
				
			}
		
			rs.close();
			pstmt.close();
			
			
			
		}catch(SQLException ex){
			
			JOptionPane.showMessageDialog(null,	"Error en la consulta.", "Buscando subcategorias....", 0);
		}	

	}
	
	
	
	//////////////////////////////////////////// CARRITO ////////////////////////////////////////////////////
	
	public void mostrarProductosCarrito(){
		
		boolean encontrado;
		
		tabla = new JTable();
		
		modelo = new DefaultTableModel(this.filas, this.columnasCarrito);
		
		if(PrincipalLogin.carrito.size()!=0){

			
				for(int i=0;i<PrincipalLogin.carrito.size();i++){
					
					Object[] fila = new Object[8];
					
					fila[0]=PrincipalLogin.carrito.get(i).getPieCodigo();
					fila[1]=PrincipalLogin.carrito.get(i).getPieReferencia();
					fila[2]=PrincipalLogin.carrito.get(i).getPieNombre();
					fila[3]=PrincipalLogin.carrito.get(i).getPieDescripcion();
					fila[4]=PrincipalLogin.carrito.get(i).getPieCantidad();
					fila[5]=PrincipalLogin.carrito.get(i).getPiePrecio();	    
					fila[6]=PrincipalLogin.carrito.get(i).getProveedores().getProNombre();
					fila[7]=PrincipalLogin.carrito.get(i).getCatEspecifica().getCatEspNombre();
							
					modelo.addRow(fila);
				}
				
				
					
				tabla.setModel(modelo);
				scrollPane.setViewportView(tabla);
				
		
				/* Para que no se puedan modificar los campos
				 * Aporte: David.
				 */
				for (int j = 0; j < tabla.getColumnCount(); j++) {
		
					Class<?> col_class = tabla.getColumnClass(j);
					tabla.setDefaultEditor(col_class, null); // remove editor
		
				}
			
		}else{
			
			JOptionPane.showMessageDialog(null, "No existe piezas en el carrito","Advertencia",JOptionPane.WARNING_MESSAGE);
		}
		
		tabla.setModel(modelo);
		scrollPane.setViewportView(tabla);
		btnRealizarPedido.setEnabled(true);

		
	}
	
	
}