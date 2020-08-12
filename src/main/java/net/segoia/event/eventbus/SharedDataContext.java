package net.segoia.event.eventbus;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides a way for local agents to share data when processing an event
 * 
 */
public class SharedDataContext {
    /**
     * This allows storing only one object per type
     */
    private Map<Class<?>, Object> dataByType;

    public void addDataForType(Object obj) {
	if (obj != null) {
	    if (dataByType == null) {
		dataByType = new HashMap<>();
	    }
	    dataByType.put(obj.getClass(), obj);
	}
    }

    public <T> T getDataForType(Class<T> clazz) {
	if (dataByType != null) {
	    return (T) dataByType.get(clazz);
	}
	return null;
    }
}
