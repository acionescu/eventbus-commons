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
package net.segoia.event.eventbus.peers.manager.states;

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.FilteringEventProcessor;
import net.segoia.event.eventbus.PassthroughCustomEventContextListenerFactory;
import net.segoia.event.eventbus.peers.PeerContextHandler;
import net.segoia.event.eventbus.peers.PeerEventContext;
import net.segoia.event.eventbus.peers.PeerManager;
import net.segoia.event.eventbus.peers.core.PeerCommErrorEvent;

public abstract class PeerManagerState {
    private String id;
    private FilteringEventProcessor localEventsProcessor = new FilteringEventProcessor(new PassthroughCustomEventContextListenerFactory());
    private FilteringEventProcessor peerEventsProcessor = new FilteringEventProcessor(new PassthroughCustomEventContextListenerFactory());

    public PeerManagerState() {
	init();
    }
    
    public PeerManagerState(String id) {
	this.id = id;
	init();
    }

    private void init() {
	registerLocalEventHandlers();
	registerPeerEventHandlers();
    }

    public abstract void onEnterState(PeerManager peerManager);

    public abstract void onExitState(PeerManager peerManager);

    protected abstract void registerLocalEventHandlers();

    protected abstract void registerPeerEventHandlers();

    public <E extends Event> boolean handleEventFromPeer(PeerEventContext<E> ec) {
	return peerEventsProcessor.processEvent(ec);
    }

    protected <E extends Event> void registerLocalEventProcessor(Class<E> clazz, PeerContextHandler<E> handler) {
	localEventsProcessor.addEventHandler(clazz, (c) -> {
	    handler.handleEvent((PeerEventContext<E>) c);
	});
    }

    protected <E extends Event> void registerPeerEventProcessor(Class<E> clazz, PeerContextHandler<E> handler) {
	peerEventsProcessor.addEventHandler(clazz, (c) -> {
	    handler.handleEvent((PeerEventContext<E>) c);
	});
    }
    
    protected void registerPeerEventProcessor(String eventType, PeerContextHandler<Event> handler) {
	peerEventsProcessor.addEventHandler(eventType, (c)->{
	    handler.handleEvent((PeerEventContext<Event>)c);
	});
    }
    
    protected void registerPeerEventProcessor(PeerContextHandler<Event> handler) {
	peerEventsProcessor.addEventHandler((c)->{
	    handler.handleEvent((PeerEventContext<Event>)c);
	});
    }
    
    protected void setPeerEventNotProcessedHandler(PeerContextHandler<Event> handler) {
	peerEventsProcessor.setEventNotProcessedHandler((c)->{
	    handler.handleEvent((PeerEventContext<Event>)c);
	});
    }
    
    public void handlePeerError(PeerEventContext<PeerCommErrorEvent> eventContext) {
	
    }
    
    public String getId() {
	return getClass().getSimpleName();
    }

    @Override
    public String toString(){
        return getId();
    }
}
