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
package net.segoia.event.eventbus.peers.vo.bind;

import net.segoia.event.eventbus.peers.core.EventTransceiver;

public class DisconnectFromPeerRequest {
    private String peerId;
    private EventTransceiver transceiver;

    public DisconnectFromPeerRequest(EventTransceiver transceiver) {
	super();
	this.transceiver = transceiver;
    }

    public DisconnectFromPeerRequest(String peerId) {
	super();
	this.peerId = peerId;
    }

    public String getPeerId() {
	return peerId;
    }

    public void setPeerId(String peerId) {
	this.peerId = peerId;
    }

    public EventTransceiver getTransceiver() {
	return transceiver;
    }

    public void setTransceiver(EventTransceiver transceiver) {
	this.transceiver = transceiver;
    }

}
