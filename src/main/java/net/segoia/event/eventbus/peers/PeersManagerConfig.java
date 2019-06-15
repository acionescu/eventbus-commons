/**
 * eventbus-commons - Core classes for net.segoia.event-bus framework
 * Copyright (C) 2016  Adrian Cristian Ionescu - https://github.com/acionescu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.segoia.event.eventbus.peers;

import java.util.Map;

public class PeersManagerConfig {
    private PeerManagerFactory defaultPeerManagerFactory = new DefaultPeerManagerFactory();
    private Map<String,PeerManagerFactory> peerManagerFactories;
    
    private PeerManagerFactory defaultRemotePeerManagerFactory = new DefaultRemotePeerManagerFactory();
    private Map<String,PeerManagerFactory> remotePeerManagerFactories;
    
    
    /**
     * Set this to true to allow remote peers creation
     */
    private boolean allowRemotePeersOn;
    
    /**
     * How much time should a remote peer be allowed to live before cleanup due to inactivity ( in ms )
     * <br>
     * Default 5 minutes
     */
    private long minRemotePeerTTLBeforeCleanup=300000;
    
    /**
     * The period to cleanup inactive remote peers
     */
    private long inactiveRemotePeerCeanupPeriod=300000;
    
    /**
     * Max remote peer inactivity period before being purged
     */
    private long maxRemotePeerInactivePeriod=300000;
    

    public PeerManagerFactory getDefaultPeerManagerFactory() {
	return defaultPeerManagerFactory;
    }

    public void setDefaultPeerManagerFactory(PeerManagerFactory defaultPeerManagerFactory) {
	this.defaultPeerManagerFactory = defaultPeerManagerFactory;
    }

    public Map<String, PeerManagerFactory> getPeerManagerFactories() {
        return peerManagerFactories;
    }

    public void setPeerManagerFactories(Map<String, PeerManagerFactory> peerManagerFactories) {
        this.peerManagerFactories = peerManagerFactories;
    }

    public PeerManagerFactory getDefaultRemotePeerManagerFactory() {
        return defaultRemotePeerManagerFactory;
    }

    public void setDefaultRemotePeerManagerFactory(PeerManagerFactory defaultRemotePeerManagerFactory) {
        this.defaultRemotePeerManagerFactory = defaultRemotePeerManagerFactory;
    }

    public Map<String, PeerManagerFactory> getRemotePeerManagerFactories() {
        return remotePeerManagerFactories;
    }

    public void setRemotePeerManagerFactories(Map<String, PeerManagerFactory> remotePeerManagerFactories) {
        this.remotePeerManagerFactories = remotePeerManagerFactories;
    }

    public boolean isAllowRemotePeersOn() {
        return allowRemotePeersOn;
    }

    public void setAllowRemotePeersOn(boolean allowRemotePeersOn) {
        this.allowRemotePeersOn = allowRemotePeersOn;
    }

    public long getMinRemotePeerTTLBeforeCleanup() {
        return minRemotePeerTTLBeforeCleanup;
    }

    public void setMinRemotePeerTTLBeforeCleanup(long minRemotePeerTTLBeforeCleanup) {
        this.minRemotePeerTTLBeforeCleanup = minRemotePeerTTLBeforeCleanup;
    }

    public long getInactiveRemotePeerCeanupPeriod() {
        return inactiveRemotePeerCeanupPeriod;
    }

    public void setInactiveRemotePeerCeanupPeriod(long inactiveRemotePeerCeanupPeriod) {
        this.inactiveRemotePeerCeanupPeriod = inactiveRemotePeerCeanupPeriod;
    }

    public long getMaxRemotePeerInactivePeriod() {
        return maxRemotePeerInactivePeriod;
    }

    public void setMaxRemotePeerInactivePeriod(long maxRemotePeerInactivePeriod) {
        this.maxRemotePeerInactivePeriod = maxRemotePeerInactivePeriod;
    }
    
    
}
