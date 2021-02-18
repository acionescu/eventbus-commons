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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DefaultDataStore implements DataStore {
    /**
     * Configuration of this data store
     */
    private DataStoreConfig config = new DataStoreConfig();
    /**
     * The underlying storage implementation
     */
    private Storage storage;

    public DefaultDataStore() {
	super();
    }

    public DefaultDataStore(Storage storage, DataStoreConfig config) {
	super();
	this.storage = storage;
	this.config = config;
    }

    public DefaultDataStore(Storage storage) {
	super();
	this.storage = storage;
    }

    @Override
    public OutputStream create(String key) throws StorageEntityExistsException, StorageException {
	return storage.create(key);
    }

    @Override
    public OutputStream openForUpdate(String key) throws StorageException {
	return storage.openForUpdate(key);
    }

    @Override
    public boolean delete(String key) {
	return storage.delete(key);
    }

    @Override
    public boolean exists(String key) {
	return storage.exists(key);
    }

    @Override
    public InputStream openForRead(String key) throws StorageException {
	return storage.openForRead(key);
    }

    @Override
    public void create(String key, byte[] data) throws StorageEntityExistsException, StorageException {
	create(key, new ByteArrayInputStream(data));
    }

    @Override
    public void create(String key, InputStream source) throws StorageEntityExistsException, StorageException {
	/**
	 * Create the output storage and get output stream
	 */
	OutputStream out = create(key);

	try {
	    copyInputToOutput(source, out);
	}
	catch(StorageException e) {
	    throw new StorageException("Faield creating key " + key + " in storage " + storage.getId(), e);
	}
    }
    
    protected void copyInputToOutput(InputStream source, OutputStream out) throws StorageException {
	byte[] readBuff = new byte[config.getWriteBufferLength()];
	int bytesRead;
	try {
	    while ((bytesRead = source.read(readBuff)) != -1) {
		if (bytesRead > 0) {
		    out.write(readBuff, 0, bytesRead);
		}
	    }

	    /* call flush on output to make sure all data reaches the destination */
	    out.flush();
	} catch (IOException e) {
	    throw new StorageException("Failed copyint input to output", e);
	} finally {
	    /* finally, close the streams */
	    try {
		source.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    try {
		out.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}
    }

    @Override
    public byte[] get(String key) throws DataTooLargeException, StorageMissingException, StorageException {
	InputStream source = openForRead(key);

	ByteArrayOutputStream readArray = new ByteArrayOutputStream();

	byte[] readBuff = new byte[config.getReadBufferLength()];
	int bytesRead;
	try {
	    while ((bytesRead = source.read(readBuff)) != -1) {
		if (bytesRead > 0) {
		    if (readArray.size() > config.getMaxInMemoryReadSize()) {
			throw new DataTooLargeException("That data for key " + key + " on storage " + getId()
				+ " exceeds maximum in memory read size of " + config.getMaxInMemoryReadSize()
				+ " bytes.");
		    }
		    readArray.write(readBuff, 0, bytesRead);
		}
	    }

	    /* call flush on output to make sure all data reaches the destination */
	    readArray.flush();
	} catch (IOException e) {
	    throw new StorageException("Faield creating key " + key + " in storage " + storage.getId(), e);
	} finally {
	    /* finally, close the streams */
	    try {
		source.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	    try {
		readArray.close();
	    } catch (IOException e) {
		e.printStackTrace();
	    }
	}

	return readArray.toByteArray();
    }

    @Override
    public String getId() {
	return storage.getId();
    }

    @Override
    public String[] list() {
	return storage.list();
    }

    @Override
    public DataStore createStorage(String key)  throws StorageException{
	return new DefaultDataStore(storage.createStorage(key));
    }

    @Override
    public DataStore createStorageHierarchy(String... keys)  throws StorageException{
	return new DefaultDataStore(storage.createStorageHierarchy(keys));
    }

    @Override
    public boolean isStorage(String key) {
	return storage.isStorage(key);
    }

    @Override
    public StorageEntityInfo[] listEntities() {
	return storage.listEntities();
    }

    @Override
    public String[] extractHierarchy(String key) {
	return storage.extractHierarchy(key);
    }

    @Override
    public OutputStream createLeaf(String key) throws StorageEntityExistsException, StorageException {
	return storage.createLeaf(key);
    }

    @Override
    public void createLeaf(String key, InputStream source) throws StorageEntityExistsException, StorageException {
	/**
	 * Create the output storage and get output stream
	 */
	OutputStream out = createLeaf(key);

	try {
	    copyInputToOutput(source, out);
	}
	catch(StorageException e) {
	    throw new StorageException("Faield creating key " + key + " in storage " + storage.getId(), e);
	}
	
    }

    @Override
    public Storage getStorage(String path) throws StorageException{
	Storage retStorage = storage.getStorage(path);
	if(retStorage != null) {
	    return new DefaultDataStore(retStorage);
	}
	return null;
    }

    @Override
    public StorageEntity getStorageEntity(String key) throws StorageException {
	return storage.getStorageEntity(key);
    }

    @Override
    public String getRelativePath(String key) throws StorageException {
	return storage.getRelativePath(key);
    }

    @Override
    public boolean move(String sourceKey, String destPath) throws StorageException {
	return storage.move(sourceKey, destPath);
    }

    @Override
    public StorageEntityInfo[] listEntities(StorageFilter filter) {
	return storage.listEntities(filter);
    }

    @Override
    public boolean rename(String key, String newName) throws StorageException {
	return storage.rename(key, newName);
    }
}
