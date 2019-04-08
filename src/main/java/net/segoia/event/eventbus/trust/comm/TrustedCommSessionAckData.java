package net.segoia.event.eventbus.trust.comm;

public class TrustedCommSessionAckData {
    private String sessionId;

    public TrustedCommSessionAckData() {
	super();
    }

    public TrustedCommSessionAckData(String sessionId) {
	super();
	this.sessionId = sessionId;
    }

    public String getSessionId() {
	return sessionId;
    }

    public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
    }

}
