package net.segoia.event.eventbus.peers;

public class PeerManagerContext {
    private PeerManager peerManager;

    public PeerManagerContext(PeerManager peerManager) {
	super();
	this.peerManager = peerManager;
    }

    public PeerManager getPeerManager() {
        return peerManager;
    }
    
    
}
