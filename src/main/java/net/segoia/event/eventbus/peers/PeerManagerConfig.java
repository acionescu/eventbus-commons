package net.segoia.event.eventbus.peers;

import java.util.List;

public class PeerManagerConfig {
    /**
     * If set to true, will allow relays on received events
     * <br/>
     * Defaults to false
     */
    private boolean allowPeerRelays;
    private List<PeerManagerAgent> peerManagerAgents;

    public List<PeerManagerAgent> getPeerManagerAgents() {
	return peerManagerAgents;
    }

    public void setPeerManagerAgents(List<PeerManagerAgent> peerManagerAgents) {
	this.peerManagerAgents = peerManagerAgents;
    }

    public boolean isAllowPeerRelays() {
        return allowPeerRelays;
    }

    public void setAllowPeerRelays(boolean allowPeerRelays) {
        this.allowPeerRelays = allowPeerRelays;
    }

    
    
}
