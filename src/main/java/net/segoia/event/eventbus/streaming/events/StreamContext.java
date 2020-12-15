package net.segoia.event.eventbus.streaming.events;

public class StreamContext {
    private String streamSessionId;
    private StreamInfo streamInfo;
    private String sourcePeerId;

    public StreamContext(String streamSessionId, StreamInfo streamInfo, String sourcePeerId) {
	super();
	this.streamSessionId = streamSessionId;
	this.streamInfo = streamInfo;
	this.sourcePeerId=sourcePeerId;
    }

    public String getStreamSessionId() {
	return streamSessionId;
    }

    public void setStreamSessionId(String streamSessionId) {
	this.streamSessionId = streamSessionId;
    }

    public StreamInfo getStreamInfo() {
	return streamInfo;
    }

    public void setStreamInfo(StreamInfo streamInfo) {
	this.streamInfo = streamInfo;
    }

    public String getSourcePeerId() {
        return sourcePeerId;
    }

    public void setSourcePeerId(String sourcePeerId) {
        this.sourcePeerId = sourcePeerId;
    }
}
