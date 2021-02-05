package net.segoia.event.eventbus.peers.events;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;
import net.segoia.event.eventbus.peers.vo.GenericResponse;

@EventType("EBUS:RESPONSE:GENERIC")
public class GenericResponseEvent extends CustomEvent<GenericResponse> {
    public static final String ET = "EBUS:RESPONSE:GENERIC";

    public GenericResponseEvent(GenericResponse data) {
	super(ET, data);
    }

    public GenericResponseEvent() {
	super(ET);
    }

    @Override
    public GenericResponse getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(GenericResponse data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }

}
