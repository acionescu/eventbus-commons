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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.segoia.event.eventbus.CustomEventContext;
import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.EventContext;
import net.segoia.event.eventbus.EventHeader;
import net.segoia.event.eventbus.PeerBindRequest;
import net.segoia.event.eventbus.constants.Events;
import net.segoia.event.eventbus.peers.core.EventTransceiver;
import net.segoia.event.eventbus.peers.core.PrivateIdentityData;
import net.segoia.event.eventbus.peers.events.NewPeerEvent;
import net.segoia.event.eventbus.peers.events.PeerAcceptedEvent;
import net.segoia.event.eventbus.peers.events.PeerConnectionFailedEvent;
import net.segoia.event.eventbus.peers.events.PeerLeavingEvent;
import net.segoia.event.eventbus.peers.events.PeerLeftEvent;
import net.segoia.event.eventbus.peers.events.PeerTerminatedEvent;
import net.segoia.event.eventbus.peers.events.auth.PeerAuthAcceptedEvent;
import net.segoia.event.eventbus.peers.events.auth.PeerAuthRejectedEvent;
import net.segoia.event.eventbus.peers.events.auth.PeerAuthRequestEvent;
import net.segoia.event.eventbus.peers.events.auth.PeerProtocolConfirmedEvent;
import net.segoia.event.eventbus.peers.events.bind.ConnectToPeerRequestEvent;
import net.segoia.event.eventbus.peers.events.bind.DisconnectFromPeerRequestEvent;
import net.segoia.event.eventbus.peers.events.bind.PeerBindAcceptedEvent;
import net.segoia.event.eventbus.peers.events.bind.PeerBindRejectedEvent;
import net.segoia.event.eventbus.peers.events.bind.PeerBindRequestEvent;
import net.segoia.event.eventbus.peers.events.session.PeerSessionStartedEvent;
import net.segoia.event.eventbus.peers.routing.RoutingTable;
import net.segoia.event.eventbus.peers.vo.NodeInfo;
import net.segoia.event.eventbus.peers.vo.PeerInfo;
import net.segoia.event.eventbus.peers.vo.PeerTerminatedData;
import net.segoia.event.eventbus.peers.vo.ReasonData;
import net.segoia.event.eventbus.peers.vo.auth.NodeAuth;
import net.segoia.event.eventbus.peers.vo.auth.id.IdentityType;
import net.segoia.event.eventbus.peers.vo.auth.id.NodeIdentity;
import net.segoia.event.eventbus.peers.vo.bind.ConnectToPeerRequest;
import net.segoia.event.eventbus.peers.vo.bind.PeerBindRejected;
import net.segoia.util.data.SetMap;

public class PeersManager extends GlobalEventNodeAgent {
    private PeersManagerConfig config;
    private EventNodeContext nodeContext;
    private EventNodePeersRegistry peersRegistry = new EventNodePeersRegistry();
    private RoutingTable routingTable = new RoutingTable();

    private PeerManagerFactory peerManagerFactory;
    private PeerManagerFactory remotePeerManagerFactory;

    private long lastRemotePeersCleanupTs;

    public void init(EventNodeContext hostNodeContext) {
	this.nodeContext = hostNodeContext;
	config = hostNodeContext.getConfig().getPeersManagerConfig();
	if (config == null) {
	    config = new PeersManagerConfig();
	}

	peerManagerFactory = new PeerManagerAbstractFactory(config);
	remotePeerManagerFactory = new GenericPeerManagerAbstractFactory(config.getDefaultRemotePeerManagerFactory(),
		config.getRemotePeerManagerFactories());

	initGlobalContext(new GlobalAgentEventNodeContext(hostNodeContext, this));
    }

    @Override
    public void terminate() {
	// TODO Auto-generated method stub

    }

    @Override
    protected void config() {
	// TODO Auto-generated method stub

    }

    @Override
    protected void registerHandlers() {
	context.addEventHandler(ConnectToPeerRequestEvent.class, (c) -> {
	    try {
		handleConnectToPeerRequest(c);
	    } catch (Throwable t) {
		nodeContext.getLogger().error("Failed handling connect to peer request", t);
	    } finally {
		c.getEvent().setHandled();
	    }
	});

	context.addEventHandler(PeerBindRequestEvent.class, (c) -> {
	    handlePeerBindRequest(c);

	});

	context.addEventHandler(PeerBindAcceptedEvent.class, (c) -> {
	    handlePeerBindAccepted(c);

	});

	context.addEventHandler(PeerAuthRequestEvent.class, (c) -> {
	    handlePeerAuthRequest(c);
	});

	context.addEventHandler(PeerAuthRejectedEvent.class, (c) -> {
	    handlePeerAuthRejected(c);
	});

	context.addEventHandler("EBUS:PEER:NEW", (c) -> {
	    // Event event = c.getEvent();
	    // String peerId = (String) event.getParam(EventParams.peerId);
	    // String lastRelay = event.getLastRelay();
	    // if (lastRelay != null) {
	    // routingTable.addRoute(peerId, lastRelay);
	    // }

	});

	context.addEventHandler("EBUS:PEER:REMOVED", (c) -> {
	    // Event event = c.getEvent();
	    // String removedPeerId = (String) event.getParam(EventParams.peerId);
	    // String lastRelay = event.getLastRelay();
	    // if (lastRelay != null) {
	    // routingTable.removeAllFor(removedPeerId);
	    // }
	});

	context.addEventHandler(PeerLeavingEvent.class, (c) -> {
	    PeerLeavingEvent event = c.getEvent();
	    PeerInfo data = event.getData().getPeerInfo();
	    String peerId = data.getPeerId();
	    removePeer(peerId);
	    onPeerRemoved(data, event);
	});

	context.addEventHandler(DisconnectFromPeerRequestEvent.class, (c) -> {
	    String peerId = c.getEvent().getData().getPeerId();
	    terminatePeer(peerId);
	});

	context.addEventHandler(PeerConnectionFailedEvent.class, (c) -> {
	    nodeContext.getLogger().error("Connection failed. terminating peer");
	    final PeerInfo peerInfo = c.getEvent().getData().getPeerInfo();

	    String peerId = peerInfo.getPeerId();
	    /* for now terminate node */
	    terminatePeer(peerId);
	});

	context.addEventHandler(PeerAcceptedEvent.class, (c) -> {
	    handlePeerAccepted(c);
	});
	
	context.addEventHandler(PeerTerminatedEvent.class, (c)->{
	    PeerTerminatedEvent event = c.getEvent();
	    PeerTerminatedData data = event.getData();
	    onPeerRemoved(data.getPeerInfo(), event);
	});

	if (config.isAllowRemotePeersOn()) {
	    /* if remote peers are allowed, register a cleanup task */
	    context.getLogger().info("Registering tick minute handler");
	    context.addEventHandler("SYSTEM:TICK:MINUTE", (c) -> {
		if ((System.currentTimeMillis() - lastRemotePeersCleanupTs) >= config
			.getInactiveRemotePeerCeanupPeriod()) {
		    /* if the cleanup wait period has passed, do another round */
		    lastRemotePeersCleanupTs = System.currentTimeMillis();
		    cleanupInactiveRemotePeers();
		}
	    });
	}

    }

    protected void cleanupInactiveRemotePeers() {
	context.getLogger().info("Running inactive remote peers cleanup process");
	Map<String, PeerManager> remotePeers = peersRegistry.getRemotePeers();
	
	int size=remotePeers.size();
	int removed=0;
	int errors=0;
	for (Map.Entry<String, PeerManager> e : remotePeers.entrySet()) {
	    PeerManager pm = e.getValue();
	    PeerManagerStats stats = pm.getStats();
	    if (stats.lifetime() >= config.getMinRemotePeerTTLBeforeCleanup()
		    && stats.durationSinceLastReceivedEvent() > config.getMaxRemotePeerInactivePeriod()) {
		/* if the cleanup condition is met, remove remote peer */
		try {
		    removeRemotePeer(pm);
		    removed++;
		} catch (Throwable t) {
		    errors++;
		    nodeContext.getLogger().error("Failed to remove remote peer "+pm.getPeerId(), t);
		}
	    }
	}
	context.getLogger().info("Remote peers cleanup process finished removed: "+removed+"/"+size+", errors: "+errors);
    }

    protected void removeRemotePeer(PeerManager pm) {
	if (pm != null) {
	    context.getLogger().info("Removing remote peer "+pm.getPeerId());
	    peersRegistry.removeRemotePeer(pm.getPeerId());
	    
	    /* remove id key index */
	    peersRegistry.removeRemotePeerForIdkey(pm.getPeerContext().getPeerRootIdentityKey());
	    
	    routingTable.removeAllFor(pm.getPeerId());
	    
	    /* call cleanUp not terminate */
	    pm.terminate(new ReasonData(99, "Terminated due to inactivity"));
	    
	}
	

	
    }

    protected void terminatePeer(String peerId) {
	PeerManager pm = peersRegistry.getDirectPeerManager(peerId);
	if (pm != null) {
	    pm.terminate();

	}
	pm = peersRegistry.getPendingPeerManager(peerId);
	if (pm != null) {
	    pm.terminate();

	}
	removePeer(peerId);

    }

    protected void handlePeerAccepted(CustomEventContext<PeerAcceptedEvent> c) {
	/* mark the peer as direct */
	PeerInfo data = c.getEvent().getData();
	String peerId = data.getPeerId();
	peersRegistry.setPendingPeerAsDirectPeer(peerId);

	/* index the peer by id key */
	peersRegistry.indexDirectPeerByIdKey(getDirectPeer(peerId));

	/* Notify everybody that we have a new peer */
	context.postEvent(new NewPeerEvent(data));
    }

    public void handleConnectToPeerRequest(CustomEventContext<ConnectToPeerRequestEvent> c) {
	ConnectToPeerRequest data = c.getEvent().getData();
	if (data == null) {
	    throw new RuntimeException("Can't connect to peer. No connect information provided");
	}

	EventTransceiver transceiver = data.getTransceiver();

	/* generate a local id for this peer */
	String peerId = nodeContext.generatePeerId();

	/* create a manager for this peer */
	PeerContext peerContext = new PeerContext(peerId, transceiver);
	/* set the custom peer id if specified */
	peerContext.setCustomPeerId(data.getCustomPeerId());

	List<PrivateIdentityData<?>> ourIdentities = data.getOurIdentities();

	if (ourIdentities != null && !ourIdentities.isEmpty()) {
	    nodeContext.getLogger().info("Connecting to peer with custom identities " + ourIdentities.size());
	    peerContext.setOurAvailableIdentities(ourIdentities);

	    NodeInfo defaultNodeInfo = nodeContext.getNodeInfo();

	    /* let's create a custom node info with specified identities */
	    NodeInfo customNodeInfo = new NodeInfo(defaultNodeInfo.getNodeId());
	    customNodeInfo.setSecurityPolicy(defaultNodeInfo.getSecurityPolicy());
	    NodeAuth customNodeAuth = new NodeAuth();
	    List<NodeIdentity<?>> customIdentities = new ArrayList<>();
	    for (PrivateIdentityData<?> pid : ourIdentities) {
		if (pid == null) {
		    throw new RuntimeException("Can't add null identity");
		}
		NodeIdentity<? extends IdentityType> pni = pid.getPublicNodeIdentity();
		if (pni == null) {
		    throw new RuntimeException("Can't add null public node identity");
		}
		customIdentities.add(pni);
	    }

	    customNodeAuth.setIdentities(customIdentities);
	    customNodeInfo.setNodeAuth(customNodeAuth);

	    peerContext.setOurNodeInfo(customNodeInfo);

	} else {
	    peerContext.setOurNodeInfo(nodeContext.getNodeInfo());
	}

	nodeContext.getLogger().info("Creating peer manager " + peerId);
	PeerManager peerManager = peerManagerFactory.buildPeerManager(peerContext);
	peerContext.setNodeContext(nodeContext);

	peerManager.setInServerMode(true);

	peersRegistry.setPendingPeerManager(peerManager);

	try {
	    peerManager.start();
	} catch (Throwable t) {
	    nodeContext.getLogger().error(peerId + ": Can't start peer manager", t);
	    terminatePeer(peerId);
	}
    }

    public void handlePeerBindRequest(CustomEventContext<PeerBindRequestEvent> c) {

	PeerBindRequest req = c.getEvent().getData();

	EventTransceiver transceiver = req.getTransceiver();

	if (!nodeContext.getConfig().isAllowServerMode()) {
	    PeerBindRejected pbr = new PeerBindRejected();
	    pbr.setReason("Node not working in server mode");
	    try {
		PeerBindRejectedEvent event = new PeerBindRejectedEvent(pbr);
		String json = event.toJson();
		if (json != null) {
		    transceiver.sendData(json.getBytes("UTF-8"));
		} else {
		    throw new RuntimeException("Cant jsontify event " + event.getEt());
		}
	    } catch (Throwable e) {
		nodeContext.getLogger().error("Can't send bind rejection message", e);

	    } finally {
		transceiver.terminate();
	    }
	    return;
	}

	/* generate a local id for this peer */
	String peerId = nodeContext.generatePeerId();

	/* create a manager for this peer */
	PeerManager peerManager = peerManagerFactory.buildPeerManager(new PeerContext(peerId, transceiver));
	peerManager.getPeerContext().setNodeContext(nodeContext);

	peersRegistry.setPendingPeerManager(peerManager);

	try {
	    peerManager.start();
	} catch (Throwable t) {
	    nodeContext.getLogger().error("Can't start peer manager", t);
	    terminatePeer(peerId);
	}

    }

    public void handlePeerBindAccepted(CustomEventContext<PeerBindAcceptedEvent> c) {

    }

    public void handlePeerAuthRequest(CustomEventContext<PeerAuthRequestEvent> c) {

    }

    public void handlePeerAuthRejected(CustomEventContext<PeerAuthRejectedEvent> c) {
	// TODO: implement this
    }

    public void handlePeerAuthAccepted(CustomEventContext<PeerAuthAcceptedEvent> c) {

    }

    public void handleProtocolConfirmed(CustomEventContext<PeerProtocolConfirmedEvent> c) {

    }

    public void handleSessionStartedEvent(CustomEventContext<PeerSessionStartedEvent> c) {

    }

    protected PeerManager getPeerManagerById(String peerId) {
	return peersRegistry.getDirectPeerManager(peerId);
    }

    protected PeerManager getPeerManagerForEvent(Event event) {
	String localPeerId = event.getLastRelay();
	return peersRegistry.getPendingPeerManager(localPeerId);
    }

    protected String getLocalNodeId() {
	return nodeContext.getLocalNodeId();
    }

    protected void forwardToDirectPeers(Event event) {
	EventContext ec = new EventContext(event, null);
	for (PeerManager peerManager : peersRegistry.getDirectPeers().values()) {
	    // EventRelay r = peerManager.getRelay();
	    // r.onLocalEvent(ec);
	    peerManager.forwardToPeer(event);
	}
    }

    protected void forwardTo(Event event, String to) {
	event.to(to);
	// if (hostNode.getId().equals(to)) {
	// postInternally(event);
	// return;
	// }

	EventContext ec = new EventContext(event, null);
	/* first check if the destination is one of the peers */
	PeerManager peerManager = peersRegistry.getDirectPeerManager(to);

	if (peerManager == null) {
	    /* see if this is a remote peer */
	    peerManager = peersRegistry.getRemotePeerManager(to);
	}
	/* if it is, forward the event */
	if (peerManager != null) {
	    peerManager.forwardToPeer(event);
	}
	/* otherwise, set destination and forward it to the peers */
	else if (!event.wasRelayedBy(getLocalNodeId())) {
	    forwardToDirectPeers(event);
	}
    }

    protected boolean forwartToPeerWithIdKey(Event event, String targetIdKey) {
	PeerManager pm = peersRegistry.getDirectPeerByIdKey(targetIdKey);
	if (pm != null) {
	    forwardTo(event, pm.getPeerId());
	    return true;
	}
	return false;
    }

    // protected EventRelay getRelayForPeer(String peerId) {
    // /* check direct peers */
    // EventRelay peerRelay = getDirectPeer(peerId);
    // if (peerRelay == null) {
    // /* check remote peers */
    // peerRelay = getRemotePeerManager(peerId);
    // }
    //
    // return peerRelay;
    // }

    protected void forwardTo(Event event, List<String> peerIds) {
	/* check if if we are targeted by the event as well */
	// if (peerIds.contains(getLocalNodeId())) {
	// postInternally(event);
	// return;
	// }

	/* only forward further, if there are other targeted nodes except us */
	if (peerIds.size() <= 0) {
	    return;
	}
	/* Keep the peers indexed by the next hop in the path to them */
	SetMap<String, String> peersByVia = new SetMap<>();

	EventContext ec = new EventContext(event, null);
	for (String cto : peerIds) {
	    String cvia = null;
	    /* if this is a direct peer or us, use targeted peer id as via */
	    if (getDirectPeer(cto) != null || getLocalNodeId().equals(cto)) {
		cvia = cto;
	    } else {
		/* if it's a remote peer, we should have a via in the routing table */
		cvia = routingTable.getBestViaTo(cto);
	    }

	    if (cvia == null) {
		/*
		 * if we can't find a via for a remote node, then forward to all, however this should not happen under
		 * normal conditions
		 */
		System.err.println(getLocalNodeId() + ": Couldn't find a via for " + cto + " , forwarding to all");
		event.setForwardTo(peerIds);
		forwardToDirectPeers(event);
		return;
	    } else {
		peersByVia.add(cvia, cto);
	    }
	}

	/* now we have to send an event to each via */
	for (Map.Entry<String, Set<String>> re : peersByVia.entrySet()) {
	    /*
	     * we have to create a new event with the header field forwardTo updated with the peers that we can reach
	     * through each via
	     */

	    /* if we want to forward messages via this relay, we have to add the nodes in the forwardTo field */
	    String via = re.getKey();

	    List<String> ftp = new ArrayList(peersByVia.get(via));
	    if (ftp != null) {
		/* if our rules forbid us to forward to this node, then don't bother */
		if (!isEventForwardingAllowed(ec, via)) {
		    continue;
		}
		PeerManager viaPeer = getDirectPeer(via);

		/* clone event, rewrite forwardTo and send it directly */
		Event ce = event.clone();
		ce.setForwardTo(ftp);

		/* since this is a forward, we will send directly, regardless of the rules of the peer */
		viaPeer.forwardToPeer(ce);

	    }
	}
    }

    protected void forwardToAllKnown(Event event) {
	EventContext ec = new EventContext(event, null);
	List<String> targetedPeers = new ArrayList(getKnownPeers(ec));

	event.setForwardTo(targetedPeers);
	forwardTo(event, targetedPeers);

    }

    protected Set<String> getKnownPeers(EventContext ec) {

	// Stream<String> dpStream = peersRegistry.getDirectPeers().entrySet().stream()
	// .filter((e) -> e.getValue().getPeerContext().getRelay().isForwardingAllowed(ec)).map((e) -> e.getKey());
	// Stream<String> rpStream = peersRegistry.getRemotePeers().entrySet().stream()
	// .filter((e) -> e.getValue().getPeerContext().getRelay().isForwardingAllowed(ec)).map((e) -> e.getKey());
	// Set<String> targetedPeers = Stream.concat(dpStream, rpStream).collect(Collectors.toSet());

	Set<String> targetedPeers = new HashSet<>();

	targetedPeers.addAll(getAllowedForwardingPeers(peersRegistry.getDirectPeers(), ec));
	targetedPeers.addAll(getAllowedForwardingPeers(peersRegistry.getRemotePeers(), ec));

	return targetedPeers;
    }

    protected Set<String> getAllowedForwardingPeers(Map<String, PeerManager> candidates, EventContext ec) {
	Set<String> allowed = new HashSet<>();
	for (Map.Entry<String, PeerManager> e : candidates.entrySet()) {
	    if (e.getValue().getPeerContext().getRelay().isForwardingAllowed(ec)) {
		allowed.add(e.getKey());
	    }
	}
	return allowed;
    }

    protected PeerManager getDirectPeer(String id) {
	return peersRegistry.getDirectPeerManager(id);
    }

    protected PeerManager getRemotePeerManager(String id) {
	return peersRegistry.getRemotePeerManager(id);
    }

    public PeerManager getRemotePeerManagerByIdKey(String idKey, PeerManager proxyManger, boolean create) {
	if (idKey == null) {
	    throw new IllegalArgumentException("idKey can't be null");
	}

	PeerManager remotePeer = peersRegistry.getRemotPeerByIdKey(idKey);

	if (remotePeer == null && create) {
	    remotePeer = addRemotePeer(idKey, proxyManger);
	}

	return remotePeer;
    }

    protected PeerManager addRemotePeer(String idKey, PeerManager proxyManger) {
	if (!config.isAllowRemotePeersOn()) {
	    throw new RuntimeException("Remote peers are not allowed. Restart with 'allowRemotePeersOn' set to true");
	}

	if (proxyManger == null) {
	    throw new IllegalArgumentException("proxyManager can't be null");
	}

	/* generate a local id for this peer */
	String peerId = nodeContext.generatePeerId();

	/* create a manager for this peer */
	PeerContext peerContext = new RemotePeerContext(peerId, proxyManger, idKey);
	PeerManager remotePeer = remotePeerManagerFactory.buildPeerManager(peerContext);
	peerContext.setNodeContext(nodeContext);

	peersRegistry.addRemotePeer(remotePeer);
	peersRegistry.indexRemotePeerByIdKey(remotePeer);
	remotePeer.onReady();

	onNewRemotePeer(remotePeer);

	return remotePeer;
    }

    protected void onNewRemotePeer(PeerManager pm) {

    }

    public boolean isEventForwardingAllowed(EventContext ec, String peerId) {
	Event event = ec.event();

	/* if a destination is specified, only forward to that peerId */
	String destination = event.to();
	if (destination != null && !event.wasRelayedBy(peerId)) {
	    return true;
	}

	/* if this wasn't forwarded allow it */
	String sourceBusId = event.from();
	if (sourceBusId == null) {
	    return true;
	}
	/* don't forward an event to a peer that already relayed that event */
	else if (nodeContext.getConfig().isAutoRelayEnabled() && !event.wasRelayedBy(peerId)) {
	    return true;
	}

	return false;
    }

    protected void updateRoute(Event event) {
	String from = event.from();
	String via = event.getLastRelay();

	if (from != null && !from.equals(via)) {
	    routingTable.addRoute(from, via);
	}
    }

    /**
     * Called when a direct peer has called {@link #terminate()} or simply dropped the connection
     * 
     * @param peerId
     */
    public void onPeerLeaving(PeerLeavingEvent peerLeavingEvent) {

    }

    /**
     * Called after a peer has been unregistered </br>
     * By default sends an EBUS:PEER:REMOVED message </br>
     * Override this if you want specific behavior
     * 
     * @param peerNode
     */
    protected void onPeerRemoved(PeerInfo peerInfo, Event reasonEvent) {
	EventHeader reasonHeader = reasonEvent.getHeader();
	PeerLeftEvent event = new PeerLeftEvent(peerInfo);
	EventHeader header = event.getHeader();

	header.setChannel(reasonHeader.getChannel());
	header.setSourceAgentId(reasonHeader.getSourceAgentId());
	header.setRootAgentId(reasonHeader.getRootAgentId());
	context.postEvent(event);
    }

    protected void removePeer(String peerId) {
	PeerManager peerManager = peersRegistry.removeDirectPeer(peerId);
	if (peerManager != null) {
	    /* remove id key index */
	    peersRegistry.removeDirectPeerByIdKey(peerManager.getPeerContext().getPeerRootIdentityKey());
	    /* call cleanUp not terminate */
	    peerManager.cleanUp();
	}
	/* remove also pending peers */
	peerManager = peersRegistry.removePendingPeer(peerId);
	if (peerManager != null) {
	    /* call cleanUp not terminate */
	    peerManager.cleanUp();
	}

	routingTable.removeAllFor(peerId);
    }

    /**
     * Called after a new peer has been registered </br>
     * By default sends an EBUS:PEER:NEW event </br>
     * Override this if you want specific behavior
     * 
     * @param peerNode
     */
    protected void onNewPeer(String peerId) {
	if (peersRegistry.getDirectPeerManager(peerId).isRemoteAgent()) {
	    peersRegistry.addAgent(peerId);
	    /* don't broadcast agents as peers, this is private business */
	    return;
	}
	Event nne = Events.builder().ebus().peer().newPeer().build();
	nne.addParam("peerId", peerId);
	forwardToDirectPeers(nne);
    }

    @Override
    protected void agentInit() {
	// TODO Auto-generated method stub

    }

    public Map<String, String> getDirectPeersByRootKey(Collection<String> rootKeys) {
	Map<String, String> activePeers = new HashMap<>();

	for (PeerManager pm : peersRegistry.getDirectPeers().values()) {
	    for (String rk : rootKeys) {
		if (rk.equals(pm.getPeerContext().getPeerRootIdentityKey())) {
		    activePeers.put(rk, pm.getPeerId());
		    if (activePeers.size() == rootKeys.size()) {
			return activePeers;
		    }
		}
	    }
	}

	return activePeers;
    }

}
