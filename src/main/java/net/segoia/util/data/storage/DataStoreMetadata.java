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
