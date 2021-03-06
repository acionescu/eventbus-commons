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

import net.segoia.event.eventbus.EBusVM;
import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.EventContext;
import net.segoia.event.eventbus.EventHeader;
import net.segoia.event.eventbus.peers.comm.CommProtocolEventTransceiver;
import net.segoia.event.eventbus.peers.comm.PeerCommManager;
import net.segoia.event.eventbus.peers.core.EventTransceiver;
import net.segoia.event.eventbus.peers.core.PeerCommErrorEvent;
import net.segoia.event.eventbus.peers.events.PeerAcceptedEvent;
import net.segoia.event.eventbus.peers.events.PeerLeavingEvent;
import net.segoia.event.eventbus.peers.events.auth.PeerAuthAcceptedEvent;
import net.segoia.event.eventbus.peers.events.session.PeerSessionStartedEvent;
import net.segoia.event.eventbus.peers.exceptions.PeerSessionException;
import net.segoia.event.eventbus.peers.manager.states.PeerManagerState;
import net.segoia.event.eventbus.peers.manager.states.client.AcceptedByPeerState;
import net.segoia.event.eventbus.peers.manager.states.client.AuthToPeerState;
import net.segoia.event.eventbus.peers.manager.states.client.BindToPeerState;
import net.segoia.event.eventbus.peers.manager.states.client.ConfirmProtocolToPeerState;
import net.segoia.event.eventbus.peers.manager.states.server.PeerAcceptedState;
import net.segoia.event.eventbus.peers.manager.states.server.PeerAuthAcceptedState;
import net.segoia.event.eventbus.peers.manager.states.server.PeerBindAcceptedState;
import net.segoia.event.eventbus.peers.manager.states.server.PeerBindRequestedState;
import net.segoia.event.eventbus.peers.security.CommDataContext;
import net.segoia.event.eventbus.peers.security.CommManager;
import net.segoia.event.eventbus.peers.security.CommOperationException;
import net.segoia.event.eventbus.peers.security.CryptoHelper;
import net.segoia.event.eventbus.peers.security.EventNodeSecurityManager;
import net.segoia.event.eventbus.peers.security.PeerCommContext;
import net.segoia.event.eventbus.peers.security.SignCommOperationOutput;
import net.segoia.event.eventbus.peers.vo.NodeInfo;
import net.segoia.event.eventbus.peers.vo.PeerErrorData;
import net.segoia.event.eventbus.peers.vo.PeerInfo;
import net.segoia.event.eventbus.peers.vo.PeerLeavingData;
import net.segoia.event.eventbus.peers.vo.PeerLeavingReason;
import net.segoia.event.eventbus.peers.vo.auth.PeerAuthRequest;
import net.segoia.event.eventbus.peers.vo.bind.PeerBindAccepted;
import net.segoia.event.eventbus.peers.vo.comm.CommunicationProtocol;
import net.segoia.event.eventbus.peers.vo.comm.CommunicationProtocolConfig;
import net.segoia.event.eventbus.peers.vo.comm.CommunicationProtocolDefinition;
import net.segoia.event.eventbus.peers.vo.comm.NodeCommunicationStrategy;
import net.segoia.event.eventbus.peers.vo.session.SessionInfo;
import net.segoia.event.eventbus.peers.vo.session.SessionKey;
import net.segoia.event.eventbus.peers.vo.session.SessionKeyData;
import net.segoia.event.eventbus.peers.vo.session.SessionStartedData;

/**
 * Implements a certain communication policy with a peer over an {@link EventRelay}
 *
 * @author adi
 *
 */
public class PeerManager implements PeerEventListener {
    private PeerManagerConfig config = new PeerManagerConfig();
    private PeerContext peerContext;
    private String peerId;
    private String peerType;

    private PeerManagerState state;

    /**
     * The state in which the communication with the peer is established and can implement whatever app logic is needed
     */
    private PeerManagerState acceptedState;

    private PeersManagerContext peersContext;

    /* when functioning as client, states */
    public static PeerManagerState BIND_TO_PEER = new BindToPeerState();
    public static PeerManagerState AUTH_TO_PEER = new AuthToPeerState();
    public static PeerManagerState CONFIRM_PROTOCOL_TO_PEER = new ConfirmProtocolToPeerState();
    public static PeerManagerState ACCEPTED_BY_PEER = new AcceptedByPeerState();

    /* when functioning as server, states */
    public static PeerManagerState PEER_BIND_REQUESTED = new PeerBindRequestedState();
    public static PeerManagerState PEER_BIND_ACCEPTED = new PeerBindAcceptedState();
    public static PeerManagerState PEER_AUTH_ACCEPTED = new PeerAuthAcceptedState();
    public static PeerManagerState PEER_ACCEPTED = new PeerAcceptedState();
    
    static {
	/* make sure the events area indexed before starting */
	new PeerBindAccepted();
	new PeerAuthAcceptedEvent();
	new PeerSessionStartedEvent();
    }

    public PeerManager(String peerId, EventTransceiver transceiver) {
	this(new PeerContext(peerId, transceiver));
    }

    public PeerManager(PeerContext peerContext) {
	this(peerContext, true);
    }

    public PeerManager(PeerContext peerContext, boolean autoInit) {
	super();
	this.peerContext = peerContext;
	if (autoInit) {
	    init(peerContext);
	}
    }

    protected void init(PeerContext peerContext) {
	EventTransceiver transceiver = peerContext.getTransceiver();
	DefaultEventRelay relay = new DefaultEventRelay(peerId, transceiver);
	/* listen on events from peer */
	relay.setRemoteEventListener(this);
	peerContext.setRelay(relay);
	this.peerType = transceiver.getClass().getSimpleName();
	this.peerId = peerContext.getPeerId();
	/* bind relay to transceiver */
	relay.bind();
    }

    public void goToState(PeerManagerState newState) {
	getNodeContext().getLogger().debug("State transition:" + peerId + ":" + state + "->" + newState);
	if (state != null) {
	    state.onExitState(this);
	}
	state = newState;
	state.onEnterState(this);
    }

    public String getPeerId() {
	return peerContext.getPeerId();
    }

    public void setPeerInfo(NodeInfo peerInfo) {
	peerContext.setPeerInfo(peerInfo);
    }

    public NodeInfo getPeerInfo() {
	return peerContext.getPeerInfo();
    }

    public void start() {
	/* the peer is the server, we are the client, so we need to initiate connection */
	if (peerContext.isInServerMode()) {
	    startInClientMode();

	} else {
	    /* we are the server and a client requested to bind to us */
	    startInServerMode();
	}
    }

    protected void startInClientMode() {
	if (acceptedState == null) {
	    acceptedState = ACCEPTED_BY_PEER;
	}
	goToState(BIND_TO_PEER);
    }

    protected void startInServerMode() {
	if (!getNodeContext().getConfig().isAllowServerMode()) {
	    throw new RuntimeException("Not allowed to start in server mode");
	}
	if (acceptedState == null) {
	    acceptedState = PEER_ACCEPTED;
	}

	goToState(PEER_BIND_REQUESTED);
    }

    protected void setAcceptedState(PeerManagerState acceptedState) {
	this.acceptedState = acceptedState;
    }

    public PeerManagerState getAcceptedState() {
	return acceptedState;
    }

    public void terminate() {
	EventRelay relay = peerContext.getRelay();
	if (relay != null) {
	    relay.terminate();
	}
	else if(getNodeContext().getLogger().isDebugEnabled()) {
	    getNodeContext().getLogger().debug("Can't terminate peer "+peerId+". No relay defined");
	}
    }

    protected void cleanUp() {

    }

    public void setUpPeerCommContext() {
	PeerCommContext peerCommContext = buildPeerCommContext();
	peerContext.setPeerCommContext(peerCommContext);
	getNodeContext().getSecurityManager().onPeerNodeAuth(peerContext);

	PeerCommManager peerCommManager = new PeerCommManager();
	peerContext.setPeerCommManager(peerCommManager);
    }

    public void setUpSessionCommManager() {
//	setUpPeerCommContext();

	/* get the session communication manager */
	EventNodeSecurityManager securityManager = getNodeContext().getSecurityManager();

	/*
	 * Get the session comm manager
	 */
	CommManager sessionCommManager = securityManager.getSessionCommManager(peerContext.getPeerCommContext());

	peerContext.getPeerCommManager().setSessionCommManager(sessionCommManager);
    }

    public void generateNewSession() throws PeerSessionException {
	EventNodeSecurityManager securityManager = getNodeContext().getSecurityManager();

//	try {
	securityManager.generateNewSessionKey(peerContext);

//	} catch (PeerSessionException e) {
//	    handleError(e);
//
//	}
    }

    public void setUpDirectCommManager() {
	EventNodeSecurityManager securityManager = getNodeContext().getSecurityManager();
	PeerCommContext peerCommContext = peerContext.getPeerCommContext();
	PeerCommManager peerCommManager = peerContext.getPeerCommManager();

	/* now we can build a direct comm manager */
	CommManager directCommManager = securityManager.getDirectCommManager(peerCommContext);

	peerCommManager.setDirectCommManager(directCommManager);
    }

    /**
     * This is called only when the node is operating in server mode
     */
    public void onProtocolConfirmed() throws PeerSessionException, CommOperationException {
	setUpPeerCommContext();
	setUpSessionCommManager();
	generateNewSession();
	setUpDirectCommManager();

	/* send the session */
	startNewPeerSession();

	/* activate the transceiver implementing the protocol */
	setUpCommProtocolTransceiver();

    }

    protected SessionInfo generateSessionInfo() throws CommOperationException {
	SessionKey sessionKey = peerContext.getSessionKey();

	PeerCommManager peerCommManager = peerContext.getPeerCommManager();

	SessionInfo sessionInfo = null;

//	try {
	// SessionKeyOutgoingAccumulator opAcc = new SessionKeyOutgoingAccumulator(
	// new OperationData(sessionKey.getKeyBytes()));

	/* prepare session token */
	CommDataContext processedSessionData = peerCommManager.getSessionCommManager()
		.processsOutgoingData(new CommDataContext(sessionKey.getKeyBytes()));

	SignCommOperationOutput out = (SignCommOperationOutput) processedSessionData.getResult();

	CryptoHelper cryptoHelper = getNodeContext().getSecurityManager().getCryptoHelper();

	/* encode base64 */
	String sessionToken = cryptoHelper.base64Encode(out.getData());
	String sessionTokenSignature = cryptoHelper.base64Encode(out.getSignature());

	// send the iv as well
	String keyIv = cryptoHelper.base64Encode(sessionKey.getIv());

	SessionKeyData sessionKeyData = new SessionKeyData(sessionToken, sessionTokenSignature, sessionKey.getKeyDef());
	sessionKeyData.setKeyIv(keyIv);

	sessionInfo = new SessionInfo(sessionKey.getSessionId(), sessionKeyData);

//	} catch (CommOperationException e) {
//	    handleError(e);
//	}

	return sessionInfo;
    }

    public void startNewPeerSession() throws CommOperationException {
	SessionInfo sessionInfo = generateSessionInfo();
	if (sessionInfo != null) {
	    /* now we can send the session start event */
	    sendNewSessionInfoToPeer(sessionInfo);
	}
    }

    protected void sendNewSessionInfoToPeer(SessionInfo sessionInfo) {
	forwardToPeer(new PeerSessionStartedEvent(new SessionStartedData(sessionInfo)));
    }

    public void setUpCommProtocolTransceiver() {
	/* chain a protocol enforcing event transceiver */
	EventRelay relay = peerContext.getRelay();
	CommProtocolEventTransceiver commProtocolEventTransceiver = new CommProtocolEventTransceiver(
		relay.getTransceiver(), peerContext);
	relay.bind(commProtocolEventTransceiver);
    }

    public void handleError(Exception e) {
	getNodeContext().getLogger().error(getPeerId() + ": Peer manager error", e);
    }

    public void handleError(String msg, Throwable t) {
	getNodeContext().getLogger().error(getPeerId() + ": " + msg, t);
    }

    protected PeerCommContext buildPeerCommContext() {
	CommunicationProtocol commProtocol = peerContext.getCommProtocol();
	CommunicationProtocolDefinition protocolDefinition = commProtocol.getProtocolDefinition();
	NodeCommunicationStrategy clientCommStrategy = protocolDefinition.getClientCommStrategy();
	NodeCommunicationStrategy serverCommStrategy = protocolDefinition.getServerCommStrategy();

	CommunicationProtocolConfig protocolConfig = commProtocol.getConfig();

	NodeCommunicationStrategy txStrategy = null;
	NodeCommunicationStrategy rxStrategy = null;
	int ourIdentityIndex;
	int peerIdentityIndex;

	if (peerContext.isInServerMode()) {
	    /* we're acting as client */
	    txStrategy = clientCommStrategy;
	    rxStrategy = serverCommStrategy;

	    ourIdentityIndex = protocolConfig.getClientNodeIdentity();
	    peerIdentityIndex = protocolConfig.getServerNodeIdentity();
	} else {
	    /* we're acting as server */
	    txStrategy = serverCommStrategy;
	    rxStrategy = clientCommStrategy;

	    peerIdentityIndex = protocolConfig.getClientNodeIdentity();
	    ourIdentityIndex = protocolConfig.getServerNodeIdentity();
	}

	return new PeerCommContext(ourIdentityIndex, peerIdentityIndex, txStrategy, rxStrategy, peerContext);
    }

    public void onReady() {
	PeerAcceptedEvent acceptedEvent = new PeerAcceptedEvent(getLocalPeerInfo());
	Event causeEvent = peerContext.getCauseEvent();
	if(causeEvent != null) {
	    causeEvent.setAsCauseFor(acceptedEvent);
	}
	postEvent(acceptedEvent);
	goToState(acceptedState);
    }

    public void postEvent(Event event) {
	EventHeader header = event.getHeader();
	header.setSourceAgentId(peerContext.getPeerIdentityKey());
	header.setRootAgentId(peerContext.getPeerRootIdentityKey());
	header.setChannel(peerContext.getCommunicationChannel());
	header.setSourceAlias(peerContext.getPeerAlias());
	header.setSourceType(peerType);

	peersContext.handlePeerEvent(new PeerEventContext<>(event, this));

    }

    public void handlePeerBindAccepted(PeerBindAccepted data) {
	peerContext.setPeerInfo(data.getNodeInfo());
    }

    public void handlePeerAuthRequest(PeerAuthRequest data) {
	peerContext.setPeerInfo(data.getNodeInfo());
    }

    public void handleEventFromPeer(Event event) {
	boolean processed = state.handleEventFromPeer(new PeerEventContext(event, this));
    }

    public void forwardToPeer(Event event) {
	try {
	    peerContext.sendEventToPeer(event);
	} catch (Throwable t) {
	    handleError("Failed to send event", t);
	}
    }

    public void setInServerMode(boolean inServerMode) {
	peerContext.setInServerMode(inServerMode);
    }

    public PeerContext getPeerContext() {
	return peerContext;
    }

    public boolean isRemoteAgent() {
	return peerContext.isRemoteAgent();
    }
    
    private boolean isAccepted(Event event) {
	return config.getPeerEventAcceptCondition().test(new PeerEventContext<>(event, this));
    }

    protected void peerEventPreProcessing(Event event) {
	if (!config.isAllowPeerRelays()) {
	    /* make sure we don't allow peers to inject relays */
	    event.clearRelays();
	}
	event.addRelay(getPeerId());
    }

    @Override
    public void onPeerEvent(Event event) {
	if(!isAccepted(event)) {
	    /* block this */
	    peerContext.getNodeContext().getLogger().error("Event not accepted: "+event.toJson());
	    return;
	}
	peerEventPreProcessing(event);
	try {
	    handleEventFromPeer(event);
	} finally {
	    peerEventPostProcessing(event);
	}
    }
    
    protected void peerEventPostProcessing(Event event) {
	/* by default we post this to the system bus too */
	EBusVM.getInstance().postSystemEvent(event);
    }

    public EventNodeContext getNodeContext() {
	return peerContext.getNodeContext();
    }

    protected PeerInfo getLocalPeerInfo() {
	return new PeerInfo(peerId, peerType, peerContext.getPeerInfo(),peerContext.getPeerAlias());
    }
    
    @Override
    public void onPeerLeaving(PeerLeavingReason reason) {
	postEvent(new PeerLeavingEvent(
		new PeerLeavingData(reason, getLocalPeerInfo())));
    }

    @Override
    public void onPeerError(PeerErrorData errorData) {
	errorData.setPeerInfo(getLocalPeerInfo());
	PeerCommErrorEvent event = new PeerCommErrorEvent(errorData);
	state.handlePeerError(new PeerEventContext<>(event, this));
	postEvent(event);
    }

    public PeersManagerContext getPeersContext() {
	return peersContext;
    }

    public void setPeersContext(PeersManagerContext peersContext) {
	this.peersContext = peersContext;
    }

    public PeerManagerConfig getConfig() {
	return config;
    }

    public void setConfig(PeerManagerConfig config) {
	this.config = config;
    }

    public void setPeerId(String peerId) {
	this.peerId = peerId;
    }

    public String getPeerType() {
	return peerType;
    }

    public void setPeerType(String peerType) {
	this.peerType = peerType;
    }

    public boolean isEventForwardingAllowed(EventContext ec) {
	return config.getEventsForwardingCondition().test(ec);
    }
    
}
