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
import net.segoia.event.eventbus.FilteringEventBus;
import net.segoia.event.eventbus.peers.vo.bind.ConnectToPeerRequest;
import net.segoia.event.eventbus.peers.vo.bind.DisconnectFromPeerRequest;
import net.segoia.event.eventbus.vo.security.IdsLinkData;

/**
 * A context for the event node agents that are only aware of the local context
 * 
 * @author adi
 *
 */
public class LocalAgentEventNodeContext {
    private EventNodeContext nodeContext;

    public LocalAgentEventNodeContext(EventNodeContext nodeContext) {
	super();
	this.nodeContext = nodeContext;
    }

    public <E extends Event> void addEventHandler(Class<E> eventClass, CustomEventHandler<E> handler) {
	nodeContext.getNode().addEventHandler(eventClass, handler);
    }

    public <E extends Event> void addEventHandler(String eventType, CustomEventHandler<E> handler) {
	nodeContext.getNode().addEventHandler(eventType, handler);
    }

    public <E extends Event> void addEventHandler(CustomEventHandler<E> handler) {
	nodeContext.getNode().addEventHandler(handler);
    }

    public <E extends Event> void addEventHandler(Condition cond, CustomEventHandler<E> handler) {
	nodeContext.getNode().addEventHandler(cond, handler);
    }

    public void postEvent(Event event) {
	nodeContext.postEvent(event);
    }

    public void registerToPeer(ConnectToPeerRequest request) {
	nodeContext.getNode().registerToPeer(request);
    }
    
    public void disconnectFromPeer(DisconnectFromPeerRequest request) {
	nodeContext.getNode().disconnectFromPeer(request);
    }
    
    public FilteringEventBus getEventBusForCondition(Condition cond) {
	return nodeContext.getNode().getEventBus(cond, true);
    }
    
    public void storeIdsLinkData(IdsLinkData data) {
	nodeContext.getSecurityManager().storeIdsLinkData(data);
    }
    
    public IdsLinkData getIdsLinkData(String idsLinkKey) {
	return nodeContext.getSecurityManager().getIdsLinkData(idsLinkKey);
    }
}
