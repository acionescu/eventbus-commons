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

import java.util.Comparator;

public class DocumentQuery<D> {
    private DocumentFilter<D> filter;
    /**
     * Used for sorting if present
     */
    private Comparator<D> comparator;

    /**
     * If greater then 0, it will start query with element at the position indicated by the offset, under the current
     * ordering
     */
    private int offset;

    /**
     * If greater then 0 it will limit the result set to that value
     */
    private int limit;
    
    
    public static <T> DocumentQuery<T> create(Class<T> clazz) {
	return new DocumentQuery<T>();
    }
    
    
    public DocumentQuery<D> setFilter(DocumentFilter<D> filter) {
        this.filter = filter;
        return this;
    }

    public DocumentQuery<D> setComparator(Comparator<D> comparator) {
        this.comparator = comparator;
        return this;
    }

    public DocumentQuery<D> setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public DocumentQuery<D> setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public DocumentFilter<D> getFilter() {
	return filter;
    }

    public Comparator<D> getComparator() {
	return comparator;
    }

    public int getOffset() {
	return offset;
    }

    public int getLimit() {
	return limit;
    }

}
