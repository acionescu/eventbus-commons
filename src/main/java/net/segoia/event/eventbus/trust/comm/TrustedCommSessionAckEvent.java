package net.segoia.event.eventbus.trust.comm;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;

@EventType("TRUST:COMM:SESSION_ACK")
public class TrustedCommSessionAckEvent extends CustomEvent<TrustedCommSessionAckData>{
    public static final String ET="TRUST:COMM:SESSION_ACK";

    public TrustedCommSessionAckEvent(TrustedCommSessionAckData data) {
	super(ET, data);
    }
    
    public TrustedCommSessionAckEvent() {
	super(ET);
    }

    @Override
    public TrustedCommSessionAckData getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(TrustedCommSessionAckData data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }

}
