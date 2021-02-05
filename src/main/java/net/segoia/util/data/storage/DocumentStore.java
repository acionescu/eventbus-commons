package net.segoia.util.data.storage;

import java.util.Collection;
import java.util.List;

public interface DocumentStore<D> {
    
    D get(String key);
    
    boolean delete(String key);
    
    /**
     * Creates the document and returs the key
     * @param document
     * @return
     */
    String create(D document) throws StorageEntityExistsException, StorageException;
    
    D update(String key, D document) throws StorageException;
    
    String[] listKeys();
    
    /**
     * List all documents from the store
     * @return
     */
    List<D> list();
    
    DocumentQuery<D> createQuery();
    
    Collection<D> list(DocumentQuery<D> query);
    
    int delete(String... keys);
}
