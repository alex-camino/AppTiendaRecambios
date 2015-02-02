package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import com.mysql.jdbc.Statement;

public class Metodos {

	
	public static void crearUsuario(Statement st, Connection conexion){
		
		//VARIABLES.
		String nombre,pass="",pass2="";
		int okUser=0;
		boolean error;
		
		
		//Bucle que se repite hasta que no se cree correctamente el usuario.
		do{
			error=false;
							
			//Atraves de la interfaz JOptionPane se introducira el nombre.
			nombre = JOptionPane.showInputDialog(null, "Introduzca el alias...", "CREANDO NUEVO USUARIO", 3);
			
			
			//En caso de dar un error al consultar a la base de datos, nos saltara la Excepcion.
			try{
				
				
				
			/* Consultamos a la BBDD si el alias introducido existe.
			 * Utilizamos consultas parametrizadas, de esa forma nos evitamos la inyeccion sql, el unico parametro que le pasamos es NOMBRE*/
				
				PreparedStatement pstmt= conexion.prepareStatement("select user_codigo from usuarios where user_alias=?");
				pstmt.setString(1, nombre);
				ResultSet user =pstmt.executeQuery();				

				
				while (user.next()){
					
					okUser=1;
				}
				
				user.close(); //Cerramos el objeto de ResultSet
				pstmt.clearParameters();
				
				if(okUser==0){
					
					do{
						
						
						//Borramo el objeto de la clase JPasswordFiel, para resetear el cuadro.
						Main.passwordField.setText(null);
						//Introducir el pass, no es visible.
						if (JOptionPane.showOptionDialog(null, Main.pswEnc, "INTRODUZCA SU PASSWORD",
								JOptionPane.YES_NO_OPTION,
								JOptionPane.QUESTION_MESSAGE, null, Main.opciones, Main.pswEnc) == JOptionPane.YES_OPTION) {
							
							pass = Main.passwordField.getText().toString();
						}
						
						
						//Llamada al metodo que comprueba el password. Si devuelve FALSE habra que volver a introducirlo
						if(!comprobarPassword(pass)){
							
							JOptionPane.showMessageDialog(null, "ERROR AL INTRODUCIR LA CONTRASE�A\n"
							                                   +"EL PASSWORD DEBE CONTENER: \n"
							                                   +"- ENTRE 8 Y 15 CARACTERES\n"
							                                   +"- MAYUSCULAS\n"
							                                   +"- MINUSCULAS\n"
							                                   +"- NUMEROS\n"
							                                   +"- CARACTERES ESPECIALES.\n","CREANDO NUEVO USUARIO", 0);
						}
					}while(!comprobarPassword(pass));
					
					
					
					
					//Borramo el objeto de la clase JPasswordFiel, para resetear el cuadro.
					Main.passwordField.setText(null);
					//Introducir el pass, no es visible.
					if (JOptionPane.showOptionDialog(null, Main.pswEnc, "INTRODUZCA DE NUEVO SU PASSWORD",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, Main.opciones, Main.pswEnc) == JOptionPane.YES_OPTION) {
						
						pass2 = Main.passwordField.getText().toString();
					}
					
					
					//Si los pass coinciden creamos el usuario.
					if(pass.equals(pass2)){
									
						/* El insert del usuario tambien lo realizamos con consultas paremtrizadas, nos evitamos cualquier posible inyeccion SQL
						 * En este caso le pasamos dos parametros el NOMBRE y el PASS.
						 * */
						pstmt= conexion.prepareStatement("insert into usuarios(user_alias,user_pass) VALUES (?, md5(?))");
						pstmt.setString(1, nombre);
						pstmt.setString(2, pass);
						pstmt.executeUpdate();
						
						
						JOptionPane.showMessageDialog(null, "USUARIO CREADO CORRECTAMENTE.", "CREANDO NUEVO USUARIO", 1);
						
					}else{
						
						JOptionPane.showMessageDialog(null, "LOS PASSWORD NO COINCIDEN.", "CREANDO NUEVO USUARIO", 0);
					}
					
				}else{
					
					JOptionPane.showMessageDialog(null, "Ya existe ese alias. PruebE con otro.");
					error=true;			//Si ya existe el alias muestra mensaje y se vuelve al principio.
				}
				
				pstmt.close(); //Cierro el objeto del statement.
			}catch (Exception ex) {


				System.err.println("ERROR AL HACER LA CONSULTA A LA BASE DE DATOS SOBRE EL ALIAS.");
				System.err.println(ex.getMessage());
			}
			
			
		}while(error);
		
		
		System.out.println("Volviendo al menu principal.");
	
		
	}
	
	
	public static void conectarUsuario(Statement st, Connection conexion){
	
		
		//Variables para loguearse.
		String nombre,pass="";
		int id_user=0;
		
		//Borramo el objeto de la clase JPasswordFiel, para resetear el cuadro.
		Main.passwordField.setText(null);
		
		try{
					
					
			//Atraves de la interfaz JOptionPane se introducira el nombre.
			nombre = JOptionPane.showInputDialog(null, "Introduzca su Alias de Usuario.", "CONECTANDOSE.....", 3);
			
			
			//Introducir el pass, no es visible.
			if (JOptionPane.showOptionDialog(null, Main.pswEnc, "INTRODUZCA SU PASSWORD",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, Main.opciones, Main.pswEnc) == JOptionPane.YES_OPTION) {
				
				pass = Main.passwordField.getText().toString();
			}
			
			
			// CODIGO QUE NOS PROTEGE DE LA INYECCION SQL, UTILIZAMOS CONSULTAS PARAMETRIZADAS. LE PASAMOS LOS PARAMETROS NOMBRE Y PASS
			PreparedStatement pstmt= conexion.prepareStatement("select user_codigo from usuarios where user_alias=? and user_pass=md5(?)");
			pstmt.setString(1, nombre);
			pstmt.setString(2, pass);
			ResultSet user =pstmt.executeQuery();
			
			/* Si no quisiese utilizar consultas parametrizadas pondria lo siguiente:
			 * 
			 * ResultSet user= st.executeQuery("select user_codigo from usuarios where user_alias='"+nombre+"' and user_pass=md5('"+pass+"')");*/
			
			
			
			//Guardamos el codigo del usuario introducido anteriormente.
			while(user.next()){
				
				id_user= user.getInt("user_codigo");
			}
			
			user.close();
			
			pstmt.close();
			
			//Si id_user tiene un valor distinto a 0, entraremos en la Aplicacion.
			if(id_user!=0){
				
				JOptionPane.showMessageDialog(null,	nombre+"  BIENVENIDO A LA APP DE OSCARO RECAMBIOS", "CONECTANDOSE.....", 1);
				
				System.out.println("");
				
				//Borramos todas los ArrayList que va a usar el usuario, 
				Main.carrito.clear();
				Main.cantidad.clear();
				
				
				//Llamada al menu del usuario
				Main.menuUsuario(st,id_user);
				
			}else{
				
				JOptionPane.showMessageDialog(null,	"Los datos introducidos son incorrectos.", "CONECTANDOSE....", 0);
			}
			
		}catch (Exception ex) {


			System.err.println("Error a la hora de hacer la consulta a la BBDD");	
			System.err.println(ex.getMessage());
		}
		
		
		
	}
	
	
	public static void eliminarUsuario(Statement st, Connection conexion){
		
		
		//Variables para loguearse.
		String nombre,pass="", verificar, alias="";
		int id_user=0, cant=-1;
		
		//Borramo el objeto de la clase JPasswordFiel, para resetear el cuadro.
		Main.passwordField.setText(null);
				
		try{
			
			
						
			//Atraves de la interfaz JOptionPane se introducira el nombre.
			nombre = JOptionPane.showInputDialog(null, "Introduzca su nombre de Usuario.", "ELIMINANDO USUARIO", 3);
			
			
			PreparedStatement pstmt= conexion.prepareStatement("select user_alias from usuarios where user_alias=?");
			pstmt.setString(1, nombre);
			ResultSet user =pstmt.executeQuery();				

			
			while (user.next()){
				
				alias=user.getObject("user_alias").toString();
			}
			
			user.close(); //Cerramos el objeto de ResultSet
			pstmt.clearParameters();//Reseteamos los parametros del Statement
			
			
			if(!alias.equalsIgnoreCase("")){
				
				
					//Introducir el pass no es visible
					if (JOptionPane.showOptionDialog(null, Main.pswEnc, "INTRODUZCA SU PASSWORD",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE, null, Main.opciones, Main.pswEnc) == JOptionPane.YES_OPTION) {
						
						pass = Main.passwordField.getText().toString();
					}
					
					
					// CODIGO QUE NOS PROTEGE DE LA INYECCION SQL, UTILIZAMOS CONSULTAS PARAMETRIZADAS. LE PASAMOS LOS PARAMETROS NOMBRE Y PASS
					pstmt= conexion.prepareStatement("select user_codigo from usuarios where user_alias=? and user_pass=md5(?)");
					pstmt.setString(1, nombre);
					pstmt.setString(2, pass);
					user =pstmt.executeQuery();
					
					//Guardamos el codigo del usuario introducido anteriormente.
					while(user.next()){
						
						id_user= user.getInt("user_codigo");
					}
					
					user.close();
					
					
					//Si id_user tiene un valor distinto a 0, entraremos en la Aplicacion.
					if(id_user!=0){
						
						
							do{
								
								verificar= JOptionPane.showInputDialog(null, "Esta seguro que desea borrar su cuenta? \n (SI) (NO).", "ELIMINANDO USUARIO", 2);
														
							}while(!verificar.equalsIgnoreCase("si")&&!verificar.equalsIgnoreCase("no"));
							
							
							//Si es (si) verificamos que la cuenta no tenga pedidos.
							if(verificar.equalsIgnoreCase("si")){
								
								ResultSet rsPedidos= st.executeQuery("select count(*) as cantidad from pedidos where user_codigo="+id_user);
								
								while(rsPedidos.next()){
									
									cant=rsPedidos.getInt("cantidad");
								}
								
								rsPedidos.close();
								
									//Si nos devuelve valor, hay pedidos, por lo que hay que confirmar si se desea eliminar la cuenta.
									if(cant>0){
										
										do{
											
											verificar= JOptionPane.showInputDialog(null, "Su cuenta tiene informacion sobre sus pedidos, \n esta seguro que desea borrar su cuenta?  (SI) (NO).", "ELIMINANDO USUARIO", 2);
											
										}while(!verificar.equalsIgnoreCase("si")&&!verificar.equalsIgnoreCase("no"));
										
										
											//ELIMINAMOS LA CUENTA.
											if(verificar.equalsIgnoreCase("si")){
												
												st.executeUpdate("delete from usuarios where user_codigo="+id_user);
												
												JOptionPane.showMessageDialog(null,	"EL USUARIO HA SIDO ELIMINADO CORRECTAMENTE", "ELIMINANDO USUARIO", 1);
											}else
												
												JOptionPane.showMessageDialog(null,	"EL USUARIO NO SE HA ELIMINADO","ELIMINANDO USUARIO", 0);
											
									}else{
										
										st.executeUpdate("delete from usuarios where user_codigo="+id_user);
										
										JOptionPane.showMessageDialog(null,	"EL USUARIO HA SIDO ELIMINADO CORRECTAMENTE", "ELIMINANDO USUARIO", 1);
									}
									
							}else{
								
								JOptionPane.showMessageDialog(null,	"EL USUARIO NO SE HA ELIMINADO","ELIMINANDO USUARIO", 0);
								
							}
						
					}else{
						
						JOptionPane.showMessageDialog(null,	"LOS DATOS INTRODUCIDOS SON INCORRECTOS.", "ELIMINANDO USUARIO", 0);
					}
					
			}else{
				
				JOptionPane.showMessageDialog(null, "NO HAY NINGUN USUARIO CON ESE ALIAS.", "ELIMINANDO USUARIO", 0);
				System.err.println("");
			}
			
			
		}catch (Exception ex) {


			System.err.println("Error a la hora de hacer la consulta a la BBDD");	
			System.err.println(ex.getMessage());
		}
		
	}
	
	
	public static void modificarUsuario(Statement st, int userCod){
		
		//VARIABLES
		int menuOpc, codigoUsuario=0;
		String alias, passActual="", passNew="", passNew2="";
		
		
		/* Modificar los datos de un usuario, desde el alias hasta el password.
		 * Si es el alias introducirlo nuevamente y realizar el update.
		 * Si es el pass introducir los datos actuales de alias y pass y despues 
		 * introducir dos veces el pass para verificar que son iguales y hacer el update.
		 * 
		 * Si hubiese algun error a la hora de realizar acciones a la Base de Datos, saltara la excepci�n.*/
		try{
			
			
			do{
				
				
				menuOpc=Integer.parseInt(JOptionPane.showInputDialog(null, "Que deseas modificar.\n"
																			+ "1-. Alias de Usuario. \n"
																			+ "2-. Cambiar Password. \n"
																			+ "0-. Salir.\n"));
						
				
				  switch(menuOpc){
				
						case 0: // SALIR DEL MENU DE MODIFICACION DE USUARIO.
							
							JOptionPane.showMessageDialog(null, "Volviendo al menu principal");
							
						   break;
							
						case 1: // MODIFICAR NOMBRE DEL USUARIO
							
							alias=JOptionPane.showInputDialog(null, "Introducza su nuevo Alias.");
							
							st.executeUpdate("UPDATE usuarios set user_alias='"+alias+"' where user_codigo="+userCod);
							
							JOptionPane.showMessageDialog(null, "Se ha modificado el alias correctamente.");
							
							break;
							
						case 2: // MODIFICAR PASSWORD DEL USUARIO
							
							//Borramos para que no se quede guardado el PASS
							Main.passwordField.setText(null);
							
							
							alias=JOptionPane.showInputDialog(null, "POR MOTIVOS DE SEGURIDAD VUELVA A INTRODUCIR SUS DATOS DE USUARIO.");
							
							
							if (JOptionPane.showOptionDialog(null, Main.pswEnc, "INTRODUZCA SU PASSWORD ACTUAL",
									JOptionPane.YES_NO_OPTION,
									JOptionPane.QUESTION_MESSAGE, null, Main.opciones, Main.pswEnc) == JOptionPane.YES_OPTION) {
								
								passActual = Main.passwordField.getText().toString();
							}
							
							//Consulta para verificar que los datos son correctos.
							ResultSet codUser= st.executeQuery("select user_codigo from usuarios where user_alias='"+alias+"' and user_pass=md5('"+passActual+"')");
							
							
							//Variable que controla si el user existe.
							int okUser=-1;
							
							while(codUser.next()){
								
								codigoUsuario=codUser.getInt("user_codigo");
								okUser=0;
							}
							
							codUser.close();
							
							if(okUser!=-1){
								
								
									//COMPROBAR QUE EL PASS CUMPLE SUS RESTRICCIONES
									do{
										
										//Borramo el objeto de la clase JPasswordFiel, para resetear el cuadro.
										Main.passwordField.setText(null);
										//Introducir el pass, no es visible.
										if (JOptionPane.showOptionDialog(null, Main.pswEnc, "INTRODUZCA SU PASSWORD",
												JOptionPane.YES_NO_OPTION,
												JOptionPane.QUESTION_MESSAGE, null, Main.opciones, Main.pswEnc) == JOptionPane.YES_OPTION) {
											
											passNew = Main.passwordField.getText().toString();
										}
										
										
										//Llamada al metodo que comprueba el password. Si devuelve FALSE habra que volver a introducirlo
										if(!comprobarPassword(passNew)){
											
											JOptionPane.showMessageDialog(null, "ERROR AL INTRODUCIR LA CONTRASE�A\n"
											                                   +"EL PASSWORD DEBE CONTENER: \n"
											                                   +"- ENTRE 8 Y 15 CARACTERES\n"
											                                   +"- MAYUSCULAS\n"
											                                   +"- MINUSCULAS\n"
											                                   +"- NUMEROS\n"
											                                   +"- CARACTERES ESPECIALES.\n");
										}
									}while(!comprobarPassword(passNew));
									
																
									//Borramos para que no se quede guardado el PASS y volvemos a pedir de nuevo el passsword.
									Main.passwordField.setText(null);
									
									
									if (JOptionPane.showOptionDialog(null, Main.pswEnc, "INTRODUZCA DE NUEVO SU PASSWORD.",
											JOptionPane.YES_NO_OPTION,
											JOptionPane.QUESTION_MESSAGE, null, Main.opciones, Main.pswEnc) == JOptionPane.YES_OPTION) {
										
										passNew2 = Main.passwordField.getText().toString();
									}
									
									
									//Si son iguales realizamos el update.
									if(passNew.equals(passNew2)){
										
										st.executeUpdate("UPDATE usuarios set user_pass=md5('"+passNew+"') where user_codigo="+codigoUsuario);
										JOptionPane.showMessageDialog(null, "Se ha modificado su password correctamente.");
										
									}else{
										
										JOptionPane.showMessageDialog(null,"Las contrase�as no coinciden, no se ha podido modificar la contrase�a.");
									}
									
							}else{
								
								JOptionPane.showMessageDialog(null,"LOS DATOS INTRODUCIDOS SON INCORRECTOS");
							}
							
						
							break;
						
						default:
							
							JOptionPane.showMessageDialog(null, "La opcion introducida es incorrecta.");
					}
						
			}while(menuOpc!=0);
			
		}catch (Exception ex) {


			System.err.println("Error a la hora de consultar a la Base de Datos");	
			System.err.println(ex.getMessage());
		}
		
	}
	
		
	public static void buscarPiezas(Statement st){
		
		//VARIABLES.
		boolean error;
		String opcion="";
		int codSubcategoria, codPieza=0, categoria, tipoVehiculo, numCant;
		
		//ArrayList que guardara los codigos de cada fila para poder controlarlo cuando haya que introducir una opcion.
		ArrayList<Integer> codigos= new ArrayList<Integer>();
		
		
		//Llamada al metodo buscar el vehiculo.
		System.out.println("BUSCADOR DE PIEZAS\n SELECCIONE SU VEHICULO POR FAVOR");
		tipoVehiculo=datosVehiculo(st);
		
		System.out.println("---------------------------");
		
		/* Codigo que seleccionara la categoria, subcategoria y piezas. Si no se introdujese ninguna categoria,
		 * subcategoria, o pieza que se muestran en la lista, se advertira al usuario, para que vuelva a introducir 
		 * la opcion que desee.
		 * 
		 * Se podran insertar piezas de distintas subcategorias. Se saldra solo cuando el usuario diga que NO desea
		 * buscar ninguna otra pieza mas. 
		 * 
		 * */
		do{
			
			try{
				
				//---------------------------------CATEGORIAS---------------------------
				
				//Borramos la lista de codigos para introducir los de las subcategorias.
				codigos.clear();
				
				//Consulta a la BBDD.
				ResultSet categ= st.executeQuery("select * from cat_piezas");
				
				System.out.println("");
				
				System.out.println("CATEGORIAS");
				System.out.println("CODIGO     NOMBRE");
				
				
				//Mostramos las filas que nos ha devuelto la consulta.
				while(categ.next()){
					
					System.out.println("  "+categ.getInt("cat_codigo")+"      "+categ.getObject("cat_nombre"));
					
					codigos.add(categ.getInt("cat_codigo"));
				}
				
				categ.close();
				
				System.out.println("");
				
				
				//Codigo que controla que se inserte una categoria de la lista.
				do{
					error=true;
					
					System.out.print("Introduce la categoria que buscas:   "); 
					categoria=Excepciones.enteros();
					
						for(int i=0;i<codigos.size();i++){
							
							
							if(codigos.get(i)==categoria){
								
								error=false;
								break;
							}
						}
					if(error){
						
						System.err.println("SOLO PUEDES INTRODUCIR LAS CATEGORIAS QUE APARECEN EN LA LISTA.");
					}
				}while(error);
				
				//-----------------------------FIN CATEGORIAS----------------------------------------
				
				
				//----------------------------- SUBCATEGORIAS----------------------------------------
				
				
				//Borramos la lista de codigos para introducir los de las subcategorias.
				codigos.clear();
				
				
				//Consulta a la BBDD.
				ResultSet subCat= st.executeQuery("select * from cat_especifica where cat_codigo="+categoria);
				
				System.out.println("");
				
				System.out.println("SUBCATEGORIAS");
				System.out.println("CODIGO     NOMBRE");
				
				
				//Mostramos las filas que nos ha devuelto la consulta.
				while(subCat.next()){
					
					System.out.println("  "+subCat.getInt("cat_esp_codigo")+"      "+subCat.getObject("cat_esp_nombre"));
					
					codigos.add(subCat.getInt("cat_esp_codigo"));
				}
				
				subCat.close();
				
				
				//Codigo que controla que se inserte una subcategoria de la lista.
				do{
					error=true;
					
					System.out.println("Introduce el codigo de la subcategoria");
					codSubcategoria=Excepciones.enteros();
					
					
					for(int i=0;i<codigos.size();i++){
						
						
						if(codigos.get(i)==codSubcategoria){
							
							error=false;
							break;
						}
					}
					
					if(error){
						
						System.err.println("SOLO PUEDES INTRODUCIR LAS SUBCATEGORIAS QUE APARECEN EN LA LISTA.");
					}
				}while(error);
				
				//---------------------------FIN SUBCATEGORIAS-------------------------------------
				
				
				//--------------------------------PIEZAS-------------------------------------------
				
				
				//Borramos la lista de codigos para introducir los de las subcategorias.
				codigos.clear();
				
				//Consulta a la BBDD.
				ResultSet piezas= st.executeQuery("select pie_codigo, pie_referencia, pie_nombre, pie_descripcion, pie_cantidad, pie_precio from piezas where cat_esp_codigo="+codSubcategoria+" and pie_codigo in "
				                                  +"(select pie_codigo from piezas_tipos where tip_codigo="+tipoVehiculo+")");
				
				System.out.println(" COD   N� REFERENCIA ------------ NOMBRE ----------------------------- DESCRIPCION ----------------------------------------------------------------- CANTIDAD ----- PRECIO");
				
				
				//Mostramos todas las filas que nos ha devuelto la consulta.
				while(piezas.next()){
					
					if(piezas.getInt("pie_cantidad")==0){
						
						System.out.println("  "+piezas.getObject("pie_codigo")+"       "+piezas.getObject("pie_referencia")+"           "+piezas.getObject("pie_nombre")+"        "+piezas.getObject("pie_descripcion")+"         STOCK NO DISPONIBLE          "+piezas.getDouble("pie_precio")+"�/Sin IVA");
					}else{
						
						System.out.println("  "+piezas.getObject("pie_codigo")+"       "+piezas.getObject("pie_referencia")+"           "+piezas.getObject("pie_nombre")+"        "+piezas.getObject("pie_descripcion")+"         "+piezas.getInt("pie_cantidad")+"           "+piezas.getDouble("pie_precio")+"�/Sin IVA");
						
						codigos.add(piezas.getInt("pie_codigo"));
					}
					
				
				}
				
				piezas.close();
				
				
				//Codigo en el que se van a�adiendo las piezas que el usuario desee al carrito y la cantidad que desea por pieza, hasta que diga que NO desea introducir ninguna mas.
				do{
					
					
						//Controlar que la pieza que se introduce es alguna de las que aparecen en la lista.
						do{
							error=true;
							
							System.out.print("Introduce el codigo de la pieza que desees a�adir al carrito ");
							codPieza=Excepciones.enteros();
							
							for(int i=0;i<codigos.size();i++){
								
								
								if(codigos.get(i)==codPieza){
									
									error=false;
									break;
								}
							}
							
							if(error)
								System.err.println("SOLO PUEDES INTRODUCIR LOS CODIGOS DE LAS PIEZAS QUE APARECEN EN LA LISTA.");
							
						}while(error);
					
					
					//A�adimos la pieza al carrito.
					Main.carrito.add(codPieza);
					
					
					System.out.print("Cuantas piezas de este tipo desea pedir?   ");
					numCant=Excepciones.enteros();
					
					
					//A�adimos la cantidad que se quiere respecto a la pieza introducida anteriormente.
					Main.cantidad.add(numCant);
					
					System.out.println("PIEZA A�ADIDA AL CARRITO");
					
					
					//Controlar que solo se inserte (si) o (no)
					do{
						System.out.print("Deseas a�adir alguna pieza mas al carrito?   (SI)  (NO)   ");
						opcion=Main.lector.nextLine();
					}while(!opcion.equalsIgnoreCase("si")&&!opcion.equalsIgnoreCase("no"));
					
					
				}while(opcion.equalsIgnoreCase("si")); 
				
				
			}catch (Exception ex) {

				System.err.println("ERROR AL HACER LA CONSULTA A LA BASE DE DATOS.");
				System.err.println(ex.getMessage());		
			}
			
			
			//Controlar que solo se inserte (si) o (no)
			do{
				System.out.print("Deseas buscar alguna otra pieza de otra categoria?   (SI)  (NO)   ");
				opcion=Main.lector.nextLine();
			}while(!opcion.equalsIgnoreCase("si")&&!opcion.equalsIgnoreCase("no"));
			
			
		}while(opcion.equalsIgnoreCase("si"));
		
		
	}
	
	
	public static int datosVehiculo(Statement st){
		
		//VARIABLES.
		int tipo=0, codMarca, codModelo;
		ArrayList<Integer> codigos= new ArrayList<Integer>();
		boolean error;
		
		
		//En caso de darnos un error la consulta, nos saltara la Excepcion y nos mostrara un mensaje.
		try{
			
			
			
			//------------------------BUSCAR MARCAS----------------------------
			
			//Borramos lo que haya en el ArrayList de codigos.
			codigos.clear();
			
			
			//Consulta a la BBDD
			ResultSet rsMarcas= st.executeQuery("select * from marcas");
			
			System.out.println("MARCAS");
			System.out.println("------");
			System.out.println("CODIGO      MARCA");
			
			//Devolvemos todas las filas sobre la consulta.
			while (rsMarcas.next()) {
				
				System.out.println("   "+rsMarcas.getObject("mar_codigo").toString()+"         "+rsMarcas.getObject("mar_nombre"));
				
				codigos.add(rsMarcas.getInt("mar_codigo"));
			}
			
			rsMarcas.close();
			
			
			//Codigo que controla que se inserte una marca de la lista.
			do{
				error=true;
				
				System.out.println("Introduce la marca que deseas:");
				codMarca=Excepciones.enteros();
				
				for(int i=0;i<codigos.size();i++){
					
					
					if(codigos.get(i)==codMarca){
						
						error=false;
						break;
					}
				}
				
				if(error){
					
					System.err.println("SOLO PUEDES INTRODUCIR LOS MARCAS QUE APARECEN EN LA LISTA.");
				}
			}while(error);
			
	 //------------------------------------FIN MARCAS-----------------------------------------
			
			
	 //----------------------------------BUSCAR MODELOS---------------------------------------
			
			
			//Borramos lo que haya en el ArrayList de codigos.
			codigos.clear();
			
						
			//Consulta a la BBDD
			ResultSet rsModelos= st.executeQuery("select * from modelos where mar_codigo="+codMarca);
			
			System.out.println("MODELOS");
			System.out.println("-------");
			System.out.println("CODIGO      MARCA");
			
			
			//Devolvemos todas las filas sobre la consulta.
			while (rsModelos.next()) {
				
				System.out.println("   "+rsModelos.getInt("mod_codigo")+"         "+rsModelos.getObject("mod_nombre"));
				
				codigos.add(rsModelos.getInt("mod_codigo"));
			}
			
			rsModelos.close();
			
			
			//Codigo que controla que se inserte una marca de la lista.
			do{
				error=true;
				
				System.out.println("\nIntroduce el modelo que buscas:");
				codModelo=Excepciones.enteros();
				
				
					for(int i=0;i<codigos.size();i++){
									
						
						if(codigos.get(i)==codModelo){
							
							error=false;
							break;
						}
					}
				if(error){
					
					System.err.println("SOLO PUEDES INTRODUCIR LOS MODELOS QUE APARECEN EN LA LISTA.");
				}
			}while(error);
			
	 //--------------------------FIN MODELOS-----------------------------------------
			
			
	 //--------------------------BUSCAR TIPOS----------------------------------------
			
			//Borramos lo que haya en el ArrayList de codigos.
			codigos.clear();
			
			
			//Consulta a la BBDD
			ResultSet rsTipos= st.executeQuery("select * from tipos where mod_codigo="+codModelo);
			
			System.out.println("TIPOS");
			System.out.println("-----");
			System.out.println("CODIGO     DESCRIPCION");
			
			
			//Devolvemos las filas sobre la Consulta.
			while(rsTipos.next()){
				
				System.out.println("   "+rsTipos.getObject("tip_codigo").toString()+"         " +rsTipos.getObject("tip_descripcion"));
				codigos.add(rsTipos.getInt("tip_codigo"));
				
			}
			
			rsTipos.close();
			
			
			//Codigo que controla que se inserte una marca de la lista.
			do{
				error=true;
				
				System.out.println("Introduce el tipo de vehiculo que buscas:");
				tipo=Excepciones.enteros();
				
				for(int i=0;i<codigos.size();i++){
					
					
					if(codigos.get(i)==tipo){
						
						error=false;
						break;
					}
				}
				
				if(error){
					
					System.err.println("SOLO PUEDES INTRODUCIR LOS TIPOS QUE APARECEN EN LA LISTA.");
				}
			}while(error);
			
	 //------------------------------------FIN TIPOS-----------------------------------------	
		
			
		}catch (Exception ex) {

			System.err.println("ERROR AL HACER LA CONSULTA A LA BASE DE DATOS");
			System.err.println(ex.getMessage());
		}
		
		return tipo;
				
	}
	
		
	public static void crearPedido(Statement st,int id_user){
		
		
		//VARIABLES
		int codPedido=0;
		
		//Si no hay piezas en el carrito no se puede crear el pedido
		if(Main.carrito.size()==0){
			
			System.err.println("NO HAY PIEZAS EN EL CARRITO.");
		}else{
			
			
			//Si hubiese algun error la hacer el insert a la BBDD saltara la Excepcion.
			try{
				
				
				//Ejecutamos el inser a la BBDD
				st.executeUpdate("insert into pedidos(user_codigo) values ("+id_user+")");
				
				
				//Consultamos el codigo del pedigo.
				ResultSet pedido= st.executeQuery("select ped_codigo from pedidos where user_codigo="+id_user);
				
				
				while(pedido.next()){
					
					codPedido=pedido.getInt("ped_codigo");
				}
				
				pedido.close();
				
			}catch (Exception ex) {


				System.err.println("ERROR AL HACER EL INSERT A LA BBDD");
				System.err.println(ex.getMessage());
			}
			
			System.out.println("CREANDO PEDIDO....");
			System.out.println("A�adiendo todas las piezas del carrito a su pedido.");
						
			
			//Llamamos al metodo a�adir piezas.
				addPiezas(st,codPedido, id_user);
		}
		
		
	}
	
	
	public static void addPiezas(Statement st, int codPedido, int id_user){
		
		//VARIABLES
		String validarPedido="", refPieza, descPieza="", nombreUser="", apellidosUser="", opcion;
		int piezaCodigo=0;
		double precPieza=0, total=0;
		
		
		//Si en algun momento la Base de datos nos da un error, mostraremos la Excepcion indicandolo.
		try{
			
			
			/* Bucle que recorrera todo el ArrayList del carrito y de la cantidad respecto a esa pieza,
			 * para obtener cada pieza y luego insertarla en la tabla lineas de pedido sobre el codigo
			 * del pedido que se le ha pasado al metodo a la hora de llamarlo.
			 * */ 
			for(int i=0;i<Main.carrito.size();i++){
				
				
				//Consultamos la BBDD para obtener unos campos.
				ResultSet pieza= st.executeQuery("select pie_referencia, pie_codigo, pie_nombre, pie_descripcion, pie_precio from piezas where pie_codigo="+Main.carrito.get(i));
							
				while(pieza.next()){
					
					refPieza=pieza.getObject("pie_referencia").toString();
					descPieza=pieza.getObject("pie_descripcion").toString();
					precPieza=pieza.getDouble("pie_precio");
					piezaCodigo=pieza.getInt("pie_codigo");
					
				
				}
				
				pieza.close();
						
				//Insertamos la pieza 
				st.executeUpdate("insert into lineas_pedido(lin_descripcion,lin_cantidad,lin_precio,pie_codigo, ped_codigo) values ('"+ descPieza + "', "+ Main.cantidad.get(i) + ", "+ (Main.cantidad.get(i)*(precPieza*1.21)) + ", "+ piezaCodigo +", "+codPedido+")");
				
				System.out.println("PIEZA A�ADIDA CORRECTAMENTE.");
				
				
			}
			
			
			//MOSTRAMOS COMO SE ENCUENTRA EL PEDIDO.
			System.out.println("Su pedido se encuentra asi: ");
			
			
			ResultSet pedido= st.executeQuery("select * from lineas_pedido where ped_codigo="+codPedido);
			
			System.out.println("  N�                        DESCRIPCION                                                                     CANTIDAD         PRECIO �/IVA Inc");
			
			int i=1;
			
			while(pedido.next()){
				
				
				
				System.out.println("  "+i+"-.         "+ pedido.getObject("lin_descripcion") + "             " + pedido.getInt("lin_cantidad")+ "     " + pedido.getDouble("lin_precio")+"�");
				
				total=total+pedido.getDouble("lin_precio");
				i++;
			}
			
			System.out.println("");
			System.out.println("El total del pedido asciende a: "+total +" iva incluido.");
			do{
				
				System.out.print("Desea confirmar el pedido??  (SI)  (NO)   ");
				validarPedido=Main.lector.nextLine();
				
			}while(!validarPedido.equalsIgnoreCase("si")&&!validarPedido.equalsIgnoreCase("no"));
			
			
			//Si introducimos (si) modificaremos el estado, importa y fecha del pedido.
			if(validarPedido.equalsIgnoreCase("si")){
				
				//Consultar si hay datos personales del user
				ResultSet datosUser= st.executeQuery("select dp_nombre, dp_apellidos from datospersonales where user_codigo="+id_user+";");
				
				int userOk=-1;
				while(datosUser.next()){
					
					nombreUser=datosUser.getObject("dp_nombre").toString();
					apellidosUser=datosUser.getObject("dp_apellidos").toString();
					
					userOk=0;
				}
				
				//Si existen datos confirmamos el pedido, sino se podran introducir para continuar.
				if(userOk!=-1){
					
					st.executeUpdate("update pedidos set ped_estado='CONFIRMADO', ped_importe="+total+", fecha_hora=SYSDATE() where ped_codigo="+codPedido);
					System.out.println("");
					System.out.println("Sr. "+nombreUser+" "+apellidosUser+".....SU PEDIDO SE HA REALIZADO CORRECTAMENTE.\n");
					
					//BORRAMOS EL CARRITO Y LA CANTIDAD PARA PODER REALIZAR OTROS PEDIDOS.
					Main.carrito.clear();
					Main.cantidad.clear();
					
				}else{
					
						System.err.println("Todavia no ha rellenado sus datos personales, para poder confirmar el pedido es necesario rellenarlos.");
						
						do{
							
							System.out.println("Desea insertarlos ahora??  (SI)  (NO)");
							opcion=Main.lector.nextLine();
						}while(!opcion.equalsIgnoreCase("si")&&!opcion.equalsIgnoreCase("no"));
						
						
						//Si introduce (si) llamaremos al metodo datosUsuario sobre el codigo del usuario que hay.
						if(opcion.equalsIgnoreCase("si")){
							
							datosPersonales(st,id_user);
							
							st.executeUpdate("update pedidos set ped_estado='CONFIRMADO', ped_importe="+total+", fecha_hora=SYSDATE() where ped_codigo="+codPedido);
							System.out.println("");
							System.out.println("");
							System.out.println("SU PEDIDO SE HA REALIZADO CORRECTAMENTE.\n");
							
							//BORRAMOS EL CARRITO Y LA CANTIDAD PARA PODER REALIZAR OTROS PEDIDOS.
							Main.carrito.clear();
							Main.cantidad.clear();
							
						}else{
							
							System.out.println("Volviendo al Menu.");
						}
					}
				
			}else{
				
				//Si es un (no) borraremos el pedido, que a su vez hace referencia con las lineas de pedido. 
				st.executeUpdate("delete from pedidos where ped_codigo="+codPedido);
				
				System.err.println("SU PEDIDO SE HA CANCELADO.");
			}
			
		}catch (Exception ex) {


			System.out.println(ex.getMessage());		
		}
		
		
	}
	
	
	public static void mostrarCesta(Statement st){
		
		//VARIABLES.
		int ok=-1;
		
		//Si en algun momento la Base de Datos nos devuelve un error, mostraremos la excepcion.
		try{
			
			System.out.println("Su carrito se encuentra asi: ");
			
			
			//Bucle para mostrar toda la informacion de cada pieza que hay en el carrito, con su cantidad.
			for(int i=0;i<Main.carrito.size();i++){
				
				ResultSet pieza= st.executeQuery("select pie_referencia, pie_nombre, pie_descripcion, pie_precio from piezas where pie_codigo="+Main.carrito.get(i));
								
				while(pieza.next()){
					
					System.out.println("PIEZA N�"+(i+1));
					System.out.println("N� REFERENCIA: "+pieza.getObject("pie_referencia"));
					System.out.println("NOMBRE: "+pieza.getObject("pie_nombre"));
					System.out.println("DESCRIPCION: "+pieza.getObject("pie_descripcion"));
					System.out.println("PRECIO: "+pieza.getDouble("pie_precio"));
					System.out.println("CANTIDAD: x"+Main.cantidad.get(i));
				
					ok=0;
				}
				
				pieza.close();			
				
			}
			
			if(ok==-1){
				
				System.err.println("NO HAY PIEZAS A�ADIDAS AL CARRITO.");
			}
		}catch (Exception ex) {

			System.err.println("ERROR AL CONSULTAR A LA BASE DE DATOS.");
			System.err.println(ex.getMessage());		
		}
		
		
	}
	
	
	public static void visualizarDatos(Statement st, int userCod){
		
		//VARIABLES
		int error=-1;
		String opcion;
		
		
		/* Mostrar los datos del usuario, si hay algun error saltara la Excepcion.
		 * En caso de no haber datos personales se mostrara el mensaje y se preguntara 
		 * si se desean insertar ahora.*/
		try{
			
			ResultSet datos= st.executeQuery("select * from datosPersonales where user_codigo="+userCod);
			
			while(datos.next()){
				
				error=0;
				
				System.out.println("NOMBRE: "+datos.getObject("dp_nombre"));
				System.out.println("APELLIDOS: "+datos.getObject("dp_apellidos"));
				System.out.println("DNI: "+datos.getObject("dp_dni"));
				System.out.println("FECHA_NACIMIENTO: "+datos.getObject("dp_fechanac"));
				System.out.println("DIRECCION: "+datos.getObject("dp_direccion"));
				System.out.println("TELF: "+datos.getObject("dp_email"));
				System.out.println("EMAIL: "+datos.getObject("dp_telefono"));
				
			}
			
			datos.close();
			
		}catch (Exception ex) {


			System.out.println(ex.getMessage());		
		}
		
		
		if(error==-1){
			
			System.err.println("ERROR----TODAVIA NO HAS INSERTADO TUS DATOS PERSONALES");
			
			do{
				
				System.out.println("Desea insertarlos ahora??  (SI)  (NO)");
				opcion=Main.lector.nextLine();
			}while(!opcion.equalsIgnoreCase("si")&&!opcion.equalsIgnoreCase("no"));
			
			
			//Si introduce (si) llamaremos al metodo datosUsuario sobre el codigo del usuario que hay.
			if(opcion.equalsIgnoreCase("si")){
				
				datosPersonales(st,userCod);
			}else{
				
				System.out.println("Volviendo al Menu.");
			}
		}	
		
	}
	
	
	public static void modificarDatos(Statement st, int userCod){
		
		//Variables menuUser
		int opMenu;
		boolean error=true;
		String opcion;
		
		
		/* Si al consultar a la BBDD no hay datos, mostrara mensaje indicandolo y preguntara
		 * si desea hacerlo ahora, de ser asi llamaremos al metodo datosUsuario.
		 * Si hubiesen datos mostraremos el menu para escoger que queremos modificar..
		 * 
		 * Si hay algun error saltara la Excepcion.*/
		try{
			
			ResultSet okDatos= st.executeQuery("select dp_codigo from datosPersonales where user_codigo="+userCod);
							
			while(okDatos.next()){
				
				error=false;
			}
			
		}catch (Exception ex) {


			System.err.println("ERROR AL CONSULTAR A LA BASE DE DATOS.");
			System.err.println(ex.getMessage());		
		}
		
		if(error){
			
			System.err.println("ERROR----TODAVIA NO HAS INSERTADO TUS DATOS PERSONALES");
			
			do{
				
				System.out.println("Desea insertarlos ahora??  (SI)  (NO)");
				opcion=Main.lector.nextLine();
				
			}while(!opcion.equalsIgnoreCase("si")&&!opcion.equalsIgnoreCase("no"));
			
			
			//Llamada al metodo datosUsuario.
			if(opcion.equalsIgnoreCase("si")){
				
				datosPersonales(st,userCod);
			}else{
				
				System.out.println("Volviendo al Menu.");
			}
			
		}else{
			
			//Entramos porque ya existe informacion sobre el usuario
			
			do{
				
				System.out.println("MODIFICAR DATOS PERSONALES\n"
				                  +"=====================\n"
						          +"1-. Modificar Nombre y Apellidos.\n"
						          +"2-. Modificar DNI.\n"
						          +"3-. Modificar Fecha Nacimiento\n"
						          +"4-. Modificar Direcci�n.\n"
						          +"5-. Modificar Telefono.\n"
						          +"6-. Modificar email.\n"
				                  +"0-. Volver al menu principal.");
				System.out.print("Introduzca la opcion que desee:  ");
				opMenu=Excepciones.enteros();
				
				switch(opMenu){
		
				
				case 0:
						System.out.println("Volviendo al menu de cliente.");
					break;
				
					//MODIFICAR EL NOMBRE Y LOS APELLIDOS.
				case 1:
						
						String nombre,apellidos;
						
						System.out.print("Introduce tu nombre:   ");
						nombre=Main.lector.nextLine();
						System.out.print("Introduce tus apellidos:   ");
						apellidos=Main.lector.nextLine();
						
						try{
							
							st.executeUpdate("update datosPersonales set dp_nombre='"+nombre+"', dp_apellidos='"+apellidos+"' where user_codigo="+userCod);
											
						}catch (Exception ex) {
							
							System.err.println("ERROR AL HACER EL UPDATE A LA BASE DE DATOS.");
							System.err.println(ex.getMessage());		
						}
						
					break;
				
					//MODIFICAR EL DNI
				case 2:
					
						String dni="";
						
						//COMPROBAR EL DNI
						do{
							
							System.out.print("Introduce tu DNI:   ");
							dni=Main.lector.nextLine();
						}while(!comprobarDNI(dni));
											
						try{
							
							st.executeUpdate("update datosPersonales set dp_dni='"+dni+"' where user_codigo="+userCod);
								
							System.out.println("DATOS MODIFICADOS CORRECTAMENTE.");
						}catch (Exception ex) {
	
							System.err.println("ERROR AL HACER EL UPDATE A LA BASE DE DATOS.");
							System.err.println(ex.getMessage());		
						}
						
					
					break;
					
					//MODIFICAR LA FECHA DE NACIMIENTO.
				case 3:
					
						String fechanac="";
						
						System.out.print("Introduce tu fecha de nacimiento en el formato:  AAAA/MM/DD  ");
						apellidos=Main.lector.nextLine();
											
						try{
							
							st.executeUpdate("update datosPersonales set dp_fechanac='"+fechanac+"' where user_codigo="+userCod);
							
							System.out.println("DATOS MODIFICADOS CORRECTAMENTE.");
							
						}catch (Exception ex) {
	
							System.err.println("ERROR AL HACER EL UPDATE A LA BASE DE DATOS.");
							System.err.println(ex.getMessage());		
						}
					
					break;
					
					//MODIFICAR LA DIRECCION
				case 4:
					
						String direccion="";
						
						System.out.print("Introduce tu direcci�n:   ");
						apellidos=Main.lector.nextLine();
											
						try{
							
							st.executeUpdate("update datosPersonales set dp_direccion='"+direccion+"' where user_codigo="+userCod);
							
							System.out.println("DATOS MODIFICADOS CORRECTAMENTE.");
							
						}catch (Exception ex) {
	
							System.err.println("ERROR AL HACER EL UPDATE A LA BASE DE DATOS.");
							System.err.println(ex.getMessage());			
						}
					
					break;
					
					//MODIFICAR EL TELEFONO
				case 5:
					
						String telef="";
						
						System.out.print("Introduce tu telefono:   ");
						apellidos=Main.lector.nextLine();
											
						try{
							
							st.executeUpdate("update datosPersonales set dp_telefono='"+telef+"' where user_codigo="+userCod);
							
							System.out.println("DATOS MODIFICADOS CORRECTAMENTE.");
							
						}catch (Exception ex) {
	
	
							System.err.println("ERROR AL HACER EL UPDATE A LA BASE DE DATOS.");
							System.err.println(ex.getMessage());	
						}
					
					break;
				
					//MODIFICAR EL EMAIL.
				case 6:
					
					String email="";
					
					//COMPROBAR EL EMAIL
					do{
						
						System.out.print("Introduce tu email:   ");
						email=Main.lector.nextLine();
						
					}while(!email(email));
						
					
					try{
						
						st.executeUpdate("update datosPersonales set dp_email='"+email+"' where user_codigo="+userCod);
						
						System.out.println("DATOS MODIFICADOS CORRECTAMENTE.");
						
					}catch (Exception ex) {

						System.err.println("ERROR AL HACER EL UPDATE A LA BASE DE DATOS.");
						System.err.println(ex.getMessage());		
					}
				
				break;
				
				default:
					
					System.err.println("La opcion introducida no es valida.");
					
				}
				
				
			}while(opMenu!=0);
			
		}
			
			
	}
		
	
	public static void datosPersonales(Statement st, int user_cod){
		
		//variables para crear el cliente
		String dni,nombre, apellidos, direccion, telf,email, fechanac;
		
		
		//PEDIREMOS TODOS LOS DATOS DEL USUARIO.
		
		
		System.out.print("Introduce tu nombre:   ");
		nombre=Main.lector.nextLine();
		System.out.print("Introduce tus apellidos:   ");
		apellidos=Main.lector.nextLine();
		
		//COMPROBAR EL DNI
		do{
			
			System.out.print("Introduce tu DNI:   ");
			dni=Main.lector.nextLine();
			
		}while(!comprobarDNI(dni));
		
		System.out.print("Introduce tu fecha de nacimiento en el formato:  AAAA/MM/DD :   ");
		fechanac=Main.lector.nextLine();
		System.out.print("Introduce tu direccion completa:   ");
		direccion=Main.lector.nextLine();
		System.out.print("Introduce tu telefono:   ");
		telf=Main.lector.nextLine();
		
		
		//COMPROBAR EL EMAIL
		do{
			
			System.out.print("Introduce tu email:   ");
			email=Main.lector.nextLine();
			
		}while(!email(email));
		
		
		
		/* Insertaremos los datos del usuario en la tabla datosPersonales, sobre el codigo 
		 * del suaurio que se le ha pasado al metodo.
		 * 
		 * Si hay algun error saltara la Excepcion.*/
		try{
			
			st.executeUpdate("insert into datosPersonales(dp_dni, dp_nombre, dp_apellidos, dp_direccion, dp_telefono, dp_email, dp_fechanac, user_codigo) VALUES "
				     +"('"+dni+"', '"+nombre+"', '"+apellidos+"', '"+direccion+"', '"+telf+"', '"+email+"', '"+fechanac+"', "+user_cod+")");
			
			System.out.println("DATOS DE USUARIO INTRODUCIDOS CORRECTAMENTE.");
	
		}catch (Exception ex) {

			System.err.println("ERROR AL HACER EL INSERT A LA BASE DE DATOS.");
			System.err.println(ex.getMessage());	
					
		}
		
		
	}
	
		
	public static boolean comprobarDNI(String dni){
		
		int cont=0;
		
		
		/*Metodo que comprobar� que la cadena tenga 9 caracteres, que los 8 primeros sen numeros y el ultimo una letra. 
		 * Mostrar� un mensaje distinto dependiendo del error */
		if(dni.length()==9){
			
			if(!Character.isDigit(dni.charAt(8))){
				
				
				for(int i=0;i<dni.length();i++){
					
					if(Character.isDigit(dni.charAt(i))){
						
						cont++;
					}
				}
				
				if(cont==8){
					
					System.out.println("DNI correcto");
					return true;
				}else{
					
					System.out.println("los 8 primeros caracteres no son numeros.");
					return false;
				}
				
			}else{
				
				System.out.println("El ultimo caracter no es una letra.");
				return false;
			}
			
						
		}else{
			
			System.out.println("El dni no tiene los 9 caracteres de su longitud.");
			return false;
		}
	}
	
	
	public static boolean email(String mail){
		
		/* Explicacion de la expresion regular:
		 * 
		 * ^ --> Indica el comienzo del String.
		 * [A-Za-z0-9._%-] --> Indica que pueden haber letras mayusculas, minusculas, numeros, puntos, guiones altos y bajos y porcentaje
		 * +@ --> Indica que a lo anterior lo sigue una @
		 * [A-Za-z0-9.-] --> Indica que pueden haber letras mayusculas, minusculas, numeros, puntos, guiones altos.
		 * +\\. --> Indica que a lo anterior lo sigue un punto (.)
		 * [a-zA-Z] --> Solo podran haber letras mayusculas o minusuclas.
		 * {2,4} --> Indica que lo que va antes de los corchetes tendra entre 2 y 4 caracteres.
		 * $ --> Indica el final del String.
		 * */
		if (mail.matches("^[A-Za-z0-9._%-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]{2,4}$")){
			
			System.out.println("EMAIL VALIDO.");
			return true;
			
		}else{
		
			System.err.println("EMAIL INCORRECTO");
			return false;
		}
		
	}
	
	
	public static void piezaReferencia(Statement st){
		
		int ok=-1;
		String referenPieza;
		
		System.out.println("Introduzca la referencia de la pieza que busca.");
		referenPieza=Main.lector.nextLine();
		
		
		System.out.println("LA PIEZA QUE HA INTRODUCIDO ES LA SIGUIENTE.");
		
		/* Consultamos a la Base de datos si la pieza existe, de ser asi se muestra info
		 * si no existiese esa pieza se informa con un mensaje.
		 * 
		 * Si hay algun error a  la hora de hacer la consulta a la Base de Datos saltara la Excepcion.*/
		try{
			
			//Consulta a la BBDD.
			ResultSet refPieza= st.executeQuery("select pie_codigo, pie_referencia, pie_nombre, pie_descripcion, pie_cantidad, pie_precio from piezas where pie_referencia='"+referenPieza+"'");
			
			System.out.println(" COD   N� REFERENCIA ------------ NOMBRE ----------------------------- DESCRIPCION ----------------------------------------------------------------- CANTIDAD ----- PRECIO");
			
			
			//Mostramos todas las filas que nos ha devuelto la consulta.
			while(refPieza.next()){
				
				System.out.println("  "+refPieza.getObject("pie_codigo")+"       "+refPieza.getObject("pie_referencia")+"           "+refPieza.getObject("pie_nombre")+"        "+refPieza.getObject("pie_descripcion")+"         "+refPieza.getInt("pie_cantidad")+"           "+refPieza.getDouble("pie_precio"));
				
				ok=0;
			
			}
			
			refPieza.close();
			
		}catch (Exception ex) {

			System.err.println("ERROR AL HACER LA CONSULTA A LA BASE DE DATOS.");
			System.err.println(ex.getMessage());	
					
		}
		
		
		if(ok==-1){
			
			System.err.println("LA REFERENCIA DE LA PIEZA INTRODUCIDA NO EXISTE.");
		}
		
		
	}
	
	
	static boolean comprobarPassword(String password){
		
		int caracEspeciales=0, mayus=0, minus=0, num=0;
		
		if(password.length()>=8&&password.length()<=15){
			
			for(int i=0;i<password.length();i++){
				
				//CONTAMOS LOS CARACTERES ESPECIALES
				if(password.charAt(i)>='\u0021'&&password.charAt(i)<='\u002F'
					||password.charAt(i)>='\u003A'&&password.charAt(i)<='\u0040'
					||password.charAt(i)>='\u005B'&&password.charAt(i)<='\u0060'
					||password.charAt(i)>='\u007B'&&password.charAt(i)<='\u007D'
					||password.charAt(i)==' '){
					
					caracEspeciales++;
				 }
				
				//CONTAMOS LAS MAYUSCULAS
				if(password.charAt(i)>='\u0041'&&password.charAt(i)<='\u005A'){
					
					mayus++;
				}
				
				//CONTAMOS LAS MINUSCULAS
				if(password.charAt(i)>='\u0061'&&password.charAt(i)<='\u007A'){
					
					minus++;
				}

				//CONTAMOS LOS NUMEROS
				if(password.charAt(i)>='\u0030'&&password.charAt(i)<='\u0039'){
	
					num++;
				}
				
			}
			
			if(caracEspeciales>0&&mayus>0&&minus>0&&num>0){
				
				return true;
			}else{
				
				return false;
			}
		}else{
			
			return false;
		}
		
		
		
	}
	

	public static void mostrarPedidos(Statement st, int userCod){
		
		//VARIABLES.
		int numPedido=0, cant=-1;
		String repetir="";
		ArrayList<Integer> codigos= new ArrayList<Integer>();
		boolean error;
		
		
		//En caso de darnos un error la consulta, nos saltara la Excepcion y nos mostrara un mensaje.
		try{
			
			ResultSet cantPedidos= st.executeQuery("select count(*) as cantidad from pedidos where ped_estado='CONFIRMADO' and user_codigo="+userCod+";");
			
			cantPedidos.next();
			
			cant=cantPedidos.getInt("cantidad");
			
			if(cant>0){
				
				
				//------------------------BUSCAR PEDIDOS----------------------------
				
				//Borramos lo que haya en el ArrayList de codigos.
				codigos.clear();
				
				
				//Consulta a la BBDD
				ResultSet rsPedidos= st.executeQuery("select ped_codigo, ped_estado, ped_importe, DATE_FORMAT(fecha_hora, '%d/%m/%Y----%H:%i:%s') as fechaHora from pedidos where ped_estado='CONFIRMADO' and user_codigo="+userCod+";");
				
				System.out.println("PEDIDOS");
				System.out.println("-------");
				System.out.println("CODIGO      ESTADO         IMPORTE          FECHA_HORA");
				
				//Devolvemos todas las filas sobre la consulta.
				while (rsPedidos.next()) {
					
					System.out.println("  "+rsPedidos.getObject("ped_codigo").toString()+"      "+rsPedidos.getObject("ped_estado")+"        "+rsPedidos.getObject("ped_importe").toString()+"        "+rsPedidos.getObject("fechaHora"));
					
					codigos.add(rsPedidos.getInt("ped_codigo"));
				}
				
				rsPedidos.close();
				
				
				do{
					
					
					//Codigo que controla que solo se pueda insertar el codigo de un pedido que aparezca en la lista.
					do{
						error=true;
						
						System.out.println("Introduce el codigo del pedido que deseas ver:");
						numPedido=Excepciones.enteros();
						
						for(int i=0;i<codigos.size();i++){
							
							
							if(codigos.get(i)==numPedido){
								
								error=false;
								break;
							}
						}
						
						if(error){
							
							System.err.println("SOLO PUEDES INTRODUCIR LOS CODIGOS DE LOS PEDIDOS QUE APARECEN EN LA LISTA.");
						}
					}while(error);
					
					
					//MOSTRAR LINEAS DE PEDIDO SOBRE EL PEDIDO INTRODUCIDO ANTERIORMENTE
						ResultSet pedPiezas= st.executeQuery("select * from lineas_pedido where ped_codigo="+numPedido+";");
						
						int i=1;
						while(pedPiezas.next()){
							
							System.out.println("PIEZA N�"+(i));
							System.out.println("DESCRIPCION: "+pedPiezas.getObject("lin_descripcion"));
							System.out.println("CANTIDAD: x"+pedPiezas.getObject("lin_cantidad").toString());
							System.out.println("PRECIO: "+pedPiezas.getDouble("lin_precio"));
							System.out.println("");
													
							i++;
						}
						
						pedPiezas.close();			
						
					
					//Controlar que solo se inserte (si) o (no)
					do{
						System.out.print("Deseas buscar los detalles de algun otro pedido?   (SI)  (NO)   ");
						repetir=Main.lector.nextLine();
					}while(!repetir.equalsIgnoreCase("si")&&!repetir.equalsIgnoreCase("no"));
					
				}while(repetir.equalsIgnoreCase("si"));
				
				
			}else{
				
				System.err.println("NO HA REALIZADO NINGUN PEDIDO TODAVIA.");
			}
			
		
		}catch (Exception ex) {
	
			System.err.println("ERROR AL HACER LA CONSULTA A LA BASE DE DATOS");
			System.err.println(ex.getMessage());
		}
	}
	
	
	public static String fechaHora(){
		/*
		 * Metodo que crea un objeto de Greg.Calendar para poder obtener la fecha y la hora.
		 * Luego se crea un objeto de SimpleDateFormat para poder convertir la fecha en un String.
		 * */
		
		
		GregorianCalendar data= new GregorianCalendar();
		
		SimpleDateFormat sdf = new SimpleDateFormat();
		
		String fecha = sdf.format(data.getTime());
		
		return fecha;
	}

	
}
