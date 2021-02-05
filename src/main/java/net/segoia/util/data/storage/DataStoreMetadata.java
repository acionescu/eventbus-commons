package net.segoia.util.data.storage;

import net.segoia.event.eventbus.services.ObjectAccessRights;

/**
 * Defines the configuration and how a data store can be accessed
 * 
 * @author adi
 *
 */
public class DataStoreMetadata {
    /**
     * An id for this store
     */
    private String id;
    /**
     * The actual key under which this data store will be saved
     */
    private String key;

    /**
     * Working configuration of the store
     */
    private DataStoreConfig config;
    
    /**
     * Client nodes access rights on this data store
     */
    private ObjectAccessRights accessRights;

    public DataStoreMetadata() {
	super();
	// TODO Auto-generated constructor stub
    }

    public DataStoreMetadata(String id, String key, DataStoreConfig config) {
	super();
	this.id = id;
	this.key = key;
	this.config = config;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getKey() {
	return key;
    }

    public void setKey(String key) {
	this.key = key;
    }

    public DataStoreConfig getConfig() {
	return config;
    }

    public void setConfig(DataStoreConfig config) {
	this.config = config;
    }

    public ObjectAccessRights getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(ObjectAccessRights accessRights) {
        this.accessRights = accessRights;
    }

}
