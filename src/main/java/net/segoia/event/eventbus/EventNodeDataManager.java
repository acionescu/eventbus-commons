package net.segoia.event.eventbus;

public class EventNodeDataManager {
    /**
     * Provides shared data storage by event id
     */
    private SharedDataStore eventDataStore = new SharedDataStore();
    
    
    public SharedDataContext getEventDataStore(String eventId, boolean create) {
	return eventDataStore.getDataContext(eventId, create);
    }
    
    public SharedDataContext removeEventDataStore(String eventId) {
	return eventDataStore.removeDataContext(eventId);
    }
    
}
