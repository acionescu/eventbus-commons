package net.segoia.event.eventbus.peers.core;

public interface PeerDataListener extends PeerListener{
    public void onPeerData(PeerDataEvent dataEvent);
}
