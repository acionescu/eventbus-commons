package net.segoia.event.eventbus.trust.comm;

public class ResumeTrustedCommSessionData {
    private String linkIdKey;

    public ResumeTrustedCommSessionData() {
	super();
    }

    public ResumeTrustedCommSessionData(String linkIdKey) {
	super();
	this.linkIdKey = linkIdKey;
    }

    public String getLinkIdKey() {
	return linkIdKey;
    }

    public void setLinkIdKey(String linkIdKey) {
	this.linkIdKey = linkIdKey;
    }

}
