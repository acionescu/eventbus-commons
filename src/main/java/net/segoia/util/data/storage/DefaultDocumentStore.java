package net.segoia.util.data.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class DefaultDocumentStore<D> implements DocumentStore<D> {
    /**
     * Underlying data store
     */
    private DataStore dataStore;

    /**
     * The translator used to serialize/deserialize this object
     */
    private DocumentTranslator<D> translator;

    /**
     * Generates a unique key for a document
     */
    private DocumentKeyGenerator<D> keyGenerator;

    private Class<D> type;

    public DefaultDocumentStore(DataStore dataStore, DocumentTranslator<D> translator,
	    DocumentKeyGenerator<D> keyGenerator, Class<D> type) {
	super();
	this.dataStore = dataStore;
	this.translator = translator;
	this.keyGenerator = keyGenerator;
	this.type = type;
    }

    @Override
    public D get(String key) {
	try {
	    byte[] data = dataStore.get(key);
	    return translator.deserialize(data);
	} catch (DataTooLargeException | StorageMissingException | StorageException e) {
	    return null;
	}
    }

    @Override
    public boolean delete(String key) {
	return dataStore.delete(key);
    }

    @Override
    public String create(D document) throws StorageEntityExistsException, StorageException {
	String key = keyGenerator.getKey(document);
	dataStore.create(key, translator.serialize(document));
	return key;
    }

    @Override
    public D update(String key, D document) throws StorageException {
	/* delete the old document and create a new one under the same key */
	dataStore.delete(key);
	try {
	    dataStore.create(key, translator.serialize(document));
	} catch (StorageEntityExistsException e) {
	    throw new StorageException("Unexpected exception", e);
	}
	return document;
    }

    @Override
    public List<D> list() {
	String[] keys = dataStore.list();
	List<D> result = new ArrayList<>();

	for (String key : keys) {
	    result.add(get(key));
	}
	return result;
    }

    @Override
    public DocumentQuery<D> createQuery() {
	return DocumentQuery.create(type);
    }

    @Override
    public Collection<D> list(DocumentQuery<D> query) {
	Comparator<D> comparator = query.getComparator();
	Collection<D> result;
	if (comparator != null) {
	    result = new TreeSet<>(comparator);
	} else {
	    result = new ArrayList<>();
	}

	DocumentFilter<D> filter = query.getFilter();

	int offset = query.getOffset();
	int limit = query.getLimit();

	String[] keys = dataStore.list();

	if (keys.length <= offset) {
	    return result;
	}

	int index = 0;

	for (String key : keys) {
	    D d = get(key);
	    boolean accepted = filter == null || filter.accept(new DocumentContext<>(key, d, this, dataStore));
	    if (accepted) {
		result.add(d);
	    }
	    index++;
	    if(limit > 0 && index >= limit) {
		break;
	    }
	}
	
	return result;
    }

    @Override
    public String[] listKeys() {
	return dataStore.list();
    }

    @Override
    public int delete(String... keys) {
	int deletedCount=0;
	for(String key : keys) {
	    boolean deleted = delete(key);
	    if(deleted) {
		deletedCount++;
	    }
	}
	return deletedCount;
    }
}
