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
