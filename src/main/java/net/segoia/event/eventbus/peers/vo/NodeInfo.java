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

import net.segoia.event.eventbus.peers.vo.auth.NodeAuth;
import net.segoia.event.eventbus.peers.vo.security.EventNodeSecurityPolicy;

/**
 * Public information about a node
 * 
 * @author adi
 *
 */
public class NodeInfo {
    private String nodeId;
    private NodeAuth nodeAuth;
    private EventNodeSecurityPolicy securityPolicy;
    
    private String nodeType;

    public NodeInfo() {
	super();
    }

    public NodeInfo(String nodeId) {
	super();
	this.nodeId = nodeId;
    }

    public String getNodeId() {
	return nodeId;
    }

    public void setNodeId(String nodeId) {
	this.nodeId = nodeId;
    }

    public NodeAuth getNodeAuth() {
	return nodeAuth;
    }

    public void setNodeAuth(NodeAuth nodeAuth) {
	this.nodeAuth = nodeAuth;
    }

    public EventNodeSecurityPolicy getSecurityPolicy() {
	return securityPolicy;
    }

    public void setSecurityPolicy(EventNodeSecurityPolicy securityPolicy) {
	this.securityPolicy = securityPolicy;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

}
