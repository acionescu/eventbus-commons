package net.segoia.event.eventbus.trust.comm;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;

@EventType("TRUST:COMM:RESUME_SESSION")
public class ResumeTrustedCommSessionEvent extends CustomEvent<ResumeTrustedCommSessionData>{
    public static final String ET="TRUST:COMM:RESUME_SESSION";
    
    public ResumeTrustedCommSessionEvent(ResumeTrustedCommSessionData data) {
	super(ET, data);
    }
    
    public ResumeTrustedCommSessionEvent() {
	super(ET);
    }

    @Override
    public ResumeTrustedCommSessionData getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(ResumeTrustedCommSessionData data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }
    
    

}
