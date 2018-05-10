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
import net.segoia.event.eventbus.peers.events.session.PeerSessionStartedEvent;
import net.segoia.event.eventbus.peers.manager.states.PeerManagerState;
import net.segoia.event.eventbus.peers.security.CommOperationException;
import net.segoia.event.eventbus.peers.security.EventNodeSecurityManager;
import net.segoia.event.eventbus.peers.vo.session.SessionInfo;
import net.segoia.event.eventbus.peers.vo.session.SessionStartedData;

public class ConfirmProtocolToPeerState extends PeerManagerState {

    @Override
    public void onEnterState(PeerManager peerManager) {

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
	registerPeerEventProcessor(PeerSessionStartedEvent.class, (c) -> {
	    handleSessionStarted(c);
	});
    }

    protected void handleSessionStarted(PeerEventContext<PeerSessionStartedEvent> c) {
	PeerSessionStartedEvent event = c.getEvent();
	PeerManager peerManager = c.getPeerManager();

	SessionStartedData data = event.getData();

	SessionInfo sessionInfo = data.getSessionInfo();

//	peerManager.setUpSessionCommManager();

	PeerContext peerContext = peerManager.getPeerContext();
	EventNodeSecurityManager securityManager = peerContext.getNodeContext().getSecurityManager();
	try {

	    securityManager.buildSessionFromSessionInfo(peerContext, sessionInfo);
	} catch (CommOperationException e) {
	    peerManager.handleError(e);
	    return;
	}
	peerManager.setUpDirectCommManager();
	peerManager.setUpCommProtocolTransceiver();
	peerManager.onReady();
    }

}
