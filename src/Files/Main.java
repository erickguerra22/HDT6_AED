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
						\n1. Agregar producto a mi colecci�n.
						2. Mostrar la categor�a de un producto espec�fico.
						3. Mostrar mi colecci�n.
						4. Mostrar mi colecci�n ordenada por categor�a.
						5. Mostrar inventario.
						6. Mostrar inventario ordenado por categor�a.
						7. Cambiar implementaci�n.
						8. Salir""";
				int option = pregunta(menu, 8, scan);
				String cathegory = "";
				String product = "";
				int quantity = 0;
				boolean repeat = true;
				switch(option) { 
				case 1:
					while(repeat) {
						System.out.println("Ingrese la categor�a del producto que desea agregar:");
						cathegory = scan.nextLine();
						if(store.getInventory().containsKey(cathegory)) {
							repeat = false;
							ArrayList<String> products = store.getInventory().get(cathegory);
							System.out.println("A continuaci�n se muestra una lista de los productos disponibles en esta categoria");
							for(String p:products)
								System.out.println(p);
							System.out.println("Escriba el nombre completo del producto que desea agregar:");
							product = scan.nextLine();
							if(products.contains(product)) {
								System.out.println("Producto disponible.");
								quantity = numeroEntero("Indique la cantidad que desea adquirir",scan);
								while(quantity>0) {
									store.addToCollection(cathegory,product);
									quantity--;
								}
								System.out.println("Productos agregados a la colecci�n correctamente");
							}else
								System.out.println("Error: El producto indicado no forma parte de esta colecci�n.");
						}else
							System.out.println("Error: La categor�a indicada no est� disponible, intente nuevamente");
					}
					break;
				case 2:
					System.out.println("Ingrese el nombre completo del producto del que desa conocer su categor�a:");
					product = scan.nextLine();
					cathegory = null;
					for(String k : store.getInventory().keySet()) {
						if(store.getInventory().get(k).contains(product))
							cathegory = k;
					}
					if(cathegory == null)
						System.out.println("El producto ingresado no pertenece a ninguna categor�a existente.");
					else
						System.out.println("El producto pertenece a la categor�a: \""+cathegory+"\"");
					break;
				case 3:
					if(store.getCollection().isEmpty())
						System.out.println("Actualmente no existen productos en la colecci�n.");
					else {
						System.out.println("Categor�a | Producto | Cantidad");
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
					if(store.getCollection().isEmpty())
						System.out.println("Actualmente no existen productos en la colecci�n.");
					else {
						System.out.println("Categor�a | Producto | Cantidad");
						for(String k : store.sortedKeys(store.getCollection())) {
							cathegory = k;
							if(!store.getCollection().get(k).isEmpty()) {
								ArrayList<String> products = new ArrayList<String>();
								for(String p : store.sortedProducts(store.getCollection().get(k))) {
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
				case 5:
					if(store.getInventory().isEmpty())
						System.out.println("Actualmente no existen productos en el inventario.");
					else {
						System.out.println("Categor�a | Producto");
						for(String k : store.getInventory().keySet()) {
							cathegory = k;
							if(!store.getInventory().get(k).isEmpty()) {
								ArrayList<String> products = new ArrayList<String>();
								for(String p : store.sortedProducts(store.getInventory().get(k))) {
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
					if(store.getInventory().isEmpty())
						System.out.println("Actualmente no existen productos en el inventario.");
					else {
						System.out.println("Categor�a | Producto");
						for(String k : store.sortedKeys(store.getInventory())) {
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
				case 7:
					System.out.println("Eliminando el contenido de la colecci�n...");
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
			System.out.println("\nRepuesta no valida. Ingrese solamente numeros.\n");
			num = numeroEntero(pregunta, scan);
		}
		return num;
	}
}
