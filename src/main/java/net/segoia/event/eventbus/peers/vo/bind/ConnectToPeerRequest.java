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

import java.util.List;

import net.segoia.event.eventbus.peers.core.EventTransceiver;
import net.segoia.event.eventbus.peers.core.PrivateIdentityData;

public class ConnectToPeerRequest {
    private transient EventTransceiver transceiver;
    private transient List<PrivateIdentityData<?>> ourIdentities;
    /**
     * A custom alias for this peer
     */
    private String peerAlias;

    public ConnectToPeerRequest(EventTransceiver transceiver) {
	super();
	this.transceiver = transceiver;
    }

    public ConnectToPeerRequest(EventTransceiver transceiver, List<PrivateIdentityData<?>> ourIdentities) {
	super();
	this.transceiver = transceiver;
	this.ourIdentities = ourIdentities;
    }

    public ConnectToPeerRequest(EventTransceiver transceiver, String peerAlias) {
	super();
	this.transceiver = transceiver;
	this.peerAlias = peerAlias;
    }

    public EventTransceiver getTransceiver() {
	return transceiver;
    }

    public void setTransceiver(EventTransceiver transceiver) {
	this.transceiver = transceiver;
    }

    public List<PrivateIdentityData<?>> getOurIdentities() {
	return ourIdentities;
    }

    public void setOurIdentities(List<PrivateIdentityData<?>> ourIdentities) {
	this.ourIdentities = ourIdentities;
    }

    public String getPeerAlias() {
        return peerAlias;
    }

    public void setPeerAlias(String peerAlias) {
        this.peerAlias = peerAlias;
    }

}
