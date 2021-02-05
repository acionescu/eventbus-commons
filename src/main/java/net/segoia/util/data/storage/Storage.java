package net.segoia.util.data.storage;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * An interface that defines a storage
 * 
 * @author adi
 *
 */
public interface Storage {
    /**
     * The default separator between hierarchical storage entities <br>
     * If a key contains one or more path separators, a storage hierarchy will be created, with the last item of the
     * path being used as file key
     */
    public static final char PATH_SEPARATOR = '/';

    public static final String PATH_SEPARATOR_STRING = "/";

    /**
     * This is used to replace the underlying file system separator in case it doesn's match our default path separator
     * <br>
     * 
     * This is required in order not to allow storage clients to inject hierarchy in other ways then by using the
     * defined PATH_SEPARATOR
     * 
     */
    public static final char LOGIC_SEPARATOR = '_';

    /**
     * Returns a unique id for this storage
     * 
     * @return
     */
    public String getId();

    /**
     * Creates a file and its path and returns the OutputStream to write to it
     * 
     * @param key
     * @return
     * @throws StorageEntityExistsException
     */
    public OutputStream create(String key) throws StorageEntityExistsException, StorageException;

    public OutputStream createLeaf(String key) throws StorageEntityExistsException, StorageException;

    /**
     * Opens a file for update. It creates it if missing. Returns the OutputStream to append to it
     * 
     * @param key
     * @return
     */
    public OutputStream openForUpdate(String key) throws StorageException;

    /**
     * Deletes the file. If exists, returns true, otherwise false
     * 
     * @param key
     * @return
     */
    public boolean delete(String key);

    /**
     * Test if the file exists.
     * 
     * @param key
     * @return
     */
    public boolean exists(String key);

    /**
     * Opens the file for read. Throws {@link StorageMissingException} if missing
     * 
     * @param key
     * @return
     * @throws StorageException TODO
     */
    public InputStream openForRead(String key) throws StorageException;

    /**
     * Lists the keys in this storage
     * 
     * @return
     */
    public String[] list();

    /**
     * Lists the entities with more info
     * 
     * @return
     */
    public StorageEntityInfo[] listEntities();

    /**
     * Creates a child storage directly under this storage
     * 
     * @param key
     * @return
     */
    public Storage createStorage(String key);

    /**
     * Returns the storage identified by the given key, representing a hierarchy of storages delimited by
     * PATH_SEPARATOR, relative to the current storage <br>
     * Throws a {@link StorageException} if the path does not exist or is not a directory
     * 
     * @param path
     * @return
     * @throws StorageException 
     */
    public Storage getStorage(String key) throws StorageException;

    /**
     * Creates a storage hierarchy in the order of the provided keys.
     * 
     * @param keys
     * @return
     */
    public Storage createStorageHierarchy(String... keys);

    /**
     * Tests if the entity identified by the key is actually a child storage instead of a data file
     * 
     * @param key
     * @return
     */
    public boolean isStorage(String key);

    /**
     * This methods needs to extract a hierarchy from the key, if exists. <br>
     * Basically, the implementation needs to split the key by the default path separator
     * 
     * @param key
     * @return
     */
    public String[] extractHierarchy(String key);
    
    /**
     * Returns a storage entity for key
     * @param key
     * @return
     */
    public StorageEntity getStorageEntity(String key) throws StorageException;
    
    /**
     * Returns the relative path of the given key to this storage in a standard format
     * <br>
     * Can be used to compare if two keys are pointing to the same storage entity
     * @param key
     * @return
     * @throws StorageException if the key has an invalid format
     */
    public String getRelativePath(String key) throws StorageException;

}
