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
	
	public String getProducts(String cathegory) {
		String listProducts = "";
		ArrayList<String> products = inventory.get(cathegory);
		for(String p:products)
			listProducts += p + "\n";
		return listProducts;
	}
	
	public void addProduct(String cathegory, String product, int quantity) {
		if(inventory.containsKey(cathegory)) {
			String products = getProducts(cathegory);
			if(products.contains(product)) {
				while(quantity>0) {
					ArrayList<String> productsList = new ArrayList<String>();
					if(collection.containsKey(cathegory))
						productsList = collection.get(cathegory);
					productsList.add(product);
					collection.put(cathegory, productsList);
					quantity--;
				}
				System.out.println("Productos agregados a la colección correctamente");
			}else
				System.out.println("Error: El producto indicado no forma parte de esta categoría.");
		}else
			System.out.println("Error: La categoría indicada no está disponible.");
	}
	
	public String getCathegory(String product) {
		String cathegory = null;
		for(String k : inventory.keySet()) {
			if(inventory.get(k).contains(product))
				cathegory = k;
		}
		return cathegory;
	}
	
	public String getCollection() {
		String listProducts = "";
		String cathegory;
		String product;
		int quantity;
		if(collection.isEmpty())
			return "Actualmente no existen productos en la colección.";
		else {
			listProducts += "Categoría | Producto | Cantidad\n";
			for(String k : collection.keySet()) {
				cathegory = k;
				if(!collection.get(k).isEmpty()) {
					ArrayList<String> products = new ArrayList<String>();
					for(String p : collection.get(k)) {
						if(!products.contains(p)) {
							product = p;
							products.add(product);
							quantity = productQuantity(collection.get(k), product);
							listProducts += cathegory + " | " + product + " | "+quantity+ "\n";
						}
					}
				}
			}
		}
		return listProducts;
	}	
	
	public String getCollectionSorted() {
		String sortedList = "";
		String cathegory;
		String product;
		int quantity;
		if(collection.isEmpty())
			return "Actualmente no existen productos en la colección.";
		else {
			sortedList += "Categoría | Producto | Cantidad\n";
			for(String k : sortedKeys(collection)) {
				cathegory = k;
				if(!collection.get(k).isEmpty()) {
					ArrayList<String> products = new ArrayList<String>();
					for(String p : sortedProducts(collection.get(k))) {
						if(!products.contains(p)) {
							product = p;
							products.add(product);
							quantity = productQuantity(collection.get(k), product);
							sortedList += cathegory + " | " + product + " | "+quantity+"\n";
						}
					}
				}
			}
		}
		return sortedList;
	}
	
	public String getInventory() {
		String inventoryList = "";
		String cathegory;
		String product;
		if(inventory.isEmpty())
			return "Actualmente no existen productos en el inventario.";
		else {
			inventoryList += "Categoría | Producto\n";
			for(String k : inventory.keySet()) {
				cathegory = k;
				if(!inventory.get(k).isEmpty()) {
					ArrayList<String> products = new ArrayList<String>();
					for(String p : inventory.get(k)) {
						if(!products.contains(p)) {
							product = p;
							products.add(product);
							inventoryList += cathegory + " | " + product+"\n";
						}
					}
				}
			}
		}
		return inventoryList;
	}
	
	public String getInventorySorted() {
		String productList = "";
		String cathegory;
		String product;
		if(inventory.isEmpty())
			return "Actualmente no existen productos en el inventario.";
		else {
			productList += "Categoría | Producto\n";
			for(String k : sortedKeys(inventory)) {
				cathegory = k;
				if(!inventory.get(k).isEmpty()) {
					ArrayList<String> products = new ArrayList<String>();
					for(String p : sortedProducts(inventory.get(k))) {
						if(!products.contains(p)) {
							product = p;
							products.add(product);
							productList += cathegory + " | " + product+"\n";
						}
					}
				}
			}
		}
		return productList;
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
	
	public int productQuantity(ArrayList<String> products, String product) {
		int quantity = 0;
		for(String p:products) {
			if(p.equals(product))
				quantity++;
		}
		return quantity;
	}
	
	public ArrayList<String> sortedProducts(ArrayList<String> products){
		Collections.sort(products, new SortByCathegory());
		return products;
	}
	
	public ArrayList<String> sortedKeys(Map<String, ArrayList<String>> map){
		ArrayList<String> keys = new ArrayList<String>(map.keySet());
		Collections.sort(keys, new SortByCathegory());
		return keys;
	}
}

class SortByCathegory implements Comparator<String>{

	@Override
	public int compare(String o1, String o2) {
		return o1.compareTo(o2);
	}
	
}
