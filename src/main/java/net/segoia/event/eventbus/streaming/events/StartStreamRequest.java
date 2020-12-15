package net.segoia.event.eventbus.streaming.events;

public class StartStreamRequest {
    private StreamInfo streamInfo;

    /**
     * First chunk of data <br>
     * Optional
     */
    private byte[] data;

    public StartStreamRequest(StreamInfo streamInfo) {
	super();
	this.streamInfo = streamInfo;
    }

    public StartStreamRequest() {
	super();
	// TODO Auto-generated constructor stub
    }

    public StreamInfo getStreamInfo() {
	return streamInfo;
    }

    public void setStreamInfo(StreamInfo streamInfo) {
	this.streamInfo = streamInfo;
    }

    public byte[] getData() {
	return data;
    }

    public void setData(byte[] data) {
	this.data = data;
    }
}
