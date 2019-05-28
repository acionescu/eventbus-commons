package net.segoia.event.eventbus.trust.comm;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;

@EventType("TRUST:COMM:SESSION_READY")
public class TrustedCommSessionReadyEvent extends CustomEvent<TrustedCommSessionReadyData> {
    public static final String ET = "TRUST:COMM:SESSION_READY";

    public TrustedCommSessionReadyEvent(TrustedCommSessionReadyData data) {
	super(ET, data);
    }
    
    public TrustedCommSessionReadyEvent() {
	super(ET);
    }

    @Override
    public TrustedCommSessionReadyData getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(TrustedCommSessionReadyData data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }
    
    
}
