package net.segoia.event.eventbus.vo.security;

import net.segoia.event.eventbus.peers.vo.session.SessionKeyPlainData;

public class IdentityLinkPrivateData {

    /**
     * The alias to identity this link by
     */
    private String alias;

    /**
     * The key used by the server to uniquely identify this link
     */
    private String idsLinkKey;

    /**
     * The id of the linking session. This can be used to verify the linked
     * nodes
     */
    private String linkingSessionId;

    /**
     * The session key used to encrypt events to/from partner
     */
    private SessionKeyPlainData sessionData;
    
    private LinkTrustLevel trustLevel;

    public IdentityLinkPrivateData() {
        super();
    }

    public IdentityLinkPrivateData(String alias) {
        super();
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIdsLinkKey() {
        return idsLinkKey;
    }

    public void setIdsLinkKey(String idsLinkKey) {
        this.idsLinkKey = idsLinkKey;
    }

    public String getLinkingSessionId() {
        return linkingSessionId;
    }

    public void setLinkingSessionId(String linkingSessionId) {
        this.linkingSessionId = linkingSessionId;
    }

    public SessionKeyPlainData getSessionData() {
        return sessionData;
    }

    public void setSessionData(SessionKeyPlainData sessionData) {
        this.sessionData = sessionData;
    }

    public LinkTrustLevel getTrustLevel() {
        return trustLevel;
    }

    public void setTrustLevel(LinkTrustLevel trustLevel) {
        this.trustLevel = trustLevel;
    }

}
