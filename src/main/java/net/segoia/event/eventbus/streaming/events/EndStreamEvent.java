package net.segoia.event.eventbus.streaming.events;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;

@EventType("DATA:STREAM:END")
public class EndStreamEvent extends CustomEvent<EndStreamData>{
    public static final String ET="DATA:STREAM:END";

    public EndStreamEvent(EndStreamData data) {
	super(ET, data);
    }
    
    public EndStreamEvent() {
	super(ET);
    }

    @Override
    public EndStreamData getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(EndStreamData data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }

}
