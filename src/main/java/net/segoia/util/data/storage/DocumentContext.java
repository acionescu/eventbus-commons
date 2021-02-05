package net.segoia.util.data.storage;

public class DocumentContext<D> {
    private String key;
    private D document;
    private DocumentStore<D> documentStore;
    private DataStore dataStore;

    public DocumentContext(String key, D document, DocumentStore<D> documentStore, DataStore dataStore) {
	super();
	this.key = key;
	this.document = document;
	this.documentStore = documentStore;
	this.dataStore = dataStore;
    }

    public String getKey() {
	return key;
    }

    public void setKey(String key) {
	this.key = key;
    }

    public D getDocument() {
	return document;
    }

    public void setDocument(D document) {
	this.document = document;
    }

    public DocumentStore<D> getDocumentStore() {
	return documentStore;
    }

    public void setDocumentStore(DocumentStore<D> documentStore) {
	this.documentStore = documentStore;
    }

    public DataStore getDataStore() {
	return dataStore;
    }

    public void setDataStore(DataStore dataStore) {
	this.dataStore = dataStore;
    }

}
