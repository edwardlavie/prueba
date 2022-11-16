import java.util.Random;
import java.util.Scanner;

public class Juego {
	
	public static Scanner entrada = new Scanner(System.in);
    public static Random r = new Random();
	
	public static void main(String[] args) {
		Juego miJuego = new Juego();
		miJuego.jugar();
	}
	
	private void jugar() {
		String opcion = "";
        int count = 0;
        int cantFallos = 0;
        int cantVerdes, cantRojos;
        int limite = 10;
        int limiteFallos = 3;
        
        int[] codigo = crearCodigo();
        int[] codIngresado = new int[4];
        int[] codVerde = new int[4];
        
        boolean primero = true;
        boolean fin = false;
        
        // mostrarCodigo(codigo); 
        
        do {
        	if (primero) {
        		System.out.println("Bienvenido/a, ingrese su código:");
        		primero = false;
        	} else {
        		System.out.println("Ingrese un nuevo código:");
        	}
        	
        	opcion = entrada.next();
        	
        	if (validarOpcion(opcion)) {
        		cargarCodigoIngresado(opcion, codIngresado);
        		//mostrarCodigo(codIngresado);
        		
        		cantVerdes = validarVerdes(codigo, codIngresado, codVerde);
            	
            	if (cantVerdes == 4) {
            		System.out.println("Felicitaciones, has adivinado el código!");
            		System.out.println("Fin");
            		fin = true;
            	} else {
            		cantRojos = validarRojos(codigo, codIngresado, codVerde);
            		System.out.println("Verdes: " + cantVerdes + " - Rojos: " + cantRojos);
            	}
            	
                count++;
        	} else {
        		cantFallos++;
        		
        		if (cantFallos == limiteFallos) {
        			System.out.println("Lo siento ha alcanzado la cantidad limte de ingresos fallidos");
        			System.out.println("Fin");
        			fin = true;
        		}
        	}
        	
        	if (count == limite) {
        		System.out.println("Lo siento has alcanzado el limite de intentos");	
        	}
        	
        } while (count < limite && !fin);		
	}
	
	private int validarVerdes(int[] codigo, int[] codIngresado, int[] codVerde) {
		int cantOk = 0;
		int valCod, valCodIngresado;
		
		for (int i=0; i<codigo.length; i++) {
			valCod 		    = codigo[i];
			valCodIngresado = codIngresado[i];
			
			if (valCod == valCodIngresado) {
				cantOk++;
				codVerde[i] = valCod;
			} else {
				codVerde[i] = -1;
			}
		}
		
		return cantOk;
	}
	
	private int validarRojos(int[] codigo, int[] codIngresado, int[] codVerde) {
		int valVerde, valIngresado;
		int[] codRojo = {-1, -1, -1, -1};
		int resultadoRojos = 0;
		
		for (int i=0; i<codVerde.length; i++) {
			valVerde = codVerde[i];
			
			if (valVerde < 0) {
				valIngresado = codIngresado[i];
				
				int cantTotal = cantAparece(valIngresado, codigo);
				int cantVerde = cantAparece(valIngresado, codVerde);
				int cantRojo  = cantAparece(valIngresado, codRojo);
				
				int cantDetectados = cantVerde + cantRojo;
				
				if (cantDetectados < cantTotal) {
					resultadoRojos++;
					codRojo[i] = valIngresado;
				} else {
					codRojo[i] = -1;
				}				
			}
		}
		
		return resultadoRojos;
	}
	
	private int cantAparece(int val, int[] codigo) {
		int cant = 0;
		int valCod;
		
		for (int i=0; i<codigo.length; i++) {
			valCod = codigo[i];
			
			if (val == valCod) {
				cant++;
			}
		}
		
		return cant;
	}
	
	private void cargarCodigoIngresado(String opcion, int[] codigoIngresado) {
		String dig;
		int val;
		
		for (int i=0; i<4; i++) {
			dig = opcion.substring(i, i+1);
			val = Integer.parseInt(dig);
			codigoIngresado[i] = val;
		}
	}
	
	private boolean validarOpcion(String opcion) {
		boolean ok = true;
		int largo = opcion.length();
		String dig;
		int candidato;
		
		if (largo > 4) {
			System.out.println("El código supera el largo de 4 dígitos");
			ok = false;
		}
		
		if (largo < 4) {
			System.out.println("El código debe tener 4 dígitos");
			ok = false;
		}
		
		for (int i=0; i<4; i++) {
			dig = opcion.substring(i, i+1);
			
			try {
				candidato = Integer.parseInt(dig);
				
				if (candidato > 6) {
					System.out.println("El código solo adminte números del 1 - 6");
					ok = false;
					break;
				}
				
			} catch (NumberFormatException e) {
				System.out.println("El código solo adminte números");
				ok = false;
				break;
			}
		}
		
		return ok;
	}
	
//	private void mostrarCodigo(int[] codigo) {
//		for (int i=0; i<codigo.length; i++) {
//			System.out.print(codigo[i]);
//		}
//		
//		System.out.println("");
//	}
	
	private int[] crearCodigo() {
		int[] codigo = new int[4];
		
        for (int i=0; i< codigo.length; i++) {
            codigo[i] = r.nextInt(6) + 1;
        }
        
        return codigo;
    }		
}