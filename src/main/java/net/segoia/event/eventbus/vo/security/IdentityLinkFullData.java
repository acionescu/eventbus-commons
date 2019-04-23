package net.segoia.event.eventbus.vo.security;

import net.segoia.event.eventbus.vo.services.NodeIdentityProfile;

public class IdentityLinkFullData {
    private IdentityLinkPrivateData privateData;
    private IdentityLinkPublicData publicData;
    private NodeIdentityProfile peerProfile;

    public IdentityLinkFullData() {
	super();
    }

    public IdentityLinkFullData(IdentityLinkPrivateData privateData, IdentityLinkPublicData publicData) {
	super();
	this.privateData = privateData;
	this.publicData = publicData;
    }

    public IdentityLinkPrivateData getPrivateData() {
	return privateData;
    }

    public void setPrivateData(IdentityLinkPrivateData privateData) {
	this.privateData = privateData;
    }

    public IdentityLinkPublicData getPublicData() {
	return publicData;
    }

    public void setPublicData(IdentityLinkPublicData publicData) {
	this.publicData = publicData;
    }

    public NodeIdentityProfile getPeerProfile() {
        return peerProfile;
    }

    public void setPeerProfile(NodeIdentityProfile peerProfile) {
        this.peerProfile = peerProfile;
    }

}
