package net.segoia.event.eventbus.peers.events;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;
import net.segoia.event.eventbus.peers.vo.PeerTerminatedData;

@EventType("EBUS:PEER:TERMINATED")
public class PeerTerminatedEvent extends CustomEvent<PeerTerminatedData>{
    public static final String ET="EBUS:PEER:TERMINATED";
    
    public PeerTerminatedEvent(PeerTerminatedData data) {
	super(ET, data);
    }
    
    
}
