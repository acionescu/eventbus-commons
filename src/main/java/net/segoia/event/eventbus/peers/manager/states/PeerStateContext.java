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
package net.segoia.event.eventbus.peers.manager.states;

import net.segoia.event.eventbus.Event;
import net.segoia.event.eventbus.peers.PeerContext;

public class PeerStateContext {
    private PeerManagerState state;
    private PeerContext peerContext;
    private Event event;

    public PeerStateContext(PeerManagerState state, PeerContext peerContext, Event event) {
	super();
	this.state = state;
	this.peerContext = peerContext;
	this.event = event;
    }

    public PeerManagerState getState() {
	return state;
    }

    public void setState(PeerManagerState state) {
	this.state = state;
    }

    public PeerContext getPeerContext() {
	return peerContext;
    }

    public void setPeerContext(PeerContext peerContext) {
	this.peerContext = peerContext;
    }

    public Event getEvent() {
	return event;
    }

    public void setEvent(Event event) {
	this.event = event;
    }

}
