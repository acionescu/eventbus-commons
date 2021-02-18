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

public class DataStoreConfig {
    private int readBufferLength=1024;
    private int writeBufferLength=1024;
    
    /**
     * The maximum size of a file that is fully loaded into memory
     * Defaults to 32 MB.
     */
    private long maxInMemoryReadSize=1024*1024*32;
    
    public int getReadBufferLength() {
        return readBufferLength;
    }
    public void setReadBufferLength(int readBufferLength) {
        this.readBufferLength = readBufferLength;
    }
    public int getWriteBufferLength() {
        return writeBufferLength;
    }
    public void setWriteBufferLength(int writeBufferLength) {
        this.writeBufferLength = writeBufferLength;
    }
    public long getMaxInMemoryReadSize() {
        return maxInMemoryReadSize;
    }
    public void setMaxInMemoryReadSize(long maxInMemoryReadSize) {
        this.maxInMemoryReadSize = maxInMemoryReadSize;
    }
    
}
