/**
 * event-bus - An event bus framework for event driven programming
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

import net.segoia.event.eventbus.peers.PeerManager;
import net.segoia.event.eventbus.peers.manager.states.PeerManagerState;

public class PeerBindRequestedState extends PeerManagerState{

    @Override
    public void onEnterState(PeerManager peerManager) {
	//TODO: check if we allow this
	
	peerManager.goToState(PeerManager.PEER_BIND_ACCEPTED);
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
	
    }

}
