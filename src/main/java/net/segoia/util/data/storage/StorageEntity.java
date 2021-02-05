package net.segoia.util.data.storage;

import java.io.InputStream;

/**
 * Encapsulates a storage entity
 * 
 * @author adi
 *
 */
public class StorageEntity {
    /**
     * Includes the full path, relative to the parent storage
     */
    private String fullKey;
    private StorageEntityInfo info;
    private Storage parentStorage;

    public StorageEntity(Storage parentStorage, String fullKey, StorageEntityInfo info) {
	super();
	this.parentStorage = parentStorage;
	this.fullKey = fullKey;
	this.info = info;
    }

    public StorageEntityInfo getInfo() {
	return info;
    }

    public InputStream openForRead() throws StorageException {
	return parentStorage.openForRead(fullKey);
    }

    public String getFullKey() {
	return fullKey;
    }

}
