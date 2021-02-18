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
