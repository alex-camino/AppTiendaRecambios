package clases;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.mysql.jdbc.Statement;


public class Main {

	public static Scanner lector= new Scanner(System.in);
	
	//PASSWORD OCULTO
	public static JPasswordField passwordField = new JPasswordField();
	public static Object[] pswEnc = { "", passwordField };
	public static Object opciones[] = { "LOGUEARSE"};
	
	//VARIABLES ESTATICAS DEL CARRITO
	public static ArrayList<Integer> carrito= new ArrayList<Integer>();
	public static ArrayList<Integer> cantidad= new ArrayList<Integer>();
	
	public static void main(String[] args){
		
		try{
			
			Class.forName("com.mysql.jdbc.Driver");
			
			// CREAMOS LA CONEXION CON LA BASE DE DATOS.
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/oscaro", "alex", "1234");
			Statement st = (Statement) conexion.createStatement();
			
			
			
			menuPrincipal(st, conexion);
			
		} catch (Exception ex) {
			
			System.out.println(ex.getMessage());
		
		}
		
	}
	
	
	public static void menuPrincipal(Statement st, Connection conexion){
		
		//variablaes locales
		int opMen;
		
		do{
			System.out.println("APP OSCARO --RECAMBIOS PARA AUTOMOVIL--");
			System.out.println("---------------------------------------");
			System.out.println("MENU PRINCIPAL\n"
					          +"==============\n"
							  +"1-. Crear usuario.\n"
							  +"2-. Conectarse como usuario.\n"
							  +"3-. Eliminar un usuario.\n"
					          +"0-. Salir de la APP OSCARO\n");
			System.out.print("Introduzca la opcion que desea: ");
			opMen=Excepciones.enteros();
			
			switch(opMen){
			
			case 0:
					System.out.println("");
					System.out.println("Muchas gracias por elegir APP OSCARO RECAMBIOS. Vuelva pronto.");
				break;
			
			case 1:
					System.out.println("");
					Metodos.crearUsuario(st, conexion);
					System.out.println("");
				break;
			
			case 2:
					System.out.println("");
					Metodos.conectarUsuario(st, conexion);
					System.out.println("");
				break;
					
			case 3:
				
					System.out.println("");
					Metodos.eliminarUsuario(st, conexion);
					System.out.println("");
				break;
			default:
				
				System.err.println("La opci�n introducida no es valida.");
				
			}
		}while(opMen!=0);
		
		
		
	}
		

	public static void menuUsuario(Statement st, int userCod){
		
			
		//Variables menuUser
		int opMenu;
			
			do{
				
				System.out.println("MENU USUARIO\n"
				                  +"============\n"
						          +"1-. Buscar piezas.\n"
						          +"2-. Realizar un pedido\n"
				                  +"3-. Ver mi cesta.\n"
						          +"4-. Datos Personales. \n"
				                  +"5-. Modificar usuario.\n"
				                  +"6-. Buscar pieza por referencia.\n"
				                  +"7-. Mostrar todos mis pedidos.\n"
						          +"0-. Volviendo al menu principal.");
				System.out.print("Introduzca la opcion que desee:  ");
				opMenu=Excepciones.enteros();
				
				switch(opMenu){
		
				
				case 0:
						System.out.println("");
						System.out.println("Est� saliendo del menu de usuarios.");
					break;
				
				case 1:
						System.out.println("");
						Metodos.buscarPiezas(st);
						
					break;
				
				case 2:
						System.out.println("");
						Metodos.crearPedido(st,userCod);
						System.out.println("");

						break;
					
				case 3:
					
						System.out.println("");
						Metodos.mostrarCesta(st);
						System.out.println("");
						
					break;
					
				case 4:
						System.out.println("");
						menuDatosPersonales(st,userCod);
						System.out.println("");
						
					break;
					
				case 5:
						System.out.println("");
						Metodos.modificarUsuario(st,userCod);
						System.out.println("");
					break;
				
				case 6:
						System.out.println("");
						Metodos.piezaReferencia(st);
						System.out.println("");
					break;
					
				case 7:
					
						System.out.println("");
						Metodos.mostrarPedidos(st, userCod);
						System.out.println("");
					break;
				default:
					
					System.err.println("La opcion introducida no es valida.");
					
				}
				
				
			}while(opMenu!=0);
				
	}
	
	
	public static void menuDatosPersonales(Statement st, int userCod){
		
		//Variables menuUser
				int opMenu;
					
					do{
						
						System.out.println("MENU DATOS PERSONALES\n"
						                  +"=====================\n"
								          +"1-. Visualizar datos personales.\n"
								          +"2-. Modificar datos personales.\n"
						                  +"0-. Volver al menu principal.");
						System.out.print("Introduzca la opcion que desee:  ");
						opMenu=Excepciones.enteros();
						
						switch(opMenu){
				
						
						case 0:
								System.out.println("Volviendo al menu de cliente.");
							break;
						
						case 1:
								System.out.println("");
								Metodos.visualizarDatos(st, userCod);
								System.out.println("");
								
							break;
						
						case 2:
								
								System.out.println("");
								Metodos.modificarDatos(st,userCod);
								System.out.println("");
								
							break;
													
						default:
								
								System.out.println("");
								System.err.println("La opcion introducida no es valida.");
							
						}
						
						
					}while(opMenu!=0);
	}
	
	
	
}
