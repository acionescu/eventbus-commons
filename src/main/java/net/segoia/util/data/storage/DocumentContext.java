/**
 * eventbus-commons - Core classes for net.segoia.event-bus framework
 * Copyright (C) 2016  Adrian Cristian Ionescu - https://github.com/acionescu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
