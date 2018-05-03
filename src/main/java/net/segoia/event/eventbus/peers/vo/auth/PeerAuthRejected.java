package net.segoia.event.eventbus.peers.vo.auth;

import net.segoia.event.eventbus.peers.vo.RequestRejectReason;

public class PeerAuthRejected {
    private RequestRejectReason reason;

    public PeerAuthRejected() {
	super();
	// TODO Auto-generated constructor stub
    }

    public PeerAuthRejected(RequestRejectReason reason) {
	super();
	this.reason = reason;
    }

    public RequestRejectReason getReason() {
	return reason;
    }

    public void setReason(RequestRejectReason reason) {
	this.reason = reason;
    }

}
