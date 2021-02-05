package net.segoia.event.eventbus.streaming;

public class StreamControllerRuntimeData {
    private long bytesReceived;
    private long bytesSent;
    
    
    public long getBytesReceived() {
        return bytesReceived;
    }
    public long getBytesSent() {
        return bytesSent;
    }
    
    public long incBytesReceived(int count) {
	bytesReceived+=count;
	return bytesReceived;
    }
    
    public long incBytesSent(int count) {
	bytesSent+=count;
	return bytesSent;
    }
    
}
