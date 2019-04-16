package net.segoia.event.eventbus.trust.comm;

public class TrustedCommSessionClosedData {
    private String reasonId;

    public TrustedCommSessionClosedData() {
	super();
	// TODO Auto-generated constructor stub
    }

    public TrustedCommSessionClosedData(String reasonId) {
	super();
	this.reasonId = reasonId;
    }

    public String getReasonId() {
        return reasonId;
    }

    public void setReasonId(String reasonId) {
        this.reasonId = reasonId;
    }
    
    
}
