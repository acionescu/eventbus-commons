package net.segoia.event.eventbus.vo.security;

public class NodeIdLinkData {
    private String ownerIdKey;
    private LinkTrustLevel trustLevel;

    public NodeIdLinkData(String ownerIdKey, LinkTrustLevel trustLevel) {
	super();
	this.ownerIdKey = ownerIdKey;
	this.trustLevel = trustLevel;
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

}
