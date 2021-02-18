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

/**
 * Defines a storage entity - folder or file
 * 
 * @author adi
 *
 */
public class StorageEntityInfo {
    /**
     * The unique key of this entity
     */
    private String key;

    /**
     * True if this entity is a directory
     */
    private boolean directory;

    /**
     * When was last modified
     */
    private long lastModified;
    
    /**
     * Size of the data in bytes
     * <br>
     * This is only provided for file entities. For directories, this will always be 0.
     */
    private long size;

    public StorageEntityInfo() {
	super();
    }

    

    public StorageEntityInfo(String key, boolean directory, long lastModified, long size) {
	super();
	this.key = key;
	this.directory = directory;
	this.lastModified = lastModified;
	if(!directory) {
	    /* set size only for files */
	    this.size = size;
	}
    }



    public String getKey() {
	return key;
    }

    public void setKey(String key) {
	this.key = key;
    }

    public boolean isDirectory() {
	return directory;
    }

    public void setDirectory(boolean directory) {
	this.directory = directory;
    }

    public long getLastModified() {
	return lastModified;
    }

    public void setLastModified(long lastModified) {
	this.lastModified = lastModified;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
    
}
