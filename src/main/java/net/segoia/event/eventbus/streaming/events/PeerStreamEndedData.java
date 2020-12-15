package net.segoia.event.eventbus.streaming.events;

import net.segoia.event.eventbus.peers.vo.ClosePeerData;

public class PeerStreamEndedData {
    private PeerStreamData streamData;
    private ClosePeerData reason;

    public PeerStreamEndedData(PeerStreamData streamData, ClosePeerData reason) {
	super();
	this.streamData = streamData;
	this.reason = reason;
    }

    public PeerStreamEndedData() {
	super();
	// TODO Auto-generated constructor stub
    }

    public PeerStreamData getStreamData() {
	return streamData;
    }

    public void setStreamData(PeerStreamData streamData) {
	this.streamData = streamData;
    }

    public ClosePeerData getReason() {
	return reason;
    }

    public void setReason(ClosePeerData reason) {
	this.reason = reason;
    }

}
