package net.segoia.event.eventbus.streaming.events;

import net.segoia.event.eventbus.peers.vo.ClosePeerData;

public class EndStreamData {
    private String streamSessionId;
    private byte[] data;
    private ClosePeerData reason;

    public EndStreamData(String streamSessionId, byte[] data, int code, String reason) {
	super();
	this.streamSessionId = streamSessionId;
	this.data = data;

	this.reason = new ClosePeerData(code, reason);
    }

    public EndStreamData(String streamSessionId, int code, String reason) {
	super();
	this.streamSessionId = streamSessionId;
	this.reason = new ClosePeerData(code, reason);
    }

    public EndStreamData(String streamSessionId, ClosePeerData reason) {
	super();
	this.streamSessionId = streamSessionId;
	this.reason = reason;
    }

    public EndStreamData(String streamSessionId, byte[] data, ClosePeerData reason) {
	super();
	this.streamSessionId = streamSessionId;
	this.data = data;
	this.reason = reason;
    }

    public EndStreamData() {
	super();
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

    public ClosePeerData getReason() {
	return reason;
    }

    public void setReason(ClosePeerData reason) {
	this.reason = reason;
    }

}
