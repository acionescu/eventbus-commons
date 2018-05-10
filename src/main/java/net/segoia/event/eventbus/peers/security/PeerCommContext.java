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
package net.segoia.event.eventbus.peers.security;

import net.segoia.event.eventbus.peers.PeerContext;
import net.segoia.event.eventbus.peers.vo.comm.NodeCommunicationStrategy;

public class PeerCommContext {
    private int ourIdentityIndex;
    private int peerIdentityIndex;
    private NodeCommunicationStrategy txStrategy;
    private NodeCommunicationStrategy rxStrategy;
    private PeerContext peerContext;

    public PeerCommContext(int ourIdentityIndex, int peerIdentityIndex, NodeCommunicationStrategy txStrategy,
	    NodeCommunicationStrategy rxStrategy, PeerContext peerContext) {
	super();
	this.ourIdentityIndex = ourIdentityIndex;
	this.peerIdentityIndex = peerIdentityIndex;
	this.txStrategy = txStrategy;
	this.rxStrategy = rxStrategy;
	this.peerContext = peerContext;
    }

    public int getOurIdentityIndex() {
	return ourIdentityIndex;
    }

    public int getPeerIdentityIndex() {
	return peerIdentityIndex;
    }

    public NodeCommunicationStrategy getTxStrategy() {
	return txStrategy;
    }

    public NodeCommunicationStrategy getRxStrategy() {
	return rxStrategy;
    }

    public PeerContext getPeerContext() {
	return peerContext;
    }

}
