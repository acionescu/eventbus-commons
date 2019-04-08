package net.segoia.event.eventbus.trust.comm;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;


@EventType("TRUST:COMM:SESSION_ACCEPTED")
public class TrustedCommSessionAcceptedEvent extends CustomEvent<TrustedCommSessionAcceptedData> {

    public static final String ET = "TRUST:COMM:SESSION_ACCEPTED";

    public TrustedCommSessionAcceptedEvent(TrustedCommSessionAcceptedData data) {
	super(ET, data);
    }

    public TrustedCommSessionAcceptedEvent() {
	super(ET);
    }

    @Override
    public TrustedCommSessionAcceptedData getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(TrustedCommSessionAcceptedData data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }
    
    
    
}
