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

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.peers.comm.PeerCommManager;
import net.segoia.event.eventbus.peers.core.EventTransceiver;
import net.segoia.event.eventbus.peers.core.PrivateIdentityData;
import net.segoia.event.eventbus.peers.core.PrivateIdentityManager;
import net.segoia.event.eventbus.peers.core.PublicIdentityManager;
import net.segoia.event.eventbus.peers.manager.states.PeerStateContext;
import net.segoia.event.eventbus.peers.security.PeerCommContext;
import net.segoia.event.eventbus.peers.security.SessionManager;
import net.segoia.event.eventbus.peers.vo.NodeInfo;
import net.segoia.event.eventbus.peers.vo.comm.CommunicationProtocol;
import net.segoia.event.eventbus.peers.vo.session.SessionKey;
import net.segoia.event.eventbus.vo.services.NodeIdentityProfile;

public class PeerContext {
    private String peerId;

    /**
     * The relay through which the current node communicates with the peer node
     */
    private EventTransceiver transceiver;

    private EventRelay relay;

    private NodeInfo peerInfo;

    /**
     * If set true, then this peer acts as server, and we are a client
     */
    private boolean inServerMode;

    /**
     * The protocol used to communicate with the peer
     */
    private CommunicationProtocol commProtocol;

    private SessionKey sessionKey;

    private boolean remoteAgent;

    private EventNodeContext nodeContext;

    /**
     * A public identity manger for this peer
     */
    private PublicIdentityManager peerIdentityManager;

    private PrivateIdentityManager ourIdentityManager;

    private SessionManager sessionManager;

    private PeerCommManager peerCommManager;

    private PeerCommContext peerCommContext;

    /**
     * In case we want to specify custom identities to use with this particular peer
     */
    private List<PrivateIdentityData<?>> ourAvailableIdentities;

    private NodeInfo ourNodeInfo;

    /**
     * Unique id to identify the peer agent
     */
    private String peerIdentityKey;

    /**
     * The root identity key for the peer
     */
    private String peerRootIdentityKey;

    private NodeIdentityProfile peerIdentityProfile;
    
    private String peerAlias;

    public PeerContext(String peerId, EventTransceiver transceiver) {
	super();
	this.peerId = peerId;
	this.transceiver = transceiver;
    }

    public EventRelay getRelay() {
	return relay;
    }

    public void setRelay(EventRelay relay) {
	this.relay = relay;
    }

    public NodeInfo getPeerInfo() {
	return peerInfo;
    }

    public void setPeerInfo(NodeInfo peerInfo) {
	this.peerInfo = peerInfo;
    }

    public String getPeerId() {
	return peerId;
    }

    public String getCommunicationChannel() {
	return relay.getChannel();
    }

    public boolean isInServerMode() {
	return inServerMode;
    }

    public void setInServerMode(boolean inServerMode) {
	this.inServerMode = inServerMode;
    }

    public CommunicationProtocol getCommProtocol() {
	return commProtocol;
    }

    public void setCommProtocol(CommunicationProtocol commProtocol) {
	this.commProtocol = commProtocol;
    }

    public SessionKey getSessionKey() {
	return sessionKey;
    }

    public void setSessionKey(SessionKey sessionKey) {
	this.sessionKey = sessionKey;
    }

    public boolean isRemoteAgent() {
	return remoteAgent;
    }

    public void setRemoteAgent(boolean remoteAgent) {
	this.remoteAgent = remoteAgent;
    }

    public EventNodeContext getNodeContext() {
	return nodeContext;
    }

    public void setNodeContext(EventNodeContext nodeContext) {
	this.nodeContext = nodeContext;
    }

    public PublicIdentityManager getPeerIdentityManager() {
	return peerIdentityManager;
    }

    public void setPeerIdentityManager(PublicIdentityManager peerIdentityManager) {
	this.peerIdentityManager = peerIdentityManager;
	peerIdentityKey = peerIdentityManager.getIdentityKey();
    }

    public PeerCommManager getPeerCommManager() {
	return peerCommManager;
    }

    public void setPeerCommManager(PeerCommManager peerCommManager) {
	this.peerCommManager = peerCommManager;
    }

    public SessionManager getSessionManager() {
	return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
	this.sessionManager = sessionManager;
    }

    public PrivateIdentityManager getOurIdentityManager() {
	return ourIdentityManager;
    }

    public void setOurIdentityManager(PrivateIdentityManager ourIdentityManager) {
	this.ourIdentityManager = ourIdentityManager;
    }

    public PeerCommContext getPeerCommContext() {
	return peerCommContext;
    }

    public void setPeerCommContext(PeerCommContext peerCommContext) {
	this.peerCommContext = peerCommContext;
    }

    public EventTransceiver getTransceiver() {
	return transceiver;
    }

    public void setTransceiver(EventTransceiver transceiver) {
	this.transceiver = transceiver;
    }

    public NodeIdentityProfile getCurrentPeerIdentityProfile() {
	if (peerIdentityProfile == null) {
	    peerIdentityProfile = nodeContext.getSecurityManager().getCurrentPeerIdentityProfile(this);
	}
	return peerIdentityProfile;
    }

    public boolean isEventAccepted(PeerStateContext context) {
	return nodeContext.getSecurityManager().isEventAccepted(context);
    }

    public List<PrivateIdentityData<?>> getOurAvailableIdentities() {
	return ourAvailableIdentities;
    }

    public void setOurAvailableIdentities(List<PrivateIdentityData<?>> ourAvailableIdentities) {
	this.ourAvailableIdentities = ourAvailableIdentities;
    }

    public NodeInfo getOurNodeInfo() {
	return ourNodeInfo;
    }

    public void setOurNodeInfo(NodeInfo ourNodeInfo) {
	this.ourNodeInfo = ourNodeInfo;
    }

    public String getPeerIdentityKey() {
	return peerIdentityKey;
    }

    public String getPeerRootIdentityKey() {
	if (peerRootIdentityKey == null) {
	    NodeIdentityProfile pip = getCurrentPeerIdentityProfile();
	    if (pip != null) {
		peerRootIdentityKey = pip.getRootIdentityKey();
	    }
	}
	return peerRootIdentityKey;
    }

    public void sendEventToPeer(Event event) {
	if (peerInfo != null) {
	    event.to(peerInfo.getNodeId());
	}
	
	//TODO: remove our node id from relays
	event.getHeader().removeRelay(nodeContext.getLocalNodeId());
	
        System.out.println(getNodeContext().getLocalNodeId() +" sending to "+peerId +" "+event.toJson());
	relay.sendEvent(event);
       
    }

    public String getPeerAlias() {
        return peerAlias;
    }

    public void setPeerAlias(String peerAlias) {
        this.peerAlias = peerAlias;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

}
