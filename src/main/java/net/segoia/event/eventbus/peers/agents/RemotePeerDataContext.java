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
package net.segoia.event.eventbus.peers.agents;

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.peers.PeerContext;
import net.segoia.event.eventbus.peers.PeerEventContext;
import net.segoia.event.eventbus.peers.PeerManager;
import net.segoia.event.eventbus.peers.RemotePeerManager;

public class RemotePeerDataContext extends PeerContext {
    public static final String PEER_PATH_SEPARATOR = ":";
    private String remotePeerId;
    private PeerManager gatewayPeer;
    /**
     * Create a full remote peer path composed from gateway peer id and remote peer id. This should be unique on the
     * local node and can act as a local remote peer id
     */
    private String fullRemotePeerPath;

    /**
     * The peer manager created to handle this remote peer
     */
    private RemotePeerManager remotePeerManager;

    private String commChannel;

    public RemotePeerDataContext(RemotePeerEventContext rpec) {
	super(null, null);
	PeerEventContext triggerEventContext = rpec.getTriggerEventContext();
	gatewayPeer = triggerEventContext.getPeerManager();
	Event event = triggerEventContext.getEvent();
	remotePeerId = rpec.getRemotePeerInfo().getPeerId();
	fullRemotePeerPath = gatewayPeer.getPeersContext().getFullPathForRemotePeer(gatewayPeer.getPeerId(),
		remotePeerId);
	
	/* the local peer id will be set by peers manager */
	
//	/* if not overwritten, use this as local remote peer id */
//	setPeerId(fullRemotePeerPath);
	
	
	setRemoteAgent(true);
	setCauseEvent(event);
	commChannel = gatewayPeer.getPeerContext().getCommunicationChannel();
    }

    @Override
    public void sendEventToPeer(Event event) {
	/* instruct gateway to forward the event to the remote peer */
	event.to(remotePeerId);

	gatewayPeer.forwardToPeer(event);
    }

    public String getRemotePeerId() {
	return remotePeerId;
    }

    public PeerManager getGatewayPeer() {
	return gatewayPeer;
    }

    public String getFullRemotePeerPath() {
	return fullRemotePeerPath;
    }

    public String getGatewayPeerId() {
	return gatewayPeer.getPeerId();
    }

    public RemotePeerManager getRemotePeerManager() {
	return remotePeerManager;
    }

    public void setRemotePeerManager(RemotePeerManager remotePeerManager) {
	this.remotePeerManager = remotePeerManager;
    }

    @Override
    public String getCommunicationChannel() {
	return commChannel;
    }

}
