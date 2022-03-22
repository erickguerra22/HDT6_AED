package Files;

import java.util.ArrayList;
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
		boolean end = false;
		
		System.out.println("Bienvenido a la tienda \"IMPERA\"");
		while(!end) { 
			String menu = """
					\nSeleccione la implementacion que desea utilizar:
					1. HashMap
					2. TreeMap
					3. LinkedHashMap""";
			int implementation = pregunta(menu, 3, scan);
			Store store = new Store(implementation);
			while(!store.findFile()) {
				System.out.println("\nArchivo no encontrado.\nPor favor, asegurese de que el archivo inventario.txt sea valido y se encuentre en la carpeta donde se encuentra el programa.");
				System.out.println("Presione enter para volver a buscar el archivo.");
				scan.nextLine();
			}				
			System.out.println("Archivo encontrado.");
			menu = """
					\n1. Agregar producto a mi colección.
					2. Mostrar la categoría de un producto específico.
					3. Mostrar mi colección.
					4. Mostrar mi colección ordenada por categoría.
					5. Mostrar inventario.
					6. Mostrar inventario ordenado por categoría.
					7. Salir""";
			int option = pregunta(menu, 7, scan);
			String cathegory = "";
			String product = "";
			int quantity = 0;
			boolean repeat = true;
			switch(option) { 
			case 1:
				while(repeat) {
					System.out.println("Ingrese la categoría del producto que desea agregar:");
					cathegory = scan.nextLine();
					if(store.getInventory().containsKey(cathegory)) {
						repeat = false;
						ArrayList<String> products = store.getInventory().get(cathegory);
						System.out.println("A continuación se muestra una lista de los productos disponibles en esta categoria");
						for(String p:products)
							System.out.println(p);
						System.out.println("Escriba el nombre completo del producto que desea agregar:");
						product = scan.nextLine();
						if(products.contains(product)) {
							store.addToCollection(cathegory,product);
							System.out.println("Producto agregado a la colección correctamente");
						}else
							System.out.println("Error: El producto indicado no forma parte de esta colección.");
					}else
						System.out.println("Error: La categoría indicada no está disponible, intente nuevamente");
				}
				break;
			case 2:
				System.out.println("Ingrese el nombre completo del producto del que desa conocer su categoría:");
				product = scan.nextLine();
				cathegory = null;
				for(String k : store.getInventory().keySet()) {
					if(store.getInventory().get(k).contains(product))
						cathegory = k;
				}
				if(cathegory == null)
					System.out.println("El producto ingresado no pertenece a ninguna categoría existente.");
				else
					System.out.println("El producto pertenece a la categoría: \""+cathegory+"\"");
				break;
			case 3:
				if(store.getCollection().isEmpty())
					System.out.println("Actualmente no existen productos en la colección.");
				else {
					System.out.println("Categoría | Producto | Cantidad");
					for(String k : store.getCollection().keySet()) {
						cathegory = k;
						if(!store.getCollection().get(k).isEmpty()) {
							ArrayList<String> products = new ArrayList<String>();
							for(String p : store.getCollection().get(k)) {
								if(!products.contains(p)) {
									product = p;
									products.add(product);
									quantity = store.productQuantity(store.getCollection().get(k), product);
									System.out.println(cathegory + " | " + product + " | "+quantity);
								}
							}
						}
					}
				}
				break;
			case 4:
				break;
			case 5:
				if(store.getInventory().isEmpty())
					System.out.println("Actualmente no existen productos en el inventario.");
				else {
					System.out.println("Categoría | Producto");
					for(String k : store.getInventory().keySet()) {
						cathegory = k;
						if(!store.getInventory().get(k).isEmpty()) {
							ArrayList<String> products = new ArrayList<String>();
							for(String p : store.getInventory().get(k)) {
								if(!products.contains(p)) {
									product = p;
									products.add(product);
									System.out.println(cathegory + " | " + product);
								}
							}
						}
					}
				}
				break;
			case 6:
				break;
			case 7: //Finaliza el programa
				System.out.println("Gracias por utilizar el programa!"); 
				end = true;
				break;
			default: //Opcion no valida
				System.out.println("Opcion no valida");
				break;
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
}
