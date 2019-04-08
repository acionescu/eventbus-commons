package net.segoia.event.eventbus.trust.comm;

public class TrustedCommSessionAcceptedData {
    private String sessionId;
    private String linkId;

    public TrustedCommSessionAcceptedData() {
	super();
    }

    public TrustedCommSessionAcceptedData(String sessionId) {
	super();
	this.sessionId = sessionId;
    }

    public TrustedCommSessionAcceptedData(String sessionId, String linkId) {
	super();
	this.sessionId = sessionId;
	this.linkId = linkId;
    }

    public String getSessionId() {
	return sessionId;
    }

    public void setSessionId(String sessionId) {
	this.sessionId = sessionId;
    }

    public String getLinkId() {
	return linkId;
    }

    public void setLinkId(String linkId) {
	this.linkId = linkId;
    }

}
