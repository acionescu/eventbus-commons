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

import net.segoia.event.eventbus.Event;

public class PeersManagerContext {
    private PeersManager peersManager;

    public PeersManagerContext(PeersManager peersManager) {
	super();
	this.peersManager = peersManager;
    }

    public <E extends Event> void handlePeerEvent(PeerEventContext<E> context) {
	peersManager.handlePeerEvent(context);
    }
    
    public String getFullPathForRemotePeer(String gatewayPeerId, String remotePeerId) {
	return peersManager.getFullPathForRemotePeer(gatewayPeerId, remotePeerId);
    }

}
