package net.segoia.event.eventbus.streaming.events;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;

@EventType("PEER:STREAM:ENDED")
public class PeerStreamEndedEvent extends CustomEvent<PeerStreamEndedData> {
    public static final String ET = "PEER:STREAM:ENDED";

    public PeerStreamEndedEvent(PeerStreamEndedData data) {
	super(ET, data);
    }

    public PeerStreamEndedEvent() {
	super(ET);
    }

    @Override
    public PeerStreamEndedData getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(PeerStreamEndedData data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }

}
