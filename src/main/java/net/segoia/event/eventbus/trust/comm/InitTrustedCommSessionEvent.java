package net.segoia.event.eventbus.trust.comm;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;

@EventType("TRUST:COMM:INIT_SESSION")
public class InitTrustedCommSessionEvent extends CustomEvent<InitTrustedCommSessionData> {
    public static final String ET = "TRUST:COMM:INIT_SESSION";

    public InitTrustedCommSessionEvent(InitTrustedCommSessionData data) {
	super(ET, data);
    }

    public InitTrustedCommSessionEvent() {
	super(ET);
    }

    @Override
    public InitTrustedCommSessionData getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(InitTrustedCommSessionData data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }
    
    
}
