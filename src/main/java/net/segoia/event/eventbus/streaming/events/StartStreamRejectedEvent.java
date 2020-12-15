package net.segoia.event.eventbus.streaming.events;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;

@EventType("DATA:STREAM:REJECTED")
public class StartStreamRejectedEvent extends CustomEvent<StartStreamRejectedData> {
    public static final String ET = "DATA:STREAM:REJECTED";

    public StartStreamRejectedEvent(StartStreamRejectedData data) {
	super(ET, data);
    }

    public StartStreamRejectedEvent() {
	super(ET);
    }

    @Override
    public StartStreamRejectedData getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(StartStreamRejectedData data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }

}
