package net.segoia.util.data.storage;

import java.io.InputStream;

public interface DataStore extends Storage{
    
    /**
     * Creates a new file with the given data
     * @param key
     * @param data
     * @return
     */
    public void create(String key, byte[] data) throws StorageEntityExistsException, StorageException;
    
    /**
     * Creates a new file and path from the source input stream
     * @param key
     * @param source
     * @return
     */
    public void create(String key, InputStream source) throws StorageEntityExistsException, StorageException;
    
    /**
     * Creates a new file if path exists from the source input stream
     * @param key
     * @param source
     * @return
     */
    public void createLeaf(String key, InputStream source) throws StorageEntityExistsException, StorageException;
    
    /**
     * Gets the data for the key. If the data is too large throws a {@link DataTooLargeException}
     * @param key
     * @return
     * @throws DataTooLargeException
     */
    public byte[] get(String key) throws DataTooLargeException, StorageMissingException, StorageException;

}
