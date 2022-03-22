package Files;

import java.util.Scanner;

/**
 * @author erick
 * @version 21/03/2022
 * 
 */
public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean mainEnd = false;
		
		System.out.println("Bienvenido a la tienda \"IMPERA\"");
		while(!mainEnd) {
			boolean end = false;
			String menu = """
					\nSeleccione la implementacion que desea utilizar:
					1. HashMap
					2. TreeMap
					3. LinkedHashMap""";
			int implementation = pregunta(menu, 3, scan)-1;
			Store store = new Store(implementation);
			while(!store.findFile()) {
				System.out.println("\nArchivo no encontrado.\nPor favor, asegurese de que el archivo inventario.txt sea valido y se encuentre en la carpeta donde se encuentra el programa.");
				System.out.println("Presione enter para volver a buscar el archivo.");
				scan.nextLine();
			}				
			System.out.println("\nArchivo encontrado.");
			while(!end) {
				menu = """
						\n1. Agregar producto a mi colección.
						2. Mostrar la categoría de un producto específico.
						3. Mostrar mi colección.
						4. Mostrar mi colección ordenada por categoría.
						5. Mostrar inventario.
						6. Mostrar inventario ordenado por categoría.
						7. Cambiar implementación.
						8. Salir""";
				int option = pregunta(menu, 8, scan);
				String cathegory = "";
				String product = "";
				String products = "";
				int quantity = 0;
				boolean repeat = true;
				switch(option) { 
				case 1:
					System.out.println("Productos disponibles:");
					System.out.println(store.getInventorySorted());
					System.out.println("Ingrese la categoría del producto que desea agregar:");
					cathegory = scan.nextLine();
					System.out.println("Escriba el nombre completo del producto que desea agregar:");
					product = scan.nextLine();
					quantity = numeroEntero("Indique la cantidad que desea adquirir",scan);
					store.addProduct(cathegory, product, quantity);
					break;
				case 2:
					System.out.println("Ingrese el nombre completo del producto del que desa conocer su categoría:");
					product = scan.nextLine();
					cathegory = store.getCathegory(product);
					if(cathegory == null)
						System.out.println("El producto ingresado no pertenece a ninguna categoría existente.");
					else
						System.out.println("El producto pertenece a la categoría: \""+cathegory+"\"");
					break;
				case 3:
					System.out.println(store.getCollection());
					break;
				case 4:
					System.out.println(store.getCollectionSorted());
					break;
				case 5:
					System.out.println(store.getInventory());
					break;
				case 6:
					System.out.println(store.getInventorySorted());
					break;
				case 7:
					System.out.println("Eliminando el contenido de la colección...");
					end = true;
					break;
				case 8: //Finaliza el programa
					System.out.println("Gracias por utilizar el programa!"); 
					end = true;
					mainEnd = true;
					break;
				default: //Opcion no valida
					System.out.println("Opcion no valida");
					break;
				}				
			}
		}
	}

	public static int pregunta(String pregunta, int opciones, Scanner scan)
	  {
	      boolean bucle = true;
	      int respuesta = 0;
	      try 
	      {
	          while(bucle)
	          {
	              System.out.println(pregunta);
	              respuesta = scan.nextInt();
	              scan.nextLine();
	              if(respuesta > 0 && respuesta <= opciones) bucle = false;
	              else System.out.println("\nRepuesta no valida.\n");
	          }    
	      } catch (Exception e) {
	          System.out.println("\nRepuesta no valida. Ingrese solamente numeros.\n");
	          respuesta = pregunta(pregunta, opciones, scan);
	      }
	      return respuesta;
	  }
	
	public static int numeroEntero(String pregunta, Scanner scan) {
		boolean bucle = true;
		int num = 0;
		try 
		{
			while(bucle)
			{
				System.out.println(pregunta);
				num = scan.nextInt();
				scan.nextLine();
				if(num > 0) bucle = false;
				else System.out.println("\nRepuesta no valida.\n");
			}    
		} catch (Exception e) {
			scan.nextLine();
			System.out.println("\nRepuesta no valida. Ingrese solamente numeros.\n");
			num = numeroEntero(pregunta, scan);
		}
		return num;
	}
}
