package net.segoia.event.eventbus.vo.security;

public class IdentityLinkPublicData {

    private String identityKey;
    private LinkTrustLevel trustLevel;
    private String publicAlias;

    public IdentityLinkPublicData(String identityKey) {
        this.identityKey = identityKey;
    }
    
    

    public IdentityLinkPublicData(String identityKey, LinkTrustLevel trustLevel) {
	super();
	this.identityKey = identityKey;
	this.trustLevel = trustLevel;
    }

    public IdentityLinkPublicData(String identityKey, LinkTrustLevel trustLevel, String publicAlias) {
	super();
	this.identityKey = identityKey;
	this.trustLevel = trustLevel;
	this.publicAlias = publicAlias;
    }

    public IdentityLinkPublicData() {
	super();
    }

    public String getIdentityKey() {
	return identityKey;
    }

    public void setIdentityKey(String identityKey) {
	this.identityKey = identityKey;
    }

    public LinkTrustLevel getTrustLevel() {
	return trustLevel;
    }

    public void setTrustLevel(LinkTrustLevel trustLevel) {
	this.trustLevel = trustLevel;
    }

    public String getPublicAlias() {
	return publicAlias;
    }

    public void setPublicAlias(String publicAlias) {
	this.publicAlias = publicAlias;
    }

}
