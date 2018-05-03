package net.segoia.event.eventbus.peers.core;

import net.segoia.event.eventbus.peers.vo.PeerLeavingReason;

/**
 * Handles the transmission of events over a particular communication channel
 * @author adi
 *
 */
public interface EventTransceiver {
    void start();
    
    void terminate();
    
//    void sendEvent(Event event);
//    void receiveEvent(Event event);
//    void setRemoteEventListener(PeerEventListener eventListener);
    
    void sendData(byte[] data);
    void receiveData(byte[] data);
    void receiveData(PeerDataEvent dataEvent);
    void setRemoteDataListener(PeerDataListener dataListener);
    
    /**
     * Called when the communication with the peer is interrupted without us initiating it
     */
    void onPeerLeaving(PeerLeavingReason reason);
    
    /**
     * 
     * @return - The communication channel type ( e.g. ws, wss, http, https, etc.. )
     */
    String getChannel();
    
}
