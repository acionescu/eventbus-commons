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
package net.segoia.event.eventbus.peers.manager.states.client;

import net.segoia.event.eventbus.peers.PeerContext;
import net.segoia.event.eventbus.peers.PeerEventContext;
import net.segoia.event.eventbus.peers.PeerManager;
import net.segoia.event.eventbus.peers.core.PeerCommErrorEvent;
import net.segoia.event.eventbus.peers.events.PeerConnectionFailedEvent;
import net.segoia.event.eventbus.peers.events.auth.PeerAuthRequestEvent;
import net.segoia.event.eventbus.peers.events.bind.PeerBindAcceptedEvent;
import net.segoia.event.eventbus.peers.manager.states.PeerManagerState;
import net.segoia.event.eventbus.peers.vo.PeerErrorData;
import net.segoia.event.eventbus.peers.vo.auth.PeerAuthRequest;
import net.segoia.event.eventbus.peers.vo.bind.PeerBindAccepted;

public class BindToPeerState extends PeerManagerState {

    @Override
    public void onEnterState(PeerManager peerManager) {
	peerManager.getPeerContext().getRelay().start();

    }

    @Override
    public void onExitState(PeerManager peerManager) {
	// TODO Auto-generated method stub

    }

    @Override
    protected void registerLocalEventHandlers() {
	// TODO Auto-generated method stub

    }
    
    @Override
    public void handlePeerError(PeerEventContext<PeerCommErrorEvent> eventContext) {
	PeerManager pm = eventContext.getPeerManager();
        final PeerErrorData data = eventContext.getEvent().getData();
        pm.getNodeContext().getLogger().error("Peer error: "+data.getMessage());
	if(pm.getPeerInfo() == null) {
	    /* connection didn't succeed */
	    pm.postEvent(new PeerConnectionFailedEvent(data));
	    
	}
    }

    @Override
    protected void registerPeerEventHandlers() {
	registerPeerEventProcessor(PeerBindAcceptedEvent.class, (c) -> {
	    handlePeerBindAccepted(c);
	});
	setPeerEventNotProcessedHandler((c)->{
	    c.getPeerManager().handleError(new RuntimeException("Expected "+PeerBindAcceptedEvent.class.getName() +" but got "+c.getEvent().getClass()));
	});
    }
    
    protected void handlePeerBindAccepted(PeerEventContext<PeerBindAcceptedEvent> c) {
	PeerBindAcceptedEvent event = c.getEvent();
	PeerBindAccepted resp = event.getData();

	String localPeerId = event.getLastRelay();

	PeerManager peerManager = c.getPeerManager();

	if (peerManager == null) {
	    throw new RuntimeException("No peer manager found for localPeerId " + localPeerId);
	}

	peerManager.handlePeerBindAccepted(resp);
	
	PeerContext peerContext = peerManager.getPeerContext();

	PeerAuthRequest peerAuthRequest = new PeerAuthRequest(peerContext.getOurNodeInfo());

	peerManager.goToState(PeerManager.AUTH_TO_PEER);
	peerManager.forwardToPeer(new PeerAuthRequestEvent(peerAuthRequest));
	event.setHandled();
    }

}
