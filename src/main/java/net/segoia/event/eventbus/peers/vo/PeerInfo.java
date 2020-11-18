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
package net.segoia.event.eventbus.peers.vo;

public class PeerInfo {
    /**
     * Local peer id
     */
    private String peerId;

    /**
     * The type of peer
     */
    private String peerType;

    /**
     * The info sent by the peer
     */
    private NodeInfo nodeInfo;
    
    /**
     * An alias to recognize thie peer by
     */
    private String alias;

    public PeerInfo(String peerId, String peerType, NodeInfo nodeInfo) {
	super();
	this.peerId = peerId;
	this.peerType = peerType;
	this.nodeInfo = nodeInfo;
    }
    
    public PeerInfo(String peerId, String peerType, NodeInfo nodeInfo, String alias) {
	super();
	this.peerId = peerId;
	this.peerType = peerType;
	this.nodeInfo = nodeInfo;
	this.alias = alias;
    }

    public String getPeerId() {
	return peerId;
    }

    public NodeInfo getNodeInfo() {
	return nodeInfo;
    }

    public String getPeerType() {
	return peerType;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    
}
