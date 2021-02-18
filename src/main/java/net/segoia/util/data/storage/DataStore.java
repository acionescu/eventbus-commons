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
