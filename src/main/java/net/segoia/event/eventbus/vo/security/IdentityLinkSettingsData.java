package net.segoia.event.eventbus.vo.security;

public class IdentityLinkSettingsData {
    private String idsLinkKey;
    private LinkTrustLevel trustLevel;
    private IdentityLinkPublicData publicData;
    
    public IdentityLinkSettingsData(String idsLinkKey, LinkTrustLevel trustLevel, IdentityLinkPublicData publicData) {
	super();
	this.idsLinkKey = idsLinkKey;
	this.trustLevel = trustLevel;
	this.publicData = publicData;
    }

    public IdentityLinkSettingsData() {
	super();
    }

    public String getIdsLinkKey() {
        return idsLinkKey;
    }

    public void setIdsLinkKey(String idsLinkKey) {
        this.idsLinkKey = idsLinkKey;
    }

    public LinkTrustLevel getTrustLevel() {
        return trustLevel;
    }

    public void setTrustLevel(LinkTrustLevel trustLevel) {
        this.trustLevel = trustLevel;
    }

    public IdentityLinkPublicData getPublicData() {
        return publicData;
    }

    public void setPublicData(IdentityLinkPublicData publicData) {
        this.publicData = publicData;
    }

}
