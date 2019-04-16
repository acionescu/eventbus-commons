package net.segoia.event.eventbus.trust.comm;

public class InitTrustedCommSessionData {
    private String linkId;

    /**
     * To be filled by the proxy node
     */
    private String sessionId;

    /**
     * The id of the service that will used this trusted session
     */
    private String serviceId;

    public InitTrustedCommSessionData() {
	super();
    }

    public InitTrustedCommSessionData(String linkId) {
	super();
	this.linkId = linkId;
    }

    public InitTrustedCommSessionData(String linkId, String serviceId) {
	super();
	this.linkId = linkId;
	this.serviceId = serviceId;
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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
    
    

}
