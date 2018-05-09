/**
 * event-bus - An event bus framework for event driven programming
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

public class PeerManagerAbstractFactory implements PeerManagerFactory{
    private PeersManagerConfig config;
    
    

    public PeerManagerAbstractFactory(PeersManagerConfig config) {
	super();
	this.config = config;
    }

    @Override
    public PeerManager buildPeerManager(PeerContext peerContext) {
	String channel = peerContext.getTransceiver().getChannel();
	Map<String, PeerManagerFactory> peerManagerFactories = config.getPeerManagerFactories();
	PeerManagerFactory peerManagerFactory = null;
	if(peerManagerFactories!= null) {
	     peerManagerFactory = peerManagerFactories.get(channel);
	}
	
	if(peerManagerFactory == null) {
	    peerManagerFactory = config.getDefaultPeerManagerFactory();
	}
	return peerManagerFactory.buildPeerManager(peerContext);
    }

    public PeersManagerConfig getConfig() {
        return config;
    }

    public void setConfig(PeersManagerConfig config) {
        this.config = config;
    }
    
    
}
