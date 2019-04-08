package net.segoia.event.eventbus.trust.comm;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;

@EventType("TRUST:COMM:START_SESSION")
public class StartTrustedCommSessionEvent extends CustomEvent<StartTrustedCommSessionData> {

    public static final String ET = "TRUST:COMM:START_SESSION";

    public StartTrustedCommSessionEvent(StartTrustedCommSessionData data) {
	super(ET, data);
    }

    public StartTrustedCommSessionEvent() {
	super(ET);
    }

    @Override
    public StartTrustedCommSessionData getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(StartTrustedCommSessionData data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }
    
    

}
