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
