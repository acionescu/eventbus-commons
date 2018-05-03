package net.segoia.event.eventbus.peers.vo;

public class PeerLeavingReason {
    private int code;
    private String message;

    public PeerLeavingReason(int code, String message) {
	super();
	this.code = code;
	this.message = message;
    }

    public int getCode() {
	return code;
    }

    public String getMessage() {
	return message;
    }

}
