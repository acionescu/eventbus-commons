package net.segoia.event.eventbus.streaming.events;

public class StreamData {
    private String streamSessionId;
    private StreamInfo streamInfo;

    public StreamData(String streamSessionId, StreamInfo streamInfo) {
	super();
	this.streamSessionId = streamSessionId;
	this.streamInfo = streamInfo;
    }

    public StreamData() {
	super();
	// TODO Auto-generated constructor stub
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

}
