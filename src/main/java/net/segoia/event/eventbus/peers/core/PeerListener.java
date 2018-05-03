package net.segoia.event.eventbus.peers.core;

import net.segoia.event.eventbus.peers.vo.PeerLeavingReason;

public interface PeerListener {
    public void onPeerLeaving(PeerLeavingReason reason);
}
