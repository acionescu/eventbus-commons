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
package net.segoia.event.eventbus.peers.vo.auth;

import net.segoia.event.eventbus.peers.vo.NodeInfo;
import net.segoia.event.eventbus.peers.vo.comm.CommunicationProtocol;

public class PeerAuthRequest {
    private NodeInfo nodeInfo;
    /**
     * Can optionally propose a communication protocol
     */
    private CommunicationProtocol communicationProtocol;

    public PeerAuthRequest(NodeInfo nodeInfo) {
	super();
	this.nodeInfo = nodeInfo;

    }

    public PeerAuthRequest() {
	super();
    }

    public NodeInfo getNodeInfo() {
	return nodeInfo;
    }

    public void setNodeInfo(NodeInfo nodeInfo) {
	this.nodeInfo = nodeInfo;
    }

    public CommunicationProtocol getCommunicationProtocol() {
	return communicationProtocol;
    }

    public void setCommunicationProtocol(CommunicationProtocol communicationProtocol) {
	this.communicationProtocol = communicationProtocol;
    }

}
