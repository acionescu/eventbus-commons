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
package net.segoia.event.eventbus.peers.vo.comm;

public class CommunicationProtocolDefinition {
    private NodeCommunicationStrategy serverCommStrategy;
    private NodeCommunicationStrategy clientCommStrategy;

    public CommunicationProtocolDefinition() {
	super();
    }

    public CommunicationProtocolDefinition(NodeCommunicationStrategy serverCommStrategy,
	    NodeCommunicationStrategy clientCommStrategy) {
	super();
	this.serverCommStrategy = serverCommStrategy;
	this.clientCommStrategy = clientCommStrategy;
    }

    public NodeCommunicationStrategy getServerCommStrategy() {
	return serverCommStrategy;
    }

    public void setServerCommStrategy(NodeCommunicationStrategy serverCommStrategy) {
	this.serverCommStrategy = serverCommStrategy;
    }

    public NodeCommunicationStrategy getClientCommStrategy() {
	return clientCommStrategy;
    }

    public void setClientCommStrategy(NodeCommunicationStrategy clientCommStrategy) {
	this.clientCommStrategy = clientCommStrategy;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((clientCommStrategy == null) ? 0 : clientCommStrategy.hashCode());
	result = prime * result + ((serverCommStrategy == null) ? 0 : serverCommStrategy.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	CommunicationProtocolDefinition other = (CommunicationProtocolDefinition) obj;
	if (clientCommStrategy == null) {
	    if (other.clientCommStrategy != null)
		return false;
	} else if (!clientCommStrategy.equals(other.clientCommStrategy))
	    return false;
	if (serverCommStrategy == null) {
	    if (other.serverCommStrategy != null)
		return false;
	} else if (!serverCommStrategy.equals(other.serverCommStrategy))
	    return false;
	return true;
    }

}
