package net.segoia.event.eventbus;

import java.util.HashMap;
import java.util.Map;

public class SharedDataStore {
    private Map<String, SharedDataContext> dataContexts = new HashMap<>();

    public SharedDataContext removeDataContext(String key) {
	return dataContexts.remove(key);
    }

    public SharedDataContext getDataContext(String key, boolean create) {
	SharedDataContext dc = dataContexts.get(key);
	if (dc == null && create) {
	    dc = new SharedDataContext();
	    dataContexts.put(key, dc);
	}
	return dc;
    }

}
