package net.segoia.event.eventbus.streaming.events;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;

@EventType("DATA:STREAM:ACCEPTED")
public class StartStreamAcceptedEvent extends CustomEvent<StreamData>{
    public static final String ET="DATA:STREAM:ACCEPTED";

    public StartStreamAcceptedEvent(StreamData data) {
	super(ET, data);
    }
    
    public StartStreamAcceptedEvent() {
	super(ET);
    }

    @Override
    public StreamData getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(StreamData data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }

}
