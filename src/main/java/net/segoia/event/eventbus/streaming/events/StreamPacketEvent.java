package net.segoia.event.eventbus.streaming.events;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;

@EventType("DATA:STREAM:PACKET")
public class StreamPacketEvent extends CustomEvent<StreamPacketData> {
    public static final String ET = "DATA:STREAM:PACKET";

    public StreamPacketEvent(StreamPacketData data) {
	super(ET, data);
    }

    public StreamPacketEvent() {
	super(ET);
    }

    @Override
    public StreamPacketData getData() {
	// TODO Auto-generated method stub
	return super.getData();
    }

    @Override
    public void setData(StreamPacketData data) {
	// TODO Auto-generated method stub
	super.setData(data);
    }
}
