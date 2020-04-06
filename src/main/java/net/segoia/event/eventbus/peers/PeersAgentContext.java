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

import net.segoia.event.conditions.Condition;
import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.FilteringEventProcessor;
import net.segoia.event.eventbus.peers.agents.RemotePeerDataContext;
import net.segoia.event.eventbus.peers.util.EventNodeLogger;

public class PeersAgentContext {
    private PeersManager peersManager;
    private EventNodeContext nodeContext;

    public PeersAgentContext(PeersManager peersManager, EventNodeContext nodeContext) {
	super();
	this.peersManager = peersManager;
	this.nodeContext = nodeContext;
    }

    public FilteringEventProcessor getBeforePostFilter() {
	return peersManager.getBeforePostFilter();
    }

    public void postEvent(Event event) {
	nodeContext.postEvent(event);
    }
    
    public EventNodeLogger logger() {
	return nodeContext.getLogger();
    }
    
    public void addRemotePeer(RemotePeerDataContext data) {
	 peersManager.addRemotePeer(data);
    }
    
    public void terminatePeer(String peerId) {
	peersManager.terminatePeer(peerId);
    }
    
    
    public <E extends Event> void registerPeerEventProcessor(Class<E> clazz, PeerContextHandler<E> handler) {
	getBeforePostFilter().addEventHandler(clazz, (c) -> {
	    handler.handleEvent((PeerEventContext<E>) c);
	});
    }
    
    public void registerPeerEventProcessor(String eventType, PeerContextHandler<Event> handler) {
	getBeforePostFilter().addEventHandler(eventType, (c)->{
	    handler.handleEvent((PeerEventContext<Event>)c);
	});
    }
    
    public void registerPeerEventProcessor(PeerContextHandler<Event> handler) {
	getBeforePostFilter().addEventHandler((c)->{
	    handler.handleEvent((PeerEventContext<Event>)c);
	});
    }
    
    public void registerPeerEventProcessor(Condition condition, PeerContextHandler<Event> handler) {
	getBeforePostFilter().addEventHandler(condition, (c)->{
	    handler.handleEvent((PeerEventContext<Event>)c);
	});
    }
}
