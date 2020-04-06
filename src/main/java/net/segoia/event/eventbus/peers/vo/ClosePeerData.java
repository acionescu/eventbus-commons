package net.segoia.event.eventbus.peers.vo;

public class ClosePeerData {
    private int code;
    private String message;

    public ClosePeerData() {
	super();
    }

    public ClosePeerData(String message) {
	super();
	this.message = message;
    }

    public ClosePeerData(int code, String message) {
	super();
	this.code = code;
	this.message = message;
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
