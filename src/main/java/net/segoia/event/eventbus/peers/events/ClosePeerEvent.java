package net.segoia.event.eventbus.peers.events;

import net.segoia.event.eventbus.CustomEvent;
import net.segoia.event.eventbus.peers.vo.ClosePeerData;

public class ClosePeerEvent extends CustomEvent<ClosePeerData>{

    public ClosePeerEvent(String et, ClosePeerData data) {
	super(et, data);
    }

}
