package net.segoia.event.eventbus.streaming.events;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;

@EventType("DATA:STREAM:START")
public class StartStreamRequestEvent extends CustomEvent<StartStreamRequest>{
    public static final String ET="DATA:STREAM:START";

    public StartStreamRequestEvent(StartStreamRequest data) {
	super(ET, data);
    }
    
    public StartStreamRequestEvent() {
	super(ET);
    }

    @Override
    public StartStreamRequest getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(StartStreamRequest data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }

    
    
}
