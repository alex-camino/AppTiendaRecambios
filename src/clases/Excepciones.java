package clases;

import java.util.InputMismatchException;

public class Excepciones {
	
	public static int enteros(){
		
		int numero=0;
		boolean error=false;
		
		
		do{
			
			try{
				error=false;
				numero=Main.lector.nextInt();
				Main.lector.nextLine();
				
			}catch(InputMismatchException e){
				
				error=true;
				System.err.println("Tiene que introducir numeros enteros");
				Main.lector.nextLine();
			}
		}while(error);
		
		return numero;
		
	}
	
	public static double decimales(){
		
		double numero=0;
		boolean error=false;
		
		
		do{
			
			try{
				error=false;
				numero=Main.lector.nextDouble();
				Main.lector.nextLine();
				
			}catch(InputMismatchException e){
				
				error=true;
				System.err.println("Tiene que introducir numeros decimales");
				Main.lector.nextLine();
			}
		}while(error);
		
		return numero;
		
	}
	

}
