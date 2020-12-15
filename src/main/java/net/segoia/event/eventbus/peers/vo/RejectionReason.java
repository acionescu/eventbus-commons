package net.segoia.event.eventbus.peers.vo;

public class RejectionReason {
    private int code;
    private String message;

    public RejectionReason(int code, String message) {
	super();
	this.code = code;
	this.message = message;
    }

    public RejectionReason() {
	super();
	// TODO Auto-generated constructor stub
    }

    public int getCode() {
	return code;
    }

    public void setCode(int code) {
	this.code = code;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

}
