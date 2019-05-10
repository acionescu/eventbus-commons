package net.segoia.event.eventbus.trust.comm;

public class TrustedCommSessionClosedData {
    private String reasonId;
    /* useful when the peer rejects request before a session id is allocated */
    private String linkKeyId;

    public TrustedCommSessionClosedData() {
	super();
	// TODO Auto-generated constructor stub
    }

    public TrustedCommSessionClosedData(String reasonId) {
	super();
	this.reasonId = reasonId;
    }

    public TrustedCommSessionClosedData(String reasonId, String linkKeyId) {
        this.reasonId = reasonId;
        this.linkKeyId = linkKeyId;
    }
    
    

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }

    public String getLinkKeyId() {
        return linkKeyId;
    }

    public void setLinkKeyId(String linkKeyId) {
        this.linkKeyId = linkKeyId;
    }
    
    
}
