package net.segoia.event.eventbus.streaming.events;

public class StreamPacketData {
    private String streamSessionId;
    private byte[] data;

    public StreamPacketData() {
	super();
	// TODO Auto-generated constructor stub
    }

    public StreamPacketData(String streamSessionId, byte[] data) {
	super();
	this.streamSessionId = streamSessionId;
	this.data = data;
    }

    public String getStreamSessionId() {
	return streamSessionId;
    }

    public void setStreamSessionId(String streamSessionId) {
	this.streamSessionId = streamSessionId;
    }

    public byte[] getData() {
	return data;
    }

    public void setData(byte[] data) {
	this.data = data;
    }

}
