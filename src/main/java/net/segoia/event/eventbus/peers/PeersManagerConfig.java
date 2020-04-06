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

import java.util.List;
import java.util.Map;

import net.segoia.event.conditions.Condition;
import net.segoia.event.conditions.TrueCondition;

public class PeersManagerConfig {
    private PeerManagerFactory defaultPeerManagerFactory = new DefaultPeerManagerFactory();
    
    private Map<String,PeerManagerFactory> peerManagerFactories;
    
    private Map<String, PeerManagerConfig> peersConfigs;
    
    private List<PeersManagerAgent> agents;
    
    private Condition defaultPeerBindCondition = new TrueCondition();
    
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

    public Map<String, PeerManagerConfig> getPeersConfigs() {
        return peersConfigs;
    }

    public void setPeersConfigs(Map<String, PeerManagerConfig> peersConfigs) {
        this.peersConfigs = peersConfigs;
    }

    public List<PeersManagerAgent> getAgents() {
        return agents;
    }

    public void setAgents(List<PeersManagerAgent> agents) {
        this.agents = agents;
    }

    public Condition getDefaultPeerBindCondition() {
        return defaultPeerBindCondition;
    }

    public void setDefaultPeerBindCondition(Condition defaultPeerBindCondition) {
        this.defaultPeerBindCondition = defaultPeerBindCondition;
    }
    
}
