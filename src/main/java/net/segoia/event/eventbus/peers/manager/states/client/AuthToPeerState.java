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
import net.segoia.event.eventbus.peers.events.auth.PeerAuthAcceptedEvent;
import net.segoia.event.eventbus.peers.events.auth.PeerProtocolConfirmedEvent;
import net.segoia.event.eventbus.peers.exceptions.PeerAuthRequestRejectedException;
import net.segoia.event.eventbus.peers.exceptions.PeerCommunicationNegotiationFailedException;
import net.segoia.event.eventbus.peers.exceptions.PeerRequestRejectedException;
import net.segoia.event.eventbus.peers.manager.states.PeerManagerState;
import net.segoia.event.eventbus.peers.vo.auth.PeerAuthAccepted;
import net.segoia.event.eventbus.peers.vo.auth.ProtocolConfirmation;
import net.segoia.event.eventbus.peers.vo.comm.CommunicationProtocol;

public class AuthToPeerState extends PeerManagerState{

    @Override
    public void onEnterState(PeerManager peerManager) {
	// TODO Auto-generated method stub
	
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
    protected void registerPeerEventHandlers() {
	registerPeerEventProcessor(PeerAuthAcceptedEvent.class, (c)->{
	    handlePeerAuthAccepted(c);
	});
	
    }
    
   
    protected void handlePeerAuthAccepted(PeerEventContext<PeerAuthAcceptedEvent> c) {
	PeerAuthAcceptedEvent event = c.getEvent();
	PeerManager peerManager = c.getPeerManager();
	PeerAuthAccepted data = event.getData();

	CommunicationProtocol protocolFromPeer = data.getCommunicationProtocol();

	PeerContext peerContext = peerManager.getPeerContext();
	CommunicationProtocol ourProtocol = peerContext.getCommProtocol();

	if (ourProtocol == null) {
	    /* if we hanve't proposed a protocol, see if we can find a matching supported protocol */
	    try {
		ourProtocol = peerManager.getNodeContext().getSecurityManager().establishPeerCommunicationProtocol(peerContext);
	    } catch (PeerCommunicationNegotiationFailedException ex) {
		peerContext.getNodeContext().getLogger().error("Auth error",ex);
	    } catch (PeerAuthRequestRejectedException arex) {
		peerContext.getNodeContext().getLogger().error("Auth error",arex);
	    }

	    /* set the protocol on peer context */
	    peerContext.setCommProtocol(ourProtocol);
	}

	/* check that the two protocols match */

	
	if (ourProtocol.equals(protocolFromPeer)) {
	    /* yey, we have a matching protocol, send confirmation */
	    ProtocolConfirmation protocolConfirmation = new ProtocolConfirmation(ourProtocol);
	    
	    peerManager.goToState(PeerManager.CONFIRM_PROTOCOL_TO_PEER);
	    peerManager.forwardToPeer(new PeerProtocolConfirmedEvent(protocolConfirmation));
	    
	    /* after we send protocol confirmation event start using it */
//	    peerManager.onProtocolConfirmed();
            peerManager.setUpPeerCommContext();
	    peerManager.setUpSessionCommManager();
	}
	else {
	    /* protocols don't match */
	    peerManager.handleError(new PeerRequestRejectedException("PROTOCOLS don't match", peerContext));
	}
    }

}
