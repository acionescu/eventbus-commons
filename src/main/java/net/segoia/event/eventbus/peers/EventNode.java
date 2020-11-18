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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import net.segoia.event.conditions.Condition;
import net.segoia.event.conditions.EventClassMatchCondition;
import net.segoia.event.conditions.StrictEventMatchCondition;
import net.segoia.event.eventbus.CustomEventContext;
import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.EventContext;
import net.segoia.event.eventbus.EventDispatcher;
import net.segoia.event.eventbus.EventsRepository;
import net.segoia.event.eventbus.FilteringEventBus;
import net.segoia.event.eventbus.PeerBindRequest;
import net.segoia.event.eventbus.agents.AgentRegisterRequest;
import net.segoia.event.eventbus.agents.GlobalAgentRegisterRequest;
import net.segoia.event.eventbus.agents.LocalAgentRegisterRequest;
import net.segoia.event.eventbus.peers.events.NodeTerminateEvent;
import net.segoia.event.eventbus.peers.events.bind.ConnectToPeerRequestEvent;
import net.segoia.event.eventbus.peers.events.bind.DisconnectFromPeerRequestEvent;
import net.segoia.event.eventbus.peers.events.bind.PeerBindRequestEvent;
import net.segoia.event.eventbus.peers.security.EventNodeSecurityConfig;
import net.segoia.event.eventbus.peers.security.EventNodeSecurityManager;
import net.segoia.event.eventbus.peers.vo.NodeInfo;
import net.segoia.event.eventbus.peers.vo.bind.ConnectToPeerRequest;
import net.segoia.event.eventbus.peers.vo.bind.DisconnectFromPeerRequest;
import net.segoia.event.eventbus.services.EventNodeServiceContext;
import net.segoia.event.eventbus.services.EventNodeServicesManager;
import net.segoia.event.eventbus.vo.services.EventNodeServiceDefinition;
import net.segoia.event.eventbus.vo.services.EventNodeServiceRef;

/**
 * This encapsulates the connection with another event bus
 * 
 * @author adi
 *
 */
public abstract class EventNode {
    protected EventBusNodeConfig config;

    private NodeInfo nodeInfo;
    protected EventNodeStats stats = new EventNodeStats();

    private EventNodeSecurityManager securityManager;

    private PeersManager peersManager;

    private EventNodeServicesManager servicesManager;

    /**
     * if true, it will call {@link #init()} from the constructor, otherwise somebody else will have to call
     * {@link #lazyInit()}
     */
    private boolean autoinit = true;
    private boolean initialized;

    /**
     * This is used to delegate events to internal handlers
     */
    private FilteringEventBus internalBus;

    /**
     * Keep a map with the synchronous registration requests
     */
    private Map<String, PeerBindRequest> waitingRegistration = new Hashtable<>();

    /**
     * The current node's runtime context
     */
    private EventNodeContext context;

    private List<EventNodeAgent> agents = new ArrayList<>();

    /**
     * A map to keep an index of all buses spawned for extra conditions
     */
    private Map<Condition, FilteringEventBus> extraBuses = new HashMap<>();

    public EventNode(boolean autoinit, EventBusNodeConfig config) {
	this.config = config;
	this.autoinit = autoinit;

	if (autoinit) {
	    init();
	}
    }

    public EventNode(boolean autoinit) {
	this(autoinit, new EventBusNodeConfig());
    }

    public EventNode() {
	this(false);
    }

    public EventNode(EventBusNodeConfig config) {
	this(true, config);
    }

    private void init() {
	registerHandlers();
	nodeConfig();
	setRequestedEventsCondition();
	nodeInit();
	initialized = true;
    }

    /**
     * A subclass may implement this to initialize itself
     */
    protected abstract void nodeInit();

    /**
     * A subclass may implement this to set up extra stuff
     */
    protected void nodeConfig() {

	EventNodeSecurityConfig securityConfig = config.getSecurityConfig();

	securityManager = buildSecurityManager(securityConfig);

	nodeInfo = new NodeInfo(config.getHelper().generatePeerId());
	nodeInfo.setNodeType(config.getNodeType());
	nodeInfo.setNodeAuth(securityConfig.getNodeAuth());
	nodeInfo.setSecurityPolicy(securityConfig.getSecurityPolicy());

	context = new EventNodeContext(this, securityManager);

	peersManager = new PeersManager();
	addAgent(peersManager);
	peersManager.init(context);

	servicesManager = new EventNodeServicesManager();
	EventsRepository.getInstance().load();
	setupStartAgents();
    }
    
    protected void setupStartAgents() {
	List<AgentRegisterRequest<?>> startAgents = config.getAgents();
	if(startAgents != null) {
	    for(AgentRegisterRequest<?> regReq : startAgents) {
		config.getLogger().info("Registering agent from config: "+regReq.getAgent());
		if(regReq instanceof GlobalAgentRegisterRequest) {
		    registerGlobalAgent((GlobalAgentRegisterRequest)regReq);
		}
		else {
		    registerLocalAgent((LocalAgentRegisterRequest)regReq);
		}
	    }
	}
    }

    protected abstract EventNodeSecurityManager buildSecurityManager(EventNodeSecurityConfig securityConfig);

    protected synchronized void addAgent(EventNodeAgent agent) {
	agents.add(agent);
    }

    /**
     * Call this to lazy initialize this node
     */
    public void lazyInit() {
	if (!autoinit && !initialized) {
	    init();
	}
    }

    private void initInternalBus() {
	if (internalBus == null) {
	    /* use this if you want a private thread for this node */
	    // internalBus = EBus.buildSingleThreadedAsyncFilteringEventBus(100, new EventNodeDispatcher());

	    /* by default this node will function on the main loop bus */
	    internalBus = buildInternalBus();
	}
    }

    protected abstract FilteringEventBus buildInternalBus();

    /**
     * Call this to spawn an extra event bus for this node </br>
     * This may be useful if you want to handle events coming from different sources but still keep the same internal
     * state </br>
     * This additional bus should run in the same thread as the internal bus of this node
     * 
     * @param eventDispatcher
     * @return
     */
    protected abstract FilteringEventBus spawnAdditionalBus(EventDispatcher eventDispatcher);

    /**
     * Spawns a bus for a particular event channel
     * 
     * @param channel
     * @param eventDispatcher
     * @return
     */
    protected FilteringEventBus spawnEventBus(Condition cond, EventDispatcher eventDispatcher) {
	FilteringEventBus ceb = spawnAdditionalBus(eventDispatcher);
	extraBuses.put(cond, ceb);
	return ceb;
    }

    protected abstract FilteringEventBus spawnEventBus(Condition cond);

    public FilteringEventBus getEventBus(Condition cond, boolean create) {
	FilteringEventBus ceb = extraBuses.get(cond);
	if (ceb == null && create) {
	    ceb = spawnEventBus(cond);
	}
	return ceb;
    }

    /**
     * Override this to register handlers, but don't forget to call super or you'll lose basic functionality
     */
    protected void registerHandlers() {

	addEventHandler(NodeTerminateEvent.class, (c) -> {
	    EventNodeInfo nodeInfo = c.getEvent().getData();
	    /* accept this command only from us */
	    if (nodeInfo.getNode() != this) {
		return;
	    }

	    onTerminate();
	    /* call terminate on all agents */
	    for (EventNodeAgent agent : agents) {
		agent.terminate();
	    }
	    if (internalBus != null) {
		internalBus.stop();
	    }
	    initialized = false;
	    /* then clean up */
	    cleanUp();

	});

	// /*
	// * if autorelay enabled, then forward the event to the peers marked as agents, that weren't already targeted
	// by
	// * the event
	// */
	// addEventHandler((c) -> {
	// Event event = c.getEvent();
	//
	// if (!config.isAutoRelayEnabled() || event.wasRelayedBy(getId())) {
	// return;
	// }
	//
	// peersRegistry.getAgents().forEach((peerId) -> {
	// if (!event.getForwardTo().contains(peerId) && !peerId.equals(event.to())) {
	// getDirectPeerManager(peerId).onLocalEvent(c);
	// }
	// });
	// });

    };

    protected abstract void setRequestedEventsCondition();

    public void terminate() {
	postInternally(new NodeTerminateEvent(this));
    }

    public abstract void cleanUp();

    protected abstract void onTerminate();

    public synchronized void registerLocalAgent(LocalAgentRegisterRequest registerRequest) {
	LocalEventNodeAgent agent = registerRequest.getAgent();
	addAgent(agent);
	agent.initLocalContext(new LocalAgentEventNodeContext(context));
    }

    public synchronized void registerGlobalAgent(GlobalAgentRegisterRequest registerRequest) {
	GlobalEventNodeAgent agent = registerRequest.getAgent();
	addAgent(agent);
	GlobalAgentEventNodeContext globalAgentContext = new GlobalAgentEventNodeContext(context, peersManager);
	agent.initGlobalContext(globalAgentContext);
	
	List<EventNodeServiceDefinition> providedServices = registerRequest.getProvidedServices();
	if (providedServices == null) {
	    return;
	}
	globalAgentContext.setProvidedServices(providedServices);
	/* register services provided by this agent */
	addServices(registerRequest);
    }

    protected void addServices(AgentRegisterRequest<?> request) {
	List<EventNodeServiceDefinition> providedServices = request.getProvidedServices();
	if (providedServices == null) {
	    return;
	}
	EventNodeAgent agent = request.getAgent();
	for (EventNodeServiceDefinition sd : providedServices) {
	    EventNodeServiceContext sc = new EventNodeServiceContext(agent, sd);
	    servicesManager.addService(sc);
	}
    }

    public EventNodeServiceDefinition getService(EventNodeServiceRef serviceRef) {
	EventNodeServiceContext sc = servicesManager.getService(serviceRef);
	if (sc == null) {
	    return null;
	}
	return sc.getServiceDef();
    }

    public void registerToPeer(ConnectToPeerRequest request) {
	postInternally(new ConnectToPeerRequestEvent(request));
    }

    public void disconnectFromPeer(DisconnectFromPeerRequest request) {
	postInternally(new DisconnectFromPeerRequestEvent(request));
    }

    public void registerPeer(PeerBindRequest request) {
	postInternally(new PeerBindRequestEvent(request));
    }

    public void registerPeer(PeerBindRequestEvent requestEvent) {
	postInternally(requestEvent);
    }

    // public void registerPeer(EventNode peerNode) {
    // registerPeer(new PeerBindRequest(peerNode));
    // }
    //
    // public void registerPeer(EventNode peerNode, Condition condition) {
    // registerPeer(new PeerBindRequest(peerNode, condition));
    // }

    // /**
    // * Will register peer as an agent
    // *
    // * @param peerNode
    // * @param condition
    // */
    // public void registerPeerAsAgent(EventNode peerNode, Condition condition) {
    // registerPeer(new PeerBindRequest(peerNode, condition, true));
    // }
    //
    // public synchronized void registerPeer(PeerBindRequest request) {
    // // getRelayForPeer(request, true);
    // String peerId = request.getRequestingNode().getId();
    // if (request.isSynchronous()) {
    // waitingRegistration.put(peerId, request);
    // }
    //
    // forwardTo(new PeerBindRequestEvent(request), getId());
    //
    // /* if synchronous wait for the registration to finish */
    // if (request.isSynchronous()) {
    // PeerBindRequest r = null;
    // synchronized (waitingRegistration) {
    // r = waitingRegistration.get(peerId);
    // }
    //
    // if (r != null) {
    // synchronized (r) {
    // try {
    // r.wait();
    // } catch (InterruptedException e) {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // }
    // }
    // }
    //
    // }
    //
    // public void unregisterPeer(EventNode peerNode) {
    // String peerId = peerNode.getId();
    // removePeer(peerId);
    // peerNode.removePeer(getId());
    //
    // // TODO: handle any errors that appear during removal
    // onPeerRemoved(peerId);
    // }

    // /**
    // * Called for all events, remote or internally generated
    // *
    // * @param event
    // * @return
    // */
    // protected abstract EventTracker handleEvent(Event event);

    protected void removeEventHandler(CustomEventListener<?> handler) {
	internalBus.removeListener(handler);
    }

    protected void addEventHandler(Class<?> eventClass, CustomEventListener<?> handler) {
	addBusHandler(new EventClassMatchCondition(eventClass), handler);
    }

    protected void addEventHandler(String eventType, CustomEventListener<?> handler) {
	addBusHandler(new StrictEventMatchCondition(eventType), handler);
    }

    protected <E extends Event> void addEventHandler(Class<E> eventClass, CustomEventHandler<E> handler) {
	addEventHandler(eventClass, new CustomEventListener<>(handler));
    }

    protected <E extends Event> void addEventHandler(String eventType, CustomEventHandler<E> handler) {
	addEventHandler(eventType, new CustomEventListener<>(handler));
    }

    protected void addEventHandler(Condition cond, CustomEventHandler<?> handler) {
	addBusHandler(cond, new CustomEventListener<>(handler));
    }

    private void addBusHandler(Condition cond, CustomEventListener<?> handler) {
	initInternalBus();
	internalBus.registerListener(cond, handler);
    }

    protected <E extends Event> void addEventHandler(CustomEventHandler<E> handler) {
	addBusHandler(new CustomEventListener<>(handler));
    }

    protected <E extends Event> void addEventHandler(CustomEventHandler<E> handler, int priority) {
	addBusHandler(new CustomEventListener<>(handler), priority);
    }

    private void addBusHandler(CustomEventListener<?> handler) {
	initInternalBus();
	internalBus.registerListener(handler);
    }

    private void addBusHandler(CustomEventListener<?> handler, int priority) {
	initInternalBus();
	internalBus.registerListener(handler, priority);
    }

    public EventNodeSecurityManager getSecurityManager() {
	return securityManager;
    }

    /**
     * Handle a remote event
     * 
     * @param pc
     * @return - true if we decide that this event is meant for us, false otherwise
     */
    protected boolean handleRemoteEvent(EventContext pc) {
	Event event = pc.getEvent();
	boolean forUs = false;
	/* if this event is sent to one of our peers then forward it to them */
	String destination = event.to();
	if (destination != null) {
	    forUs = destination.equals(getId());
	    if (!forUs) {
		peersManager.forwardTo(event, destination);
	    }
	} else {
	    /* see if this has forwardTo peers */
	    List<String> forwardTo = event.getForwardTo();
	    if (!forwardTo.isEmpty()) {
		/* check if we're targeted by this event */
		forUs = forwardTo.remove(getId());

		/* forward to the others if any */
		if (forwardTo.size() > 0) {
		    peersManager.forwardTo(event, forwardTo);
		}

	    } else {
		// forwardToAll(event);
		/* if this is a public event we can process it too */
		forUs = true;
	    }

	}

	return forUs;
    }

    protected void postInternally(Event event) {

	if (internalBus != null) {
	    internalBus.postEvent(event);
	} else {
	    throw new RuntimeException("Warning! Internal bus for node " + getId() + " is not initialized");
	}
    }
    
    protected void postInternally(EventContext ec) {

	if (internalBus != null) {
	    internalBus.postEventContext(ec);
	} else {
	    throw new RuntimeException("Warning! Internal bus for node " + getId() + " is not initialized");
	}
    }

    protected void postToExtraBusses(EventContext ec) {
	/**
	 * If the bus condition is satisfied, post event to the extra bus
	 */
	for (Condition c : extraBuses.keySet()) {
	    if (c.test(ec)) {
		/* create a new context for each bus, but allow them to share a common parent context to share data */
		extraBuses.get(c).postEventContext(new CustomEventContext<>(ec));
	    }
	}
    }

    /**
     * Post event to extra busses if conditions are met
     * 
     * @param event
     */
    protected void postToExtraBusses(Event event) {
	EventContext ec = new EventContext(event);
	postToExtraBusses(ec);
    }

    /**
     * Called for all received remote events
     * 
     * @param pc
     */
    public void onRemoteEvent(PeerEventContext pc) {

	postInternally(pc.getEvent());
    }

    /**
     * @return the id
     */
    public String getId() {
	return nodeInfo.getNodeId();
    }

    public EventBusNodeConfig getConfig() {
	return config;
    }

    public void setConfig(EventBusNodeConfig config) {
	this.config = config;
    }

    /**
     * @return the stats
     */
    public EventNodeStats getStats() {
	return stats;
    }

    public NodeInfo getNodeInfo() {
	return nodeInfo;
    }

    public boolean isInitialized() {
	return initialized;
    }

    public void stop() {
	internalBus.stop();
    }

    public EventNodeServicesManager getServicesManager() {
        return servicesManager;
    }

    /**
     * Schedules the triggering of the given event after a certain delay
     * 
     * @param event
     * @param delay
     *            - delay in milliseconds
     */
    public abstract void scheduleEvent(Event event, long delay);

}
