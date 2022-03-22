package Files;

import java.util.*;

public class MapFactory<T,V> {
	private Map<T,V> myMap;
	
	public MapFactory(int implementation) {
		if(implementation == 0)
			myMap = new HashMap<T,V>();
		else if(implementation == 1)
			myMap = new TreeMap<T,V>();
		else if(implementation == 2)
			myMap = new LinkedHashMap<T,V>();
	}
	
	public Map<T,V> getInstance(){
		return this.myMap;
	}	
}
