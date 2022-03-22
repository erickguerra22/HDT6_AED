/**
 * 
 */
package Files;

import java.io.IOException;
import java.util.*;

/**
 * @author erick
 *
 */
public class Store {
	private Map<String, ArrayList<String>> inventory;
	private Map<String, ArrayList<String>> collection;
	
	public Store(int implementation) {
		MapFactory<String,ArrayList<String>> mapFactory = new MapFactory<>(implementation); //Se determina el tipo de Map a instanciar
		inventory = mapFactory.getInstance();
		collection = mapFactory.getInstance();
	}
	
	public boolean findFile() {
		String[] fileContent = null;
		boolean repeat = true;
		try { //Se encuentra el archivo
			fileContent = FileController.readFile();
			fileToMap(fileContent);
			return true;
		} catch (IOException e) { //Si no se encuentra el archivo
			return false;
		}
	}
	
	public void fileToMap(String[] fileContent) {
		for (String row : fileContent) {
			String[] elements = row.split("\\|");
			String cathegory = elements[0].trim();
			String product = elements[1].trim();
			ArrayList<String> products = new ArrayList<String>();
			if(inventory.containsKey(cathegory))
				products = inventory.get(cathegory);
			products.add(product);
			inventory.put(cathegory, products);
		}
	}
	
	public void addToCollection(String cathegory, String product) {
		ArrayList<String> products = new ArrayList<String>();
		if(collection.containsKey(cathegory))
			products = collection.get(cathegory);
		products.add(product);
		collection.put(cathegory, products);
	}
	
	public int productQuantity(ArrayList<String> products, String product) {
		int quantity = 0;
		for(String p:products) {
			if(p.equals(product))
				quantity++;
		}
		return quantity;
	}
	
	public Map<String, ArrayList<String>> getInventory() {
		return inventory;
	}
	
	public Map<String, ArrayList<String>> getCollection() {
		return collection;
	}
	
}
