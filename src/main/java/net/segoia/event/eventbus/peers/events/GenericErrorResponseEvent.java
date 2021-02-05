package net.segoia.event.eventbus.peers.events;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;
import net.segoia.event.eventbus.peers.vo.GenericErrorResponse;

@EventType("EBUS:ERROR:GENERIC")
public class GenericErrorResponseEvent extends CustomEvent<GenericErrorResponse>{
    public static final String ET="EBUS:ERROR:GENERIC";

    public GenericErrorResponseEvent(GenericErrorResponse data) {
	super(ET, data);
    }
    
    public GenericErrorResponseEvent() {
	super(ET);
    }

    @Override
    public GenericErrorResponse getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(GenericErrorResponse data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }
}
