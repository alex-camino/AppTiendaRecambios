package clases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;

public class MetodosClase {
	
	public static int conectarUsuario(Connection conexion, String usuario, String password){
	
		
		int id_user=0;
		
		try{
							
			// CODIGO QUE NOS PROTEGE DE LA INYECCION SQL, UTILIZAMOS CONSULTAS PARAMETRIZADAS. LE PASAMOS LOS PARAMETROS NOMBRE Y PASS
			PreparedStatement pstmt= conexion.prepareStatement("select user_codigo from usuarios where user_alias=? and user_pass=md5(?)");
			pstmt.setString(1, usuario);
			pstmt.setString(2, password);
			ResultSet user =pstmt.executeQuery();
						
			
			//Guardamos el codigo del usuario introducido anteriormente.
			while(user.next()){
				
				id_user= user.getInt("user_codigo");
			}
			
			user.close();
			
			pstmt.close();
		
			
		}catch (Exception ex) {

			JOptionPane.showMessageDialog(null,	"Error a la hora de hacer la consulta a la BBDD. "+ ex.getMessage(), "CONECTANDOSE....", 0);
			
		}
		
		return id_user;
		
	}

}
