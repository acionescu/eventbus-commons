package net.segoia.event.eventbus.peers.vo;

public class PeerLeavingData {
    private PeerLeavingReason reason;
    private PeerInfo peerInfo;

    public PeerLeavingData(PeerLeavingReason reason, PeerInfo peerInfo) {
	super();
	this.reason = reason;
	this.peerInfo = peerInfo;
    }

    public PeerLeavingReason getReason() {
	return reason;
    }

    public PeerInfo getPeerInfo() {
	return peerInfo;
    }

}
