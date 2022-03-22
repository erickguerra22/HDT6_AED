package Files;

import java.util.*;

public class MapFactory<T,V> {
	
	public MapFactory() {
		
	}
	
	public Map<T,V> getMap(int implementation) {
		if(implementation == 0)
			return new HashMap<T,V>();
		else if(implementation == 1)
			return new TreeMap<T,V>();
		else if(implementation == 2)
			return new LinkedHashMap<T,V>();
		else
			return null;
	}
}
