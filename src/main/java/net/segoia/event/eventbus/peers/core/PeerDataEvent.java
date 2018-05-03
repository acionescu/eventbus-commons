package net.segoia.event.eventbus.peers.core;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.EventType;
import net.segoia.event.eventbus.peers.vo.PeerData;

@EventType("PEER:DATA:EVENT")
public class PeerDataEvent extends CustomEvent<PeerData>{

    public PeerDataEvent(PeerData data) {
	super(PeerDataEvent.class,data);
    }

}
