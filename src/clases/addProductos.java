package clases;

import Tablas.CatPiezas;
import Tablas.CatEspecifica;
import Tablas.Proveedores;
import Tablas.Piezas;
import Tablas.Usuarios;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JTextPane;

import java.awt.Font;

import javax.swing.JTabbedPane;

public class addProductos extends JPanel {

	private JPanel contentPane;
	private Connection conexion;
	private JComboBox comboBoxSubcategorias, comboBoxProveedores;
	private JTextField referencia;
	private JTextField nombrePieza;
	private JTextPane descripcionPieza;
	private JTextField cantidad;
	private JTextField precioPieza;
	

	

	/**
	 * Create the frame.
	 */
	public addProductos(JPanel contentPane, final Connection conexion) {
		
		this.contentPane=contentPane;
		this.conexion=conexion;		
	
		
		setBounds(100, 100, 1260, 660);
		setLayout(null);
		
		JLabel lblModificarPieza = new JLabel("Añadir Pieza ");
		lblModificarPieza.setBounds(459, 27, 310, 82);
		lblModificarPieza.setFont(new Font("Lucida Handwriting", Font.BOLD | Font.ITALIC, 37));
		add(lblModificarPieza);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(141, 105, 941, 510);
		add(tabbedPane);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("New tab", null, panel, null);
		panel.setLayout(null);
		
		comboBoxSubcategorias = new JComboBox();
		comboBoxSubcategorias.setBounds(179, 51, 169, 27);
		panel.add(comboBoxSubcategorias);
		
		JLabel lblReferencia = new JLabel("Referencia");
		lblReferencia.setBounds(42, 118, 65, 16);
		panel.add(lblReferencia);
		
		referencia = new JTextField();
		referencia.setBounds(179, 112, 169, 28);
		panel.add(referencia);
		referencia.setColumns(10);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setBounds(42, 192, 50, 16);
		panel.add(lblNombre);
		
		nombrePieza = new JTextField();
		nombrePieza.setBounds(179, 186, 169, 28);
		panel.add(nombrePieza);
		nombrePieza.setColumns(10);
		
		JLabel lblDescripcin = new JLabel("Descripción");
		lblDescripcin.setBounds(42, 251, 75, 16);
		panel.add(lblDescripcin);
		
		descripcionPieza = new JTextPane();
		descripcionPieza.setBounds(42, 279, 306, 139);
		panel.add(descripcionPieza);
		
		
		JLabel lblCantidad = new JLabel("Cantidad");
		lblCantidad.setBounds(511, 118, 56, 16);
		panel.add(lblCantidad);
		
		JLabel lblPrecio = new JLabel("Precio");
		lblPrecio.setBounds(511, 192, 38, 16);
		panel.add(lblPrecio);
		
		cantidad = new JTextField();
		cantidad.setBounds(635, 112, 173, 28);
		panel.add(cantidad);
		cantidad.setColumns(10);
		
		JLabel lblSubcategorias = new JLabel("SubCategorias");
		lblSubcategorias.setBounds(42, 55, 90, 16);
		panel.add(lblSubcategorias);
		
		precioPieza = new JTextField();
		precioPieza.setBounds(635, 186, 173, 28);
		panel.add(precioPieza);
		precioPieza.setColumns(10);
		
		JButton btnAddProducto = new JButton("Añadir Producto");
		btnAddProducto.setBounds(507, 330, 163, 44);
		panel.add(btnAddProducto);
		
		JLabel lblProveedor = new JLabel("Proveedor");
		lblProveedor.setBounds(511, 55, 62, 16);
		panel.add(lblProveedor);
		
		comboBoxProveedores = new JComboBox();
		comboBoxProveedores.setBounds(629, 51, 179, 27);
		panel.add(comboBoxProveedores);
		
		JButton btnVolver = new JButton("Volver ");
		btnVolver.setBounds(721, 330, 87, 44);
		panel.add(btnVolver);
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//cargarVolver();
				
				
			}
		});
		btnAddProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				addProductoBBDD();
			}

		});
		
		
		//Rellenamos los combobox
		obtenerSubcategorias();
		obtenerProveedores();

	}
	
		
		
	///////////////////////////////////////////////////////// METODOS ////////////////////////////////////////////////////////
	
	public void obtenerSubcategorias(){
		
		
		try{
			
			
			PreparedStatement pstmt= conexion.prepareStatement("select * from cat_especifica");
			ResultSet subCategoria =pstmt.executeQuery();
			
	
			while(subCategoria.next()){
			
				comboBoxSubcategorias.addItem(subCategoria.getString("cat_esp_nombre"));
			}
		
			subCategoria.close();
			pstmt.close();
			
		}catch(SQLException ex){
			
			JOptionPane.showMessageDialog(null,	"Error en la consulta.", "Buscando subcategorias....", 0);
		}
		
		
	}
	
	public void obtenerProveedores(){
		
		try{
			
			
			PreparedStatement pstmt= conexion.prepareStatement("select * from proveedores");
			ResultSet proveedores =pstmt.executeQuery();
			
	
			while(proveedores.next()){
			
				comboBoxProveedores.addItem(proveedores.getString("pro_nombre"));
			}
		
			proveedores.close();
			pstmt.close();
			
		}catch(SQLException ex){
			
			JOptionPane.showMessageDialog(null,	"Error en la consulta.", "Buscando proveedores....", 0);
		}
		
	}
	
	public void addProductoBBDD() {
		
		
		int cantidadPiezas=0;
		float precio=0;
		boolean error=false;
			
		
		if(referencia.getText().equals("")||nombrePieza.getText().equals("")||descripcionPieza.getText().equals("")
		  ||cantidad.getText().equals("")||precioPieza.getText().equals("")){
			
		
			JOptionPane.showMessageDialog(null, "Es necesario rellenar todos los campos para poder crear la pieza.", "CREANDO UNA NUEVA PIEZA", 1);

			
		}else{
			
			

			try{
				
				
				
				cantidadPiezas=Integer.parseInt(cantidad.getText());
				precio=Float.parseFloat(precioPieza.getText());
				
				
			}catch(NumberFormatException ex){
				
				JOptionPane.showMessageDialog(null,	"Debe introducir numeros en los campos numericos.", "Añadiendo pieza al carrito.....", 0);
				error=true;
			}
			
			if(!error){
				
				try{
					
					PreparedStatement pstmt= conexion.prepareStatement("insert into piezas(pie_referencia, pie_nombre, pie_descripcion, pie_cantidad,"
					+"pie_precio, pro_codigo, cat_esp_codigo) values(?,?,?,?,?,?,?)");
					pstmt.setString(1, referencia.getText());
					pstmt.setString(2, nombrePieza.getText());
					pstmt.setString(3, descripcionPieza.getText());
					pstmt.setInt(4, cantidadPiezas);
					pstmt.setFloat(5, precio);
					pstmt.setInt(6, (comboBoxProveedores.getSelectedIndex()+1));
					pstmt.setInt(7, (comboBoxSubcategorias.getSelectedIndex()+1));
					pstmt.executeUpdate();
				
					pstmt.close();
					

					pstmt = conexion.prepareStatement("insert into proveedores_piezas(pro_codigo,pie_codigo) "
					+"values("+(comboBoxProveedores.getSelectedIndex()+1)+",(select pie_codigo from piezas where pie_referencia='"+referencia.getText()+"'))");
					pstmt.executeUpdate();
					
					pstmt.close();
					
					JOptionPane.showMessageDialog(null, "PIEZA AÑADIDA CORRECTAMENTE.", "MODIFICANDO PIEZA", 1);
					
				}catch(SQLException ex){
					
					JOptionPane.showMessageDialog(null,	"Error al insertar la pieza.", "Añadiendo piezas....", 0);
				}
				
			}
			
		}
			
	}
	
}
