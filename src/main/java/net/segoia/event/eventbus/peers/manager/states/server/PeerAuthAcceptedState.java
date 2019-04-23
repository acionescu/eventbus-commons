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
package net.segoia.event.eventbus.peers.manager.states.server;

import net.segoia.event.eventbus.peers.PeerEventContext;
import net.segoia.event.eventbus.peers.PeerManager;
import net.segoia.event.eventbus.peers.core.PeerCommErrorEvent;
import net.segoia.event.eventbus.peers.events.auth.PeerProtocolConfirmedEvent;
import net.segoia.event.eventbus.peers.exceptions.PeerSessionException;
import net.segoia.event.eventbus.peers.manager.states.PeerManagerState;
import net.segoia.event.eventbus.peers.security.CommOperationException;
import net.segoia.event.eventbus.peers.vo.PeerErrorData;
import net.segoia.event.eventbus.peers.vo.auth.ProtocolConfirmation;
import net.segoia.event.eventbus.peers.vo.comm.CommunicationProtocol;

public class PeerAuthAcceptedState extends PeerManagerState {

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
	registerPeerEventProcessor(PeerProtocolConfirmedEvent.class, (c) -> {
	    handleProtocolConfirmed(c);
	});

    }

    protected void handleProtocolConfirmed(PeerEventContext<PeerProtocolConfirmedEvent> c) {
	PeerProtocolConfirmedEvent event = c.getEvent();
	PeerManager peerManager = c.getPeerManager();

	ProtocolConfirmation data = event.getData();

	/* check again if the protocols match */
	CommunicationProtocol ourProtocol = peerManager.getPeerContext().getCommProtocol();
	CommunicationProtocol peerProtocol = data.getProtocol();

	if (ourProtocol.equals(peerProtocol)) {
	    /* if they match, initiate the session */
            try {
                /* start using the protocol before sending the session started event */
                peerManager.onProtocolConfirmed();
            } catch (PeerSessionException ex) {
                peerManager.postEvent(new PeerCommErrorEvent(new PeerErrorData(-1, ex.getMessage())));
                return;
            } catch (CommOperationException ex) {
               peerManager.postEvent(new PeerCommErrorEvent(new PeerErrorData(0, ex.getMessage())));
                return;
            }
            catch(Throwable t){
                peerManager.postEvent(new PeerCommErrorEvent(new PeerErrorData(0, t.getMessage())));
                return;
            }
	    
	    peerManager.onReady();
	    
	   
	}

	else {
	    /* ups, they don't match */

	    // TODO: handle this
	}

    }

}
