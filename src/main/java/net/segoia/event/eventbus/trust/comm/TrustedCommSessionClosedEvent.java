package net.segoia.event.eventbus.trust.comm;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;

@EventType("TRUST:COMM:SESSION_CLOSED")
public class TrustedCommSessionClosedEvent extends CustomEvent<TrustedCommSessionClosedData>{
    public static final String ET="TRUST:COMM:SESSION_CLOSED";

    public TrustedCommSessionClosedEvent(TrustedCommSessionClosedData data) {
	super(ET, data);
    }
    
    public TrustedCommSessionClosedEvent() {
	super(ET);
    }

    @Override
    public TrustedCommSessionClosedData getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(TrustedCommSessionClosedData data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }
    
    

}
