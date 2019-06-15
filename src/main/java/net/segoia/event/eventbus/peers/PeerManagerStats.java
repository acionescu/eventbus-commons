package net.segoia.event.eventbus.peers;

public class PeerManagerStats {
    private long creationTs=System.currentTimeMillis();
    
    /**
     * The unixtime when the last event was received from the peer
     */
    private long lastReceivedEventTs;
    
    /**
     * The unixtime when the last event was sent to the peer
     */
    private long lastSentEventTs;
    
    /**
     * 
     * @return Returns the duration in milliseconds since this peer was created
     */
    public long lifetime() {
	return System.currentTimeMillis() - creationTs;
    }
    
    
    public long durationSinceLastReceivedEvent() {
	return System.currentTimeMillis() - lastReceivedEventTs;
    }
    
    public long durationSinceLastSentEvent() {
	return System.currentTimeMillis() - lastSentEventTs;
    }

    public long getLastReceivedEventTs() {
        return lastReceivedEventTs;
    }

    public void setLastReceivedEventTs(long lastReceivedEventTs) {
        this.lastReceivedEventTs = lastReceivedEventTs;
    }

    public long getLastSentEventTs() {
        return lastSentEventTs;
    }

    public void setLastSentEventTs(long lastSentEventTs) {
        this.lastSentEventTs = lastSentEventTs;
    }
    
}
