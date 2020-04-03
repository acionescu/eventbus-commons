package net.segoia.event.eventbus.peers;

import java.util.List;

public class PeerManagerConfig {
    private List<PeerManagerAgent> peerManagerAgents;

    public List<PeerManagerAgent> getPeerManagerAgents() {
	return peerManagerAgents;
    }

    public void setPeerManagerAgents(List<PeerManagerAgent> peerManagerAgents) {
	this.peerManagerAgents = peerManagerAgents;
    }

}
