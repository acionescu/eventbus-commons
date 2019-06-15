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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.segoia.event.eventbus.Event;

/**
 * A context that is aware of peer events as well as local events
 * @author adi
 *
 */
public class GlobalAgentEventNodeContext extends LocalAgentEventNodeContext{
    private PeersManager peersManager;

    public GlobalAgentEventNodeContext(EventNodeContext nodeContext, PeersManager peersManager) {
	super(nodeContext);
	this.peersManager = peersManager;
    }

    public void forwardTo(Event event, String peerId) {
	peersManager.forwardTo(event, peerId);
    }
    
    public void forwardTo(Event event, List<String> peerIds) {
	peersManager.forwardTo(event, peerIds);
    }
    
    public void forwardToDirectPeers(Event event) {
	peersManager.forwardToDirectPeers(event);
    }
    
    public void forwardToAllKnown(Event event) {
	peersManager.forwardToAllKnown(event);
    }
    
    public Map<String,String> getDirectPeersByRootKey(Collection<String> rootKeys){
	return peersManager.getDirectPeersByRootKey(rootKeys);
    }
    
    public boolean forwartToPeerWithIdKey(Event event, String targetIdKey) {
	return peersManager.forwartToPeerWithIdKey(event, targetIdKey);
    }
}
