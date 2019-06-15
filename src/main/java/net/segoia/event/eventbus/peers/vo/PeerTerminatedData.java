package net.segoia.event.eventbus.peers.vo;

public class PeerTerminatedData {
    private ReasonData reason;
    private PeerInfo peerInfo;

    public PeerTerminatedData() {
	super();
	// TODO Auto-generated constructor stub
    }

    public PeerTerminatedData(ReasonData reason, PeerInfo peerInfo) {
	super();
	this.reason = reason;
	this.peerInfo = peerInfo;
    }

    public ReasonData getReason() {
	return reason;
    }

    public void setReason(ReasonData reason) {
	this.reason = reason;
    }

    public PeerInfo getPeerInfo() {
	return peerInfo;
    }

    public void setPeerInfo(PeerInfo peerInfo) {
	this.peerInfo = peerInfo;
    }

}
