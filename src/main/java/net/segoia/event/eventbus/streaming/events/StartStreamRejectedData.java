package net.segoia.event.eventbus.streaming.events;

import net.segoia.event.eventbus.peers.vo.RejectionReason;

public class StartStreamRejectedData {
    private String streamId;
    private RejectionReason reason;

    public StartStreamRejectedData() {
	super();
    }

    public StartStreamRejectedData(String streamId, int code, String message) {
	super();
	this.streamId = streamId;
	reason = new RejectionReason(code, message);
    }

    public StartStreamRejectedData(String streamId, RejectionReason reason) {
	super();
	this.streamId = streamId;
	this.reason = reason;
    }

    public String getStreamId() {
	return streamId;
    }

    public void setStreamId(String streamId) {
	this.streamId = streamId;
    }

    public RejectionReason getReason() {
	return reason;
    }

    public void setReason(RejectionReason reason) {
	this.reason = reason;
    }

}
