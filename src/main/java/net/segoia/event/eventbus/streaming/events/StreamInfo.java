package net.segoia.event.eventbus.streaming.events;

public class StreamInfo {
    /**
     * An id specified by the initiator
     */
    private String streamId;
    
    /**
     * The id of the app that should process this stream
     */
    private String appId;
    
    /**
     * A specific topic of the app 
     */
    private String appTopicId;
    
    /**
     * Use a mime type here
     */
    private String streamType;
    /**
     * The size of the data to be streamed, in bytes. <br>
     * Optional.
     */
    private long totalDataLength;

    /**
     * The proposed size of data packet, in bytes. <br>
     * Optional
     */
    private long packetSize;
    
    public StreamInfo(String streamId, String streamType) {
	super();
	this.streamId = streamId;
	this.streamType = streamType;
    }

    public StreamInfo() {
	super();
	// TODO Auto-generated constructor stub
    }

    public String getStreamId() {
	return streamId;
    }

    public void setStreamId(String streamId) {
	this.streamId = streamId;
    }

    public String getStreamType() {
	return streamType;
    }

    public void setStreamType(String streamType) {
	this.streamType = streamType;
    }

    public long getTotalDataLength() {
	return totalDataLength;
    }

    public void setTotalDataLength(long totalDataLength) {
	this.totalDataLength = totalDataLength;
    }

    public long getPacketSize() {
	return packetSize;
    }

    public void setPacketSize(long packetSize) {
	this.packetSize = packetSize;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppTopicId() {
        return appTopicId;
    }

    public void setAppTopicId(String appTopicId) {
        this.appTopicId = appTopicId;
    }
    
    
}
