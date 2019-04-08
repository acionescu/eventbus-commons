package net.segoia.event.eventbus.trust.comm;

public class InitTrustedCommSessionData {
    private String linkId;
    /**
     * To be filled by the proxy node
     */
    private String sessionId;

    public InitTrustedCommSessionData() {
	super();
    }

    public InitTrustedCommSessionData(String linkId) {
	super();
	this.linkId = linkId;
    }

    public String getLinkId() {
	return linkId;
    }

    public void setLinkId(String linkId) {
	this.linkId = linkId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    

}
