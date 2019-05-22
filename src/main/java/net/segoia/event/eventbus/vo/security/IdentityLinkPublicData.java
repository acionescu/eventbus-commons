package net.segoia.event.eventbus.vo.security;

public class IdentityLinkPublicData {

    private String identityKey;
    private String publicAlias;

    public IdentityLinkPublicData(String identityKey) {
        this.identityKey = identityKey;
    }
    


    public IdentityLinkPublicData(String identityKey,String publicAlias) {
	super();
	this.identityKey = identityKey;
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


    public String getPublicAlias() {
	return publicAlias;
    }

    public void setPublicAlias(String publicAlias) {
	this.publicAlias = publicAlias;
    }

}
