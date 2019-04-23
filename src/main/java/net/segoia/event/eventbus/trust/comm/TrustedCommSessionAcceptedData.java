package net.segoia.event.eventbus.trust.comm;

public class TrustedCommSessionAcceptedData {

    /**
     * The session id allocated by the server for this connection
     */
    private String sessionId;
    private String linkId;
    /**
     * The id of the key used to encrypt data, if one was sent by the initiator
     * and accepted by this party
     */
    private String sessionKeyId;

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

    public TrustedCommSessionAcceptedData(String sessionId, String linkId, String sessionKeyId) {
        this.sessionId = sessionId;
        this.linkId = linkId;
        this.sessionKeyId = sessionKeyId;
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

    public String getSessionKeyId() {
        return sessionKeyId;
    }

    public void setSessionKeyId(String sessionKeyId) {
        this.sessionKeyId = sessionKeyId;
    }

}
