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
