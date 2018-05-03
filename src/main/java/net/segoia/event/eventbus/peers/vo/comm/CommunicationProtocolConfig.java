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

public class CommunicationProtocolConfig {
    /**
     * Position in the identities list of the selected server identity to be used
     */
    private int serverNodeIdentity=-1;
    /**
     * Position in the identities list of the selected client identity to be used
     */
    private int clientNodeIdentity=-1;

    public CommunicationProtocolConfig() {
	super();
	// TODO Auto-generated constructor stub
    }

    public CommunicationProtocolConfig(int serverNodeIdentity, int clientNodeIdentity) {
	super();
	this.serverNodeIdentity = serverNodeIdentity;
	this.clientNodeIdentity = clientNodeIdentity;
    }

    public int getServerNodeIdentity() {
	return serverNodeIdentity;
    }

    public void setServerNodeIdentity(int serverNodeIdentity) {
	this.serverNodeIdentity = serverNodeIdentity;
    }

    public int getClientNodeIdentity() {
	return clientNodeIdentity;
    }

    public void setClientNodeIdentity(int clientNodeIdentity) {
	this.clientNodeIdentity = clientNodeIdentity;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + clientNodeIdentity;
	result = prime * result + serverNodeIdentity;
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
	CommunicationProtocolConfig other = (CommunicationProtocolConfig) obj;
	if (clientNodeIdentity != other.clientNodeIdentity)
	    return false;
	if (serverNodeIdentity != other.serverNodeIdentity)
	    return false;
	return true;
    }

  
}
