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
		MapFactory<String,ArrayList<String>> mapFactory = new MapFactory<>();
		inventory = mapFactory.getMap(implementation);
		collection = mapFactory.getMap(implementation);
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
	
	public void saveInventory() {
		String text = "Categoría | Producto\n";
		for(String k : collection.keySet()) {
			for(String p : collection.get(k)) {
				text += k + " | " + p +"\n";
			}
		}
		mapToFile(text,"");
	}
	
	public void saveCollection() {
		String text = "Categoría | Producto | Cantidad\n";
		for(String k : collection.keySet()) {
			for(String p : collection.get(k)) {
				int quantity = productQuantity(collection.get(k), p);
				text += k + " | " + p + " | " + quantity;
			}
		}
		mapToFile(text,"collection.txt");
	}
	
	public void mapToFile(String text, String fileName) {
		try {
			FileController.writeFile(text, fileName);
		} catch (IOException e) {
			System.out.println("Ha ocurrido un error al guardar la información.");
			e.printStackTrace();
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
	
	public ArrayList<String> sortedProducts(ArrayList<String> products){
		Collections.sort(products, new SortAlphabetically());
		return products;
	}
	
	public ArrayList<String> sortedKeys(Map<String, ArrayList<String>> map){
		ArrayList<String> keys = new ArrayList<String>(map.keySet());
		Collections.sort(keys, new SortAlphabetically());
		return keys;
	}
	
	public Map<String, ArrayList<String>> getInventory() {
		return inventory;
	}
	
	public Map<String, ArrayList<String>> getCollection() {
		return collection;
	}
	
}

class SortAlphabetically implements Comparator<String>{

	@Override
	public int compare(String o1, String o2) {
		return o1.compareTo(o2);
	}
	
}
