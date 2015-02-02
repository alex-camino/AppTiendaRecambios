package Tablas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;



public class Usuarios implements java.io.Serializable {

	private Integer userCodigo;
	private String userAlias;
	private String userPass;
	private DatosPersonales datosPersonales;
	private ArrayList<Pedidos> pedidosUsuario;
	private boolean administrador;
	

	public Usuarios() {
	}

	public Usuarios(String userAlias, String userPass, DatosPersonales datosPersonaleses, ArrayList<Pedidos> pedidosUsuario, boolean administrador) {
		this.userAlias = userAlias;
		this.userPass = userPass;
		this.datosPersonales = datosPersonaleses;
		this.pedidosUsuario = pedidosUsuario;
		this.administrador=administrador;
		
	}

	
	public Integer getUserCodigo() {
		return this.userCodigo;
	}

	public void setUserCodigo(Integer userCodigo) {
		this.userCodigo = userCodigo;
	}

	public String getUserAlias() {
		return this.userAlias;
	}

	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}

	public String getUserPass() {
		return this.userPass;
	}

	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}

	public DatosPersonales getDatosPersonales() {
		return this.datosPersonales;
	}

	public void setDatosPersonaleses(DatosPersonales datosPersonales) {
		this.datosPersonales = datosPersonales;
	}

	public ArrayList<Pedidos> getPedidosUsuario() {
		return this.pedidosUsuario;
	}

	public void setPedidoses(ArrayList<Pedidos> pedidosUsuario) {
		this.pedidosUsuario= pedidosUsuario;
	}
	
	public boolean getAdministrador(){
		
		return this.administrador;
	}
	
	public void setAdministrador(boolean administrador){
		
		this.administrador=administrador;
	}
	
	public boolean validarUsuario(Connection conexion){
		
		boolean existe=false;
		
		try{
			
			// CODIGO QUE NOS PROTEGE DE LA INYECCION SQL, UTILIZAMOS CONSULTAS PARAMETRIZADAS. LE PASAMOS LOS PARAMETROS NOMBRE Y PASS
			PreparedStatement pstmt= conexion.prepareStatement("select * from usuarios where user_alias=? and user_pass=md5(?)");
			pstmt.setString(1, this.userAlias);
			pstmt.setString(2, this.userPass);
			ResultSet user =pstmt.executeQuery();
			
			//Guardamos el codigo del usuario introducido anteriormente.
			while(user.next()){
			
				this.userCodigo= user.getInt("user_codigo");
				this.userAlias=user.getString("user_alias");
				this.userPass=user.getString("user_pass");
				this.administrador=user.getBoolean("administrador");
				
				existe=true;
			}
		
			user.close();
		
			pstmt.close();
		}catch(SQLException ex){
			
			JOptionPane.showMessageDialog(null,	"Los datos introducidos son incorrectos.", "Buscando usuario....", 0);
		}
		
					
		
		
		return existe;
	}

}
