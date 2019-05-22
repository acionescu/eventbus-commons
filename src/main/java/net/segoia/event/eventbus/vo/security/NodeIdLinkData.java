package net.segoia.event.eventbus.vo.security;

public class NodeIdLinkData {
    private String ownerIdKey;
    private LinkTrustLevel trustLevel;
    private IdentityLinkPublicData publicData;

    public NodeIdLinkData(String ownerIdKey, LinkTrustLevel trustLevel) {
	super();
	this.ownerIdKey = ownerIdKey;
	this.trustLevel = trustLevel;
    }

    public NodeIdLinkData(String ownerIdKey, LinkTrustLevel trustLevel, IdentityLinkPublicData publicData) {
	super();
	this.ownerIdKey = ownerIdKey;
	this.trustLevel = trustLevel;
	this.publicData = publicData;
    }

    public NodeIdLinkData() {
	super();
	// TODO Auto-generated constructor stub
    }

    public String getOwnerIdKey() {
	return ownerIdKey;
    }

    public void setOwnerIdKey(String ownerIdKey) {
	this.ownerIdKey = ownerIdKey;
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
