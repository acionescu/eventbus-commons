package net.segoia.event.eventbus.streaming.events;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;

@EventType("PEER:STREAM:STARTED")
public class PeerStreamStartedEvent extends CustomEvent<PeerStreamData>{
    public static final String ET="PEER:STREAM:STARTED";

    public PeerStreamStartedEvent(PeerStreamData data) {
	super(ET, data);
    }

    public PeerStreamStartedEvent() {
	super(ET);
    }

    
    @Override
    public PeerStreamData getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(PeerStreamData data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }

}
