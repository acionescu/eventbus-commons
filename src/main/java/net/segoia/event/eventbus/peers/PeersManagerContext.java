package net.segoia.event.eventbus.peers;

import net.segoia.event.eventbus.Event;

public class PeersManagerContext {
    private PeersManager peersManager;

    public PeersManagerContext(PeersManager peersManager) {
	super();
	this.peersManager = peersManager;
    }

    public <E extends Event> void handlePeerEvent(PeerEventContext<E> context) {
	peersManager.handlePeerEvent(context);
    }
    
    

}
